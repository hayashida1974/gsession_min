package jp.groupsession.v2.wml.wml320kn;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.AbstractWebmailAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlDelmailTempDao;
import jp.groupsession.v2.wml.wml320.Wml320Biz;

/**
 * <br>[機  能] WEBメール 個人設定 メール情報一括削除確認画面アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Wml320knAction extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml320knAction.class);

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
     * @throws GSException オペレーションログ出力時 例外発生
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
                                         Connection con) throws SQLException, GSException {
         log__.debug("START");

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         log__.debug("CMD = " + cmd);

         ActionForward forward = null;
         Wml320knForm thisForm = (Wml320knForm) form;

         //パラメータチェック
         Wml320knParamModel paramMdl = new Wml320knParamModel();
         paramMdl.setParam(form);
         Wml320knBiz biz = new Wml320knBiz(con);
         boolean result = biz.checkParameter(paramMdl,
                                             getSessionUserModel(req),
                                             getRequestModel(req));
         if (!result) {
             return getSubmitErrorPage(map, req);
         }

         if (cmd.equals("decision")) {
             //確定ボタンクリック
             forward = __doDecision(map, thisForm, req, res, con);

         } else if (cmd.equals("backInput")) {
             //戻るボタンクリック
             forward = map.findForward("backInput");

         } else {
             log__.debug("初期表示");
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
      * @throws SQLException SQL実行時例外
      */
     private ActionForward __doInit(ActionMapping map, Wml320knForm form,
             HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
         BaseUserModel buMdl = getSessionUserModel(req);
         con.setAutoCommit(true);

         Wml320knParamModel paramMdl = new Wml320knParamModel();
         paramMdl.setParam(form);
         Wml320knBiz biz = new Wml320knBiz(con);
         biz._setInitData(paramMdl, buMdl, getRequestModel(req));

         paramMdl.setFormData(form);
         con.setAutoCommit(false);
         return map.getInputForward();
     }

     /**
      * <br>[機  能] 確定ボタンクリック時処理
      * <br>[解  説]
      * <br>[備  考]
      * @param map ActionMapping
      * @param form フォーム
      * @param req HttpServletRequest
      * @param res HttpServletResponse
      * @param con DB Connection
      * @return ActionForward
      * @throws SQLException SQL実行時例外
      * @throws GSException オペレーションログ出力時に例外発生
      */
     private ActionForward __doDecision(ActionMapping map, Wml320knForm form,
             HttpServletRequest req, HttpServletResponse res, Connection con)
     throws SQLException, GSException {

         if (!isTokenValid(req, true)) {
             log__.info("２重投稿");
             return getSubmitErrorPage(map, req);
         }

         BaseUserModel buMdl = getSessionUserModel(req);
         RequestModel reqMdl = getRequestModel(req);

         //入力チェック
         ActionErrors errors = form.validateCheck(req, con, reqMdl, buMdl);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             return __doInit(map, form, req, res, con);
         }

         //削除メール存在チェック
         Wml320knParamModel paramMdl = new Wml320knParamModel();
         paramMdl.setParam(form);
         WmlDelmailTempDao delTempDao = new WmlDelmailTempDao(con);
         int delMailCnt = delTempDao.getDataCount(buMdl.getUsrsid());
         if (delMailCnt <= 0) {
             return getSubmitErrorPage(map, req);
         }

         //指定アカウントが削除実行中の場合、画面を再表示する
         Wml320Biz biz320 = new Wml320Biz(con, reqMdl);
         if (biz320.getDeleteMailCount(form.getWml320svAccount()) > 0) {
             saveToken(req);
             return __doInit(map, form, req, res, con);
         }

         //ログ出力
         Wml320knBiz biz = new Wml320knBiz(con);
         String msg = biz._getLogMessage(getRequestModel(req), paramMdl, buMdl);
         String opCode = getInterMessage(req, "cmn.delete");

         WmlBiz wmlBiz = new WmlBiz();
         wmlBiz.outPutLog(map, getRequestModel(req), con,
                 opCode, GSConstLog.LEVEL_INFO,
                 msg);

         //一括削除対象メールを登録する
         boolean commit = false;
         try {
             biz.entryDelmailList(reqMdl, paramMdl, buMdl);
             con.commit();
             commit = true;
         } finally {
             if (!commit) {
                 JDBCUtil.rollback(con);
             }
         }

         //メール情報一括削除処理スレッド 開始
         WmlDeleteMailThread.startThread(
                 reqMdl.getDomain(),
                 paramMdl.getWml320svAccount(),
                 getAppRootPath(),
                 WmlDeleteMailThread.STARTSTATUS_START);

         __setCompPageParam(map, req, form);

         return map.findForward("gf_msg");
     }

     /**
      * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
      * <br>[解  説]
      * <br>[備  考]
      * @param map マッピング
      * @param req リクエスト
      * @param form アクションフォーム
      */
     private void __setCompPageParam(
         ActionMapping map,
         HttpServletRequest req,
         Wml320knForm form) {

         Cmn999Form cmn999Form = new Cmn999Form();
         cmn999Form.setType(Cmn999Form.TYPE_OK);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

         //OKボタンクリック時遷移先
         ActionForward forwardOk = map.findForward("backInput");
         cmn999Form.setUrlOK(forwardOk.getPath());

         MessageResources msgRes = getResources(req);
         //削除完了
         cmn999Form.setMessage(
                 msgRes.getMessage("sakujo.kanryo.multiple.mail"));

         //画面パラメータをセット
         form.setHiddenParam(cmn999Form);

         req.setAttribute("cmn999Form", cmn999Form);
     }
}
