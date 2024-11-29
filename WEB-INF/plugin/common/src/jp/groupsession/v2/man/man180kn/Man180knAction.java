package jp.groupsession.v2.man.man180kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴自動削除設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man180knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man180knAction.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(Man180knAction req, ActionForm form) {
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
         Man180knForm manform = (Man180knForm) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //確定ボタン押下
         if (cmd.equals("batch_update")) {
             log__.debug("確定ボタン押下");
             forward = __doUpdate(map, manform, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("back_batch_input")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("back_batch_input");
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
                                     Man180knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

         return map.getInputForward();
     }

     /**
      * <br>[機  能] 確定ボタンクリック時
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map ActionMapping
      * @param form フォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return forward アクションフォワード
     * @throws Exception 実行時例外
      */
     public ActionForward __doUpdate(ActionMapping map,
                                      Man180knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {

         ActionForward forward = null;

         if (!isTokenValid(req, true)) {
             log__.info("２重投稿");
             forward = getSubmitErrorPage(map, req);
             return forward;
         }

         con.setAutoCommit(false);
         boolean commit = false;

         try {

             Man180knParamModel paramMdl = new Man180knParamModel();
             paramMdl.setParam(form);
             Man180knBiz biz = new Man180knBiz();
             biz.updateLhisBatchConf(con, paramMdl, getSessionUserSid(req));
             paramMdl.setFormData(form);

             commit = true;

         } catch (SQLException e) {
             log__.error("SQLException", e);
             throw e;
         } finally {
             if (commit) {
                 con.commit();
             } else {
                 JDBCUtil.rollback(con);
             }
         }

         //ログ出力
         RequestModel reqMdl = getRequestModel(req);
         CommonBiz cmnBiz = new CommonBiz();
         GsMessage gsMsg = new GsMessage(req);
         cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                 getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO,
                 "[year]" + form.getMan180Year() + "[month]" + form.getMan180Month());

         forward = __setUpdateComp(map, form, req);
         return forward;
     }

     /**
      * <br>[機  能] 更新完了後画面設定
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map マップ
      * @param form フォーム
      * @param req リクエスト
      * @return ActionForward フォワード
      * @throws Exception 実行時例外
      */
     private ActionForward __setUpdateComp(ActionMapping map,
                                            Man180knForm form,
                                            HttpServletRequest req)
         throws Exception {

         Cmn999Form cmn999Form = new Cmn999Form();
         cmn999Form.setType(Cmn999Form.TYPE_OK);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

         //OKボタンクリック時遷移先
         ActionForward forwardOk = map.findForward("updateComp");
         cmn999Form.setUrlOK(forwardOk.getPath());
         MessageResources msgRes = getResources(req);
         cmn999Form.setMessage(
                 msgRes.getMessage(
                         "settei.kanryo.object",
                         getInterMessage(req, GSConstMain.TEXT_LHIS_AUTODEL)));

         req.setAttribute("cmn999Form", cmn999Form);
         return map.findForward("gf_msg");
     }
}