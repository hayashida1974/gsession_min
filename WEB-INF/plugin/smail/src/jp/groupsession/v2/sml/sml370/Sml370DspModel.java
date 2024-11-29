package jp.groupsession.v2.sml.sml370;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] ショートメール統計情報表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sml370DspModel implements Serializable {

    /** 日付 */
    private String date__;
    /** 日付 (UDate) */
    private UDate uDate__;
    /** 表示用日付 */
    private String dspDate__;
    /** 受信件数 */
    private int jmailNum__;
    /** 受信件数（表示用） */
    private String strJmailNum__;
    /** 送信件数 */
    private int smailNum__;
    /** 送信件数（表示用） */
    private String strSmailNum__;

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
     * <p>jmailNum を取得します。
     * @return jmailNum
     */
    public int getJmailNum() {
        return jmailNum__;
    }
    /**
     * <p>jmailNum をセットします。
     * @param jmailNum jmailNum
     */
    public void setJmailNum(int jmailNum) {
        jmailNum__ = jmailNum;
    }
    /**
     * <p>smailNum を取得します。
     * @return smailNum
     */
    public int getSmailNum() {
        return smailNum__;
    }
    /**
     * <p>smailNum をセットします。
     * @param smailNum smailNum
     */
    public void setSmailNum(int smailNum) {
        smailNum__ = smailNum;
    }
    /**
     * <p>strJmailNum を取得します。
     * @return strJmailNum
     */
    public String getStrJmailNum() {
        return strJmailNum__;
    }
    /**
     * <p>strJmailNum をセットします。
     * @param strJmailNum strJmailNum
     */
    public void setStrJmailNum(String strJmailNum) {
        strJmailNum__ = strJmailNum;
    }
    /**
     * <p>strSmailNum を取得します。
     * @return strSmailNum
     */
    public String getStrSmailNum() {
        return strSmailNum__;
    }
    /**
     * <p>strSmailNum をセットします。
     * @param strSmailNum strSmailNum
     */
    public void setStrSmailNum(String strSmailNum) {
        strSmailNum__ = strSmailNum;
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

}