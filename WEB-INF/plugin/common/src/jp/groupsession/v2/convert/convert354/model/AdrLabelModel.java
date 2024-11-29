package jp.groupsession.v2.convert.convert354.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrLabelModel implements Serializable {

    /** ALB_SID mapping */
    private int albSid__;
    /** ALB_NAME mapping */
    private String albName__;
    /** ALB_BIKO mapping */
    private String albBiko__;
    /** ALB_SORT mapping */
    private int albSort__;
    /** ALB_AUID mapping */
    private int albAuid__;
    /** ALB_ADATE mapping */
    private UDate albAdate__;
    /** ALB_EUID mapping */
    private int albEuid__;
    /** ALB_EDATE mapping */
    private UDate albEdate__;
    /** ALC_SID mapping */
    private int alcSid__;
    /** カウント */
    private int count__;


    /**
     * <p>Default Constructor
     */
    public AdrLabelModel() {
    }

    /**
     * <p>get ALB_SID value
     * @return ALB_SID value
     */
    public int getAlbSid() {
        return albSid__;
    }

    /**
     * <p>set ALB_SID value
     * @param albSid ALB_SID value
     */
    public void setAlbSid(int albSid) {
        albSid__ = albSid;
    }

    /**
     * <p>get ALB_NAME value
     * @return ALB_NAME value
     */
    public String getAlbName() {
        return albName__;
    }

    /**
     * <p>set ALB_NAME value
     * @param albName ALB_NAME value
     */
    public void setAlbName(String albName) {
        albName__ = albName;
    }

    /**
     * <p>get ALB_BIKO value
     * @return ALB_BIKO value
     */
    public String getAlbBiko() {
        return albBiko__;
    }

    /**
     * <p>set ALB_BIKO value
     * @param albBiko ALB_BIKO value
     */
    public void setAlbBiko(String albBiko) {
        albBiko__ = albBiko;
    }

    /**
     * <p>get ALB_SORT value
     * @return ALB_SORT value
     */
    public int getAlbSort() {
        return albSort__;
    }

    /**
     * <p>set ALB_SORT value
     * @param albSort ALB_SORT value
     */
    public void setAlbSort(int albSort) {
        albSort__ = albSort;
    }

    /**
     * <p>get ALB_AUID value
     * @return ALB_AUID value
     */
    public int getAlbAuid() {
        return albAuid__;
    }

    /**
     * <p>set ALB_AUID value
     * @param albAuid ALB_AUID value
     */
    public void setAlbAuid(int albAuid) {
        albAuid__ = albAuid;
    }

    /**
     * <p>get ALB_ADATE value
     * @return ALB_ADATE value
     */
    public UDate getAlbAdate() {
        return albAdate__;
    }

    /**
     * <p>set ALB_ADATE value
     * @param albAdate ALB_ADATE value
     */
    public void setAlbAdate(UDate albAdate) {
        albAdate__ = albAdate;
    }

    /**
     * <p>get ALB_EUID value
     * @return ALB_EUID value
     */
    public int getAlbEuid() {
        return albEuid__;
    }

    /**
     * <p>set ALB_EUID value
     * @param albEuid ALB_EUID value
     */
    public void setAlbEuid(int albEuid) {
        albEuid__ = albEuid;
    }

    /**
     * <p>get ALB_EDATE value
     * @return ALB_EDATE value
     */
    public UDate getAlbEdate() {
        return albEdate__;
    }

    /**
     * <p>set ALB_EDATE value
     * @param albEdate ALB_EDATE value
     */
    public void setAlbEdate(UDate albEdate) {
        albEdate__ = albEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(albSid__);
        buf.append(",");
        buf.append(NullDefault.getString(albName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(albBiko__, ""));
        buf.append(",");
        buf.append(albSort__);
        buf.append(",");
        buf.append(albAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(albAdate__, ""));
        buf.append(",");
        buf.append(albEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(albEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>count を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }

    /**
     * <p>count をセットします。
     * @param count count
     */
    public void setCount(int count) {
        count__ = count;
    }

    /**
     * <p>alcSid を取得します。
     * @return alcSid
     */
    public int getAlcSid() {
        return alcSid__;
    }

    /**
     * <p>alcSid をセットします。
     * @param alcSid alcSid
     */
    public void setAlcSid(int alcSid) {
        alcSid__ = alcSid;
    }



}
