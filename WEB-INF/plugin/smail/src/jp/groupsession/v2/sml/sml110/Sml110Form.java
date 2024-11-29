package jp.groupsession.v2.sml.sml110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml100.Sml100Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ショートメール 管理者設定 転送設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml110Form extends Sml100Form {

    /** 転送設定 */
    private String sml110MailForward__ = null;
    /** SMTPサーバ */
    private String sml110SmtpUrl__ = null;
    /** SMTPサーバ認証ユーザ */
    private String sml110SmtpUser__ = null;
    /** SMTPサーバ認証パスワード */
    private String sml110SmtpPass__ = null;
    /** 転送メールfromアドレス */
    private String sml110FromAddress__ = null;
    /** SMTPポート */
    private String sml110SmtpPort__ = null;
    /** 転送先制限 区分 制限しない=0 制限する=1 */
    private String sml110FwLmtKbn__ = null;
    /** 転送先制限 テキストエリア */
    private String sml110FwlmtTextArea__ = null;
    /** 転送先制限  */
    private List<Sml110FwCheckModel> sml110FwCheckList__ = null;
    /** チェックボタン押下フラグ  */
    private boolean sml110CheckFlg__ = GSConstSmail.FW_CHECK_OFF;
    /** SMTPサーバ暗号化  */
    private int sml110SmtpEncrypt__ = -1;
    /** 暗号化プロトコル 一覧 */
    private List<LabelValueBean> sml110AngoProtocolCombo__ = null;

    /** 認証形式 */
    private String sml110authMethod__ = null;
    /** プロバイダ */
    private int sml110provider__ = 0;
    /** OAuth認証 アカウントID */
    private String sml110authAccount__ = null;
    /** プロバイダ一覧 */
    private List<LabelValueBean> sml110providerList__ = null;
    /** OAuth認証済みフラグ */
    private int sml110oauthCompFlg__ = GSConstSmail.AUTH_YET;
    /** OAuth認証情報トークンSID */
    private int sml110cotSid__ = 0;

    /** メール転送設定 初期表示フラグ */
    private int sml110InitFlg__ = GSConstSmail.DSP_FIRST;


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String serverUrl = gsMsg.getMessage(req, "sml.sml110.07");
        String smtpSerPort = gsMsg.getMessage(req, "sml.sml110.08");
        String userId = gsMsg.getMessage(req, "sml.sml110.22");
        String userPass = gsMsg.getMessage(req, "sml.sml110.21");
        String fromAdr = gsMsg.getMessage(req, "sml.sml110.17");

        //SMTPサーバURLのチェック
        if (sml110MailForward__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))) {

            //認証形式
            int authMethod = NullDefault.getInt(
                    sml110authMethod__, GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL);
            if (authMethod != GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL
                    && authMethod != GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
                msg
                =  new ActionMessage("error.input.format.text",
                        gsMsg.getMessage("cmn.auth.method"));
                String eprefix = "sml110authMethod";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            //転送メールfromアドレス
            if (StringUtil.isNullZeroString(sml110FromAddress__)) {
                // 未入力チェック
                msg = new ActionMessage("error.input.required.text", fromAdr);
                StrutsUtil.addMessage(errors, msg, "sml110FromAddress"
                        + "error.input.required.text");
            } else {
                errors = validateMail(
                        errors,
                        sml110FromAddress__,
                        "sml110FromAddress",
                        fromAdr);
            }

            //転送先を制限する文字列の入力チェックを行う
            if (sml110FwLmtKbn__.equals(GSConstSmail.MAIL_FORWARD_LIMIT)) {
                errors = validateFwMail(errors, req);
            }

            //認証形式別に入力チェックを変更する
            if (authMethod == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
                //OAuth認証

                //プロバイダ入力チェック
                CmnOauthDao couDao = new CmnOauthDao(con);
                if (couDao.select(sml110provider__) == null) {
                    msg = new ActionMessage("error.input.format.file",
                            gsMsg.getMessage("cmn.auth.provider"),
                            gsMsg.getMessage("cmn.value"));
                    String eprefix = "sml110provider";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else {
              //アドレス 入力
                if (StringUtil.isNullZeroString(sml110SmtpUrl__)) {
                    msg = new ActionMessage("error.input.required.text", serverUrl);
                    errors.add("sml110SmtpUrl.error.input.required.text", msg);
                //アドレス 文字数
                } else if (sml110SmtpUrl__.length() > GSConstSmail.MAX_LENGTH_SMTP) {
                    msg = new ActionMessage("error.input.length.text",
                            serverUrl,
                            GSConstSmail.MAX_LENGTH_SMTP);
                    errors.add("sml110SmtpUrl.error.input.length.text", msg);
                //アドレス 使用文字
                } else if (!GSValidateUtil.isGsJapaneaseString(sml110SmtpUrl__)) {
                    String nstr = GSValidateUtil.getNotGsJapaneaseString(sml110SmtpUrl__);
                    msg = new ActionMessage("error.input.njapan.text",
                            serverUrl,
                            nstr);
                    errors.add("sml110SmtpUrl.error.input.njapan.text", msg);
                }


                //ポート番号
                if (StringUtil.isNullZeroString(sml110SmtpPort__)) {
                    msg = new ActionMessage("error.input.required.text",
                            smtpSerPort);
                    errors.add("sml110SmtpPort.error.input.required.text", msg);
                } else if (StringUtil.isNullZeroString(sml110SmtpPort__)) {
                    msg = new ActionMessage("error.input.required.text",
                            smtpSerPort);
                    errors.add("sml110SmtpPort.error.input.required.text", msg);
                //ポート番号 文字数
                } else if (sml110SmtpPort__.length() > GSConstSmail.MAX_LENGTH_SMTP_PORT) {
                    msg = new ActionMessage("error.input.length.text",
                            smtpSerPort,
                            GSConstSmail.MAX_LENGTH_SMTP_PORT);
                    errors.add("sml110SmtpPort.error.input.length.text", msg);
                //ポート番号 半角数字
                } else if (!ValidateUtil.isNumber(sml110SmtpPort__)) {
                    msg = new ActionMessage("error.input.length.number2",
                            smtpSerPort,
                            String.valueOf(GSConstSmail.MAX_LENGTH_SMTP_PORT));
                    errors.add("sml110SmtpPort.error.input.comp.text", msg);
                //ポート番号 最大値
                } else if (Integer.parseInt(sml110SmtpPort__)
                        > GSConstSmail.MAX_NUMBER_SMTP_PORTNUM) {
                    msg = new ActionMessage("error.input.number.under",
                            smtpSerPort,
                            GSConstSmail.MAX_NUMBER_SMTP_PORTNUM);
                    errors.add("sml110SmtpPort.error.input.comp.text", msg);
                }
                // SMTPサーバ暗号化入力チェック
                MailEncryptBiz protocolBiz = new MailEncryptBiz();
                if (!protocolBiz.isExistProtocol(sml110SmtpEncrypt__)) {
                    msg = new ActionMessage("error.input.format.file",
                            gsMsg.getMessage("sml.sml110.06"),
                            gsMsg.getMessage("cmn.ango"));
                    String eprefix = "notSmtpServerEncrypt";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }

                //認証ユーザ
                GSValidateCommon.validateTextField(errors,
                        sml110SmtpUser__, "sml110SmtpUser", userId,
                        GSConstSmail.MAX_LENGTH_USER, false);

                //認証パスワード
                GSValidateCommon.validateTextField(errors,
                        sml110SmtpPass__, "sml110SmtpPass", userPass,
                        GSConstSmail.MAX_LENGTH_PASS, false);
            }

        }
        return errors;
    }

    /**
     * <p>転送先を制限する文字列の入力チェックを行う
     * @param errors ActionErrors
     * @param req リクエスト
     * @param authType 認証形式
     * @return ActionErrors
     */
    public ActionErrors validateFwMail(ActionErrors errors, HttpServletRequest req) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        String lengthLimit = gsMsg.getMessage(req, "sml.158");

        // 未入力チェック
        if (sml110FwlmtTextArea__ == null || sml110FwlmtTextArea__.equals("")) {

            msg = new ActionMessage("error.input.required.text", lengthLimit);
            StrutsUtil.addMessage(errors, msg, "sml110FwlmtTextArea"
                    + "error.input.required.text");
            return errors;
        }

        //転送先制限文字フォーマットチェック
        String[] fwlmtText = null;
        if (sml110FwlmtTextArea__ != null) {
            fwlmtText = sml110FwlmtTextArea__.split("\n");

        }

        for (int i = 0; fwlmtText.length > i; i++) {
            if (!GSValidateUtil.isAsciiOrNumber(StringUtilHtml.deleteHtmlTag(fwlmtText[i]))) {
                msg = new ActionMessage("error.input.format.text", lengthLimit);
                StrutsUtil.addMessage(errors, msg, "sml110FwlmtTextArea"
                        + "error.input.format.text");
                return errors;
            }
        }


        return errors;
    }

    /**
     * <p>メールアドレスの入力チェックを行う
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @param eprefix メッセージサフィックス
     * @param text 項目名
     * @return ActionErrors
     */
    public static ActionErrors validateMail(ActionErrors errors,
            String mail, String eprefix, String text) {
        ActionMessage msg = null;

//        String eprefix = num + "mail.";

        if (!StringUtil.isNullZeroString(mail)) {
            if (mail.length() > GSConstUser.MAX_LENGTH_MAIL) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        text,
                        GSConstUser.MAX_LENGTH_MAIL);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else {

                //メールフォーマットチェック
                if (!GSValidateUtil.isMailFormat(mail)) {
                    msg = new ActionMessage("error.input.format.text", text);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.format.text");
                }
            }

        }
        return errors;
    }

    /**
     * <p>sml110FwCheckList を取得します。
     * @return sml110FwCheckList
     */
    public List<Sml110FwCheckModel> getSml110FwCheckList() {
        return sml110FwCheckList__;
    }

    /**
     * <p>sml110FwCheckList をセットします。
     * @param sml110FwCheckList sml110FwCheckList
     */
    public void setSml110FwCheckList(List<Sml110FwCheckModel> sml110FwCheckList) {
        sml110FwCheckList__ = sml110FwCheckList;
    }

    /**
     * <p>sml110FwLmtKbn を取得します。
     * @return sml110FwLmtKbn
     */
    public String getSml110FwLmtKbn() {
        return sml110FwLmtKbn__;
    }

    /**
     * <p>sml110FwLmtKbn をセットします。
     * @param sml110FwLmtKbn sml110FwLmtKbn
     */
    public void setSml110FwLmtKbn(String sml110FwLmtKbn) {
        sml110FwLmtKbn__ = sml110FwLmtKbn;
    }

    /**
     * <p>sml110FwlmtTextArea を取得します。
     * @return sml110FwlmtTextArea
     */
    public String getSml110FwlmtTextArea() {
        return sml110FwlmtTextArea__;
    }

    /**
     * <p>sml110FwlmtTextArea をセットします。
     * @param sml110FwlmtTextArea sml110FwlmtTextArea
     */
    public void setSml110FwlmtTextArea(String sml110FwlmtTextArea) {
        sml110FwlmtTextArea__ = sml110FwlmtTextArea;
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Sml110Form() {
    }

    /**
     * <p>sml110SmtpPort を取得します。
     * @return sml110SmtpPort
     */
    public String getSml110SmtpPort() {
        return sml110SmtpPort__;
    }
    /**
     * <p>sml110SmtpPort をセットします。
     * @param sml110SmtpPort sml110SmtpPort
     */
    public void setSml110SmtpPort(String sml110SmtpPort) {
        sml110SmtpPort__ = sml110SmtpPort;
    }
    /**
     * <p>sml110FromAddress を取得します。
     * @return sml110FromAddress
     */
    public String getSml110FromAddress() {
        return sml110FromAddress__;
    }

    /**
     * <p>sml110FromAddress をセットします。
     * @param sml110FromAddress sml110FromAddress
     */
    public void setSml110FromAddress(String sml110FromAddress) {
        sml110FromAddress__ = sml110FromAddress;
    }

    /**
     * <p>sml110SmtpPass を取得します。
     * @return sml110SmtpPass
     */
    public String getSml110SmtpPass() {
        return sml110SmtpPass__;
    }

    /**
     * <p>sml110SmtpPass をセットします。
     * @param sml110SmtpPass sml110SmtpPass
     */
    public void setSml110SmtpPass(String sml110SmtpPass) {
        sml110SmtpPass__ = sml110SmtpPass;
    }

    /**
     * <p>sml110SmtpUser を取得します。
     * @return sml110SmtpUser
     */
    public String getSml110SmtpUser() {
        return sml110SmtpUser__;
    }

    /**
     * <p>sml110SmtpUser をセットします。
     * @param sml110SmtpUser sml110SmtpUser
     */
    public void setSml110SmtpUser(String sml110SmtpUser) {
        sml110SmtpUser__ = sml110SmtpUser;
    }

    /**
     * <p>sml110MailForward を取得します。
     * @return sml110MailForward
     */
    public String getSml110MailForward() {
        return sml110MailForward__;
    }

    /**
     * <p>sml110MailForward をセットします。
     * @param sml110MailForward sml110MailForward
     */
    public void setSml110MailForward(String sml110MailForward) {
        sml110MailForward__ = sml110MailForward;
    }

    /**
     * <p>sml110SmtpUrl を取得します。
     * @return sml110SmtpUrl
     */
    public String getSml110SmtpUrl() {
        return sml110SmtpUrl__;
    }

    /**
     * <p>sml110SmtpUrl をセットします。
     * @param sml110SmtpUrl sml110SmtpUrl
     */
    public void setSml110SmtpUrl(String sml110SmtpUrl) {
        sml110SmtpUrl__ = sml110SmtpUrl;
    }


    /**
     * <p>sml110CheckFlg を取得します。
     * @return sml110CheckFlg
     */
    public boolean isSml110CheckFlg() {
        return sml110CheckFlg__;
    }

    /**
     * <p>sml110CheckFlg をセットします。
     * @param sml110CheckFlg sml110CheckFlg
     */
    public void setSml110CheckFlg(boolean sml110CheckFlg) {
        sml110CheckFlg__ = sml110CheckFlg;
    }

    /**
     * <p>sml110SmtpEncrypt を取得します。
     * @return sml110SmtpEncrypt
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110SmtpEncrypt__
     */
    public int getSml110SmtpEncrypt() {
        return sml110SmtpEncrypt__;
    }

    /**
     * <p>sml110SmtpEncrypt をセットします。
     * @param sml110SmtpEncrypt sml110SmtpEncrypt
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110SmtpEncrypt__
     */
    public void setSml110SmtpEncrypt(int sml110SmtpEncrypt) {
        sml110SmtpEncrypt__ = sml110SmtpEncrypt;
    }

    /**
     * <p>sml110AngoProtocolCombo を取得します。
     * @return sml110AngoProtocolCombo
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110AngoProtocolCombo__
     */
    public List<LabelValueBean> getSml110AngoProtocolCombo() {
        return sml110AngoProtocolCombo__;
    }

    /**
     * <p>sml110AngoProtocolCombo をセットします。
     * @param sml110AngoProtocolCombo sml110AngoProtocolCombo
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110AngoProtocolCombo__
     */
    public void setSml110AngoProtocolCombo(
            List<LabelValueBean> sml110AngoProtocolCombo) {
        sml110AngoProtocolCombo__ = sml110AngoProtocolCombo;
    }

    /**
     * <p>sml110authMethod を取得します。
     * @return sml110authMethod
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110authMethod__
     */
    public String getSml110authMethod() {
        return sml110authMethod__;
    }

    /**
     * <p>sml110authMethod をセットします。
     * @param sml110authMethod sml110authMethod
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110authMethod__
     */
    public void setSml110authMethod(String sml110authMethod) {
        sml110authMethod__ = sml110authMethod;
    }

    /**
     * <p>sml110provider を取得します。
     * @return sml110provider
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110provider__
     */
    public int getSml110provider() {
        return sml110provider__;
    }

    /**
     * <p>sml110provider をセットします。
     * @param sml110provider sml110provider
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110provider__
     */
    public void setSml110provider(int sml110provider) {
        sml110provider__ = sml110provider;
    }

    /**
     * <p>sml110authAccount を取得します。
     * @return sml110authAccount
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110authAccount__
     */
    public String getSml110authAccount() {
        return sml110authAccount__;
    }

    /**
     * <p>sml110authAccount をセットします。
     * @param sml110authAccount sml110authAccount
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110authAccount__
     */
    public void setSml110authAccount(String sml110authAccount) {
        sml110authAccount__ = sml110authAccount;
    }

    /**
     * <p>sml110providerList を取得します。
     * @return sml110providerList
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110providerList__
     */
    public List<LabelValueBean> getSml110providerList() {
        return sml110providerList__;
    }

    /**
     * <p>sml110providerList をセットします。
     * @param sml110providerList sml110providerList
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110providerList__
     */
    public void setSml110providerList(List<LabelValueBean> sml110providerList) {
        sml110providerList__ = sml110providerList;
    }

    /**
     * <p>sml110oauthCompFlg を取得します。
     * @return sml110oauthCompFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110oauthCompFlg__
     */
    public int getSml110oauthCompFlg() {
        return sml110oauthCompFlg__;
    }

    /**
     * <p>sml110oauthCompFlg をセットします。
     * @param sml110oauthCompFlg sml110oauthCompFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110oauthCompFlg__
     */
    public void setSml110oauthCompFlg(int sml110oauthCompFlg) {
        sml110oauthCompFlg__ = sml110oauthCompFlg;
    }

    /**
     * <p>sml110cotSid を取得します。
     * @return sml110cotSid
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110cotSid__
     */
    public int getSml110cotSid() {
        return sml110cotSid__;
    }

    /**
     * <p>sml110cotSid をセットします。
     * @param sml110cotSid sml110cotSid
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110cotSid__
     */
    public void setSml110cotSid(int sml110cotSid) {
        sml110cotSid__ = sml110cotSid;
    }

    /**
     * <p>sml110InitFlg を取得します。
     * @return sml110InitFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110InitFlg__
     */
    public int getSml110InitFlg() {
        return sml110InitFlg__;
    }

    /**
     * <p>sml110InitFlg をセットします。
     * @param sml110InitFlg sml110InitFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110Form#sml110InitFlg__
     */
    public void setSml110InitFlg(int sml110InitFlg) {
        sml110InitFlg__ = sml110InitFlg;
    }

}