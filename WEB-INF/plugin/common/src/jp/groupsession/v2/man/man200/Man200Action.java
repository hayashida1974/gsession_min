package jp.groupsession.v2.man.man200;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 管理者設定 パスワードルール設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man200Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man200Action.class);

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
     * @throws Exception 実行時例外
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
                                        Connection con) throws Exception {

        //パスワード変更を許可しない場合、アクセスエラーとする
        if (!canChangePassword(con, 0)) {
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        Man200Form manform = (Man200Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //OKボタン押下
        if (cmd.equals("rule_settei_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doSetting(map, manform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("backKtool")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("ktools");
        //確認画面からの遷移
        } else if (cmd.equals("back_pswdConf")) {
            log__.debug("確認画面からの遷移");
            forward = __doRedraw(map, manform, req, res, con);
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, manform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return ActionForward フォワード
    * @throws Exception 実行時例外
    */
    private ActionForward __doInit(ActionMapping map,
                                    Man200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Man200ParamModel paramMdl = new Man200ParamModel();
        paramMdl.setParam(form);
        Man200Biz biz = new Man200Biz();
        biz.setInitData(con, paramMdl);
        biz.setDspData(reqMdl, paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再表示処理
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return ActionForward フォワード
    * @throws Exception 実行時例外
    */
    private ActionForward __doRedraw(ActionMapping map,
                                    Man200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Man200ParamModel paramMdl = new Man200ParamModel();
        paramMdl.setParam(form);
        Man200Biz biz = new Man200Biz();
        biz.setDspData(reqMdl, paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return ActionForward フォワード
    * @throws Exception 実行時例外
    */
    private ActionForward __doSetting(
        ActionMapping map,
        Man200Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = null;
        errors = form.validateCheck(getRequestModel(req));

        if (errors.size() > 0) {
            addErrors(req, errors);
            __doRedraw(map, form, req, res, con);
            log__.debug("入力エラー");
            return map.getInputForward();
        }

        saveToken(req);
        return map.findForward("rule_settei_kakunin");
    }
}