package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] メール情報API パラメータモデル
 * <br>[解  説] DELETE
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsMailboxesMailsDeleteParamModel
           extends SmlAccountsMailboxesMailsGetParamModel {
    /** メールSID */
    private int mailSid__;
    /**
     * <p>mailSid を取得します。
     * @return mailSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.send.mails.
     *       SmlAccountsLabelsDelParamModel#mailSid__
     */
    public int getMailSid() {
        return mailSid__;
    }

    /**
     * <p>mailSid をセットします。
     * @param mailSid mailSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.send.mails.
     *       SmlAccountsLabelsDelParamModel#mailSid__
     */
    public void setMailSid(int mailSid) {
        mailSid__ = mailSid;
    }
}
