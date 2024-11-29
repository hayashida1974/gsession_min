package jp.groupsession.v2.sml.sml400kn;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sml.sml400.Sml400Form;

/**
 * <br>[機  能] ショートメール 管理者設定 表示設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml400knForm extends Sml400Form {

    /** 表示用 表示件数 */
    private String sml400knMaxDsp__ = null;
    /** 表示用 自動リロード時間 */
    private String sml400knReloadTime__ = null;
    /** 表示用 写真表示設定 */
    private String sml400knPhotoDsp__ = null;
    /** 表示用 添付画像表示設定 */
    private String sml400knAttachImgDsp__ = null;

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
    }

    /**
     * <p>sml400knMaxDsp を取得します。
     * @return sml400knMaxDsp
     */
    public String getSml400knMaxDsp() {
        return sml400knMaxDsp__;
    }

    /**
     * <p>sml400knMaxDsp をセットします。
     * @param sml400knMaxDsp sml400knMaxDsp
     */
    public void setSml400knMaxDsp(String sml400knMaxDsp) {
        sml400knMaxDsp__ = sml400knMaxDsp;
    }

    /**
     * <p>sml400knReloadTime を取得します。
     * @return sml400knReloadTime
     */
    public String getSml400knReloadTime() {
        return sml400knReloadTime__;
    }

    /**
     * <p>sml400knReloadTime をセットします。
     * @param sml400knReloadTime sml400knReloadTime
     */
    public void setSml400knReloadTime(String sml400knReloadTime) {
        sml400knReloadTime__ = sml400knReloadTime;
    }

    /**
     * <p>sml400knPhotoDsp を取得します。
     * @return sml400knPhotoDsp
     */
    public String getSml400knPhotoDsp() {
        return sml400knPhotoDsp__;
    }

    /**
     * <p>sml400knPhotoDsp をセットします。
     * @param sml400knPhotoDsp sml400knPhotoDsp
     */
    public void setSml400knPhotoDsp(String sml400knPhotoDsp) {
        sml400knPhotoDsp__ = sml400knPhotoDsp;
    }

    /**
     * <p>sml400knAttachImgDsp を取得します。
     * @return sml400knAttachImgDsp
     */
    public String getSml400knAttachImgDsp() {
        return sml400knAttachImgDsp__;
    }

    /**
     * <p>sml400knAttachImgDsp をセットします。
     * @param sml400knAttachImgDsp sml400knAttachImgDsp
     */
    public void setSml400knAttachImgDsp(String sml400knAttachImgDsp) {
        sml400knAttachImgDsp__ = sml400knAttachImgDsp;
    }
}
