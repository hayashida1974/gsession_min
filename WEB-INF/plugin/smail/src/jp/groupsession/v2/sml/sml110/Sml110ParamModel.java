package jp.groupsession.v2.sml.sml110;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml100.Sml100ParamModel;

/**
 * <br>[機  能] ショートメール 管理者設定 転送設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml110ParamModel extends Sml100ParamModel {
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
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110SmtpEncrypt__
     */
    public int getSml110SmtpEncrypt() {
        return sml110SmtpEncrypt__;
    }
    /**
     * <p>sml110SmtpEncrypt をセットします。
     * @param sml110SmtpEncrypt sml110SmtpEncrypt
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110SmtpEncrypt__
     */
    public void setSml110SmtpEncrypt(int sml110SmtpEncrypt) {
        sml110SmtpEncrypt__ = sml110SmtpEncrypt;
    }
    /**
     * <p>sml110AngoProtocolCombo を取得します。
     * @return sml110AngoProtocolCombo
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110AngoProtocolCombo__
     */
    public List<LabelValueBean> getSml110AngoProtocolCombo() {
        return sml110AngoProtocolCombo__;
    }
    /**
     * <p>sml110AngoProtocolCombo をセットします。
     * @param sml110AngoProtocolCombo sml110AngoProtocolCombo
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110AngoProtocolCombo__
     */
    public void setSml110AngoProtocolCombo(
            List<LabelValueBean> sml110AngoProtocolCombo) {
        sml110AngoProtocolCombo__ = sml110AngoProtocolCombo;
    }
    /**
     * <p>sml110authMethod を取得します。
     * @return sml110authMethod
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110authMethod__
     */
    public String getSml110authMethod() {
        return sml110authMethod__;
    }
    /**
     * <p>sml110authMethod をセットします。
     * @param sml110authMethod sml110authMethod
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110authMethod__
     */
    public void setSml110authMethod(String sml110authMethod) {
        sml110authMethod__ = sml110authMethod;
    }
    /**
     * <p>sml110provider を取得します。
     * @return sml110provider
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110provider__
     */
    public int getSml110provider() {
        return sml110provider__;
    }
    /**
     * <p>sml110provider をセットします。
     * @param sml110provider sml110provider
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110provider__
     */
    public void setSml110provider(int sml110provider) {
        sml110provider__ = sml110provider;
    }
    /**
     * <p>sml110authAccount を取得します。
     * @return sml110authAccount
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110authAccount__
     */
    public String getSml110authAccount() {
        return sml110authAccount__;
    }
    /**
     * <p>sml110authAccount をセットします。
     * @param sml110authAccount sml110authAccount
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110authAccount__
     */
    public void setSml110authAccount(String sml110authAccount) {
        sml110authAccount__ = sml110authAccount;
    }
    /**
     * <p>sml110providerList を取得します。
     * @return sml110providerList
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110providerList__
     */
    public List<LabelValueBean> getSml110providerList() {
        return sml110providerList__;
    }
    /**
     * <p>sml110providerList をセットします。
     * @param sml110providerList sml110providerList
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110providerList__
     */
    public void setSml110providerList(List<LabelValueBean> sml110providerList) {
        sml110providerList__ = sml110providerList;
    }
    /**
     * <p>sml110oauthCompFlg を取得します。
     * @return sml110oauthCompFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110oauthCompFlg__
     */
    public int getSml110oauthCompFlg() {
        return sml110oauthCompFlg__;
    }
    /**
     * <p>sml110oauthCompFlg をセットします。
     * @param sml110oauthCompFlg sml110oauthCompFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110oauthCompFlg__
     */
    public void setSml110oauthCompFlg(int sml110oauthCompFlg) {
        sml110oauthCompFlg__ = sml110oauthCompFlg;
    }
    /**
     * <p>sml110cotSid を取得します。
     * @return sml110cotSid
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110cotSid__
     */
    public int getSml110cotSid() {
        return sml110cotSid__;
    }
    /**
     * <p>sml110cotSid をセットします。
     * @param sml110cotSid sml110cotSid
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110cotSid__
     */
    public void setSml110cotSid(int sml110cotSid) {
        sml110cotSid__ = sml110cotSid;
    }
    /**
     * <p>sml110InitFlg を取得します。
     * @return sml110InitFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110InitFlg__
     */
    public int getSml110InitFlg() {
        return sml110InitFlg__;
    }
    /**
     * <p>sml110InitFlg をセットします。
     * @param sml110InitFlg sml110InitFlg
     * @see jp.groupsession.v2.sml.sml110.Sml110ParamModel#sml110InitFlg__
     */
    public void setSml110InitFlg(int sml110InitFlg) {
        sml110InitFlg__ = sml110InitFlg;
    }
}