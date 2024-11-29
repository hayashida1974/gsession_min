package jp.groupsession.v2.rng;

import java.sql.Connection;

import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] jp.groupsession.v2.rng.IRingiListenerを実装したクラスで使用するパラメータ格納Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiListenerModel extends AbstractModel {

    /** コネクション */
    private Connection con__ = null;
    /** MlCountMtController */
    private MlCountMtController cntCon__ = null;
    /** アプリケーションルートパス */
    private String appRootPath__ = null;
    /** 稟議SID */
    private int rngSid__ = 0;
    /** ユーザSID */
    private int userSid__ = 0;
    /** PluginConfig */
    private PluginConfig pluginConfig__ = null;
    /** ショートメールプラグイン有効フラグ */
    private boolean smailPluginFlg__ = true;
    /** URL */
    private String rngUrl__ = null;
    /** 稟議管理者設定 ショートメール通知設定 */
    private int smlNtf__ = RngConst.RAR_SML_NTF_USER;

    /**
     * <p>appRootPath を取得します。
     * @return appRootPath
     */
    public String getAppRootPath() {
        return appRootPath__;
    }
    /**
     * <p>appRootPath をセットします。
     * @param appRootPath appRootPath
     */
    public void setAppRootPath(String appRootPath) {
        appRootPath__ = appRootPath;
    }
    /**
     * <p>cntCon を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * <p>cntCon をセットします。
     * @param cntCon cntCon
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }
    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>rngSid を取得します。
     * @return rngSid
     */
    public int getRngSid() {
        return rngSid__;
    }
    /**
     * <p>rngSid をセットします。
     * @param rngSid rngSid
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>pluginConfig を取得します。
     * @return pluginConfig
     */
    public PluginConfig getPluginConfig() {
        return pluginConfig__;
    }
    /**
     * <p>pluginConfig をセットします。
     * @param pluginConfig pluginConfig
     */
    public void setPluginConfig(PluginConfig pluginConfig) {
        pluginConfig__ = pluginConfig;
    }
    /**
     * <p>smailPluginFlg を取得します。
     * @return smailPluginFlg
     */
    public boolean isSmailPluginFlg() {
        return smailPluginFlg__;
    }
    /**
     * <p>smailPluginFlg をセットします。
     * @param smailPluginFlg smailPluginFlg
     */
    public void setSmailPluginFlg(boolean smailPluginFlg) {
        smailPluginFlg__ = smailPluginFlg;
    }
    /**
     * <p>rngUrl を取得します。
     * @return rngUrl
     */
    public String getRngUrl() {
        return rngUrl__;
    }
    /**
     * <p>rngUrl をセットします。
     * @param rngUrl rngUrl
     */
    public void setRngUrl(String rngUrl) {
        rngUrl__ = rngUrl;
    }
    /**
     * <p>smlNtf を取得します。
     * @return smlNtf
     */
    public int getSmlNtf() {
        return smlNtf__;
    }
    /**
     * <p>smlNtf をセットします。
     * @param smlNtf smlNtf
     */
    public void setSmlNtf(int smlNtf) {
        smlNtf__ = smlNtf;
    }
}
