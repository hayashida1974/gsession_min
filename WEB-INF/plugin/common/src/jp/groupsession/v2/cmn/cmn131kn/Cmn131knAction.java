package jp.groupsession.v2.cmn.cmn131kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn131.Cmn131Biz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン 個人設定 マイグループ登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn131knAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn131knAction.class);

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

        log__.debug("START_Cmn131kn");
        ActionForward forward = null;

        Cmn131knForm thisForm = (Cmn131knForm) form;

        //編集権限チェック
        forward = __doCheck(map, req, thisForm, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backToInput")) {
            log__.debug("戻る");
            forward = map.findForward("backToInput");

        } else if (cmd.equals("touroku")) {
            log__.debug("登録");
            forward = __doTouroku(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cmn131kn");
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
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws Exception その他例外
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cmn131knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        //初期表示情報を画面にセットする
        RequestModel reqMdl = getRequestModel(req);
        Cmn131knBiz biz = new Cmn131knBiz(new GsMessage(reqMdl));
        Cmn131knParamModel paramModel = new Cmn131knParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録ボタンクリック時の処理
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
    private ActionForward __doTouroku(
        ActionMapping map,
        Cmn131knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
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
        Cmn131knBiz biz = new Cmn131knBiz(new GsMessage(req));
        Cmn131knParamModel paramModel = new Cmn131knParamModel();
        paramModel.setParam(form);
        biz.doAddEdit(paramModel, con, cntCon, userSid);
        paramModel.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        String textEdit = gsMsg.getMessage("cmn.change");
        String textCreate = gsMsg.getMessage("cmn.entry");

        //ログ出力
        String opCode = "";
        if (NullDefault.getString(form.getCmn130cmdMode(), "").equals(Cmn131Biz.MODE_EDIT)) {
            opCode = textEdit;
        } else {
            opCode = textCreate;
        }
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                opCode, GSConstLog.LEVEL_INFO,
                "[name]" + NullDefault.getString(form.getCmn131name(), ""));

        //完了画面を表示
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
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Cmn131knForm form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("backToList");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        GsMessage gsMsg = new GsMessage();
        String textMyGroup = gsMsg.getMessage(req, "cmn.mygroup");


        String cmdMode = NullDefault.getString(form.getCmn130cmdMode(), "");
        if (cmdMode.equals(Cmn131Biz.MODE_ADD)) {
            //登録
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", textMyGroup));
        } else if (cmdMode.equals(Cmn131Biz.MODE_EDIT)) {
            //編集
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", textMyGroup));
        }

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 編集権限チェックを行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param form Cmn131knForm
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException
     */
    private ActionForward __doCheck(
        ActionMapping map,
        HttpServletRequest req,
        Cmn131knForm form,
        Connection con) throws SQLException {

        //ログインユーザSIDを取得
        int userSid = -1;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Cmn131Biz biz = new Cmn131Biz(new GsMessage(req));
        Cmn131knParamModel paramModel = new Cmn131knParamModel();
        paramModel.setParam(form);
        boolean editKengen = false;
        editKengen = biz.checkEditKengen(paramModel, con, userSid);
        if (!editKengen) {
            return __setErrorPage(map, req);
        }
        return null;
    }
    
    /**
     * [機  能] 警告画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setErrorPage(
        ActionMapping map,
        HttpServletRequest req) {
        
        MessageResources msgRes = getResources(req);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        ActionForward urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.mygroup");
        cmn999Form.setMessage(msgRes.getMessage("search.data.notfound", msg));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
