package jp.groupsession.v2.usr.usr044;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.usr.usr043.Usr043ParamModel;

/**
 * <br>[機  能] ユーザ情報 ラベル一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr044ParamModel extends Usr043ParamModel {

    /** ラベル情報List */
    private ArrayList<CmnLabelUsrModel> labelList__  = null;
    /** 処理区分 */
    private int usr044ProcMode__ = 0;
    /** 編集ラベルSID */
    private int labelEditSid__ = -1;
    /** チェックされている並び順 */
    private String usr044SortRadio__;
    /** 表示順リスト */
    private String[] usr044KeyList__;

    /** カテゴリ名 */
    private String usr044CatName__ = "";


    /**
     * <p>labelList を取得します。
     * @return labelList
     */
    public ArrayList<CmnLabelUsrModel> getLabelList() {
        return labelList__;
    }
    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(ArrayList<CmnLabelUsrModel> labelList) {
        labelList__ = labelList;
    }
    /**
     * <p>usr044ProcMode を取得します。
     * @return usr044ProcMode
     */
    public int getUsr044ProcMode() {
        return usr044ProcMode__;
    }
    /**
     * <p>usr044ProcMode をセットします。
     * @param usr044ProcMode usr044ProcMode
     */
    public void setUsr044ProcMode(int usr044ProcMode) {
        usr044ProcMode__ = usr044ProcMode;
    }
    /**
     * <p>labelEditSid を取得します。
     * @return labelEditSid
     */
    public int getLabelEditSid() {
        return labelEditSid__;
    }
    /**
     * <p>labelEditSid をセットします。
     * @param labelEditSid labelEditSid
     */
    public void setLabelEditSid(int labelEditSid) {
        labelEditSid__ = labelEditSid;
    }
    /**
     * <p>usr044SortRadio を取得します。
     * @return usr044SortRadio
     */
    public String getUsr044SortRadio() {
        return usr044SortRadio__;
    }
    /**
     * <p>usr044SortRadio をセットします。
     * @param usr044SortRadio usr044SortRadio
     */
    public void setUsr044SortRadio(String usr044SortRadio) {
        usr044SortRadio__ = usr044SortRadio;
    }
    /**
     * <p>usr044KeyList を取得します。
     * @return usr044KeyList
     */
    public String[] getUsr044KeyList() {
        return usr044KeyList__;
    }
    /**
     * <p>usr044KeyList をセットします。
     * @param usr044KeyList usr044KeyList
     */
    public void setUsr044KeyList(String[] usr044KeyList) {
        usr044KeyList__ = usr044KeyList;
    }
    /**
     * @return usr044CatName
     */
    public String getUsr044CatName() {
        return usr044CatName__;
    }
    /**
     * @param usr044CatName セットする usr044CatName
     */
    public void setUsr044CatName(String usr044CatName) {
        usr044CatName__ = usr044CatName;
    }

}