package jp.groupsession.v2.wml.wml250kn;

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
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.wml250.Wml250Action;
import jp.groupsession.v2.wml.wml250.Wml250Biz;

/**
 * <br>[機  能] WEBメール メールテンプレート登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml250knAction extends Wml250Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml250knAction.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;

        }
        return false;
    }

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

        //アクセス可能かをチェック
        Wml250knForm thisForm = (Wml250knForm) form;
        if (!_canAccess(con, req, thisForm)) {
            return getAuthErrorPage(map, req);
        }

        ActionForward forward = null;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

        } else if (cmd.equals("fileDownload")) {
            //ファイルダウンロード
            forward = __doDownLoad(map, thisForm, req, res, con);
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
    public ActionForward __doInit(ActionMapping map, Wml250knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        Wml250knParamModel paramMdl = new Wml250knParamModel();
        paramMdl.setParam(form);
        Wml250knBiz biz = new Wml250knBiz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(
            ActionMapping map,
            Wml250knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getWml250knFileId();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        Wml250knBiz biz = new Wml250knBiz();
        String tempDir = biz.getTempDir(reqMdl);

        WmlBiz wmlBiz = new WmlBiz();
        String fileName = wmlBiz.downloadTempFile(req, res, tempDir, fileId);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        wmlBiz.outPutLog(
                map, reqMdl, con,
                gsMsg.getMessage("cmn.download"),
                GSConstLog.LEVEL_INFO, fileName, fileId, GSConstWebmail.WML_LOG_FLG_DOWNLOAD);

        return null;
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
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
    public ActionForward __doDecision(ActionMapping map, Wml250knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheck(reqMdl, getAppRootPath());
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        boolean commit = false;
        try {
            //登録処理
            MlCountMtController cntCon = getCountMtController(req);
            //登録、または更新処理を行う
            Wml250knParamModel paramMdl = new Wml250knParamModel();
            paramMdl.setParam(form);
            Wml250knBiz biz = new Wml250knBiz();
            biz.entryTemplate(con, paramMdl, reqMdl, getAppRootPath(), cntCon);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("メールテンプレートの登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }

            //テンポラリディレクトリを削除
            Wml250Biz biz = new Wml250Biz();
            biz.deleteTempDir(reqMdl);
        }

        String opCode = "";
        if (form.getWmlTemplateCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else if (form.getWmlTemplateCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
            opCode = getInterMessage(req, "cmn.change");
        }

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String accountName = "";
        // アカウント名
        if (form.getWmlAccountSid() == GSConstWebmail.WML_ACCOUNT_COMMON) {
            accountName = gsMsg.getMessage("cmn.common");
        } else {
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountMdl = accountDao.select(form.getWmlAccountSid());
            accountName = accountMdl.getWacName();
        }

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        String msg = "[" + gsMsg.getMessage("wml.96") + "] " + accountName
                + "\r\n[" + gsMsg.getMessage("wml.wml240.01") + "]"
                + NullDefault.getString(form.getWml250TemplateName(), "");
        wmlBiz.outPutLog(map, reqMdl, con,
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
            Wml250knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時の遷移先画面を設定
        ActionForward urlForward = map.findForward("mailTemplateConf");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getWmlTemplateCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getWmlTemplateCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "anp.anp090.03")));

        cmn999Form.addHiddenParam("wml240SortRadio", form.getWml240SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

    }
}
