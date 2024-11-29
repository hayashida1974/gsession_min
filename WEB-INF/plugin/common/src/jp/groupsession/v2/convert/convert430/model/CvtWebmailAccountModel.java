package jp.groupsession.v2.convert.convert430.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アカウント情報Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CvtWebmailAccountModel extends AbstractModel {
    /** アカウントSID */
    private int wacSid__ = 0;
    /** アカウント名 */
    private String wacName__ = null;
    /** 送信文字コード */
    private String wacEncodeSend__ = null;
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
     * <p>wacEncodeSend を取得します。
     * @return wacEncodeSend
     */
    public String getWacEncodeSend() {
        return wacEncodeSend__;
    }
    /**
     * <p>wacEncodeSend をセットします。
     * @param wacEncodeSend wacEncodeSend
     */
    public void setWacEncodeSend(String wacEncodeSend) {
        wacEncodeSend__ = wacEncodeSend;
    }
}
