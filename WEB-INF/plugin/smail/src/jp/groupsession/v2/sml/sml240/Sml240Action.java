package jp.groupsession.v2.sml.sml240;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール アカウントマネージャー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml240Action extends AbstractSmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml240Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        boolean accessFlg = false;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("getAccount")
                || cmd.equals("initData")
                || cmd.equals("getAcinitDatacount")
                || cmd.equals("nextPageData")
                || cmd.equals("prevPageData")) {
            accessFlg = true;
        }
        return accessFlg;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
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
        log__.debug("START");

        ActionForward forward = null;
        Sml240Form thisForm = (Sml240Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("accountDetail")) {
            //追加ボタン or アカウント名クリック
            forward = map.findForward("editAccount");
        } else if (cmd.equals("accountDelete")) {
            //削除ボタン
            forward = __doAccountDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("delAccount")) {
            //削除確認
            __doDelUserCheck(map, thisForm, req, res, con);

        } else if (cmd.equals("doDeleteAccount")) {
            //削除実行
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("search")) {
            //検索ボタンクリック
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページボタンクリック
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("admTool")) {
            //戻るボタンクリック
            forward = map.findForward("backToMenu");

        } else if (cmd.equals("acntImport")) {
            //インポートボタンクリック
            forward = map.findForward("acntImport");

        } else if (cmd.equals("confLabel")) {
            //「ラベル」ボタンクリック
            forward = map.findForward("confLabel");

        } else if (cmd.equals("confFilter")) {
            //「フィルタ」ボタンクリック
            forward = map.findForward("confFilter");

        } else if (cmd.equals("getAccount")) {
            //検索ボタンクリック
            __doGetAccount(map, thisForm, req, res, con);
        } else if (cmd.equals("initData")) {
            //検索ボタンクリック
            __doInitData(map, thisForm, req, res, con);
        } else if (cmd.equals("nextPageData")) {
            //次ページボタンクリック
            __doNextData(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPageData")) {
            //前ページボタンクリック
            __doPrevData(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Sml240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Sml240ParamModel paramMdl = new Sml240ParamModel();
        paramMdl.setParam(form);
        Sml240Biz biz = new Sml240Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        if (form.getAccountList() == null || form.getAccountList().isEmpty()) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage(
                    "search.data.notfound", gsMsg.getMessage("wml.102"));
            StrutsUtil.addMessage(errors, msg,  gsMsg.getMessage("wml.102"));
            addErrors(req, errors);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSearch(ActionMapping map, Sml240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setSml240searchFlg(1);

        } else {

            form.setSml240svKeyword(form.getSml240keyword());
            form.setSml240svGroup(form.getSml240group());
            form.setSml240svUser(form.getSml240user());

            //検索実行フラグON
            form.setSml240searchFlg(0);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
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
    private ActionForward __doPrev(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getSml240pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setSml240pageTop(page);
        form.setSml240pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
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
    private ActionForward __doNext(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getSml240pageTop();
        page += 1;
        form.setSml240pageTop(page);
        form.setSml240pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doPrevData(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getSml240pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setSml240pageTop(page);
        form.setSml240pageBottom(page);

        __doInitData(map, form, req, res, con);
    }


    /**
     * <br>[機  能] 次ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doNextData(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getSml240pageTop();
        page += 1;
        form.setSml240pageTop(page);
        form.setSml240pageBottom(page);

        __doInitData(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doAccountDelete(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionErrors errors = form.validateAccount(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("mine");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=doDeleteAccount");

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = "";

        Sml240Biz biz = new Sml240Biz();
        msg = msgRes.getMessage("sakujo.kakunin.list",
                                gsMsg.getMessage(req, "wml.102"),
                                biz.getMsgAccountTitle(con, form.getSml240selectAcount()));

        cmn999Form.setMessage(msg);

        cmn999Form.addHiddenParam("CMD", "search");
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("smlCmdMode", form.getSmlCmdMode());
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());
        cmn999Form.addHiddenParam("smlAccountMode", form.getSmlAccountMode());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());
        cmn999Form.addHiddenParam("sml240svKeyword", form.getSml240svKeyword());
        cmn999Form.addHiddenParam("sml240svGroup", form.getSml240svGroup());
        cmn999Form.addHiddenParam("sml240svUser", form.getSml240svUser());
        cmn999Form.addHiddenParam("sml240sortKey", form.getSml240sortKey());
        cmn999Form.addHiddenParam("sml240order", form.getSml240order());
        cmn999Form.addHiddenParam("sml240searchFlg", form.getSml240searchFlg());
        cmn999Form.addHiddenParam("sml240keyword", form.getSml240keyword());
        cmn999Form.addHiddenParam("sml240group", form.getSml240group());
        cmn999Form.addHiddenParam("sml240user", form.getSml240user());
        cmn999Form.addHiddenParam("sml240selectAcount", form.getSml240selectAcount());

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //ログ用退避
        Sml240Biz biz = new Sml240Biz();
        String msg = biz.getLogMessage(form.getSml240selectAcount(), con);

        //アカウントを削除する
        Sml240ParamModel paramMdl = new Sml240ParamModel();
        paramMdl.setParam(form);
        biz.deleteAccount(con, paramMdl, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        GsMessage gsMsg = new GsMessage(req);
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
        smlBiz.outPutLog(map, getRequestModel(req),
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                "[" + gsMsg.getMessage("sml.sml240.01") + "]" + msg);

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }
    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Sml240Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");

        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "wml.102")));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除アカウント確認
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doDelUserCheck(ActionMapping map,
                                                   Sml240Form form,
                                                   HttpServletRequest req,
                                                   HttpServletResponse res,
                                                   Connection con)
        throws Exception {

        con.setAutoCommit(true);
        try {
            //削除可能チェック
            Sml240ParamModel paramMdl = new Sml240ParamModel();
            paramMdl.setParam(form);
            Sml240Biz biz = new Sml240Biz();
            ActionErrors errors = biz.checkCanDelAct(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);

            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            }

            JSONObject jsonData = new JSONObject();
            jsonData = JSONObject.fromObject(form);
            jsonData.element("success", true);
            PrintWriter out = null;

            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData);
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(削除アカウントチェック)");
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
    }

    /**
     * <br>jsonエラーメッセージ作成
     * @param req リクエスト
     * @param errors エラーメッセージ
     * @throws Exception 実行例外
     * @return errorResult jsonエラーメッセージ
     */
    private List<String> __getJsonErrorMsg(
        HttpServletRequest req, ActionErrors errors) throws Exception {

        @SuppressWarnings("all")
        Iterator iterator = errors.get();

        List<String> errorList = new ArrayList<String>();
        while (iterator.hasNext()) {
            ActionMessage error = (ActionMessage) iterator.next();
            errorList.add(getResources(req).getMessage(error.getKey(), error.getValues()));
        }
        return errorList;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @throws Exception 実行時例外
     */
    public void __doInitData(ActionMapping map, Sml240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        form.setSml240popKbn(1);

        con.setAutoCommit(true);
        Sml240ParamModel paramMdl = new Sml240ParamModel();
        paramMdl.setParam(form);
        Sml240Biz biz = new Sml240Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(アカウント情報)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] アカウント情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @throws Exception 実行時例外
     */
    public void __doGetAccount(ActionMapping map, Sml240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        form.setSml240svUser(form.getSml240user());
        form.setSml240svKeyword(form.getSml240keyword());

        form.setSml240popKbn(1);

        //検索実行フラグON
        form.setSml240searchFlg(0);

        con.setAutoCommit(true);
        Sml240ParamModel paramMdl = new Sml240ParamModel();
        paramMdl.setParam(form);
        Sml240Biz biz = new Sml240Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(アカウント情報)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
