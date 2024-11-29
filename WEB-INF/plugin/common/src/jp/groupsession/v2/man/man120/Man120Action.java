package jp.groupsession.v2.man.man120;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 プラグインマネージャー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man120Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man120Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Man120");
        ActionForward forward = null;

        Man120Form thisForm = (Man120Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backAdminMenu")) {
            log__.debug("戻る");
            forward = map.findForward("menu");

        } else if (cmd.equals("up")) {
            log__.debug("上矢印");
            forward = __doUp(map, thisForm, req, res, con);

        } else if (cmd.equals("down")) {
            log__.debug("下矢印");
            forward = __doDown(map, thisForm, req, res, con);

        } else if (cmd.equals("conf")) {
            log__.debug("設定ボタン");
            forward = __doConf(map, thisForm, req, res, con);

        } else if (cmd.equals("pluginUseEdit")) {
            log__.debug("アクセス権変更ボタン");
            forward = map.findForward("man280");

        } else if (cmd.equals("120_commit_ok")) {
            log__.debug("登録処理");
            forward = __doCommit(map, thisForm, req, res, con);

        } else if (cmd.equals("120_pluginDelete")) {
            log__.debug("未使用ラジオ選択");
            forward = __doPluginDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("120_pluginAdd")) {
            log__.debug("プラグイン使用リンククリック");
            forward = __doPluginAdd(map, thisForm, req, res, con);

        } else if (cmd.equals("seigenSettei")) {
            log__.debug("制限設定リンククリック");
            forward = map.findForward("man121");

        } else if (cmd.equals("dspSettei")) {
            log__.debug("メニュー表示設定リンククリック");
            forward = map.findForward("man122");

        } else if (cmd.equals("addPluginMenu")) {
            log__.debug("メニュー追加ボタンクリック");
            forward = map.findForward("man340");

        } else if (cmd.equals("editPluginMenu")) {
            log__.debug("ユーザプラグイン編集");
            forward = map.findForward("man340_edit");

        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード"
            forward = __doGetImageFile(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Man120");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     * @throws IOToolsException
     */
    private ActionForward __doInit(
        ActionMapping map,
        Man120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOToolsException {

        con.setAutoCommit(true);
        PluginConfig pconfig = getPluginConfig(req);
        Man120ParamModel paramMdl = new Man120ParamModel();
        paramMdl.setParam(form);
        Man120Biz biz = new Man120Biz();
        biz.setInitData(paramMdl, con, pconfig, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * 入力チェックを行い確認画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     */
    private ActionForward __doConf(
            ActionMapping map,
            Man120Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionForward forward = null;
        //入力チェック
        log__.debug("確認画面へ遷移");
        // トランザクショントークン設定
        saveToken(req);

        //確認画面へ
        log__.debug("削除確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("120_commit");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("man120");
        cmn999Form.setUrlCancel(urlForward.getPath());

        PluginConfig pconfig = getPluginConfig(req);
        Man120ParamModel paramMdl = new Man120ParamModel();
        paramMdl.setParam(form);
        Man120Biz biz = new Man120Biz();
        String plugins = biz.createPluginStringForKakunin(paramMdl, getRequestModel(req), pconfig);
        paramMdl.setFormData(form);

        cmn999Form.setMessage(msgRes.getMessage("edit.henkou.kakunin.plugin",
                getInterMessage(req, GSConstMain.TEXT_SYSCONF_PLUGIN), plugins));

        //非表示プラグイン選択
        String[] selectNotView = form.getMan120selectNotViewMenu();
        if (selectNotView != null) {
            for (String plugin : selectNotView) {
                cmn999Form.addHiddenParam("man120selectNotViewMenu", plugin);
            }
        }
        //非表示プラグイン
        String[] notView = form.getMan120notViewMenuList();
        if (notView != null) {
            for (String plugin : notView) {
                cmn999Form.addHiddenParam("man120notViewMenuList", plugin);
            }
        }
        //表示プラグイン選択
        String[] selectView = form.getMan120selectNotViewMenu();
        if (selectView != null) {
            for (String plugin : selectView) {
                cmn999Form.addHiddenParam("man120selectNotViewMenu", plugin);
            }
        }
        //表示プラグイン
        String[] view = form.getMan120viewMenuList();
        if (view != null) {
            for (String plugin : view) {
                cmn999Form.addHiddenParam("man120viewMenuList", plugin);
            }
        }

        //選択したプラグインのプラグインID
        if (!StringUtil.isNullZeroString(form.getMan120pluginId())) {
            cmn999Form.addHiddenParam("man120pluginId", form.getMan120pluginId());
        }

        cmn999Form.addHiddenParam("menuEditOk", form.getMenuEditOk());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * 登録処理を行い管理者設定メニューへ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     */
    private ActionForward __doCommit(
            ActionMapping map,
            Man120Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionForward forward = null;
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        log__.debug("登録処理");
        String[] view = form.getMan120viewMenuList();
        String[] notView = form.getMan120notViewMenuList();
        Man120Biz biz = new Man120Biz();
        biz.insertTdisp(con, view, notView, sessionUsrSid);
        //完了メッセージ画面
        forward = __doCompDsp(map, form, req, res, con);

        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, "");
        return forward;
    }

    /**
     * <br>[機  能] 未使用ラジオ選択時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     * @throws IOToolsException 例外処理
     */
    private ActionForward __doPluginDelete(
        ActionMapping map,
        Man120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException,
                        NoSuchMethodException, IOToolsException {

        if (!StringUtil.isNullZeroString(form.getMan120pluginId())) {
            int sessionUsrSid = getSessionUserSid(req); //セッションユーザSID

            //選択されたメニューを表示メニューから削除する
            Man120ParamModel paramMdl = new Man120ParamModel();
            paramMdl.setParam(form);
            Man120Biz biz = new Man120Biz();
            biz.deleteViewMenu(paramMdl, sessionUsrSid);
            paramMdl.setFormData(form);

            log__.debug("登録処理");
            String[] view = form.getMan120viewMenuList();
            String[] notView = form.getMan120notViewMenuList();
            biz.insertTdisp(con, view, notView, sessionUsrSid);

        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 右ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     * @throws IOToolsException 例外処理
     */
    private ActionForward __doPluginAdd(
        ActionMapping map,
        Man120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException,
                        NoSuchMethodException, IOToolsException {

        if (!StringUtil.isNullZeroString(form.getMan120pluginId())) {
            int sessionUsrSid = getSessionUserSid(req); //セッションユーザSID

            //選択されたプラグインを表示メニューに追加する
            Man120ParamModel paramMdl = new Man120ParamModel();
            paramMdl.setParam(form);
            Man120Biz biz = new Man120Biz();
            biz.addViewMenu(paramMdl, sessionUsrSid);
            paramMdl.setFormData(form);

            log__.debug("登録処理");
            String[] view = form.getMan120viewMenuList();
            String[] notView = form.getMan120notViewMenuList();
            biz.insertTdisp(con, view, notView, sessionUsrSid);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 上ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     * @throws IOToolsException 例外処理
     */
    private ActionForward __doUp(
        ActionMapping map,
        Man120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException,
                        NoSuchMethodException, IOToolsException {
  
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        if (!StringUtil.isNullZeroString(form.getMan120sort())) {
            boolean commitFlg = false;
            
            //選択されたメニューの表示順を上げる
            Man120ParamModel paramMdl = new Man120ParamModel();
            paramMdl.setParam(form);
            Man120Biz biz = new Man120Biz();
            try {
                biz.updateOrderViewMenu(con, paramMdl, GSConst.SORT_UP);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }
            paramMdl.setFormData(form);

            log__.debug("登録処理");
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 下ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     * @throws IOToolsException 例外処理
     */
    private ActionForward __doDown(
        ActionMapping map,
        Man120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException,
                        NoSuchMethodException, IOToolsException {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
        
        if (!StringUtil.isNullZeroString(form.getMan120sort())) {
            //選択されたメニューの表示順を下げる
            Man120ParamModel paramMdl = new Man120ParamModel();
            paramMdl.setParam(form);
            Man120Biz biz = new Man120Biz();
            biz.updateOrderViewMenu(con, paramMdl, GSConst.SORT_DOWN);
            paramMdl.setFormData(form);

            log__.debug("登録処理");
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * 登録・更新完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Man120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("menu");

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                getInterMessage(req, GSConstMain.TEXT_SYSCONF_PLUGIN)));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
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
                                            Man120Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        Man120Biz biz = new Man120Biz();
        long binSid = biz.getPluginImageSid(getPluginConfig(req), form.getMan120imgPluginId());

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;
        cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }
}
