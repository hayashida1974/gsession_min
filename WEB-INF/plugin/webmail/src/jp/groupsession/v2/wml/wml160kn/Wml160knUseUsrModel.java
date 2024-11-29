package jp.groupsession.v2.wml.wml160kn;

import java.util.List;

import jp.groupsession.v2.wml.wml160.WebmailCsvModel;

/**
 * <br>[機  能] WEBメール アカウントの使用者、代理人に関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160knUseUsrModel {

    /** アカウント名 */
    private String accountName__ = null;
    /** 区分(ユーザ or グループ) */
    private int userKbn__ = WebmailCsvModel.USERKBN_USER;
    /** 使用ユーザ名 */
    private List<Wml160knUserDataModel> userNameList__ = null;
    /** 代理人名 */
    private List<Wml160knUserDataModel> proxyUserNameList__ = null;

    /**
     * <p>accountName を取得します。
     * @return accountName
     */
    public String getAccountName() {
        return accountName__;
    }

    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
    }
    /**
     * <p>userKbn を取得します。
     * @return userKbn
     */
    public int getUserKbn() {
        return userKbn__;
    }
    /**
     * <p>userKbn をセットします。
     * @param userKbn userKbn
     */
    public void setUserKbn(int userKbn) {
        userKbn__ = userKbn;
    }
    /**
     * <p>userNameList を取得します。
     * @return userNameList
     */
    public List<Wml160knUserDataModel> getUserNameList() {
        return userNameList__;
    }

    /**
     * <p>userNameList をセットします。
     * @param userNameList userNameList
     */
    public void setUserNameList(List<Wml160knUserDataModel> userNameList) {
        userNameList__ = userNameList;
    }

    /**
     * <p>proxyUserNameList を取得します。
     * @return proxyUserNameList
     */
    public List<Wml160knUserDataModel> getProxyUserNameList() {
        return proxyUserNameList__;
    }

    /**
     * <p>proxyUserNameList をセットします。
     * @param proxyUserNameList proxyUserNameList
     */
    public void setProxyUserNameList(List<Wml160knUserDataModel> proxyUserNameList) {
        proxyUserNameList__ = proxyUserNameList;
    }
}
