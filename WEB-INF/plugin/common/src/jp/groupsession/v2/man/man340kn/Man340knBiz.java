package jp.groupsession.v2.man.man340kn;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.TopMenuInfo;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginParamDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPluginDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginParamModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPluginModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man340.model.Man340UrlParamModel;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン追加確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man340knBiz.class);

    /**
     * <br>[機  能] テンポラリディレクトリのファイル削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void doDeleteFile(String tempDir) throws IOToolsException {

        //テンポラリディレクトリのファイルを削除する
        IOTools.deleteDir(tempDir);
    }

    /**
     * <br>[機  能] プラグインの並び順を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param userSid ユーザSID
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @throws SQLException SQL実行例外
     */
    public void setSort(Man340knParamModel paramMdl, Connection con,
                        PluginConfig pconfig, int userSid)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException  {
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
        ArrayList<String> r_plugin_list = new ArrayList<String>();
        ArrayList<String> l_plugin_list = new ArrayList<String>();
        for (Plugin plugin : pconfig.getPluginDataList()) {

            if (plugin != null
            && !plugin.getId().equals(GSConst.PLUGINID_COMMON)
            && !plugin.getId().equals("help")
            && !plugin.getId().equals("license")) {
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
            for (Plugin pluginData : pluginList) {
                if (pluginData.getId().equals("license")
                    || pluginData.getId().equals(GSConst.PLUGINID_COMMON)
                    || pluginData.getId().equals("help")) {

                    continue;
                }
                r_plugin_list.add(pluginData.getId());
            }
        } else {
            for (CmnTdispModel dbDispMdl : dispList) {
                Plugin plugin = pconfig.getPlugin(dbDispMdl.getTdpPid());
                if (plugin != null) {
                    if (plugin.getId().equals("license")
                            || plugin.getId().equals(GSConst.PLUGINID_COMMON)
                            || plugin.getId().equals("help")) {
                        continue;
                    }
                    r_plugin_list.add(dbDispMdl.getTdpPid());
                    pluginIdList.remove(dbDispMdl.getTdpPid());
                }
            }
            r_plugin_list.add(paramMdl.getMan340funcId());
            pluginIdList.remove(paramMdl.getMan340funcId());
            for (String pluginId : pluginIdList) {
                Plugin plugin = pconfig.getPlugin(pluginId);
                if (plugin.getId().equals("license")
                        || plugin.getId().equals(GSConst.PLUGINID_COMMON)
                        || plugin.getId().equals("help")) {
                    continue;
                }
                l_plugin_list.add(pluginId);
            }
        }

        __insertTdisp(con, (String[]) r_plugin_list.toArray(new String[r_plugin_list.size()])
                         , (String[]) l_plugin_list.toArray(new String[l_plugin_list.size()])
                         , userSid);
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
    private void __insertTdisp(Connection con, String[] viewList, String[] notViewList,
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
     * <br>[機  能] プラグイン情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    public void insertData(Man340knParamModel paramMdl,
                           Connection con,
                           MlCountMtController cntCon,
                           int userSid,
                           String appRoot,
                           String tempDir)
    throws Exception {
        log__.debug("START");

        UDate now = new UDate();

        //ユーザプラグインSID採番
        int saiSid = (int) cntCon.getSaibanNumber(SaibanModel.SBNSID_USER_PLUGIN,
                      SaibanModel.SBNSID_SUB_USER_PLUGIN,
                      userSid);

        //ユーザプラグイン情報の登録
        CmnUsrPluginModel usrPlgMdl = new CmnUsrPluginModel();
        paramMdl.setMan340funcId(GSConstMain.USER_PLUGIN_HEAD + String.valueOf(saiSid));
        usrPlgMdl.setCupPid(paramMdl.getMan340funcId());
        usrPlgMdl.setCupName(paramMdl.getMan340title());
        usrPlgMdl.setCupUrl(paramMdl.getMan340url());
        if (paramMdl.getMan340openKbn() == GSConstMain.TARGET_BLANK) {
            usrPlgMdl.setCupTarget(GSConstMain.TARGET_BLANK);
        } else {
            usrPlgMdl.setCupTarget(GSConstMain.TARGET_BODY);
        }
        usrPlgMdl.setCupView(0);


        //アイコン情報を登録
        Long binSid = new Long(0);
        CommonBiz cmnBiz = new CommonBiz();
        if (!NullDefault.getStringZeroLength(paramMdl.getMan340file(), "").equals("")
                && !NullDefault.getStringZeroLength(paramMdl.getMan340SaveFile(), "").equals("")) {

            String filePath = tempDir
                             + paramMdl.getMan340SaveFile() + GSConstCommon.ENDSTR_SAVEFILE;

            binSid = cmnBiz.insertBinInfo(
                    con, appRoot, cntCon, userSid, now, filePath, paramMdl.getMan340file());

        }
        usrPlgMdl.setBinSid(binSid);
        paramMdl.setMan340knBinSid(binSid);

        usrPlgMdl.setCupParamKbn(paramMdl.getMan340paramKbn());

        if (paramMdl.getMan340paramKbn() == GSConstMain.PARAM_KBN_YES) {
            usrPlgMdl.setCupSendKbn(paramMdl.getMan340sendKbn());
        } else {
            usrPlgMdl.setCupSendKbn(GSConstMain.SEND_KBN_POST);
        }


        CmnUsrPluginDao cmnUsrPlgDao = new CmnUsrPluginDao(con);
        cmnUsrPlgDao.insert(usrPlgMdl);

        //パラメータ設定区分「設定する」の場合
        if (paramMdl.getMan340paramKbn() == GSConstMain.PARAM_KBN_YES) {

            //URLパラメータ情報を登録する
            CmnPluginParamDao cppDao = new CmnPluginParamDao(con);

            //URLパラメータの登録用データを取得する
            ArrayList<CmnPluginParamModel> cppList =
                    __getRegistCppList(
                            paramMdl.getMan340funcId(),
                            paramMdl.getMan340urlParamListToList());

            cppDao.insert(cppList);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグイン情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    public void updateData(Man340knParamModel paramMdl,
                           Connection con,
                           MlCountMtController cntCon,
                           int userSid,
                           String appRoot,
                           String tempDir)
    throws Exception {
        log__.debug("START");

        UDate now = new UDate();

        //ユーザプラグイン情報の更新
        CmnUsrPluginModel usrPlgMdl = new CmnUsrPluginModel();
        usrPlgMdl.setCupPid(paramMdl.getMan340funcId());
        usrPlgMdl.setCupName(paramMdl.getMan340title());
        usrPlgMdl.setCupUrl(paramMdl.getMan340url());
        if (paramMdl.getMan340openKbn() == GSConstMain.TARGET_BLANK) {
            usrPlgMdl.setCupTarget(GSConstMain.TARGET_BLANK);
        } else {
            usrPlgMdl.setCupTarget(GSConstMain.TARGET_BODY);
        }
        usrPlgMdl.setCupView(0);

        //アイコン情報を更新
        Long binSid = new Long(0);
        CommonBiz cmnBiz = new CommonBiz();
        if (!NullDefault.getStringZeroLength(paramMdl.getMan340file(), "").equals("")
                && !NullDefault.getStringZeroLength(paramMdl.getMan340SaveFile(), "").equals("")) {

            String filePath = tempDir
                             + paramMdl.getMan340SaveFile() + GSConstCommon.ENDSTR_SAVEFILE;

            binSid = cmnBiz.insertBinInfo(
                    con, appRoot, cntCon, userSid, now, filePath, paramMdl.getMan340file());

        }
        usrPlgMdl.setBinSid(binSid);
        paramMdl.setMan340knBinSid(binSid);

        usrPlgMdl.setCupParamKbn(paramMdl.getMan340paramKbn());

        if (paramMdl.getMan340paramKbn() == GSConstMain.PARAM_KBN_YES) {
            usrPlgMdl.setCupSendKbn(paramMdl.getMan340sendKbn());
        } else {
            usrPlgMdl.setCupSendKbn(GSConstMain.SEND_KBN_POST);
        }

        CmnUsrPluginDao cmnUsrPlgDao = new CmnUsrPluginDao(con);
        cmnUsrPlgDao.updateIconFlg(usrPlgMdl.getCupPid());
        cmnUsrPlgDao.update(usrPlgMdl);

        CmnPluginParamDao cppDao = new CmnPluginParamDao(con);

        //URLパラメータ情報の登録前に指定プラグインIDのURLパラメータ情報を全て削除する
        cppDao.delete(paramMdl.getMan340funcId());

        //URLパラメータ情報を登録する
        if (paramMdl.getMan340paramKbn() == GSConstMain.PARAM_KBN_YES) {

            //URLパラメータの登録用データを取得する
            ArrayList<CmnPluginParamModel> cppList =
                    __getRegistCppList(
                            paramMdl.getMan340funcId(),
                            paramMdl.getMan340urlParamListToList());

            //登録
            cppDao.insert(cppList);
        }


        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグイン情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return plugin プラグインモデル
     * @throws Exception 実行例外
     */
    public Plugin setPlugin(Man340knParamModel paramMdl)
    throws Exception {
        Plugin pg = new Plugin();
        pg.setId(paramMdl.getMan340funcId());
        pg.setName(paramMdl.getMan340title());
        pg.setPluginKbn(GSConst.PLUGIN_KBN_USER);
        TopMenuInfo topMenuInfo = new TopMenuInfo();
        topMenuInfo.setView("true");
        if (paramMdl.getMan340openKbn() == GSConstMain.TARGET_BLANK) {
            log__.debug("ブランクを設定");
            topMenuInfo.setTarget(GSConstMain.TARGET_BLANK_STR);
        } else {
            log__.debug("ボディを設定");
            topMenuInfo.setTarget(GSConstMain.TARGET_BODY_STR);
        }
        topMenuInfo.setUrl(paramMdl.getMan340url());
        topMenuInfo.setBinSid(paramMdl.getMan340knBinSid());

        topMenuInfo.setParamKbn(paramMdl.getMan340paramKbn());

        if (paramMdl.getMan340paramKbn() == GSConstMain.PARAM_KBN_YES) {
            topMenuInfo.setSendKbn(paramMdl.getMan340sendKbn());
        } else {
            topMenuInfo.setSendKbn(GSConstMain.SEND_KBN_POST);
        }


        pg.setTopMenuInfo(topMenuInfo);
        return pg;
    }

    /**
     * <br>[機  能] URLパラメータモデルが登録対象外かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param upMdl URLパラメータモデル
     * @return true:登録対象外  false:登録対象
     */
    private boolean __isNotRegistUrlParamData(Man340UrlParamModel upMdl) {

        if (upMdl == null) {
            return false;
        }

        if (StringUtil.isNullZeroString(upMdl.getParamName())
                || StringUtil.isNullZeroString(upMdl.getParamValue())) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] URLパラメータを登録用データを取得する
     * <br>[解  説] 空行は除去する
     * <br>[備  考]
     * @param funcId プラグインID
     * @param urlParamList URLパラメータリスト
     * @return 登録用URLパラメータリスト
     */
    private ArrayList<CmnPluginParamModel> __getRegistCppList(
            String funcId,
            List<Man340UrlParamModel> urlParamList) {

        ArrayList<CmnPluginParamModel> ret = new ArrayList<CmnPluginParamModel>();
        CmnPluginParamModel cppMdl = null;

        int num = 1;
        for (Man340UrlParamModel mdl : urlParamList) {

            //登録対象外かチェックする
            if (__isNotRegistUrlParamData(mdl)) {
                continue;
            }

            cppMdl = new CmnPluginParamModel();
            cppMdl.setCupPid(funcId);
            //表示順
            cppMdl.setCppNum(num);
            cppMdl.setCppName(mdl.getParamName());
            cppMdl.setCppValue(mdl.getParamValue());
            ret.add(cppMdl);
            num++;
        }

        return ret;
    }

}