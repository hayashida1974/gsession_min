package jp.groupsession.v2.rng.rng020kn;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RngNotAcceptTemplateException;
import jp.groupsession.v2.rng.RngNotfoundException;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngFormBuildBiz;
import jp.groupsession.v2.rng.model.RingiRequestModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng020.Rng020Action;
import jp.groupsession.v2.rng.rng020.Rng020Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議作成確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020knAction extends AbstractRingiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng020knAction.class);

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
    @Override
    protected String _getTempDirId() {
        return Rng020Action.DIRID;
    }
    /**
     *
     * <br>[機  能] 戻り先画面への遷移を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param thisForm Rng020Form
     * @param map ActionMapping
     * @return 戻り先
     */
    public ActionForward __getForwardBack(Rng020knForm thisForm, ActionMapping map) {
        ActionForward forward;
        if (thisForm.getRngCmdMode() == RngConst.RNG_CMDMODE_ADD) {
            if (thisForm.isRng020copyApply()) {
                forward = map.findForward("rng010");
            } else {
                if (thisForm.getRng020prevForward() == null) {
                    forward = map.findForward("rng010");
                    return forward;
                }
                if (thisForm.getRng020prevForward().equals("rng060")) {
                    forward = map.findForward("rng060");
                    return forward;
                }
                forward = map.findForward("rng010");
                return forward;
            }
        } else {
            if (thisForm.getRng020prevForward() == null) {
                forward = map.findForward("rng010");
                return forward;
            }
            if (thisForm.getRng020prevForward().equals("search")) {
                forward = map.findForward("search");
                return forward;
            }
            forward = map.findForward("rng010");
        }
        return forward;
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
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Rng020knForm thisForm = (Rng020knForm) form;

        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        try {
            if (cmd.equals("rng020knback")) {
                log__.debug("*** 戻るボタンクリック");
                forward = map.findForward("rng020");

            } else if (cmd.equals("rng020knSinsei")) {
                log__.debug("申請ボタンクリック");
                forward = __doEntry(map, thisForm, req, res, con, 0);

            } else if (cmd.equals("fileDownload")) {
                log__.debug("添付ファイルダウンロード");
                forward = __doDownLoad(map, thisForm, req, res, con);

            } else {
                log__.debug("*** 初期表示を行います。");
                forward = __doInit(map, thisForm, req, res, con);
            }
        } catch (RtpNotfoundException rtpe) {
            log__.debug("*** テンプレートが削除されました。");
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(thisForm, thisForm.getClass(), "");
            ActionForward backForward;
            if (thisForm.getRng130searchFlg() == 1) {
                backForward = map.findForward("search");
            } else {
                backForward = map.findForward("rng010");
            }

            return getCatchExceptionPage(map, req,
                    rtpe, backForward, cmn999form);
        }

        return forward;
    }

    /**
     *
     * <br>[機  能] アクション実行前の事前処理 アクセス制限を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward _immigration(ActionMapping map, Rng020knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        ActionForward back;
        back = __getForwardBack(form, map);
        int viewUserSid = reqMdl.getSmodel().getUsrsid();

        Rng020Biz rng020Biz = new Rng020Biz(con, reqMdl);
        Cmn999Form cmn999form = new Cmn999Form();
        cmn999form.addHiddenAll(form, form.getClass(), "");

        if (form.getRngSid() >= 0) {

            //稟議SIDの指定あり時　複写して登録でも草稿編集でもないは不正
            if (!form.isRng020copyApply()
                    && form.getRngCmdMode() != RngConst.RNG_CMDMODE_EDIT) {
                return getCatchExceptionPage(map, req,
                        new RngNotfoundException(),
                        back, cmn999form);
            }

            //草稿、複写時に参照元稟議SIDの権限チェック
            //内容確認画面で表示できない稟議を使用できないように
            if (!rng020Biz.chkViewRingi(
                    viewUserSid,
                    form.getRngSid(),
                    form.getRngCmdMode()
                    )) {
                return getCatchExceptionPage(map, req,
                        new RngNotfoundException(),
                        back, cmn999form);
            }
        } else {
            //稟議SIDの指定なし時　複写して登録時、草稿編集は不正
            if (form.isRng020copyApply()
                    || form.getRngCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
                return getCatchExceptionPage(map, req,
                        new RngNotfoundException(),
                        back, cmn999form);
            }
        }
        //汎用稟議テンプレート時、不正なSID、VER指定の場合
        if (form.getRng020rtpSid() < 0
                || (form.getRng020rtpSid() == 0 && form.getRng020rtpVer() != 0)
                ) {
            return getCatchExceptionPage(map, req,
                    new RtpNotfoundException(), back,
                    cmn999form);

        }
        try {
            //テンプレートバージョン変更時 なにもせず登録画面へ戻す
            //データコンバートを確認画面側で行うと、
            //リクエストパラメータ・・・コンバート前
            //テンポラリディレクトリのファイルパス・・・コンバート後となり不整合が発生する
            RngFormBuildBiz formBiz = new RngFormBuildBiz(reqMdl);

            RngTemplateModel prevMdl = formBiz.getRtpModel(con, form.getRng020rtpSid(),
                    form.getRng020rtpVer());
            RngTemplateModel rtpMdl = formBiz.getRtpModelMaxVer(con, form.getRng020rtpSid());
            int rtpVerupFlg = rng020Biz.chkRtpUpdated(rtpMdl, prevMdl,
                    form.getRng020rtpKeiroVersion());
            if (rtpVerupFlg != RngConst.CODE_TPVERCHK_EQ) {
                return map.findForward("rng020");
            }
        } catch (RtpNotfoundException e) {
            return getCatchExceptionPage(map, req,
                    new RtpNotfoundException(), back,
                    cmn999form);
        }
        return null;
    }
    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイル操作時例外
     * @throws EnumOutRangeException 列挙型不正例外
     * @throws RtpNotfoundException テンプレート不正例外
     */
    public ActionForward __doInit(ActionMapping map, Rng020knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOException,
                    IOToolsException, TempFileException,
                    EnumOutRangeException, RtpNotfoundException {
        try {
            con.setAutoCommit(true);
            Rng020knParamModel paramMdl = new Rng020knParamModel();
            paramMdl.setParam(form);
            Rng020knBiz biz = new Rng020knBiz(con, getRequestModel(req));
            biz.setInitData(getRequestModel(req), paramMdl, getAppRootPath(),
                    _getRingiDir(req), getSessionUserModel(req));
            paramMdl.setFormData(form);
            con.setAutoCommit(false);
        } catch (SQLException se) {
            throw se;
        } catch (IOException ioe) {
            throw ioe;
        } catch (IOToolsException iote) {
            throw iote;
        } catch (TempFileException tfe) {
            throw tfe;
        } catch (RtpNotfoundException rtpe) {
            throw rtpe;
        }
        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 稟議情報登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 登録モード 0:申請 1:草稿に保存
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doEntry(
            ActionMapping map,
            Rng020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int mode) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);

        ActionForward  forward = null;
        //入力チェック前に描画設定でテンプレートの読み込み、自動計算要素の再計算を行う必要がある
        forward =  __doInit(map, form, req, res, con);

        RngBiz rngBiz = new RngBiz(con);
        RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
        Rng020Biz rng020Biz = new Rng020Biz(con, reqMdl);
        // テンプレート使用制限チェック
        boolean templateChk = rng020Biz.isAcceptTemplate(aconfMdl,
                form.getRng020rtpSid(), form.getRng020rtpType());
        if (!templateChk) {
            ActionForward okForward = __getForwardBack(form, map);
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");
            return getCatchExceptionPage(map, req,
                    new RngNotAcceptTemplateException(),
                    okForward,
                    cmn999form);
        }
        if (form.getRng020rtpSid() > 0
                && !rng020Biz.isUseableTemplate(form.getRng020rtpSid())) {
            ActionForward okForward = __getForwardBack(form, map);
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");
            return getCatchExceptionPage(map, req,
                    new RtpNotfoundException(), okForward,
                    cmn999form);
        }

        //入力チェックを行う
        ActionErrors errors = null;
        errors = form.validateCheck(Rng020knForm.CHECKTYPE_REQUEST,
                getRequestModel(req), con);
        if ((errors != null && !errors.isEmpty()) || form.getUseTemplateFlg() == 0) {
            addErrors(req, errors);
            return map.findForward("rng020");
        }

        Rng020knBiz rng020knBiz = new Rng020knBiz(con, reqMdl);
        if (form.isRng020useCopyKeiro()) {
            // 最終経路をデータ保持用の配列にコピー
            form.setRng020kakuninSvKeiroMap(
                    rng020knBiz.getSvCopyKakuninKeiro(form.getRng020kakuninKeiroMap())
                    );
        }

        //最終確認経路を単一経路に変換
        form.setRng020kakuninKeiroMap(
                rng020knBiz.changeOneKakuninKeiro(form.getRng020kakuninKeiroMap())
                );

        boolean commit = false;
        PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);
        try {
            Rng020knParamModel paramMdl = new Rng020knParamModel();
            paramMdl.setParam(form);
            Rng020knBiz biz = new Rng020knBiz(con, reqMdl);
            RingiRequestModel model = rng020Biz.entryRingiData(paramMdl,
                    getCountMtController(req),
                    getAppRootPath(), _getRingiDir(req).getTempPath(),
                    mode,
                    getPluginConfig(req), smailPluginUseFlg,
                    reqMdl);
            // スキップされたユーザへショートメール通知を送る
            String appRootPath = getAppRootPath();
            biz.executeSendSmail(getCountMtController(req), paramMdl,
                    appRootPath, getPluginConfig(req), smailPluginUseFlg);
            paramMdl.setFormData(form);
            forward = __setCompPageParam(map, req, form);

            GsMessage gsMsg = new GsMessage(reqMdl);

            //ログ出力処理
            String opCode = gsMsg.getMessage("rng.63"); //gsMsg.getMessage("cmn.entry");
            String msg = rng020Biz.outputRingiData(model, paramMdl, reqMdl, con);

            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_TRACE,
                    msg,
                    reqMdl);

            con.commit();
            commit = true;

            //テンポラリディレクトリの削除
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));
        } catch (Exception e) {
            log__.error("稟議情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

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
            Rng020knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getRng020BinSid();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        String dirId  = form.getRng020BinDirId();

        RngBiz rngBiz = new RngBiz(con);
        GSTemporaryPathModel tempDir = new GSTemporaryPathModel(_getRingiDir(req), dirId);


        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir.getTempPath(), fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir.getTempPath() + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        rngBiz.outPutLog(
                map,
                msg, GSConstLog.LEVEL_INFO, fMdl.getFileName(),
                reqMdl, fileId);

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Rng020knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        if (form.getRng130searchFlg() == 1) {
            urlForward = map.findForward("search");
        } else {
            urlForward = map.findForward("rng010");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "sinsei.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "rng.62");

        cmn999Form.setMessage(msgRes.getMessage(msgState, msg));

        cmn999Form.addHiddenParam("rng130Type", form.getRng130Type());
        cmn999Form.addHiddenParam("rng130Status", form.getRng130Status());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("sltUserSid", form.getSltUserSid());
        cmn999Form.addHiddenParam("rng130keyKbn", form.getRng130keyKbn());
        cmn999Form.addHiddenParam("rng130searchSubject1", form.getRng130searchSubject1());
        cmn999Form.addHiddenParam("rng130searchSubject2", form.getRng130searchSubject2());
        cmn999Form.addHiddenParam("rng130searchSubject3", form.getRng130searchSubject3());
        cmn999Form.addHiddenParam("sltSortKey1", form.getSltSortKey1());
        cmn999Form.addHiddenParam("rng130orderKey1", form.getRng130orderKey1());
        cmn999Form.addHiddenParam("sltSortKey2", form.getSltSortKey2());
        cmn999Form.addHiddenParam("rng130orderKey2", form.getRng130orderKey1());
        cmn999Form.addHiddenParam("sltApplYearFr", form.getSltApplYearFr());
        cmn999Form.addHiddenParam("sltApplMonthFr", form.getSltApplMonthFr());
        cmn999Form.addHiddenParam("sltApplDayFr", form.getSltApplDayFr());
        cmn999Form.addHiddenParam("sltApplYearTo", form.getSltApplYearTo());
        cmn999Form.addHiddenParam("sltApplMonthTo", form.getSltApplMonthTo());
        cmn999Form.addHiddenParam("sltApplDayTo", form.getSltApplDayTo());
        cmn999Form.addHiddenParam("sltLastManageYearFr", form.getSltLastManageYearFr());
        cmn999Form.addHiddenParam("sltLastManageMonthFr", form.getSltLastManageMonthFr());
        cmn999Form.addHiddenParam("sltLastManageDayFr", form.getSltLastManageDayFr());
        cmn999Form.addHiddenParam("sltLastManageYearTo", form.getSltLastManageYearTo());
        cmn999Form.addHiddenParam("sltLastManageMonthTo", form.getSltLastManageMonthTo());
        cmn999Form.addHiddenParam("sltLastManageDayTo", form.getSltLastManageDayTo());
        cmn999Form.addHiddenParam("rng130pageTop", form.getRng130pageTop());
        cmn999Form.addHiddenParam("rng130pageBottom", form.getRng130pageBottom());

        cmn999Form.addHiddenParam("svRngViewAccount", form.getSvRngViewAccount());
        cmn999Form.addHiddenParam("svRngKeyword", form.getSvRngKeyword());
        cmn999Form.addHiddenParam("svRng130Type", form.getSvRng130Type());
        cmn999Form.addHiddenParam("svRng130Category", form.getSvRng130Category());
        cmn999Form.addHiddenParam("svGroupSid", form.getSvGroupSid());
        cmn999Form.addHiddenParam("svUserSid", form.getSvUserSid());
        cmn999Form.addHiddenParam("svRng130keyKbn", form.getSvRng130keyKbn());
        cmn999Form.addHiddenParam("svRng130Status", form.getSvRng130Status());
        cmn999Form.addHiddenParam("svRng130searchSubject1", form.getSvRng130searchSubject1());
        cmn999Form.addHiddenParam("svRng130searchSubject2", form.getSvRng130searchSubject2());
        cmn999Form.addHiddenParam("svRng130searchSubject3", form.getSvRng130searchSubject3());
        cmn999Form.addHiddenParam("svSortKey1", form.getSvSortKey1());
        cmn999Form.addHiddenParam("svRng130orderKey1", form.getSvRng130orderKey1());
        cmn999Form.addHiddenParam("svSortKey2", form.getSvSortKey2());
        cmn999Form.addHiddenParam("svRng130orderKey2", form.getSvRng130orderKey1());
        cmn999Form.addHiddenParam("svApplYearFr", form.getSvApplYearFr());
        cmn999Form.addHiddenParam("svApplMonthFr", form.getSvApplMonthFr());
        cmn999Form.addHiddenParam("svApplDayFr", form.getSvApplDayFr());
        cmn999Form.addHiddenParam("svApplYearTo", form.getSvApplYearTo());
        cmn999Form.addHiddenParam("svApplMonthTo", form.getSvApplMonthTo());
        cmn999Form.addHiddenParam("svApplDayTo", form.getSvApplDayTo());
        cmn999Form.addHiddenParam("svLastManageYearFr", form.getSvLastManageYearFr());
        cmn999Form.addHiddenParam("svLastManageMonthFr", form.getSvLastManageMonthFr());
        cmn999Form.addHiddenParam("svLastManageDayFr", form.getSvLastManageDayFr());
        cmn999Form.addHiddenParam("svLastManageYearTo", form.getSvLastManageYearTo());
        cmn999Form.addHiddenParam("svLastManageMonthTo", form.getSvLastManageMonthTo());
        cmn999Form.addHiddenParam("svLastManageDayTo", form.getSvLastManageDayTo());
        cmn999Form.addHiddenParam("rng130searchFlg", form.getRng130searchFlg());
        
        form.setHiddenParam(cmn999Form, false);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}
