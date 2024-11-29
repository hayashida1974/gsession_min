package jp.groupsession.v2.convert.convert430.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アカウント情報Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CvtWebmailFilterModel extends AbstractModel {
    /** フィルターSID */
    private int wftSid__ = 0;
    /** ユーザSID */
    private int usrSid__ = 0;
    /** アカウントSID */
    private int wacSid__ = 0;
    /**
     * <p>wftSid を取得します。
     * @return wftSid
     */
    public int getWftSid() {
        return wftSid__;
    }
    /**
     * <p>wftSid をセットします。
     * @param wftSid wftSid
     */
    public void setWftSid(int wftSid) {
        wftSid__ = wftSid;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
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
