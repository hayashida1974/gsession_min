package jp.groupsession.v2.wml.wml320;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
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
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailAction;
import jp.groupsession.v2.wml.dao.base.WmlDelmailTempDao;

/**
 * <br>[機  能] WEBメール 個人設定 メール情報一括削除画面アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Wml320Action extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml320Action.class);

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
                                         Connection con) throws Exception {

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         log__.debug("CMD = " + cmd);

         ActionForward forward = null;
         Wml320Form thisForm = (Wml320Form) form;

         if (cmd.equals("delete")) {
             //削除ボタンクリック
             forward = __doDelete(map, thisForm, req, res, con);

         } else if (cmd.equals("psnTool")) {
             //戻るボタンクリック
             forward = __doBack(map, thisForm, req, res, con);
         } else if (cmd.equals("search")) {
             //検索ボタンクリック
             forward = __doSearch(map, thisForm, req, res, con);
         } else if (cmd.equals("prevPage")) {
             log__.debug("前ページクリック");
             thisForm.setWml320pageTop(thisForm.getWml320pageTop() - 1);
             forward = __doDsp(map, thisForm, req, res, con);

         } else if (cmd.equals("nextPage")) {
             log__.debug("次ページクリック");
             thisForm.setWml320pageTop(thisForm.getWml320pageTop() + 1);
             forward = __doDsp(map, thisForm, req, res, con);

         } else if (cmd.equals("changeAccount")) {
             log__.debug("アカウント変更");
             forward = __doDsp(map, thisForm, req, res, con);

         } else if (cmd.equals("getDeleteMailCount")) {
             log__.debug("削除中メール件数の確認");
             forward = __doGetDeleteMailCount(map, thisForm, req, res, con);

         } else {
             log__.debug("初期表示");
             forward = __doInit(map, thisForm, req, res, con);
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
     * @throws SQLException SQL実行時例外
      * @return ActionForward
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
      */
     private ActionForward __doInit(ActionMapping map, Wml320Form form,
             HttpServletRequest req, HttpServletResponse res, Connection con)
                     throws SQLException, IllegalAccessException,
                     InvocationTargetException, NoSuchMethodException {

         //確認画面から遷移した際は画面表示内容を設定する
         if (form.isWml320searchFlg()) {
             form.setWml320account(form.getWml320svAccount());
             form.setWml320directory(form.getWml320svDirectory());
             form.setWml320keyword(form.getWml320svKeyword());
             form.setWml320from(form.getWml320svFrom());
             form.setWml320readed(form.getWml320svReaded());
             form.setWml320dest(form.getWml320svDest());
             form.setWml320destTypeTo(form.getWml320svDestTypeTo());
             form.setWml320destTypeBcc(form.getWml320svDestTypeBcc());
             form.setWml320destTypeCc(form.getWml320svDestTypeCc());
             form.setWml320attach(form.getWml320svAttach());
             form.setWml320dateType(form.getWml320svDateType());
             form.setWml320label(form.getWml320svLabel());
             form.setWml320size(form.getWml320svSize());
             form.setWml320sortKey(form.getWml320svSortKey());
             form.setWml320order(form.getWml320svOrder());

             GsMessage gsMsg = new GsMessage(req);
             DateTimePickerBiz picker = new DateTimePickerBiz();
             String strDateNameJp = gsMsg.getMessage("wml.202");
             String endDateNameJp = gsMsg.getMessage("wml.206");

             picker.setDateParam(
                     form, 
                     "wml320DateFr",
                     "wml320dateYearFr",
                     "wml320dateMonthFr",
                     "wml320dateDayFr",
                     strDateNameJp);
             picker.setDateParam(
                     form, 
                     "wml320DateTo",
                     "wml320dateYearTo",
                     "wml320dateMonthTo",
                     "wml320dateDayTo",
                     endDateNameJp);
         } else {
             int[] initDirectory = new int[1];
             initDirectory[0] = 1;
             form.setWml320directory(initDirectory);
         }
         return __doDsp(map, form, req, res, con);
     }

    /**
      * <br>[機  能] 画面表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
     * @throws SQLException SQL実行時例外
      * @return ActionForward
      */
     private ActionForward __doDsp(ActionMapping map,
                                     Wml320Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws SQLException {

         BaseUserModel buMdl = getSessionUserModel(req);

         con.setAutoCommit(true);
         Wml320Biz biz = new Wml320Biz(con, getRequestModel(req));
         Wml320ParamModel paramMdl = new Wml320ParamModel();
         paramMdl.setParam(form);
         biz._setInitData(paramMdl, buMdl, getAppRootPath());
         paramMdl.setFormData(form);
         con.setAutoCommit(false);

         return map.getInputForward();
     }

     /**
      * <br>[機  能] 画面表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
     * @throws SQLException SQL実行時例外
      * @return ActionForward
      */
     private ActionForward __doBack(ActionMapping map,
                                     Wml320Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws SQLException {

         BaseUserModel buMdl = getSessionUserModel(req);

         boolean commit = false;
         try {
             Wml320Biz biz = new Wml320Biz(con, getRequestModel(req));
             Wml320ParamModel paramMdl = new Wml320ParamModel();
             paramMdl.setParam(form);
             biz._deleteWmlDelMail(paramMdl, buMdl);
             paramMdl.setFormData(form);
             con.commit();
             commit = true;
         } finally {
             if (!commit) {
                 JDBCUtil.rollback(con);
             }
         }
         con.setAutoCommit(false);

         return map.findForward("psnTool");
     }

     /**
      * <br>[機  能] 検索実行時処理
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行時例外
      * @return ActionForward
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
      */
     private ActionForward __doSearch(ActionMapping map, Wml320Form form,
             HttpServletRequest req, HttpServletResponse res, Connection con)
                     throws SQLException, IllegalAccessException,
                     InvocationTargetException, NoSuchMethodException {
         RequestModel reqMdl = getRequestModel(req);

         BaseUserModel buMdl = getSessionUserModel(req);

         //指定アカウントが削除実行中の場合、画面を再表示する
         Wml320Biz biz = new Wml320Biz(con, reqMdl);
         if (biz.getDeleteMailCount(form.getWml320account()) > 0) {
             return __doDsp(map, form, req, res, con);
         }

         //入力チェック
         ActionErrors errors = form.validateCheck(req, con, reqMdl, buMdl);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             form.setWml320searchFlg(false);
             return __doDsp(map, form, req, res, con);
         }
         Wml320ParamModel paramMdl = new Wml320ParamModel();
         paramMdl.setParam(form);
         biz._getSearchData(paramMdl, buMdl, getAppRootPath());
         paramMdl.setFormData(form);
         form.setWml320searchFlg(true);

         return __doDsp(map, form, req, res, con);
     }

     /**
      * <br>[機  能] 削除ボタン押下時処理
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行時例外
      * @return ActionForward
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
      */
     private ActionForward __doDelete(ActionMapping map, Wml320Form form,
             HttpServletRequest req, HttpServletResponse res, Connection con)
                     throws SQLException, IllegalAccessException,
                     InvocationTargetException, NoSuchMethodException {

         RequestModel reqMdl = getRequestModel(req);
         BaseUserModel buMdl = getSessionUserModel(req);

         //指定アカウントが削除実行中の場合、画面を再表示する
         Wml320Biz biz = new Wml320Biz(con, reqMdl);
         if (biz.getDeleteMailCount(form.getWml320svAccount()) > 0) {
             return __doDsp(map, form, req, res, con);
         }

         //入力チェック
         ActionErrors errors = form.validateCheck(req, con, reqMdl, buMdl);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             form.setWml320searchFlg(false);
             return __doDsp(map, form, req, res, con);
         }

         //検索未実行で削除ボタン押下
         if (!form.isWml320searchFlg()) {
             ActionErrors err = new ActionErrors();
             GsMessage gsMsg = new GsMessage();
             ActionMessage msg = new ActionMessage("search.data.notfound3",
                                                 gsMsg.getMessage("wml.wml320.2"));
             err.add("search.data.notfound3", msg);
             addErrors(req, err);
             form.setWml320searchFlg(false);
             return __doDsp(map, form, req, res, con);
         }

         //削除メール存在チェック
         WmlDelmailTempDao delTempDao = new WmlDelmailTempDao(con);
         int delMailCnt = delTempDao.getDataCount(buMdl.getUsrsid());
         if (delMailCnt <= 0) {
             ActionErrors err = new ActionErrors();
             ActionMessage msg = new ActionMessage("search.data.notfound2");
             err.add("search.data.notfound2", msg);
             addErrors(req, err);
             return __doDsp(map, form, req, res, con);
         }

         // トランザクショントークン設定
         saveToken(req);

         return map.findForward("confirm");
     }

     /**
      * <br>[機  能] 一括削除中メール件数の取得
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行時例外
      * @return ActionForward
      */
     private ActionForward __doGetDeleteMailCount(ActionMapping map, Wml320Form form,
             HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

         res.setContentType("text/json; charset=UTF-8");
         PrintWriter writer = null;
         con.setAutoCommit(true);
         try {
             RequestModel reqMdl = getRequestModel(req);
             int wacSid = form.getWml320account();

             StringBuilder sb = new StringBuilder("");
             sb.append("{");

             //メール件数
             Wml320Biz biz = new Wml320Biz(con, reqMdl);
             int count = biz.getDeleteMailCount(wacSid);
             sb.append("\"mailCount\":\"");
             sb.append(count);
             sb.append("\"");

             sb.append("}");

             writer = res.getWriter();
             writer.write(sb.toString());
             writer.flush();
         } catch (Exception e) {
             log__.error("一括削除中メール件数でエラー", e);
         } finally {
             if (writer != null) {
                 writer.close();
             }
             con.setAutoCommit(false);
         }

         return null;
     }

}
