package jp.groupsession.v2.man.man120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.config.AdminSettingInfo;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 プラグインマネージャー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man120Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man120Biz.class);

    /**
     * <br>[機  能] 登録確認用に使用するプラグイン名文字列を生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param pconfig プラグイン設定情報
     * @return String プラグイン名文字列
     */
    public String createPluginStringForKakunin(Man120ParamModel paramMdl, RequestModel reqMdl,
                                               PluginConfig pconfig) {

        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        StringBuilder buf = new StringBuilder();
        buf.append(gsMsg.getMessage(GSConstMain.PLUGIN_MNG_CONF_MSG1));
        buf.append("<br>");
        // プラグイン一覧を取得
        String[] viewPlugins = paramMdl.getMan120viewMenuList();
        int order = 1;
        for (String viewPlugin : viewPlugins) {
            Plugin plugin = pconfig.getPlugin(viewPlugin);
            buf.append(order++);
            buf.append(":");
            buf.append(plugin.getName(reqMdl));
            buf.append("<br>");
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 例外処理
     */
    public void setInitData(Man120ParamModel paramMdl, Connection con, PluginConfig pconfig,
                            RequestModel reqMdl)
    throws SQLException, IOToolsException {

        List<Man120Model> viewMenuList = new ArrayList<Man120Model>();
        List<Man120Model> notViewMenuList = new ArrayList<Man120Model>();
        //コントロールマスタを取得
        CmnContmDao contDao = new CmnContmDao(con);
        CmnContmModel contMdl = contDao.select();
        int menuEdit = GSConstMain.MENU_STATIC_NOT_USE;
        if (contMdl != null) {
            menuEdit = contMdl.getCntMenuStatic();
        }
        paramMdl.setMenuEditOk(String.valueOf(menuEdit));

        // プラグイン一覧を取得
        List<Plugin> pluginList = new ArrayList<Plugin>();
        ArrayList<String> pluginIdList = new ArrayList<String>();
        for (Plugin plugin : pconfig.getPluginDataList()) {

            if (plugin != null
            && plugin.isViewPluginManager()) {
                pluginList.add(plugin);
                pluginIdList.add(plugin.getId());
            }
        }

        //初期表示:非表示プラグイン無し
        //プラグイン設定情報又はトップ表示設定から画面を生成
        //トップ表示設定を取得
        CmnTdispDao dispDao = new CmnTdispDao(con);
        //管理者ユーザの登録値を取得
        List<CmnTdispModel> dispList = dispDao.getAdminTdispList();

        if (dispList == null || dispList.isEmpty()) {
            //トップ表示設定が登録されていない場合
            int order = 1;
            for (Plugin pluginData : pluginList) {
                if (!pluginData.isViewPluginManager()) {

                    continue;
                }
                Long binSid = Long.valueOf(0);
                if (pluginData.getTopMenuInfo() != null) {
                    binSid = pluginData.getTopMenuInfo().getBinSid();
                }
                Man120Model pluginLabel = __createPluginOption(order++, pluginData.getId(),
                                                                pluginData.getName(reqMdl),
                                                                pluginData.getPluginKbn(),
                                                                binSid,
                                                                pluginData);
                viewMenuList.add(pluginLabel);
            }

        } else {
            int order = 1;
            for (CmnTdispModel dbDispMdl : dispList) {
                Plugin plugin = pconfig.getPlugin(dbDispMdl.getTdpPid());
                if (plugin != null) {
                    if (!plugin.isViewPluginManager()) {
                        continue;
                    }
                    Long binSid = Long.valueOf(0);
                    if (plugin.getTopMenuInfo() != null) {
                        binSid = plugin.getTopMenuInfo().getBinSid();
                    }
                    viewMenuList.add(__createPluginOption(order++,
                                                        dbDispMdl.getTdpPid(),
                                                        plugin.getName(reqMdl),
                                                        plugin.getPluginKbn(),
                                                        binSid,
                                                        plugin));
                    pluginIdList.remove(dbDispMdl.getTdpPid());
                }
            }
            ArrayList<String> l_plugin_list = new ArrayList<String>();
            for (String pluginId : pluginIdList) {
                Plugin plugin = pconfig.getPlugin(pluginId);
                if (!plugin.isViewPluginManager()) {
                    continue;
                }
                Long binSid = Long.valueOf(0);
                if (plugin.getTopMenuInfo() != null) {
                    binSid = plugin.getTopMenuInfo().getBinSid();
                }
                notViewMenuList.add(__createPluginOption(0,
                                                        pluginId,
                                                        plugin.getName(reqMdl),
                                                        plugin.getPluginKbn(),
                                                        binSid,
                                                        plugin));
                l_plugin_list.add(pluginId);
            }
            paramMdl.setMan120notViewMenuList(
                    (String[]) l_plugin_list.toArray(new String[l_plugin_list.size()]));
        }

        paramMdl.setMan120viewMenuLabel(viewMenuList);
        paramMdl.setMan120notViewMenuLabel(notViewMenuList);

    }

    /**
     * <br>[機  能] プラグインコンボの情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param order 並び順
     * @param pluginId プラグインID
     * @param pluginName プラグイン名称
     * @param pluginKbn プラグイン区分
     * @param pluginBinSid バイナリSID
     * @param plugin プラグイン情報
     * @return プラグインコンボの情報
     * @throws IOToolsException 例外処理
     */
    private Man120Model __createPluginOption(int order,
                                             String pluginId,
                                             String pluginName,
                                             int pluginKbn,
                                             long pluginBinSid,
                                             Plugin plugin) throws IOToolsException {
        StringBuilder labelValue = new StringBuilder();
        if (order > 0) {
            labelValue.append(order++);
            labelValue.append(":");
        }
        labelValue.append(pluginName);
        Man120Model mdl = new Man120Model();
        mdl.setPluginId(pluginId);
        mdl.setPluginName(labelValue.toString());
        mdl.setPluginKbn(pluginKbn);
        mdl.setPluginBinSid(pluginBinSid);
        AdminSettingInfo asinfo = plugin.getAdminSettingInfo();
        mdl.setIcon(__getIconPath(asinfo.getIcon(), pluginId));
        mdl.setIconClassic(__getIconPath(asinfo.getIconClassic(), pluginId));

        return mdl;
    }

    /**
     * <br>[機  能] アイコンのパスが存在しない場合、デフォルトのアイコンパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param icon アイコンのパス
     * @param pluginId プラグインID
     * @return icon アイコンのパス
     * @throws IOToolsException ファイル操作時の例外
     */
    private String __getIconPath(String icon, String pluginId) throws IOToolsException {

        String defaultIcon = "../" + pluginId + "/images/menu_icon_single.gif";
        if (StringUtil.isNullZeroStringSpace(icon)) {
            return defaultIcon;
        }

        return icon;
    }

    /**
     * <br>[機  能] 表示メニューの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     */
    public void deleteViewMenu(Man120ParamModel paramMdl, int userSid) {

        //リスト（左）からパラメータを取得
        String[] l_plugins = paramMdl.getMan120notViewMenuList();
        if (l_plugins == null) {
            l_plugins = new String [0];
        }
        String[] r_plugins = new String [1];
        r_plugins[0] = paramMdl.getMan120pluginId();

        //非表示プラグインを格納
        ArrayList<String> l_plugin_list = new ArrayList<String>();
        ArrayList<String> viewMenuList = new ArrayList<String>();
        for (String pluginId : paramMdl.getMan120viewMenuList()) {
            viewMenuList.add(pluginId);
        }

        for (int i = 0; i < l_plugins.length; i++) {
            l_plugin_list.add(l_plugins[i]);
        }

        if (r_plugins.length > 0) {
            //リスト（左）へ追加
            for (int i = 0; i < r_plugins.length; i++) {
                if (r_plugins[i].length() == 0) {
                    continue;
                }
                //選択プラグイン有り
                boolean exist = false;
                for (int j = 0; j < l_plugins.length; j++) {
                    //同一プラグインSIDか判定
                    if (l_plugins[j].equals(r_plugins[i])) {
                        exist = true;
                        break;
                    }
                }

                if (!exist) {
                    l_plugin_list.add(r_plugins[i]);
                    viewMenuList.remove(r_plugins[i]);
                }
            }

            paramMdl.setMan120notViewMenuList(
                    (String[]) l_plugin_list.toArray(new String[l_plugin_list.size()]));
            paramMdl.setMan120viewMenuList(
                    (String[]) viewMenuList.toArray(new String[viewMenuList.size()]));
        }

    }

    /**
     * <br>[機  能] 表示メニューの追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     */
    public void addViewMenu(Man120ParamModel paramMdl, int userSid) {

        //同時登録ユーザリスト（右）からパラメータを取得
        String[] l_plugins = paramMdl.getMan120notViewMenuList();
        String[] selectPlugins = new String [1];
        selectPlugins[0] = paramMdl.getMan120pluginId();
        if (l_plugins == null) {
            l_plugins = new String [0];
        }

        ArrayList<String> sv_plugin_list = new ArrayList<String>();
        ArrayList<String> viewMenuList = new ArrayList<String>();
        if (paramMdl.getMan120viewMenuList() != null) {
            for (String pluginId : paramMdl.getMan120viewMenuList()) {
                viewMenuList.add(pluginId);
            }
        }

        for (String notViewPlugin : l_plugins) {
            boolean exists = false;
            for (String selectPlugin : selectPlugins) {
                if (notViewPlugin.equals(selectPlugin)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                sv_plugin_list.add(notViewPlugin);
            } else {
                viewMenuList.add(notViewPlugin);
            }
        }

        paramMdl.setMan120notViewMenuList(
                (String[]) sv_plugin_list.toArray(new String[sv_plugin_list.size()]));
        paramMdl.setMan120viewMenuList(
                (String[]) viewMenuList.toArray(new String[viewMenuList.size()]));

    }
    
    /**
     * <br>[機  能] 表示メニューの表示順を変更する
     * <br>[解  説] 指定された表示メニューの表示順を下げる
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return ArrayList 表示プラグインリスト
     * @throws SQLException SQL実行時例外
     */
    public void updateOrderViewMenu(Connection con,
            Man120ParamModel paramMdl, int changeKbn) throws SQLException {

        final CmnTdispDao dao = new CmnTdispDao(con);

        //ラジオ選択値取得
        String pluginId = paramMdl.getMan120sort();
        if (StringUtil.isNullZeroString(pluginId)) {
            return;
        }

        SortChangeBiz<CmnTdispModel> sortBiz =
                SortChangeBiz.<CmnTdispModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getAdminTdispList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getTdpPid(), pluginId));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getTdpOrder();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getTdpPid().equals(m2.getTdpPid())) {
                        return 0;
                    } else {
                        return (m1.getTdpPid().compareTo(m2.getTdpPid())) 
                                / Math.abs(m1.getTdpPid().compareTo(m2.getTdpPid()));
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setTdpOrder(newSort);
                    dao.updateSort(m);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }

    /**
     * <br>[機  能] 管理者用トップ表示設定の登録を行う
     * <br>[解  説]
     * <br>[備  考] プラグインの追加/削除を考えdelete insertで登録を行う
     *
     * @param con コネクション
     * @param viewList 表示するプラグインID一覧
     * @param notViewList 表示しないプラグインID一覧
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void insertTdisp(Connection con, String[] viewList, String[] notViewList,
                            int userSid)
    throws SQLException {

        CmnTdispDao dispDao = new CmnTdispDao(con);

        boolean commit = false;
        try {
            //既存の情報を全て削除
            dispDao.delete(GSConst.SYSTEM_USER_ADMIN);

            CmnTdispModel model = new CmnTdispModel();
            model.setUsrSid(GSConst.SYSTEM_USER_ADMIN);
            model.setTdpAuid(userSid);
            model.setTdpAdate(new UDate());
            model.setTdpEuid(userSid);
            model.setTdpEdate(model.getTdpAdate());

            int order = 1;

            if (viewList != null) {
                for (String pluginId : viewList) {
                    model.setTdpPid(pluginId);
                    model.setTdpOrder(order++);
                    dispDao.insert(model);
                }
            }

            if (notViewList != null) {
                for (String pluginId : notViewList) {
                    model.setTdpPid(pluginId);
                    model.setTdpOrder(0);
                    dispDao.insert(model);
                }
            }

            //各ユーザのメニュー設定を更新
            if (notViewList != null) {
                for (String pluginId : notViewList) {
                    dispDao.deleteUserConf(pluginId);
                }
            }

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("トップ表示設定の登録", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 指定したプラグインのアイコン画像SIDを取得する
     * <br>[解  説] 指定された表示メニューの表示順を下げる
     * <br>[備  考]
     * @param pconfig PluginConfig
     * @param pluginId プラグインID
     * @return アイコン画像SID
     */
    public long getPluginImageSid(PluginConfig pconfig, String pluginId) {

        long binSid = 0;

        Plugin plugin = pconfig.getPlugin(pluginId);
        if (plugin != null && plugin.getTopMenuInfo() != null) {
            binSid = plugin.getTopMenuInfo().getBinSid();
        }

        return binSid;
    }
}
