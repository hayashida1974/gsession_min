package jp.groupsession.v2.man.man021;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン 管理者設定 休日設定/追加画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man021Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man021Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
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
        log__.debug("START_MAN021");
        ActionForward forward = null;
        Man021Form thisForm = (Man021Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("confirm")) {
            //追加(変更)ボタン押下
            forward = __doConfirm(map, thisForm, req, res, con);
        } else if (cmd.equals("backToList")) {
            //戻るボタン押下
            forward = map.findForward("backToList");
        } else {
            //初期表示
            if (cmd.equals("add")) {
                thisForm.setMan021CmdMode(GSConstMain.CMDMODE_HOLIDAY_ADD);
            } else if (cmd.equals("edit")) {
                thisForm.setMan021CmdMode(GSConstMain.CMDMODE_HOLIDAY_EDIT);
            }

            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }

        Man021Biz biz = new Man021Biz(getRequestModel(req));
        thisForm.setMan021MonthLabel(biz.getMonthLabel());
        thisForm.setMan021DayLabel(biz.getDayLabel());

        log__.debug("END_MAN021");
        return forward;
    }

    /**
     * 初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Man021Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);

        Man021ParamModel paramMdl = new Man021ParamModel();
        paramMdl.setParam(form);
        Man021Biz biz = new Man021Biz(getRequestModel(req));
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
    }

    /**
     * 追加(変更)ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doConfirm(ActionMapping map, Man021Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //日付追加・修正処理
        forward = __doDecision(map, form, req, res, con);
        return forward;
    }

    /**
     * 追加(変更)ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDecision(ActionMapping map, Man021Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(reqMdl, con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        boolean commit = false;
        String opCode = "";
        try {
            //休日情報の登録 or 変更
            Man021ParamModel paramMdl = new Man021ParamModel();
            paramMdl.setParam(form);
            Man021Biz biz = new Man021Biz(reqMdl);
            if (form.getMan021CmdMode() == GSConstMain.CMDMODE_HOLIDAY_ADD) {
                biz.insertHoliday(paramMdl, con);
                opCode = getInterMessage(req, "cmn.entry");
            } else if (form.getMan021CmdMode() == GSConstMain.CMDMODE_HOLIDAY_EDIT) {
                biz.updateHoliday(paramMdl, con);
                opCode = getInterMessage(req, "cmn.change");
            }
            paramMdl.setFormData(form);
            commit = true;
        } catch (SQLException e) {
            log__.error("休日登録・変更に失敗", e);
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        UDate holDate = new UDate();
        holDate.setMilliSecond(0);
        holDate.setYear(Integer.parseInt(form.getMan020DspYear()));
        holDate.setMonth(form.getMan021HolMonth());
        holDate.setDay(form.getMan021HolDay());
        //ログ出力
        GsMessage gsMsg = new GsMessage(req);
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                opCode, GSConstLog.LEVEL_INFO, "[date]" + holDate.getDateString());

        //休日登録・変更完了画面へ
        forward = __doCompDsp(map, form, req, res);

        return forward;
    }

    /**
     * 登録・変更完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Man021Form form,
            HttpServletRequest req, HttpServletResponse res) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        if (form.getMan021CmdMode() == GSConstMain.CMDMODE_HOLIDAY_ADD) {
            cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                        getInterMessage(req, GSConstMain.HOLIDAY_MSG)));
        } else {
            cmn999Form.setMessage(msgRes.getMessage("hensyu.kanryo.object",
                   getInterMessage(req, GSConstMain.HOLIDAY_MSG)));
        }
        cmn999Form.addHiddenParam("man020DspYear", form.getMan020DspYear());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

//    /**
//     * [機  能] 警告画面の設定処理を行う<br>
//     * [解  説] <br>
//     * [備  考] <br>
//     * @param map マッピング
//     * @param req リクエスト
//     * @return ActionForward
//     */
//    private ActionForward __setWarnDispParam(
//        ActionMapping map,
//        HttpServletRequest req) {
//
//        ActionForward forward = null;
//        forward = map.findForward("gf_auth");
//        return forward;
//
//    }
}
