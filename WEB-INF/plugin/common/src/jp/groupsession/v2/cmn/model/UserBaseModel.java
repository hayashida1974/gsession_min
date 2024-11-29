package jp.groupsession.v2.cmn.model;


/**
 * <br>[機  能] ユーザ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserBaseModel extends AbstractModel {

    /** ユーザID */
    private String userId__;
    /** 姓 */
    private String sei__;
    /** 名 */
    private String mei__;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * @return the sei
     */
    public String getSei() {
        return sei__;
    }
    /**
     * @param sei the sei to set
     */
    public void setSei(String sei) {
        sei__ = sei;
    }
    /**
     * @return the mei
     */
    public String getMei() {
        return mei__;
    }
    /**
     * @param mei the mei to set
     */
    public void setMei(String mei) {
        mei__ = mei;
    }

}
