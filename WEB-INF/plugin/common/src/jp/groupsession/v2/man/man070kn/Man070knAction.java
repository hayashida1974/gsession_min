package jp.groupsession.v2.man.man070kn;

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
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 プロキシサーバ設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man070knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man070knAction.class);

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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;
        Man070knForm thisForm = (Man070knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //設定ボタン押下
        if (cmd.equals("settei")) {
            log__.debug("設定ボタン押下");
            forward = __doSettei(map, thisForm, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("input")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("input");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisForm, req, res, con);
        }

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
     * @return ActionForward フォワード
     */
    private ActionForward __doInit(ActionMapping map,
                                    Man070knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        Man070knParamModel paramMdl = new Man070knParamModel();
        paramMdl.setParam(form);
        Man070knBiz biz = new Man070knBiz();
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException プロキシ情報更新時例外
     * @throws EncryptionException パスワードの暗号化に失敗
     */
    private ActionForward __doSettei(ActionMapping map,
                                      Man070knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, EncryptionException {

        ActionForward forward = null;
        boolean commit = false;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Man070knParamModel paramMdl = new Man070knParamModel();
        paramMdl.setParam(form);
        try {

            //プロキシ情報更新
            Man070knBiz biz = new Man070knBiz();
            biz.updateProxyData(paramMdl, con);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;

        } catch (SQLException e) {
            log__.error("プロキシ情報更新時エラー", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String value = "";
        value = "[" + gsMsg.getMessage("main.man070.10") + "] ";
        if (paramMdl.getMan070pxyUseKbn() == GSConstMain.PROXY_SERVER_NOT_USE) {
            value += gsMsg.getMessage("main.man070kn.2");
        } else if (paramMdl.getMan070pxyUseKbn() == GSConstMain.PROXY_SERVER_USE) {
            value += gsMsg.getMessage("main.man070kn.3");
            value += "\r\n" + gsMsg.getMessage("cmn.address.2") + ":"
                                        + paramMdl.getMan070address();
            value += "\r\n" + gsMsg.getMessage("cmn.port.number") + ":"
                                        + paramMdl.getMan070portnum();
            if (paramMdl.getMan070Auth() == GSConstMain.PROXY_SERVER_USERAUTH_AUTH) {
                value += "\r\n" + "[" + gsMsg.getMessage("main.man070.4") + "] ";
                value += gsMsg.getMessage("cmn.user") + ":" + paramMdl.getMan070AuthUser();
            }
            String noProxyAddress = paramMdl.getMan070NoProxyAddress();
            if (noProxyAddress.length() > 0) {
                value += "\r\n" + "[" + gsMsg.getMessage("main.man070kn.5") + "] " + noProxyAddress;
            }
        }
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, value);

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
                                       Man070knForm form,
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

        urlForward = map.findForward("back_to_kanri_menu");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object",
                                getInterMessage(req, GSConstMain.TEXT_PROXY_SERVER)));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
