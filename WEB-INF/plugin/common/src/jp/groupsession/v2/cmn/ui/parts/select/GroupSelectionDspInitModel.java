package jp.groupsession.v2.cmn.ui.parts.select;

import java.util.ArrayList;
import java.util.List;

public class GroupSelectionDspInitModel {
    /** 選択グループラベル */
    private IChild selectGrp__;
    /** グループラベルリスト*/
    private List<? extends IChild> selectionGroupList__ = new ArrayList<>();
    /** ユーザラベルリスト*/
    private List<? extends IChild> selectionList__ = new ArrayList<>();
    /** 選択済みラベルリスト*/
    private List<? extends IChild> selectedList__ = new ArrayList<>();
    /**
     * <p>selectGrp を取得します。
     * @return selectGrp
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectGrp__
     */
    public IChild getSelectGrp() {
        return selectGrp__;
    }
    /**
     * <p>selectGrp をセットします。
     * @param selectGrp selectGrp
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectGrp__
     */
    public void setSelectGrp(IChild selectGrp) {
        selectGrp__ = selectGrp;
    }
    /**
     * <p>selectionGroupList を取得します。
     * @return selectionGroupList
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectionGroupList__
     */
    public List<? extends IChild> getSelectionGroupList() {
        return selectionGroupList__;
    }
    /**
     * <p>selectionGroupList をセットします。
     * @param selectionGroupList selectionGroupList
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectionGroupList__
     */
    public void setSelectionGroupList(List<? extends IChild> selectionGroupList) {
        selectionGroupList__ = selectionGroupList;
    }
    /**
     * <p>selectionList を取得します。
     * @return selectionList
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectionList__
     */
    public List<? extends IChild> getSelectionList() {
        return selectionList__;
    }
    /**
     * <p>selectionList をセットします。
     * @param selectionList selectionList
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectionList__
     */
    public void setSelectionList(List<? extends IChild> selectionList) {
        selectionList__ = selectionList;
    }
    /**
     * <p>selectedList を取得します。
     * @return selectedList
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectedList__
     */
    public List<? extends IChild> getSelectedList() {
        return selectedList__;
    }
    /**
     * <p>selectedList をセットします。
     * @param selectedList selectedList
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel#selectedList__
     */
    public void setSelectedList(List<? extends IChild> selectedList) {
        selectedList__ = selectedList;
    }


}
