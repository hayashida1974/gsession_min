package jp.groupsession.v2.cmn.cmn270;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] OAuth認証画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn270ParamModel extends AbstractParamModel {
    /** 呼び出し元プラグイン */
    private String cmn270PluginId__ = null;
    /** 呼び出し元画面ID */
    private String cmn270ParentId__ = null;
    /** 認証情報SID */
    private int cmn270AuthSid__ = 0;
    /** メールアドレス */
    private String cmn270MailAddress__ = null;
    /** 認証終了フラグ */
    private int cmn270AuthComplete__ = 0;
    /** code */
    private String code__ = null;
    /** state */
    private String state__ = null;

    /** OAuth認証情報トークンSID */
    private int cotSid__ = 0;
    /** OAuth認証情報トークンSID 設定先パラメータ名(親画面) */
    private String cmn270cotSidParamName__ = null;

    /**
     * <p>cmn270PluginId を取得します。
     * @return cmn270PluginId
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270ParamModel#cmn270PluginId__
     */
    public String getCmn270PluginId() {
        return cmn270PluginId__;
    }
    /**
     * <p>cmn270PluginId をセットします。
     * @param cmn270PluginId cmn270PluginId
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270ParamModel#cmn270PluginId__
     */
    public void setCmn270PluginId(String cmn270PluginId) {
        cmn270PluginId__ = cmn270PluginId;
    }
    /**
     * <p>cmn270ParentId を取得します。
     * @return cmn270ParentId
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270ParentId__
     */
    public String getCmn270ParentId() {
        return cmn270ParentId__;
    }
    /**
     * <p>cmn270ParentId をセットします。
     * @param cmn270ParentId cmn270ParentId
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270ParentId__
     */
    public void setCmn270ParentId(String cmn270ParentId) {
        cmn270ParentId__ = cmn270ParentId;
    }
    /**
     * <p>cmn270AuthSid を取得します。
     * @return cmn270AuthSid
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthSid__
     */
    public int getCmn270AuthSid() {
        return cmn270AuthSid__;
    }
    /**
     * <p>cmn270AuthSid をセットします。
     * @param cmn270AuthSid cmn270AuthSid
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthSid__
     */
    public void setCmn270AuthSid(int cmn270AuthSid) {
        cmn270AuthSid__ = cmn270AuthSid;
    }
    /**
     * <p>cmn270MailAddress を取得します。
     * @return cmn270MailAddress
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270MailAddress__
     */
    public String getCmn270MailAddress() {
        return cmn270MailAddress__;
    }
    /**
     * <p>cmn270MailAddress をセットします。
     * @param cmn270MailAddress cmn270MailAddress
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270MailAddress__
     */
    public void setCmn270MailAddress(String cmn270MailAddress) {
        cmn270MailAddress__ = cmn270MailAddress;
    }
    /**
     * <p>cmn270AuthComplete を取得します。
     * @return cmn270AuthComplete
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthComplete__
     */
    public int getCmn270AuthComplete() {
        return cmn270AuthComplete__;
    }
    /**
     * <p>cmn270AuthComplete をセットします。
     * @param cmn270AuthComplete cmn270AuthComplete
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthComplete__
     */
    public void setCmn270AuthComplete(int cmn270AuthComplete) {
        cmn270AuthComplete__ = cmn270AuthComplete;
    }
    /**
     * <p>code を取得します。
     * @return code
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#code__
     */
    public String getCode() {
        return code__;
    }
    /**
     * <p>code をセットします。
     * @param code code
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#code__
     */
    public void setCode(String code) {
        code__ = code;
    }
    /**
     * <p>state を取得します。
     * @return state
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#state__
     */
    public String getState() {
        return state__;
    }
    /**
     * <p>state をセットします。
     * @param state state
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#state__
     */
    public void setState(String state) {
        state__ = state;
    }
    /**
     * <p>cotSid を取得します。
     * @return cotSid
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270ParamModel#cotSid__
     */
    public int getCotSid() {
        return cotSid__;
    }
    /**
     * <p>cotSid をセットします。
     * @param cotSid cotSid
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270ParamModel#cotSid__
     */
    public void setCotSid(int cotSid) {
        cotSid__ = cotSid;
    }
    /**
     * <p>cmn270cotSidParamName を取得します。
     * @return cmn270cotSidParamName
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270ParamModel#cmn270cotSidParamName__
     */
    public String getCmn270cotSidParamName() {
        return cmn270cotSidParamName__;
    }
    /**
     * <p>cmn270cotSidParamName をセットします。
     * @param cmn270cotSidParamName cmn270cotSidParamName
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270ParamModel#cmn270cotSidParamName__
     */
    public void setCmn270cotSidParamName(String cmn270cotSidParamName) {
        cmn270cotSidParamName__ = cmn270cotSidParamName;
    }
}
