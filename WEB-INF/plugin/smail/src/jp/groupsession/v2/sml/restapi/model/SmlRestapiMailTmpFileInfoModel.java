package jp.groupsession.v2.sml.restapi.model;

/**
 *
 * <br>[機  能] メール情報 添付ファイル情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlRestapiMailTmpFileInfoModel {
    /** バイナリSID */
    private long binSid__;
    /** ファイル名 */
    private String fileName__;
    /** ファイルサイズ(Byte) */
    private int fileSizeByteNum__;
    /**
     * <p>binSid を取得します。
     * @return binSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel#binSid__
     */
    public long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel#binSid__
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>fileName を取得します。
     * @return fileName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel#fileName__
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel#fileName__
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    /**
     * <p>fileSizeByteNum を取得します。
     * @return fileSizeByteNum
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel#fileSizeByteNum__
     */
    public int getFileSizeByteNum() {
        return fileSizeByteNum__;
    }
    /**
     * <p>fileSizeByteNum をセットします。
     * @param fileSizeByteNum fileSizeByteNum
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel#fileSizeByteNum__
     */
    public void setFileSizeByteNum(int fileSizeByteNum) {
        fileSizeByteNum__ = fileSizeByteNum;
    }
}
