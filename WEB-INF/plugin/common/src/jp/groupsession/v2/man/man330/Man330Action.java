package jp.groupsession.v2.man.man330;

import java.io.File;
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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man330.model.Man330CsvModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 所属情報一括設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man330Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man330";

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "ktool";
    /** CMD:エクスポートタブクリック */
    public static final String CMD_EXPORT = "export";
    /** CMD:インポートタブクリック */
    public static final String CMD_IMPORT = "import";
    /** CMD:エクスポート実行ボタンクリック */
    public static final String CMD_EXPORT_EXE = "export_exe";
    /** CMD:インポート実行ボタンクリック */
    public static final String CMD_IMPORT_EXE = "import_exe";
    /** CMD:インポート実行ボタンクリック */
    public static final String CMD_IMPORT_KN = "import_kn";
    /** CMD:サンプルCSVファイルリンククリック */
    public static final String CMD_SAMPLE = "man330_sample";
    /** CMD:再表示 */
    public static final String CMD_REDRAW = "man330kn_back";

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     *
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals(CMD_SAMPLE) || cmd.equals(CMD_EXPORT_EXE)) {
            log__.debug("CSVファイルダウンロード");
                return true;
        }
        return false;
    }

    /**
     * <br>アクション実行
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

        log__.debug("START_MAN330");

        ActionForward forward = null;
        Man330Form thisForm = (Man330Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        log__.debug("CMD==>" + cmd);
        if (cmd.equals(CMD_IMPORT_EXE)) {
            //所属情報一括設定確認(インポート)
            forward = __doImportCheck(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_EXPORT_EXE)) {
            //エクスポート実行
            forward = __doExportCheck(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_BACK)) {
            //戻る
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_EXPORT)) {
            //エクスポートタブ
            thisForm.setMan330cmdMode(GSConstMain.MODE_EXPORT);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_IMPORT)) {
            //インポートタブ
            thisForm.setMan330cmdMode(GSConstMain.MODE_IMPORT);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_SAMPLE)) {
            //サンプルダウンロード
            forward = __doSampleDownLoad(map, req, res, con);

        } else if (cmd.equals(CMD_REDRAW)) {
            //再表示
            forward = __doDsp(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        log__.debug("END_MAN330");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws IOToolsException 取込みファイル操作時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Man330Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException {

        log__.debug("初期表示");

        con.setAutoCommit(true);
        Man330Biz biz = new Man330Biz(con);

        //テンポラリディレクトリパスを削除・生成
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        if (form.getMan330cmdMode().equals(GSConstMain.MODE_EXPORT)) {
            //エクスポート初期設定
            Man330ParamModel paramMdl = new Man330ParamModel();
            paramMdl.setParam(form);
            biz.setDefultCsvOut(paramMdl);
            paramMdl.setFormData(form);
        }

        con.setAutoCommit(false);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doBack(ActionMapping map,
                                    Man330Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws IOToolsException {

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        return map.findForward(CMD_BACK);
    }

    /**
     * <br>[機  能] 所属情報エクスポートの処理
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
    private ActionForward __doExportCheck(
        ActionMapping map,
        Man330Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");

        RequestModel reqMdl = getRequestModel(req);

        ActionErrors errors = form.validateCsvOutCheck(reqMdl);
        //CSV出力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        String fileName = Man330CsvWriter.MAN330_FILE_NAME;
        String fullPath = tempDir + fileName;

        con.setAutoCommit(false);
        __doExport(map, form, req, res, con, tempDir);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        GsMessage gsMsg = new GsMessage(reqMdl);
        //メッセージ エクスポート
        String export = gsMsg.getMessage("cmn.export");

        //ログ出力処理
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                export, GSConstLog.LEVEL_INFO, fileName);

        return null;
    }

    /**
     * <br>[機  能] インポートボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
    * @throws Exception インポート処理時例外
     */
    private ActionForward __doImportCheck(ActionMapping map,
                                           Man330Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con)
        throws Exception {

        log__.debug("インポート処理");
        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        //全グループIDを取得
        Man330Biz biz = new Man330Biz(con);
        List<String> allGpIdList = biz.getAllGrpIdList();

        //全ユーザIDを取得
        List<String> allUsrIdList = biz.getAllUsrIdList();

        con.setAutoCommit(false);

        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con, allGpIdList, allUsrIdList);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        return map.findForward(CMD_IMPORT_KN);
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     */
    private ActionForward __doDsp(ActionMapping map,
                                   Man330Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
        throws SQLException, IOToolsException {
        con.setAutoCommit(true);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        //初期表示情報を画面にセットする
        String cmdMode = NullDefault.getString(form.getMan330cmdMode(), "");
        if (cmdMode.equals(GSConstMain.MODE_IMPORT)) {
            //インポート
            Man330ParamModel paramMdl = new Man330ParamModel();
            paramMdl.setParam(form);
            Man330Biz biz = new Man330Biz(con);
            biz.setInitDataImport(paramMdl, tempDir);
            paramMdl.setFormData(form);
        }

        con.setAutoCommit(false);
        return map.getInputForward();
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
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(ActionMapping map, Man330Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {

        log__.debug("エクスポート処理(CSV)");
        Man330Biz biz = new Man330Biz(con);

        //ユーザ所属グループデータを取得する
        Man330ParamModel paramMdl = new Man330ParamModel();
        paramMdl.setParam(form);
        List<Man330CsvModel> outMdlList = biz.getCsvOutModel(paramMdl);

        //所属するグループが一番多いユーザのグループ数を取得する
        int maxGrpCnt = biz.getMaxGrpCnt(outMdlList);

        //CSVファイルを作成
        Man330CsvWriter write = new Man330CsvWriter(getRequestModel(req), paramMdl);
        write.setUsrDataMdlList(outMdlList);
        write.setMaxGrpCnt(maxGrpCnt);
        write.outputCsv(con, outDir);

        return null;
    }

    /**
     * <br>[機  能] サンプルCSVをダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception ダウンロード時例外
     * @return ActionForward
     */
    private ActionForward __doSampleDownLoad(ActionMapping map,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        log__.debug("サンプルCSVダウンロード");

        String fileName = GSConstMain.SAMPLE_MAN330_CSV_NAME;

        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstUser.PLUGIN_ID_USER);
        buf.append(File.separator);
        buf.append("doc");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        GsMessage gsMsg = new GsMessage(req);
        //メッセージ ダウンロード
        String download = gsMsg.getMessage(req, "cmn.download");

        //ログ出力処理
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                download, GSConstLog.LEVEL_INFO, GSConstMain.SAMPLE_MAN330_CSV_NAME);

        return null;
    }
}
