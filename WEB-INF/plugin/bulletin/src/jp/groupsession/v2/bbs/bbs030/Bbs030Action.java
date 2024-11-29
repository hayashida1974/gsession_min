package jp.groupsession.v2.bbs.bbs030;

import java.io.IOException;
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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.exception.TempFileException;

/**
 * <br>[機  能] 掲示板 フォーラム登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs030Action extends AbstractBulletinAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs030Action.class);

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
        Bbs030Form bbsForm = (Bbs030Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("forumConfirm")) {
            //ＯＫボタンクリック
            forward = __doConfirm(map, bbsForm, req, res, con);
        } else if (cmd.equals("backForumList")) {
            _deleteBulletinTempDir(req, bbsForm);
            //戻るボタンクリック
            forward = map.findForward("backForumList");
        } else if (cmd.equals("changeGrp")) {
            //グループ変更クリック
            forward = __doDsp(map, bbsForm, req, res, con);

        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード"
            forward = __doGetImageFile(map, bbsForm, req, res, con);
        } else if (cmd.equals("bbs030tempdeleteMark")) {
            //添付削除
            forward = __doTempDeleteMark(map, bbsForm, req, res, con);
        } else if (cmd.equals("selectForum")) {
            //親フォーラム選択
            forward = __doSelectParentForum(map, bbsForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, bbsForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws IOException バイナリファイル操作時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     *
     */
    private ActionForward __doInit(
            ActionMapping map,
            Bbs030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException, IOException, TempFileException {
        _initBulletinTempDir(req, form);
        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws IOException バイナリファイル操作時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     *
     */
    private ActionForward __doDsp(ActionMapping map,
        Bbs030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, IOToolsException, IOException, TempFileException {

        con.setAutoCommit(true);
        Bbs030ParamModel paramMdl = new Bbs030ParamModel();
        paramMdl.setParam(form);
        Bbs030Biz biz = new Bbs030Biz();

        //アプリケーションのルートパス
        String appRootPath = getAppRootPath();

        //テンポラリディレクトリパス
        String tempPath = _getBulletinTempDir(req, form);

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        biz.setInitData(cmd, getRequestModel(req), paramMdl, con,
                        tempPath, appRootPath, getSessionUserSid(req));
        paramMdl.setFormData(form);


        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] ＯＫボタンクリック時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doConfirm(
            ActionMapping map,
            Bbs030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //テンポラリディレクトリパス
        String tempPath = _getBulletinTempDir(req, form);
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req, con, tempPath);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("moveConfirm");
    }


    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Bbs030Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {


        Bbs030Biz bbs030Biz = new Bbs030Biz();
        String tempDir =
                _getBulletinTempDir(req, form);
        Cmn110FileModel bean = bbs030Biz.getFileInfo(tempDir);

        if (bean != null) {
            //テンポラリディレクトリパスを取得
            String filePath = IOTools.replaceFileSep(tempDir + "/" + bean.getSaveFileName());

            //画像ファイル読込
            TempFileUtil.downloadInline(req, res, filePath, bean.getFileName(), Encoding.UTF_8);
        }
        return null;
    }

    /**
     * <br>[機  能] アイコン削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doTempDeleteMark(ActionMapping map,
                                      Bbs030Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, IOException, TempFileException {



        //添付ファイルを削除する
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.clearTempPath(getRequestModel(req),
                GSConstBulletin.PLUGIN_ID_BULLETIN,
                form.getTempDirId());

        form.setBbs030ImageName("");
        form.setBbs030ImageSaveName("");

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 親フォーラム選択時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doSelectParentForum(
            ActionMapping map,
            Bbs030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException, IOException, TempFileException {

        return __doDsp(map, form, req, res, con);
    }

}
