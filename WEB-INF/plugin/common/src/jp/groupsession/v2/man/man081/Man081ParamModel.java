package jp.groupsession.v2.man.man081;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.man080.Man080FileModel;

/**
 * <br>[機  能] メイン 管理者設定 手動バックアップ設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man081ParamModel extends AbstractParamModel {
    /** バックアップファイル名 */
    private String man081backupFile__ = null;

    //表示項目
    /** ファイル一覧 */
    private List<Man080FileModel> fileDataList__ = null;

    /**
     * <p>fileDataList を取得します。
     * @return fileDataList
     */
    public List<Man080FileModel> getFileDataList() {
        return fileDataList__;
    }

    /**
     * <p>fileDataList をセットします。
     * @param fileDataList fileDataList
     */
    public void setFileDataList(List<Man080FileModel> fileDataList) {
        fileDataList__ = fileDataList;
    }

    /**
     * <p>man081backupFile を取得します。
     * @return man081backupFile
     */
    public String getMan081backupFile() {
        return man081backupFile__;
    }

    /**
     * <p>man081backupFile をセットします。
     * @param man081backupFile man081backupFile
     */
    public void setMan081backupFile(String man081backupFile) {
        man081backupFile__ = man081backupFile;
    }
}