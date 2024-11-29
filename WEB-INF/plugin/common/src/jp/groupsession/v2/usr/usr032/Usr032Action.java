package jp.groupsession.v2.usr.usr032;

import java.io.File;
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
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

/**
 * <br>[機  能] メイン 管理者設定 ユーザインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr032Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr032Action.class);

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

        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("sample"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("Usr032_sample01") || cmd.equals("Usr032_sample02")
                || cmd.equals("Usr032_sample03")) {
            if (downLoadFlg.equals("1")) {
                log__.debug("サンプルCSVファイルダウンロード");
                return true;
            }
        }
        return false;
    }

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
        Usr032Form usr032Form = (Usr032Form) form;

        con.setAutoCommit(true);
        UsrCommonBiz ucBiz = new UsrCommonBiz(con, getRequestModel(req));
        boolean existGroup = ucBiz.isNotAdminGroupExists(con);
        con.setAutoCommit(false);
        if (!existGroup) {
            //システム管理グループ以外のグループが存在しない場合、共通メッセージ画面へ遷移
            return getNotAdminGroupErrorPage(map, req);
        }

        //パスワード変更の有効・無効を設定
        if (canChangePassword(con, 0)) {
            usr032Form.setChangePassword(GSConst.CHANGEPASSWORD_PARMIT);
        } else {
            usr032Form.setChangePassword(GSConst.CHANGEPASSWORD_NOTPARMIT);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("Usr032_userImp")) {
            //インポートボタン押下
            log__.debug("インポートボタン押下");
            forward = __doKakunin(map, usr032Form, req, res, con);
        } else if (cmd.equals("Usr032_Back")) {
            //戻るボタン押下
            log__.debug("戻るボタン押下");
            forward = map.findForward("back");

            //テンポラリディレクトリ削除
            Usr032Biz biz = new Usr032Biz();
            biz.deleteTempDir(getRequestModel(req));
        } else if (cmd.equals("Usr032kn_Back")) {
            forward = __doInit(map, usr032Form, req, res, con);
        } else if(cmd.equals("Usr032kn_ImpComp")) {
            usr032Form.setUsr032initFlg(0);
            forward = __doInit(map, usr032Form, req, res, con);
        } else if (cmd.equals("Usr032_sample01")) {
            //通常sample.xlsリンククリック
            log__.debug("sample.xlsダウンロード");
            __doSampleDownLoad(map, usr032Form, req, res, con);
        } else if (cmd.equals("Usr032_sample02")) {
            //グループ一括sample.xlsリンククリック
            log__.debug("sample02.xlsダウンロード");
            __doSampleDownLoad(map, usr032Form, req, res, con);
        } else if (cmd.equals("Usr032_sample03")) {
            //複数グループsample.xlsリンククリック
            log__.debug("sample03.xlsダウンロード");
            __doSampleDownLoad(map, usr032Form, req, res, con);
        } else if (cmd.equals("tujou")) {
            log__.debug("通常タブクリック");
            forward = __doTabClick(map, usr032Form, req, res, con, cmd);
        } else if (cmd.equals("groupikatu")) {
            log__.debug("グループ一括タブクリック");
            forward = __doTabClick(map, usr032Form, req, res, con, cmd);
        } else if (cmd.equals("multipleGroup")) {
            log__.debug("複数グループ選択");
            forward = __doTabClick(map, usr032Form, req, res, con, cmd);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, usr032Form, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期パラメータ設定
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
    private ActionForward __doInit(ActionMapping map,
                                    Usr032Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //テンポラリディレクトリパスの初期化
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (form.getUsr032initFlg() != 1 || cmd.equals("delete")) {
            UsrCommonBiz usrCmnBiz = new UsrCommonBiz(getRequestModel(req));
            usrCmnBiz.clearTempDir(Usr032Biz.SCR_ID);
            form.setUsr032initFlg(1);
        }

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Usr032Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //テンポラリディレクトリパスを取得
        Usr032Biz biz = new Usr032Biz();
        String tempDir = biz.getTempDir(getRequestModel(req));
        log__.debug("テンポラリディレクトリ = " + tempDir);

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);

        Usr032ParamModel paramMdl = new Usr032ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, tempDir);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確認画面へ遷移Action
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doKakunin(ActionMapping map,
                                       Usr032Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = map.findForward("kakunin");

        //テンポラリディレクトリパスを取得
        Usr032Biz biz = new Usr032Biz();
        String tempDir = biz.getTempDir(getRequestModel(req));
        log__.debug("テンポラリディレクトリパスを取得" + tempDir);

        con.setAutoCommit(true);
        boolean updatePass = (form.getUsr032updatePassFlg() == GSConstUser.PASS_CHANGE_OK);
        ActionErrors errors = form.validateCheck(map, getRequestModel(req), tempDir, con,
                                                                     updatePass);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 通常タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cmd コマンド
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doTabClick(
        ActionMapping map,
        Usr032Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        String cmd) throws Exception {

        log__.debug("タブクリック");

        //テンポラリディレクトリパスを取得
        Usr032Biz biz = new Usr032Biz();
        String tempDir = biz.getTempDir(getRequestModel(req));

        if (cmd.equals("multipleGroup")) {
            form.setUsr032cmdMode(GSConstUser.MODE_MULTIPLE_GROUP);
        } else if (cmd.equals("groupikatu")) {
            form.setUsr032cmdMode(GSConstUser.MODE_GROUP_ALL);
        } else {
            form.setUsr032cmdMode(GSConstUser.MODE_NORMAL);
        }

        //画面に常に表示する値をセットする
        con.setAutoCommit(true);

        Usr032ParamModel paramMdl = new Usr032ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, tempDir);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] サンプルCSVをダウンロードします。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception ダウンロード時の例外
     */
    private void __doSampleDownLoad(ActionMapping map,
                                    Usr032Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
    throws Exception {

        String fileName = null;
        // モードを取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        if (cmd.equals("Usr032_sample01")) {
            fileName = GSConstUser.SAMPLE_CSV_FILE_NAME01;
        } else if (cmd.equals("Usr032_sample02")) {
            fileName = GSConstUser.SAMPLE_CSV_FILE_NAME02;
        } else {
            fileName = GSConstUser.SAMPLE_CSV_FILE_NAME03;
        }



        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstUser.PLUGIN_ID_USER);
        buf.append(File.separator);
        buf.append("doc");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();
        log__.debug("FULLPATH=" + fullPath);
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        GsMessage gsMsg = new GsMessage(req);
        /** メッセージ ダウンロード **/
        String download = gsMsg.getMessage("cmn.download");

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                download, GSConstLog.LEVEL_INFO, fileName);
    }
}