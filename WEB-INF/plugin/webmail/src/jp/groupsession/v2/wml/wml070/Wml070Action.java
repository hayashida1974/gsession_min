package jp.groupsession.v2.wml.wml070;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] WEBメール 送受信ログ管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml070Action extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml070Action.class);

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
        Wml070Form thisForm = (Wml070Form) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("cmd = " + cmd);

        if (cmd.equals("searchResult")) {
            //検索ボタンクリック
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("admTool")) {
            //戻るボタンクリック
            forward = map.findForward("admTool");

        } else if (cmd.equals("prevPage")) {
            //前へクリック
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("next")) {
            //次へクリック
            forward = __doNext(map, thisForm, req, res, con);

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
    public ActionForward __doInit(ActionMapping map, Wml070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        //検索しない時の処理
        if (form.getWml070searchFlg() == GSConstWebmail.SEARCH_EXECUTE_FALSE) {
            return __doDsp(map, form, req, res, con);
        }

        Wml070ParamModel paramMdl = new Wml070ParamModel();
        paramMdl.setParam(form);
        Wml070Biz biz = new Wml070Biz();
        ActionErrors errors = biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setWml070searchFlg(GSConstWebmail.SEARCH_EXECUTE_FALSE);
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
    public ActionForward __doSearch(ActionMapping map, Wml070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("検索開始");

        ActionErrors errors = new ActionErrors();

        //入力チェック
        errors = form.validateWml070Check(map, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setWml070searchFlg(GSConstWebmail.SEARCH_EXECUTE_FALSE);
            return __doDsp(map, form, req, res, con);
        }

        form.setWml070pageTop(1);
        //検索条件SAVE
        form.saveSearchParm();

        //検索実行フラグON
        form.setWml070searchFlg(GSConstWebmail.SEARCH_EXECUTE_TRUE);

        log__.debug("検索終了");
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説] 検索を行わない
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException 実行例外
     * @return ActionForward フォワード
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Wml070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        //初期表示情報を画面にセットする
        Wml070ParamModel paramMdl = new Wml070ParamModel();
        paramMdl.setParam(form);
        Wml070Biz biz = new Wml070Biz();
        biz.setDsp(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
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
        Wml070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml070pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setWml070pageTop(page);
        form.setWml070pageBottom(page);

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
        Wml070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml070pageTop();
        page += 1;
        form.setWml070pageTop(page);
        form.setWml070pageBottom(page);

        return __doInit(map, form, req, res, con);
    }
}
