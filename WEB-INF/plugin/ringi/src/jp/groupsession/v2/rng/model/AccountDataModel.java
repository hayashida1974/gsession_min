package jp.groupsession.v2.rng.model;


import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アカウント情報の取得条件を格納したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AccountDataModel extends AbstractModel {

    /** アカウントSID */
    private int accountSid__ = 0;
    /** アカウント名 */
    private String accountName__ = null;
    /** アカウント受信件数*/
    private int accountCount__ = 0;
    /** ユーザ有効フラグ */
    private int usrUkoFlg__ = 0;

    /**
     * <p>accountName を取得します。
     * @return accountName
     */
    public String getAccountName() {
        return accountName__;
    }
    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
    }
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     */
    public int getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>accountCount を取得します。
     * @return accountCount
     */
    public int getAccountCount() {
        return accountCount__;
    }
    /**
     * <p>accountCount をセットします。
     * @param accountCount accountCount
     */
    public void setAccountCount(int accountCount) {
        accountCount__ = accountCount;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
}
