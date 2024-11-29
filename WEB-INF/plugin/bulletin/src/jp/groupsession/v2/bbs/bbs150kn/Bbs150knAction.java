package jp.groupsession.v2.bbs.bbs150kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 手動データ削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs150knAction extends AbstractBulletinAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs150knAction.class);

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
         Bbs150knForm manform = (Bbs150knForm) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //確定ボタン押下
         if (cmd.equals("goToMenu")) {
             log__.debug("確定ボタン押下");
             forward = __doDelete(map, manform, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("back_syudo_input")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("back_syudo_input");
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
                                     Bbs150knForm form,
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
     public ActionForward __doDelete(ActionMapping map,
                                      Bbs150knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {

         log__.debug("掲示板バッチ処理開始");
         ActionForward forward = null;

         if (!isTokenValid(req, true)) {
             log__.info("２重投稿");
             forward = getSubmitErrorPage(map, req);
             return forward;
         }

         con.setAutoCommit(false);
         boolean commit = false;

         try {
             BbsBiz biz = new BbsBiz();

             //削除する境界の日付を設定する
             int year = form.getBbs150Year();
             int month = form.getBbs150Month();
             UDate delUdate = new UDate();
             log__.debug("現在日 = " + delUdate.getDateString());
             log__.debug("削除条件 経過年 = " + year);
             log__.debug("削除条件 経過年 = " + month);

             delUdate.addYear((year * -1));
             delUdate.addMonth((month * -1));
             delUdate.setHour(GSConstBulletin.DAY_END_HOUR);
             delUdate.setMinute(GSConstBulletin.DAY_END_MINUTES);
             delUdate.setSecond(GSConstBulletin.DAY_END_SECOND);
             delUdate.setMilliSecond(GSConstBulletin.DAY_END_MILLISECOND);

             log__.debug("削除境界線(この日以前のデータを削除) = " + delUdate.getTimeStamp());

             //削除実行
             biz.deleteOldBulletin(con, delUdate);
             //掲示期限を過ぎたスレッドを削除する
             biz.deleteOverLimitBulletin(con);

             commit = true;

         } catch (SQLException e) {
             log__.error("掲示板データ手動削除失敗", e);
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
         BbsBiz bbsBiz = new BbsBiz(con);
         GsMessage gsMsg = new GsMessage(reqMdl);
         String delete = gsMsg.getMessage("cmn.delete");
         String value = null;

         value = "手動削除\n" + form.getBbs150Year() + "年" + form.getBbs150Month() + "月経過したデータ";

         bbsBiz.outPutLog(map, reqMdl, delete, GSConstLog.LEVEL_INFO, value);
         forward = __setUpdateComp(map, form, req);

         log__.debug("掲示板バッチ処理終了");

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
                                            Bbs150knForm form,
                                            HttpServletRequest req)
         throws Exception {

         Cmn999Form cmn999Form = new Cmn999Form();
         cmn999Form.setType(Cmn999Form.TYPE_OK);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

         //OKボタンクリック時遷移先
         ActionForward forwardOk = map.findForward("deleteComp");
         cmn999Form.setUrlOK(forwardOk.getPath());
         MessageResources msgRes = getResources(req);
         cmn999Form.setMessage(
                 msgRes.getMessage(
                         "sakujo.kanryo.object",
                         getInterMessage(req, GSConstBulletin.TEXT_THRE_DEL)));

         cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
         cmn999Form.addHiddenParam("s_key", form.getS_key());
         cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());

         req.setAttribute("cmn999Form", cmn999Form);
         return map.findForward("gf_msg");
     }

}
