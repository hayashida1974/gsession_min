package jp.groupsession.v2.man.man330.model;

/**
 * <br>[機  能] グループ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330CsvGroupDataModel {

    /** グループID */
    private String groupId__;
    /** グループ名 */
    private String groupName__;
    /** グループ名カナ */
    private String groupNameKana__;

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
    /**
     * <p>groupName を取得します。
     * @return groupName
     */
    public String getGroupName() {
        return groupName__;
    }
    /**
     * <p>groupName をセットします。
     * @param groupName groupName
     */
    public void setGroupName(String groupName) {
        groupName__ = groupName;
    }
    /**
     * <p>groupNameKana を取得します。
     * @return groupNameKana
     */
    public String getGroupNameKana() {
        return groupNameKana__;
    }
    /**
     * <p>groupNameKana をセットします。
     * @param groupNameKana groupNameKana
     */
    public void setGroupNameKana(String groupNameKana) {
        groupNameKana__ = groupNameKana;
    }


}