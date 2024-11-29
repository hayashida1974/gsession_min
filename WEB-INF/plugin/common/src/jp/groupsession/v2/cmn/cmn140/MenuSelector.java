package jp.groupsession.v2.cmn.cmn140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;

public class MenuSelector extends AbstractSelector {
    /** プラグインIDリスト*/
    private List<String> pluginIdList__ = null;
    /** プラグインMap*/
    private Map<String, Plugin> pluginMap__ = new HashMap<>();


    protected MenuSelector(ParamForm param) {
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
    public static class ParamForm extends SelectorFactory<MenuSelector, ParamForm> {

        public ParamForm(Class<MenuSelector> targetClz) {
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
        ParamForm ret = new ParamForm(MenuSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
            throws SQLException {
        List<String> pluginIdList = __getPluginIdList(reqMdl, con);


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
        List<String> pluginIdList = __getPluginIdList(reqMdl, con);

        List<IChild> ret =  pluginIdList.stream()
            .filter(pluginId -> selectedSidList.contains(pluginId) == false)
            .map(pluginId -> pluginMap__.get(pluginId))
            .map(plugin -> new Child(plugin.getName(reqMdl), plugin.getId()))
            .collect(Collectors.toList());
        return ret;
    }
    /**
     * <p>pluginIdList を取得します。
     * @param reqMdl
     * @param con
     * @return pluginIdList
     * @throws SQLException
     * @see jp.groupsession.v2.cmn.cmn140.MenuSelectorFilter#pluginIdList__
     */
    private List<String> __getPluginIdList(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        if (pluginIdList__ != null) {
            return pluginIdList__;
        }
        //ユーザが使用可能なプラグインのみを設定する
        CommonBiz cmnBiz = new CommonBiz();
        List<String> pluginIdList = new ArrayList<String>();
        PluginConfig pconfig = GroupSession.getResourceManager().getPluginConfig(reqMdl);
        CmnTdispDao dispDao = new CmnTdispDao(con);
        //管理者メニュー設定
        List<CmnTdispModel> adminDispList = dispDao.getAdminTdispList();

        for (Plugin plugin : pconfig.getPluginDataList()) {
            pluginMap__.put(plugin.getId(), plugin);
            //メニューに表示するプラグインのみを一覧に追加する
            if (plugin != null && plugin.isMenuPlugin()) {

                if (__isDspOk(plugin.getId(), adminDispList)) {
                    pluginIdList.add(plugin.getId());
                }
            }
        }
        pluginIdList = cmnBiz.getPluginIdWithoutControl(con, pluginIdList,
                                                        reqMdl.getSmodel().getUsrsid());
        pluginIdList__ = pluginIdList;
        return pluginIdList__;

    }
    /**
     * <br>[機  能] メニュー管理者設定で許可されているプラグインか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginId プラグインID
     * @param adminDispList 管理者メニュー設定リスト
     * @return boolean true:表示OK false:非表示
     */
    private boolean __isDspOk(String pluginId, List<CmnTdispModel> adminDispList) {
        for (CmnTdispModel tdisp : adminDispList) {
            if (tdisp.getTdpPid().equals(pluginId)) {
                return true;
            }
        }
        return false;
    }



}
