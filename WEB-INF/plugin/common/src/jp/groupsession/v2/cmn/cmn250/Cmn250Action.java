package jp.groupsession.v2.cmn.cmn250;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] OAuth認証情報管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn250Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn250Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Cmn250Form thisForm = (Cmn250Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("cmn250Next")) {
            //追加、修正ボタン押下時
            forward = __doConfig(map, thisForm, req, res, con);

        } else if (cmd.equals("cmn250Back")) {
            //戻るボタン押下時
            forward = map.findForward("gf_main_kanri");

        } else if (cmd.equals("cmn250Delete")) {
            //削除ボタン押下時
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("cmn250DeleteOk")) {
            //削除確認画面でOK押下時
            forward = __doDeleteOk(map, thisForm, req, res, con);

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
    private ActionForward __doInit(ActionMapping map, Cmn250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        //セッション情報の取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        Cmn250ParamModel paramMdl = new Cmn250ParamModel();
        paramMdl.setParam(form);
        Cmn250Biz biz = new Cmn250Biz();
        biz.setInitData(con, paramMdl, userSid, getRequestModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

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
    public ActionForward __doConfig(ActionMapping map, Cmn250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        if (form.getCmn250CmdMode() == GSConstCommon.MODE_EDIT) {

            Cmn250ParamModel paramMdl = new Cmn250ParamModel();
            Cmn250Biz biz = new Cmn250Biz();
            paramMdl.setParam(form);

            //Outh認証情報の存在チェック
            boolean exist = biz._existOauthData(con, paramMdl);
            if (!exist) {
                ActionErrors errors = new ActionErrors();
                ActionMessage msg = null;
                msg = new ActionMessage("error.oauth.exist");
                errors.add("error.oauth.exist", msg);
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
        }
        return map.findForward("cmn250Next");
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
     * @return ActionForward
     * @throws Exception 実行時例外発生
     */
    private ActionForward __doDelete(
            ActionMapping map,
            Cmn250Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        Cmn250ParamModel paramMdl = new Cmn250ParamModel();
        Cmn250Biz biz = new Cmn250Biz();
        paramMdl.setParam(form);

        //Outh認証情報の存在チェック
        boolean exist = biz._existOauthData(con, paramMdl);
        if (!exist) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = null;
            msg = new ActionMessage("error.oauth.exist");
            errors.add("error.oauth.exist", msg);
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);

        } else {
            //削除対象となるOauth認証情報がアカウント情報に指定されているか確認
            boolean result = biz._existAuthCheck(con, paramMdl);
            if (result) {
                ActionErrors errors = new ActionErrors();
                ActionMessage msg = null;
                msg = new ActionMessage("error.oauth.use");
                errors.add("error.oauth.use", msg);
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        __setKakuninDsp(map, form, req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     */
    private void __setKakuninDsp(
            ActionMapping map,
            Cmn250Form form,
            HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(map.findForward("cmn250Own").getPath());
        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("deleteOauthOk").getPath());

        //メッセージ
        String msg = gsMsg.getMessage(req, "cmn.cmn250.02");
        cmn999Form.setMessage(msg);

        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("cmnAuthSid", form.getCmnAuthSid());
        req.setAttribute("cmn999Form", cmn999Form);
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
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doDeleteOk(
            ActionMapping map,
            Cmn250Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログ用 認証情報名退避
        Cmn250Biz biz = new Cmn250Biz();
        int authSid = form.getCmnAuthSid();
        int provider = biz._getAuthName(con, authSid);
        String authName = null;
        if (provider == GSConstCommon.COU_PROVIDER_GOOGLE) {
            authName = getInterMessage(req, GSConstCommon.PROVIDER_NAME_GOOGLE);
        } else {
            authName = getInterMessage(req, GSConstCommon.PROVIDER_NAME_MICROSOFT);
        }

        Cmn250ParamModel paramMdl = new Cmn250ParamModel();
        boolean commitFlg = false;

        try {
            //認証情報を削除する
            paramMdl.setParam(form);
            biz._deleteOAuth(con, authSid);
            paramMdl.setFormData(form);
            //コミット実行
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("削除失敗", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
        //ログ出力
        __outPutDeleteLog(map, form, req, con, authName);

        //削除完了画面を表示
        __setKanryoDsp(map, form, req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 管理者設定 認証情報削除のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param delAuthName 削除対象の認証情報
     * @throws SQLException SQL実行例外
     */
    private void __outPutDeleteLog(
            ActionMapping map,
            Cmn250Form form,
            HttpServletRequest req,
            Connection con,
            String delAuthName) throws SQLException {

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();

        //内容セット
        String opCode = gsMsg.getMessage(req, "cmn.delete");
        values.append("[" + gsMsg.getMessage("cmn.cmn260.04") + "] " + delAuthName);

        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con, opCode,
                            GSConstLog.LEVEL_INFO, values.toString());
    }

    /**
     * <br>[機  能] 削除完了画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     */
    private void __setKanryoDsp(
            ActionMapping map,
            Cmn250Form form,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("cmn250Own").getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", getInterMessage(req, "cmn.cmn260.04")));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}