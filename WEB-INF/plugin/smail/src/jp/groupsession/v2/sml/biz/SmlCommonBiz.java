package jp.groupsession.v2.sml.biz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.mail.Sender;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstShortMail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrInoutDao;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountAutoDestDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountDiskDao;
import jp.groupsession.v2.sml.dao.SmlAccountForwardDao;
import jp.groupsession.v2.sml.dao.SmlAdminDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlLogCountDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlUserDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.AccountDataModel;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.LabelDataModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmlAccountDiskModel;
import jp.groupsession.v2.sml.model.SmlAccountForwardModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.model.SmlLogCountModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;
import jp.groupsession.v2.sml.model.SmlUserModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.sml.util.SmlConfigBundle;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;

/**
 * <br>[機  能] ショートメール共通ビジネスロジッククラス
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlCommonBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlCommonBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public SmlCommonBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public SmlCommonBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SmlCommonBiz() {
    }

    /**
     * <br>[機  能] ショートメール: 未開封の受信メッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param con コネクション
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行時例外
     */
    public int getUnopenedMsgCnt(int sacSid, Connection con) throws SQLException {

        log__.debug("未開封の受信メッセージ件数取得");

        int cnt = 0;
        SmlJmeisDao jdao = new SmlJmeisDao(con);
        cnt = jdao.getUnopenedMsgCnt(sacSid, GSConst.JTKBN_TOROKU);

        log__.debug("未開封の受信メッセージ件数 = " + cnt);

        return cnt;
    }

    /**
     * <br>[機  能] ショートメール: 未開封の受信メッセージ件数をアカウント単位で取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param accountList アカウントリスト
     * @param con コネクション
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, Integer> getAccountUnopenedMsgCnt(
            List<SmlAccountModel> accountList, Connection con) throws SQLException {

        log__.debug("未開封の受信メッセージ件数取得");

        Map<Integer, Integer> cntMap = new HashMap<>();
        SmlJmeisDao jdao = new SmlJmeisDao(con);
        cntMap = jdao.getAccountUnopenedMsgCnt(accountList, GSConst.JTKBN_TOROKU);

        log__.debug("未開封の受信メッセージ件数取得終了");

        return cntMap;
    }

    /**
     * <br>[機  能] ショートメール: 未開封の受信メッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param con コネクション
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行時例外
     */
    public int getUnopenedGomiMsgCnt(int sacSid, Connection con) throws SQLException {

        log__.debug("未開封の受信メッセージ件数取得");

        int cnt = 0;
        SmlJmeisDao jdao = new SmlJmeisDao(con);
        cnt = jdao.getUnopenedMsgCnt(sacSid, GSConstSmail.SML_JTKBN_GOMIBAKO);

        log__.debug("未開封の受信メッセージ件数 = " + cnt);

        return cnt;
    }

    /**
     * <br>[機  能] ショートメール: ログインユーザの未開封の受信メッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行時例外
     */
    public int getUnopenedMsgCntDef(int usrSid, Connection con) throws SQLException {

        log__.debug("未開封の受信メッセージ件数取得");

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = null;

        sacMdl = sacDao.selectFromUsrSid(usrSid);

        int cnt = 0;
        SmlJmeisDao jdao = new SmlJmeisDao(con);
        cnt = jdao.getUnopenedMsgCnt(sacMdl.getSacSid(), GSConst.JTKBN_TOROKU);

        log__.debug("未開封の受信メッセージ件数 = " + cnt);

        return cnt;
    }

    /**
     * <br>[機  能] ショートメール: 草稿のメール件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param con コネクション
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行時例外
     */
    public int getSokoMsgCnt(int sacSid, Connection con) throws SQLException {

        log__.debug("未開封の受信メッセージ件数取得");

        int cnt = 0;
        SmlWmeisDao jdao = new SmlWmeisDao(con);
        cnt = jdao.getSokoMsgCnt(sacSid);

        log__.debug("未開封の受信メッセージ件数 = " + cnt);

        return cnt;
    }

    /**
     * <br>[機  能] ショートメールソート条件のリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mode メールモード
     * @param reqMdl リクエスト情報
     * @return ArrayList
     */
    public static ArrayList < LabelValueBean > getSortLabelList(String mode,
                                                                RequestModel reqMdl) {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgMark = gsMsg.getMessage("cmn.mark");
        String msgSubject = gsMsg.getMessage("cmn.subject");
        String msgSnder = gsMsg.getMessage("cmn.sendfrom");
        String msgDate = gsMsg.getMessage("cmn.date");
        String msgSize = gsMsg.getMessage("cmn.size");

        log__.debug(">>>mode :" + mode);
        labelList.add(new LabelValueBean(msgMark, String.valueOf(GSConstSmail.MSG_SORT_KEY_MARK)));
        labelList.add(new LabelValueBean(msgSubject,
                                         String.valueOf(GSConstSmail.MSG_SORT_KEY_TITLE)));

        if (GSConstSmail.TAB_DSP_MODE_JUSIN.equals(mode)
                || GSConstSmail.TAB_DSP_MODE_GOMIBAKO.equals(mode)) {
            //受信モード
            labelList.add(new LabelValueBean(msgSnder,
                                             String.valueOf(GSConstSmail.MSG_SORT_KEY_NAME)));

        }

        labelList.add(new LabelValueBean(msgDate, String.valueOf(GSConstSmail.MSG_SORT_KEY_DATE)));
        labelList.add(new LabelValueBean(msgSize, String.valueOf(GSConstSmail.MSG_SORT_KEY_SIZE)));

        return labelList;
    }

    /**
     * <br>[機  能] 検索対象のデフォルト値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトターゲット配列
     */
    public static String[] getDefultSearchTarget() {
        String[] targets = {
                String.valueOf(GSConstSmail.SEARCH_TARGET_TITLE),
                String.valueOf(GSConstSmail.SEARCH_TARGET_HONBUN)
              };

        return targets;
    }

    /**
     * ショートメールの管理者設定を取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @return SmlAdminModel 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public SmlAdminModel getSmailAdminConf(int userSid, Connection con) throws SQLException {
        SmlAdminModel ret = null;
        SmlAdminDao dao = new SmlAdminDao(con);
        ret = dao.select();
        if (ret == null) {
            ret = new SmlAdminModel(userSid);
        }
        return ret;
    }

    /**
     * ユーザSIDを指定しショートメール個人設定を取得しいます。
     * <br>[機  能]
     * <br>[解  説] 設定値が無い場合は初期値を返します。
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return SmlUserModel 個人設定
     */
    public SmlUserModel getSmailUserConf(int userSid, Connection con) throws SQLException {
        //表示件数を取得
        SmlUserDao smlDao = new SmlUserDao(con);
        SmlUserModel smlMdl = new SmlUserModel();
        smlMdl.setUsrSid(userSid);

        SmlUserModel result = smlDao.select(smlMdl);
        if (result == null) {
            result = new SmlUserModel();
            result.setSmlMaxDsp(GSConstSmail.MAX_RECORD_COUNT);
            result.setSmlReload(GSConstSmail.MAIL_RELOAD_10MIN);
            result.setSmlMainKbn(GSConstSmail.SML_MAIN_KBN_MIDOKU);
            result.setSmlMainCnt(GSConstSmail.SML_MAIN_CNT_10);
            result.setSmlMainSort(GSConstSmail.SML_MAIN_SORT_KOUJYUN);
            result.setSmlPhotoDsp(GSConstSmail.SML_PHOTO_DSP_DSP);
            result.setSmlTempDsp(GSConstSmail.SML_IMAGE_TEMP_DSP);
        }
        return result;
    }

    /**
     * アカウントSID,ユーザSIDを指定しショートメール転送設定を取得しいます。
     * <br>[機  能]
     * <br>[解  説] 設定値が無い場合は初期値を返します。
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return SmlUserModel 個人設定
     */
    public SmlAccountForwardModel getSmailAccountForward(int sacSid,
                      int userSid, Connection con) throws SQLException {


        if (userSid < 0) {
            //アカウントのデフォルト使用ユーザを取得
            SmlAccountDao sacDao = new SmlAccountDao(con);
            SmlAccountModel sacMdl = null;
            sacMdl = sacDao.select(sacSid);
            if (sacMdl != null) {
                userSid = sacMdl.getUsrSid();
            }
        }

        SmlAccountForwardModel result = null;

        if (userSid > 0) {
            //表示件数を取得
            SmlAccountForwardDao smlDao = new SmlAccountForwardDao(con);
            SmlAccountForwardModel smlMdl = new SmlAccountForwardModel();
            smlMdl.setUsrSid(userSid);

            result = smlDao.getSafUserInfo(userSid, sacSid);
        }

        if (result == null) {
            result = new SmlAccountForwardModel();
            result.setUsrSid(userSid);
            result.setSacSid(sacSid);
        }


        return result;
    }

    /**
     * アカウントSID,を指定しショートメール転送設定を取得します。
     * <br>[機  能]
     * <br>[解  説] 設定値が無い場合は初期値を返します。
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return SmlUserModel 個人設定
     */
    public List<SmlAccountForwardModel> getSmailAccountForwardList(int sacSid,
                                       Connection con) throws SQLException {
        //表示件数を取得
        SmlAccountForwardDao smlDao = new SmlAccountForwardDao(con);
        List<SmlAccountForwardModel> smlMdlList = null;

        smlMdlList = smlDao.getSafUserInfoList(sacSid);

        return smlMdlList;
    }

    /**
     * <br>[機  能]ショートメール転送を行うユーザか判定します。
     * <br>[解  説]管理者設定で転送を禁止している場合はreturn false
     * <br>[備  考]
     * @param adminConf ショートメール管理者設定
     * @return true:転送をする
     * @throws SQLException SQL実行時例外
     */
    public boolean isSmailForwardOk(
            SmlAdminModel adminConf) throws SQLException {
        if (adminConf.getSmaMailfw() == GSConstSmail.MAIL_FORWARD_NG) {
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能]ショートメール転送を行うユーザか判定します。
     * <br>[解  説]管理者設定で転送を禁止している場合はreturn false
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param forwardMdl 転送設定
     * @param adminConf ショートメール管理者設定
     * @param con コネクション
     * @return true:転送をする
     * @throws SQLException SQL実行時例外
     */
    public boolean isSmailForwardOk(
            int sacSid,
            SmlAccountForwardModel forwardMdl,
            SmlAdminModel adminConf,
            Connection con) throws SQLException {
        if (adminConf.getSmaMailfw() == GSConstSmail.MAIL_FORWARD_NG) {
            return false;
        }

        //個人設定チェック
        if (forwardMdl.getSafMailfw() == GSConstSmail.MAIL_FORWARD_NG) {
            return false;
        }

        return true;
    }
    /**
     * <br>[機  能]ショートメールをE-mailにて転送します。
     * <br>[解  説]転送される条件：管理者設定で転送機能が有効にされていること
     *                             個人設定で転送を使用する場合
     * <br>[備  考]
     * @param sparam ショートメール送信情報
     * @param sdList 送信情報(SmailDetailModel)
     * @param sacSid 転送アカウントSID
     * @param fileList 添付ファイルリスト
     * @param adminConf 管理者設定
     * @param pconfig プラグイン設定
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return エラーフラグ
     */
    public int sendSmailForward(
            SmlSmeisModel sparam,
            ArrayList<SmailDetailModel> sdList,
            int sacSid,
            List<TempFileModel> fileList,
            SmlAdminModel adminConf,
            PluginConfig pconfig,
            Connection con) throws SQLException {

        int ret = -1;
        String sendMail = "";
        List<SmlAccountForwardModel> forwardConfList
                           = getSmailAccountForwardList(sacSid, con);
        GsMessage gsMsg = new GsMessage(reqMdl__);


        if (forwardConfList != null && !forwardConfList.isEmpty()) {

            Sender sender = null;
            try {

                //送信元メールアドレス
                String fromMail = adminConf.getSmaFromAdd();

                //メールサーバ接続
                try {
                    Properties prop = new Properties();
                    prop.setProperty("mail.smtp.connectiontimeout", "30000");
                    prop.setProperty("mail.smtp.timeout", "30000");
                    if (adminConf.getSmaAuthType() == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
                        //認証 = OAuth
                        prop.setProperty("mail.smtp.starttls.enable", "true");
                        prop.setProperty("mail.smtp.starttls.required", "true");
                        prop.setProperty("mail.smtp.sasl.enable", "true");
                        sender = new Sender(prop, false);

                        String appRootPath =
                                (String) GroupSession.getContext().get(GSContext.APP_ROOT_PATH);
                        OAuthBiz authBiz = new OAuthBiz();
                        OauthMailServerModel serverMdl
                                            = authBiz.getSendServerData(con, adminConf.getCotSid(),
                                                                        appRootPath);

                        sender.connectOAuth(
                                serverMdl.getHost(),
                                serverMdl.getPort(),
                                serverMdl.getAccountId(),
                                serverMdl.getAccessToken());
                    } else {
                        //認証 = 基本認証

                        //SMTPサーバー
                        String smtpServer = adminConf.getSmaSmtpurl();
                        log__.info("mail.smtp.server ==>" + smtpServer);
                        log__.info("mail.from ==>" + fromMail);

                        //認証ユーザID取得
                        String userId = adminConf.getSmaSmtpUser();
                        log__.info("smtp.userID ==>" + userId);
                        //パスフレーズ取得
                        String pass = GSPassword.getDecryPassword(adminConf.getSmaSmtpPass());
                        log__.info("smtp.passphrase ==>" + pass);

                        int portNumber =
                            NullDefault.getInt(
                                    adminConf.getSmaSmtpPort(), Sender.DEFAULT_PORT);

                        log__.info("portNumber = " + portNumber);

                        boolean smtp = (!StringUtil.isNullZeroString(userId));

                        log__.info("メールサーバーに接続開始");
                        //メールサーバーに接続
                        //SSL_TLS通信の場合
                        if (adminConf.getSmaSsl() == MailEncryptBiz.ANGO_SSL_TLS) {
                            prop.setProperty("mail.smtp.socketFactory.class",
                                                        "javax.net.ssl.SSLSocketFactory");
                            prop.setProperty("mail.smtp.socketFactory.fallback", "false");
                            prop.setProperty("mail.smtp.socketFactory.port",
                                            String.valueOf(portNumber));
                        } else if (adminConf.getSmaSsl() == MailEncryptBiz.ANGO_STARTTLS) {
                          //STARTTLS通信の場合
                            prop.setProperty("mail.smtp.starttls.enable", "true");
                        }
                        sender = new Sender(prop, smtp);
                        if (isSmailForwardOk(adminConf)) {
                            if (smtp) {
                                sender.connect(smtpServer, portNumber, userId, pass);
                            } else {
                                sender.connect(smtpServer, portNumber, null, null);
                            }
                        } else {
                            sender = null;
                        }
                    }
                    log__.info("メールサーバーに接続完了");
                } catch (Exception e) {
                    log__.warn("メールサーバーへの接続に失敗しました。", e);
                    sender.disConnect();
                    return GSConstSmail.ERROR_KBN;
                }

                for (SmlAccountForwardModel forwardConf : forwardConfList) {
                    if (!isSmailForwardOk(forwardConf.getSacSid(), forwardConf, adminConf, con)) {
                        ret = GSConstSmail.ERROR_KBN;
                    } else {
                        //転送する場合、転送先メールアドレスを取得
                        sendMail = __getSendToMailAddress(
                                forwardConf.getUsrSid(), forwardConf, pconfig, con);

                        //転送先メールアドレスが取得できなかった場合、メール送信を行わない
                        if (StringUtil.isNullZeroString(sendMail)) {
                            continue;
                        }

                        log__.info("送信先メールアドレス==>" + sendMail);
                        String tensou = gsMsg.getMessage("cmn.forward");
                        //件名
                        String subject = tensou + " " + sparam.getSmsTitle();
                        log__.info("mail.subject ==>" + subject);
                        //メール本文
                        String body = __convertBody(sdList, forwardConf.getSacSid());
                        log__.info("mail.body ==>" + body);
                        log__.info("メール送信開始");
                        sender.sendFile(subject, fromMail, sendMail, body, fileList);
                        log__.info("メール送信終了");

                        //アカウントのデフォルトユーザの場合のみ更新
                        SmlAccountDao sacDao = new SmlAccountDao(con);
                        SmlAccountModel sacMdl = null;
                        sacMdl = sacDao.select(forwardConf.getSacSid());

                        if (sacMdl != null && sacMdl.getUsrSid() == forwardConf.getUsrSid()) {
                            //デフォルトユーザに転送した場合
                            ret = 1;
                        } else {
                            //デフォルトユーザ以外に転送した場合
                            if (ret < 0) {
                                ret = 0;
                            }
                        }
                        //既読区分変更
                        SmlJmeisDao jdao = new SmlJmeisDao(con);
                        int kbn = GSConstSmail.OPKBN_UNOPENED;
                        if (forwardConf != null && isSmailForwardOk(
                                sacMdl.getSacSid(), forwardConf, adminConf, con)) {
                            kbn = forwardConf.getSafSmailOp();
                        }
                        jdao.updateOpkbn(
                                sdList.get(0).getSmlSid(),
                                forwardConf.getSacSid(),
                                forwardConf.getSacSid(),
                                kbn,
                                new UDate());
                    }
                }

            } catch (MessagingException me) {
                log__.fatal("メールサーバへの接続に失敗しました。", me);
                ret = GSConstSmail.ERROR_KBN;
            } catch (Exception e) {
                log__.fatal("メール送信中に例外が発生しました。", e);
                ret = GSConstSmail.ERROR_KBN;
            } finally {
                if (sender != null) {
                    sender.disConnect();
                }
            }
        }

        return ret;
    }

    /**
     * 送信先メールアドレスを取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 送信先ユーザSID
     * @param forwardConf ショートメール転送設定
     * @param pconfig プラグイン設定
     * @param con コネクション
     * @return String 送信先メールアドレス
     * @throws SQLException SQL実行時例外
     */
    private String __getSendToMailAddress(
            int userSid,
            SmlAccountForwardModel forwardConf,
            PluginConfig pconfig,
            Connection con) throws SQLException {
        String sendMail = "";
        //転送先メールアドレスを取得
        sendMail = forwardConf.getSafMailDf();
        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSmail.PLUGIN_ID_ZAISEKI, pconfig)
                && forwardConf.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_OK) {
            //在席状況を取得
            CmnUsrInoutDao ioDao = new CmnUsrInoutDao(con);
            CmnUsrInoutModel ioMdl = ioDao.select(userSid);
            if (ioMdl.getUioStatus() == GSConst.UIOSTS_IN) {
                sendMail = forwardConf.getSafZmail1();
            } else if (ioMdl.getUioStatus() == GSConst.UIOSTS_LEAVE) {
                sendMail = forwardConf.getSafZmail2();
            } else if (ioMdl.getUioStatus() == GSConst.UIOSTS_ETC) {
                sendMail = forwardConf.getSafZmail3();
            }
        } else if (cmnBiz.isCanUsePlugin(GSConstSmail.PLUGIN_ID_ZAISEKI, pconfig)
                && forwardConf.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_FUZAI_OK) {
            //在席状況を取得
            CmnUsrInoutDao ioDao = new CmnUsrInoutDao(con);
            CmnUsrInoutModel ioMdl = ioDao.select(userSid);
            if (ioMdl.getUioStatus() == GSConst.UIOSTS_LEAVE) {
                sendMail = forwardConf.getSafZmail2();
            }
        }
        return sendMail;
    }

    /**
     * <br>[機  能] 本文を変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 検索結果
     * @param sacSid アカウントSID
     * @return cnvBody 変換後本文
     */
    private String __convertBody(ArrayList<SmailDetailModel> bean, int sacSid) {

        String newLine = "\r\n";
        int atesakiLimit = 4;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String tensou = gsMsg.getMessage("sml.97");
        String msgSender = gsMsg.getMessage("cmn.sendfrom")
                         + gsMsg.getMessage("wml.215");
        String msgTo = gsMsg.getMessage("cmn.from")
                     + gsMsg.getMessage("wml.215");
        String cnvBody = "";
        SmailDetailModel sMdl = bean.get(0);

        /******************** 見出し設定 ********************/
        cnvBody = tensou;
        cnvBody += newLine;

        /******************** 差出人設定 ********************/
        cnvBody += msgSender;
        String sousinsya = sMdl.getAccountName();
        //ユーザアカウントの場合、ユーザ名を差出人として使用する
        if (sMdl.getUsrSid() > 0
        && !StringUtil.isNullZeroString(sMdl.getUsiSei())) {
            sousinsya = sMdl.getUsiSei() + " " + sMdl.getUsiMei();
        }
        cnvBody += sousinsya;
        cnvBody += newLine;

        /******************** 宛先設定 ********************/
        cnvBody += "  " + msgTo;
        ArrayList<AtesakiModel> atsk = sMdl.getAtesakiList();
        int limCount = 0;
        boolean newLineFlg = false;

        for (int i = 0; i < atsk.size(); i++) {
            AtesakiModel aMdl = (AtesakiModel) atsk.get(i);
            String atesakiName = aMdl.getAccountName();
            int sendKbn = aMdl.getSmjSendkbn();
            if (sendKbn == GSConstSmail.SML_SEND_KBN_BCC
                    && sacSid != aMdl.getAccountSid()) {
                continue;
            }

            //ユーザアカウントの場合、ユーザ名を宛先として使用する
            if (aMdl.getUsrSid() > 0
            && !StringUtil.isNullZeroString(aMdl.getUsiSei())) {
                atesakiName = aMdl.getUsiSei() + " " + aMdl.getUsiMei();
            }

            if (newLineFlg) {
                cnvBody += "　　　　";
                newLineFlg = false;
            }
            cnvBody += atesakiName;

            if (i < atsk.size() - 1) {
                cnvBody += "; ";
            }

            limCount += 1;
            if (limCount == atesakiLimit
                && i != atsk.size() - 1) {
                limCount = 0;
                newLineFlg = true;
                cnvBody += newLine;
            }
        }
        cnvBody += newLine;

        String soushinDate = gsMsg.getMessage("sml.154");
        String msgSubject = gsMsg.getMessage("cmn.subject")
                         + gsMsg.getMessage("wml.215");
        String mark = gsMsg.getMessage("cmn.mark");
        /******************** 送信日設定 ********************/
        cnvBody += soushinDate;
        UDate sDate = sMdl.getSmsSdate();
        if (sDate != null) {
            String strSdate =
                UDateUtil.getSlashYYMD(sDate)
                + " "
                + UDateUtil.getSeparateHMS(sDate);
            cnvBody += strSdate;
        }
        cnvBody += newLine;

        /******************** 件名設定 ********************/
        cnvBody += "  " + msgSubject;
        cnvBody += sMdl.getSmsTitle();
        cnvBody += newLine;

        /******************** マーク設定 ********************/
        cnvBody += "  " + mark;
        String markStr = convertMark2(sMdl.getSmsMark());
        cnvBody += markStr;
        cnvBody += newLine;
        cnvBody += newLine;

        /******************** 本文設定 ********************/
        String[] splStr = sMdl.getSmsBody().split(newLine);
        if (splStr != null && splStr.length > 0) {
            for (int j = 0; j < splStr.length; j++) {
                cnvBody += __replaceStr(splStr[j]);
                cnvBody += newLine;
            }
        }

        cnvBody = __replaceStr(cnvBody);
        return cnvBody;
    }

    /**
     * <br>[機  能] Windows-31J - Unicode間の変換時に文字化けする文字を置換する
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字列
     * @return 変換後文字列
     */
    private String __replaceStr(String str) {
        if (StringUtil.isNullZeroString(str)) {
            return str;
        }

        //～
        String replaceStr = str.replace('\u301C', '\uFF5E');
        //∥
        replaceStr = replaceStr.replace('\u2016', '\u2225');
        //－
        replaceStr = replaceStr.replace('\u2212', '\uFF0D');
        //￠
        replaceStr = replaceStr.replace('\u00A2', '\uFFE0');
        //￡
        replaceStr = replaceStr.replace('\u00A3', '\uFFE1');
        //￢
        replaceStr = replaceStr.replace('\u00AC', '\uFFE2');

        return replaceStr;
    }

    /**
     * <br>[機  能] 区分値に応じ、マークの文字列表現へ変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mark マーク区分
     * @return cnvMark 変換後マーク
     */
    public String convertMark(int mark) {

        String cnvMark = "";

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msgColon = gsMsg.getMessage("wml.215");
        String msgMark = "";

        if (mark == GSConstSmail.MARK_KBN_NONE) {
            //変換無し
        } else if (mark == GSConstSmail.MARK_KBN_TEL) {
            msgMark = gsMsg.getMessage("cmn.phone");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_INP) {
            msgMark = gsMsg.getMessage("sml.61");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_SMAILY) {
            msgMark = gsMsg.getMessage("sml.11");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_WORRY) {
            msgMark = gsMsg.getMessage("sml.86");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_ANGRY) {
            msgMark = gsMsg.getMessage("sml.83");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_SADRY) {
            msgMark = gsMsg.getMessage("sml.87");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_BEER) {
            msgMark = gsMsg.getMessage("sml.15");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_HART) {
            msgMark = gsMsg.getMessage("sml.13");
            cnvMark = msgMark + msgColon;
        } else if (mark == GSConstSmail.MARK_KBN_ZASETSU) {
            msgMark = gsMsg.getMessage("sml.88");
            cnvMark = msgMark + msgColon;
        }

        return cnvMark;
    }

    /**
     * <br>[機  能] 区分値に応じ、マークの文字列表現へ変換する
     * <br>[解  説] 最後にコロン(:)が付いていない形
     * <br>[備  考]
     *
     * @param mark マーク区分
     * @return cnvMark 変換後マーク
     */
    public String convertMark2(int mark) {

        String cnvMark = "";

        GsMessage gsMsg = new GsMessage();
        String msgMark = "";

        if (mark == GSConstSmail.MARK_KBN_NONE) {
            msgMark = gsMsg.getMessage("cmn.no3");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_TEL) {
            msgMark = gsMsg.getMessage("cmn.phone");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_INP) {
            msgMark = gsMsg.getMessage("sml.61");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_SMAILY) {
            msgMark = gsMsg.getMessage("sml.11");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_WORRY) {
            msgMark = gsMsg.getMessage("sml.86");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_ANGRY) {
            msgMark = gsMsg.getMessage("sml.83");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_SADRY) {
            msgMark = gsMsg.getMessage("sml.87");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_BEER) {
            msgMark = gsMsg.getMessage("sml.15");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_HART) {
            msgMark = gsMsg.getMessage("sml.13");
            cnvMark = msgMark;
        } else if (mark == GSConstSmail.MARK_KBN_ZASETSU) {
            msgMark = gsMsg.getMessage("sml.88");
            cnvMark = msgMark;
        }

        return cnvMark;
    }

    /**
     * ショートメール全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value) {
        outPutLog(map, reqMdl, opCode, level, value, null, GSConstSmail.SML_LOG_FLG_NONE);
    }

    /**
     * ショートメール全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            String fileId,
            int logFlg) {
                outPutLog(map, reqMdl, opCode, level, value, -1, fileId, logFlg);
    }

    /**
     * ショートメール全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param smlSid ショートメールSID
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            int smlSid,
            String fileId,
            int logFlg) {

        GsMessage gsMsg = new GsMessage();
        String msgSmail = gsMsg.getMessage("cmn.shortmail");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstSmail.PLUGIN_ID_SMAIL);
        logMdl.setLogPluginName(msgSmail);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType()));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        switch (logFlg) {
            case GSConstSmail.SML_LOG_FLG_DOWNLOAD:
                logMdl.setLogCode("binSid：" + fileId);
                break;

            case GSConstSmail.SML_LOG_FLG_PDF:
                logMdl.setLogCode(" PDFエクスポート：" + smlSid);
                break;

            case GSConstSmail.SML_LOG_FLG_EML:
                logMdl.setLogCode(" EMLエクスポート：" + smlSid);
                break;

            default:
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * ショートメール管理ＡＰＩ全般のログ出力を行う
     * @param req リクエスト
     * @param con コネクション
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutApiLog(
            HttpServletRequest req,
            Connection con,
            int usid,
            String pgId,
            String opCode,
            String level,
            String value) {

        GsMessage gsMsg = new GsMessage();
        String msgSmail = gsMsg.getMessage("cmn.shortmail");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstSmail.PLUGIN_ID_SMAIL);
        logMdl.setLogPluginName(msgSmail);
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(getPgName(pgId));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();
        if (id == null) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage();
        String msg = "";

        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.api.smail.send.ApiSmlSendAction")) {
            msg = gsMsg.getMessage("cmn.send.shortmail");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.api.smail.filedownload.ApiSmlFileDownloadAction")) {
            msg = gsMsg.getMessage("sml.156");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml010.Sml010Action")) {
            msg = gsMsg.getMessage("sml.115");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml020.Sml020Action")) {
            msg = gsMsg.getMessage("sml.117");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml020kn.Sml020knAction")) {
            msg = gsMsg.getMessage("sml.118");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml030.Sml030Action")) {
            msg = gsMsg.getMessage("sml.121");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml040.Sml040Action")) {
            msg = gsMsg.getMessage("cmn.preferences2") + " "
                        + gsMsg.getMessage("cmn.display.settings");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml060.Sml060Action")) {
            msg = gsMsg.getMessage("sml.125");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml110.Sml110Action")) {
            msg = gsMsg.getMessage("sml.137");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml130kn.Sml130knAction")) {
            msg = gsMsg.getMessage("sml.148");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml140kn.Sml140knAction")) {
            msg = gsMsg.getMessage("sml.149");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml150kn.Sml150knAction")) {
            msg = gsMsg.getMessage("sml.134");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml160kn.Sml160knAction")) {
            msg = gsMsg.getMessage("sml.135");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml170.Sml170Action")) {
            msg = gsMsg.getMessage("sml.150");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml180kn.Sml180knAction")) {
            msg = gsMsg.getMessage("sml.136");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml240.Sml240Action")) {
            msg = gsMsg.getMessage("sml.172");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml250.Sml250Action")) {
            msg = gsMsg.getMessage("sml.173");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml250kn.Sml250knAction")) {
            msg = gsMsg.getMessage("sml.174");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml260.Sml260Action")) {
            msg = gsMsg.getMessage("sml.175");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml260kn.Sml260knAction")) {
            msg = gsMsg.getMessage("sml.176");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml270.Sml270Action")) {
            msg = gsMsg.getMessage("sml.177");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml280kn.Sml280knAction")) {
            msg = gsMsg.getMessage("cmn.admin.setting") + " "
                        + gsMsg.getMessage("cmn.preferences.kn");
            return msg;
        }

        if (id.equals("jp.groupsession.v2.sml.sml290.Sml290Action")) {
            msg = gsMsg.getMessage("sml.179");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml300kn.Sml300knAction")) {
            msg = gsMsg.getMessage("sml.180");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml310.Sml310Action")) {
            msg = gsMsg.getMessage("sml.181");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml320kn.Sml320knAction")) {
            msg = gsMsg.getMessage("sml.182");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml330.Sml330Action")) {
            msg = gsMsg.getMessage("sml.183");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml340kn.Sml340knAction")) {
            msg = gsMsg.getMessage("sml.184");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml350.Sml350Action")) {
            msg = gsMsg.getMessage("sml.185");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml360kn.Sml360knAction")) {
            msg = gsMsg.getMessage("sml.186");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml380.Sml380Action")) {
            msg = gsMsg.getMessage("sml.188");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml390.Sml390Action")) {
            msg = gsMsg.getMessage("cmn.admin.setting") + " " + gsMsg.getMessage("sml.sml390.01");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml390kn.Sml390knAction")) {
            msg = gsMsg.getMessage("cmn.admin.setting") + " " + gsMsg.getMessage("sml.sml390kn.01");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.sml.sml400kn.Sml400knAction")) {
            msg = gsMsg.getMessage("sml.sml400.01");
            return msg;
        }

        return ret;
    }

    /**
     * <br>[機  能] 送信ショートメールを論理削除する。
     * <br>[解  説] 日次バッチで使用する。
     * <br>[備  考]
     * @param con コネクション
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行時例外
     */
    public void deleteSmeis(Connection con,
            SmlAdelModel delMdl,
            int kbn) throws SQLException {

        SmlSmeisDao smsDao = new SmlSmeisDao(con);

        //削除するショートメールSIDリストを取得する。
        List<String> allDelList = new ArrayList<String>();
        int limit = GSConstSmail.SML_BATCH_DELETE_COUNT;
        int offset = 0;

        int maxCount = smsDao.getDeleteMailCount(delMdl, kbn);
        Map<Integer, Long> userMap = new HashMap<Integer, Long>();
        
        while (offset < maxCount) {
            Map<SmlSmeisModel, Long> delMap = smsDao.getDeleteMail(delMdl, kbn, limit, offset);
            for (Map.Entry<SmlSmeisModel, Long> map : delMap.entrySet()) {
                int sacSid = map.getKey().getSacSid();
                Long total = userMap.get(sacSid);
                if (total == null) {
                    total = Long.valueOf(0);
                }
                total += map.getValue();
                userMap.remove(sacSid);
                userMap.put(sacSid, total);
            }
            List<String> delList = delMap.keySet().stream()
                    .map(sid -> String.valueOf(sid.getSmsSid()))
                    .collect(Collectors.toList());
            allDelList.addAll(delList);
            offset += limit;
        }

        if (allDelList == null || allDelList.size() < 1 || allDelList.isEmpty()) {
            return;
        }

        int i = 0;
        int delCount = GSConstSmail.SML_BATCH_DELETE_COUNT;
        List<String> updateList = new ArrayList<String>();
        for (String smlSid : allDelList) {

            updateList.add(smlSid);
            i++;

            if (i > delCount) {
                //論理削除する。
                smsDao.delete(updateList);
                updateList = new ArrayList<String>();
                i = 0;
            }

        }

        if (updateList != null && updateList.size() > 0) {
            //論理削除する。
            smsDao.delete(updateList);
        }
        
        //ユーザ毎のディスクサイズ容量を更新
        for (Map.Entry<Integer, Long> map : userMap.entrySet()) {
            updateAccountDiskSize(con, map.getKey(), -map.getValue());
        }
    }

    /**
     * <br>[機  能] 受信ショートメールを論理削除する。
     * <br>[解  説] 日次バッチで使用する。
     * <br>[備  考]
     * @param con コネクション
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行時例外
     */
    public void deleteJmeis(Connection con,
            SmlAdelModel delMdl,
            int kbn) throws SQLException {

        SmlJmeisDao smjDao = new SmlJmeisDao(con);

        //削除するショートメールSIDリストを取得する。
        List<SmlJmeisModel> allDelList = new ArrayList<SmlJmeisModel>();
        int limit = GSConstSmail.SML_BATCH_DELETE_COUNT;
        int offset = 0;

        int maxCount = smjDao.getDeleteMailCount(delMdl, kbn);
        Map<Integer, Long> userMap = new HashMap<Integer, Long>();
        
        while (offset < maxCount) {
            Map<SmlJmeisModel, Long> delMap = smjDao.getDeleteMail(delMdl, kbn, limit, offset);
            for (Map.Entry<SmlJmeisModel, Long> map : delMap.entrySet()) {
                int sacSid = map.getKey().getSacSid();
                Long total = userMap.get(sacSid);
                if (total == null) {
                    total = Long.valueOf(0);
                }
                total += map.getValue();
                userMap.remove(sacSid);
                userMap.put(sacSid, total);
            }
            List<SmlJmeisModel> delList = delMap.keySet().stream().collect(Collectors.toList());
            allDelList.addAll(delList);
            offset += limit;
        }

        if (allDelList == null || allDelList.size() < 1 || allDelList.isEmpty()) {
            return;
        }

        int i = 0;
        int delCount = GSConstSmail.SML_BATCH_DELETE_COUNT;
        List<SmlJmeisModel> updateList = new ArrayList<SmlJmeisModel>();
        for (SmlJmeisModel model : allDelList) {

            updateList.add(model);
            i++;

            if (i > delCount) {
                //論理削除する。
                smjDao.delete(updateList);
                updateList = new ArrayList<SmlJmeisModel>();
                i = 0;
            }
        }

        if (updateList != null && updateList.size() > 0) {
            //論理削除する。
            smjDao.delete(updateList);
        }
        
        //ユーザ毎のディスクサイズ容量を更新
        for (Map.Entry<Integer, Long> map : userMap.entrySet()) {
            updateAccountDiskSize(con, map.getKey(), -map.getValue());
        }
    }

    /**
     * <br>[機  能] 草稿ショートメールを物理削除する。
     * <br>[解  説] 日次バッチで使用する。
     * <br>[備  考]
     * @param con コネクション
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行時例外
     */
    public void deleteWmeis(Connection con,
            SmlAdelModel delMdl,
            int kbn) throws SQLException {

        SmlWmeisDao smwDao = new SmlWmeisDao(con);

        //削除するショートメールSIDリストを取得する。
        List<String> allDelList = new ArrayList<String>();
        int limit = GSConstSmail.SML_BATCH_DELETE_COUNT;
        int offset = 0;
        int count = 1;

        int n = 0;
        Map<Integer, Long> userMap = new HashMap<Integer, Long>();
        
        while (n == 0) {
            Map<SmlWmeisModel, Long> delMap = smwDao.getDeleteMail(delMdl, kbn, limit, offset);
            for (Map.Entry<SmlWmeisModel, Long> map : delMap.entrySet()) {
                int sacSid = map.getKey().getSacSid();
                Long total = userMap.get(sacSid);
                if (total == null) {
                    total = Long.valueOf(0);
                }
                total += map.getValue();
                userMap.remove(sacSid);
                userMap.put(sacSid, total);
            }
            List<String> delList = delMap.keySet().stream()
                    .map(mdl -> String.valueOf(mdl.getSmwSid()))
                    .collect(Collectors.toList());
            if (delList == null || delList.size() < 1) {
                n = 1;
            } else {
                allDelList.addAll(delList);
            }
            offset = count * limit;
            count++;
        }

        if (allDelList == null || allDelList.size() < 1 || allDelList.isEmpty()) {
            return;
        }

        int i = 0;
        int delCount = GSConstSmail.SML_BATCH_DELETE_COUNT;
        List<Integer> deleteList = new ArrayList<Integer>();
        for (String smlSid : allDelList) {

            deleteList.add(Integer.parseInt(smlSid));
            i++;

            if (i > delCount) {
                //物理削除する
                deleteSoukouMail(con, deleteList);

                deleteList.clear();
                i = 0;
            }

        }

        if (deleteList != null && deleteList.size() > 0) {
            //物理削除する
            deleteSoukouMail(con, deleteList);
        }
        
        //ユーザ毎のディスクサイズ容量を更新
        for (Map.Entry<Integer, Long> map : userMap.entrySet()) {
            updateAccountDiskSize(con, map.getKey(), -map.getValue());
        }
    }

    /**
     *
     * <br>[機  能] 草稿メールの物理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param delSidList 削除対象のメールSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteSoukouMail(Connection con, List<Integer> delSidList) throws SQLException {
        SmlWmeisDao smwDao = new SmlWmeisDao(con);

        if (delSidList == null || delSidList.isEmpty()) {
            return;
        }

        //ショートメール情報(草稿)のデータ使用量を登録(削除対象のデータ使用量を減算)
        SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
        usedDataBiz.insertSoukouDataSize(delSidList, false);

        CmnBinfDao binDao = new CmnBinfDao(con);
        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(GSConstUser.SID_ADMIN);
        cbMdl.setBinUpdate(new UDate());

        SmlBinDao sbinDao = new SmlBinDao(con);
        List<Long> binSidList = null;

        //sid を1000件ずつ処理
        int sidx = 0;
        List<String> subSidList = null;
        for (int idx = 0; idx < delSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == delSidList.size()) {

                subSidList
                    = delSidList.subList(sidx, idx + 1).stream()
                            .map(String::valueOf)
                            .collect(Collectors.toList());

                //バイナリ情報を論理削除
                binSidList = sbinDao.selectBinSidList(subSidList);
                binDao.updateJKbn(cbMdl, binSidList);

                //ショートメールバイナリファイル送付情報を物理削除
                sbinDao.deleteSmlBin(subSidList);

                //ショートメール情報(草稿)を物理削除
                smwDao.deleteMsgButuri(subSidList);

                sidx = idx + 1;
            }
        }


        binDao.updateJKbn(cbMdl, binSidList);

    }

    /**
     * <br>[機  能] 指定したショートメールをユーザが閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSid アカウントSID
     * @param smlSid メールSID
     * @param userSid ユーザSID
     * @return true: 閲覧可能 false:閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isViewSmail(Connection con, int accountSid, int smlSid, int userSid)
    throws SQLException {
        return isViewSmail(con, accountSid, smlSid, -1, userSid);
    }

    /**
     * <br>[機  能] 指定したショートメールをユーザが閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSid アカウントSID
     * @param smlSid メールSID
     * @param binSid バイナリSID
     * @param userSid ユーザSID
     * @return true: 閲覧可能 false:閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isViewSmail(Connection con, int accountSid, int smlSid, long binSid, int userSid)
    throws SQLException {
        boolean result = false;

        if (accountSid <= 0 || userSid <= 0) {
            return false;
        }

        //指定されたアカウントを使用可能かを判定
        if (!canUseAccount(con, userSid, accountSid)) {
            return false;
        }

        SmailDao smlDao = new SmailDao(con);
        if (smlSid > 0) {
            result = smlDao.isViewSmail(accountSid, smlSid);
        }

        if (binSid > 0) {
            SmlBinDao smlBinDao = new SmlBinDao(con);
            int binSmlSid = smlBinDao.getSmlSid(binSid);
            if (binSmlSid > 0) {
                result = smlDao.isViewSmail(accountSid, binSmlSid);
            }
        }

        return result;
    }

    /**
     * <br>[機  能] WEBメールで使用する「引用符」(画面表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacQuotes アカウント情報 引用符
     * @param reqMdl リクエスト情報
     * @return 引用符(画面表示用)
     */
    public static String getViewMailQuotes(int wacQuotes, RequestModel reqMdl) {
        String quates = ">";
        switch (wacQuotes) {
            case GSConstSmail.SAC_QUOTES_DEF:
                quates = ">";
                break;
            case GSConstSmail.SAC_QUOTES_NONE:
                GsMessage gsMsg = new GsMessage(reqMdl);
                quates = gsMsg.getMessage("cmn.no");
                break;
            case GSConstSmail.SAC_QUOTES_2:
                quates = ">>";
                break;
            case GSConstSmail.SAC_QUOTES_3:
                quates = "<";
                break;
            case GSConstSmail.SAC_QUOTES_4:
                quates = "<<";
                break;
            case GSConstSmail.SAC_QUOTES_5:
                quates = "|";
                break;
            default:
        }

        return quates;
    }

    /**
     * <br>[機  能] ファイルコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return ファイルコンボ
     * @throws Exception 実行時例外
     */
    public List<LabelValueBean> getFileCombo(String tempDir) throws Exception {

        ArrayList<LabelValueBean> fileCombo = new ArrayList<LabelValueBean>();
        List<Cmn110FileModel> fileDataList = getFileData(tempDir);
        if (fileDataList != null && !fileDataList.isEmpty()) {
            for (Cmn110FileModel fileData : fileDataList) {
                fileCombo.add(new LabelValueBean(fileData.getFileName(),
                                                fileData.getSaveFileName()));
            }
        }

        return fileCombo;
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付ファイル情報
     * @throws Exception 実行時例外
     */
    public List<Cmn110FileModel> getFileData(String tempDir) throws Exception {

        List<Cmn110FileModel> fileDataList = new ArrayList<Cmn110FileModel>();
        List<String> fileNameList = IOTools.getFileNames(tempDir);

        if (fileNameList != null) {
            for (String fileName : fileNameList) {
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                //表示用リストへ追加
                fileDataList.add((Cmn110FileModel) fObj);
            }
        }

        return fileDataList;
    }

    /**
     * <br>[機  能] ユーザSIDからアカウントSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSids ユーザSID
     * @return accountSids アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public String[] getAccountSidFromUsr(Connection con, String[] usrSids)
    throws SQLException {


        List<String> newUserSid = new ArrayList<String>();
        List<String> accountUserSid = new ArrayList<String>();

        for (String usid : usrSids) {
            if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                //作成アカウント
                accountUserSid.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                newUserSid.add(usid);
            }
        }

        List<String> accountSids = new ArrayList<String>();
        List<String> usrSidsList = null;

        if (newUserSid != null && newUserSid.size() > 0) {
            //GSユーザのアカウントSIDを取得
            SmlAccountDao accountDao = new SmlAccountDao(con);
            usrSidsList = accountDao.selectFromUsrSids(
                    (String[]) newUserSid.toArray(new String[newUserSid.size()]));
            if (usrSidsList != null && !usrSidsList.isEmpty()) {
                accountSids.addAll(usrSidsList);
            }
        }

        if (accountUserSid != null && accountUserSid.size() > 0) {
            //アカウントSID
            accountSids.addAll(accountUserSid);
        }

        return (String[]) accountSids.toArray(new String[accountSids.size()]);
    }

    /**
     * <br>[機  能] アカウントディスク使用量を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid アカウントSID
     * @param sabunSize 差分容量
     * @throws SQLException SQL実行時例外
     */
    public void updateAccountDiskSize(Connection con, int sacSid, long sabunSize)
    throws SQLException {
        
        SmlAccountDiskModel diskMdl = null;
        SmlAccountDiskDao diskDao = null;
        try {
            diskDao = new SmlAccountDiskDao(con);
            if (diskDao.updateSabun(sacSid, sabunSize) == 0) {
                diskMdl = new SmlAccountDiskModel();
                diskMdl.setSacSid(sacSid);
                diskMdl.setSdsSize(sabunSize);
                diskDao.insert(diskMdl);
            }
        } finally {
            diskMdl = null;
            diskDao = null;
        }

    }

    /**
     * アカウント情報を設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param accountSid アカウントSID
     * @return accountUseFlg
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseAccount(Connection con, int usrSid, int accountSid)
                                                         throws SQLException {

        boolean accountUseFlg = false;
        SmailDao sacDao = new SmailDao(con);

        //使用可能なアカウントか判定
        accountUseFlg = sacDao.canUseCheckAccount(usrSid, accountSid);

        return accountUseFlg;

    }

    /**
     * <br>[機  能] アカウントコンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param adMdlList アカウントリスト
     * @return ArrayList (in LabelValueBean)  アカウントコンボ
     */
    public List<LabelValueBean> getAcntCombo(RequestModel reqMdl,
                                            List<AccountDataModel> adMdlList) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.select.plz"), "-1"));
        for (int i = 0; i < adMdlList.size(); i++) {
            labelList.add(
               new LabelValueBean(adMdlList.get(i).getAccountName(),
                       String.valueOf(adMdlList.get(i).getAccountSid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] ラベルコンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param lbMdlList ラベルリスト
     * @return ArrayList (in LabelValueBean)  ラベルコンボ
     */
    public List<LabelValueBean> getLbCombo(RequestModel reqMdl, List<LabelDataModel> lbMdlList) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        for (int i = 0; i < lbMdlList.size(); i++) {
            labelList.add(
               new LabelValueBean(lbMdlList.get(i).getLabelName(),
                       String.valueOf(lbMdlList.get(i).getLabelSid())));
        }
        return labelList;
    }


    /**
     * <br>[機  能] アカウントの編集が可能なユーザかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid アカウントSID
     * @param userSid セッションユーザSID
     * @return true:編集可能 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditAccount(Connection con, int sacSid, int userSid) throws SQLException {

        //使用者かを判定する
        SmailDao smailDao = new SmailDao(con);
        return smailDao.canUseCheckAccount(userSid, sacSid);
    }

    /**
     * アカウント情報を設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return sacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public int getDefaultAccount(Connection con, int usrSid) throws SQLException {

        int accountSid = -1;

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = null;

        //デフォルトのアカウントを取得
        sacMdl = sacDao.selectFromUsrSid(usrSid);

        if (sacMdl != null) {
            accountSid = sacMdl.getSacSid();
        }

        return accountSid;

    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考] OSチェック未実装
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    public String fileNameCheck(String fileName) {
            String escName = fileName;

            escName = StringUtilHtml.replaceString(escName, "/", "");
            escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
            escName = StringUtilHtml.replaceString(escName, "?", "");
            escName = StringUtilHtml.replaceString(escName, "*", "");
            escName = StringUtilHtml.replaceString(escName, ":", "");
            escName = StringUtilHtml.replaceString(escName, "|", "");
            escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
            escName = StringUtilHtml.replaceString(escName, "<", "");
            escName = StringUtilHtml.replaceString(escName, ">", "");
            escName = StringUtilHtml.replaceString(escName, ".", "");
            escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }

    /**
     * <br>[機  能] 指定したバイナリのデータが取得可能かチェックします。
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param userSid セッションユーザSID
     * @param smlSid メールSID
     * @param binSid バイナリSID
     * @return true: 可能 false:不可
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckSmailImage(int sacSid, int userSid, int smlSid, Long binSid)
            throws SQLException {

        SmlAccountDao sacDao = new SmlAccountDao(con__);

        //ユーザが使用可能なアカウントかチェックする。
        if (!sacDao.canUseAccount(sacSid, userSid)) {
            return false;
        }

        SmailDao smlDao = new SmailDao(con__);
        return smlDao.isCheckSmailImage(sacSid, smlSid, binSid);
    }

    /**
     * <br>[機  能] 送信メールの集計データを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid アカウントSID
     * @param cntTo TO件数
     * @param cntCc CC件数
     * @param cntBcc BCC件数
     * @param sendTime 送信日時
     *
     * @throws SQLException SQL実行時例外
     */
    public void regSmeisLogCnt(
            Connection con, int sacSid, int cntTo, int cntCc, int cntBcc, UDate sendTime)
                    throws SQLException {

        __registLogCnt(con, sacSid, GSConstSmail.LOG_COUNT_KBN_SMAIL,
                GSConstSmail.LOG_COUNT_SYSKBN_NORMAL, cntTo, cntCc, cntBcc, sendTime);
    }


    /**
     * <br>[機  能] 受信メールの集計データを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid アカウントSID
     * @param mailKbn メール区分 (TO or CC or BCC)
     * @param sendTime 受信日時
     *
     * @throws SQLException SQL実行時例外
     */
    public void regJmeisLogCnt(
            Connection con, int sacSid, int mailKbn, UDate sendTime)
                    throws SQLException {


        int cntTo = 0;
        int cntCc = 0;
        int cntBcc = 0;
        if (mailKbn == GSConstSmail.SML_SEND_KBN_ATESAKI) {
            cntTo = 1;
        } else if (mailKbn == GSConstSmail.SML_SEND_KBN_CC) {
            cntCc = 1;
        } else if (mailKbn == GSConstSmail.SML_SEND_KBN_BCC) {
            cntBcc = 1;
        }

        __registLogCnt(con, sacSid, GSConstSmail.LOG_COUNT_KBN_JMAIL,
                GSConstSmail.LOG_COUNT_SYSKBN_NORMAL, cntTo, cntCc, cntBcc, sendTime);
    }

    /**
     * <br>[機  能] 受信メールの集計データを登録します。(システムメール)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid アカウントSID
     * @param sendTime 受信日時
     *
     * @throws SQLException SQL実行時例外
     */
    public void regJmeisLogCntSystem(Connection con, int sacSid, UDate sendTime)
                    throws SQLException {

        __registLogCnt(con, sacSid, GSConstSmail.LOG_COUNT_KBN_JMAIL,
                GSConstSmail.LOG_COUNT_SYSKBN_SYSTEM, 1, 0, 0, sendTime);
    }

    /**
     * <br>[機  能] ショートメール 集計データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid アカウントSID
     * @param slcKbn ログ区分
     * @param slcSysKbn システムメール区分
     * @param cntTo TO件数
     * @param cntCc CC件数
     * @param cntBcc BCC件数
     * @param sendTime 送信日時
     * @throws SQLException SQL実行時例外
     */
    private void __registLogCnt(
            Connection con, int sacSid, int slcKbn, int slcSysKbn,
            int cntTo, int cntCc, int cntBcc, UDate sendTime)
                    throws SQLException {
        SmlLogCountModel slcMdl = new SmlLogCountModel();
        slcMdl.setSacSid(sacSid);
        slcMdl.setSlcKbn(slcKbn);
        slcMdl.setSlcSysKbn(slcSysKbn);
        slcMdl.setSlcCntTo(cntTo);
        slcMdl.setSlcCntCc(cntCc);
        slcMdl.setSlcCntBcc(cntBcc);
        slcMdl.setSlcDate(sendTime);
        SmlLogCountDao dao = new SmlLogCountDao(con);
        dao.insert(slcMdl);

//        //集計
//        SmlLogCountSumDao logSumDao = new SmlLogCountSumDao(con);
//        SmlLogCountSumModel logSumMdl = logSumDao.getSumLogCount(slcMdl, sendTime);
//        if (logSumMdl != null) {
//            if (logSumDao.update(logSumMdl) == 0) {
//                logSumDao.insert(logSumMdl);
//            }
//        }
    }
    /**
    *
    * <br>[機  能] 選択した値がグループコンボ上にない場合に有効な値を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param list ラベルリスト
    * @param nowSel 選択中ラベルID
    * @param defSel 初期ラベルID
    * @return 有効な選択値
    */
   public String getEnableSelectGroup(List<? extends LabelValueBean> list,
           String nowSel, String defSel) {
       boolean nowVar = false;
       boolean defVar = false;
       if (list == null || list.size() <= 0) {
           return "";
       }
       for (LabelValueBean label : list) {
           if (label.getValue().equals(nowSel)) {
               nowVar = true;
               break;
           }
           if (label.getValue().equals(defSel)) {
               defVar = true;
           }
       }
       if (nowVar) {
           return nowSel;
       }
       if (defVar) {
           return defSel;
       }
       return list.get(0).getValue();
   }

   /**
    * <br>[機  能] 送信可能なユーザを取得します。
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param userSidList ユーザSID
    * @param sessionUserSid セッションユーザSID
    * @return 指定したユーザSIDのうち、送信可能なユーザのユーザSID
    * @throws SQLException SQL実行時例外
    */
   public List<Integer> getValiableDestUsrSid(Connection con, int sessionUserSid,
           List<Integer> userSidList)
   throws SQLException {


       SmlBanDestDao sbdDao = new SmlBanDestDao(con);
       return sbdDao.getValiableDestUsrSidList(userSidList, sessionUserSid);

   }
   /**
    * <br>[機  能] 送信可能なアカウントを取得します。
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param accSidList アカウントSID
    * @param sessionUserSid セッションユーザSID
    * @return 指定したアカウントSIDのうち、送信可能なアカウントのアカウントSID
    * @throws SQLException SQL実行時例外
    */
   public List<Integer> getValiableDestAccSid(Connection con, int sessionUserSid,
           List<Integer> accSidList)
   throws SQLException {


       SmlBanDestDao sbdDao = new SmlBanDestDao(con);
       return sbdDao.getValiableDestAccSidList(accSidList, sessionUserSid);

   }
   /**
    *
    * <br>[機  能] 自動送信先アカウントモデル取得
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param sacSid 使用アカウントSID
    * @param saaType 送信先タイプ 0:TO 1:CC 2:BCC
    * @throws SQLException SQL実行時例外
    * @return 自動送信先アカウントリスト
    */
   public List<AtesakiModel> getAutoDestList(Connection con,
           int sacSid,
           int saaType) throws SQLException {
       SmlAccountAutoDestDao saaDao = new SmlAccountAutoDestDao(con);
       List<SmlAccountModel> accList = saaDao.getAutoDestAccounts(sacSid, saaType);
       List<AtesakiModel> ret = new ArrayList<AtesakiModel>();
       for (SmlAccountModel sacModel : accList) {
           AtesakiModel mdl = new AtesakiModel();
           mdl.setAccountName(sacModel.getSacName());
           mdl.setAccountSid(sacModel.getSacSid());
           mdl.setAccountJkbn(GSConstSmail.SAC_JKBN_NORMAL);
           mdl.setUsiSei(sacModel.getSacName());
           mdl.setUsiMei("");
           mdl.setUsrSid(sacModel.getUsrSid());
           ret.add(mdl);
       }
       return ret;
   }

  /**
  *
  * <br>[機  能]受信メールのリストを降順にして返す
  * <br>[解  説]
  * <br>[備  考]
  * @param meisList 降順にするSIDのリスト
  * @return 降順にしたSIDのリスト
  */
 public ArrayList<SmlJmeisModel> setOrderBySidDescMdl(ArrayList<SmlJmeisModel> meisList) {

     for (int source = 0; source < meisList.size(); source++) {
         for (int target = 0; target < meisList.size(); target++) {
             if (meisList.get(source).getSacSid() < meisList.get(target).getSacSid()) {
                 SmlJmeisModel tempMdl = meisList.get(source);
                 meisList.set(source, meisList.get(target));
                 meisList.set(target, tempMdl);
             }
         }
     }

     return meisList;
 }


 /**
 *
 * <br>[機  能]int型のSIDリストを降順にして返す
 * <br>[解  説]
 * <br>[備  考]
 * @param sidList 降順にするSIDのリスト
 * @return 降順にしたSIDのリスト
 */
public List<Integer> setOrderBySidDesc(List<Integer> sidList) {

    for (int source = 0; source < sidList.size(); source++) {
        for (int target = 0; target < sidList.size(); target++) {
            if (sidList.get(source) < sidList.get(target)) {
                int tempSid = sidList.get(source);
                sidList.set(source, sidList.get(target));
                sidList.set(target, tempSid);
            }
        }
    }
    return sidList;
}

/**
 *
 * <br>[機  能]String型のSIDリストを降順に並び替える
 * <br>[解  説]
 * <br>[備  考]
 * @param sidList String型のSIDリスト
 * @return 降順に並び替えられたString型のアカウントSID
 */
public ArrayList<String> setOrderBySidDescStr(ArrayList<String> sidList) {

   List<Integer> numList = new ArrayList<Integer>();
   for (String sidStr:sidList) {
       numList.add(NullDefault.getInt(sidStr, -1));
   }

   numList = setOrderBySidDesc(numList);

   sidList.clear();
   for (int numSid:numList) {
       sidList.add(String.valueOf(numSid));
   }

   return sidList;
}

    /**
     * <br>[機  能] メール本文の最大文字数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return メール本文の最大文字数
    */
    public static int getBodyLimitLength(String appRootPath) {
        int bodyLimit = getConfValue(appRootPath,
                                GSConstShortMail.MAILCONF_MAILBODY_LIMIT,
                                GSConstShortMail.MAILBODY_LIMIT_DEFAULT);

        if ((!(bodyLimit == -100 || bodyLimit == -101)) && bodyLimit < 0) {
            bodyLimit = GSConstShortMail.MAILBODY_LIMIT_DEFAULT;
        }
        return bodyLimit;
    }

    /**
     * <br>[機  能] ショートメールメール設定ファイルの設定値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param confKey 設定値のキー
     * @param defValue デフォルト値
     * @return 設定値
     */
    public static synchronized int getConfValue(String appRootPath, String confKey, int defValue) {
        String confValue = SmlConfigBundle.getValue(confKey);

        if (StringUtil.isNullZeroString(confValue)) {
            try {
                SmlConfigBundle.readConfig(appRootPath);
                confValue = SmlConfigBundle.getValue(confKey);
            } catch (IOException e) {
                log__.error("ショートメール設定ファイルの読み込みに失敗", e);
                return -100;
            }
        }

        if (!StringUtil.isNullZeroString(confValue)) {
            Perl5Util util = new Perl5Util();
            if (util.match("/^[0-9-]+$/", confValue)) {
                if (-1 > Integer.parseInt(confValue)) {
                    log__.error("ショートメール設定ファイル最大文字数の値が不正");
                    return -101;
                }
            } else {
                log__.error("ショートメール設定ファイル最大文字数の値が不正");
                return -101;
            }
            return Integer.parseInt(confValue);
        }
        return defValue;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl,
                                                GSConstSmail.PLUGIN_ID_SMAIL,
                                                dirId);
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     */
    public void deleteTempDir(RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl,
                                    GSConstSmail.PLUGIN_ID_SMAIL,
                                    dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void createTempDir(RequestModel reqMdl, String dirId)
    throws IOToolsException {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.createTempDir(reqMdl,
                                GSConstSmail.PLUGIN_ID_SMAIL,
                                dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリの初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl, String dirId)
    throws IOToolsException {
        //テンポラリディレクトリの削除後、再作成
        deleteTempDir(reqMdl, dirId);
        createTempDir(reqMdl, dirId);
    }

}
