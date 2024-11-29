package jp.groupsession.v2.rng.rng140;

import java.sql.Connection;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiTemplateAction;
import jp.groupsession.v2.rng.RngCategoriCantAccessException;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.rng090.Rng090Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng140Action extends AbstractRingiTemplateAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng140Action.class);

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
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Rng140Form thisForm = (Rng140Form) form;
        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        if (cmd.equals("rng140back")) {
            log__.debug("*** 戻るボタンクリック");
            if (thisForm.getRng140SeniFlg() == 1) {
                forward = map.findForward("rng150");
            } else {
                forward = map.findForward("rng140back");
            }

        } else if (cmd.equals("ok")) {
            log__.debug("*** OKボタンクリック。");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            log__.debug("*** 削除ボタンクリック。");
            forward = __setDeleteDsp(map, req, thisForm, con, res);

        } else if (cmd.equals("delexe")) {
            log__.debug("*** 削除確認OK。");
            forward = __doDelete(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con, getRequestModel(req));
        }
        return forward;
    }

    /**
     *
     * <br>[機  能] アクション実行前の事前処理 アクセス制限を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */

    protected ActionForward _immigration(ActionMapping map, Rng140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        if (form.getRngTemplateMode() != RngConst.RNG_TEMPLATE_SHARE
                && form.getRngTemplateMode() != RngConst.RNG_TEMPLATE_PRIVATE) {
            return getSubmitErrorPage(map, req);
        }
        if (form.getRng140ProcMode() < RngConst.RNG_CMDMODE_ADD
                || form.getRng140ProcMode() > RngConst.RNG_CMDMODE_EDIT) {
            return getSubmitErrorPage(map, req);
        }

        if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
            RngBiz rngBiz = new RngBiz(con);
            RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
            // 汎用稟議の使用が不可ならエラー
            if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
                return getSubmitErrorPage(map, req);
            }
            // 個人テンプレートの使用が不可ならエラー
            if (aconfMdl.getRarTemplatePersonalFlg()
                    == RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO) {
                return getSubmitErrorPage(map, req);
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), getPluginId());
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_SHARE && !adminUser) {
            return getSubmitErrorPage(map, req);
        }
        if (form.getRng140ProcMode() == RngConst.RNG_CMDMODE_EDIT) {
            //カテゴリアクセス権限例外用のエラー場面設定を用意
            RngCategoriCantAccessException rcaException = new RngCategoriCantAccessException();
            rcaException.setSeigenKbn(RngCategoriCantAccessException.SEIGEN_KBN_AUTH);
            rcaException.setCantActionStr(gsMsg.getMessage("cmn.edit"));
            ActionForward backForward;
            if (form.getRng140SeniFlg() == 1) {
                backForward = map.findForward("rng150");
            } else {
                backForward = map.findForward("rng140back");
            }

            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");

            ActionForward authError =  getCatchExceptionPage(map, req,
                    rcaException, backForward, cmn999form);
            //カテゴリアクセス権限
            Rng090Biz rng090Biz = new Rng090Biz(con, reqMdl);
            if (!rng090Biz.categoriAuthChk(form.getRngTemplateMode(),
                    form.getRng140CatSid(), adminUser)) {
                return authError;
            }

        }

        return null;
    }

/**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param reqMdl リクエストモデル
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map,
                                  Rng140Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con,
                                  RequestModel reqMdl)
                                  throws Exception {

        Rng140ParamModel paramMdl = new Rng140ParamModel();
        paramMdl.setParam(form);
        Rng140Biz biz = new Rng140Biz(getRequestModel(req));

        String cmd = req.getParameter("CMD");
        biz.init(paramMdl, con, getSessionUserSid(req), cmd);

        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOk(ActionMapping map,
                                Rng140Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateRng140(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con, getRequestModel(req));
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("okclick");
    }

    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param con Connection
     * @param res HttpServletResponse
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Rng140Form form,
                                          Connection con,
                                          HttpServletResponse res) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Rng140Biz biz = new Rng140Biz(reqMdl);
        int editSid = form.getRng140CatSid();
        BaseUserModel smodel = getSessionUserModel(req);

        GsMessage gsMsg = new GsMessage(req);
        UserGroupSelectModel ugsAdmModel = form.getRng140UserAdmList();
        ugsAdmModel.init(con, reqMdl,
                new String[] {RngConst.CATEGORY_ADMIN},
                new String[] {gsMsg.getMessage("cmn.admin")}, "", null, null);

        UserGroupSelectModel ugsLimitModel = form.getRng140UserLimitList();
        ugsLimitModel.init(con, reqMdl,
                new String[] {RngConst.CATEGORY_USE_LIMIT},
                new String[] {gsMsg.getMessage("cmn.use") + gsMsg.getMessage("anp.syusya.no"), },
                "", null, null);


        //カテゴリ内のテンプレート有無
        ActionErrors errors = form.deleteCheck(con, smodel.getUsrsid(), reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con, reqMdl);
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("rng140");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delexe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("delback");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = biz.getDeletePosMsg(con, editSid, msgRes);
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rngTemplateMode", form.getRngTemplateMode());
        cmn999Form.addHiddenParam("rng060SelectCat", form.getRng060SelectCat());
        cmn999Form.addHiddenParam("rng140CatName", form.getRng140CatName());
        cmn999Form.addHiddenParam("rng140CatSid", form.getRng140CatSid());
        cmn999Form.addHiddenParam("rng140ProcMode", form.getRng140ProcMode());
        cmn999Form.addHiddenParam("rng140SeniFlg", form.getRng140SeniFlg());
        cmn999Form.addHiddenParam("rng140UserLimit", form.getRng140UserLimit());
        cmn999Form.addHiddenParam("rng140UserLimitType", form.getRng140UserLimitType());
        
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);
        
        for (UsrLabelValueBean admin
                : form.getRng140UserAdmList().getSelectedList(RngConst.CATEGORY_ADMIN)) {
            cmn999Form.addHiddenParam("rng140UserAdmList.selected(adminList)", admin.getValue());
        }

        for (UsrLabelValueBean limit
                : form.getRng140UserLimitList().getSelectedList(RngConst.CATEGORY_USE_LIMIT)) {
            cmn999Form.addHiddenParam("rng140UserLimitList.selected(limitList)", limit.getValue());
        }
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除実行
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
    private ActionForward __doDelete(
        ActionMapping map,
        Rng140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        boolean commit = false;
        try {
            Rng140ParamModel paramMdl = new Rng140ParamModel();
            paramMdl.setParam(form);
            Rng140Biz biz = new Rng140Biz(getRequestModel(req));
            //カテゴリ削除
            biz.deleteTplCat(paramMdl,
                            paramMdl.getRng140CatSid(),
                             getSessionUserSid(req),
                             con);
            paramMdl.setFormData(form);

            forward = __setCompDsp(map, req, form);
            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String msg = gsMsg.getMessage("cmn.delete");

            String opCode = msg;

            //ログ出力処理
            RngBiz rngBiz = new RngBiz(con);
            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_INFO,
                    "[" + gsMsg.getMessage("cmn.category.name") + "]" + form.getRng140CatName(),
                    reqMdl, null, form.getRngTemplateMode());

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("稟議テンプレートカテゴリ情報の削除に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面
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
                                        Rng140Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        if (form.getRng140SeniFlg() == 1) {
            forwardOk = map.findForward("rng150");
        } else {
            forwardOk = map.findForward("rng060");
        }
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "rng.93");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rngTemplateMode", form.getRngTemplateMode());
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}
