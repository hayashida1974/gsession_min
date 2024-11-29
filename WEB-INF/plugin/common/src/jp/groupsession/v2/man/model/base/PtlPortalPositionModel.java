package jp.groupsession.v2.man.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>PTL_PORTAL_POSITION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalPositionModel implements Serializable {

    /** PTL_SID mapping */
    private int ptlSid__;
    /** PTP_ITEMID mapping */
    private String ptpItemid__;
    /** PLY_POSITION mapping */
    private int plyPosition__;
    /** PTP_SORT mapping */
    private int ptpSort__;
    /** PTP_VIEW mapping */
    private int ptpView__;
    /** PTP_TYPE mapping */
    private int ptpType__;
    /** PLT_SID mapping */
    private int pltSid__;
    /** PCT_PID mapping */
    private String pctPid__;
    /** MSC_ID mapping */
    private String mscId__;
    /** PTP_PARAMKBN mapping */
    private int ptpParamkbn__;
    /**
     * <p>Default Constructor
     */
    public PtlPortalPositionModel() {
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
     * <p>get PLY_POSITION value
     * @return PLY_POSITION value
     */
    public int getPlyPosition() {
        return plyPosition__;
    }

    /**
     * <p>set PLY_POSITION value
     * @param plyPosition PLY_POSITION value
     */
    public void setPlyPosition(int plyPosition) {
        plyPosition__ = plyPosition;
    }

    /**
     * <p>get PTP_SORT value
     * @return PTP_SORT value
     */
    public int getPtpSort() {
        return ptpSort__;
    }

    /**
     * <p>set PTP_SORT value
     * @param ptpSort PTP_SORT value
     */
    public void setPtpSort(int ptpSort) {
        ptpSort__ = ptpSort;
    }

    /**
     * <p>get PTP_VIEW value
     * @return PTP_VIEW value
     */
    public int getPtpView() {
        return ptpView__;
    }

    /**
     * <p>set PTP_VIEW value
     * @param ptpView PTP_VIEW value
     */
    public void setPtpView(int ptpView) {
        ptpView__ = ptpView;
    }

    /**
     * <p>get PTP_TYPE value
     * @return PTP_TYPE value
     */
    public int getPtpType() {
        return ptpType__;
    }

    /**
     * <p>set PTP_TYPE value
     * @param ptpType PTP_TYPE value
     */
    public void setPtpType(int ptpType) {
        ptpType__ = ptpType;
    }

    /**
     * <p>get PLT_SID value
     * @return PLT_SID value
     */
    public int getPltSid() {
        return pltSid__;
    }

    /**
     * <p>set PLT_SID value
     * @param pltSid PLT_SID value
     */
    public void setPltSid(int pltSid) {
        pltSid__ = pltSid;
    }

    /**
     * <p>get PCT_PID value
     * @return PCT_PID value
     */
    public String getPctPid() {
        return pctPid__;
    }

    /**
     * <p>set PCT_PID value
     * @param pctPid PCT_PID value
     */
    public void setPctPid(String pctPid) {
        pctPid__ = pctPid;
    }

    /**
     * <p>get MSC_ID value
     * @return MSC_ID value
     */
    public String getMscId() {
        return mscId__;
    }

    /**
     * <p>set MSC_ID value
     * @param mscId MSC_ID value
     */
    public void setMscId(String mscId) {
        mscId__ = mscId;
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
        buf.append(plyPosition__);
        buf.append(",");
        buf.append(ptpSort__);
        buf.append(",");
        buf.append(ptpView__);
        buf.append(",");
        buf.append(ptpType__);
        buf.append(",");
        buf.append(pltSid__);
        buf.append(",");
        buf.append(NullDefault.getString(pctPid__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(mscId__, ""));
        return buf.toString();
    }

    /**
     * <p>ptpParamkbn を取得します。
     * @return ptpParamkbn
     */
    public int getPtpParamkbn() {
        return ptpParamkbn__;
    }

    /**
     * <p>ptpParamkbn をセットします。
     * @param ptpParamkbn ptpParamkbn
     */
    public void setPtpParamkbn(int ptpParamkbn) {
        ptpParamkbn__ = ptpParamkbn;
    }

}
