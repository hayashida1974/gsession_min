package jp.groupsession.v2.wml.restapi.accounts.labels;

import jp.groupsession.v2.restapi.response.annotation.ResponceModel;

/**
 * <br>[機  能] WEBメール ラベル一覧取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ResponceModel(targetField = {
    "sid",
    "name"
})
public class WmlAccountsLabelsResultModel {

    /** ラベルSID */
    private int sid__ = -1;
    /** ラベル名称 */
    private String name__ = "";

    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccountsLabelsResultModel#sid__
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccountsLabelsResultModel#sid__
     */
    public void setSid(int sid) {
        sid__ = sid;
    }

    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccountsLabelsResultModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccountsLabelsResultModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }

    public boolean canDisplayName() {
        return true;
    }
    
}
