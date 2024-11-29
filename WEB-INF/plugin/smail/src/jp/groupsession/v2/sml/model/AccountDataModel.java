package jp.groupsession.v2.sml.model;


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
    /** アカウント種別 */
    private int accountType__;
    /** 備考 */
    private String accountBiko__ = null;
    /** 並び順 */
    private long accountSort__;

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
     * <p>accountBiko を取得します。
     * @return accountBiko
     */
    public String getAccountBiko() {
        return accountBiko__;
    }
    /**
     * <p>accountBiko をセットします。
     * @param accountBiko accountBiko
     */
    public void setAccountBiko(String accountBiko) {
        accountBiko__ = accountBiko;
    }
    /**
     * <p>accountSort を取得します。
     * @return accountSort
     */
    public long getAccountSort() {
        return accountSort__;
    }
    /**
     * <p>accountSort をセットします。
     * @param accountSort accountSort
     */
    public void setAccountSort(long accountSort) {
        accountSort__ = accountSort;
    }
    /**
     * <p>accountType を取得します。
     * @return accountType
     */
    public int getAccountType() {
        return accountType__;
    }
    /**
     * <p>accountType をセットします。
     * @param accountType accountType
     */
    public void setAccountType(int accountType) {
        accountType__ = accountType;
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
