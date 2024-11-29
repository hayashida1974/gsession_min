package jp.groupsession.v2.sml.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;


/**
 * <p>SML_LOG_COUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlLogCountModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SLC_KBN mapping */
    private int slcKbn__;
    /** SLC_SYS_KBN mapping */
    private int slcSysKbn__;
    /** SLC_CNT_TO mapping */
    private int slcCntTo__;
    /** SLC_CNT_CC mapping */
    private int slcCntCc__;
    /** SLC_CNT_BCC mapping */
    private int slcCntBcc__;
    /** SLC_DATE mapping */
    private UDate slcDate__;

    /**
     * <p>Default Constructor
     */
    public SmlLogCountModel() {
    }

    /**
     * <p>get SAC_SID value
     * @return SAC_SID value
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>set SAC_SID value
     * @param sacSid SAC_SID value
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * <p>get SLC_KBN value
     * @return SLC_KBN value
     */
    public int getSlcKbn() {
        return slcKbn__;
    }

    /**
     * <p>set SLC_KBN value
     * @param slcKbn SLC_KBN value
     */
    public void setSlcKbn(int slcKbn) {
        slcKbn__ = slcKbn;
    }

    /**
     * <p>slcSysKbn を取得します。
     * @return slcSysKbn
     */
    public int getSlcSysKbn() {
        return slcSysKbn__;
    }

    /**
     * <p>slcSysKbn をセットします。
     * @param slcSysKbn slcSysKbn
     */
    public void setSlcSysKbn(int slcSysKbn) {
        slcSysKbn__ = slcSysKbn;
    }

    /**
     * <p>get SLC_CNT_TO value
     * @return SLC_CNT_TO value
     */
    public int getSlcCntTo() {
        return slcCntTo__;
    }

    /**
     * <p>set SLC_CNT_TO value
     * @param slcCntTo SLC_CNT_TO value
     */
    public void setSlcCntTo(int slcCntTo) {
        slcCntTo__ = slcCntTo;
    }

    /**
     * <p>get SLC_CNT_CC value
     * @return SLC_CNT_CC value
     */
    public int getSlcCntCc() {
        return slcCntCc__;
    }

    /**
     * <p>set SLC_CNT_CC value
     * @param slcCntCc SLC_CNT_CC value
     */
    public void setSlcCntCc(int slcCntCc) {
        slcCntCc__ = slcCntCc;
    }

    /**
     * <p>get SLC_CNT_BCC value
     * @return SLC_CNT_BCC value
     */
    public int getSlcCntBcc() {
        return slcCntBcc__;
    }

    /**
     * <p>set SLC_CNT_BCC value
     * @param slcCntBcc SLC_CNT_BCC value
     */
    public void setSlcCntBcc(int slcCntBcc) {
        slcCntBcc__ = slcCntBcc;
    }

    /**
     * <p>get SLC_DATE value
     * @return SLC_DATE value
     */
    public UDate getSlcDate() {
        return slcDate__;
    }

    /**
     * <p>set SLC_DATE value
     * @param slcDate SLC_DATE value
     */
    public void setSlcDate(UDate slcDate) {
        slcDate__ = slcDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(slcKbn__);
        buf.append(",");
        buf.append(slcSysKbn__);
        buf.append(",");
        buf.append(slcCntTo__);
        buf.append(",");
        buf.append(slcCntCc__);
        buf.append(",");
        buf.append(slcCntBcc__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(slcDate__, ""));
        return buf.toString();
    }



}
