package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

/**
 * <p>メールの添付ファイル情報を保持するModel
 */
public class WmlMailFileModel implements Serializable {

    /** ファイル名 */
    private String fileName__ = null;
    /** ファイルパス */
    private String filePath__ = null;
    /** Content-Type */
    private String contentType__ = null;
    /** HTMLメール */
    private boolean htmlMail__ = false;

    /**
     * <p>contentType を取得します。
     * @return contentType
     */
    public String getContentType() {
        return contentType__;
    }
    /**
     * <p>contentType をセットします。
     * @param contentType contentType
     */
    public void setContentType(String contentType) {
        contentType__ = contentType;
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

}
