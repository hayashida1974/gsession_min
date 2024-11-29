package jp.groupsession.v2.wml.wml040;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.AbstractWmlForm;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;

/**
 * <br>[機  能] WEBメール アカウント登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml040Action extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml040Action.class);

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
        Wml040Form thisForm = (Wml040Form) form;
        thisForm.setWml010adminUser(_checkAuth(map, req, con));
        WmlAdmConfDao wacDao = new WmlAdmConfDao(con);
        int editKbn = wacDao.selectAdmData().getWadAcntMake();
        log__.debug("editKbn = " + editKbn);

        if (!_checkAuth(map, req, con)) {
            //管理者権限チェック
            if (thisForm.getWmlAccountMode() == GSConstWebmail.ACCOUNTMODE_COMMON) {
                return map.findForward("gf_power");
            } else {
                if (editKbn == GSConstWebmail.KANRI_USER_ONLY) {
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
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);

        } else if (cmd.equals("beforePage")) {
            //戻るボタンクリック
            if (thisForm.getWmlAccountMode() == GSConstWebmail.WAC_TYPE_USER) {
                forward = map.findForward("accountManager");
            } else {
                forward = map.findForward("userAccountList");
            }

            //テンポラリディレクトリ削除
            Wml040Biz biz = new Wml040Biz();
            biz.deleteTempDir(getRequestModel(req));

        } else if (cmd.equals("deleteAccount")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteAccountComp")) {
            //削除確認画面からの遷移
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("upSign")) {
            //上へボタンクリック
            forward = __doSortSign(map, thisForm, req, res, con, GSConstWebmail.SORT_UP);

        } else if (cmd.equals("downSign")) {
            //下へボタンクリック
            forward = __doSortSign(map, thisForm, req, res, con, GSConstWebmail.SORT_DOWN);

        } else if (cmd.equals("deleteSign")) {
            //削除(署名)ボタンクリック
            forward = __doDeleteSign(map, thisForm, req, res, con);

        } else if (cmd.equals("oauthSuccess")) {
            //認証ウィンドウからの遷移
            forward = __doInit(map, thisForm, req, res, con);

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
    public ActionForward __doInit(ActionMapping map, Wml040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリの初期化
        if (form.getWml040initFlg() == GSConstWebmail.DSP_FIRST) {
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.clearTempDir(reqMdl, Wml040Biz.SCR_ID);
        }

        con.setAutoCommit(true);
        Wml040ParamModel paramMdl = new Wml040ParamModel();
        paramMdl.setParam(form);
        Wml040Biz biz = new Wml040Biz();
        biz.setInitData(con, paramMdl, reqMdl,
                        getSessionUserSid(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
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
    public ActionForward __doOK(ActionMapping map, Wml040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        // トランザクショントークン設定
        saveToken(req);

        //入力チェック
        ActionErrors errors = form.validateCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("confirm");
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
        Wml040Form form,
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
        Wml040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //アカウントを削除する
        Wml040ParamModel paramMdl = new Wml040ParamModel();
        paramMdl.setParam(form);
        Wml040Biz biz = new Wml040Biz();
        biz.deleteAccount(con, paramMdl, userSid);
        paramMdl.setFormData(form);

        //テンポラリディレクトリ削除
        biz.deleteTempDir(getRequestModel(req));

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        WmlAccountDao accountDao = new WmlAccountDao(con);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = " [" + gsMsg.getMessage("wml.96") + "]"
                + accountDao.getAccountName(form.getWmlAccountSid());
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
        Wml040Form form,
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
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteAccountComp");

        //メッセージ
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "wml.130");
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
        Wml040Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("delete");

        if (form.getWmlAccountMode() != GSConstWebmail.ACCOUNTMODE_COMMON) {
            forwardOk = map.findForward("userAccountList");
        }
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "wml.102")));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
        Wml040Form form) {

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
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doSortSign(
        ActionMapping map,
        Wml040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Wml040Biz biz = new Wml040Biz();
        String tempDir = biz.getTempDir(reqMdl);

        int signNo = biz.sortSignModel(reqMdl,
                                        tempDir,
                                        form.getWml040signNo(),
                                        changeKbn);
        form.setWml040signNo(signNo);

        return  __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除(署名)ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteSign(
        ActionMapping map,
        Wml040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        int signNo = form.getWml040signNo();
        if (signNo > 0) {
            //テンポラリディレクトリパスを取得
            RequestModel reqMdl = getRequestModel(req);
            Wml040Biz biz = new Wml040Biz();
            String tempDir = biz.getTempDir(reqMdl);

            biz.deleteSignModel(reqMdl, tempDir, signNo);
            form.setWml040signNo(0);
        }

        return  __doInit(map, form, req, res, con);
    }
}

