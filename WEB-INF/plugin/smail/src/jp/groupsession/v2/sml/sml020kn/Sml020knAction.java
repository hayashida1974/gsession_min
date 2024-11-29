package jp.groupsession.v2.sml.sml020kn;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlPushSender;
import jp.groupsession.v2.sml.biz.SmlReceiveFilter;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmailSendModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.sml020.Sml020Biz;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール作成確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml020knAction extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml020knAction.class);
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
     * @see jp.groupsession.v2.sml.AbstractSmlAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeSmail(ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("START_SML020kn");

        ActionForward forward = null;
        Sml020knForm smlform = (Sml020knForm) form;

        //アクセス権限チェック
        try {
            con.setAutoCommit(true);
            //選択されているアカウントが使用可能かを判定する
            SmlCommonBiz smlBiz = new SmlCommonBiz();
            if (!smlBiz.canUseAccount(
                    con, getSessionUserSid(req), smlform.getSmlViewAccount())) {
                return getAuthErrorPage(map, req);
            }

            //返信、転送の場合、元ショートメールが閲覧可能かを判定する
            int editSmlSid = smlform.getSml010EditSid();
            if (editSmlSid > 0) {
                if (!smlBiz.isViewSmail(con, smlform.getSmlViewAccount(),
                        editSmlSid,
                        getSessionUserSid(req))) {
                    return getAuthErrorPage(map, req);
                }
            }
        } finally {
            con.setAutoCommit(false);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        log__.debug("CMD==>" + cmd);
        //送信ボタン押下
        if (cmd.equals("send")) {
            log__.debug("送信ボタン押下");
            forward = __doSend(map, smlform, req, res, con);
            //戻るボタン押下
        } else if (cmd.equals("backFromSmailCreateKn")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, smlform, req, res, con);
        } else if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            forward = __doDownLoad(map, smlform, req, res, con);


        } else if (cmd.equals("getInitData")) {
            log__.debug("初期表示データ取得");
            __doInitData(map, smlform, req, res, con);
            //送信ボタン押下
        } else if (cmd.equals("sendData")) {
            log__.debug("送信ボタン押下");
            forward = __doSendData(map, smlform, req, res, con);


            //初期表示
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, smlform, req, res, con);
        }

        log__.debug("END_SML020kn");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     */
    private ActionForward __doInit(ActionMapping map,
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException, IOException {

        //リクエストパラメータに回覧先がある場合、フォームにセット
        Object obj = req.getAttribute("cmn120userSid");
        if (obj != null) {
            form.setCmn120userSid((String[]) obj);
        }

        return __doRedraw(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doRedraw(ActionMapping map,
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sml020knBiz biz = new Sml020knBiz(reqMdl);

        Sml020knParamModel paramMdl = new Sml020knParamModel();
        paramMdl.setParam(form);
        //宛先名称一覧を設定
        biz.setAtesaki(paramMdl, con);
        //CC名称一覧を設定
        biz.setAtesakiCc(paramMdl, con);
        //BCC名称一覧を設定
        biz.setAtesakiBcc(paramMdl, con);

        //写真表示フラグを設定
        biz.setPhotoDsp(paramMdl, reqMdl, con);

        //表示用内容
        String tmpBody =
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getSml020Body(), ""));
        paramMdl.setSml020knSmsBody(tmpBody);

        //テンポラリディレクトリパスを取得
        Sml020Biz biz020 = new Sml020Biz(reqMdl);
        String tempDir = biz020.getTempDir();

        //添付ファイル情報セット
        biz.setTempFiles(paramMdl, tempDir, con);

        //トランザクショントークン設定
        saveToken(req);

        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doSend(ActionMapping map,
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;
        RequestModel reqMdl = getRequestModel(req);

        try {

            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //テンポラリディレクトリパスを取得
            Sml020Biz biz020 = new Sml020Biz(reqMdl);
            String tempDir = biz020.getTempDir();

            //入力チェック
            ActionErrors errors =
                    form.validateCheck020kn(Sml020knForm.VALIDATE_MODE_SOUSIN, con,
                            reqMdl, tempDir, getAppRootPath());
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doRedraw(map, form, req, res, con);
            }

            //アプリケーションのルートパス
            String appRootPath = getAppRootPath();

            //DBに登録
            Sml020knBiz biz = new Sml020knBiz(reqMdl);
            MlCountMtController cntCon = getCountMtController(req);
            PluginConfig pluginConfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);

            Sml020knParamModel paramMdl = new Sml020knParamModel();
            paramMdl.setParam(form);
            biz.insertMailData(paramMdl, getRequestModel(req), con, cntCon,
                    appRootPath, tempDir, pluginConfig);
            paramMdl.setFormData(form);

            commitFlg = true;

            //テンポラリディレクトリを削除
            biz020.deleteTempDir();

        } catch (ClassNotFoundException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsException", e);
            throw e;
        } catch (IOException e) {
            log__.error("IOException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.sent");

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                + "\n[title]" + form.getSml020Title());

        //完了画面設定
        return __setCompDsp(map, req, form, 1);
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doBack(ActionMapping map,
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws IOToolsException {

        ActionForward forward = null;
        forward = map.findForward("backToSml020");
        return forward;
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param mode 1=送信完了  2=草稿保存完了
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
            HttpServletRequest req,
            Sml020knForm form,
            int mode) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        String procMode = form.getSml020ProcMode();
        String fowardStr = "";

        //TOP画面より
        if (form.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
            cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());
            fowardStr = "backToTop";
        } else {
            //新規、返信、全返信、転送、草稿
            if (procMode.equals(GSConstSmail.MSG_CREATE_MODE_NEW)
                    || procMode.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                    || procMode.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)
                    || procMode.equals(GSConstSmail.MSG_CREATE_MODE_TENSO)
                    || procMode.equals(GSConstSmail.MSG_CREATE_MODE_SOKO)
                    || procMode.equals(GSConstSmail.MSG_CREATE_MODE_COPY)) {

                //画面パラメータをセット
                cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
                cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
                cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
                cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
                cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
                cmn999Form.addHiddenParam("sml020ProcMode", form.getSml020ProcMode());
                cmn999Form.addHiddenParam("sml030SelectedRowNum", form.getSml030SelectedRowNum());
                fowardStr = "backToMsgList";
                //スケジュールから(日間)
            } else if (procMode.equals(GSConstSmail.MSG_CREATE_MODE_SC_NIKKAN)) {
                //画面パラメータをセット
                cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
                cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
                cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
                fowardStr = "backToNikkan";
                //スケジュールから(週間)
            } else if (procMode.equals(GSConstSmail.MSG_CREATE_MODE_SC_SYUKAN)) {
                //画面パラメータをセット
                cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
                cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
                cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
                fowardStr = "backToSyukan";
                //日報(日間)
            } else if (procMode.equals(GSConstSmail.MSG_CREATE_MODE_NTP_NIKKAN)) {
                //画面パラメータをセット
                cmn999Form.addHiddenParam("ntp010DspDate", form.getNtp010DspDate());
                cmn999Form.addHiddenParam("ntp010DspGpSid", form.getNtp010DspGpSid());
                cmn999Form.addHiddenParam("ntp030FromHour", form.getNtp030FromHour());
                fowardStr = "backToNtpNikkan";
                //日報(週間)
            } else if (procMode.equals(GSConstSmail.MSG_CREATE_MODE_NTP_SYUKAN)) {
                //画面パラメータをセット
                cmn999Form.addHiddenParam("ntp010DspDate", form.getNtp010DspDate());
                cmn999Form.addHiddenParam("ntp010DspGpSid", form.getNtp010DspGpSid());
                cmn999Form.addHiddenParam("ntp030FromHour", form.getNtp030FromHour());
                fowardStr = "backToNtpSyukan";
                //在席管理から
            } else if (procMode.equals(GSConstSmail.MSG_CREATE_MODE_ZAISEKI)) {
                //在席管理からポップアップで開かれる場合は下記のコメントを外す
                //cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
                fowardStr = "backToZaiseki";
                //メインから
            } else if (procMode.equals(GSConstSmail.MSG_CREATE_MODE_MAIN)) {
                fowardStr = "backToTop";
            }
            //検索画面から
            if (GSConstSmail.SEARCH_BACK_ON.equals(form.getSml090BackParm())) {
                cmn999Form.addHiddenParam("sml090ProcModeSave", form.getSml090ProcModeSave());
                cmn999Form.addHiddenParam("sml090BackParm", form.getSml090BackParm());
                cmn999Form.addHiddenParam("searchFlg", form.getSearchFlg());
                cmn999Form.addHiddenParam("sml090page1", form.getSml090page1());
                cmn999Form.addHiddenParam("sml090page2", form.getSml090page2());
                cmn999Form.addHiddenParam("sml090SltGroup", form.getSml090SltGroup());
                cmn999Form.addHiddenParam("sml090SltUser", form.getSml090SltUser());
                cmn999Form.addHiddenParam("sml090MailSyubetsu", form.getSml090MailSyubetsu());
                cmn999Form.addHiddenParam("sml090MailMark", form.getSml090MailMark());
                cmn999Form.addHiddenParam("sml090KeyWord", form.getSml090KeyWord());
                cmn999Form.addHiddenParam("sml090KeyWordkbn", form.getSml090KeyWordkbn());
                cmn999Form.addHiddenParam("sml090SearchTarget", form.getSml090SearchTarget());
                cmn999Form.addHiddenParam("sml090SearchSortKey1", form.getSml090SearchSortKey1());
                cmn999Form.addHiddenParam("sml090SearchOrderKey1", form.getSml090SearchOrderKey1());
                cmn999Form.addHiddenParam("sml090SearchSortKey2", form.getSml090SearchSortKey2());
                cmn999Form.addHiddenParam("sml090SearchOrderKey2", form.getSml090SearchOrderKey2());
                cmn999Form.addHiddenParam("cmn120userSid", form.getSml090userSid());
                cmn999Form.addHiddenParam("cmn120SvuserSid", form.getCmn120SvuserSid());
                cmn999Form.addHiddenParam("sml090SvSltGroup", form.getSml090SvSltGroup());
                cmn999Form.addHiddenParam("sml090SvSltUser", form.getSml090SvSltUser());
                cmn999Form.addHiddenParam("sml090SvAtesaki", form.getSml090SvAtesaki());
                cmn999Form.addHiddenParam("sml090SvMailSyubetsu", form.getSml090SvMailSyubetsu());
                cmn999Form.addHiddenParam("sml090SvMailMark", form.getSml090SvMailMark());
                cmn999Form.addHiddenParam("sml090SvKeyWord", form.getSml090SvKeyWord());
                cmn999Form.addHiddenParam("sml090SvKeyWordkbn", form.getSml090SvKeyWordkbn());
                cmn999Form.addHiddenParam("sml090SvSearchTarget", form.getSml090SvSearchTarget());
                cmn999Form.addHiddenParam(
                        "sml090SvSearchOrderKey1", form.getSml090SvSearchOrderKey1());
                cmn999Form.addHiddenParam(
                        "sml090SvSearchSortKey1", form.getSml090SvSearchSortKey1());
                cmn999Form.addHiddenParam(
                        "sml090SvSearchOrderKey2", form.getSml090SvSearchOrderKey2());
                cmn999Form.addHiddenParam(
                        "sml090SvSearchSortKey2", form.getSml090SvSearchSortKey2());

                fowardStr = "backToSearch";
            }
        }

        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward(fowardStr);
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.message");
        String soukou = gsMsg.getMessage(req, "cmn.draft");
        //送信完了
        if (mode == 1) {
            cmn999Form.setMessage(
                    msgRes.getMessage("sousin.kanryo.object", msg));
            //草稿保存完了
        } else if (mode == 2) {
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", soukou));
        }

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getSml020knBinSid();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        Sml020Biz biz020 = new Sml020Biz(reqMdl);
        String tempDir = biz020.getTempDir();

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);

        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());
        GsMessage gsMsg = new GsMessage();

        String download = gsMsg.getMessage(req, "cmn.download");
        //ログ出力内容作成
        StringBuilder valueSb = new StringBuilder();
        valueSb.append(gsMsg.getMessage(req, "cmn.pdf.account"));
        valueSb.append(sacMdl.getSacName());
        valueSb.append("\n");
        valueSb.append(download);
        valueSb.append(" : ");
        valueSb.append(fMdl.getFileName());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                download, GSConstLog.LEVEL_INFO, valueSb.toString(),
                fileId, GSConstSmail.SML_LOG_FLG_DOWNLOAD);

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);

        return null;
    }



    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doInitData(ActionMapping map,
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //リクエストパラメータに回覧先がある場合、フォームにセット
        Object obj = req.getAttribute("cmn120userSid");
        if (obj != null) {
            form.setCmn120userSid((String[]) obj);
        }

        __doRedrawData(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doRedrawData(ActionMapping map,
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        JSONObject jsonData = new JSONObject();

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sml020knBiz biz = new Sml020knBiz(reqMdl);

        Sml020knParamModel paramMdl = new Sml020knParamModel();
        paramMdl.setParam(form);

        //差出人を設定
        biz.setSendAccount(paramMdl, con);
        //宛先名称一覧を設定
        biz.setAtesaki(paramMdl, con);
        //CC名称一覧を設定
        biz.setAtesakiCc(paramMdl, con);
        //BCC名称一覧を設定
        biz.setAtesakiBcc(paramMdl, con);

        //写真表示フラグを設定
        biz.setPhotoDsp(paramMdl, reqMdl, con);

        //表示用内容
        String tmpBody = "";
        if (form.getSml020MailType() == GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
            //テキスト形式
            tmpBody = StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(paramMdl.getSml020Body(), ""));

        } else {
            //HTML形式
            tmpBody = NullDefault.getString(paramMdl.getSml020BodyHtml(), "");
        }
        paramMdl.setSml020knSmsBody(tmpBody);


        //テンポラリディレクトリパスを取得
        Sml020Biz biz020 = new Sml020Biz(reqMdl);
        String tempDir = biz020.getTempDir();

        //添付ファイル情報セット
        biz.setTempFiles(paramMdl, tempDir, con);

        //トランザクショントークン設定
        saveToken(req);

        paramMdl.setFormData(form);

        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        jsonData.element("token", req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY));
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(初期データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * <br>[機  能] メール送信処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 
     * @throws Exception 実行時例外
     */
    private ActionForward __doSendData(ActionMapping map,
            Sml020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        if (!isTokenValid(req, false)) {
            log__.info("2重投稿");
            return getSubmitErrorPage(map, req);
        }

        JSONObject jsonData = new JSONObject();
        RequestModel reqMdl = getRequestModel(req);
        Sml020knBiz biz = new Sml020knBiz(reqMdl);
        Sml020knParamModel paramMdl = new Sml020knParamModel();

        boolean commitFlg = false;
        SmailSendModel sendMdl = null;
        try {

            //テンポラリディレクトリパスを取得
            Sml020Biz biz020 = new Sml020Biz(reqMdl);
            String tempDir = biz020.getTempDir();

            //入力チェック
            ActionErrors errors =
                    form.validateCheck020kn(Sml020Form.VALIDATE_MODE_SOUSIN, con,
                            reqMdl, tempDir, getAppRootPath());
            if (!errors.isEmpty()) {
                form.setErrorsList(__getJsonErrorMsg(req, errors));
                jsonData = JSONObject.fromObject(form);
                PrintWriter out = null;

                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    out = res.getWriter();
                    out.print(jsonData);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(メール送信)");
                    throw e;
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                return null;
            }

            con.setAutoCommit(false);

            //アプリケーションのルートパス
            String appRootPath = getAppRootPath();

            //DBに登録
            MlCountMtController cntCon = getCountMtController(req);
            PluginConfig pluginConfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);

            paramMdl.setParam(form);
            sendMdl = biz.insertMailData(paramMdl, getRequestModel(req), con, cntCon,
                    appRootPath, tempDir, pluginConfig);
            paramMdl.setFormData(form);

            commitFlg = true;

            //テンポラリディレクトリを削除
            biz020.deleteTempDir();

        } catch (ClassNotFoundException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsException", e);
            throw e;
        } catch (IOException e) {
            log__.error("IOException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //フィルタ処理
        new SmlReceiveFilter(con, sendMdl)
            .doFilterJmail();

        //GSショートメールアプリ使用者にPush通知
        SmlPushSender pushSender = new SmlPushSender(reqMdl, con,
                sendMdl.getAccountSidList(),
                paramMdl.getSml020Title(),
                sendMdl.getSmjSid(),
                sendMdl.getSacMdl());
        pushSender.sendPush();


        //オペレーションログ 操作内容を取得
        String opValue = biz.createSendLogValue(req, paramMdl, con, sendMdl.getSmjSid());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                getInterMessage(req, "cmn.sent"),
                GSConstLog.LEVEL_TRACE,
                opValue);

        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(メール送信)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * <br>jsonエラーメッセージ作成
     * @param req リクエスト
     * @param errors エラーメッセージ
     * @throws Exception 実行例外
     * @return errorResult jsonエラーメッセージ
     */
    private List<String> __getJsonErrorMsg(
            HttpServletRequest req, ActionErrors errors) throws Exception {

        @SuppressWarnings("all")
        Iterator iterator = errors.get();

        List<String> errorList = new ArrayList<String>();
        while (iterator.hasNext()) {
            ActionMessage error = (ActionMessage) iterator.next();
            errorList.add(getResources(req).getMessage(error.getKey(), error.getValues()));
        }
        return errorList;
    }
}