package jp.groupsession.v2.rng.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>RNG_KEIRO_STEP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngKeiroStepModel implements Serializable {

    /** RKS_SID mapping */
    private int rksSid__;
    /** RNG_SID mapping */
    private int rngSid__;
    /** RKS_SORT mapping */
    private int rksSort__;
    /** RKS_STATUS mapping */
    private int rksStatus__;
    /** RTK_SID mapping */
    private int rtkSid__;
    /** RKS_ROLL_TYPE mapping */
    private int rksRollType__;
    /** RKS_RCVDATE mapping */
    private UDate rksRcvdate__;
    /** RKS_CHKDATE mapping */
    private UDate rksChkdate__;
    /** RKS_AUID mapping */
    private int rksAuid__;
    /** RKS_ADATE mapping */
    private UDate rksAdate__;
    /** RKS_EUID mapping */
    private int rksEuid__;
    /** RKS_EDATE mapping */
    private UDate rksEdate__;
    /** RKS_BELONG_SID mapping */
    private int rksBelongSid__ = 0;
    /** RKS_KEIRO_KOETU mapping*/
    private int rksKeiroKoetu__ = 0;
    /** RKS_KOETU_SIZI mapping*/
    private int rksKoetuSizi__ = 0;
    /** RKS_ADDSTEP*/
    private int rksAddstep__ = 0;

    /**
     * <p>Default Constructor
     */
    public RngKeiroStepModel() {
    }

    /**
     * <p>get RKS_SID value
     * @return RKS_SID value
     */
    public int getRksSid() {
        return rksSid__;
    }

    /**
     * <p>set RKS_SID value
     * @param rksSid RKS_SID value
     */
    public void setRksSid(int rksSid) {
        rksSid__ = rksSid;
    }

    /**
     * <p>get RNG_SID value
     * @return RNG_SID value
     */
    public int getRngSid() {
        return rngSid__;
    }

    /**
     * <p>set RNG_SID value
     * @param rngSid RNG_SID value
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }

    /**
     * <p>get RKS_SORT value
     * @return RKS_SORT value
     */
    public int getRksSort() {
        return rksSort__;
    }

    /**
     * <p>set RKS_SORT value
     * @param rksSort RKS_SORT value
     */
    public void setRksSort(int rksSort) {
        rksSort__ = rksSort;
    }

    /**
     * <p>get RKS_STATUS value
     * @return RKS_STATUS value
     */
    public int getRksStatus() {
        return rksStatus__;
    }

    /**
     * <p>set RKS_STATUS value
     * @param rksStatus RKS_STATUS value
     */
    public void setRksStatus(int rksStatus) {
        rksStatus__ = rksStatus;
    }

    /**
     * <p>get RTK_SID value
     * @return RTK_SID value
     */
    public int getRtkSid() {
        return rtkSid__;
    }

    /**
     * <p>set RTK_SID value
     * @param rtkSid RTK_SID value
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
    }

    /**
     * <p>get RKS_ROLL_TYPE value
     * @return RKS_ROLL_TYPE value
     */
    public int getRksRollType() {
        return rksRollType__;
    }

    /**
     * <p>set RKS_ROLL_TYPE value
     * @param rksRollType RKS_ROLL_TYPE value
     */
    public void setRksRollType(int rksRollType) {
        rksRollType__ = rksRollType;
    }

    /**
     * <p>get RKS_RCVDATE value
     * @return RKS_RCVDATE value
     */
    public UDate getRksRcvdate() {
        return rksRcvdate__;
    }

    /**
     * <p>set RKS_RCVDATE value
     * @param rksRcvdate RKS_RCVDATE value
     */
    public void setRksRcvdate(UDate rksRcvdate) {
        rksRcvdate__ = rksRcvdate;
    }

    /**
     * <p>get RKS_CHKDATE value
     * @return RKS_CHKDATE value
     */
    public UDate getRksChkdate() {
        return rksChkdate__;
    }

    /**
     * <p>set RKS_CHKDATE value
     * @param rksChkdate RKS_CHKDATE value
     */
    public void setRksChkdate(UDate rksChkdate) {
        rksChkdate__ = rksChkdate;
    }

    /**
     * <p>get RKS_AUID value
     * @return RKS_AUID value
     */
    public int getRksAuid() {
        return rksAuid__;
    }

    /**
     * <p>set RKS_AUID value
     * @param rksAuid RKS_AUID value
     */
    public void setRksAuid(int rksAuid) {
        rksAuid__ = rksAuid;
    }

    /**
     * <p>get RKS_ADATE value
     * @return RKS_ADATE value
     */
    public UDate getRksAdate() {
        return rksAdate__;
    }

    /**
     * <p>set RKS_ADATE value
     * @param rksAdate RKS_ADATE value
     */
    public void setRksAdate(UDate rksAdate) {
        rksAdate__ = rksAdate;
    }

    /**
     * <p>get RKS_EUID value
     * @return RKS_EUID value
     */
    public int getRksEuid() {
        return rksEuid__;
    }

    /**
     * <p>set RKS_EUID value
     * @param rksEuid RKS_EUID value
     */
    public void setRksEuid(int rksEuid) {
        rksEuid__ = rksEuid;
    }

    /**
     * <p>get RKS_EDATE value
     * @return RKS_EDATE value
     */
    public UDate getRksEdate() {
        return rksEdate__;
    }

    /**
     * <p>set RKS_EDATE value
     * @param rksEdate RKS_EDATE value
     */
    public void setRksEdate(UDate rksEdate) {
        rksEdate__ = rksEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rksSid__);
        buf.append(",");
        buf.append(rngSid__);
        buf.append(",");
        buf.append(rksSort__);
        buf.append(",");
        buf.append(rksStatus__);
        buf.append(",");
        buf.append(rtkSid__);
        buf.append(",");
        buf.append(rksRollType__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rksRcvdate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rksChkdate__, ""));
        buf.append(",");
        buf.append(rksAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rksAdate__, ""));
        buf.append(",");
        buf.append(rksEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rksEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>rksBelongSid を取得します。
     * @return rksBelongSid
     */
    public int getRksBelongSid() {
        return rksBelongSid__;
    }

    /**
     * <p>rksBelongSid をセットします。
     * @param rksBelongSid rksBelongSid
     */
    public void setRksBelongSid(int rksBelongSid) {
        rksBelongSid__ = rksBelongSid;
    }

    /**
     * <p>get RKS_KEIRO_KOETU value
     * @return RKS_KEIRO_KOETU value
     */
    public int getRksKeiroKoetu() {
        return rksKeiroKoetu__;
    }

    /**
     * <p>set RKS_KEIRO_KOETU value
     * @param rksKeiroKoetu RKS_KEIRO_KOETU value
     */
    public void setRksKeiroKoetu(int rksKeiroKoetu) {
        rksKeiroKoetu__ = rksKeiroKoetu;
    }

    /**
     * <p>get RKS_KOETU_SIZI value
     * @return RKS_KOETU_SIZI value
     */
    public int getRksKoetuSizi() {
        return rksKoetuSizi__;
    }

    /**
     * <p>set RKS_SID value
     * @param rksKoetuSizi RKS_KOETU_SIZI value
     */
    public void setRksKoetuSizi(int rksKoetuSizi) {
        rksKoetuSizi__ = rksKoetuSizi;
    }

    /**
     * <p>rksAddstep を取得します。
     * @return rksAddstep
     * @see jp.groupsession.v2.rng.model.RngKeiroStepModel#rksAddstep__
     */
    public int getRksAddstep() {
        return rksAddstep__;
    }

    /**
     * <p>rksAddstep をセットします。
     * @param rksAddstep rksAddstep
     * @see jp.groupsession.v2.rng.model.RngKeiroStepModel#rksAddstep__
     */
    public void setRksAddstep(int rksAddstep) {
        rksAddstep__ = rksAddstep;
    }

}
