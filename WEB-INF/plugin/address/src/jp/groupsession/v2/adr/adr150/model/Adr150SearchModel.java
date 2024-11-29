package jp.groupsession.v2.adr.adr150.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス帳 会社選択の検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr150SearchModel extends AbstractModel {

    /** ソート項目 */
    private int sortKey__ = 0;
    /** 並び順 */
    private int orderKey__ = 0;

    /** 企業コード */
    private String coCode__ = null;
    /** 会社名 */
    private String coName__ = null;
    /** 会社名カナ */
    private String coNameKn__ = null;
    /** 支店・営業所名 */
    private String coBaseName__ = null;
    /** 業種 */
    private int atiSid__ = -1;
    /** 都道府県 */
    private int tdfk__ = -1;
    /** 備考 */
    private String biko__ = null;

    /** ページ */
    private int page__ = 0;
    /** 1ページの最大表示件数 */
    private int maxViewCount__ = 0;

    /**
     * <p>atiSid を取得します。
     * @return atiSid
     */
    public int getAtiSid() {
        return atiSid__;
    }
    /**
     * <p>atiSid をセットします。
     * @param atiSid atiSid
     */
    public void setAtiSid(int atiSid) {
        atiSid__ = atiSid;
    }
    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }
    /**
     * <p>coBaseName を取得します。
     * @return coBaseName
     */
    public String getCoBaseName() {
        return coBaseName__;
    }
    /**
     * <p>coBaseName をセットします。
     * @param coBaseName coBaseName
     */
    public void setCoBaseName(String coBaseName) {
        coBaseName__ = coBaseName;
    }
    /**
     * <p>coCode を取得します。
     * @return coCode
     */
    public String getCoCode() {
        return coCode__;
    }
    /**
     * <p>coCode をセットします。
     * @param coCode coCode
     */
    public void setCoCode(String coCode) {
        coCode__ = coCode;
    }
    /**
     * <p>coName を取得します。
     * @return coName
     */
    public String getCoName() {
        return coName__;
    }
    /**
     * <p>coName をセットします。
     * @param coName coName
     */
    public void setCoName(String coName) {
        coName__ = coName;
    }
    /**
     * <p>coNameKn を取得します。
     * @return coNameKn
     */
    public String getCoNameKn() {
        return coNameKn__;
    }
    /**
     * <p>coNameKn をセットします。
     * @param coNameKn coNameKn
     */
    public void setCoNameKn(String coNameKn) {
        coNameKn__ = coNameKn;
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
     * <p>tdfk を取得します。
     * @return tdfk
     */
    public int getTdfk() {
        return tdfk__;
    }
    /**
     * <p>tdfk をセットします。
     * @param tdfk tdfk
     */
    public void setTdfk(int tdfk) {
        tdfk__ = tdfk;
    }
    /**
     * <p>maxViewCount を取得します。
     * @return maxViewCount
     */
    public int getMaxViewCount() {
        return maxViewCount__;
    }
    /**
     * <p>maxViewCount をセットします。
     * @param maxViewCount maxViewCount
     */
    public void setMaxViewCount(int maxViewCount) {
        maxViewCount__ = maxViewCount;
    }
    /**
     * <p>page を取得します。
     * @return page
     */
    public int getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(int page) {
        page__ = page;
    }


}