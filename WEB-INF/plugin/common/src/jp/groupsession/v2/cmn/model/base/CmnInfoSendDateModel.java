package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_INFO_SEND_DATE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnInfoSendDateModel implements Serializable {

    /** CSD_DATE mapping */
    private UDate csdDate__;

    /**
     * <p>Default Constructor
     */
    public CmnInfoSendDateModel() {
    }

    /**
     * <p>get CSD_DATE value
     * @return CSD_DATE value
     */
    public UDate getCsdDate() {
        return csdDate__;
    }

    /**
     * <p>set CSD_DATE value
     * @param csdDate CSD_DATE value
     */
    public void setCsdDate(UDate csdDate) {
        csdDate__ = csdDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getStringFmObj(csdDate__, ""));
        return buf.toString();
    }
}
