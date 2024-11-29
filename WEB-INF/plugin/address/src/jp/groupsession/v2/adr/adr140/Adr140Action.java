package jp.groupsession.v2.adr.adr140;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr130.Adr130Action;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] アドレス帳 ラベル登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr140Action extends Adr130Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr140Action.class);

    /** 初期表示 */
    private static final int INIT = 0;
    /** 確認画面からの遷移 */
    private static final int KAKUNIN_BACK = 1;

    /**
     * <br>[機  能] アクション実行
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
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Adr140");
        ActionForward forward = null;

        Adr140Form thisForm = (Adr140Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        if (cmd.equals("ok")) {
            log__.debug("OKボタンクリック");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタンクリック");
            forward = __doDeleteConf(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("input_back")) {
            log__.debug("確認画面から戻る");
            forward = __doInit(map, thisForm, req, res, con, KAKUNIN_BACK);

        } else if (cmd.equals("list_back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("adr130");

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con, INIT);
        }

        log__.debug("END_Adr140");
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
     * @param kbn 遷移元画面
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Adr140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int kbn) throws SQLException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Adr140Biz biz = new Adr140Biz(getRequestModel(req));

        Adr140ParamModel paramMdl = new Adr140ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl, kbn);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        
        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doOk(
        ActionMapping map,
        Adr140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = form.validateAdr140(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con, KAKUNIN_BACK);
            return map.getInputForward();
        }
        
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //登録、または更新処理を行う
        Adr140Biz biz = new Adr140Biz(getRequestModel(req));

        Adr140ParamModel paramMdl = new Adr140ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, con, cntCon, userSid);
        paramMdl.setFormData(form);

        String opCode = "";
        GsMessage gsMsg = new GsMessage();
        if (form.getAdr130ProcMode() == GSConstAddress.PROCMODE_ADD) {
            opCode = gsMsg.getMessage(req, "cmn.entry");

        } else if (form.getAdr130ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            opCode = gsMsg.getMessage(req, "cmn.change");
        }

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        adrBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + form.getAdr140albName());

        //登録・更新完了画面を表示
        return __setTourokuKanryoDsp(map, form, req);
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteConf(
        ActionMapping map,
        Adr140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //削除チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.deleteCheck(con, getRequestModel(req));
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con, KAKUNIN_BACK);
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
     * @return ActionForward
     */
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Adr140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //業種を削除する
        Adr140Biz biz = new Adr140Biz(getRequestModel(req));

        Adr140ParamModel paramMdl = new Adr140ParamModel();
        paramMdl.setParam(form);
        biz.deleteAlb(con, paramMdl);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        String opCode = gsMsg.getMessage(req, "cmn.delete");

        adrBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + form.getAdr140albName());

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
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
        Adr140Form form,
        HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("adr130");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "cmn.label")));
        cmn999Form.addHiddenParam("adr280EditSid", form.getAdr280EditSid());


        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
    private ActionForward __setTourokuKanryoDsp(
        ActionMapping map,
        Adr140Form form,
        HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("adr130");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        int procMode = form.getAdr130ProcMode();
        if (procMode == GSConstAddress.PROCMODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object",
                            gsMsg.getMessage(req, "cmn.label")));
        } else if (procMode == GSConstAddress.PROCMODE_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object",
                            gsMsg.getMessage(req, "cmn.label")));
        }

        //画面パラメータをセット
        cmn999Form.addHiddenParam("adr130SortRadio", form.getAdr130SortRadio());
        cmn999Form.addHiddenParam("adr280EditSid", form.getAdr280EditSid());
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
        Adr140Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("adr140");
        cmn999Form.setUrlCancel(
                forwardCancel.getPath() + "?" + GSConst.P_CMD + "=input_back");

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("adr140");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //メッセージ
        MessageResources msgRes = getResources(req);
        Adr140Biz biz = new Adr140Biz(getRequestModel(req));
        String msg = biz.getDeletePosMsg(con, form.getAdr130EditAlbSid(), msgRes);
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("adr130ProcMode", form.getAdr130ProcMode());
        cmn999Form.addHiddenParam("adr130EditAlbSid", form.getAdr130EditAlbSid());
        cmn999Form.addHiddenParam("adr130SortRadio", form.getAdr130SortRadio());
        cmn999Form.addHiddenParam("adr140albName", form.getAdr140albName());
        cmn999Form.addHiddenParam("adr140bikou", form.getAdr140bikou());
        cmn999Form.addHiddenParam("adr140CatSid", form.getAdr140CatSid());
        cmn999Form.addHiddenParam("adr280EditSid", form.getAdr280EditSid());
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}
