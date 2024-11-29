package jp.groupsession.v2.wml.model;

import java.io.Serializable;

/**
 * <br>[機  能] メイン画面で表示するメッセージ情報を格納するModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlMainInfoMessageModel implements Serializable {

    /** アカウントSID */
    private int wacSid__ = 0;
    /** アカウント名 */
    private String wacName__ = null;
    /** 未読メール件数 */
    private long noReadCount__ = 0;
    /** アカウント ディスク使用量 */
    private long wdsSize__ = 0;

    /**
     * <p>noReadCount を取得します。
     * @return noReadCount
     */
    public long getNoReadCount() {
        return noReadCount__;
    }
    /**
     * <p>noReadCount をセットします。
     * @param noReadCount noReadCount
     */
    public void setNoReadCount(long noReadCount) {
        noReadCount__ = noReadCount;
    }
    /**
     * <p>wacName を取得します。
     * @return wacName
     */
    public String getWacName() {
        return wacName__;
    }
    /**
     * <p>wacName をセットします。
     * @param wacName wacName
     */
    public void setWacName(String wacName) {
        wacName__ = wacName;
    }
    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid wacSid
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }
    /**
     * <p>wdsSize を取得します。
     * @return wdsSize
     */
    public long getWdsSize() {
        return wdsSize__;
    }
    /**
     * <p>wdsSize をセットします。
     * @param wdsSize wdsSize
     */
    public void setWdsSize(long wdsSize) {
        wdsSize__ = wdsSize;
    }
}
