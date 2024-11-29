package jp.groupsession.v2.cmn.dao;

/**
 * <br>[機 能] グループ情報を保持するModelクラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class GroupModel {
    /** GROUP SID */
    private int groupSid__ = -1;
    /** GROUP ID */
    private String groupId__ = null;
    /** グループ名 */
    private String groupName__ = null;
    /** 階層レベル mapping */
    private int classLevel__;
    /** 所属グループ区分 */
    private int grpKbn__;

    /**
     * @return grpKbn__ を戻します。
     */
    public int getGrpKbn() {
        return grpKbn__;
    }
    /**
     * @param grpKbn 設定する grpKbn__。
     */
    public void setGrpKbn(int grpKbn) {
        grpKbn__ = grpKbn;
    }
    /**
     * @return classLevel を戻します。
     */
    public int getClassLevel() {
        return classLevel__;
    }
    /**
     * @param classLevel 設定する classLevel。
     */
    public void setClassLevel(int classLevel) {
        classLevel__ = classLevel;
    }
    /**
     * @return groupName を戻します。
     */
    public String getGroupName() {
        return groupName__;
    }
    /**
     * @param groupName 設定する groupName。
     */
    public void setGroupName(String groupName) {
        groupName__ = groupName;
    }
    /**
     * @return groupSid を戻します。
     */
    public int getGroupSid() {
        return groupSid__;
    }
    /**
     * @param groupSid 設定する groupSid。
     */
    public void setGroupSid(int groupSid) {
        groupSid__ = groupSid;
    }
    /**
     * <p>groupId を取得します。
     * @return groupId
     */
    public String getGroupId() {
        return groupId__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
}
