package jp.groupsession.v2.cmn.restapi.plugins;

/**
 *
 * <br>[機  能] プラグイン 実行時パラメータ情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ParameterInfoModel {
    /** パラメータ名                                       */
    private String name__;
    /** パラメータ値                                       */
    private String value__;
    /**
     * <p>name を取得します。
     * @return name
     * @see ParameterInfoModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see ParameterInfoModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>value を取得します。
     * @return value
     * @see ParameterInfoModel#value__
     */
    public String getValue() {
        return value__;
    }
    /**
     * <p>value をセットします。
     * @param value value
     * @see ParameterInfoModel#value__
     */
    public void setValue(String value) {
        value__ = value;
    }


}
