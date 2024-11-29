package jp.groupsession.v2.bbs.bbs180;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 回覧板統計情報表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs180DspModel implements Serializable {

    /** 日付 */
    private String date__;
    /** 日付 (UDate) */
    private UDate uDate__;
    /** 表示用日付 */
    private String dspDate__;
    /** 閲覧件数 */
    private int vbbsNum__;
    /** 閲覧件数（表示用） */
    private String strVbbsNum__;
    /** 投稿件数 */
    private int wbbsNum__;
    /** 投稿件数（表示用） */
    private String strWbbsNum__;
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
     * <p>uDate を取得します。
     * @return uDate
     */
    public UDate getUDate() {
        return uDate__;
    }
    /**
     * <p>uDate をセットします。
     * @param uDate uDate
     */
    public void setUDate(UDate uDate) {
        uDate__ = uDate;
    }
    /**
     * <p>dspDate を取得します。
     * @return dspDate
     */
    public String getDspDate() {
        return dspDate__;
    }
    /**
     * <p>dspDate をセットします。
     * @param dspDate dspDate
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }
    /**
     * <p>vbbsNum を取得します。
     * @return vbbsNum
     */
    public int getVbbsNum() {
        return vbbsNum__;
    }
    /**
     * <p>vbbsNum をセットします。
     * @param vbbsNum vbbsNum
     */
    public void setVbbsNum(int vbbsNum) {
        vbbsNum__ = vbbsNum;
    }
    /**
     * <p>strVbbsNum を取得します。
     * @return strVbbsNum
     */
    public String getStrVbbsNum() {
        return strVbbsNum__;
    }
    /**
     * <p>strVbbsNum をセットします。
     * @param strVbbsNum strVbbsNum
     */
    public void setStrVbbsNum(String strVbbsNum) {
        strVbbsNum__ = strVbbsNum;
    }
    /**
     * <p>wbbsNum を取得します。
     * @return wbbsNum
     */
    public int getWbbsNum() {
        return wbbsNum__;
    }
    /**
     * <p>wbbsNum をセットします。
     * @param wbbsNum wbbsNum
     */
    public void setWbbsNum(int wbbsNum) {
        wbbsNum__ = wbbsNum;
    }
    /**
     * <p>strWbbsNum を取得します。
     * @return strWbbsNum
     */
    public String getStrWbbsNum() {
        return strWbbsNum__;
    }
    /**
     * <p>strWbbsNum をセットします。
     * @param strWbbsNum strWbbsNum
     */
    public void setStrWbbsNum(String strWbbsNum) {
        strWbbsNum__ = strWbbsNum;
    }

}