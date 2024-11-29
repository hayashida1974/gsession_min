package jp.groupsession.v2.adr.adr160;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.adr.AbstractAddressAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr020kn.Adr020knForm;
import jp.groupsession.v2.adr.adr160.csv.Adr160CsvWriter;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr160Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr160Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "adr160";

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

        if (cmd.equals("csv")) {
            log__.debug("CSVファイルダウンロード");
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
        Adr160Form thisForm = (Adr160Form) form;
        GsMessage gsMsg = new GsMessage();
        //閲覧権限チェック
        forward = checkPow(map, thisForm, req, con);
        if (forward != null) {
            return forward;
        }
        //エクスポート権限チェック
        if (checkExportPow(map, req, con)) {
            //権限あり
            thisForm.setAdr160exportPower(1);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("adr160add")) {
            log__.debug("追加ボタンクリック");
            forward = map.findForward("adr170Inp");

        } else if (cmd.equals("adr161")) {
            log__.debug("タイトルクリック");
            forward = map.findForward("adr161");

        } else if (cmd.equals("adr160edit")) {
            log__.debug("コンタクト履歴修正");
            forward = map.findForward("adr170Inp");

        } else if (cmd.equals("adr160back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("adr010");

        } else if (cmd.equals("prev")) {
            log__.debug("前ページ");
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("next")) {
            log__.debug("次ページ");
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("csv")) {
            //CSV出力
            forward = __doExportCsv(map, thisForm, req, res, con);

            //ログ出力処理
            AdrCommonBiz adrBiz = new AdrCommonBiz(con);
            adrBiz.outPutLog(
                    map, req, res,
                    gsMsg.getMessage(req, "cmn.export"),
                    GSConstLog.LEVEL_INFO,
                    Adr160CsvWriter.FILE_NAME);

        } else if (cmd.equals("editAdrData")) {
            //アドレス帳名称クリック
            con.setAutoCommit(true);
            AddressBiz addressBiz = new AddressBiz(getRequestModel(req));
            boolean editFlg =
                addressBiz.isEditAddressData(con, thisForm.getAdr010EditAdrSid(),
                                            getSessionUserModel(req).getUsrsid());
            con.setAutoCommit(false);

            if (editFlg) {
                forward = map.findForward("registAddress");
            } else {
                thisForm.setAdr020viewFlg(1);
                Adr020knForm adr020knForm = new Adr020knForm();
                BeanUtils.copyProperties(adr020knForm, thisForm);
                req.setAttribute("adr020knForm", adr020knForm);
                forward = map.findForward("viewAddress");
            }

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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Adr160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //セッションユーザSIDを取得する。
        int userSid = getSessionUserModel(req).getUsrsid();

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Adr160Biz biz = new Adr160Biz(getRequestModel(req));

        Adr160ParamModel paramMdl = new Adr160ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, userSid);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Adr160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getAdr160pageNum1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setAdr160pageNum1(page);
        form.setAdr160pageNum2(page);

        //一覧セット
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doNext(
        ActionMapping map,
        Adr160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getAdr160pageNum1();
        page += 1;
        form.setAdr160pageNum1(page);
        form.setAdr160pageNum2(page);

        //一覧セット
        return __doInit(map, form, req, res, con);
    }

    /**コンタクト履歴情報ダウンロードの処理
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
    private ActionForward __doExportCsv(
        ActionMapping map,
        Adr160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");
        //エクスポート権限チェック
        if (form.getAdr160exportPower() == 0) {
            return map.findForward("gf_power");
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);
        String fileName = Adr160CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        __doExport(map, form, req, res, con, tempDir);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        return null;
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
    private ActionForward __doExport(ActionMapping map, Adr160Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {

        log__.debug("エクスポート処理(CSV)");

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();

        //CSVファイルを作成
        con.setAutoCommit(true);
        Adr160CsvWriter write = new Adr160CsvWriter();
        write.setSessionUserSid(sessionUsrSid);
        write.setAddressSid(form.getAdr010EditAdrSid());
        write.setContactSchType(GSConstAddress.DSP_CONTACT_ADR160);
        write.outputCsv(con, outDir, getRequestModel(req));
        con.setAutoCommit(false);

        return null;
    }

    /**
     * <br>[機  能] エクスポート権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return boolean true:権限あり false:権限なし
     * @throws Exception 実行時例外
     */
    public boolean checkExportPow(ActionMapping map,
            HttpServletRequest req, Connection con)
    throws Exception {

        //ユーザ情報を取得
        HttpSession session = req.getSession(false);
        BaseUserModel usModel = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        //GS管理者権限を取得
        CommonBiz cmnBiz = new CommonBiz();
        boolean gsAdmFlg = cmnBiz.isPluginAdmin(con, usModel, GSConstAddress.PLUGIN_ID_ADDRESS);

        //エクスポート権限を取得
        con.setAutoCommit(true);
        AdrAconfDao dao = new AdrAconfDao(con);
        AdrAconfModel model = dao.selectAconf();
        con.setAutoCommit(false);

        if (!gsAdmFlg && (model == null || model.getAacExport() == GSConstAddress.POW_LIMIT)) {
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 閲覧権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward checkPow(ActionMapping map, Adr160Form form,
            HttpServletRequest req, Connection con)
    throws Exception {
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        try {
            AddressBiz adrBiz = new AddressBiz(reqMdl);
            if (!adrBiz.isViewAddressData(con,
                    form.getAdr010EditAdrSid(),
                    reqMdl.getSmodel().getUsrsid())) {
                return map.findForward("gf_power");
            }
        } finally {
            con.setAutoCommit(false);
        }

        return null;
    }
}
