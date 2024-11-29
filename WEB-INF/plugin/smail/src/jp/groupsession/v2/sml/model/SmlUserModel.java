package jp.groupsession.v2.sml.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SML_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlUserModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** SML_MAX_DSP mapping */
    private int smlMaxDsp__;
    /** SML_AUID mapping */
    private int smlAuid__;
    /** SML_ADATE mapping */
    private UDate smlAdate__;
    /** SML_EUID mapping */
    private int smlEuid__;
    /** SML_EDATE mapping */
    private UDate smlEdate__;
    /** SML_RELOAD mapping */
    private int smlReload__;
    /** SML_MAIN_KBN mapping */
    private int smlMainKbn__;
    /** SML_MAIN_CNT mapping */
    private int smlMainCnt__;
    /** SML_MAIN_SORT mapping */
    private int smlMainSort__;
    /** SML_PHOTO_DSP mapping */
    private int smlPhotoDsp__;
    /** SML_PHOTO_DSP mapping */
    private int smlTempDsp__;
    /**
     * <p>smlReload を取得します。
     * @return smlReload
     */
    public int getSmlReload() {
        return smlReload__;
    }

    /**
     * <p>smlReload をセットします。
     * @param smlReload smlReload
     */
    public void setSmlReload(int smlReload) {
        smlReload__ = smlReload;
    }

    /**
     * <p>Default Constructor
     */
    public SmlUserModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get SML_MAX_DSP value
     * @return SML_MAX_DSP value
     */
    public int getSmlMaxDsp() {
        return smlMaxDsp__;
    }

    /**
     * <p>set SML_MAX_DSP value
     * @param smlMaxDsp SML_MAX_DSP value
     */
    public void setSmlMaxDsp(int smlMaxDsp) {
        smlMaxDsp__ = smlMaxDsp;
    }

    /**
     * <p>get SML_AUID value
     * @return SML_AUID value
     */
    public int getSmlAuid() {
        return smlAuid__;
    }

    /**
     * <p>set SML_AUID value
     * @param smlAuid SML_AUID value
     */
    public void setSmlAuid(int smlAuid) {
        smlAuid__ = smlAuid;
    }

    /**
     * <p>get SML_ADATE value
     * @return SML_ADATE value
     */
    public UDate getSmlAdate() {
        return smlAdate__;
    }

    /**
     * <p>set SML_ADATE value
     * @param smlAdate SML_ADATE value
     */
    public void setSmlAdate(UDate smlAdate) {
        smlAdate__ = smlAdate;
    }

    /**
     * <p>get SML_EUID value
     * @return SML_EUID value
     */
    public int getSmlEuid() {
        return smlEuid__;
    }

    /**
     * <p>set SML_EUID value
     * @param smlEuid SML_EUID value
     */
    public void setSmlEuid(int smlEuid) {
        smlEuid__ = smlEuid;
    }

    /**
     * <p>get SML_EDATE value
     * @return SML_EDATE value
     */
    public UDate getSmlEdate() {
        return smlEdate__;
    }

    /**
     * <p>set SML_EDATE value
     * @param smlEdate SML_EDATE value
     */
    public void setSmlEdate(UDate smlEdate) {
        smlEdate__ = smlEdate;
    }


    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(smlMaxDsp__);
        buf.append(",");
        buf.append(smlAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smlAdate__, ""));
        buf.append(",");
        buf.append(smlEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smlEdate__, ""));

        return buf.toString();
    }

    /**
     * @return smlMainKbn
     */
    public int getSmlMainKbn() {
        return smlMainKbn__;
    }

    /**
     * @param smlMainKbn セットする smlMainKbn
     */
    public void setSmlMainKbn(int smlMainKbn) {
        smlMainKbn__ = smlMainKbn;
    }

    /**
     * @return smlMainCnt
     */
    public int getSmlMainCnt() {
        return smlMainCnt__;
    }

    /**
     * @param smlMainCnt セットする smlMainCnt
     */
    public void setSmlMainCnt(int smlMainCnt) {
        smlMainCnt__ = smlMainCnt;
    }

    /**
     * @return smlMainSort
     */
    public int getSmlMainSort() {
        return smlMainSort__;
    }

    /**
     * @param smlMainSort セットする smlMainSort
     */
    public void setSmlMainSort(int smlMainSort) {
        smlMainSort__ = smlMainSort;
    }

    /**
     * @return smlPhotoDsp
     */
    public int getSmlPhotoDsp() {
        return smlPhotoDsp__;
    }

    /**
     * @param smlPhotoDsp セットする smlPhotoDsp
     */
    public void setSmlPhotoDsp(int smlPhotoDsp) {
        smlPhotoDsp__ = smlPhotoDsp;
    }

    /**
     * <p>smlTempDsp を取得します。
     * @return smlTempDsp
     */
    public int getSmlTempDsp() {
        return smlTempDsp__;
    }

    /**
     * <p>smlTempDsp をセットします。
     * @param smlTempDsp smlTempDsp
     */
    public void setSmlTempDsp(int smlTempDsp) {
        smlTempDsp__ = smlTempDsp;
    }

}
