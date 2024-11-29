package jp.groupsession.v2.rng.rng210;

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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.rng210kn.Rng210knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng210Action extends AbstractRingiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng210Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;
        Rng210Form thisForm = (Rng210Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("Rng210Action CMD==>" + cmd);

        //OKボタン押下
        if (cmd.equals("ok")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("rngIdMenu")) {
            log__.debug("戻るボタン押下");
            return map.findForward("rngIdMenu");

        } else if (cmd.equals("delete")) {
            log__.debug("削除");
            forward = __dodelete(map, thisForm, req, res, con);

        } else if (cmd.equals("delComp")) {
            log__.debug("完了");
            forward = __exeDelete(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示処理");
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
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rng210Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Rng210ParamModel paramMdl = new Rng210ParamModel();
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        paramMdl.setParam(form);
        Rng210Biz biz = new Rng210Biz();
        biz.setInit(con, paramMdl, getSessionUserSid(req), cmd);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward 画面遷移先
     */
    private ActionForward __doOk(ActionMapping map,
                                  Rng210Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con) throws Exception {

        // トランザクショントークン設定
        saveToken(req);

        // チェック
        if (form.getRng200Sid() == 0 && form.getRng210Title() == "汎用申請ID") {
            //デフォルト編集
            return map.findForward("rngIdMenu");
        }
        ActionErrors errors = form.validateCheck(getRequestModel(req));
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            Rng210ParamModel paramMdl = new Rng210ParamModel();
            paramMdl.setParam(form);
            Rng210Biz biz = new Rng210Biz();
            biz.formatSplit(paramMdl, paramMdl.getRng210Format(), con);
            paramMdl.setFormData(form);
            return map.getInputForward();
        }
        return map.findForward("ok");
    }

    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __dodelete(
            ActionMapping map,
            Rng210Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("delComp");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delComp");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("delback");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "rng.rng180.04");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage(
                        "sakujo.kakunin.once",
                        msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rng210Format", form.getRng210Format());
        cmn999Form.addHiddenParam("rng210Init", form.getRng210Init());
        cmn999Form.addHiddenParam("rng210Manual", form.getRng210Manual());
        cmn999Form.addHiddenParam("rng210Date", form.getRng210Date());
        cmn999Form.addHiddenParam("rng200Sid", form.getRng200Sid());
        
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

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
    private ActionForward __exeDelete(
        ActionMapping map,
        Rng210Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        boolean commit = false;
        try {
            RequestModel reqMdl = getRequestModel(req);
            Rng210Biz biz = new Rng210Biz();
            Rng210knParamModel paramMdl = new Rng210knParamModel();
            paramMdl.setParam(form);
            RingiIdModel mdl = biz.deleteKanryoRng(paramMdl, con);
            paramMdl.setFormData(form);
            forward = __setCompDsp(map, req, form);

            GsMessage gsMsg = new GsMessage(reqMdl);
            String delete = gsMsg.getMessage("cmn.delete");

            String title = "";
            if (mdl != null) {
                title = mdl.getRngTitle();
            }

            //ログ出力処理
            RngBiz rngBiz = new RngBiz(con);
            String opCode = delete;
            String msg = "[" + gsMsg.getMessage("cmn.title") + "] " + title;

            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_TRACE, msg,
                    reqMdl);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("稟議情報の削除に失敗", e);
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
                                        Rng210Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;

        forwardOk = map.findForward("rngIdMenu");

        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String rng = gsMsg.getMessage(req, "rng.rng180.04");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", rng));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rng210Format", form.getRng210Format());
        cmn999Form.addHiddenParam("rng210Init", form.getRng210Init());
        cmn999Form.addHiddenParam("rng210Manual", form.getRng210Manual());
        cmn999Form.addHiddenParam("rng210Date", form.getRng210Date());
        cmn999Form.addHiddenParam("rng200Sid", form.getRng200Sid());
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
