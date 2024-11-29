package jp.groupsession.v2.cmn.cmn300;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] 自動削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn300Form extends AbstractGsForm {
    /** バッチ処理実行時間 */
    private String batchTime__ = "";

    /** スケジュール表示フラグ*/
    private int cmn300schDsp__ = 0;
    /** 掲示板表示フラグ*/
    private int cmn300bbsDsp__ = 0;
    /** ショートメール表示フラグ*/
    private int cmn300smlDsp__ = 0;
    /** 施設予約表示フラグ*/
    private int cmn300rsvDsp__ = 0;
    /** 日報表示フラグ*/
    private int cmn300ntpDsp__ = 0;
    /** 回覧板表示フラグ*/
    private int cmn300cirDsp__ = 0;
    /** ショートメール管理表示フラグ*/
    private int cmn300wmlDsp__ = 0;
    /** 稟議表示フラグ*/
    private int cmn300rngDsp__ = 0;
    /** アンケート表示フラグ*/
    private int cmn300enqDsp__ = 0;
    /** チャット表示フラグ*/
    private int cmn300chtDsp__ = 0;
    /** メモ表示フラグ*/
    private int cmn300memDsp__ = 0;

    /**
     * <p>batchTime を取得します。
     * @return batchTime
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#batchTime__
     */
    public String getBatchTime() {
        return batchTime__;
    }
    /**
     * <p>batchTime をセットします。
     * @param batchTime batchTime
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#batchTime__
     */
    public void setBatchTime(String batchTime) {
        batchTime__ = batchTime;
    }
    /**
     * <p>cmn300schDsp を取得します。
     * @return cmn300schDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300schDsp__
     */
    public int getCmn300schDsp() {
        return cmn300schDsp__;
    }
    /**
     * <p>cmn300schDsp をセットします。
     * @param cmn300schDsp cmn300schDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300schDsp__
     */
    public void setCmn300schDsp(int cmn300schDsp) {
        cmn300schDsp__ = cmn300schDsp;
    }
    /**
     * <p>cmn300bbsDsp を取得します。
     * @return cmn300bbsDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300bbsDsp__
     */
    public int getCmn300bbsDsp() {
        return cmn300bbsDsp__;
    }
    /**
     * <p>cmn300bbsDsp をセットします。
     * @param cmn300bbsDsp cmn300bbsDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300bbsDsp__
     */
    public void setCmn300bbsDsp(int cmn300bbsDsp) {
        cmn300bbsDsp__ = cmn300bbsDsp;
    }
    /**
     * <p>cmn300smlDsp を取得します。
     * @return cmn300smlDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300smlDsp__
     */
    public int getCmn300smlDsp() {
        return cmn300smlDsp__;
    }
    /**
     * <p>cmn300smlDsp をセットします。
     * @param cmn300smlDsp cmn300smlDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300smlDsp__
     */
    public void setCmn300smlDsp(int cmn300smlDsp) {
        cmn300smlDsp__ = cmn300smlDsp;
    }
    /**
     * <p>cmn300rsvDsp を取得します。
     * @return cmn300rsvDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300rsvDsp__
     */
    public int getCmn300rsvDsp() {
        return cmn300rsvDsp__;
    }
    /**
     * <p>cmn300rsvDsp をセットします。
     * @param cmn300rsvDsp cmn300rsvDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300rsvDsp__
     */
    public void setCmn300rsvDsp(int cmn300rsvDsp) {
        cmn300rsvDsp__ = cmn300rsvDsp;
    }
    /**
     * <p>cmn300ntpDsp を取得します。
     * @return cmn300ntpDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300ntpDsp__
     */
    public int getCmn300ntpDsp() {
        return cmn300ntpDsp__;
    }
    /**
     * <p>cmn300ntpDsp をセットします。
     * @param cmn300ntpDsp cmn300ntpDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300ntpDsp__
     */
    public void setCmn300ntpDsp(int cmn300ntpDsp) {
        cmn300ntpDsp__ = cmn300ntpDsp;
    }
    /**
     * <p>cmn300cirDsp を取得します。
     * @return cmn300cirDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300cirDsp__
     */
    public int getCmn300cirDsp() {
        return cmn300cirDsp__;
    }
    /**
     * <p>cmn300cirDsp をセットします。
     * @param cmn300cirDsp cmn300cirDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300cirDsp__
     */
    public void setCmn300cirDsp(int cmn300cirDsp) {
        cmn300cirDsp__ = cmn300cirDsp;
    }
    /**
     * <p>cmn300wmlDsp を取得します。
     * @return cmn300wmlDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300wmlDsp__
     */
    public int getCmn300wmlDsp() {
        return cmn300wmlDsp__;
    }
    /**
     * <p>cmn300wmlDsp をセットします。
     * @param cmn300wmlDsp cmn300wmlDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300wmlDsp__
     */
    public void setCmn300wmlDsp(int cmn300wmlDsp) {
        cmn300wmlDsp__ = cmn300wmlDsp;
    }
    /**
     * <p>cmn300rngDsp を取得します。
     * @return cmn300rngDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300rngDsp__
     */
    public int getCmn300rngDsp() {
        return cmn300rngDsp__;
    }
    /**
     * <p>cmn300rngDsp をセットします。
     * @param cmn300rngDsp cmn300rngDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300rngDsp__
     */
    public void setCmn300rngDsp(int cmn300rngDsp) {
        cmn300rngDsp__ = cmn300rngDsp;
    }
    /**
     * <p>cmn300enqDsp を取得します。
     * @return cmn300enqDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300enqDsp__
     */
    public int getCmn300enqDsp() {
        return cmn300enqDsp__;
    }
    /**
     * <p>cmn300enqDsp をセットします。
     * @param cmn300enqDsp cmn300enqDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300enqDsp__
     */
    public void setCmn300enqDsp(int cmn300enqDsp) {
        cmn300enqDsp__ = cmn300enqDsp;
    }
    /**
     * <p>cmn300chtDsp を取得します。
     * @return cmn300chtDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300chtDsp__
     */
    public int getCmn300chtDsp() {
        return cmn300chtDsp__;
    }
    /**
     * <p>cmn300chtDsp をセットします。
     * @param cmn300chtDsp cmn300chtDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300chtDsp__
     */
    public void setCmn300chtDsp(int cmn300chtDsp) {
        cmn300chtDsp__ = cmn300chtDsp;
    }
    /**
     * <p>cmn300memDsp を取得します。
     * @return cmn300memDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300memDsp__
     */
    public int getCmn300memDsp() {
        return cmn300memDsp__;
    }
    /**
     * <p>cmn300memDsp をセットします。
     * @param cmn300memDsp cmn300memDsp
     * @see jp.groupsession.v2.cmn.cmn300.Cmn300Form#cmn300memDsp__
     */
    public void setCmn300memDsp(int cmn300memDsp) {
        cmn300memDsp__ = cmn300memDsp;
    }
}