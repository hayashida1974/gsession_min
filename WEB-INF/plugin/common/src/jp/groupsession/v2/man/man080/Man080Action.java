package jp.groupsession.v2.man.man080;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン 管理者設定 自動バックアップ設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man080Action extends AdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man080Action.class);

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

        log__.debug("START Man080Action");

        //キャスト
        Man080Form thisForm = (Man080Form) form;
        //アクションフォーワード生成
        ActionForward forward = null;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("コマンド cmd = " + cmd);

        if (cmd.equals("backadmconf")) {
            //管理者ツールメニューボタンクリック
            forward = map.findForward("menu");

        } else if (cmd.equals("download")) {
            //ファイル名クリック
            forward = __doDownload(map, thisForm, req, res, con);

        } else if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doConfirm(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示を行います");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END Man080Action");
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
                    Man080Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException, IOException {

        Man080ParamModel paramMdl = new Man080ParamModel();
        paramMdl.setParam(form);
        Man080Biz biz = new Man080Biz();

        con.setAutoCommit(true);
        biz.setInitData(con, getRequestModel(req), paramMdl, getAppRootPath());
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理を行います
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
                    Man080Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException {

        saveToken(req);
        return map.findForward("confirm");
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
            Man080Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        ActionForward forward = null;

        //バックアップディレクトリの取得
        String backupDir = CommonBiz.getBackupDirPath(getAppRootPath());
        log__.debug("バックアップディレクトリ = " + backupDir);

        //ファイルの存在チェックを行う
        String backupFileHashName = form.getMan080backupFile();
        String backupFileName = null;
        //ファイル一覧を設定
        Enumeration<File> fileList = IOTools.getFiles(backupDir);
        Man080Biz biz = new Man080Biz();
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
        TempFileUtil.downloadAtachment(req, res, backupFilePath, backupFileName, Encoding.UTF_8);
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
        Man080Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("mine");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "search.data.notfound";
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.backupfile")));
        cmn999Form = __setFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 共通メッセージフォームにフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     * @param form アクションフォーム
     * @return 共通メッセージフォーム
     */
    private Cmn999Form __setFormParam(Cmn999Form cmn999Form, Man080Form form) {
        return cmn999Form;
    }
}
