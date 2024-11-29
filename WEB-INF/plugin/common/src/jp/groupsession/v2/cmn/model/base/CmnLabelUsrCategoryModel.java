package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import java.io.Serializable;

/**
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnLabelUsrCategoryModel implements Serializable {

    /** LUC_SID mapping */
    private int lucSid__;
    /** LUC_NAME mapping */
    private String lucName__;
    /** LUC_BIKO mapping */
    private String lucBiko__;
    /** LUC_AUID mapping */
    private int lucAuid__;
    /** LUC_ADATE mapping */
    private UDate lucAdate__;
    /** LUC_EUID mapping */
    private int lucEuid__;
    /** LUC_EDATE mapping */
    private UDate lucEdate__;
    /** LUC_SORT mapping */
    private int lucSort__;
    /** 表示順(画面用) */
    private String ulcValue__;
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
     * <p>lucName を取得します。
     * @return lucName
     */
    public String getLucName() {
        return lucName__;
    }
    /**
     * <p>lucName をセットします。
     * @param lucName lucName
     */
    public void setLucName(String lucName) {
        lucName__ = lucName;
    }
    /**
     * <p>lucBiko を取得します。
     * @return lucBiko
     */
    public String getLucBiko() {
        return lucBiko__;
    }
    /**
     * <p>lucBiko をセットします。
     * @param lucBiko lucBiko
     */
    public void setLucBiko(String lucBiko) {
        lucBiko__ = lucBiko;
    }
    /**
     * <p>lucAuid を取得します。
     * @return lucAuid
     */
    public int getLucAuid() {
        return lucAuid__;
    }
    /**
     * <p>lucAuid をセットします。
     * @param lucAuid lucAuid
     */
    public void setLucAuid(int lucAuid) {
        lucAuid__ = lucAuid;
    }
    /**
     * <p>lucAdate を取得します。
     * @return lucAdate
     */
    public UDate getLucAdate() {
        return lucAdate__;
    }
    /**
     * <p>lucAdate をセットします。
     * @param lucAdate lucAdate
     */
    public void setLucAdate(UDate lucAdate) {
        lucAdate__ = lucAdate;
    }
    /**
     * <p>lucEuid を取得します。
     * @return lucEuid
     */
    public int getLucEuid() {
        return lucEuid__;
    }
    /**
     * <p>lucEuid をセットします。
     * @param lucEuid lucEuid
     */
    public void setLucEuid(int lucEuid) {
        lucEuid__ = lucEuid;
    }
    /**
     * <p>lucEdate を取得します。
     * @return lucEdate
     */
    public UDate getLucEdate() {
        return lucEdate__;
    }
    /**
     * <p>lucEdate をセットします。
     * @param lucEdate lucEdate
     */
    public void setLucEdate(UDate lucEdate) {
        lucEdate__ = lucEdate;
    }
    /**
     * <p>lucSort を取得します。
     * @return lucSort
     */
    public int getLucSort() {
        return lucSort__;
    }
    /**
     * <p>lucSort をセットします。
     * @param lucSort lucSort
     */
    public void setLucSort(int lucSort) {
        lucSort__ = lucSort;
    }
    /**
     * <p>ulcValue を取得します。
     * @return ulcValue
     */
    public String getUlcValue() {
        return ulcValue__;
    }
    /**
     * <p>ulcValue をセットします。
     * @param ulcValue ulcValue
     */
    public void setUlcValue(String ulcValue) {
        ulcValue__ = ulcValue;
    }
}
