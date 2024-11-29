package jp.groupsession.v2.adr.adr161;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.adr160.Adr160Action;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr161Action extends Adr160Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr161Action.class);

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

        if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
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

        log__.debug("START_Adr170");
        ActionForward forward = null;

        Adr161Form thisForm = (Adr161Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //権限チェック
        forward = checkPow(map, thisForm, req, con);
        if (forward != null) {
            return forward;
        }

        if (cmd.equals("edit")) {
            log__.debug("編集ボタンクリック");
            forward = map.findForward("adr170Inp");

        } else if (cmd.equals("adr161_back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("adr160");

        } else if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            forward = __doDownLoad(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Adr170");
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
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Adr161Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException, IOException, TempFileException {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Adr161Biz biz = new Adr161Biz(reqMdl);

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);


        Adr161ParamModel paramMdl = new Adr161ParamModel();
        paramMdl.setParam(form);
        ActionForward forward = null;
        AdrCommonBiz commonBiz = new AdrCommonBiz(con);
        AddressBiz adrBiz = new AddressBiz(reqMdl);

        if (commonBiz.canViewContactData(con, paramMdl.getAdr160EditSid()) != 0) {
            forward =  __doNoneAdcDataError(map, form, req, res, con);
        } else if (!adrBiz.isViewAddressData(con,
                paramMdl.getAdr010EditAdrSid(),
                reqMdl.getSmodel().getUsrsid())) {
            forward =  __doNoneAdrDataError(map, form, req, res, con);
        } else {
            biz.getInitData(con, paramMdl, getAppRootPath(), pconfig, getSessionUserModel(req));
            forward =  map.getInputForward();
        }
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
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
    private ActionForward __doDownLoad(
        ActionMapping map,
        Adr161Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        AddressBiz adrBiz = new AddressBiz(getRequestModel(req));

        //アドレスSID
        int adrSid = form.getAdr010EditAdrSid();
        //コンタクト履歴SID
        int adcSid = form.getAdr160EditSid();
        Long fileId = NullDefault.getLong(form.getAdr161TmpFileId(), -1);

        //コンタクト履歴の添付ファイルがダウンロード可能かチェックする
        if (adrBiz.isDownloadContactTmp(
                con, adrSid, adcSid, getSessionUserSid(req), fileId)) {
            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, fileId,
                    GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                GsMessage gsMsg = new GsMessage(req);
                //コンタクト履歴情報取得
                int editSid = form.getAdr160EditSid();
                AdrContactDao adcDao = new AdrContactDao(con);
                AdrContactModel adcMdl = adcDao.select(editSid);
                String albName = "";
                String filName = cbMdl.getBinFileName();

                if (adcMdl != null) {
                    albName = NullDefault.getString(adcMdl.getAdcTitle(), "");
                }
                //ログ出力処理
                AdrCommonBiz adrcBiz = new AdrCommonBiz(con);
                adrcBiz.outPutLog(
                        map, req, res,
                        gsMsg.getMessage("cmn.download"),
                        GSConstLog.LEVEL_INFO,
                        "[" + gsMsg.getMessage("cmn.title") + "]" + albName
                        + "　[" + gsMsg.getMessage("cmn.file.name") + "]" + filName,
                        String.valueOf(fileId));

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
            }

        }
        return null;
    }

    /**
     * <br>アドレス情報が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneAdrDataError(ActionMapping map, Adr161Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //エラー画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("adrList");

        form.setHiddenParam(cmn999Form);

        //アドレス情報
        String textSchedule = gsMsg.getMessage(req, "address.src.2");
        //閲覧
        String textChange = gsMsg.getMessage(req, "cmn.reading");


        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                textSchedule, textChange));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
    /**
     * <br>コンタクト情報が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneAdcDataError(ActionMapping map, Adr161Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //エラー画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("adr160");

        form.setHiddenParam(cmn999Form);

        //コンタクト履歴情報
        String textSchedule = gsMsg.getMessage(req, "address.src.4");
        //閲覧
        String textChange = gsMsg.getMessage(req, "cmn.reading");


        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                textSchedule, textChange));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}