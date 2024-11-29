package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 送信先パラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlAddressParamModel extends AbstractModel {
    /** 種別 ユーザ情報 */
    public static final int TYPE_USER = 0;
    /** 種別 アドレス帳 */
    public static final int TYPE_ADDRESS = 1;

    /** 役職フラグ（なし） */
    public static final int YAKUSYOKU_NOT_EXIST = 1;
    /** 役職フラグ（あり） */
    public static final int YAKUSYOKU_EXIST = 0;

    /** 会社情報フラグ（なし） */
    public static final int COMPANY_NOT_EXIST = 1;
    /** 会社情報フラグ（あり） */
    public static final int COMPANY_EXIST = 0;


    /** 送信先ID */
    private String destId__ = null;
    /** 種別 */
    private int type__ = 0;
    /** SID(ユーザSID or アドレス帳SID) */
    private int sid__ = 0;
    /** メール番号 */
    private int mailNo__ = 0;
    /** 名称 */
    private String name__ = null;
    /** 姓 */
    private String sei__ = null;
    /** 名 */
    private String mei__ = null;
    /** 姓カナ */
    private String seiKn__ = null;
    /** 名カナ */
    private String meiKn__ = null;
    /** メールアドレス(RESTAPI用) */
    private String mailAddressText__ = null;
    /** メールアドレス(PC用) */
    private String mailAddress__ = null;
    /** メールアドレス(送信先設定用) */
    private String sendMailAddress__ = null;
    /** 役職SID */
    private Integer yakusyokuSid__ = 0;
    /** 役職コード */
    private String yakusyokuId__ = null;
    /** 役職名 */
    private String yakusyoku__ = null;
    /** 役職表示順 */
    private Integer yakusyokuSort__ = 0;
    /** 役職フラグ（なし：1 あり：0） */
    private Integer yakusyokuFlg__ = YAKUSYOKU_NOT_EXIST;
    /** ログイン停止フラグ*/
    private int usrUkoFlg__ = 0;

    //アドレス帳情報
    /** 企業コード */
    private String acoId__ = null;
    /** 企業名 */
    private String acoName__ = null;
    /** 企業名カナ */
    private String acoNameKn__ = null;
    /** 会社拠点コード */
    private String abaId__ = null;
    /** 会社拠点名 */
    private String abaName__ = null;
    /** 会社拠点種別名 */
    private String abaType__ = null;
    /** 会社情報フラグ */
    private Integer acoFlg__ = COMPANY_NOT_EXIST;

    /**
     * <p>destId を取得します。
     * @return destId
     */
    public String getDestId() {
        return destId__;
    }
    /**
     * <p>destId をセットします。
     * @param destId destId
     */
    public void setDestId(String destId) {
        destId__ = destId;
    }
    /**
     * <p>type を取得します。
     * @return type
     */
    public int getType() {
        return type__;
    }
    /**
     * <p>type をセットします。
     * @param type type
     */
    public void setType(int type) {
        type__ = type;
    }
    /**
     * <p>sid を取得します。
     * @return sid
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     * <p>mailNo を取得します。
     * @return mailNo
     */
    public int getMailNo() {
        return mailNo__;
    }
    /**
     * <p>mailNo をセットします。
     * @param mailNo mailNo
     */
    public void setMailNo(int mailNo) {
        mailNo__ = mailNo;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>mailAddress を取得します。
     * @return mailAddress
     */
    public String getMailAddress() {
        return mailAddress__;
    }
    /**
     * <p>mailAddress をセットします。
     * @param mailAddress mailAddress
     */
    public void setMailAddress(String mailAddress) {
        mailAddress__ = mailAddress;
    }
    /**
     * <p>acoName を取得します。
     * @return acoName
     */
    public String getAcoName() {
        return acoName__;
    }
    /**
     * <p>acoName をセットします。
     * @param acoName acoName
     */
    public void setAcoName(String acoName) {
        acoName__ = acoName;
    }
    /**
     * <p>abaName を取得します。
     * @return abaName
     */
    public String getAbaName() {
        return abaName__;
    }
    /**
     * <p>abaName をセットします。
     * @param abaName abaName
     */
    public void setAbaName(String abaName) {
        abaName__ = abaName;
    }
    /**
     * <p>yakusyoku を取得します。
     * @return yakusyoku
     */
    public String getYakusyoku() {
        return yakusyoku__;
    }
    /**
     * <p>yakusyoku をセットします。
     * @param yakusyoku yakusyoku
     */
    public void setYakusyoku(String yakusyoku) {
        yakusyoku__ = yakusyoku;
    }
    /**
     * <p>sendMailAddress を取得します。
     * @return sendMailAddress
     */
    public String getSendMailAddress() {
        return sendMailAddress__;
    }
    /**
     * <p>sendMailAddress をセットします。
     * @param sendMailAddress sendMailAddress
     */
    public void setSendMailAddress(String sendMailAddress) {
        sendMailAddress__ = sendMailAddress;
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
    public String getSei() {
        return sei__;
    }
    public void setSei(String sei) {
        sei__ = sei;
    }
    public String getMei() {
        return mei__;
    }
    public void setMei(String mei) {
        mei__ = mei;
    }
    public String getSeiKn() {
        return seiKn__;
    }
    public void setSeiKn(String seiKn) {
        seiKn__ = seiKn;
    }
    public String getMeiKn() {
        return meiKn__;
    }
    public void setMeiKn(String meiKn) {
        meiKn__ = meiKn;
    }
    public Integer getYakusyokuSid() {
        return yakusyokuSid__;
    }
    public void setYakusyokuSid(Integer yakusyokuSid) {
        yakusyokuSid__ = yakusyokuSid;
    }
    public String getYakusyokuId() {
        return yakusyokuId__;
    }
    public void setYakusyokuId(String yakusyokuId) {
        yakusyokuId__ = yakusyokuId;
    }
    public String getAcoId() {
        return acoId__;
    }
    public void setAcoId(String acoId) {
        acoId__ = acoId;
    }
    public String getAbaId() {
        return abaId__;
    }
    public void setAbaId(String abaId) {
        abaId__ = abaId;
    }
    public String getAbaType() {
        return abaType__;
    }
    public void setAbaType(String abaType) {
        abaType__ = abaType;
    }
    public Integer getYakusyokuSort() {
        return yakusyokuSort__;
    }
    public void setYakusyokuSort(Integer yakusyokuSort) {
        yakusyokuSort__ = yakusyokuSort;
    }
    public Integer getYakusyokuFlg() {
        return yakusyokuFlg__;
    }
    public void setYakusyokuFlg(Integer yakusyokuFlg) {
        yakusyokuFlg__ = yakusyokuFlg;
    }
    public Integer getAcoFlg() {
        return acoFlg__;
    }
    public void setAcoFlg(Integer acoFlg) {
        acoFlg__ = acoFlg;
    }
    public String getAcoNameKn() {
        return acoNameKn__;
    }
    public void setAcoNameKn(String acoNameKn) {
        acoNameKn__ = acoNameKn;
    }
    public String getMailAddressText() {
        return mailAddressText__;
    }
    public void setMailAddressText(String mailAddressText) {
        mailAddressText__ = mailAddressText;
    }
}