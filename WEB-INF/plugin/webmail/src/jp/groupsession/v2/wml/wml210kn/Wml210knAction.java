package jp.groupsession.v2.wml.wml210kn;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailAdminAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;

/**
 * <br>[機  能] WEBメール 管理者設定 ラベル登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml210knAction extends AbstractWebmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml210knAction.class);

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
        Wml210knForm thisForm = (Wml210knForm) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

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
    public ActionForward __doInit(ActionMapping map, Wml210knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Wml210knParamModel paramMdl = new Wml210knParamModel();
        paramMdl.setParam(form);
        Wml210knBiz biz = new Wml210knBiz();
        biz.setInitData(con, paramMdl);
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
    public ActionForward __doDecision(ActionMapping map, Wml210knForm form,
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
        Wml210knParamModel paramMdl = new Wml210knParamModel();
        paramMdl.setParam(form);
        Wml210knBiz biz = new Wml210knBiz(con);
        biz.doAddEdit(paramMdl, userSid, cntCon);
        paramMdl.setFormData(form);

        String opCode = "";
        if (form.getWmlLabelCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else if (form.getWmlLabelCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
            opCode = getInterMessage(req, "cmn.change");
        }
        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();

        // アカウント名
        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountModel accountMdl = accountDao.select(form.getWmlAccountSid());
        String accountName = accountMdl.getWacName();
        // 出力メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = "[" + gsMsg.getMessage("wml.96") + "] " + accountName;
        msg += "\r\n[" + gsMsg.getMessage("wml.74") + "]" + NullDefault.getString(
                form.getWml210LabelName(), "");
        wmlBiz.outPutLog(map, getRequestModel(req), con,
                opCode, GSConstLog.LEVEL_INFO, msg);

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
        Wml210knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("labelConf");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getWmlLabelCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getWmlLabelCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.label")));

        cmn999Form.addHiddenParam("wml200SortRadio", form.getWml200SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

    }
}
