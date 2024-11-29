package jp.groupsession.v2.usr.usr046;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrConfDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.AbstractUsrAction;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

/**
 * <br>[機  能] ユーザ情報 ラベル登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr046Action extends AbstractUsrAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr046Action.class);

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

        log__.debug("START_Usr046");
        ActionForward forward = null;

        Usr046Form thisForm = (Usr046Form) form;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("usr046ok")) {
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

        } else if (cmd.equals("labelListback")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward(cmd);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con, INIT);
        }

        log__.debug("END_Usr046");
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
     * @param kbn 遷移元区分
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Usr046Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int kbn) throws SQLException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Usr046Biz biz = new Usr046Biz(getRequestModel(req));

        Usr046ParamModel paramMdl = new Usr046ParamModel();
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
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doOk(
            ActionMapping map,
            Usr046Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = form.validateUsr046(con, getRequestModel(req));
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
        Usr046Biz biz = new Usr046Biz(getRequestModel(req));
        Usr046ParamModel paramMdl = new Usr046ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, con, cntCon, userSid);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        /** メッセージ 登録 **/
        String entry = "";
        int procMode = paramMdl.getUsr044ProcMode();
        if (procMode == GSConstUser.PROCMODE_ADD) {
            entry = gsMsg.getMessage("cmn.entry");
        } else {
            entry = gsMsg.getMessage("cmn.change");
        }

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                entry, GSConstLog.LEVEL_INFO,
                "[" + gsMsg.getMessage("cmn.label.name") + "]" + form.getUsr046LabelName()
                + "[" + gsMsg.getMessage("cmn.memo") + "]"
                        + NullDefault.getString(form.getUsr046bikou(), ""));

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
        Usr046Form form,
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
        Usr046Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //業種を削除する
        Usr046Biz biz = new Usr046Biz(getRequestModel(req));

        Usr046ParamModel paramMdl = new Usr046ParamModel();
        paramMdl.setParam(form);
        biz.deleteAlb(con, paramMdl);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 削除 **/
        String delete = gsMsg.getMessage(req, "cmn.delete");

        //ログ出力処理
        UsrCommonBiz usrBiz = new UsrCommonBiz(con, getRequestModel(req));
        String opCode = delete;

        usrBiz.outPutLog(
                opCode,
                GSConstLog.LEVEL_TRACE,
                "[" + gsMsg.getMessage(req, "cmn.label.name") + "]"
                + form.getUsr046LabelName(), map.getType());


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
        Usr046Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("usr044");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //ラベル
        String textLabel = gsMsg.getMessage(req, "cmn.label");
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", textLabel));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("usr043EditSid", form.getUsr043EditSid());

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("usr040cmdMode", form.getUsr040cmdMode());
        cmn999Form.addHiddenParam("usr040orderKey", form.getUsr040orderKey());
        cmn999Form.addHiddenParam("usr040sortKey", form.getUsr040sortKey());
        cmn999Form.addHiddenParam("usr040orderKey2", form.getUsr040orderKey2());
        cmn999Form.addHiddenParam("usr040sortKey2", form.getUsr040sortKey2());
        cmn999Form.addHiddenParam("usr040pageNum1", form.getUsr040pageNum1());
        cmn999Form.addHiddenParam("usr040pageNum2", form.getUsr040pageNum2());
        cmn999Form.addHiddenParam("usr040SearchKana", form.getUsr040SearchKana());
        cmn999Form.addHiddenParam("selectgsid", form.getSelectgsid());
        cmn999Form.addHiddenParam("usr040Keyword", form.getUsr040Keyword());
        cmn999Form.addHiddenParam("usr040KeyKbnShainno", form.getUsr040KeyKbnShainno());
        cmn999Form.addHiddenParam("usr040KeyKbnName", form.getUsr040KeyKbnName());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKn", form.getUsr040KeyKbnNameKn());
        cmn999Form.addHiddenParam("usr040KeyKbnMail", form.getUsr040KeyKbnMail());
        cmn999Form.addHiddenParam("usr040KeyKbnTel", form.getUsr040KeyKbnTel());
        cmn999Form.addHiddenParam("usr040agefrom", form.getUsr040agefrom());
        cmn999Form.addHiddenParam("usr040ageto", form.getUsr040ageto());
        cmn999Form.addHiddenParam("usr040yakushoku", form.getUsr040yakushoku());
        cmn999Form.addHiddenParam("usr040tdfkCd", form.getUsr040tdfkCd());
        cmn999Form.addHiddenParam("usr040seibetu", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFr", form.getUsr040entranceYearFr());
        cmn999Form.addHiddenParam("usr040entranceMonthFr", form.getUsr040entranceMonthFr());
        cmn999Form.addHiddenParam("usr040entranceDayFr", form.getUsr040entranceDayFr());
        cmn999Form.addHiddenParam("usr040entranceYearTo", form.getUsr040entranceYearTo());
        cmn999Form.addHiddenParam("usr040entranceMonthTo", form.getUsr040entranceMonthTo());
        cmn999Form.addHiddenParam("usr040entranceDayTo", form.getUsr040entranceDayTo());
        if (form.getUsr040labSid() != null) {
            for (int i = 0; i < form.getUsr040labSid().length; i++) {
                cmn999Form.addHiddenParam("usr040labSid", form.getUsr040labSid()[i]);
            }
        }

        cmn999Form.addHiddenParam("selectgsidSave", form.getSelectgsidSave());
        cmn999Form.addHiddenParam("usr040SearchKanaSave", form.getUsr040SearchKanaSave());
        cmn999Form.addHiddenParam("usr040KeywordSave", form.getUsr040KeywordSave());
        cmn999Form.addHiddenParam("usr040KeyKbnShainnoSave", form.getUsr040KeyKbnShainnoSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameSave", form.getUsr040KeyKbnNameSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKnSave", form.getUsr040KeyKbnNameKnSave());
        cmn999Form.addHiddenParam("usr040KeyKbnMailSave", form.getUsr040KeyKbnMailSave());
        cmn999Form.addHiddenParam("usr040KeyKbnTelSave", form.getUsr040KeyKbnTelSave());
        cmn999Form.addHiddenParam("usr040agefromSave", form.getUsr040agefromSave());
        cmn999Form.addHiddenParam("usr040agetoSave", form.getUsr040agetoSave());
        cmn999Form.addHiddenParam("usr040yakushokuSave", form.getUsr040yakushokuSave());
        cmn999Form.addHiddenParam("usr040tdfkCdSave", form.getUsr040tdfkCdSave());
        cmn999Form.addHiddenParam("usr040seibetuSave", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFrSave", form.getUsr040entranceYearFrSave());
        cmn999Form.addHiddenParam("usr040entranceMonthFrSave", form.getUsr040entranceMonthFrSave());
        cmn999Form.addHiddenParam("usr040entranceDayFrSave", form.getUsr040entranceDayFrSave());
        cmn999Form.addHiddenParam("usr040entranceYearToSave", form.getUsr040entranceYearToSave());
        cmn999Form.addHiddenParam("usr040entranceMonthToSave", form.getUsr040entranceMonthToSave());
        cmn999Form.addHiddenParam("usr040entranceDayToSave", form.getUsr040entranceDayToSave());
        if (form.getUsr040labSidSave() != null && form.getUsr040labSidSave().length != 0) {
            for (int n = 0; n < form.getUsr040labSidSave().length; n++) {
                cmn999Form.addHiddenParam("usr040labSidSave", form.getUsr040labSidSave()[n]);
            }
        }

        cmn999Form.addHiddenParam("usr040SearchFlg", form.getUsr040SearchFlg());
        cmn999Form.addHiddenParam("usr040DspFlg", form.getUsr040DspFlg());
        cmn999Form.addHiddenParam("usr040GrpSearchGId", form.getUsr040GrpSearchGId());
        cmn999Form.addHiddenParam("usr040GrpSearchGName", form.getUsr040GrpSearchGName());
        cmn999Form.addHiddenParam("usr040GrpSearchGIdSave", form.getUsr040GrpSearchGIdSave());
        cmn999Form.addHiddenParam("usr040GrpSearchGNameSave", form.getUsr040GrpSearchGNameSave());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    
    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setTourokuKanryoDsp(
        ActionMapping map,
        Usr046Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("labelListback");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        int procMode = form.getUsr044ProcMode();
        GsMessage gsMsg = new GsMessage();
        //ラベル
        String textLabel = gsMsg.getMessage(req, "cmn.label");
        if (procMode == GSConstUser.PROCMODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", textLabel));
        } else if (procMode == GSConstUser.PROCMODE_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", textLabel));
        }

        //画面パラメータをセット
        cmn999Form.addHiddenParam("usr043EditSid", form.getUsr043EditSid());

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("usr040cmdMode", form.getUsr040cmdMode());
        cmn999Form.addHiddenParam("usr040orderKey", form.getUsr040orderKey());
        cmn999Form.addHiddenParam("usr040sortKey", form.getUsr040sortKey());
        cmn999Form.addHiddenParam("usr040orderKey2", form.getUsr040orderKey2());
        cmn999Form.addHiddenParam("usr040sortKey2", form.getUsr040sortKey2());
        cmn999Form.addHiddenParam("usr040pageNum1", form.getUsr040pageNum1());
        cmn999Form.addHiddenParam("usr040pageNum2", form.getUsr040pageNum2());
        cmn999Form.addHiddenParam("usr040SearchKana", form.getUsr040SearchKana());
        cmn999Form.addHiddenParam("selectgsid", form.getSelectgsid());
        cmn999Form.addHiddenParam("usr040Keyword", form.getUsr040Keyword());
        cmn999Form.addHiddenParam("usr040KeyKbnShainno", form.getUsr040KeyKbnShainno());
        cmn999Form.addHiddenParam("usr040KeyKbnName", form.getUsr040KeyKbnName());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKn", form.getUsr040KeyKbnNameKn());
        cmn999Form.addHiddenParam("usr040KeyKbnMail", form.getUsr040KeyKbnMail());
        cmn999Form.addHiddenParam("usr040KeyKbnTel", form.getUsr040KeyKbnTel());
        cmn999Form.addHiddenParam("usr040agefrom", form.getUsr040agefrom());
        cmn999Form.addHiddenParam("usr040ageto", form.getUsr040ageto());
        cmn999Form.addHiddenParam("usr040yakushoku", form.getUsr040yakushoku());
        cmn999Form.addHiddenParam("usr040tdfkCd", form.getUsr040tdfkCd());
        cmn999Form.addHiddenParam("usr040seibetu", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFr", form.getUsr040entranceYearFr());
        cmn999Form.addHiddenParam("usr040entranceMonthFr", form.getUsr040entranceMonthFr());
        cmn999Form.addHiddenParam("usr040entranceDayFr", form.getUsr040entranceDayFr());
        cmn999Form.addHiddenParam("usr040entranceYearTo", form.getUsr040entranceYearTo());
        cmn999Form.addHiddenParam("usr040entranceMonthTo", form.getUsr040entranceMonthTo());
        cmn999Form.addHiddenParam("usr040entranceDayTo", form.getUsr040entranceDayTo());
        if (form.getUsr040labSid() != null) {
            for (int i = 0; i < form.getUsr040labSid().length; i++) {
                cmn999Form.addHiddenParam("usr040labSid", form.getUsr040labSid()[i]);
            }
        }

        cmn999Form.addHiddenParam("selectgsidSave", form.getSelectgsidSave());
        cmn999Form.addHiddenParam("usr040SearchKanaSave", form.getUsr040SearchKanaSave());
        cmn999Form.addHiddenParam("usr040KeywordSave", form.getUsr040KeywordSave());
        cmn999Form.addHiddenParam("usr040KeyKbnShainnoSave", form.getUsr040KeyKbnShainnoSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameSave", form.getUsr040KeyKbnNameSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKnSave", form.getUsr040KeyKbnNameKnSave());
        cmn999Form.addHiddenParam("usr040KeyKbnMailSave", form.getUsr040KeyKbnMailSave());
        cmn999Form.addHiddenParam("usr040KeyKbnTelSave", form.getUsr040KeyKbnTelSave());
        cmn999Form.addHiddenParam("usr040agefromSave", form.getUsr040agefromSave());
        cmn999Form.addHiddenParam("usr040agetoSave", form.getUsr040agetoSave());
        cmn999Form.addHiddenParam("usr040yakushokuSave", form.getUsr040yakushokuSave());
        cmn999Form.addHiddenParam("usr040tdfkCdSave", form.getUsr040tdfkCdSave());
        cmn999Form.addHiddenParam("usr040seibetuSave", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFrSave", form.getUsr040entranceYearFrSave());
        cmn999Form.addHiddenParam("usr040entranceMonthFrSave", form.getUsr040entranceMonthFrSave());
        cmn999Form.addHiddenParam("usr040entranceDayFrSave", form.getUsr040entranceDayFrSave());
        cmn999Form.addHiddenParam("usr040entranceYearToSave", form.getUsr040entranceYearToSave());
        cmn999Form.addHiddenParam("usr040entranceMonthToSave", form.getUsr040entranceMonthToSave());
        cmn999Form.addHiddenParam("usr040entranceDayToSave", form.getUsr040entranceDayToSave());
        if (form.getUsr040labSidSave() != null && form.getUsr040labSidSave().length != 0) {
            for (int n = 0; n < form.getUsr040labSidSave().length; n++) {
                cmn999Form.addHiddenParam("usr040labSidSave", form.getUsr040labSidSave()[n]);
            }
        }

        cmn999Form.addHiddenParam("usr040SearchFlg", form.getUsr040SearchFlg());
        cmn999Form.addHiddenParam("usr040DspFlg", form.getUsr040DspFlg());
        cmn999Form.addHiddenParam("usr040GrpSearchGId", form.getUsr040GrpSearchGId());
        cmn999Form.addHiddenParam("usr040GrpSearchGName", form.getUsr040GrpSearchGName());
        cmn999Form.addHiddenParam("usr040GrpSearchGIdSave", form.getUsr040GrpSearchGIdSave());
        cmn999Form.addHiddenParam("usr040GrpSearchGNameSave", form.getUsr040GrpSearchGNameSave());

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
        Usr046Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);


        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("usr046");
        cmn999Form.setUrlCancel(
                forwardCancel.getPath() + "?" + GSConst.P_CMD + "=input_back");

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("usr046");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //メッセージ
        MessageResources msgRes = getResources(req);
        Usr046Biz biz = new Usr046Biz(getRequestModel(req));
        String msg = biz.getDeletePosMsg(con, form.getLabelEditSid(), msgRes);
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("usr046LabelName", form.getUsr046LabelName());
        cmn999Form.addHiddenParam("usr046bikou", form.getUsr046bikou());
        cmn999Form.addHiddenParam("usr043EditSid", form.getUsr043EditSid());
        cmn999Form.addHiddenParam("labelEditSid", form.getLabelEditSid());
        cmn999Form.addHiddenParam("usr044ProcMode", form.getUsr044ProcMode());
        cmn999Form.addHiddenParam("usr043EditSid", form.getUsr043EditSid());
        cmn999Form.addHiddenParam("usr046CatSid", form.getUsr046CatSid());

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("usr040cmdMode", form.getUsr040cmdMode());
        cmn999Form.addHiddenParam("usr040orderKey", form.getUsr040orderKey());
        cmn999Form.addHiddenParam("usr040sortKey", form.getUsr040sortKey());
        cmn999Form.addHiddenParam("usr040orderKey2", form.getUsr040orderKey2());
        cmn999Form.addHiddenParam("usr040sortKey2", form.getUsr040sortKey2());
        cmn999Form.addHiddenParam("usr040pageNum1", form.getUsr040pageNum1());
        cmn999Form.addHiddenParam("usr040pageNum2", form.getUsr040pageNum2());
        cmn999Form.addHiddenParam("usr040SearchKana", form.getUsr040SearchKana());
        cmn999Form.addHiddenParam("selectgsid", form.getSelectgsid());
        cmn999Form.addHiddenParam("usr040Keyword", form.getUsr040Keyword());
        cmn999Form.addHiddenParam("usr040KeyKbnShainno", form.getUsr040KeyKbnShainno());
        cmn999Form.addHiddenParam("usr040KeyKbnName", form.getUsr040KeyKbnName());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKn", form.getUsr040KeyKbnNameKn());
        cmn999Form.addHiddenParam("usr040KeyKbnMail", form.getUsr040KeyKbnMail());
        cmn999Form.addHiddenParam("usr040KeyKbnTel", form.getUsr040KeyKbnTel());
        cmn999Form.addHiddenParam("usr040agefrom", form.getUsr040agefrom());
        cmn999Form.addHiddenParam("usr040ageto", form.getUsr040ageto());
        cmn999Form.addHiddenParam("usr040yakushoku", form.getUsr040yakushoku());
        cmn999Form.addHiddenParam("usr040tdfkCd", form.getUsr040tdfkCd());
        cmn999Form.addHiddenParam("usr040seibetu", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFr", form.getUsr040entranceYearFr());
        cmn999Form.addHiddenParam("usr040entranceMonthFr", form.getUsr040entranceMonthFr());
        cmn999Form.addHiddenParam("usr040entranceDayFr", form.getUsr040entranceDayFr());
        cmn999Form.addHiddenParam("usr040entranceYearTo", form.getUsr040entranceYearTo());
        cmn999Form.addHiddenParam("usr040entranceMonthTo", form.getUsr040entranceMonthTo());
        cmn999Form.addHiddenParam("usr040entranceDayTo", form.getUsr040entranceDayTo());
        if (form.getUsr040labSid() != null) {
            for (int i = 0; i < form.getUsr040labSid().length; i++) {
                cmn999Form.addHiddenParam("usr040labSid", form.getUsr040labSid()[i]);
            }
        }

        cmn999Form.addHiddenParam("selectgsidSave", form.getSelectgsidSave());
        cmn999Form.addHiddenParam("usr040SearchKanaSave", form.getUsr040SearchKanaSave());
        cmn999Form.addHiddenParam("usr040KeywordSave", form.getUsr040KeywordSave());
        cmn999Form.addHiddenParam("usr040KeyKbnShainnoSave", form.getUsr040KeyKbnShainnoSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameSave", form.getUsr040KeyKbnNameSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKnSave", form.getUsr040KeyKbnNameKnSave());
        cmn999Form.addHiddenParam("usr040KeyKbnMailSave", form.getUsr040KeyKbnMailSave());
        cmn999Form.addHiddenParam("usr040KeyKbnTelSave", form.getUsr040KeyKbnTelSave());
        cmn999Form.addHiddenParam("usr040agefromSave", form.getUsr040agefromSave());
        cmn999Form.addHiddenParam("usr040agetoSave", form.getUsr040agetoSave());
        cmn999Form.addHiddenParam("usr040yakushokuSave", form.getUsr040yakushokuSave());
        cmn999Form.addHiddenParam("usr040tdfkCdSave", form.getUsr040tdfkCdSave());
        cmn999Form.addHiddenParam("usr040seibetuSave", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFrSave", form.getUsr040entranceYearFrSave());
        cmn999Form.addHiddenParam("usr040entranceMonthFrSave", form.getUsr040entranceMonthFrSave());
        cmn999Form.addHiddenParam("usr040entranceDayFrSave", form.getUsr040entranceDayFrSave());
        cmn999Form.addHiddenParam("usr040entranceYearToSave", form.getUsr040entranceYearToSave());
        cmn999Form.addHiddenParam("usr040entranceMonthToSave", form.getUsr040entranceMonthToSave());
        cmn999Form.addHiddenParam("usr040entranceDayToSave", form.getUsr040entranceDayToSave());
        if (form.getUsr040labSidSave() != null && form.getUsr040labSidSave().length != 0) {
            for (int n = 0; n < form.getUsr040labSidSave().length; n++) {
                cmn999Form.addHiddenParam("usr040labSidSave", form.getUsr040labSidSave()[n]);
            }
        }

        cmn999Form.addHiddenParam("usr040SearchFlg", form.getUsr040SearchFlg());
        cmn999Form.addHiddenParam("usr040DspFlg", form.getUsr040DspFlg());

        cmn999Form.addHiddenParam("usr040GrpSearchGId", form.getUsr040GrpSearchGId());
        cmn999Form.addHiddenParam("usr040GrpSearchGName", form.getUsr040GrpSearchGName());
        cmn999Form.addHiddenParam("usr040GrpSearchGIdSave", form.getUsr040GrpSearchGIdSave());
        cmn999Form.addHiddenParam("usr040GrpSearchGNameSave", form.getUsr040GrpSearchGNameSave());

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

        BaseUserModel buMdl = getSessionUserModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstUser.PLUGIN_ID_USER);

        if (!adminUser) {
            con.setAutoCommit(true);
            CmnLabelUsrConfDao dao = new CmnLabelUsrConfDao(con);
            CmnLabelUsrConfModel model = dao.select();
            con.setAutoCommit(false);
            if (model != null && model.getLufEdit() == GSConstUser.POW_LIMIT) {
                return getNotAdminSeniPage(map, req);
            }
        }
        return null;
    }

}
