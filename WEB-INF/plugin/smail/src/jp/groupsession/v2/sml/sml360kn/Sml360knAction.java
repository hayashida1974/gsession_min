package jp.groupsession.v2.sml.sml360kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;

/**
 * <br>[機  能] ショートメール 個人設定 フィルタ登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml360knAction extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml360knAction.class);

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
        Sml360knForm thisForm = (Sml360knForm) form;

        //編集可能かを判定
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(con, getRequestModel(req));
        if ((!_checkAuth(map, req, con)) && thisForm.getSmlAccountSid() > 0) {
            if (!smlCmnBiz.canEditAccount(con, thisForm.getSmlAccountSid(),
                                    getSessionUserModel(req).getUsrsid())) {
                return map.findForward("gf_msg");
            }
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

        } else if (cmd.equals("prevPage")) {
            //前ページボタンクリック
            thisForm.setSml360mailListPageTop(thisForm.getSml360mailListPageTop() - 1);
            thisForm.setSml360mailListPageBottom(thisForm.getSml360mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            thisForm.setSml360mailListPageTop(thisForm.getSml360mailListPageTop() + 1);
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
    public ActionForward __doInit(ActionMapping map, Sml360knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Sml360knParamModel paramMdl = new Sml360knParamModel();
        paramMdl.setParam(form);
        Sml360knBiz biz = new Sml360knBiz();
        biz.setInitData(con, getRequestModel(req), paramMdl, getSessionUserSid(req));
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
    public ActionForward __doDecision(ActionMapping map, Sml360knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

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

        //登録処理
        MlCountMtController cntCon = getCountMtController(req);
        //登録、または更新処理を行う
        Sml360knParamModel paramMdl = new Sml360knParamModel();
        paramMdl.setParam(form);
        Sml360knBiz biz = new Sml360knBiz(con);
        biz.doAddEdit(paramMdl, userSid, cntCon, getRequestModel(req));
        paramMdl.setFormData(form);

        //ログ出力
        String opCode = "";
        if (form.getSmlFilterCmdMode() == GSConstSmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else if (form.getSmlFilterCmdMode() == GSConstSmail.CMDMODE_EDIT) {
            opCode = getInterMessage(req, "cmn.change");
        }
        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlAccountSid());

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
        smlBiz.outPutLog(map, getRequestModel(req),
                opCode, GSConstLog.LEVEL_INFO, "アカウント:"
         + sacMdl.getSacName()
         + "\n");

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
        Sml360knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("filterConf");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getSmlFilterCmdMode() == GSConstSmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getSmlFilterCmdMode() == GSConstSmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "wml.144")));

        cmn999Form.addHiddenParam("sml350SortRadio", form.getSml350SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());
        cmn999Form.addHiddenParam("smlCmdMode", form.getSmlCmdMode());
        cmn999Form.addHiddenParam("smlAccountMode", form.getSmlAccountMode());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

    }
}
