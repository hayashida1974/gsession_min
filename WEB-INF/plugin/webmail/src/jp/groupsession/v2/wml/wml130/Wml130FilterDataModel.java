package jp.groupsession.v2.wml.wml130;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール フィルター情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml130FilterDataModel extends AbstractModel {

    /** フィルターSID */
    private int filterSid__ = 0;
    /** フィルター名称 */
    private String filterName__ = null;
    /** フィルター番号 */
    private int filterSort__ = 0;
    /** 表示順(画面用) */
    private String filValue__ = null;

    /**
     * <p>filterName を取得します。
     * @return filterName
     */
    public String getFilterName() {
        return filterName__;
    }
    /**
     * <p>filterName をセットします。
     * @param filterName filterName
     */
    public void setFilterName(String filterName) {
        filterName__ = filterName;
    }
    /**
     * <p>filterSid を取得します。
     * @return filterSid
     */
    public int getFilterSid() {
        return filterSid__;
    }
    /**
     * <p>filterSid をセットします。
     * @param filterSid filterSid
     */
    public void setFilterSid(int filterSid) {
        filterSid__ = filterSid;
    }
    /**
     * <p>filValue を取得します。
     * @return filValue
     */
    public String getFilValue() {
        return filValue__;
    }
    /**
     * <p>filValue をセットします。
     * @param filValue filValue
     */
    public void setFilValue(String filValue) {
        filValue__ = filValue;
    }
    /**
     * <p>filterSort を取得します。
     * @return filterSort
     */
    public int getFilterSort() {
        return filterSort__;
    }
    /**
     * <p>filterSort をセットします。
     * @param filterSort filterSort
     */
    public void setFilterSort(int filterSort) {
        filterSort__ = filterSort;
    }
}
