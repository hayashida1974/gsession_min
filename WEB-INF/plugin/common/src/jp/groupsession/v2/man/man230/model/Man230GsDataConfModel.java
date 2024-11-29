package jp.groupsession.v2.man.man230.model;

/**
 * 
 * <br>[機  能]GSData.confのデータを保持するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man230GsDataConfModel {

    /** GSDATA_DIR */
    private String gsDataDir__ = null;
    /** BACKUP_DIR */
    private String backUpDir__ = null;
    /** FILEKANRI_DIR */
    private String fileKanriDir__ = null;
    /** WEBMAIL_DIR */
    private String webMailDir__ = null;
    /**
     * <p>gsDataDir を取得します。
     * @return gsDataDir
     */
    public String getGsDataDir() {
        return gsDataDir__;
    }
    /**
     * <p>gsDataDir をセットします。
     * @param gsDataDir gsDataDir
     */
    public void setGsDataDir(String gsDataDir) {
        gsDataDir__ = gsDataDir;
    }
    /**
     * <p>backUpDir を取得します。
     * @return backUpDir
     */
    public String getBackUpDir() {
        return backUpDir__;
    }
    /**
     * <p>backUpDir をセットします。
     * @param backUpDir backUpDir
     */
    public void setBackUpDir(String backUpDir) {
        backUpDir__ = backUpDir;
    }
    /**
     * <p>fileKanriDir を取得します。
     * @return fileKanriDir
     */
    public String getFileKanriDir() {
        return fileKanriDir__;
    }
    /**
     * <p>fileKanriDir をセットします。
     * @param fileKanriDir fileKanriDir
     */
    public void setFileKanriDir(String fileKanriDir) {
        fileKanriDir__ = fileKanriDir;
    }
    /**
     * <p>webMailDir を取得します。
     * @return webMailDir
     */
    public String getWebMailDir() {
        return webMailDir__;
    }
    /**
     * <p>webMailDir をセットします。
     * @param webMailDir webMailDir
     */
    public void setWebMailDir(String webMailDir) {
        webMailDir__ = webMailDir;
    }



}
