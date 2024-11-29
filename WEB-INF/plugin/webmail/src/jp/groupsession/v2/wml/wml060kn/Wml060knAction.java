package jp.groupsession.v2.wml.wml060kn;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.WmlMailDeleteModel;

/**
 * <br>[機  能] WEBメール 手動データ削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml060knAction extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml060knAction.class);

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
        Wml060knForm thisForm = (Wml060knForm) form;

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
    public ActionForward __doInit(ActionMapping map, Wml060knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        Wml060knParamModel paramMdl = new Wml060knParamModel();
        paramMdl.setParam(form);
        Wml060knBiz biz = new Wml060knBiz();
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
    public ActionForward __doDecision(ActionMapping map, Wml060knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Wml060knBiz biz = new Wml060knBiz(con);

        //手動削除を行う
        Wml060knParamModel paramMdl = new Wml060knParamModel();
        paramMdl.setParam(form);
        RequestModel reqMdl = getRequestModel(req);
        List<WmlMailDeleteModel> wmMdlList = biz.setData(reqMdl, paramMdl, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        GsMessage gsMsg = new GsMessage(req);

        String msg = "";
        msg += "[" + gsMsg.getMessage("cmn.trash") + "]";
        msg += __logSettingKbn(req, NullDefault.getString(form.getWml060delKbn1(), "0"),
                String.valueOf(form.getWml060delYear1()),
                String.valueOf(form.getWml060delMonth1()),
                String.valueOf(form.getWml060delDay1()));
        msg += "\r\n[" + gsMsg.getMessage("wml.19") + "]";
        msg += __logSettingKbn(req, NullDefault.getString(form.getWml060delKbn2(), "0"),
                String.valueOf(form.getWml060delYear2()),
                String.valueOf(form.getWml060delMonth2()),
                String.valueOf(form.getWml060delDay2()));
        msg += "\r\n[" + gsMsg.getMessage("cmn.draft") + "]";
        msg += __logSettingKbn(req, NullDefault.getString(form.getWml060delKbn3(), "0"),
                String.valueOf(form.getWml060delYear3()),
                String.valueOf(form.getWml060delMonth3()),
                String.valueOf(form.getWml060delDay3()));
        msg += "\r\n[" + gsMsg.getMessage("wml.37") + "]";
        msg += __logSettingKbn(req, NullDefault.getString(form.getWml060delKbn4(), "0"),
                String.valueOf(form.getWml060delYear4()),
                String.valueOf(form.getWml060delMonth4()),
                String.valueOf(form.getWml060delDay4()));
        msg += "\r\n[" + gsMsg.getMessage("cmn.strage") + "]";
        msg += __logSettingKbn(req, NullDefault.getString(form.getWml060delKbn5(), "0"),
                String.valueOf(form.getWml060delYear5()),
                String.valueOf(form.getWml060delMonth5()),
                String.valueOf(form.getWml060delDay5()));

        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.outPutLog(map, reqMdl, con,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                msg);

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ログ メッセージ作成用
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param kbn 区分
     * @param year 年
     * @param month 月
     * @param day 日
     * @return メッセージ
     */
    private String __logSettingKbn(
        HttpServletRequest req, String kbn, String year, String month, String day) {

        String ret = "";
        GsMessage gsMsg = new GsMessage(req);

        if (kbn.equals(String.valueOf(GSConstWebmail.MANU_DEL_OK))) {
            ret += gsMsg.getMessage("wml.60");
            ret += " - " + year + gsMsg.getMessage("cmn.year2") + " "
                     + month + gsMsg.getMessage("cmn.months2") + " "
                     + day + gsMsg.getMessage("cmn.day") + " " + gsMsg.getMessage("wml.73");
        } else {
            ret += gsMsg.getMessage("cmn.dont.delete");
        }

        return ret;
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
        Wml060knForm form) {

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
        cmn999Form.setMessage(
                msgRes.getMessage(msgState, getInterMessage(req, "cmn.manual.delete2")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}

