package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

/**
 * <p>BBS_THRE_RSV Data Bindding JavaBean
 * 予約投稿されたスレッドおよびフォーラムのSIDを保持
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreRsvModel implements Serializable {

    /** BTI_SID mapping */
    private int btiSid__;
    /** BFI_SID mapping */
    private int bfiSid__;
    /** BTR_URL mapping*/
    private String btrUrl__;

    /**
     * <p>Default Constructor
     */
    public BbsThreRsvModel() {
    }

    /**
     * <p>BTI_SIDを取得します。
     * @return btiSid スレッドSID
     */
    public int getBtiSid() {
        return btiSid__;
    }

    /**
     * <p>BTI_SIDをセットします。
     * @param btiSid btiSid
     */
    public void setBtiSid(int btiSid) {
        btiSid__ = btiSid;
    }

    /**
     * <p>BFI_SIDを取得します。
     * @return bfiSid フォーラムSID
     */
    public int getBfiSid() {
        return bfiSid__;
    }

    /**
     * <p>BFI_SIDをセットします。
     * @param bfiSid bfiSid
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }

    /**
     * btrUrlを取得します。
     * @return btrUrl 予約投稿スレッドURL
     * */
    public String getBtrUrl() {
        return btrUrl__;
    }

    /**
     * btrUrlをセットします。
     * @param btrUrl btrUrl
     * */
    public void setBtrUrl(String btrUrl) {
        btrUrl__ = btrUrl;
    }

}
