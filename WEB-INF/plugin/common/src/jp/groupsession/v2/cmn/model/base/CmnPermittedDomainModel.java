package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_PERMITTED_DOMAIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPermittedDomainModel implements Serializable {

    /** CPD_EXT_DOMAIN mapping */
    private String cpdExtDomain__;
    /** CPD_AUID mapping */
    private int cpdAuid__;
    /** CPD_ADATE mapping */
    private UDate cpdAdate__;
    /** CPD_EUID mapping */
    private int cpdEuid__;
    /** CPD_EDATE mapping */
    private UDate cpdEdate__;


    /**
     * <p> get CPD_EXT_DOMAIN value
     * @return CPD_EXT_DOMAIN
     * */
    public String getCpdExtDomain() {
        return cpdExtDomain__;
    }
    /**
     * <p> set CPD_EXT_DOMAIN value
     * @param cpdExtDomain CPD_EXT_DOMAIN
     * */
    public void setCpdExtDomain(String cpdExtDomain) {
        cpdExtDomain__ = cpdExtDomain;
    }

    /**
     * <p> get CPD_AUID value
     * @return CPD_AUID
     * */
    public int getCpdAuid() {
        return cpdAuid__;
    }
    /**
     * <p> set CPD_AUID value
     * @param cpdAuid CPD_AUID
     * */
    public void setCpdAuid(int cpdAuid) {
        cpdAuid__ = cpdAuid;
    }

    /**
     * <p> get CPD_ADATE value
     * @return CPD_ADATE
     * */
    public UDate getCpdAdate() {
        return cpdAdate__;
    }
    /**
     * <p> set CPD_ADATE value
     * @param cpdAdate CPD_ADATE
     * */
    public void setCpdAdate(UDate cpdAdate) {
        cpdAdate__ = cpdAdate;
    }

    /**
     * <p> get CPD_EUID value
     * @return CPD_EUID
     * */
    public int getCpdEuid() {
        return cpdEuid__;
    }
    /**
     * <p> set CPD_EUID value
     * @param cpdEuid CPD_EUID
     * */
    public void setCpdEuid(int cpdEuid) {
        cpdEuid__ = cpdEuid;
    }

    /**
     * <p> get CPD_EDATE value
     * @return CPD_EDATE
     * */
    public UDate getCpdEdate() {
        return cpdEdate__;
    }
    /**
     * <p> set CPD_EDATE value
     * @param cpdEdate CPD_EDATE
     * */
    public void setCpdEdate(UDate cpdEdate) {
        cpdEdate__ = cpdEdate;
    }
}
