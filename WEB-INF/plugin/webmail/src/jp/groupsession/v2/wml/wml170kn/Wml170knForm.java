package jp.groupsession.v2.wml.wml170kn;

import jp.groupsession.v2.wml.wml170.Wml170Form;


/**
 * <br>[機  能] WEBメール 送受信ログ自動削除設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml170knForm extends Wml170Form {

    /** 表示用 自動削除設定 */
    private String delSetUp__ = null;
    /** 表示用 自動削除 年設定 */
    private String delSetUpYear__ = null;
    /** 表示用 自動削除設定 月設定 */
    private String delSetUpMonth__ = null;
    /** 表示用 自動削除設定 日設定 */
    private String delSetUpDay__ = null;
    /**
     * <p>delSetUp を取得します。
     * @return delSetUp
     */
    public String getDelSetUp() {
        return delSetUp__;
    }
    /**
     * <p>delSetUp をセットします。
     * @param delSetUp delSetUp
     */
    public void setDelSetUp(String delSetUp) {
        delSetUp__ = delSetUp;
    }
    /**
     * <p>delSetUpMonth を取得します。
     * @return delSetUpMonth
     */
    public String getDelSetUpMonth() {
        return delSetUpMonth__;
    }
    /**
     * <p>delSetUpMonth をセットします。
     * @param delSetUpMonth delSetUpMonth
     */
    public void setDelSetUpMonth(String delSetUpMonth) {
        delSetUpMonth__ = delSetUpMonth;
    }
    /**
     * <p>delSetUpYear を取得します。
     * @return delSetUpYear
     */
    public String getDelSetUpYear() {
        return delSetUpYear__;
    }
    /**
     * <p>delSetUpYear をセットします。
     * @param delSetUpYear delSetUpYear
     */
    public void setDelSetUpYear(String delSetUpYear) {
        delSetUpYear__ = delSetUpYear;
    }
    /**
     * <p>delSetUpDay を取得します。
     * @return delSetUpDay
     */
    public String getDelSetUpDay() {
        return delSetUpDay__;
    }
    /**
     * <p>delSetUpDay をセットします。
     * @param delSetUpDay delSetUpDay
     */
    public void setDelSetUpDay(String delSetUpDay) {
        delSetUpDay__ = delSetUpDay;
    }

}
