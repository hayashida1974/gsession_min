package jp.groupsession.v2.bbs.bbs020;

import jp.groupsession.v2.bbs.bbs010.Bbs010ParamModel;

/**
 * <br>[機  能] 掲示板 フォーラム管理画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs020ParamModel extends Bbs010ParamModel {
    /** ページコンボ上 */
    private int bbs020page1__ = 0;
    /** ページコンボ下 */
    private int bbs020page2__ = 0;
    /** フォーラムSID */
    private int bbs020forumSid__ = 0;
    /** 表示順ラジオボタン */
    private int bbs020indexRadio__ = 0;
    /** 削除フォーラム名 */
    private String bbs020delForumName__ = null;

    /**
     * <p>bbs020indexRadio を取得します。
     * @return bbs020indexRadio
     */
    public int getBbs020indexRadio() {
        return bbs020indexRadio__;
    }
    /**
     * <p>bbs020indexRadio をセットします。
     * @param bbs020indexRadio bbs020indexRadio
     */
    public void setBbs020indexRadio(int bbs020indexRadio) {
        bbs020indexRadio__ = bbs020indexRadio;
    }
    /**
     * @return bbs020page1 を戻します。
     */
    public int getBbs020page1() {
        return bbs020page1__;
    }
    /**
     * @param bbs020page1 設定する bbs020page1。
     */
    public void setBbs020page1(int bbs020page1) {
        bbs020page1__ = bbs020page1;
    }
    /**
     * @return bbs020page2 を戻します。
     */
    public int getBbs020page2() {
        return bbs020page2__;
    }
    /**
     * @param bbs020page2 設定する bbs020page2。
     */
    public void setBbs020page2(int bbs020page2) {
        bbs020page2__ = bbs020page2;
    }
    /**
     * @return bbs020forumSid を戻します。
     */
    public int getBbs020forumSid() {
        return bbs020forumSid__;
    }
    /**
     * @param bbs020forumSid 設定する bbs020forumSid。
     */
    public void setBbs020forumSid(int bbs020forumSid) {
        bbs020forumSid__ = bbs020forumSid;
    }
    /**
     * <p>bbs020delForumName を取得します。
     * @return bbs020delForumName
     */
    public String getBbs020delForumName() {
        return bbs020delForumName__;
    }
    /**
     * <p>bbs020delForumName をセットします。
     * @param bbs020delForumName bbs020delForumName
     */
    public void setBbs020delForumName(String bbs020delForumName) {
        bbs020delForumName__ = bbs020delForumName;
    }
}