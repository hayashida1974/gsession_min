package jp.groupsession.v2.sml.restapi.accounts.count;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] メール件数情報API パラメータモデル
 * <br>[解  説] GET
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsCountGetParamModel {
    /** アカウントSID */
    private int accountSid__ = -1;

    /**
     * <p>accountSid を取得します。
     * @return accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.count.SmlAccountsCountGetParamModel#accountSid__
     */
    public int getAccountSid() {
        return accountSid__;
    }

    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.count.SmlAccountsCountGetParamModel#accountSid__
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
}
