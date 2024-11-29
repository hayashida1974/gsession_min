package jp.groupsession.v2.adr.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ADR_BELONG_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrBelongLabelModel implements Serializable {

    /** ADR_SID mapping */
    private int adrSid__;
    /** ALB_SID mapping */
    private int albSid__;
    /** ABL_AUID mapping */
    private int ablAuid__;
    /** ABL_ADATE mapping */
    private UDate ablAdate__;
    /** ABL_EUID mapping */
    private int ablEuid__;
    /** ABL_EDATE mapping */
    private UDate ablEdate__;

    /** 該当ラベルのユーザ情報付加件数*/
    private int count__ = 0;


    /**
     * <p>Default Constructor
     */
    public AdrBelongLabelModel() {
    }

    /**
     * <p>get ADR_SID value
     * @return ADR_SID value
     */
    public int getAdrSid() {
        return adrSid__;
    }

    /**
     * <p>set ADR_SID value
     * @param adrSid ADR_SID value
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
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
     * <p>get ABL_AUID value
     * @return ABL_AUID value
     */
    public int getAblAuid() {
        return ablAuid__;
    }

    /**
     * <p>set ABL_AUID value
     * @param ablAuid ABL_AUID value
     */
    public void setAblAuid(int ablAuid) {
        ablAuid__ = ablAuid;
    }

    /**
     * <p>get ABL_ADATE value
     * @return ABL_ADATE value
     */
    public UDate getAblAdate() {
        return ablAdate__;
    }

    /**
     * <p>set ABL_ADATE value
     * @param ablAdate ABL_ADATE value
     */
    public void setAblAdate(UDate ablAdate) {
        ablAdate__ = ablAdate;
    }

    /**
     * <p>get ABL_EUID value
     * @return ABL_EUID value
     */
    public int getAblEuid() {
        return ablEuid__;
    }

    /**
     * <p>set ABL_EUID value
     * @param ablEuid ABL_EUID value
     */
    public void setAblEuid(int ablEuid) {
        ablEuid__ = ablEuid;
    }

    /**
     * <p>get ABL_EDATE value
     * @return ABL_EDATE value
     */
    public UDate getAblEdate() {
        return ablEdate__;
    }

    /**
     * <p>set ABL_EDATE value
     * @param ablEdate ABL_EDATE value
     */
    public void setAblEdate(UDate ablEdate) {
        ablEdate__ = ablEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(adrSid__);
        buf.append(",");
        buf.append(albSid__);
        buf.append(",");
        buf.append(ablAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ablAdate__, ""));
        buf.append(",");
        buf.append(ablEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ablEdate__, ""));
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

}
