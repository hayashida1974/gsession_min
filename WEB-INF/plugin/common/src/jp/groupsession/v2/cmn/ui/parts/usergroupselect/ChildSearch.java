package jp.groupsession.v2.cmn.ui.parts.usergroupselect;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.usr.UserUtil;
/**
 *
 * <br>[機  能] 選択肢 表示クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChildSearch implements IChild {
    /**ラベルタイプ*/
    private final EnumChildType type__;
    /**モデル ユーザ*/
    private CmnUserModel user__;
    /**モデル グループ*/
    private GroupModel group__;
    /**モデル マイグループ*/
    private CmnMyGroupModel mygroup__;
    /**ラベル一覧*/
    private List<String> userLabelList__ = new ArrayList<>();
    /**ユーザ 画像バイナリSID*/
    private long binSid__ = 0;
    /**ユーザ 画像公開区分*/
    private int pictKf__ = 0;
    /**ユーザ デフォルトグループ名*/
    private String defaultGroup__;

    public ChildSearch(CmnUserModel user, long binSid, int pictKf, String defaultGroup) {
        type__ = EnumChildType.USER;
        user__ = user;
        binSid__ = binSid;
        pictKf__ = pictKf;
        defaultGroup__ = defaultGroup;
    }
    public ChildSearch(GroupModel group) {
        type__ = EnumChildType.GROUP;
        group__ = group;
    }
    public ChildSearch(CmnMyGroupModel mygroup) {
        type__ = EnumChildType.MYGROUP;
        mygroup__ = mygroup;
    }
    /**
     * <p>user を取得します。
     * @return user
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child#user__
     */
    public CmnUserModel getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child#user__
     */
    public void setUser(CmnUserModel user) {
        user__ = user;
    }
    /**
     * <p>group を取得します。
     * @return group
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child#group__
     */
    public GroupModel getGroup() {
        return group__;
    }
    /**
     * <p>group をセットします。
     * @param group group
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child#group__
     */
    public void setGroup(GroupModel group) {
        group__ = group;
    }
    /**
     * <p>mygroup を取得します。
     * @return mygroup
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child#mygroup__
     */
    public CmnMyGroupModel getMygroup() {
        return mygroup__;
    }
    /**
     * <p>mygroup をセットします。
     * @param mygroup mygroup
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child#mygroup__
     */
    public void setMygroup(CmnMyGroupModel mygroup) {
        mygroup__ = mygroup;
    }
    /**
     * <p>type を取得します。
     * @return type
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child#type__
     */
    public EnumChildType getType() {
        return type__;
    }
    @Override
    public String getName() {
        switch (type__) {
        case USER:
            return UserUtil.makeName(user__.getUsiSei(), user__.getUsiMei());
        case GROUP:
            return group__.getGroupName();
        case MYGROUP:
            return mygroup__.getMgpName();
        default:
            return null;
        }
    }
    @Override
    public String getValue() {
        switch (type__) {
        case USER:
            return String.valueOf(user__.getUsrSid());
        case GROUP:
            return String.format("G%d", group__.getGroupSid());
        case MYGROUP:
            return String.format("M%d", mygroup__.getMgpSid());
        default:
            return "";
        }
    }
    /**
     * @return 停止状態フラグ 0：通常  1:停止
     */
    @Override
    public int getTeisiFlg() {
        switch (type__) {
        case USER:
            return user__.getUsrUkoFlg();
        default:
            return 0;
        }
    }
    /**
     * @return 削除済みフラグ 0：通常  9:削除済み
     */
    @Override
    public int getJkbn() {
        switch (type__) {
        case USER:
            return user__.getUsrJkbn();
        default:
            return 0;
        }
    }
    /**
     * <p>labelリスト を取得します。
     * @return userLabel
     */
    @Override
    public List<String> getLabelList() {
        return userLabelList__;
    }
    /**
     * <p>labelリスト をセットします。
     * @param userLabel userLabel
     */
    @Override
       public void setLabelList(List<String> userLabel) {
        userLabelList__ = userLabel;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.ChildSearch#binSid__
     */
    public long getBinSid() {
        return binSid__;
    }
    /**
     * <p>pictKf を取得します。
     * @return pictKf
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.ChildSearch#pictKf__
     */
    public int getPictKf() {
        return pictKf__;
    }
    /**
     * <p>defaultGroup を取得します。
     * @return defaultGroup
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.ChildSearch#defaultGroup__
     */
    public String getDefaultGroup() {
        return defaultGroup__;
    }
}
