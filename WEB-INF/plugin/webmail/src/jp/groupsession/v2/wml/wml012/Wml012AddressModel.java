package jp.groupsession.v2.wml.wml012;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール 送信メール確認(ポップアップ)画面の送信先(宛先、CC、BCC)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml012AddressModel extends AbstractModel {
    /** ドメイン種別 最小値 */
    public static final int MIN_DOMAINTYPE  = 1;
    /** ドメイン種別 最大値 */
    public static final int MAX_DOMAINTYPE  = 10;

    /** メールアドレス */
    private String address__ = null;
    /** ユーザ名 */
    private String user__ = null;
    /** ドメイン */
    private String domain__ = null;
    /** ドメイン 最後尾の文字列 */
    private String domainEnd__ = "";
    /** ドメイン種別(1 ～ 10) */
    private String domainType__ = "1";

    /** アドレス前 文字列 */
    private String addressPrev__ = null;
    /** アドレス後 文字列 */
    private String addressAfter__ = null;

    /**
     * <p>address を取得します。
     * @return address
     */
    public String getAddress() {
        return address__;
    }
    /**
     * <p>address をセットします。
     * @param address address
     */
    public void setAddress(String address) {
        address__ = address;
    }
    /**
     * <p>user を取得します。
     * @return user
     */
    public String getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(String user) {
        user__ = user;
    }
    /**
     * <p>domain を取得します。
     * @return domain
     */
    public String getDomain() {
        return domain__;
    }
    /**
     * <p>domain をセットします。
     * @param domain domain
     */
    public void setDomain(String domain) {
        domain__ = domain;
    }
    /**
     * <p>domainEnd を取得します。
     * @return domainEnd
     */
    public String getDomainEnd() {
        return domainEnd__;
    }
    /**
     * <p>domainEnd をセットします。
     * @param domainEnd domainEnd
     */
    public void setDomainEnd(String domainEnd) {
        domainEnd__ = domainEnd;
    }
    /**
     * <p>domainType を取得します。
     * @return domainType
     */
    public String getDomainType() {
        return domainType__;
    }
    /**
     * <p>domainType をセットします。
     * @param domainType domainType
     */
    public void setDomainType(String domainType) {
        domainType__ = domainType;
    }
    /**
     * <p>addressPrev を取得します。
     * @return addressPrev
     * @see jp.groupsession.v2.wml.wml012.Wml012AddressModel#addressPrev__
     */
    public String getAddressPrev() {
        return addressPrev__;
    }
    /**
     * <p>addressPrev をセットします。
     * @param addressPrev addressPrev
     * @see jp.groupsession.v2.wml.wml012.Wml012AddressModel#addressPrev__
     */
    public void setAddressPrev(String addressPrev) {
        addressPrev__ = addressPrev;
    }
    /**
     * <p>addressAfter を取得します。
     * @return addressAfter
     * @see jp.groupsession.v2.wml.wml012.Wml012AddressModel#addressAfter__
     */
    public String getAddressAfter() {
        return addressAfter__;
    }
    /**
     * <p>addressAfter をセットします。
     * @param addressAfter addressAfter
     * @see jp.groupsession.v2.wml.wml012.Wml012AddressModel#addressAfter__
     */
    public void setAddressAfter(String addressAfter) {
        addressAfter__ = addressAfter;
    }
}
