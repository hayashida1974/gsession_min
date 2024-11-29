package jp.groupsession.v2.man.man029;

import java.io.Serializable;


/**
 * <br>[機  能] 休日テンプレート 拡張情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HolidayTemplateExModel implements Serializable {

    /** 拡張入力 月 */
    private String exMonth__;
    /** 拡張入力 週 */
    private String exWeek__;
    /** 拡張入力 曜日 */
    private String exDay__;
    /**
     * @return exMonth
     */
    public String getExMonth() {
        return exMonth__;
    }
    /**
     * @param exMonth セットする exMonth
     */
    public void setExMonth(String exMonth) {
        exMonth__ = exMonth;
    }
    /**
     * @return exWeek
     */
    public String getExWeek() {
        return exWeek__;
    }
    /**
     * @param exWeek セットする exWeek
     */
    public void setExWeek(String exWeek) {
        exWeek__ = exWeek;
    }
    /**
     * @return exDay
     */
    public String getExDay() {
        return exDay__;
    }
    /**
     * @param exDay セットする exDay
     */
    public void setExDay(String exDay) {
        exDay__ = exDay;
    }

}