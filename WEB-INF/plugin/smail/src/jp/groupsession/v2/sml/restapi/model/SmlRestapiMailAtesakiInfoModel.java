package jp.groupsession.v2.sml.restapi.model;

/**
 *
 * <br>[機  能] メール情報 送信先情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlRestapiMailAtesakiInfoModel {
    /** 宛先区分 */
    private int type__;
    /** 宛先アカウントSID */
    private int accountSid__;
    /** 宛先アカウント名 */
    private String name__;
    /** 宛先ユーザ削除フラグ */
    private int userDeleteFlg__;
    /** 宛先ユーザログイン停止フラグ */
    private int loginStopFlg__;

    /**
     * <p>type を取得します。
     * @return type
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#type__
     */
    public int getType() {
        return type__;
    }
    /**
     * <p>type をセットします。
     * @param type type
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#type__
     */
    public void setType(int type) {
        type__ = type;
    }
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#accountSid__
     */
    public int getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#accountSid__
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>userDeleteFlg を取得します。
     * @return userDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#userDeleteFlg__
     */
    public int getUserDeleteFlg() {
        return userDeleteFlg__;
    }
    /**
     * <p>userDeleteFlg をセットします。
     * @param userDeleteFlg userDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#userDeleteFlg__
     */
    public void setUserDeleteFlg(int userDeleteFlg) {
        userDeleteFlg__ = userDeleteFlg;
    }
    /**
     * <p>loginStopFlg を取得します。
     * @return loginStopFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#loginStopFlg__
     */
    public int getLoginStopFlg() {
        return loginStopFlg__;
    }
    /**
     * <p>loginStopFlg をセットします。
     * @param loginStopFlg loginStopFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel#loginStopFlg__
     */
    public void setLoginStopFlg(int loginStopFlg) {
        loginStopFlg__ = loginStopFlg;
    }
}
