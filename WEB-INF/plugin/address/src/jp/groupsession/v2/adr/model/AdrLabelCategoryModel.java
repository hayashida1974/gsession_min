package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_LABEL_CATEGORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrLabelCategoryModel implements Serializable {

    /** ALC_SID mapping */
    private int alcSid__;
    /** ALC_NAME mapping */
    private String alcName__;
    /** ALC_BIKO mapping */
    private String alcBiko__;
    /** ALC_SORT mapping */
    private int alcSort__;
    /** ALC_AUID mapping */
    private int alcAuid__;
    /** ALC_ADATE mapping */
    private UDate alcAdate__;
    /** ALC_EUID mapping */
    private int alcEuid__;
    /** ALC_EDATE mapping */
    private UDate alcEdate__;
    /** 表示順(画面用) */
    private String alcValue__;


    /**
     * <p>Default Constructor
     */
    public AdrLabelCategoryModel() {
    }

    /**
     * <p>get ALC_SID value
     * @return ALC_SID value
     */
    public int getAlcSid() {
        return alcSid__;
    }

    /**
     * <p>set ALC_SID value
     * @param alcSid ALC_SID value
     */
    public void setAlcSid(int alcSid) {
        alcSid__ = alcSid;
    }

    /**
     * <p>get ALC_NAME value
     * @return ALC_NAME value
     */
    public String getAlcName() {
        return alcName__;
    }

    /**
     * <p>set ALC_NAME value
     * @param alcName ALC_NAME value
     */
    public void setAlcName(String alcName) {
        alcName__ = alcName;
    }

    /**
     * <p>get ALC_BIKO value
     * @return ALC_BIKO value
     */
    public String getAlcBiko() {
        return alcBiko__;
    }

    /**
     * <p>set ALC_BIKO value
     * @param alcBiko ALC_BIKO value
     */
    public void setAlcBiko(String alcBiko) {
        alcBiko__ = alcBiko;
    }

    /**
     * <p>get ALC_SORT value
     * @return ALC_SORT value
     */
    public int getAlcSort() {
        return alcSort__;
    }

    /**
     * <p>set ALC_SORT value
     * @param alcSort ALC_SORT value
     */
    public void setAlcSort(int alcSort) {
        alcSort__ = alcSort;
    }

    /**
     * <p>get ALC_AUID value
     * @return ALC_AUID value
     */
    public int getAlcAuid() {
        return alcAuid__;
    }

    /**
     * <p>set ALC_AUID value
     * @param alcAuid ALC_AUID value
     */
    public void setAlcAuid(int alcAuid) {
        alcAuid__ = alcAuid;
    }

    /**
     * <p>get ALC_ADATE value
     * @return ALC_ADATE value
     */
    public UDate getAlcAdate() {
        return alcAdate__;
    }

    /**
     * <p>set ALC_ADATE value
     * @param alcAdate ALC_ADATE value
     */
    public void setAlcAdate(UDate alcAdate) {
        alcAdate__ = alcAdate;
    }

    /**
     * <p>get ALC_EUID value
     * @return ALC_EUID value
     */
    public int getAlcEuid() {
        return alcEuid__;
    }

    /**
     * <p>set ALC_EUID value
     * @param alcEuid ALC_EUID value
     */
    public void setAlcEuid(int alcEuid) {
        alcEuid__ = alcEuid;
    }

    /**
     * <p>get ALC_EDATE value
     * @return ALC_EDATE value
     */
    public UDate getAlcEdate() {
        return alcEdate__;
    }

    /**
     * <p>set ALC_EDATE value
     * @param alcEdate ALC_EDATE value
     */
    public void setAlcEdate(UDate alcEdate) {
        alcEdate__ = alcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(alcSid__);
        buf.append(",");
        buf.append(NullDefault.getString(alcName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(alcBiko__, ""));
        buf.append(",");
        buf.append(alcSort__);
        buf.append(",");
        buf.append(alcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(alcAdate__, ""));
        buf.append(",");
        buf.append(alcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(alcEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>alcValue を取得します。
     * @return alcValue
     */
    public String getAlcValue() {
        return alcValue__;
    }

    /**
     * <p>alcValue をセットします。
     * @param alcValue alcValue
     */
    public void setAlcValue(String alcValue) {
        alcValue__ = alcValue;
    }

}
