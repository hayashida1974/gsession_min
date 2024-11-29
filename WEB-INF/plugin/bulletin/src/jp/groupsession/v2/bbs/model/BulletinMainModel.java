package jp.groupsession.v2.bbs.model;


/**
 * <br>[機  能] メイン画面に表示する掲示板一覧の情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinMainModel {

    /** Newアイコン表示フラグ 非表示 */
    public static final int NEWFLG_NOTVIEW = 0;
    /** Newアイコン表示フラグ 表示 */
    public static final int NEWFLG_VIEW = 1;

    /** フォーラムSID */
    private int bfiSid__ = 0;
    /** フォーラム名 */
    private String bfiName__  = null;
    /** スレッドSID */
    private int btiSid__ = 0;
    /** スレッド名 */
    private String btiTitle__  = null;
    /** Newアイコン表示フラグ */
    private int newFlg__ = NEWFLG_NOTVIEW;
    /** 最終投稿日時 */
    private String lastUpdate__ = null;
    /** 最終投稿者 */
    private String lastUpuser__ = null;

    /**
     * <p>bfiSid を取得します。
     * @return bfiSid
     */
    public int getBfiSid() {
        return bfiSid__;
    }
    /**
     * <p>bfiSid をセットします。
     * @param bfiSid bfiSid
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }
    /**
     * <p>bfiName を取得します。
     * @return bfiName
     */
    public String getBfiName() {
        return bfiName__;
    }
    /**
     * <p>bfiName をセットします。
     * @param bfiName bfiName
     */
    public void setBfiName(String bfiName) {
        bfiName__ = bfiName;
    }
    /**
     * <p>btiSid を取得します。
     * @return btiSid
     */
    public int getBtiSid() {
        return btiSid__;
    }
    /**
     * <p>btiSid をセットします。
     * @param btiSid btiSid
     */
    public void setBtiSid(int btiSid) {
        btiSid__ = btiSid;
    }
    /**
     * <p>btiTitle を取得します。
     * @return btiTitle
     */
    public String getBtiTitle() {
        return btiTitle__;
    }
    /**
     * <p>btiTitle をセットします。
     * @param btiTitle btiTitle
     */
    public void setBtiTitle(String btiTitle) {
        btiTitle__ = btiTitle;
    }
    /**
     * <p>lastUpdate を取得します。
     * @return lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate__;
    }
    /**
     * <p>lastUpdate をセットします。
     * @param lastUpdate lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        lastUpdate__ = lastUpdate;
    }
    /**
     * <p>lastUpuser を取得します。
     * @return lastUpuser
     */
    public String getLastUpuser() {
        return lastUpuser__;
    }
    /**
     * <p>lastUpuser をセットします。
     * @param lastUpuser lastUpuser
     */
    public void setLastUpuser(String lastUpuser) {
        lastUpuser__ = lastUpuser;
    }
    /**
     * <p>newFlg を取得します。
     * @return newFlg
     */
    public int getNewFlg() {
        return newFlg__;
    }
    /**
     * <p>newFlg をセットします。
     * @param newFlg newFlg
     */
    public void setNewFlg(int newFlg) {
        newFlg__ = newFlg;
    }

}
