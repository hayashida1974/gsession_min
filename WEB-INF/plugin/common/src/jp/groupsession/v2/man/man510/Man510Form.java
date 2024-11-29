package jp.groupsession.v2.man.man510;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.mail.Sender;
import jp.co.sjts.util.struts.AbstractForm;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.biz.SubnetBiz;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;
/**
 *
 * <br>[機  能] ワンタイムパスワード設定画面 フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man510Form extends AbstractForm implements ISelectorUseForm {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man510Form.class);

    /**画面操作時スクロール位置保存*/
    private int scrollY__ = 0;
    //input
    /** ワンタイムパスワード使用設定 */
    private int man510useOtp__;
    /** ワンタイムパスワード使用設定 対象 */
    private int man510otpUser__;
    /** ワンタイムパスワード使用設定 対象区分 */
    private int man510otpUserKbn__;

    /** ワンタイムパスワード使用設定 対象ユーザ グループ */
    private String man510targetUserGroup__ = null;
    /** ワンタイムパスワード使用設定 対象ユーザ */
    private String[] man510targetUser__ = null;
    /** ワンタイムパスワード使用設定 対象ユーザ UI */
    private UserGroupSelector man510targetUserUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("main.man510.9", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "man510targetUser")
                    )
                .chainGroupSelectionParamName("man510targetUserGroup")
                .build();
    /** ワンタイムパスワード使用設定 対象ユーザ */
    private UserGroupSelectModel man510targetUserList__ = new UserGroupSelectModel();

    /** ワンタイムパスワード使用設定 条件 */
    private int man510otpCond__;
    /** ワンタイムパスワード使用設定 条件IP */
    private String man510otpIpArea__;
    /** ワンタイムパスワード使用設定 条件IP 表示用*/
    private String man510otpIpDsp__;

    /** トークン認証 使用*/
    private int man510useToken__;
    /** トークン 使用IP*/
    private String man510tokenIpArea__;
    /** トークン 有効期限*/
    private int man510tokenLimit__;
    /** トークン 自動削除フラグ*/
    private int man510tokenAutoDel__;
    /** ベーシック認証 使用*/
    private int man510useBasic__;
    /** ベーシック 使用IP*/
    private String man510basicIpArea__;

    /** ワンタイムパスワード通知アドレス*/
    private String man510sendToAddress__;

    /** 認証情報 */
    private int man510authType__;

    /** SMTPサーバ URL */
    private String man510SmtpUrl__;
    /** SMTPサーバ ポート */
    private String man510SmtpPort__;
    /** SMTPサーバ 暗号化 */
    private int man510SmtpEncrypt__;
    /** SMTPサーバ 送信元アドレス */
    private String man510FromAddress__;
    /** SMTPサーバ ユーザID */
    private String man510SmtpUser__;
    /** SMTPサーバ パスワード */
    private String man510SmtpPass__;

    /** SMTPサーバOauth プロバイダ */
    private int man510provider__;
    /** SMTPサーバOauth 認証状態 */
    private boolean man510oauthCompFlg__;

    /** OAuth認証トークンSID */
    private int man510tokenSid__;

    //表示用
    /** トークン 有効期限 選択値*/
    private String man510tokenLimitDsp__;
    /** トークン 有効期限 コンボリスト*/
    private List<LabelValueBean> man510tokenLimitOption__;
    /** トークン 使用IP*/
    private String man510tokenIpAreaDsp__;
    /** ベーシック 使用IP*/
    private String man510basicIpAreaDsp__;
    /** プロバイダ 表示用 */
    private int man510SendProvider__ = 0;
    /** 暗号化プロトコル 一覧 */
    private List<LabelValueBean> man510AngoProtocolCombo__ = null;
    /** プロバイダ一覧 */
    private List<LabelValueBean> man510providerList__ = null;


    /**
     *
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @return エラー
     * @throws Exception SMTPサーバチェック実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
            Connection con, String appRootPath) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        //       ワンタイムパスワード使用   範囲外チェック
        if (man510useOtp__ != GSConstMain.OTP_USE
                && man510useOtp__ != GSConstMain.OTP_NOUSE) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("main.man510.17")),
                    "man510useOtp");
        }
        //       ワンタイムパスワード 使用しない場合はその他の設定の入力チェックは行わない
        if (man510useOtp__ != GSConstMain.OTP_USE) {
            return errors;
        }
        //       ワンタイムパスワード 対象   範囲外チェック
        //     ワンタイムパスワード 対象指定方法   範囲外チェック
        if ((man510otpUser__ != GSConstMain.OTP_TARGET_ALL
                && man510otpUser__ != GSConstMain.OTP_TARGET_SELECT)
                || (man510otpUserKbn__ != GSConstMain.OTP_USRTYPE_USE
                && man510otpUserKbn__ != GSConstMain.OTP_USRTYPE_NOUSE)
                ) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("main.man510.18")
                            + " " + gsMsg.getMessage("cmn.target")),
                    "man510useOtp");
        }
        //       ワンタイムパスワード 対象ユーザ
        //     ワンタイムパスワードの使用制限 制限する かつ ユーザ選択が空欄の場合エラー
        if (man510otpUser__ == GSConstMain.OTP_TARGET_SELECT) {
            if (man510targetUser__ == null || man510targetUser__.length <= 0) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.notvalidate.data",
                                gsMsg.getMessage("main.man510.18")
                                + " " + gsMsg.getMessage("cmn.target")),
                        "man510otpUser");
            }
        }

        //     ワンタイムパスワード 指定IP
        if (man510otpCond__ != GSConstMain.OTP_IPCOND_ALL
                && man510otpCond__ != GSConstMain.OTP_IPCOND_OUTIP) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("main.man510.18")
                            + " " + gsMsg.getMessage("cmn.conditions")),
                    "man510otpCond");
        }
        GSValidateCommon.validateTextField(errors,
                man510otpIpArea__,
                "man510otpIpArea",
                gsMsg.getMessage("main.man510.18")
                + " " + gsMsg.getMessage("cmn.conditions"),
                0,
                (man510otpCond__ == GSConstMain.OTP_IPCOND_OUTIP));

        //  ワンタイムパスワード 指定IP(不正チェック)
        if (!StringUtil.isNullZeroString(man510otpIpArea__)) {
            SubnetBiz ipBiz = new SubnetBiz();
            errors = ipBiz.validateSubnet(errors, reqMdl, man510otpIpArea__,
                                        "man510otpIpArea",
                                        gsMsg.getMessage("main.man510.18")
                                        + " " + gsMsg.getMessage("cmn.conditions"));
        }

        int errCnt = errors.size();
        //SMTPサーバ 認証
        if (man510authType__ != GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL
                && man510authType__ != GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("wml.307")
                            + " " + gsMsg.getMessage("wml.307")),
                    "man510authType");
        }
        if (man510authType__ == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
            //SMTPサーバ設定.サーバURL   共通文字入力処理へのチェック
            GSValidateCommon.validateTextField(errors,
                    man510SmtpUrl__,
                    "man510SmtpUrl",
                    gsMsg.getMessage("sml.sml110.06")
                    + " " + gsMsg.getMessage("sml.sml110.07"),
                    200, true);
            //SMTPサーバ設定.ポート番号     共通数字入力処理へのチェック
            GSValidateCommon.validateNumberInt(errors,
                    man510SmtpPort__,
                    gsMsg.getMessage("sml.sml110.06")
                    + " " + gsMsg.getMessage("sml.sml110.08"),
                    5);
            //SMTPサーバ暗号化入力チェック
            MailEncryptBiz protocolBiz = new MailEncryptBiz();
            if (!protocolBiz.isExistProtocol(man510SmtpEncrypt__)) {
                ActionMessage msg
                =  new ActionMessage("error.input.format.file",
                        gsMsg.getMessage("sml.sml110.06"),
                        gsMsg.getMessage("cmn.ango"));
                String eprefix = "notSmtpServerEncrypt";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        }
        //SMTPサーバ設定.fromアドレス   共通文字入力処理へのチェック、メールアドレスRFC規約チェック
        GSValidateCommon.validateMailAddressField(errors,
                man510FromAddress__, "man510FromAddress",
                gsMsg.getMessage("sml.sml110.06")
                + " " + gsMsg.getMessage("sml.sml110.17"), true);

        if (man510authType__ == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
            //SMTPサーバ設定.認証ユーザID    共通文字入力処理へのチェック
            GSValidateCommon.validateTextField(errors,
                    man510SmtpUser__,
                    "man510SmtpUser",
                    gsMsg.getMessage("sml.sml110.06")
                    + " " + gsMsg.getMessage("sml.sml110.22"),
                    100, false);
            //SMTPサーバ設定.認証パスワード   共通文字入力処理へのチェック
            GSValidateCommon.validateTextField(errors,
                    man510SmtpPass__,
                    "man510SmtpPass",
                    gsMsg.getMessage("sml.sml110.06")
                    + " " + gsMsg.getMessage("sml.sml110.21"),
                    140, false);
        }
        if (man510authType__ == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
            //プロバイダ
            CmnOauthDao coDao = new CmnOauthDao(con);
            if (coDao.select(man510provider__) == null) {
                StrutsUtil.addMessage(errors,
                    new ActionMessage("search.data.notfound",
                            gsMsg.getMessage("wml.308")),
                    "man510provider");
            }
        }

        //SMTP接続確認
        if (errCnt == errors.size()) {
            if (!checkSendConnect(con, appRootPath)) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.connect.failed.mailserver",
                                gsMsg.getMessage("sml.sml110.06"),
                                gsMsg.getMessage("sml.sml110.06")),
                        "send.error.connect.failed.mailserver");

            }
        }
        //トークン認証使用   範囲外チェック
        if (man510useToken__ != GSConstApi.USEKBN_AUTH_USE
                && man510useToken__ != GSConstApi.USEKBN_AUTH_NOUSE
                && man510useToken__ != GSConstApi.USEKBN_AUTH_USEIP) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("api.api020.3")),
                    "man510useToken");
        }
        //   トークン認証使用   ワンタイムパスワードによる選択不可
        if (man510useToken__ == GSConstApi.USEKBN_AUTH_NOUSE) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("api.api020.3")),
                    "man510useToken");
        }
        //    トークン認証使用   IP設定
        GSValidateCommon.validateTextField(errors,
                man510tokenIpArea__,
                "man510tokenIpArea",
                gsMsg.getMessage("api.api020.3") + gsMsg.getMessage("api.api020.16"),
                0,
                (man510useToken__ == GSConstApi.USEKBN_AUTH_USEIP));

        //    トークン認証使用   IP設定(不正チェック)
        if (!StringUtil.isNullZeroString(man510tokenIpArea__)) {
            SubnetBiz ipBiz = new SubnetBiz();
            errors = ipBiz.validateSubnet(errors, reqMdl, man510tokenIpArea__,
                                        "man510tokenIpArea",
                                        gsMsg.getMessage("api.api020.3")
                                        + gsMsg.getMessage("api.api020.16"));
        }

        //   トークン認証有効期限   範囲外チェック
        if (man510tokenLimit__ < GSConstApi.TOKEN_LIMIT_30M
                || man510tokenLimit__ > GSConstApi.TOKEN_LIMIT_FREE) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("api.api020.11")),
                    "man510tokenLimit");
        }

        //   ベーシック認証使用   範囲外チェック
        if (man510useBasic__ != GSConstApi.USEKBN_AUTH_USE
                && man510useBasic__ != GSConstApi.USEKBN_AUTH_NOUSE
                && man510useBasic__ != GSConstApi.USEKBN_AUTH_USEIP) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("api.api020.12")),
                    "man510useBasic");
        }
        //  ベーシック認証使用   ワンタイムパスワードによる選択不可
        if (man510useBasic__ == GSConstApi.USEKBN_AUTH_USE) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("api.api020.12")),
                    "man510useBasic");
        }
        //  ベーシック認証使用   IP設定
        GSValidateCommon.validateTextField(errors,
                man510basicIpArea__,
                "man510basicIpArea",
                gsMsg.getMessage("api.api020.12") + gsMsg.getMessage("api.api020.16"),
                0,
                (man510useBasic__ == GSConstApi.USEKBN_AUTH_USEIP));

        //  ベーシック認証使用   IP設定(不正チェック)
        if (!StringUtil.isNullZeroString(man510basicIpArea__)) {
            SubnetBiz ipBiz = new SubnetBiz();
            errors = ipBiz.validateSubnet(errors, reqMdl, man510basicIpArea__,
                                        "man510basicIpArea",
                                        gsMsg.getMessage("api.api020.12")
                                        + gsMsg.getMessage("api.api020.16"));
        }

        GSValidateUser gsValidateUser = new GSValidateUser(reqMdl);

        //ワンタイムパスワード通知先アドレス
        gsValidateUser.validateMail(errors, man510sendToAddress__,
                gsMsg.getMessage("user.157"), "man510sendToAddress.", true);


        return errors;
    }
    /**
     * <br>[機  能] メール送信サーバの接続情報チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @return true: 正常 false:不正
     * @throws Exception 受信サーバまたは送信サーバの接続close時に例外発生
     */
    public boolean checkSendConnect(Connection con, String appRootPath) throws Exception {

        boolean result = false;
        Sender sender = null;

        try {
            log__.info("メールサーバーに接続開始");
            //メールサーバーに接続
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.connectiontimeout", "30000");
            prop.setProperty("mail.smtp.timeout", "30000");
            if (man510authType__ == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
                //SMTPサーバー
                String smtpServer = getMan510SmtpUrl();
                log__.info("mail.smtp.server ==>" + smtpServer);
                //ポート番号
                int portNumber = Integer.parseInt(getMan510SmtpPort());
                log__.info("portNumber = " + portNumber);
                //送信元メールアドレス
                String fromMail = getMan510FromAddress();
                log__.info("mail.from ==>" + fromMail);

                //認証ユーザID取得
                String userId = getMan510SmtpUser();
                log__.info("smtp.userID ==>" + userId);
                //パスフレーズ取得
                String pass = getMan510SmtpPass();
                log__.info("smtp.passphrase ==>" + pass);

                boolean smtp = true;


                if (getMan510SmtpEncrypt() == MailEncryptBiz.ANGO_SSL_TLS) {
                    //SSL通信の場合
                    prop.setProperty("mail.smtp.socketFactory.class",
                            "javax.net.ssl.SSLSocketFactory");
                    prop.setProperty("mail.smtp.socketFactory.fallback", "false");
                    prop.setProperty("mail.smtp.socketFactory.port", String.valueOf(portNumber));
                } else if (getMan510SmtpEncrypt() == MailEncryptBiz.ANGO_STARTTLS) {
                    // STARTTLSの場合
                    prop.setProperty("mail.smtp.starttls.enable", "true");
                }
                sender = new Sender(prop, smtp);
                sender.connect(smtpServer, portNumber, userId, pass);
            } else if (man510authType__ == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
                prop.setProperty("mail.smtp.starttls.enable", "true");
                prop.setProperty("mail.smtp.starttls.required", "true");
                prop.setProperty("mail.smtp.sasl.enable", "false");

                OAuthBiz authBiz = new OAuthBiz();
                int cotSid = authBiz.checkOAuthToken(con,
                        getMan510provider(), getMan510FromAddress(), true);

                OauthMailServerModel serverMdl =
                        authBiz.getSendServerData(con, cotSid, appRootPath);
                sender = new Sender(prop, false);
                sender.connectOAuth(serverMdl.getHost(), serverMdl.getPort(),
                                    serverMdl.getAccountId(),
                                    serverMdl.getAccessToken());
            }
            result = true;
            log__.info("メールサーバーに接続完了");
        } catch (MessagingException me) {
            log__.fatal("メールサーバへの接続に失敗しました。", me);
        } catch (Exception e) {
            throw e;
        } finally {
            if (sender != null) {
                sender.disConnect();
            }
        }
        return result;
    }

    /**
     * <p>man510useOtp を取得します。
     * @return man510useOtp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510useOtp__
     */
    public int getMan510useOtp() {
        return man510useOtp__;
    }
    /**
     * <p>man510useOtp をセットします。
     * @param man510useOtp man510useOtp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510useOtp__
     */
    public void setMan510useOtp(int man510useOtp) {
        man510useOtp__ = man510useOtp;
    }
    /**
     * <p>man510otpUser を取得します。
     * @return man510otpUser
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpUser__
     */
    public int getMan510otpUser() {
        return man510otpUser__;
    }
    /**
     * <p>man510otpUser をセットします。
     * @param man510otpUser man510otpUser
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpUser__
     */
    public void setMan510otpUser(int man510otpUser) {
        man510otpUser__ = man510otpUser;
    }
    /**
     * <p>man510otpUserKbn を取得します。
     * @return man510otpUserKbn
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpUserKbn__
     */
    public int getMan510otpUserKbn() {
        return man510otpUserKbn__;
    }
    /**
     * <p>man510otpUserKbn をセットします。
     * @param man510otpUserKbn man510otpUserKbn
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpUserKbn__
     */
    public void setMan510otpUserKbn(int man510otpUserKbn) {
        man510otpUserKbn__ = man510otpUserKbn;
    }
    /**
     * <p>man510targetUserGroup を取得します。
     * @return man510targetUserGroup
     */
    public String getMan510targetUserGroup() {
        return man510targetUserGroup__;
    }
    /**
     * <p>man510targetUserGroup をセットします。
     * @param man510targetUserGroup man510targetUserGroup
     */
    public void setMan510targetUserGroup(String man510targetUserGroup) {
        man510targetUserGroup__ = man510targetUserGroup;
    }
    /**
     * <p>man510targetUser を取得します。
     * @return man510targetUser
     */
    public String[] getMan510targetUser() {
        return man510targetUser__;
    }
    /**
     * <p>man510targetUser をセットします。
     * @param man510targetUser man510targetUser
     */
    public void setMan510targetUser(String[] man510targetUser) {
        man510targetUser__ = man510targetUser;
    }
    /**
     * <p>man510targetUserUI を取得します。
     * @return man510targetUserUI
     */
    public UserGroupSelector getMan510targetUserUI() {
        return man510targetUserUI__;
    }
    /**
     * <p>man510targetUserUI をセットします。
     * @param man510targetUserUI man510targetUserUI
     */
    public void setMan510targetUserUI(UserGroupSelector man510targetUserUI) {
        man510targetUserUI__ = man510targetUserUI;
    }
    /**
     * <p>man510targetUserList を取得します。
     * @return man510targetUserList
     */
    public UserGroupSelectModel getMan510targetUserList() {
        return man510targetUserList__;
    }
    /**
     * <p>man510targetUserList をセットします。
     * @param man510targetUserList man510targetUserList
     */
    public void setMan510targetUserList(UserGroupSelectModel man510targetUserList) {
        man510targetUserList__ = man510targetUserList;
    }
    /**
     * <p>man510otpCond を取得します。
     * @return man510otpCond
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpCond__
     */
    public int getMan510otpCond() {
        return man510otpCond__;
    }
    /**
     * <p>man510otpCond をセットします。
     * @param man510otpCond man510otpCond
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpCond__
     */
    public void setMan510otpCond(int man510otpCond) {
        man510otpCond__ = man510otpCond;
    }
    /**
     * <p>man510otpIpArea を取得します。
     * @return man510otpIpArea
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpIpArea__
     */
    public String getMan510otpIpArea() {
        return man510otpIpArea__;
    }
    /**
     * <p>man510otpIpArea をセットします。
     * @param man510otpIpArea man510otpIpArea
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpIpArea__
     */
    public void setMan510otpIpArea(String man510otpIpArea) {
        man510otpIpArea__ = man510otpIpArea;
    }
    /**
     * <p>man510SmtpUrl を取得します。
     * @return man510SmtpUrl
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpUrl__
     */
    public String getMan510SmtpUrl() {
        return man510SmtpUrl__;
    }
    /**
     * <p>man510SmtpUrl をセットします。
     * @param man510SmtpUrl man510SmtpUrl
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpUrl__
     */
    public void setMan510SmtpUrl(String man510SmtpUrl) {
        man510SmtpUrl__ = man510SmtpUrl;
    }
    /**
     * <p>man510SmtpPort を取得します。
     * @return man510SmtpPort
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpPort__
     */
    public String getMan510SmtpPort() {
        return man510SmtpPort__;
    }
    /**
     * <p>man510SmtpPort をセットします。
     * @param man510SmtpPort man510SmtpPort
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpPort__
     */
    public void setMan510SmtpPort(String man510SmtpPort) {
        man510SmtpPort__ = man510SmtpPort;
    }

    /**
     * <p>man510FromAddress を取得します。
     * @return man510FromAddress
     * @see jp.groupsession.v2.man.man510.Man510Form#man510FromAddress__
     */
    public String getMan510FromAddress() {
        return man510FromAddress__;
    }
    /**
     * <p>man510FromAddress をセットします。
     * @param man510FromAddress man510FromAddress
     * @see jp.groupsession.v2.man.man510.Man510Form#man510FromAddress__
     */
    public void setMan510FromAddress(String man510FromAddress) {
        man510FromAddress__ = man510FromAddress;
    }
    /**
     * <p>man510SmtpUser を取得します。
     * @return man510SmtpUser
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpUser__
     */
    public String getMan510SmtpUser() {
        return man510SmtpUser__;
    }
    /**
     * <p>man510SmtpUser をセットします。
     * @param man510SmtpUser man510SmtpUser
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpUser__
     */
    public void setMan510SmtpUser(String man510SmtpUser) {
        man510SmtpUser__ = man510SmtpUser;
    }
    /**
     * <p>man510SmtpPass を取得します。
     * @return man510SmtpPass
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpPass__
     */
    public String getMan510SmtpPass() {
        return man510SmtpPass__;
    }
    /**
     * <p>man510SmtpPass をセットします。
     * @param man510SmtpPass man510SmtpPass
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpPass__
     */
    public void setMan510SmtpPass(String man510SmtpPass) {
        man510SmtpPass__ = man510SmtpPass;
    }
    /**
     * <p>man510provider を取得します。
     * @return man510provider
     * @see jp.groupsession.v2.man.man510.Man510Form#man510provider__
     */
    public int getMan510provider() {
        return man510provider__;
    }
    /**
     * <p>man510provider をセットします。
     * @param man510provider man510provider
     * @see jp.groupsession.v2.man.man510.Man510Form#man510provider__
     */
    public void setMan510provider(int man510provider) {
        man510provider__ = man510provider;
    }
    /**
     * <p>man510oauthCompFlg を取得します。
     * @return man510oauthCompFlg
     * @see jp.groupsession.v2.man.man510.Man510Form#man510oauthCompFlg__
     */
    public boolean isMan510oauthCompFlg() {
        return man510oauthCompFlg__;
    }
    /**
     * <p>man510oauthCompFlg をセットします。
     * @param man510oauthCompFlg man510oauthCompFlg
     * @see jp.groupsession.v2.man.man510.Man510Form#man510oauthCompFlg__
     */
    public void setMan510oauthCompFlg(boolean man510oauthCompFlg) {
        man510oauthCompFlg__ = man510oauthCompFlg;
    }
    /**
     * <p>man510tokenSid を取得します。
     * @return man510tokenSid
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenSid__
     */
    public int getMan510tokenSid() {
        return man510tokenSid__;
    }
    /**
     * <p>man510tokenSid をセットします。
     * @param man510tokenSid man510tokenSid
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenSid__
     */
    public void setMan510tokenSid(int man510tokenSid) {
        man510tokenSid__ = man510tokenSid;
    }
    /**
     * <p>man510useToken を取得します。
     * @return man510useToken
     * @see jp.groupsession.v2.man.man510.Man510Form#man510useToken__
     */
    public int getMan510useToken() {
        return man510useToken__;
    }
    /**
     * <p>man510useToken をセットします。
     * @param man510useToken man510useToken
     * @see jp.groupsession.v2.man.man510.Man510Form#man510useToken__
     */
    public void setMan510useToken(int man510useToken) {
        man510useToken__ = man510useToken;
    }
    /**
     * <p>man510tokenIpArea を取得します。
     * @return man510tokenIpArea
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenIpArea__
     */
    public String getMan510tokenIpArea() {
        return man510tokenIpArea__;
    }
    /**
     * <p>man510tokenIpArea をセットします。
     * @param man510tokenIpArea man510tokenIpArea
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenIpArea__
     */
    public void setMan510tokenIpArea(String man510tokenIpArea) {
        man510tokenIpArea__ = man510tokenIpArea;
    }
    /**
     * <p>man510tokenLimit を取得します。
     * @return man510tokenLimit
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenLimit__
     */
    public int getMan510tokenLimit() {
        return man510tokenLimit__;
    }
    /**
     * <p>man510tokenLimit をセットします。
     * @param man510tokenLimit man510tokenLimit
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenLimit__
     */
    public void setMan510tokenLimit(int man510tokenLimit) {
        man510tokenLimit__ = man510tokenLimit;
    }
    /**
     * <p>man510tokenAutoDel を取得します。
     * @return man510tokenAutoDel
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenAutoDel__
     */
    public int getMan510tokenAutoDel() {
        return man510tokenAutoDel__;
    }
    /**
     * <p>man510tokenAutoDel をセットします。
     * @param man510tokenAutoDel man510tokenAutoDel
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenAutoDel__
     */
    public void setMan510tokenAutoDel(int man510tokenAutoDel) {
        man510tokenAutoDel__ = man510tokenAutoDel;
    }
    /**
     * <p>man510useBasic を取得します。
     * @return man510useBasic
     * @see jp.groupsession.v2.man.man510.Man510Form#man510useBasic__
     */
    public int getMan510useBasic() {
        return man510useBasic__;
    }
    /**
     * <p>man510useBasic をセットします。
     * @param man510useBasic man510useBasic
     * @see jp.groupsession.v2.man.man510.Man510Form#man510useBasic__
     */
    public void setMan510useBasic(int man510useBasic) {
        man510useBasic__ = man510useBasic;
    }
    /**
     * <p>man510basicIpArea を取得します。
     * @return man510basicIpArea
     * @see jp.groupsession.v2.man.man510.Man510Form#man510basicIpArea__
     */
    public String getMan510basicIpArea() {
        return man510basicIpArea__;
    }
    /**
     * <p>man510basicIpArea をセットします。
     * @param man510basicIpArea man510basicIpArea
     * @see jp.groupsession.v2.man.man510.Man510Form#man510basicIpArea__
     */
    public void setMan510basicIpArea(String man510basicIpArea) {
        man510basicIpArea__ = man510basicIpArea;
    }
    /**
     * <p>man510sendToAddress を取得します。
     * @return man510sendToAddress
     * @see jp.groupsession.v2.man.man510.Man510Form#man510sendToAddress__
     */
    public String getMan510sendToAddress() {
        return man510sendToAddress__;
    }
    /**
     * <p>man510sendToAddress をセットします。
     * @param man510sendToAddress man510sendToAddress
     * @see jp.groupsession.v2.man.man510.Man510Form#man510sendToAddress__
     */
    public void setMan510sendToAddress(String man510sendToAddress) {
        man510sendToAddress__ = man510sendToAddress;
    }
    /**
     * <p>man510authType を取得します。
     * @return man510authType
     * @see jp.groupsession.v2.man.man510.Man510Form#man510authType__
     */
    public int getMan510authType() {
        return man510authType__;
    }
    /**
     * <p>man510authType をセットします。
     * @param man510authType man510authType
     * @see jp.groupsession.v2.man.man510.Man510Form#man510authType__
     */
    public void setMan510authType(int man510authType) {
        man510authType__ = man510authType;
    }
    /**
     * <p>man510tokenLimitDsp を取得します。
     * @return man510tokenLimitDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenLimitDsp__
     */
    public String getMan510tokenLimitDsp() {
        return man510tokenLimitDsp__;
    }
    /**
     * <p>man510tokenLimitDsp をセットします。
     * @param man510tokenLimitDsp man510tokenLimitDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenLimitDsp__
     */
    public void setMan510tokenLimitDsp(String man510tokenLimitDsp) {
        man510tokenLimitDsp__ = man510tokenLimitDsp;
    }
    /**
     * <p>man510tokenLimitOption を取得します。
     * @return man510tokenLimitOption
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenLimitOption__
     */
    public List<LabelValueBean> getMan510tokenLimitOption() {
        return man510tokenLimitOption__;
    }
    /**
     * <p>man510tokenLimitOption をセットします。
     * @param man510tokenLimitOption man510tokenLimitOption
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenLimitOption__
     */
    public void setMan510tokenLimitOption(
            List<LabelValueBean> man510tokenLimitOption) {
        man510tokenLimitOption__ = man510tokenLimitOption;
    }
    /**
     * <p>man510tokenIpAreaDsp を取得します。
     * @return man510tokenIpAreaDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenIpAreaDsp__
     */
    public String getMan510tokenIpAreaDsp() {
        return man510tokenIpAreaDsp__;
    }
    /**
     * <p>man510tokenIpAreaDsp をセットします。
     * @param man510tokenIpAreaDsp man510tokenIpAreaDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510tokenIpAreaDsp__
     */
    public void setMan510tokenIpAreaDsp(String man510tokenIpAreaDsp) {
        man510tokenIpAreaDsp__ = man510tokenIpAreaDsp;
    }
    /**
     * <p>man510basicIpAreaDsp を取得します。
     * @return man510basicIpAreaDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510basicIpAreaDsp__
     */
    public String getMan510basicIpAreaDsp() {
        return man510basicIpAreaDsp__;
    }
    /**
     * <p>man510basicIpAreaDsp をセットします。
     * @param man510basicIpAreaDsp man510basicIpAreaDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510basicIpAreaDsp__
     */
    public void setMan510basicIpAreaDsp(String man510basicIpAreaDsp) {
        man510basicIpAreaDsp__ = man510basicIpAreaDsp;
    }
    /**
     * <p>man510SendProvider を取得します。
     * @return man510SendProvider
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SendProvider__
     */
    public int getMan510SendProvider() {
        return man510SendProvider__;
    }
    /**
     * <p>man510SendProvider をセットします。
     * @param man510SendProvider man510SendProvider
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SendProvider__
     */
    public void setMan510SendProvider(int man510SendProvider) {
        man510SendProvider__ = man510SendProvider;
    }
    /**
     * <p>man510otpIpDsp を取得します。
     * @return man510otpIpDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpIpDsp__
     */
    public String getMan510otpIpDsp() {
        return man510otpIpDsp__;
    }
    /**
     * <p>man510otpIpDsp をセットします。
     * @param man510otpIpDsp man510otpIpDsp
     * @see jp.groupsession.v2.man.man510.Man510Form#man510otpIpDsp__
     */
    public void setMan510otpIpDsp(String man510otpIpDsp) {
        man510otpIpDsp__ = man510otpIpDsp;
    }
    /**
     * <p>scrollY を取得します。
     * @return scrollY
     * @see jp.groupsession.v2.man.man510.Man510Form#scrollY__
     */
    public int getScrollY() {
        return scrollY__;
    }
    /**
     * <p>scrollY をセットします。
     * @param scrollY scrollY
     * @see jp.groupsession.v2.man.man510.Man510Form#scrollY__
     */
    public void setScrollY(int scrollY) {
        scrollY__ = scrollY;
    }
    /**
     * <p>man510SmtpEncrypt を取得します。
     * @return man510SmtpEncrypt
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpEncrypt__
     */
    public int getMan510SmtpEncrypt() {
        return man510SmtpEncrypt__;
    }
    /**
     * <p>man510SmtpEncrypt をセットします。
     * @param man510SmtpEncrypt man510SmtpEncrypt
     * @see jp.groupsession.v2.man.man510.Man510Form#man510SmtpEncrypt__
     */
    public void setMan510SmtpEncrypt(int man510SmtpEncrypt) {
        man510SmtpEncrypt__ = man510SmtpEncrypt;
    }
    /**
     * <p>man510AngoProtocolCombo を取得します。
     * @return man510AngoProtocolCombo
     * @see jp.groupsession.v2.man.man510.Man510Form#man510AngoProtocolCombo__
     */
    public List<LabelValueBean> getMan510AngoProtocolCombo() {
        return man510AngoProtocolCombo__;
    }
    /**
     * <p>man510AngoProtocolCombo をセットします。
     * @param man510AngoProtocolCombo man510AngoProtocolCombo
     * @see jp.groupsession.v2.man.man510.Man510Form#man510AngoProtocolCombo__
     */
    public void setMan510AngoProtocolCombo(
            List<LabelValueBean> man510AngoProtocolCombo) {
        man510AngoProtocolCombo__ = man510AngoProtocolCombo;
    }
    /**
     * <p>man510providerList を取得します。
     * @return man510providerList
     * @see jp.groupsession.v2.man.man510.Man510Form#man510providerList__
     */
    public List<LabelValueBean> getMan510providerList() {
        return man510providerList__;
    }
    /**
     * <p>man510providerList をセットします。
     * @param man510providerList man510providerList
     * @see jp.groupsession.v2.man.man510.Man510Form#man510providerList__
     */
    public void setMan510providerList(List<LabelValueBean> man510providerList) {
        man510providerList__ = man510providerList;
    }
}