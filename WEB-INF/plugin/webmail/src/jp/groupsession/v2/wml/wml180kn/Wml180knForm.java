package jp.groupsession.v2.wml.wml180kn;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.wml.wml180.Wml180Form;


/**
 * <br>[機  能] WEBメール 送受信ログ手動削除確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml180knForm extends Wml180Form {

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
    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {

        super.setHiddenParam(msgForm);
    }
}
