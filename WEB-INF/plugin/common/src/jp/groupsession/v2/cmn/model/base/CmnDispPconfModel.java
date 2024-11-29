package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

/**
 * <p>CMN_DISP_PCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnDispPconfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** CDP_ROKUYOU mapping */
    private int cdpRokuyou__;

    /**
     * <p>Default Constructor
     */
    public CmnDispPconfModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get CDP_ROKUYOU value
     * @return CDP_ROKUYOU value
     */
    public int getCdpRokuyou() {
        return cdpRokuyou__;
    }

    /**
     * <p>set CDP_ROKUYOU value
     * @param cdpRokuyou CDP_ROKUYOU value
     */
    public void setCdpRokuyou(int cdpRokuyou) {
        cdpRokuyou__ = cdpRokuyou;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(cdpRokuyou__);
        return buf.toString();
    }

}
