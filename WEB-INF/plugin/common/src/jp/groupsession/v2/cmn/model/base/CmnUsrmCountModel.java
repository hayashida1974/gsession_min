package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_USRM_COUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrmCountModel implements Serializable {

    /** CUC_DATE mapping */
    private UDate cucDate__;
    /** CUC_MONTH mapping */
    private int cucMonth__;
    /** CUC_CNT mapping */
    private long cucCnt__;
    /** CUC_LOGIN mapping */
    private long cucLogin__;
    /** CUC_USEr mapping */
    private long cucUser__;

    /**
     * <p>Default Constructor
     */
    public CmnUsrmCountModel() {
    }

    /**
     * <p>get CUC_DATE value
     * @return CUC_DATE value
     */
    public UDate getCucDate() {
        return cucDate__;
    }

    /**
     * <p>set CUC_DATE value
     * @param cucDate CUC_DATE value
     */
    public void setCucDate(UDate cucDate) {
        cucDate__ = cucDate;
    }

    /**
     * <p>get CUC_MONTH value
     * @return CUC_MONTH value
     */
    public int getCucMonth() {
        return cucMonth__;
    }

    /**
     * <p>set CUC_MONTH value
     * @param cucMonth CUC_MONTH value
     */
    public void setCucMonth(int cucMonth) {
        cucMonth__ = cucMonth;
    }

    /**
     * <p>get CUC_CNT value
     * @return CUC_CNT value
     */
    public long getCucCnt() {
        return cucCnt__;
    }

    /**
     * <p>set CUC_CNT value
     * @param cucCnt CUC_CNT value
     */
    public void setCucCnt(long cucCnt) {
        cucCnt__ = cucCnt;
    }

    /**
     * <p>cucLogin を取得します。
     * @return cucLogin
     */
    public long getCucLogin() {
        return cucLogin__;
    }

    /**
     * <p>cucLogin をセットします。
     * @param cucLogin cucLogin
     */
    public void setCucLogin(long cucLogin) {
        cucLogin__ = cucLogin;
    }

    /**
     * <p>cucUser を取得します。
     * @return cucUser
     */
    public long getCucUser() {
        return cucUser__;
    }

    /**
     * <p>cucUser をセットします。
     * @param cucUser cucUser
     */
    public void setCucUser(long cucUser) {
        cucUser__ = cucUser;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getStringFmObj(cucDate__, ""));
        buf.append(",");
        buf.append(cucMonth__);
        buf.append(",");
        buf.append(cucCnt__);
        buf.append(",");
        buf.append(cucLogin__);
        buf.append(",");
        buf.append(cucUser__);
        return buf.toString();
    }

}
