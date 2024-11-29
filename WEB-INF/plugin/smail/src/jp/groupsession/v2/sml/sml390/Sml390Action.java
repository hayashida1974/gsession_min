package jp.groupsession.v2.sml.sml390;

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
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.sml380.Sml380Biz;
import jp.groupsession.v2.sml.sml380.Sml380Dao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 送信先制限設定 追加編集画面　アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml390Action extends AbstractSmailAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml390Action.class);
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Sml390Form thisForm = (Sml390Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ok")) {
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("delete")) {
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteComp")) {
            forward = __doDeleteComp(map, thisForm, req, res, con);
        } else if (cmd.equals("back")) {
            forward = map.findForward("banConf");
        } else {
            //初期設定
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
    public ActionForward __doInit(ActionMapping map, Sml390Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {


        con.setAutoCommit(true);
        Sml390ParamModel paramMdl = new Sml390ParamModel();
        Sml390Biz biz = new Sml390Biz();
        paramMdl.setParam(form);
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック
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
    public ActionForward __doOk(ActionMapping map, Sml390Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        ActionErrors errors = form.validateInputCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);
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
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Sml390Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionErrors errors = form.validateEditCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

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
        Sml390Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //TODO 送信制限設定を削除する
        Sml390ParamModel paramMdl = new Sml390ParamModel();
        paramMdl.setParam(form);

        Sml380Dao dao = new Sml380Dao(con);
        StringBuilder delNames = new StringBuilder();
        boolean first = true;
        for (String name : dao.getSbcNameList(new String[] {
                String.valueOf(
                paramMdl.getSml380EditBan())})) {
            if (!first) {
                delNames.append("\n");
            }
            delNames.append(name);
        }
        Sml390Biz biz = new Sml390Biz();
        con.setAutoCommit(false);
        boolean commit = false;
        try {
            biz.deleteDestList(con, paramMdl);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("送信先制限設定の削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

         //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                gsMsg.getMessage("sml.188") + ":" + delNames.toString());

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
        Sml390Form form,
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
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteComp");

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(req);

        Sml380Biz biz = new Sml380Biz();
//        String msg = biz.getDeletePosMsg(con, form.getAdr080EditAtiSid(), msgRes);
        String msg = msgRes.getMessage("sakujo.kakunin.list",
                gsMsg.getMessage(req, "sml.188"),
                biz.getSendListName(con,
                        new String[] {String.valueOf(form.getSml380EditBan())}));

        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());
        cmn999Form.addHiddenParam("smlCmdMode", form.getSmlCmdMode());
        cmn999Form.addHiddenParam("smlAccountMode", form.getSmlAccountMode());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());


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
        Sml390Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("banConf");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", getInterMessage(req, "sml.188")));

        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());
        cmn999Form.addHiddenParam("smlCmdMode", form.getSmlCmdMode());
        cmn999Form.addHiddenParam("smlAccountMode", form.getSmlAccountMode());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
