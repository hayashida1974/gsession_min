package jp.groupsession.v2.rng.rng180kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng180knAction extends AbstractRingiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng180knAction.class);

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

        ActionForward forward = null;
        Rng180knForm thisForm = (Rng180knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("Rng180knAction CMD==>" + cmd);

        if (cmd.equals("decision")) {
            log__.debug("確定");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            log__.debug("戻る");
            forward = map.findForward("backInput");

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Rng180kn");
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
        Rng180knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        Rng180knBiz biz = new Rng180knBiz();
        Rng180knParamModel paramMdl = new Rng180knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 回覧板 送信ボタンクリック時の処理
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
        Rng180knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        boolean commit = false;
        try {
            //con.setAutoCommit(true);

            Rng180knParamModel paramMdl = new Rng180knParamModel();
            paramMdl.setParam(form);
            Rng180knBiz biz = new Rng180knBiz();
            biz.entryAdmConf(con, paramMdl, getSessionUserSid(req));
            paramMdl.setFormData(form);
            //con.setAutoCommit(false);

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);

            //ログ出力処理
            RngBiz rngBiz = new RngBiz(con);
            String opCode = gsMsg.getMessage("cmn.change");
            String msg = "[" + gsMsg.getMessage("cmn.delete") + "] ";
            if (paramMdl.getRng180delKbn() == RngConst.RAR_DEL_AUTH_ADM) {
                msg += gsMsg.getMessage("cmn.delete.only.admin");
            } else {
                msg += gsMsg.getMessage("man.no.limit");
            }
            msg += "\r\n[" + gsMsg.getMessage("rng.rng180.06") + "] ";
            if (paramMdl.getRng180HanyoFlg() == RngConst.RAR_HANYO_FLG_YES) {
                msg += gsMsg.getMessage("rng.rng180.05");
                msg += "\r\n[" + gsMsg.getMessage("cmn.personal.template") + "] ";
                if (paramMdl.getRng180TemplatePFlg() == RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES) {
                    msg += gsMsg.getMessage("rng.rng180.05");
                } else {
                    msg += gsMsg.getMessage("rng.rng180.03");
                }
            } else {
                msg += gsMsg.getMessage("rng.rng180.03");
            }
            msg += "\r\n[" + gsMsg.getMessage("rng.rng180.07") + "] ";
            if (paramMdl.getRng180KeiroPFlg() == RngConst.RAR_KEIRO_PERSONAL_FLG_YES) {
                msg += gsMsg.getMessage("rng.rng180.05");
            } else {
                msg += gsMsg.getMessage("rng.rng180.03");
            }

            msg += "\r\n[" + gsMsg.getMessage("rng.rng180.04") + "] "; // 申請ID
            if (form.getRng180sinseiKbn() == RngConst.RAR_SINSEI_NONE) {
                // 使用しない
                msg += gsMsg.getMessage("rng.rng180.03");
            } else {
                // 使用する
                msg += gsMsg.getMessage("rng.rng180.05") + "\r\n";

                msg += "[" + gsMsg.getMessage("rng.rng280.01") + "] "; // 申請ID使用方法
                if (form.getRng180sinseiKbn() == RngConst.RAR_SINSEI_TOUITU) {
                    // 全稟議で統一
                    msg += gsMsg.getMessage("rng.rng180.01") + ": ";

                    RngIdDao dao = new RngIdDao(con);
                    RingiIdModel mdl = dao.selectData(form.getRng180defaultId());
                    if (mdl != null) {
                        msg += mdl.getRngTitle();
                    }
                } else {
                    // テンプレート毎に設定
                    msg += gsMsg.getMessage("rng.rng180.02");
                }
                msg += "\r\n";

                msg += "[" + gsMsg.getMessage("rng.rng210.05") + "] "; // 重複ID
                if (form.getRng180Overlap() == RngConst.RAR_SINSEI_NOT_KYOKA) {
                    msg += gsMsg.getMessage("cmn.not.permit"); // 許可する
                } else {
                    msg += gsMsg.getMessage("cmn.permit");     // 許可しない
                }
                msg += "\r\n";
            }

            rngBiz.outPutLog(
                    map, opCode, GSConstLog.LEVEL_INFO, msg,
                    reqMdl);

            commit = true;
        } catch (Exception e) {
            log__.error("稟議個人設定更新エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }


        //完了画面を表示
        return __doCompDsp(map, form, req, res);
    }


    /**
     * <br>[機  能] 完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Rng180knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String textBaseConf = gsMsg.getMessage(req, "cmn.preferences");

        urlForward = map.findForward("rngAdmMenu");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("touroku.kanryo.object", textBaseConf));

        //hiddenパラメータ
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
