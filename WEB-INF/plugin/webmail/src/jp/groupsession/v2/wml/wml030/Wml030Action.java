package jp.groupsession.v2.wml.wml030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailAdminAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;

/**
 * <br>[機  能] WEBメール アカウントマネージャー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml030Action extends AbstractWebmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml030Action.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        return cmd.equals("acntExport");
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

        ActionForward forward = null;
        Wml030Form thisForm = (Wml030Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("accountDetail")) {
            //追加ボタン or アカウント名クリック
            forward = map.findForward("confAccount");
        } else if (cmd.equals("accountDelete")) {
            //削除ボタン
            forward = __doAccountDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("doDeleteAccount")) {
            //削除実行
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("search")) {
            //検索ボタンクリック
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページボタンクリック
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("admTool")) {
            //戻るボタンクリック
            forward = map.findForward("admTool");

            //テンポラリディレクトリを削除
            Wml030Biz biz = new Wml030Biz();
            biz.deleteTempDir(getRequestModel(req));

        } else if (cmd.equals("acntImport")) {
            //インポートボタンクリック
            forward = map.findForward("acntImport");

        } else if (cmd.equals("acntExport")) {
            //エクスポートボタンクリック
            forward = __doExport(map, thisForm, req, res, con);

        } else if (cmd.equals("confLabel")) {
            //「ラベル」ボタンクリック
            forward = map.findForward("confLabel");

        } else if (cmd.equals("confFilter")) {
            //「フィルタ」ボタンクリック
            forward = map.findForward("confFilter");

        } else if (cmd.equals("confMailTemplate")) {
            //「テンプレート」ボタンクリック
            forward = map.findForward("mailTemplateConf");

        } else if (cmd.equals("reloadAuthStatus")) {
            //認証状態更新ボタンクリック
            forward = __doReloadAuthStatus(map, thisForm, req, res, con);

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
    public ActionForward __doInit(ActionMapping map, Wml030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Wml030ParamModel paramMdl = new Wml030ParamModel();
        paramMdl.setParam(form);
        Wml030Biz biz = new Wml030Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        if (form.getAccountList() == null || form.getAccountList().isEmpty()) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage(
                    "search.data.notfound", gsMsg.getMessage("wml.102"));
            StrutsUtil.addMessage(errors, msg,  gsMsg.getMessage("wml.102"));
            addErrors(req, errors);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
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
    public ActionForward __doSearch(ActionMapping map, Wml030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setWml030searchFlg(1);

        } else {
            form.setWml030svKeyword(form.getWml030keyword());
            form.setWml030svGroup(form.getWml030group());
            form.setWml030svUser(form.getWml030user());
            form.setWml030svNinsyo(form.getWml030ninsyo());
            form.setWml030svNinsyoStatus(form.getWml030ninsyoStatus());
            
            //検索実行フラグON
            form.setWml030searchFlg(0);
        }


        return __doInit(map, form, req, res, con);
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
        Wml030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml030pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setWml030pageTop(page);
        form.setWml030pageBottom(page);

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
        Wml030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getWml030pageTop();
        page += 1;
        form.setWml030pageTop(page);
        form.setWml030pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doAccountDelete(
        ActionMapping map,
        Wml030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionErrors errors = form.validateAccount(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] エクスポート処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(ActionMapping map, Wml030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("エクスポート処理");

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Wml030Biz biz = new Wml030Biz();
        String tempDir = biz.getTempDir(reqMdl);

        String fileName = Wml030CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        con.setAutoCommit(true);
        Wml030ParamModel paramMdl = new Wml030ParamModel();
        try {
            paramMdl.setParam(form);
            List<Wml030ExportModel> exportDataList = biz.getExportData(con, paramMdl);

            Wml030CsvWriter csvWriter
                = new Wml030CsvWriter(con, reqMdl,
                                                    exportDataList);
            //テンポラリディレクトリを作成(初期化)
            biz.clearTempDir(reqMdl);

            csvWriter.setCsvPath(fullPath);
            csvWriter.write();

            TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
        } finally {
            con.setAutoCommit(false);

            //TEMPディレクトリ削除
            biz.deleteTempDir(reqMdl);
        }

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.outPutLog(map, reqMdl, con,
                gsMsg.getMessage("cmn.export"), GSConstLog.LEVEL_INFO,
                "");
        return null;
    }

    /**
     * <br>[機  能] 認証状態の更新処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doReloadAuthStatus(ActionMapping map, Wml030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Wml030ParamModel paramMdl = new Wml030ParamModel();
        paramMdl.setParam(form);

        Wml030Biz biz = new Wml030Biz();
        biz.reloadAuthStatus(con, paramMdl, reqMdl);

        return null;
    }

    /**
     * <br>[機  能] 削除確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Wml030Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("mine");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=doDeleteAccount");

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = "";

        Wml030Biz biz = new Wml030Biz();
        msg = msgRes.getMessage("sakujo.kakunin.list",
                                gsMsg.getMessage(req, "wml.102"),
                                biz.getMsgAccountTitle(con, form.getWml030selectAcount()));

        cmn999Form.setMessage(msg);

        cmn999Form.addHiddenParam("CMD", "search");
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("wmlCmdMode", form.getWmlCmdMode());
        cmn999Form.addHiddenParam("wmlViewAccount", form.getWmlViewAccount());
        cmn999Form.addHiddenParam("wmlAccountMode", form.getWmlAccountMode());
        cmn999Form.addHiddenParam("wmlAccountSid", form.getWmlAccountSid());
        cmn999Form.addHiddenParam("wml030svKeyword", form.getWml030svKeyword());
        cmn999Form.addHiddenParam("wml030svGroup", form.getWml030svGroup());
        cmn999Form.addHiddenParam("wml030svUser", form.getWml030svUser());
        cmn999Form.addHiddenParam("wml030svNinsyo", form.getWml030svNinsyo());
        cmn999Form.addHiddenParam("wml030svNinsyoStatus", form.getWml030svNinsyoStatus());
        cmn999Form.addHiddenParam("wml030sortKey", form.getWml030sortKey());
        cmn999Form.addHiddenParam("wml030order", form.getWml030order());
        cmn999Form.addHiddenParam("wml030searchFlg", form.getWml030searchFlg());
        cmn999Form.addHiddenParam("wml030keyword", form.getWml030keyword());
        cmn999Form.addHiddenParam("wml030group", form.getWml030group());
        cmn999Form.addHiddenParam("wml030user", form.getWml030user());
        cmn999Form.addHiddenParam("wml030ninsyo", form.getWml030ninsyo());
        cmn999Form.addHiddenParam("wml030ninsyoStatus", form.getWml030ninsyoStatus());
        cmn999Form.addHiddenParam("wml030selectAcount", form.getWml030selectAcount());

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Wml030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //アカウントを削除する
        Wml030ParamModel paramMdl = new Wml030ParamModel();
        paramMdl.setParam(form);
        Wml030Biz biz = new Wml030Biz();
        biz.deleteAccount(con, paramMdl, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        WmlAccountDao accountDao = new WmlAccountDao(con);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = " [" + gsMsg.getMessage("wml.96") + "]";
        ArrayList<String> delNameList = accountDao.getAccountName(
                                                              form.getWml030selectAcount());
        boolean multiFlg = false;
        for (String name : delNameList) {
            if (multiFlg) {
                msg += "\r\n";
            } else {
                multiFlg = true;
            }
            msg += name;
        }
        msg = StringUtil.trimRengeString(msg, 3000);
        wmlBiz.outPutLog(map, getRequestModel(req), con,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                msg);

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Wml030Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");

        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "wml.102")));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
