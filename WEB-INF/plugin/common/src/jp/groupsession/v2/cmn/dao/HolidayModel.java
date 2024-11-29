package jp.groupsession.v2.cmn.dao;

import java.util.Comparator;

import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;

/**
 * <br>[機 能] 休日情報を保持するModelクラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class HolidayModel extends CmnHolidayTemplateModel
implements Comparator<HolidayModel> {

    /** 日付（数値） */
    private int date__ = 0;
    /** 日付（文字列） */
    private String strDate__ = null;

    /**
     * 休日比較用の数値を返却します
     * @param o1 休日モデル
     * @param o2 休日モデル
     * @return int 比較数値
     */
    public int compare(HolidayModel o1, HolidayModel o2) {
        return o1.getDate() - o2.getDate();
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
     * @return strDate を戻します。
     */
    public String getStrDate() {
        return strDate__;
    }

    /**
     * @param strDate 設定する strDate。
     */
    public void setStrDate(String strDate) {
        this.strDate__ = strDate;
    }
}
