package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_WEATHER_AREA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnWeatherAreaModel implements Serializable {

    /** CWA_SID mapping */
    private int cwaSid__;
    /** CWA_NAME mapping */
    private String cwaName__;
    /** CWA_SORT mapping */
    private int cwaSort__;

    /**
     * <p>Default Constructor
     */
    public CmnWeatherAreaModel() {
    }

    /**
     * <p>get CWA_SID value
     * @return CWA_SID value
     */
    public int getCwaSid() {
        return cwaSid__;
    }

    /**
     * <p>set CWA_SID value
     * @param cwaSid CWA_SID value
     */
    public void setCwaSid(int cwaSid) {
        cwaSid__ = cwaSid;
    }

    /**
     * <p>get CWA_NAME value
     * @return CWA_NAME value
     */
    public String getCwaName() {
        return cwaName__;
    }

    /**
     * <p>set CWA_NAME value
     * @param cwaName CWA_NAME value
     */
    public void setCwaName(String cwaName) {
        cwaName__ = cwaName;
    }

    /**
     * <p>get CWA_SORT value
     * @return CWA_SORT value
     */
    public int getCwaSort() {
        return cwaSort__;
    }

    /**
     * <p>set CWA_SORT value
     * @param cwaSort CWA_SORT value
     */
    public void setCwaSort(int cwaSort) {
        cwaSort__ = cwaSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(cwaSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cwaName__, ""));
        buf.append(",");
        buf.append(cwaSort__);
        return buf.toString();
    }

}
