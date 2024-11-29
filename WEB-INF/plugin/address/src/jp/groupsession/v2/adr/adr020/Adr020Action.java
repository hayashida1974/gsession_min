package jp.groupsession.v2.adr.adr020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr020Action extends AbstractAddressSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr020Action.class);

    /** 処理種別 確認 */
    private static final int MSGTYPE_CONFIRM__ = 1;
    /** 処理種別 完了 */
    private static final int MSGTYPE_COMPLETE__ = 2;

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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        Adr020Form thisForm = (Adr020Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("CMD = " + cmd);

        ActionForward forward = null;
        if (cmd.equals("registConfirm")) {
            //OKボタンクリック
            forward = __doConfirm(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteComplete")) {
            //削除確認画面からの遷移
            forward = __doDeleteComplete(map, thisForm, req, res, con);

        } else if (cmd.equals("backAdrList")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals("addCompany") || cmd.equals("editCompany")) {
            //追加(会社)ボタンクリック
            forward = map.findForward("inputCompany");

        } else if (cmd.equals("selectedCompany")) {
            //会社選択
            forward = __doSelectCompany(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteLabel")) {
            //削除(ラベル)ボタンクリック
            forward = __doDelLabel(map, thisForm, req, res, con);

        } else {
            forward = __doInit(map, thisForm, req, res, con);
        }

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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Adr020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Adr020Biz biz = new Adr020Biz(reqMdl);

        Adr020ParamModel paramMdl = new Adr020ParamModel();
        paramMdl.setParam(form);
        ActionForward forward = null;
        AddressBiz addressBiz = new AddressBiz(reqMdl);
        if (!addressBiz.isAbleAdd(con, reqMdl.getSmodel().getUsrsid())) {
            form.setAdr020viewFlg(1);
            Adr020Form adr020Form = new Adr020Form();
            BeanUtils.copyProperties(adr020Form, form);
            req.setAttribute("adr020Form", adr020Form);
            forward = map.findForward("confirmAddress");
            return forward;
        }

        if ((paramMdl.getAdr020ProcMode() == GSConstAddress.PROCMODE_ADD
        && paramMdl.getAdrCopyFlg() == 1
        ) || paramMdl.getAdr020ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            if (!addressBiz.isViewAddressData(con,
                    form.getAdr010EditAdrSid(),
                    reqMdl.getSmodel().getUsrsid())) {
                return __doNoneDataError(map, form, req, res, con);
            }
        }

        if (forward == null) {
            biz.setInitData(paramMdl, con, getSessionUserModel(req));

            paramMdl.setFormData(form);

            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
            form.setAdr020searchUse(CommonBiz.getWebSearchUse(pconfig));
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
        Adr020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        String backId = NullDefault.getString(form.getAdr020BackId(), "");

        if (StringUtil.isNullZeroStringSpace(backId)) {
            forward = map.findForward("adrList");
            if (form.getAdr330back() == 1) {
                forward = map.findForward("adrAdmList");
            }
        } else if (backId.equals("adr160")) {
            forward = map.findForward("backContactHistory");
        }

        return forward;
    }

    /**
     * <br>[機  能] 削除(ラベル)ボタン押下時処理を行う
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
    private ActionForward __doDelLabel(
        ActionMapping map,
        Adr020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        form.setAdr020label(
                cmnBiz.getDeleteMember(new String[] {form.getAdr020deleteLabel()},
                                    form.getAdr020label()));

        return  __doInit(map, form, req, res, con);
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
                                    Adr020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        boolean commit = false;
        con.setAutoCommit(true);

        AdrAddressModel addressMdl = new AdrAddressModel();

        try {
            Adr020Biz biz = new Adr020Biz(getRequestModel(req));
            Adr020ParamModel paramMdl = new Adr020ParamModel();

            paramMdl.setParam(form);
            addressMdl = biz.entryAddressData(con, paramMdl, getCountMtController(req),
                    getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);


            forward = __setCompPageParam(map, req, form);
            con.commit();
            commit = true;

        } catch (Exception e) {
            log__.error("アドレス情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        String opCode = "";
        GsMessage gsMsg = new GsMessage();
        if (form.getAdr020ProcMode() == GSConstAddress.PROCMODE_ADD) {
            opCode = gsMsg.getMessage(req, "cmn.entry");

        } else if (form.getAdr020ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            opCode = gsMsg.getMessage(req, "cmn.change");
        }

        adrBiz.outPutLog(
                map, req, res,
                opCode,
                GSConstLog.LEVEL_TRACE,
                "[" + gsMsg.getMessage(req, "cmn.name") + "]"
                + addressMdl.getAdrSei() + " " + addressMdl.getAdrMei());

        return forward;
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
                                   Adr020Form form,
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
                                           Adr020Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con) throws Exception {


        RequestModel reqMdl = getRequestModel(req);
        AddressBiz adrBiz = new AddressBiz(reqMdl);
        if (!adrBiz.isEditAddressData(con,
                form.getAdr010EditAdrSid(),
                reqMdl.getSmodel().getUsrsid())) {
            return __doNoneDataError(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }


        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            Adr020Biz biz = new Adr020Biz(getRequestModel(req));

            //削除処理実行
            Adr020ParamModel paramMdl = new Adr020ParamModel();
            paramMdl.setParam(form);

            biz.deleteAddress(con, paramMdl, getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);

            con.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("アドレス情報の削除に失敗", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        AdrCommonBiz adrCmnBiz = new AdrCommonBiz(con);
        String opCode = gsMsg.getMessage(req, "cmn.delete");

        adrCmnBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + form.getAdr020unameSei() + " " + form.getAdr020unameMei());

        return __doDeleteMessage(map, form, req, con, MSGTYPE_COMPLETE__);
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
                                          Adr020Form form,
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
            String msg = gsMsg.getMessage(req, "cmn.address.2")
                    + "　"
                    + StringUtilHtml.transToHTmlPlusAmparsant(form.getAdr020unameSei()) + " "
                    + StringUtilHtml.transToHTmlPlusAmparsant(form.getAdr020unameMei());
            cmn999Form.setMessage(
                 msgRes.getMessage("sakujo.kakunin.once", msg));
            cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=deleteComplete");
            cmn999Form.setUrlCancel(map.findForward("mine").getPath());
        } else {
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "cmn.address.2")));
            ActionForward urlForward = map.findForward("adrList");
            if (form.getAdr330back() == 1) {
                urlForward = map.findForward("adrAdmList");
            }
           cmn999Form.setUrlOK(urlForward.getPath());
        }

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        saveToken(req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 会社を選択処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doSelectCompany(ActionMapping map,
                                            Adr020Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
    throws Exception {

        String[] companySids = form.getAdr020CompanySid();
        String companySid = null;
        if (companySids != null && companySids.length > 0) {
            for (String cmpSid : companySids) {
                companySid = cmpSid;
            }
            form.setAdr020selectCompany(companySid);
        }

        String[] companyBaseSids = form.getAdr020CompanyBaseSid();
        String companyBaseSid = null;
        if (companyBaseSids != null && companyBaseSids.length > 0) {
            for (String cmpBaseSid : companyBaseSids) {
                companyBaseSid = cmpBaseSid;
            }
            form.setAdr020selectCompanyBase(companyBaseSid);
        }

        return __doInit(map, form, req, res, con);
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
    private ActionForward __doNoneDataError(ActionMapping map, Adr020Form form,
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
        //変更
        String textChange = gsMsg.getMessage(req, "cmn.change");
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        if (cmd.equals("deleteComplete")) {
            textChange = gsMsg.getMessage(req, "cmn.delete");
        }
        if (cmd.equals("delete")) {
            textChange = gsMsg.getMessage(req, "cmn.delete");
        }


        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                textSchedule, textChange));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
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
     * @throws Exception 実行事例外
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Adr020Form form) throws Exception {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = null;

        if (form.getAdr020webmail() == 1) {
            //WEBメールからの呼び出し
            cmn999Form = new Cmn999Form("javascript:window.parent.webmailEntrySubWindowClose();");
            cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
            cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
        } else {
            cmn999Form = new Cmn999Form();
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            cmn999Form.setUrlOK(__doBack(map, form, req, null, null).getPath());
        }
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        //メッセージセット
        String msgState = null;
        if (form.getAdr020ProcMode() == GSConstAddress.PROCMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, gsMsg.getMessage(req, "cmn.address.2")));
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}