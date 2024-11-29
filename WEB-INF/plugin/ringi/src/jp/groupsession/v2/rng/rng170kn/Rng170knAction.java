package jp.groupsession.v2.rng.rng170kn;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngDeleteModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 稟議 手動データ削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng170knAction extends AbstractRingiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng170knAction.class);

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
        Rng170knForm thisForm = (Rng170knForm) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

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
    public ActionForward __doInit(ActionMapping map, Rng170knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
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
    public ActionForward __doDecision(ActionMapping map, Rng170knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Rng170knBiz biz = new Rng170knBiz();
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //手動削除を行う
        Rng170knParamModel paramMdl = new Rng170knParamModel();
        paramMdl.setParam(form);
        List<RngDeleteModel> delList = biz.getDelDataList(reqMdl, paramMdl);
        paramMdl.setFormData(form);

        boolean commit = false;
        RngBiz rngBiz = new RngBiz(con);
        try {
            rngBiz.deleteRngData(con, delList, getSessionUserSid(req));

            //ログ出力処理
            String msg = "";
            if (form.getRng170pendingKbn() == RngConst.MANU_DEL_OK) {
                msg += "[" + gsMsg.getMessage("rng.48") + "] "; // 申請中
                msg += form.getRng170pendingYear()  + gsMsg.getMessage("cmn.year2");
                msg += form.getRng170pendingMonth() + gsMsg.getMessage("cmn.months2");
                msg += form.getRng170pendingDay()   + gsMsg.getMessage("cmn.day");
            }

            if (form.getRng170completeKbn() == RngConst.RAD_KBN_DELETE) {
                if (msg.length() > 0) {
                    msg += "\r\n";
                }
                msg += "[" + gsMsg.getMessage("cmn.complete") + "] "; // 完了
                msg += form.getRng170completeYear()  + gsMsg.getMessage("cmn.year2");
                msg += form.getRng170completeMonth() + gsMsg.getMessage("cmn.months2");
                msg += form.getRng170completeDay()   + gsMsg.getMessage("cmn.day");
            }

            if (form.getRng170draftKbn() == RngConst.RAD_KBN_DELETE) {
                if (msg.length() > 0) {
                    msg += "\r\n";
                }
                msg += "[" + gsMsg.getMessage("cmn.draft") + "] "; // 草稿
                msg += form.getRng170draftYear()  + gsMsg.getMessage("cmn.year2");
                msg += form.getRng170draftMonth() + gsMsg.getMessage("cmn.months2");
                msg += form.getRng170draftDay()   + gsMsg.getMessage("cmn.day");
            }

            rngBiz.outPutLog(map, getInterMessage(req, "cmn.delete"),
                    GSConstLog.LEVEL_INFO, msg,
                    reqMdl);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("稟議 手動削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
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
        Rng170knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("rngAdmMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "cmn.kanryo.object";
        cmn999Form.setMessage(
                msgRes.getMessage(msgState, getInterMessage(req, "cmn.manual.delete2")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form, true);
        form.setConfHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}

