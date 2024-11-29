package jp.groupsession.v2.sml.sml180;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmlAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ショートメール 管理者設定 メール転送一括設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml180Action extends AbstractSmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml180Action.class);

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
        RequestModel reqMdl = getRequestModel(req);

        Sml180Form thisForm = (Sml180Form) form;
        SmlCommonBiz cmnBiz = new SmlCommonBiz(reqMdl);
        Sml180Biz biz = new Sml180Biz(reqMdl);
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        int sessionUsrSid = usModel.getUsrsid();
        con.setAutoCommit(true);

        //管理者メール転送設定が個人で設定可能かチェックする。
        SmlAdminModel admConf = cmnBiz.getSmailAdminConf(sessionUsrSid, con);
        if (admConf.getSmaMailfw() == GSConstSmail.MAIL_FORWARD_NG) {
            return cantUseDsp(map, thisForm, req, res);
        }

        //在席管理プラグインが有効チェック
        Sml180ParamModel paramMdl = new Sml180ParamModel();
        paramMdl.setParam(form);
        biz.setCanUsePluginFlg(paramMdl, con, getPluginConfigForMain(getPluginConfig(req), con,
                                                                    getSessionUserSid(req),
                                                                    getRequestModel(req)));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //戻るボタン押下
        if (cmd.equals("backToList")) {
            log__.debug("戻るボタン押下");
            return map.findForward("backToList");

        //設定ボタン押下
        } else if (cmd.equals("ok")) {
            log__.debug("設定ボタン押下");
            forward = __doSet(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteMnb")) {
            log__.debug("左矢印");
            forward = __doDeleteMnb(map, thisForm, req, res, con);

        } else if (cmd.equals("addMnb")) {
            log__.debug("右矢印");
            forward = __doAddMnb(map, thisForm, req, res, con);

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
                                    Sml180Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sml180ParamModel paramMdl = new Sml180ParamModel();
        paramMdl.setParam(form);
        Sml180Biz biz = new Sml180Biz(reqMdl);
        biz.setInitData(reqMdl, paramMdl, con);
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
                                   Sml180Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
        throws SQLException {

        ActionForward forward = null;

        ActionErrors errors = form.validateCheck(map, req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トークン設定
        saveToken(req);

        //完了画面
        forward = map.findForward("ok");

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

    /**
     * <br>[機  能] 左矢印クリック時の処理
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
    private ActionForward __doDeleteMnb(
        ActionMapping map,
        Sml180Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //コンボで選択中のメンバーをメンバーリストから削除する
        RequestModel reqMdl = getRequestModel(req);
        Sml180ParamModel paramMdl = new Sml180ParamModel();
        paramMdl.setParam(form);
        Sml180Biz biz = new Sml180Biz(reqMdl);
        biz.deleteMnb(paramMdl);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 右矢印クリック時の処理
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
    private ActionForward __doAddMnb(
        ActionMapping map,
        Sml180Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //追加用メンバーコンボで選択中のメンバーをメンバーリストに追加する
        RequestModel reqMdl = getRequestModel(req);
        Sml180ParamModel paramMdl = new Sml180ParamModel();
        paramMdl.setParam(form);
        Sml180Biz biz = new Sml180Biz(reqMdl);
        biz.addMnb(paramMdl);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
}