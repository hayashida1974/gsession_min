package jp.groupsession.v2.wml.wml240;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;

/**
 * <br>[機  能] WEBメール メールテンプレート管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml240Action extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml240Action.class);

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

        //アクセス可能かをチェック
        Wml240Form thisForm = (Wml240Form) form;
        if (!_canAccess(con, req, thisForm)) {
            return getAuthErrorPage(map, req);
        }

        ActionForward forward = null;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("configTemplate")) {
            //追加ボタン、修正ボタンクリック
            forward = __doConfig(map, thisForm, req, res, con);

        } else if (cmd.equals("wml240Back")) {
            //戻るボタンクリック
            if (thisForm.getWmlMailTemplateKbn() == GSConstWebmail.MAILTEMPLATE_COMMON) {
                forward = map.findForward("admTool");
            } else {
                if (thisForm.getWmlAccountMode() == GSConstWebmail.WAC_TYPE_USER) {
                    forward = map.findForward("accountManager");
                } else {
                    forward = map.findForward("userAccountList");
                }
            }

        } else if (cmd.equals("deleteTemplate")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteTemplateComp")) {
            //削除確認画面からの遷移
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("upTemplateData")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConstWebmail.SORT_UP);

        } else if (cmd.equals("downTemplateData")) {
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
    private ActionForward __doInit(ActionMapping map, Wml240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        Wml240ParamModel paramMdl = new Wml240ParamModel();
        paramMdl.setParam(form);
        Wml240Biz biz = new Wml240Biz();
        biz.setInitData(con, paramMdl, userSid, getRequestModel(req));
        paramMdl.setFormData(form);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 追加ボタン、修正ボタンクリック時処理
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
    public ActionForward __doConfig(ActionMapping map, Wml240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //アカウント選択チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("confTemplate");
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Wml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
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
        Wml240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;

        //ログ用 退避
        WmlMailTemplateDao wlDao = new WmlMailTemplateDao(con);
        WmlMailTemplateModel delTempMdl = wlDao.select(form.getWmlEditTemplateId());

        try {
            //ラベルを削除する
            Wml240ParamModel paramMdl = new Wml240ParamModel();
            paramMdl.setParam(form);
            Wml240Biz biz = new Wml240Biz();
            biz.deleteTemplate(con, paramMdl, getSessionUserSid(req));
            paramMdl.setFormData(form);

            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        WmlBiz wmlBiz = new WmlBiz();
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String accountName = "";
        // アカウント名
        if (form.getWmlAccountSid() == GSConstWebmail.WML_ACCOUNT_COMMON) {
            accountName = gsMsg.getMessage("cmn.common");
        } else {
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountMdl = accountDao.select(form.getWmlAccountSid());
            accountName = accountMdl.getWacName();
        }

        //ログ出力
        String msg = "[" + gsMsg.getMessage("wml.96") + "] " + accountName;
        msg += "\r\n[" + gsMsg.getMessage("wml.wml240.01") + "]" + delTempMdl.getWtpName();
        wmlBiz.outPutLog(map, getRequestModel(req), con,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                msg);

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Wml240Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forward = map.findForward("mine");
        cmn999Form.setUrlCancel(forward.getPath());

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteTemplateComp");

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = gsMsg.getMessage("cmn.confirm.msg.delete",
                                                new String[] {gsMsg.getMessage("anp.anp090.03")});
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("wml240SortRadio", form.getWml240SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());
        cmn999Form.addHiddenParam("wmlEditTemplateId", form.getWmlEditTemplateId());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
        Wml240Form form,
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
        Wml240Biz biz = new Wml240Biz();
        boolean updateFlg = false;
        try {

            Wml240ParamModel paramMdl = new Wml240ParamModel();
            paramMdl.setParam(form);
            updateFlg = biz.updateSort(con, paramMdl, changeKbn, getSessionUserSid(req));
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
            String accountName = gsMsg.getMessage("cmn.common");
            if (form.getWmlAccountSid() != 0) {
                accountName = biz.getAccountName(con, form.getWmlAccountSid());
            }
            String targetName = biz.getTempName(con, form.getWml240SortRadio());
            if (changeKbn == 0) {
                sort = gsMsg.getMessage("cmn.up");
            } else {
                sort = gsMsg.getMessage("cmn.down");
            }
            msg += "[" + gsMsg.getMessage("wml.96") + "]" + accountName
                    + "\r\n[" + gsMsg.getMessage("cmn.target") + "]" + targetName
                    + "\r\n[" + gsMsg.getMessage("change.sort.order") + "]" + sort;

            wmlBiz.outPutLog(map, getRequestModel(req), con,
                    getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, msg);
        }
        return __doInit(map, form, req, res, con);
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
        Wml240Form form,
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
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", getInterMessage(req, "anp.anp090.03")));

        form.setHiddenParam(cmn999Form);

        cmn999Form.addHiddenParam("wml240SortRadio", form.getWml240SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 使用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param form フォーム
     * @return true:許可する,false:許可しない
     * @throws SQLException SQL実行時例外
     */
    protected boolean _canAccess(Connection con, HttpServletRequest req, Wml240Form form)
    throws SQLException {
        boolean result = false;

        int templateKbn = form.getWmlMailTemplateKbn();
        if (templateKbn == GSConstWebmail.MAILTEMPLATE_COMMON
        || form.getWmlAccountMode() == GSConstWebmail.WAC_TYPE_USER) {
            BaseUserModel smodel = getSessionUserModel(req);
            if (smodel != null) {
                CommonBiz cmnBiz = new CommonBiz();
                result = cmnBiz.isPluginAdmin(con, smodel, getPluginId());
            }
        } else {
            int wacSid = form.getWmlAccountSid();
            WmlDao wmlDao = new WmlDao(con);
            result = wmlDao.canUseAccount(wacSid, getSessionUserSid(req));
       }

        return result;
    }
}
