package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_PERMIT_VIEW Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPermitViewModel implements Serializable {

    /** ADR_SID mapping */
    private int adrSid__;
    /** ADR_PERMIT_VIEW mapping */
    private int adrPermitView__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** APV_AUID mapping */
    private int apvAuid__;
    /** APV_ADATE mapping */
    private UDate apvAdate__;
    /** APV_EUID mapping */
    private int apvEuid__;
    /** APV_EDATE mapping */
    private UDate apvEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrPermitViewModel() {
    }

    /**
     * <p>get ADR_SID value
     * @return ADR_SID value
     */
    public int getAdrSid() {
        return adrSid__;
    }

    /**
     * <p>set ADR_SID value
     * @param adrSid ADR_SID value
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
    }

    /**
     * <p>get ADR_PERMIT_VIEW value
     * @return ADR_PERMIT_VIEW value
     */
    public int getAdrPermitView() {
        return adrPermitView__;
    }

    /**
     * <p>set ADR_PERMIT_VIEW value
     * @param adrPermitView ADR_PERMIT_VIEW value
     */
    public void setAdrPermitView(int adrPermitView) {
        adrPermitView__ = adrPermitView;
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
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
     * <p>get APV_AUID value
     * @return APV_AUID value
     */
    public int getApvAuid() {
        return apvAuid__;
    }

    /**
     * <p>set APV_AUID value
     * @param apvAuid APV_AUID value
     */
    public void setApvAuid(int apvAuid) {
        apvAuid__ = apvAuid;
    }

    /**
     * <p>get APV_ADATE value
     * @return APV_ADATE value
     */
    public UDate getApvAdate() {
        return apvAdate__;
    }

    /**
     * <p>set APV_ADATE value
     * @param apvAdate APV_ADATE value
     */
    public void setApvAdate(UDate apvAdate) {
        apvAdate__ = apvAdate;
    }

    /**
     * <p>get APV_EUID value
     * @return APV_EUID value
     */
    public int getApvEuid() {
        return apvEuid__;
    }

    /**
     * <p>set APV_EUID value
     * @param apvEuid APV_EUID value
     */
    public void setApvEuid(int apvEuid) {
        apvEuid__ = apvEuid;
    }

    /**
     * <p>get APV_EDATE value
     * @return APV_EDATE value
     */
    public UDate getApvEdate() {
        return apvEdate__;
    }

    /**
     * <p>set APV_EDATE value
     * @param apvEdate APV_EDATE value
     */
    public void setApvEdate(UDate apvEdate) {
        apvEdate__ = apvEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(adrSid__);
        buf.append(",");
        buf.append(adrPermitView__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(apvAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apvAdate__, ""));
        buf.append(",");
        buf.append(apvEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apvEdate__, ""));
        return buf.toString();
    }

}
