package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>USR_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrmLabelModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** LAB_SID mapping */
    private int labSid__;
    /** USL_AUID mapping */
    private int uslAuid__;
    /** USL_ADATE mapping */
    private UDate uslAdate__;
    /** USL_EUID mapping */
    private int uslEuid__;
    /** USL_EDATE mapping */
    private UDate uslEdate__;

    /** 該当ラベルのユーザ情報付加件数*/
    private int count__ = 0;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>labSid を取得します。
     * @return labSid
     */
    public int getLabSid() {
        return labSid__;
    }

    /**
     * <p>labSid をセットします。
     * @param labSid labSid
     */
    public void setLabSid(int labSid) {
        labSid__ = labSid;
    }

    /**
     * <p>uslAuid を取得します。
     * @return uslAuid
     */
    public int getUslAuid() {
        return uslAuid__;
    }

    /**
     * <p>uslAuid をセットします。
     * @param uslAuid uslAuid
     */
    public void setUslAuid(int uslAuid) {
        uslAuid__ = uslAuid;
    }

    /**
     * <p>uslAdate を取得します。
     * @return uslAdate
     */
    public UDate getUslAdate() {
        return uslAdate__;
    }

    /**
     * <p>uslAdate をセットします。
     * @param uslAdate uslAdate
     */
    public void setUslAdate(UDate uslAdate) {
        uslAdate__ = uslAdate;
    }

    /**
     * <p>uslEuid を取得します。
     * @return uslEuid
     */
    public int getUslEuid() {
        return uslEuid__;
    }

    /**
     * <p>uslEuid をセットします。
     * @param uslEuid uslEuid
     */
    public void setUslEuid(int uslEuid) {
        uslEuid__ = uslEuid;
    }

    /**
     * <p>uslEdate を取得します。
     * @return uslEdate
     */
    public UDate getUslEdate() {
        return uslEdate__;
    }

    /**
     * <p>uslEdate をセットします。
     * @param uslEdate uslEdate
     */
    public void setUslEdate(UDate uslEdate) {
        uslEdate__ = uslEdate;
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
