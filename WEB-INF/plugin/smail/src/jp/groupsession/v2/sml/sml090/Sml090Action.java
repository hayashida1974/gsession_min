package jp.groupsession.v2.sml.sml090;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountDiskDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmlAccountDiskModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.pdf.SmlPdfModel;
import jp.groupsession.v2.sml.sml010.Sml010Biz;
import jp.groupsession.v2.sml.sml010.Sml010Form;
import jp.groupsession.v2.sml.sml010.Sml010ParamModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール詳細検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml090Action extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml090Action.class);

    /** CMD :戻るボタンクリック */
    public static final String CMD_BACK = "backToMsgList";
    /** CMD :差出人グループコンボ変更 */
    public static final String CMD_CHANGE_GROUP = "changeGroup";
    /** CMD :宛先選択ボタンクリック */
    public static final String CMD_TO_SELECT = "selectAetsaki";
    /** CMD :ページコンボ選択 */
    public static final String CMD_CHANGE_PCOMBO = "changePageCombo";
    /** CMD :前ページクリック */
    public static final String CMD_FORMER_PAGE = "fomerPage";
    /** CMD :次ページクリック */
    public static final String CMD_NEXT_PAGE = "nextPage";
    /** CMD :検索ボタンクリック */
    public static final String CMD_SEARCH = "search";
    /** CMD :検索ボタンクリック(他画面より) */
    public static final String CMD_SEARCH_OTHER = "smlSearch";
    /** CMD :件名クリック */
    public static final String CMD_CLICK_TITLE = "clickTitle";
    /** CMD :件名クリック */
    public static final String CMD_CLICK_TITLE_SOUKOU = "clickTitleSoukou";
    /** CMD :メール種別ラジオボタンチェンジ */
    public static final String CMD_CHANGE_SYUBETSU = "changeSyubetsu";
    /** メッセージに対するラベル付与 ラベル追加 */
    private static final int MSG_LABEL_ADD__ = 0;
    /** メッセージに対するラベル付与 ラベル削除 */
    private static final int MSG_LABEL_DEL__ = 1;

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

        log__.debug("START");

        ActionForward forward = null;
        Sml090Form myForm = (Sml090Form) form;

        //アカウントが未選択の場合、デフォルトアカウントを設定する
        if (myForm.getSmlViewAccount() <= 0) {
            Sml010Biz biz = new Sml010Biz();
            myForm.setSmlViewAccount(
                    biz.getDefaultAccount(con, getSessionUserSid(req)));
        }

        //選択されているアカウントが使用可能かを判定する
        SmlCommonBiz biz = new SmlCommonBiz();
        if (!biz.canUseAccount(
                con, getSessionUserSid(req), myForm.getSmlViewAccount())) {
            return getAuthErrorPage(map, req);
        }


        //コマンドパラメータ取得
        String cmd = __getCmdProperty(req);

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = __doBack(map, myForm, req, res, con);

        } else if (CMD_CHANGE_GROUP.equals(cmd)) {
            log__.debug("グループコンボ変更");
            forward = __doChangeGroupCombo(map, myForm, req, res, con);

        } else if (cmd.equals("dsp")) {
            log__.debug("宛先選択画面からの戻り");
            forward = __doSearch(map, myForm, req, res, con, false);

        } else if (CMD_CHANGE_PCOMBO.equals(cmd)) {
            log__.debug("ページコンボ変更");
            forward = __doSearch(map, myForm, req, res, con, false);

        } else if (CMD_FORMER_PAGE.equals(cmd)) {
            log__.debug("前ページクリック");
            myForm.setSml090page1(myForm.getSml090page1() - 1);
            forward = __doSearch(map, myForm, req, res, con, false);

        } else if (CMD_NEXT_PAGE.equals(cmd)) {
            log__.debug("次ページクリック");
            myForm.setSml090page1(myForm.getSml090page1() + 1);
            forward = __doSearch(map, myForm, req, res, con, false);

        } else if (CMD_SEARCH_OTHER.equals(cmd)) {
            log__.debug("検索ボタンクリック(他画面より)");
            forward = __doModeContorolSearch(map, myForm, req, res, con);

        } else if (CMD_SEARCH.equals(cmd)) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, myForm, req, res, con, true);

        } else if (CMD_CLICK_TITLE.equals(cmd)) {
            log__.debug("メール件名リンククリック");
            forward = __doTitleClick(map, myForm, req, res, con);

        } else if (CMD_CLICK_TITLE_SOUKOU.equals(cmd)) {
            log__.debug("メール件名クリック(草稿モード)");
            forward =
                __setSmsgParam(map, req, con, myForm, GSConstSmail.MSG_CREATE_MODE_SOKO);

        } else if (cmd.equals("msg_delete")) {
            log__.debug("削除ボタン押下");
            forward = __doDeleteConfirmation(map, myForm, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            log__.debug("削除OKボタン押下");
            forward = __doDeleteOk(map, myForm, req, res, con);
        //ラベルを追加する
        } else if (cmd.equals("msg_addLabel")) {
            log__.debug("ラベルの登録");
            __doAddLabel(map, myForm, req, res, con);
        //ラベルを削除する
        } else if (cmd.equals("msg_deleteLabel")) {
            log__.debug("ラベルの削除");
            __doAddLabel(map, myForm, req, res, con);
        //ラベルのデータを取得する
        } else if (cmd.equals("getLabelData")) {
            log__.debug("ラベルの取得");
            __getLabelData(map, myForm, req, res, con);
        } else if (cmd.equals("addMessageLabel")) {
        //ラベル追加
            __doMessageLabel(map, myForm, req, res, con, MSG_LABEL_ADD__);

        } else if (cmd.equals("delMessageLabel")) {
        //ラベル削除
            __doMessageLabel(map, myForm, req, res, con, MSG_LABEL_DEL__);
            //PDF出力ボタン押下
        } else if (cmd.equals("msg_pdfData")) {
            log__.debug("PDF出力ボタン押下");
            __doPdfConfirmationData(map, myForm, req, res, con);
        //PDF出力確認画面でOKボタン押下
        } else if (cmd.equals("exportByPdfData")) {
            log__.debug("PDF出力OKボタン押下");
            forward = __doExportByPdfDataOk(map, myForm, req, res, con);
        //eml出力ボタン押下
        } else if (cmd.equals("msg_emlData")) {
            log__.debug("eml出力ボタン押下");
            __doEmlConfirmationData(map, myForm, req, res, con);
        //eml出力確認画面でOKボタン押下
        } else if (cmd.equals("exportByEmlData")) {
            log__.debug("eml出力OKボタン押下");
            forward = __doExportByEmlDataOk(map, myForm, req, res, con);
            //既読にする(一覧)
        } else if (cmd.equals("msg_kidokuData")) {
            log__.debug("ラベルの既読にする(一覧)");
            __doCheckMail2(map, myForm, req, res, con, 1);
        //未読にする(一覧)
        } else if (cmd.equals("msg_midokuData")) {
            log__.debug("未読にする(一覧)");
            __doCheckMail2(map, myForm, req, res, con, 2);
        //既読にする(一覧)
        } else if (cmd.equals("kidokuOkData")) {
            log__.debug("ラベルの既読にする(一覧)");
            __doSelRead(map, myForm, req, res, con, GSConstSmail.OPKBN_OPENED);
        //未読にする(一覧)
        } else if (cmd.equals("midokuOkData")) {
            log__.debug("未読にする(一覧)");
            __doSelRead(map, myForm, req, res, con, GSConstSmail.OPKBN_UNOPENED);

        } else if (cmd.equals("smlCkeckKeyword")) {
            log__.debug("検索ボタン押下の前の入力チェック");
            __doCheckKeyword(map, myForm, req, res, con, true);

        } else if (cmd.equals("smlSearchData")) {
            log__.debug("検索ボタン押下");
            __doSearchData(map, myForm, req, res, con, true);

        } else if (cmd.equals("changeMailShubetu")) {
            log__.debug("メール種別変更");
            __doChangeMailShubetu(map, myForm, req, res, con);

        } else if (cmd.equals("searchPrevPage")) {
            log__.debug("前ページクリック");
            myForm.setSml090page1(myForm.getSml090page1() - 1);
            __doSearchData(map, myForm, req, res, con, false);

        } else if (cmd.equals("searchNextPage")) {
            log__.debug("次ページクリック");
            myForm.setSml090page1(myForm.getSml090page1() + 1);
            __doSearchData(map, myForm, req, res, con, false);

        } else if (cmd.equals("changePageComboData")) {
            log__.debug("ページコンボ変更");
            __doSearchData(map, myForm, req, res, con, false);

        } else if (cmd.equals("changeSortData")) {
            log__.debug("ソート変更");
            __doSearchData(map, myForm, req, res, con, false);
        } else if (cmd.equals("reloadSearchData")) {
            log__.debug("更新");
            __doSearchData(map, myForm, req, res, con, false);

        } else if (cmd.equals("msg_deleteData")) {
            log__.debug("削除ボタン押下");
            __doDeleteConfirmationData(map, myForm, req, res, con);

        } else if (cmd.equals("deleteDataOk")) {
            log__.debug("削除OKボタン押下");
            __doDeleteDataOk(map, myForm, req, res, con);


        } else {
            forward = __doSearch(map, myForm, req, res, con, false);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 戻るボタンクリック時の遷移時処理
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
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        log__.debug("start");

        log__.debug("end");
        return map.findForward(CMD_BACK);
    }

    /**
     * <br>[機  能] 他画面（メール一覧）からの遷移時処理
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
    private ActionForward __doModeContorolSearch(ActionMapping map,
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        __doSearch(map, form, req, res, con, true);

        return map.getInputForward();
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
     */
    private ActionForward __doInit(ActionMapping map,
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sml090ParamModel paramMdl = new Sml090ParamModel();
        paramMdl.setParam(form);

        Sml090Biz myBiz = new Sml090Biz(reqMdl);
        myBiz.setInitData(paramMdl, reqMdl, con);
        myBiz.setAtesaki(paramMdl, con);
        paramMdl.setFormData(form);

        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        form.setSml090searchUse(CommonBiz.getWebSearchUse(pconfig));

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 件名クリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doTitleClick(ActionMapping map,
            Sml090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {

        return map.findForward(CMD_CLICK_TITLE);
    }


    /**
     * <br>[機  能] グループコンボ変更時処理
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
    private ActionForward __doChangeGroupCombo(ActionMapping map,
                                      Sml090Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException {

        log__.debug("start");
        log__.debug("end");
        return __doSearch(map, form, req, res, con, false);

    }


    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param save 検索条件をセーブするか true: する false:しない
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con,
                                    boolean save)
        throws SQLException {

        log__.debug("start");
        log__.debug("検索ワード :" + form.getSml090KeyWord());
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sml090Biz myBiz = new Sml090Biz(reqMdl);
        ActionErrors errors = new ActionErrors();
        if (save) {
            //入力チェック
            errors = form.validateSml090Check(map, reqMdl);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            form.setSml090page1(1);
            //検索条件SAVE
            form.saveSearchParm();
        }

        //検索実行
        form.setSearchFlg(GSConstSmail.SEARCH_EXECUTE_TRUE);

        Sml090ParamModel paramMdl = new Sml090ParamModel();
        paramMdl.setParam(form);
        int count = myBiz.getSearchResult(paramMdl, reqMdl, con);
        paramMdl.setFormData(form);
        log__.debug("検索結果件数 :" + count + " 件");

        GsMessage gsMsg = new GsMessage();
        String smailInfo = gsMsg.getMessage(req, "sml.116");

        if (count < 1
                && !(CMD_CHANGE_SYUBETSU.equals(__getCmdProperty(req))
                        || "dsp".equals(__getCmdProperty(req)))) {
            ActionMessage msg =
                new ActionMessage("search.data.notfound", smailInfo, "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);

        }

        //モードチェンジ
        form.setSml010ProcMode(form.getSml090SvMailSyubetsu());
        log__.debug("end");
        return __doInit(map, form, req, res, con);

    }

    /**
     * <br>[機  能] ショートメッセージ画面遷移パラメータ設定
     * <br>[解  説]
     * <br>[備  考] 草稿リンククリック時
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
                                          Sml090Form form,
                                          String mode)
        throws SQLException {

        log__.debug("start");
        con.setAutoCommit(true);

        Sml020Form sml020Form = new Sml020Form();
        //詰め替え
        sml020Form.setSml010ProcMode(form.getSml090SvMailSyubetsu());

        //以外はそのままセット
//        sml020Form.setSml010ProcMode(form.getSml010ProcMode());
        sml020Form.setSml010Sort_key(form.getSml010Sort_key());
        sml020Form.setSml010Order_key(form.getSml010Order_key());
        sml020Form.setSml010PageNum(form.getSml010PageNum());
        sml020Form.setSml010SelectedDelSid(form.getSml010SelectedDelSid());
        sml020Form.setSml010DelSid(form.getSml010DelSid());
        sml020Form.setSml020ProcMode(mode);

        //下書きモード時は宛先を生成する
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {
            Sml010Biz biz = new Sml010Biz();
            ArrayList<AtesakiModel> ret =
                biz.getAtesaki(form.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_ATESAKI, con);
            ArrayList<AtesakiModel> retCc =
                biz.getAtesaki(form.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_CC, con);
            ArrayList<AtesakiModel> retBcc =
                biz.getAtesaki(form.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_BCC, con);

            String[] sml020userSid = null;
            if (ret.isEmpty()) {
                sml020userSid = new String[0];
            } else {
                sml020userSid = new String[ret.size()];
                for (int i = 0; i < ret.size(); i++) {
                    AtesakiModel retMdl = (AtesakiModel) ret.get(i);
                    sml020userSid[i] = String.valueOf(retMdl.getUsrSid());
                }
            }

            String[] sml020userSidCc = null;
            if (retCc.isEmpty()) {
                sml020userSidCc = new String[0];
            } else {
                sml020userSidCc = new String[retCc.size()];
                for (int i = 0; i < retCc.size(); i++) {
                    AtesakiModel retMdl = (AtesakiModel) retCc.get(i);
                    sml020userSidCc[i] = String.valueOf(retMdl.getUsrSid());
                }
            }

            String[] sml020userSidBcc = null;
            if (retBcc.isEmpty()) {
                sml020userSidBcc = new String[0];
            } else {
                sml020userSidBcc = new String[retBcc.size()];
                for (int i = 0; i < retBcc.size(); i++) {
                    AtesakiModel retMdl = (AtesakiModel) retBcc.get(i);
                    sml020userSidBcc[i] = String.valueOf(retMdl.getUsrSid());
                }
            }

            sml020Form.setSml020userSid(sml020userSid);
            sml020Form.setSml020userSidCc(sml020userSidCc);
            sml020Form.setSml020userSidBcc(sml020userSidBcc);
        }

        req.setAttribute("sml020Form", sml020Form);

        log__.debug("end");
        return map.findForward(CMD_CLICK_TITLE_SOUKOU);
    }

    /**
     * <br>[機  能] リクエストよりコマンドパラメータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return CMDパラメータ
     */
    private String __getCmdProperty(HttpServletRequest req) {
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        log__.debug("--- cmd :" + cmd);
        return cmd;
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
                                                  Sml090Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.delete.message");

        ActionForward forward = null;
        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheckDel(
                        deleteMessage, req, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doSearch(map, form, req, res, con, false);
            }

            Sml090Biz biz = new Sml090Biz(reqMdl);

            //削除対象のメールタイトル一覧を取得する
            Sml090ParamModel paramMdl = new Sml090ParamModel();
            paramMdl.setParam(form);
            ArrayList<SmailDetailModel> delList =
                biz.getTargetMailList(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

            //削除確認画面を設定
            forward = __setConfirmationDsp(map, req, form, delList, 1);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return forward;
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
                                        Sml090Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);

        try {

            //削除処理実行
            Sml090ParamModel paramMdl = new Sml090ParamModel();
            paramMdl.setParam(form);
            Sml090Biz biz = new Sml090Biz(reqMdl);
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

            //完了画面設定
            return __setCompDsp(map, req, form, 1);

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
     * <br>[機  能] 確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param delList 削除対象リスト
     * @param kbn 処理区分 1:削除確認画面  2:復旧確認画面
     * @return ActionForward フォワード
     */
    private ActionForward __setConfirmationDsp(ActionMapping map,
                                                HttpServletRequest req,
                                                Sml090Form form,
                                                ArrayList<SmailDetailModel> delList,
                                                int kbn) {

        MessageResources msgRes = getResources(req);
        String mode = form.getSml090SvMailSyubetsu();
        String delMsg = "";
        int midokuCnt = 0;
        GsMessage gsMsg = new GsMessage();

        String ten = gsMsg.getMessage(req, "wml.231");

       /************************************************************************
        *
        * 確認画面に表示するメッセージを作成する
        * (復旧処理は処理モード = ゴミ箱しかありえない)
        *
        ************************************************************************/
        if (!delList.isEmpty()) {
            for (int i = 0; i < delList.size(); i++) {

                //処理モード = 受信モード
                if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                    SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                    //対象が未読の場合(赤文字で強調表示)
                    if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
                        midokuCnt += 1;
                        delMsg += "<font color='#ff0000'>";
                        delMsg += "<strong>";
                        delMsg += "・";
                        delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                NullDefault.getString(ret.getSmsTitle(), ""));
                        delMsg += "</strong>";
                        delMsg += "</font>";
                    //対象が既読の場合
                    } else {
                        delMsg += "・";
                        delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                NullDefault.getString(ret.getSmsTitle(), ""));
                    }

                    //最後の要素以外は改行を挿入
                    if (i < delList.size() - 1) {
                        delMsg += "<br>";
                    }

                //処理モード = 送信モード or 草稿モード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                    || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {


                    SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                    delMsg += ten;
                    delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(ret.getSmsTitle(), ""));

                    //最後の要素以外は改行を挿入
                    if (i < delList.size() - 1) {
                        delMsg += "<br>";
                    }

                //処理モード = ゴミ箱
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {

                    SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                    delMsg += ten;
                    String mailKbn = ret.getMailKbn();
                    String smailMsg = gsMsg.getMessage(req, "sml.100");

                    //対象が(元は)受信メッセージ
                    if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                        smailMsg = gsMsg.getMessage(req, "sml.100");
                        delMsg += smailMsg + " ";
                    //対象が(元は)送信メッセージ
                    } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                        smailMsg = gsMsg.getMessage(req, "sml.102");
                        delMsg += smailMsg + " ";
                    //対象が(元は)草稿メッセージ
                    } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                        smailMsg = gsMsg.getMessage(req, "sml.101");
                        delMsg += smailMsg + " ";
                    }
                    delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(ret.getSmsTitle(), ""));

                    //最後の要素以外は改行を挿入
                    if (i < delList.size() - 1) {
                        delMsg += "<br>";
                    }
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

        String msg = gsMsg.getMessage(req, "cmn.message");

        //削除処理時
        if (kbn == 1) {
            //OKボタンクリック遷移先
            cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");
            //処理モード = 受信モード or 送信モード or 草稿モード
            if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                //未読メッセージが存在する場合は注意文言を表示
                if (midokuCnt > 0) {
                    cmn999Form.setMessage(
                            msgRes.getMessage(
                                    "move.gomibako.mail2", msg, delMsg));
                } else {
                    cmn999Form.setMessage(
                            msgRes.getMessage(
                                    "move.gomibako.mail", msg, delMsg));
                }

            //処理モード = ゴミ箱
            } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                cmn999Form.setMessage(
                        msgRes.getMessage(
                                "sakujo.kakunin.list", msg, delMsg));
            }

        //復旧処理時
        } else if (kbn == 2) {
            //OKボタンクリック遷移先
            cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=revivedOk");
            cmn999Form.setMessage(
                    msgRes.getMessage(
                            "move.former.mail", msg, delMsg));
        }

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //hiddenパラメータ
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());

        cmn999Form.addHiddenParam("sml090ProcModeSave", form.getSml090ProcModeSave());
        cmn999Form.addHiddenParam("sml010SelectedDelSid", form.getSml010SelectedDelSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("sml090SelectedDelSid", form.getSml090SelectedDelSid());
        cmn999Form.addHiddenParam("sml090DelSid", form.getSml090DelSid());
        cmn999Form.addHiddenParam("sml090SvAtesaki", form.getSml090SvAtesaki());
        cmn999Form.addHiddenParam("sml090SvSltGroup", form.getSml090SvSltGroup());
        cmn999Form.addHiddenParam("sml090SvSltUser", form.getSml090SvSltUser());
        cmn999Form.addHiddenParam("searchFlg", form.getSearchFlg());
        cmn999Form.addHiddenParam("sml090SvMailSyubetsu", form.getSml090SvMailSyubetsu());
        cmn999Form.addHiddenParam("sml090SvMailMark", form.getSml090SvMailMark());
        cmn999Form.addHiddenParam("sml090SvKeyWord", form.getSml090SvKeyWord());
        cmn999Form.addHiddenParam("sml090SvKeyWordkbn", form.getSml090SvKeyWordkbn());
        cmn999Form.addHiddenParam("sml090SvSearchTarget", form.getSml090SvSearchTarget());
        cmn999Form.addHiddenParam("sml090SvSearchOrderKey1", form.getSml090SvSearchOrderKey1());
        cmn999Form.addHiddenParam("sml090SvSearchSortKey1", form.getSml090SvSearchSortKey1());
        cmn999Form.addHiddenParam("sml090SvSearchOrderKey2", form.getSml090SvSearchOrderKey2());
        cmn999Form.addHiddenParam("sml090SvSearchSortKey2", form.getSml090SvSearchSortKey2());

        cmn999Form.addHiddenParam("sml090page1", form.getSml090page1());
        cmn999Form.addHiddenParam("sml090page2", form.getSml090page2());
        cmn999Form.addHiddenParam("cmn120userSid", form.getCmn120userSid());
        cmn999Form.addHiddenParam("sml090SltGroup", form.getSml090SltGroup());
        cmn999Form.addHiddenParam("sml090SltUser", form.getSml090SltUser());
        cmn999Form.addHiddenParam("sml090MailSyubetsu", form.getSml090MailSyubetsu());
        cmn999Form.addHiddenParam("sml090MailMark", form.getSml090MailMark());
        cmn999Form.addHiddenParam("sml090KeyWordkbn", form.getSml090KeyWordkbn());
        cmn999Form.addHiddenParam("sml090SearchTarget", form.getSml090SearchTarget());
        cmn999Form.addHiddenParam("sml090SearchSortKey1", form.getSml090SearchSortKey1());
        cmn999Form.addHiddenParam("sml090SearchOrderKey1", form.getSml090SearchOrderKey1());
        cmn999Form.addHiddenParam("sml090SearchSortKey2", form.getSml090SearchSortKey2());
        cmn999Form.addHiddenParam("sml090SearchOrderKey2", form.getSml090SearchOrderKey2());

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
     * @param kbn 処理区分 1:削除確認画面  2:復旧確認画面
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Sml090Form form,
                                        int kbn) {

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.message");

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        String mode = form.getSml090SvMailSyubetsu();
        //処理モード = 受信モード or 送信モード or 草稿モード
        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

            cmn999Form.setMessage(
                    msgRes.getMessage("move.gomibako.object", msg));
        //処理モード = ゴミ箱
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            //削除処理時
            if (kbn == 1) {
                cmn999Form.setMessage(
                        msgRes.getMessage("sakujo.kanryo.object", msg));
            //復旧処理時
            } else if (kbn == 2) {
                cmn999Form.setMessage(
                        msgRes.getMessage("move.former.object", msg));
            //ゴミ箱クリア時
            } else if (kbn == 3) {
                cmn999Form.setMessage(
                        msgRes.getMessage("conf.clear.comp.gomibako"));
            }
        }

        //画面パラメータをセット
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());

        cmn999Form.addHiddenParam("sml090ProcModeSave", form.getSml090ProcModeSave());
        cmn999Form.addHiddenParam("sml010SelectedDelSid", form.getSml010SelectedDelSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("sml090SvAtesaki", form.getSml090SvAtesaki());
        cmn999Form.addHiddenParam("sml090SvSltGroup", form.getSml090SvSltGroup());
        cmn999Form.addHiddenParam("sml090SvSltUser", form.getSml090SvSltUser());
        cmn999Form.addHiddenParam("searchFlg", form.getSearchFlg());
        cmn999Form.addHiddenParam("sml090SvMailSyubetsu", form.getSml090SvMailSyubetsu());
        cmn999Form.addHiddenParam("sml090SvMailMark", form.getSml090SvMailMark());
        cmn999Form.addHiddenParam("sml090SvKeyWord", form.getSml090SvKeyWord());
        cmn999Form.addHiddenParam("sml090SvKeyWordkbn", form.getSml090SvKeyWordkbn());
        cmn999Form.addHiddenParam("sml090SvSearchTarget", form.getSml090SvSearchTarget());
        cmn999Form.addHiddenParam("sml090SvSearchOrderKey1", form.getSml090SvSearchOrderKey1());
        cmn999Form.addHiddenParam("sml090SvSearchSortKey1", form.getSml090SvSearchSortKey1());
        cmn999Form.addHiddenParam("sml090SvSearchOrderKey2", form.getSml090SvSearchOrderKey2());
        cmn999Form.addHiddenParam("sml090SvSearchSortKey2", form.getSml090SvSearchSortKey2());

        cmn999Form.addHiddenParam("sml090page1", form.getSml090page1());
        cmn999Form.addHiddenParam("sml090page2", form.getSml090page2());
        cmn999Form.addHiddenParam("cmn120userSid", form.getCmn120userSid());
        cmn999Form.addHiddenParam("sml090SltGroup", form.getSml090SltGroup());
        cmn999Form.addHiddenParam("sml090SltUser", form.getSml090SltUser());
        cmn999Form.addHiddenParam("sml090MailSyubetsu", form.getSml090MailSyubetsu());
        cmn999Form.addHiddenParam("sml090MailMark", form.getSml090MailMark());
        cmn999Form.addHiddenParam("sml090KeyWordkbn", form.getSml090KeyWordkbn());
        cmn999Form.addHiddenParam("sml090SearchTarget", form.getSml090SearchTarget());
        cmn999Form.addHiddenParam("sml090SearchSortKey1", form.getSml090SearchSortKey1());
        cmn999Form.addHiddenParam("sml090SearchOrderKey1", form.getSml090SearchOrderKey1());
        cmn999Form.addHiddenParam("sml090SearchSortKey2", form.getSml090SearchSortKey2());
        cmn999Form.addHiddenParam("sml090SearchOrderKey2", form.getSml090SearchOrderKey2());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sml090ParamModel paramMdl = new Sml090ParamModel();
        paramMdl.setParam(form);

        Sml090Biz myBiz = new Sml090Biz(reqMdl);
        myBiz.setInitData(paramMdl, reqMdl, con);
        myBiz.setAtesaki(paramMdl, con);
        paramMdl.setFormData(form);

        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        form.setSml090searchUse(CommonBiz.getWebSearchUse(pconfig));

        con.setAutoCommit(false);

        JSONObject jsonData = new JSONObject();
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
            log__.error("jsonデータ送信失敗(検索データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param save save
     * @throws Exception 実行時例外
     */
    private void __doCheckKeyword(ActionMapping map,
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con,
                                    boolean save)
        throws Exception {

        log__.debug("start");
        log__.debug("検索ワード :" + form.getSml090KeyWord());
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateSearchCheck(reqMdl);

        if (!errors.isEmpty()) {
            form.setErrorsList(__getJsonErrorMsg(req, errors));
        }

        JSONObject jsonData = new JSONObject();
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
            log__.error("jsonデータ送信失敗(検索データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param save save
     * @throws Exception 実行時例外
     */
    private void __doSearchData(ActionMapping map,
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con,
                                    boolean save)
        throws Exception {

        boolean errorFlg = false;

        log__.debug("start");
        log__.debug("検索ワード :" + form.getSml090KeyWord());
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sml090Biz myBiz = new Sml090Biz(reqMdl);
        ActionErrors errors = new ActionErrors();

        if (save) {
            form.setSml090page1(1);
            //検索条件SAVE
            form.saveSearchParm();
        }

        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);

        //未読メール件数取得
        form.setSml010MidokuCnt(smlCmnBiz.getUnopenedMsgCnt(form.getSmlViewAccount(), con));

        //草稿メール件数取得
        form.setSml010SokoCnt(smlCmnBiz.getSokoMsgCnt(form.getSmlViewAccount(), con));

        //未読メール件数(ゴミ箱)取得
        form.setSml010GomiMidokuCnt(
                smlCmnBiz.getUnopenedGomiMsgCnt(form.getSmlViewAccount(), con));

        //アカウントディスク使用量
        SmlAccountDiskDao diskDao = new SmlAccountDiskDao(con);
        SmlAccountDiskModel sadMdl = null;
        sadMdl = diskDao.select(form.getSmlViewAccount());
        if (sadMdl != null) {
            form.setSml010AccountDisk(
                    String.valueOf(BigDecimal.valueOf(sadMdl.getSdsSize()).divide(
                            new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP)));
        }

        //検索実行
        form.setSearchFlg(GSConstSmail.SEARCH_EXECUTE_TRUE);

        Sml090ParamModel paramMdl = new Sml090ParamModel();
        paramMdl.setParam(form);
        int count = myBiz.getSearchResultData(paramMdl, reqMdl, con);
        paramMdl.setFormData(form);
        log__.debug("検索結果件数 :" + count + " 件");

        GsMessage gsMsg = new GsMessage();
        String smailInfo = gsMsg.getMessage(req, "sml.116");

        if (count < 1
                && !(CMD_CHANGE_SYUBETSU.equals(__getCmdProperty(req))
                        || "dsp".equals(__getCmdProperty(req)))) {
            ActionMessage msg =
                    new ActionMessage("search.data.notfound", smailInfo, "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            addErrors(req, errors);
            form.setErrorsList(__getJsonErrorMsg(req, errors));
            errorFlg = true;

        } else {

            log__.debug("end");
            __doInitData(map, form, req, res, con);
        }

        if (errorFlg) {

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(検索データ)");
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }

    }

    /**
     * <br>[機  能] メール種別変更
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
    private void __doChangeMailShubetu(ActionMapping map,
                                    Sml090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        form.setSml090SortKeyLabelList(SmlCommonBiz.getSortLabelList(
                form.getSml090MailSyubetsu(), getRequestModel(req)));

        JSONObject jsonData = new JSONObject();
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
            log__.error("jsonデータ送信失敗(メール種別変更)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
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
     * @throws Exception 実行時例外
     */
    private void __doDeleteConfirmationData(ActionMapping map,
                                                  Sml090Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.delete.message");

        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheckDel(
                        deleteMessage, req, con);

            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml090Biz biz = new Sml090Biz(reqMdl);

                //削除対象のメールタイトル一覧を取得する
                Sml090ParamModel paramMdl = new Sml090ParamModel();
                paramMdl.setParam(form);
                ArrayList<SmailDetailModel> delList =
                    biz.getTargetMailList(paramMdl, reqMdl, con);
                paramMdl.setFormData(form);

                //削除確認画面を設定
                MessageResources msgRes = getResources(req);
                String mode = form.getSml090SvMailSyubetsu();
                String delMsg = "";
                int midokuCnt = 0;

                String ten = gsMsg.getMessage(req, "wml.231");

               /************************************************************************
                *
                * 確認画面に表示するメッセージを作成する
                * (復旧処理は処理モード = ゴミ箱しかありえない)
                *
                ************************************************************************/
                if (!delList.isEmpty()) {
                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
                                midokuCnt += 1;
                                delMsg += "<font color='#ff0000'>";
                                delMsg += "<strong>";
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                                delMsg += "</strong>";
                                delMsg += "</font>";
                            //対象が既読の場合
                            } else {
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                            }

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = 送信モード or 草稿モード
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {


                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += ten;
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = ゴミ箱
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += ten;
                            String mailKbn = ret.getMailKbn();
                            String smailMsg = gsMsg.getMessage(req, "sml.100");

                            //対象が(元は)受信メッセージ
                            if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                                smailMsg = gsMsg.getMessage(req, "sml.100");
                                delMsg += smailMsg + " ";
                            //対象が(元は)送信メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                                smailMsg = gsMsg.getMessage(req, "sml.102");
                                delMsg += smailMsg + " ";
                            //対象が(元は)草稿メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                smailMsg = gsMsg.getMessage(req, "sml.101");
                                delMsg += smailMsg + " ";
                            }
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }
                        }
                    }
                }

               /************************************************************************
                *
                * 確認画面設定
                *
                ************************************************************************/

                String msg = gsMsg.getMessage(req, "cmn.message");
                List<String> messageList = new ArrayList<String>();
                //処理モード = 受信モード or 送信モード or 草稿モード
                if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                    || mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                    || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                    //未読メッセージが存在する場合は注意文言を表示
                    if (midokuCnt > 0) {
                        messageList.add(
                                msgRes.getMessage(
                                        "move.gomibako.mail2", msg, delMsg));
                    } else {
                        messageList.add(
                                msgRes.getMessage(
                                        "move.gomibako.mail", msg, delMsg));
                    }

                //処理モード = ゴミ箱
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                    messageList.add(
                            msgRes.getMessage(
                                    "sakujo.kakunin.list", msg, delMsg));
                }

                form.setMessageList(messageList);

            }

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(削除確認)");
                throw e;
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
     * <br>[機  能] 削除確認画面でOKボタン押下
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
    private void __doDeleteDataOk(ActionMapping map,
                                        Sml090Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        RequestModel reqMdl = getRequestModel(req);

        try {

            //削除処理実行
            Sml090ParamModel paramMdl = new Sml090ParamModel();
            paramMdl.setParam(form);
            Sml090Biz biz = new Sml090Biz(reqMdl);
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
            String msg = gsMsg.getMessage(req, "cmn.message");
            List<String> messageList = new ArrayList<String>();
            MessageResources msgRes = getResources(req);
            String mode = form.getSml090SvMailSyubetsu();
            //処理モード = 受信モード or 送信モード or 草稿モード
            if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                messageList.add(
                        msgRes.getMessage("move.gomibako.object", msg));
            //処理モード = ゴミ箱
            } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                //削除処理時
                messageList.add(
                            msgRes.getMessage("sakujo.kanryo.object", msg));

            }
            form.setMessageList(messageList);

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(削除完了)");
                throw e;
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
     * <br>[機  能] ラベルの登録を行う
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
    private void __doAddLabel(ActionMapping map,
                                                  Sml090Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.mail");

        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheckDel(deleteMessage, req, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml090Biz biz = new Sml090Biz(getRequestModel(req));

                //削除対象のメールタイトル一覧を取得する
                Sml090ParamModel paramMdl = new Sml090ParamModel();
                paramMdl.setParam(form);
                ArrayList<SmailDetailModel> delList =
                    biz.getTargetMailList(paramMdl, getRequestModel(req), con);
                paramMdl.setFormData(form);


                /************************************************************************
                *
                * 確認画面に表示するメッセージを作成する
                * (復旧処理は処理モード = ゴミ箱しかありえない)
                *
                ************************************************************************/
                MessageResources msgRes = getResources(req);
                String mode = form.getSml090MailSyubetsu();
                String delMsg = "";
                int midokuCnt = 0;

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
                                midokuCnt += 1;
                                delMsg += "<font color='#ff0000'>";
                                delMsg += "<strong>";
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                                delMsg += "</strong>";
                                delMsg += "</font>";
                            //対象が既読の場合
                            } else {
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                            }

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = 送信モード or 草稿モード
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = ゴミ箱
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            String mailKbn = ret.getMailKbn();

                            String msg = "";

                            //対象が(元は)受信メッセージ
                            if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                                msg = gsMsg.getMessage(req, "sml.100");
                                delMsg += msg + " ";
                            //対象が(元は)送信メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                                msg = gsMsg.getMessage(req, "sml.102");
                                delMsg += msg + " ";
                            //対象が(元は)草稿メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                msg = gsMsg.getMessage(req, "sml.101");
                                delMsg += msg + " ";
                            }
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }
                        }
                    }
                }

               /************************************************************************
                *
                * 確認画面設定
                *
                ************************************************************************/

                List<String> messageList = new ArrayList<String>();
                String msg = gsMsg.getMessage(req, "cmn.message");

                //処理モード = 受信モード or 送信モード or 草稿モード
                if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                    || mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                    || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {


                    //未読メッセージが存在する場合は注意文言を表示
                    if (midokuCnt > 0) {
                        messageList.add(msgRes.getMessage(
                                        "move.gomibako.mail2", msg, delMsg));
                    } else {
                        messageList.add(msgRes.getMessage(
                                        "move.gomibako.mail", msg, delMsg));
                    }

                //処理モード = ゴミ箱
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                    messageList.add(msgRes.getMessage(
                                    "sakujo.kakunin.list", msg, delMsg));
                }

                form.setMessageList(messageList);
            }

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(削除確認データ)");
                throw e;
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
     * <br>[機  能] ラベルを取得する
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
    private void __getLabelData(ActionMapping map,
                                                  Sml010Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        try {

            Sml010Biz biz = new Sml010Biz();

            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            biz.getLabelData(paramMdl, con);
            paramMdl.setFormData(form);

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(ラベルデータ)");
                throw e;
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
     * <br>[機  能] メールに対するラベル追加 or ラベル削除 処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param type 処理種別 0:ラベル追加 1:ラベル削除
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doMessageLabel(ActionMapping map,
                                            Sml090Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            int type) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter out = null;
        String opCode = "";

        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);

        try {
            con.setAutoCommit(false);

            Sml090ParamModel paramMdl = new Sml090ParamModel();
            paramMdl.setParam(form);

            Sml090Biz biz = new Sml090Biz(getRequestModel(req));

            if (type == MSG_LABEL_ADD__) {
                biz.setLabelForMessage(map, reqMdl, res, con, paramMdl,
                                                getCountMtController(req),
                                                getSessionUserSid(req));
                opCode = getInterMessage(req, "cmn.entry");
            } else if (type == MSG_LABEL_DEL__) {
                biz.deleteLabelForMessage(con, paramMdl, getRequestModel(req));
                opCode = getInterMessage(req, "cmn.delete");
            }

            paramMdl.setFormData(form);

            JSONObject jsonData = new JSONObject();
            jsonData = JSONObject.fromObject(form);
            jsonData.element("success", true);
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();

            con.commit();
            commit = true;
        } finally {
            if (out != null) {
                out.close();
            }

            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                opCode, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n"
                                             + getInterMessage(req, GSConstSmail.LOG_VALUE_LABEL));

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


    /**
     * <br>[機  能] PDF出力確認処理
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
    private void __doPdfConfirmationData(ActionMapping map,
                                                  Sml090Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.delete.message");

        con.setAutoCommit(true);
        try {
            //出力対象選択チェック
            ActionErrors errors =
                form.validateSelectCheckDel(deleteMessage, req, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml090Biz biz = new Sml090Biz(getRequestModel(req));

                //出力対象のメールタイトル一覧を取得する
                Sml090ParamModel paramMdl = new Sml090ParamModel();
                paramMdl.setParam(form);
                ArrayList<SmailDetailModel> delList =
                    biz.getTargetMailList(paramMdl, getRequestModel(req), con);
                paramMdl.setFormData(form);

                /************************************************************************
                *
                * 確認画面に表示するメッセージを作成する
                * (復旧処理は処理モード = ゴミ箱しかありえない)
                *
                ************************************************************************/
                MessageResources msgRes = getResources(req);
                String mode = form.getSml010ProcMode();
                String delMsg = "";

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
                                delMsg += "<font color='#ff0000'>";
                                delMsg += "<strong>";
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                                delMsg += "</strong>";
                                delMsg += "</font>";
                            //対象が既読の場合
                            } else {
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                            }

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = 送信モード or 草稿モード
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = ゴミ箱 or ラベル
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                                || mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            String mailKbn = ret.getMailKbn();

                            String msg = "";

                            //対象が(元は)受信メッセージ
                            if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                                msg = gsMsg.getMessage(req, "sml.100");
                                delMsg += msg + " ";
                            //対象が(元は)送信メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                                msg = gsMsg.getMessage(req, "sml.102");
                                delMsg += msg + " ";
                            //対象が(元は)草稿メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                msg = gsMsg.getMessage(req, "sml.101");
                                delMsg += msg + " ";
                            }
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }
                        }
                    }
                }

                if (delList == null || delList.isEmpty()) {
                    ActionMessage msgs = null;
                    msgs = new ActionMessage("search.notfound.tdfkcode", deleteMessage);
                    StrutsUtil.addMessage(errors, msgs, "mailSid");
                    form.setErrorsList(__getJsonErrorMsg(req, errors));
                }

               /************************************************************************
                *
                * 確認画面設定
                *
                ************************************************************************/

                List<String> messageList = new ArrayList<String>();
                String msg = gsMsg.getMessage(req, "cmn.message");

                messageList.add(msgRes.getMessage(
                        "export.pdf.list", msg, delMsg));

                form.setMessageList(messageList);
            }

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(PDF出力データ)");
                throw e;
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
     * <br>[機  能] EML出力確認処理
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
    private void __doEmlConfirmationData(ActionMapping map,
                                                  Sml090Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.delete.message");

        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheckDel(deleteMessage, req, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml090Biz biz = new Sml090Biz(getRequestModel(req));

                //削除対象のメールタイトル一覧を取得する
                Sml090ParamModel paramMdl = new Sml090ParamModel();
                paramMdl.setParam(form);
                ArrayList<SmailDetailModel> delList =
                    biz.getTargetMailList(paramMdl, getRequestModel(req), con);
                paramMdl.setFormData(form);

                /************************************************************************
                *
                * 確認画面に表示するメッセージを作成する
                * (復旧処理は処理モード = ゴミ箱しかありえない)
                *
                ************************************************************************/
                MessageResources msgRes = getResources(req);
                String mode = form.getSml010ProcMode();
                String delMsg = "";

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
                                delMsg += "<font color='#ff0000'>";
                                delMsg += "<strong>";
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                                delMsg += "</strong>";
                                delMsg += "</font>";
                            //対象が既読の場合
                            } else {
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                            }

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = 送信モード or 草稿モード
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = ゴミ箱 or ラベル
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                                || mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            String mailKbn = ret.getMailKbn();

                            String msg = "";

                            //対象が(元は)受信メッセージ
                            if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                                msg = gsMsg.getMessage(req, "sml.100");
                                delMsg += msg + " ";
                            //対象が(元は)送信メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                                msg = gsMsg.getMessage(req, "sml.102");
                                delMsg += msg + " ";
                            //対象が(元は)草稿メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                msg = gsMsg.getMessage(req, "sml.101");
                                delMsg += msg + " ";
                            }
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }
                        }
                    }
                }

                if (delList == null || delList.isEmpty()) {
                    ActionMessage msgs = null;
                    msgs = new ActionMessage("search.notfound.tdfkcode", deleteMessage);
                    StrutsUtil.addMessage(errors, msgs, "mailSid");
                    form.setErrorsList(__getJsonErrorMsg(req, errors));
                }

               /************************************************************************
                *
                * 確認画面設定
                *
                ************************************************************************/

                List<String> messageList = new ArrayList<String>();
                String msg = gsMsg.getMessage(req, "cmn.message");

                messageList.add(msgRes.getMessage(
                        "export.eml.list", msg, delMsg));

                form.setMessageList(messageList);
            }

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(eml出力データ)");
                throw e;
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
     * <br>[機  能] PDF出力確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doExportByPdfDataOk(ActionMapping map,
                                        Sml090Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

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
    private ActionForward __createPdf(ActionMapping map, Sml090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {


        //テンポラリディレクトリを作成
        Sml090Biz biz = new Sml090Biz(getRequestModel(req));
        biz.clearTempDir();
        String outDir = biz.getTempDir();

        String procMode = "";

        RequestModel reqMdl = getRequestModel(req);
        Sml090ParamModel paramMdl = new Sml090ParamModel();
        paramMdl.setParam(form);

        procMode = paramMdl.getSml090SvMailSyubetsu();
        //受信モード
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            //データセット
            biz.setInitDataJusin(paramMdl, reqMdl, con);

            //送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            //データセット
            biz.setInitDataSosin(paramMdl, reqMdl, con);
            //ゴミ箱モード or ラベル
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                || paramMdl.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            //データセット
            biz.setInitDataGomi(paramMdl, reqMdl, con);
            //草稿
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            biz.setInitDataSoko(paramMdl, reqMdl, con);
        }

        log__.debug("ファイルダウンロード処理(PDF)");
        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();

        UDate now = new UDate();

        String topStr = UDateUtil.getSeparateYMD(now)
                      + "_"
                      + UDateUtil.getSeparateHMS(now)
                      + "_";

        topStr = biz.fileNameCheck(topStr);

        //プラグイン固有のテンポラリパス取得
        String outTempDir = IOTools.replaceFileSep(
                                outDir + topStr + "smailPdf/");
        String zipDir = outDir.toString();

        SmlPdfModel smlMdl = null;
        try {
            smlMdl = biz.createSmlPdf(
                         paramMdl, con, reqMdl, appRootPath, outTempDir, zipDir, topStr);
        } catch (IOToolsException | IOException e) {
            log__.error("ZIP作成に失敗", e);
            try {
                biz.clearTempDir();
            } catch (IOToolsException e2) {
                log__.error("テンポラリ初期化に失敗", e);
            }
            return __setZipErrorDsp(map, req, form);

        }

        String outBookName = smlMdl.getFileName();

        String outFilePath = IOTools.setEndPathChar(zipDir) + outBookName;
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        biz.deleteTempDir();

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String downloadPdf = gsMsg.getMessage(req, "sml.167");
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                downloadPdf, GSConstLog.LEVEL_INFO, "アカウント:" + sacMdl.getSacName()
                                             + "\n"
                                             + outBookName);

        paramMdl.setFormData(form);
        return null;
    }

    /**
     * <br>[機  能] PDF出力確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doExportByEmlDataOk(ActionMapping map,
                                        Sml090Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        log__.debug("ショートメールEmlファイルダウンロード処理");
        ActionForward forward = null;

        //emlファイルのダウンロード
        forward = __createEml(map, form, req, res, con);

        return forward;

    }

    /**
     * <br>[機  能] Emlファイルダウンロード処理を実行
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
    private ActionForward __createEml(ActionMapping map, Sml090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        //テンポラリディレクトリを作成
        RequestModel reqMdl = getRequestModel(req);
        Sml090Biz biz = new Sml090Biz(reqMdl);
        biz.clearTempDir();
        String outDir = biz.getTempDir();

        String procMode = "";

        Sml090ParamModel paramMdl = new Sml090ParamModel();
        paramMdl.setParam(form);

        procMode = paramMdl.getSml090SvMailSyubetsu();
        //受信モード
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP)) {
            //データセット
            biz.setInitDataJusin(paramMdl, reqMdl, con);

            //送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            //データセット
            biz.setInitDataSosin(paramMdl, reqMdl, con);
            //ゴミ箱モード or ラベル
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                || paramMdl.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            //データセット
            biz.setInitDataGomi(paramMdl, reqMdl, con);
            //草稿
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            biz.setInitDataSoko(paramMdl, reqMdl, con);
        }

        log__.debug("ファイルダウンロード処理(PDF)");
        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();

        UDate now = new UDate();

        String topStr = UDateUtil.getSeparateYMD(now)
                      + "_" + UDateUtil.getSeparateHMS(now)
                      + "_";

        topStr = biz.fileNameCheck(topStr);

        //プラグイン固有のテンポラリパス取得
        String outTempDir = IOTools.replaceFileSep(
                outDir + topStr + "smailEml/");
        String zipDir = outDir.toString();

        SmlPdfModel smlMdl = null;
        try {
            smlMdl =
                    biz.createSmlEml(paramMdl, con, reqMdl, appRootPath, outTempDir, zipDir, topStr);
        } catch (IOToolsException | IOException e) {
            log__.error("ZIP作成に失敗", e);
            try {
                biz.clearTempDir();
            } catch (IOToolsException e2) {
                log__.error("テンポラリ初期化に失敗", e);
            }
            return __setZipErrorDsp(map, req, form);

        }
        String outBookName = smlMdl.getFileName();

        String outFilePath = IOTools.setEndPathChar(zipDir) + outBookName;
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        biz.deleteTempDir();

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String downloadEml = "eml" + gsMsg.getMessage(req, "main.output");
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                downloadEml, GSConstLog.LEVEL_INFO, "アカウント:" + sacMdl.getSacName()
                                             + "\n"
                                             + outBookName);

        paramMdl.setFormData(form);
        return null;
    }

    /**
     * <br>[機  能] ファイル出力エラー画面設定処理
     * <br>[解  説] ファイル出力エラー画面のパラメータセット
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return メッセージ画面遷移
     * @throws Exception 実行時例外
     */
    private ActionForward __setZipErrorDsp(ActionMapping map,
            HttpServletRequest req, Sml090Form form) {
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
     * <br>[機  能] メールの選択チェック（既読・未読変更時）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param checkKbn 0:ラベル 1:既読 2:未読
     * @throws Exception 実行時例外
     */
    private void __doCheckMail2(ActionMapping map,
                                                  Sml090Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con,
                                                  int checkKbn)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.mail");


        //受信モード以外の場合メールを受信のみに設定
        if (!form.getSml090MailSyubetsu().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            if (form.getSml090DelSid() != null && form.getSml090DelSid().length > 0) {
                List<String> jMeis = new ArrayList<String>();
                for (String mailKey : form.getSml090DelSid()) {
                    if (mailKey.startsWith(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                        jMeis.add(mailKey);
                    }
                }
                form.setSml090DelSid(jMeis.toArray(new String[jMeis.size()]));
            }
        }


        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheckDel(deleteMessage, req, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml090Biz biz = new Sml090Biz(getRequestModel(req));

                //削除対象のメールタイトル一覧を取得する
                Sml090ParamModel paramMdl = new Sml090ParamModel();
                paramMdl.setParam(form);
                ArrayList<SmailDetailModel> delList =
                    biz.getTargetMailList(paramMdl, getRequestModel(req), con);
                paramMdl.setFormData(form);


                /************************************************************************
                *
                * 確認画面に表示するメッセージを作成する
                * (復旧処理は処理モード = ゴミ箱しかありえない)
                *
                ************************************************************************/
                MessageResources msgRes = getResources(req);
                String mode = form.getSml090MailSyubetsu();
                String delMsg = "";

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
                                delMsg += "<font color='#ff0000'>";
                                delMsg += "<strong>";
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                                delMsg += "</strong>";
                                delMsg += "</font>";
                            //対象が既読の場合
                            } else {
                                delMsg += "・";
                                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                        NullDefault.getString(ret.getSmsTitle(), ""));
                            }

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = 送信モード or 草稿モード
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }

                        //処理モード = ゴミ箱
                        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                                || mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);
                            delMsg += "・";
                            String mailKbn = ret.getMailKbn();

                            String msg = "";

                            //対象が(元は)受信メッセージ
                            if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                                msg = gsMsg.getMessage(req, "sml.100");
                                delMsg += msg + " ";
                            //対象が(元は)送信メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                                msg = gsMsg.getMessage(req, "sml.102");
                                delMsg += msg + " ";
                            //対象が(元は)草稿メッセージ
                            } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                msg = gsMsg.getMessage(req, "sml.101");
                                delMsg += msg + " ";
                            }
                            delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(ret.getSmsTitle(), ""));

                            //最後の要素以外は改行を挿入
                            if (i < delList.size() - 1) {
                                delMsg += "<br>";
                            }
                        }
                    }
                }

               /************************************************************************
                *
                * 確認画面設定
                *
                ************************************************************************/

                List<String> messageList = new ArrayList<String>();
                String msg = gsMsg.getMessage(req, "cmn.message");

                if (checkKbn == 0) {
                    messageList.add(msgRes.getMessage(
                            "move.gomibako.mail2", msg, delMsg));
                } else if  (checkKbn == 1) {
                    messageList.add(msgRes.getMessage(
                            "change.kidoku.list", msg, delMsg));
                } else if  (checkKbn == 2) {
                    messageList.add(msgRes.getMessage(
                            "change.midoku.list", msg, delMsg));
                }



                form.setMessageList(messageList);
            }

            JSONObject jsonData = new JSONObject();
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
                log__.error("jsonデータ送信失敗(削除確認データ)");
                throw e;
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
     * <br>[機  能] 受信メールの開封区分を変更する（選択したメール）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param kbn 開封区分
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doSelRead(ActionMapping map,
                                       Sml090Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con,
                                       int kbn)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            //受信モード以外の場合メールを受信のみに設定
            if (!form.getSml090MailSyubetsu().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                if (form.getSml090DelSid() != null && form.getSml090DelSid().length > 0) {
                    List<String> jMeis = new ArrayList<String>();
                    for (String mailKey : form.getSml090DelSid()) {
                        if (mailKey.startsWith(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                            jMeis.add(mailKey);
                        }
                    }
                    form.setSml090DelSid(jMeis.toArray(new String[jMeis.size()]));
                }
            }

            //削除処理実行
            Sml090ParamModel paramMdl = new Sml090ParamModel();
            paramMdl.setParam(form);
            Sml090Biz biz = new Sml090Biz(getRequestModel(req));
            biz.selsRead(paramMdl, reqMdl, con, kbn);
            paramMdl.setFormData(form);

            commitFlg = true;

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

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.delete");
        String msgGomi = gsMsg.getMessage("cmn.empty.trash");

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n" + msgGomi);

//        //メッセージ
//        List<String> messageList = new ArrayList<String>();
//        MessageResources msgRes = getResources(req);
//
//        messageList.add(
//                msgRes.getMessage("conf.clear.comp.gomibako"));
//        form.setMessageList(messageList);

        JSONObject jsonData = new JSONObject();
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
            log__.error("jsonデータ送信失敗");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
}