package jp.groupsession.v2.adr.adr330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.AbstractAddressSubAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.csv.Adr010CsvWriter;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.adr020kn.Adr020knForm;
import jp.groupsession.v2.adr.adr330.model.Adr330ViewSearchModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr330Action extends AbstractAddressSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr330Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "adr330";

    /**
     * <p>キャッシュを有効にして良いか判定を行う
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {
        return NullDefault.getString(req.getParameter(GSConst.P_CMD), "").equals("export");
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


        Adr330Form thisForm = (Adr330Form) form;
        ActionForward forward = null;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("CMD = " + cmd);

        if (cmd.equals("editAdrData")) {
            //アドレス帳名称クリック

            AddressBiz addressBiz = new AddressBiz(getRequestModel(req));
            boolean editFlg =
                addressBiz.isEditAddressData(con, thisForm.getAdr010EditAdrSid(),
                                            getSessionUserModel(req).getUsrsid());

            //システム管理者及びプラグイン管理者はアドレス編集画面へ遷移
            BaseUserModel usModel = getSessionUserModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            boolean adrAdminFlg
                    = cmnBiz.isPluginAdmin(con, usModel, GSConstAddress.PLUGIN_ID_ADDRESS);

            if (editFlg || adrAdminFlg) {
                forward = map.findForward("registAddress");
            } else {
                thisForm.setAdr020viewFlg(1);
                Adr020knForm adr020knForm = new Adr020knForm();
                BeanUtils.copyProperties(adr020knForm, thisForm);
                req.setAttribute("adr020knForm", adr020knForm);
                forward = map.findForward("viewAddress");
            }
        } else if (cmd.equals("adr330back")) {
            //戻る
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals("viewCompany")) {
            //会社名称クリック
            forward = map.findForward("inputCompanyConfirm");

        } else if (cmd.equals("export")) {
            //エクスポートボタンクリック
            forward = __doExport(map, thisForm, req, res, con);

        } else if (cmd.equals("search")) {
            //検索ボタンクリック
            forward = __doSearchBtn(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            Adr330ViewSearchModel smdl = thisForm.getAdr330searchSVBean();
            smdl.setPage(smdl.getPage() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            Adr330ViewSearchModel smdl = thisForm.getAdr330searchSVBean();
            smdl.setPage(smdl.getPage() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("grpChange")) {
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("chgPrjDspKbn")) {
            thisForm.setAdr010searchFlg(0);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("adrDelete")) {
            //削除ボタンクリック
            forward = __doDeleteConfirmation(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            //削除OKボタンクリック
            forward = __doDeleteOk(map, thisForm, req, res, con);

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
                                    Adr330Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Adr330Biz biz = new Adr330Biz(getRequestModel(req));

        Adr330ParamModel paramMdl = new Adr330ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getSessionUserModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
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
    private ActionForward __doSearch(ActionMapping map,
                                    Adr330Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {


        //入力チェック
        ActionErrors errors = form.validateCheck(con, req);
        GsMessage gsMsg = new GsMessage();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {

            //検索結果件数の確認を行う

            //検索結果件数の確認
            Adr330Biz biz = new Adr330Biz(getRequestModel(req));
            Adr330ViewSearchModel vsearchMdl = form.getAdr330searchBean();
            Adr010SearchModel searchMdl = vsearchMdl.createSearchModel(
                    getSessionUserModel(req).getUsrsid());
            con.setAutoCommit(true);
            try {

                String adrMsg = "";
                //アドレス情報
                adrMsg = gsMsg.getMessage(req, "address.src.2");
                if (biz.getSearchCount(con, searchMdl) <= 0) {
                    ActionMessage msg = new ActionMessage("search.data.notfound", adrMsg);
                    StrutsUtil.addMessage(errors, msg, "addressSearch");
                    addErrors(req, errors);
//                    return __doInit(map, form, req, res, con);
                }
            } finally {
                con.setAutoCommit(false);
            }

            form.setAdr330searchSVBean(vsearchMdl);
            form.setAdr330searchFlg(1);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
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
    private ActionForward __doSearchBtn(ActionMapping map,
                                    Adr330Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        return __doSearch(map, form, req, res, con);
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
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(ActionMapping map, Adr330Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        GsMessage gsMsg = new GsMessage();
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);
        String fileName = Adr010CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        con.setAutoCommit(true);
        Adr010CsvWriter csvWriter = new Adr010CsvWriter();

        Adr330ViewSearchModel vsearchMdl = form.getAdr330searchSVBean();
        Adr010SearchModel searchMdl = vsearchMdl.createSearchModel(
                getSessionUserModel(req).getUsrsid());

        Adr330ParamModel paramMdl = new Adr330ParamModel();
        paramMdl.setParam(form);
        csvWriter.setSearchModel(searchMdl);
        paramMdl.setFormData(form);

        csvWriter.outputCsv(con, tempDir, getRequestModel(req));
        con.setAutoCommit(false);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        adrBiz.outPutLog(
                map, req, res,
                gsMsg.getMessage(req, "cmn.export"), GSConstLog.LEVEL_INFO, fileName);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        return null;
    }
    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Adr330Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("adr330back");
        return forward;
    }

    /**
     * <br>[機  能] 削除確認処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doDeleteConfirmation(ActionMapping map,
                                                  Adr330Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        ActionForward forward = null;

        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheck010(req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }



            AdrAddressDao dao = new AdrAddressDao(con);
            //削除対象のアドレス一覧を取得する
            Adr330ParamModel paramMdl = new Adr330ParamModel();
            paramMdl.setParam(form);
            ArrayList<AdrAddressModel> delList =
                dao.selAdrList(paramMdl.getAdr330selectSid());
            if (delList.size() < paramMdl.getAdr330selectSid().length) {
                //すでに削除されている場合
                GsMessage gsMsg = new GsMessage();
                ActionMessage msg = null;

                //アドレス情報
                String textAddress = gsMsg.getMessage(req, "address.src.2");
                //変更
                String textDel = gsMsg.getMessage(req, "cmn.delete");

                msg = new ActionMessage(
                        "error.edit.power.notfound", textAddress, textDel);

                StrutsUtil.addMessage(errors, msg, "adrSid");
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);

            }

            paramMdl.setFormData(form);

            //削除確認画面を設定
            forward = __setConfirmationDsp(map, req, form, delList);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return forward;
    }

    /**
     * <br>[機  能] 確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param delList 削除対象リスト
     * @return ActionForward フォワード
     */
    private ActionForward __setConfirmationDsp(ActionMapping map,
                                                HttpServletRequest req,
                                                Adr330Form form,
                                                ArrayList<AdrAddressModel> delList) {

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String delMsg = "";

       /************************************************************************
        *
        * 確認画面に表示するアドレスを作成する
        *
        ************************************************************************/

        if (!delList.isEmpty()) {

            for (int i = 0; i < delList.size(); i++) {

                AdrAddressModel ret = (AdrAddressModel) delList.get(i);

                delMsg += "・";
                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(ret.getAdrSei(), ""));
                delMsg += "&nbsp;";
                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(ret.getAdrMei(), ""));

                //最後の要素以外は改行を挿入
                if (i < delList.size() - 1) {
                    delMsg += "<br>";
                }

            }
        }

       /************************************************************************
        *
        * 確認画面設定
        *
        ************************************************************************/

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = map.findForward("redraw");

        //OKボタンクリック遷移先
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");

        cmn999Form.setMessage(
                msgRes.getMessage(
                        "sakujo.kakunin.list", gsMsg.getMessage(req, "address.src.2"), delMsg));

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //hiddenパラメータ
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteOk(ActionMapping map,
                                        Adr330Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        //削除対象選択チェック
        ActionErrors errors =
            form.validateSelectCheck010(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }


        boolean commitFlg = false;
        con.setAutoCommit(false);
        ArrayList<AdrAddressModel> delList = new ArrayList<AdrAddressModel>();

        try {
            Adr330Biz biz = new Adr330Biz(getRequestModel(req));
            Adr330ParamModel paramMdl = new Adr330ParamModel();
            GsMessage gsMsg = new GsMessage();
            //削除処理実行
            paramMdl.setParam(form);
            delList = biz.deleteAddress(paramMdl, getSessionUserModel(req).getUsrsid(), con);
            paramMdl.setFormData(form);

            //ログ出力処理
            AdrCommonBiz adrBiz = new AdrCommonBiz(con);
            String msg = "[" + gsMsg.getMessage("cmn.target") + "]";
            boolean multiFlg = false;
            for (AdrAddressModel mdl : delList) {
                if (multiFlg) {
                    msg += "\r\n";
                } else {
                    multiFlg = true;
                }
                msg += mdl.getAdrSei() + " " + mdl.getAdrMei();
            }
            String opCode = gsMsg.getMessage(req, "cmn.delete");
            adrBiz.outPutLog(
                    map, req, res, opCode, GSConstLog.LEVEL_TRACE, msg);

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
                                        Adr330Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();

        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "address.src.2")));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 設定可能チェック
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

        if (!gsAdmFlg) {
            return map.findForward("gf_submit");
        }

        return null;
    }

}