package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_PERMIT_EDIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPermitEditModel implements Serializable {

    /** ADR_SID mapping */
    private int adrSid__;
    /** ADR_PERMIT_EDIT mapping */
    private int adrPermitEdit__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** APE_AUID mapping */
    private int apeAuid__;
    /** APE_ADATE mapping */
    private UDate apeAdate__;
    /** APE_EUID mapping */
    private int apeEuid__;
    /** APE_EDATE mapping */
    private UDate apeEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrPermitEditModel() {
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
     * <p>get ADR_PERMIT_EDIT value
     * @return ADR_PERMIT_EDIT value
     */
    public int getAdrPermitEdit() {
        return adrPermitEdit__;
    }

    /**
     * <p>set ADR_PERMIT_EDIT value
     * @param adrPermitEdit ADR_PERMIT_EDIT value
     */
    public void setAdrPermitEdit(int adrPermitEdit) {
        adrPermitEdit__ = adrPermitEdit;
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
     * <p>get APE_AUID value
     * @return APE_AUID value
     */
    public int getApeAuid() {
        return apeAuid__;
    }

    /**
     * <p>set APE_AUID value
     * @param apeAuid APE_AUID value
     */
    public void setApeAuid(int apeAuid) {
        apeAuid__ = apeAuid;
    }

    /**
     * <p>get APE_ADATE value
     * @return APE_ADATE value
     */
    public UDate getApeAdate() {
        return apeAdate__;
    }

    /**
     * <p>set APE_ADATE value
     * @param apeAdate APE_ADATE value
     */
    public void setApeAdate(UDate apeAdate) {
        apeAdate__ = apeAdate;
    }

    /**
     * <p>get APE_EUID value
     * @return APE_EUID value
     */
    public int getApeEuid() {
        return apeEuid__;
    }

    /**
     * <p>set APE_EUID value
     * @param apeEuid APE_EUID value
     */
    public void setApeEuid(int apeEuid) {
        apeEuid__ = apeEuid;
    }

    /**
     * <p>get APE_EDATE value
     * @return APE_EDATE value
     */
    public UDate getApeEdate() {
        return apeEdate__;
    }

    /**
     * <p>set APE_EDATE value
     * @param apeEdate APE_EDATE value
     */
    public void setApeEdate(UDate apeEdate) {
        apeEdate__ = apeEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(adrSid__);
        buf.append(",");
        buf.append(adrPermitEdit__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(apeAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apeAdate__, ""));
        buf.append(",");
        buf.append(apeEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apeEdate__, ""));
        return buf.toString();
    }

}
