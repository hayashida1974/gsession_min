package jp.groupsession.v2.man.man024kn;

import java.util.Comparator;

import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 休日テンプレート情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man024knHolidayModel extends CmnHolidayTemplateModel
implements Comparable<Man024knHolidayModel>, Comparator<Man024knHolidayModel> {

    /** 日付のYYYYMMDD形式文字列 */
    private String strDate__ = null;
    /** 休日の画面表示用文字列 */
    private String viewDate__ = null;

    /** 比較用数値 */
    private int date__ = 0;

    /** 不正日付フラグ */
    private boolean dspBorderFlg__ = true;

    /** 既存休日に上書きフラグ */
    private int asterisk__ = GSConstMain.HOLIDAY_TEMPLATE_ASTERISK_FLG_NO;

    /** 振替休日フラグ */
    private boolean hurikaeFlg__ = false;

    /**
     * 不正日付フラグを取得します。
     * @return 不正日付フラグ
     */
    public boolean isDspBorderFlg() {
        return dspBorderFlg__;
    }

    /**
     * 不正日付フラグをセットします。
     * @param dspBorderFlg 不正日付フラグ
     */
    public void setDspBorderFlg(boolean dspBorderFlg) {
        dspBorderFlg__ = dspBorderFlg;
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
    public int compareTo(Man024knHolidayModel o) {

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
    public int compare(Man024knHolidayModel o1, Man024knHolidayModel o2) {

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
    public int getAsterisk() {
        return asterisk__;
    }

    /**
     * @param asterisks 設定する asterisks__。
     */
    public void setAsterisk(int asterisks) {
        asterisk__ = asterisks;
    }

    /**
     * <p>hurikaeFlg を取得します。
     * @return hurikaeFlg
     * @see jp.groupsession.v2.man.man024kn.Man024knHolidayModel#hurikaeFlg
     */
    public boolean isHurikaeFlg() {
        return hurikaeFlg__;
    }

    /**
     * <p>hurikaeFlg をセットします。
     * @param hurikaeFlg hurikaeFlg
     * @see jp.groupsession.v2.man.man024kn.Man024knHolidayModel#hurikaeFlg
     */
    public void setHurikaeFlg(boolean hurikaeFlg) {
        hurikaeFlg__ = hurikaeFlg;
    }
}
