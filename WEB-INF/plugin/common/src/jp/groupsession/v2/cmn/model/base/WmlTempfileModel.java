package jp.groupsession.v2.cmn.model.base;

import java.io.File;
import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;

/**
 * <p>WML_TEMPFILE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlTempfileModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WTF_SID mapping */
    private long wtfSid__;
    /** WTF_FILE_NAME mapping */
    private String wtfFileName__;
    /** WTF_FILE_PATH mapping */
    private String wtfFilePath__;
    /** WTF_FILE_EXTENSION mapping */
    private String wtfFileExtension__;
    /** WTF_FILE_SIZE mapping */
    private long wtfFileSize__;
    /** WTF_AUID mapping */
    private int wtfAuid__;
    /** WTF_ADATE mapping */
    private UDate wtfAdate__;
    /** WTF_EUID mapping */
    private int wtfEuid__;
    /** WTF_EDATE mapping */
    private UDate wtfEdate__;
    /** WTF_JKBN mapping */
    private int wtfJkbn__;
    /** WTF_HTMLMAIL mapping */
    private int wtfHtmlmail__;
    /** WTF_CHARSET mapping */
    private String wtfCharset__;
    /** WTF_FILE_DATA mapping */
    private File wtfFileData__;
    /** WTF_FILE_DATA mapping */
    private long wtfFileDataOid__;

    /** ファイナライズで添付ファイルの削除有無 通常は1=削除 0=削除しない*/
    private int finalizeRmFilekbn__ = GSConstCommon.TEMPFILE_FINALIZE_DEL;

    /**
     * <p>Default Constructor
     */
    public WmlTempfileModel() {
    }

    /**
     * <p>get WMD_MAILNUM value
     * @return WMD_MAILNUM value
     */
    public long getWmdMailnum() {
        return wmdMailnum__;
    }

    /**
     * <p>set WMD_MAILNUM value
     * @param wmdMailnum WMD_MAILNUM value
     */
    public void setWmdMailnum(long wmdMailnum) {
        wmdMailnum__ = wmdMailnum;
    }

    /**
     * <p>get WTF_SID value
     * @return WTF_SID value
     */
    public long getWtfSid() {
        return wtfSid__;
    }

    /**
     * <p>set WTF_SID value
     * @param wtfSid WTF_SID value
     */
    public void setWtfSid(long wtfSid) {
        wtfSid__ = wtfSid;
    }

    /**
     * <p>get WTF_FILE_NAME value
     * @return WTF_FILE_NAME value
     */
    public String getWtfFileName() {
        return wtfFileName__;
    }

    /**
     * <p>set WTF_FILE_NAME value
     * @param wtfFileName WTF_FILE_NAME value
     */
    public void setWtfFileName(String wtfFileName) {
        wtfFileName__ = wtfFileName;
    }

    /**
     * <p>get WTF_FILE_PATH value
     * @return WTF_FILE_PATH value
     */
    public String getWtfFilePath() {
        return wtfFilePath__;
    }

    /**
     * <p>set WTF_FILE_PATH value
     * @param wtfFilePath WTF_FILE_PATH value
     */
    public void setWtfFilePath(String wtfFilePath) {
        wtfFilePath__ = wtfFilePath;
    }

    /**
     * <p>get WTF_FILE_EXTENSION value
     * @return WTF_FILE_EXTENSION value
     */
    public String getWtfFileExtension() {
        return wtfFileExtension__;
    }

    /**
     * <p>set WTF_FILE_EXTENSION value
     * @param wtfFileExtension WTF_FILE_EXTENSION value
     */
    public void setWtfFileExtension(String wtfFileExtension) {
        wtfFileExtension__ = wtfFileExtension;
    }

    /**
     * <p>get WTF_FILE_SIZE value
     * @return WTF_FILE_SIZE value
     */
    public long getWtfFileSize() {
        return wtfFileSize__;
    }

    /**
     * <p>set WTF_FILE_SIZE value
     * @param wtfFileSize WTF_FILE_SIZE value
     */
    public void setWtfFileSize(long wtfFileSize) {
        wtfFileSize__ = wtfFileSize;
    }

    /**
     * <p>get WTF_AUID value
     * @return WTF_AUID value
     */
    public int getWtfAuid() {
        return wtfAuid__;
    }

    /**
     * <p>set WTF_AUID value
     * @param wtfAuid WTF_AUID value
     */
    public void setWtfAuid(int wtfAuid) {
        wtfAuid__ = wtfAuid;
    }

    /**
     * <p>get WTF_ADATE value
     * @return WTF_ADATE value
     */
    public UDate getWtfAdate() {
        return wtfAdate__;
    }

    /**
     * <p>set WTF_ADATE value
     * @param wtfAdate WTF_ADATE value
     */
    public void setWtfAdate(UDate wtfAdate) {
        wtfAdate__ = wtfAdate;
    }

    /**
     * <p>get WTF_EUID value
     * @return WTF_EUID value
     */
    public int getWtfEuid() {
        return wtfEuid__;
    }

    /**
     * <p>set WTF_EUID value
     * @param wtfEuid WTF_EUID value
     */
    public void setWtfEuid(int wtfEuid) {
        wtfEuid__ = wtfEuid;
    }

    /**
     * <p>get WTF_EDATE value
     * @return WTF_EDATE value
     */
    public UDate getWtfEdate() {
        return wtfEdate__;
    }

    /**
     * <p>set WTF_EDATE value
     * @param wtfEdate WTF_EDATE value
     */
    public void setWtfEdate(UDate wtfEdate) {
        wtfEdate__ = wtfEdate;
    }

    /**
     * <p>get WTF_JKBN value
     * @return WTF_JKBN value
     */
    public int getWtfJkbn() {
        return wtfJkbn__;
    }

    /**
     * <p>set WTF_JKBN value
     * @param wtfJkbn WTF_JKBN value
     */
    public void setWtfJkbn(int wtfJkbn) {
        wtfJkbn__ = wtfJkbn;
    }

    /**
     * <p>get WTF_HTMLMAIL value
     * @return WTF_HTMLMAIL value
     */
    public int getWtfHtmlmail() {
        return wtfHtmlmail__;
    }

    /**
     * <p>set WTF_HTMLMAIL value
     * @param wtfHtmlmail WTF_HTMLMAIL value
     */
    public void setWtfHtmlmail(int wtfHtmlmail) {
        wtfHtmlmail__ = wtfHtmlmail;
    }

    /**
     * <p>get WTF_CHARSET value
     * @return WTF_CHARSET value
     */
    public String getWtfCharset() {
        return wtfCharset__;
    }

    /**
     * <p>set WTF_CHARSET value
     * @param wtfCharset WTF_CHARSET value
     */
    public void setWtfCharset(String wtfCharset) {
        wtfCharset__ = wtfCharset;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(wtfSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wtfFileName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wtfFilePath__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wtfFileExtension__, ""));
        buf.append(",");
        buf.append(wtfFileSize__);
        buf.append(",");
        buf.append(wtfAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wtfAdate__, ""));
        buf.append(",");
        buf.append(wtfEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wtfEdate__, ""));
        buf.append(",");
        buf.append(wtfJkbn__);
        buf.append(",");
        buf.append(wtfHtmlmail__);
        buf.append(",");
        buf.append(NullDefault.getString(wtfCharset__, ""));
        return buf.toString();
    }

    /**
     * <p>wtfFileData を取得します。
     * @return wtfFileData
     */
    public File getWtfFileData() {
        return wtfFileData__;
    }

    /**
     * <p>wtfFileData をセットします。
     * @param wtfFileData wtfFileData
     */
    public void setWtfFileData(File wtfFileData) {
        wtfFileData__ = wtfFileData;
    }

    /**
     * @return finalizeRmFilekbn
     */
    public int getFinalizeRmFilekbn() {
        return finalizeRmFilekbn__;
    }

    /**
     * @param finalizeRmFilekbn セットする finalizeRmFilekbn
     */
    public void setFinalizeRmFilekbn(int finalizeRmFilekbn) {
        finalizeRmFilekbn__ = finalizeRmFilekbn;
    }
    /**
     * <p>wtfFileDataOid を取得します。
     * @return wtfFileDataOid
     */
    public long getWtfFileDataOid() {
        return wtfFileDataOid__;
    }

    /**
     * <p>wtfFileDataOid をセットします。
     * @param wtfFileDataOid wtfFileDataOid
     */
    public void setWtfFileDataOid(long wtfFileDataOid) {
        wtfFileDataOid__ = wtfFileDataOid;
    }

    /** finalize処理
     * @see java.lang.Object#finalize()
     * @throws Throwable IOToolsException
     */
    @Override
    protected void finalize() throws Throwable {
//        removeTempFile();
        super.finalize();
    }


    /**
     * 一時ファイルがある場合に削除を行います。
     * @throws IOToolsException 一時ファイルの削除に失敗
     */
    public void removeTempFile() throws IOToolsException {
        if (wtfFileData__ == null) {
            return;
        }
        if (!wtfFileData__.exists()) {
            return;
        }
        if (finalizeRmFilekbn__ == GSConstCommon.TEMPFILE_FINALIZE_NOTDEL) {
            return;
        }

        String parentDir = wtfFileData__.getParent();
        if (parentDir != null) {
            IOTools.deleteDir(parentDir);
        }
    }
}
