package jp.groupsession.v2.man.model.base;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PTL_PORTAL_LAYOUT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalLayoutModel implements Serializable {

    /** PTL_SID mapping */
    private int ptlSid__;
    /** PLY_POSITION mapping */
    private int plyPosition__;
    /** PTS_VIEW mapping */
    private int ptsView__;
    /** PLY_AUID mapping */
    private int plyAuid__;
    /** PLY_ADATE mapping */
    private UDate plyAdate__;
    /** PLY_EUID mapping */
    private int plyEuid__;
    /** PLY_EDATE mapping */
    private UDate plyEdate__;

    /**
     * <p>Default Constructor
     */
    public PtlPortalLayoutModel() {
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
     * <p>get PTS_VIEW value
     * @return PTS_VIEW value
     */
    public int getPtsView() {
        return ptsView__;
    }

    /**
     * <p>set PTS_VIEW value
     * @param ptsView PTS_VIEW value
     */
    public void setPtsView(int ptsView) {
        ptsView__ = ptsView;
    }

    /**
     * <p>get PLY_AUID value
     * @return PLY_AUID value
     */
    public int getPlyAuid() {
        return plyAuid__;
    }

    /**
     * <p>set PLY_AUID value
     * @param plyAuid PLY_AUID value
     */
    public void setPlyAuid(int plyAuid) {
        plyAuid__ = plyAuid;
    }

    /**
     * <p>get PLY_ADATE value
     * @return PLY_ADATE value
     */
    public UDate getPlyAdate() {
        return plyAdate__;
    }

    /**
     * <p>set PLY_ADATE value
     * @param plyAdate PLY_ADATE value
     */
    public void setPlyAdate(UDate plyAdate) {
        plyAdate__ = plyAdate;
    }

    /**
     * <p>get PLY_EUID value
     * @return PLY_EUID value
     */
    public int getPlyEuid() {
        return plyEuid__;
    }

    /**
     * <p>set PLY_EUID value
     * @param plyEuid PLY_EUID value
     */
    public void setPlyEuid(int plyEuid) {
        plyEuid__ = plyEuid;
    }

    /**
     * <p>get PLY_EDATE value
     * @return PLY_EDATE value
     */
    public UDate getPlyEdate() {
        return plyEdate__;
    }

    /**
     * <p>set PLY_EDATE value
     * @param plyEdate PLY_EDATE value
     */
    public void setPlyEdate(UDate plyEdate) {
        plyEdate__ = plyEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ptlSid__);
        buf.append(",");
        buf.append(plyPosition__);
        buf.append(",");
        buf.append(ptsView__);
        buf.append(",");
        buf.append(plyAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(plyAdate__, ""));
        buf.append(",");
        buf.append(plyEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(plyEdate__, ""));
        return buf.toString();
    }

}
