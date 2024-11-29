package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール 添付ファイル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MailTempFileModel extends AbstractModel {

    /** バイナリーSID */
    private long binSid__ = new Long(0);
    /** ファイル名 */
    private String fileName__ = null;
    /** ファイルパス */
    private String filePath__ = null;
    /** ファイルサイズ */
    private long fileSize__ = 0;
    /** HTMLメール */
    private boolean htmlMail__ = false;
    /** charset */
    private String charset__ = null;
    /** ファイルサイズ(表示用) */
    private String fileSizeDsp__ = null;

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
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
     * <p>filePath を取得します。
     * @return filePath
     */
    public String getFilePath() {
        return filePath__;
    }
    /**
     * <p>filePath をセットします。
     * @param filePath filePath
     */
    public void setFilePath(String filePath) {
        filePath__ = filePath;
    }
    /**
     * <p>fileSize を取得します。
     * @return fileSize
     */
    public long getFileSize() {
        return fileSize__;
    }
    /**
     * <p>fileSize をセットします。
     * @param fileSize fileSize
     */
    public void setFileSize(long fileSize) {
        fileSize__ = fileSize;
    }
    /**
     * <p>charset を取得します。
     * @return charset
     */
    public String getCharset() {
        return charset__;
    }
    /**
     * <p>charset をセットします。
     * @param charset charset
     */
    public void setCharset(String charset) {
        charset__ = charset;
    }
    /**
     * <p>htmlMail を取得します。
     * @return htmlMail
     */
    public boolean isHtmlMail() {
        return htmlMail__;
    }
    /**
     * <p>htmlMail をセットします。
     * @param htmlMail htmlMail
     */
    public void setHtmlMail(boolean htmlMail) {
        htmlMail__ = htmlMail;
    }
    /**
     * @return fileSizeDsp
     */
    public String getFileSizeDsp() {
        return fileSizeDsp__;
    }
    /**
     * @param fileSizeDsp セットする fileSizeDsp
     */
    public void setFileSizeDsp(String fileSizeDsp) {
        fileSizeDsp__ = fileSizeDsp;
    }
}
