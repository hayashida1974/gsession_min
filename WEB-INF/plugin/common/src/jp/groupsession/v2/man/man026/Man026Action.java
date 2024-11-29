package jp.groupsession.v2.man.man026;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.man025.Man025Form;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート追加/拡張画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man026Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man026Action.class);

    /** 戻るボタン押下時CMD */
    private static String cmd_BACK = "backTemp";

    /** テンプレート追加ボタン押下時CMD */
    private static String cmd_ADD = "add";
    /** テンプレート変更ボタン押下時CMD */
    private static String cmd_EDIT = "edit";
    /** 日付入力ボタン押下時CMD */
    private static String cmd_INPUT_DATE = "normal";

    /** 登録完了メッセージ画面 */
    private static String cmd_FINISH_MSG = "gf_msg";
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
        Man026Form thisForm = (Man026Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        log__.debug(">>>tempSid :" + thisForm.getEditHltSid());

        if (cmd.equals(cmd_BACK)) {
            log__.debug(">>>戻るボタン押下");
            forward = map.findForward(cmd_BACK);
        } else if (cmd.equals(cmd_ADD)) {
            log__.debug(">>>追加ボタン押下");
            forward = __doCommitAdd(map, thisForm, req, res, con);
        } else if (cmd.equals(cmd_EDIT)) {
            log__.debug(">>>変更ボタン押下");
            forward = __doCommitEdit(map, thisForm, req, res, con);
        } else if (cmd.equals(cmd_INPUT_DATE)) {
            log__.debug(">>>日付入力ボタン押下");
            forward = map.findForward(cmd_INPUT_DATE);
        } else {
            log__.debug(">>>初期表示");
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }

        RequestModel reqMdl = getRequestModel(req);
        thisForm.setMan026MonthLabel(Man026Biz.getMonthLabel(reqMdl));
        thisForm.setMan026WeekLabel(Man026Biz.getWeekLabel(reqMdl));
        thisForm.setMan026DayOfWeekLabel(Man026Biz.getDayOfWeekLabel(reqMdl));

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Man026Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals(cmd_EDIT + "Temp")) {
            log__.debug("編集モード初期表示");
            form.setProcessMode(cmd_EDIT + "Temp");
            con.setAutoCommit(true);
            Man026ParamModel paramMdl = new Man026ParamModel();
            paramMdl.setParam(form);
            Man026Biz biz = new Man026Biz();
            biz.setInitData(paramMdl, con);
            paramMdl.setFormData(form);
            con.setAutoCommit(false);
        }

    }

    /**
     * <br>[機  能] データ登録処理
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
    private ActionForward __doCommitAdd(ActionMapping map, Man026Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(req, con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
        }

        RequestModel reqMdl = getRequestModel(req);
        ActionForward forward = map.findForward(cmd_FINISH_MSG);

        //SID採番
        BaseUserModel user = getSessionUserModel(req);
        MlCountMtController cntCon = getCountMtController(req);
        int sid = (int) cntCon.getSaibanNumber(SaibanModel.SBNSID_MAIN,
                SaibanModel.SBNSID_SUB_HLT, user.getUsrsid());

        Man026ParamModel paramMdl = new Man026ParamModel();
        paramMdl.setParam(form);
        Man026Biz biz = new Man026Biz();
        boolean result = biz.execDataTran(paramMdl, con, getSessionSid(req), sid);
        paramMdl.setFormData(form);

        if (result) {
            __setKanryou(map, req, form);
            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            GsMessage gsMsg = new GsMessage(reqMdl);
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    getInterMessage(reqMdl, "cmn.entry"), GSConstLog.LEVEL_INFO,
                    "[name]" + form.getMan026HltName());
        }

        return forward;
    }

    /**
     * <br>[機  能] データ編集処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommitEdit(ActionMapping map, Man026Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(req, con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
        }

        ActionForward forward = map.findForward(cmd_FINISH_MSG);

        Man026ParamModel paramMdl = new Man026ParamModel();
        paramMdl.setParam(form);
        Man026Biz biz = new Man026Biz();
        boolean result = biz.execDataTran(paramMdl, con,  getSessionSid(req), form.getEditHltSid());
        paramMdl.setFormData(form);

        if (result) {
            __setKanryou(map, req, form);
            //ログ出力
            RequestModel reqMdl = getRequestModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            GsMessage gsMsg = new GsMessage(reqMdl);
            String value = "";
            MainCommonBiz mainBiz = new MainCommonBiz();
            CmnHolidayTemplateDao holTempDao = new CmnHolidayTemplateDao(con);
            CmnHolidayTemplateModel holTempMdl = holTempDao.select(form.getEditHltSid());
            String date = mainBiz.getHolidayDate(reqMdl, holTempMdl);
            value += "[" + gsMsg.getMessage("cmn.holiday.name") + "] " + date;
            value += "\r\n" + "[" + gsMsg.getMessage("cmn.name4") + "] ";
            value += form.getMan026HltName();
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, value);
        }

        return forward;
    }

    /**
     * <br>[機  能] 登録完了画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKanryou(
        ActionMapping map,
        HttpServletRequest req,
        Man025Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("list");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        log__.debug("■完了画面msgState :" + msgState);
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.HOLIDAY_TEMPLATE_MSG)));

        cmn999Form.addHiddenParam("man020DspYear", form.getMan020DspYear());
        cmn999Form.addHiddenParam("man023CheckAll", form.getMan023CheckAll());
        cmn999Form.addHiddenParam("man023hltSid", form.getMan023hltSid());
        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>[機  能] セッションユーザーSIDを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return int セッションuserSID
     */
    private int getSessionSid(HttpServletRequest req) {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        return  usModel.getUsrsid(); //セッションユーザSID
    }

}
