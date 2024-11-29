package jp.groupsession.v2.wml.wml030;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;

/**
 * <br>[機  能] WEBメール アカウントマネージャー画面 エクスポート情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml030ExportModel extends AbstractModel {
    /** アカウント情報 */
    WmlAccountModel accountData__ = null;
    /** 使用者ユーザID / グループID */
    private List<String> accountUserList__ = null;
    /** 代理人ユーザID / グループID */
    private List<String> accountProxyUserList__ = null;
    /**
     * <p>accountData を取得します。
     * @return accountData
     */
    public WmlAccountModel getAccountData() {
        return accountData__;
    }
    /**
     * <p>accountData をセットします。
     * @param accountData accountData
     */
    public void setAccountData(WmlAccountModel accountData) {
        accountData__ = accountData;
    }
    /**
     * <p>accountUserList を取得します。
     * @return accountUserList
     */
    public List<String> getAccountUserList() {
        return accountUserList__;
    }
    /**
     * <p>accountUserList をセットします。
     * @param accountUserList accountUserList
     */
    public void setAccountUserList(List<String> accountUserList) {
        accountUserList__ = accountUserList;
    }
    /**
     * <p>accountProxyUserList を取得します。
     * @return accountProxyUserList
     */
    public List<String> getAccountProxyUserList() {
        return accountProxyUserList__;
    }
    /**
     * <p>accountProxyUserList をセットします。
     * @param accountProxyUserList accountProxyUserList
     */
    public void setAccountProxyUserList(List<String> accountProxyUserList) {
        accountProxyUserList__ = accountProxyUserList;
    }
}
