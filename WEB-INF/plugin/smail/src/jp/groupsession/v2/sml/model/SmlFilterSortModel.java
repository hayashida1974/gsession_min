package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_FILTER_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlFilterSortModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SFT_SID mapping */
    private int sftSid__;
    /** SFS_SORT mapping */
    private long sfsSort__;
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
     * <p>sftSid を取得します。
     * @return sftSid
     */
    public int getSftSid() {
        return sftSid__;
    }
    /**
     * <p>sftSid をセットします。
     * @param sftSid sftSid
     */
    public void setSftSid(int sftSid) {
        sftSid__ = sftSid;
    }
    /**
     * <p>sfsSort を取得します。
     * @return sfsSort
     */
    public long getSfsSort() {
        return sfsSort__;
    }
    /**
     * <p>sfsSort をセットします。
     * @param sfsSort sfsSort
     */
    public void setSfsSort(long sfsSort) {
        sfsSort__ = sfsSort;
    }

}
