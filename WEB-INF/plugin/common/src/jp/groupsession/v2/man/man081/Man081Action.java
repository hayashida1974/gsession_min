package jp.groupsession.v2.man.man081;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man080.Man080FileModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 手動バックアップ設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man081Action extends AdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man081Action.class);

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

        if (cmd.equals("download")) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] アクションを実行します
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーム
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(
                ActionMapping map,
                ActionForm form,
                HttpServletRequest req,
                HttpServletResponse res,
                Connection con) throws Exception {

        log__.debug("START Man081Action");

        //キャスト
        Man081Form thisForm = (Man081Form) form;
        //アクションフォーワード生成
        ActionForward forward = null;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("コマンド cmd = " + cmd);

        if (cmd.equals("backadmconf")) {
            //管理者設定へ戻るボタンクリック
            forward = map.findForward("gf_main_kanri");

        } else if (cmd.equals("download")) {
            //ファイル名クリック
            forward = __doDownload(map, thisForm, req, res, con);

        } else if (cmd.equals("confirm")) {
            //バックアップ作成ボタンクリック
            forward = __doConfirm(map, thisForm, req, res, con);

        } else if (cmd.equals("backUpOk")) {
            //バックアップ作成確認OKボタンクリック
            forward = __doCreateBackup(map, thisForm, req, res, con);

        } else if (cmd.equals("reload")) {
            //再読込ボタンクリック
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            //削除ボタンクリック
            forward = __setDeleteKakunin(map, thisForm, req);

        } else if (cmd.equals("deleteOk")) {
            //削除OKボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示を行います");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END Man081Action");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     * @throws IOException 空き容量の取得に失敗
     */
    public ActionForward __doInit(
                    ActionMapping map,
                    Man081Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException, IOException {

        Man081ParamModel paramMdl = new Man081ParamModel();
        paramMdl.setParam(form);
        Man081Biz biz = new Man081Biz();

        con.setAutoCommit(true);
        biz.setInitData(con, paramMdl, getAppRootPath());
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        
        // トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] バックアップファイル削除処理を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws IOToolsException ファイルの削除に失敗
     */
    public ActionForward __doDelete(
                    ActionMapping map,
                    Man081Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws IOToolsException {
        
        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("2重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        Man081ParamModel paramMdl = new Man081ParamModel();
        paramMdl.setParam(form);
        Man081Biz biz = new Man081Biz();
        String backupFileName = biz.deleteBackupFile(paramMdl, getAppRootPath());
        paramMdl.setFormData(form);

        //削除ファイル名が取得できなかった場合
        if (backupFileName == null) {
            return _setNotFileDispParam(map, form, req);
        }

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.delete"),
                GSConstLog.LEVEL_INFO, backupFileName);
        return __setDeleteComp(map, form, req);
    }

    /**
     * <br>[機  能] バックアップ作成ボタンクリック時の処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     */
    public ActionForward __doConfirm(
                    ActionMapping map,
                    Man081Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException {

        return __setConfirmDsp(map, form, req);
    }

    /**
     * <br>[機  能] バックアップ作成OKボタンクリック時の処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws Exception SQL実行時例外
     */
    public ActionForward __doCreateBackup(
                    ActionMapping map,
                    Man081Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws Exception {

        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("2重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリパスを取得
        CommonBiz cmnBiz = new CommonBiz();
        //ログ出力
        UDate now = new UDate();
        //バックアップファイルパス
        String backupFileName = "";
        backupFileName += GSConstMain.BACKUPFILE_HEADSTR;
        backupFileName += now.getDateString();
        backupFileName += ".zip";

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.entry"), GSConstLog.LEVEL_INFO, backupFileName);

        Man081Biz biz = new Man081Biz();

        //バックアップファイルを作成する。
        try {
            biz.createBackupFile(
                 con, getAppRootPath(), getPluginConfig(req), getGsContext(), GSConst.GS_DOMAIN);
        } catch (IOToolsException e) {
            return __setBackupErrorDsp(map, req, form);
        }
        return __setKanryou(map, req, form);
    }

    /**
     * <br>[機  能] ダウンロード処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception ダウンロード失敗
     */
    private ActionForward __doDownload(ActionMapping map,
            Man081Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        ActionForward forward = null;

        //バックアップディレクトリの取得
        String backupDir = CommonBiz.getManualBackupDirPath(getAppRootPath());
        log__.debug("バックアップディレクトリ = " + backupDir);

        //ファイルの存在チェックを行う
        String backupFileHashName = form.getMan081backupFile();
        String backupFileName = null;
        //ファイル一覧を設定
        Enumeration<File> fileList = IOTools.getFiles(backupDir);
        Man081Biz biz = new Man081Biz();
        List<Man080FileModel> dspFileList = biz.getFileDataList(fileList);
        boolean dlFlg = false;
        //パラメータのハッシュ値とバックアップディレクトリ内のファイル名のハッシュ値が一致したらダウンロード可能
        for (Man080FileModel fileMdl : dspFileList) {
            String hashFileName = fileMdl.getHashFileName();
            if (backupFileHashName.equals(hashFileName)) {
                backupFileName = fileMdl.getFileName();
                dlFlg = true;
                break;
            }
        }

        if (!dlFlg) {
            //パラメータとバックアップディレクトリ内のファイル名のハッシュ値が一致しなければエラー
            forward = _setNotFileDispParam(map, form, req);
            return forward;
        }

        File backupFilePath = new File(backupDir + File.separator + backupFileName);
        if (!backupFilePath.exists()) {
            //ファイルが無ければエラー画面
            forward = _setNotFileDispParam(map, form, req);
            return forward;
        }
        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.download"), GSConstLog.LEVEL_INFO, backupFileName);

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ダウンロードするファイルの作成
        TempFileUtil.downloadAtachment(req, res, backupFilePath, backupFileName,
                                    Encoding.UTF_8);

        //ダウンロードのため遷移先なし
        return null;
    }

    /**
     * [機  能] ファイルが存在しない場合の警告画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @return 警告画面遷移
     */
    protected ActionForward _setNotFileDispParam(
        ActionMapping map,
        Man081Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("init");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "search.data.notfound";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.BACKUP_FILE_MSG)));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * [機  能] バックアップファイル作成確認画面の設定処理を行う。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @return 確認画面遷移
     */
    private ActionForward __setConfirmDsp(
        ActionMapping map,
        Man081Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        urlForward = map.findForward("confirm");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setUrlCancel(map.findForward("init").getPath());

        //メッセージセット
        String msgState = "create.kakunin.once";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.BACKUP_FILE_MSG)));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] バックアップ完了画面設定処理
     * <br>[解  説] バックアップ完了画面のパラメータセット
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return 完了画面遷移
     */
    private ActionForward __setKanryou(
        ActionMapping map,
        HttpServletRequest req,
        Man081Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("init");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "cmn.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.BACKUP_MANUAL_MSG)));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] バックアップエラー画面設定処理
     * <br>[解  説] バックアップエラー画面のパラメータセット
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return 完了画面遷移
     */
    private ActionForward __setBackupErrorDsp(
        ActionMapping map,
        HttpServletRequest req,
        Man081Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("init");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.fail";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.BACKUP_MANUAL_MSG)));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }



    /**
     * [機  能] バックアップファイル削除確認画面の設定処理を行う。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @return 確認画面遷移
     */
    private ActionForward __setDeleteKakunin(
        ActionMapping map,
        Man081Form form,
        HttpServletRequest req) {

        ActionForward forward = null;

        //バックアップディレクトリの取得
        String backupDir = CommonBiz.getManualBackupDirPath(getAppRootPath());
        log__.debug("バックアップディレクトリ = " + backupDir);

        //ファイルの存在チェックを行う
        String backupFileHashName = form.getMan081backupFile();
        String backupFileName = null;
        //ファイル一覧を設定
        Enumeration<File> fileList = IOTools.getFiles(backupDir);
        Man081Biz biz = new Man081Biz();
        List<Man080FileModel> dspFileList = biz.getFileDataList(fileList);
        boolean dlFlg = false;
        //パラメータのハッシュ値とバックアップディレクトリ内のファイル名のハッシュ値が一致したらダウンロード可能
        for (Man080FileModel fileMdl : dspFileList) {
            String hashFileName = fileMdl.getHashFileName();
            if (backupFileHashName.equals(hashFileName)) {
                backupFileName = fileMdl.getFileName();
                dlFlg = true;
                break;
            }
        }

        if (!dlFlg) {
            //パラメータとバックアップディレクトリ内のファイル名のハッシュ値が一致しなければエラー
            forward = _setNotFileDispParam(map, form, req);
            return forward;
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setUrlOK(map.findForward("delete").getPath());
        cmn999Form.setUrlCancel(map.findForward("init").getPath());

        //メッセージセット
        String msgState = "sakujo.kakunin.list";
        cmn999Form.setMessage(msgRes.getMessage(
                msgState, getInterMessage(req, GSConstMain.BACKUP_FILE_MSG),
                backupFileName));
        cmn999Form.addHiddenParam("man081backupFile", backupFileHashName);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * [機  能] バックアップ削除完了画面の設定処理を行う。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @return 確認画面遷移
     */
    private ActionForward __setDeleteComp(
        ActionMapping map,
        Man081Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setUrlOK(map.findForward("init").getPath());

        //メッセージセット
        String msgState = "sakujo.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(
                msgState, getInterMessage(req, GSConstMain.BACKUP_FILE_MSG)));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}
