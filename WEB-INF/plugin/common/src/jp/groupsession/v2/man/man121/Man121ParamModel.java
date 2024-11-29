package jp.groupsession.v2.man.man121;

import java.util.List;

import jp.groupsession.v2.man.man120.Man120ParamModel;
import jp.groupsession.v2.man.man120.Man120PluginControlModel;

/**
 * <br>[機  能] メイン 管理者設定 プラグインマネージャー(制限設定一覧)画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man121ParamModel extends Man120ParamModel {
    /** 表示一覧 */
    private List<Man120PluginControlModel> man121dspList__ = null;
    /** プラグインID */
    private String man121pluginId__ = null;

    /**
     * <p>man121dspList を取得します。
     * @return man121dspList
     */
    public List<Man120PluginControlModel> getMan121dspList() {
        return man121dspList__;
    }

    /**
     * <p>man121dspList をセットします。
     * @param man121dspList man121dspList
     */
    public void setMan121dspList(List<Man120PluginControlModel> man121dspList) {
        man121dspList__ = man121dspList;
    }

    /**
     * <p>man121pluginId を取得します。
     * @return man121pluginId
     */
    public String getMan121pluginId() {
        return man121pluginId__;
    }

    /**
     * <p>man121pluginId をセットします。
     * @param man121pluginId man121pluginId
     */
    public void setMan121pluginId(String man121pluginId) {
        man121pluginId__ = man121pluginId;
    }
}