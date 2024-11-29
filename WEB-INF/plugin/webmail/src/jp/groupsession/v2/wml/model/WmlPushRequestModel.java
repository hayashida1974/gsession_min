package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.model.PushRequestModel;

/**
 * <br>[機  能] WEBメール用のPUSH通知情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlPushRequestModel extends PushRequestModel {

    /** アカウントSID */
    private int accountSid__;

    public int getAccountSid() {
        return accountSid__;
    }
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
}
