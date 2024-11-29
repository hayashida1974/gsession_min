package jp.groupsession.v2.convert.convert430.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ラベル情報Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CvtWebmailLabelModel extends AbstractModel {
    /** ラベルSID */
    private int wlbSid__ = 0;
    /** ユーザSID */
    private int usrSid__ = 0;
    /** ラベル名称 */
    private String wlbName__;
    /** アカウントSID */
    private int wacSid__ = 0;
    /** 並び順 */
    private int wlbOrder__ = 0;
    /**
     * <p>wlbSid を取得します。
     * @return wlbSid
     */
    public int getWlbSid() {
        return wlbSid__;
    }
    /**
     * <p>wlbSid をセットします。
     * @param wlbSid wlbSid
     */
    public void setWlbSid(int wlbSid) {
        wlbSid__ = wlbSid;
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
     * <p>wlbName を取得します。
     * @return wlbName
     */
    public String getWlbName() {
        return wlbName__;
    }
    /**
     * <p>wlbName をセットします。
     * @param wlbName wlbName
     */
    public void setWlbName(String wlbName) {
        wlbName__ = wlbName;
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
     * <p>wlbOrder を取得します。
     * @return wlbOrder
     */
    public int getWlbOrder() {
        return wlbOrder__;
    }
    /**
     * <p>wlbOrder をセットします。
     * @param wlbOrder wlbOrder
     */
    public void setWlbOrder(int wlbOrder) {
        wlbOrder__ = wlbOrder;
    }
}
