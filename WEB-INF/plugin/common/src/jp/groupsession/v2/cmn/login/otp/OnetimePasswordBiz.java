package jp.groupsession.v2.cmn.login.otp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.mail.Sender;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.SubnetBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.OtpDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpAtokenDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpTokenDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpAtokenModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpTokenModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;
/**
 *
 * <br>[機  能] ワンタイムパスワード関連 ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class OnetimePasswordBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(OnetimePasswordBiz.class);
    /**一次トークン用ロックオブジェクト*/
    private static final Object LOCK_OTOKEN__ = new Object();
    /**通知先アドレス登録画面トークン用ロックオブジェクト*/
    private static final Object LOCK_ATOKEN__ = new Object();
    /**通知先アドレス登録画面トークン用ロックオブジェクト*/
    private CmnOtpConfModel otpConf__ = null;

    /**
     *
     * <br>[機  能] ワンタイムパスワード設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ワンタイムパスワード設定
     * @throws SQLException SQL実行時例外
     */
    public CmnOtpConfModel getOtpConf(Connection con) throws SQLException {
        if (otpConf__ != null) {
            return otpConf__;
        }

        CmnOtpConfModel ret = null;
        CmnOtpConfDao cocDao = new CmnOtpConfDao(con);
        List<CmnOtpConfModel> list = cocDao.select();
        if (list.isEmpty()) {
            ret = new CmnOtpConfModel();
        } else {
            ret = list.get(0);
        }

        otpConf__ = ret;
        return ret;
    }
    /**
    *
    * <br>[機  能] ワンタイムパスワードによる認証が必要か判定
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエスト
    * @param smodel セッションユーザ情報
    * @param con コネクション
    * @return ワンタイムパスワードによる認証が必要 true
    * @throws SQLException SQL実行時例外
    */
    public boolean isNeedOtpAuth(HttpServletRequest req,
           BaseUserModel smodel, Connection con) throws SQLException {
       CmnOtpConfModel otpConf = getOtpConf(con);
       log__.info("-- ワンタイムパスワード使用判定 開始");
       if (otpConf.getCocUseOtp() == GSConstMain.OTP_NOUSE) {
           return false;
       }

       boolean isOtpTarget = __isOtpUser(otpConf, smodel, con);
       boolean isOtpIPCond = __isOtpIPCond(otpConf, req);
       log__.info("-- ワンタイムパスワード使用判定 終了");
       return (isOtpTarget && isOtpIPCond);
    }
   /**
    *
    * <br>[機  能] ワンタイムパスワード対象IPか判定
    * <br>[解  説]
    * <br>[備  考]
    * @param otpConf ワンタイムパスワード設定
    * @param req リクエスト
    * @return 判定結果 true:対象
    */
   private boolean __isOtpIPCond(CmnOtpConfModel otpConf,
            HttpServletRequest req) {
        log__.info("--- ワンタイムパスワード使用 IP判定 開始");
        try {
            if (otpConf.getCocOtpIpcond() == GSConstMain.OTP_IPCOND_ALL) {
                return true;
            }
            String address = CommonBiz.getRemoteAddr(req);
            String ipStr = otpConf.getCocOtpIp();
            String[] chkArr = ipStr.split("\\r\\n");

            for (String chkStr : chkArr) {
                //プレフィックス or サブネットマスク指定時
                if (chkStr.indexOf(SubnetBiz.DELIMITER) > 0) {
                    SubnetBiz ipBiz = new SubnetBiz();
                    if (ipBiz.checkIPinRange(chkStr, address)) {
                        return false;
                    }
                    continue;
                }

                if (chkStr.indexOf("*") >= 0) {
                    chkStr = chkStr.substring(0, chkStr.indexOf("*"));
                } else {
                    if (address.equals(chkStr)) {
                        return false;
                    }
                    continue;
                }
                if (StringUtil.isNullZeroString(chkStr)) {
                    return false;
                }
                if (address.startsWith(chkStr)) {
                    return false;
                }
            }
        } finally {
            log__.info("--- ワンタイムパスワード使用 IP判定 終了");
        }
        return true;
    }
   /**
    *
    * <br>[機  能] ワンタイムパスワード対象ユーザか判定
    * <br>[解  説]
    * <br>[備  考]
    * @param otpConf ワンタイムパスワード設定
    * @param smodel ユーザモデル
    * @param con コネクション
    * @return 判定結果
    * @throws SQLException SQL実行時例外
    */
   private boolean __isOtpUser(CmnOtpConfModel otpConf,
           BaseUserModel smodel, Connection con) throws SQLException {
       log__.info("--- ワンタイムパスワード使用 ユーザ判定 開始");
       try {
           if (otpConf.getCocOtpUser() == GSConstMain.OTP_TARGET_ALL) {
               return true;
           }
           OtpDao otpDao = new OtpDao(con);
           return otpDao.isNeedOtpUser(smodel.getUsrsid(), otpConf);
       } finally {
           log__.info("--- ワンタイムパスワード使用 ユーザ判定 終了");
       }
   }
   /**
    *
    * <br>[機  能] ワンタイムパスワード通知メールを送信
    * <br>[解  説] 送信先アドレス未設定の場合は応答モデルのエラーコードを設定する
    * <br>[備  考]
    * @param smodel ログインユーザ
    * @param reqMdl リクエストモデル
    * @param con コネクション
    * @return 実行結果モデル
    * @throws SQLException SQL実行時例外
    *
    */
   public OtpSendResult sendOtp(
           BaseUserModel smodel, RequestModel reqMdl,
           Connection con) throws SQLException {
       OtpSendResult ret = new OtpSendResult();
       String sendTo = null;

       CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
       CmnUsrmInfModel usrInf = usrDao.select(smodel.getUsrsid());

       sendTo = usrInf.getUsiOtpsendAddress();
       //送信先アドレス未設定
       if (StringUtil.isNullZeroString(sendTo)) {
           MainCommonBiz mainBiz = new MainCommonBiz();
           CmnPconfEditModel pconfMdl = mainBiz.getCpeConf(smodel.getUsrsid(), con);
           if (pconfMdl.getCpeOtpsendKbn() == GSConstMain.PCONF_EDIT_ADM) {
               ret.setEcode(OtpSendResult.ECODE_NOADDRES_CANTEDIT);
               return ret;
           }

           CmnOtpAtokenModel coa = saveOtpAtoken(smodel, reqMdl);
           ret.setOtpToken(coa.getCoaToken());
           ret.setEcode(OtpSendResult.ECODE_NOADDRES_ABLEEDIT);

           return ret;

       }

       CmnOtpTokenModel cot = __saveOtpToken(smodel, reqMdl);
       ret.setOtpToken(cot.getCotToken());
       ret.setOtpSendDate(cot.getCotDate());
       sendOtpMail(ret, cot.getCotPass(), sendTo, reqMdl, con);
       if (ret.getEcode() == OtpSendResult.ECODE_SENDERROR) {
           //メール送信失敗時は登録済みのワンタイムパスワードを破棄
           CmnOtpTokenDao cotDao = new CmnOtpTokenDao(con);
           cotDao.delete(ret.getOtpToken());
           con.commit();
       }
       return ret;
   }
   /**
    *
    * <br>[機  能] トークン文字列作成
    * <br>[解  説]
    * <br>[備  考]
    * @param loginId ログインID
    * @return ハッシュ文字列
    * @throws NoSuchAlgorithmException SHA256未サポート例外
    */
   public String createToken(String loginId) throws NoSuchAlgorithmException {
       UDate now = new UDate();
     //ハッシュを生成したい元の文字列
       String source = loginId + String.valueOf(System.nanoTime()) + now.toString();
       //ハッシュ生成前にバイト配列に置き換える際のCharset
       Charset charset = StandardCharsets.UTF_8;
       //ハッシュアルゴリズム
       String algorithm = "SHA-256";

       //ハッシュ生成処理
       byte[] bytes = MessageDigest.getInstance(algorithm).digest(source.getBytes(charset));
       String result = Base64.encodeBase64String(bytes);
       return result;

   }
   /**
    *
    * <br>[機  能] ワンタイムパスワード文字列作成
    * <br>[解  説] 4桁 の数字列を返す
    * <br>[備  考]
    * @param loginId
    * @return ハッシュ文字列
    * @throws NoSuchAlgorithmException SHA256未サポート例外
    */
   public String createOtp() {
       Random rand = new Random(System.nanoTime());
       int max = (int) Math.pow(10, GSConstCommon.OTP_LENGTH);

       return String.format("%0" + GSConstCommon.OTP_LENGTH + "d", rand.nextInt(max));

   }
   /**
    *
    * <br>[機  能] 新規ワンタイムパスワード発行情報を登録
    * <br>[解  説]
    * <br>[備  考]
    * @param smodel ログインユーザモデル
    * @param reqMdl リクエストモデル
    * @return 発行済みワンタイムパスワード発行情報
    * @throws SQLException SQL実行時例外
    */
   public CmnOtpAtokenModel saveOtpAtoken(
           BaseUserModel smodel, RequestModel reqMdl) throws SQLException {
       CmnOtpAtokenModel ret = null;
       synchronized (LOCK_ATOKEN__) {
           Connection con = null;
           boolean succsess = false;
           try {
               con = GroupSession.getConnection(
                       reqMdl.getDomain(),
                       1000);
               con.setAutoCommit(false);

               CmnOtpAtokenDao coaDao = new CmnOtpAtokenDao(con);
               int limitCnt = 3;
               String token = "";
               //トークン重複確認
               boolean exist = true;
               while (exist && limitCnt > 1) {
                   token = createToken(smodel.getLgid());
                   CmnOtpAtokenModel mdl = coaDao.select(token);
                   if (mdl == null) {
                       exist = false;
                   }
                   limitCnt--;
               }
               UDate now = new UDate();
               UDate limit = now.cloneUDate();
               limit.addMinute(5);
               ret = new CmnOtpAtokenModel();
               ret.setCoaToken(token);
               ret.setCoaPass("");
               ret.setUsrSid(smodel.getUsrsid());
               ret.setCoaDate(now);
               ret.setCoaLimitDate(limit);
               coaDao.insert(ret);
               con.commit();
               succsess = true;
           } catch (SQLException sqle) {
               throw sqle;
           } catch (Exception e) {
               log__.error("データソース取得に失敗", e);
               throw new SQLException(e);
           } finally {
               if (con != null) {
                   if (!succsess) {
                       JDBCUtil.rollback(con);
                   }
                   JDBCUtil.closeConnection(con);
               }
               con = null;
           }
       }
       return ret;
   }
   /**
    *
    * <br>[機  能] 新規ワンタイムパスワード発行情報を登録
    * <br>[解  説]
    * <br>[備  考]
    * @param smodel ログインユーザモデル
    * @param reqMdl リクエストモデル
    * @return 発行済みワンタイムパスワード発行情報
    * @throws SQLException SQL実行時例外
    */
   private CmnOtpTokenModel __saveOtpToken(
           BaseUserModel smodel, RequestModel reqMdl) throws SQLException {
       CmnOtpTokenModel cot = null;
       synchronized (LOCK_OTOKEN__) {
           Connection con = null;
           boolean succsess = false;
           try {
               con = GroupSession.getConnection(
                       reqMdl.getDomain(),
                       1000);
               con.setAutoCommit(false);

               CmnOtpTokenDao cotDao = new CmnOtpTokenDao(con);
               int limitCnt = 3;
               String token = "";
               //トークン重複確認
               boolean exist = true;
               while (exist && limitCnt > 1) {
                   token = createToken(smodel.getLgid());
                   CmnOtpTokenModel mdl = cotDao.select(token);
                   if (mdl == null) {
                       exist = false;
                   }
                   limitCnt--;
               }
               String otp = createOtp();
               UDate now = new UDate();
               UDate limit = now.cloneUDate();
               limit.addMinute(GSConstCommon.OTP_LIMIT_TIME);

               cot = new CmnOtpTokenModel();
               cot.setCotToken(token);
               cot.setCotPass(otp);
               cot.setUsrSid(smodel.getUsrsid());
               cot.setCotDate(now);
               cot.setCotLimitDate(limit);
               cotDao.insert(cot);
               con.commit();
               succsess = true;
           } catch (SQLException sqle) {
               throw sqle;
           } catch (Exception e) {
               log__.error("データソース取得に失敗", e);
               throw new SQLException(e);
           } finally {
               if (con != null) {
                   if (!succsess) {
                       JDBCUtil.rollback(con);
                   }
                   JDBCUtil.closeConnection(con);
               }
               con = null;
           }
       }
       return cot;
   }

   /**
    * <br>[機  能]ワンタイムパスワードをE-mailにて送信する
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param result 送信結果モデル
    * @param otpass パスワード
    * @param address 送信先アドレス
    * @param reqMdl リクエストモデル
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
   public void sendOtpMail(
           OtpSendResult result,
           String otpass,
           String address,
           RequestModel reqMdl,
           Connection con) throws SQLException {

       GsMessage gsMsg = new GsMessage(reqMdl);
       CmnOtpConfModel otpConf = getOtpConf(con);

           Sender sender = null;
           try {
               //送信元メールアドレス
               String fromMail = otpConf.getCocSmtpFrom();
               log__.debug("mail.from ==>" + fromMail);
               //メールサーバーに接続
               Properties prop = new Properties();
               prop.setProperty("mail.smtp.connectiontimeout", "30000");
               prop.setProperty("mail.smtp.timeout", "30000");

               if (otpConf.getCocSmtpAuthType() == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
                   prop.setProperty("mail.smtp.starttls.enable", "true");
                   prop.setProperty("mail.smtp.starttls.required", "true");
                   prop.setProperty("mail.smtp.sasl.enable", "true");
                   sender = new Sender(prop, false);

                   String appRootPath =
                           (String) GroupSession.getContext().get(GSContext.APP_ROOT_PATH);
                   OAuthBiz authBiz = new OAuthBiz();
                   OauthMailServerModel serverMdl
                                       = authBiz.getSendServerData(con, otpConf.getCotSid(),
                                               appRootPath);

                   sender.connectOAuth(
                           serverMdl.getHost(),
                           serverMdl.getPort(),
                           serverMdl.getAccountId(),
                           serverMdl.getAccessToken());
               } else {
                   //SMTPサーバー
                   String smtpServer = otpConf.getCocSmtpUrl();
                   log__.debug("mail.smtp.server ==>" + smtpServer);

                   //認証ユーザID取得
                   String userId = otpConf.getCocSmtpUser();
                   log__.debug("smtp.userID ==>" + userId);
                   //パスフレーズ取得
                   String pass = GSPassword.getDecryPassword(otpConf.getCocSmtpPass());
                   log__.debug("smtp.passphrase ==>" + pass);

                   int portNumber =
                       NullDefault.getInt(
                               otpConf.getCocSmtpPort(), Sender.DEFAULT_PORT);

                   log__.debug("portNumber = " + portNumber);

                   boolean smtp = (!StringUtil.isNullZeroString(userId));

                   log__.debug("メールサーバーに接続開始");

                   //SSL_TLS通信の場合
                   if (otpConf.getCocSmtpSsluse() == MailEncryptBiz.ANGO_SSL_TLS) {
                       prop.setProperty("mail.smtp.socketFactory.class",
                                                   "javax.net.ssl.SSLSocketFactory");
                       prop.setProperty("mail.smtp.socketFactory.fallback", "false");
                       prop.setProperty("mail.smtp.socketFactory.port",
                                       String.valueOf(portNumber));
                   } else if (otpConf.getCocSmtpSsluse() == MailEncryptBiz.ANGO_STARTTLS) {
                     //STARTTLS通信の場合
                       prop.setProperty("mail.smtp.starttls.enable", "true");
                   }

                   sender = new Sender(prop, smtp);
                   if (smtp) {
                       sender.connect(smtpServer, portNumber, userId, pass);
                   } else {
                       sender.connect(smtpServer, portNumber, null, null);
                   }
               }
               log__.debug("メールサーバーに接続完了");


               log__.debug("送信先メールアドレス==>" + address);
               //件名
               String subject = gsMsg.getMessage("cmn.cmn004.mail.subject");
               log__.debug("mail.subject ==>" + subject);
               //メール本文
               StringBuilder bb = new StringBuilder();
               bb.append(gsMsg.getMessage("cmn.cmn004.mail.body"));
               bb.append(System.lineSeparator());
               bb.append(gsMsg.getMessage("cmn.cmn004.mail.body2"));
               bb.append(System.lineSeparator());
               bb.append(System.lineSeparator());
               bb.append(gsMsg.getMessage("cmn.cmn004.mail.body3",
                       new String[] { otpass }));
               bb.append(System.lineSeparator());
               bb.append(gsMsg.getMessage("cmn.cmn004.mail.body4",
                       new String[] { result.getOtpSendDateStr() }));
               String body = bb.toString();
               log__.debug("mail.body ==>" + body);
               log__.debug("メール送信開始");
               sender.send(subject, fromMail, address, body);
               log__.debug("メール送信終了");

               result.setOtpSendMailFrom(fromMail);
               result.setOtpSendMailTo(secretedToAddres(address));
               result.setOtpSendMailSubject(subject);

           } catch (MessagingException me) {
               log__.fatal("メールサーバへの接続に失敗しました。", me);
               result.setEcode(OtpSendResult.ECODE_SENDERROR);
           } catch (Exception e) {
               result.setEcode(OtpSendResult.ECODE_SENDERROR);
               log__.fatal("メール送信中に例外が発生しました。", e);
           } finally {
               if (sender != null) {
                   sender.disConnect();
               }
           }
   }
   /**
    *
    * <br>[機  能] メールアドレスのユーザIDとドメインの頭3文字を残し秘匿した文字列を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param address アドレス
    * @return 秘匿後文字列
    */
   public String secretedToAddres(String address) {
       int atIdx = address.indexOf("@");
       if (atIdx > 0) {
           String user = address.substring(0, atIdx);
           String domain = address.substring(atIdx);
           if (domain.length() <= 1) {
               domain = "";
           } else {
               domain = domain.substring(1);
           }
           if (user.length() > 3) {
               user = user.substring(0, 3) + "**";
           } else {
               user = user + "**";
           }
           if (domain.length() > 3) {
               domain = domain.substring(0, 3) + "**";
           } else {
               domain = domain + "**";
           }
           return user + "@" + domain;
       }
       return "";
   }
   /**
   *
   * <br>[機  能] 一次トークンの有効期限確認を行う
   * <br>[解  説]
   * <br>[備  考]
    * @param token 一次トークン
    * @param con コネクション
    * @return 結果モデル
    * @throws SQLException SQL実行時例外
    */
   public LoginResultModel checkTokenLive(String token,
          Connection con) throws SQLException {

       CmnOtpTokenDao tokenDao = new CmnOtpTokenDao(con);
       CmnOtpTokenModel tokenMdl = tokenDao.select(token);
       return checkTokenLive(tokenMdl);
   }
   /**
    *
    * <br>[機  能] 一次トークンの有効期限確認を行う
    * <br>[解  説] 不正なトークンか、有効期限内かを確認する
    * <br>[備  考]
    * @param tokenMdl トークンモデル
    * @return 結果モデル
    */
   public LoginResultModel checkTokenLive(
           CmnOtpTokenModel tokenMdl) {
       LoginResultModel resultModel = new LoginResultModel();
       if (tokenMdl == null) {
           //トークンが不正
           resultModel.setErrCode(LoginResultModel.ECODE_TOKENNONE);
           resultModel.setErrors();
           return resultModel;
       }

       UDate now = new UDate();
       if (now.compare(now, tokenMdl.getCotLimitDate()) == UDate.SMALL) {
           //トークンが有効期限切れ
           resultModel.setErrCode(LoginResultModel.ECODE_TOKENTIMEOVER);
           resultModel.setErrors();
           return resultModel;
       }

       return resultModel;
   }

   /**
    *
    * <br>[機  能] 一次トークンとワンタイムパスワードによる認証を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param token 一次トークン
    * @param pass パスワード
    * @param con コネクション
    * @return 結果モデル
    * @throws SQLException SQL実行時例外
    */
   public LoginResultModel otpAuth(String token,
           String pass, Connection con) throws SQLException {

       CmnOtpTokenDao tokenDao = new CmnOtpTokenDao(con);
       CmnOtpTokenModel tokenMdl = tokenDao.select(token);

       //トークン有効性チェック
       LoginResultModel resultModel = checkTokenLive(tokenMdl);
       if (resultModel.getErrCode() != LoginResultModel.ECODE_NONE) {
           return resultModel;
       }

       String tPass = tokenMdl.getCotPass();
       if (tPass == null) {
           tPass = "";
       }
       if (!tPass.equals(pass)) {
           //パスワードが不正
           resultModel.setErrCode(LoginResultModel.ECODE_MISS_OTPASS);
           resultModel.setErrors();
           return resultModel;
       }

       AuthDao aDao = new AuthDao(con);
       BaseUserModel smodel = aDao.selectLogin(tokenMdl.getUsrSid());
       //該当ユーザなし
       if (smodel == null) {
           resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
           resultModel.setErrors();
           return resultModel;
       } else if (smodel.getUsrsid() == 1) {
           //システムメールユーザはログイン不可
           resultModel.setErrCode(LoginResultModel.ECODE_SYSMAIL_LOGIN);
           resultModel.setErrors();
           return resultModel;
       }
       if (smodel.getUsrsid() > GSConstUser.USER_RESERV_SID
               && smodel.getUsrUkoFlg() == GSConst.YUKOMUKO_MUKO) {
           //ログイン停止ユーザはログイン不可
           resultModel.setErrCode(LoginResultModel.ECODE_LOGINTEISI_USER);
           resultModel.setErrors();
           return resultModel;
       }
       resultModel.setLoginUser(smodel);
       resultModel.setResult(true);
       return resultModel;
   }
   /**
    *
    * <br>[機  能] API設定のベーシック認証が使用するかを確認
    * <br>[解  説] ベーシッk認証使用する場合にワンタイムパスワード機能を使用させないため
    * <br>[備  考]
    * @param con Connection
    * @return 判定結果
    * @throws SQLException SQL実行時例外
    */
   public boolean checkCompOtpUseCondApiConf(Connection con) throws SQLException {
       OtpDao otpDao = new OtpDao(con);
       return otpDao.checkCompOtpUseCondApiConf();
   }



}
