package jp.groupsession.v2.adr.adr280;

import java.util.ArrayList;
import jp.groupsession.v2.adr.adr010.Adr010ParamModel;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;

/**
 * <br>[機  能] アドレス帳 カテゴリ設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr280ParamModel extends Adr010ParamModel {
    /** カテゴリSID */
    private int catSID__;
    /** カテゴリ情報List */
    private ArrayList<AdrLabelCategoryModel> catList__  = null;
    /** チェックされている並び順 */
    private String adr280SortRadio__;
    /** 表示順リスト */
    private String[] adr280KeyList__;
    /** 処理区分 */
    private int adr280ProcMode__;
    /** 編集カテゴリSID */
    private int adr280EditSid__ = 0;
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
    public ArrayList<AdrLabelCategoryModel> getCatList() {
        return catList__;
    }
    /**
     * <p>catList をセットします。
     * @param catList catList
     */
    public void setCatList(ArrayList<AdrLabelCategoryModel> catList) {
        catList__ = catList;
    }
    /**
     * <p>adr280SortRadio を取得します。
     * @return adr280SortRadio
     */
    public String getAdr280SortRadio() {
        return adr280SortRadio__;
    }
    /**
     * <p>adr280SortRadio をセットします。
     * @param adr280SortRadio adr280SortRadio
     */
    public void setAdr280SortRadio(String adr280SortRadio) {
        adr280SortRadio__ = adr280SortRadio;
    }
    /**
     * <p>adr280KeyList を取得します。
     * @return adr280KeyList
     */
    public String[] getAdr280KeyList() {
        return adr280KeyList__;
    }
    /**
     * <p>adr280KeyList をセットします。
     * @param adr280KeyList adr280KeyList
     */
    public void setAdr280KeyList(String[] adr280KeyList) {
        adr280KeyList__ = adr280KeyList;
    }
    /**
     * <p>adr280ProcMode を取得します。
     * @return adr280ProcMode
     */
    public int getAdr280ProcMode() {
        return adr280ProcMode__;
    }
    /**
     * <p>adr280ProcMode をセットします。
     * @param adr280ProcMode adr280ProcMode
     */
    public void setAdr280ProcMode(int adr280ProcMode) {
        adr280ProcMode__ = adr280ProcMode;
    }
    /**
     * <p>adr280EditSid を取得します。
     * @return adr280EditSid
     */
    public int getAdr280EditSid() {
        return adr280EditSid__;
    }
    /**
     * <p>adr280EditSid をセットします。
     * @param adr280EditSid adr280EditSid
     */
    public void setAdr280EditSid(int adr280EditSid) {
        adr280EditSid__ = adr280EditSid;
    }
}