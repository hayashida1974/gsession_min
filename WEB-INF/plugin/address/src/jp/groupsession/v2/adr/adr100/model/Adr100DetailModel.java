package jp.groupsession.v2.adr.adr100.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス帳 会社一覧画面の検索結果情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr100DetailModel extends AbstractModel {

    /** 会社帳SID */
    private int acoSid__ = 0;

    /** 会社名 */
    private String companyName__ = null;
    /** 企業コード */
    private String companyCode__ = null;
    /** 業種名 */
    private String industryName__ = null;
    /** 備考 */
    private String companyBiko__ = null;

    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public int getAcoSid() {
        return acoSid__;
    }
    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }
    /**
     * <p>companyBiko を取得します。
     * @return companyBiko
     */
    public String getCompanyBiko() {
        return companyBiko__;
    }
    /**
     * <p>companyBiko をセットします。
     * @param companyBiko companyBiko
     */
    public void setCompanyBiko(String companyBiko) {
        companyBiko__ = companyBiko;
    }
    /**
     * <p>companyName を取得します。
     * @return companyName
     */
    public String getCompanyName() {
        return companyName__;
    }
    /**
     * <p>companyName をセットします。
     * @param companyName companyName
     */
    public void setCompanyName(String companyName) {
        companyName__ = companyName;
    }
    /**
     * <p>industryName を取得します。
     * @return industryName
     */
    public String getIndustryName() {
        return industryName__;
    }
    /**
     * <p>industryName をセットします。
     * @param industryName industryName
     */
    public void setIndustryName(String industryName) {
        industryName__ = industryName;
    }
    /**
     * <p>companyCode を取得します。
     * @return companyCode
     */
    public String getCompanyCode() {
        return companyCode__;
    }
    /**
     * <p>companyCode をセットします。
     * @param companyCode companyCode
     */
    public void setCompanyCode(String companyCode) {
        companyCode__ = companyCode;
    }
}