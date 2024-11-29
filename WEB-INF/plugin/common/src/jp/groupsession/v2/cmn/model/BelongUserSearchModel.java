package jp.groupsession.v2.cmn.model;

import java.util.ArrayList;

/**
 * <br>[機  能] ユーザ情報、および在席状況を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class BelongUserSearchModel extends AbstractModel {

    /** グループSID */
    private int gpSid__;
    /** セッションユーザSID */
    private int sessionUsrSid__;
    /** 除外するユーザSID */
    private ArrayList<Integer> usrSids__;
    /** 個人情報公開フラグを適用(非公開情報はNull)するかどうか */
    private boolean kfFlg__;
    /** ソートKEY1 */
    private int sortKey__;
    /** オーダKEY1 */
    private int orderKey__;
    /** ソートKEY2 */
    private int sortKey2__;
    /** オーダKEY2 */
    private int orderKey2__;
    /** sqlタイプ true=select false=count */
    private boolean sqlType__;
    /**
     * <p>gpSid を取得します。
     * @return gpSid
     */
    public int getGpSid() {
        return gpSid__;
    }
    /**
     * <p>gpSid をセットします。
     * @param gpSid gpSid
     */
    public void setGpSid(int gpSid) {
        gpSid__ = gpSid;
    }
    /**
     * <p>kfFlg を取得します。
     * @return kfFlg
     */
    public boolean isKfFlg() {
        return kfFlg__;
    }
    /**
     * <p>kfFlg をセットします。
     * @param kfFlg kfFlg
     */
    public void setKfFlg(boolean kfFlg) {
        kfFlg__ = kfFlg;
    }
    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public int getOrderKey() {
        return orderKey__;
    }
    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }
    /**
     * <p>orderKey2 を取得します。
     * @return orderKey2
     */
    public int getOrderKey2() {
        return orderKey2__;
    }
    /**
     * <p>orderKey2 をセットします。
     * @param orderKey2 orderKey2
     */
    public void setOrderKey2(int orderKey2) {
        orderKey2__ = orderKey2;
    }
    /**
     * <p>sessionUsrSid を取得します。
     * @return sessionUsrSid
     */
    public int getSessionUsrSid() {
        return sessionUsrSid__;
    }
    /**
     * <p>sessionUsrSid をセットします。
     * @param sessionUsrSid sessionUsrSid
     */
    public void setSessionUsrSid(int sessionUsrSid) {
        sessionUsrSid__ = sessionUsrSid;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>sortKey2 を取得します。
     * @return sortKey2
     */
    public int getSortKey2() {
        return sortKey2__;
    }
    /**
     * <p>sortKey2 をセットします。
     * @param sortKey2 sortKey2
     */
    public void setSortKey2(int sortKey2) {
        sortKey2__ = sortKey2;
    }
    /**
     * <p>sqlType を取得します。
     * @return sqlType
     */
    public boolean isSqlType() {
        return sqlType__;
    }
    /**
     * <p>sqlType をセットします。
     * @param sqlType sqlType
     */
    public void setSqlType(boolean sqlType) {
        sqlType__ = sqlType;
    }
    /**
     * <p>usrSids を取得します。
     * @return usrSids
     */
    public ArrayList<Integer> getUsrSids() {
        return usrSids__;
    }
    /**
     * <p>usrSids をセットします。
     * @param usrSids usrSids
     */
    public void setUsrSids(ArrayList<Integer> usrSids) {
        usrSids__ = usrSids;
    }

}
