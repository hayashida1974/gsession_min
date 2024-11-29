package jp.groupsession.v2.adr.adr241.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <p>アドレス帳 会社選択画面 担当者一覧の画面表示情報を保持するModel
 */
public class Adr241AddressModel extends AbstractModel {

    /** アドレス帳SID */
    private int addressSid__ = 0;
    /** 会社SID */
    private int companySid__ = 0;
    /** 会社拠点SID */
    private int companyBaseSid__ = 0;
    /** アドレス帳名称 */
    private String addressName__ = null;
    /**
     * <p>addressName を取得します。
     * @return addressName
     */
    public String getAddressName() {
        return addressName__;
    }
    /**
     * <p>addressName をセットします。
     * @param addressName addressName
     */
    public void setAddressName(String addressName) {
        addressName__ = addressName;
    }
    /**
     * <p>addressSid を取得します。
     * @return addressSid
     */
    public int getAddressSid() {
        return addressSid__;
    }
    /**
     * <p>addressSid をセットします。
     * @param addressSid addressSid
     */
    public void setAddressSid(int addressSid) {
        addressSid__ = addressSid;
    }
    /**
     * <p>companyBaseSid を取得します。
     * @return companyBaseSid
     */
    public int getCompanyBaseSid() {
        return companyBaseSid__;
    }
    /**
     * <p>companyBaseSid をセットします。
     * @param companyBaseSid companyBaseSid
     */
    public void setCompanyBaseSid(int companyBaseSid) {
        companyBaseSid__ = companyBaseSid;
    }
    /**
     * <p>companySid を取得します。
     * @return companySid
     */
    public int getCompanySid() {
        return companySid__;
    }
    /**
     * <p>companySid をセットします。
     * @param companySid companySid
     */
    public void setCompanySid(int companySid) {
        companySid__ = companySid;
    }

    /**
     * <br>[機  能] アドレス情報のIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ID
     */
    public String getAddressId() {
        return String.valueOf(addressSid__);
    }
}
