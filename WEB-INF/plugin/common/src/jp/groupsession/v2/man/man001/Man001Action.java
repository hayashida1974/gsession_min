package jp.groupsession.v2.man.man001;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] メイン画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man001Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man001Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        Man001Form thisForm = (Man001Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (thisForm.getPtlMainSid() == -1 && !cmd.equals("setPosition")) {

            //管理者設定をプラグイン設定へ反映し新たにPluginConfigを生成
            PluginConfig pconfig = getPluginConfig(req);
            int userSid = getSessionUserSid(req);
            RequestModel reqMdl = getRequestModel(req);
            PluginConfig mainPconfig
                = getPluginConfigForMain(pconfig, con, getSessionUserSid(req), reqMdl);

            //ポータル遷移フラグを取得する。
            Man001ParamModel paramMdl = new Man001ParamModel();
            paramMdl.setParam(form);
            Man001Biz biz = new Man001Biz(reqMdl);
            boolean ptlFlg = biz.getPortalConfValue(con, mainPconfig, userSid, paramMdl);
            paramMdl.setFormData(form);

            if (ptlFlg) {
                return map.findForward("portal");
            }
        }



        if (cmd.equals("ktools")) {
            //管理者ツールメニュー
            log__.debug("管理者ツールメニュー");
            forward = map.findForward("ktools");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
            //回覧板アクション
        } else if (cmd.equals("cirConf")) {
            //お知らせ確認
            forward = map.findForward("cir020");
            //ショートメール
        } else if (cmd.equals("smlCheck")) {
            forward = map.findForward("smlCheck");
            //レイアウト保存ボタンクリック
        } else if (cmd.equals("setPosition")) {
            //デフォルト メイン画面表示
            __doSavePosition(map, thisForm, req, res, con);

            forward = null;
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward  = map.findForward("month");

        } else if (cmd.equals("infoSetting")) {
            //インフォメーション一覧
            forward  = map.findForward("infoSetting");

        } else if (cmd.equals("portal")) {
            //ポータル
            forward  = map.findForward("portal");


        } else if (cmd.equals("portalSetting")) {
            //ポータル設定
            forward  = map.findForward("portalSetting");
//初期表示
        } else {
            //隠しコマンド
            __setHidden(map, thisForm, req, res, con);
            //デフォルト メイン画面表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return forward forward
     * @throws Exception 例外
     */
    private ActionForward __doInit(ActionMapping map, Man001Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        PluginConfig pconfig = getPluginConfig(req);
        int sessionUserSid = getSessionUserSid(req);

        //管理者設定をプラグイン設定へ反映し新たにPluginConfigを生成
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig mainPconfig
            = getPluginConfigForMain(pconfig, con, sessionUserSid, reqMdl);

        con.setAutoCommit(true);

        Man001ParamModel paramMdl = new Man001ParamModel();
        paramMdl.setParam(form);
        Man001Biz biz = new Man001Biz(reqMdl);
        biz.getInitData(paramMdl, con, mainPconfig, sessionUserSid,
                        getServlet().getServletContext());

        //各プラグインが利用可能か判定する。
        __setCanUsePluginFlg(paramMdl, req, con);
        con.setAutoCommit(false);

        boolean commit = true;
        try {
            //自動リロード時間を設定する。
            biz.setReloadTime(paramMdl, con, sessionUserSid);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("自動リロード時間の設定に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] レイアウト保存ボタンクリック時表処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     */
    private void __doSavePosition(ActionMapping map, Man001Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        boolean commit = false;
        try {
            Man001ParamModel paramMdl = new Man001ParamModel();
            paramMdl.setParam(form);
            Man001Biz biz = new Man001Biz(getRequestModel(req));
            biz.saveMainScreenPosition(con, getSessionUserSid(req),
                                    form.getMan001PositionLeft(),
                                    form.getMan001PositionRight(),
                                    form.getMan001PositionTop(),
                                    form.getMan001PositionBottom(),
                                    form.getMan001PositionCenter());
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("メイン画面位置情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 各プラグインがパラメータを取得できる様に隠しパラメータをセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    @SuppressWarnings("all")
    private void __setHidden(ActionMapping map, Man001Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        //コマンドパラメータ取得(HCMD)
        Enumeration em = req.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            if (name.startsWith("gshd_")) {
                String val = NullDefault.getString(req.getParameter(name), "");
                log__.debug("PLUGIN Hidden Param " + name + " = " + val);
                form.addHiddenParam(name, val);
            }
        }
    }

    /**
     * <br>[機  能] ショートメールプラグインが利用可能かフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param param フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Man001ParamModel param
            , HttpServletRequest req
            , Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        CommonBiz cmnBiz = new CommonBiz();

        //ショートメール
        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
            param.setSmailUseOk(GSConstMain.PLUGIN_USE);
        } else {
            param.setSmailUseOk(GSConstMain.PLUGIN_NOT_USE);
        }

        //プロジェクト管理
        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_PROJECT, pconfig)) {
            param.setProjectUseOk(GSConstMain.PLUGIN_USE);
        } else {
            param.setProjectUseOk(GSConstMain.PLUGIN_NOT_USE);
        }

        //WEB検索
        int searchUse = CommonBiz.getWebSearchUse(pconfig);
        if (searchUse == GSConst.PLUGIN_NOT_USE) {
            param.setSearchUseOk(GSConstMain.PLUGIN_NOT_USE);
        } else {
            param.setSearchUseOk(GSConstMain.PLUGIN_USE);
        }

    }
}