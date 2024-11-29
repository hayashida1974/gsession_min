package jp.groupsession.v2.wml.wml260;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール 予約送信メール一覧画面の検索パラメータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260SearchParameterModel {

    /** アカウント名 */
    private String wml260AccountName__ = null;
    /** 件名 */
    private List<String> wml260Title__ = null;
    /** メールアドレス */
    private String wml260Address__ = null;
    /** メールアドレス 差出人 */
    private int wml260AddressFrom__ = 0;
    /** メールアドレス 宛先 */
    private int wml260AddressTo__ = 0;
    /** 日時 年 */
    private int wml260SendDateYear__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 月 */
    private int wml260SendDateMonth__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 日 */
    private int wml260SendDateDay__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時To 年 */
    private int wml260SendDateYearTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時To 月 */
    private int wml260SendDateMonthTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時To 日 */
    private int wml260SendDateDayTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 条件 */
    private int wml260SendDateCondition__ = GSConstWebmail.DATE_KBN_EQUAL;
    /** 日時 */
    private UDate writeDate__ = null;
    /** 日時 To */
    private UDate writeDateTo__ = null;
    /** 種別 */
    private int wml260Type__ = GSConstWebmail.TYPE_FREE;
    /** 1ページの件数 */
    private int limit__ = 0;
    /** カーソルスタート位置 */
    private int start__ = 0;
    /** ソートキー */
    private int sortKey__ = GSConstWebmail.SKEY_TITLE;
    /** 並び順 */
    private int order__ = GSConstWebmail.ORDER_ASC;

    /** メッセージ番号 */
    private long[] mailNumList__ = null;

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
        setWml260Title(wBiz.setKeyword(textWordTit));
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
     * <p>wml260Title を取得します。
     * @return wml260Title
     */
    public List<String> getWml260Title() {
        return wml260Title__;
    }
    /**
     * <p>wml260Title をセットします。
     * @param wml260Title wml260Title
     */
    public void setWml260Title(List<String> wml260Title) {
        wml260Title__ = wml260Title;
    }
    /**
     * <p>wml260SendDateCondition を取得します。
     * @return wml260SendDateCondition
     */
    public int getWml260SendDateCondition() {
        return wml260SendDateCondition__;
    }
    /**
     * <p>wml260SendDateCondition をセットします。
     * @param wml260SendDateCondition wml260SendDateCondition
     */
    public void setWml260SendDateCondition(int wml260SendDateCondition) {
        wml260SendDateCondition__ = wml260SendDateCondition;
    }
    /**
     * <p>wml260SendDateDay を取得します。
     * @return wml260SendDateDay
     */
    public int getWml260SendDateDay() {
        return wml260SendDateDay__;
    }
    /**
     * <p>wml260SendDateDay をセットします。
     * @param wml260SendDateDay wml260SendDateDay
     */
    public void setWml260SendDateDay(int wml260SendDateDay) {
        wml260SendDateDay__ = wml260SendDateDay;
    }
    /**
     * <p>wml260SendDateMonth を取得します。
     * @return wml260SendDateMonth
     */
    public int getWml260SendDateMonth() {
        return wml260SendDateMonth__;
    }
    /**
     * <p>wml260SendDateMonth をセットします。
     * @param wml260SendDateMonth wml260SendDateMonth
     */
    public void setWml260SendDateMonth(int wml260SendDateMonth) {
        wml260SendDateMonth__ = wml260SendDateMonth;
    }
    /**
     * <p>wml260SendDateYear を取得します。
     * @return wml260SendDateYear
     */
    public int getWml260SendDateYear() {
        return wml260SendDateYear__;
    }
    /**
     * <p>wml260SendDateYear をセットします。
     * @param wml260SendDateYear wml260SendDateYear
     */
    public void setWml260SendDateYear(int wml260SendDateYear) {
        wml260SendDateYear__ = wml260SendDateYear;
    }

    /**
     * <p>wml260SendDateDayTo を取得します。
     * @return wml260SendDateDayTo
     */
    public int getWml260SendDateDayTo() {
        return wml260SendDateDayTo__;
    }

    /**
     * <p>wml260SendDateDayTo をセットします。
     * @param wml260SendDateDayTo wml260SendDateDayTo
     */
    public void setWml260SendDateDayTo(int wml260SendDateDayTo) {
        wml260SendDateDayTo__ = wml260SendDateDayTo;
    }

    /**
     * <p>wml260SendDateMonthTo を取得します。
     * @return wml260SendDateMonthTo
     */
    public int getWml260SendDateMonthTo() {
        return wml260SendDateMonthTo__;
    }

    /**
     * <p>wml260SendDateMonthTo をセットします。
     * @param wml260SendDateMonthTo wml260SendDateMonthTo
     */
    public void setWml260SendDateMonthTo(int wml260SendDateMonthTo) {
        wml260SendDateMonthTo__ = wml260SendDateMonthTo;
    }

    /**
     * <p>wml260SendDateYearTo を取得します。
     * @return wml260SendDateYearTo
     */
    public int getWml260SendDateYearTo() {
        return wml260SendDateYearTo__;
    }

    /**
     * <p>wml260SendDateYearTo をセットします。
     * @param wml260SendDateYearTo wml260SendDateYearTo
     */
    public void setWml260SendDateYearTo(int wml260SendDateYearTo) {
        wml260SendDateYearTo__ = wml260SendDateYearTo;
    }

    /**
     * <p>wml260AccountName を取得します。
     * @return wml260AccountName
     */
    public String getWml260AccountName() {
        return wml260AccountName__;
    }

    /**
     * <p>wml260AccountName をセットします。
     * @param wml260AccountName wml260AccountName
     */
    public void setWml260AccountName(String wml260AccountName) {
        wml260AccountName__ = wml260AccountName;
    }

    /**
     * <p>wml260Address を取得します。
     * @return wml260Address
     */
    public String getWml260Address() {
        return wml260Address__;
    }

    /**
     * <p>wml260Address をセットします。
     * @param wml260Address wml260Address
     */
    public void setWml260Address(String wml260Address) {
        wml260Address__ = wml260Address;
    }

    /**
     * <p>wml260AddressFrom を取得します。
     * @return wml260AddressFrom
     */
    public int getWml260AddressFrom() {
        return wml260AddressFrom__;
    }

    /**
     * <p>wml260AddressFrom をセットします。
     * @param wml260AddressFrom wml260AddressFrom
     */
    public void setWml260AddressFrom(int wml260AddressFrom) {
        wml260AddressFrom__ = wml260AddressFrom;
    }

    /**
     * <p>wml260AddressTo を取得します。
     * @return wml260AddressTo
     */
    public int getWml260AddressTo() {
        return wml260AddressTo__;
    }

    /**
     * <p>wml260AddressTo をセットします。
     * @param wml260AddressTo wml260AddressTo
     */
    public void setWml260AddressTo(int wml260AddressTo) {
        wml260AddressTo__ = wml260AddressTo;
    }

    /**
     * <p>wml260Type を取得します。
     * @return wml260Type
     */
    public int getWml260Type() {
        return wml260Type__;
    }

    /**
     * <p>wml260Type をセットします。
     * @param wml260Type wml260Type
     */
    public void setWml260Type(int wml260Type) {
        wml260Type__ = wml260Type;
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

    /**
     * <p>mailNumList を取得します。
     * @return mailNumList
     */
    public long[] getMailNumList() {
        return mailNumList__;
    }

    /**
     * <p>mailNumList をセットします。
     * @param mailNumList mailNumList
     */
    public void setMailNumList(long[] mailNumList) {
        mailNumList__ = mailNumList;
    }

}
