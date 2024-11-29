package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import java.io.Serializable;

/**
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnLabelUsrModel implements Serializable {

    /** LAB_SID mapping */
    private int labSid__;
    /** LUC_SID mapping */
    private int lucSid__;
    /** LAB_NAME mapping */
    private String labName__;
    /** LAB_BIKO mapping */
    private String labBiko__;
    /** LAB_AUID mapping */
    private int labAuid__;
    /** LAB_ADATE mapping */
    private UDate labAdate__;
    /** LAB_EUID mapping */
    private int labEuid__;
    /** LAB_EDATE mapping */
    private UDate labEdate__;
    /** LAB_SORT mapping */
    private int labSort__;
    /** 表示順(画面用) */
    private String lauValue__;
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
     * <p>lucSid を取得します。
     * @return lucSid
     */
    public int getLucSid() {
        return lucSid__;
    }
    /**
     * <p>lucSid をセットします。
     * @param lucSid lucSid
     */
    public void setLucSid(int lucSid) {
        lucSid__ = lucSid;
    }
    /**
     * <p>labName を取得します。
     * @return labName
     */
    public String getLabName() {
        return labName__;
    }
    /**
     * <p>labName をセットします。
     * @param labName labName
     */
    public void setLabName(String labName) {
        labName__ = labName;
    }
    /**
     * <p>labBiko を取得します。
     * @return labBiko
     */
    public String getLabBiko() {
        return labBiko__;
    }
    /**
     * <p>labBiko をセットします。
     * @param labBiko labBiko
     */
    public void setLabBiko(String labBiko) {
        labBiko__ = labBiko;
    }
    /**
     * <p>labAuid を取得します。
     * @return labAuid
     */
    public int getLabAuid() {
        return labAuid__;
    }
    /**
     * <p>labAuid をセットします。
     * @param labAuid labAuid
     */
    public void setLabAuid(int labAuid) {
        labAuid__ = labAuid;
    }
    /**
     * <p>labAdate を取得します。
     * @return labAdate
     */
    public UDate getLabAdate() {
        return labAdate__;
    }
    /**
     * <p>labAdate をセットします。
     * @param labAdate labAdate
     */
    public void setLabAdate(UDate labAdate) {
        labAdate__ = labAdate;
    }
    /**
     * <p>labEuid を取得します。
     * @return labEuid
     */
    public int getLabEuid() {
        return labEuid__;
    }
    /**
     * <p>labEuid をセットします。
     * @param labEuid labEuid
     */
    public void setLabEuid(int labEuid) {
        labEuid__ = labEuid;
    }
    /**
     * <p>labEdate を取得します。
     * @return labEdate
     */
    public UDate getLabEdate() {
        return labEdate__;
    }
    /**
     * <p>labEdate をセットします。
     * @param labEdate labEdate
     */
    public void setLabEdate(UDate labEdate) {
        labEdate__ = labEdate;
    }
    /**
     * <p>lauSort を取得します。
     * @return lauSort
     */
    public int getLabSort() {
        return labSort__;
    }
    /**
     * <p>lauSort をセットします。
     * @param lauSort lauSort
     */
    public void setLabSort(int lauSort) {
        labSort__ = lauSort;
    }
    /**
     * <p>lauValue を取得します。
     * @return lauValue
     */
    public String getLauValue() {
        return lauValue__;
    }
    /**
     * <p>lauValue をセットします。
     * @param lauValue lauValue
     */
    public void setLauValue(String lauValue) {
        lauValue__ = lauValue;
    }
}
