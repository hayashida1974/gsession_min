package jp.groupsession.v2.sml.restapi.accounts.templates;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] 個人ひな形情報API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsTemplatesGetParamModel  {
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
