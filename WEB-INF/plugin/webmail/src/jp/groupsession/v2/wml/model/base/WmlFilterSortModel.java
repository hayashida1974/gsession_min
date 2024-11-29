package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_FILTER_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterSortModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WFT_SID mapping */
    private int wftSid__;
    /** WFS_SORT mapping */
    private long wfsSort__;

    /**
     * <p>Default Constructor
     */
    public WmlFilterSortModel() {
    }

    /**
     * <p>get WAC_SID value
     * @return WAC_SID value
     */
    public int getWacSid() {
        return wacSid__;
    }

    /**
     * <p>set WAC_SID value
     * @param wacSid WAC_SID value
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>get WFT_SID value
     * @return WFT_SID value
     */
    public int getWftSid() {
        return wftSid__;
    }

    /**
     * <p>set WFT_SID value
     * @param wftSid WFT_SID value
     */
    public void setWftSid(int wftSid) {
        wftSid__ = wftSid;
    }

    /**
     * <p>get WFS_SORT value
     * @return WFS_SORT value
     */
    public long getWfsSort() {
        return wfsSort__;
    }

    /**
     * <p>set WFS_SORT value
     * @param wfsSort WFS_SORT value
     */
    public void setWfsSort(long wfsSort) {
        wfsSort__ = wfsSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wftSid__);
        buf.append(",");
        buf.append(wfsSort__);
        return buf.toString();
    }

}
