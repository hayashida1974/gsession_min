package jp.groupsession.v2.wml.wml270;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール 送信先リスト管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml270Action extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml270Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return ((Wml270Form) form).getWmlAccountMode() == 1;
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
        Wml270Form thisForm = (Wml270Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("addDestList")) {
            //追加ボタンクリック
            forward = map.findForward("confDestList");
        } else if (cmd.equals("destListDetail")) {
            //送信先リスト名クリック
            forward = map.findForward("confDestList");
            if (thisForm.getWmlAccountMode() == 1) {
                //編集可能な送信先リストかを判定する
                WmlBiz wmlBiz = new WmlBiz();
                if (!wmlBiz.canEditDestlist(con, thisForm.getWmlEditDestList(),
                                                        getSessionUserSid(req))) {
                    forward = map.findForward("viewDestList");
                }
            }

        } else if (cmd.equals("destListDelete")) {
            //削除ボタン
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("doDeleteDestList")) {
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
            if (thisForm.getWmlAccountMode() == 1) {
                forward = map.findForward("psnTool");
            } else {
                forward = map.findForward("admTool");
            }

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
    public ActionForward __doInit(ActionMapping map, Wml270Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Wml270ParamModel paramMdl = new Wml270ParamModel();
        paramMdl.setParam(form);
        Wml270Biz biz = new Wml270Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

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
    public ActionForward __doSearch(ActionMapping map, Wml270Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setWml270searchFlg(1);
        } else {
            form.setWml270svKeyword(form.getWml270keyword());
            form.setWml270searchFlg(0);
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
        Wml270Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml270pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setWml270pageTop(page);
        form.setWml270pageBottom(page);

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
        Wml270Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml270pageTop();
        page += 1;
        form.setWml270pageTop(page);
        form.setWml270pageBottom(page);

        return __doInit(map, form, req, res, con);
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
    private ActionForward __doDelete(
        ActionMapping map,
        Wml270Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionErrors errors = form.validateDelete(getRequestModel(req));
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
        Wml270Form form,
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
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=doDeleteDestList");

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = "";

        Wml270Biz biz = new Wml270Biz();
        msg = msgRes.getMessage("sakujo.kakunin.list",
                                gsMsg.getMessage(req, "wml.262"),
                                biz.getSendListName(con, form.getWml270selectDestList()));

        cmn999Form.setMessage(msg);

        cmn999Form.addHiddenParam("CMD", "search");
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("wmlCmdMode", form.getWmlCmdMode());
        cmn999Form.addHiddenParam("wmlViewAccount", form.getWmlViewAccount());
        cmn999Form.addHiddenParam("wmlAccountMode", form.getWmlAccountMode());
        cmn999Form.addHiddenParam("wmlAccountSid", form.getWmlAccountSid());
        cmn999Form.addHiddenParam("wml270svKeyword", form.getWml270svKeyword());
        cmn999Form.addHiddenParam("wml270sortKey", form.getWml270sortKey());
        cmn999Form.addHiddenParam("wml270order", form.getWml270order());
        cmn999Form.addHiddenParam("wml270searchFlg", form.getWml270searchFlg());
        cmn999Form.addHiddenParam("wml270keyword", form.getWml270keyword());
        cmn999Form.addHiddenParam("wml270selectDestList", form.getWml270selectDestList());

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
        Wml270Form form,
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

        //送信先リストを削除する
        Wml270Biz biz = new Wml270Biz();

        //ログ用 退避
        String delList = biz.getDestList(con, form.getWml270selectDestList());

        Wml270ParamModel paramMdl = new Wml270ParamModel();
        paramMdl.setParam(form);

        boolean commit = false;
        try {
            biz.deleteDestList(con, paramMdl, userSid);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("送信先リストの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        paramMdl.setFormData(form);

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        wmlBiz.outPutLog(map, getRequestModel(req), con,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                "[" + gsMsg.getMessage("wml.263") + "]" + delList);

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
        Wml270Form form,
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
                                gsMsg.getMessage(req, "wml.262")));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
