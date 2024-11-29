package jp.groupsession.v2.rng.rng020;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RngNotAcceptTemplateException;
import jp.groupsession.v2.rng.RngNotfoundException;
import jp.groupsession.v2.rng.RngUnuseableKeiroException;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.RtpUnuseableInputException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.model.RingiRequestModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng100.Rng100Biz;
import jp.groupsession.v2.rng.rng100.Rng100ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 稟議作成画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020Action extends AbstractRingiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng020Action.class);
    /** ディレクトリID */
    public static final String DIRID = "rng020";

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

        if (cmd.equals("templateFileDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;

        }
        return false;
    }
    @Override
    protected String _getTempDirId() {
        return DIRID;
    }
    /**
    *
    * <br>[機  能] アクション実行前の事前処理を行う
    * <br>[解  説] パラメータ比較による初期値切り替えなど
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @throws Exception 実行時例外
    */
   protected void _prepareAction(ActionMapping map, Rng020Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {

       //遷移元画面の設定（初回アクセス時）
       if (form.getRng020prevForward() == null) {
           //稟議テンプレート選択画面からの遷移
           if (req.getAttribute("rng060Form") != null) {
               form.setRng020prevForward("rng060");
           } else if (req.getAttribute("rng130Form") != null) {
               form.setRng020prevForward("search");
           } else {
               form.setRng020prevForward("rng010");
           }
       }


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
    protected ActionForward _immigration(ActionMapping map, Rng020Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        ActionForward back;
        back = __getForwardBack(form, map);
        int viewUserSid = reqMdl.getSmodel().getUsrsid();

        Rng020Biz rng020Biz = new Rng020Biz(con, reqMdl);
        Cmn999Form cmn999form = new Cmn999Form();
        cmn999form.addHiddenAll(form, form.getClass(), "");

        //経路テンプレートダイアログ表示以外の場合、システムユーザのアクセスを許可しない
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (!cmd.equals("rctSelectDialog")
                && getSessionUserSid(req) <= GSConstUser.USER_RESERV_SID) {
            return getSubmitErrorPage(map, req);
        }

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
        return null;
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
    public ActionForward __getForwardBack(Rng020Form thisForm, ActionMapping map) {
        ActionForward forward;

        if (thisForm.getRngCmdMode() == RngConst.RNG_CMDMODE_ADD) {
            if (thisForm.isRng020copyApply()) {
                forward = map.findForward("rng030");
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
            if (thisForm.getRng020prevForward().equals("search")) {
                forward = map.findForward("search");
                return forward;
            }
        }
        forward = map.findForward("rng010");
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

        Rng020Form thisForm = (Rng020Form) form;

        _prepareAction(map, thisForm, req, res, con);

        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        try {
            if (cmd.equals("rng010")) {
                log__.debug("*** 戻るボタン。");

                //テンポラリディレクトリの削除
                GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();

                tempUtil.deleteTempPath(getRequestModel(req), getPluginId(), _getTempDirId());
                forward = __getForwardBack(thisForm, map);

            } else if (cmd.equals("rng060")) {
                log__.debug("*** 内容テンプレート一覧。");
                forward = map.findForward("rng060");
            } else if (cmd.equals("delTemp")) {
                log__.debug("削除(添付ファイル)ボタンクリック");
                forward = __doDelTemp(map, thisForm, req, res, con);

            } else if (cmd.equals("draft")) {
                log__.debug("草稿に保存ボタンクリック");

                forward = __doEntry(map, thisForm, req, res, con, 1);

            } else if (cmd.equals("approval")) {
                log__.debug("申請ボタンクリック");
                forward = __doEntry(map, thisForm, req, res, con, 0);

            } else if (cmd.equals("rng020knback")) {
                log__.debug("確認画面戻るボタンクリック");
                forward = __doDsp(map, thisForm, req, res, con);

            } else if (cmd.equals("060back")) {
                log__.debug("内容テンプレート一覧画面戻るボタンクリック");
                forward = __doDsp(map, thisForm, req, res, con);

            } else if (cmd.equals("reload")) {
                log__.debug("再読み込み");
                forward = __doDsp(map, thisForm, req, res, con);

            } else if (cmd.equals("optbtn")) {
                log__.debug("テンプレート使用");
                forward = __doDsp(map, thisForm, req, res, con);

            } else if (cmd.equals("templateFileDownload")) {
                log__.debug("添付ファイルダウンロード");
                forward = __doDownLoad(map, thisForm, req, res, con);
            } else if (cmd.equals("rctSelectDialog")) {
                log__.debug("*** 経路テンプレート選択ダイアログ");
                forward = __rctSelectDialog(map, thisForm, req, res, con);
            } else {
                log__.debug("*** 初期表示を行います。");
                forward = __doInit(map, thisForm, req, res, con);
            }
        } catch (RtpNotfoundException rtpe) {
            log__.debug("*** テンプレートが削除されました。");
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(thisForm, thisForm.getClass(), "");
            ActionForward backForward = __getForwardBack(thisForm, map);

            return getCatchExceptionPage(map, req,
                    rtpe, backForward, cmn999form);
        }
        return forward;
    }
    /**
     * <br>[機  能] 稟議経路テンプレートダイアログの表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    protected ActionForward __rctSelectDialog(ActionMapping map,
            Rng020Form thisForm, HttpServletRequest req,
            HttpServletResponse res, Connection con) throws SQLException {
        Rng100Biz rng100Biz = new Rng100Biz();
        Rng100ParamModel param1 = new Rng100ParamModel();
        rng100Biz.initDsp(con, param1, 0);
        Rng100ParamModel param2 = new Rng100ParamModel();
        rng100Biz.initDsp(con, param2, getSessionUserSid(req));

        req.setAttribute("rng230Form", param1);
        req.setAttribute("rng100Form", param2);

        return map.findForward("rctSelectDialog");
    }
    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws RtpNotfoundException テンプレート削除済み例外
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws RtpNotfoundException, Exception {
        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリの削除
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();

        tempUtil.deleteTempPath(getRequestModel(req), getPluginId(), _getTempDirId());
        tempUtil.createTempDir(getRequestModel(req), getPluginId(), _getTempDirId());
        RngBiz rngBiz = new RngBiz(con);

        Rng020ParamModel paramMdl = new Rng020ParamModel();
        paramMdl.setParam(form);
        Rng020Biz biz = new Rng020Biz(con, reqMdl);
        biz.setInitData(req, paramMdl, getAppRootPath(),
                _getRingiDir(req), getSessionUserModel(req), false);

        paramMdl.setFormData(form);

        ActionForward okForward = __getForwardBack(form, map);

        //初回表示時、表示可能なテンプレートかを判定する
        //経路が使用できるかは経路等の表示設定がそろってからでないと判定できないためinit後に行う

        RngAconfModel aconfMdl = rngBiz.getRngAconf(con);

        // 汎用稟議テンプレート
        if (form.getRng020rtpSid() == 0
                && aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
                // 草稿判定
                boolean isLoaded = false;
                if (form.getRngSid() >= 0 && !form.isRng020copyApply()) {
                    isLoaded = true;
                }
                // 草稿外ならエラー画面に遷移
                if (!isLoaded) {
                    return getSubmitErrorPage(map, req);
                } else {
                // 草稿なら画面内で制限設定を行う
                    form.setUseTemplateFlg(RngConst.RTP_LIMIT_TEMPLATE_NO);
                    form.setRng020ButtonDsp(false);
                    return map.getInputForward();
                }
        }

        // 個人テンプレート
        if (form.getRng020rtpSid() > 0
                && form.getRng020rtpType() == RngConst.RNG_TEMPLATE_PRIVATE) {
            // 草稿判定
            boolean isLoaded = false;
            if (form.getRngSid() >= 0 && !form.isRng020copyApply()) {
                isLoaded = true;
            }
            if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
             // 草稿外ならエラー画面に遷移
                if (!isLoaded) {
                    return getSubmitErrorPage(map, req);
                }
             // 草稿なら画面内で制限設定を行う
                form.setUseTemplateFlg(RngConst.RTP_LIMIT_TEMPLATE_NO);
                form.setRng020ButtonDsp(false);
                return map.getInputForward();
            }
            // 個人テンプレート使用制限
            if (aconfMdl.getRarTemplatePersonalFlg()
                    == RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO) {
                // 草稿外かつ複写新規作成外ならエラー画面に遷移
                if (!isLoaded && !form.isRng020copyApply()) {
                    return getSubmitErrorPage(map, req);
                }
            }
        }
        // 共有テンプレート
        if (form.getRng020rtpSid() > 0
                && form.getRng020rtpType() == RngConst.RNG_TEMPLATE_SHARE) {
            ActionErrors errors = form.getRng020input().chkUnuseableInput(reqMdl);

            if (errors.size() > 0) {
                Cmn999Form cmn999form = new Cmn999Form();
                cmn999form.addHiddenAll(form, form.getClass(), "");
                return getCatchExceptionPage(map, req,
                        new RtpUnuseableInputException(errors, getResources(req)), okForward,
                        cmn999form);
            }
        }
        //テンプレートへのアクセス制限時
        if (form.getRng020rtpSid() > 0
         && !biz.isUseableTemplate(form.getRng020rtpSid())) {
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");
            return getCatchExceptionPage(map, req,
                    new RtpNotfoundException(), okForward,
                    cmn999form);
        }

        //申請不可能な経路があるか
        ActionErrors errors;
        errors = biz.chkRTKuseable(form.getRng020keiroMap(), RngConst.RNG_RNCTYPE_APPR);
        if (errors.size() > 0) {
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");
            return getCatchExceptionPage(map, req,
                    new RngUnuseableKeiroException(errors, getResources(req)),
                    okForward,
                    cmn999form);
        }
        errors = biz.chkRTKuseable(form.getRng020kakuninKeiroMap(), RngConst.RNG_RNCTYPE_CONFIRM);
        if (errors.size() > 0) {
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");
            return getCatchExceptionPage(map, req,
                    new RngUnuseableKeiroException(errors, getResources(req)),
                    okForward,
                    cmn999form);
        }

        if (form.getRtpVerUpdated() > 0) {
            errors = new ActionErrors();
            ActionMessage msg = null;
            GsMessage gsmgs = new GsMessage(req);

            String errMsgKey  = "error.open.update.template"; // 稟議テンプレートバージョン違い
            String modeMsgKey = "cmn.draft";  // 草稿

            if (form.getRtpVerUpdated() == 2) {
                errMsgKey  = "error.open.update.keiro"; // 経路テンプレートバージョン違い
            }
            if (form.isRng020copyApply()) {
                modeMsgKey = "rng.rng020.09"; // 複写
            }
            msg = new ActionMessage(errMsgKey, gsmgs.getMessage(modeMsgKey));

            StrutsUtil.addMessage(errors, msg, errMsgKey);
            addErrors(req, errors);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws RtpNotfoundException テンプレート削除済み例外
     * @throws Exception 実行時例外
     */
    public ActionForward __doDsp(ActionMapping map, Rng020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Rng020ParamModel paramMdl = new Rng020ParamModel();
        paramMdl.setParam(form);
        Rng020Biz biz = new Rng020Biz(con, getRequestModel(req));
        RngBiz rngBiz = new RngBiz(con);
        GSTemporaryPathModel tempDir = _getRingiDir(req);
        biz.setRedspData(paramMdl, tempDir);

        biz.setDspData(paramMdl, getAppRootPath(),
                tempDir, getSessionUserModel(req));
        paramMdl.setFormData(form);

        RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
        // テンプレート使用制限チェック
        boolean templateChk = biz.isAcceptTemplate(aconfMdl,
                form.getRng020rtpSid(), form.getRng020rtpType());
        if (!templateChk) {
            // 草稿判定
            boolean isLoaded = false;
            if (form.getRngSid() >= 0 && !form.isRng020copyApply()) {
                isLoaded = true;
            }
            if (!isLoaded) {
                ActionForward okForward = __getForwardBack(form, map);
                Cmn999Form cmn999form = new Cmn999Form();
                cmn999form.addHiddenAll(form, form.getClass(), "");
                return getCatchExceptionPage(map, req,
                        new RngNotAcceptTemplateException(),
                        okForward,
                        cmn999form);
            }
            form.setUseTemplateFlg(RngConst.RTP_LIMIT_TEMPLATE_NO);
            form.setRng020ButtonDsp(false);
        }
        //テンプレートへのアクセス制限時
        if (form.getRng020rtpSid() > 0
         && !biz.isUseableTemplate(form.getRng020rtpSid())) {
            ActionForward okForward = __getForwardBack(form, map);
            Cmn999Form cmn999form = new Cmn999Form();
            cmn999form.addHiddenAll(form, form.getClass(), "");
            return getCatchExceptionPage(map, req,
                    new RtpNotfoundException(), okForward,
                    cmn999form);
        }

        if (form.getRtpVerUpdated() > 0) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = null;
            GsMessage gsmgs = new GsMessage(req);

            String errMsgKey  = "error.open.update.template"; // 稟議テンプレートバージョン違い
            String modeMsgKey = "rng.rng020.13";  // 入力中

            if (form.getRtpVerUpdated() == 2) {
                errMsgKey  = "error.open.update.keiro"; // 経路テンプレートバージョン違い
            }
            msg = new ActionMessage(errMsgKey, gsmgs.getMessage(modeMsgKey));

            StrutsUtil.addMessage(errors, msg, errMsgKey);
            addErrors(req, errors);
        }


        // トランザクショントークン設定
        saveToken(req);
        con.setAutoCommit(false);
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
        Rng020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = _getRingiDir(req);
        log__.debug("テンポラリディレクトリ = " + tempDir);

        //選択された添付ファイルを削除する
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.deleteFile(form.getRng020files(), tempDir);

        return __doDsp(map, form, req, res, con);
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
        Rng020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int mode) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //入力チェック前に描画設定でテンプレートの読み込み、自動計算要素の再計算を行う必要がある
        ActionForward forward =  __doDsp(map, form, req, res, con);
        //テンプレートバージョンアップ時
        if (form.getRtpVerUpdated() > 0) {
            return forward;
        }
        //使用経路の自動変更時
        if (form.getKeiroAutoChanged() == 1) {
            return forward;
        }

        RngBiz rngBiz = new RngBiz(con);
        RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
        RequestModel reqMdl = getRequestModel(req);
        Rng020Biz biz = new Rng020Biz(con, reqMdl, getSessionUserSid(req));

        // テンプレート使用制限チェック
        boolean templateChk = biz.isAcceptTemplate(aconfMdl,
                form.getRng020rtpSid(), form.getRng020rtpType());
        if (!templateChk) {
            // 草稿判定
                ActionForward okForward = __getForwardBack(form, map);
                Cmn999Form cmn999form = new Cmn999Form();
                cmn999form.addHiddenAll(form, form.getClass(), "");
                return getCatchExceptionPage(map, req,
                        new RngNotAcceptTemplateException(),
                        okForward,
                        cmn999form);
        }

        //入力チェックを行う
        ActionErrors errors = null;
        if (mode == 0) {
            errors = form.validateCheck(Rng020Form.CHECKTYPE_REQUEST,
                    getRequestModel(req), con);
        } else if (mode == 1) {
            errors = form.validateCheck(Rng020Form.CHECKTYPE_DRAFT,
                    getRequestModel(req), con);
        }
        if ((errors != null && !errors.isEmpty())) {
            addErrors(req, errors);
            return forward;
        }

        //新規作成の場合、確認画面へ遷移する。
        if (mode == 0) {
            req.setAttribute("rng020knForm", form);
            return map.findForward("rng020kn");
        }

        boolean commit = false;
        PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        try {
            Rng020ParamModel paramMdl = new Rng020ParamModel();
            paramMdl.setParam(form);
            RingiRequestModel model = biz.entryRingiData(paramMdl, getCountMtController(req),
                            getAppRootPath(), _getRingiDir(req).getTempPath(), mode,
                            getPluginConfig(req),
                            smailPluginUseFlg,
                            getRequestModel(req));
            paramMdl.setFormData(form);

            forward = __setCompPageParam(map, req, form);

            GsMessage gsMsg = new GsMessage(reqMdl);

            //ログ出力処理
            String opCode = gsMsg.getMessage("cmn.save.draft3"); //gsMsg.getMessage("cmn.entry");
            String msg = biz.outputRingiData(model, paramMdl, reqMdl, con);

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
        Rng020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        Rng020Biz biz = new Rng020Biz(con, reqMdl);
        RngBiz rngBiz = new RngBiz(con);

        Long binSid = Long.valueOf(NullDefault.getLong(form.getRng020TemplateFileId(), -1));
        int  rtpSid = form.getRng020rtpSid();

        // 添付ファイルがダウンロード可能かチェックする
        if (biz.isCheckDLSampleTemp(rtpSid, binSid)) {
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                    GroupSession.getResourceManager().getDomain(req));
            if (cbMdl != null) {
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                String msg = gsMsg.getMessage("cmn.download");
                //ログ出力処理
                StringBuilder sb = new StringBuilder();
                // ファイル名
                sb.append("[" + gsMsg.getMessage("cmn.file.name") + "] ");
                sb.append(cbMdl.getBinFileName());
                sb.append(System.getProperty("line.separator"));
                // 使用テンプレート
                RngTemplateDao rngTempDao = new RngTemplateDao(con);
                RngTemplateModel rngTempModel = rngTempDao.select(rtpSid);
                if (rngTempModel == null) {
                    rngTempModel = new RngTemplateModel();
                }
                sb.append("[" + gsMsg.getMessage("cmn.template") + "] ");
                sb.append(rngTempModel.getRtpTitle());
                rngBiz.outPutLog(
                        map,
                        msg, GSConstLog.LEVEL_INFO, sb.toString(),
                        getRequestModel(req));
                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);
                //ファイルをダウンロードする
                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(),
                        Encoding.UTF_8);
            }
        } else {
            return getSubmitErrorPage(map, req);
        }
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
        Rng020Form form) {

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
        if (form.getRngCmdMode() == RngConst.RNG_CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getRngCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
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
        cmn999Form.addHiddenParam("svRng130Status", form.getSvRng130Status());
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

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}
