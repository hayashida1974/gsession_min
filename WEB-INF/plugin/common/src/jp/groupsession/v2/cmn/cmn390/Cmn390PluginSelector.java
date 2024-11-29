package jp.groupsession.v2.cmn.cmn390;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;

public class Cmn390PluginSelector extends AbstractSelector {
    /** プラグインIDリスト*/
    private List<String> pluginIdList__ = null;
    /** プラグインMap*/
    private Map<String, Plugin> pluginMap__ = new HashMap<>();


    protected Cmn390PluginSelector(ParamForm param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<Cmn390PluginSelector, ParamForm> {

        public ParamForm(Class<Cmn390PluginSelector> targetClz) {
            super(targetClz);
        }

    }
    /**
     *
     * <br>[機  能] ビルダークラスの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダークラス
     */
    public static ParamForm builder() {
        ParamForm ret = new ParamForm(Cmn390PluginSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
            throws SQLException {
        List<String> pluginIdList = getPluginIdList(reqMdl, con);


        List<IChild> ret =
                selectedSidList.stream()
                    .filter(pluginId -> pluginIdList.contains(pluginId))
                    .map(pluginId -> pluginMap__.get(pluginId))
                    .map(plugin -> new Child(plugin.getName(reqMdl), plugin.getId()))
                    .collect(Collectors.toList());
        return ret;
    }
    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        List<String> pluginIdList = getPluginIdList(reqMdl, con);

        List<IChild> ret =  pluginIdList.stream()
            .filter(pluginId -> selectedSidList.contains(pluginId) == false)
            .map(pluginId -> pluginMap__.get(pluginId))
            .map(plugin -> new Child(plugin.getName(reqMdl), plugin.getId()))
            .collect(Collectors.toList());
        return ret;
    }
    /**
     * <p>pluginIdList を取得します。
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return pluginIdList プラグインID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getPluginIdList(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        if (pluginIdList__ != null) {
            return pluginIdList__;
        }
        List<String> pluginIdList = new ArrayList<String>();
        PluginConfig pconfig = GroupSession.getResourceManager().getPluginConfig(reqMdl);

        List<String> defPidList = Arrays.asList(GSConstCommon.FIREWALL_MBLAPP_ALLOW_PLUGINLIST);
        for (Plugin plugin : pconfig.getPluginDataList()) {
            //デフォルトで許可するプラグインは除外する
            if (!defPidList.contains(plugin.getId())) {
                //「プラグインマネージャーに表示する」プラグインのみを選択可能とする
                if (plugin.isViewPluginManager()) {
                    pluginMap__.put(plugin.getId(), plugin);
                    pluginIdList.add(plugin.getId());
                }
            }
        }
        pluginIdList__ = pluginIdList;
        return pluginIdList__;

    }
}
