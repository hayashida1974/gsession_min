package jp.groupsession.v2.man.man330.model;

/**
 * <br>[機  能] エクスポート対象の所属情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330ExpModel {

    /** ユーザSID */
    private int userSid__;
    /** ユーザID */
    private String userId__;
    /** 氏名 */
    private String usrName__;
    /** 氏名カナ */
    private String usrNameKana__;
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
    /**
     * <p>userId を取得します。
     * @return userId
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>userId をセットします。
     * @param userId userId
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>usrName を取得します。
     * @return usrName
     */
    public String getUsrName() {
        return usrName__;
    }
    /**
     * <p>usrName をセットします。
     * @param usrName usrName
     */
    public void setUsrName(String usrName) {
        usrName__ = usrName;
    }
    /**
     * <p>usrNameKana を取得します。
     * @return usrNameKana
     */
    public String getUsrNameKana() {
        return usrNameKana__;
    }
    /**
     * <p>usrNameKana をセットします。
     * @param usrNameKana usrNameKana
     */
    public void setUsrNameKana(String usrNameKana) {
        usrNameKana__ = usrNameKana;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
}