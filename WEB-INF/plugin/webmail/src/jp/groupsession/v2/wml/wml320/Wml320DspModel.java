package jp.groupsession.v2.wml.wml320;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] WEBメール一括削除リスト表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Wml320DspModel implements Serializable {

    /** 添付 */
    private long mailNum__;
    /** 添付 */
    private int tempFlg__;
    /** 開封状況(既読・未読) */
    private int readed__;
    /** 送信先 */
    private String dest__;
    /** 差出人 */
    private String from__;
    /** 件名 */
    private String title__;
    /** 日付 (UDate) */
    private UDate uDate__;
    /** 表示用日付 */
    private String dspDate__;

    /**
     * <p>mailNum を取得します。
     * @return mailNum
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#mailNum__
     */
    public long getMailNum() {
        return mailNum__;
    }
    /**
     * <p>mailNum をセットします。
     * @param mailNum mailNum
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#mailNum__
     */
    public void setMailNum(long mailNum) {
        mailNum__ = mailNum;
    }
    /**
     * <p>tempFlg を取得します。
     * @return tempFlg
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#tempFlg__
     */
    public int getTempFlg() {
        return tempFlg__;
    }
    /**
     * <p>tempFlg をセットします。
     * @param tempFlg tempFlg
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#tempFlg__
     */
    public void setTempFlg(int tempFlg) {
        tempFlg__ = tempFlg;
    }
    /**
     * <p>readed を取得します。
     * @return readed
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#readed__
     */
    public int getReaded() {
        return readed__;
    }
    /**
     * <p>readed をセットします。
     * @param readed readed
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#readed__
     */
    public void setReaded(int readed) {
        readed__ = readed;
    }
    /**
     * <p>dest を取得します。
     * @return dest
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#dest__
     */
    public String getDest() {
        return dest__;
    }
    /**
     * <p>dest をセットします。
     * @param dest dest
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#dest__
     */
    public void setDest(String dest) {
        dest__ = dest;
    }
    /**
     * <p>from を取得します。
     * @return from
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#from__
     */
    public String getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#from__
     */
    public void setFrom(String from) {
        from__ = from;
    }
    /**
     * <p>title を取得します。
     * @return title
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#title__
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#title__
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>uDate を取得します。
     * @return uDate
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#uDate__
     */
    public UDate getuDate() {
        return uDate__;
    }
    /**
     * <p>uDate をセットします。
     * @param uDate uDate
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#uDate__
     */
    public void setuDate(UDate uDate) {
        uDate__ = uDate;
    }
    /**
     * <p>dspDate を取得します。
     * @return dspDate
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#dspDate__
     */
    public String getDspDate() {
        return dspDate__;
    }
    /**
     * <p>dspDate をセットします。
     * @param dspDate dspDate
     * @see jp.groupsession.v2.wml.wml320.Wml320DspModel#dspDate__
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }
}