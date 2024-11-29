package jp.groupsession.v2.adr.adr070;

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
import jp.groupsession.v2.adr.AbstractAddressAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 アドレスインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr070Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr070Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "adr070";

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
        if (cmd.equals("downloadCsv")) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] アクション実行
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
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START");

        ActionForward forward = null;

        Adr070Form thisForm = (Adr070Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        GsMessage gsMsg = new GsMessage();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        if (cmd.equals("importAddressConfirm")) {
            log__.debug("インポートボタンクリック");
            forward = __doImport(map, thisForm, req, res, con);

        } else if (cmd.equals("backAddressList")) {
            log__.debug("戻るボタンクリック");
            temp.deleteTempPath(getRequestModel(req),
                    GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);
            forward = map.findForward("adrList");

        } else if (cmd.equals("downloadCsv")) {
            log__.debug("アドレス取込み用csvファイルクリック");
            __doDownLoadCsv(req, res, thisForm);
            //ログ出力処理
            AdrCommonBiz adrBiz = new AdrCommonBiz(con);
            adrBiz.outPutLog(
                    map, req, res,
                    gsMsg.getMessage(req, "cmn.download"),
                    GSConstLog.LEVEL_INFO,
                    "import_address.xls");

        } else if (cmd.equals("tujou")) {
            //会社指定タブクリック
            forward = __doTujouTab(map, thisForm, req, res, con);

        } else if (cmd.equals("douji")) {
            //アドレス・会社情報同時タブクリック
            forward = __doDoujiTab(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
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
        Adr070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        if (form.getAdr070init() == 0) {
            temp.deleteTempPath(getRequestModel(req), GSConstAddress.PLUGIN_ID_ADDRESS,
                    TEMP_DIRECTORY_ID);
            temp.createTempDir(getRequestModel(req),
                    GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);
        }
        //テンポラリディレクトリパスを取得
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        //初期表示情報を取得する
        Adr070Biz biz = new Adr070Biz(getRequestModel(req));

        Adr070ParamModel paramMdl = new Adr070ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl, tempDir, getSessionUserModel(req).getUsrsid());
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

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
        Adr070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);
        //入力チェック
        ActionErrors errors = form.validateCheck(
                con, tempDir, getRequestModel(req), getSessionUserModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        con.setAutoCommit(false);

        saveToken(req);
        return map.findForward("confirmAddressImport");
    }

    /**
     * <br>[機  能] インポート用ファイルをダウンロードします。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @throws Exception ダウンロード時の例外
     */
    private void __doDownLoadCsv(HttpServletRequest req, HttpServletResponse res, Adr070Form form)
    throws Exception {

        String fileName = "";

        if (form.getAdr070cmdMode() == 1) {
            fileName = "import_address_company.xls";
        } else {
            fileName = "import_address.xls";
        }

        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstAddress.PLUGIN_ID_ADDRESS);
        buf.append(File.separator);
        buf.append("templete");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();
        log__.debug("FULLPATH=" + fullPath);
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
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
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doTujouTab(
        ActionMapping map,
        Adr070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();

        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        form.setAdr070cmdMode(0);

        //画面に常に表示する値をセットする
        //初期表示情報を取得する
        Adr070Biz biz = new Adr070Biz(getRequestModel(req));

        Adr070ParamModel paramMdl = new Adr070ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl, tempDir, getSessionUserModel(req).getUsrsid());
        paramMdl.setFormData(form);

        con.setAutoCommit(false);


        return map.getInputForward();
    }

    /**
     * <br>[機  能] アドレス・会社情報同時タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doDoujiTab(
        ActionMapping map,
        Adr070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        //テンポラリディレクトリパスを取得
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        form.setAdr070cmdMode(1);

        //初期表示情報を取得する
        Adr070Biz biz = new Adr070Biz(getRequestModel(req));

        Adr070ParamModel paramMdl = new Adr070ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl, tempDir, getSessionUserModel(req).getUsrsid());
        paramMdl.setFormData(form);

        con.setAutoCommit(false);


        return map.getInputForward();
    }

}
