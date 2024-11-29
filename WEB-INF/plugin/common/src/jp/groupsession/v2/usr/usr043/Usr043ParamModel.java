package jp.groupsession.v2.usr.usr043;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.usr.usr042.Usr042ParamModel;

/**
 * <br>[機  能] ユーザ情報 カテゴリ設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr043ParamModel extends Usr042ParamModel {
    /** カテゴリSID */
    private int catSID__;
    /** カテゴリ情報List */
    private ArrayList<CmnLabelUsrCategoryModel> catList__  = null;
    /** チェックされている並び順 */
    private String usr043SortRadio__;
    /** 表示順リスト */
    private String[] usr043KeyList__;
    /** 処理区分 */
    private int usr043ProcMode__;
    /** 編集カテゴリSID */
    private int usr043EditSid__ = 0;


    /**
     * <p>catSID を取得します。
     * @return catSID
     */
    public int getCatSID() {
        return catSID__;
    }
    /**
     * <p>catSID をセットします。
     * @param catSID catSID
     */
    public void setCatSID(int catSID) {
        catSID__ = catSID;
    }
    /**
     * <p>catList を取得します。
     * @return catList
     */
    public ArrayList<CmnLabelUsrCategoryModel> getCatList() {
        return catList__;
    }
    /**
     * <p>catList をセットします。
     * @param catList catList
     */
    public void setCatList(ArrayList<CmnLabelUsrCategoryModel> catList) {
        catList__ = catList;
    }
    /**
     * <p>usr043SortRadio を取得します。
     * @return usr043SortRadio
     */
    public String getUsr043SortRadio() {
        return usr043SortRadio__;
    }
    /**
     * <p>usr043SortRadio をセットします。
     * @param usr043SortRadio usr043SortRadio
     */
    public void setUsr043SortRadio(String usr043SortRadio) {
        usr043SortRadio__ = usr043SortRadio;
    }
    /**
     * <p>usr043KeyList を取得します。
     * @return usr043KeyList
     */
    public String[] getUsr043KeyList() {
        return usr043KeyList__;
    }
    /**
     * <p>usr043KeyList をセットします。
     * @param usr043KeyList usr043KeyList
     */
    public void setUsr043KeyList(String[] usr043KeyList) {
        usr043KeyList__ = usr043KeyList;
    }
    /**
     * <p>usr043ProcMode を取得します。
     * @return usr043ProcMode
     */
    public int getUsr043ProcMode() {
        return usr043ProcMode__;
    }
    /**
     * <p>usr043ProcMode をセットします。
     * @param usr043ProcMode usr043ProcMode
     */
    public void setUsr043ProcMode(int usr043ProcMode) {
        usr043ProcMode__ = usr043ProcMode;
    }
    /**
     * <p>usr043EditSid を取得します。
     * @return usr043EditSid
     */
    public int getUsr043EditSid() {
        return usr043EditSid__;
    }
    /**
     * <p>usr043EditSid をセットします。
     * @param usr043EditSid usr043EditSid
     */
    public void setUsr043EditSid(int usr043EditSid) {
        usr043EditSid__ = usr043EditSid;
    }

}