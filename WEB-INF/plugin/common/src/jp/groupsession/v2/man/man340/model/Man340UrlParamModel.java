package jp.groupsession.v2.man.man340.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] プラグイン追加時のURLのパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340UrlParamModel extends AbstractModel {

    /** パラメータ名 */
    private String paramName__ = null;
    /** パラメータ値 */
    private String paramValue__ = null;

    /**
     * <p>paramName を取得します。
     * @return paramName
     */
    public String getParamName() {
        return paramName__;
    }
    /**
     * <p>paramName をセットします。
     * @param paramName paramName
     */
    public void setParamName(String paramName) {
        paramName__ = paramName;
    }
    /**
     * <p>paramValue を取得します。
     * @return paramValue
     */
    public String getParamValue() {
        return paramValue__;
    }
    /**
     * <p>paramValue をセットします。
     * @param paramValue paramValue
     */
    public void setParamValue(String paramValue) {
        paramValue__ = paramValue;
    }

}
