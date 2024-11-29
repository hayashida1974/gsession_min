package jp.groupsession.v2.adr.adr290;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.adr.AbstractAddressAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] アドレス帳 カテゴリ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr290Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr290Action.class);

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

        log__.debug("START_Adr280");
        ActionForward forward = null;

        Adr290Form thisForm = (Adr290Form) form;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        //カテゴリ「未設定」を編集・削除しようとした場合
        if (thisForm.getAdr280EditSid() == 1) {
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("ok")) {
            log__.debug("OKボタンクリック");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタンクリック");
            forward = __doDeleteCat(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("input_back")) {
            log__.debug("確認画面から戻る");
            forward = map.getInputForward();

        } else if (cmd.equals("adr290back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("listback");

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Adr280");
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Adr290Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Adr290Biz biz = new Adr290Biz(getRequestModel(req));

        Adr290ParamModel paramMdl = new Adr290ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

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
        Adr290Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateAdr290(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
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
        RequestModel reqMdl = getRequestModel(req);
        Adr290Biz biz = new Adr290Biz(reqMdl);

        Adr290ParamModel paramMdl = new Adr290ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, con, cntCon, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        GsMessage gsMsg = new GsMessage(req);
        String opCode = "";
        if (form.getAdr280ProcMode() == GSConstAddress.PROCMODE_ADD) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else if (form.getAdr280ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            opCode = gsMsg.getMessage("cmn.update");
        }

        adrBiz.outPutLog(map, req, res,
                opCode, GSConstLog.LEVEL_TRACE,
                "[" + gsMsg.getMessage("cmn.category.name") + "]"
                + form.getAdr290CategoryName()
                + " [" + gsMsg.getMessage("cmn.memo") + "]"
                + NullDefault.getString(form.getAdr290bikou(), ""));


        return __setKanryoTourokuDsp(map, form, req);
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
    private ActionForward __doDeleteCat(
        ActionMapping map,
        Adr290Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //削除チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.deleteCheck(con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
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
        Adr290Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //カテゴリを削除する
        Adr290Biz biz = new Adr290Biz(getRequestModel(req));

        Adr290ParamModel paramMdl = new Adr290ParamModel();
        paramMdl.setParam(form);
        biz.deleteCat(con, paramMdl);
        paramMdl.setFormData(form);

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        GsMessage gsMsg = new GsMessage();
        String opCode = gsMsg.getMessage(req, "cmn.delete");

        adrBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[" + gsMsg.getMessage("cmn.category.name") + "]"
                + form.getAdr290CategoryName());


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
        Adr290Form form,
        HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("listback");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "cmn.category")));
        //パラメータセット

        cmn999Form.addHiddenParam("adr280EditSid", form.getAdr280EditSid());

        cmn999Form.addHiddenParam("adr010cmdMode", form.getAdr010cmdMode());
        cmn999Form.addHiddenParam("adr010searchFlg", form.getAdr010searchFlg());
        cmn999Form.addHiddenParam("adr010EditAdrSid", form.getAdr010EditAdrSid());
        cmn999Form.addHiddenParam("adr010orderKey", form.getAdr010orderKey());

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
    private ActionForward __setKanryoTourokuDsp(
        ActionMapping map,
        Adr290Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("listback");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        int procMode = form.getAdr280ProcMode();
        if (procMode == GSConstAddress.PROCMODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object",
                            gsMsg.getMessage(req, "cmn.category")));
        } else if (procMode == GSConstUser.PROCMODE_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object",
                            gsMsg.getMessage(req, "cmn.category")));
        }

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("adr010cmdMode", form.getAdr010cmdMode());
        cmn999Form.addHiddenParam("adr010searchFlg", form.getAdr010searchFlg());
        cmn999Form.addHiddenParam("adr010EditAdrSid", form.getAdr010EditAdrSid());
        cmn999Form.addHiddenParam("adr010orderKey", form.getAdr010orderKey());
        cmn999Form.addHiddenParam("adr010sortKey", form.getAdr010sortKey());
        cmn999Form.addHiddenParam("adr010page", form.getAdr010page());
        cmn999Form.addHiddenParam("adr010pageTop", form.getAdr010pageTop());
        cmn999Form.addHiddenParam("adr010pageBottom", form.getAdr010pageBottom());
        cmn999Form.addHiddenParam("adr010code", form.getAdr010code());
        cmn999Form.addHiddenParam("adr010coName", form.getAdr010coName());
        cmn999Form.addHiddenParam("adr010coNameKn", form.getAdr010coNameKn());
        cmn999Form.addHiddenParam("adr010coBaseName", form.getAdr010coBaseName());
        cmn999Form.addHiddenParam("adr010atiSid", form.getAdr010atiSid());
        cmn999Form.addHiddenParam("adr010tdfk", form.getAdr010tdfk());
        cmn999Form.addHiddenParam("adr010biko", form.getAdr010biko());
        cmn999Form.addHiddenParam("adr010syozoku", form.getAdr010syozoku());
        cmn999Form.addHiddenParam("adr010svCode", form.getAdr010svCode());
        cmn999Form.addHiddenParam("adr010svCoName", form.getAdr010svCoName());
        cmn999Form.addHiddenParam("adr010svCoNameKn", form.getAdr010svCoNameKn());
        cmn999Form.addHiddenParam("adr010svCoBaseName", form.getAdr010svCoBaseName());
        cmn999Form.addHiddenParam("adr010svAtiSid", form.getAdr010svAtiSid());
        cmn999Form.addHiddenParam("adr010svTdfk", form.getAdr010svTdfk());
        cmn999Form.addHiddenParam("adr010svBiko", form.getAdr010svBiko());
        cmn999Form.addHiddenParam("adr010SearchComKana", form.getAdr010SearchComKana());
        cmn999Form.addHiddenParam("adr010svSearchComKana", form.getAdr010svSearchComKana());
        cmn999Form.addHiddenParam("adr010SearchKana", form.getAdr010SearchKana());
        cmn999Form.addHiddenParam("adr010svSearchKana", form.getAdr010svSearchKana());
        cmn999Form.addHiddenParam("adr010tantoGroup", form.getAdr010tantoGroup());
        cmn999Form.addHiddenParam("adr010tantoUser", form.getAdr010tantoUser());
        cmn999Form.addHiddenParam("adr010svTantoGroup", form.getAdr010svTantoGroup());
        cmn999Form.addHiddenParam("adr010svTantoUser", form.getAdr010svTantoUser());
        cmn999Form.addHiddenParam("adr010unameSei", form.getAdr010unameSei());
        cmn999Form.addHiddenParam("adr010unameMei", form.getAdr010unameMei());
        cmn999Form.addHiddenParam("adr010unameSeiKn", form.getAdr010unameSeiKn());
        cmn999Form.addHiddenParam("adr010unameMeiKn", form.getAdr010unameMeiKn());
        cmn999Form.addHiddenParam("adr010detailCoName", form.getAdr010detailCoName());
        cmn999Form.addHiddenParam("adr010position", form.getAdr010position());
        cmn999Form.addHiddenParam("adr010mail", form.getAdr010mail());
        cmn999Form.addHiddenParam("adr010detailTantoGroup", form.getAdr010detailTantoGroup());
        cmn999Form.addHiddenParam("adr010detailTantoUser", form.getAdr010detailTantoUser());
        cmn999Form.addHiddenParam("adr010detailAtiSid", form.getAdr010detailAtiSid());
        cmn999Form.addHiddenParam("adr010svUnameSei", form.getAdr010svUnameSei());
        cmn999Form.addHiddenParam("adr010svUnameMei", form.getAdr010svUnameMei());
        cmn999Form.addHiddenParam("adr010svUnameSeiKn", form.getAdr010svUnameSeiKn());
        cmn999Form.addHiddenParam("adr010svUnameMeiKn", form.getAdr010svUnameMeiKn());
        cmn999Form.addHiddenParam("adr010svDetailCoName", form.getAdr010svDetailCoName());
        cmn999Form.addHiddenParam("adr010svPosition", form.getAdr010svPosition());
        cmn999Form.addHiddenParam("adr010svMail", form.getAdr010svMail());
        cmn999Form.addHiddenParam("adr010svDetailTantoGroup", form.getAdr010svDetailTantoGroup());
        cmn999Form.addHiddenParam("adr010svDetailTantoUser", form.getAdr010svDetailTantoUser());
        cmn999Form.addHiddenParam("adr010svDetailAtiSid", form.getAdr010svDetailAtiSid());
        cmn999Form.addHiddenParam("adr010tantoGroupContact", form.getAdr010tantoGroupContact());
        cmn999Form.addHiddenParam("adr010tantoUserContact", form.getAdr010tantoUserContact());
        cmn999Form.addHiddenParam("adr010unameSeiContact", form.getAdr010unameSeiContact());
        cmn999Form.addHiddenParam("adr010unameMeiContact", form.getAdr010unameMeiContact());
        cmn999Form.addHiddenParam("adr010CoNameContact", form.getAdr010CoNameContact());
        cmn999Form.addHiddenParam("adr010CoBaseNameContact", form.getAdr010CoBaseNameContact());
        cmn999Form.addHiddenParam("adr010ProjectContact", form.getAdr010ProjectContact());
        cmn999Form.addHiddenParam("adr010TempFilekbnContact", form.getAdr010TempFilekbnContact());
        cmn999Form.addHiddenParam("adr010SltYearFrContact", form.getAdr010SltYearFrContact());
        cmn999Form.addHiddenParam("adr010SltMonthFrContact", form.getAdr010SltMonthFrContact());
        cmn999Form.addHiddenParam("adr010SltDayFrContact", form.getAdr010SltDayFrContact());
        cmn999Form.addHiddenParam("adr010SltYearToContact", form.getAdr010SltYearToContact());
        cmn999Form.addHiddenParam("adr010SltMonthToContact", form.getAdr010SltMonthToContact());
        cmn999Form.addHiddenParam("adr010SltDayToContact", form.getAdr010SltDayToContact());
        cmn999Form.addHiddenParam("adr010SyubetsuContact", form.getAdr010SyubetsuContact());
        cmn999Form.addHiddenParam("adr010SearchWordContact", form.getAdr010SearchWordContact());
        cmn999Form.addHiddenParam("adr010KeyWordkbnContact", form.getAdr010KeyWordkbnContact());
        cmn999Form.addHiddenParam("adr010dateNoKbn", form.getAdr010dateNoKbn());
        cmn999Form.addHiddenParam("adr010svTantoGroupContact", form.getAdr010svTantoGroupContact());
        cmn999Form.addHiddenParam("adr010svTantoUserContact", form.getAdr010svTantoUserContact());
        cmn999Form.addHiddenParam("adr010svUnameSeiContact", form.getAdr010svUnameSeiContact());
        cmn999Form.addHiddenParam("adr010svUnameMeiContact", form.getAdr010svUnameMeiContact());
        cmn999Form.addHiddenParam("adr010svCoNameContact", form.getAdr010svCoNameContact());
        cmn999Form.addHiddenParam("adr010svCoBaseNameContact", form.getAdr010svCoBaseNameContact());
        cmn999Form.addHiddenParam("adr010svProjectContact", form.getAdr010svProjectContact());
        cmn999Form.addHiddenParam("adr010SvTempFilekbnContact",
                                                             form.getAdr010SvTempFilekbnContact());
        cmn999Form.addHiddenParam("adr010svSltYearFrContact", form.getAdr010svSltYearFrContact());
        cmn999Form.addHiddenParam("adr010svSltMonthFrContact", form.getAdr010svSltMonthFrContact());
        cmn999Form.addHiddenParam("adr010svSltDayFrContact", form.getAdr010svSltDayFrContact());
        cmn999Form.addHiddenParam("adr010svSltYearToContact", form.getAdr010svSltYearToContact());
        cmn999Form.addHiddenParam("adr010svSltMonthToContact", form.getAdr010svSltMonthToContact());
        cmn999Form.addHiddenParam("adr010svSltDayToContact", form.getAdr010svSltDayToContact());
        cmn999Form.addHiddenParam("adr010svSyubetsuContact", form.getAdr010svSyubetsuContact());
        cmn999Form.addHiddenParam("adr010svSearchWordContact", form.getAdr010svSearchWordContact());
        cmn999Form.addHiddenParam("adr010SvKeyWordkbnContact", form.getAdr010SvKeyWordkbnContact());
        cmn999Form.addHiddenParam("adr010svdateNoKbn", form.getAdr010svdateNoKbn());
        cmn999Form.addHiddenParam("adr010svSyozoku", form.getAdr010svSyozoku());
        cmn999Form.addHiddenParam("adr010InitDspContactFlg", form.getAdr010InitDspContactFlg());
        cmn999Form.addHiddenParam("projectKbnSv", form.getProjectKbnSv());
        cmn999Form.addHiddenParam("statusKbnSv", form.getStatusKbnSv());
        cmn999Form.addHiddenParam("selectingProjectSv", form.getSelectingProjectSv());
        cmn999Form.addHiddenParam("projectKbn", form.getProjectKbn());
        cmn999Form.addHiddenParam("statusKbn", form.getStatusKbn());
        cmn999Form.addHiddenParam("selectingProject", form.getSelectingProject());
        cmn999Form.addHiddenParam("adr010selectCategory", form.getAdr010selectCategory());

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 削除確認画面<br>
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
        Adr290Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        int editSid = form.getAdr280EditSid();
        //ラベル一覧を取得
        Adr290Biz biz = new Adr290Biz(getRequestModel(req));
        ArrayList<String> list = biz.getDeleteLabList(con, editSid);
        String delMsg = "";
        boolean labExis = false;

        if (list.size() > 0) {
            //対象のカテゴリにラベルが存在する場合
            for (int i = 0; i < list.size(); i++) {
                delMsg += "・";
                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(list.get(i), ""));

                //最後の要素以外は改行を挿入
                if (i < list.size() - 1) {
                    delMsg += "<br>";
                }
            }
            form.setCatKbn(GSConstAddress.CATEGORY_EXIST_YES);
            labExis = true;
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("adr280back");
        cmn999Form.setUrlCancel(
                forwardCancel.getPath() + "?" + GSConst.P_CMD + "=input_back");

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("deleteok");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //カテゴリ内にラベルが存在する場合
        if (labExis) {
            //メッセージ
            MessageResources msgRes = getResources(req);
            String msg = biz.getDeleteLabAndCatMsg(con, form.getAdr280EditSid(), msgRes, delMsg);
            cmn999Form.setMessage(msg);
        } else {
            //メッセージ
            MessageResources msgRes = getResources(req);
            String msg = biz.getDeletePosMsg(con, form.getAdr280EditSid(), msgRes);
            cmn999Form.setMessage(msg);
        }

        //画面パラメータをセット
        cmn999Form.addHiddenParam("adr280EditSid", form.getAdr280EditSid());

        cmn999Form.addHiddenParam("adr010cmdMode", form.getAdr010cmdMode());
        cmn999Form.addHiddenParam("adr010searchFlg", form.getAdr010searchFlg());
        cmn999Form.addHiddenParam("adr010EditAdrSid", form.getAdr010EditAdrSid());
        cmn999Form.addHiddenParam("adr010orderKey", form.getAdr010orderKey());
        cmn999Form.addHiddenParam("adr010orderKey", form.getAdr010orderKey());
        cmn999Form.addHiddenParam("adr290bikou", form.getAdr290bikou());
        cmn999Form.addHiddenParam("adr290CategoryName", form.getAdr290CategoryName());
        cmn999Form.addHiddenParam("catKbn", form.getCatKbn());
        cmn999Form.addHiddenParam("adr280ProcMode", form.getAdr280ProcMode());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] 権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward checkPow(ActionMapping map,
            HttpServletRequest req, Connection con)
    throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel usModel = getSessionUserModel(req);
        boolean gsAdmFlg = cmnBiz.isPluginAdmin(con, usModel, GSConstAddress.PLUGIN_ID_ADDRESS);

        if (!gsAdmFlg) {
            con.setAutoCommit(true);
            AdrAconfDao dao = new AdrAconfDao(con);
            AdrAconfModel model = dao.selectAconf();
            con.setAutoCommit(false);
            if (model != null && model.getAacAlbEdit() == GSConstAddress.POW_LIMIT) {
                return getNotAdminSeniPage(map, req);
            }
        }
        return null;
    }


}
