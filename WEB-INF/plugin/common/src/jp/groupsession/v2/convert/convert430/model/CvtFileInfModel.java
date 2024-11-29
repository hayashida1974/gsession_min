package jp.groupsession.v2.convert.convert430.model;

import java.io.Serializable;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_DIRECTORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CvtFileInfModel implements Serializable {

    /** FCB_SID mapping */
    private int fcbSid__;
    /** FDR_PARENT_SID mapping */
    private int fdrParentSid__;
    /** FDR_VER_KBN mapping */
    private int fdrVerKbn__;
    /** FDR_LEVEL mapping */
    private int fdrLevel__;
    /** FDR_NAME mapping */
    private String fileName__;
    /** FILE_EXTENSION mapping */
    private String fileExtension__;
    /** FFL_FILE_SIZE mapping */
    private long fflFileSize__;
    /** FDR_AUID mapping */
    private int fdrAuid__;
    /** FDR_ADATE mapping */
    private UDate fdrAdate__;
    /** FDR_EUID mapping */
    private int fdrEuid__;
    /** FDR_EDATE mapping */
    private UDate fdrEdate__;

    /** FCB_MARK mapping */
    private long fcbMark__;

    /** BIN_SID mapping */
    private Long binSid__;
    /** 更新者名 */
    private String editUsrName__;
    /** 更新者状態区分 */
    private String editUsrJkbn__;
    /**
     * <p>Default Constructor
     */
    public CvtFileInfModel() {
    }

    /**
     * <p>get FCB_SID value
     * @return FCB_SID value
     */
    public int getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>set FCB_SID value
     * @param fcbSid FCB_SID value
     */
    public void setFcbSid(int fcbSid) {
        fcbSid__ = fcbSid;
    }

    /**
     * <p>get FDR_PARENT_SID value
     * @return FDR_PARENT_SID value
     */
    public int getFdrParentSid() {
        return fdrParentSid__;
    }

    /**
     * <p>set FDR_PARENT_SID value
     * @param fdrParentSid FDR_PARENT_SID value
     */
    public void setFdrParentSid(int fdrParentSid) {
        fdrParentSid__ = fdrParentSid;
    }

    /**
     * <p>get FDR_VER_KBN value
     * @return FDR_VER_KBN value
     */
    public int getFdrVerKbn() {
        return fdrVerKbn__;
    }

    /**
     * <p>set FDR_VER_KBN value
     * @param fdrVerKbn FDR_VER_KBN value
     */
    public void setFdrVerKbn(int fdrVerKbn) {
        fdrVerKbn__ = fdrVerKbn;
    }

    /**
     * <p>get FDR_LEVEL value
     * @return FDR_LEVEL value
     */
    public int getFdrLevel() {
        return fdrLevel__;
    }

    /**
     * <p>set FDR_LEVEL value
     * @param fdrLevel FDR_LEVEL value
     */
    public void setFdrLevel(int fdrLevel) {
        fdrLevel__ = fdrLevel;
    }

    /**
     * <p>get FDR_AUID value
     * @return FDR_AUID value
     */
    public int getFdrAuid() {
        return fdrAuid__;
    }

    /**
     * <p>set FDR_AUID value
     * @param fdrAuid FDR_AUID value
     */
    public void setFdrAuid(int fdrAuid) {
        fdrAuid__ = fdrAuid;
    }

    /**
     * <p>get FDR_ADATE value
     * @return FDR_ADATE value
     */
    public UDate getFdrAdate() {
        return fdrAdate__;
    }

    /**
     * <p>set FDR_ADATE value
     * @param fdrAdate FDR_ADATE value
     */
    public void setFdrAdate(UDate fdrAdate) {
        fdrAdate__ = fdrAdate;
    }

    /**
     * <p>get FDR_EUID value
     * @return FDR_EUID value
     */
    public int getFdrEuid() {
        return fdrEuid__;
    }

    /**
     * <p>set FDR_EUID value
     * @param fdrEuid FDR_EUID value
     */
    public void setFdrEuid(int fdrEuid) {
        fdrEuid__ = fdrEuid;
    }

    /**
     * <p>get FDR_EDATE value
     * @return FDR_EDATE value
     */
    public UDate getFdrEdate() {
        return fdrEdate__;
    }

    /**
     * <p>set FDR_EDATE value
     * @param fdrEdate FDR_EDATE value
     */
    public void setFdrEdate(UDate fdrEdate) {
        fdrEdate__ = fdrEdate;
    }

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>editUsrName を取得します。
     * @return editUsrName
     */
    public String getEditUsrName() {
        return editUsrName__;
    }

    /**
     * <p>editUsrName をセットします。
     * @param editUsrName editUsrName
     */
    public void setEditUsrName(String editUsrName) {
        editUsrName__ = editUsrName;
    }

    /**
     * <p>editUsrJkbn を取得します。
     * @return editUsrJkbn
     */
    public String getEditUsrJkbn() {
        return editUsrJkbn__;
    }

    /**
     * <p>editUsrJkbn をセットします。
     * @param editUsrJkbn editUsrJkbn
     */
    public void setEditUsrJkbn(String editUsrJkbn) {
        editUsrJkbn__ = editUsrJkbn;
    }

    /**
     * <p>fcbMark を取得します。
     * @return fcbMark
     */
    public long getFcbMark() {
        return fcbMark__;
    }

    /**
     * <p>fcbMark をセットします。
     * @param fcbMark fcbMark
     */
    public void setFcbMark(long fcbMark) {
        fcbMark__ = fcbMark;
    }

    /**
     * <p>fileName を取得します。
     * @return fileName
     */
    public String getFileName() {
        return fileName__;
    }

    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }

    /**
     * <p>fileExtension を取得します。
     * @return fileExtension
     */
    public String getFileExtension() {
        return fileExtension__;
    }

    /**
     * <p>fileExtension をセットします。
     * @param fileExtension fileExtension
     */
    public void setFileExtension(String fileExtension) {
        fileExtension__ = fileExtension;
    }

    /**
     * <p>fflFileSize を取得します。
     * @return fflFileSize
     */
    public long getFflFileSize() {
        return fflFileSize__;
    }

    /**
     * <p>fflFileSize をセットします。
     * @param fflFileSize fflFileSize
     */
    public void setFflFileSize(long fflFileSize) {
        fflFileSize__ = fflFileSize;
    }

}
