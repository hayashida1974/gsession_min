package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_MANAGE_DELUID Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlManageDeluidModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WDU_DELDATE mapping */
    private UDate wduDeldate__;

    /**
     * <p>Default Constructor
     */
    public WmlManageDeluidModel() {
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
     * <p>get WDU_DELDATE value
     * @return WDU_DELDATE value
     */
    public UDate getWduDeldate() {
        return wduDeldate__;
    }

    /**
     * <p>set WDU_DELDATE value
     * @param wduDeldate WDU_DELDATE value
     */
    public void setWduDeldate(UDate wduDeldate) {
        wduDeldate__ = wduDeldate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wduDeldate__, ""));
        return buf.toString();
    }

}
