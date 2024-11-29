package jp.groupsession.v2.man.model.base;

import java.io.Serializable;

/**
 * <p>PTL_PORTAL_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalSortModel implements Serializable {

    /** PTL_SID mapping */
    private int ptlSid__;
    /** PTS_KBN mapping */
    private int ptsKbn__;
    /** USR_SID mapping */
    private int usrSid__;
    /** PTS_SORT mapping */
    private int ptsSort__;

    /**
     * <p>Default Constructor
     */
    public PtlPortalSortModel() {
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
     * <p>get PTS_KBN value
     * @return PTS_KBN value
     */
    public int getPtsKbn() {
        return ptsKbn__;
    }

    /**
     * <p>set PTS_KBN value
     * @param ptsKbn PTS_KBN value
     */
    public void setPtsKbn(int ptsKbn) {
        ptsKbn__ = ptsKbn;
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
     * <p>get PTS_SORT value
     * @return PTS_SORT value
     */
    public int getPtsSort() {
        return ptsSort__;
    }

    /**
     * <p>set PTS_SORT value
     * @param ptsSort PTS_SORT value
     */
    public void setPtsSort(int ptsSort) {
        ptsSort__ = ptsSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ptlSid__);
        buf.append(",");
        buf.append(ptsKbn__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(ptsSort__);
        return buf.toString();
    }

}
