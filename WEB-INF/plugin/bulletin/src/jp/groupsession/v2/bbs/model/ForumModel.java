package jp.groupsession.v2.bbs.model;

/**
 * <br>[機 能] 掲示板フォーラム情報を保持するModelクラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class ForumModel {
    /** フォーラム SID */
    private int forumSid__ = -1;
    /** 親フォーラム SID */
    private int parentForumSid__ = -1;
    /** フォーラム名称 */
    private String forumName__ = null;
    /** 階層レベル mapping */
    private int classLevel__;
    /** アイコン画像のバイナリSID */
    private Long binSid__ = new Long(0);

    /**
     * <p>forumSid を取得します。
     * @return forumSid
     */
    public int getForumSid() {
        return forumSid__;
    }
    /**
     * <p>forumSid をセットします。
     * @param forumSid forumSid
     */
    public void setForumSid(int forumSid) {
        forumSid__ = forumSid;
    }
    /**
     * <p>parentForumSid を取得します。
     * @return parentForumSid
     */
    public int getParentForumSid() {
        return parentForumSid__;
    }
    /**
     * <p>parentForumSid をセットします。
     * @param parentForumSid parentForumSid
     */
    public void setParentForumSid(int parentForumSid) {
        parentForumSid__ = parentForumSid;
    }
    /**
     * <p>forumName を取得します。
     * @return forumName
     */
    public String getForumName() {
        return forumName__;
    }
    /**
     * <p>forumName をセットします。
     * @param forumName forumName
     */
    public void setForumName(String forumName) {
        forumName__ = forumName;
    }
    /**
     * <p>classLevel を取得します。
     * @return classLevel
     */
    public int getClassLevel() {
        return classLevel__;
    }
    /**
     * <p>classLevel をセットします。
     * @param classLevel classLevel
     */
    public void setClassLevel(int classLevel) {
        classLevel__ = classLevel;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

}
