package jp.groupsession.v2.man.man080;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 自動バックアップ設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man080ParamModel extends AbstractParamModel {
    /** 間隔 */
    private int man080Interval__ = -1;
    /** 曜日 */
    private int man080dow__ = 0;
    /** 週 */
    private int man080weekmonth__ = 1;
    /** 曜日(月) */
    private int man080monthdow__ = 1;
    /** 世代 */
    private int man080generation__ = 1;
    /** バックアップファイル名 */
    private String man080backupFile__ = null;
    /** ZIP形式出力区分 */
    private int man080zipOutputKbn__ = GSConstMain.ZIP_BACKUP_FLG_ON;

    //表示項目
    /** バッチ起動時間 */
    private String man080batchHour__ = null;
    /** 曜日一覧 */
    private List<LabelValueBean> dowList__ = null;
    /** 週一覧 */
    private List<LabelValueBean> weekmonthList__ = null;
    /** 世代一覧 */
    private List<LabelValueBean> generationList__ = null;
    /** ファイル一覧 */
    private List<Man080FileModel> fileDataList__ = null;

    /**
     * <p>dowList を取得します。
     * @return dowList
     */
    public List<LabelValueBean> getDowList() {
        return dowList__;
    }
    /**
     * <p>dowList をセットします。
     * @param dowList dowList
     */
    public void setDowList(List<LabelValueBean> dowList) {
        dowList__ = dowList;
    }
    /**
     * <p>generationList を取得します。
     * @return generationList
     */
    public List<LabelValueBean> getGenerationList() {
        return generationList__;
    }
    /**
     * <p>generationList をセットします。
     * @param generationList generationList
     */
    public void setGenerationList(List<LabelValueBean> generationList) {
        generationList__ = generationList;
    }
    /**
     * <p>man080batchHour を取得します。
     * @return man080batchHour
     */
    public String getMan080batchHour() {
        return man080batchHour__;
    }
    /**
     * <p>man080batchHour をセットします。
     * @param man080batchHour man080batchHour
     */
    public void setMan080batchHour(String man080batchHour) {
        man080batchHour__ = man080batchHour;
    }
    /**
     * <p>man080dow を取得します。
     * @return man080dow
     */
    public int getMan080dow() {
        return man080dow__;
    }
    /**
     * <p>man080dow をセットします。
     * @param man080dow man080dow
     */
    public void setMan080dow(int man080dow) {
        man080dow__ = man080dow;
    }
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
     * <p>man080generation を取得します。
     * @return man080generation
     */
    public int getMan080generation() {
        return man080generation__;
    }
    /**
     * <p>man080generation をセットします。
     * @param man080generation man080generation
     */
    public void setMan080generation(int man080generation) {
        man080generation__ = man080generation;
    }
    /**
     * <p>man080Interval を取得します。
     * @return man080Interval
     */
    public int getMan080Interval() {
        return man080Interval__;
    }
    /**
     * <p>man080Interval をセットします。
     * @param man080Interval man080Interval
     */
    public void setMan080Interval(int man080Interval) {
        man080Interval__ = man080Interval;
    }
    /**
     * <p>man080monthdow を取得します。
     * @return man080monthdow
     */
    public int getMan080monthdow() {
        return man080monthdow__;
    }
    /**
     * <p>man080monthdow をセットします。
     * @param man080monthdow man080monthdow
     */
    public void setMan080monthdow(int man080monthdow) {
        man080monthdow__ = man080monthdow;
    }
    /**
     * <p>man080weekmonth を取得します。
     * @return man080weekmonth
     */
    public int getMan080weekmonth() {
        return man080weekmonth__;
    }
    /**
     * <p>man080weekmonth をセットします。
     * @param man080weekmonth man080weekmonth
     */
    public void setMan080weekmonth(int man080weekmonth) {
        man080weekmonth__ = man080weekmonth;
    }
    /**
     * <p>weekmonthList を取得します。
     * @return weekmonthList
     */
    public List<LabelValueBean> getWeekmonthList() {
        return weekmonthList__;
    }
    /**
     * <p>weekmonthList をセットします。
     * @param weekmonthList weekmonthList
     */
    public void setWeekmonthList(List<LabelValueBean> weekmonthList) {
        weekmonthList__ = weekmonthList;
    }
    /**
     * <p>man080backupFile を取得します。
     * @return man080backupFile
     */
    public String getMan080backupFile() {
        return man080backupFile__;
    }
    /**
     * <p>man080backupFile をセットします。
     * @param man080backupFile man080backupFile
     */
    public void setMan080backupFile(String man080backupFile) {
        man080backupFile__ = man080backupFile;
    }
    /**
     * <p>man080zipOutputKbn を取得します。
     * @return man080zipOutputKbn
     */
    public int getMan080zipOutputKbn() {
        return man080zipOutputKbn__;
    }
    /**
     * <p>man080zipOutputKbn をセットします。
     * @param man080zipOutputKbn man080zipOutputKbn
     */
    public void setMan080zipOutputKbn(int man080zipOutputKbn) {
        man080zipOutputKbn__ = man080zipOutputKbn;
    }
}