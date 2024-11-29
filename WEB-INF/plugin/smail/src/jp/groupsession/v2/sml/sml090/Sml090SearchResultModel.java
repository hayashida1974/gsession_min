package jp.groupsession.v2.sml.sml090;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ショートメール詳細検索画面の検索結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml090SearchResultModel extends AbstractModel {

    /** マーク */
    private String resMark__ = null;
    /** 件名 */
    private String resTitle__ = null;
    /** 差出人 */
    private String resSender__ = null;
    /** 宛先 */
    private String resAtesaki__ = null;
    /** 日時 */
    private String resDate__ = null;

    /**
     * <p>resAtesaki を取得します。
     * @return resAtesaki
     */
    public String getResAtesaki() {
        return resAtesaki__;
    }
    /**
     * <p>resAtesaki をセットします。
     * @param resAtesaki resAtesaki
     */
    public void setResAtesaki(String resAtesaki) {
        resAtesaki__ = resAtesaki;
    }
    /**
     * <p>resDate を取得します。
     * @return resDate
     */
    public String getResDate() {
        return resDate__;
    }
    /**
     * <p>resDate をセットします。
     * @param resDate resDate
     */
    public void setResDate(String resDate) {
        resDate__ = resDate;
    }
    /**
     * <p>resMark を取得します。
     * @return resMark
     */
    public String getResMark() {
        return resMark__;
    }
    /**
     * <p>resMark をセットします。
     * @param resMark resMark
     */
    public void setResMark(String resMark) {
        resMark__ = resMark;
    }
    /**
     * <p>resSender を取得します。
     * @return resSender
     */
    public String getResSender() {
        return resSender__;
    }
    /**
     * <p>resSender をセットします。
     * @param resSender resSender
     */
    public void setResSender(String resSender) {
        resSender__ = resSender;
    }
    /**
     * <p>resTitle を取得します。
     * @return resTitle
     */
    public String getResTitle() {
        return resTitle__;
    }
    /**
     * <p>resTitle をセットします。
     * @param resTitle resTitle
     */
    public void setResTitle(String resTitle) {
        resTitle__ = resTitle;
    }
}