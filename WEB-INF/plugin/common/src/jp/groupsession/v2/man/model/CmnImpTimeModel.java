package jp.groupsession.v2.man.model;



import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_IMP_TIME Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnImpTimeModel implements Serializable {

    /** CIT_USR_FLG mapping */
    private int citUsrFlg__;
    /** CIT_USR_TIME_FLG mapping */
    private int citUsrTimeFlg__;
    /** CIT_USR_TIME mapping */
    private UDate citUsrTime__;
    /** CIT_GRP_FLG mapping */
    private int citGrpFlg__;
    /** CIT_GRP_TIME_FLG mapping */
    private int citGrpTimeFlg__;
    /** CIT_GRP_TIME mapping */
    private UDate citGrpTime__;
    /** CIT_BEG_FLG mapping */
    private int citBegFlg__;
    /** CIT_BEG_TIME_FLG mapping */
    private int citBegTimeFlg__;
    /** CIT_BEG_TIME mapping */
    private UDate citBegTime__;

    /**
     * <p>Default Constructor
     */
    public CmnImpTimeModel() {
    }

    /**
     * <p>get CIT_USR_FLG value
     * @return CIT_USR_FLG value
     */
    public int getCitUsrFlg() {
        return citUsrFlg__;
    }

    /**
     * <p>set CIT_USR_FLG value
     * @param citUsrFlg CIT_USR_FLG value
     */
    public void setCitUsrFlg(int citUsrFlg) {
        citUsrFlg__ = citUsrFlg;
    }

    /**
     * <p>get CIT_USR_TIME_FLG value
     * @return CIT_USR_TIME_FLG value
     */
    public int getCitUsrTimeFlg() {
        return citUsrTimeFlg__;
    }

    /**
     * <p>set CIT_USR_TIME_FLG value
     * @param citUsrTimeFlg CIT_USR_TIME_FLG value
     */
    public void setCitUsrTimeFlg(int citUsrTimeFlg) {
        citUsrTimeFlg__ = citUsrTimeFlg;
    }

    /**
     * <p>get CIT_USR_TIME value
     * @return CIT_USR_TIME value
     */
    public UDate getCitUsrTime() {
        return citUsrTime__;
    }

    /**
     * <p>set CIT_USR_TIME value
     * @param citUsrTime CIT_USR_TIME value
     */
    public void setCitUsrTime(UDate citUsrTime) {
        citUsrTime__ = citUsrTime;
    }

    /**
     * <p>get CIT_GRP_FLG value
     * @return CIT_GRP_FLG value
     */
    public int getCitGrpFlg() {
        return citGrpFlg__;
    }

    /**
     * <p>set CIT_GRP_FLG value
     * @param citGrpFlg CIT_GRP_FLG value
     */
    public void setCitGrpFlg(int citGrpFlg) {
        citGrpFlg__ = citGrpFlg;
    }

    /**
     * <p>get CIT_GRP_TIME_FLG value
     * @return CIT_GRP_TIME_FLG value
     */
    public int getCitGrpTimeFlg() {
        return citGrpTimeFlg__;
    }

    /**
     * <p>set CIT_GRP_TIME_FLG value
     * @param citGrpTimeFlg CIT_GRP_TIME_FLG value
     */
    public void setCitGrpTimeFlg(int citGrpTimeFlg) {
        citGrpTimeFlg__ = citGrpTimeFlg;
    }

    /**
     * <p>get CIT_GRP_TIME value
     * @return CIT_GRP_TIME value
     */
    public UDate getCitGrpTime() {
        return citGrpTime__;
    }

    /**
     * <p>set CIT_GRP_TIME value
     * @param citGrpTime CIT_GRP_TIME value
     */
    public void setCitGrpTime(UDate citGrpTime) {
        citGrpTime__ = citGrpTime;
    }

    /**
     * <p>get CIT_BEG_FLG value
     * @return CIT_BEG_FLG value
     */
    public int getCitBegFlg() {
        return citBegFlg__;
    }

    /**
     * <p>set CIT_BEG_FLG value
     * @param citBegFlg CIT_BEG_FLG value
     */
    public void setCitBegFlg(int citBegFlg) {
        citBegFlg__ = citBegFlg;
    }

    /**
     * <p>get CIT_BEG_TIME_FLG value
     * @return CIT_BEG_TIME_FLG value
     */
    public int getCitBegTimeFlg() {
        return citBegTimeFlg__;
    }

    /**
     * <p>set CIT_BEG_TIME_FLG value
     * @param citBegTimeFlg CIT_BEG_TIME_FLG value
     */
    public void setCitBegTimeFlg(int citBegTimeFlg) {
        citBegTimeFlg__ = citBegTimeFlg;
    }

    /**
     * <p>get CIT_BEG_TIME value
     * @return CIT_BEG_TIME value
     */
    public UDate getCitBegTime() {
        return citBegTime__;
    }

    /**
     * <p>set CIT_BEG_TIME value
     * @param citBegTime CIT_BEG_TIME value
     */
    public void setCitBegTime(UDate citBegTime) {
        citBegTime__ = citBegTime;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(citUsrFlg__);
        buf.append(",");
        buf.append(citUsrTimeFlg__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(citUsrTime__, ""));
        buf.append(",");
        buf.append(citGrpFlg__);
        buf.append(",");
        buf.append(citGrpTimeFlg__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(citGrpTime__, ""));
        buf.append(",");
        buf.append(citBegFlg__);
        buf.append(",");
        buf.append(citBegTimeFlg__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(citBegTime__, ""));
        return buf.toString();
    }

}
