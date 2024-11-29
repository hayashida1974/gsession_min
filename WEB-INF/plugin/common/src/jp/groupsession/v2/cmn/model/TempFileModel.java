package jp.groupsession.v2.cmn.model;

import java.io.File;


/**
 * <br>[機  能] メールで転送する添付ファイル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TempFileModel extends AbstractModel {

    /** ファイル */
    private File file__;
    /** 実際のファイル名 */
    private String fileName__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public TempFileModel() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param file ファイル
     */
    public TempFileModel(File file) {
        file__ = file;
        fileName__ = file.getName();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param file ファイル
     * @param fileName 実際のファイル名
     */
    public TempFileModel(File file, String fileName) {
        file__ = file;
        fileName__ = fileName;
    }

    /**
     * <p>file を取得します。
     * @return file
     */
    public File getFile() {
        return file__;
    }
    /**
     * <p>file をセットします。
     * @param file file
     */
    public void setFile(File file) {
        file__ = file;
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

}