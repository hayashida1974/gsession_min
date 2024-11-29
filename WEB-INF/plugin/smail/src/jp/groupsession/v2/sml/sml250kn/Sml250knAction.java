package jp.groupsession.v2.sml.sml250kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sml.AbstractSmlForm;
import jp.groupsession.v2.sml.AbstractSmlSubAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ショートメール アカウント登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml250knAction extends AbstractSmlSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml250knAction.class);

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
    public ActionForward executeSmail(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Sml250knForm thisForm = (Sml250knForm) form;
        SmlCommonBiz smlBiz = new SmlCommonBiz();
        SmlAdminModel admMdl = null;
        admMdl = smlBiz.getSmailAdminConf(0, con);

        int editKbn = admMdl.getSmaAcntMake();

        log__.debug("editKbn = " + editKbn);

        boolean auth = _checkAuth(map, req, con);
        //管理者権限のみアカウント設定が可能かどうか
        if (editKbn == GSConstSmail.KANRI_USER_ONLY) {
            if (!auth && thisForm.getSmlAccountSid() <= 0) {
                return getAuthErrorPage(map, req);
            }
            if (thisForm.getSmlCmdMode() == GSConstSmail.CMDMODE_ADD
                    && thisForm.getSmlAccountMode() != GSConstSmail.ACCOUNTMODE_COMMON) {
                return getAuthErrorPage(map, req);
            }
        }
        //編集可能かを判定
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(con, getRequestModel(req));
        if (!auth) {
            //一般ユーザが管理者設定画面に遷移したらNG
            if (thisForm.getSmlAccountMode() == GSConstSmail.ACCOUNTMODE_COMMON) {
                return getAuthErrorPage(map, req);
            }
            //編集可能かを判定
            if (thisForm.getSmlAccountSid() > 0) {
                if (!smlCmnBiz.canEditAccount(con, thisForm.getSmlAccountSid(),
                                        getSessionUserSid(req))) {
                    return map.findForward("gf_msg");
                }
            }
        }



        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("smlConfAccount");

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
    public ActionForward __doInit(ActionMapping map, Sml250knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Sml250knParamModel paramMdl = new Sml250knParamModel();
        paramMdl.setParam(form);
        Sml250knBiz biz = new Sml250knBiz();
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
    public ActionForward __doDecision(ActionMapping map, Sml250knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        
        //入力チェック
        ActionErrors errors = form.validateCheck250(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("smlConfAccount");
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        SmlAccountModel accountModel = null;
        boolean commit = false;
        try {
            Sml250knParamModel paramMdl = new Sml250knParamModel();
            paramMdl.setParam(form);
            Sml250knBiz biz = new Sml250knBiz();
            accountModel = biz.entryAddressData(con, paramMdl, getCountMtController(req),
                                getSessionUserModel(req).getUsrsid(), getRequestModel(req));
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
        }

        String opCode = "";
        if (form.getSmlCmdMode() == GSConstSmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else {
            opCode = getInterMessage(req, "cmn.change");
        }
        if (accountModel != null) {
            //ログ出力
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
            smlBiz.outPutLog(map, getRequestModel(req),
                    opCode, GSConstLog.LEVEL_INFO,
                    "[name]" + accountModel.getSacName());
        }

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
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
        Sml250knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("smlUserAccountList");
        if (form.getSmlAccountMode() == GSConstSmail.SAC_TYPE_USER) {
            urlForward = map.findForward("smlAccountManager");
        }

        ((AbstractSmlForm) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getSmlCmdMode() == GSConstSmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getSmlCmdMode() == GSConstSmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "wml.102")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
//
//    /**
//     * <br>[機  能] アカウント登録権限エラー画面遷移時のパラメータセット
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param map マッピング
//     * @param req リクエスト
//     * @param form アクションフォーム
//     */
//    private void __setAcntErrPageParam(
//        ActionMapping map,
//        HttpServletRequest req,
//        Sml250knForm form) {
//
//        Cmn999Form cmn999Form = new Cmn999Form();
//        ActionForward urlForward = null;
//
//        cmn999Form.setType(Cmn999Form.TYPE_OK);
//        MessageResources msgRes = getResources(req);
//        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
//        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
//
//        urlForward = map.findForward("mailList");
//
//        ((AbstractSmlForm) form).setHiddenParam(cmn999Form);
//        cmn999Form.setUrlOK(urlForward.getPath());
//
//        //メッセージセット
//        String msgState = null;
//        msgState = "add.touroku.wmluser";
//
//        cmn999Form.setMessage(msgRes.getMessage(msgState));
//        //画面パラメータをセット
//        form.setHiddenParam(cmn999Form);
//        req.setAttribute("cmn999Form", cmn999Form);
//    }
}
