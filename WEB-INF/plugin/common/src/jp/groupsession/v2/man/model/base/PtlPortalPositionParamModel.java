package jp.groupsession.v2.man.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PTL_PORTAL_POSITION_PARAM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalPositionParamModel implements Serializable {

    /** PTL_SID mapping */
    private int ptlSid__;
    /** PTP_ITEMID mapping */
    private String ptpItemid__;
    /** PPM_PARAM_NO mapping */
    private int ppmParamNo__;
    /** PPM_PARAM_NAME mapping */
    private String ppmParamName__;
    /** PPM_PARAM_VALUE mapping */
    private String ppmParamValue__;

    /**
     * <p>Default Constructor
     */
    public PtlPortalPositionParamModel() {
    }

    /**
     * <p>get PTL_SID value
     * @return PTL_SID value
     */
    public int getPtlSid() {
        return ptlSid__;
    }

    /**
     * <p>set PTL_SID value
     * @param ptlSid PTL_SID value
     */
    public void setPtlSid(int ptlSid) {
        ptlSid__ = ptlSid;
    }

    /**
     * <p>get PTP_ITEMID value
     * @return PTP_ITEMID value
     */
    public String getPtpItemid() {
        return ptpItemid__;
    }

    /**
     * <p>set PTP_ITEMID value
     * @param ptpItemid PTP_ITEMID value
     */
    public void setPtpItemid(String ptpItemid) {
        ptpItemid__ = ptpItemid;
    }

    /**
     * <p>get PPM_PARAM_NO value
     * @return PPM_PARAM_NO value
     */
    public int getPpmParamNo() {
        return ppmParamNo__;
    }

    /**
     * <p>set PPM_PARAM_NO value
     * @param ppmParamNo PPM_PARAM_NO value
     */
    public void setPpmParamNo(int ppmParamNo) {
        ppmParamNo__ = ppmParamNo;
    }

    /**
     * <p>get PPM_PARAM_NAME value
     * @return PPM_PARAM_NAME value
     */
    public String getPpmParamName() {
        return ppmParamName__;
    }

    /**
     * <p>set PPM_PARAM_NAME value
     * @param ppmParamName PPM_PARAM_NAME value
     */
    public void setPpmParamName(String ppmParamName) {
        ppmParamName__ = ppmParamName;
    }

    /**
     * <p>get PPM_PARAM_VALUE value
     * @return PPM_PARAM_VALUE value
     */
    public String getPpmParamValue() {
        return ppmParamValue__;
    }

    /**
     * <p>set PPM_PARAM_VALUE value
     * @param ppmParamValue PPM_PARAM_VALUE value
     */
    public void setPpmParamValue(String ppmParamValue) {
        ppmParamValue__ = ppmParamValue;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ptlSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ptpItemid__, ""));
        buf.append(",");
        buf.append(ppmParamNo__);
        buf.append(",");
        buf.append(NullDefault.getString(ppmParamName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ppmParamValue__, ""));
        return buf.toString();
    }

}
