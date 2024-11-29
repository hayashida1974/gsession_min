package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_MDISP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMdispModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MDP_PID mapping */
    private String mdpPid__;
    /** MDP_DSP mapping */
    private int mdpDsp__;
    /** MDP_RELOAD mapping */
    private int mdpReload__;
    /** MDP_AUID mapping */
    private int mdpAuid__;
    /** MDP_ADATE mapping */
    private UDate mdpAdate__;
    /** MDP_EUID mapping */
    private int mdpEuid__;
    /** MDP_EDATE mapping */
    private UDate mdpEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnMdispModel() {
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
     * <p>get MDP_PID value
     * @return MDP_PID value
     */
    public String getMdpPid() {
        return mdpPid__;
    }

    /**
     * <p>set MDP_PID value
     * @param mdpPid MDP_PID value
     */
    public void setMdpPid(String mdpPid) {
        mdpPid__ = mdpPid;
    }

    /**
     * <p>get MDP_DSP value
     * @return MDP_DSP value
     */
    public int getMdpDsp() {
        return mdpDsp__;
    }

    /**
     * <p>set MDP_DSP value
     * @param mdpDsp MDP_DSP value
     */
    public void setMdpDsp(int mdpDsp) {
        mdpDsp__ = mdpDsp;
    }

    /**
     * <p>get MDP_AUID value
     * @return MDP_AUID value
     */
    public int getMdpAuid() {
        return mdpAuid__;
    }

    /**
     * <p>set MDP_AUID value
     * @param mdpAuid MDP_AUID value
     */
    public void setMdpAuid(int mdpAuid) {
        mdpAuid__ = mdpAuid;
    }

    /**
     * <p>get MDP_ADATE value
     * @return MDP_ADATE value
     */
    public UDate getMdpAdate() {
        return mdpAdate__;
    }

    /**
     * <p>set MDP_ADATE value
     * @param mdpAdate MDP_ADATE value
     */
    public void setMdpAdate(UDate mdpAdate) {
        mdpAdate__ = mdpAdate;
    }

    /**
     * <p>get MDP_EUID value
     * @return MDP_EUID value
     */
    public int getMdpEuid() {
        return mdpEuid__;
    }

    /**
     * <p>set MDP_EUID value
     * @param mdpEuid MDP_EUID value
     */
    public void setMdpEuid(int mdpEuid) {
        mdpEuid__ = mdpEuid;
    }

    /**
     * <p>get MDP_EDATE value
     * @return MDP_EDATE value
     */
    public UDate getMdpEdate() {
        return mdpEdate__;
    }

    /**
     * <p>set MDP_EDATE value
     * @param mdpEdate MDP_EDATE value
     */
    public void setMdpEdate(UDate mdpEdate) {
        mdpEdate__ = mdpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(mdpPid__, ""));
        buf.append(",");
        buf.append(mdpDsp__);
        buf.append(",");
        buf.append(mdpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mdpAdate__, ""));
        buf.append(",");
        buf.append(mdpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mdpEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>mdpReload を取得します。
     * @return mdpReload
     */
    public int getMdpReload() {
        return mdpReload__;
    }

    /**
     * <p>mdpReload をセットします。
     * @param mdpReload mdpReload
     */
    public void setMdpReload(int mdpReload) {
        mdpReload__ = mdpReload;
    }

}
