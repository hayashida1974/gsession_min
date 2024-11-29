package jp.groupsession.v2.sml.sml260kn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmlForm;
import jp.groupsession.v2.sml.AbstractSmlSubAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.sml260.SmailCsvModel;
import jp.groupsession.v2.sml.sml260.Sml260Biz;

/**
 * <br>[機  能] ショートメール アカウントインポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260knAction extends AbstractSmlSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml260knAction.class);

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
    public ActionForward executeSmail(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Sml260knForm thisForm = (Sml260knForm) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Sml260knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        try {
            con.setAutoCommit(true);
            Sml260knParamModel paramMdl = new Sml260knParamModel();
            paramMdl.setParam(form);
            Sml260knBiz biz = new Sml260knBiz();
            biz.setInitData(con, getRequestModel(req), paramMdl);
            paramMdl.setFormData(form);

        } catch (SQLException se) {
            throw se;
        } catch (IOException ioe) {
            throw ioe;
        } catch (IOToolsException iote) {
            throw iote;
        }
        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] アカウント登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDecision(
                            ActionMapping map,
                            Sml260knForm form,
                            HttpServletRequest req,
                            HttpServletResponse res,
                            Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Sml260Biz biz260 = new Sml260Biz();
        String tempDir = biz260.getTempDir(reqMdl);

        //入力チェックを行う
        ActionErrors errors = null;
        errors = form.validateCheck(con, req, tempDir);
        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("backInput");
        }

        boolean commit = false;
        List<SmailCsvModel> accountList = null;
        try {
            Sml260knBiz biz = new Sml260knBiz();
            accountList = biz.addAccount(con, reqMdl,
                            getCountMtController(req), getSessionUserSid(req));

            con.commit();
            commit = true;

        } catch (Exception e) {
            log__.error("アカウント情報のインポートに失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }

            //テンポラリディレクトリ削除
            biz260.deleteTempDir(reqMdl);
        }

        //ログ出力
        if (accountList != null) {
            StringBuilder buf = new StringBuilder();
            for (SmailCsvModel mdl : accountList) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append("[name]" + mdl.getAccountName());
            }
            //ログ出力
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
            smlBiz.outPutLog(map, reqMdl,
                    getInterMessage(req, "cmn.entry"), GSConstLog.LEVEL_INFO,
                    StringUtil.trimRengeString(buf.toString(), 3000));
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
        Sml260knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("smlAccountManager");
        if (form.getSmlAccountMode() == GSConstSmail.SAC_TYPE_USER) {
            urlForward = map.findForward("smlAccountManager");
        }

        ((AbstractSmlForm) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getSmlCmdMode() == GSConstSmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getSmlCmdMode() == GSConstSmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "wml.wml160.05")));
        //画面パラメータをセット

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

}
