package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_USRM_LABEL_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLabelUsrConfModel implements Serializable {

    /** LUF_EDIT mapping */
    private int lufEdit__ = 0;
    /** LUF_SET mapping */
    private int lufSet__ = 0;
    /** LUF_AUID mapping */
    private int lufAuid__ = 0;
    /** LUF_ADATE mapping */
    private UDate lufAdate__;
    /** LUF_EUID mapping */
    private int lufEuid__ = 0;
    /** LUF_EDATE mapping */
    private UDate lufEdate__;
    /**
     * <p>lufEdit を取得します。
     * @return lufEdit
     */
    public int getLufEdit() {
        return lufEdit__;
    }
    /**
     * <p>lufEdit をセットします。
     * @param lufEdit lufEdit
     */
    public void setLufEdit(int lufEdit) {
        lufEdit__ = lufEdit;
    }
    /**
     * <p>lufSet を取得します。
     * @return lufSet
     */
    public int getLufSet() {
        return lufSet__;
    }
    /**
     * <p>lufSet をセットします。
     * @param lufSet lufSet
     */
    public void setLufSet(int lufSet) {
        lufSet__ = lufSet;
    }
    /**
     * <p>lufAuid を取得します。
     * @return lufAuid
     */
    public int getLufAuid() {
        return lufAuid__;
    }
    /**
     * <p>lufAuid をセットします。
     * @param lufAuid lufAuid
     */
    public void setLufAuid(int lufAuid) {
        lufAuid__ = lufAuid;
    }
    /**
     * <p>lufAdate を取得します。
     * @return lufAdate
     */
    public UDate getLufAdate() {
        return lufAdate__;
    }
    /**
     * <p>lufAdate をセットします。
     * @param lufAdate lufAdate
     */
    public void setLufAdate(UDate lufAdate) {
        lufAdate__ = lufAdate;
    }
    /**
     * <p>lufEuid を取得します。
     * @return lufEuid
     */
    public int getLufEuid() {
        return lufEuid__;
    }
    /**
     * <p>lufEuid をセットします。
     * @param lufEuid lufEuid
     */
    public void setLufEuid(int lufEuid) {
        lufEuid__ = lufEuid;
    }
    /**
     * <p>lufEdate を取得します。
     * @return lufEdate
     */
    public UDate getLufEdate() {
        return lufEdate__;
    }
    /**
     * <p>lufEdate をセットします。
     * @param lufEdate lufEdate
     */
    public void setLufEdate(UDate lufEdate) {
        lufEdate__ = lufEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(lufEdit__);
        buf.append(",");
        buf.append(lufSet__);
        buf.append(",");
        buf.append(lufAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(lufAdate__, ""));
        buf.append(",");
        buf.append(lufEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(lufEdate__, ""));
        return buf.toString();
    }

}
