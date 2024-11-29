package jp.groupsession.v2.usr.usr012;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 グループマネージャー（所属ユーザ一覧）画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr012ParamModel extends AbstractParamModel {

    /** グループID */
    private int usr010grpSid__;
    /** オーダーキー */
    private int usr012OrderKey__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー */
    private int usr012SortKey__ = GSConstUser.USER_SORT_YKSK;

    /** 所属ユーザ一覧 */
    private List<SltUserPerGroupModel> usr012ModelList__
        = new ArrayList<SltUserPerGroupModel>();

    /**
     * @return usr012ModelList を戻します。
     */
    public List<SltUserPerGroupModel> getUsr012ModelList() {
        return usr012ModelList__;
    }

    /**
     * @param usr012ModelList 設定する usr012ModelList。
     */
    public void setUsr012ModelList(List<SltUserPerGroupModel> usr012ModelList) {
        usr012ModelList__ = usr012ModelList;
    }

    /**
     * @return usr012OrderKey を戻します。
     */
    public int getUsr012OrderKey() {
        return usr012OrderKey__;
    }

    /**
     * @param usr012OrderKey 設定する usr012OrderKey。
     */
    public void setUsr012OrderKey(int usr012OrderKey) {
        usr012OrderKey__ = usr012OrderKey;
    }

    /**
     * @return usr012SortKey を戻します。
     */
    public int getUsr012SortKey() {
        return usr012SortKey__;
    }

    /**
     * @param usr012SortKey 設定する usr012SortKey。
     */
    public void setUsr012SortKey(int usr012SortKey) {
        usr012SortKey__ = usr012SortKey;
    }

    /**
     * @return usr010grpSid を戻します。
     */
    public int getUsr010grpSid() {
        return usr010grpSid__;
    }

    /**
     * @param usr010grpSid 設定する usr010grpSid。
     */
    public void setUsr010grpSid(int usr010grpSid) {
        usr010grpSid__ = usr010grpSid;
    }
}