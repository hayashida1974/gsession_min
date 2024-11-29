package jp.groupsession.v2.bbs.bbs190;

import java.util.ArrayList;

import jp.groupsession.v2.bbs.model.ForumModel;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] 掲示板 フォーラム選択(ラジオボタン選択方式)のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs190Form extends AbstractGsForm {
    /** 初期選択フォーラムSID*/
    private String checkForum__ = null;
    /** 選択可能Level */
    private String selectLevel__;
    /** 親画面名 */
    private String parentName__ = null;
    /** グループツリー */
    ArrayList<ForumModel> forumList__ = null;

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
     * <p>forumList を取得します。
     * @return forumList
     */
    public ArrayList<ForumModel> getForumList() {
        return forumList__;
    }
    /**
     * <p>forumList をセットします。
     * @param forumList forumList
     */
    public void setForumList(ArrayList<ForumModel> forumList) {
        forumList__ = forumList;
    }
}