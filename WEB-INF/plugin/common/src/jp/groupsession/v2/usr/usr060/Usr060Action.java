package jp.groupsession.v2.usr.usr060;

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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.biz.GSLoginBiz;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**mh
 *
 * <br>[機  能] ワンタイムパスワード通知アドレス変更画面 アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr060Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr060Action.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        Usr060Form thisForm = (Usr060Form) form;
        //アクセス権限チェック
        forward = __doImmigration(map, thisForm, req, con);
        if (forward != null) {
            return forward;
        }

        //前提設定
        __doPrepareSetting(thisForm, req, con);

        //初回表示
        if (cmd.equals("otp_sendto_address")
            || cmd.equals("login")) {
            forward = __doInit(map, thisForm, req, res, con);
        //戻る
        } else if (cmd.equals("usr060Back")) {
            Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
            String backForwardStr = biz.getBackForwardStr(isSession(req));
            forward = map.findForward(backForwardStr);
        //OK
        } else if (cmd.equals("usr060Ok")) {
            forward = __doOk(map, thisForm, req, res, con);
        //確定
        } else if (cmd.equals("usr060kakutei")) {
            forward = __doComplete(map, thisForm, req, res, con);
        //再描画
        } else {
            //確認パスワード送信
            if (!StringUtil.isNullZeroString(thisForm.getUsr060SendOtp())) {
                forward = __doSendPass(map, thisForm, req, res, con);
            //再入力ボタンクリック
            } else  if (!StringUtil.isNullZeroString(thisForm.getUsr060Reenter())) {
                forward = __doReenter(map, thisForm, req, res, con);
            } else {
                forward = __doDsp(map, thisForm, req, res, con);
            }
        }
        return forward;
    }
    /**
     *
     * <br>[機  能] 登録確定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doComplete(ActionMapping map, Usr060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);
        Usr060Biz biz = new Usr060Biz(reqMdl, con);
        boolean commit = false;
        BaseUserModel smodel = null;

        try {
            ActionErrors errors = form.validatePass(reqMdl);
            if (errors.size() > 0) {
                addErrors(req, errors);
                return __doDsp(map, form, req, res, con);
            }
            Usr060ParamModel param = new Usr060ParamModel();
            param.setParam(form);
            LoginResultModel result = biz.checkAuth(param);
            //ワンタイムパスワードが間違っている
            if (result.getErrCode() == LoginResultModel.ECODE_MISS_OTPASS) {
                addErrors(req, result.getErrors());
                return __doDsp(map, form, req, res, con);
            }
            if (result.getErrCode() != LoginResultModel.ECODE_NONE) {
                return getSubmitErrorPage(map, req);
            }
            if (!isTokenValid(req, true)) {
                return getSubmitErrorPage(map, req);
            }
            smodel = result.getLoginUser();
            reqMdl.setSmodel(smodel);

            biz.doUpdateAddress(param, smodel);

            param.setFormData(param);

            con.commit();


            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ 変更 **/
            String change = gsMsg.getMessage("cmn.change");

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    change, GSConstLog.LEVEL_INFO,
                    gsMsg.getMessage("user.157") + ":" + param.getUsr060SendToAddress()
                    );
            con.commit();
            commit = true;

        } finally {
            if (!commit) {
                con.rollback();
            }
            con.setAutoCommit(true);
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        String backForwardStr = biz.getBackForwardStr(isSession(req));
        ActionForward urlForward = map.findForward(backForwardStr);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage(req, "user.157");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("hensyu.henkou.kanryo.object", textPassWord));

        cmn999Form.addHiddenAll(form, form.getClass(), "");

        if (!isSession(req)) {
            //ログイン画面から遷移時
            PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);

            GSLoginBiz cmnLoginBiz = new GSLoginBiz();
            //ログイン共通処理
            cmnLoginBiz.doLogin(req, smodel,  con, pconfig);
            //ログイン後遷移先設定
            ILogin loginBiz = _getLoginInstance();
            String forwardName = loginBiz.checkForwordPassowrd(smodel, con);
            ActionForward forward = map.findForward(forwardName);
            cmn999Form.setUrlOK(forward.getPath());
            cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        }

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");

    }
    /**
    *
    * <br>[機  能] アドレス再入力受付処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
    private ActionForward __doReenter(ActionMapping map, Usr060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        Usr060ParamModel param = new Usr060ParamModel();
        param.setParam(form);
        Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
        biz.doReenter(param);

        param.setFormData(form);

        return __doDsp(map, form, req, res, con);
    }
    /**
    *
    * <br>[機  能] ワンタイムパスワード送信を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
    private ActionForward __doSendPass(ActionMapping map, Usr060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        boolean commit = false;
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateAddress(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }
        try {
            Usr060ParamModel param = new Usr060ParamModel();
            param.setParam(form);
            Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
            OtpSendResult result = biz.doSendPass(param);
            if (result.getEcode() != OtpSendResult.ECODE_NONE) {
                ActionMessage msg = new ActionMessage("error.smtp.otppass.send");
                StrutsUtil.addMessage(errors, msg, "usr060address");
                addErrors(req, errors);
                return __doDsp(map, form, req, res, con);
            }
           con.commit();
           commit = true;
           param.setFormData(form);
           return __doDsp(map, form, req, res, con);
        } finally {
            if (!commit) {
                con.rollback();
            }
            con.setAutoCommit(true);
        }
    }
    /**
     *
     * <br>[機  能] 描画設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doDsp(ActionMapping map, Usr060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        Usr060ParamModel param = new Usr060ParamModel();
        param.setParam(form);
        Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
        biz.doDsp(param);

        param.setFormData(form);
        saveToken(req);
        return map.getInputForward();
    }
    /**
     *
     * <br>[機  能] 初回アクセス時 初期化処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Usr060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        Usr060ParamModel param = new Usr060ParamModel();
        param.setParam(form);
        Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
        biz.doInit(param, false);
        param.setFormData(form);
        return __doDsp(map, form, req, res, con);
    }
    /**
     *
     * <br>[機  能] OKボタンクリック処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doOk(ActionMapping map, Usr060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validatePass(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        Usr060ParamModel param = new Usr060ParamModel();
        param.setParam(form);
        Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
        LoginResultModel result = biz.checkAuth(param);
        //ワンタイムパスワードが間違っている
        if (result.getErrCode() == LoginResultModel.ECODE_MISS_OTPASS) {
            addErrors(req, result.getErrors());
            return __doDsp(map, form, req, res, con);
        }
        if (result.getErrCode() != LoginResultModel.ECODE_NONE) {
            return getSubmitErrorPage(map, req);
        }

        param.setFormData(form);
        __doDsp(map, form, req, res, con);
        //共通確認画面遷移
        return __setConfirmationDsp(map, req, form);
    }
    /**
     *
     * <br>[機  能] 確認画面遷移
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return 画面遷移
     * @throws Exception 実行時例外
     */
    private ActionForward __setConfirmationDsp(ActionMapping map,
            HttpServletRequest req, Usr060Form form) throws Exception {
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);


        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("kakutei");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCanc = map.findForward("dsp");
        cmn999Form.setUrlCancel(forwardCanc.getPath());

        if (!isSession(req)) {
            //ログイン画面から遷移時
            cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        }

        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage(req, "user.157");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("setting.kakunin.data",
                     textPassWord, form.getUsr060SendToAddress()));

        cmn999Form.addHiddenAll(form, form.getClass(), "");

        req.setAttribute("cmn999Form", cmn999Form);
        saveToken(req);
        return map.findForward("gf_msg");
    }
    /**
     *
     * <br>[機  能] 画面事前設定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doPrepareSetting(IUsr060Param form, HttpServletRequest req,
            Connection con) throws Exception {
        Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
        biz.doPrepareSetting(form);
    }
    /**
     *
     * <br>[機  能] アクセス権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return エラー時遷移情報
     * @throws Exception 実行時例外
     */
    private ActionForward __doImmigration(ActionMapping map, IUsr060Param form,
            HttpServletRequest req,
            Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        Usr060Biz biz = new Usr060Biz(reqMdl, con);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));

        OnetimePasswordBiz passBiz = new OnetimePasswordBiz();
        //ワンタイムパスワード使用しない時は不正アクセス
        CmnOtpConfModel otpConf = passBiz.getOtpConf(con);
        if (otpConf.getCocUseOtp() == GSConstMain.OTP_NOUSE) {
            return getSubmitErrorPage(map, req);
        }
        // 個人情報修正区分、パスワード修正区分取得
        MainCommonBiz manCmnBiz = new MainCommonBiz();
        CmnPconfEditModel pconfEditMdl = new CmnPconfEditModel();
        pconfEditMdl = manCmnBiz.getCpeConf(0, con);
        if (pconfEditMdl.getCpeOtpsendKbn() == GSConstMain.PCONF_EDIT_ADM) {
            //設定権限がない
            ActionForward forward;
            String backForwardStr = biz.getBackForwardStr(isSession(req));
            ActionForward urlForward = map.findForward(backForwardStr);
            Cmn999Form cmn999Form = new Cmn999Form();
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setUrlOK(urlForward.getPath());
            req.setAttribute("cmn999Form", cmn999Form);
            MessageResources msgRes = getResources(req);
            String textDelWrite = gsMsg.getMessage("user.usr060.1");
            String textDel = gsMsg.getMessage("cmn.edit");

            //メッセージセット
            String msgState = "error.edit.power.user";
            cmn999Form.setMessage(msgRes.getMessage(msgState,
                    textDelWrite,
                    textDel));
            forward = map.findForward("gf_msg");
            return forward;

        }

        if (form.isUsr060otpSended()
                || !StringUtil.isNullZeroString(form.getUsr060Token())) {
            LoginResultModel result = biz.checkTokenLive(form.getUsr060Token(), reqMdl.getSmodel());
            if (result.getErrCode() == LoginResultModel.ECODE_TOKENNONE) {
                return getSubmitErrorPage(map, req);
            }
            if (result.getErrCode() == LoginResultModel.ECODE_USERNONE) {
                return getSubmitErrorPage(map, req);
            }
            if (result.getErrCode() == LoginResultModel.ECODE_TOKENTIMEOVER) {
                //一次トークンの有効期限切れ
                ActionForward forward;
                String backForwardStr = biz.getBackForwardStr(isSession(req));
                ActionForward urlForward = map.findForward(backForwardStr);
                Cmn999Form cmn999Form = new Cmn999Form();
                cmn999Form.setIcon(Cmn999Form.ICON_WARN);
                cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
                cmn999Form.setType(Cmn999Form.TYPE_OK);
                cmn999Form.setUrlOK(urlForward.getPath());
                req.setAttribute("cmn999Form", cmn999Form);
                cmn999Form.setMessage(gsMsg.getMessage("cmn.cmn004.error.limitover"));
                forward = map.findForward("gf_msg");
                return forward;
            }
        }
        return null;
    }
    @Override
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {

        Usr060Form thisForm = (Usr060Form) form;
        //トークン情報取得済み
        if (!StringUtil.isNullZeroString(thisForm.getUsr060Token())) {
            return true;
        }
        return false;
    }
    @Override
    public ActionForward getSubmitErrorPage(ActionMapping map,
            HttpServletRequest req) {
        if (!isSession(req)) {
            Cmn999Form cmn999Form = new Cmn999Form();
            ActionForward urlForward = null;

            //２重投稿エラー警告画面パラメータの設定
            MessageResources msgRes = getResources(req);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

            urlForward = map.findForward("gf_domain_logout");
            cmn999Form.setUrlOK(urlForward.getPath());
            cmn999Form.setMessage(msgRes.getMessage("error.access.double.submit"));
            req.setAttribute("cmn999Form", cmn999Form);

            urlForward = map.findForward("gf_msg");
            return urlForward;
        } else {
            return super.getSubmitErrorPage(map, req);
        }
    }

}
