package jp.groupsession.v2.man.man280kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man280.Man280Biz;
import jp.groupsession.v2.man.man280.Man280Form;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン使用制限確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man280knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man280knAction.class);

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
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Man280knForm thisForm = (Man280knForm) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("decision")) {
            //登録ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);
        } else if (cmd.equals("backToInput")) {
            //戻るボタンクリック
            forward = map.findForward("back_man280");
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Man280knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        con.setAutoCommit(true);
        Man280knParamModel paramMdl = new Man280knParamModel();
        paramMdl.setParam(form);
        Man280knBiz biz = new Man280knBiz();
        biz.setInitData(paramMdl, con, getPluginConfig(req), getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDecision(ActionMapping map,
        Man280knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Man280Biz man280Biz = new Man280Biz();
        String pluginId = form.getMan120pluginId();

        //プラグインIDの存在チェック
        boolean exFlg = man280Biz._existCheckPluginId(reqMdl, con, pluginId);
        if (!exFlg) {
            return __doNonePluginIdError(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        boolean commit = false;

        Man280knParamModel paramMdl = new Man280knParamModel();
        paramMdl.setParam(form);
        Man280knBiz biz = new Man280knBiz();
        try {
            biz.insertPluginUseConfig(paramMdl, con, userSid);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("プラグイン使用制限情報登録処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String value = "";
        // 管理者
        if (paramMdl.getMan280AdminSid().length > 0) {
            value += gsMsg.getMessage("main.man280.2") + "\r\n";
            value += "[" + gsMsg.getMessage("cmn.admin") + "]\r\n";
            List<String> adminUsr = new ArrayList<String>();
            List<String> adminGrp = new ArrayList<String>();
            for (String admin : paramMdl.getMan280AdminSid()) {
                if (admin.startsWith("G")) {
                    adminGrp.add(admin);
                } else {
                    adminUsr.add(admin);
                }
            }
            // グループ
            if (adminGrp.size() > 0) {
                value += gsMsg.getMessage("cmn.group") + ":\r\n";
                value += biz.getLogMessage(
                        adminGrp.toArray(new String[adminGrp.size()]), con, reqMdl);
            }
            // ユーザ
            if (adminUsr.size() > 0) {
                if (adminGrp.size() > 0) {
                    value += "\r\n";
                }
                value += gsMsg.getMessage("cmn.user") + ":\r\n";
                value += biz.getLogMessage(
                        adminUsr.toArray(new String[adminUsr.size()]), con, reqMdl);
            }
            value += "\r\n";
        }
        // 使用制限
        value += gsMsg.getMessage("main.plugin.usage.restrictions") + "\r\n";
        value += "[" + gsMsg.getMessage("main.plugin.usage.restrictions") + "] ";
        if (paramMdl.getMan280useKbn() == GSConstMain.PCT_KBN_ALLOK) {
            value += gsMsg.getMessage("main.man280.4");
        } else if (paramMdl.getMan280useKbn() == GSConstMain.PCT_KBN_MEMBER) {
            value += gsMsg.getMessage("main.man280.5");
            value += "\r\n" + "[" + gsMsg.getMessage("cmn.howto.limit") + "] ";
            String[] limitTarget = {
                    gsMsg.getMessage("main.man280.7"),
                    gsMsg.getMessage("main.man280.8")
            };
            String[] target = {
                    gsMsg.getMessage("main.can.use.user"),
                    gsMsg.getMessage("man.restricted.use.user")
            };
            value += limitTarget[paramMdl.getMan280limitType()] + "\r\n";
            value += target[paramMdl.getMan280limitType()] + "\r\n";
            List<String> targetUsr = new ArrayList<String>();
            List<String> targetGrp = new ArrayList<String>();
            for (String admin : paramMdl.getMan280memberSid()) {
                if (admin.startsWith("G")) {
                    targetGrp.add(admin);
                } else {
                    targetUsr.add(admin);
                }
            }
            // グループ
            if (targetGrp.size() > 0) {
                value += gsMsg.getMessage("cmn.group") + ":\r\n";
                value += biz.getLogMessage(
                        targetGrp.toArray(new String[targetGrp.size()]), con, reqMdl);
                value += "\r\n";
            }
            // ユーザ
            if (targetUsr.size() > 0) {
                value += gsMsg.getMessage("cmn.user") + ":\r\n";
                value += biz.getLogMessage(
                        targetUsr.toArray(new String[targetUsr.size()]), con, reqMdl);
            }
        }

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                          + gsMsg.getMessage("main.man280kn.1");
        cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                    getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, value, dspName);

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
        Man280knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        String backId = NullDefault.getString(form.getMan280backId(), "");
        if (backId.equals("man121")) {
            urlForward =  map.findForward("man121");
        } else {
            urlForward =  map.findForward("man120");
        }

        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, "main.plugin.usage.restrictions")));

        cmn999Form.addHiddenParam("menuEditOk", form.getMenuEditOk());

        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>[機  能] プラグインIDが存在しない場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNonePluginIdError(ActionMapping map, Man280knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //エラー画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("man120");

        //メッセージ「プラグインID」
        String message = gsMsg.getMessage(req, "cmn.plugin") + gsMsg.getMessage(req, "cmn.id");

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(
                "error.input.notvalidate.data", message));

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");

        return forward;
    }
}
