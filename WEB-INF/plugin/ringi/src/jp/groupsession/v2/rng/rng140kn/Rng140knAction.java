package jp.groupsession.v2.rng.rng140kn;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiTemplateAction;
import jp.groupsession.v2.rng.RngCategoriCantAccessException;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.rng090.Rng090Biz;
import jp.groupsession.v2.rng.rng140.Rng140Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng140knAction extends AbstractRingiTemplateAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng140knAction.class);

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

        Rng140knForm thisForm = (Rng140knForm) form;
        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        if (cmd.equals("input_back")) {
            log__.debug("*** 戻るボタンクリック");
            forward = map.findForward("rng140knback");

        } else if (cmd.equals("kakutei")) {
            log__.debug("*** 確定ボタンクリック。");
            forward = __doKakutei(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
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
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map,
                                  Rng140knForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        RequestModel reqMdl = getRequestModel(req);
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
    public ActionForward __doKakutei(ActionMapping map,
                                  Rng140knForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateRng140(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
       }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //ログインユーザSID取得
        BaseUserModel smodel = getSessionUserModel(req);
        int usrSid = smodel.getUsrsid();

        //登録または更新処理
        Rng140knBiz biz = new Rng140knBiz();

        boolean commit = false;
        try {
            Rng140knParamModel paramMdl = new Rng140knParamModel();
            paramMdl.setParam(form);
            if (form.getRng140ProcMode() == RngConst.RNG_CMDMODE_ADD) {
                biz.insert(paramMdl, con, usrSid, cntCon);
            } else if (form.getRng140ProcMode() == RngConst.RNG_CMDMODE_EDIT) {
                biz.update(paramMdl, con, usrSid, cntCon);
            }
            paramMdl.setFormData(form);

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String msg = "";
            if (form.getRng140ProcMode() == RngConst.RNG_CMDMODE_ADD) {
                msg = gsMsg.getMessage("cmn.entry");
            } else {
                msg = gsMsg.getMessage("cmn.edit");
            }

            //ログ出力
            RngBiz rngBiz = new RngBiz(con);
            rngBiz.outPutLog(map, msg, GSConstLog.LEVEL_INFO,
                    "[" + gsMsg.getMessage("cmn.category.name") + "]"
            + form.getRng140CatName(), reqMdl, null,
                    form.getRngTemplateMode());

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("稟議テンプレートカテゴリの登録に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(ActionMapping map,
                                         Rng140knForm form,
                                         HttpServletRequest req) {

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

        MessageResources msgRes = getResources(req);

        int procMode = form.getRng140ProcMode();

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.category");

        if (procMode == RngConst.RNG_CMDMODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", msg));
        } else if (procMode == RngConst.RNG_CMDMODE_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", msg));
        }

        cmn999Form.addHiddenParam("rngTemplateMode", form.getRngTemplateMode());
        cmn999Form.addHiddenParam("rng060SelectCat", form.getRng060SelectCat());
        cmn999Form.addHiddenParam("rng010TransitionFlg", form.getRng010TransitionFlg());
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);
        
        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
               backForward = map.findForward("rng060");
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

}
