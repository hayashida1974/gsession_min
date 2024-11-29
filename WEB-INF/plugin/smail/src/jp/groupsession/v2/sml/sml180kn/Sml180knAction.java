package jp.groupsession.v2.sml.sml180kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.sml180.Sml180Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ショートメール 管理者設定 メール転送一括設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml180knAction extends AbstractSmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml180knAction.class);

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
     * @see jp.groupsession.v2.sml.AbstractSmlAction
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
                                       Connection con)
        throws Exception {

        ActionForward forward = null;
        Sml180knForm thisForm = (Sml180knForm) form;

        //管理者メール転送設定が個人で設定可能かチェックする。
        SmlCommonBiz cmnBiz = new SmlCommonBiz(getRequestModel(req));
        SmlAdminModel admConf = cmnBiz.getSmailAdminConf(getSessionUserModel(req).getUsrsid(), con);
        if (admConf.getSmaMailfw() == GSConstSmail.MAIL_FORWARD_NG) {
            return cantUseDsp(map, thisForm, req, res);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //戻るボタン押下
        if (cmd.equals("back")) {
            log__.debug("戻るボタン押下");
            return map.findForward("back");
        //設定ボタン押下
        } else if (cmd.equals("setting")) {
            log__.debug("設定ボタン押下");
            forward = __doSet(map, thisForm, req, res, con);
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
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Sml180knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        Sml180knParamModel paramMdl = new Sml180knParamModel();
        paramMdl.setParam(form);
        Sml180knBiz biz = new Sml180knBiz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSet(ActionMapping map,
                                   Sml180knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
        throws SQLException {

        ActionForward forward = null;
        boolean commit = false;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(map, req, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        try {

            //ショートメール個人設定の更新
            RequestModel reqMdl = getRequestModel(req);
            Sml180knParamModel paramMdl = new Sml180knParamModel();
            paramMdl.setParam(form);
            Sml180knBiz biz = new Sml180knBiz();
            biz.updateMailFw(getSessionUserSid(req), paramMdl, con);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage(reqMdl);
            String edit = gsMsg.getMessage("cmn.edit");

            //ログ出力処理
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl, edit, GSConstLog.LEVEL_INFO, "");

            //完了画面
            forward = __doCompDsp(map, form, req, res);
            commit = true;

        } catch (SQLException e) {
            log__.error("ショートメール転送一括設定の更新に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        return forward;
    }

    /**
     * <br>[機  能] 完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Sml180knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String tensouMail = gsMsg.getMessage(req, "sml.sml100.03");

        urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object", tensouMail));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] この画面が使用不可の場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    public ActionForward cantUseDsp(ActionMapping map,
                                        Sml180Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.cant.use.sml.forward"));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}