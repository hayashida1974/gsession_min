package jp.groupsession.v2.sml.sml030;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBodyBinDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.pdf.SmlPdfModel;
import jp.groupsession.v2.sml.sml010.Sml010Biz;
import jp.groupsession.v2.sml.sml010.Sml010ExportFileModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 内容確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml030Action extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml030Action.class);

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

        if (cmd.equals("downLoad")) {
            log__.debug("添付ファイルダウンロード");
            return true;
        } else if (cmd.equals("allTmpExp")) {
            log__.debug("添付ファイル一括ダウンロード");
            return true;
        }  else if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
            return true;
        } else if (cmd.equals("eml")) {
            log__.debug("emlファイルダウンロード");
            return true;
        } else if (cmd.equals("getBodyFile")) {
            log__.debug("ファイルダウンロード");
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

        log__.debug("START_SML030");

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        ActionForward forward = null;
        Sml030Form smlform = (Sml030Form) form;
        //パラメータセット
        if (cmd.equals("getDetail") || cmd.equals("prevData") || cmd.equals("nextData")) {
            __setDetailParam(smlform, req, cmd);
        }

        //指定されたメール(添付ファイル)を閲覧可能か判定
        if (!cmd.equals("getBodyFile")) {
            long binSid = -1;
            String paramBinSid = smlform.getSml030binSid();
            if (paramBinSid != null && ValidateUtil.isNumber(paramBinSid)) {
                binSid = Long.parseLong(paramBinSid);
            }
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
            if (!smlBiz.isViewSmail(con, smlform.getSmlViewAccount(),
                                    smlform.getSml010SelectedSid(),
                                    binSid,
                                    getSessionUserSid(req))) {
                return getAuthErrorPage(map, req);
            }
        }

        //移動可能フラグ初期化
        smlform.setSml030PrevNextFlg(0);
        //前へボタン押下
        if (cmd.equals("prev")) {
            log__.debug("前へボタン押下");
            forward =
                __doChangeMsg(map, smlform, GSConstSmail.MSG_PREV, req, res, con);
        //次へボタン押下
        } else if (cmd.equals("next")) {
            log__.debug("次へボタン押下");
            forward =
                __doChangeMsg(map, smlform, GSConstSmail.MSG_NEXT, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("backToMsgList")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, smlform, req, res, con);
        //返信ボタン押下
        } else if (cmd.equals("hensin")) {
            log__.debug("返信ボタン押下");
            forward =
                __setSmsgParam(map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_HENSIN);
        //全返信ボタン押下
        } else if (cmd.equals("zenhensin")) {
            log__.debug("全返信ボタン押下");
            forward =
                __setSmsgParam(map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_ZENHENSIN);
        //転送ボタン押下
        } else if (cmd.equals("tenso")) {
            log__.debug("転送ボタン押下");
            forward =
                __setSmsgParam(map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_TENSO);
        //複写して新規作成ボタン押下
        } else if (cmd.equals("copy")) {
            log__.debug("複写して新規作成ボタン押下");
            forward =
                __setSmsgParam(map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_COPY);
            //元に戻すボタン押下
        } else if (cmd.equals("revived")) {
            log__.debug("元に戻すボタン押下");
            forward = __doRevivedConfirmation(map, smlform, req, res, con);
        //元に戻す処理確認画面でOKボタン押下
        } else if (cmd.equals("revivedOk")) {
            log__.debug("元に戻すOKボタン押下");
            forward = __doRevivedOk(map, smlform, req, res, con);
        //削除確認
        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタン押下");
            forward = __doDeleteConfirmation(map, smlform, req, res, con);
        //削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteOk")) {
            log__.debug("削除確認画面でOK押下");
            forward = __doDeleteOk(map, smlform, req, res, con);
            //完全に削除確認(送った先のメールも削除)
        } else if (cmd.equals("deleteAll")) {
            log__.debug("完全に削除ボタン押下");
            forward = __doDeleteAllConfirmation(map, smlform, req, res, con);
        //完全に削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteAllOk")) {
            log__.debug("完全に削除確認画面でOK押下");
            forward = __doDeleteAllOk(map, smlform, req, res, con);
        //添付ファイルダウンロード
        } else if (cmd.equals("downLoad")) {
            log__.debug("添付ファイルダウンロード");
            forward = __doDownLoad(map, smlform, req, res, con);
        //添付ファイル一括ダウンロード
        } else if (cmd.equals("allTmpExp")) {
            log__.debug("添付ファイル一括ダウンロード");
            forward = __doDownLoadAll(map, smlform, req, res, con);

        //添付画像表示
        } else if (cmd.equals("tempview")) {
            log__.debug("添付画像表示");
            forward = __doTempView(map, smlform, req, res, con);
        } else if (cmd.equals("confCancel")) {
            log__.debug("確認画面から戻った時の初期表示");
            forward = __doCancelConf(map, smlform, null, req, res, con);

        //PDF出力
        } else if (cmd.equals("pdf")) {
            log__.debug("ＰＤＦファイルダウンロード");
            forward = __doDownLoadPdf(map, smlform, req, res, con);
        //eml出力
        } else if (cmd.equals("eml")) {
            log__.debug("emlファイルダウンロード");
            forward = __doDownLoadEml(map, smlform, req, res, con);
        //メール情報取得
        } else if (cmd.equals("getDetail")) {
            log__.debug("メール情報取得");

            //パラメータの設定
            __setDetailParam(smlform, req, cmd);

            __getDetail(map, smlform, null, req, res, con);

         //削除確認
        } else if (cmd.equals("deleteData")) {
            log__.debug("削除ボタン押下");
            __doDeleteConfirmationData(map, smlform, req, res, con);
        //削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteOkData")) {
            log__.debug("削除確認画面でOK押下");
            __doDeleteOkData(map, smlform, req, res, con);
        //完全に削除確認
        } else if (cmd.equals("deleteAllData")) {
            log__.debug("完全に削除ボタン押下");
            __doDeleteAllConfirmationData(map, smlform, req, res, con);
        //完全に削除確認画面でOKボタン押下(送った先のメールも削除)
        } else if (cmd.equals("deleteAllOkData")) {
            log__.debug("完全に削除確認画面でOK押下");
            __doDeleteAllOkData(map, smlform, req, res, con);
        //元に戻すボタン押下
        } else if (cmd.equals("revivedData")) {
            log__.debug("元に戻すボタン押下");
            __doRevivedConfirmationData(map, smlform, req, res, con);
        //元に戻す処理確認画面でOKボタン押下
        } else if (cmd.equals("revivedOkData")) {
            log__.debug("元に戻すOKボタン押下");
            __doRevivedOkData(map, smlform, req, res, con);
        //前へボタン押下
        } else if (cmd.equals("prevData")) {
            log__.debug("前へボタン押下");

            //パラメータの設定
            __setDetailParam(smlform, req, cmd);

            __doChangeMsgData(map, smlform, GSConstSmail.MSG_PREV, req, res, con);
        //次へボタン押下
        } else if (cmd.equals("nextData")) {
            log__.debug("次へボタン押下");

            //パラメータの設定
            __setDetailParam(smlform, req, cmd);

            __doChangeMsgData(map, smlform, GSConstSmail.MSG_NEXT, req, res, con);

            //WEBメール共有押下
        } else if (cmd.equals("shareClick")) {
            log__.debug("WEBメール共有押下");
            __doShare(map, smlform, req, res, con);
         //ファイル取得
        } else if (cmd.equals("getBodyFile")) {
            log__.debug("WEBメール共有押下");
            __doGetBodyFile(map, smlform, req, res, con);
         //初期表示
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, smlform, null, req, res, con);
        }
        log__.debug("END_SML030");
        return forward;
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownLoadPdf(ActionMapping map,
                                    Sml030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, Exception {

        log__.debug("ショートメールＰＤＦファイルダウンロード処理");
        ActionForward forward = null;

        //PDFファイルのダウンロード
        forward = __createPdf(map, form, req, res, con);

        return forward;
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイルの書き出しに失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws TempFileException 添付ファイル情報の取得に失敗
     * @throws Exception 実行例外
     */
    private ActionForward __createPdf(ActionMapping map, Sml030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        //テンポラリディレクトリを作成
        RequestModel reqMdl = getRequestModel(req);
        Sml030Biz biz = new Sml030Biz(reqMdl);
        biz.clearTempDir();
        String outDir = biz.getTempDir();

        String procMode = "";

        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);

        //全データ中の位置を把握するためのハッシュ作成
        HashControlModel ret = biz.getAllDataHash(paramMdl, reqMdl, con);
        HashMap<Integer, Sml030Model> hash = ret.getMap();
        paramMdl.setSml030SelectedRowNum(ret.getRowNum());
        Object obj = hash.get(ret.getRowNum());
        if (obj != null) {
            Sml030Model mdl = (Sml030Model) obj;
            paramMdl.setSml010SelectedMailKbn(mdl.getMailKbn());
        }

        procMode = paramMdl.getSml010ProcMode();
        //受信モード
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            //データセット
            biz.setInitDataJusin(paramMdl, reqMdl, con);

            //送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            //データセット
            biz.setInitDataSosin(paramMdl, reqMdl, con);
            //ゴミ箱モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                         || procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            //データセット
            biz.setInitDataGomi(paramMdl, reqMdl, con);
        }

        log__.debug("ファイルダウンロード処理(PDF)");
//        アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        String outTempDir = IOTools.replaceFileSep(outDir + "exppdf/");

        Sml030Biz sml30Biz = new Sml030Biz(reqMdl);
        SmlPdfModel smlMdl = sml30Biz.createSmlPdf(paramMdl, con, appRootPath, outTempDir);
        String outBookName = smlMdl.getFileName();

        String outFilePath = IOTools.setEndPathChar(outTempDir) + smlMdl.getSaveFileName();
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        biz.deleteTempDir();

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(req);
        String downloadPdf = gsMsg.getMessage("sml.167");
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());
        StringBuilder sbr = new StringBuilder();
        sbr.append("[アカウント]");
        sbr.append(sacMdl.getSacName());
        sbr.append("\n");
        sbr.append("[ファイル名]");
        sbr.append(outBookName);
        sbr.append("\n");
        sbr.append("[Subject]");
        sbr.append(smlMdl.getTitle());
        sbr.append("\n");
        sbr.append("[Date]");
        sbr.append(smlMdl.getDate());
        sbr.append("\n");
        sbr.append("[From]");
        sbr.append(NullDefault.getString(smlMdl.getSender(), ""));
        sbr.append("\n");
        sbr.append("[To]");
        sbr.append(NullDefault.getString(smlMdl.getAtesaki(), ""));
        sbr.append("\n");
        sbr.append("[Cc]");
        sbr.append(NullDefault.getString(smlMdl.getAtesakiCC(), ""));
        sbr.append("\n");
        sbr.append("[Bcc]");
        sbr.append(NullDefault.getString(smlMdl.getAtesakiBCC(), ""));
        String opLog = sbr.toString();

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                downloadPdf, GSConstLog.LEVEL_INFO, opLog,
                form.getSml010SelectedSid(), "-1", GSConstSmail.SML_LOG_FLG_PDF);

        paramMdl.setFormData(form);
        return null;
    }

    /**
     * <br>[機  能] emlファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownLoadEml(ActionMapping map,
                                    Sml030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, Exception {

        log__.debug("ショートメールemlファイルダウンロード処理");
        ActionForward forward = null;

        //emlファイルのダウンロード
        forward = __createEml(map, form, req, res, con);

        return forward;
    }

    /**
     * <br>[機  能] emlファイルダウンロード処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイルの書き出しに失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws TempFileException 添付ファイル情報の取得に失敗
     * @throws Exception 実行例外
     */
    private ActionForward __createEml(ActionMapping map, Sml030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        //テンポラリディレクトリを作成
        RequestModel reqMdl = getRequestModel(req);
        Sml030Biz biz = new Sml030Biz(reqMdl);
        biz.clearTempDir();
        String outDir = biz.getTempDir();

        String procMode = "";

        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);

        //全データ中の位置を把握するためのハッシュ作成
        HashControlModel ret = biz.getAllDataHash(paramMdl, reqMdl, con);
        HashMap<Integer, Sml030Model> hash = ret.getMap();
        paramMdl.setSml030SelectedRowNum(ret.getRowNum());
        Object obj = hash.get(ret.getRowNum());
        if (obj != null) {
            Sml030Model mdl = (Sml030Model) obj;
            paramMdl.setSml010SelectedMailKbn(mdl.getMailKbn());
        }

        procMode = paramMdl.getSml010ProcMode();
        //受信モード
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            //データセット
            biz.setInitDataJusin(paramMdl, reqMdl, con);

            //送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            //データセット
            biz.setInitDataSosin(paramMdl, reqMdl, con);
            //ゴミ箱モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                  || procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            //データセット
            biz.setInitDataGomi(paramMdl, reqMdl, con);
        }

        log__.debug("ファイルダウンロード処理(PDF)");

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();

        //プラグイン固有のテンポラリパス取得
        String outTempDir = IOTools.replaceFileSep(outDir + "expeml/");

        Sml030Biz sml30Biz = new Sml030Biz(reqMdl);
        Sml010ExportFileModel exportFileData = sml30Biz.createSmlEml(con, getRequestModel(req),
                                                     paramMdl, appRootPath, outTempDir);

        if (exportFileData != null) {
            //ログ出力
            StringBuilder sbr = new StringBuilder();
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
            String day = UDateUtil.getYymdJ(exportFileData.getSdate(), req);
            String date = UDateUtil.getSeparateHMSJ(exportFileData.getSdate(), req);
            sbr.append("[アカウント]");
            sbr.append(sacMdl.getSacName());
            sbr.append("\n");
            sbr.append(getInterMessage(req, "wml.219"));
            sbr.append(exportFileData.getMessageNum());
            sbr.append("\n");
            sbr.append("[Subject]");
            sbr.append(exportFileData.getSubject());
            sbr.append("\n");
            sbr.append("[Date]");
            sbr.append(day);
            sbr.append(date);
            sbr.append("\n");
            sbr.append("[From]");
            sbr.append(NullDefault.getString(exportFileData.getSender(), ""));
            sbr.append("\n");
            sbr.append("[To]");
            sbr.append(NullDefault.getString(exportFileData.getAtesaki(), ""));
            sbr.append("\n");
            sbr.append("[Cc]");
            sbr.append(NullDefault.getString(exportFileData.getAtesakiCC(), ""));
            sbr.append("\n");
            sbr.append("[Bcc]");
            sbr.append(NullDefault.getString(exportFileData.getAtesakiBCC(), ""));

            String opLog = sbr.toString();
            smlBiz.outPutLog(map, reqMdl,
                    "eml" + getInterMessage(req, "cmn.export"), GSConstLog.LEVEL_INFO, opLog,
                    form.getSml010SelectedSid(), "-1", GSConstSmail.SML_LOG_FLG_EML);

            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする

            //ファイル名用の送信時間を設定
            String mailDate = UDateUtil.getYYMD(exportFileData.getSdate()) + "_"
                    + UDateUtil.getSeparateHMS(exportFileData.getSdate());
            String fileName = mailDate + "_";
            String subject = exportFileData.getSubject();
            if (StringUtil.isNullZeroString(subject)) {
                subject = "message";
            }
            fileName += subject;
            //使用可能なファイル名かチェック
            fileName = smlBiz.fileNameCheck(fileName);
            fileName += ".eml";

            TempFileUtil.downloadAtachment(req, res, exportFileData.getFilePath().getPath(),
                                        fileName, Encoding.UTF_8);

            //TEMPディレクトリ削除
            biz.deleteTempDir();
        }
        return null;
    }

    /**
     * <br>[機  能] 初期表示（確認画面から戻った時）処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param cnt ハッシュコントロール
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doCancelConf(ActionMapping map,
                                    Sml030Form form,
                                    HashControlModel cnt,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {
        //宛先クリア
        form.setCmn120userSid(null);
        //宛先end

        return __doInit(map, form, cnt, req, res, con);
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param cnt ハッシュコントロール
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Sml030Form form,
                                    HashControlModel cnt,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;
        String procMode = "";
        RequestModel reqMdl = getRequestModel(req);

        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);
        try {

            Sml030Biz biz = new Sml030Biz(reqMdl);

            procMode = form.getSml010ProcMode();
            //受信モード
            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
                //データセット
                biz.setInitDataJusin(paramMdl, reqMdl, con);

                //使用可能プラグイン情報を取得する。
                PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);

                //未読メッセージの場合は既読にする
                biz.updateMidokuMsg(paramMdl, reqMdl, con, pconf);

                commitFlg = true;
            //送信モード
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                //データセット
                biz.setInitDataSosin(paramMdl, reqMdl, con);
            //ゴミ箱モード
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                //データセット
                biz.setInitDataGomi(paramMdl, reqMdl, con);
            }
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
        } finally {
            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }

            paramMdl.setFormData(form);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param mode 前へ、次へボタン区分
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doChangeMsg(ActionMapping map,
                                        Sml030Form form,
                                        int mode,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);

        try {
            //全データ中の位置を把握するためのハッシュ作成
            Sml030Biz biz = new Sml030Biz(reqMdl);
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            HashControlModel ret = biz.getAllDataHash(paramMdl, reqMdl, con);
            HashMap<Integer, Sml030Model> hash = ret.getMap();
            paramMdl.setFormData(form);

            if (!hash.isEmpty()) {

                //int rowNum = form.getSml030SelectedRowNum();
                form.setSml030SelectedRowNum(ret.getRowNum());
                int rowNum = form.getSml030SelectedRowNum();

                //前へボタン
                if (mode == GSConstSmail.MSG_PREV) {
                    rowNum -= 1;
                //次へボタン
                } else if (mode == GSConstSmail.MSG_NEXT) {
                    rowNum += 1;
                }

                if (rowNum == 0 || rowNum == hash.size() + 1) {
                    form.setSml030PrevNextFlg(1);
                    return __doInit(map, form, ret, req, res, con);
                }

                form.setSml030SelectedRowNum(rowNum);

                //前、または次のデータのメールSID取得
                Object obj = hash.get(rowNum);
                if (obj != null) {
                    Sml030Model mdl = (Sml030Model) obj;
                    form.setSml010SelectedSid(mdl.getMailSid());
                    form.setSml010PageNum(mdl.getPageNum());
                    form.setSml010SelectedMailKbn(mdl.getMailKbn());
                } else {
                    form.setSml030PrevNextFlg(1);
                }
            } else {
                form.setSml030PrevNextFlg(1);
            }

            //取得したメールSIDで再表示
            return __doInit(map, form, ret, req, res, con);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
    }

    /**
     * <br>[機  能] 削除確認画面表示処理
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
     */
    private ActionForward __doDeleteConfirmation(ActionMapping map,
                                                  Sml030Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        //削除するメッセージの件名を取得する
        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);
        Sml030Biz biz = new Sml030Biz(getRequestModel(req));
        String mailName = biz.getDelMsgTitle(paramMdl, con);
        paramMdl.setFormData(form);

        return __setDeleteDsp(map, req, form, mailName);
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map,
                                    Sml030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        ActionForward forward = null;
        String procMode = form.getSml010ProcMode();

        log__.debug("---------" + form.getSml090BackParm());
        //検索画面へ戻る場合
        if (GSConstSmail.SEARCH_BACK_ON.equals(form.getSml090BackParm())) {
            return map.findForward("backToSearch");
        }

        //一覧画面からの遷移時
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
            || procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
            || procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            forward = map.findForward("backToMsgList");
        //メイン画面(TOP)からの遷移の場合
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            forward = map.findForward("backToTop");
        }

        return forward;
    }

    /**
     * <br>[機  能] 復旧処理確認
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
     */
    private ActionForward __doRevivedConfirmation(ActionMapping map,
                                                   Sml030Form form,
                                                   HttpServletRequest req,
                                                   HttpServletResponse res,
                                                   Connection con)
        throws SQLException {

        ActionForward forward = null;
        con.setAutoCommit(true);
        try {

            RequestModel reqMdl = getRequestModel(req);
            Sml030Biz biz = new Sml030Biz(reqMdl);

            //復旧対象のメールタイトル一覧を取得する
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            String mailName = biz.getMailName(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            //復旧確認画面を設定
            forward = __setRevivedDsp(map, req, form, mailName);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return forward;
    }


    /**
     * <br>[機  能] 復旧確認画面でOKボタン押下
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
     */
    private ActionForward __doRevivedOk(ActionMapping map,
                                         Sml030Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);

        try {

            //復旧処理実行
            Sml030Biz biz = new Sml030Biz(reqMdl);
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            biz.revivedMessage(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String edit = gsMsg.getMessage(req, "cmn.edit");
            String msgReturn = gsMsg.getMessage(req, "cmn.undo");

            //ログ出力処理
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl,
                    edit, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                                 + "\n" + msgReturn);

            commitFlg = true;

            //完了画面設定
            return __setCompRevived(map, req, form);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下
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
     */
    private ActionForward __doDeleteOk(ActionMapping map,
                                        Sml030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);

        try {

            //削除処理実行
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            Sml030Biz biz = new Sml030Biz(reqMdl);
            biz.deleteMessage(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            //ログ出力処理
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl,
                    delete, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                                 + "\n");

            commitFlg = true;

            //完了画面設定
            return __setCompDsp(map, req, form);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 完全に削除確認画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteAllConfirmation(ActionMapping map,
                                                  Sml030Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        con.setAutoCommit(true);
        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);

        String mailName = null;
        try {
            Sml030Biz biz = new Sml030Biz(getRequestModel(req));
            //完全に削除可能かチェック
            if (!biz.isDeleteAll(paramMdl, con)) {
                ActionErrors errors = new ActionErrors();
                ActionMessage msg =
                    new ActionMessage(
                        "error.alldelete.mail.delete");
                StrutsUtil.addMessage(errors, msg, "mailDeleteAll");
                addErrors(req, errors);
                return __doInit(map, form, null, req, res, con);
            }
            //削除するメッセージの件名を取得する
             mailName = biz.getDelMsgTitle(paramMdl, con);
        } finally {
            paramMdl.setFormData(form);
        }

        return __setDeleteAllDsp(map, req, form, mailName);
    }
    /**
     * <br>[機  能] 完全に削除確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteAllOk(ActionMapping map,
                                        Sml030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);

        //完全に削除可能かチェック
        Sml030Biz biz = new Sml030Biz(reqMdl);
        if (!biz.isDeleteAll(paramMdl, con)) {
            paramMdl.setFormData(form);
            ActionErrors errors = new ActionErrors();
            ActionMessage msg =
                new ActionMessage(
                    "error.alldelete.mail.delete");
            StrutsUtil.addMessage(errors, msg, "mailDeleteAll");
            return __doInit(map, form, null, req, res, con);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {

            //削除処理実行
            biz.allDeleteMessage(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");
            String allDelete = gsMsg.getMessage(req, "cmn.delete.all");

            //ログ出力処理
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl,
                    delete, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                                 + "\n" + allDelete);

            commitFlg = true;

            //完了画面設定
            return __setAllDeleteCompDsp(map, req, form);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 元に戻す確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param mailName 復帰メール件名
     * @return ActionForward フォワード
     */
    private ActionForward __setRevivedDsp(ActionMapping map,
                                           HttpServletRequest req,
                                           Sml030Form form,
                                           String mailName) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=revivedOk");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);

        String mailKbnName = "";
        String mailKbn = form.getSml010SelectedMailKbn();

        GsMessage gsMsg = new GsMessage();
        String msg = "";

        //受信メール
        if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            msg = gsMsg.getMessage(req, "sml.100");
            mailKbnName = msg + " ";
        //送信メール
        } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            msg = gsMsg.getMessage(req, "sml.102");
            mailKbnName = msg + " ";
        //草稿
        } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            msg = gsMsg.getMessage(req, "sml.101");
            mailKbnName = msg + " ";
        }

        String msg2 = gsMsg.getMessage(req, "cmn.message");
        String ten = gsMsg.getMessage(req, "wml.231");
        cmn999Form.setMessage(
                msgRes.getMessage(
                        "move.former.mail", msg2,
                        ten
                        + mailKbnName
                        + StringUtilHtml.transToHTmlPlusAmparsant(mailName)));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("sml010SelectedMailKbn", form.getSml010SelectedMailKbn());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());

        //検索から遷移時のパラメータセット
        __setSearchHiddenParm(cmn999Form, form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 元に戻す完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompRevived(ActionMapping map,
                                            HttpServletRequest req,
                                            Sml030Form form) {

        GsMessage gsMsg = new GsMessage();

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());

        //検索から遷移時のパラメータセット
        __setSearchHiddenParm(cmn999Form, form);

        //OKボタンクリック時遷移先
        String nextMap = "backToMsgList";
        //検索画面から遷移時
        if (GSConstSmail.SEARCH_BACK_ON.equals(form.getSml090BackParm())) {
            nextMap = "backToSearch";
        }
        ActionForward forwardOk = map.findForward(nextMap);
        cmn999Form.setUrlOK(forwardOk.getPath());

        String msg = gsMsg.getMessage(req, "cmn.message");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("move.former.object", msg));

        int selectedSid = form.getSml010SelectedSid();
        String[] delList = form.getSml010DelSid();
        String[] newDelList = null;
        ArrayList<String> delArray = new ArrayList<String>();

        //選択チェックhiddenリスト再生成(削除したデータにチェックされていた場合に外すため)
        if (delList != null && delList.length > 0) {
            for (int i = 0; i < delList.length; i++) {
                 if (selectedSid != Integer.parseInt(delList[i].substring(1))) {
                    delArray.add(delList[i]);
                }
            }
            if (delArray.isEmpty()) {
                newDelList = new String[0];
            } else {
                newDelList =
                    (String[]) delArray.toArray(new String[delArray.size()]);
            }
        }

        cmn999Form.addHiddenParam("sml010DelSid", newDelList);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param mailName 削除メール件名
     * @return ActionForward フォワード
     */
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Sml030Form form,
                                          String mailName) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        cmn999Form.setUrlCancel(forwardCancel.getPath() + "?" + GSConst.P_CMD + "=confCancel");

        //メッセージ
        MessageResources msgRes = getResources(req);

        String mailKbnName = "";
        String mailKbn = form.getSml010SelectedMailKbn();

        GsMessage gsMsg = new GsMessage();
        String msg = "";

        //受信メール
        if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            msg = gsMsg.getMessage(req, "sml.100");
            mailKbnName = msg + " ";
        //送信メール
        } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            msg = gsMsg.getMessage(req, "sml.102");
            mailKbnName = msg + " ";
        //草稿
        } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            msg = gsMsg.getMessage(req, "sml.101");
            mailKbnName = msg + " ";
        }

        String msgId = "";
        if (form.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            msgId = "sakujo.kakunin.list";
        } else {
            msgId = "move.gomibako.mail";
        }

        String msg2 = gsMsg.getMessage(req, "cmn.message");
        String msg3 = gsMsg.getMessage(req, "wml.231");

        cmn999Form.setMessage(
                msgRes.getMessage(
                        msgId,
                        msg2,
                        msg3
                        + mailKbnName
                        + StringUtilHtml.transToHTmlPlusAmparsant(mailName)));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("sml010SelectedMailKbn", form.getSml010SelectedMailKbn());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());

        //検索から遷移時のパラメータセット
        __setSearchHiddenParm(cmn999Form, form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 完全削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param mailName 削除メール件名
     * @return ActionForward フォワード
     */
    private ActionForward __setDeleteAllDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Sml030Form form,
                                          String mailName) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteAllOk");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        cmn999Form.setUrlCancel(forwardCancel.getPath() + "?" + GSConst.P_CMD + "=confCancel");

        //メッセージ
        MessageResources msgRes = getResources(req);

        GsMessage gsMsg = new GsMessage();
        String send = gsMsg.getMessage(req, "sml.102");
        String msg = gsMsg.getMessage(req, "cmn.message");
        String ten = gsMsg.getMessage(req, "wml.231");

        String mailKbnName = send + " ";

        String msgId = "move.alldelete.mail";
        cmn999Form.setMessage(
                msgRes.getMessage(
                        msgId,
                        msg,
                        ten
                        + mailKbnName
                        + StringUtilHtml.transToHTmlPlusAmparsant(mailName)));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("sml010SelectedMailKbn", form.getSml010SelectedMailKbn());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());

        //検索から遷移時のパラメータセット
        __setSearchHiddenParm(cmn999Form, form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Sml030Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        String procMode = form.getSml010ProcMode();

        //検索画面から遷移時
        if (GSConstSmail.SEARCH_BACK_ON.equals(form.getSml090BackParm())) {
            forwardOk = map.findForward("backToSearch");

        // 受信、送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
            || procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
            || procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            forwardOk = map.findForward("backToMsgList");
        //受信モード(TOP画面より)
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            forwardOk = map.findForward("backToTop");
        }

        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        String msgId = "";

        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            msgId = "sakujo.kanryo.object";
        } else {
            msgId = "move.gomibako.object";
        }

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.message");

        cmn999Form.setMessage(
                msgRes.getMessage(msgId, msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());

        //検索から遷移時のパラメータセット
        __setSearchHiddenParm(cmn999Form, form);

        int selectedSid = form.getSml010SelectedSid();
        String[] delList = form.getSml010DelSid();
        String[] newDelList = null;
        ArrayList<String> delArray = new ArrayList<String>();

        //選択チェックhiddenリスト再生成(削除したデータにチェックされていた場合に外すため)
        if (delList != null && delList.length > 0) {
            for (int i = 0; i < delList.length; i++) {
                 if (selectedSid != Integer.parseInt(delList[i].substring(1))) {
                    delArray.add(delList[i]);
                }
            }
            if (delArray.isEmpty()) {
                newDelList = new String[0];
            } else {
                newDelList =
                    (String[]) delArray.toArray(new String[delArray.size()]);
            }
        }

        cmn999Form.addHiddenParam("sml010DelSid", newDelList);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 全て削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setAllDeleteCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Sml030Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        String procMode = form.getSml010ProcMode();

        //検索画面から遷移時
        if (GSConstSmail.SEARCH_BACK_ON.equals(form.getSml090BackParm())) {
            forwardOk = map.findForward("backToSearch");

        // 受信、送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
            || procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
            || procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            forwardOk = map.findForward("backToMsgList");
        //受信モード(TOP画面より)
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            forwardOk = map.findForward("backToTop");
        }

        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        String msgId = "sakujo.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.message");

        cmn999Form.setMessage(
                msgRes.getMessage(msgId, msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());

        //検索から遷移時のパラメータセット
        __setSearchHiddenParm(cmn999Form, form);

        int selectedSid = form.getSml010SelectedSid();
        String[] delList = form.getSml010DelSid();
        String[] newDelList = null;
        ArrayList<String> delArray = new ArrayList<String>();

        //選択チェックhiddenリスト再生成(削除したデータにチェックされていた場合に外すため)
        if (delList != null && delList.length > 0) {
            for (int i = 0; i < delList.length; i++) {
                 if (selectedSid != Integer.parseInt(delList[i].substring(1))) {
                    delArray.add(delList[i]);
                }
            }
            if (delArray.isEmpty()) {
                newDelList = new String[0];
            } else {
                newDelList =
                    (String[]) delArray.toArray(new String[delArray.size()]);
            }
        }

        cmn999Form.addHiddenParam("sml010DelSid", newDelList);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 画面遷移パラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @param form フォーム
     * @param mode 処理モード
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __setSmsgParam(ActionMapping map,
                                          HttpServletRequest req,
                                          Connection con,
                                          Sml030Form form,
                                          String mode)
        throws SQLException {

        con.setAutoCommit(true);
        Sml020Form sml020Form = new Sml020Form();
        sml020Form.setSml010ProcMode(form.getSml010ProcMode());
        sml020Form.setSml010Sort_key(form.getSml010Sort_key());
        sml020Form.setSml010Order_key(form.getSml010Order_key());
        sml020Form.setSml010PageNum(form.getSml010PageNum());
        sml020Form.setSml010SelectedDelSid(form.getSml010SelectedDelSid());
        sml020Form.setSml010DelSid(form.getSml010DelSid());
        sml020Form.setSml020ProcMode(mode);
        sml020Form.setSchWeekDate(form.getSchWeekDate());
        sml020Form.setSchDailyDate(form.getSchDailyDate());

        String[] cmn120userSid = null;

        RequestModel reqMdl = getRequestModel(req);
        Sml030Biz biz = new Sml030Biz(reqMdl);
        String procMode = form.getSml010ProcMode();

        ArrayList<SmailDetailModel> ret = null;
        ArrayList<String> sidArray = new ArrayList<String>();

       /************************************************************************
        *
        * 宛先ユーザ配列を作成する
        *
        ************************************************************************/

        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);

        //受信一覧、メイン(TOP)画面からの遷移の場合
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
            || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            ret = biz.getAtesakiZyusin(paramMdl, reqMdl, con);
        //送信一覧からの遷移の場合
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            ret = biz.getAtesakiSosin(paramMdl, reqMdl, con);
        }

        //返信処理時
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)) {
            //送信してきたユーザを返信の宛先に設定
            SmailDetailModel mdl = (SmailDetailModel) ret.get(0);
            sidArray.add(String.valueOf(mdl.getUsrSid()));
        //全返信処理時
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
            //受信一覧、メイン(TOP)画面からの遷移の場合
            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
                ret = biz.getAtesakiZyusin2(paramMdl, reqMdl, con);
            //送信一覧からの遷移の場合
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                ret = biz.getAtesakiSosin2(paramMdl, reqMdl, con);
            }
            paramMdl.setFormData(form);

            //送信してきたユーザ + その宛先全員を全返信の宛先に設定
            SmailDetailModel mdl = (SmailDetailModel) ret.get(0);
            int sousinsya = mdl.getUsrSid();
            sidArray.add(String.valueOf(sousinsya));
            ArrayList<AtesakiModel> atesakiList =
                mdl.getAtesakiList();

           /******************************************************
            *
            * 差出人と宛先に同人物がいる場合は1人だけセットする。
            *
            * EX)
            * 差出人：ユーザA
            * 宛先：ユーザA、ユーザB、ユーザC
            *
            * のような場合単純に差出人 + 宛先 = 全返信宛先とすると
            *
            * [1] = ユーザA
            * [2] = ユーザA
            * [3] = ユーザB
            * [4] = ユーザC
            *
            * となってしまうので、これを
            *
            * [1] = ユーザA
            * [2] = ユーザB
            * [3] = ユーザC
            *
            * とする処理。
            *
            ******************************************************/
            for (AtesakiModel atsk : atesakiList) {
                if (sousinsya != atsk.getUsrSid()) {
                    sidArray.add(String.valueOf(atsk.getUsrSid()));
                }
            }

        //転送処理時
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_TENSO)
                || mode.equals(GSConstSmail.MSG_CREATE_MODE_COPY)) {
            //宛先無しに設定
            cmn120userSid = new String[0];
        }

        if (!sidArray.isEmpty()) {
            cmn120userSid =
                (String[]) sidArray.toArray(new String[sidArray.size()]);
        }

        sml020Form.setCmn120userSid(cmn120userSid);

        req.setAttribute("sml020Form", sml020Form);
        return map.findForward("createMsg");
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
    private ActionForward __doDownLoad(ActionMapping map,
                                        Sml030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);

        int sacSid = form.getSmlViewAccount();
        int smlSid = form.getSml010SelectedSid();
        Long binSid = (Long) NullDefault.getLong(form.getSml030binSid(), -1);

        //指定したバイナリデータが取得可能かチェックする
        if (smlBiz.isCheckSmailImage(
                sacSid, getSessionUserModel(req).getUsrsid(), smlSid, binSid)) {

            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(
                    con, binSid, GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                String download = gsMsg.getMessage("cmn.download");

                //ログ出力処理
                SmlAccountDao sacDao = new SmlAccountDao(con);
                SmlAccountModel sacMdl = sacDao.select(sacSid);
                String accName = new String();
                if (sacMdl != null) {
                    accName = sacMdl.getSacName();
                }

                StringBuilder value = new StringBuilder();
                value.append(gsMsg.getMessage("cmn.pdf.account"));
                value.append(accName);
                value.append("\n");
                value.append(gsMsg.getMessage("cmn.download"));
                value.append(" : ");
                value.append(cbMdl.getBinFileName());
                CommonBiz cmn = new CommonBiz();
                value.append(cmn.getByteSizeString(cbMdl.getBinFileSize()));

                smlBiz.outPutLog(map, reqMdl,
                        download, GSConstLog.LEVEL_INFO,
                        value.toString(),
                        smlSid, String.valueOf(binSid), GSConstSmail.SML_LOG_FLG_DOWNLOAD);

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 添付ファイル一括ダウンロードの処理
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
    private ActionForward __doDownLoadAll(ActionMapping map,
                                        Sml030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリIDを取得
        Sml030Biz biz = new Sml030Biz(reqMdl);
        String tempDirId = biz.createDirId(GSConstSmail.PLUGIN_ID_SMAIL);

        //テンポラリディレクトリパスを取得
        String tempDir = biz.getTempDir(tempDirId);

        log__.debug("テンポラリディレクトリ = " + tempDir);

        try {
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);

            String outFilePath = null;

            try {
                outFilePath = biz.makeAllTmpFile(
                        paramMdl, con, getAppRootPath(), tempDir, reqMdl);
            } catch (IOToolsException | IOException e) {
                log__.error("ZIP作成に失敗", e);
                return __setZipErrorDsp(map, req, form);

            }

            paramMdl.setFormData(form);
            if (outFilePath == null) {
                return getSubmitErrorPage(map, req);
            }

            String outBookName = outFilePath.substring(outFilePath.lastIndexOf(File.separator) + 1);
            GsMessage gsMsg = new GsMessage(reqMdl);
            //ログ出力処理
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(
                    map, getRequestModel(req), gsMsg.getMessage("cir.allTmep.download.log"),
                    GSConstLog.LEVEL_INFO, outBookName);

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

        } catch (Exception e) {
            log__.error("添付ファイル一括ダウンロードに失敗" + e);
            throw e;
        } finally {

            //TEMPディレクトリ削除
            biz.deleteTempDir(tempDirId);
        }

        return null;
    }

    private ActionForward __setZipErrorDsp(ActionMapping map,
            HttpServletRequest req, Sml030Form form) {
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("redraw");

        cmn999Form.setUrlOK(urlForward.getPath());


        //メッセージセット
        String msgState = "error.fail";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, "cmn.zip.create")));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");

    }

    /**
     * <br>[機  能] 添付画像ファイル表示の処理
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
    private ActionForward __doTempView(ActionMapping map,
                                        Sml030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException, Exception {

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
        int sacSid = form.getSmlViewAccount();
        int smlSid = form.getSml010SelectedSid();
        Long binSid = (Long) NullDefault.getLong(form.getSml030binSid(), -1);
        //指定したバイナリデータが取得可能かチェックする
        if (smlBiz.isCheckSmailImage(
                sacSid, getSessionUserModel(req).getUsrsid(), smlSid, binSid)) {

            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cbMdl
            = cmnBiz.getBinInfo(con, NullDefault.getLong(form.getSml030binSid(), -1),
                    GroupSession.getResourceManager().getDomain(req));

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }

        return null;
    }

    /**
     * <br>[機  能] 検索画面から遷移時のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999From
     * @param form Sml030Form
     */
    private void __setSearchHiddenParm(Cmn999Form cmn999Form, Sml030Form form) {
        //検索画面から遷移の時
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
            cmn999Form.addHiddenParam("sml090DelSid", form.getSml090DelSid());
            cmn999Form.addHiddenParam("sml090SelectedDelSid", form.getSml090SelectedDelSid());
//            cmn999Form.addHiddenParam("cmn120userSid", form.getCmn120userSid());
            //宛先start
            cmn999Form.addHiddenParam("sml090userSid", form.getSml090userSid());
            cmn999Form.addHiddenParam("cmn120userSid", form.getSml090userSid());
            //宛先end
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
        }

    }


    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param cnt ハッシュコントロール
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __getDetail(ActionMapping map,
                                    Sml030Form form,
                                    HashControlModel cnt,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;
        String procMode = "";
        RequestModel reqMdl = getRequestModel(req);

        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);
        try {

            Sml030Biz biz = new Sml030Biz(reqMdl);
            //使用可能プラグイン情報を取得する。
            PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);

            procMode = form.getSml010ProcMode();
            //受信モード
            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
                //データセット
                biz.setInitDataJusin(paramMdl, reqMdl, con);

                //未読メッセージの場合は既読にする
                biz.updateMidokuMsg(paramMdl, reqMdl, con, pconf);

                commitFlg = true;
            //送信モード
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                //データセット
                biz.setInitDataSosin(paramMdl, reqMdl, con);
            //ゴミ箱モード or ラベルモード
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                      || procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
                //データセット
                biz.setInitDataGomi(paramMdl, reqMdl, con);

                if (paramMdl.getSml010SelectedMailKbn().equals(
                                         GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                    //未読メッセージの場合は既読にする
                    biz.updateMidokuMsg(paramMdl, reqMdl, con, pconf);
                    commitFlg = true;
                }
            }
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
        } finally {
            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)
                   && paramMdl.getSml010SelectedMailKbn().equals(
                        GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }

            paramMdl.setFormData(form);


//            for (SmailDetailModel smlMdl : paramMdl.getSml030SmlList()) {
//                smlMdl.setSmsSdate(null);
//            }
//
//            for (CmnBinfModel binfMdl : paramMdl.getSml030FileList()) {
//                binfMdl.setBinAddate(null);
//                binfMdl.setBinUpdate(null);
//            }

            JSONObject jsonData = new JSONObject();
            jsonData = JSONObject.fromObject(paramMdl);
            jsonData.element("success", true);
            PrintWriter out = null;

            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData.toString());
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(メールデータ取得)");
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        }
    }

    /**
     * <br>[機  能] 削除確認画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doDeleteConfirmationData(ActionMapping map,
                                                  Sml030Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        //削除するメッセージの件名を取得する
        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);
        Sml030Biz biz = new Sml030Biz(getRequestModel(req));
        String mailName = biz.getDelMsgTitle(paramMdl, con);
        paramMdl.setFormData(form);


        //メッセージ
        MessageResources msgRes = getResources(req);

        String mailKbnName = "";
        String mailKbn = form.getSml010SelectedMailKbn();

        GsMessage gsMsg = new GsMessage();
        String msg = "";

        //受信メール
        if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            msg = gsMsg.getMessage(req, "sml.100");
            mailKbnName = msg + " ";
        //送信メール
        } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            msg = gsMsg.getMessage(req, "sml.102");
            mailKbnName = msg + " ";
        //草稿
        } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            msg = gsMsg.getMessage(req, "sml.101");
            mailKbnName = msg + " ";
        }

        String msgId = "";
        if (form.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            msgId = "sakujo.kakunin.list";
        } else {
            msgId = "move.gomibako.mail";
        }

        String msg2 = gsMsg.getMessage(req, "cmn.message");
        String msg3 = gsMsg.getMessage(req, "wml.231");

        String message =
                msgRes.getMessage(
                        msgId,
                        msg2,
                        msg3
                        + mailKbnName
                        + StringUtilHtml.transToHTmlPlusAmparsant(mailName));

        List<String> messageList = new ArrayList<String>();
        messageList.add(message);

        form.setMessageList(messageList);

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(メールデータ取得)");
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doDeleteOkData(ActionMapping map,
                                        Sml030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);

        try {

            //削除処理実行
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            Sml030Biz biz = new Sml030Biz(reqMdl);
            biz.deleteMessage(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl,
                    delete, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                                 + "\n");

            commitFlg = true;


            //メッセージ
            MessageResources msgRes = getResources(req);
            String msgId = "";

            String procMode = form.getSml010ProcMode();
            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                msgId = "sakujo.kanryo.object";
            } else {
                msgId = "move.gomibako.object";
            }

            String msg = gsMsg.getMessage(req, "cmn.message");

            String message = msgRes.getMessage(msgId, msg);

            List<String> messageList = new ArrayList<String>();
            messageList.add(message);
            form.setMessageList(messageList);

            JSONObject jsonData = new JSONObject();
            jsonData = JSONObject.fromObject(form);
            jsonData.element("success", true);
            PrintWriter out = null;

            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData.toString());
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(メールデータ取得)");
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 完全に削除確認画面表示処理
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
    private void __doDeleteAllConfirmationData(ActionMapping map,
                                                  Sml030Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        con.setAutoCommit(true);
        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);

        String mailName = null;
        try {
            Sml030Biz biz = new Sml030Biz(getRequestModel(req));
//            //完全に削除可能かチェック
//            if (!biz.isDeleteAll(paramMdl, con)) {
//                ActionErrors errors = new ActionErrors();
//                ActionMessage msg =
//                    new ActionMessage(
//                        "error.alldelete.mail.delete");
//                StrutsUtil.addMessage(errors, msg, "mailDeleteAll");
//                if (!errors.isEmpty()) {
//                    form.setErrorsList(__getJsonErrorMsg(req, errors));
//                }
//            }
            //削除するメッセージの件名を取得する
             mailName = biz.getDelMsgTitle(paramMdl, con);
        } finally {
            paramMdl.setFormData(form);
        }

        //メッセージ
        List<String> messageList = new ArrayList<String>();
        MessageResources msgRes = getResources(req);

        GsMessage gsMsg = new GsMessage();
        String send = gsMsg.getMessage(req, "sml.102");
        String msg = gsMsg.getMessage(req, "cmn.message");
        String ten = gsMsg.getMessage(req, "wml.231");

        String mailKbnName = send + " ";

        String msgId = "move.alldelete.mail";
        messageList.add(
                msgRes.getMessage(
                        msgId,
                        msg,
                        ten
                        + mailKbnName
                        + StringUtilHtml.transToHTmlPlusAmparsant(mailName)));
        form.setMessageList(messageList);

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(メールデータ取得)");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] 完全に削除確認画面でOKボタン押下
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
    private void __doDeleteAllOkData(ActionMapping map,
                                        Sml030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);
        Sml030Biz biz = new Sml030Biz(reqMdl);

//        //完全に削除可能かチェック
//        if (!biz.isDeleteAll(paramMdl, con)) {
//            paramMdl.setFormData(form);
//            ActionErrors errors = new ActionErrors();
//            ActionMessage msg =
//                new ActionMessage(
//                    "error.alldelete.mail.delete");
//            StrutsUtil.addMessage(errors, msg, "mailDeleteAll");
//
//        } else {

            boolean commitFlg = false;
            con.setAutoCommit(false);

            try {

                //削除処理実行
                biz.allDeleteMessage(paramMdl, reqMdl, con);
                paramMdl.setFormData(form);

                GsMessage gsMsg = new GsMessage();
                String delete = gsMsg.getMessage(req, "cmn.delete");
                String allDelete = gsMsg.getMessage(req, "cmn.delete.all");

                //ログ出力処理
                SmlAccountModel sacMdl = new SmlAccountModel();
                SmlAccountDao sacDao = new SmlAccountDao(con);
                sacMdl = sacDao.select(form.getSmlViewAccount());

                SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
                smlBiz.outPutLog(map, reqMdl,
                        delete, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                                     + "\n" + allDelete);

                commitFlg = true;

                //メッセージ
                List<String> messageList = new ArrayList<String>();
                MessageResources msgRes = getResources(req);
                String msgId = "sakujo.kanryo.object";

                String msg = gsMsg.getMessage(req, "cmn.message");

                messageList.add(
                        msgRes.getMessage(msgId, msg));

                form.setMessageList(messageList);

                JSONObject jsonData = new JSONObject();
                jsonData = JSONObject.fromObject(form);
                jsonData.element("success", true);
                PrintWriter out = null;

                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    out = res.getWriter();
                    out.print(jsonData.toString());
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(メールデータ取得)");
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }


            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }
//        }

    }

    /**
     * <br>[機  能] 復旧処理確認
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doRevivedConfirmationData(ActionMapping map,
                                                   Sml030Form form,
                                                   HttpServletRequest req,
                                                   HttpServletResponse res,
                                                   Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        try {

            RequestModel reqMdl = getRequestModel(req);
            Sml030Biz biz = new Sml030Biz(reqMdl);

            //復旧対象のメールタイトル一覧を取得する
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            String mailName = biz.getMailName(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            //メッセージ
            List<String> messageList = new ArrayList<String>();
            MessageResources msgRes = getResources(req);

            String mailKbnName = "";
            String mailKbn = form.getSml010SelectedMailKbn();

            GsMessage gsMsg = new GsMessage();
            String msg = "";

            //受信メール
            if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                msg = gsMsg.getMessage(req, "sml.100");
                mailKbnName = msg + " ";
            //送信メール
            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                msg = gsMsg.getMessage(req, "sml.102");
                mailKbnName = msg + " ";
            //草稿
            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                msg = gsMsg.getMessage(req, "sml.101");
                mailKbnName = msg + " ";
            }

            String msg2 = gsMsg.getMessage(req, "cmn.message");
            String ten = gsMsg.getMessage(req, "wml.231");
            messageList.add(
                    msgRes.getMessage(
                            "move.former.mail", msg2,
                            ten
                            + mailKbnName
                            + StringUtilHtml.transToHTmlPlusAmparsant(mailName)));
            form.setMessageList(messageList);

            JSONObject jsonData = new JSONObject();
            jsonData = JSONObject.fromObject(form);
            jsonData.element("success", true);
            PrintWriter out = null;

            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData.toString());
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(メールデータ取得)");
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
    }


    /**
     * <br>[機  能] 復旧確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doRevivedOkData(ActionMapping map,
                                         Sml030Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);

        try {

            //復旧処理実行
            Sml030Biz biz = new Sml030Biz(reqMdl);
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            biz.revivedMessage(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String edit = gsMsg.getMessage(req, "cmn.edit");
            String msgReturn = gsMsg.getMessage(req, "cmn.undo");

            //ログ出力処理
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl,
                    edit, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                                 + "\n" + msgReturn);

            commitFlg = true;

            //メッセージ
            List<String> messageList = new ArrayList<String>();
            String msg = gsMsg.getMessage(req, "cmn.message");
            MessageResources msgRes = getResources(req);
            messageList.add(
                    msgRes.getMessage("move.former.object", msg));

            form.setMessageList(messageList);

            JSONObject jsonData = new JSONObject();
            jsonData = JSONObject.fromObject(form);
            jsonData.element("success", true);
            PrintWriter out = null;

            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData.toString());
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(メールデータ取得)");
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param mode 前へ、次へボタン区分
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doChangeMsgData(ActionMapping map,
                                        Sml030Form form,
                                        int mode,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);

        try {
            //全データ中の位置を把握するためのハッシュ作成
            Sml030Biz biz = new Sml030Biz(reqMdl);
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setParam(form);
            HashControlModel ret = biz.getAllDataHash(paramMdl, reqMdl, con);
            HashMap<Integer, Sml030Model> hash = ret.getMap();
            paramMdl.setFormData(form);

            if (!hash.isEmpty()) {

                //int rowNum = form.getSml030SelectedRowNum();
                form.setSml030SelectedRowNum(ret.getRowNum());
                int rowNum = form.getSml030SelectedRowNum();

                //前へボタン
                if (mode == GSConstSmail.MSG_PREV) {
                    rowNum -= 1;
                //次へボタン
                } else if (mode == GSConstSmail.MSG_NEXT) {
                    rowNum += 1;
                }

                if (rowNum == 0 || rowNum == hash.size() + 1) {

                    form.setSml030PrevNextFlg(1);
                    __getDetail(map, form, ret, req, res, con);
                } else {

                    form.setSml030SelectedRowNum(rowNum);

                    //前、または次のデータのメールSID取得
                    Object obj = hash.get(rowNum);
                    if (obj != null) {
                        Sml030Model mdl = (Sml030Model) obj;
                        form.setSml010SelectedSid(mdl.getMailSid());
                        form.setSml010PageNum(mdl.getPageNum());
                        form.setSml010SelectedMailKbn(mdl.getMailKbn());
                    } else {
                        form.setSml030PrevNextFlg(1);
                    }
                    //取得したメールSIDで再表示
                    __getDetail(map, form, ret, req, res, con);
                }


            } else {
                form.setSml030PrevNextFlg(1);
                //取得したメールSIDで再表示
                __getDetail(map, form, ret, req, res, con);
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
    }

    /**
     * <br>[機  能] メール詳細情報用のパラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param cmd コマンド
     */
    private void __setDetailParam(Sml030Form form, HttpServletRequest req, String cmd) {

        String procMode = NullDefault.getString(req.getParameter("PROCMODE"), "");
        procMode = procMode.trim();

        String sid = NullDefault.getString(req.getParameter("SELECTSID"), "0");
        sid = sid.trim();

        String kbn = NullDefault.getString(req.getParameter("SELECTKBN"), "");
        kbn = kbn.trim();

        String accountSid = NullDefault.getString(req.getParameter("ACCOUNT"), "0");
        accountSid = accountSid.trim();

        form.setSml010ProcMode(procMode);
        form.setSml010SelectedSid(Integer.parseInt(sid));
        form.setSml010SelectedMailKbn(kbn);
        form.setSmlViewAccount(Integer.parseInt(accountSid));

        //詳細検索フラグ
        String sFlg = NullDefault.getString(req.getParameter("SERCHFLG"), "");
        sFlg = sFlg.trim();

        //(前へボタン押下 || 次へボタン押下) && 検索フラグON
        if ((cmd.equals("prevData") || cmd.equals("nextData")) && sFlg.equals("true")) {
            form.setSml090BackParm(GSConstSmail.SEARCH_BACK_ON);
        }
    }

    /**
     * <br>[機  能]共有ボタン押下
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception Exception
     */

    private void __doShare(ActionMapping map,
            Sml030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        JSONObject jsonData = new JSONObject();
        con.setAutoCommit(true);
        Sml030ParamModel paramMdl = new Sml030ParamModel();
        paramMdl.setParam(form);
        //トランザクショントークン設定
        this.saveToken(req);
        paramMdl.setFormData(form);
        PrintWriter out = null;
        try {
            Sml010Biz biz010 = new Sml010Biz();
            RequestModel reqMdl = getRequestModel(req);
            //WEBメール共有チェック
            PluginConfig pconfig =
                    getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
            biz010.getSharePluginCheck(paramMdl, reqMdl, pconfig, con, GSConst.PLUGINID_WML);
            Sml030Biz biz030 = new Sml030Biz(reqMdl);

            //ショートメール閲覧権限チェック
            boolean check = biz030.getShareCheck(paramMdl, reqMdl, con);

            if (paramMdl.getSml010webmailShareFlg() == 0 && check) {
                jsonData.element("success", true);

                //テンポラリディレクトリIDを取得
                Sml030Biz biz = new Sml030Biz(reqMdl);
                String tempDirId = biz.createDirId(GSConst.PLUGINID_WML);

                //テンポラリディレクトリパスを取得
                String tempDir = biz.getPluginTempDir(GSConst.PLUGINID_WML, tempDirId);
                tempDir = tempDir + "sendmail/";

                //テンポラリディレクトリの削除
                GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
                tempPathUtil.deleteTempPath(reqMdl, GSConst.PLUGINID_WML, tempDirId, "sendmail");

                String appRootPath = getAppRootPath();
                ArrayList<String> shareList = biz030.getShareInfo(paramMdl, con,
                        tempDir, appRootPath, GroupSession.getResourceManager().getDomain(req));

                jsonData.element("title", shareList.get(0));
                jsonData.element("detail", shareList.get(1));
                jsonData.element("htmlFlg", shareList.get(2));
                if (shareList.size() == 4) {
                    jsonData.element("pass", tempDirId);
                }
            } else {
                jsonData.element("success", false);
            }

            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ取得失敗(WEBメール共有)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
    /**
     * <br>[機  能] 本文内添付ファイルを取得します
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
    private ActionForward __doGetBodyFile(
            ActionMapping map,
            Sml030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
        Long binSid = sbbDao.select(form.getSml030SmlSid(), 
                form.getSml030BodyFileSid(), reqMdl.getSmodel().getUsrsid());

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        if (binSid == null || binSid < 0L) {
            return null;
        }
        cbMdl = cmnBiz.getBinInfo(con, binSid,
        GroupSession.getResourceManager().getDomain(req));

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);
            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }
}