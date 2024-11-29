package jp.groupsession.v2.cmn.model.base;

import java.io.File;
import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;

/**
 * <p>CMN_BINF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBinfModel implements Serializable {

    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);
    /** BIN_FILE_NAME mapping */
    private String binFileName__;
    /** BIN_FILE_PATH mapping */
    private String binFilePath__;
    /** BIN_FILE_EXTENSION mapping */
    private String binFileExtension__;
    /** BIN_FILE_SIZE mapping */
    private long binFileSize__;
    /** BIN_ADUSER mapping */
    private int binAduser__;
    /** BIN_ADDATE mapping */
    private UDate binAddate__;
    /** BIN_UPUSER mapping */
    private int binUpuser__;
    /** BIN_UPDATE mapping */
    private UDate binUpdate__;
    /** BIN_JKBN mapping */
    private int binJkbn__;
    /** BIN_FILE_KBN mapping */
    private int binFilekbn__ = 0;
    /** BIN_FILE_DATA mapping */
    private File binFileData__;
    /** BIN_FILE_DATA mapping */
    private long binFileDataOid__;

    /** ファイナライズで添付ファイルの削除有無 通常は1=削除 0=削除しない*/
    private int finalizeRmFilekbn__ = GSConstCommon.TEMPFILE_FINALIZE_DEL;
    /** 画面表示用ファイルサイズ文字列 */
    private String binFileSizeDsp__;

    /**
     * <p>binFilekbn を取得します。
     * @return binFilekbn
     */
    public int getBinFilekbn() {
        return binFilekbn__;
    }

    /**
     * <p>binFilekbn をセットします。
     * @param binFilekbn binFilekbn
     */
    public void setBinFilekbn(int binFilekbn) {
        binFilekbn__ = binFilekbn;
    }

    /**
     * <p>Default Constructor
     */
    public CmnBinfModel() {
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
     * <p>get BIN_FILE_NAME value
     * @return BIN_FILE_NAME value
     */
    public String getBinFileName() {
        return binFileName__;
    }

    /**
     * <p>set BIN_FILE_NAME value
     * @param binFileName BIN_FILE_NAME value
     */
    public void setBinFileName(String binFileName) {
        binFileName__ = binFileName;
    }

    /**
     * <p>get BIN_FILE_PATH value
     * @return BIN_FILE_PATH value
     */
    public String getBinFilePath() {
        return binFilePath__;
    }

    /**
     * <p>set BIN_FILE_PATH value
     * @param binFilePath BIN_FILE_PATH value
     */
    public void setBinFilePath(String binFilePath) {
        binFilePath__ = binFilePath;
    }

    /**
     * <p>get BIN_FILE_EXTENSION value
     * @return BIN_FILE_EXTENSION value
     */
    public String getBinFileExtension() {
        return binFileExtension__;
    }

    /**
     * <p>set BIN_FILE_EXTENSION value
     * @param binFileExtension BIN_FILE_EXTENSION value
     */
    public void setBinFileExtension(String binFileExtension) {
        binFileExtension__ = binFileExtension;
    }

    /**
     * <p>get BIN_FILE_SIZE value
     * @return BIN_FILE_SIZE value
     */
    public long getBinFileSize() {
        return binFileSize__;
    }

    /**
     * <p>set BIN_FILE_SIZE value
     * @param binFileSize BIN_FILE_SIZE value
     */
    public void setBinFileSize(long binFileSize) {
        binFileSize__ = binFileSize;
    }

    /**
     * <p>get BIN_ADUSER value
     * @return BIN_ADUSER value
     */
    public int getBinAduser() {
        return binAduser__;
    }

    /**
     * <p>set BIN_ADUSER value
     * @param binAduser BIN_ADUSER value
     */
    public void setBinAduser(int binAduser) {
        binAduser__ = binAduser;
    }

    /**
     * <p>get BIN_ADDATE value
     * @return BIN_ADDATE value
     */
    public UDate getBinAddate() {
        return binAddate__;
    }

    /**
     * <p>set BIN_ADDATE value
     * @param binAddate BIN_ADDATE value
     */
    public void setBinAddate(UDate binAddate) {
        binAddate__ = binAddate;
    }

    /**
     * <p>get BIN_UPUSER value
     * @return BIN_UPUSER value
     */
    public int getBinUpuser() {
        return binUpuser__;
    }

    /**
     * <p>set BIN_UPUSER value
     * @param binUpuser BIN_UPUSER value
     */
    public void setBinUpuser(int binUpuser) {
        binUpuser__ = binUpuser;
    }

    /**
     * <p>get BIN_UPDATE value
     * @return BIN_UPDATE value
     */
    public UDate getBinUpdate() {
        return binUpdate__;
    }

    /**
     * <p>set BIN_UPDATE value
     * @param binUpdate BIN_UPDATE value
     */
    public void setBinUpdate(UDate binUpdate) {
        binUpdate__ = binUpdate;
    }

    /**
     * <p>get BIN_JKBN value
     * @return BIN_JKBN value
     */
    public int getBinJkbn() {
        return binJkbn__;
    }

    /**
     * <p>set BIN_JKBN value
     * @param binJkbn BIN_JKBN value
     */
    public void setBinJkbn(int binJkbn) {
        binJkbn__ = binJkbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(binSid__);
        buf.append(",");
        buf.append(NullDefault.getString(binFileName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(binFilePath__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(binFileExtension__, ""));
        buf.append(",");
        buf.append(binFileSize__);
        buf.append(",");
        buf.append(binAduser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(binAddate__, ""));
        buf.append(",");
        buf.append(binUpuser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(binUpdate__, ""));
        buf.append(",");
        buf.append(binJkbn__);
        return buf.toString();
    }

    /**
     * <p>binFileData を取得します。
     * @return binFileData
     */
    public File getBinFileData() {
        return binFileData__;
    }

    /**
     * <p>binFileData をセットします。
     * @param binFileData binFileData
     */
    public void setBinFileData(File binFileData) {
        binFileData__ = binFileData;
    }

    /**
     * <p>binFileDataOid を取得します。
     * @return binFileDataOid
     */
    public long getBinFileDataOid() {
        return binFileDataOid__;
    }

    /**
     * <p>binFileDataOid をセットします。
     * @param binFileDataOid binFileDataOid
     */
    public void setBinFileDataOid(long binFileDataOid) {
        binFileDataOid__ = binFileDataOid;
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

    /** finalize処理
     * @see java.lang.Object#finalize()
     * @throws Throwable IOToolsException
     */
    @Override
    protected void finalize() throws Throwable {
        removeTempFileFinal();
        super.finalize();
    }


    /**
     * 一時ファイルがある場合に削除を行います。
     * 一次ファイルがGSContext.TEMP_PATH_BLOBの下の場合、
     * 展開ディレクトリ（GSContext.TEMP_PATH_BLOB/{日付}/{乱数}）ごと消します
     * @throws IOToolsException 一時ファイルの削除に失敗
     */
    public void removeTempFile() throws IOToolsException {
        if (binFileData__ == null
        || !binFileData__.exists()
        || finalizeRmFilekbn__ == GSConstCommon.TEMPFILE_FINALIZE_NOTDEL) {
            return;
        }

        String parentDir = binFileData__.getParent();
        if (parentDir != null) {
            if (parentDir.indexOf(GSContext.TEMP_PATH_BLOB) >= 0) {
                IOTools.deleteDir(parentDir);
            } else {
                IOTools.deleteFile(binFileData__);
            }
        }
    }
    /**
     * 一時ファイルがある場合に削除を行います。
     * 一時ファイル保管後、一時ファイルへの参照をnullにします。
     *
     * @throws IOToolsException 一時ファイルの削除に失敗
     */
    public void removeBinFileData() throws IOToolsException {
        if (binFileData__ == null
        || !binFileData__.exists()
        || finalizeRmFilekbn__ == GSConstCommon.TEMPFILE_FINALIZE_NOTDEL) {
            return;
        }
        String parentDir = binFileData__.getParent();

        IOTools.deleteFile(binFileData__);
        binFileData__ = null;

        if (parentDir != null
                && parentDir.indexOf(GSContext.TEMP_PATH_BLOB) >= 0) {
            //親ディレクトリが空になったので親ディレクトリを削除
            File dir = new File(parentDir);
            File[] files = dir.listFiles();
            if (files != null && files.length == 0 ) {
                IOTools.deleteDir(parentDir);
            }
        }
    }

    /**
     * 一時ファイルがある場合に削除を行います（ファイナライズで実行される場合）。
     * @throws IOToolsException 一時ファイルの削除に失敗
     */
    public void removeTempFileFinal() throws IOToolsException {
        if (binFileData__ == null
        || !binFileData__.exists()
        || finalizeRmFilekbn__ == GSConstCommon.TEMPFILE_FINALIZE_NOTDEL) {
            return;
        }

        String parentDir = binFileData__.getParent();
        if (parentDir != null) {
            if (parentDir.indexOf(GSContext.TEMP_PATH_BLOB) >= 0) {
                IOTools.deleteDir(parentDir);
            } else {
                //30分経過していないものは削除しない
                UDate now = new UDate();
                UDate modifiedDate = UDate.getInstance(binFileData__.lastModified());
                if (UDateUtil.diffMinute(now, modifiedDate) > 30) {
                    IOTools.deleteFile(binFileData__);
                }
            }
        }
    }

    /**　　
     * @return binFileSizeDsp
     */
    public String getBinFileSizeDsp() {
        return binFileSizeDsp__;
    }

    /**
     * @param binFileSizeDsp セットする binFileSizeDsp
     */
    public void setBinFileSizeDsp(String binFileSizeDsp) {
        binFileSizeDsp__ = binFileSizeDsp;
    }
}
