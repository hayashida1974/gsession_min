package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_DATAFOLDER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnDatafolderModel implements Serializable {

    /** CDF_NAME mapping */
    private String cdfName__;
    /** CDF_SIZE mapping */
    private long cdfSize__;
    /** CDF_SORT mapping */
    private int cdfSort__;

    /**
     * <p>Default Constructor
     */
    public CmnDatafolderModel() {
    }

    /**
     * <p>get CDF_NAME value
     * @return CDF_NAME value
     */
    public String getCdfName() {
        return cdfName__;
    }

    /**
     * <p>set CDF_NAME value
     * @param cdfName CDF_NAME value
     */
    public void setCdfName(String cdfName) {
        cdfName__ = cdfName;
    }

    /**
     * <p>get CDF_SIZE value
     * @return CDF_SIZE value
     */
    public long getCdfSize() {
        return cdfSize__;
    }

    /**
     * <p>set CDF_SIZE value
     * @param cdfSize CDF_SIZE value
     */
    public void setCdfSize(long cdfSize) {
        cdfSize__ = cdfSize;
    }

    /**
     * <p>get CDF_SORT value
     * @return CDF_SORT value
     */
    public int getCdfSort() {
        return cdfSort__;
    }

    /**
     * <p>set CDF_SORT value
     * @param cdfSort CDF_SORT value
     */
    public void setCdfSort(int cdfSort) {
        cdfSort__ = cdfSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(cdfName__, ""));
        buf.append(",");
        buf.append(cdfSize__);
        buf.append(",");
        buf.append(cdfSort__);
        return buf.toString();
    }

}
