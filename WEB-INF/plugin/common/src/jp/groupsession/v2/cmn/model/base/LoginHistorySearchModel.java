package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ログイン履歴一覧取得で使用するModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LoginHistorySearchModel extends AbstractModel {

    /** ユーザSID */
    private int usid__;
    /** グループSID */
    private int gsid__;
    /** 端末 */
    private int terminal__;
    /** キャリア */
    private int car__;
    /** 開始日付 */
    private UDate frDate__;
    /** 終了日付 */
    private UDate toDate__;
    /** ソートキー */
    private int sortKey__;
    /** オーダーキー */
    private int orderKey__;
    /** ページ */
    private int page__;
    /** 最大ページ */
    private int maxCnt__;

    /** モード 0:一覧 1:検索 */
    private int mode__;

    /**
     * <p>usid を取得します。
     * @return usid
     */
    public int getUsid() {
        return usid__;
    }
    /**
     * <p>usid をセットします。
     * @param usid usid
     */
    public void setUsid(int usid) {
        usid__ = usid;
    }
    /**
     * <p>gsid を取得します。
     * @return gsid
     */
    public int getGsid() {
        return gsid__;
    }
    /**
     * <p>gsid をセットします。
     * @param gsid gsid
     */
    public void setGsid(int gsid) {
        gsid__ = gsid;
    }
    /**
     * <p>terminal を取得します。
     * @return terminal
     */
    public int getTerminal() {
        return terminal__;
    }
    /**
     * <p>terminal をセットします。
     * @param terminal terminal
     */
    public void setTerminal(int terminal) {
        terminal__ = terminal;
    }
    /**
     * <p>car を取得します。
     * @return car
     */
    public int getCar() {
        return car__;
    }
    /**
     * <p>car をセットします。
     * @param car car
     */
    public void setCar(int car) {
        car__ = car;
    }
    /**
     * <p>frDate を取得します。
     * @return frDate
     */
    public UDate getFrDate() {
        return frDate__;
    }
    /**
     * <p>frDate をセットします。
     * @param frDate frDate
     */
    public void setFrDate(UDate frDate) {
        frDate__ = frDate;
    }
    /**
     * <p>toDate を取得します。
     * @return toDate
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
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
    /**
     * <p>maxCnt を取得します。
     * @return maxCnt
     */
    public int getMaxCnt() {
        return maxCnt__;
    }
    /**
     * <p>maxCnt をセットします。
     * @param maxCnt maxCnt
     */
    public void setMaxCnt(int maxCnt) {
        maxCnt__ = maxCnt;
    }
    /**
     * <p>mode を取得します。
     * @return mode
     */
    public int getMode() {
        return mode__;
    }
    /**
     * <p>mode をセットします。
     * @param mode mode
     */
    public void setMode(int mode) {
        mode__ = mode;
    }


}