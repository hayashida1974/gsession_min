package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BBS_WRITE_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsWriteInfModel implements Serializable {

    /** BWI_SID mapping */
    private int bwiSid__;
    /** BFI_SID mapping */
    private int bfiSid__;
    /** BTI_SID mapping */
    private int btiSid__;
    /** BWI_VALUE mapping */
    private String bwiValue__;
    /** BWI_VALUE_PLAIN mapping */
    private String bwiValuePlain__;
    /** BWI_TYPE mapping */
    private int bwiType__;
    /** BWI_AUID mapping */
    private int bwiAuid__;
    /** BWI_ADATE mapping */
    private UDate bwiAdate__;
    /** BWI_EUID mapping */
    private int bwiEuid__;
    /** BWI_EDATE mapping */
    private UDate bwiEdate__;
    /** BWI_AGID mapping */
    private int bwiAgid__;
    /** BWI_EGID mapping */
    private int bwiEgid__;
    /** BWI_PARENT_FLG mapping */
    private int bwiParentFlg__;

    /**
     * <p>Default Constructor
     */
    public BbsWriteInfModel() {
    }

    /**
     * <p>get BWI_SID value
     * @return BWI_SID value
     */
    public int getBwiSid() {
        return bwiSid__;
    }

    /**
     * <p>set BWI_SID value
     * @param bwiSid BWI_SID value
     */
    public void setBwiSid(int bwiSid) {
        bwiSid__ = bwiSid;
    }

    /**
     * <p>get BFI_SID value
     * @return BFI_SID value
     */
    public int getBfiSid() {
        return bfiSid__;
    }

    /**
     * <p>set BFI_SID value
     * @param bfiSid BFI_SID value
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }

    /**
     * <p>get BTI_SID value
     * @return BTI_SID value
     */
    public int getBtiSid() {
        return btiSid__;
    }

    /**
     * <p>set BTI_SID value
     * @param btiSid BTI_SID value
     */
    public void setBtiSid(int btiSid) {
        btiSid__ = btiSid;
    }

    /**
     * <p>get BWI_VALUE value
     * @return BWI_VALUE value
     */
    public String getBwiValue() {
        return bwiValue__;
    }

    /**
     * <p>set BWI_VALUE value
     * @param bwiValue BWI_VALUE value
     */
    public void setBwiValue(String bwiValue) {
        bwiValue__ = bwiValue;
    }

    /**
     * <p>bwiValuePlain を取得します。
     * @return bwiValuePlain
     */
    public String getBwiValuePlain() {
        return bwiValuePlain__;
    }

    /**
     * <p>bwiValuePlain をセットします。
     * @param bwiValuePlain bwiValuePlain
     */
    public void setBwiValuePlain(String bwiValuePlain) {
        bwiValuePlain__ = bwiValuePlain;
    }

    /**
     * <p>bwiType を取得します。
     * @return bwiType
     */
    public int getBwiType() {
        return bwiType__;
    }

    /**
     * <p>bwiType をセットします。
     * @param bwiType bwiType
     */
    public void setBwiType(int bwiType) {
        bwiType__ = bwiType;
    }

    /**
     * <p>get BWI_AUID value
     * @return BWI_AUID value
     */
    public int getBwiAuid() {
        return bwiAuid__;
    }

    /**
     * <p>set BWI_AUID value
     * @param bwiAuid BWI_AUID value
     */
    public void setBwiAuid(int bwiAuid) {
        bwiAuid__ = bwiAuid;
    }

    /**
     * <p>get BWI_ADATE value
     * @return BWI_ADATE value
     */
    public UDate getBwiAdate() {
        return bwiAdate__;
    }

    /**
     * <p>set BWI_ADATE value
     * @param bwiAdate BWI_ADATE value
     */
    public void setBwiAdate(UDate bwiAdate) {
        bwiAdate__ = bwiAdate;
    }

    /**
     * <p>get BWI_EUID value
     * @return BWI_EUID value
     */
    public int getBwiEuid() {
        return bwiEuid__;
    }

    /**
     * <p>set BWI_EUID value
     * @param bwiEuid BWI_EUID value
     */
    public void setBwiEuid(int bwiEuid) {
        bwiEuid__ = bwiEuid;
    }

    /**
     * <p>get BWI_EDATE value
     * @return BWI_EDATE value
     */
    public UDate getBwiEdate() {
        return bwiEdate__;
    }

    /**
     * <p>set BWI_EDATE value
     * @param bwiEdate BWI_EDATE value
     */
    public void setBwiEdate(UDate bwiEdate) {
        bwiEdate__ = bwiEdate;
    }

    /**
     * <p>get BWI_AGID value
     * @return BWI_AGID value
     */
    public int getBwiAgid() {
        return bwiAgid__;
    }

    /**
     * <p>set BWI_AGID value
     * @param bwiAgid BWI_AGID value
     */
    public void setBwiAgid(int bwiAgid) {
        bwiAgid__ = bwiAgid;
    }

    /**
     * <p>get BWI_EGID value
     * @return BWI_EGID value
     */
    public int getBwiEgid() {
        return bwiEgid__;
    }

    /**
     * <p>set BWI_EGID value
     * @param bwiEgid BWI_EGID value
     */
    public void setBwiEgid(int bwiEgid) {
        bwiEgid__ = bwiEgid;
    }

    /**
     * <p>get BWI_PARENT_FLG value
     * @return BWI_PARENT_FLG value
     */
    public int getBwiParentFlg() {
        return bwiParentFlg__;
    }

    /**
     * <p>set BWI_PARENT_FLG value
     * @param bwiParentFlg BWI_PARENT_FLG value
     */
    public void setBwiParentFlg(int bwiParentFlg) {
        bwiParentFlg__ = bwiParentFlg;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(bwiSid__);
        buf.append(",");
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(btiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(bwiValue__, ""));
        buf.append(",");
        buf.append(bwiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bwiAdate__, ""));
        buf.append(",");
        buf.append(bwiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bwiEdate__, ""));
        buf.append(",");
        buf.append(bwiAgid__);
        buf.append(",");
        buf.append(bwiEgid__);
        buf.append(",");
        buf.append(NullDefault.getString(bwiValuePlain__, ""));
        buf.append(",");
        buf.append(bwiType__);
        buf.append(",");
        buf.append(bwiParentFlg__);
        return buf.toString();
    }
}
