package jp.groupsession.v2.wml.wml010.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アカウント情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010AccountModel extends AbstractModel {

    /** アカウントSID */
    private int wacSid__ = 0;
    /** アカウント名 */
    private String wacName__ = null;
    /** 未読メール件数 */
    private long notReadCount__ = 0;
    /**
     * <p>notReadCount を取得します。
     * @return notReadCount
     */
    public long getNotReadCount() {
        return notReadCount__;
    }
    /**
     * <p>notReadCount をセットします。
     * @param notReadCount notReadCount
     */
    public void setNotReadCount(long notReadCount) {
        notReadCount__ = notReadCount;
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
}
