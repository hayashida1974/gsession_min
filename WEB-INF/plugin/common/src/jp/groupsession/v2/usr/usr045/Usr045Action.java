package jp.groupsession.v2.usr.usr045;

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
 * <br>[機  能] ユーザ情報 カテゴリ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr045Action extends AbstractUsrAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr045Action.class);

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

        log__.debug("START_Usr045");
        ActionForward forward = null;

        Usr045Form thisForm = (Usr045Form) form;

        //カテゴリ「未設定」チェック
        if (thisForm.getUsr043EditSid() == 0) {
            return __setNotDelCategory(map, thisForm, req);
        }

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
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

        } else if (cmd.equals("usr043back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("listback");

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Usr045");
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
        Usr045Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Usr045Biz biz = new Usr045Biz(getRequestModel(req));

        Usr045ParamModel paramMdl = new Usr045ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl);
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
        Usr045Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = form.validateUsr045(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
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
        Usr045Biz biz = new Usr045Biz(getRequestModel(req));

        Usr045ParamModel paramMdl = new Usr045ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, con, cntCon, userSid);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        /** メッセージ 登録 **/
        String entry = "";
        int procMode = paramMdl.getUsr043ProcMode();
        if (procMode == GSConstUser.PROCMODE_ADD) {
            //登録
            entry = gsMsg.getMessage("cmn.entry");
        } else if (procMode == GSConstUser.PROCMODE_EDIT) {
            //更新
            entry = gsMsg.getMessage("cmn.change");
        }

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                entry, GSConstLog.LEVEL_INFO,
                "[" + gsMsg.getMessage("cmn.category.name") + "]" + form.getUsr045CategoryName()
                + "[" + gsMsg.getMessage("cmn.memo") + "]"
                        + NullDefault.getString(form.getUsr045bikou(), ""));


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
    private ActionForward __doDeleteCat(
        ActionMapping map,
        Usr045Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //削除チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.deleteCheck(con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
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
        Usr045Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //カテゴリを削除する
        Usr045Biz biz = new Usr045Biz(getRequestModel(req));

        Usr045ParamModel paramMdl = new Usr045ParamModel();
        paramMdl.setParam(form);
        biz.deleteCat(con, paramMdl);
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
                "[" + gsMsg.getMessage(req, "cmn.category.name") + "]"
                + form.getUsr045CategoryName(), map.getType());


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
        Usr045Form form,
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
        //カテゴリ
        String textCategory = gsMsg.getMessage(req, "cmn.category");

        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", textCategory));
        __setHiddenParam(form, cmn999Form);

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
        Usr045Form form,
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
        //カテゴリ
        String textCategory = gsMsg.getMessage(req, "cmn.category");
        int procMode = form.getUsr043ProcMode();
        if (procMode == GSConstUser.PROCMODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", textCategory));
        } else if (procMode == GSConstUser.PROCMODE_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", textCategory));
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
        Usr045Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        int editSid = form.getUsr043EditSid();
        //ラベル一覧を取得
        Usr045Biz biz = new Usr045Biz(getRequestModel(req));
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
            form.setCatKbn(GSConstUser.CATEGORY_EXIST_YES);
            labExis = true;
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("usr045");
        cmn999Form.setUrlCancel(
                forwardCancel.getPath() + "?" + GSConst.P_CMD + "=input_back");

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("usr045");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //カテゴリ内にラベルが存在する場合
        if (labExis) {
            //メッセージ
            MessageResources msgRes = getResources(req);
            String msg = biz.getDeleteLabAndCatMsg(con, form.getUsr043EditSid(), msgRes, delMsg);
            cmn999Form.setMessage(msg);
        } else {
            //メッセージ
            MessageResources msgRes = getResources(req);
            String msg = biz.getDeletePosMsg(con, form.getUsr043EditSid(), msgRes);
            cmn999Form.setMessage(msg);
        }

        __setHiddenParam(form, cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 画面のパラメータをcmn999にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param cmn999Form cmn999フォーム
     */
    private void __setHiddenParam(Usr045Form form, Cmn999Form cmn999Form) {
        //画面パラメータをセット
        cmn999Form.addHiddenParam("usr045CategoryName", form.getUsr045CategoryName());
        cmn999Form.addHiddenParam("usr045bikou", form.getUsr045bikou());
        cmn999Form.addHiddenParam("usr043EditSid", form.getUsr043EditSid());
        cmn999Form.addHiddenParam("usr043ProcMode", form.getUsr043ProcMode());
        cmn999Form.addHiddenParam("catKbn", form.getCatKbn());
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

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setNotDelCategory(
        ActionMapping map,
        Usr045Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("listback");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("error.not.delete.category"));

        __setHiddenParam(form, cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}
