package jp.groupsession.v2.sml.restapi.accounts.query;

import jp.groupsession.v2.restapi.parameter.QueryParamModelBase;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
/**
 *
 * <br>[機  能] アカウントリスト情報API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsQueryPostParamModel  extends QueryParamModelBase {
    /** グループID */
    private String groupId__;
    /** 代表アカウント取得フラグ */
    @Selectable({"0", "1"})
    private int sharedOnlyFlg__ = 0;
    /** マイグループSID */
    private int myGroupSid__ = -1;
    /** 指定アカウントSID */
    private int[] accountSidArray__;
    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see jp.groupsession.v2.sml.restapi.accounts.query.SmlAccountsQueryPostParamModel#groupId__
     */
    public String getGroupId() {
        return groupId__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see jp.groupsession.v2.sml.restapi.accounts.query.SmlAccountsQueryPostParamModel#groupId__
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
    /**
     * <p>sharedOnlyFlg を取得します。
     * @return sharedOnlyFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostParamModel#sharedOnlyFlg__
     */
    public int getSharedOnlyFlg() {
        return sharedOnlyFlg__;
    }
    /**
     * <p>sharedOnlyFlg をセットします。
     * @param sharedOnlyFlg sharedOnlyFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostParamModel#sharedOnlyFlg__
     */
    public void setSharedOnlyFlg(int sharedOnlyFlg) {
        sharedOnlyFlg__ = sharedOnlyFlg;
    }
    /**
     * <p>myGroupSid を取得します。
     * @return myGroupSid
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostParamModel#myGroupSid__
     */
    public int getMyGroupSid() {
        return myGroupSid__;
    }
    /**
     * <p>myGroupSid をセットします。
     * @param myGroupSid myGroupSid
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostParamModel#myGroupSid__
     */
    public void setMyGroupSid(int myGroupSid) {
        myGroupSid__ = myGroupSid;
    }
    /**
     * <p>accountSidArray を取得します。
     * @return accountSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostParamModel#accountSidArray__
     */
    public int[] getAccountSidArray() {
        return accountSidArray__;
    }
    /**
     * <p>accountSidArray をセットします。
     * @param accountSidArray accountSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.query.
     *       SmlAccountsQueryPostParamModel#accountSidArray__
     */
    public void setAccountSidArray(int[] accountSidArray) {
        accountSidArray__ = accountSidArray;
    }
}
