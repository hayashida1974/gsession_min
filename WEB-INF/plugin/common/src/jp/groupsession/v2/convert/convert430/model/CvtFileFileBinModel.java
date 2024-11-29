package jp.groupsession.v2.convert.convert430.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_FILE_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CvtFileFileBinModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** FDR_VERSION mapping */
    private int fdrVersion__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);
    /** FFL_EXT mapping */
    private String fflExt__;
    /** FFL_FILE_SIZE mapping */
    private long fflFileSize__;
    /** FFL_LOCK_KBN mapping */
    private int fflLockKbn__;
    /** FFL_LOCK_USER mapping */
    private int fflLockUser__;
    /** FFL_LOCK_DATE mapping */
    private UDate fflLockDate__ = null;

    /**
     * <p>Default Constructor
     */
    public CvtFileFileBinModel() {
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
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get FFL_EXT value
     * @return FFL_EXT value
     */
    public String getFflExt() {
        return fflExt__;
    }

    /**
     * <p>set FFL_EXT value
     * @param fflExt FFL_EXT value
     */
    public void setFflExt(String fflExt) {
        fflExt__ = fflExt;
    }

    /**
     * <p>get FFL_FILE_SIZE value
     * @return FFL_FILE_SIZE value
     */
    public long getFflFileSize() {
        return fflFileSize__;
    }

    /**
     * <p>set FFL_FILE_SIZE value
     * @param fflFileSize FFL_FILE_SIZE value
     */
    public void setFflFileSize(long fflFileSize) {
        fflFileSize__ = fflFileSize;
    }

    /**
     * <p>get FFL_LOCK_KBN value
     * @return FFL_LOCK_KBN value
     */
    public int getFflLockKbn() {
        return fflLockKbn__;
    }

    /**
     * <p>set FFL_LOCK_KBN value
     * @param fflLockKbn FFL_LOCK_KBN value
     */
    public void setFflLockKbn(int fflLockKbn) {
        fflLockKbn__ = fflLockKbn;
    }

    /**
     * <p>get FFL_LOCK_USER value
     * @return FFL_LOCK_USER value
     */
    public int getFflLockUser() {
        return fflLockUser__;
    }

    /**
     * <p>set FFL_LOCK_USER value
     * @param fflLockUser FFL_LOCK_USER value
     */
    public void setFflLockUser(int fflLockUser) {
        fflLockUser__ = fflLockUser;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(fdrSid__);
        buf.append(",");
        buf.append(fdrVersion__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(NullDefault.getString(fflExt__, ""));
        buf.append(",");
        buf.append(fflFileSize__);
        buf.append(",");
        buf.append(fflLockKbn__);
        buf.append(",");
        buf.append(fflLockUser__);
        return buf.toString();
    }

    /**
     * <p>fflLockDate を取得します。
     * @return fflLockDate
     */
    public UDate getFflLockDate() {
        return fflLockDate__;
    }

    /**
     * <p>fflLockDate をセットします。
     * @param fflLockDate fflLockDate
     */
    public void setFflLockDate(UDate fflLockDate) {
        fflLockDate__ = fflLockDate;
    }

}
