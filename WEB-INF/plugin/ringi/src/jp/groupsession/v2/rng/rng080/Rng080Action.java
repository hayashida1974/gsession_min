package jp.groupsession.v2.rng.rng080;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.rng010.Rng010Action;

/**
 * <br>[機  能] 稟議 個人設定メニュー画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng080Action extends AbstractRingiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng010Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Rng080Form thisForm = (Rng080Form) form;
        if (cmd.equals("rng010")) {
            log__.debug("*** 戻るボタン押下。");
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("rng060")) {
            log__.debug("*** 内容テンプレート一覧。");
            forward = map.findForward("rng060");

        } else if (cmd.equals("rng050")) {
            log__.debug("*** 申請中案件管理。");
            forward = map.findForward("rng050");

        } else if (cmd.equals("rng070")) {
            log__.debug("*** 完了案件管理。");
            forward = map.findForward("rng070");

        } else if (cmd.equals("rng100")) {
            log__.debug("*** 経路テンプレート一覧。");
            forward = map.findForward("rng100");

        } else if (cmd.equals("rng120")) {
            log__.debug("*** 稟議個人設定");
            forward = map.findForward("rng120");

        } else if (cmd.equals("rng250")) {
            log__.debug("*** 稟議ショートメール設定");
            forward = map.findForward("rng250");

        } else if (cmd.equals("rng270")) {
            log__.debug("*** 稟議代理人設定");
            forward = map.findForward("rng270");

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RngBiz rngBiz = new RngBiz(con);
        if (rngBiz.canUseSmlConf(getRequestModel(req), con, RngConst.CONF_KBN_PRI)) {
            form.setRng080SmlFlg(GSConst.PLUGIN_USE);
        } else {
            form.setRng080SmlFlg(GSConst.PLUGIN_NOT_USE);
        }

        Rng080Biz biz = new Rng080Biz(con, getRequestModel(req));
        Rng080ParamModel paramMdl = new Rng080ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __doBack(ActionMapping map, Rng080Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            return map.findForward("mainPriSetting");
        }

        return map.findForward("rng010");
    }

}
