package jp.groupsession.v2.man.man100;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] メイン 管理者設定 役職マネージャー画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man100ParamModel extends AbstractParamModel {
    //入力項目
    /** チェックされている並び順 */
    private String man100SortRadio__;
    /** 役職表示順リスト */
    private String[] man100KeyList__;
    /** 処理区分 */
    private int man100ProcMode__;
    /** 修正対象の役職SID */
    private int man100EditPosSid__;

    //表示項目
    /** 役職リスト */
    private List<Man100PositionModel> posList__;

    /**
     * <p>posList を取得します。
     * @return posList
     */
    public List<Man100PositionModel> getPosList() {
        return posList__;
    }
    /**
     * <p>posList をセットします。
     * @param posList posList
     */
    public void setPosList(List<Man100PositionModel> posList) {
        posList__ = posList;
    }
    /**
     * <p>man100SortRadio を取得します。
     * @return man100SortRadio
     */
    public String getMan100SortRadio() {
        return man100SortRadio__;
    }
    /**
     * <p>man100SortRadio をセットします。
     * @param man100SortRadio man100SortRadio
     */
    public void setMan100SortRadio(String man100SortRadio) {
        man100SortRadio__ = man100SortRadio;
    }
    /**
     * <p>man100KeyList を取得します。
     * @return man100KeyList
     */
    public String[] getMan100KeyList() {
        return man100KeyList__;
    }
    /**
     * <p>man100KeyList をセットします。
     * @param man100KeyList man100KeyList
     */
    public void setMan100KeyList(String[] man100KeyList) {
        man100KeyList__ = man100KeyList;
    }
    /**
     * <p>man100EditPosSid を取得します。
     * @return man100EditPosSid
     */
    public int getMan100EditPosSid() {
        return man100EditPosSid__;
    }
    /**
     * <p>man100EditPosSid をセットします。
     * @param man100EditPosSid man100EditPosSid
     */
    public void setMan100EditPosSid(int man100EditPosSid) {
        man100EditPosSid__ = man100EditPosSid;
    }
    /**
     * <p>man100ProcMode を取得します。
     * @return man100ProcMode
     */
    public int getMan100ProcMode() {
        return man100ProcMode__;
    }
    /**
     * <p>man100ProcMode をセットします。
     * @param man100ProcMode man100ProcMode
     */
    public void setMan100ProcMode(int man100ProcMode) {
        man100ProcMode__ = man100ProcMode;
    }

}