package jp.groupsession.v2.convert.convert240.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_FILE_REKI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CvtFileFileRekiModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** FDR_VERSION mapping */
    private int fdrVersion__;
    /** FFR_FNAME mapping */
    private String ffrFname__;
    /** FFR_KBN mapping */
    private int ffrKbn__;
    /** FFR_EUID mapping */
    private int ffrEuid__;
    /** FFR_EDATE mapping */
    private UDate ffrEdate__;
    /** FFR_PARENT_SID mapping */
    private int ffrParentSid__;

    /**
     * <p>ffrParentSid を取得します。
     * @return ffrParentSid
     */
    public int getFfrParentSid() {
        return ffrParentSid__;
    }

    /**
     * <p>ffrParentSid をセットします。
     * @param ffrParentSid ffrParentSid
     */
    public void setFfrParentSid(int ffrParentSid) {
        ffrParentSid__ = ffrParentSid;
    }

    /**
     * <p>Default Constructor
     */
    public CvtFileFileRekiModel() {
    }

    /**
     * <p>get FDR_SID value
     * @return FDR_SID value
     */
    public int getFdrSid() {
        return fdrSid__;
    }

    /**
     * <p>set FDR_SID value
     * @param fdrSid FDR_SID value
     */
    public void setFdrSid(int fdrSid) {
        fdrSid__ = fdrSid;
    }

    /**
     * <p>get FDR_VERSION value
     * @return FDR_VERSION value
     */
    public int getFdrVersion() {
        return fdrVersion__;
    }

    /**
     * <p>set FDR_VERSION value
     * @param fdrVersion FDR_VERSION value
     */
    public void setFdrVersion(int fdrVersion) {
        fdrVersion__ = fdrVersion;
    }

    /**
     * <p>get FFR_FNAME value
     * @return FFR_FNAME value
     */
    public String getFfrFname() {
        return ffrFname__;
    }

    /**
     * <p>set FFR_FNAME value
     * @param ffrFname FFR_FNAME value
     */
    public void setFfrFname(String ffrFname) {
        ffrFname__ = ffrFname;
    }

    /**
     * <p>get FFR_KBN value
     * @return FFR_KBN value
     */
    public int getFfrKbn() {
        return ffrKbn__;
    }

    /**
     * <p>set FFR_KBN value
     * @param ffrKbn FFR_KBN value
     */
    public void setFfrKbn(int ffrKbn) {
        ffrKbn__ = ffrKbn;
    }

    /**
     * <p>get FFR_EUID value
     * @return FFR_EUID value
     */
    public int getFfrEuid() {
        return ffrEuid__;
    }

    /**
     * <p>set FFR_EUID value
     * @param ffrEuid FFR_EUID value
     */
    public void setFfrEuid(int ffrEuid) {
        ffrEuid__ = ffrEuid;
    }

    /**
     * <p>get FFR_EDATE value
     * @return FFR_EDATE value
     */
    public UDate getFfrEdate() {
        return ffrEdate__;
    }

    /**
     * <p>set FFR_EDATE value
     * @param ffrEdate FFR_EDATE value
     */
    public void setFfrEdate(UDate ffrEdate) {
        ffrEdate__ = ffrEdate;
    }

}
