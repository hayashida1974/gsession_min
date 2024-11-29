package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 稟議 管理者設定 申請ID一覧のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiIdModel {

    /** 申請SID*/
    private int rngSid__ = 0;
    /** 申請IDフォーマット*/
    private String rngFormat__ = null;
    /** 申請初期値*/
    private int rngInit__ = 1;
    /** 申請手動*/
    private int rngManual__ = 0;
    /** リセット*/
    private int rngReset__ = 0;
    /** 0埋め*/
    private int rngZeroume__ = 0;
    /** 稟議タイトル*/
    private String rngTitle__ = null;
    /** RID_USE_DATE mapping */
    private UDate ridUseDate__;

    /**
     * <p>rngSid を取得します。
     * @return rngSid
     */
    public int getRngSid() {
        return rngSid__;
    }
    /**
     * <p>rngSid をセットします。
     * @param rngSid rngSid
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }
    /**
     * <p>rngFormat を取得します。
     * @return rngFormat
     */
    public String getRngFormat() {
        return rngFormat__;
    }
    /**
     * <p>rngFormat をセットします。
     * @param rngFormat rngFormat
     */
    public void setRngFormat(String rngFormat) {
        rngFormat__ = rngFormat;
    }
    /**
     * <p>rngInit を取得します。
     * @return rngInit
     */
    public int getRngInit() {
        return rngInit__;
    }
    /**
     * <p>rngInit をセットします。
     * @param rngInit rngInit
     */
    public void setRngInit(int rngInit) {
        rngInit__ = rngInit;
    }
    /**
     * <p>rngManual を取得します。
     * @return rngManual
     */
    public int getRngManual() {
        return rngManual__;
    }
    /**
     * <p>rngManual をセットします。
     * @param rngManual rngManual
     */
    public void setRngManual(int rngManual) {
        rngManual__ = rngManual;
    }
    /**
     * <p>rngReset を取得します。
     * @return rngReset
     */
    public int getRngReset() {
        return rngReset__;
    }
    /**
     * <p>rngReset をセットします。
     * @param rngReset rngReset
     */
    public void setRngReset(int rngReset) {
        rngReset__ = rngReset;
    }
    /**
     * <p>rngZeroume を取得します。
     * @return rngZeroume
     */
    public int getRngZeroume() {
        return rngZeroume__;
    }
    /**
     * <p>rngZeroume をセットします。
     * @param rngZeroume rngZeroume
     */
    public void setRngZeroume(int rngZeroume) {
        rngZeroume__ = rngZeroume;
    }
    /**
     * <p>rngTitle を取得します。
     * @return rngTitle
     */
    public String getRngTitle() {
        return rngTitle__;
    }
    /**
     * <p>rngTitle をセットします。
     * @param rngTitle rngTitle
     */
    public void setRngTitle(String rngTitle) {
        rngTitle__ = rngTitle;
    }
    /**
     * <p>get RID_USE_DATE value
     * @return RID_USE_DATE value
     */
    public UDate getRidUseDate() {
        return ridUseDate__;
    }

    /**
     * <p>set RID_USE_DATE value
     * @param ridUseDate RID_USE_DATE value
     */
    public void setRidUseDate(UDate ridUseDate) {
        ridUseDate__ = ridUseDate;
    }


}
