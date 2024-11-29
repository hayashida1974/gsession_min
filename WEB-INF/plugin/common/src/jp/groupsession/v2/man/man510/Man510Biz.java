package jp.groupsession.v2.man.man510;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.lang.ClassUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpUserDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpUserModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.IApiConfModel;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSPassword;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] ワンタイムパスワード設定画面 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man510Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man510Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** コネクション */
    public Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Man510Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }

    /** 画面ID */
    public static final String SCR_ID = "man510";

    /**
     *
     * <br>[機  能] 初期化処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @throws Exception 実行時例外
     */
    public void doInit(Man510ParamModel param) throws Exception {
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        //DB状態読み込み
        CmnOtpConfModel otpConf = otpBiz.getOtpConf(con__);
        param.setMan510useOtp(otpConf.getCocUseOtp());
        param.setMan510otpUser(otpConf.getCocOtpUser());
        param.setMan510otpUserKbn(otpConf.getCocOtpUserType());
        param.setMan510otpCond(otpConf.getCocOtpIpcond());
        param.setMan510otpIpArea(otpConf.getCocOtpIp());
        param.setMan510SmtpUrl(otpConf.getCocSmtpUrl());
        param.setMan510SmtpPort(otpConf.getCocSmtpPort());
        param.setMan510SmtpEncrypt(otpConf.getCocSmtpSsluse());
        param.setMan510FromAddress(otpConf.getCocSmtpFrom());
        param.setMan510SmtpUser(otpConf.getCocSmtpUser());
        if (!StringUtil.isNullZeroString(otpConf.getCocSmtpPass())) {
            param.setMan510SmtpPass(GSPassword.getDecryPassword(otpConf.getCocSmtpPass()));
        }
        param.setMan510authType(otpConf.getCocSmtpAuthType());
        param.setMan510tokenSid(otpConf.getCotSid());

        //OAuyh認証情報トークンの取得
        if (param.getMan510authType() == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
            OAuthBiz oauthBiz = new OAuthBiz();
            OauthDataModel authData = oauthBiz.getAuthData(con__, param.getMan510tokenSid());
            param.setMan510provider(authData.getCouSid());
        }

        //プロバイダコンボを設定
        _setProviderCombo(con__, param, reqMdl__);

        //承認済、未承認を設定
        OAuthBiz authBiz = new OAuthBiz();
        OauthDataModel authData = authBiz.getAuthData(con__, param.getMan510tokenSid());
        boolean oauthCompFlg = (authData != null && authData.getRefreshToken() != null);
        param.setMan510oauthCompFlg(oauthCompFlg);


        CmnOtpUserDao couDao = new CmnOtpUserDao(con__);
        List<CmnOtpUserModel> users =  couDao.select();
        if (!users.isEmpty()) {
            String[] selected = new String[users.size()];
            int idx = 0;
            for (CmnOtpUserModel user : users) {
                if (user.getUsrSid() >= 0) {
                    selected[idx] = String.valueOf(user.getUsrSid());
                } else {
                    selected[idx] = UserGroupSelectBiz.GROUP_PREFIX
                            + String.valueOf(user.getGrpSid());
                }
                idx++;
            }

            param.setMan510targetUser(selected);
        }

        //API基本設定
        IApiConfBiz confBiz = null;
        confBiz = (IApiConfBiz) ClassUtil.getDefConstractorObject(
                "jp.groupsession.v2.api.biz.ApiConfBiz");
        IApiConfModel conf = confBiz.getConf(con__);
        /* トークン認証 使用*/
        param.setMan510useToken(conf.getApcToakenUse());
        /* トークン 使用IP*/
        param.setMan510tokenIpArea(conf.getApcToakenIp());
        /* トークン 有効期限*/
        param.setMan510tokenLimit(conf.getApcToakenLife());
        param.setMan510tokenAutoDel(conf.getApcAutoDel());
        /* ベーシック認証 使用*/
        param.setMan510useBasic(conf.getApcBasicUse());
        /* ベーシック 使用IP*/
        param.setMan510basicIpArea(conf.getApcBasicIp());

        //Adminユーザワンタイムパスワード通知アドレス
        CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel usrInf = usrDao.select(GSConst.SYSTEM_USER_ADMIN);
        param.setMan510sendToAddress(usrInf.getUsiOtpsendAddress());

    }
    /**
     *
     * <br>[機  能] 描画設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @throws Exception 実行時例外
     */
    public void doDsp(Man510ParamModel param) throws Exception {

        //ワンタイムパスワード使用設定 条件 IP
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (!StringUtil.isNullZeroString(param.getMan510otpIpArea())) {
            param.setMan510otpIpDsp(StringUtilHtml.transToHTml(param.getMan510otpIpArea()));
        }

        /** トークン有効期限 コレクション*/
        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        String msgMin = gsMsg.getMessage("cmn.minute");
        String msgDay = gsMsg.getMessage("cmn.day");
        String msgWeek = gsMsg.getMessage("cmn.weeks");
        String msgFree = gsMsg.getMessage("main.man200.9");

        labelList.add(new LabelValueBean("30" + msgMin,
                String.valueOf(GSConstApi.TOKEN_LIMIT_30M)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"1"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_1H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"2"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_2H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"3"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_3H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"5"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_5H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"8"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_8H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"10"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_10H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"12"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_12H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"15"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_15H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"20"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_20H)));
        labelList.add(new LabelValueBean("1" + msgDay,
                String.valueOf(GSConstApi.TOKEN_LIMIT_1D)));
        labelList.add(new LabelValueBean("1" + msgWeek,
                String.valueOf(GSConstApi.TOKEN_LIMIT_1W)));
        labelList.add(new LabelValueBean("2" + msgWeek,
                String.valueOf(GSConstApi.TOKEN_LIMIT_2W)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.months", new String[] {"1"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_1MONTH)));
        labelList.add(new LabelValueBean(msgFree, String.valueOf(GSConstApi.TOKEN_LIMIT_FREE)));

        param.setMan510tokenLimitOption(labelList);
        //トークン有効期限 表示値
        for (LabelValueBean bean : labelList) {
            if (bean.getValue().equals(String.valueOf(param.getMan510tokenLimit()))) {
                param.setMan510tokenLimitDsp(bean.getLabel());
            }
        }


        //トークンIP指定 表示値
        param.setMan510tokenIpAreaDsp(
                StringUtilHtml.transToHTml(
                        param.getMan510tokenIpArea()));

        //ベーシックIP指定 表示値
        param.setMan510basicIpAreaDsp(
                StringUtilHtml.transToHTml(
                        param.getMan510basicIpArea()));

        //暗号化プロトコルコンボを設定
        MailEncryptBiz protcolBiz = new MailEncryptBiz(reqMdl__);
        param.setMan510AngoProtocolCombo(
                protcolBiz.setDspProtocolLabels());

        //プロバイダコンボを設定
        _setProviderCombo(con__, param, reqMdl__);

        //承認済、未承認を設定
        OAuthBiz authBiz = new OAuthBiz();
        int cotSid = authBiz.checkOAuthToken(con__,
                param.getMan510provider(), param.getMan510FromAddress(), true);
        OauthDataModel authData = authBiz.getAuthData(con__, cotSid);
        boolean oauthCompFlg = (authData != null && authData.getRefreshToken() != null);
        param.setMan510oauthCompFlg(oauthCompFlg);

    }
    /**
     *
     * <br>[機  能] 前提設定を行う
     * <br>[解  説] ワンタイムパスワード設定条件の設定
     * <br>[備  考]
     * @param param パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    public void doPrepareSetting(Man510ParamModel param) throws SQLException {

    }
    /**
     *
     * <br>[機  能] 登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @throws Exception 実行時例外
     */
    public void doUpdate(Man510ParamModel param) throws Exception {
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        CmnOtpConfDao otpConfDao = new CmnOtpConfDao(con__);

        //DB状態読み込み
        CmnOtpConfModel otpConf = otpBiz.getOtpConf(con__);
        otpConf.setCocUseOtp(param.getMan510useOtp());

        //      ワンタイムパスワード 使用しない場合は他の設定値をデフォルトに戻す
        if (param.getMan510useOtp() != GSConstMain.OTP_USE) {
            otpConf.setCocOtpUser(GSConstMain.OTP_TARGET_ALL);
            otpConf.setCocOtpUserType(GSConstMain.OTP_USRTYPE_USE);
            otpConf.setCocOtpIpcond(GSConstMain.OTP_IPCOND_ALL);
            otpConf.setCocOtpIp("");
            otpConf.setCocSmtpUrl("");
            otpConf.setCocSmtpPort("");
            otpConf.setCocSmtpSsluse(0);
            otpConf.setCocSmtpFrom("");
            otpConf.setCocSmtpUser("");
            otpConf.setCocSmtpPass("");
            otpConf.setCocSmtpAuthType(0);
            otpConf.setCotSid(0);
        } else {
            otpConf.setCocOtpUser(param.getMan510otpUser());
            otpConf.setCocOtpUserType(param.getMan510otpUserKbn());
            otpConf.setCocOtpIpcond(param.getMan510otpCond());
            //改行を統一
            String ipvalue = NullDefault.getString(param.getMan510otpIpArea(),
                    "").replaceAll("\\r\\n|\\r|\\n", "\r\n");
            otpConf.setCocOtpIp(ipvalue);

            if (param.getMan510authType() == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
                otpConf.setCocSmtpUrl(param.getMan510SmtpUrl());
                otpConf.setCocSmtpPort(param.getMan510SmtpPort());
                otpConf.setCocSmtpSsluse(param.getMan510SmtpEncrypt());
                otpConf.setCocSmtpUser(param.getMan510SmtpUser());
                otpConf.setCocSmtpPass(GSPassword.getEncryPassword(param.getMan510SmtpPass()));
                otpConf.setCotSid(0);

            } else if (param.getMan510authType() == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
                otpConf.setCocSmtpUrl("");
                otpConf.setCocSmtpPort("");
                otpConf.setCocSmtpSsluse(0);
                otpConf.setCocSmtpUser("");
                otpConf.setCocSmtpPass("");

                OAuthBiz authBiz = new OAuthBiz();
                int cotSid = authBiz.checkOAuthToken(con__,
                        param.getMan510provider(), param.getMan510FromAddress(), true);
                otpConf.setCotSid(cotSid);
            }
            otpConf.setCocSmtpFrom(param.getMan510FromAddress());
            otpConf.setCocSmtpAuthType(param.getMan510authType());
        }

        boolean commitFlg = false;
        try {
            //DB状態書き込み
            if (otpConfDao.update(otpConf) == 0) {
                otpConfDao.insert(otpConf);
            }
            CmnOtpUserDao couDao = new CmnOtpUserDao(con__);
            couDao.deleteAll();

            if (param.getMan510otpUser() != GSConstMain.OTP_TARGET_ALL) {
                String[] targetUser = param.getMan510targetUser();
                if (targetUser != null && targetUser.length > 0) {
                    for (String value : targetUser) {
                        CmnOtpUserModel model = new CmnOtpUserModel();
                        if (value.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                            model.setGrpSid(NullDefault.getInt(
                                    value.substring(UserGroupSelectBiz.GROUP_PREFIX.length()),
                                    -1));
                            model.setUsrSid(-1);
                        } else {
                            model.setGrpSid(-1);
                            model.setUsrSid(NullDefault.getInt(
                                    value,
                                    -1));
                        }
                        couDao.insert(model);
                    }
                }
            }

            //使用時のみAPI基本設定、管理者ユーザ用ワンタイムパスワード通知先アドレス変更
            if (param.getMan510useOtp() == GSConstMain.OTP_USE) {
                saveApiConf(param);
                saveAdminMailAddress(param);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ワンタイムパスワード設定の登録に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }
    /**
     *
     * <br>[機  能] API基本設定部を補完
     * <br>[解  説]
     * <br>[備  考]
     * @param param 画面パラメータ
     * @throws SQLException SQL実行時例外
     * @throws IllegalAccessException API関連クラス定義例外
     * @throws InstantiationException API関連クラス定義例外
     * @throws ClassNotFoundException API関連クラス定義例外
     */
    private void saveApiConf(Man510ParamModel param) throws SQLException,
    ClassNotFoundException,
    InstantiationException,
    IllegalAccessException  {
        //API基本設定
        IApiConfBiz confBiz = null;
        confBiz = (IApiConfBiz) ClassUtil.getDefConstractorObject(
                "jp.groupsession.v2.api.biz.ApiConfBiz");
        IApiConfModel conf = confBiz.getConf(con__);
        /* トークン認証 使用*/
        conf.setApcToakenUse(param.getMan510useToken());
        /* トークン 使用IP*/
        if (param.getMan510useToken() == GSConstApi.USEKBN_AUTH_USEIP) {
            //改行を統一
            String value = param.getMan510tokenIpArea()
                    .replaceAll("\\r\\n|\\r|\\n", "\r\n");
            conf.setApcToakenIp(value);
        } else {
            conf.setApcToakenIp("");
        }
        /* トークン 有効期限*/
        conf.setApcToakenLife(param.getMan510tokenLimit());
        /* ベーシック認証 使用*/
        conf.setApcBasicUse(param.getMan510useBasic());
        /* ベーシック 使用IP*/
        if (param.getMan510useBasic() == GSConstApi.USEKBN_AUTH_USEIP) {
            //改行を統一
            String value = param.getMan510basicIpArea()
                    .replaceAll("\\r\\n|\\r|\\n", "\r\n");
            conf.setApcBasicIp(value);
        } else {
            conf.setApcBasicIp("");
        }
        /* トークン 自動削除*/
        if (param.getMan510tokenLimit() == GSConstApi.TOKEN_LIMIT_FREE) {
            conf.setApcAutoDel(1);
        } else {
            conf.setApcAutoDel(0);
        }

        confBiz.saveConf(con__, conf);

    }
    /**
     *
     * <br>[機  能] 管理者ユーザ通知アドレス登録部
     * <br>[解  説]
     * <br>[備  考]
     * @param param 画面パラメータ
     * @throws SQLException SQL実行時例外
     */
    private void saveAdminMailAddress(Man510ParamModel param) throws SQLException {
        //既存情報 取得
        CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel usrInf = usrDao.select(GSConst.SYSTEM_USER_ADMIN);
        //更新
        usrInf.setUsiOtpsendAddress(param.getMan510sendToAddress());
        usrInf.setUsiEdate(new UDate());
        usrInf.setUsiEuid(reqMdl__.getSmodel().getUsrsid());
        usrDao.updateCmnUserInf(usrInf);
    }
    /**
     *
     * <br>[機  能] 登録内容からログメッセージを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @return ログメッセージ
     * @throws SQLException SQL実行時例外
     */
    public String makeLogMessage(Man510ParamModel param) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> saveList = new ArrayList<>();

        //        ワンタイムパスワード使用
        String value = "";
        if (param.getMan510useOtp() == GSConstMain.OTP_USE) {
            value = gsMsg.getMessage("main.man510.13");
        } else {
            value = gsMsg.getMessage("main.man510.14");
        }
        saveList.add(new LabelValueBean(
                gsMsg.getMessage("main.man510.17"),
                value
                ));

        if (param.getMan510useOtp() == GSConstMain.OTP_USE) {
            //対象
            StringBuilder sb = new StringBuilder();
            sb.append(gsMsg.getMessage("cmn.target"));
            sb.append(":");
            if (param.getMan510otpUser() == GSConstMain.OTP_TARGET_ALL) {
                sb.append(gsMsg.getMessage("cmn.alluser"));
            } else {
                sb.append(gsMsg.getMessage("cmn.named.user"));
            }
            sb.append(" ");
            if (param.getMan510otpUser() != GSConstMain.OTP_TARGET_ALL) {
                //選択区分
                if (param.getMan510otpUserKbn() == GSConstMain.OTP_USRTYPE_USE) {
                    sb.append(gsMsg.getMessage("main.man510.10"));
                } else {
                    sb.append(gsMsg.getMessage("main.man510.11"));
                }
                sb.append(System.lineSeparator());

                UserGroupSelectModel userSel = new UserGroupSelectModel();
                userSel.setSelected(UserGroupSelectModel.KEY_DEFAULT, param.getMan510targetUser());
                userSel.init(con__, reqMdl__, new String[] {UserGroupSelectModel.KEY_DEFAULT},
                        new String[] { "" },
                        String.valueOf(UserGroupSelectBiz.GROUP_GRPLIST),
                        null,
                        null);
                List<UsrLabelValueBean> selUsr = new ArrayList<>();
                selUsr = userSel.getSelectedList(UserGroupSelectModel.KEY_DEFAULT);
                for (UsrLabelValueBean user : selUsr) {
                    sb.append(user.getLabel());
                    sb.append(", ");
                }

            }
            sb.append(System.lineSeparator());

            //条件
            sb.append(gsMsg.getMessage("cmn.conditions"));
            sb.append(":");
            if (param.getMan510otpCond() == GSConstMain.OTP_IPCOND_ALL) {
                sb.append(gsMsg.getMessage("main.man510.15"));
            } else {
                sb.append(gsMsg.getMessage("main.man510.16"));
                sb.append(System.lineSeparator());
                sb.append(param.getMan510otpIpArea());
            }
            value = sb.toString();
            saveList.add(new LabelValueBean(
                    gsMsg.getMessage("main.man510.18"),
                    value
                    ));

            sb = new StringBuilder();

            if (param.getMan510authType() == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
                //SMTPサーバ設定.URL
                sb.append(gsMsg.getMessage("sml.sml110.07"));
                sb.append(":");
                sb.append(param.getMan510SmtpUrl());
                //SMTPサーバ設定.ポート番号
                sb.append(System.lineSeparator());
                sb.append(gsMsg.getMessage("sml.sml110.08"));
                sb.append(":");
                sb.append(param.getMan510SmtpPort());
                if (param.getMan510SmtpEncrypt() > 0) {
                    //SMTPサーバ設定.暗号化使用
                    sb.append(" ");
                    sb.append(gsMsg.getMessage("sml.sml110.06"));
                }
            }
            //SMTPサーバ設定.fromアドレス
            sb.append(System.lineSeparator());
            sb.append(gsMsg.getMessage("sml.sml110.17"));
            sb.append(":");
            sb.append(param.getMan510FromAddress());
            if (param.getMan510authType() == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
                //SMTPサーバ設定.認証ユーザID
                sb.append(System.lineSeparator());
                sb.append(gsMsg.getMessage("sml.sml110.22"));
                sb.append(":");
                sb.append(param.getMan510SmtpUser());

            } else if (param.getMan510authType() == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
                sb.append(System.lineSeparator());
                sb.append(gsMsg.getMessage("cmn.auth.provider"));
                sb.append(":");
                if (param.getMan510SendProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
                    sb.append(gsMsg.getMessage("cmn.cmn260.02"));

                } else if (param.getMan510SendProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
                    sb.append(gsMsg.getMessage("cmn.cmn260.03"));
                }
            }
            value = sb.toString();
            saveList.add(new LabelValueBean(
                    gsMsg.getMessage("sml.sml110.06"),
                    value));
        }
        StringBuilder builder = new StringBuilder();
        for (LabelValueBean label : saveList) {
            builder.append("[");
            builder.append(label.getLabel());
            builder.append("]");
            builder.append(" ");
            builder.append(label.getValue());
            builder.append(System.lineSeparator());
        }
        if (param.getMan510useOtp() == GSConstMain.OTP_USE) {

            builder.append("[");
            builder.append(gsMsg.getMessage("user.157"));
            builder.append("]");
            builder.append(" ");
            builder.append(param.getMan510sendToAddress());
            builder.append(System.lineSeparator());
            builder.append(gsMsg.getMessage("api.api020.3") + ":");
            if (param.getMan510useToken() == GSConstApi.USEKBN_AUTH_NOUSE) {
                builder.append(gsMsg.getMessage("api.api020.8"));
            }
            if (param.getMan510useToken() == GSConstApi.USEKBN_AUTH_USE) {
                builder.append(gsMsg.getMessage("api.api020.9"));
            }
            if (param.getMan510useToken() == GSConstApi.USEKBN_AUTH_USEIP) {
                builder.append(gsMsg.getMessage("api.api020.10"));
                builder.append("\r\n" + param.getMan510tokenIpArea());
            }
            builder.append(System.lineSeparator());
            builder.append(gsMsg.getMessage("api.api020.11"));
            builder.append(":");
            String[] tokenLimit = {
                    GSConstMain.OTP_TOKEN_LIMIT_30_MINUTE,
                    GSConstMain.OTP_TOKEN_LIMIT_1_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_2_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_3_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_5_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_8_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_10_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_12_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_15_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_20_HOUR,
                    GSConstMain.OTP_TOKEN_LIMIT_1_DAY,
                    GSConstMain.OTP_TOKEN_LIMIT_1_WEEK,
                    GSConstMain.OTP_TOKEN_LIMIT_2_WEEK,
                    GSConstMain.OTP_TOKEN_LIMIT_1_MONTH,
                    GSConstMain.OTP_TOKEN_LIMIT_FREE
            };
            builder.append(tokenLimit[param.getMan510tokenLimit()]);
            builder.append(System.lineSeparator());

            builder.append(gsMsg.getMessage("api.api020.12"));
            builder.append(":");
            if (param.getMan510useBasic() == GSConstApi.USEKBN_AUTH_NOUSE) {
                builder.append(gsMsg.getMessage("api.api020.8"));
            }
            if (param.getMan510useBasic() == GSConstApi.USEKBN_AUTH_USE) {
                builder.append(gsMsg.getMessage("api.api020.9"));
            }
            if (param.getMan510useBasic() == GSConstApi.USEKBN_AUTH_USEIP) {
                builder.append(gsMsg.getMessage("api.api020.10"));
                builder.append("\r\n" + param.getMan510basicIpArea());
            }
        }
        return builder.toString();
    }

    /**
     * <br>[機  能] プロバイダコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param param パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setProviderCombo(Connection con,
            Man510ParamModel param, RequestModel reqMdl)
                    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> providerCombo = new ArrayList<LabelValueBean>();
        providerCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        CmnOauthDao oauthDao = new CmnOauthDao(con);
        List<CmnOauthModel> authDataList =  oauthDao.select();
        for (CmnOauthModel authData : authDataList) {
            providerCombo.add(
                    new LabelValueBean(
                            OAuthBiz.getProviderName(authData.getCouProvider(), reqMdl),
                            String.valueOf(authData.getCouSid())
                            ));
        }
        param.setMan510providerList(providerCombo);
    }
}
