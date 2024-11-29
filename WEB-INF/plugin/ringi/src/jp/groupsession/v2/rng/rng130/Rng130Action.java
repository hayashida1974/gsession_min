package jp.groupsession.v2.rng.rng130;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.csv.RngCsvWriter;
import jp.groupsession.v2.rng.pdf.RngMultiThread;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議詳細検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng130Action extends AbstractRingiAction {

    /** ディレクトリID */
    private static final String DIRID = "rng130";
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng130Action.class);

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

        Rng130Form thisForm = (Rng130Form) form;
        thisForm.setRng130sessionSid(getSessionUserSid(req));

        if (cmd.equals("prevPage")) {
            log__.debug("前ページクリック");
            thisForm.setRng130pageTop(thisForm.getRng130pageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            log__.debug("次ページクリック");
            thisForm.setRng130pageTop(thisForm.getRng130pageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("backRngList")) {

            log__.debug("戻るボタンクリック");
            forward = map.findForward("backList");
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));

        } else if (cmd.equals("rngDetail")) {

            log__.debug("稟議タイトル(受信、申請、完了)クリック");
            forward = map.findForward("detail");

        } else if (cmd.equals("rngEdit")) {

            log__.debug("稟議タイトル(草稿)クリック");
            forward = map.findForward("edit");

        } else if (cmd.equals("changeType")) {

            log__.debug("種別を変更");
            forward = __doChangeType(map, thisForm, req, res, con);

        } else if (cmd.equals("moveSearch")) {

            log__.debug("他画面からの検索");
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("search")) {

            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("rng130export")) {

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
            forward = __doInitSortSet(map, thisForm, req, res, con);
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));
        }

        return forward;
    }


    /**
     * <br>[機  能] 初期表示ソートセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInitSortSet(ActionMapping map, Rng130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        form.setRng130Type(form.getRngProcMode());

        if (form.getRng130searchFlg() == 0) {
            __doSortType(map, form, req, res, con);
            form.setRng130searchSubject1(1);
            form.setRng130searchSubject2(1);
            form.setRng130searchSubject3(1);
        }

        con.setAutoCommit(false);

        return __doInit(map, form, req, res, con);
    }



    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel userMdl = getSessionUserModel(req);
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());

        // 検索実行済みの場合、入力チェック
        if (form.getRng130searchFlg() > 0) {
            ActionErrors errors = form.validateCheck(req, con, getSessionUserSid(req),
                    true, reqMdl, adminUser);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setRng130searchFlg(0);
            }
        }
        con.setAutoCommit(true);
        Rng130ParamModel paramMdl = new Rng130ParamModel();
        paramMdl.setParam(form);
        Rng130Biz biz = new Rng130Biz(con, reqMdl);
        paramMdl.setRng130Type(paramMdl.getRngProcMode());

        biz.setInitData(reqMdl, paramMdl, getAppRootPath(),
                getSessionUserModel(req), adminUser);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSearch(ActionMapping map, Rng130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //入力チェック
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel userMdl = getSessionUserModel(req);
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());

        ActionErrors errors = form.validateCheck(req, con, getSessionUserSid(req),
                false, reqMdl, adminUser);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setRng130searchFlg(0);
            return __doInit(map, form, req, res, con);
        }

        Rng130ParamModel paramMdl = new Rng130ParamModel();
        paramMdl.setParam(form);
        Rng130Biz biz = new Rng130Biz(con, reqMdl);
        int searchCount = biz.getSearchCount(paramMdl, form.getRng010ViewAccount());
        paramMdl.setFormData(form);
        if (searchCount <= 0) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String rng = gsMsg.getMessage("rng.62");

            ActionMessage msg = new ActionMessage("search.data.notfound", rng);
            errors.add("search.data.notfound", msg);
            addErrors(req, errors);
            form.setRng130searchFlg(0);
        } else {

            form.setSvRngViewAccount(form.getRng010ViewAccount());
            form.setSvRngKeyword(form.getRngKeyword());
            form.setSvRng130Type(form.getRng130Type());
            form.setSvRng130Category(form.getRng010SearchCategory());
            form.setSvGroupSid(form.getSltGroupSid());
            form.setSvUserSid(form.getSltUserSid());
            form.setSvRng130keyKbn(form.getRng130keyKbn());
            form.setSvRng130Status(form.getRng130Status());
            form.setSvRng130searchSubject1(form.getRng130searchSubject1());
            form.setSvRng130searchSubject2(form.getRng130searchSubject2());
            form.setSvRng130searchSubject3(form.getRng130searchSubject3());
            form.setSvSortKey1(form.getSltSortKey1());
            form.setSvRng130orderKey1(form.getRng130orderKey1());
            form.setSvSortKey2(form.getSltSortKey2());
            form.setSvRng130orderKey2(form.getRng130orderKey2());

            form.setSvApplYearFr(form.getSltApplYearFr());
            form.setSvApplMonthFr(form.getSltApplMonthFr());
            form.setSvApplDayFr(form.getSltApplDayFr());
            form.setSvApplYearTo(form.getSltApplYearTo());
            form.setSvApplMonthTo(form.getSltApplMonthTo());
            form.setSvApplDayTo(form.getSltApplDayTo());
            form.setSvLastManageYearFr(form.getSltLastManageYearFr());
            form.setSvLastManageMonthFr(form.getSltLastManageMonthFr());
            form.setSvLastManageDayFr(form.getSltLastManageDayFr());
            form.setSvLastManageYearTo(form.getSltLastManageYearTo());
            form.setSvLastManageMonthTo(form.getSltLastManageMonthTo());
            form.setSvLastManageDayTo(form.getSltLastManageDayTo());

            form.setRng130pageTop(1);
            form.setRng130pageBottom(1);
            form.setRng130searchFlg(1);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] エクスポートボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng130Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doCsvExport(ActionMapping map, Rng130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("エクスポート処理");

        CommonBiz cmnBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel userMdl = getSessionUserModel(req);
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());


        //入力チェック
        ActionErrors errors = form.validateCheck(req, con, getSessionUserSid(req),
                false, reqMdl, adminUser);
        if (!errors.isEmpty()) {
            String responseJson = "{\"result\":\"false\",\"errorCode\":\"1\"}";
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.print(responseJson);
            return null;
        }


        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = _getRingiDir(req);

        con.setAutoCommit(false);


        Rng130ParamModel paramMdl = new Rng130ParamModel();
        paramMdl.setParam(form);

        Rng130Biz biz = new Rng130Biz(con, reqMdl);
        int searchCount = biz.getSearchCount(paramMdl, form.getRng010ViewAccount(), true);
        paramMdl.setFormData(form);

        if (searchCount <= 0) {
            //検索結果が0件の場合、エラーメッセージを表示
            String responseJson = "{\"result\":\"false\",\"errorCode\":\"2\"}";
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.print(responseJson);
            return null;
        }

        // CSV出力
        biz.exoprtCsv(reqMdl, paramMdl, form.getRng010ViewAccount(), tempDir.getTempPath());

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
            Rng130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        
        log__.debug("エクスポート(CSV)ファイルダウンロード処理");
        RequestModel reqMdl = getRequestModel(req);
        
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
        rngBiz.outPutLog(map, export, GSConstLog.LEVEL_INFO, fileName, reqMdl);
        
        return null;
    }

    /**
     * <br>[機  能] 種別変更時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @throws Exception 実行時例外
     */
    public void __doSortType(ActionMapping map, Rng130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        switch (form.getRng130Type()) {
            case RngConst.RNG_MODE_JYUSIN :
                form.setSltSortKey1(RngConst.RNG_SORT_JYUSIN);
                form.setRng130orderKey1(RngConst.RNG_ORDER_ASC);
                break;
            case RngConst.RNG_MODE_SINSEI :
                form.setSltSortKey1(RngConst.RNG_SORT_KAKUNIN);
                form.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            case RngConst.RNG_MODE_KANRYO :
                form.setSltSortKey1(RngConst.RNG_SORT_KAKUNIN);
                form.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            case RngConst.RNG_MODE_SOUKOU :
                form.setSltSortKey1(RngConst.RNG_SORT_TOUROKU);
                form.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            case RngConst.RNG_MODE_KOETU :
                form.setSltSortKey1(RngConst.RNG_SORT_DATE);
                form.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            default :
                form.setSltSortKey1(RngConst.RNG_SORT_TITLE);
                form.setRng130orderKey1(RngConst.RNG_ORDER_ASC);
        }
        form.setSltSortKey2(RngConst.RNG_SORT_TITLE);
        form.setRng130orderKey2(RngConst.RNG_ORDER_ASC);
        form.setRngProcMode(form.getRng130Type());
    }

    /**
     * <br>[機  能] 種別変更時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doChangeType(ActionMapping map, Rng130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        __doSortType(map, form, req, res, con);
        return __doInit(map, form, req, res, con);
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
            Rng130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイル出力処理(稟議PDF)");

        CommonBiz cmnBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel userMdl = getSessionUserModel(req);
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());

        //入力チェック
        ActionErrors errors = form.validateCheck(req, con, getSessionUserSid(req),
                false, reqMdl, adminUser);
        if (!errors.isEmpty()) {
            form.setRng130searchFlg(0);
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

        //PDF生成
        Rng130ParamModel paramMdl = new Rng130ParamModel();

        paramMdl.setParam(form);

        paramMdl.setRng130Type(paramMdl.getSvRng130Type());
        Rng130Biz biz = new Rng130Biz(con, reqMdl);
        int searchCount = biz.getSearchCount(paramMdl, form.getRng010ViewAccount(), true);
        UDate now = new UDate();
        String nowDate = now.getStrYear()
                + now.getStrMonth()
                + now.getStrDay();
        paramMdl.setRng130rngNowDate(nowDate);
        paramMdl.setRng130pdfOutputMax(searchCount);

        paramMdl.setFormData(form);

        if (searchCount <= 0) {
            form.setRng130searchFlg(0);
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

        //ハッシュ値生成
        String tempDirId = GSTemporaryPathUtil.getInstance().getTempDirID(reqMdl, getPluginId());
        GSTemporaryPathModel tempDir =  new GSTemporaryPathModel(
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
                + ",\"cnt\":\"" + searchCount + "\"}";

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
            Rng130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルチェック処理(稟議PDF)");

        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathModel outTempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng130outPutDirHash());

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
            if (cancelFile.exists()) {
                cancelFlg = true;
            }
            if (zipStartFile.exists()) {
                zipStartFlg = true;
            }
            if (pdfCompleatFile.exists()) {
                pdfComplateFlg = true;
                String downloadTmpName = outTempDir.getTempPath() + form.getRng130rngNowDate()
                        + "_ringi.zip";
                dir = new File(downloadTmpName);
                double fileSize__ = dir.length() / 1024.0;
                if (fileSize__ > 1023.0) {
                    fileSize__ = fileSize__ / 1024.0;
                    BigDecimal bd  = new BigDecimal(fileSize__);
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
        return DIRID;
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
    private ActionForward __doCancelPdf(ActionMapping map,
            Rng130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathModel tempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng130outPutDirHash());

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
            Rng130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルチェック処理(稟議PDF)");
        RequestModel reqMdl = getRequestModel(req);
        GSTemporaryPathModel outTempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng130outPutDirHash());

        String downloadTmpName = form.getRng130rngNowDate()
                + "_ringi.zip";
        String downloadPath = outTempDir.getTempPath() + downloadTmpName;

        String downloadName = form.getRng130rngNowDate()
                + "_稟議一覧" + ".zip";

        File downloadFile = new File(downloadPath);

        if (!downloadFile.exists()) {
            ActionErrors errors = new ActionErrors();
            GsMessage gsMsg = new GsMessage();
            ActionMessage msg = new ActionMessage("search.notfound.tdfkcode",
                    gsMsg.getMessage(req, "cmn.file"));
            errors.add("error.input.format.file", msg);
            addErrors(req, errors);
            form.setRng130searchFlg(0);
            return __doInit(map, form, req, res, con);
        }

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
            Rng130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイル削除処理(稟議PDF)");
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathModel outTempDir = new GSTemporaryPathModel(
                _getRingiDir(req), form.getRng130outPutDirHash());


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
