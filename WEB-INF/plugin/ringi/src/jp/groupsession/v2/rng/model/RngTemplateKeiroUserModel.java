package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_TEMPLATE_KEIRO_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateKeiroUserModel implements Serializable {

    /** RTK_SID mapping */
    private int rtkSid__;
    /** USR_SID mapping */
    private int usrSid__ = -1;
    /** GRP_SID mapping */
    private int grpSid__ = -1;
    /** POS_SID mapping */
    private int posSid__ = -1;
    /** RKU_TYPE mapping */
    private int rkuType__;
    /** RKU_AUID mapping */
    private int rkuAuid__;
    /** RKU_ADATE mapping */
    private UDate rkuAdate__;
    /** RKU_EUID mapping */
    private int rkuEuid__;
    /** RKU_EDATE mapping */
    private UDate rkuEdate__;

    /**
     * <p>Default Constructor
     */
    public RngTemplateKeiroUserModel() {
    }

    /**
     * <p>get RTK_SID value
     * @return RTK_SID value
     */
    public int getRtkSid() {
        return rtkSid__;
    }

    /**
     * <p>set RTK_SID value
     * @param rtkSid RTK_SID value
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
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
     * <p>get POS_SID value
     * @return POS_SID value
     */
    public int getPosSid() {
        return posSid__;
    }

    /**
     * <p>set POS_SID value
     * @param posSid POS_SID value
     */
    public void setPosSid(int posSid) {
        posSid__ = posSid;
    }

    /**
     * <p>get RKU_TYPE value
     * @return RKU_TYPE value
     */
    public int getRkuType() {
        return rkuType__;
    }

    /**
     * <p>set RKU_TYPE value
     * @param rkuType RKU_TYPE value
     */
    public void setRkuType(int rkuType) {
        rkuType__ = rkuType;
    }

    /**
     * <p>get RKU_AUID value
     * @return RKU_AUID value
     */
    public int getRkuAuid() {
        return rkuAuid__;
    }

    /**
     * <p>set RKU_AUID value
     * @param rkuAuid RKU_AUID value
     */
    public void setRkuAuid(int rkuAuid) {
        rkuAuid__ = rkuAuid;
    }

    /**
     * <p>get RKU_ADATE value
     * @return RKU_ADATE value
     */
    public UDate getRkuAdate() {
        return rkuAdate__;
    }

    /**
     * <p>set RKU_ADATE value
     * @param rkuAdate RKU_ADATE value
     */
    public void setRkuAdate(UDate rkuAdate) {
        rkuAdate__ = rkuAdate;
    }

    /**
     * <p>get RKU_EUID value
     * @return RKU_EUID value
     */
    public int getRkuEuid() {
        return rkuEuid__;
    }

    /**
     * <p>set RKU_EUID value
     * @param rkuEuid RKU_EUID value
     */
    public void setRkuEuid(int rkuEuid) {
        rkuEuid__ = rkuEuid;
    }

    /**
     * <p>get RKU_EDATE value
     * @return RKU_EDATE value
     */
    public UDate getRkuEdate() {
        return rkuEdate__;
    }

    /**
     * <p>set RKU_EDATE value
     * @param rkuEdate RKU_EDATE value
     */
    public void setRkuEdate(UDate rkuEdate) {
        rkuEdate__ = rkuEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rtkSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(posSid__);
        buf.append(",");
        buf.append(rkuType__);
        buf.append(",");
        buf.append(rkuAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rkuAdate__, ""));
        buf.append(",");
        buf.append(rkuEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rkuEdate__, ""));
        return buf.toString();
    }

}
