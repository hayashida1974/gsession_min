package jp.groupsession.v2.wml.wml260kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.AbstractWmlForm;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール 送信予定メール管理確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260knAction extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml260knAction.class);

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
        Wml260knForm thisForm = (Wml260knForm) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("cmd = " + cmd);

        if (cmd.equals("wml260knSendCancel")) {
            //取消ボタンクリック
            forward = __doSendCancel(map, thisForm, req, res, con);

        } else if (cmd.equals("backList")) {
            //戻るボタンクリック
            forward = map.findForward("backList");

        } else if (cmd.equals("prevPage")) {
            //前へクリック
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("next")) {
            //次へクリック
            forward = __doNext(map, thisForm, req, res, con);

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
    public ActionForward __doInit(ActionMapping map, Wml260knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        try {
            Wml260knParamModel paramMdl = new Wml260knParamModel();
            paramMdl.setParam(form);
            Wml260knBiz biz = new Wml260knBiz();
            biz.setInitData(con, paramMdl, getRequestModel(req));
            paramMdl.setFormData(form);

            //取消対象メールが存在しない場合、アクセスエラーとする
            if (form.getWml260SendResvList() == null
            || form.getWml260SendResvList().isEmpty()) {
                return getAuthErrorPage(map, req);
            }
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
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
    private ActionForward __doPrev(
        ActionMapping map,
        Wml260knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml260pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setWml260pageTop(page);
        form.setWml260pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
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
    private ActionForward __doNext(
        ActionMapping map,
        Wml260knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml260pageTop();
        page += 1;
        form.setWml260pageTop(page);
        form.setWml260pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 取消ボタンクリック時処理
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
    public ActionForward __doSendCancel(ActionMapping map, Wml260knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("検索開始");

        //入力チェック
        ActionErrors errors = form.validateSelectMessage(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return getAuthErrorPage(map, req);
        }

        //予約送信メールの取消
        boolean commit = false;
        try {
            Wml260knParamModel paramMdl = new Wml260knParamModel();
            paramMdl.setParam(form);
            Wml260knBiz biz = new Wml260knBiz();
            biz.cancelSendMail(con, paramMdl, getRequestModel(req));
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("予約送信の取消に失敗", e);
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力
        String opCode = getInterMessage(req, "wml.js.84");

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        Wml260knBiz biz = new Wml260knBiz();
        String msg = "";
        ArrayList<String> logList = biz.getLogList(con, form.getWml260selectMailNum());
        boolean multiFlg = false;
        for (String log : logList) {
            if (multiFlg) {
                msg += "\r\n";
            } else {
                multiFlg = true;
            }
            msg += log;
        }

        wmlBiz.outPutLog(map, getRequestModel(req), con,
                opCode, GSConstLog.LEVEL_INFO,
                msg);

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
        Wml260knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("backList");

        ((AbstractWmlForm) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage(req);
        String msgState = "torikeshi.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, gsMsg.getMessage("wml.211")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}
