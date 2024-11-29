package jp.groupsession.v2.bbs.bbs190;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] 掲示板 フォーラム選択(ラジオボタン選択方式)のパラメータモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs190ParamModel extends AbstractParamModel {
    /** 初期選択フォーラムSID*/
    private String checkForum__ = null;
    /** 選択可能Level */
    private String selectLevel__;
    /** 親画面名 */
    private String parentName__ = null;
    /** グループツリー */
    ArrayList<GroupModel> groupList__ = null;

    /**
     * <p>checkForum を取得します。
     * @return checkForum
     */
    public String getCheckForum() {
        return checkForum__;
    }
    /**
     * <p>checkForum をセットします。
     * @param checkForum checkForum
     */
    public void setCheckForum(String checkForum) {
        checkForum__ = checkForum;
    }
    /**
     * <p>selectLevel を取得します。
     * @return selectLevel
     */
    public String getSelectLevel() {
        return selectLevel__;
    }
    /**
     * <p>selectLevel をセットします。
     * @param selectLevel selectLevel
     */
    public void setSelectLevel(String selectLevel) {
        selectLevel__ = selectLevel;
    }
    /**
     * <p>parentName を取得します。
     * @return parentName
     */
    public String getParentName() {
        return parentName__;
    }
    /**
     * <p>parentName をセットします。
     * @param parentName parentName
     */
    public void setParentName(String parentName) {
        parentName__ = parentName;
    }
    /**
     * <p>groupList を取得します。
     * @return groupList
     */
    public ArrayList<GroupModel> getGroupList() {
        return groupList__;
    }
    /**
     * <p>groupList をセットします。
     * @param groupList groupList
     */
    public void setGroupList(ArrayList<GroupModel> groupList) {
        groupList__ = groupList;
    }
}