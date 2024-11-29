package jp.groupsession.v2.cmn.cmn200;

/**
 * <br>[機  能] カレンダー用JSONデータ定義を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn200HolidayMdl {
    /** 日付 */
    private String date__ = null;
    /** 休日名 */
    private String holidayName__ = null;
    /**
     * <p>date を取得します。
     * @return date
     */
    public String getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     */
    public void setDate(String date) {
        date__ = date;
    }
    /**
     * <p>holidayName を取得します。
     * @return holidayName
     */
    public String getHolidayName() {
        return holidayName__;
    }
    /**
     * <p>holidayName をセットします。
     * @param holidayName holidayName
     */
    public void setHolidayName(String holidayName) {
        holidayName__ = holidayName;
    }

}
