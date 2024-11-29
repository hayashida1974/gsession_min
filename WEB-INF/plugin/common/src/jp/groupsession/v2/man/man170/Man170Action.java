package jp.groupsession.v2.man.man170;

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
 * <br>[機  能] メイン ログイン履歴詳細画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man170Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man170Action.class);

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
         Man170Form manform = (Man170Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         if (cmd.equals("backToLoginList")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("backToLoginList");
         } else if (cmd.equals("pageleft")) {
             log__.debug("前ページボタン押下");
             manform.setMan170PageTop(manform.getMan170PageTop() - 1);
             forward = __doSearch(map, manform, req, res, con);
         } else if (cmd.equals("pageright")) {
             log__.debug("次ページボタン押下");
             manform.setMan170PageTop(manform.getMan170PageTop() + 1);
             forward = __doSearch(map, manform, req, res, con);
         } else if (cmd.equals("pageChange")) {
             log__.debug("ページコンボチェンジ");
             forward = __doSearch(map, manform, req, res, con);
         } else if (cmd.equals("research")) {
             log__.debug("再検索");
             forward = __doSearch(map, manform, req, res, con);
         } else {
             log__.debug("初期表示");
             forward = __doInit(map, manform, req, res, con);
         }

         return forward;
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
      * @throws Exception 実行時例外
      * @return ActionForward
      */
     private ActionForward __doInit(ActionMapping map,
                                     Man170Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

         return __doSearch(map, form, req, res, con);
     }

     /**
      * <br>[機  能] 検索を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws Exception 実行時例外
      * @return ActionForward
      */
     private ActionForward __doSearch(ActionMapping map,
                                       Man170Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
         throws Exception {

         con.setAutoCommit(true);
         Man170ParamModel paramMdl = new Man170ParamModel();
         paramMdl.setParam(form);
         Man170Biz biz = new Man170Biz();
         biz.setSearchData(con, getRequestModel(req), paramMdl);
         paramMdl.setFormData(form);
         con.setAutoCommit(false);

         return map.getInputForward();
     }
}