package jp.groupsession.v2.man.man180;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴自動削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man180Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man180Action.class);

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

         ActionForward forward = null;
         Man180Form manform = (Man180Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //OKボタン押下
         if (cmd.equals("batch_settei_kakunin")) {
             log__.debug("OKボタン押下");
             saveToken(req);
             forward = map.findForward("batch_settei_kakunin");
         //戻るボタン押下
         } else if (cmd.equals("backKtool")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("ktools");
         //確認画面から戻る
         } else if (cmd.equals("back_batch_input")) {
             log__.debug("確認画面から戻る");
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
                                     Man180Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

         con.setAutoCommit(true);
         Man180ParamModel paramMdl = new Man180ParamModel();
         paramMdl.setParam(form);
         Man180Biz biz = new Man180Biz();
         biz.setInitData(con, paramMdl);
         paramMdl.setFormData(form);
         con.setAutoCommit(false);

         return __doRedraw(map, form, req, res, con);
     }

     /**
      * <br>[機  能] 常に設定する値の処理
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
                                       Man180Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con) throws Exception {

         Man180ParamModel paramMdl = new Man180ParamModel();
         paramMdl.setParam(form);
         Man180Biz biz = new Man180Biz();
         biz.setDspData(getRequestModel(req), paramMdl);
         paramMdl.setFormData(form);

         return map.getInputForward();
     }
}