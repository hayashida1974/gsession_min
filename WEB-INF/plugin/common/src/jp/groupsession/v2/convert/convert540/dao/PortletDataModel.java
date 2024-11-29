package jp.groupsession.v2.convert.convert540.dao;

import java.io.Serializable;

/**
 * <p>バージョンアップコンバート時のポートレット情報を保持するModelクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PortletDataModel implements Serializable {

    /** ポートレットSID */
    private int pltSid__;
    /** ポートレット 内容 */
    private String pltContent__ = null;

    /** ポートレット 画像SID */
    private long pliSid__ = 0;
    /** バイナリSID */
    private long binSid__ = 0;
    /**
     * <p>pltSid を取得します。
     * @return pltSid
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#pltSid__
     */
    public int getPltSid() {
        return pltSid__;
    }
    /**
     * <p>pltSid をセットします。
     * @param pltSid pltSid
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#pltSid__
     */
    public void setPltSid(int pltSid) {
        pltSid__ = pltSid;
    }
    /**
     * <p>pltContent を取得します。
     * @return pltContent
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#pltContent__
     */
    public String getPltContent() {
        return pltContent__;
    }
    /**
     * <p>pltContent をセットします。
     * @param pltContent pltContent
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#pltContent__
     */
    public void setPltContent(String pltContent) {
        pltContent__ = pltContent;
    }
    /**
     * <p>pliSid を取得します。
     * @return pliSid
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#pliSid__
     */
    public long getPliSid() {
        return pliSid__;
    }
    /**
     * <p>pliSid をセットします。
     * @param pliSid pliSid
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#pliSid__
     */
    public void setPliSid(long pliSid) {
        pliSid__ = pliSid;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#binSid__
     */
    public long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     * @see jp.groupsession.v2.convert.convert540.dao.PortletDataModel#binSid__
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

}
