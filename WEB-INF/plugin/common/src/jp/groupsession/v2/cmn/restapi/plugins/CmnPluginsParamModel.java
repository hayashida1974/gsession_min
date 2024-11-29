package jp.groupsession.v2.cmn.restapi.plugins;

import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;

/**
 *
 * <br>[機  能] プラグイン情報取得API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class CmnPluginsParamModel {
    /** プラグインID*/
    @MaxLength(10)
    private String pluginId__;

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see CmnPluginsParamModel#pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }

    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see CmnPluginsParamModel#pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
}
