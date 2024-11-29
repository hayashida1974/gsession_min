package jp.groupsession.v2.sml.sml110;

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
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 管理者設定 転送設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml110Action extends AbstractSmailAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml110Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;
        Sml110Form schForm = (Sml110Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("sml110kakunin")) {
            //確認
            forward = __doKakunin(map, schForm, req, res, con);
        } else if (cmd.equals("sml110commit")) {
            //登録
            forward = __doCommit(map, schForm, req, res, con);
        } else if (cmd.equals("sml110back")) {
            //戻るボタンクリック
            forward = __doBack(map, schForm, req, res, con);
        } else if (cmd.equals("sml110lmtCheck")) {
            //チェックするボタンクリック
            forward = __doFwCheck(map, schForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, schForm, req, res, con);
        }
        return forward;
    }
    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sml110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        //初期値をセット
        Sml110ParamModel paramMdl = new Sml110ParamModel();
        paramMdl.setParam(form);

        Sml110Biz biz = new Sml110Biz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Sml110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("backToMenu");
    }
    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Sml110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("確認処理");
        //入力チェック
        ActionErrors errors = form.validateCheck(map, req, con);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        log__.debug("入力エラーなし");
        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninPageParam(map, req, form, con);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Sml110Form form,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "sml.120");
        String tensouNg = gsMsg.getMessage(req, "sml.129");
        String tensouOk = gsMsg.getMessage(req, "sml.130");

        //メッセージセット
        String msgState = "setting.kakunin.data";
        String mkey1 = msg;
        String mkey2 = null;
        String oauthMsg = "";
        if (form.getSml110MailForward().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_NG))) {
            mkey2 = tensouNg;
        } else {
            int authMethod = NullDefault.getInt(form.getSml110authMethod(), GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL); 
            if (authMethod == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH
                    && form.getSml110oauthCompFlg() == GSConstSmail.AUTH_YET) {
                OAuthBiz oatBiz = new OAuthBiz();
                
                int cotSid = oatBiz.checkOAuthToken(con, form.getSml110provider(),
                        form.getSml110FromAddress(), true);
                
                if (cotSid > 0) {
                    oauthMsg = gsMsg.getMessage("cmn.auth.already.message");
                    oauthMsg += "<br>";
                }
            }
            mkey2 = tensouOk;
        }
        cmn999Form.setMessage(oauthMsg.concat(msgRes.getMessage(msgState, mkey1, mkey2)));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("sml110MailForward", form.getSml110MailForward());
        cmn999Form.addHiddenParam("sml110SmtpUrl", form.getSml110SmtpUrl());
        cmn999Form.addHiddenParam("sml110SmtpPort", form.getSml110SmtpPort());
        cmn999Form.addHiddenParam("sml110SmtpUser", form.getSml110SmtpUser());
        cmn999Form.addHiddenParam("sml110SmtpPass", form.getSml110SmtpPass());
        cmn999Form.addHiddenParam("sml110FromAddress", form.getSml110FromAddress());
        cmn999Form.addHiddenParam("sml110FwLmtKbn", form.getSml110FwLmtKbn());
        cmn999Form.addHiddenParam("sml110FwlmtTextArea", form.getSml110FwlmtTextArea());
        cmn999Form.addHiddenParam("sml110SmtpEncrypt", form.getSml110SmtpEncrypt());
        cmn999Form.addHiddenParam("sml110authMethod", form.getSml110authMethod());
        cmn999Form.addHiddenParam("sml110provider", form.getSml110provider());
        cmn999Form.addHiddenParam("sml110FromAddress", form.getSml110FromAddress());
        cmn999Form.addHiddenParam("sml110cotSid", form.getSml110cotSid());
        cmn999Form.addHiddenParam("sml110InitFlg", form.getSml110InitFlg());
        
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Sml110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheck(map, req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        
        //DB更新
        Sml110ParamModel paramMdl = new Sml110ParamModel();
        paramMdl.setParam(form);
        
        Sml110Biz biz = new Sml110Biz();
        MlCountMtController mtCon = getCountMtController(req);
        BaseUserModel umodel = getSessionUserModel(req);
        biz._setSmlOauthData(paramMdl, umodel, con, mtCon);
        
        biz.setSmlConfData(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        String cstLog = gsMsg.getMessage(req, "cmn.edit");

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        String msg = __getLogMessage(form, req, con);
        smlBiz.outPutLog(map, reqMdl,
                         cstLog, GSConstLog.LEVEL_INFO, msg);

        //共通メッセージ画面(OK キャンセル)を表示
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 転送先不正チェックを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doFwCheck(ActionMapping map, Sml110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        ActionErrors errors = new ActionErrors();
        form.validateFwMail(errors, req);

        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Sml110ParamModel paramMdl = new Sml110ParamModel();
        paramMdl.setParam(form);
        Sml110Biz biz = new Sml110Biz();
        biz.setCheckList(paramMdl, con);
        paramMdl.setFormData(form);

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
        Sml110Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backToMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "sml.20");

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        String key1 = msg;
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());

        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Sml110Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";

        //転送設定
        msg += "[" + gsMsg.getMessage("sml.80") + "]";
        if (form.getSml110MailForward().equals(GSConstSmail.MAIL_FORWARD_OFF)) {
            msg += gsMsg.getMessage("sml.78");
        } else if (form.getSml110MailForward().equals(GSConstSmail.MAIL_FORWARD_ON)) {
            msg += gsMsg.getMessage("sml.79");
            
            //認証形式
            int authMethod = NullDefault.getInt(
                    form.getSml110authMethod(), GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL);
            
            if (authMethod == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
                //SMTPサーバ
                msg += "\r\n[" + gsMsg.getMessage("sml.sml110.06") + "]";
                msg += gsMsg.getMessage("sml.sml110.07") + " : " + form.getSml110SmtpUrl();
                msg += "\r\n" + gsMsg.getMessage("sml.sml110.08") + " : " + form.getSml110SmtpPort();
                if (form.getSml110SmtpEncrypt() > 0) {
                    msg += "\r\n" + gsMsg.getMessage("wml.296");
                }
                msg += "\r\n" + gsMsg.getMessage("sml.sml110.17") + " : "
                           + form.getSml110FromAddress();
                msg += "\r\n" + gsMsg.getMessage("sml.sml110.22") + " : " + form.getSml110SmtpUser();
                msg += "\r\n" + gsMsg.getMessage("sml.sml110.21") + " : ";
                
            } else {
                msg += "\r\n[" + gsMsg.getMessage("cmn.auth.provider") + "]";
                OAuthBiz oatBiz = new OAuthBiz();
                OauthDataModel oatMdl = oatBiz.getAuthData(con, form.getSml110cotSid());
                if (oatMdl.getProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
                    msg += gsMsg.getMessage("cmn.cmn260.02");
                } else if (oatMdl.getProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
                    msg += gsMsg.getMessage("cmn.cmn260.03");
                }
                
                msg += "\r\n[" + gsMsg.getMessage("sml.sml110.17") + "]";
                msg += form.getSml110FromAddress();
            }
            
            msg += "\r\n[" + gsMsg.getMessage("sml.sml110.20") + "]";
            if (form.getSml110FwLmtKbn().equals(GSConstSmail.MAIL_FORWARD_LIMIT_OFF)) {
                msg += gsMsg.getMessage("cmn.not.limit");
            } else if (form.getSml110FwLmtKbn().equals(GSConstSmail.MAIL_FORWARD_LIMIT_ON)) {
                msg += gsMsg.getMessage("cmn.do.limit");
                msg += "\r\n" + form.getSml110FwlmtTextArea();
            }
        }

        return msg;
    }
}
