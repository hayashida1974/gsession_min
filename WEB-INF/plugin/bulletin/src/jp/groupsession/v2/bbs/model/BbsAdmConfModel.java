package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BBS_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsAdmConfModel implements Serializable {

    /** BAC_ATDEL_FLG mapping */
    private int bacAtdelFlg__;
    /** BAC_ATDEL_Y mapping */
    private int bacAtdelY__;
    /** BAC_ATDEL_M mapping */
    private int bacAtdelM__;
    /** BAC_AUID mapping */
    private int bacAuid__;
    /** BAC_ADATE mapping */
    private UDate bacAdate__;
    /** BAC_EUID mapping */
    private int bacEuid__;
    /** BAC_EDATE mapping */
    private UDate bacEdate__;
    /** BAC_SML_NTF mapping */
    private int bacSmlNtf__;
    /** BAC_SML_NTF_KBN mapping */
    private int bacSmlNtfKbn__;
    /** BAC_INI_POST_TYPE mapping */
    private int bacIniPostType__;
    /** BAC_INI_POST_TYPE_KBN */
    private int bacIniPostTypeKbn__;

    /**
     * <p>Default Constructor
     */
    public BbsAdmConfModel() {
    }

    /**
     * <p>get BAC_ATDEL_FLG value
     * @return BAC_ATDEL_FLG value
     */
    public int getBacAtdelFlg() {
        return bacAtdelFlg__;
    }

    /**
     * <p>set BAC_ATDEL_FLG value
     * @param bacAtdelFlg BAC_ATDEL_FLG value
     */
    public void setBacAtdelFlg(int bacAtdelFlg) {
        bacAtdelFlg__ = bacAtdelFlg;
    }

    /**
     * <p>get BAC_ATDEL_Y value
     * @return BAC_ATDEL_Y value
     */
    public int getBacAtdelY() {
        return bacAtdelY__;
    }

    /**
     * <p>set BAC_ATDEL_Y value
     * @param bacAtdelY BAC_ATDEL_Y value
     */
    public void setBacAtdelY(int bacAtdelY) {
        bacAtdelY__ = bacAtdelY;
    }

    /**
     * <p>get BAC_ATDEL_M value
     * @return BAC_ATDEL_M value
     */
    public int getBacAtdelM() {
        return bacAtdelM__;
    }

    /**
     * <p>set BAC_ATDEL_M value
     * @param bacAtdelM BAC_ATDEL_M value
     */
    public void setBacAtdelM(int bacAtdelM) {
        bacAtdelM__ = bacAtdelM;
    }

    /**
     * <p>get BAC_AUID value
     * @return BAC_AUID value
     */
    public int getBacAuid() {
        return bacAuid__;
    }

    /**
     * <p>set BAC_AUID value
     * @param bacAuid BAC_AUID value
     */
    public void setBacAuid(int bacAuid) {
        bacAuid__ = bacAuid;
    }

    /**
     * <p>get BAC_ADATE value
     * @return BAC_ADATE value
     */
    public UDate getBacAdate() {
        return bacAdate__;
    }

    /**
     * <p>set BAC_ADATE value
     * @param bacAdate BAC_ADATE value
     */
    public void setBacAdate(UDate bacAdate) {
        bacAdate__ = bacAdate;
    }

    /**
     * <p>get BAC_EUID value
     * @return BAC_EUID value
     */
    public int getBacEuid() {
        return bacEuid__;
    }

    /**
     * <p>set BAC_EUID value
     * @param bacEuid BAC_EUID value
     */
    public void setBacEuid(int bacEuid) {
        bacEuid__ = bacEuid;
    }

    /**
     * <p>get BAC_EDATE value
     * @return BAC_EDATE value
     */
    public UDate getBacEdate() {
        return bacEdate__;
    }

    /**
     * <p>set BAC_EDATE value
     * @param bacEdate BAC_EDATE value
     */
    public void setBacEdate(UDate bacEdate) {
        bacEdate__ = bacEdate;
    }

    /**
     * <p>get BAC_SML_NTF value
     * @return BAC_SML_NTF value
     */
    public int getBacSmlNtf() {
        return bacSmlNtf__;
    }

    /**
     * <p>set BAC_SML_NTF value
     * @param bacSmlNtf BAC_SML_NTF value
     */
    public void setBacSmlNtf(int bacSmlNtf) {
        bacSmlNtf__ = bacSmlNtf;
    }

    /**
     * <p>get BAC_SML_NTF_KBN value
     * @return BAC_SML_NTF_KBN value
     */
    public int getBacSmlNtfKbn() {
        return bacSmlNtfKbn__;
    }

    /**
     * <p>set BAC_SML_NTF_KBN value
     * @param bacSmlNtfKbn BAC_SML_NTF_KBN value
     */
    public void setBacSmlNtfKbn(int bacSmlNtfKbn) {
        bacSmlNtfKbn__ = bacSmlNtfKbn;
    }

    /**
     * <p>bacIniPostType を取得します。
     * @return bacIniPostType
     */
    public int getBacIniPostType() {
        return bacIniPostType__;
    }

    /**
     * <p>bacIniPostType をセットします。
     * @param bacIniPostType bacIniPostType
     */
    public void setBacIniPostType(int bacIniPostType) {
        bacIniPostType__ = bacIniPostType;
    }

    /**
     * <p>bacIniPostTypeKbn を取得します。
     * @return bacIniPostTypeKbn
     */
    public int getBacIniPostTypeKbn() {
        return bacIniPostTypeKbn__;
    }

    /**
     * <p>bacIniPostTypeKbn をセットします。
     * @param bacIniPostTypeKbn bacIniPostTypeKbn
     */
    public void setBacIniPostTypeKbn(int bacIniPostTypeKbn) {
        bacIniPostTypeKbn__ = bacIniPostTypeKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(bacAtdelFlg__);
        buf.append(",");
        buf.append(bacAtdelY__);
        buf.append(",");
        buf.append(bacAtdelM__);
        buf.append(",");
        buf.append(bacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bacAdate__, ""));
        buf.append(",");
        buf.append(bacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bacEdate__, ""));
        buf.append(",");
        buf.append(bacSmlNtf__);
        buf.append(",");
        buf.append(bacSmlNtfKbn__);
        return buf.toString();
    }

}
