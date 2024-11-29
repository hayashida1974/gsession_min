package jp.groupsession.v2.cmn.cmn140;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;

/**
 * <br>[機  能] メイン 個人設定 メニュー項目の設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn140Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn140Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn140Model パラメータ格納モデル
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param reqMdl リクエスト情報
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cmn140ParamModel cmn140Model, Connection con, PluginConfig pconfig,
                            RequestModel reqMdl)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException  {

        List<LabelValueBean> viewMenuList = new ArrayList<LabelValueBean>();
        List<LabelValueBean> notViewMenuList = new ArrayList<LabelValueBean>();

        CmnContmDao cntDao = new CmnContmDao(con);
        CmnContmModel cntMdl = cntDao.select();
        int menuKbn = cntMdl.getCntMenuStatic();
        cmn140Model.setCmn140EditKbn(menuKbn);

        CmnTdispDao dispDao = new CmnTdispDao(con);
        //管理者メニュー設定
        List<CmnTdispModel> adminDispList = dispDao.getAdminTdispList();

        // プラグイン一覧を取得
        List<Plugin> pluginList = new ArrayList<Plugin>();
        List<String> pluginIdList = new ArrayList<String>();
        for (Plugin plugin : pconfig.getPluginDataList()) {

            //メニューに表示するプラグインのみを一覧に追加する
            if (plugin != null && plugin.isMenuPlugin()) {

                if (__isDspOk(plugin.getId(), adminDispList)) {
                    pluginList.add(plugin);
                    pluginIdList.add(plugin.getId());
                }
            }
        }

        //ユーザが使用可能なプラグインのみを設定する
        CommonBiz cmnBiz = new CommonBiz();
        pluginIdList = cmnBiz.getPluginIdWithoutControl(con, pluginIdList,
                                                        reqMdl.getSmodel().getUsrsid());
        List<Plugin> viewPluginList = new ArrayList<Plugin>();
        for (Plugin plugin : pluginList) {
            if (pluginIdList.contains(plugin.getId())) {
                viewPluginList.add(plugin);
            }
        }
        pluginList = viewPluginList;

        //トップ表示設定を取得

        //個人メニュー設定
        List<CmnTdispModel> dispList = dispDao.select(reqMdl.getSmodel().getUsrsid());

        if (dispList == null || dispList.isEmpty()) {
            //トップ表示設定が登録されていない場合

            //プラグイン情報を一時格納するmap
            Map<Integer, Plugin> map = new HashMap<Integer, Plugin>();
            List<Integer> list = new ArrayList<Integer>();
            int count = 999;

            int order = 1;
            for (Plugin pluginData : pluginList) {
                int menuOrder = 0;
                try {
                    menuOrder = NullDefault.getInt(pluginData.getTopMenuInfo().getOrder(), 0);
                } catch (Exception e) {
                }
                if (menuOrder > 0) {
                    //プラグインに表示順の指定がある場合
                    map.put(menuOrder, pluginData);
                    list.add(menuOrder);
                } else {
                    //プラグインに表示順の指定がない場合
                    map.put(count, pluginData);
                    list.add(count);
                    count++;
                }
            }
            Collections.sort(list);
            for (int i : list) {
                LabelValueBean pluginLabel = __createPluginOption(order++, map.get(i).getId(),
                        map.get(i).getName(reqMdl));
                viewMenuList.add(pluginLabel);
            }


        } else {

            int order = 1;
            for (CmnTdispModel dbDispMdl : dispList) {
                if (pluginIdList.contains(dbDispMdl.getTdpPid())) {
                    Plugin plugin = pconfig.getPlugin(dbDispMdl.getTdpPid());
                    if (plugin != null) {

                        viewMenuList.add(__createPluginOption(order++,
                                                            dbDispMdl.getTdpPid(),
                                                            plugin.getName(reqMdl)));
                        pluginIdList.remove(dbDispMdl.getTdpPid());
                    }
                }
            }

            for (String pluginId : pluginIdList) {
                Plugin plugin = pconfig.getPlugin(pluginId);
                notViewMenuList.add(__createPluginOption(order++,
                                                        pluginId,
                                                        plugin.getName(reqMdl)));
            }
        }

        cmn140Model.setCmn140viewMenuLabel(viewMenuList);
        cmn140Model.setCmn140viewMenuList(
                viewMenuList.stream()
                            .map(label -> label.getValue())
                            .toArray(String[]::new)
                    );
        cmn140Model.setCmn140notViewMenuLabel(notViewMenuList);
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
    /**
     * <br>[機  能] プラグインコンボの情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param order 並び順
     * @param pluginId プラグインID
     * @param pluginName プラグイン名称
     * @return プラグインコンボの情報
     */
    private LabelValueBean __createPluginOption(int order, String pluginId, String pluginName) {
        StringBuilder labelValue = new StringBuilder();
        labelValue.append(order++);
        labelValue.append(":");
        labelValue.append(pluginName);

        return new LabelValueBean(labelValue.toString(), pluginId);
    }

    /**
     * <br>[機  能] 表示メニューの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn140Model パラメータ格納モデル
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteViewMenu(Cmn140ParamModel cmn140Model, Connection con, int userSid)
    throws SQLException {

        ArrayList<String> pluginIdList = new ArrayList<String>();
        for (String pluginId : cmn140Model.getCmn140viewMenuList()) {
            pluginIdList.add(pluginId);
        }

        //選択されたメニューを表示メニュー一覧から削除
        for (String deletePlugin : cmn140Model.getCmn140selectViewMenu()) {
            if (!deletePlugin.equals(GSConst.PLUGINID_MAIN)) {
                pluginIdList.remove(deletePlugin);
            }
        }

        __insertTdisp(con, pluginIdList, userSid);
    }

    /**
     * <br>[機  能] 表示メニューの追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn140Model パラメータ格納モデル
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void addViewMenu(Cmn140ParamModel cmn140Model, Connection con, int userSid)
    throws SQLException {

        ArrayList<String> pluginIdList = new ArrayList<String>();
        for (String pluginId : cmn140Model.getCmn140viewMenuList()) {
            pluginIdList.add(pluginId);
        }

        //選択されたメニューを表示メニュー一覧に追加
        for (String addPlugin : cmn140Model.getCmn140selectNotViewMenu()) {
            pluginIdList.add(addPlugin);
        }

        __insertTdisp(con, pluginIdList, userSid);
    }

    /**
     * <br>[機  能] 表示メニューの表示順を変更する
     * <br>[解  説] 指定された表示メニューの表示順を上げる
     * <br>[備  考]
     * @param cmn140Model パラメータ格納モデル
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return ソートフラグ
     * @throws SQLException SQL実行例外
     */
    public boolean updateOrderViewMenu(Cmn140ParamModel cmn140Model,
            Connection con, int userSid, int changeKbn) throws SQLException {

        boolean sortFlg = false;
        SortResult<CmnTdispModel> result = null;
        final CmnTdispDao dao = new CmnTdispDao(con);

        //ラジオ選択値取得
        String[] selectMenu = cmn140Model.getCmn140selectViewMenu();
        if (selectMenu == null) {
            return false;
        }
        String pluginId = selectMenu[0];

        //ユーザの表示順が登録されていない場合、登録を行う
        List<CmnTdispModel> mdlList = dao.select(userSid);
        if (mdlList.isEmpty()) {
            ArrayList<String> pluginIdList = new ArrayList<String>();
            for (String plgId : cmn140Model.getCmn140viewMenuList()) {
                pluginIdList.add(plgId);
            }
            __insertTdisp(con, pluginIdList, userSid);
        }

        SortChangeBiz<CmnTdispModel> sortBiz =
                SortChangeBiz.<CmnTdispModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select(userSid);
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
                    m.setTdpEdate(new UDate());
                    dao.updateSort(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }

        if (result != null) {
            sortFlg = true;
        }
        return sortFlg;
    }

    /**
     * <br>[機  能] トップ表示設定の登録を行う
     * <br>[解  説]
     * <br>[備  考] プラグインの追加/削除を考えdelete insertで登録を行う
     *
     * @param con コネクション
     * @param pluginIdList 表示するプラグインID一覧
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __insertTdisp(Connection con, List<String> pluginIdList, int userSid)
    throws SQLException {

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        CmnTdispDao dispDao = new CmnTdispDao(con);
        boolean commit = false;
        try {

            //既存の情報を削除
            dispDao.delete(userSid);

            CmnTdispModel model = new CmnTdispModel();
            model.setUsrSid(userSid);
            model.setTdpAuid(userSid);
            model.setTdpAdate(new UDate());
            model.setTdpEuid(userSid);
            model.setTdpEdate(model.getTdpAdate());

            int order = 1;
            for (String pluginId : pluginIdList) {
                model.setTdpPid(pluginId);
                model.setTdpOrder(order++);
                dispDao.insert(model);
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

            if (autoCommit) {
                con.setAutoCommit(autoCommit);
            }
        }
    }

    public void editViewMenu(Cmn140ParamModel paramModel, Connection con,
            int usrsid) throws SQLException {
        ArrayList<String> pluginIdList = new ArrayList<String>();
        for (String pluginId : paramModel.getCmn140viewMenuList()) {
            pluginIdList.add(pluginId);
        }

        //選択されたメニューを表示メニュー一覧から削除
        for (String deletePlugin : paramModel.getCmn140selectViewMenu()) {
            if (!deletePlugin.equals(GSConst.PLUGINID_MAIN)) {
                pluginIdList.remove(deletePlugin);
            }
        }

        __insertTdisp(con, pluginIdList, usrsid);

    }
}
