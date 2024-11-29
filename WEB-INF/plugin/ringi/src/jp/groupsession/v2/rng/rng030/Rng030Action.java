package jp.groupsession.v2.rng.rng030;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RngMoveConfimationKeiroException;
import jp.groupsession.v2.rng.RngNotAcceptTemplateException;
import jp.groupsession.v2.rng.RngNotDairiAccountAccessException;
import jp.groupsession.v2.rng.RngNotfoundException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.pdf.RngTanPdfModel;
import jp.groupsession.v2.rng.rng020.Rng020Keiro;
import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import net.sf.json.JSONArray;

/**
 * <br>[機  能] 稟議内容確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng030Action extends AbstractRingiAction {
    public static final String SUBDIRNAME_COMMENTEDIT_ROW = "row";

    public static final String SUBDIRNAME_COMMENTEDIT = "commentEdit";

    /** 添付ファイルディレクトリID*/
    public static final String TEMPDIRID = "rng030";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng030Action.class);

    /** 確認/完了画面の種類 承認 */
    private static final int MSGPAGETYPE_APPROVAL__ = 1;
    /** 確認/完了画面の種類 否認 */
    private static final int MSGPAGETYPE_DENIAL__ = 2;
    /** 確認/完了画面の種類 差し戻し */
    private static final int MSGPAGETYPE_REFLECTION__ = 3;
    /** 確認/完了画面の種類 確認 */
    private static final int MSGPAGETYPE_CONFIRMATION__ = 4;
    /** 確認/完了画面の種類 完了 */
    private static final int MSGPAGETYPE_COMPLETE__ = 5;
    /** 確認/完了画面の種類 強制完了 */
    private static final int MSGPAGETYPE_COMPELCOMPLETE__ = 6;
    /** 確認/完了画面の種類 強制削除 */
    private static final int MSGPAGETYPE_COMPELDELETE__ = 7;
    /** 確認/完了画面の種類 スキップ */
    private static final int MSGPAGETYPE_SKIP__ = 8;
    /** 確認/完了画面の種類 再申請 */
    private static final int MSGPAGETYPE_APPLICATE__ = 9;
    /** 確認/完了画面の種類 取り消し */
    private static final int MSGPAGETYPE_TORISAGE__ = 10;
    /** 確認/完了画面の種類 後閲*/
    private static final int MSGPAGETYPE_KOETU__ = 11;
    /** 確認/完了画面の種類 編集*/
    private static final int MSGPAGETYPE_EDIT__ = 12;
    /** 確認/完了画面の種類 経路の追加*/
    private static final int MSGPAGETYPE_ADDKEIRO__ = 13;

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    @Override
    protected String _getTempDirId() {
        return TEMPDIRID;
    }
    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        if (_isSystemAdmin(req, con)) {
            return true; //システム管理者の場合はプラグインチェックをしない
        }
        return super._isAccessPlugin(req, form, con);
    }

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {

        int apprMode = ((Rng030Form) form).getRngApprMode();

        if (apprMode == RngConst.RNG_APPRMODE_DISCUSSING
        || apprMode == RngConst.RNG_APPRMODE_COMPLETE) {
            return false;
        }
        return true;
    }

    /**
     * <p>キャッシュを有効にして良いか判定を行う
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        return cmd.equals("fileDownload");
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
    protected ActionForward _immigration(ActionMapping map, Rng030Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        Rng030Biz rng030Biz = new Rng030Biz(con, reqMdl);
        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz biz = new Rng030Biz(con, getRequestModel(req));
        Cmn999Form cmn999form = new Cmn999Form();
        cmn999form.addHiddenAll(form, form.getClass(), "");
        int viewUserSid = form.getRng010ViewAccount();
        if (paramMdl.getRngApprMode() == RngConst.RNG_APPRMODE_DISCUSSING) {
            //申請中案件管理画面から
            viewUserSid = 0;
        } else if (paramMdl.getRngApprMode() == RngConst.RNG_APPRMODE_COMPLETE) {
            //完了案件管理画面から
            viewUserSid = 0;
        } else if (viewUserSid == 0) {
            viewUserSid = reqMdl.getSmodel().getUsrsid();
        }

        if (!rng030Biz.chkViewAccount(viewUserSid)) {
            //権限のないアカウントへのアクセス
            return getCatchExceptionPage(map, req,
                    new RngNotDairiAccountAccessException(),
                    biz.getForward(map, paramMdl), cmn999form);
        }
        if (!rng030Biz.chkViewRingi(viewUserSid, form.getRngSid())) {
            //受信していない稟議へのアクセス
            return getCatchExceptionPage(map, req,
                    new RngNotfoundException(),
                    biz.getForward(map, paramMdl), cmn999form);
        }
        return null;
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
        Rng030Form thisForm = (Rng030Form) form;

        //共通アクセス制限
        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }
        if (cmd.equals("backList")) {
            log__.debug("戻るボタンクリック");

            Rng030ParamModel paramMdl = new Rng030ParamModel();
            paramMdl.setParam(thisForm);
            Rng030Biz biz = new Rng030Biz(con, getRequestModel(req));
            forward = biz.getForward(map, paramMdl);

            paramMdl.setFormData(thisForm);

            if (thisForm.getRng030AddKeiroMode() == 1) {
                thisForm.setRng030AddKeiroMode(0);
                return __doDsp(map, thisForm, req, res, con);
            }
            forward = biz.getForward(map, paramMdl);
            //テンポラリディレクトリを削除する
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));
        } else if (cmd.equals("delTemp")) {
            log__.debug("削除(添付ファイル)ボタンクリック");
            forward = __doDelTemp(map, thisForm, req, res, con);

        } else if (cmd.equals("approval")) {
            log__.debug("承認ボタンクリック");
            forward = __doApproval(map, thisForm, req, res, con);

        } else if (cmd.equals("reject")) {
            log__.debug("却下ボタンクリック");
            forward = __doDenial(map, thisForm, req, res, con);

        } else if (cmd.equals("reflection")) {
            log__.debug("差し戻しボタンクリック");
            forward = __doReflection(map, thisForm, req, res, con);

        } else if (cmd.equals("confirmation")) {
            log__.debug("確認ボタンクリック");
            forward = __doConfirmation(map, thisForm, req, res, con);

        } else if (cmd.equals("complete")) {
            log__.debug("完了ボタンクリック");
            forward = __doComplete(map, thisForm, req, res, con);

        } else if (cmd.equals("compelcomplete")) {
            log__.debug("強制完了ボタンクリック");
            forward = __doCompleComplete(map, thisForm, req, res, con);

        } else if (cmd.equals("compeldelete")) {
            log__.debug("強制削除ボタンクリック");
            forward = __doCompleDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("skip")) {
            log__.debug("スキップボタンクリック");
            forward = __doSkip(map, thisForm, req, res, con);

        } else if (cmd.equals("applicate")) {
            log__.debug("再申請ボタンクリック");
            forward = __doApplicate(map, thisForm, req, res, con);

        } else if (cmd.equals("fileDownload")) {
            //添付ファイルリンククリッククリック
            forward = __doDownLoad(map, thisForm, req, res, con);

        } else if (cmd.equals("copyApply")) {
            //複写して申請ボタンクリック
            forward = __doCopyApply(map, thisForm, req, res, con);

        } else if (cmd.equals("rngConfirm")) {
            log__.debug("各種確認画面からの遷移");
            forward = __doCompletePage(map, thisForm, res, req, con);
        } else if (cmd.equals("addKeiro")) {
            //ダイアログ内 経路に追加ボタンクリック
            log__.debug("経路に追加実行ボタンクリック");
            forward = __doAddRoot(map, thisForm, res, req, con);

        } else if (cmd.equals("loadAddKeiro")) {
            //画面内 経路に追加ボタンクリック
            log__.debug("経路に追加ダイアログオープンボタンクリック");
            forward = __loadDlgAddRoot(map, thisForm, res, req, con);
        } else if (cmd.equals("reload")) {
            log__.debug("描画更新");
            forward = __doDsp(map, thisForm, req, res, con);

        } else if (cmd.equals("torisage")) {
            //取り下げボタンクリック
            log__.debug("取り下げボタンクリック");
            forward = __doTorisage(map, thisForm, res, req, con);

        } else if (cmd.equals("koetu")) {
            //後閲ボタンクリック
            log__.debug("後閲ボタンクリック");
            forward = __doKoetu(map, thisForm, res, req, con);

        } else if (cmd.equals("editcomment")) {
            //確定ボタンクリック
            log__.debug("確定ボタンクリック");
            __doKakutei(map, thisForm, res, req, con);

        } else if (cmd.equals("delEditTemp")) {
            //添付編集にて削除ボタンクリック
            log__.debug("削除ボタンクリック");
            __doDeleteEditTemp(map, thisForm, req, res, con);

        } else if (cmd.equals("editRngData")) {
            //編集ボタンクリック
            log__.debug("編集ボタンクリック");
            __doEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("pdf")) {
            //PDF出力
            log__.debug("PDFファイルダウンロード");
            __doDownLoadPdf(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }
    /**
     * <br>[機  能] 経路に追加ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param res レスポンス
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __loadDlgAddRoot(ActionMapping map,
            Rng030Form form, HttpServletResponse res,
            HttpServletRequest req, Connection con) throws Exception {
        if (form.getRng030AddKeiroMode() == 2) {
            RequestModel reqMdl = getRequestModel(req);
            Rng030ParamModel paramMdl = new Rng030ParamModel();
            Rng030Biz biz = new Rng030Biz(con, reqMdl);
            paramMdl.setParam(form);
            biz.validateInitAddKeiro(paramMdl);
            paramMdl.setFormData(form);
            ActionErrors errors = form.validateCheckAddKeiro(con, getRequestModel(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
            }
        }
        form.setRng030AddKeiroMode(1);
        ActionForward forward = __doDsp(map, form, req, res, con);
        if ("gf_msg".equals(forward.getName())) {
            return forward;
        }
        return map.findForward("loadAddKeiro");

    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画面表示情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doDsp(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        //画面表示情報を設定
        RequestModel reqMdl = getRequestModel(req);
        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        paramMdl.setParamList(form);
        Rng030Biz biz = new Rng030Biz(con, reqMdl);
        // ショートメール通知から遷移時はセッションユーザSIDをアカウントSIDとする(代理人は除く)
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        GSTemporaryPathModel tempDir = _getRingiDir(req);

        if (cmd.equals("fromSmail")) {
            int sessionSid = reqMdl.getSmodel().getUsrsid();
            int viewSid = NullDefault.getInt(req.getParameter("rng010ViewAccount"),
                    sessionSid);
            paramMdl.setRng010ViewAccount(viewSid);
            if (viewSid != sessionSid) {
                paramMdl.setRng010DairiFlg(1);
            }
            int rngSid = NullDefault.getInt(req.getParameter("rngSid"), 0);
            paramMdl.setRngProcMode(biz.getProcMode(rngSid, viewSid, con));
        }
        int userSid = paramMdl.getRng010ViewAccount();
        if (paramMdl.getRng030InitFlg() == 0) {
            //テンポラリディレクトリの削除(初回表示のみ)
            GSTemporaryPathUtil.getInstance().deleteTempPath(tempDir);
        }
        GSTemporaryPathUtil.getInstance().createTempDir(_getRingiDir(req));
        boolean existRingi = biz.setDspData(reqMdl, paramMdl, con,
                                            getAppRootPath(), tempDir,
                                            userSid, req);
        paramMdl.setRng030InitFlg(1);
        paramMdl.setFormData(form);
        paramMdl.setFormList(form);
        con.setAutoCommit(false);

        //稟議が存在しない場合は共通メッセージ画面へ遷移する
        if (!existRingi) {

            Cmn999Form cmn999Form = new Cmn999Form();
            ActionForward urlForward = map.findForward("rng010");

            MessageResources msgRes = getResources(req);
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setUrlOK(urlForward.getPath());

            GsMessage gsMsg = new GsMessage();
            String msg = gsMsg.getMessage(req, "rng.62");

            //メッセージセット
            cmn999Form.setMessage(msgRes.getMessage("search.data.notfound", msg));
            req.setAttribute("cmn999Form", cmn999Form);

            return map.findForward("gf_msg");
        }

        //WEB検索プラグインを使用可能か設定
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        form.setRngWebSearchUse(CommonBiz.getWebSearchUse(pconfig));
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 添付ファイル削除ボタンクリック時の処理
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
    private ActionForward __doDelTemp(
        ActionMapping map,
        Rng030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = _getRingiDir(req);
        log__.debug("テンポラリディレクトリ = " + tempDir);

        //選択された添付ファイルを削除する
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.deleteFile(form.getRng030files(), tempDir);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>編集ボタンクリック
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __doEdit(
            ActionMapping map,
            Rng030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        //テンポラリディレクトリを削除する
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();

        GSTemporaryPathModel tempDir = __doTempPath(form, req);
        tempUtil.deleteTempPath(tempDir);
        tempUtil.createTempDir(tempDir);

        //稟議SID取得
        String editRksSid = NullDefault.getString(req.getParameter("editRksSid"), "");

        if (!StringUtil.isNullZeroStringSpace(editRksSid)) {
            //一時保管ファイル取得
            __doSetTemp(editRksSid, map, form, req, res, con);
        }
    }

    /**
     * <br>テンポラリディレクトリパスを取得
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return tempDir テンポラリディレクトリパス
     */
    private GSTemporaryPathModel __doTempPath(
            Rng030Form form,
            HttpServletRequest req) throws SQLException, Exception {

        String editRksSid = form.getRng030EditRowNo();

        GSTemporaryPathModel tempDir = new GSTemporaryPathModel(_getRingiDir(req),
                SUBDIRNAME_COMMENTEDIT,
                SUBDIRNAME_COMMENTEDIT_ROW + editRksSid
                );
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリに添付ファイル保存
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doSetTemp(String rksSid,
                                      ActionMapping map,
                                      Rng030Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = __doTempPath(form, req);
        int userSid = form.getRng010ViewAccount();

        //添付ファイル情報
        RingiDao ringiDao = new RingiDao(con);
        List<CmnBinfModel> binList = ringiDao.getSingiTemp(Integer.parseInt(rksSid), userSid);
        String[] binSids = new String[binList.size()];

        for (int i = 0; i < binSids.length; i++) {
            binSids[i] = String.valueOf(binList.get(i).getBinSid());
        }
        CommonBiz cmnBiz = new CommonBiz();
        String domain = GroupSession.getResourceManager().getDomain(req);
        binList = cmnBiz.getBinInfo(con, binSids, domain);
        //添付ファイルがあるなるならばテンポラリにコピー
        if (!binList.isEmpty()) {
            Rng030Biz biz = new Rng030Biz(con, getRequestModel(req));
            biz.tempFileCopy(binList, getAppRootPath(), tempDir.getTempPath(), con,
                    domain);

            //添付ファイル情報取得
            String jsonStr = null;
            jsonStr = biz.setTempFiles(tempDir.getTempPath(), con);
            if (!StringUtil.isNullZeroString(jsonStr)) {
                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.print(jsonStr);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(編集)");
                }
            }
        }
        return null;
    }


    /**
     * <br>[機  能] 削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doDeleteEditTemp(ActionMapping map,
                                      Rng030Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = __doTempPath(form, req);

        log__.debug("テンポラリディレクトリ = " + tempDir.getTempPath());

        //jsonデータ取得
        String[] deletefile = __getJsonTempData(req, form);
        //選択された添付ファイルを削除する
        if (deletefile != null) {
            GSTemporaryPathUtil.getInstance().deleteFile(deletefile, tempDir);
        }
        __doRedrawData(map, form, req, res, con);
    }

    /**
     * <br>JSON添付ファイルデータ取得
     * @param req リクエスト
     * @param form アクションフォーム
     * @throws Exception 実行例外
     * @return String[]
     */
    private String[] __getJsonTempData(HttpServletRequest req, Rng030Form form) throws Exception {

        String nippouTempData = NullDefault.getString(req.getParameter("rngTempDataEdit"), "");
        if (!StringUtil.isNullZeroStringSpace((String) nippouTempData)) {

            JSONArray obj = JSONArray.fromObject(nippouTempData);
            ArrayList<String> fileList = new ArrayList<String>();
            for (int i = 0; i < obj.size(); i++) {
                fileList.add((String) obj.getString(i));
            }
            if (!fileList.isEmpty()) {
                return  fileList.toArray(new String[fileList.size()]);
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 承認ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doApproval(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = paramMdl.getRng010ViewAccount();

        //ボタンがつかえるか判定
        if (!btnConfBiz.appRejBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }
        paramMdl.setFormData(form);
        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_APPROVAL__);
    }

    /**
     * <br>[機  能] 否認ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doDenial(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = form.getRng010ViewAccount();

        //ボタンがつかえるか判定
        if (!btnConfBiz.appRejBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }
        paramMdl.setFormData(form);

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_DENIAL__);
    }

    /**
     * <br>[機  能] 差し戻しボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doReflection(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = paramMdl.getRng010ViewAccount();


        //ボタンがつかえるか判定
        if (!btnConfBiz.reflectionBtnConf(con, paramMdl, userSid)) {
            Cmn999Form cmn999Form = new Cmn999Form();

            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            return getCatchExceptionPage(map, req,
                    new RngMoveConfimationKeiroException(),
                    map.findForward("rng010"), cmn999Form);
        }
        paramMdl.setFormData(form);
        form.setRng030confirmMode(MSGPAGETYPE_REFLECTION__);

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_REFLECTION__);
    }

    /**
     * <br>[機  能] 確認ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doConfirmation(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = paramMdl.getRng010ViewAccount();

        //ボタンがつかえるか判定
        if (!btnConfBiz.confBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_CONFIRMATION__);
    }

    /**
     * <br>[機  能] 完了ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doComplete(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = paramMdl.getRng010ViewAccount();

        //ボタンがつかえるか判定
        if (!btnConfBiz.compBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_COMPLETE__);
    }

    /**
     * <br>[機  能] 強制完了ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doCompleComplete(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = getSessionUserModel(req).getUsrsid();

        //ボタンがつかえるか判定・管理者ではない場合はエラー
        if (!btnConfBiz.cpAppDelBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_COMPELCOMPLETE__);
    }

    /**
     * <br>[機  能] 強制削除ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doCompleDelete(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = getSessionUserModel(req).getUsrsid();

        //ボタンがつかえるか判定・管理者ではない場合はエラー
        if (!btnConfBiz.cpAppDelBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_COMPELDELETE__);
    }

    /**
     * <br>[機  能] スキップボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSkip(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = getSessionUserModel(req).getUsrsid();

        //ボタンがつかえるか判定・管理者ではない場合はエラー
        if (!btnConfBiz.skipBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_SKIP__);

    }

    /**
     * <br>[機  能] 再申請ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doApplicate(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, reqMdl);
        int userSid = getSessionUserModel(req).getUsrsid();

        btnConfBiz.templateRoad(paramMdl, paramMdl.getRngSid(), userSid,
                                getAppRootPath(), _getRingiDir(req), false);

        //ボタンがつかえるか判定
        if (!btnConfBiz.applicateBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }

        //入力チェックを行う
        ActionErrors errors = form.validateCheckApplicate(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_APPLICATE__);
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
                                    Rng030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        RngBiz rngBiz = new RngBiz(con);

        CommonBiz commonBiz = new CommonBiz();
        boolean admin = commonBiz.isPluginAdmin(
                con, getRequestModel(req).getSmodel(), RngConst.PLUGIN_ID_RINGI);

        int rngSid = form.getRngSid();
        Long binSid = form.getRng030fileId();
        //申請された稟議の添付ファイルをダウンロード可能かチェックする
        if (rngBiz.isCheckDLRngTemp(con, rngSid, form.getRng010ViewAccount(), admin, binSid)) {
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                    GroupSession.getResourceManager().getDomain(req));
            if (cbMdl != null) {
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                String download = gsMsg.getMessage("cmn.download");
                //ログ出力処理
                rngBiz.outPutLog(
                        map,
                        download, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(),
                        getRequestModel(req));
                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);
                //ファイルをダウンロードする
                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(),
                        Encoding.UTF_8);
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 複写して申請ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng030Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doCopyApply(ActionMapping map, Rng030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = form.getRng010ViewAccount();

        //ボタンがつかえるか判定
        int erChk = btnConfBiz.copyApprBtnConf(con, paramMdl, userSid);
        if (erChk == RngConst.RNG_COPY_ERROR_ACCESS) {
            return getSubmitErrorPage(map, req);
        } else if (erChk == RngConst.RNG_COPY_ERROR_TEMPLATE) {
            ActionForward okForward = map.findForward("rng010");
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");
            return getCatchExceptionPage(map, req,
                    new RngNotAcceptTemplateException(),
                    okForward,
                    cmn999form);
        }
        GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));


        return map.findForward("rng020");
    }

    /**
     * <br>[機  能] 経路に追加ボタン(実行)クリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param res レスポンス
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __doAddRoot(ActionMapping map,
                                            Rng030Form form,
                                            HttpServletResponse res,
                                            HttpServletRequest req,
                                            Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        Rng030ParamModel paramMdl = new Rng030ParamModel();
        Rng030Biz biz = new Rng030Biz(con, reqMdl);
        paramMdl.setParam(form);
        biz.validateInitAddKeiro(paramMdl);
        paramMdl.setFormData(form);
        ActionErrors errors = form.validateCheckAddKeiro(con, getRequestModel(req));
        form.setRng030AddKeiroMode(1);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setRng030AddKeiroMode(2);
            ActionForward forward = __doDsp(map, form, req, res, con);
            return forward;
        }
        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_ADDKEIRO__);
    }

    /**
     * <br>[機  能] 取り下げボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param res レスポンス
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __doTorisage(ActionMapping map,
                                            Rng030Form form,
                                            HttpServletResponse res,
                                            HttpServletRequest req,
                                            Connection con) throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        int userSid = getSessionUserModel(req).getUsrsid();
        btnConfBiz.templateRoad(paramMdl, paramMdl.getRngSid(), userSid,
                                getAppRootPath(), _getRingiDir(req), false);

        //確認画面の設定を行う
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_TORISAGE__);
    }

    /**
     * <br>[機  能] 後閲ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param res レスポンス
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __doKoetu(ActionMapping map,
                                    Rng030Form form,
                                    HttpServletResponse res,
                                    HttpServletRequest req,
                                    Connection con) throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));
        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        int userSid = paramMdl.getRng010ViewAccount();
        //ボタンがつかえるか判定
        if (!btnConfBiz.koetuBtnConf(con, paramMdl, userSid)) {
            return getSubmitErrorPage(map, req);
        }

        form.setRng030confirmMode(MSGPAGETYPE_KOETU__);

        //後閲実行・確認画面へ
        return __doConfirm(map, form, req, res, con, MSGPAGETYPE_KOETU__);
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param res レスポンス
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行時例外発生
     */
    private void __doKakutei(ActionMapping map,
                                    Rng030Form form,
                                    HttpServletResponse res,
                                    HttpServletRequest req,
                                    Connection con) throws Exception {

        //入力チェックを行う
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);

        }

        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);

        RequestModel reqMdl = getRequestModel(req);
        Rng030Biz biz = new Rng030Biz(con, reqMdl);
        int userSid = form.getRng010ViewAccount();
        int rngSid = form.getRngSid();
        int rksSid = form.getRng030RksSid();

        String appRootPath = getAppRootPath();
        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = __doTempPath(form, req);

        //不正アクセス確認(rksSidが不正)
        if (!biz.chkCommentEdit(userSid, rksSid)) {
            __doRedrawData(map, form, req, res, con);
        }

        List<String> logList = biz.edit(paramMdl, getCountMtController(req), rngSid, userSid,
                                        rksSid, appRootPath, tempDir.getTempPath());

        // --------------------------------------------
        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.comment") + gsMsg.getMessage("cmn.edit");

        RngRndataDao rngDao = new RngRndataDao(con);
        RngRndataModel rngMdl = rngDao.select(rngSid);

        String msg = "[" + gsMsg.getMessage("rng.62") + "] ";
        if (rngMdl != null) {
            msg += rngMdl.getRngTitle();
        }
        msg += "\r\n[" + gsMsg.getMessage("cmn.comment") + "] "; // コメント
        msg += logList.get(0);
        if (logList.size() > 1) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.attach.file") + "] "; // 添付ファイル
            for (int i = 1; i < logList.size(); i++) {
                if (i > 1) {
                    msg += ",";
                }
                msg += logList.get(i);
            }
        }

        RngBiz rngBiz = new RngBiz(con);
        rngBiz.outPutLog(map, opCode, GSConstLog.LEVEL_TRACE, msg, reqMdl);
        // --------------------------------------------

        __doRedrawData(map, form, req, res, con);

        //テンポラリディレクトリを削除する
        GSTemporaryPathUtil.getInstance().deleteTempPath(tempDir);
    }

    /**
     * <br>[機  能] 各種確認画面の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 確認画面の種類
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doConfirm(ActionMapping map,
                                    Rng030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con,
                                    int mode
                                    )
        throws Exception {


        Cmn999Form cmn999Form = new Cmn999Form();
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //種別により確認メッセージを変更
        String msgState = "";
        switch (mode) {
            case  MSGPAGETYPE_APPROVAL__:
                //承認
                msgState = "approval.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_DENIAL__ :
                //否認
                msgState = "denial.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_REFLECTION__ :
                //差し戻し
                msgState = "reflection.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_CONFIRMATION__ :
                //確認
                msgState = "confirmation.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_COMPLETE__ :
            case MSGPAGETYPE_COMPELCOMPLETE__ :
                //完了 or 強制完了
                msgState = "complete.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_COMPELDELETE__ :
                //強制削除
                msgState = "delete.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_SKIP__ :
                //スキップ
                msgState = "skip.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_APPLICATE__ :
                //再申請
                msgState = "applicate.kakunin.ringi2";
                break;
            case MSGPAGETYPE_TORISAGE__:
                //取り消し
                msgState = "torisage.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_KOETU__:
                // 後閲指示
                msgState = "koetu.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_EDIT__:
                // 編集
                msgState = "edit.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            case MSGPAGETYPE_ADDKEIRO__:
                // 経路追加
                msgState = "addkeiro.kakunin.ringi2";
                //稟議タイトル
                cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
                break;
            default :
                return __doDsp(map, form, req, res, con);
        }
        if (mode == MSGPAGETYPE_APPLICATE__) {
            //再申請
            cmn999Form.setMessage(msgRes.getMessage(msgState,
                    StringUtilHtml.transToHTmlPlusAmparsant(form.getRng030Title())));
        } else if (mode == MSGPAGETYPE_TORISAGE__) {
            //取り下げ
            String torisageMsg = null;
            if (form.getRng030ViewTitle() != null) {
                torisageMsg = form.getRng030ViewTitle();
            } else {
                torisageMsg = form.getRng030Title();
            }
            cmn999Form.setMessage(msgRes.getMessage(msgState,
                    StringUtilHtml.transToHTmlPlusAmparsant(torisageMsg)));

        } else {
            //それ以外
            cmn999Form.setMessage(msgRes.getMessage(msgState,
                    StringUtilHtml.transToHTmlPlusAmparsant(form.getRng030ViewTitle())));
        }

        cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=rngConfirm");
        cmn999Form.setUrlCancel(map.findForward("mine").getPath());
        cmn999Form = __setFormParam(cmn999Form, form, mode);
        req.setAttribute("cmn999Form", cmn999Form);
        saveToken(req);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param res レスポンス
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __doCompletePage(ActionMapping map,
                                            Rng030Form form,
                                            HttpServletResponse res,
                                            HttpServletRequest req,
                                            Connection con) throws Exception {

        int mode = form.getRng030confirmMode();
        if (!(mode == MSGPAGETYPE_REFLECTION__ || mode == MSGPAGETYPE_KOETU__)) {
            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }
        }

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        String title = form.getRng030ViewTitle();
        if (title == null) {
            title = form.getRng030Title();
        }
        RngBiz rngBiz = new RngBiz(con);

        boolean commit = false;

        try {
            //種別により更新処理、完了メッセージを変更
            RequestModel reqMdl = getRequestModel(req);
            String msgState = "";
            Rng030Biz biz = new Rng030Biz(con, reqMdl);
            int userSid = form.getRng010ViewAccount();
            String appRootPath = getAppRootPath();
            GSTemporaryPathModel tempDir = _getRingiDir(req);

            PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
            CommonBiz cmnBiz = new CommonBiz();
            boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);
            int sUserSid = getSessionUserModel(req).getUsrsid();

            Rng030ParamModel paramMdl = new Rng030ParamModel();
            paramMdl.setParam(form);

            GsMessage gsMsg = new GsMessage(reqMdl);
            String opCode = "";
            String logLv = null;
            StringBuilder msgValue = new StringBuilder();
            msgValue.append("[");
            msgValue.append(gsMsg.getMessage("rng.62"));
            msgValue.append("]");
            msgValue.append(title);
            int rngSid = form.getRngSid();

            switch (mode) {
                case MSGPAGETYPE_APPROVAL__ :
                    //承認

                    if (!form.validateCheck(req).isEmpty()) {
                        //入力チェック
                        return getSubmitErrorPage(map, req);
                    }

                    //ボタンがつかえるか判定
                    if (!biz.appRejBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                map.findForward("mine"), cmn999Form);
                    }

                    biz.approvalRingi(paramMdl, getCountMtController(req), userSid,
                                    appRootPath, tempDir.getTempPath(),
                                    getPluginConfig(req),
                                    smailPluginUseFlg,
                                    reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "approval.kanryo.ringi2";

                    opCode = gsMsg.getMessage("rng.41");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_DENIAL__ :
                    //否認

                    if (!form.validateCheck(req).isEmpty()) {
                        //入力チェック
                        return getSubmitErrorPage(map, req);
                    }

                    //ボタンがつかえるか判定
                    if (!biz.appRejBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }

                    biz.denialRingi(paramMdl, getCountMtController(req), userSid,
                                    appRootPath, tempDir.getTempPath(),
                                    getPluginConfig(req),
                                    smailPluginUseFlg,
                                    reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "denial.kanryo.ringi2";

                    opCode = gsMsg.getMessage("rng.rng030.12");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_REFLECTION__ :
                    //差し戻し
                    if (!form.validateCheck(req).isEmpty()) {
                        //入力チェック
                        return getSubmitErrorPage(map, req);
                    }

                    //ボタンがつかえるか判定
                    if (!biz.reflectionBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }

                    int beforeRksSid = biz.reflectionRingi(paramMdl,
                            getCountMtController(req), userSid,
                            appRootPath, tempDir.getTempPath(),
                            getPluginConfig(req),
                            smailPluginUseFlg,
                            reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "reflection.kanryo.ringi2";
                    opCode = gsMsg.getMessage("rng.rng030.08");

                    //差し戻し先のログかき出し
                    biz.setOpLogValue(gsMsg, msgValue, beforeRksSid, rngSid);

                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_CONFIRMATION__ :
                    //確認
                    //ボタンがつかえるか判定
                    if (!biz.confBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }

                    biz.confirmationRingi(paramMdl, con, getCountMtController(req),
                                        userSid, appRootPath, tempDir.getTempPath());
                    paramMdl.setFormData(form);
                    msgState = "confirmation.kanryo.ringi2";

                    opCode = gsMsg.getMessage("cmn.check");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_COMPLETE__ :
                    //完了
                    //ボタンがつかえるか判定
                    if (!biz.compBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }

                    biz.completeRingi(paramMdl, con, userSid,
                                    getCountMtController(req), appRootPath, getPluginConfig(req),
                                    smailPluginUseFlg, reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "complete.kanryo.ringi2";

                    opCode = gsMsg.getMessage("cmn.complete");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_COMPELCOMPLETE__ :
                    //強制完了
                    if (!form.validateCheck(req).isEmpty()) {
                        //入力チェック
                        return getSubmitErrorPage(map, req);
                    }
                    //ボタンがつかえるか判定
                    if (!biz.cpAppDelBtnConf(con, paramMdl, sUserSid)) {
                        return getSubmitErrorPage(map, req);
                    }

                    biz.compelCompleteRingi(paramMdl, con, userSid,
                                    getCountMtController(req), appRootPath, getPluginConfig(req));
                    paramMdl.setFormData(form);
                    msgState = "complete.kanryo.ringi2";

                    opCode = gsMsg.getMessage("rng.rng030.06");
                    logLv = GSConstLog.LEVEL_INFO;
                    break;
                case MSGPAGETYPE_COMPELDELETE__ :
                    //ボタンがつかえるか判定
                    if (!biz.cpAppDelBtnConf(con, paramMdl, sUserSid)) {
                        return getSubmitErrorPage(map, req);
                    }
                    //強制削除
                    biz.compelDeleteRingi(paramMdl, con, userSid);
                    paramMdl.setFormData(form);
                    msgState = "delete.kanryo.ringi2";

                    opCode = gsMsg.getMessage("rng.rng030.07");
                    logLv = GSConstLog.LEVEL_INFO;
                    break;
                case MSGPAGETYPE_SKIP__ :
                    //スキップ
                    //ボタンがつかえるか判定
                    if (!biz.skipBtnConf(con, paramMdl, sUserSid)) {
                        return getSubmitErrorPage(map, req);
                    }
                    int skipRksSid = biz.skipRingi(paramMdl, con, userSid,
                            getCountMtController(req), appRootPath, getPluginConfig(req),
                            smailPluginUseFlg, reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "skip.kanryo.ringi2";

                    //スキップ対象経路のログ書き出し
                    biz.setOpLogValue(gsMsg, msgValue, skipRksSid, rngSid);
                    opCode = gsMsg.getMessage("rng.rng030.03");

                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_APPLICATE__ :
                    //再申請
                    Rng030Biz btnConfBiz = new Rng030Biz(con, getRequestModel(req));

                    btnConfBiz.templateRoad(paramMdl, paramMdl.getRngSid(), userSid,
                                            appRootPath, tempDir, false);

                    ActionErrors errors = form.validateCheckApplicate(reqMdl);
                    if (!errors.isEmpty()) {
                        //入力チェック
                        return getSubmitErrorPage(map, req);
                    }
                    if (!btnConfBiz.applicateBtnConf(con, paramMdl, sUserSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }

                    biz.applicateRingi(paramMdl, getCountMtController(req), sUserSid,
                                    appRootPath, _getRingiDir(req).getTempPath(),
                                    getPluginConfig(req),
                                    smailPluginUseFlg, reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "applicate.kanryo.ringi2";

                    opCode = gsMsg.getMessage("rng.rng030.09");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_TORISAGE__:
                    //取り下げ
                    if (!biz.torisageBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }

                    biz.torisage(paramMdl, getCountMtController(req), userSid,
                            appRootPath, getPluginConfig(req), smailPluginUseFlg, reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "torisage.kanryo.ringi2";

                    opCode = gsMsg.getMessage("rng.rng030.15");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_KOETU__:
                    //後閲

                    if (!form.validateCheck(req).isEmpty()) {
                        //入力チェック
                        return getSubmitErrorPage(map, req);
                    }
                    if (!biz.koetuBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }
                    //後閲指示先のソート番号取得
                    int koetuRksSid = biz.koetu(paramMdl, getCountMtController(req), userSid,
                            appRootPath, tempDir.getTempPath(),
                            getPluginConfig(req),
                            smailPluginUseFlg,
                            reqMdl);
                    paramMdl.setFormData(form);
                    msgState = "koetu.kanryo.ringi2";

                    //後閲指示先をオペレーションログに書き出し
                    biz.setOpLogValue(gsMsg, msgValue, koetuRksSid, rngSid);

                    opCode = gsMsg.getMessage("rng.rng030.18");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                case MSGPAGETYPE_ADDKEIRO__:
                    //経路の追加
                    paramMdl.setParam(form);
                    biz.validateInitAddKeiro(paramMdl);
                    paramMdl.setFormData(form);
                    if (!(form.validateCheckAddKeiro(con, getRequestModel(req)).isEmpty())) {
                        //入力チェック
                        return getSubmitErrorPage(map, req);
                    }
                    if (!biz.addKeiroBtnConf(con, paramMdl, userSid)) {
                        return getCatchExceptionPage(map, req,
                                new RngMoveConfimationKeiroException(),
                                biz.getForward(map, paramMdl), cmn999Form);
                    }

                    // 追加経路をオペレーションログ出力用文字列に書き出し
                    // 登録前にやらないと対象行番号が変わってしまう
                    msgValue.append("\r\n");
                    UserGroupSelectBiz usrBiz = new UserGroupSelectBiz();
                    for (Entry<Integer, Rng020KeiroBlock> entry
                            : paramMdl.getRng030addKeiroMap().entrySet()) {
                        biz.setOpLogValue(gsMsg, msgValue, entry.getKey(), rngSid);
                        msgValue.append("\r\n");
                        for (Entry<Integer, Rng020Keiro> entryKeiro
                                :entry.getValue().getKeiroMap().entrySet()) {
                            Rng020Keiro keiro = entryKeiro.getValue();
                            msgValue.append("追加");
                            msgValue.append(entryKeiro.getKey() + 1);
                            msgValue.append(":");
                            String[] selected = keiro.getUsrgrpSel().getSelectedSimple();
                            ArrayList<UsrLabelValueBean> labelList
                            = usrBiz.getSelectedLabel(selected, con);
                            boolean first = true;
                            for (UsrLabelValueBean label : labelList) {
                                if (!first) {
                                    msgValue.append(",");
                                }
                                msgValue.append(label.getLabel());
                                first = false;
                            }
                            msgValue.append("\r\n");
                        }

                    }

                    biz.registAddKeiro(paramMdl, getCountMtController(req));
                    paramMdl.setFormData(form);
                    msgState = "addkeiro.kanryo.ringi2";


                    opCode = gsMsg.getMessage("rng.rng020.02");
                    logLv = GSConstLog.LEVEL_TRACE;
                    break;
                default :
                    return map.findForward("gf_auth");
            }

            if (logLv != null) {
                //ログ出力処理
                rngBiz.outPutLog(
                        map,
                        opCode,
                        logLv,
                        msgValue.toString(),
                        reqMdl);
            }

            MessageResources msgRes = getResources(req);
            // 完了画面へ稟議タイトルを引き継ぎ
            cmn999Form.setMessage(msgRes.getMessage(msgState,
                    StringUtilHtml.transToHTmlPlusAmparsant(title)));

            //戻り先画面を設定
            ActionForward urlForward = biz.getForward(map, paramMdl);
            paramMdl.setFormData(form);
            cmn999Form.setUrlOK(urlForward.getPath());

            cmn999Form = __setFormParam(cmn999Form, form, mode);
            req.setAttribute("cmn999Form", cmn999Form);

            //テンポラリディレクトリを削除する
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error(e);
            throw e;

        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 共通メッセージフォームにフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     * @param form アクションフォーム
     * @param mode 確認種別
     * @return 共通メッセージフォーム
     */
    private Cmn999Form __setFormParam(Cmn999Form cmn999Form, Rng030Form form, int mode) {

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("rngSid", form.getRngSid());
        cmn999Form.addHiddenParam("rngTemplateMode", form.getRngTemplateMode());
        cmn999Form.addHiddenParam("rngCmdMode", form.getRngCmdMode());
        cmn999Form.addHiddenParam("rngApprMode", form.getRngApprMode());
        cmn999Form.addHiddenParam("rngDspMode", form.getRngDspMode());
        cmn999Form.addHiddenParam("rngSearchKeyword", form.getRngSearchKeyword());
        cmn999Form.addHiddenParam("rngSearchGroup", form.getRngSearchGroup());
        cmn999Form.addHiddenParam("rngSearchUser", form.getRngSearchUser());
        cmn999Form.addHiddenParam("rng030CmdMode", form.getRng030CmdMode());
        cmn999Form.addHiddenParam("rng030mode", form.getRng030mode());
        cmn999Form.addHiddenParam("rng030compBtnFlg", form.getRng030compBtnFlg());
        cmn999Form.addHiddenParam("rng030skipBtnFlg", form.getRng030skipBtnFlg());
        cmn999Form.addHiddenParam("rng030fileId", form.getRng030fileId());
        cmn999Form.addHiddenParam("rng030Comment", form.getRng030Comment());
        cmn999Form.addHiddenParam("rng030files", form.getRng030files());
        cmn999Form.addHiddenParam("rng030confirmMode", mode);
        cmn999Form.addHiddenParam("rng030Title", form.getRng030Title());
        cmn999Form.addHiddenParam("rng030ViewTitle", form.getRng030ViewTitle());
        cmn999Form.addHiddenParam("rng030Content", form.getRng030Content());
        cmn999Form.addHiddenParam("rng030RksSid", form.getRng030RksSid());
        cmn999Form.addHiddenParam("rng030KoetuFlg", form.getRng030KoetuFlg());
        cmn999Form.addHiddenParam("rng010DairiFlg", form.getRng010DairiFlg());
        cmn999Form.addHiddenParam("rng030koetuNo", form.getRng030koetuNo());
        cmn999Form.addHiddenParam("rng030SasiNo", form.getRng030SasiNo());
        cmn999Form.addHiddenParam("rng030InitFlg", form.getRng030InitFlg());
        //経路追加関連パラメータ
        form.addHiddenParamAddKeiro(cmn999Form);

        cmn999Form.addHiddenParam("rngAdminKeyword", form.getRngAdminKeyword());
        cmn999Form.addHiddenParam("rngAdminGroupSid", form.getRngAdminGroupSid());
        cmn999Form.addHiddenParam("rngAdminUserSid", form.getRngAdminUserSid());
        cmn999Form.addHiddenParam("rngAdminApplYearFr", form.getRngAdminApplYearFr());
        cmn999Form.addHiddenParam("rngAdminApplMonthFr", form.getRngAdminApplMonthFr());
        cmn999Form.addHiddenParam("rngAdminApplDayFr", form.getRngAdminApplDayFr());
        cmn999Form.addHiddenParam("rngAdminApplYearTo", form.getRngAdminApplYearTo());
        cmn999Form.addHiddenParam("rngAdminApplMonthTo", form.getRngAdminApplMonthTo());
        cmn999Form.addHiddenParam("rngAdminApplDayTo", form.getRngAdminApplDayTo());
        cmn999Form.addHiddenParam("rngAdminLastManageYearFr", form.getRngAdminLastManageYearFr());
        cmn999Form.addHiddenParam("rngAdminLastManageMonthFr", form.getRngAdminLastManageMonthFr());
        cmn999Form.addHiddenParam("rngAdminLastManageDayFr", form.getRngAdminLastManageDayFr());
        cmn999Form.addHiddenParam("rngAdminLastManageYearTo", form.getRngAdminLastManageYearTo());
        cmn999Form.addHiddenParam("rngAdminLastManageMonthTo", form.getRngAdminLastManageMonthTo());
        cmn999Form.addHiddenParam("rngAdminLastManageDayTo", form.getRngAdminLastManageDayTo());
        cmn999Form.addHiddenParam("rngAdminSortKey", form.getRngAdminSortKey());
        cmn999Form.addHiddenParam("rngAdminOrderKey", form.getRngAdminOrderKey());
        cmn999Form.addHiddenParam("rngAdminPageTop", form.getRngAdminPageTop());
        cmn999Form.addHiddenParam("rngAdminPageBottom", form.getRngAdminPageBottom());
        cmn999Form.addHiddenParam("rngAdminSearchFlg", form.getRngAdminSearchFlg());
        cmn999Form.addHiddenParam("rngInputKeyword", form.getRngInputKeyword());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("sltUserSid", form.getSltUserSid());
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

        cmn999Form.addHiddenParam("rng130Type", form.getRng130Type());
        cmn999Form.addHiddenParam("svRng130Category", form.getSvRng130Category());
        cmn999Form.addHiddenParam("rng130keyKbn", form.getRng130keyKbn());
        cmn999Form.addHiddenParam("rng130searchSubject1", form.getRng130searchSubject1());
        cmn999Form.addHiddenParam("rng130searchSubject2", form.getRng130searchSubject2());
        cmn999Form.addHiddenParam("rng130searchSubject3", form.getRng130searchSubject3());
        cmn999Form.addHiddenParam("sltSortKey1", form.getSltSortKey1());
        cmn999Form.addHiddenParam("rng130orderKey1", form.getRng130orderKey1());
        cmn999Form.addHiddenParam("sltSortKey2", form.getSltSortKey2());
        cmn999Form.addHiddenParam("rng130orderKey2", form.getRng130orderKey1());
        cmn999Form.addHiddenParam("rng130pageTop", form.getRng130pageTop());
        cmn999Form.addHiddenParam("rng130pageBottom", form.getRng130pageBottom());

        cmn999Form.addHiddenParam("svRngViewAccount", form.getSvRngViewAccount());
        cmn999Form.addHiddenParam("svRngKeyword", form.getSvRngKeyword());
        cmn999Form.addHiddenParam("svRng130Type", form.getSvRng130Type());
        cmn999Form.addHiddenParam("svGroupSid", form.getSvGroupSid());
        cmn999Form.addHiddenParam("svUserSid", form.getSvUserSid());
        cmn999Form.addHiddenParam("svRng130keyKbn", form.getSvRng130keyKbn());
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

        if (mode == MSGPAGETYPE_APPLICATE__ || mode == MSGPAGETYPE_TORISAGE__) {
            form.getRng030template().setHiddenParam(cmn999Form, "rng030template");
        }
        
        return cmn999Form;
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
                                      Rng030Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        JSONObject jsonData = new JSONObject();

        con.setAutoCommit(true);
        //トランザクショントークン設定
        this.saveToken(req);

        //添付ファイル情報取得
        Rng030Biz biz = new Rng030Biz(con, getRequestModel(req));
        jsonData = biz.setTempSingiFiles(con, form.getRng030RksSid(), form.getRng010ViewAccount());
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(コメント編集)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
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
            Rng030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルダウンロード処理(稟議単票PDF)");

        RequestModel reqMdl = getRequestModel(req);

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();

        String tmpDirId = tempUtil.getTempDirID(reqMdl, getPluginId());
        GSTemporaryPathModel tempDir = GSTemporaryPathModel.getInstance(
                reqMdl, getPluginId(), tmpDirId);

        //PDF生成
        Rng030ParamModel paramMdl = new Rng030ParamModel();
        paramMdl.setParam(form);
        paramMdl.setParamList(form);
        Rng030Biz biz = new Rng030Biz(con, reqMdl);
        RngTanPdfModel pdfMdl =
                biz.createRngTanPdf(paramMdl, appRootPath,
                        tempDir.getTempPath(), getSessionUserSid(req));
        paramMdl.setFormData(form);
        paramMdl.setFormList(form);

        String outBookName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSaveFileName();
        String outFilePath = IOTools.setEndPathChar(tempDir.getTempPath()) + saveFileName;
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        tempUtil.deleteTempPath(tempDir);

        //ログ出力処理
        RngBiz rngBiz = new RngBiz(con);
        GsMessage gsMsg = new GsMessage();
        String downloadPdf = gsMsg.getMessage(req, "cmn.pdf");
        rngBiz.outPutLog(map, downloadPdf, GSConstLog.LEVEL_INFO, outBookName,  reqMdl, null,
                form.getRngTemplateMode());

        return null;
    }
}
