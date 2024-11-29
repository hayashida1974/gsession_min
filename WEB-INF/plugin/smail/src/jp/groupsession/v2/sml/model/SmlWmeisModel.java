package jp.groupsession.v2.sml.model;


import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>SML_WMEIS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlWmeisModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SMW_SID mapping */
    private int smwSid__;
    /** SMW_TITLE mapping */
    private String smwTitle__;
    /** SMW_MARK mapping */
    private int smwMark__;
    /** SMW_BODY mapping */
    private String smwBody__;
    /** SMW_BODY_PLAIN mapping */
    private String smwBodyPlain__;
    /** SMW_SIZE mapping */
    private Long smwSize__;
    /** SMW_TYPE mapping */
    private int smwType__;
    /** SMW_JKBN mapping */
    private int smwJkbn__;
    /** SMW_AUID mapping */
    private int smwAuid__;
    /** SMW_ADATE mapping */
    private UDate smwAdate__;
    /** SMW_EUID mapping */
    private int smwEuid__;
    /** SMW_EDATE mapping */
    private UDate smwEdate__;
    /** SMW_ORIGIN mapping */
    private int smwOrigin__;
    /** SMW_EDIT_KBN mapping */
    private int smwEditKbn__;

    /**
     * <p>Default Constructor
     */
    public SmlWmeisModel() {
    }

    /**
     * <p>get SMW_SID value
     * @return SMW_SID value
     */
    public int getSmwSid() {
        return smwSid__;
    }

    /**
     * <p>set SMW_SID value
     * @param smwSid SMW_SID value
     */
    public void setSmwSid(int smwSid) {
        smwSid__ = smwSid;
    }

    /**
     * <p>get SMW_TITLE value
     * @return SMW_TITLE value
     */
    public String getSmwTitle() {
        return smwTitle__;
    }

    /**
     * <p>set SMW_TITLE value
     * @param smwTitle SMW_TITLE value
     */
    public void setSmwTitle(String smwTitle) {
        smwTitle__ = smwTitle;
    }

    /**
     * <p>get SMW_MARK value
     * @return SMW_MARK value
     */
    public int getSmwMark() {
        return smwMark__;
    }

    /**
     * <p>set SMW_MARK value
     * @param smwMark SMW_MARK value
     */
    public void setSmwMark(int smwMark) {
        smwMark__ = smwMark;
    }

    /**
     * <p>get SMW_BODY value
     * @return SMW_BODY value
     */
    public String getSmwBody() {
        return smwBody__;
    }

    /**
     * <p>set SMW_BODY value
     * @param smwBody SMW_BODY value
     */
    public void setSmwBody(String smwBody) {
        smwBody__ = smwBody;
    }

    /**
     * <p>get SMW_JKBN value
     * @return SMW_JKBN value
     */
    public int getSmwJkbn() {
        return smwJkbn__;
    }

    /**
     * <p>set SMW_JKBN value
     * @param smwJkbn SMW_JKBN value
     */
    public void setSmwJkbn(int smwJkbn) {
        smwJkbn__ = smwJkbn;
    }

    /**
     * <p>get SMW_AUID value
     * @return SMW_AUID value
     */
    public int getSmwAuid() {
        return smwAuid__;
    }

    /**
     * <p>set SMW_AUID value
     * @param smwAuid SMW_AUID value
     */
    public void setSmwAuid(int smwAuid) {
        smwAuid__ = smwAuid;
    }

    /**
     * <p>get SMW_ADATE value
     * @return SMW_ADATE value
     */
    public UDate getSmwAdate() {
        return smwAdate__;
    }

    /**
     * <p>set SMW_ADATE value
     * @param smwAdate SMW_ADATE value
     */
    public void setSmwAdate(UDate smwAdate) {
        smwAdate__ = smwAdate;
    }

    /**
     * <p>get SMW_EUID value
     * @return SMW_EUID value
     */
    public int getSmwEuid() {
        return smwEuid__;
    }

    /**
     * <p>set SMW_EUID value
     * @param smwEuid SMW_EUID value
     */
    public void setSmwEuid(int smwEuid) {
        smwEuid__ = smwEuid;
    }

    /**
     * <p>get SMW_EDATE value
     * @return SMW_EDATE value
     */
    public UDate getSmwEdate() {
        return smwEdate__;
    }

    /**
     * <p>set SMW_EDATE value
     * @param smwEdate SMW_EDATE value
     */
    public void setSmwEdate(UDate smwEdate) {
        smwEdate__ = smwEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(smwSid__);
        buf.append(",");
        buf.append(NullDefault.getString(smwTitle__, ""));
        buf.append(",");
        buf.append(smwMark__);
        buf.append(",");
        buf.append(NullDefault.getString(smwBody__, ""));
        buf.append(",");
        buf.append(smwJkbn__);
        buf.append(",");
        buf.append(smwAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smwAdate__, ""));
        buf.append(",");
        buf.append(smwEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smwEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>smwSize を取得します。
     * @return smwSize
     */
    public Long getSmwSize() {
        return smwSize__;
    }

    /**
     * <p>smwSize をセットします。
     * @param smwSize smwSize
     */
    public void setSmwSize(Long smwSize) {
        smwSize__ = smwSize;
    }

    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>sacSid をセットします。
     * @param sacSid sacSid
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * <p>smwType を取得します。
     * @return smwType
     */
    public int getSmwType() {
        return smwType__;
    }

    /**
     * <p>smwType をセットします。
     * @param smwType smwType
     */
    public void setSmwType(int smwType) {
        smwType__ = smwType;
    }

    /**
     * <p>smwBodyPlain を取得します。
     * @return smwBodyPlain
     */
    public String getSmwBodyPlain() {
        return smwBodyPlain__;
    }

    /**
     * <p>smwBodyPlain をセットします。
     * @param smwBodyPlain smwBodyPlain
     */
    public void setSmwBodyPlain(String smwBodyPlain) {
        smwBodyPlain__ = smwBodyPlain;
    }

    /**
     * <p>get SMW_ORIGIN value
     * @return SMW_ORIGIN value
     */
    public int getSmwOrigin() {
        return smwOrigin__;
    }

    /**
     * <p>set SMW_ORIGIN value
     * @param smwOrigin SMW_ORIGIN value
     */
    public void setSmwOrigin(int smwOrigin) {
        smwOrigin__ = smwOrigin;
    }

    /**
     * <p>get SMW_EDIT_KBN value
     * @return SMW_EDIT_KBN value
     */
    public int getSmwEditKbn() {
        return smwEditKbn__;
    }

    /**
     * <p>set SMW_EDIT_KBN value
     * @param smwEditKbn SMW_EDIT_KBN value
     */
    public void setSmwEditKbn(int smwEditKbn) {
        smwEditKbn__ = smwEditKbn;
    }
}
