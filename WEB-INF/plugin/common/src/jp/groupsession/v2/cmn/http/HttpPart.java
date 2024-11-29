package jp.groupsession.v2.cmn.http;

import java.io.File;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] マルチパートリクエスト時の各パート毎情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HttpPart extends AbstractModel {
    /** パラメータ名 */
    private String paramName__ = null;

    /** 文字コード */
    private String charset__ = null;
    /* 文字列 */
    private String strValue__ = null;

    /** ファイル名 */
    private String fileName__ = null;
    /** ファイルパス */
    private File file__ = null;

    public String getParamName() {
        return paramName__;
    }
    public void setParamName(String paramName) {
        paramName__ = paramName;
    }
    public String getFileName() {
        return fileName__;
    }
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    public File getFile() {
        return file__;
    }
    public void setFile(File file) {
        file__ = file;
    }
    public String getStrValue() {
        return strValue__;
    }
    public String getCharset() {
        return charset__;
    }
    public void setCharset(String charset) {
        charset__ = charset;
    }
    public void setStrValue(String strValue) {
        strValue__ = strValue;
    }
}
