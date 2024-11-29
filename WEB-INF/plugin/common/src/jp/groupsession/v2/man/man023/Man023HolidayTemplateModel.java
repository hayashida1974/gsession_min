package jp.groupsession.v2.man.man023;

import java.util.Comparator;

import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;

/**
 * <br>[機  能] 休日テンプレート情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man023HolidayTemplateModel extends CmnHolidayTemplateModel
implements Comparable<Man023HolidayTemplateModel>, Comparator<Man023HolidayTemplateModel> {

    /** 日付のYYYYMMDD形式文字列 */
    private String strDate__ = null;
    /** 休日の画面表示用文字列 */
    private String viewDate__ = null;

    /** 比較用数値 */
    private int date__ = 0;


    /** 不正日付フラグ */
    private boolean flgNGDate__ = true;

    /** 既存休日に上書きフラグ */
    private String asterisk__ = "0";

    /**
     * @return flgNGDate__ を戻します。
     */
    public boolean getFlgNGDate() {
        return flgNGDate__;
    }

    /**
     * @param flgNGDate 設定する flgNGDate__。
     */
    public void setFlgNGDate(boolean flgNGDate) {
        flgNGDate__ = flgNGDate;
    }

    /**
     * <br>[機  能] 比較用数値の月を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @return int 比較用数値月
     */
    public int getMonth() {
        return (getHltDateMonth() + getHltExMonth());
    }

    /**
     * <br>[機  能] 休日比較用の数値を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param o 休日モデル
     * @return int 判定値
     */
    public int compareTo(Man023HolidayTemplateModel o) {

        int compareMonth = getMonth() - o.getMonth();
        if (compareMonth != 0) {
            return compareMonth;
        }

        int compareDate = getDate() - o.getDate();
        if (compareDate != 0) {
            return compareDate;
        }

        return getHltExflg() - o.getHltExflg();
    }

    /**
     * <br>[機  能] 休日比較用の数値を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param o1 休日モデル
     * @param o2 休日モデル
     * @return int 判定値
     */
    public int compare(Man023HolidayTemplateModel o1, Man023HolidayTemplateModel o2) {

        int compareMonth = o1.getMonth() - o2.getMonth();
        if (compareMonth != 0) {
            return compareMonth;
        }

        return (o1.getDate() - o2.getDate());
    }

    /**
     * @return strDate を戻します。
     */
    public String getStrDate() {
        return strDate__;
    }
    /**
     * @param strDate 設定する strDate。
     */
    public void setStrDate(String strDate) {
        strDate__ = strDate;
    }
    /**
     * @return viewDate を戻します。
     */
    public String getViewDate() {
        return viewDate__;
    }
    /**
     * @param viewDate 設定する viewDate。
     */
    public void setViewDate(String viewDate) {
        viewDate__ = viewDate;
    }

    /**
     * @return date を戻します。
     */
    public int getDate() {
        return date__;
    }

    /**
     * @param date 設定する date。
     */
    public void setDate(int date) {
        this.date__ = date;
    }

    /**
     * @return asterisks__ を戻します。
     */
    public String getAsterisk() {
        return asterisk__;
    }

    /**
     * @param asterisks 設定する asterisks__。
     */
    public void setAsterisk(String asterisks) {
        this.asterisk__ = asterisks;
    }


}
