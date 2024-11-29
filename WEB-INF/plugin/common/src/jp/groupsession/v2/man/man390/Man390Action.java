package jp.groupsession.v2.man.man390;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] 掲示板 管理者設定 統計情報画面アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Man390Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man390Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws SQLException {

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        ActionForward forward = null;
        Man390Form thisForm = (Man390Form) form;

        if (cmd.equals("admTool")) {
            //「管理者設定へ戻る」ボタンクリック時
            forward = __backToMenu(map, thisForm);

        } else if (cmd.equals("pageDate")) {
            //集計日付変更
            thisForm.setMan390NowPage(1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("dspNumChange")) {
            //表示件数変更
            thisForm.setMan390NowPage(1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("dateUnitChange")) {
            //月週日ラジオボタン選択
            thisForm.setMan390NowPage(1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("dateUnitChange")) {
            //月・週・日表示単位変更
            thisForm.setMan390NowPage(1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("pageChange")) {
            //ページコンボボックス選択時
            forward = __movePage(map, thisForm, req, res, con, 0);

        } else if (cmd.equals("pageLast")) {
            //「前ページ」ボタンクリック時
            forward = __movePage(map, thisForm, req, res, con, -1);

        } else if (cmd.equals("pageNext")) {
            //「次ページ」ボタンクリック時
            forward = __movePage(map, thisForm, req, res, con, 1);

        } else if (cmd.equals("bulletin")) {
            //「掲示板」ボタンクリック時
            forward = map.findForward("statsBulletin");

        } else if (cmd.equals("smail")) {
            //「ショートメール」ボタンクリック時
            forward = map.findForward("statsSmail");

        } else if (cmd.equals("circular")) {
            //「回覧板」ボタンクリック時
            forward = map.findForward("statsCircular");

        } else if (cmd.equals("file")) {
            //「ファイル管理」ボタンクリック時
            forward = map.findForward("statsFile");

        } else if (cmd.equals("webmail")) {
            //「WEBメール」ボタンクリック時
            forward = map.findForward("statsWebmail");

        } else if (cmd.equals("chat")) {
            //「チャット」ボタンクリック時
            forward = map.findForward("statsChat");

        } else {

            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
      * <br>[機  能] ページ移動処理
      * <br>[解  説]
      * <br>[備  考]
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @param pageNo ページ数
      * @return アクションフォーム
     * @throws SQLException SQL実行時例外
      */
    private ActionForward __movePage(
            ActionMapping map, Man390Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con, int pageNo) throws SQLException {

        //ページ数調整
        int page = form.getMan390NowPage();
        page += pageNo;
        if (page < 1) {
            page = 1;
        }
        form.setMan390NowPage(page);

        return __doInit(map, form, req, res, con);
    }

    /**
      * <br>[機  能] 初期表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward
      * @throws SQLException SQL実行時例外
      */
    private ActionForward __doInit(
            ActionMapping map,
            Man390Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        BaseUserModel buMdl = getSessionUserModel(req);

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Man390Biz biz = new Man390Biz(con, reqMdl);

        Man390ParamModel paramMdl = new Man390ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, buMdl,
                getPluginConfigForMain(getPluginConfig(req), con, reqMdl));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻り先の画面を判別する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __backToMenu(ActionMapping map, Man390Form form) {
        ActionForward forward = null;
        String orgnPage = form.getLogCountBack();
        if (orgnPage.equals(GSConst.PLUGIN_ID_BULLETIN)) {
            //掲示板管理者画面
            forward = map.findForward("adminBulletin");
        } else if (orgnPage.equals(GSConst.PLUGINID_SML)) {
            //ショートメール管理者画面
            forward = map.findForward("adminSmail");
        } else if (orgnPage.equals(GSConst.PLUGINID_CIR)) {
            //回覧板管理者画面
            forward  = map.findForward("adminCircular");
        } else if (orgnPage.equals(GSConst.PLUGIN_ID_FILE)) {
            //ファイル管理管理者画面
            forward = map.findForward("adminFile");
        } else if (orgnPage.equals(GSConst.PLUGINID_WML)) {
            //WEBメール管理者画面
            forward  = map.findForward("adminWebmail");
        } else if (orgnPage.equals(GSConst.PLUGIN_ID_CHAT)) {
            //チャット管理者画面
            forward = map.findForward("adminChat");
        } else {
            //メイン管理者画面
            forward  = map.findForward("backToMenu");
        }
        return forward;
    }

}