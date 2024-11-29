package jp.co.sjts.util.ical;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] iCalendar情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ical4jModel {

    /** タイトル */
    private String ical4jTitle__ = "";
    /** 開始日 */
    private UDate ical4jFrom__ = null;
    /** 終了日 */
    private UDate ical4jTo__ = null;

    /**
     * <p>ical4jTitle を取得します。
     * @return ical4jTitle
     */
    public String getIcal4jTitle() {
        return ical4jTitle__;
    }
    /**
     * <p>ical4jTitle をセットします。
     * @param ical4jTitle ical4jTitle
     */
    public void setIcal4jTitle(String ical4jTitle) {
        ical4jTitle__ = ical4jTitle;
    }
    /**
     * <p>ical4jFrom を取得します。
     * @return ical4jFrom
     */
    public UDate getIcal4jFrom() {
        return ical4jFrom__;
    }
    /**
     * <p>ical4jFrom をセットします。
     * @param ical4jFrom ical4jFrom
     */
    public void setIcal4jFrom(UDate ical4jFrom) {
        ical4jFrom__ = ical4jFrom;
    }
    /**
     * <p>ical4jTo を取得します。
     * @return ical4jTo
     */
    public UDate getIcal4jTo() {
        return ical4jTo__;
    }
    /**
     * <p>ical4jTo をセットします。
     * @param ical4jTo ical4jTo
     */
    public void setIcal4jTo(UDate ical4jTo) {
        ical4jTo__ = ical4jTo;
    }
}
