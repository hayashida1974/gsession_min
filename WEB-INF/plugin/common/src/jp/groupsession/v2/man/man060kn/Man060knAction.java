package jp.groupsession.v2.man.man060kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 ディスク容量管理確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man060knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man060knAction.class);

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
        Man060knForm thisForm = (Man060knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);

        if (cmd.equals("backInput")) {
            forward = map.findForward("back");
        } else if (cmd.equals("decision")) {
            //設定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);
        } else {
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
    private void __doInit(ActionMapping map, Man060knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        Man060knParamModel paramMdl = new Man060knParamModel();
        paramMdl.setParam(form);
        Man060knBiz biz = new Man060knBiz();
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

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
    private ActionForward __doDecision(ActionMapping map, Man060knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        boolean commit = false;
        Man060knParamModel paramMdl = new Man060knParamModel();
        paramMdl.setParam(form);
        try {
            Man060knBiz biz = new Man060knBiz();
            biz.insertDiskAdmin(paramMdl, con, sessionUsrSid);
            paramMdl.setFormData(form);

            __setKanryou(map, req, form);
            forward = map.findForward("gf_msg");

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("ディスク容量管理情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String dspName = gsMsg.getMessage("cmn.admin.setting")
                                    + " " + gsMsg.getMessage("main.man060kn.1");
        String value = "";
        value = "[" + gsMsg.getMessage("cmn.warning") + "] ";
        if (paramMdl.getMan060capacity() < 1) {
            value += gsMsg.getMessage("cmn.specified.no");
        } else {
            int capacity = paramMdl.getMan060capacity();
            String byteNum = "";
            if (capacity < 1000) {
                byteNum = "MB";
            } else {
                capacity = capacity / 1000;
                byteNum = "GB";
            }
            value += capacity + byteNum;
            value += gsMsg.getMessage("main.info.warn");
        }
        cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"),
                GSConstLog.LEVEL_INFO, value, dspName);

        return forward;
    }
    /**
     * <br>[機  能] 削除完了画面設定処理
     * <br>[解  説] 削除完了画面のパラメータセット
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKanryou(
        ActionMapping map,
        HttpServletRequest req,
        Man060knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("menu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.DISKSPACE_CONF_MSG)));
        req.setAttribute("cmn999Form", cmn999Form);

    }

}
