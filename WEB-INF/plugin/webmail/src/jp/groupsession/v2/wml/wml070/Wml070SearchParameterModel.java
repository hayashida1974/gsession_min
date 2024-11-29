package jp.groupsession.v2.wml.wml070;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール 送受信ログ一覧画面の検索パラメータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml070SearchParameterModel {

    /** 件名 */
    private List<String> wml070Title__ = null;
    /** メールアドレス */
    private String wml070Address__ = null;
    /** メールアドレス 差出人 */
    private int wml070AddressFrom__ = 0;
    /** メールアドレス 宛先 */
    private int wml070AddressTo__ = 0;
    /** 日時 年 */
    private int wml070SendDateYear__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 月 */
    private int wml070SendDateMonth__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 日 */
    private int wml070SendDateDay__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時To 年 */
    private int wml070SendDateYearTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時To 月 */
    private int wml070SendDateMonthTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時To 日 */
    private int wml070SendDateDayTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 条件 */
    private int wml070SendDateCondition__ = GSConstWebmail.DATE_KBN_EQUAL;
    /** 日時 */
    private UDate writeDate__ = null;
    /** 日時 To */
    private UDate writeDateTo__ = null;
    /** 種別 */
    private int wml070Type__ = GSConstWebmail.TYPE_FREE;
    /** 1ページの件数 */
    private int limit__ = 0;
    /** カーソルスタート位置 */
    private int start__ = 0;
    /** ソートキー */
    private int sortKey__ = GSConstWebmail.SKEY_TITLE;
    /** 並び順 */
    private int order__ = GSConstWebmail.ORDER_ASC;

    /**
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }

    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
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
     * <p>start を取得します。
     * @return start
     */
    public int getStart() {
        return start__;
    }

    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(int start) {
        start__ = start;
    }

    /**
     * <br>[機  能] 件名を設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param textWordTit 件名キーワード
     */
    public void setTextWordTitle(String textWordTit) {

        WmlBiz wBiz = new WmlBiz();
        setWml070Title(wBiz.setKeyword(textWordTit));
    }

    /**
     * <p>limit を取得します。
     * @return limit
     */
    public int getLimit() {
        return limit__;
    }

    /**
     * <p>limit をセットします。
     * @param limit limit
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }
    /**
     * <p>wml070Title を取得します。
     * @return wml070Title
     */
    public List<String> getWml070Title() {
        return wml070Title__;
    }
    /**
     * <p>wml070Title をセットします。
     * @param wml070Title wml070Title
     */
    public void setWml070Title(List<String> wml070Title) {
        wml070Title__ = wml070Title;
    }
    /**
     * <p>wml070SendDateCondition を取得します。
     * @return wml070SendDateCondition
     */
    public int getWml070SendDateCondition() {
        return wml070SendDateCondition__;
    }
    /**
     * <p>wml070SendDateCondition をセットします。
     * @param wml070SendDateCondition wml070SendDateCondition
     */
    public void setWml070SendDateCondition(int wml070SendDateCondition) {
        wml070SendDateCondition__ = wml070SendDateCondition;
    }
    /**
     * <p>wml070SendDateDay を取得します。
     * @return wml070SendDateDay
     */
    public int getWml070SendDateDay() {
        return wml070SendDateDay__;
    }
    /**
     * <p>wml070SendDateDay をセットします。
     * @param wml070SendDateDay wml070SendDateDay
     */
    public void setWml070SendDateDay(int wml070SendDateDay) {
        wml070SendDateDay__ = wml070SendDateDay;
    }
    /**
     * <p>wml070SendDateMonth を取得します。
     * @return wml070SendDateMonth
     */
    public int getWml070SendDateMonth() {
        return wml070SendDateMonth__;
    }
    /**
     * <p>wml070SendDateMonth をセットします。
     * @param wml070SendDateMonth wml070SendDateMonth
     */
    public void setWml070SendDateMonth(int wml070SendDateMonth) {
        wml070SendDateMonth__ = wml070SendDateMonth;
    }
    /**
     * <p>wml070SendDateYear を取得します。
     * @return wml070SendDateYear
     */
    public int getWml070SendDateYear() {
        return wml070SendDateYear__;
    }
    /**
     * <p>wml070SendDateYear をセットします。
     * @param wml070SendDateYear wml070SendDateYear
     */
    public void setWml070SendDateYear(int wml070SendDateYear) {
        wml070SendDateYear__ = wml070SendDateYear;
    }

    /**
     * <p>wml070SendDateDayTo を取得します。
     * @return wml070SendDateDayTo
     */
    public int getWml070SendDateDayTo() {
        return wml070SendDateDayTo__;
    }

    /**
     * <p>wml070SendDateDayTo をセットします。
     * @param wml070SendDateDayTo wml070SendDateDayTo
     */
    public void setWml070SendDateDayTo(int wml070SendDateDayTo) {
        wml070SendDateDayTo__ = wml070SendDateDayTo;
    }

    /**
     * <p>wml070SendDateMonthTo を取得します。
     * @return wml070SendDateMonthTo
     */
    public int getWml070SendDateMonthTo() {
        return wml070SendDateMonthTo__;
    }

    /**
     * <p>wml070SendDateMonthTo をセットします。
     * @param wml070SendDateMonthTo wml070SendDateMonthTo
     */
    public void setWml070SendDateMonthTo(int wml070SendDateMonthTo) {
        wml070SendDateMonthTo__ = wml070SendDateMonthTo;
    }

    /**
     * <p>wml070SendDateYearTo を取得します。
     * @return wml070SendDateYearTo
     */
    public int getWml070SendDateYearTo() {
        return wml070SendDateYearTo__;
    }

    /**
     * <p>wml070SendDateYearTo をセットします。
     * @param wml070SendDateYearTo wml070SendDateYearTo
     */
    public void setWml070SendDateYearTo(int wml070SendDateYearTo) {
        wml070SendDateYearTo__ = wml070SendDateYearTo;
    }

    /**
     * <p>wml070Address を取得します。
     * @return wml070Address
     */
    public String getWml070Address() {
        return wml070Address__;
    }

    /**
     * <p>wml070Address をセットします。
     * @param wml070Address wml070Address
     */
    public void setWml070Address(String wml070Address) {
        wml070Address__ = wml070Address;
    }

    /**
     * <p>wml070AddressFrom を取得します。
     * @return wml070AddressFrom
     */
    public int getWml070AddressFrom() {
        return wml070AddressFrom__;
    }

    /**
     * <p>wml070AddressFrom をセットします。
     * @param wml070AddressFrom wml070AddressFrom
     */
    public void setWml070AddressFrom(int wml070AddressFrom) {
        wml070AddressFrom__ = wml070AddressFrom;
    }

    /**
     * <p>wml070AddressTo を取得します。
     * @return wml070AddressTo
     */
    public int getWml070AddressTo() {
        return wml070AddressTo__;
    }

    /**
     * <p>wml070AddressTo をセットします。
     * @param wml070AddressTo wml070AddressTo
     */
    public void setWml070AddressTo(int wml070AddressTo) {
        wml070AddressTo__ = wml070AddressTo;
    }

    /**
     * <p>wml070Type を取得します。
     * @return wml070Type
     */
    public int getWml070Type() {
        return wml070Type__;
    }

    /**
     * <p>wml070Type をセットします。
     * @param wml070Type wml070Type
     */
    public void setWml070Type(int wml070Type) {
        wml070Type__ = wml070Type;
    }

    /**
     * <p>writeDate を取得します。
     * @return writeDate
     */
    public UDate getWriteDate() {
        return writeDate__;
    }

    /**
     * <p>writeDate をセットします。
     * @param writeDate writeDate
     */
    public void setWriteDate(UDate writeDate) {
        writeDate__ = writeDate;
    }

    /**
     * <p>writeDateTo を取得します。
     * @return writeDateTo
     */
    public UDate getWriteDateTo() {
        return writeDateTo__;
    }

    /**
     * <p>writeDateTo をセットします。
     * @param writeDateTo writeDateTo
     */
    public void setWriteDateTo(UDate writeDateTo) {
        writeDateTo__ = writeDateTo;
    }

}
