package jp.groupsession.v2.wml.wml160;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール アカウントインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160Action extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml160Action.class);

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
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Wml160Form thisForm = (Wml160Form) form;

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

            //テンポラリディレクトリを削除
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.deleteTempDir(getRequestModel(req), Wml160Biz.SCR_ID);

        } else if (cmd.equals("import_account")) {
            //import_account.xlsリンククリック
            //ログ出力
            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);

            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.outPutLog(map, reqMdl, con,
                    gsMsg.getMessage("cmn.download"), GSConstLog.LEVEL_INFO,
                    GSConstWebmail.SAMPLE_CSV_FILE_NAME);
            __doSampleDownLoad(req, res);

        } else if (cmd.equals("deleteFile")) {
            //削除ボタンクリック
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.clearTempDir(getRequestModel(req), Wml160Biz.SCR_ID);

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
        Wml160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //初期表示情報を取得する
        RequestModel reqMdl = getRequestModel(req);
        Wml160ParamModel paramMdl = new Wml160ParamModel();
        paramMdl.setParam(form);
        Wml160Biz biz = new Wml160Biz();
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
        Wml160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        Wml160Biz biz = new Wml160Biz();
        String tempDir = biz.getTempDir(reqMdl);

        con.setAutoCommit(true);
        //入力チェック
        ActionErrors errors = form.validateCheck(con, reqMdl, tempDir);
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
        buf.append(GSConstWebmail.PLUGIN_ID_WEBMAIL);
        buf.append(File.separator);
        buf.append("templete");
        buf.append(File.separator);
        buf.append(GSConstWebmail.SAMPLE_CSV_FILE_NAME);
        String fullPath = buf.toString();
        log__.debug("FULLPATH=" + fullPath);
        TempFileUtil.downloadAtachment(req, res, fullPath,
                                    GSConstWebmail.SAMPLE_CSV_FILE_NAME,
                                    Encoding.UTF_8);
    }
}
