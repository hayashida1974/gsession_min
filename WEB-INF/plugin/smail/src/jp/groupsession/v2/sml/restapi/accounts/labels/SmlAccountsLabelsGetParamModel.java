package jp.groupsession.v2.sml.restapi.accounts.labels;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] ラベル情報API パラメータモデル
 * <br>[解  説] GET
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsLabelsGetParamModel  {
    /** アカウントSID */
    private int accountSid__;

    /**
     * <p>accountSid を取得します。
     * @return accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.templates.
     *       SmlAccountsTemplatesParamModel#accountSid__
     */
    public int getAccountSid() {
        return accountSid__;
    }

    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.templates.
     *       SmlAccountsTemplatesParamModel#accountSid__
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
}
