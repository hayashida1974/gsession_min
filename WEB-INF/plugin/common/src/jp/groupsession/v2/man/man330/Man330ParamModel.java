package jp.groupsession.v2.man.man330;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 所属情報一括設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330ParamModel extends AbstractParamModel {
    //入力項目
    /** タブ切り替えモード エクスポート:0 インポート:1 */
    private String man330cmdMode__ = GSConstMain.MODE_EXPORT;
    /** CSV出力項目 */
    private String[] man330CsvOutField__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] man330SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man330FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;

    /**
     * <br>[機  能] フォームパラメータをコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setParam(Object form) {
        super.setParam(form);
        Man330Form thisForm = (Man330Form) form;
        if (thisForm.getMan330CsvOutField() == null) {
            setMan330CsvOutField(null);
        }
        if (thisForm.getMan330SelectFiles() == null) {
            setMan330SelectFiles(null);
        }
    }

    /**
     * <p>impDataCnt を取得します。
     * @return impDataCnt
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }
    /**
     * <p>impDataCnt をセットします。
     * @param impDataCnt impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }
    /**
     * <p>man330cmdMode を取得します。
     * @return man330cmdMode
     */
    public String getMan330cmdMode() {
        return man330cmdMode__;
    }
    /**
     * <p>man330cmdMode をセットします。
     * @param man330cmdMode man330cmdMode
     */
    public void setMan330cmdMode(String man330cmdMode) {
        man330cmdMode__ = man330cmdMode;
    }
    /**
     * <p>man330CsvOutField を取得します。
     * @return man330CsvOutField
     */
    public String[] getMan330CsvOutField() {
        return man330CsvOutField__;
    }
    /**
     * <p>man330CsvOutField をセットします。
     * @param man330CsvOutField man330CsvOutField
     */
    public void setMan330CsvOutField(String[] man330CsvOutField) {
        man330CsvOutField__ = man330CsvOutField;
    }
    /**
     * <p>man330SelectFiles を取得します。
     * @return man330SelectFiles
     */
    public String[] getMan330SelectFiles() {
        return man330SelectFiles__;
    }
    /**
     * <p>man330SelectFiles をセットします。
     * @param man330SelectFiles man330SelectFiles
     */
    public void setMan330SelectFiles(String[] man330SelectFiles) {
        man330SelectFiles__ = man330SelectFiles;
    }
    /**
     * <p>man330FileLabelList を取得します。
     * @return man330FileLabelList
     */
    public ArrayList<LabelValueBean> getMan330FileLabelList() {
        return man330FileLabelList__;
    }
    /**
     * <p>man330FileLabelList をセットします。
     * @param man330FileLabelList man330FileLabelList
     */
    public void setMan330FileLabelList(ArrayList<LabelValueBean> man330FileLabelList) {
        man330FileLabelList__ = man330FileLabelList;
    }
}