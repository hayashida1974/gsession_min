package jp.groupsession.v2.cmn.formmodel.action;

import jp.co.sjts.util.struts.AbstractForm;

/**
*
* <br>[機  能] 添付ファイル削除 フォーム
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class TempFileDeleteForm extends AbstractForm {
    /** プラグイン名 */
    private String plugin__;

    /** テンポラリディレクトリID */
    private String tempDirId__ = "";

    /** サブディレクトリID */
    private String subDir__ = "";



    /** 削除する添付ファイル一覧 */
    private String[] files__;

    /**
     * <p>plugin を取得します。
     * @return plugin
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#plugin
     */
    public String getPlugin() {
        return plugin__;
    }

    /**
     * <p>plugin をセットします。
     * @param plugin plugin
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#plugin
     */
    public void setPlugin(String plugin) {
        plugin__ = plugin;
    }

    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#tempDirId
     */
    public String getTempDirId() {
        return tempDirId__;
    }

    /**
     * <p>tempDirId をセットします。
     * @param tempDirId tempDirId
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#tempDirId
     */
    public void setTempDirId(String tempDirId) {
        tempDirId__ = tempDirId;
    }

    /**
     * <p>files を取得します。
     * @return files
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#filest
     */
    public String[] getFiles() {
        return files__;
    }

    /**
     * <p>files をセットします。
     * @param files files
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#files
     */
    public void setFiles(String[] files) {
        files__ = files;
    }

    /**
     * <p>subDir を取得します。
     * @return subDir
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#subDir__
     */
    public String getSubDir() {
        return subDir__;
    }

    /**
     * <p>subDir をセットします。
     * @param subDir subDir
     * @see jp.groupsession.v2.cmn.formmodel.action.TempFileDeleteForm#subDir__
     */
    public void setSubDir(String subDir) {
        subDir__ = subDir;
    }
}
