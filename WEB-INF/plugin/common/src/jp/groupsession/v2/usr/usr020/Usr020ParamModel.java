package jp.groupsession.v2.usr.usr020;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] グループ選択(ラジオボタン選択方式)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr020ParamModel extends AbstractParamModel {
    /** 初期選択グループSID*/
    private String checkGroup__ = null;
    /** リストタイプ */
    private String listType__ = null;
    /** 選択可能Level */
    private String selectLevel__;
    /** Root表示有無 */
    private String dspRoot__;
    /** 親画面名 */
    private String parentName__ = null;
    /** グループツリー */
    ArrayList<GroupModel> groupList__ = null;

    /** グループ検索絞込み グループID 値渡し用パラメータ */
    private String searchGrpId__ = null;
    /** グループ検索絞込み グループ名 値渡し用パラメータ */
    private String searchGrpName__ = null;


    /**
     * @return checkGroup を戻します。
     */
    public String getCheckGroup() {
        return checkGroup__;
    }

    /**
     * @param checkGroup 設定する checkGroup。
     */
    public void setCheckGroup(String checkGroup) {
        checkGroup__ = checkGroup;
    }

    /**
     * @return dspRoot を戻します。
     */
    public String getDspRoot() {
        return dspRoot__;
    }

    /**
     * @param dspRoot 設定する dspRoot。
     */
    public void setDspRoot(String dspRoot) {
        dspRoot__ = dspRoot;
    }

    /**
     * @return listType を戻します。
     */
    public String getListType() {
        return listType__;
    }

    /**
     * @param listType 設定する listType。
     */
    public void setListType(String listType) {
        listType__ = listType;
    }

    /**
     * @return selectLevel を戻します。
     */
    public String getSelectLevel() {
        return selectLevel__;
    }

    /**
     * @param selectLevel 設定する selectLevel。
     */
    public void setSelectLevel(String selectLevel) {
        selectLevel__ = selectLevel;
    }

    /**
     * @return groupList を戻します。
     */
    public ArrayList<GroupModel> getGroupList() {
        return groupList__;
    }

    /**
     * @param groupList 設定する groupList。
     */
    public void setGroupList(ArrayList<GroupModel> groupList) {
        groupList__ = groupList;
    }
    /**
     * @return parentName を戻します。
     */
    public String getParentName() {
        return parentName__;
    }
    /**
     * @param parentName 設定する parentName。
     */
    public void setParentName(String parentName) {
        parentName__ = parentName;
    }

    /**
     * <p>searchGrpId を取得します。
     * @return searchGrpId
     */
    public String getSearchGrpId() {
        return searchGrpId__;
    }

    /**
     * <p>searchGrpId をセットします。
     * @param searchGrpId searchGrpId
     */
    public void setSearchGrpId(String searchGrpId) {
        searchGrpId__ = searchGrpId;
    }

    /**
     * <p>searchGrpName を取得します。
     * @return searchGrpName
     */
    public String getSearchGrpName() {
        return searchGrpName__;
    }

    /**
     * <p>searchGrpName をセットします。
     * @param searchGrpName searchGrpName
     */
    public void setSearchGrpName(String searchGrpName) {
        searchGrpName__ = searchGrpName;
    }

}