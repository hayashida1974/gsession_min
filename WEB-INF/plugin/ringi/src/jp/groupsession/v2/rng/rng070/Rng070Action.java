package jp.groupsession.v2.rng.rng070;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.csv.RngCsvWriter;
import jp.groupsession.v2.rng.pdf.RngMultiThread;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 完了案件管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng070Action extends AbstractRingiAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng070Action.class);

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
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Rng070Form thisForm = (Rng070Form) form;
        if (cmd.equals("rng040")) {
            log__.debug("*** 管理者設定 稟議。");
            forward = map.findForward("rng040");
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));

        } else if (cmd.equals("rng030")) {
            log__.debug("*** 稟議承認。");
            forward = map.findForward("rng030");

        } else if (cmd.equals("search")) {
            log__.debug("*** 検索ボタン押下。");
            thisForm.setRngAdminSearchFlg(1);
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("rng070del")) {
            log__.debug("*** 削除ボタン押下。");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("delexe")) {
            log__.debug("*** 削除確認OK。");
            forward = __exeDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("pageleft")) {
            log__.debug("*** 左矢印ボタン押下");
            thisForm.setRngAdminPageTop(thisForm.getRngAdminPageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("pageright")) {
            log__.debug("*** 右矢印ボタン押下");
            thisForm.setRngAdminPageTop(thisForm.getRngAdminPageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("export")) {
            log__.debug("エクスポートボタンクリック");
            forward = __doCsvExport(map, thisForm, req, res, con);

        } else if (cmd.equals("csvDownload")) {
            log__.debug("エクスポート(csv)ファイルダウンロード");
            forward = __doCsvDownload(map, thisForm, req, res, con);
        
        } else if (cmd.equals("outputPdf")) {

            log__.debug("PDFファイル出力");
            forward = __doOutputPdf(map, thisForm, req, res, con);
        } else if (cmd.equals("pdfCheck")) {

            log__.debug("PDFファイルチェック");
            forward = __doCheckPdf(map, thisForm, req, res, con);
        } else if (cmd.equals("pdfCancel")) {

            log__.debug("PDF出力キャンセル");
            forward = __doCancelPdf(map, thisForm, req, res, con);
        } else if (cmd.equals("pdf")) {

            log__.debug("PDFファイルダウンロード");
            forward = __doDownloadPdf(map, thisForm, req, res, con);
        } else if (cmd.equals("pdfDelete")) {

            log__.debug("PDFファイル削除");
            forward = __doDeletePdf(map, thisForm, req, res, con);
        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);

        Rng070ParamModel paramMdl = new Rng070ParamModel();
        paramMdl.setParam(form);
        Rng070Biz biz = new Rng070Biz(con, getRequestModel(req));
        biz.initDsp(paramMdl, getSessionUserSid(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }


    /**
     * <br>[機  能] 検索ボタン押下時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSearch(ActionMapping map, Rng070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        ActionErrors errors = null;
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            log__.debug("入力チェックエラーです");
            addErrors(req, errors);
            form.setRngAdminSearchFlg(0);
            return __doInit(map, form, req, res, con);
        }

        form.setRngAdminGroupSid(form.getSltGroupSid());
        form.setRngAdminUserSid(form.getSltUserSid());
        form.setRngAdminKeyword(form.getRngInputKeyword());
        form.setRngAdminApplYearFr(form.getSltApplYearFr());
        form.setRngAdminApplMonthFr(form.getSltApplMonthFr());
        form.setRngAdminApplDayFr(form.getSltApplDayFr());
        form.setRngAdminApplYearTo(form.getSltApplYearTo());
        form.setRngAdminApplMonthTo(form.getSltApplMonthTo());
        form.setRngAdminApplDayTo(form.getSltApplDayTo());
        form.setRngAdminLastManageYearFr(form.getSltLastManageYearFr());
        form.setRngAdminLastManageMonthFr(form.getSltLastManageMonthFr());
        form.setRngAdminLastManageDayFr(form.getSltLastManageDayFr());
        form.setRngAdminLastManageYearTo(form.getSltLastManageYearTo());
        form.setRngAdminLastManageMonthTo(form.getSltLastManageMonthTo());
        form.setRngAdminLastManageDayTo(form.getSltLastManageDayTo());

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String rng = gsMsg.getMessage("rng.62");
        //検索結果が0件の場合、エラーメッセージを表示
        Rng070ParamModel paramMdl = new Rng070ParamModel();
        paramMdl.setParam(form);
        Rng070Biz biz = new Rng070Biz(con, reqMdl);
        int rngCount = biz.getSearchCount(paramMdl);
        paramMdl.setFormData(form);
        if (rngCount <= 0) {
            ActionMessage msg = new ActionMessage("search.data.notfound", rng);
            errors.add("search.data.notfound", msg);
            addErrors(req, errors);
            form.setRngAdminSearchFlg(0);

        } else {
            form.setRngAdminPageTop(1);
            form.setRngAdminSearchFlg(1);
        }
        return __doInit(map, form, req, res, con);
    }
    /**
     * <br>[機  能] 削除ボタン押下時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doDelete(ActionMapping map, Rng070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionErrors errors = null;
        errors = form.validateDel(req);
        if (!errors.isEmpty()) {
            log__.debug("入力チェックエラーです");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面へ遷移
        return __setDeleteDsp(map, req, form);
    }

    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Rng070Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("delexe");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delexe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("delback");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "rng.85");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage(
                        "sakujo.kakunin.once",
                        msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rng070dellist", form.getRng070dellist());
        cmn999Form.addHiddenParam("rngAdminOrderKey", form.getRngAdminOrderKey());
        cmn999Form.addHiddenParam("rngAdminSortKey", form.getRngAdminSortKey());
        cmn999Form.addHiddenParam("rngAdminSearchFlg", form.getRngAdminSearchFlg());
        cmn999Form.addHiddenParam("rngAdminPageTop", form.getRngAdminPageTop());
        cmn999Form.addHiddenParam("rngAdminPageBottom", form.getRngAdminPageBottom());

        cmn999Form.addHiddenParam("rngInputKeyword", form.getRngInputKeyword());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("sltUserSid", form.getSltUserSid());
        cmn999Form.addHiddenParam("sltApplYearFr", form.getSltApplYearFr());
        cmn999Form.addHiddenParam("sltApplMonthFr", form.getSltApplMonthFr());
        cmn999Form.addHiddenParam("sltApplDayFr", form.getSltApplDayFr());
        cmn999Form.addHiddenParam("sltApplYearTo", form.getSltApplYearTo());
        cmn999Form.addHiddenParam("sltApplMonthTo", form.getSltApplMonthTo());
        cmn999Form.addHiddenParam("sltApplDayTo", form.getSltApplDayTo());
        cmn999Form.addHiddenParam("sltLastManageYearFr", form.getSltLastManageYearFr());
        cmn999Form.addHiddenParam("sltLastManageMonthFr", form.getSltLastManageMonthFr());
        cmn999Form.addHiddenParam("sltLastManageDayFr", form.getSltLastManageDayFr());
        cmn999Form.addHiddenParam("sltLastManageYearTo", form.getSltLastManageYearTo());
        cmn999Form.addHiddenParam("sltLastManageMonthTo", form.getSltLastManageMonthTo());
        cmn999Form.addHiddenParam("sltLastManageDayTo", form.getSltLastManageDayTo());
        cmn999Form.addHiddenParam("rng070ApplDateFr", form.getRng070ApplDateFr());
        cmn999Form.addHiddenParam("rng070ApplDateTo", form.getRng070ApplDateTo());
        cmn999Form.addHiddenParam("rng070LastManageDateFr", form.getRng070LastManageDateFr());
        cmn999Form.addHiddenParam("rng070LastManageDateTo", form.getRng070LastManageDateTo());

        cmn999Form.addHiddenParam("rngAdminKeyword", form.getRngAdminKeyword());
        cmn999Form.addHiddenParam("rngAdminGroupSid", form.getRngAdminGroupSid());
        cmn999Form.addHiddenParam("rngAdminUserSid", form.getRngAdminUserSid());
        cmn999Form.addHiddenParam("rngAdminApplYearFr", form.getRngAdminApplYearFr());
        cmn999Form.addHiddenParam("rngAdminApplMonthFr", form.getRngAdminApplMonthFr());
        cmn999Form.addHiddenParam("rngAdminApplDayFr", form.getRngAdminApplDayFr());
        cmn999Form.addHiddenParam("rngAdminApplYearTo", form.getRngAdminApplYearTo());
        cmn999Form.addHiddenParam("rngAdminApplMonthTo", form.getRngAdminApplMonthTo());
        cmn999Form.addHiddenParam("rngAdminApplDayTo", form.getRngAdminApplDayTo());
        cmn999Form.addHiddenParam("rngAdminLastManageYearFr", form.getRngAdminLastManageYearFr());
        cmn999Form.addHiddenParam("rngAdminLastManageMonthFr", form.getRngAdminLastManageMonthFr());
        cmn999Form.addHiddenParam("rngAdminLastManageDayFr", form.getRngAdminLastManageDayFr());
        cmn999Form.addHiddenParam("rngAdminLastManageYearTo", form.getRngAdminLastManageYearTo());
        cmn999Form.addHiddenParam("rngAdminLastManageMonthTo", form.getRngAdminLastManageMonthTo());
        cmn999Form.addHiddenParam("rngAdminLastManageDayTo", form.getRngAdminLastManageDayTo());
        
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 削除実行
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
    private ActionForward __exeDelete(
        ActionMapping map,
        Rng070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        boolean commit = false;
        try {
            RequestModel reqMdl = getRequestModel(req);
            Rng070Biz biz = new Rng070Biz(con, reqMdl);
            Rng070ParamModel paramMdl = new Rng070ParamModel();
            paramMdl.setParam(form);

            // 削除対象の稟議タイトルを取得
            List<String> delRngNames = biz.getRingiNameList(paramMdl, con);

            for (String delrng : form.getRng070dellist()) {
                log__.debug("[ 削除する稟議 ] = " + delrng);
                biz.deleteKanryoRng(
                        paramMdl, getSessionUserSid(req), Integer.parseInt(delrng));
                paramMdl.setFormData(form);
            }
            forward = __setCompDsp(map, req, form);

            GsMessage gsMsg = new GsMessage(reqMdl);
            String delete = gsMsg.getMessage("cmn.delete");
            StringBuilder msgValue = new StringBuilder();
            msgValue.append("[");
            msgValue.append(gsMsg.getMessage("rng.62"));
            msgValue.append("]");
            for (String title : delRngNames) {
                msgValue.append("\r\n");
                msgValue.append(title);
            }

            //ログ出力処理
            RngBiz rngBiz = new RngBiz(con);
            String opCode = delete;

            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_TRACE,
                    msgValue.toString(),
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


        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Rng070Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;

        forwardOk = map.findForward("rng070");

        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String rng = gsMsg.getMessage(req, "rng.62");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", rng));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rngAdminOrderKey", form.getRngAdminOrderKey());
        cmn999Form.addHiddenParam("rngAdminSortKey", form.getRngAdminSortKey());
        cmn999Form.addHiddenParam("rngAdminSearchFlg", form.getRngAdminSearchFlg());
        cmn999Form.addHiddenParam("rngAdminPageTop", form.getRngAdminPageTop());
        cmn999Form.addHiddenParam("rngAdminPageBottom", form.getRngAdminPageBottom());

        cmn999Form.addHiddenParam("rngInputKeyword", form.getRngInputKeyword());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("sltUserSid", form.getSltUserSid());
        cmn999Form.addHiddenParam("sltApplYearFr", form.getSltApplYearFr());
        cmn999Form.addHiddenParam("sltApplMonthFr", form.getSltApplMonthFr());
        cmn999Form.addHiddenParam("sltApplDayFr", form.getSltApplDayFr());
        cmn999Form.addHiddenParam("sltApplYearTo", form.getSltApplYearTo());
        cmn999Form.addHiddenParam("sltApplMonthTo", form.getSltApplMonthTo());
        cmn999Form.addHiddenParam("sltApplDayTo", form.getSltApplDayTo());
        cmn999Form.addHiddenParam("sltLastManageYearFr", form.getSltLastManageYearFr());
        cmn999Form.addHiddenParam("sltLastManageMonthFr", form.getSltLastManageMonthFr());
        cmn999Form.addHiddenParam("sltLastManageDayFr", form.getSltLastManageDayFr());
        cmn999Form.addHiddenParam("sltLastManageYearTo", form.getSltLastManageYearTo());
        cmn999Form.addHiddenParam("sltLastManageMonthTo", form.getSltLastManageMonthTo());
        cmn999Form.addHiddenParam("sltLastManageDayTo", form.getSltLastManageDayTo());
        cmn999Form.addHiddenParam("rng070ApplDateFr", form.getRng070ApplDateFr());
        cmn999Form.addHiddenParam("rng070ApplDateTo", form.getRng070ApplDateTo());
        cmn999Form.addHiddenParam("rng070LastManageDateFr", form.getRng070LastManageDateFr());
        cmn999Form.addHiddenParam("rng070LastManageDateTo", form.getRng070LastManageDateTo());

        cmn999Form.addHiddenParam("rngAdminKeyword", form.getRngAdminKeyword());
        cmn999Form.addHiddenParam("rngAdminGroupSid", form.getRngAdminGroupSid());
        cmn999Form.addHiddenParam("rngAdminUserSid", form.getRngAdminUserSid());
        cmn999Form.addHiddenParam("rngAdminApplYearFr", form.getRngAdminApplYearFr());
        cmn999Form.addHiddenParam("rngAdminApplMonthFr", form.getRngAdminApplMonthFr());
        cmn999Form.addHiddenParam("rngAdminApplDayFr", form.getRngAdminApplDayFr());
        cmn999Form.addHiddenParam("rngAdminApplYearTo", form.getRngAdminApplYearTo());
        cmn999Form.addHiddenParam("rngAdminApplMonthTo", form.getRngAdminApplMonthTo());
        cmn999Form.addHiddenParam("rngAdminApplDayTo", form.getRngAdminApplDayTo());
        cmn999Form.addHiddenParam("rngAdminLastManageYearFr", form.getRngAdminLastManageYearFr());
        cmn999Form.addHiddenParam("rngAdminLastManageMonthFr", form.getRngAdminLastManageMonthFr());
        cmn999Form.addHiddenParam("rngAdminLastManageDayFr", form.getRngAdminLastManageDayFr());
        cmn999Form.addHiddenParam("rngAdminLastManageYearTo", form.getRngAdminLastManageYearTo());
        cmn999Form.addHiddenParam("rngAdminLastManageMonthTo", form.getRngAdminLastManageMonthTo());
        cmn999Form.addHiddenParam("rngAdminLastManageDayTo", form.getRngAdminLastManageDayTo());
        
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] エクスポートボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng070Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doCsvExport(ActionMapping map, Rng070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("エクスポート処理");

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = _getRingiDir(req);

        con.setAutoCommit(false);

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            String responseJson = "{\"result\":\"false\",\"errorCode\":\"1\"}";
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.print(responseJson);
            return null;
        }

        Rng070ParamModel paramMdl = new Rng070ParamModel();
        paramMdl.setParam(form);

        Rng070Biz biz = new Rng070Biz(con, reqMdl);
        int rngCount = biz.getSearchCount(paramMdl);
        paramMdl.setFormData(form);

        form.setRngAdminGroupSid(form.getSltGroupSid());
        form.setRngAdminUserSid(form.getSltUserSid());
        form.setRngAdminKeyword(form.getRngInputKeyword());
        form.setRngAdminApplYearFr(form.getSltApplYearFr());
        form.setRngAdminApplMonthFr(form.getSltApplMonthFr());
        form.setRngAdminApplDayFr(form.getSltApplDayFr());
        form.setRngAdminApplYearTo(form.getSltApplYearTo());
        form.setRngAdminApplMonthTo(form.getSltApplMonthTo());
        form.setRngAdminApplDayTo(form.getSltApplDayTo());
        form.setRngAdminLastManageYearFr(form.getSltLastManageYearFr());
        form.setRngAdminLastManageMonthFr(form.getSltLastManageMonthFr());
        form.setRngAdminLastManageDayFr(form.getSltLastManageDayFr());
        form.setRngAdminLastManageYearTo(form.getSltLastManageYearTo());
        form.setRngAdminLastManageMonthTo(form.getSltLastManageMonthTo());
        form.setRngAdminLastManageDayTo(form.getSltLastManageDayTo());

        if (rngCount <= 0) {
            //検索結果が0件の場合、エラーメッセージを表示
            String responseJson = "{\"result\":\"false\",\"errorCode\":\"2\"}";
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.print(responseJson);
            return null;
        }

        form.setRngAdminSortKey(RngConst.RNG_SORT_KAKUNIN);
        form.setRngAdminOrderKey(RngConst.RNG_ORDER_DESC);
        form.setRngAdminPageTop(1);
        form.setRngAdminSearchFlg(1);

        // CSV出力
        biz.exoprtCsv(reqMdl, paramMdl, getSessionUserSid(req), tempDir.getTempPath());

        String responseJson = "{\"result\":\"true\"}";
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(responseJson);

        return null;
    }
    
    /**
     * <br>[機  能] エクスポート(CSV)ファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     * 
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward __doCsvDownload(ActionMapping map,
            Rng070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        
        log__.debug("エクスポート(CSV)ファイルダウンロード処理");
        
        GSTemporaryPathModel tempDir = _getRingiDir(req);
        String fileName = RngCsvWriter.FILE_NAME;
        String fullPath = tempDir.getTempPath() + fileName;
        
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
        
        //TEMPディレクトリ削除
        GSTemporaryPathUtil.getInstance().clearTempPath(tempDir);
        
        GsMessage gsMsg = new GsMessage();
        //メッセージエクスポート
        String export = gsMsg.getMessage(req, "cmn.export");
        
        //ログ出力処理
        RngBiz rngBiz = new RngBiz(con);
        RequestModel reqMdl = getRequestModel(req);
        rngBiz.outPutLog(map, export, GSConstLog.LEVEL_INFO, fileName, reqMdl);
        
        return null;
    }

    /**
     * <br>[機  能] PDFファイル出力処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doOutputPdf(ActionMapping map,
            Rng070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

      log__.debug("ファイルダウンロード処理(稟議単票PDF)");


      //入力チェック
      ActionErrors errors = form.validateCheck(req);
      if (!errors.isEmpty()) {
          form.setRngAdminSearchFlg(0);
          @SuppressWarnings("rawtypes")
          Iterator it = errors.get();
          MessageResources mres = getResources(req);
          String errMessage = "";
          while (it.hasNext()) {
              ActionMessage error = (ActionMessage) it.next();
              if (errMessage.length() != 0) {
                  errMessage += "<br>";
              }
              errMessage += mres.getMessage(error.getKey(), error.getValues());
          }
          String responseJson =
                  "{\"rtn\":\"false\""
                  + ",\"err\":\"" + errMessage + "\"}";
          res.setContentType("application/json;charset=UTF-8");
          PrintWriter out = res.getWriter();
          out.print(responseJson);
          return null;
      }

      Rng070ParamModel paramMdl = new Rng070ParamModel();
      paramMdl.setParam(form);

      RequestModel reqMdl = getRequestModel(req);
      Rng070Biz biz = new Rng070Biz(con, reqMdl);
      int rngCount = biz.getSearchCount(paramMdl);
      paramMdl.setFormData(form);

      form.setRng070pdfOutputMax(rngCount);

      if (rngCount <= 0) {
          form.setRngAdminSearchFlg(0);
          GsMessage gsMsg = new GsMessage(reqMdl);
          String rng = gsMsg.getMessage("rng.62");
          ActionMessage msg = new ActionMessage("search.data.notfound", rng);
          errors.add("search.data.notfound", msg);
          MessageResources mres = getResources(req);
          String errMessage = mres.getMessage(msg.getKey(), msg.getValues());
          String responseJson =
                  "{\"rtn\":\"false\""
                  + ",\"err\":\"" + errMessage + "\"}";
          res.setContentType("application/json;charset=UTF-8");
          PrintWriter out = res.getWriter();
          out.print(responseJson);
          return null;
      }

      //アプリケーションルートパス取得
      String appRootPath = getAppRootPath();

      paramMdl = new Rng070ParamModel();
      paramMdl.setParam(form);

      //出力処理

        UDate now = new UDate();
        String nowDate = now.getStrYear()
                + now.getStrMonth()
                + now.getStrDay();
        paramMdl.setRng070rngNowDate(nowDate);

        paramMdl.setFormData(form);


        String tempDirId = GSTemporaryPathUtil.getInstance().getTempDirID(reqMdl, getPluginId());
        GSTemporaryPathModel tempDir = new GSTemporaryPathModel(
                _getRingiDir(req), tempDirId);
        GSTemporaryPathUtil.getInstance().createTempDir(tempDir);

        RngMultiThread mt = new RngMultiThread(
                reqMdl,
                paramMdl,
                tempDir,
                getSessionUserSid(req),
                nowDate,
                appRootPath);
        mt.start();

        String responseJson =
                "{\"rtn\":\"true\""
                + ",\"now\":\"" + nowDate + "\""
                        + ",\"hash\":\"" + tempDirId + "\""
                + ",\"cnt\":\"" + rngCount + "\"}";

        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(responseJson);
        return null;
    }

    /**
     * <br>[機  能] PDFファイルチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doCheckPdf(ActionMapping map,
            Rng070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルチェック処理(稟議PDF)");

        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathModel outTempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng070outPutDirHash());

        String outputDir = new GSTemporaryPathModel(outTempDir,
                RngMultiThread.DIRNAME_TEMP).getTempPath();


        //listFilesメソッドを使用して一覧を取得する
        boolean errorFlg = false;
        boolean cancelFlg = false;
        boolean zipStartFlg = false;
        boolean pdfComplateFlg = false;
        String fileSize = "";

        //各フラグファイル存在有無
        File dir = new File(outTempDir.getTempPath());
        File[] list = dir.listFiles();
        if (list != null) {
            String errorDir = outTempDir.getTempPath() + "error.flg";
            String cancelDir = outTempDir.getTempPath() + "cancel.flg";
            String zipStartDir = outTempDir.getTempPath() + "zipStart.flg";
            String pdfCompleatDir = outTempDir.getTempPath() + "pdfCompleat.flg";
            File errorFile = new File(errorDir);
            File cancelFile = new File(cancelDir);
            File zipStartFile = new File(zipStartDir);
            File pdfCompleatFile = new File(pdfCompleatDir);

            if (errorFile.exists()) {
                        errorFlg = true;
            }
            if (zipStartFile.exists()) {
                      zipStartFlg = true;
            }
            if (cancelFile.exists()) {
                cancelFlg = true;
            }
            if (pdfCompleatFile.exists()) {
                pdfComplateFlg = true;
                String downloadTmpName = outTempDir.getTempPath() + form.getRng070rngNowDate()
                        + "_ringi.zip";
                dir = new File(downloadTmpName);
                double fileSize__ = dir.length() / 1024.0;
                if (fileSize__ > 1023.0) {
                    fileSize__ = fileSize__ / 1024.0;
                    BigDecimal bd = new BigDecimal(fileSize__);
                    bd = bd.setScale(1, RoundingMode.UP);
                    fileSize = "" + bd.doubleValue() + "MB";
                } else {
                    BigDecimal bd = new BigDecimal(fileSize__);
                    bd = bd.setScale(1, RoundingMode.UP);
                    fileSize = "" + bd.doubleValue() + "KB";
                }
              }
          }

        //出力件数
        dir = new File(outputDir);
        int count = 0;
        list = dir.listFiles();
        if (list != null) {
            count = list.length;
        }

        String responseJson =
                "{\"count\":\"" + count
                + "\",\"fileSize\":\"" + fileSize
                + "\",\"errorFlg\":\"" + errorFlg
                + "\",\"cancelFlg\":\"" + cancelFlg
                + "\",\"zipStartFlg\":\"" + zipStartFlg
                + "\",\"pdfComplateFlg\":\"" + pdfComplateFlg + "\"}";
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(responseJson);
        return null;
    }
    @Override
    protected String _getTempDirId() {
        return "rng070";
    }

    /**
     * <br>[機  能] PDF出力キャンセル処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doCancelPdf(ActionMapping map,
            Rng070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathModel tempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng070outPutDirHash());

        log__.debug("キャンセル処理(稟議PDF)");

        if (IOTools.isDirCheck(tempDir.getTempPath(), false)) {

            PrintWriter pw = new PrintWriter(tempDir.getTempPath() + "cancel.flg");
            pw.close();
        }

        String responseJson =
                "{\"rtn\":\"true\"}";
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(responseJson);
        return null;
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownloadPdf(ActionMapping map,
            Rng070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルチェック処理(稟議PDF)");
        RequestModel reqMdl = getRequestModel(req);

        GSTemporaryPathModel outTempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng070outPutDirHash());
        String downloadTmpName = form.getRng070rngNowDate()
                + "_ringi.zip";
        String downloadPath = outTempDir.getTempPath() + downloadTmpName;

        File downloadFile = new File(downloadPath);

        if (!downloadFile.exists()) {
            ActionErrors errors = new ActionErrors();
            GsMessage gsMsg = new GsMessage();
            ActionMessage msg = new ActionMessage("search.notfound.tdfkcode",
                    gsMsg.getMessage(req, "cmn.file"));
            errors.add("error.input.format.file", msg);
            addErrors(req, errors);
            form.setRngAdminSearchFlg(0);
            return __doInit(map, form, req, res, con);
        }

      String downloadName = form.getRng070rngNowDate()
              + "_稟議一覧" + ".zip";
      try {
          TempFileUtil.downloadAtachment(req, res, downloadPath, downloadName, Encoding.UTF_8);

      } catch (Exception e) {
          log__.error("稟議一括出力に失敗", e);
      }

      //TEMPディレクトリ削除
      GSTemporaryPathUtil.getInstance().deleteTempPath(outTempDir);

      //ログ出力処理
      RngBiz rngBiz = new RngBiz(con);
      GsMessage gsMsg = new GsMessage();
      String downloadPdf = gsMsg.getMessage(req, "cmn.pdf");
      rngBiz.outPutLog(map, downloadPdf, GSConstLog.LEVEL_INFO, downloadName,  reqMdl, null,
              form.getRngTemplateMode());

        return null;
    }

    /**
     * <br>[機  能] PDFファイル削除処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeletePdf(ActionMapping map,
            Rng070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {
        log__.debug("ファイル削除処理(稟議PDF)");
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathModel outTempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng070outPutDirHash());

      //TEMPディレクトリ削除
        GSTemporaryPathUtil.getInstance().deleteTempPath(outTempDir);

      String responseJson =
              "{\"rtn\":\"true\"}";
      res.setContentType("application/json;charset=UTF-8");
      PrintWriter out = res.getWriter();
      out.print(responseJson);
      return null;
    }
}
