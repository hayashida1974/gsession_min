package jp.groupsession.v2.cmn.model;


/**
 * <br>[機  能] グループ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GroupBaseModel extends AbstractModel {

    /** グループID */
    private String groupId__;

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId__;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
}
