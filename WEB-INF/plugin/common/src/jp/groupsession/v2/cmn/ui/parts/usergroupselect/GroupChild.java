package jp.groupsession.v2.cmn.ui.parts.usergroupselect;

import java.util.List;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;

public class GroupChild implements IChild {
    /**ラベルタイプ*/
    private final EnumChildType type__;
    /**モデル グループ*/
    private GroupModel group__;
    /**モデル マイグループ*/
    private CmnMyGroupModel mygroup__;

    public GroupChild(GroupModel group) {
        type__ = EnumChildType.GROUP;
        group__ = group;
    }

    public GroupChild(CmnMyGroupModel mygroup) {
        type__ = EnumChildType.MYGROUP;
        mygroup__ = mygroup;
    }

    /**
     * <p>group を取得します。
     * @return group
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild#group__
     */
    public GroupModel getGroup() {
        return group__;
    }
    /**
     * <p>group をセットします。
     * @param group group
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild#group__
     */
    public void setGroup(GroupModel group) {
        group__ = group;
    }
    /**
     * <p>mygroup を取得します。
     * @return mygroup
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild#mygroup__
     */
    public CmnMyGroupModel getMygroup() {
        return mygroup__;
    }
    /**
     * <p>mygroup をセットします。
     * @param mygroup mygroup
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild#mygroup__
     */
    public void setMygroup(CmnMyGroupModel mygroup) {
        mygroup__ = mygroup;
    }
    /**
     * <p>type を取得します。
     * @return type
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild#type__
     */
    public EnumChildType getType() {
        return type__;
    }
    @Override
    public String getName() {
        switch (type__) {
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
        case GROUP:
            return String.format("%d", group__.getGroupSid());
        case MYGROUP:
            return String.format("M%d", mygroup__.getMgpSid());
        default:
            return "";
        }
    }

    @Override
    public int getTeisiFlg() {
        return 0;
    }

    @Override
    public int getJkbn() {
        return 0;
    }

    @Override
    public List<String> getLabelList() {
        return List.of();
    }

    @Override
    public void setLabelList(List<String> userLabel) {

    }

}
