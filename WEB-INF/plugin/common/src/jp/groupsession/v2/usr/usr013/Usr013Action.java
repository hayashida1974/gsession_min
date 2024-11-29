package jp.groupsession.v2.usr.usr013;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

/**
 * <br>[機  能] メイン 管理者設定 グループインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr013Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr013Action.class);

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

        if (cmd.equals("Usr013_sample")) {
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
        Usr013Form usr013Form = (Usr013Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("Usr013_groupImp")) {
            //インポートボタン押下
            log__.debug("インポートボタン押下");
            forward = __doKakunin(map, usr013Form, req, res, con);
        } else if (cmd.equals("Usr013_Back")) {
            //戻るボタン押下
            log__.debug("戻るボタン押下");
            forward = map.findForward("back");

            //テンポラリディレクトリを削除
            Usr013Biz biz = new Usr013Biz();
            biz.deleteTempDir(getRequestModel(req));

        } else if (cmd.equals("Usr013kn_Back")) {
            forward = __doDsp(map, usr013Form, req, res, con);

        } else if (cmd.equals("Usr013_sample")) {
            //sample.xlsリンククリック
            log__.debug(GSConstUser.SAMPLE_CSV_FILE_NAME_GROUP + "ダウンロード");
            __doSampleDownLoad(req, res);

            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ ダウンロード **/
            String download = gsMsg.getMessage("cmn.download");

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                    download, GSConstLog.LEVEL_INFO,
                    GSConstUser.SAMPLE_CSV_FILE_NAME_GROUP);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, usr013Form, req, res, con);
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
                                    Usr013Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //テンポラリディレクトリパスの初期化
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (form.getUsr013initFlg() != 1 || cmd.equals("delete")) {
            UsrCommonBiz usrCmnBiz = new UsrCommonBiz(getRequestModel(req));
            usrCmnBiz.clearTempDir(Usr013Biz.SCR_ID);
            form.setUsr013initFlg(1);
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
        Usr013Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //初期表示情報を画面にセットする
        Usr013Biz biz = new Usr013Biz();

        Usr013ParamModel paramMdl = new Usr013ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

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
                                       Usr013Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = map.findForward("kakunin");

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Usr013Biz biz = new Usr013Biz();
        String tempDir = biz.getTempDir(reqMdl);

        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] サンプルCSVをダウンロードします。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception ダウンロード時の例外
     */
    private void __doSampleDownLoad(HttpServletRequest req, HttpServletResponse res)
    throws Exception {

        String fileName = GSConstUser.SAMPLE_CSV_FILE_NAME_GROUP;
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
    }

}