package jp.groupsession.v2.cmn.model.base;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 言語情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnLangModel extends AbstractModel {

    /** LNG_SID mapping */
    private int lngSid__;
    /** LNG_NAME MAPPING */
    private String lngName__;
    /** LNG_CODE mapping */
    private String lngCode__;
    /** LNG_CODE mapping */
    private String lngCountry__;
    /**
     * <p>lngSid を取得します。
     * @return lngSid
     */
    public int getLngSid() {
        return lngSid__;
    }
    /**
     * <p>lngSid をセットします。
     * @param lngSid lngSid
     */
    public void setLngSid(int lngSid) {
        lngSid__ = lngSid;
    }
    /**
     * <p>lngName を取得します。
     * @return lngName
     */
    public String getLngName() {
        return lngName__;
    }
    /**
     * <p>lngName をセットします。
     * @param lngName lngName
     */
    public void setLngName(String lngName) {
        lngName__ = lngName;
    }
    /**
     * <p>lngCode を取得します。
     * @return lngCode
     */
    public String getLngCode() {
        return lngCode__;
    }
    /**
     * <p>lngCode をセットします。
     * @param lngCode lngCode
     */
    public void setLngCode(String lngCode) {
        lngCode__ = lngCode;
    }
    /**
     * <p>lngCountry を取得します。
     * @return lngCountry
     */
    public String getLngCountry() {
        return lngCountry__;
    }
    /**
     * <p>lngCountry をセットします。
     * @param lngCountry lngCountry
     */
    public void setLngCountry(String lngCountry) {
        lngCountry__ = lngCountry;
    }
}