package jp.groupsession.v2.sml.restapi.accounts.configs;
/**
 *
 * <br>[機  能] アカウント設定情報API リザルトモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsConfigsGetResultModel  {

    /** アカウントSID */
    private int sid__;
    /** アカウント名 */
    private String name__ = "";
    /** ユーザ削除フラグ */
    private int userDeleteFlg__ = -1;
    /** ユーザログイン停止フラグ */
    private int loginStopFlg__ = -1;
    /** デフォルトアカウントフラグ */
    private int defaultFlg__ = 0;
    /** 未読メール件数 */
    private int midokuCount__ = 0;
    /** アカウント設定情報 */
    private SmlAccountsConfigsConfigInfoModel configInfo__;
    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.SmlAccountsConfigsGetResultModel#sid__
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.SmlAccountsConfigsGetResultModel#sid__
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.SmlAccountsConfigsGetResultModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.SmlAccountsConfigsGetResultModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>userDeleteFlg を取得します。
     * @return userDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#userDeleteFlg__
     */
    public int getUserDeleteFlg() {
        return userDeleteFlg__;
    }
    /**
     * <p>userDeleteFlg をセットします。
     * @param userDeleteFlg userDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#userDeleteFlg__
     */
    public void setUserDeleteFlg(int userDeleteFlg) {
        userDeleteFlg__ = userDeleteFlg;
    }
    /**
     * <p>loginStopFlg を取得します。
     * @return loginStopFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#loginStopFlg__
     */
    public int getLoginStopFlg() {
        return loginStopFlg__;
    }
    /**
     * <p>loginStopFlg をセットします。
     * @param loginStopFlg loginStopFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#loginStopFlg__
     */
    public void setLoginStopFlg(int loginStopFlg) {
        loginStopFlg__ = loginStopFlg;
    }
    /**
     * <p>defaultFlg を取得します。
     * @return defaultFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#defaultFlg__
     */
    public int getDefaultFlg() {
        return defaultFlg__;
    }
    /**
     * <p>defaultFlg をセットします。
     * @param defaultFlg defaultFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#defaultFlg__
     */
    public void setDefaultFlg(int defaultFlg) {
        defaultFlg__ = defaultFlg;
    }
    /**
     * <p>midokuCount を取得します。
     * @return midokuCount
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#midokuCount__
     */
    public int getMidokuCount() {
        return midokuCount__;
    }
    /**
     * <p>midokuCount をセットします。
     * @param midokuCount midokuCount
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#midokuCount__
     */
    public void setMidokuCount(int midokuCount) {
        midokuCount__ = midokuCount;
    }
    /**
     * <p>configInfo を取得します。
     * @return configInfo
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#configInfo__
     */
    public SmlAccountsConfigsConfigInfoModel getConfigInfo() {
        return configInfo__;
    }
    /**
     * <p>configInfo をセットします。
     * @param configInfo configInfo
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsGetResultModel#configInfo__
     */
    public void setConfigInfo(SmlAccountsConfigsConfigInfoModel configInfo) {
        configInfo__ = configInfo;
    }
}
