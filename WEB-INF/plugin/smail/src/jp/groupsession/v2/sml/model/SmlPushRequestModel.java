package jp.groupsession.v2.sml.model;

import jp.groupsession.v2.cmn.model.PushRequestModel;


/**
 * <br>[機  能] ショートメール用のPUSH通知情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlPushRequestModel extends PushRequestModel {

    /** アカウントSID */
    private int accountSid__;

    public int getAccountSid() {
        return accountSid__;
    }
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
}
