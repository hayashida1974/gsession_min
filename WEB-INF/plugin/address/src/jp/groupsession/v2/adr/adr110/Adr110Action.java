package jp.groupsession.v2.adr.adr110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.AbstractAddressSubAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr111.Adr111Form;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110Action extends AbstractAddressSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr110Action.class);

    /** 処理種別 確認 */
    private static final int MSGTYPE_CONFIRM__ = 1;
    /** 処理種別 完了 */
    private static final int MSGTYPE_COMPLETE__ = 2;

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

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        Adr110Form thisForm = (Adr110Form) form;
        
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        
        if (cmd.equals("confirmCompany")) {
            //OKボタンクリック
            forward = __doConfirm(map, thisForm, req, res, con);

        } else if (cmd.equals("backCompanyList")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals("backInputAddress")) {
            //戻るボタンクリック
            forward = map.findForward("inputAddress");

        } else if (cmd.equals("deleteCompany")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteCompanyComplete")) {
            //削除確認画面からの遷移
            forward = __doDeleteComplete(map, thisForm, req, res, con);

        } else if (cmd.equals("addCompanyBase")) {
            //追加(会社拠点)ボタンクリック
            forward = __doAddCompanyBase(map, thisForm, req, res, con);

        } else if (cmd.equals("editCompanyBase")) {
            //編集(会社拠点)ボタンクリック
            forward = map.findForward("inputCompanyBase");

        } else if (cmd.equals("adr111commit")) {
            //会社拠点編集画面からの遷移
            forward = __doEditBaseComplete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteCompanyBase")) {
            //削除(会社拠点)ボタンクリック
            forward = __doDeleteCoBase(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteCompanyBaseComplete")) {
            //削除(会社拠点)確認画面からの遷移
            forward = __doDeleteCoBaseComplete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteCompanyBaseCommit")) {
            //削除(会社拠点)実行後の画面遷移
            forward = __doDispBaseComplete(map, thisForm, req, res, con);
        } else {
            //初期表示
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
        Adr110Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        ActionForward forward = null;


        //初期表示情報を取得する
        RequestModel reqMdl = getRequestModel(req);
        Adr110Biz biz = new Adr110Biz(reqMdl);

        Adr110ParamModel paramMdl = new Adr110ParamModel();
        paramMdl.setParam(form);
        
        //処理モード = 編集 かつ 初期表示の場合は会社情報の有無を確認する
        if (paramMdl.getAdr110ProcMode() == GSConstAddress.PROCMODE_EDIT
        && paramMdl.getAdr110initFlg() == 0) {
            if (biz.canViewCompanyData(con, paramMdl) != 0) {
                forward = __doNoneDataError(map, form, req, res, con);
            }
        }
        if (forward == null) {
            
            biz.getInitData(con, paramMdl);
            paramMdl.setFormData(form);

            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
            form.setAdr110searchUse(CommonBiz.getWebSearchUse(pconfig));
            forward = map.getInputForward();
        }
        
        con.setAutoCommit(false);
        
        saveToken(req);
        
        return forward;
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doBack(
        ActionMapping map,
        Adr110Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        String backId = NullDefault.getString(form.getAdr110BackId(), "");

        if (StringUtil.isNullZeroStringSpace(backId)) {
            forward = map.findForward("companyList");
        } else {
            if (res == null) {
                forward = map.findForward("adrList");
                if (form.getAdr330back() == 1) {
                    forward = map.findForward("adrAdmList");
                }

            } else if (backId.equals("adr110kn")) {
                forward = map.findForward("backadr110kn");
            }
        }
        return forward;
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
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
    private ActionForward __doConfirm(ActionMapping map,
                                    Adr110Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(con, req);
        ActionErrors baseErrors = form.validateCheckBase(con, req);
        con.setAutoCommit(false);
        if (!errors.isEmpty() || !baseErrors.isEmpty()) {
            addErrors(req, errors);
            addErrors(req, baseErrors);
            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;

        boolean commit = false;
        try {
            Adr110Biz biz = new Adr110Biz(getRequestModel(req));

            Adr110ParamModel paramMdl = new Adr110ParamModel();
            paramMdl.setParam(form);

            biz.entryCompanyData(con, paramMdl, getCountMtController(req),
                    getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);

            forward = __setCompPageParam(map, req, form);
            con.commit();
            commit = true;

        } catch (Exception e) {
            log__.error("会社情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        String opCode = "";
        GsMessage gsMsg = new GsMessage();
        if (form.getAdr110ProcMode() == GSConstAddress.PROCMODE_ADD) {
            opCode = gsMsg.getMessage(req, "cmn.entry");

        } else if (form.getAdr110ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            opCode = gsMsg.getMessage(req, "cmn.change");
        }

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        adrBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + form.getAdr110coName());

        return forward;
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
        Adr110Form form) {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        if (form.getAdr020webmail() == 1) {
            //WEBメールからの呼び出し
            cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        } else {
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        }

        urlForward = map.findForward("companyList");
        if (form.getAdr100backFlg() == 1) {
            urlForward = map.findForward("inputAddress");
        } else if (form.getAdr100backFlg() == 3) {
            urlForward = map.findForward("adrList");
            if (form.getAdr330back() == 1) {
                urlForward = map.findForward("adrAdmList");
            }
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getAdr110ProcMode() == GSConstAddress.PROCMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, gsMsg.getMessage(req, "address.118")));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("adr100mode", form.getAdr100mode());
        //詳細検索の場合、入力項目記憶
        if (form.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) {
            cmn999Form.addHiddenParam("adr100code", form.getAdr100code());
            cmn999Form.addHiddenParam("adr100atiSid", form.getAdr100atiSid());
            cmn999Form.addHiddenParam("adr100coName", form.getAdr100coName());
            cmn999Form.addHiddenParam("adr100tdfk", form.getAdr100tdfk());
            cmn999Form.addHiddenParam("adr100coNameKn", form.getAdr100coNameKn());
            cmn999Form.addHiddenParam("adr100coBaseName", form.getAdr100coBaseName());
            cmn999Form.addHiddenParam("adr100biko", form.getAdr100biko());

        } else if (form.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_50) {
            cmn999Form.addHiddenParam("adr100SearchKana", form.getAdr100SearchKana());
        }

        //パラメータ設定
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 追加(会社拠点)ボタンクリック時処理
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
    private ActionForward __doAddCompanyBase(ActionMapping map,
                                            Adr110Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con) throws Exception {

        //入力チェック
        Adr110BaseForm baseForm = new Adr110BaseForm();
        baseForm.setAdr110abaTypeDetail(form.getAdr110abaType());
        baseForm.setAdr110abaName(form.getAdr110abaName());
        baseForm.setAdr110abaPostno1(form.getAdr110abaPostno1());
        baseForm.setAdr110abaPostno2(form.getAdr110abaPostno2());
        baseForm.setAdr110abaTdfk(form.getAdr110abaTdfk());
        baseForm.setAdr110abaAddress1(form.getAdr110abaAddress1());
        baseForm.setAdr110abaAddress2(form.getAdr110abaAddress2());
        baseForm.setAdr110abaBiko(form.getAdr110abaBiko());

        ActionErrors errors = baseForm.validateCheck(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            List<Adr110BaseForm> abaList = form.getAbaListToList();
            if (abaList == null) {
                abaList = new ArrayList<Adr110BaseForm>();
            }
            baseForm.setAdr110abaIndex(abaList.size() + 1);
            abaList.add(baseForm);
            form.setAbaListForm(abaList);
            form.resetAbaParam();
        }

        return __doInit(map, form, req, res, con);
    }
    
    /**
     * <br>[機  能] 削除ボタンクリック時処理
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
    private ActionForward __doDelete(ActionMapping map,
                                   Adr110Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws Exception {


        return __doDeleteMessage(map, form, req, con, MSGTYPE_CONFIRM__);
    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下時処理
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
    private ActionForward __doDeleteComplete(ActionMapping map,
                                           Adr110Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con) throws Exception {

        Adr110Biz biz = new Adr110Biz(getRequestModel(req));

        Adr110ParamModel paramMdl = new Adr110ParamModel();
        paramMdl.setParam(form);

        if (biz.canViewCompanyData(con, paramMdl) != 0) {
            return __doNoneDataError(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            //削除処理実行
            paramMdl.setParam(form);
            biz.deleteCompany(con, paramMdl, getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);

            //ログ出力処理
            AdrCommonBiz adrBiz = new AdrCommonBiz(con);
            GsMessage gsMsg = new GsMessage();
            String opCode = gsMsg.getMessage(req, "cmn.delete");

            adrBiz.outPutLog(
                    map, req, res, opCode,
                    GSConstLog.LEVEL_TRACE,
                    "[name]" + form.getAdr110coName());

            con.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("会社情報の削除に失敗", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        return __doDeleteMessage(map, form, req, con, MSGTYPE_COMPLETE__);
    }
    
    /**
     * <br>[機  能] 拠点編集画面からの遷移時処理
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
    private ActionForward __doEditBaseComplete(ActionMapping map,
                                            Adr110Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con) throws Exception {

        ActionForward forward = __doInit(map, form, req, res, con);
        Adr111Form adr111Form = (Adr111Form) req.getAttribute("adr111Form");
        //拠点情報を取得できなければ処理を終了する
        if (adr111Form == null) {
            return forward;
        }
        
        //入力チェック
        Adr110BaseForm baseForm = new Adr110BaseForm();
        baseForm.setAdr110abaTypeDetail(adr111Form.getAdr111abaType());
        baseForm.setAdr110abaName(adr111Form.getAdr111abaName());
        baseForm.setAdr110abaPostno1(adr111Form.getAdr111abaPostno1());
        baseForm.setAdr110abaPostno2(adr111Form.getAdr111abaPostno2());
        baseForm.setAdr110abaTdfk(adr111Form.getAdr111abaTdfk());
        baseForm.setAdr110abaAddress1(adr111Form.getAdr111abaAddress1());
        baseForm.setAdr110abaAddress2(adr111Form.getAdr111abaAddress2());
        baseForm.setAdr110abaBiko(adr111Form.getAdr111abaBiko());

        ActionErrors errors = baseForm.validateCheck(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return forward;
        }
        List<Adr110BaseForm> abaList = adr111Form.getAbaListToList();
        if (abaList == null) {
            abaList = new ArrayList<Adr110BaseForm>();
        }
        form.setAbaListForm(abaList);
        
        return forward;
    }
    
    /**
     * <br>[機  能] 削除確認画面からの遷移時処理
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
    private ActionForward __doDispBaseComplete(ActionMapping map,
                                            Adr110Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con) throws Exception {

        ActionForward forward = __doInit(map, form, req, res, con);
        __doDeleteCoBaseComplete(map, form, req, res, con);
        
        return forward;
    }

    /**
     * <br>[機  能] 削除ボタン(会社拠点)クリック時処理
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
    private ActionForward __doDeleteCoBase(ActionMapping map,
                                   Adr110Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws Exception {

        Adr110Biz biz = new Adr110Biz(getRequestModel(req));

        Adr110ParamModel paramMdl = new Adr110ParamModel();
        paramMdl.setParam(form);
        String coBaseName = biz.getCoBaseName(paramMdl, form.getAdr110deleteCompanyBaseIndex());
        paramMdl.setFormData(form);

        return __doDeleteCoBaseMessage(map, form, req, con, MSGTYPE_CONFIRM__, coBaseName);
    }

    /**
     * <br>[機  能] 削除(会社拠点)確認画面でOKボタン押下時処理
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
    private ActionForward __doDeleteCoBaseComplete(ActionMapping map,
                                           Adr110Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con) throws Exception {

        Adr110Biz biz = new Adr110Biz(getRequestModel(req));


        Adr110ParamModel paramMdl = new Adr110ParamModel();
        paramMdl.setParam(form);
        String coBaseName = biz.getCoBaseName(paramMdl, form.getAdr110deleteCompanyBaseIndex());

        //削除処理実行
        biz.deleteCompanyBase(paramMdl);
        paramMdl.setFormData(form);

        return __doDeleteCoBaseMessage(map, form, req, con, MSGTYPE_COMPLETE__, coBaseName);
    }

    /**
     * <br>[機  能] 削除時の共通メッセージ画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @param msgType メッセージ種別
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteMessage(ActionMapping map,
                                          Adr110Form form,
                                          HttpServletRequest req,
                                          Connection con, int msgType) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();

        if (msgType == MSGTYPE_CONFIRM__) {
            cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
            cmn999Form.setMessage(
                 msgRes.getMessage("sakujo.kakunin.once", gsMsg.getMessage(req, "address.118")));
            cmn999Form.setUrlOK(map.findForward("inputCompany").getPath()
                                + "?CMD=deleteCompanyComplete");
            cmn999Form.setUrlCancel(map.findForward("inputCompany").getPath());
        } else {
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "address.118")));
           cmn999Form.setUrlOK(__doBack(map, form, req, null, con).getPath());
        }

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        saveToken(req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 会社拠点情報削除時の共通メッセージ画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @param msgType メッセージ種別
     * @param coBaseName 支店・営業所名
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteCoBaseMessage(ActionMapping map,
                                                  Adr110Form form,
                                                  HttpServletRequest req,
                                                  Connection con,
                                                  int msgType,
                                                  String coBaseName) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //拠点情報
        String msgKyotenInfo = gsMsg.getMessage(req, "address.src.28");
        coBaseName = StringUtilHtml.transToHTmlPlusAmparsant(coBaseName);
        
        if (msgType == MSGTYPE_CONFIRM__) {
            cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
            cmn999Form.setMessage(
                 msgRes.getMessage("sakujo.kakunin.once",
                         msgKyotenInfo + " : " + coBaseName));
            cmn999Form.setUrlOK(map.findForward("inputCompany").getPath()
                                + "?CMD=deleteCompanyBaseComplete");
            cmn999Form.setUrlCancel(map.findForward("inputCompany").getPath());
        } else {
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                         msgKyotenInfo + " : " + coBaseName));
           cmn999Form.setUrlOK(
                   map.findForward("inputCompany").getPath() + "?CMD=deleteCompanyBaseCommit");
        }

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward checkPow(ActionMapping map,
                                   HttpServletRequest req,
                                   Connection con) throws Exception {

        //ユーザ情報を取得
        HttpSession session = req.getSession(false);
        BaseUserModel usModel = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        //GS管理者権限を取得
        CommonBiz cmnBiz = new CommonBiz();
        boolean gsAdmFlg = cmnBiz.isPluginAdmin(con, usModel, GSConstAddress.PLUGIN_ID_ADDRESS);

        //業種編集権限を取得
        AdrAconfDao dao = new AdrAconfDao(con);
        AdrAconfModel model = dao.selectAconf();

        if (!gsAdmFlg && (model != null && model.getAacAcoEdit() == GSConstAddress.POW_LIMIT)) {
            return map.findForward("gf_power");
        }

        return null;
    }
    /**
     * <br>編集対象が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneDataError(ActionMapping map, Adr110Form form,
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
        if (form.getAdr330back() == 1) {
            urlForward = map.findForward("adrAdmList");
        }

        form.setHiddenParam(cmn999Form);

        //アドレス情報
        String textSchedule = gsMsg.getMessage(req, "address.118");

        //変更
        String textChange = gsMsg.getMessage(req, "cmn.change");
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        if (cmd.equals("deleteCompanyComplete")) {
            textChange = gsMsg.getMessage(req, "cmn.delete");
        }
        if (cmd.equals("deleteCompany")) {
            textChange = gsMsg.getMessage(req, "cmn.delete");
        }

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                textSchedule, textChange));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}