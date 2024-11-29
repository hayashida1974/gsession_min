package jp.groupsession.v2.wml.restapi.model;

/**
 * <br>[機  能] WEBメールRESTAPI 添付ファイル情報保持クラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class TmpFileInfo {
        
    /** バイナリSID */
    private long binSid__ = 0;
    /** ファイル名 */
    private String fileName__ = null;
    /** ファイルサイズ（Byte） */
    private long fileSizeByteNum__ = 0;
    public long getBinSid() {
        return binSid__;
    }
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }
    public String getFileName() {
        return fileName__;
    }
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    public long getFileSizeByteNum() {
        return fileSizeByteNum__;
    }
    public void setFileSizeByteNum(long fileSizeByteNum) {
        fileSizeByteNum__ = fileSizeByteNum;
    }
}
