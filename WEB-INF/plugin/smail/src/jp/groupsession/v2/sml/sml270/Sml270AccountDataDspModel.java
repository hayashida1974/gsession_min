package jp.groupsession.v2.sml.sml270;

import java.io.Serializable;

/**
 * <br>[機  能] アカウント情報表示用モデル
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Sml270AccountDataDspModel implements Serializable {

    /** アカウントSID */
    private int accountSid__ = 0;
    /** アカウント名 */
    private String accountName__ = null;
    /** アカウント種別 */
    private int accountType__ = 0;
    /** メールアドレス */
    private String accountAddress__ = null;
    /** 備考 */
    private String accountBiko__ = null;
    /** 並び順 */
    private int accountSort__;
    /** 表示順(画面用) */
    private String acValue__ = null;
    /** ユーザ有効フラグ */
    private int usrUkoFlg__ = 0;

    /**
     * <p>accountAddress を取得します。
     * @return accountAddress
     */
    public String getAccountAddress() {
        return accountAddress__;
    }
    /**
     * <p>accountAddress をセットします。
     * @param accountAddress accountAddress
     */
    public void setAccountAddress(String accountAddress) {
        accountAddress__ = accountAddress;
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
     * <p>accountSort を取得します。
     * @return accountSort
     */
    public int getAccountSort() {
        return accountSort__;
    }
    /**
     * <p>accountSort をセットします。
     * @param accountSort accountSort
     */
    public void setAccountSort(int accountSort) {
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
     * <p>acValue を取得します。
     * @return acValue
     */
    public String getAcValue() {
        return acValue__;
    }
    /**
     * <p>acValue をセットします。
     * @param acValue acValue
     */
    public void setAcValue(String acValue) {
        acValue__ = acValue;
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
