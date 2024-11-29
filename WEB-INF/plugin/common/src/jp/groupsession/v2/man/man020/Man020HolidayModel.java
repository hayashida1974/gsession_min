package jp.groupsession.v2.man.man020;

import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;

/**
 * <br>[機  能] 休日情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man020HolidayModel extends CmnHolidayModel {

    /** 休日のYYYYMMDD形式文字列 */
    private String strDate__ = null;
    /** 休日の画面表示用文字列 */
    private String viewDate__ = null;

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

}
