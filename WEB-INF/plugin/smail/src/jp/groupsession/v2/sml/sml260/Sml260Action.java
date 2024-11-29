package jp.groupsession.v2.sml.sml260;

import java.io.File;
import java.sql.Connection;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmlSubAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;

/**
 * <br>[機  能] ショートメール アカウントインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260Action extends AbstractSmlSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml260Action.class);

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
        if (cmd.equals("import_account")) {
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
    public ActionForward executeSmail(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Sml260Form thisForm = (Sml260Form) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("importAccountConfirm")) {
            // トランザクショントークン設定
            saveToken(req);
            //インポートボタンクリック
            forward = __doImport(map, thisForm, req, res, con);

        } else if (cmd.equals("beforePage")) {
            //戻るボタンクリック
            forward = map.findForward("beforePage");

            //テンポラリディレクトリ削除
            Sml260Biz biz = new Sml260Biz();
            biz.deleteTempDir(getRequestModel(req));

        } else if (cmd.equals("import_account")) {
            //import_account.xlsリンククリック
            //ログ出力
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
            smlBiz.outPutLog(map, getRequestModel(req),
                    getInterMessage(req, "cmn.download"), GSConstLog.LEVEL_INFO,
                    GSConstSmail.SAMPLE_CSV_FILE_NAME);
            __doSampleDownLoad(req, res);

        } else if (cmd.equals("deleteFile")) {
            //削除ボタンクリック
            Sml260Biz biz = new Sml260Biz();
            biz.clearTempDir(getRequestModel(req));

            forward = __doInit(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
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
    private ActionForward __doInit(
        ActionMapping map,
        Sml260Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Sml260Biz biz = new Sml260Biz();

        //初期表示の場合、テンポラリディレクトリを初期化する
        if (form.getSml260initFlg() != 1) {
            biz.clearTempDir(reqMdl);
            form.setSml260initFlg(1);
        }

        //初期表示情報を取得する
        Sml260ParamModel paramMdl = new Sml260ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, reqMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] インポートボタンクリック時の処理
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
    private ActionForward __doImport(
        ActionMapping map,
        Sml260Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);

        //入力チェック
        Sml260Biz biz = new Sml260Biz();
        String tempDir = biz.getTempDir(getRequestModel(req));
        ActionErrors errors = form.validateCheck(con, req, tempDir);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("importAccountConfirm");
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

        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstSmail.PLUGIN_ID_SMAIL);
        buf.append(File.separator);
        buf.append("templete");
        buf.append(File.separator);
        buf.append(GSConstSmail.SAMPLE_CSV_FILE_NAME);
        String fullPath = buf.toString();
        log__.debug("FULLPATH=" + fullPath);
        TempFileUtil.downloadAtachment(req, res, fullPath,
                                    GSConstSmail.SAMPLE_CSV_FILE_NAME,
                                    Encoding.UTF_8);
    }
}
