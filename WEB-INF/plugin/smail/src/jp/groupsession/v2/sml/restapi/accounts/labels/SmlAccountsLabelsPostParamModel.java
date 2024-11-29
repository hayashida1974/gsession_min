package jp.groupsession.v2.sml.restapi.accounts.labels;

import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
/**
 *
 * <br>[機  能] ラベル情報API パラメータモデル
 * <br>[解  説] POST
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsLabelsPostParamModel  {
    /** アカウントSID */
    private int accountSid__;
    /** ラベル名 */
    @NotBlank
    @MaxLength(100)
    @TextField
    private String name__;
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
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.sml.restapi.accounts.labels.SmlAccountsLabelsPostParamModel#name__
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.sml.restapi.accounts.labels.SmlAccountsLabelsPostParamModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
}
