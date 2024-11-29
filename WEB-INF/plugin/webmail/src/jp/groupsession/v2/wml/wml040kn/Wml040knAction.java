package jp.groupsession.v2.wml.wml040kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.AbstractWmlForm;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.wml040.Wml040Biz;

/**
 * <br>[機  能] WEBメール アカウント登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml040knAction extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml040knAction.class);

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
        Wml040knForm thisForm = (Wml040knForm) form;
        WmlAdmConfDao wacDao = new WmlAdmConfDao(con);
        int editKbn = wacDao.selectAdmData().getWadAcntMake();

        log__.debug("editKbn = " + editKbn);

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            if (thisForm.getWmlAccountMode() == GSConstWebmail.ACCOUNTMODE_COMMON) {
                return map.findForward("gf_power");
            } else if (editKbn == GSConstWebmail.KANRI_USER_ONLY) {
                //管理者権限のみアカウント設定が可能かどうか
                 __setAcntErrPageParam(map, req, thisForm);
                 return map.findForward("gf_msg");
            } else if (thisForm.getWmlCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
                //ユーザが編集可能なアカウントか
                int wacSid = thisForm.getWmlAccountSid();
                WmlBiz wmlBiz = new WmlBiz();
                if (!wmlBiz.canEditAccount(con, wacSid, getSessionUserSid(req))) {
                    __setAcntErrPageParam(map, req, thisForm);
                    return map.findForward("gf_msg");
                }
            }
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("connectTest")) {
            //接続テストボタンクリック
            forward = __doTestConnect(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("confAccount");

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
    public ActionForward __doInit(ActionMapping map, Wml040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Wml040knParamModel paramMdl = new Wml040knParamModel();
        paramMdl.setParam(form);
        Wml040knBiz biz = new Wml040knBiz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

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
     * @throws Exception 実行時例外
     */
    public ActionForward __doDecision(ActionMapping map, Wml040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheck(con, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        WmlAccountModel accountModel = null;
        boolean commit = false;
        try {
            Wml040knParamModel paramMdl = new Wml040knParamModel();
            paramMdl.setParam(form);

            Wml040knBiz biz = new Wml040knBiz();
            accountModel = biz.entryAccountData(con, paramMdl, getCountMtController(req),
                                                            reqMdl.getSmodel().getUsrsid(), reqMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("アカウント情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }

            //テンポラリディレクトリ削除
            Wml040Biz biz040 = new Wml040Biz();
            biz040.deleteTempDir(reqMdl);
        }

        String opCode = "";
        if (form.getWmlCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else {
            opCode = getInterMessage(req, "cmn.change");
        }
        if (accountModel != null) {
            //ログ出力
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.outPutLog(map, getRequestModel(req), con,
                    opCode, GSConstLog.LEVEL_INFO,
                    "[" + getInterMessage(req, "wml.96") + "]" + accountModel.getWacName()
                 + " [" + getInterMessage(req, "cmn.mailaddress") + "]"
                 + accountModel.getWacAddress());
        }

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 接続テストボタンクリック時処理
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
    public ActionForward __doTestConnect(ActionMapping map, Wml040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String appRootPath = getAppRootPath();
        RequestModel reqMdl = getRequestModel(req);

        //受信サーバチェック
        if (!form.checkReceiveConnect(con, appRootPath, reqMdl)) {
            msg = new ActionMessage("error.connect.failed.mailserver",
                    getInterMessage(req, "wml.154"),
                    getInterMessage(req, "wml.154"));
            StrutsUtil.addMessage(errors, msg, "receive.error.connect.failed.mailserver");
        }

        //送信サーバチェック
        if (!form.checkSendConnect(con, appRootPath, reqMdl)) {
            msg = new ActionMessage("error.connect.failed.mailserver",
                    getInterMessage(req, "wml.80"),
                    getInterMessage(req, "wml.80"));
            StrutsUtil.addMessage(errors, msg, "send.error.connect.failed.mailserver");
        }

        if (errors.isEmpty()) {
            msg = new ActionMessage("success.connect.mailserver");
            StrutsUtil.addMessage(errors, msg, "success.connect.mailserver");
        }

        addErrors(req, errors);
        return __doInit(map, form, req, res, con);
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
        Wml040knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("userAccountList");
        if (form.getWmlAccountMode() == GSConstWebmail.WAC_TYPE_USER) {
            urlForward = map.findForward("accountManager");
        }

        ((AbstractWmlForm) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getWmlCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getWmlCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "wml.102")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] アカウント登録権限エラー画面遷移時のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setAcntErrPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Wml040knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("mailList");

        ((AbstractWmlForm) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "add.touroku.wmluser";

        cmn999Form.setMessage(msgRes.getMessage(msgState));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}
