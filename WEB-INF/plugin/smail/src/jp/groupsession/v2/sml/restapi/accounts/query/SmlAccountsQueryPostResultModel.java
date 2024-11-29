package jp.groupsession.v2.sml.restapi.accounts.query;
/**
 *
 * <br>[機  能] アカウントリスト情報API リザルトモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsQueryPostResultModel  {

    /** アカウントSID */
    private int sid__;
    /** アカウント名 */
    private String name__ = "";
    /** ユーザ削除フラグ */
    private int userDeleteFlg__ = -1;
    /** ユーザログイン停止フラグ */
    private int loginStopFlg__ = -1;
    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.sml.restapi.accounts.query.SmlAccountsQueryPostResultModel#sid__
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.sml.restapi.accounts.query.SmlAccountsQueryPostResultModel#sid__
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.sml.restapi.accounts.query.SmlAccountsQueryPostResultModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.sml.restapi.accounts.query.SmlAccountsQueryPostResultModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>userDeleteFlg を取得します。
     * @return userDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *      SmlAccountsQueryPostResultModel#userDeleteFlg__
     */
    public int getUserDeleteFlg() {
        return userDeleteFlg__;
    }
    /**
     * <p>userDeleteFlg をセットします。
     * @param userDeleteFlg userDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostResultModel#userDeleteFlg__
     */
    public void setUserDeleteFlg(int userDeleteFlg) {
        userDeleteFlg__ = userDeleteFlg;
    }
    /**
     * <p>loginStopFlg を取得します。
     * @return loginStopFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostResultModel#loginStopFlg__
     */
    public int getLoginStopFlg() {
        return loginStopFlg__;
    }
    /**
     * <p>loginStopFlg をセットします。
     * @param loginStopFlg loginStopFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostResultModel#loginStopFlg__
     */
    public void setLoginStopFlg(int loginStopFlg) {
        loginStopFlg__ = loginStopFlg;
    }
}
