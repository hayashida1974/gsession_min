package jp.groupsession.v2.usr.usr050;

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
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 個人設定 パスワード変更画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr050Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr050Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return !(form instanceof Usr050Form)
                || ((Usr050Form) form).getBackScreen() != GSConstMain.BACK_MAIN_ADM_SETTING;
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

        //パスワード変更 = 許可しない の場合、エラーページへ遷移する
        if (!canChangePassword(con, getSessionUserSid(req))
        && !__checkSystemUser(getSessionUserModel(req))) {
            throw new Exception("パスワード変更は許可されていません。");
        }

        ActionForward forward = null;
        Usr050Form usr050Form = (Usr050Form) form;

        //個人情報編集値取得
        MainCommonBiz manCmnBiz = new MainCommonBiz();
        CmnPconfEditModel cmnPconfEditMdl = new CmnPconfEditModel();
        cmnPconfEditMdl = manCmnBiz.getCpeConf(GSConst.SYSTEM_USER_ADMIN, con);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        CmnUsrmDao uDao = new CmnUsrmDao(con);
        int usrSid = getSessionUserModel(req).getUsrsid();
        CmnUsrmModel uMdl = uDao.select(usrSid);

        //個人情報パスワード編集区分 = 「管理者が設定する 」かつ「次回ログイン時変更する」以外かつ adminユーザ以外の時
        if (cmnPconfEditMdl.getCpePasswordKbn() == GSConstMain.PASSWORD_EDIT_ADM
                && uMdl.getUsrPswdKbn() != GSConstUser.PSWD_UPDATE_ON
                && usrSid != GSConst.SYSTEM_USER_ADMIN) {

            if (usr050Form.getBackScreen() != GSConstMain.BACK_MAIN_ADM_SETTING) {
                //共通メッセージ画面へ遷移
                return __setCompPageParam(map, req, usr050Form);
            }
        }

        if (cmd.equals("backToMenu")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, usr050Form, req, res, con);

        } else if (cmd.equals("ok")) {
            log__.debug("OKボタン押下");
            forward = __doEdit(map, usr050Form, req, res, con);
        } else if (cmd.equals("editOk")) {
            log__.debug("確認画面でOKボタン押下");
            forward = __doEditOk(map, usr050Form, req, res, con);

        } else if (cmd.equals("editCancel")) {
            log__.debug("確認画面でキャンセルボタン押下");
            forward = __doEditCancel(map, usr050Form, req, res, con);
        } else if (cmd.equals("pswdUpdate")) {
            log__.debug("初期表示");
            usr050Form.setUsr050Mode(GSConstUser.PSWD_MODE_UPDATE);
            forward = __doInit(map, usr050Form, req, res, con);
        } else if (cmd.equals("pswdLimit")) {
            log__.debug("初期表示");
            usr050Form.setUsr050Mode(GSConstUser.PSWD_MODE_LIMIT);
            forward = __doInit(map, usr050Form, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, usr050Form, req, res, con);
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
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード暗号・復号時例外
     */
    public ActionForward __doInit(ActionMapping map,
                                    Usr050Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, EncryptionException {

            //パスワードルール取得
            __doSetPswdRule(form, con);
        return  map.getInputForward();
    }

    /**
     * <br>[機  能] パスワードルール設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード暗号・復号時例外
     */
    public void __doSetPswdRule(Usr050Form form, Connection con)
        throws SQLException, EncryptionException {

        con.setAutoCommit(true);
        CmnPswdConfDao dao = new CmnPswdConfDao(con);
        CmnPswdConfModel model = dao.select();
        con.setAutoCommit(false);

        // パスワードルール設定取得
        if (model != null) {
            form.setUsr050CoeKbn(model.getPwcCoe());
            form.setUsr050UppercaseKbn(model.getPwcUppercase());
            form.setUsr050UidPswdKbn(model.getPwcUidPswd());
            form.setUsr050OldPswdKbn(model.getPwcOldPswd());
            int digit = model.getPwcDigit();

            if (digit == -1) {
                form.setUsr050Digit(GSConstMain.DEFAULT_DIGIT);
            } else {
                form.setUsr050Digit(digit);
            }
        }
    }
    /**
     * <br>[機  能] 戻るボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード暗号・復号時例外
     */
    public ActionForward __doBack(ActionMapping map,
                                    Usr050Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, EncryptionException {

        ActionForward forward = null;

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            forward = map.findForward("backToTop");
        } else {
            forward = map.findForward("backToMenu");
        }
        return forward;
    }

    /**
     * <br>[機  能] OKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward __doEdit(ActionMapping map,
                                    Usr050Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        Usr050Biz biz = new Usr050Biz(getRequestModel(req));
        int userSid = biz.getSessionUserSid();

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validate(userSid, con, getRequestModel(req));
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //確認画面の設定
        return __setConfirmationDsp(map, req, form);
    }

    /**
     * <br>[機  能] 確認画面でキャンセルボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード暗号・復号時例外
     */
    public ActionForward __doEditCancel(ActionMapping map,
                                        Usr050Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException, EncryptionException {

        ActionForward forward = map.getInputForward();

        //セッション情報を取得
        HttpSession session = req.getSession();
        Object obj = session.getAttribute(GSConst.SESSION_KEY_PASSWORD);
        if (obj != null) {
            session.removeAttribute(GSConst.SESSION_KEY_PASSWORD);
            Usr050Model sessionMdl = (Usr050Model) obj;
            form.setUsr050OldPassWord(sessionMdl.getUsrOldPassWord());
            form.setUsr050Mode(sessionMdl.getUsrMode());
//            form.setUsr050NewPassWord(sessionMdl.getUsrNewPassWord());
//            form.setUsr050NewPassWordKn(sessionMdl.getUsrNewPassWord());
        }
        __doSetPswdRule(form, con);
        return forward;
    }

    /**
     * <br>[機  能] 確認画面でOKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward __doEditOk(ActionMapping map,
                                    Usr050Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        boolean commitFlg = false;
        Usr050Model sessionMdl = new Usr050Model();

        try {

            //セッション情報を取得
            HttpSession session = req.getSession();
            Object obj = session.getAttribute(GSConst.SESSION_KEY_PASSWORD);

            if (obj != null) {
                session.removeAttribute(GSConst.SESSION_KEY_PASSWORD);
                sessionMdl = (Usr050Model) obj;
                form.setUsr050Mode(sessionMdl.getUsrMode());

                form.setUsr050UserSid(sessionMdl.getUsrSid());
                form.setUsr050OldPassWord(sessionMdl.getUsrOldPassWord());
                form.setUsr050NewPassWord(sessionMdl.getUsrNewPassWord());
                form.setUsr050NewPassWordKn(sessionMdl.getUsrNewPassWord());
            }

            //入力チェック
            con.setAutoCommit(true);
            __doSetPswdRule(form, con);
            ActionErrors errors = form.validate(
                    getSessionUserSid(req), con, getRequestModel(req));
            con.setAutoCommit(false);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return map.getInputForward();
            }

            Usr050Biz biz = new Usr050Biz(getRequestModel(req));
            biz.updatePassword(con, sessionMdl, form.getBackScreen());

            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ 変更 **/
            String change = gsMsg.getMessage("cmn.change");

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                    change, GSConstLog.LEVEL_INFO, gsMsg.getMessage("main.man500.5"));
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

        return __setCompDsp(map, req, form);
    }

    /**
     * <br>[機  能] 変更確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
     private ActionForward __setConfirmationDsp(ActionMapping map,
                                                HttpServletRequest req,
                                                Usr050Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        if (form.getUsr050Mode() != GSConstUser.PSWD_MODE_NOMAL) {
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        } else {
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        }

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=editOk");

        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(forwardOk.getPath() + "?" + GSConst.P_CMD + "=editCancel");
        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage(req, "user.117");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
            msgRes.getMessage("edit.henkou.kakunin.once", textPassWord));

        Usr050Model sessionMdl = new Usr050Model();
        sessionMdl.setUsrSid(getSessionUserSid(req));
        sessionMdl.setUsrOldPassWord(form.getUsr050OldPassWord());
        sessionMdl.setUsrNewPassWord(form.getUsr050NewPassWord());
        sessionMdl.setUsrMode(form.getUsr050Mode());

        HttpSession session = req.getSession(false);
        session.setAttribute(GSConst.SESSION_KEY_PASSWORD, sessionMdl);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Usr050Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        if (form.getUsr050Mode() != GSConstUser.PSWD_MODE_NOMAL) {
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
            cmn999Form.setUrlOK(map.findForward("backToFrame").getPath());
        } else {
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
                cmn999Form.setUrlOK(map.findForward("backToTop").getPath());
            } else {
                cmn999Form.setUrlOK(map.findForward("backToMenu").getPath());
            }
        }
        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage(req, "user.117");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
            msgRes.getMessage("hensyu.henkou.kanryo.object", textPassWord));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] エラー画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward フォワード
     */
    private ActionForward  __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Usr050Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("backToLogin");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textDelWrite = gsMsg.getMessage("main.man500.5");
        String textDel = gsMsg.getMessage("cmn.edit");

        //メッセージセット
        String msgState = "error.edit.power.user";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelWrite,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ユーザがシステム予約ユーザかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param smodel ユーザ情報
     * @return 判定結果 true:システム予約ユーザ false:一般ユーザ
     */
    private boolean __checkSystemUser(BaseUserModel smodel) {
        if (smodel != null) {
            int userSid = smodel.getUsrsid();
            return userSid == GSConst.SYSTEM_USER_ADMIN
                    || userSid == GSConst.SYSTEM_USER_MAIL;
        }
        return false;
    }
}