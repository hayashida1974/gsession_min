package jp.groupsession.v2.wml.wml180kn;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール 送受信ログ手動削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml180knAction extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml180knAction.class);

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
        Wml180knForm thisForm = (Wml180knForm) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

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
    public ActionForward __doInit(ActionMapping map, Wml180knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        Wml180knParamModel paramMdl = new Wml180knParamModel();
        paramMdl.setParam(form);
        Wml180knBiz biz = new Wml180knBiz();
        biz.setInitData(getRequestModel(req), paramMdl);
        paramMdl.setFormData(form);

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
    public ActionForward __doDecision(ActionMapping map, Wml180knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateInput(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Wml180knBiz biz = new Wml180knBiz(con);

        //手動削除を行う
        Wml180knParamModel paramMdl = new Wml180knParamModel();
        paramMdl.setParam(form);
        biz.setData(paramMdl, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        StringBuilder buf = new StringBuilder();
        GsMessage gsMsg = new GsMessage(req);

        if (form.getWml180delKbn() == 0) {
            buf.append("[" + gsMsg.getMessage("wml.59") + "]"
                            + gsMsg.getMessage("wml.wml180.01"));
            buf.append("\r\n" + form.getWml180delYear() + gsMsg.getMessage("cmn.year2")
                              + " " + form.getWml180delMonth() + gsMsg.getMessage("cmn.months2")
                              + " " + form.getWml180delDay() + gsMsg.getMessage("cmn.day")
                              + " " + gsMsg.getMessage("wml.73"));
        } else if (form.getWml180delKbn() == 1) {
            buf.append("[" + gsMsg.getMessage("wml.59") + "]"
                            + gsMsg.getMessage("wml.05"));
            buf.append("\r\n" + form.getWml180delYearFr() + gsMsg.getMessage("cmn.year2")
            + " " + form.getWml180delMonthFr() + gsMsg.getMessage("cmn.month")
            + " " + form.getWml180delDayFr() + gsMsg.getMessage("cmn.day")
            + " ~ "
            + form.getWml180delYearTo() + gsMsg.getMessage("cmn.year2")
            + " " + form.getWml180delMonthTo() + gsMsg.getMessage("cmn.month")
            + " " + form.getWml180delDayTo() + gsMsg.getMessage("cmn.day"));
        } else if (form.getWml180delKbn() == 2) {
            buf.append("[" + gsMsg.getMessage("wml.59") + "]"
                            +  gsMsg.getMessage("cmn.delete.all"));
        }
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.outPutLog(map, getRequestModel(req), con,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                buf.toString());

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
        Wml180knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("admTool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "cmn.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, "cmn.manual.delete2")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}

