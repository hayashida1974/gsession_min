package jp.groupsession.v2.man.man040;

import java.io.PrintWriter;
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
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 バッチ処理起動時間設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man040Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man040Action.class);

    /** 設定ボタン押下時CMD */
    private static final String CMD_SETTING = "setting";
    /** 戻るボタン押下時CMD */
    private static final String CMD_BACK = "040_back";
    /** 強制実行ボタン */
    private static final String CMD_EXECUTION = "execution";
    /** バッチ処理強制実行OK */
    private static final String CMD_BATCH_JOB_EXECUTION = "batchJobOk";
    /** バッチ処理強制実行完了 */
    private static final String CMD_BATCH_JOB_COMP = "batchJobComp";
    /** 再表示 */
    private static final String CMD_DSP = "040_init";

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
        Man040Form thisForm = (Man040Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        //クラウド版の場合は不正なアクセス
        if (!GroupSession.getResourceManager().getDomain(req).equals(GSConst.GS_DOMAIN)) {
            forward = map.findForward("gf_submit");
            return forward;
        }

        log__.debug("CMD==>" + cmd);
        if (CMD_SETTING.equals(cmd)) {
            log__.debug("個人設定登録");
            forward = __doSetting(map, thisForm, req, res, con);
        } else if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = __doBack(map, thisForm, req, res, con);
        } else if (CMD_EXECUTION.equals(cmd)) {
            log__.debug("バッチ処理実行確認");
            forward = __doExecution(map, thisForm, req, res, con);
        } else if (CMD_BATCH_JOB_EXECUTION.equals(cmd)) {
            log__.debug("バッチ処理実行");
            forward = __doBatch(map, thisForm, req, res, con);
        } else if (CMD_BATCH_JOB_COMP.equals(cmd)) {
            log__.debug("バッチ処理実行完了");
            forward = __doCompDsp(map, req);
        } else {
            log__.debug(">バッチジョブ起動時間設定表示");
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }
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
    private void __doInit(ActionMapping map, Man040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        Man040ParamModel paramMdl = new Man040ParamModel();
        paramMdl.setParam(form);
        Man040Biz biz = new Man040Biz(getRequestModel(req));
        //初期表示
        if (form.getMan040FrHour() == null) {
            biz.setInitData(paramMdl, con);
        } else {
            //再表示
            biz.setDspData(paramMdl);
        }
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSetting(ActionMapping map, Man040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        //トランザクショントークン設定
        saveToken(req);

        return map.findForward(CMD_SETTING);
    }

    /**
     * <br>[機  能] 強制実行ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doExecution(ActionMapping map, Man040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        return __doKakuninDsp(map, req, form);
    }

    /**
     * <br>[機  能] 管理者メニューへ遷移する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     */
    private ActionForward __doBack(ActionMapping map, Man040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;
        forward = map.findForward(CMD_BACK);

        return forward;
    }

    /**
     * <br>[機  能] 日次バッチを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     * @throws Exception 例外発生
     */
    private ActionForward __doBatch(ActionMapping map, Man040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {
        RequestModel reqMdl = getRequestModel(req);

        //日次バッチを実行する。
        Man040Biz biz = new Man040Biz(reqMdl);
        biz.executeBatchJob(getGsContext(), con, getPluginConfig(req));

        //ログ出力
        Connection logConnection = null;
        try {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String message = getInterMessage(reqMdl, "cmn.batch.start");
            if (CommonBiz.isMultiAP() && CommonBiz.getApNumber() > 0) {
                message += ": AP" + CommonBiz.getApNumber();
            }

            logConnection = getConnection(req);
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, logConnection,
                    message, GSConstLog.LEVEL_INFO,
                    getInterMessage(reqMdl, "main.src.man040.1"));
        } finally {
            if (logConnection != null) {
                closeConnection(logConnection);
            }
        }

        PrintWriter out = null;
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.element("success", true);
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("日次バッチ手動実行 完了処理でエラー発生", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] バッチ処理実行確認画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward
     */
    private ActionForward __doKakuninDsp(
            ActionMapping map, HttpServletRequest req, Man040Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("confirm");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setUrlCancel(map.findForward(CMD_DSP).getPath());
        cmn999Form.setMessage(msgRes.getMessage("execute.kakunin.once",
                 getInterMessage(req, GSConstMain.BATCH_JOB_MSG)));
        cmn999Form.addHiddenParam("man040FrHour", form.getMan040FrHour());
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] バッチ処理実行完了画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, HttpServletRequest req) {
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward(CMD_BACK);
        cmn999Form.setUrlOK(urlForward.getPath());

        cmn999Form.setMessage(msgRes.getMessage("cmn.kanryo.object",
                getInterMessage(req, GSConstMain.BATCH_JOB_MSG)));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}