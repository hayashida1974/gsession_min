package jp.groupsession.v2.man.man520;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
*
* <br>[機  能] データ使用量一覧画面 パラメータモデル
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class Man520ParamModel extends AbstractParamModel {
    /** プラグイン別データ使用量一覧 */
    private List<Man520DataModel> dataList__ = null;
    /** 合計 値*/
    private String sumValue__ = null;
    /** 合計 単位*/
    private String sumUnit__ = null;
    
    /**
     * <p>dataList を取得します。
     * @return dataList
     * @see jp.groupsession.v2.man.man520.Man520Form#dataList__
     */
    public List<Man520DataModel> getDataList() {
        return dataList__;
    }
    /**
     * <p>dataList をセットします。
     * @param dataList dataList
     * @see jp.groupsession.v2.man.man520.Man520Form#dataList__
     */
    public void setDataList(List<Man520DataModel> dataList) {
        dataList__ = dataList;
    }
    /**
     * <p>sumValue を取得します。
     * @return sumValue
     * @see jp.groupsession.v2.man.man520.Man520Form#sumValue__
     */
    public String getSumValue() {
        return sumValue__;
    }
    /**
     * <p>sumValue をセットします。
     * @param sumValue sumValue
     * @see jp.groupsession.v2.man.man520.Man520Form#sumValue__
     */
    public void setSumValue(String sumValue) {
        sumValue__ = sumValue;
    }
    /**
     * <p>sumUnit を取得します。
     * @return sumUnit
     * @see jp.groupsession.v2.man.man520.Man520Form#sumUnit__
     */
    public String getSumUnit() {
        return sumUnit__;
    }
    /**
     * <p>sumUnit をセットします。
     * @param sumUnit sumUnit
     * @see jp.groupsession.v2.man.man520.Man520Form#sumUnit__
     */
    public void setSumUnit(String sumUnit) {
        sumUnit__ = sumUnit;
    }
}
