package jp.groupsession.v2.sml.restapi.accounts.labels;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] ラベル情報API パラメータモデル
 * <br>[解  説] DELETE
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsLabelsDelParamModel  {
    /** アカウントSID */
    private int accountSid__;
    /** ラベルSID */
    private int labelSid__;

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
     * <p>labelSid を取得します。
     * @return labelSid
     * @see jp.groupsession.v2.sml.restapi.accounts.labels.SmlAccountsLabelsDelParamModel#labelSid__
     */
    public int getLabelSid() {
        return labelSid__;
    }

    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     * @see jp.groupsession.v2.sml.restapi.accounts.labels.SmlAccountsLabelsDelParamModel#labelSid__
     */
    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }
}
