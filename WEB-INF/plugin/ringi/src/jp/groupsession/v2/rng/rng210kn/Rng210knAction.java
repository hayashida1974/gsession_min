package jp.groupsession.v2.rng.rng210kn;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.rng210.Rng210Biz;
import jp.groupsession.v2.rng.rng210.Rng210ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 申請フォーマット登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng210knAction extends AbstractRingiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng210knAction.class);

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
        Rng210knForm thisForm = (Rng210knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("Rng200knAction CMD==>" + cmd);

        if (cmd.equals("decision")) {
            log__.debug("確定");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("backList")) {
            log__.debug("戻る");
            forward = map.findForward("backList");

        } else {
            log__.debug("初期表示");
            forward = map.getInputForward();
        }

        log__.debug("END_Rng200kn");
        return forward;
    }

    /**
     * <br>[機  能] 確定ボタンクリック時の処理
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
        Rng210knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        boolean commit = false;
        try {

            ActionErrors errors = form.validateCheck(getRequestModel(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return map.findForward("backList");
            }

            //con.setAutoCommit(true);
            //採番マスタコントローラーを取得
            MlCountMtController cntCon = getCountMtController(req);
            RequestModel reqMdl = getRequestModel(req);
            Rng210knParamModel paramMdl = new Rng210knParamModel();
            paramMdl.setParam(form);
            Rng210knBiz biz = new Rng210knBiz();
            biz.idEntry(con, paramMdl, getSessionUserSid(req), cntCon);
            paramMdl.setFormData(form);
            //con.setAutoCommit(false);

            //ログ出力処理
            GsMessage gsMsg = new GsMessage(reqMdl);
            String opCode = "";
            String msg = "";
            if (paramMdl.getRng200Sid() == 0) {
                opCode = gsMsg.getMessage("cmn.entry"); // 登録
            } else {
                opCode = gsMsg.getMessage("cmn.edit");  // 編集
            }

            msg += "[" + gsMsg.getMessage("cmn.title") + "] "; // タイトル
            msg += form.getRng210Title() + "\r\n";
            msg += "[" + gsMsg.getMessage("rng.12") + "] ";    // フォーマット
            msg += form.getRng210DispFormat() + "\r\n";
            msg += "[" + gsMsg.getMessage("rng.rng210.10") + "] "; // 0埋め桁数
            msg += form.getRng210Zeroume() + "\r\n";
            msg += "[" + gsMsg.getMessage("rng.rng210.01") + "] "; // 現在の連番値
            msg += form.getRng210Init() + "\r\n";

            // リセット期間
            msg += "[" + gsMsg.getMessage("rng.rng210.07") + "] ";
            if (form.getRng210Reset() == RngConst.RAR_RESET_NONE) {
                msg += gsMsg.getMessage("rng.rng210.08");  // リセットしない
            } else if (form.getRng210Reset() == RngConst.RAR_RESET_YEAR) {
                msg += gsMsg.getMessage("cmn.year2");      // 年
            } else if (form.getRng210Reset() == RngConst.RAR_RESET_MONTH) {
                msg += gsMsg.getMessage("cmn.month");      // 月
            } else if (form.getRng210Reset() == RngConst.RAR_RESET_DAY) {
                msg += gsMsg.getMessage("cmn.day");        // 日
            }
            msg += "\r\n";

            // 手入力変更
            msg += "[" + gsMsg.getMessage("rng.rng210.04") + "] ";
            if (form.getRng210Manual() == RngConst.RAR_SINSEI_MANUAL_TEMPLATE) {
                msg += gsMsg.getMessage("rng.rng210.03");  // テンプレート毎に設定
            } else if (form.getRng210Manual() == RngConst.RAR_SINSEI_MANUAL_KYOKA) {
                msg += gsMsg.getMessage("cmn.permit");     // 許可する
            } else if (form.getRng210Manual() == RngConst.RAR_SINSEI_MANUAL_NOT_KYOKA) {
                msg += gsMsg.getMessage("cmn.not.permit"); // 許可しない
            }

            RngBiz rngBiz = new RngBiz(con);
            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_TRACE, msg,
                    reqMdl);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("稟議情報の削除に失敗", e);
        } finally {
            if (!commit) {
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
                                       Rng210knForm form,
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
        String textBaseConf = gsMsg.getMessage(req, "rng.rng180.04");

        urlForward = map.findForward("rng200");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getRng200Sid() == 0) {
            msgState = "touroku.kanryo.object";
        } else {
            msgState = "hensyu.kanryo.object";
        }

        cmn999Form.setMessage(
                msgRes.getMessage(msgState, textBaseConf));

        //hiddenパラメータ
        form.setHiddenParam(cmn999Form, true);
        form.setConfHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

}
