package jp.groupsession.v2.rng.rng190;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 ショートメール通知設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng190Action extends AbstractRingiAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng190Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;

        Rng190Form ntpForm = (Rng190Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("rng190ok")) {
            //OKボタンクリック
            forward = __doDecision(map, ntpForm, req, res, con);
        } else if (cmd.equals("rng190back")) {
            //戻るボタンクリック
            forward = __doBack(map, ntpForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 設定ボタンクリック時の処理
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
            Rng190Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        RngBiz rngBiz = new RngBiz(con);
        if (!rngBiz.canUseSmlConf(getRequestModel(req), con, RngConst.CONF_KBN_ADM)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        // エラーチェック
        ActionErrors errors = form.validateCheck(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        boolean commit = false;
        try {
            Rng190ParamModel paramMdl = new Rng190ParamModel();
            paramMdl.setParam(form);
            Rng190Biz biz = new Rng190Biz(con, reqMdl);
            biz.updateSmailSetting(paramMdl, reqMdl.getSmodel().getUsrsid());
            paramMdl.setFormData(form);

            //ログ出力処理
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textEdit = gsMsg.getMessage("cmn.change");

            String msg = "[" + gsMsg.getMessage("cmn.sml.notification.setting") + "] ";
            if (form.getRng190SmlNtf() == RngConst.RAR_SML_NTF_ADMIN) { // 管理者が設定
                msg += gsMsg.getMessage("cmn.set.the.admin") + "\r\n";

                msg += "[" + gsMsg.getMessage("rng.rng190.01") + "] ";
                if (form.getRng190SmlNtfKbn() == RngConst.RAR_SML_NTF_KBN_YES) {
                    msg += gsMsg.getMessage("cmn.notify");
                } else {
                    msg += gsMsg.getMessage("cmn.dont.notify");
                }
                msg += "\r\n";

                msg += "[" + gsMsg.getMessage("rng.rng190.03") + "] ";
                if (form.getRng190SmlJusin() == RngConst.RAR_SML_NTF_KBN_YES) {
                    msg += gsMsg.getMessage("cmn.notify");
                } else {
                    msg += gsMsg.getMessage("cmn.dont.notify");
                }
                msg += "\r\n";

                msg += "[" + gsMsg.getMessage("rng.rng190.05") + "] ";
                if (form.getRng190SmlDairi() == RngConst.RAR_SML_NTF_KBN_YES) {
                    msg += gsMsg.getMessage("cmn.notify");
                } else {
                    msg += gsMsg.getMessage("cmn.dont.notify");
                }
            } else if (form.getRng190SmlNtf() == RngConst.RAR_SML_NTF_KBN_YES) { // 各ユーザが設定
                msg += gsMsg.getMessage("cmn.set.eachuser");
            }

            rngBiz.outPutLog(map, textEdit,  GSConstLog.LEVEL_INFO, msg, reqMdl);

            commit = true;
        } catch (Exception e) {
            log__.error("掲示板 管理者設定 ショートメール通知更新エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

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
        Rng190Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("rngAdmMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "settei.kanryo.object";
        String key1 = "ショートメール通知";
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        //hiddenパラメータ
        form.setHiddenParam(cmn999Form, true);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Rng190Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("rngAdmMenu");
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Rng190Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示");
        RngBiz rngBiz = new RngBiz(con);
        if (!rngBiz.canUseSmlConf(getRequestModel(req), con, RngConst.CONF_KBN_ADM)) {
            return getSubmitErrorPage(map, req);
        }

        Rng190Biz biz = new Rng190Biz(con, getRequestModel(req));

        Rng190ParamModel paramMdl = new Rng190ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
}