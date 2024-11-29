package jp.groupsession.v2.man.man390;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴統計情報表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man390DspModel implements Serializable {

    /** 日付 */
    private String date__;
    /** 日付 (UDate) */
    private UDate uDate__;
    /** 表示用日付 */
    private String dspDate__;
    /** ログイン回数 */
    private long loginNum__;
    /** ログイン回数(表示用) */
    private String dspLoginNum__;
    /** ログインユーザ数 */
    private long loginUserNum__;
    /** ログインユーザ数（表示用） */
    private String dspLoginUserNum__;
    /** ログイン率 */
    private long loginRate__;
    /** ログイン率（表示用） */
    private String dspLoginRate__;
    /** 登録ユーザ数 */
    private long regUserNum__;

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
    public UDate getuDate() {
        return uDate__;
    }
    /**
     * <p>uDate をセットします。
     * @param uDate uDate
     */
    public void setuDate(UDate uDate) {
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
     * <p>loginNum を取得します。
     * @return loginNum
     */
    public long getLoginNum() {
        return loginNum__;
    }
    /**
     * <p>loginNum をセットします。
     * @param loginNum loginNum
     */
    public void setLoginNum(long loginNum) {
        loginNum__ = loginNum;
    }
    /**
     * <p>dspLoginNum を取得します。
     * @return dspLoginNum
     */
    public String getDspLoginNum() {
        return dspLoginNum__;
    }
    /**
     * <p>dspLoginNum をセットします。
     * @param dspLoginNum dspLoginNum
     */
    public void setDspLoginNum(String dspLoginNum) {
        dspLoginNum__ = dspLoginNum;
    }
    /**
     * <p>loginUserNum を取得します。
     * @return loginUserNum
     */
    public long getLoginUserNum() {
        return loginUserNum__;
    }
    /**
     * <p>loginUserNum をセットします。
     * @param loginUserNum loginUserNum
     */
    public void setLoginUserNum(long loginUserNum) {
        loginUserNum__ = loginUserNum;
    }
    /**
     * <p>dspLoginUserNum を取得します。
     * @return dspLoginUserNum
     */
    public String getDspLoginUserNum() {
        return dspLoginUserNum__;
    }
    /**
     * <p>dspLoginUserNum をセットします。
     * @param dspLoginUserNum dspLoginUserNum
     */
    public void setDspLoginUserNum(String dspLoginUserNum) {
        dspLoginUserNum__ = dspLoginUserNum;
    }
    /**
     * <p>loginRate を取得します。
     * @return loginRate
     */
    public long getLoginRate() {
        return loginRate__;
    }
    /**
     * <p>loginRate をセットします。
     * @param loginRate loginRate
     */
    public void setLoginRate(long loginRate) {
        loginRate__ = loginRate;
    }
    /**
     * <p>dspLoginRate を取得します。
     * @return dspLoginRate
     */
    public String getDspLoginRate() {
        return dspLoginRate__;
    }
    /**
     * <p>dspLoginRate をセットします。
     * @param dspLoginRate dspLoginRate
     */
    public void setDspLoginRate(String dspLoginRate) {
        dspLoginRate__ = dspLoginRate;
    }
    /**
     * <p>regUserNum を取得します。
     * @return regUserNum
     */
    public long getRegUserNum() {
        return regUserNum__;
    }
    /**
     * <p>regUserNum をセットします。
     * @param regUserNum regUserNum
     */
    public void setRegUserNum(long regUserNum) {
        regUserNum__ = regUserNum;
    }
}