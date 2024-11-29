package jp.groupsession.v2.cmn.cmn390;

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
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] GSファイアウォール設定画面 Actionクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn390Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn390Action.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        ActionForward forward = null;
        Cmn390Form thisForm = (Cmn390Form) form;

        if (cmd.equals("cmn390Back")) {
            //「管理者設定へ戻る」ボタンクリック時
            log__.debug("戻るボタン押下");
            forward = map.findForward("ktools");

        } else if (cmd.equals("reset")) {
            log__.debug("resetToolによる初期化");
            forward = __doReset(map, thisForm, req, res, con);

        } else if (cmd.equals("cmn390Commit")) {
            log__.debug("登録ボタンクリック");
            forward = __doCommit(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }
    /**
     *
     * <br>[機  能] 初期表示処理の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map
     * @param thisForm
     * @param req
     * @param res
     * @param con
     * @return フォワード先
     * @throws SQLException
     */
    private ActionForward __doInit(ActionMapping map, Cmn390Form thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
        con.setAutoCommit(true);
        Cmn390Biz biz = new Cmn390Biz(getRequestModel(req), con);
        Cmn390ParamModel paramMdl = new Cmn390ParamModel();
        paramMdl.setParam(thisForm);
        biz.doInit(paramMdl);
        biz.doDsp(paramMdl);
        paramMdl.setFormData(thisForm);

        saveToken(req);

        return map.getInputForward();
    }
    /**
     *
     * <br>[機  能] 登録処理の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map
     * @param thisForm
     * @param req
     * @param res
     * @param con
     * @return フォワード先
     * @throws SQLException
     */
    private ActionForward __doCommit(ActionMapping map, Cmn390Form thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
        Cmn390Biz biz = new Cmn390Biz(getRequestModel(req), con);
        Cmn390ParamModel paramMdl = new Cmn390ParamModel();
        paramMdl.setParam(thisForm);

        //入力チェック
        ActionErrors errors = thisForm.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            biz.doDsp(paramMdl);
            paramMdl.setFormData(thisForm);
            return map.getInputForward();
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }


        biz.doCommit(paramMdl);


        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = "[" + getInterMessage(reqMdl, "cmn.cmn390.03") + "]\r\n";
        if (thisForm.getCmn390ipAddrSeigenUseFlg() == 1) {
            msg += getInterMessage(reqMdl, "cmn.cmn390.10") + "\r\n"
                + "[" + getInterMessage(reqMdl, "cmn.cmn390.04") + "]\r\n"
                + thisForm.getCmn390arrowIpAddrText() + "\r\n"
                + "[" + getInterMessage(reqMdl, "cmn.cmn390.05") + "]\r\n";
            if (thisForm.getCmn390arrowMblFlg() == 0
                    && thisForm.getCmn390arrowAnpFlg() == 0) {
                msg += getInterMessage(reqMdl, "cmn.noset");
            } else {
                boolean rnFlg = false;
                if (thisForm.getCmn390arrowAnpFlg() == 1) {
                    msg += getInterMessage(reqMdl, "cmn.cmn390.07");
                    rnFlg = true;
                }
                if (thisForm.getCmn390arrowMblFlg() == 1) {
                    if (rnFlg) {
                        msg += "\r\n";
                    }
                    msg += getInterMessage(reqMdl, "cmn.cmn390.06");

                    msg += "\r\n" + getInterMessage(reqMdl, "cmn.cmn390.15") + " ";
                    if (paramMdl.getCmn390mobilePermissionKbn() == 0) {
                        msg += getInterMessage(reqMdl, "cmn.not.limit");
                    } else {
                        msg += getInterMessage(reqMdl, "cmn.do.limit");

                        //許可するプラグインの一覧を出力
                        PluginConfig pconfig = getPluginConfig(req);
                        msg += "\r\n　・" + getInterMessage(reqMdl, "schedule.sch240.07");
                        for (String pid : paramMdl.getCmn390mobilePermissionPluginList()) {
                            msg += "\r\n　　" + pconfig.getPlugin(pid).getName(reqMdl);
                        }
                    }

                }
            }
        } else {
            msg += getInterMessage(reqMdl, "cmn.cmn390.09");
        }
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"),
                GSConstLog.LEVEL_INFO, msg);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("ktools").getPath());

        //メッセージセット
        String msgState = null;
        msgState = "touroku.kanryo.object";
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.cmn390.01")));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");

    }
    /**
     *
     * <br>[機  能] リセット処理の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map
     * @param thisForm
     * @param req
     * @param res
     * @param con
     * @return フォワード先
     * @throws SQLException
     */
    private ActionForward __doReset(ActionMapping map, Cmn390Form thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        thisForm.setCmn390ipAddrSeigenUseFlg(Cmn390Biz.FLG_NEGATIVE);
        Cmn390Biz biz = new Cmn390Biz(getRequestModel(req), con);
        Cmn390ParamModel paramMdl = new Cmn390ParamModel();
        paramMdl.setParam(thisForm);
        biz.doCommit(paramMdl);



        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        //URL
        urlForward = map.findForward("gf_menu");
        if (!isSession(req)) {
            //未ログインの場合、ログイン画面へ遷移
            urlForward = map.findForward("gf_logout");
        } else if (getSessionUserSid(req) == GSConst.SYSTEM_USER_ADMIN) {
            //adminユーザの場合、管理者設定メニュー画面へ遷移
            urlForward = map.findForward("gf_main_kanri");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "hensyu.henkou.kanryo.object";
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.cmn390.01")));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return __isLocalAccess(req);
    }
    /**
     * <br>[機  能] リクエスト元がAPサーバと一致するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true: 一致する false: 一致しない
     */
    private boolean __isLocalAccess(HttpServletRequest req) {

        RequestModel reqMdl = getRequestModel(req);
        String remoteAddr = NullDefault.getString(reqMdl.getRemoteAddr(), "");
        return remoteAddr.equals("127.0.0.1")
                || remoteAddr.equals("::1")
                || remoteAddr.equals("0:0:0:0:0:0:0:1");
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return __isLocalAccess(req);
    }

}
