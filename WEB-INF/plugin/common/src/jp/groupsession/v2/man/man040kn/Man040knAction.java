package jp.groupsession.v2.man.man040kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man040.Man040Form;
import jp.groupsession.v2.man.man040.Man040ParamModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン 管理者設定 バッチ処理起動時間設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man040knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man040knAction.class);

    /** 設定ボタン押下時CMD */
    private static final String CMD_SETTING = "commit";
    /** 戻るボタン押下時CMD */
    private static final String CMD_BACK = "040kn_back";

    /** 完了後戻り先CMD */
    private static final String CMD_MENU = "menu";


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

        log__.debug("CMD==>" + cmd);
        if (CMD_SETTING.equals(cmd)) {
            log__.debug("個人設定登録");
            forward = __doSetting(map, thisForm, req, res, con);
        } else if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = __doBack(map, thisForm, req, res, con);
        } else {
            log__.debug("バッチジョブ起動時間設定確認画面表示");
            thisForm.setMan040FrHour(
                    StringUtilHtml.transToHTmlPlusAmparsant(thisForm.getMan040FrHour()));
            forward = map.getInputForward();
        }
        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 設定ボタンクリック時処理
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

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        log__.debug("入力エラーなし");
        return __doCommit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 登録・修正処理
     * <br>[解  説] 登録・修正処理を行う
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Man040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;


        //セッション情報を取得
        int sessionUsrSid = getSessionUserSid(req);
        RequestModel reqMdl = getRequestModel(req);

        Man040knBiz biz = new Man040knBiz();
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            Man040ParamModel paramMdl = new Man040ParamModel();
            paramMdl.setParam(form);
            biz.executeBatchJobStartTime(paramMdl, sessionUsrSid, con);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("バッチジョブ起動時間設定の登録に失敗しました", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"),
                GSConstLog.LEVEL_INFO, "[hour]" + form.getMan040FrHour());
        forward = __doCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理
     * <br>[解  説] 入力画面へ遷移する
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
     * <br>[機  能] 登録完了画面設定処理
     * <br>[解  説] 登録完了画面設定を行う
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Man040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward(CMD_MENU);
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("settei.kanryo.object",
                    getInterMessage(req, GSConstMain.BATCH_JOB_START_TIME_MSG)));
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
