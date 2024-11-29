package jp.groupsession.v2.sml.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SMAIL_HINA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlHinaModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SHN_SID mapping */
    private int shnSid__;
    /** SHN_HNAME mapping */
    private String shnHname__;
    /** SHN_TITLE mapping */
    private String shnTitle__;
    /** SHN_MARK mapping */
    private int shnMark__;
    /** SHN_BODY mapping */
    private String shnBody__;
    /** SHN_JKBN mapping */
    private int shnJkbn__;
    /** SHN_CKBN mapping */
    private int shnCkbn__;
    /** SHN_AUID mapping */
    private int shnAuid__;
    /** SHN_ADATE mapping */
    private UDate shnAdate__;
    /** SHN_EUID mapping */
    private int shnEuid__;
    /** SHN_EDATE mapping */
    private UDate shnEdate__;

    /** ひな形名 画面表示用 mapping */
    private String shnHnameDsp__;

    /**
     * <p>Default Constructor
     */
    public SmlHinaModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setSacSid(int usrSid) {
        sacSid__ = usrSid;
    }

    /**
     * <p>get SHN_SID value
     * @return SHN_SID value
     */
    public int getShnSid() {
        return shnSid__;
    }

    /**
     * <p>set SHN_SID value
     * @param shnSid SHN_SID value
     */
    public void setShnSid(int shnSid) {
        shnSid__ = shnSid;
    }

    /**
     * <p>get SHN_HNAME value
     * @return SHN_HNAME value
     */
    public String getShnHname() {
        return shnHname__;
    }

    /**
     * <p>set SHN_HNAME value
     * @param shnHname SHN_HNAME value
     */
    public void setShnHname(String shnHname) {
        shnHname__ = shnHname;
    }

    /**
     * <p>get SHN_TITLE value
     * @return SHN_TITLE value
     */
    public String getShnTitle() {
        return shnTitle__;
    }

    /**
     * <p>set SHN_TITLE value
     * @param shnTitle SHN_TITLE value
     */
    public void setShnTitle(String shnTitle) {
        shnTitle__ = shnTitle;
    }

    /**
     * <p>get SHN_MARK value
     * @return SHN_MARK value
     */
    public int getShnMark() {
        return shnMark__;
    }

    /**
     * <p>set SHN_MARK value
     * @param shnMark SHN_MARK value
     */
    public void setShnMark(int shnMark) {
        shnMark__ = shnMark;
    }

    /**
     * <p>get SHN_BODY value
     * @return SHN_BODY value
     */
    public String getShnBody() {
        return shnBody__;
    }

    /**
     * <p>set SHN_BODY value
     * @param shnBody SHN_BODY value
     */
    public void setShnBody(String shnBody) {
        shnBody__ = shnBody;
    }

    /**
     * <p>get SHN_JKBN value
     * @return SHN_JKBN value
     */
    public int getShnJkbn() {
        return shnJkbn__;
    }

    /**
     * <p>set SHN_JKBN value
     * @param shnJkbn SHN_JKBN value
     */
    public void setShnJkbn(int shnJkbn) {
        shnJkbn__ = shnJkbn;
    }

    /**
     * <p>get SHN_AUID value
     * @return SHN_AUID value
     */
    public int getShnAuid() {
        return shnAuid__;
    }

    /**
     * <p>set SHN_AUID value
     * @param shnAuid SHN_AUID value
     */
    public void setShnAuid(int shnAuid) {
        shnAuid__ = shnAuid;
    }

    /**
     * <p>get SHN_ADATE value
     * @return SHN_ADATE value
     */
    public UDate getShnAdate() {
        return shnAdate__;
    }

    /**
     * <p>set SHN_ADATE value
     * @param shnAdate SHN_ADATE value
     */
    public void setShnAdate(UDate shnAdate) {
        shnAdate__ = shnAdate;
    }

    /**
     * <p>get SHN_EUID value
     * @return SHN_EUID value
     */
    public int getShnEuid() {
        return shnEuid__;
    }

    /**
     * <p>set SHN_EUID value
     * @param shnEuid SHN_EUID value
     */
    public void setShnEuid(int shnEuid) {
        shnEuid__ = shnEuid;
    }

    /**
     * <p>get SHN_EDATE value
     * @return SHN_EDATE value
     */
    public UDate getShnEdate() {
        return shnEdate__;
    }

    /**
     * <p>set SHN_EDATE value
     * @param shnEdate SHN_EDATE value
     */
    public void setShnEdate(UDate shnEdate) {
        shnEdate__ = shnEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(shnSid__);
        buf.append(",");
        buf.append(NullDefault.getString(shnHname__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(shnTitle__, ""));
        buf.append(",");
        buf.append(shnMark__);
        buf.append(",");
        buf.append(NullDefault.getString(shnBody__, ""));
        buf.append(",");
        buf.append(shnJkbn__);
        buf.append(",");
        buf.append(shnAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(shnAdate__, ""));
        buf.append(",");
        buf.append(shnEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(shnEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>shnCkbn を取得します。
     * @return shnCkbn
     */
    public int getShnCkbn() {
        return shnCkbn__;
    }

    /**
     * <p>shnCkbn をセットします。
     * @param shnCkbn shnCkbn
     */
    public void setShnCkbn(int shnCkbn) {
        shnCkbn__ = shnCkbn;
    }

    /**
     * <p>shnHnameDsp を取得します。
     * @return shnHnameDsp
     */
    public String getShnHnameDsp() {
        return shnHnameDsp__;
    }

    /**
     * <p>shnHnameDsp をセットします。
     * @param shnHnameDsp shnHnameDsp
     */
    public void setShnHnameDsp(String shnHnameDsp) {
        shnHnameDsp__ = shnHnameDsp;
    }

}