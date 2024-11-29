package jp.groupsession.v2.convert.convert240.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_MEMBERS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjMembersModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** PRM_EMPLOYEE_KBN mapping */
    private int prmEmployeeKbn__;
    /** PRM_ADMIN_KBN mapping */
    private int prmAdminKbn__;
    /** PRM_AUID mapping */
    private int prmAuid__;
    /** PRM_ADATE mapping */
    private UDate prmAdate__;
    /** PRM_EUID mapping */
    private int prmEuid__;
    /** PRM_EDATE mapping */
    private UDate prmEdate__;
    /** PRM_MEM_KEY */
    private String prmMemKey__;

    /**
     * <p>prmMemKey を取得します。
     * @return prmMemKey
     */
    public String getPrmMemKey() {
        return prmMemKey__;
    }

    /**
     * <p>prmMemKey をセットします。
     * @param prmMemKey prmMemKey
     */
    public void setPrmMemKey(String prmMemKey) {
        prmMemKey__ = prmMemKey;
    }

    /**
     * <p>Default Constructor
     */
    public PrjMembersModel() {
    }

    /**
     * <p>get PRJ_SID value
     * @return PRJ_SID value
     */
    public int getPrjSid() {
        return prjSid__;
    }

    /**
     * <p>set PRJ_SID value
     * @param prjSid PRJ_SID value
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
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
     * <p>get PRM_EMPLOYEE_KBN value
     * @return PRM_EMPLOYEE_KBN value
     */
    public int getPrmEmployeeKbn() {
        return prmEmployeeKbn__;
    }

    /**
     * <p>set PRM_EMPLOYEE_KBN value
     * @param prmEmployeeKbn PRM_EMPLOYEE_KBN value
     */
    public void setPrmEmployeeKbn(int prmEmployeeKbn) {
        prmEmployeeKbn__ = prmEmployeeKbn;
    }

    /**
     * <p>get PRM_ADMIN_KBN value
     * @return PRM_ADMIN_KBN value
     */
    public int getPrmAdminKbn() {
        return prmAdminKbn__;
    }

    /**
     * <p>set PRM_ADMIN_KBN value
     * @param prmAdminKbn PRM_ADMIN_KBN value
     */
    public void setPrmAdminKbn(int prmAdminKbn) {
        prmAdminKbn__ = prmAdminKbn;
    }

    /**
     * <p>get PRM_AUID value
     * @return PRM_AUID value
     */
    public int getPrmAuid() {
        return prmAuid__;
    }

    /**
     * <p>set PRM_AUID value
     * @param prmAuid PRM_AUID value
     */
    public void setPrmAuid(int prmAuid) {
        prmAuid__ = prmAuid;
    }

    /**
     * <p>get PRM_ADATE value
     * @return PRM_ADATE value
     */
    public UDate getPrmAdate() {
        return prmAdate__;
    }

    /**
     * <p>set PRM_ADATE value
     * @param prmAdate PRM_ADATE value
     */
    public void setPrmAdate(UDate prmAdate) {
        prmAdate__ = prmAdate;
    }

    /**
     * <p>get PRM_EUID value
     * @return PRM_EUID value
     */
    public int getPrmEuid() {
        return prmEuid__;
    }

    /**
     * <p>set PRM_EUID value
     * @param prmEuid PRM_EUID value
     */
    public void setPrmEuid(int prmEuid) {
        prmEuid__ = prmEuid;
    }

    /**
     * <p>get PRM_EDATE value
     * @return PRM_EDATE value
     */
    public UDate getPrmEdate() {
        return prmEdate__;
    }

    /**
     * <p>set PRM_EDATE value
     * @param prmEdate PRM_EDATE value
     */
    public void setPrmEdate(UDate prmEdate) {
        prmEdate__ = prmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(prmEmployeeKbn__);
        buf.append(",");
        buf.append(prmAdminKbn__);
        buf.append(",");
        buf.append(prmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(prmAdate__, ""));
        buf.append(",");
        buf.append(prmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(prmEdate__, ""));
        return buf.toString();
    }

}
