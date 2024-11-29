package jp.groupsession.v2.wml.wml180kn;

import jp.groupsession.v2.wml.wml180.Wml180ParamModel;

/**
 * <br>[機  能] WEBメール 送受信ログ手動削除確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml180knParamModel extends Wml180ParamModel {
    /** 表示用 手動削除設定 */
    private String manuDelSetUp__ = null;
    /** 表示用 手動削除 年設定 */
    private String manuDelSetUpYear__ = null;
    /** 表示用 手動削除設定 月設定 */
    private String manuDelSetUpMonth__ = null;
    /** 表示用 手動削除設定 日設定 */
    private String manuDelSetUpDay__ = null;
    /**
     * <p>manuDelSetUp を取得します。
     * @return manuDelSetUp
     */
    public String getManuDelSetUp() {
        return manuDelSetUp__;
    }
    /**
     * <p>manuDelSetUp をセットします。
     * @param manuDelSetUp manuDelSetUp
     */
    public void setManuDelSetUp(String manuDelSetUp) {
        manuDelSetUp__ = manuDelSetUp;
    }
    /**
     * <p>manuDelSetUpMonth を取得します。
     * @return manuDelSetUpMonth
     */
    public String getManuDelSetUpMonth() {
        return manuDelSetUpMonth__;
    }
    /**
     * <p>manuDelSetUpMonth をセットします。
     * @param manuDelSetUpMonth manuDelSetUpMonth
     */
    public void setManuDelSetUpMonth(String manuDelSetUpMonth) {
        manuDelSetUpMonth__ = manuDelSetUpMonth;
    }
    /**
     * <p>manuDelSetUpYear を取得します。
     * @return manuDelSetUpYear
     */
    public String getManuDelSetUpYear() {
        return manuDelSetUpYear__;
    }
    /**
     * <p>manuDelSetUpYear をセットします。
     * @param manuDelSetUpYear manuDelSetUpYear
     */
    public void setManuDelSetUpYear(String manuDelSetUpYear) {
        manuDelSetUpYear__ = manuDelSetUpYear;
    }
    /**
     * <p>manuDelSetUpDay を取得します。
     * @return manuDelSetUpDay
     */
    public String getManuDelSetUpDay() {
        return manuDelSetUpDay__;
    }
    /**
     * <p>manuDelSetUpDay をセットします。
     * @param manuDelSetUpDay manuDelSetUpDay
     */
    public void setManuDelSetUpDay(String manuDelSetUpDay) {
        manuDelSetUpDay__ = manuDelSetUpDay;
    }
}