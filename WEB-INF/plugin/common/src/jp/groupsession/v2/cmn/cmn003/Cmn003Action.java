package jp.groupsession.v2.cmn.cmn003;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.TopMenuBiz;
import jp.groupsession.v2.cmn.cmn001.Cmn001Biz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.TopMenuInfo;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnEnterInfDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPluginDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnEnterInfModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPluginModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsAction;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] メインメニューのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn003Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn003Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <p>
     * アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");
        ActionForward forward = null;
        Cmn003Form thisForm = (Cmn003Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        if (cmd.equals("getImageFile")) {
            //画像ダウンロード"
            forward = __doGetImageFile(map, thisForm, req, res, con);
        } else if (cmd.equals("getLogoImageFile")) {
            //画像ダウンロード"
            forward = __doGetLogoImageFile(map, thisForm, req, res, con);

        } else if (cmd.equals("getHrefUrl")) {
            //ユーザプラグインHrefのURL取得
            __getHrefUrl(map, thisForm, req, res, con);
        } else if (cmd.equals("getClickConf")) {
            //設定クリック時
            __getClickConf(map, thisForm, req, res, con);
        } else if (cmd.equals("getClickUrl")) {
            //ユーザプラグインクリック時
            __getClickUrl(map, thisForm, req, res, con);
        } else if (cmd.equals("getSettingParam")) {
            //設定ホバー時
            __getSettingParam(map, thisForm, req, res, con);
        } else {

            //メニュー一覧を取得
            con.setAutoCommit(true);

            //ユーザ追加プラグインの設定を再読み込みする
            if (CommonBiz.isMultiAP()) {
                List<CmnUsrPluginModel> usrPluginList = new ArrayList<CmnUsrPluginModel>();
                Map<String, CmnUsrPluginModel> uPmap = new HashMap<String, CmnUsrPluginModel>();
                CmnUsrPluginDao uPdao = new CmnUsrPluginDao(con);
                usrPluginList = uPdao.select();

                if (usrPluginList != null && !usrPluginList.isEmpty()) {
                    //ユーザ追加プラグインをマップに格納
                    for (CmnUsrPluginModel uPmdl : usrPluginList) {
                        uPmap.put(uPmdl.getCupPid(), uPmdl);
                    }
                }

                ArrayList < String > pidList = getPluginConfig(req).getUserPluginIdList();
                if (pidList != null && !pidList.isEmpty()) {
                    RequestModel reqMdl = getRequestModel(req);
                    for (String pid : pidList) {
                        //DBと同じidの情報がある場合
                        if (uPmap.get(pid) != null) {
                            //DBの設定と違いがないか確認
                            CmnUsrPluginModel dbUpMdl = new  CmnUsrPluginModel();
                            dbUpMdl = uPmap.get(pid);
                            if (__checkPlugin(getPluginConfig(req).getPlugin(pid),
                                    dbUpMdl, reqMdl)) {
                                //違いがある場合は更新
                                getPluginConfig(req).removePlugin(pid);
                                getPluginConfig(req).addPlugin(__setPlugin(dbUpMdl));
                            }

                        } else {
                            //DBに情報がない場合は削除
                            getPluginConfig(req).removePlugin(pid);
                        }
                    }

                    //DBにのみ値がある場合
                    if (usrPluginList != null) {
                        for (CmnUsrPluginModel cuPm : usrPluginList) {
                            if (pidList.indexOf(cuPm.getCupPid()) == -1) {
                                getPluginConfig(req).addPlugin(__setPlugin(cuPm));
                            }
                        }
                    }

                } else if (usrPluginList != null) {
                    //すべてのDBの値を設定
                    for (CmnUsrPluginModel cuPmdl : usrPluginList) {
                        getPluginConfig(req).addPlugin(__setPlugin(cuPmdl));
                    }
                }
            }

            //メモプラグインが使用可能か判定
            __setMemoPluginInfo(thisForm, req, con);

            List<MenuInfo> menuInfoList = __getMenuInfo(thisForm, req, con);
            con.setAutoCommit(false);

            //ページを設定
            int maxPage = menuInfoList.size() / 16;
            if (menuInfoList.size() % 16 > 0) {
                maxPage++;
            }
            thisForm.setCmn003maxPage(maxPage);

            int page = thisForm.getMenuPage();
            if (page > maxPage) {
                page = maxPage;
            } else if (page < 1) {
                page = 1;
            }
            thisForm.setMenuPage(page);

            //WEB検索フラグを設定
            for (MenuInfo menuInfo : menuInfoList) {
                if (menuInfo.getPluginId().equals("search")) {
                    thisForm.setCmn003searchFlg(1);
                    break;
                }
            }

            //メニュー表示プラグインを設定
            List<MenuInfo> viewMenuInfoList = new ArrayList<MenuInfo>();
            for (int idx = 0; idx < menuInfoList.size() && idx < maxPage * 16; idx++) {
                viewMenuInfoList.add(menuInfoList.get(idx));
            }
            thisForm.setMenuInfoList(viewMenuInfoList);

            //管理者ユーザフラグを設定
            thisForm.setCmn003SysAdminFlg(getSessionUserModel(req).getUsrsid() == 0);

            //管理者ユーザ or 1ページ以下の場合はボタン非表示
            if (maxPage <= 1 || thisForm.isCmn003SysAdminFlg()) {
                thisForm.setCmn003buttonFlg(1);
            }

            //ユーザ設定のロゴを取得
            Cmn001Biz biz = new Cmn001Biz();
            CmnEnterInfModel model = biz.getEnterData(con);
            Long logoBinSid = Long.valueOf(0);

            if (model != null) {
                if (model.getEniMenuImgKbn() == 1) {
                    logoBinSid = model.getMenuBinSid();
                } else {
                    //デフォルトロゴ
                    logoBinSid = Long.valueOf(0);
                }
            }
            thisForm.setCmn003LogoBinSid(logoBinSid);

            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * <br>[機  能] メニュー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return メニュー情報
     * @throws SQLException SQL実行例外
     * @throws EncryptionException 暗号化に失敗時例外
     * @throws NoSuchAlgorithmException  SHA-512アルゴリズムが使用不可
     * @throws UnsupportedEncodingException 文字エンコード時例外
     */
    private List<MenuInfo> __getMenuInfo(Cmn003Form form, HttpServletRequest req, Connection con)
            throws SQLException, EncryptionException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        PluginConfig pconfig = getPluginConfig(req);
        List<MenuInfo> menuInfoList = new ArrayList<MenuInfo>();
        TopMenuBiz topMenuBiz = new TopMenuBiz();
        String url = null;
        int    target = 0;
        int    pluginKbn = 0;
        long   binSid = Long.valueOf(0);
        int paramKbn = 0;
        int sendKbn = 0;

        //トップメニューの個人情報を取得
        Map<String, TopMenuInfo> topMenuMap
        = topMenuBiz.setTopMenu(getRequestModel(req), con, pconfig);

        CmnContmDao cntDao = new CmnContmDao(con);
        int menuStatic = cntDao.getMenuStatic();

        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<CmnTdispModel> dispList = null;
        if (menuStatic == GSConstMain.MENU_STATIC_USE) {
            //メニュー項目固定の場合、管理者設定を優先
            dispList = tdispDao.getAdminTdispList();
        } else {
            dispList = tdispDao.select(getSessionUserModel(req).getUsrsid());
        }

        RequestModel reqMdl = getRequestModel(req);
        if (dispList == null || dispList.isEmpty()) {
            //トップ表示設定が登録されていない場合
            List<Plugin> pluginList = pconfig.getPluginDataList();
            List<CmnTdispModel> adminDispList = tdispDao.getAdminTdispList();

            //プラグイン情報を一時格納するmap
            Map<Integer, MenuInfo> map = new HashMap<Integer, MenuInfo>();
            List<Integer> list = new ArrayList<Integer>();
            int count = 999;

            for (Plugin pluginData : pluginList) {
                if (pluginData.getTopMenuInfo() != null
                        && NullDefault.getString(pluginData.getTopMenuInfo().getView(),
                                "false").equals("true")
                        && pluginData.getTopMenuInfo().getUrl() != null) {
                    //URLを設定
                    if (topMenuMap.get(pluginData.getId()) != null) {
                        url = topMenuMap.get(pluginData.getId()).getUrl();
                    } else {
                        url = pluginData.getTopMenuInfo().getUrl();
                    }
                    //別ウィンドウの設定
                    if (pluginData.getTopMenuInfo().getTarget() != null) {
                        if (pluginData.getTopMenuInfo()
                                .getTarget().equals(GSConstMain.TARGET_BLANK_STR)) {
                            target = 1;
                        } else {
                            target = 0;
                        }
                    } else {
                        target = 0;
                    }
                    boolean dspOk = false;
                    for (CmnTdispModel tdisp : adminDispList) {
                        if (tdisp.getTdpPid().equals(pluginData.getId())) {
                            dspOk = true;
                            break;
                        }
                    }

                    //プラグイン区分
                    pluginKbn = pluginData.getPluginKbn();

                    //画像SIDの設定
                    if (pluginKbn != 0 && pluginData.getTopMenuInfo().getBinSid() != 0) {
                        binSid = pluginData.getTopMenuInfo().getBinSid();
                    }

                    //パラメータ設定区分
                    paramKbn = pluginData.getTopMenuInfo().getParamKbn();
                    //送信区分
                    sendKbn = pluginData.getTopMenuInfo().getSendKbn();


                    //管理者設定に無いプラグインは除外する
                    if (dspOk) {
                        int menuOrder = 0;
                        try {
                            menuOrder
                            = NullDefault.getInt(pluginData.getTopMenuInfo().getOrder(), 0);
                        } catch (Exception e) {
                        }

                        MenuInfo menuInf = __createMenuInfo(req, con,
                                pluginData.getId(), pluginData.getName(reqMdl),
                                url, target, pluginKbn, binSid,
                                paramKbn, sendKbn);

                        menuInf.setIcon(pluginData.getTopMenuInfo().getIcon());
                        menuInf.setIconClassic(pluginData.getTopMenuInfo().getIconClassic());

                        if (menuOrder > 0) {
                            //プラグインに表示順の指定がある場合
                            map.put(menuOrder, menuInf);
                            list.add(menuOrder);
                        } else {
                            //プラグインに表示順の指定がない場合
                            map.put(count, menuInf);
                            list.add(count);
                            count++;
                        }
                    }
                    pluginKbn = 0;
                    binSid = Long.valueOf(0);
                }
            }

            Collections.sort(list);
            for (int i : list) {
                menuInfoList.add(map.get(i));
            }
        } else {
            for (CmnTdispModel dispMdl : dispList) {
                Plugin plugin = pconfig.getPlugin(dispMdl.getTdpPid());
                if (plugin != null && plugin.getTopMenuInfo() != null
                        && NullDefault.getString(plugin.getTopMenuInfo().getView(), "false").equals("true")
                        && plugin.getTopMenuInfo().getUrl() != null) {
                    //URLを設定
                    if (topMenuMap.get(plugin.getId()) != null) {
                        url = topMenuMap.get(plugin.getId()).getUrl();
                    } else {
                        url = plugin.getTopMenuInfo().getUrl();
                    }
                    //別ウィンドウの設定
                    if (plugin.getTopMenuInfo().getTarget() != null) {
                        if (plugin.getTopMenuInfo().getTarget()
                                .equals(GSConstMain.TARGET_BLANK_STR)) {
                            target = 1;
                        } else {
                            target = 0;
                        }
                    } else {
                        target = 0;
                    }

                    //プラグイン区分
                    pluginKbn = plugin.getPluginKbn();

                    //画像SIDの設定
                    if (pluginKbn != 0 && plugin.getTopMenuInfo().getBinSid() != 0) {
                        binSid = plugin.getTopMenuInfo().getBinSid();
                    }

                    //パラメータ設定区分
                    paramKbn = plugin.getTopMenuInfo().getParamKbn();
                    //送信区分
                    sendKbn = plugin.getTopMenuInfo().getSendKbn();
                    MenuInfo menuInf = __createMenuInfo(req, con, dispMdl.getTdpPid(),
                            plugin.getName(reqMdl),
                            url, target, pluginKbn, binSid,
                            paramKbn, sendKbn);

                    menuInf.setIcon(plugin.getTopMenuInfo().getIcon());
                    menuInf.setIconClassic(plugin.getTopMenuInfo().getIconClassic());

                    menuInfoList.add(menuInf);
                }
                pluginKbn = 0;
                binSid = Long.valueOf(0);
            }
        }

        //プラグイン使用制限を確認する
        List<String> pluginIdList = new ArrayList<String>();
        for (MenuInfo menuInfo : menuInfoList) {
            pluginIdList.add(menuInfo.getPluginId());
        }
        CommonBiz cmnBiz = new CommonBiz();
        List<String> canUsePluginList
        = cmnBiz.getPluginIdWithoutControl(con, pluginIdList,
                getSessionUserModel(req).getUsrsid());

        List<MenuInfo> viewMenuInfoList = new ArrayList<MenuInfo>();
        for (MenuInfo menuInfo : menuInfoList) {
            if (canUsePluginList.contains(menuInfo.getPluginId())) {
                viewMenuInfoList.add(menuInfo);
            }
        }

        if (form == null) {
            form = new Cmn003Form();
        }
        return viewMenuInfoList;
    }

    /**
     * <br>[機  能] メニュー情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req リクエスト
     * @param con コネクション
     * @param id プラグインID
     * @param name プラグイン名称
     * @param url URL
     * @param target 別ウィンドウ設定
     * @param pluginKbn プラグイン区分
     * @param binSid 画像バイナリSID
     * @param paramKbn パラメータ設定区分
     * @param sendKbn 送信区分
     * @return メニュー情報
     * @throws SQLException SQL実行例外
     * @throws EncryptionException 暗号化に失敗時例外
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     * @throws UnsupportedEncodingException 文字エンコード時例外
     */
    private MenuInfo __createMenuInfo(HttpServletRequest req,
            Connection con,
            String id,
            String name,
            String url,
            int target,
            int pluginKbn,
            long binSid,
            int paramKbn,
            int sendKbn
            ) throws EncryptionException, SQLException,
    NoSuchAlgorithmException, UnsupportedEncodingException {
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setPluginId(id);
        if (name.length() > 10) {
            name = name.substring(0, 10);
        }
        menuInfo.setName(name);

        CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usrInfMdl = usrInfDao.select(getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        String replaceUrl =
                cmnBiz.replaceGSReservedWord(
                        getRequestModel(req), con, getAppRootPath(), url, usrInfMdl, true);
        menuInfo.setUrl(replaceUrl);
        //        menuInfo.setUrl(__urlCheck(req, con, url));
        menuInfo.setTarget(target);
        menuInfo.setPluginKbn(pluginKbn);
        menuInfo.setBinSid(binSid);
        menuInfo.setParamKbn(paramKbn);
        menuInfo.setSendKbn(sendKbn);

        return menuInfo;
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
            Cmn003Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        CommonBiz cmnBiz = new CommonBiz();

        CmnUsrPluginDao dao = new CmnUsrPluginDao(con);
        CmnBinfModel cbMdl = null;

        //指定したプラグインのバイナリSIDかチェックする（プラグイン画像）
        boolean dispFlg = dao.isCheckPluginImage(form.getCmn003PluginId(), form.getCmn003BinSid());
        if (dispFlg) {
            cbMdl = cmnBiz.getBinInfo(con, form.getCmn003BinSid(),
                    GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                        Encoding.UTF_8);
            }
        }
        return null;
    }
    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetLogoImageFile(ActionMapping map,
            Cmn003Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnEnterInfDao dao = new CmnEnterInfDao(con);
        //メニューのロゴ画像かチェックする
        if (dao.isCheckMenuImage(form.getCmn003LogoBinSid())) {
            CmnBinfModel cbMdl = null;

            cbMdl = cmnBiz.getBinInfo(con, form.getCmn003LogoBinSid(),
                    GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                        Encoding.UTF_8);
            }
        }

        return null;
    }

    /**
     * <br>[機  能] プラグイン情報の確認
     * <br>[解  説] DBのプラグイン設定と、PConfigの設定を見比べる
     * <br>[備  考]
     * @param pg プラグイン情報
     * @param uPmdl ユーザプラグインモデル
     * @param reqMdl リクエスト情報
     * @return true:変更あり false:変更なし
     * @throws Exception 実行例外
     */
    private boolean __checkPlugin(Plugin pg, CmnUsrPluginModel uPmdl, RequestModel reqMdl)
            throws Exception {

        boolean chk = false;

        if (!pg.getName(reqMdl).equals(uPmdl.getCupName())) {
            chk = true;
        } else if (pg.getPluginKbn() != 1) {
            chk = true;
        }

        TopMenuInfo topMenuInfo = new TopMenuInfo();
        topMenuInfo = pg.getTopMenuInfo();
        if (topMenuInfo.getView().equals("true")) {
            if (uPmdl.getCupView() != 0) {
                chk = true;
            }
        } else {
            if (uPmdl.getCupView() == 0) {
                chk = true;
            }
        }

        if (uPmdl.getCupTarget() == GSConstMain.TARGET_BLANK) {
            if (!topMenuInfo.getTarget().equals(GSConstMain.TARGET_BLANK_STR)) {
                chk = true;
            }
        } else {
            if (!topMenuInfo.getTarget().equals(GSConstMain.TARGET_BODY_STR)) {
                chk = true;
            }
        }

        if (!topMenuInfo.getUrl().equals(uPmdl.getCupUrl())) {
            chk = true;
        }

        if (topMenuInfo.getBinSid() != uPmdl.getBinSid()) {
            chk = true;
        }

        if (topMenuInfo.getParamKbn() != uPmdl.getCupParamKbn()) {
            chk = true;
        }

        if (topMenuInfo.getSendKbn() != uPmdl.getCupSendKbn()) {
            chk = true;
        }

        return chk;
    }

    /**
     * <br>[機  能] プラグイン情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param uPmdl ユーザプラグインモデル
     * @return plugin プラグインモデル
     * @throws Exception 実行例外
     */
    private Plugin __setPlugin(CmnUsrPluginModel uPmdl)
            throws Exception {
        Plugin pg = new Plugin();
        pg.setId(uPmdl.getCupPid());
        pg.setName(uPmdl.getCupName());
        pg.setPluginKbn(GSConst.PLUGIN_KBN_USER);
        TopMenuInfo topMenuInfo = new TopMenuInfo();
        if (uPmdl.getCupView() == 0) {
            topMenuInfo.setView("true");
        } else {
            topMenuInfo.setView("false");
        }

        if (uPmdl.getCupTarget() == GSConstMain.TARGET_BLANK) {
            log__.debug("ブランクを設定");
            topMenuInfo.setTarget(GSConstMain.TARGET_BLANK_STR);
        } else {
            log__.debug("ボディを設定");
            topMenuInfo.setTarget(GSConstMain.TARGET_BODY_STR);
        }
        topMenuInfo.setUrl(uPmdl.getCupUrl());
        topMenuInfo.setBinSid(uPmdl.getBinSid());

        topMenuInfo.setParamKbn(uPmdl.getCupParamKbn());
        topMenuInfo.setSendKbn(uPmdl.getCupSendKbn());

        pg.setTopMenuInfo(topMenuInfo);
        return pg;
    }

    /**
     * <br>[機  能] ユーザプラグインのHref属性のURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __getHrefUrl(ActionMapping map,
            Cmn003Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {


        //コマンドパラメータ取得
        String pid = NullDefault.getString(req.getParameter("pid"), "");

        JSONObject jsonData = null;

        if (!StringUtil.isNullZeroStringSpace(pid)) {
            Cmn003Biz biz = new Cmn003Biz(con, getRequestModel(req));

            jsonData = biz.getHrefUrl(pid, getPluginConfig(req), getAppRootPath());
        }

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(ユーザプラグインHrefデータ取得)", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] ユーザプラグインをクリック時、URL情報、パラメータ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __getClickUrl(ActionMapping map,
            Cmn003Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {


        //コマンドパラメータ取得
        String pid = NullDefault.getString(req.getParameter("pid"), "");

        JSONObject jsonData = null;

        if (!StringUtil.isNullZeroStringSpace(pid)) {
            Cmn003Biz biz = new Cmn003Biz(con, getRequestModel(req));
            jsonData = biz.getClickUrl(pid, getPluginConfig(req), getAppRootPath());
        }

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(ユーザプラグインクリック時URLデータ取得)", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] 設定をクリック時、URL情報、パラメータ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __getClickConf(ActionMapping map,
            Cmn003Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {


        //コマンドパラメータ取得
        String conf = NullDefault.getString(req.getParameter("conf"), "");
        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        JSONObject jsonData = null;

        if (!StringUtil.isNullZeroStringSpace(conf)) {
            Cmn003Biz biz = new Cmn003Biz(con, getRequestModel(req));
            jsonData = biz.getClickConf(conf, pluginId, getPluginConfig(req), getAppRootPath());
        }

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(ユーザプラグインクリック時URLデータ取得)", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] 設定ホバー時、表示する項目に必要な情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __getSettingParam(ActionMapping map,
            Cmn003Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {


        //コマンドパラメータ取得
        String plugin = NullDefault.getString(req.getParameter("plugin"), "");
        JSONObject jsonData = null;

        if (!StringUtil.isNullZeroStringSpace(plugin)) {
            RequestModel reqMdl = getRequestModel(req);
            Cmn003Biz biz = new Cmn003Biz(con, reqMdl);
            jsonData = biz.getSettingParam(plugin, 
                    getPluginConfigForMain(getPluginConfig(req), con, reqMdl),
                    reqMdl,
                    con);
        }

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(設定ホバー時データ取得)", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] メモプラグインを使用できる時trueを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __setMemoPluginInfo(Cmn003Form form, HttpServletRequest req, Connection con)
            throws Exception {

        //メモプラグイン使用制限を確認する
        RequestModel reqMdl = getRequestModel(req);
        boolean memoUse = false;
        PluginConfig plgConfig = getPluginConfigForMain(getPluginConfig(req),
                con, getSessionUserSid(req), reqMdl);
        for (String plugin : plgConfig.getPluginIdList()) {
            if (plugin.equals("memo")) {
                memoUse = true;
            }
        }
        form.setCmn003MemoUse(BooleanUtils.toInteger(memoUse));
    }
}
