package jp.groupsession.v2.wml.wml100;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;

/**
 * <br>[機  能] WEBメール アカウントの管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml100Action extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml100Action.class);

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
        Wml100Form thisForm = (Wml100Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confAccount")) {
            //追加ボタン
            forward = map.findForward("confAccount");

        } else if (cmd.equals("psnTool")) {
            //戻るボタンクリック
            if (thisForm.getWmlAccountMode() == GSConstWebmail.ACCOUNTMODE_PSNLSETTING) {
                log__.debug("アカウント処理モード 個人設定");
                forward = map.findForward("psnTool");

            } else {
                log__.debug("アカウント処理モード 通常");
                forward = map.findForward("mailList");
            }

        } else if (cmd.equals("editAccount")) {
            //編集ボタンクリック
            forward = __doEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("confMailTemplate")) {
            //「テンプレート」ボタンクリック
            forward = map.findForward("mailTemplateConf");

        } else if (cmd.equals("upFilterData")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConstWebmail.SORT_UP);

        } else if (cmd.equals("downFilterData")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConstWebmail.SORT_DOWN);

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
    public ActionForward __doInit(ActionMapping map, Wml100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Wml100Biz biz = new Wml100Biz();

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        //管理者ユーザかどうか
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        form.setWml010adminUser(adminUser);

        Wml100ParamModel paramMdl = new Wml100ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(con, paramMdl, userSid);
        paramMdl.setFormData(form);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Wml100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;
        Wml100Biz biz = new Wml100Biz();
        boolean updateFlg = false;

        try {

            Wml100ParamModel paramMdl = new Wml100ParamModel();
            paramMdl.setParam(form);
            updateFlg = biz.updateSort(con, paramMdl, getSessionUserSid(req), changeKbn);
            paramMdl.setFormData(form);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        if (updateFlg) {
            //ログ出力
            WmlBiz wmlBiz = new WmlBiz();

            GsMessage gsMsg = new GsMessage(req);
            String msg = "";
            String sort = "";
            String[] selectKeyList = form.getWml100sortAccount().split(":");
            String targetName = biz.getAccountName(con, Integer.parseInt(selectKeyList[0]));
            if (changeKbn == 0) {
                sort = gsMsg.getMessage("cmn.up");
            } else {
                sort = gsMsg.getMessage("cmn.down");
            }
            msg += "[" + gsMsg.getMessage("cmn.target") + "]" + targetName
                    + "\r\n[" + gsMsg.getMessage("change.sort.order") + "]" + sort;
            wmlBiz.outPutLog(map, getRequestModel(req), con,
                    getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO,
                    msg);
        }
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 編集ボタンクリック時の処理を行う
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
    private ActionForward __doEdit(
        ActionMapping map,
        Wml100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = map.findForward("confAccount");

        BaseUserModel usModel = getSessionUserModel(req);
        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(con, usModel, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        if (!adminUser) {

            WmlAdmConfDao wacDao = new WmlAdmConfDao(con);
            WmlAdmConfModel wacMdl = wacDao.selectAdmData();

            if (wacMdl == null || wacMdl.getWadAcntMake() == GSConstWebmail.KANRI_USER_ONLY) {
                forward = map.findForward("editAccount");
            }
        }

        return forward;
    }
}
