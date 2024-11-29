package jp.groupsession.v2.wml.wml190;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.wml.wml020.Wml020ParamModel;
import jp.groupsession.v2.wml.wml040.Wml040Form;

/**
 * <br>[機  能] WEBメール 個人設定 アカウント編集画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml190ParamModel extends Wml020ParamModel {
    /** アカウント名 */
    private String wml190name__ = null;
    /** メールアドレス */
    private String wml190address__ = null;

    /** メール受信サーバ */
    private String wml190receiveServer__ = null;
    /** メール受信サーバ ポート番号 */
    private String wml190receiveServerPort__ = null;
    /** メール受信サーバ 暗号化 */
    private int wml190receiveServerEncrypt__ = 0;
    /** メール受信サーバの種類 */
    private int wml190receiveServerType__ = Wml040Form.RSERVERTYPE_POP;
    /** メール受信サーバ ユーザID */
    private String wml190receiveServerUser__ = null;
    /** メール受信サーバ パスワード */
    private String wml190receiveServerPassword__ = null;
    /** メール送信サーバ */
    private String wml190sendServer__ = null;
    /** メール送信サーバ名 ポート番号 */
    private String wml190sendServerPort__ = null;
    /** メール受信サーバ 暗号化 */
    private int wml190sendServerEncrypt__ = 0;
    /** SMTP認証ON/OFF */
    private int wml190smtpAuth__ = Wml040Form.SMTPAUTH_OFF;
    /** メール送信サーバ ユーザ名 */
    private String wml190sendServerUser__ = null;
    /** メール送信サーバ名 パスワード */
    private String wml190sendServerPassword__ = null;

    /** 認証形式 */
    private int wml190authMethod__ = GSConstWebmail.WAC_AUTH_TYPE_NORMAL;
    /** プロバイダ */
    private int wml190provider__ = 0;
    /** OAuth認証 アカウントID */
    private String wml190authAccount__ = null;
    /** OAuth認証済みフラグ */
    private boolean wml190oauthCompFlg__ = false;
    /** OAuth認証情報トークンSID */
    private int wml190cotSid__ = 0;

    /** 署名 */
    private int wml190sign__ = 0;
    /** 署名 自動挿入*/
    private int wml190signAuto__ = 0;
    /** 自動TO */
    private String wml190autoTo__ = null;
    /** 自動CC */
    private String wml190autoCc__ = null;
    /** 自動BCC */
    private String wml190autoBcc__ = null;
    /** テーマ */
    private int wml190theme__ = 0;
    /** 引用符 */
    private int wml190quotes__ = 0;
    /** 自動保存 */
    private String wml190autoSaveMin__ = null;
    /** 代理人 */
    private String[] wml190proxyUser__ = null;

    /** 初期表示フラグ */
    private int wml190initFlg__ = 0;
    /** サーバ情報設定許可 */
    private int wml190settingServer__ = 0;

    /** 代理人 許可 */
    private boolean wml190proxyUserFlg__ = false;

    /** テーマ 一覧 */
    private List<LabelValueBean> wml190themeList__ = null;
    /** 引用符 一覧 */
    private List<LabelValueBean> wml190quotesList__ = null;
    /** 署名 一覧 */
    private List<LabelValueBean> wml190signList__ = null;

    /** 代理人 グループ */
    private String wml190proxyUserGroup__ = null;
    /** 代理人 選択ユーザ */
    private List<UsrLabelValueBean> proxyUserSelectCombo__  = null;
    /** 代理人 UI */
    private UserGroupSelector wml190proxyUserUI__ = null;

    /** 暗号化プロトコル 一覧 */
    private List<LabelValueBean> wml190AngoProtocolCombo__ = null;
    /** 自動保存リスト */
    private List<LabelValueBean> wml190autoSaveList__ = null;

    /**
     * <p>wml190autoBcc を取得します。
     * @return wml190autoBcc
     */
    public String getWml190autoBcc() {
        return wml190autoBcc__;
    }
    /**
     * <p>wml190autoBcc をセットします。
     * @param wml190autoBcc wml190autoBcc
     */
    public void setWml190autoBcc(String wml190autoBcc) {
        wml190autoBcc__ = wml190autoBcc;
    }
    /**
     * <p>wml190autoCc を取得します。
     * @return wml190autoCc
     */
    public String getWml190autoCc() {
        return wml190autoCc__;
    }
    /**
     * <p>wml190autoCc をセットします。
     * @param wml190autoCc wml190autoCc
     */
    public void setWml190autoCc(String wml190autoCc) {
        wml190autoCc__ = wml190autoCc;
    }
    /**
     * <p>wml190autoTo を取得します。
     * @return wml190autoTo
     */
    public String getWml190autoTo() {
        return wml190autoTo__;
    }
    /**
     * <p>wml190autoTo をセットします。
     * @param wml190autoTo wml190autoTo
     */
    public void setWml190autoTo(String wml190autoTo) {
        wml190autoTo__ = wml190autoTo;
    }
    /**
     * <p>wml190name を取得します。
     * @return wml190name
     */
    public String getWml190name() {
        return wml190name__;
    }
    /**
     * <p>wml190name をセットします。
     * @param wml190name wml190name
     */
    public void setWml190name(String wml190name) {
        wml190name__ = wml190name;
    }
    /**
     * <p>wml190address を取得します。
     * @return wml190address
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190address__
     */
    public String getWml190address() {
        return wml190address__;
    }
    /**
     * <p>wml190address をセットします。
     * @param wml190address wml190address
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190address__
     */
    public void setWml190address(String wml190address) {
        wml190address__ = wml190address;
    }
    /**
     * <p>wml190receiveServer を取得します。
     * @return wml190receiveServer
     */
    public String getWml190receiveServer() {
        return wml190receiveServer__;
    }
    /**
     * <p>wml190receiveServer をセットします。
     * @param wml190receiveServer wml190receiveServer
     */
    public void setWml190receiveServer(String wml190receiveServer) {
        wml190receiveServer__ = wml190receiveServer;
    }
    /**
     * <p>wml190receiveServerPort を取得します。
     * @return wml190receiveServerPort
     */
    public String getWml190receiveServerPort() {
        return wml190receiveServerPort__;
    }
    /**
     * <p>wml190receiveServerPort をセットします。
     * @param wml190receiveServerPort wml190receiveServerPort
     */
    public void setWml190receiveServerPort(String wml190receiveServerPort) {
        wml190receiveServerPort__ = wml190receiveServerPort;
    }

    /**
     * <p>wml190receiveServerType を取得します。
     * @return wml190receiveServerType
     */
    public int getWml190receiveServerType() {
        return wml190receiveServerType__;
    }
    /**
     * <p>wml190receiveServerType をセットします。
     * @param wml190receiveServerType wml190receiveServerType
     */
    public void setWml190receiveServerType(int wml190receiveServerType) {
        wml190receiveServerType__ = wml190receiveServerType;
    }
    /**
     * <p>wml190receiveServerUser を取得します。
     * @return wml190receiveServerUser
     */
    public String getWml190receiveServerUser() {
        return wml190receiveServerUser__;
    }
    /**
     * <p>wml190receiveServerUser をセットします。
     * @param wml190receiveServerUser wml190receiveServerUser
     */
    public void setWml190receiveServerUser(String wml190receiveServerUser) {
        wml190receiveServerUser__ = wml190receiveServerUser;
    }
    /**
     * <p>wml190receiveServerPassword を取得します。
     * @return wml190receiveServerPassword
     */
    public String getWml190receiveServerPassword() {
        return wml190receiveServerPassword__;
    }
    /**
     * <p>wml190receiveServerPassword をセットします。
     * @param wml190receiveServerPassword wml190receiveServerPassword
     */
    public void setWml190receiveServerPassword(String wml190receiveServerPassword) {
        wml190receiveServerPassword__ = wml190receiveServerPassword;
    }
    /**
     * <p>wml190sendServer を取得します。
     * @return wml190sendServer
     */
    public String getWml190sendServer() {
        return wml190sendServer__;
    }
    /**
     * <p>wml190sendServer をセットします。
     * @param wml190sendServer wml190sendServer
     */
    public void setWml190sendServer(String wml190sendServer) {
        wml190sendServer__ = wml190sendServer;
    }
    /**
     * <p>wml190sendServerPort を取得します。
     * @return wml190sendServerPort
     */
    public String getWml190sendServerPort() {
        return wml190sendServerPort__;
    }
    /**
     * <p>wml190sendServerPort をセットします。
     * @param wml190sendServerPort wml190sendServerPort
     */
    public void setWml190sendServerPort(String wml190sendServerPort) {
        wml190sendServerPort__ = wml190sendServerPort;
    }

    /**
     * <p>wml190smtpAuth を取得します。
     * @return wml190smtpAuth
     */
    public int getWml190smtpAuth() {
        return wml190smtpAuth__;
    }
    /**
     * <p>wml190smtpAuth をセットします。
     * @param wml190smtpAuth wml190smtpAuth
     */
    public void setWml190smtpAuth(int wml190smtpAuth) {
        wml190smtpAuth__ = wml190smtpAuth;
    }

    /**
     * <p>wml190sendServerUser を取得します。
     * @return wml190sendServerUser
     */
    public String getWml190sendServerUser() {
        return wml190sendServerUser__;
    }
    /**
     * <p>wml190sendServerUser をセットします。
     * @param wml190sendServerUser wml190sendServerUser
     */
    public void setWml190sendServerUser(String wml190sendServerUser) {
        wml190sendServerUser__ = wml190sendServerUser;
    }
    /**
     * <p>wml190sendServerPassword を取得します。
     * @return wml190sendServerPassword
     */
    public String getWml190sendServerPassword() {
        return wml190sendServerPassword__;
    }
    /**
     * <p>wml190sendServerPassword をセットします。
     * @param wml190sendServerPassword wml190sendServerPassword
     */
    public void setWml190sendServerPassword(String wml190sendServerPassword) {
        wml190sendServerPassword__ = wml190sendServerPassword;
    }
    /**
     * <p>wml190sign を取得します。
     * @return wml190sign
     */
    public int getWml190sign() {
        return wml190sign__;
    }
    /**
     * <p>wml190sign をセットします。
     * @param wml190sign wml190sign
     */
    public void setWml190sign(int wml190sign) {
        wml190sign__ = wml190sign;
    }
    /**
     * <p>wml190signAuto を取得します。
     * @return wml190signAuto
     */
    public int getWml190signAuto() {
        return wml190signAuto__;
    }
    /**
     * <p>wml190signAuto をセットします。
     * @param wml190signAuto wml190signAuto
     */
    public void setWml190signAuto(int wml190signAuto) {
        wml190signAuto__ = wml190signAuto;
    }
    /**
     * <p>wml190initFlg を取得します。
     * @return wml190initFlg
     */
    public int getWml190initFlg() {
        return wml190initFlg__;
    }
    /**
     * <p>wml190initFlg をセットします。
     * @param wml190initFlg wml190initFlg
     */
    public void setWml190initFlg(int wml190initFlg) {
        wml190initFlg__ = wml190initFlg;
    }
    /**
     * <p>wml190settingServer を取得します。
     * @return wml190settingServer
     */
    public int getWml190settingServer() {
        return wml190settingServer__;
    }
    /**
     * <p>wml190settingServer をセットします。
     * @param wml190settingServer wml190settingServer
     */
    public void setWml190settingServer(int wml190settingServer) {
        wml190settingServer__ = wml190settingServer;
    }
    /**
     * <p>wml190theme を取得します。
     * @return wml190theme
     */
    public int getWml190theme() {
        return wml190theme__;
    }
    /**
     * <p>wml190theme をセットします。
     * @param wml190theme wml190theme
     */
    public void setWml190theme(int wml190theme) {
        wml190theme__ = wml190theme;
    }
    /**
     * <p>wml190quotes を取得します。
     * @return wml190quotes
     */
    public int getWml190quotes() {
        return wml190quotes__;
    }
    /**
     * <p>wml190quotes をセットします。
     * @param wml190quotes wml190quotes
     */
    public void setWml190quotes(int wml190quotes) {
        wml190quotes__ = wml190quotes;
    }
    /**
     * <p>wml190autoSaveMin を取得します。
     * @return wml190autoSaveMin
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190autoSaveMin__
     */
    public String getWml190autoSaveMin() {
        return wml190autoSaveMin__;
    }
    /**
     * <p>wml190autoSaveMin をセットします。
     * @param wml190autoSaveMin wml190autoSaveMin
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190autoSaveMin__
     */
    public void setWml190autoSaveMin(String wml190autoSaveMin) {
        wml190autoSaveMin__ = wml190autoSaveMin;
    }
    /**
     * <p>wml190themeList を取得します。
     * @return wml190themeList
     */
    public List<LabelValueBean> getWml190themeList() {
        return wml190themeList__;
    }
    /**
     * <p>wml190themeList をセットします。
     * @param wml190themeList wml190themeList
     */
    public void setWml190themeList(List<LabelValueBean> wml190themeList) {
        wml190themeList__ = wml190themeList;
    }
    /**
     * <p>wml190quotesList を取得します。
     * @return wml190quotesList
     */
    public List<LabelValueBean> getWml190quotesList() {
        return wml190quotesList__;
    }
    /**
     * <p>wml190quotesList をセットします。
     * @param wml190quotesList wml190quotesList
     */
    public void setWml190quotesList(List<LabelValueBean> wml190quotesList) {
        wml190quotesList__ = wml190quotesList;
    }
    /**
     * <p>wml190signList を取得します。
     * @return wml190signList
     */
    public List<LabelValueBean> getWml190signList() {
        return wml190signList__;
    }
    /**
     * <p>wml190signList をセットします。
     * @param wml190signList wml190signList
     */
    public void setWml190signList(List<LabelValueBean> wml190signList) {
        wml190signList__ = wml190signList;
    }
    /**
     * <p>wml190proxyUser を取得します。
     * @return wml190proxyUser
     */
    public String[] getWml190proxyUser() {
        return wml190proxyUser__;
    }
    /**
     * <p>wml190proxyUser をセットします。
     * @param wml190proxyUser wml190proxyUser
     */
    public void setWml190proxyUser(String[] wml190proxyUser) {
        wml190proxyUser__ = wml190proxyUser;
    }
    /**
     * <p>wml190proxyUserFlg を取得します。
     * @return wml190proxyUserFlg
     */
    public boolean isWml190proxyUserFlg() {
        return wml190proxyUserFlg__;
    }
    /**
     * <p>wml190proxyUserFlg をセットします。
     * @param wml190proxyUserFlg wml190proxyUserFlg
     */
    public void setWml190proxyUserFlg(boolean wml190proxyUserFlg) {
        wml190proxyUserFlg__ = wml190proxyUserFlg;
    }
    /**
     * <p>wml190proxyUserGroup を取得します。
     * @return wml190proxyUserGroup
     */
    public String getWml190proxyUserGroup() {
        return wml190proxyUserGroup__;
    }
    /**
     * <p>wml190proxyUserGroup をセットします。
     * @param wml190proxyUserGroup wml190proxyUserGroup
     */
    public void setWml190proxyUserGroup(String wml190proxyUserGroup) {
        wml190proxyUserGroup__ = wml190proxyUserGroup;
    }
    /**
     * <p>proxyUserSelectCombo を取得します。
     * @return proxyUserSelectCombo
     */
    public List<UsrLabelValueBean> getProxyUserSelectCombo() {
        return proxyUserSelectCombo__;
    }
    /**
     * <p>proxyUserSelectCombo をセットします。
     * @param proxyUserSelectCombo proxyUserSelectCombo
     */
    public void setProxyUserSelectCombo(List<UsrLabelValueBean> proxyUserSelectCombo) {
        proxyUserSelectCombo__ = proxyUserSelectCombo;
    }
    /**
     * <p>wml190proxyUserUI を取得します。
     * @return wml190proxyUserUI
     */
    public UserGroupSelector getWml190proxyUserUI() {
        return wml190proxyUserUI__;
    }
    /**
     * <p>wml190proxyUserUI をセットします。
     * @param wml190proxyUserUI wml190proxyUserUI
     */
    public void setWml190proxyUserUI(UserGroupSelector wml190proxyUserUI) {
        wml190proxyUserUI__ = wml190proxyUserUI;
    }
    /**
     * <p>wml190receiveServerEncrypt を取得します。
     * @return wml190receiveServerEncrypt
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190receiveServerEncrypt__
     */
    public int getWml190receiveServerEncrypt() {
        return wml190receiveServerEncrypt__;
    }
    /**
     * <p>wml190receiveServerEncrypt をセットします。
     * @param wml190receiveServerEncrypt wml190receiveServerEncrypt
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190receiveServerEncrypt__
     */
    public void setWml190receiveServerEncrypt(int wml190receiveServerEncrypt) {
        wml190receiveServerEncrypt__ = wml190receiveServerEncrypt;
    }
    /**
     * <p>wml190sendServerEncrypt を取得します。
     * @return wml190sendServerEncrypt
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190sendServerEncrypt__
     */
    public int getWml190sendServerEncrypt() {
        return wml190sendServerEncrypt__;
    }
    /**
     * <p>wml190sendServerEncrypt をセットします。
     * @param wml190sendServerEncrypt wml190sendServerEncrypt
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190sendServerEncrypt__
     */
    public void setWml190sendServerEncrypt(int wml190sendServerEncrypt) {
        wml190sendServerEncrypt__ = wml190sendServerEncrypt;
    }
    /**
     * <p>wml190AngoProtocolCombo を取得します。
     * @return wml190AngoProtocolCombo
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190AngoProtocolCombo__
     */
    public List<LabelValueBean> getWml190AngoProtocolCombo() {
        return wml190AngoProtocolCombo__;
    }
    /**
     * <p>wml190AngoProtocolCombo をセットします。
     * @param wml190AngoProtocolCombo wml190AngoProtocolCombo
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190AngoProtocolCombo__
     */
    public void setWml190AngoProtocolCombo(
            List<LabelValueBean> wml190AngoProtocolCombo) {
        wml190AngoProtocolCombo__ = wml190AngoProtocolCombo;
    }
    /**
     * <p>wml190autoSaveList を取得します。
     * @return wml190autoSaveList
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190autoSaveList__
     */
    public List<LabelValueBean> getWml190autoSaveList() {
        return wml190autoSaveList__;
    }
    /**
     * <p>wml190autoSaveList をセットします。
     * @param wml190autoSaveList wml190autoSaveList
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190autoSaveList__
     */
    public void setWml190autoSaveList(List<LabelValueBean> wml190autoSaveList) {
        wml190autoSaveList__ = wml190autoSaveList;
    }

    /**
     * <p>wml190authMethod を取得します。
     * @return wml190authMethod
     * @see jp.groupsession.v2.wml.wml190.Wml190Form#wml190authMethod__
     */
    public int getWml190authMethod() {
        return wml190authMethod__;
    }

    /**
     * <p>wml190authMethod をセットします。
     * @param wml190authMethod wml190authMethod
     * @see jp.groupsession.v2.wml.wml190.Wml190Form#wml190authMethod__
     */
    public void setWml190authMethod(int wml190authMethod) {
        wml190authMethod__ = wml190authMethod;
    }

    /**
     * <p>wml190provider を取得します。
     * @return wml190provider
     * @see jp.groupsession.v2.wml.wml190.Wml190Form#wml190provider__
     */
    public int getWml190provider() {
        return wml190provider__;
    }

    /**
     * <p>wml190provider をセットします。
     * @param wml190provider wml190provider
     * @see jp.groupsession.v2.wml.wml190.Wml190Form#wml190provider__
     */
    public void setWml190provider(int wml190provider) {
        wml190provider__ = wml190provider;
    }

    /**
     * <p>wml190authAccount を取得します。
     * @return wml190authAccount
     * @see jp.groupsession.v2.wml.wml190.Wml190Form#wml190authAccount__
     */
    public String getWml190authAccount() {
        return wml190authAccount__;
    }

    /**
     * <p>wml190authAccount をセットします。
     * @param wml190authAccount wml190authAccount
     * @see jp.groupsession.v2.wml.wml190.Wml190Form#wml190authAccount__
     */
    public void setWml190authAccount(String wml190authAccount) {
        wml190authAccount__ = wml190authAccount;
    }
    /**
     * <p>wml190oauthCompFlg を取得します。
     * @return wml190oauthCompFlg
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190oauthCompFlg__
     */
    public boolean isWml190oauthCompFlg() {
        return wml190oauthCompFlg__;
    }
    /**
     * <p>wml190oauthCompFlg をセットします。
     * @param wml190oauthCompFlg wml190oauthCompFlg
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190oauthCompFlg__
     */
    public void setWml190oauthCompFlg(boolean wml190oauthCompFlg) {
        wml190oauthCompFlg__ = wml190oauthCompFlg;
    }
    /**
     * <p>wml190cotSid を取得します。
     * @return wml190cotSid
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190cotSid__
     */
    public int getWml190cotSid() {
        return wml190cotSid__;
    }
    /**
     * <p>wml190cotSid をセットします。
     * @param wml190cotSid wml190cotSid
     * @see jp.groupsession.v2.wml.wml190.Wml190ParamModel#wml190cotSid__
     */
    public void setWml190cotSid(int wml190cotSid) {
        wml190cotSid__ = wml190cotSid;
    }
}