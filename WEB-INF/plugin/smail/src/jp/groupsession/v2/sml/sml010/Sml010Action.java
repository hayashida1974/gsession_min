package jp.groupsession.v2.sml.sml010;

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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.pdf.SmlPdfModel;
import jp.groupsession.v2.sml.sml020.Sml020Biz;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.sml.sml030.Sml030Biz;
import jp.groupsession.v2.sml.sml090.Sml090Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml010Action extends AbstractSmlAction {

    /** メッセージに対するラベル付与 ラベル追加 */
    private static final int MSG_LABEL_ADD__ = 0;
    /** メッセージに対するラベル付与 ラベル削除 */
    private static final int MSG_LABEL_DEL__ = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml010Action.class);

    /** ユーザ選択モード ユーザ名クリック */
    public static final int SELECT_USR_MODE_USRNAME = 1;
    /** ユーザ選択モード 宛先追加クリック */
    public static final int SELECT_USR_MODE_ATESAKI = 2;
    /** ユーザ選択モード CC追加クリック */
    public static final int SELECT_USR_MODE_CC = 3;
    /** ユーザ選択モード BCC追加クリック */
    public static final int SELECT_USR_MODE_BCC = 4;

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

        if (cmd.equals("exportByPdfData")) {
            log__.debug("PDFファイルダウンロード");
            return true;
        }  else if (cmd.equals("exportByEmlData")) {
            log__.debug("emlファイルダウンロード");
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

        log__.debug("START_SML010");

        ActionForward forward = null;
        Sml010Form smlform = (Sml010Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        con.setAutoCommit(true);
        try {
            //アカウントが未選択の場合、デフォルトアカウントを設定する
            if (smlform.getSmlViewAccount() <= 0) {
                Sml010Biz biz = new Sml010Biz();
                smlform.setSmlViewAccount(
                        biz.getDefaultAccount(con, getSessionUserSid(req)));
            }

            //選択されているアカウントが使用可能かを判定する
            SmlCommonBiz biz = new SmlCommonBiz();
            if (!biz.canUseAccount(
                    con, getSessionUserSid(req), smlform.getSmlViewAccount())) {
                return getAuthErrorPage(map, req);
            }
        } finally {
            con.setAutoCommit(false);
        }

        //個人設定ボタン押下
        if (cmd.equals("kojinEdit")) {
            log__.debug("個人設定ボタン押下");
            forward = map.findForward("kojinEdit");
            __deleteAllTempDir(req);

        //管理者設定ボタン押下
        } else if (cmd.equals("admConf")) {
            log__.debug("管理者設定ボタン押下");
            forward = map.findForward("smlAdmConf");
            __deleteAllTempDir(req);

        //再読込ボタン押下
        } else if (cmd.equals("saiyomikomi")) {
            log__.debug("再読込ボタン押下");
            smlform.setSml010ProcMode(GSConstSmail.TAB_DSP_MODE_JUSIN);
            forward = __doChangeMode(map, smlform, req, res, con);
        //新規作成ボタン押下
        } else if (cmd.equals("msg_create")) {
            log__.debug("新規作成ボタン押下");
            forward =
                __setSmsgParam(map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_NEW, 0);
        //ひな形ボタン押下
        } else if (cmd.equals("hina_edit")) {
            log__.debug("ひな形ボタン押下");
            __deleteAllTempDir(req);
            return map.findForward("hinaEdit");

        //削除ボタン押下
        } else if (cmd.equals("msg_delete")) {
            log__.debug("削除ボタン押下");
            forward = __doDeleteConfirmation(map, smlform, req, res, con);
        //削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteOk")) {
            log__.debug("削除OKボタン押下");
            forward = __doDeleteOk(map, smlform, req, res, con);
        //元に戻すボタン押下
        } else if (cmd.equals("revived")) {
            log__.debug("元に戻すボタン押下");
            forward = __doRevivedConfirmation(map, smlform, req, res, con);
        //元に戻す処理確認画面でOKボタン押下
        } else if (cmd.equals("revivedOk")) {
            log__.debug("元に戻すOKボタン押下");
            forward = __doRevivedOk(map, smlform, req, res, con);
        //メール件名クリック(受信、送信モード)
        } else if (cmd.equals("moveDetail")) {
            log__.debug("メール件名クリック");
            forward = map.findForward("detail");
        //メール件名クリック(草稿モード)
        } else if (cmd.equals("moveMessage")) {
            log__.debug("メール件名クリック(草稿モード)");
            forward =
                __setSmsgParam2(map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_SOKO);
        //左矢印押下
        } else if (cmd.equals("arrorw_left")) {
            log__.debug("左矢印ボタン押下");
            forward = __doPageMinus(map, smlform, req, res, con);
        //右矢印押下
        } else if (cmd.equals("arrorw_right")) {
            log__.debug("右矢印ボタン押下");
            forward = __doPagePlus(map, smlform, req, res, con);
        //ゴミ箱を空にする画像クリック
        } else if (cmd.equals("gomibakoClear")) {
            log__.debug("ゴミ箱を空にする画像クリック");
            forward = __doClearConfirmation(map, smlform, req, res, con);
        //ゴミ箱を空にする確認画面でOKボタンクリック
        } else if (cmd.equals("clearOk")) {
            log__.debug("ゴミ箱の削除OKボタン押下");
            forward = __doClearOk(map, smlform, req, res, con);
        } else if (cmd.equals("smlSearch")) {
            log__.debug("ショートメール検索クリック");
            forward = __doSearch(map, smlform, req, res, con);
        //ユーザ一覧ユーザ名クリック
        } else if (cmd.equals("addUsr")) {
            log__.debug("ユーザ名クリック");
            forward = __setSmsgParam(
                    map, req, con, smlform,
                    GSConstSmail.MSG_CREATE_MODE_NEW, SELECT_USR_MODE_USRNAME);
        //ユーザ一覧宛先追加ボタンクリック
        } else if (cmd.equals("addUsrAtesaki")) {
            log__.debug("宛先追加ボタンクリック");
            forward = __setSmsgParam(
                    map, req, con, smlform,
                    GSConstSmail.MSG_CREATE_MODE_NEW, SELECT_USR_MODE_ATESAKI);
        //ユーザ一覧CC追加ボタンクリック
        } else if (cmd.equals("addUsrCc")) {
            log__.debug("CC追加ボタンクリック");
            forward = __setSmsgParam(
                    map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_NEW, SELECT_USR_MODE_CC);
        //ユーザ一覧BCC追加ボタンクリック
        } else if (cmd.equals("addUsrBcc")) {
            log__.debug("BCC追加ボタンクリック");
            forward = __setSmsgParam(
                    map, req, con, smlform, GSConstSmail.MSG_CREATE_MODE_NEW, SELECT_USR_MODE_BCC);
        //アカウント管理
        } else if (cmd.equals("accountConf")) {
            log__.debug("アカウント管理ボタン押下");
            forward = map.findForward("accountConf");
            __deleteAllTempDir(req);

        } else if (cmd.equals("calledWebmail")) {
            log__.debug("WEBメール連携");
            smlform.setSml010scriptFlg(GSConstSmail.SCRIPT_FIG_TRUE);
            smlform.setSml010scriptKbn(GSConstSmail.SCRIPT_WEB_MAIL);
            smlform.setSml020SendAccount(smlform.getSmlViewAccount());
            forward = __doInit(map, smlform, req, res, con);

        //初期データ取得
        } else if (cmd.equals("getInitData")) {
            __getInitData(map, smlform, req, res, con);
        //左矢印押下
        } else if (cmd.equals("page_left")) {
            log__.debug("左矢印ボタン押下");
            __getPageMinus(map, smlform, req, res, con);
        //右矢印押下
        } else if (cmd.equals("page_right")) {
            log__.debug("右矢印ボタン押下");
            __getPagePlus(map, smlform, req, res, con);
        //フォルダ変更
        } else if (cmd.equals("changeDir")) {
            log__.debug("フォルダ変更");
            __doChangeDir(map, smlform, req, res, con);
        //グループコンボ変更
        } else if (cmd.equals("changeGrpData")) {
            log__.debug("フォルダ変更");
            __getGroupData(map, smlform, req, res, con);
        //PDF出力ボタン押下
        } else if (cmd.equals("msg_pdfData")) {
            log__.debug("PDF出力ボタン押下");
            __doPdfConfirmationData(map, smlform, req, res, con);
        //PDF出力確認画面でOKボタン押下
        } else if (cmd.equals("exportByPdfData")) {
            log__.debug("PDF出力OKボタン押下");
            forward = __doExportByPdfDataOk(map, smlform, req, res, con);
        //eml出力ボタン押下
        } else if (cmd.equals("msg_emlData")) {
            log__.debug("eml出力ボタン押下");
            __doEmlConfirmationData(map, smlform, req, res, con);
        //eml出力確認画面でOKボタン押下
        } else if (cmd.equals("exportByEmlData")) {
            log__.debug("eml出力OKボタン押下");
            forward = __doExportByEmlDataOk(map, smlform, req, res, con);
        //削除ボタン押下
        } else if (cmd.equals("msg_deleteData")) {
            log__.debug("削除ボタン押下");
            __doDeleteConfirmationData(map, smlform, req, res, con);
        //削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteDataOk")) {
            log__.debug("削除OKボタン押下");
            __doDeleteDataOk(map, smlform, req, res, con);
        //元に戻すボタン押下
        } else if (cmd.equals("revivedData")) {
            log__.debug("元に戻すボタン押下");
            __doRevivedConfirmationData(map, smlform, req, res, con);
        //元に戻す処理確認画面でOKボタン押下
        } else if (cmd.equals("revivedDataOk")) {
            log__.debug("元に戻すOKボタン押下");
            __doRevivedDataOk(map, smlform, req, res, con);
         //ゴミ箱を空にする画像クリック
        } else if (cmd.equals("gomibakoDataClear")) {
            log__.debug("ゴミ箱を空にするクリック");
            __doClearConfirmationData(map, smlform, req, res, con);
        //ゴミ箱を空にする確認画面でOKボタンクリック
        } else if (cmd.equals("clearDataOk")) {
            log__.debug("ゴミ箱の削除OKボタン押下");
            __doClearDataOk(map, smlform, req, res, con);
        //検索グループコンボ変更
        } else if (cmd.equals("getSearchGrpUsr")) {
            log__.debug("検索グループのユーザ取得");
            __getSearchGrpUsr(map, smlform, req, res, con);
        //すべて既読にする
        } else if (cmd.equals("allRead")) {
            log__.debug("検索グループのユーザ取得");
            __doAllRead(map, smlform, req, res, con, GSConstSmail.OPKBN_OPENED);
        //すべて未読にする
        } else if (cmd.equals("allNoRead")) {
            log__.debug("検索グループのユーザ取得");
            __doAllRead(map, smlform, req, res, con, GSConstSmail.OPKBN_UNOPENED);
        //既読にする
        } else if (cmd.equals("read")) {
            log__.debug("検索グループのユーザ取得");
            __doRead(map, smlform, req, res, con, GSConstSmail.OPKBN_OPENED);
        //未読にする
        } else if (cmd.equals("noRead")) {
            log__.debug("検索グループのユーザ取得");
            __doRead(map, smlform, req, res, con, GSConstSmail.OPKBN_UNOPENED);
        //ラベルを追加する
        } else if (cmd.equals("msg_addLabel")) {
            log__.debug("ラベルの登録");
            __doCheckMail(map, smlform, req, res, con, 0);
        //ラベルを削除する
        } else if (cmd.equals("msg_deleteLabel")) {
            log__.debug("ラベルの削除");
            __doCheckMail(map, smlform, req, res, con, 0);
        //ラベルのデータを取得する
        } else if (cmd.equals("getLabelData")) {
            log__.debug("ラベルの取得");
            __getLabelData(map, smlform, req, res, con);
        } else if (cmd.equals("addMessageLabel")) {
        //ラベル追加
            __doMessageLabel(map, smlform, req, res, con, MSG_LABEL_ADD__);

        } else if (cmd.equals("delMessageLabel")) {
        //ラベル削除
            __doMessageLabel(map, smlform, req, res, con, MSG_LABEL_DEL__);
        //既読にする(一覧)
        } else if (cmd.equals("msg_kidokuData")) {
            log__.debug("ラベルの既読にする(一覧)");
            __doCheckMail2(map, smlform, req, res, con, 1);
        //未読にする(一覧)
        } else if (cmd.equals("msg_midokuData")) {
            log__.debug("未読にする(一覧)");
            __doCheckMail2(map, smlform, req, res, con, 2);
        //既読にする(一覧)
        } else if (cmd.equals("kidokuOkData")) {
            log__.debug("ラベルの既読にする(一覧)");
            __doSelRead(map, smlform, req, res, con, GSConstSmail.OPKBN_OPENED);
        //未読にする(一覧)
        } else if (cmd.equals("midokuOkData")) {
            log__.debug("未読にする(一覧)");
            __doSelRead(map, smlform, req, res, con, GSConstSmail.OPKBN_UNOPENED);
        //共有エラー
        } else if (cmd.equals("shareError")) {
            log__.debug("共有エラー");
            int kbn = 0;
            if (smlform.getSml010MailBodyLimit() == -100
                    || smlform.getSml010MailBodyLimit() == -101) {
                kbn = smlform.getSml010MailBodyLimit();
            }
            forward = __doErrorSubmit(map, smlform, req, res, con, kbn);
        //初期表示
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, smlform, req, res, con);
        }

        log__.debug("END_SML010");
        return forward;
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doSearch(ActionMapping map,
        Sml010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //入力チェック
        ActionErrors errors = form.validateSearchCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        return map.findForward("smlSearch");
    }

    /**
     * <br>[機  能] ページコンボ変更処理
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
    private ActionForward __doChangeMode(ActionMapping map,
                                          Sml010Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws SQLException {

        form.setSml010PageNum(1);
        form.setSml010Sort_key(GSConstSmail.MSG_SORT_KEY_DATE);
        form.setSml010Order_key(GSConstSmail.ORDER_KEY_DESC);
        form.setSml010DelSid(new String[0]);
        form.setSml010SelectedDelSid(new ArrayList<String>());

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 左矢印押下処理
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
    private ActionForward __doPageMinus(ActionMapping map,
                                         Sml010Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException {

        //ページ数取得
        int page = form.getSml010PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        //調整後ページ数セット
        form.setSml010PageNum(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 右矢印押下処理
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
    private ActionForward __doPagePlus(ActionMapping map,
                                        Sml010Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        //ページ数取得
        int page = form.getSml010PageNum();
        page += 1;

        //調整後ページ数セット
        form.setSml010PageNum(page);

        return __doInit(map, form, req, res, con);
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
                                    Sml010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //リクエストパラメータに送信先がある場合、フォームにセット
        if (req.getAttribute("sml010scriptFlg") != null) {
            form.setSml010scriptFlg(1);
            Object usrSidobj = req.getAttribute("cmn120userSid");
            if (usrSidobj != null) {
                __setSelUsrInf((String[]) usrSidobj, form, con);
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        Sml010Biz biz = new Sml010Biz();
        con.setAutoCommit(true);
        //処理モード取得
        String procMode = form.getSml010ProcMode();
        //初期表示データセット
        Sml010ParamModel paramMdl = new Sml010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, procMode, reqMdl, con, getAppRootPath());
        biz.setHinagataList(paramMdl, reqMdl, con);
        //WEBメール共有チェック
        PluginConfig pconfig =
                getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);

        biz.getSharePluginCheck(paramMdl, reqMdl, pconfig,  con, GSConst.PLUGINID_WML);

        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 選択ユーザ情報取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSids ユーザSID
     * @param form フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setSelUsrInf(String[] userSids,
                                Sml010Form form,
                                Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        try {
            CmnUsrmInfDao dao = new CmnUsrmInfDao(con);
            List<CmnUsrmInfModel> usrList = null;
            usrList = dao.getUsersDataList(userSids);
            if (usrList != null && !usrList.isEmpty()) {
                List<SmlAccountModel> accountList =
                                  new ArrayList<SmlAccountModel>();
                SmlAccountModel smlAccountMdl = null;
                for (CmnUsrmInfModel usrMdl : usrList) {
                    smlAccountMdl = new SmlAccountModel();
                    smlAccountMdl.setUsrSid(usrMdl.getUsrSid());
                    smlAccountMdl.setSacName(
                            usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
                    accountList.add(smlAccountMdl);
                }
                form.setSml010scriptUsrList(accountList);
            }
        } finally {
            con.setAutoCommit(false);
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
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteConfirmation(ActionMapping map,
                                                  Sml010Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.delete.message");

        ActionForward forward = null;
        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheck010(deleteMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            Sml010Biz biz = new Sml010Biz();

            //削除対象のメールタイトル一覧を取得する
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            ArrayList<SmailDetailModel> delList =
                biz.getTargetMailList(paramMdl, getRequestModel(req), con);
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
     * <br>[機  能] ゴミ箱を空にする確認
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
    private ActionForward __doClearConfirmation(ActionMapping map,
                                                 Sml010Form form,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res,
                                                 Connection con)
        throws SQLException {

        ActionForward forward = null;
        con.setAutoCommit(true);
        try {

            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            int cnt = biz.getGomibakoCnt(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);
            if (cnt > 0) {
                forward = __setClearConfirmationDsp(map, req, form);
            } else {
                forward = __doInit(map, form, req, res, con);
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            con.setAutoCommit(false);
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
                                        Sml010Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            //削除処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            biz.deleteMessage(paramMdl, reqMdl, con);
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

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.delete");
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n");

        //完了画面設定
        return __setCompDsp(map, req, form, 1);

    }

    /**
     * <br>[機  能] ゴミ箱を空にする確認画面でOKボタン押下
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
    private ActionForward __doClearOk(ActionMapping map,
                                       Sml010Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            //削除処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            biz.clearGomibako(paramMdl, reqMdl, con);
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

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n" + msgGomi);

        //完了画面設定
        return __setCompDsp(map, req, form, 3);
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
                                         Sml010Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            //復旧処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            biz.revivedMessage(paramMdl, reqMdl, con);
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
        String msg = gsMsg.getMessage("cmn.edit");
        String msgReturn = gsMsg.getMessage("cmn.undo");

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n" + msgReturn);

        //完了画面設定
        return __setCompDsp(map, req, form, 2);
    }

    /**
     * <br>[機  能] 元に戻す処理確認
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
    private ActionForward __doRevivedConfirmation(ActionMapping map,
                                                   Sml010Form form,
                                                   HttpServletRequest req,
                                                   HttpServletResponse res,
                                                   Connection con)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String revivedMessage = gsMsg.getMessage(req, "cmn.revived.message");

        ActionForward forward = null;
        con.setAutoCommit(true);
        try {
            //復旧対象選択チェック
            ActionErrors errors =
                form.validateSelectCheck010(revivedMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            Sml010Biz biz = new Sml010Biz();

            //復旧対象のメールタイトル一覧を取得する
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            ArrayList<SmailDetailModel> delList =
                biz.getTargetMailList(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);

            //復旧確認画面を設定
            forward = __setConfirmationDsp(map, req, form, delList, 2);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return forward;
    }

    /**
     * <br>[機  能] ショートメッセージ画面遷移パラメータ設定
     * <br>[解  説]
     * <br>[備  考] 下書きリンククリック時
     *
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @param form フォーム
     * @param mode 処理モード
     * @param usrAddMode ユーザ追加モード
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __setSmsgParam(ActionMapping map,
                                          HttpServletRequest req,
                                          Connection con,
                                          Sml010Form form,
                                          String mode,
                                          int usrAddMode)
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

        //下書きモード時は宛先を生成する
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {
            Sml010Biz biz = new Sml010Biz();
            ArrayList<AtesakiModel> ret =
                biz.getAtesaki(form.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_ATESAKI, con);

            String[] cmn120userSid = null;
            if (ret.isEmpty()) {
                cmn120userSid = new String[0];
            } else {
                cmn120userSid = new String[ret.size()];
                for (int i = 0; i < ret.size(); i++) {
                    AtesakiModel retMdl = (AtesakiModel) ret.get(i);
                    cmn120userSid[i] = String.valueOf(retMdl.getUsrSid());
                }
            }
            sml020Form.setCmn120userSid(cmn120userSid);

        } else if (usrAddMode == SELECT_USR_MODE_USRNAME) {
            //ユーザ一覧でユーザ名選択時
            String[] cmn120userSid = new String[1];
            cmn120userSid[0] = String.valueOf(form.getSml010usrSid());
            sml020Form.setSml020userSid(cmn120userSid);
        } else if (usrAddMode == SELECT_USR_MODE_ATESAKI) {
            //一括追加宛先
            sml020Form.setSml020userSid(form.getSml010usrSids());
        } else if (usrAddMode == SELECT_USR_MODE_CC) {
            //一括追加CC
            sml020Form.setSml020userSidCc(form.getSml010usrSids());
        } else if (usrAddMode == SELECT_USR_MODE_BCC) {
            //一括追加BCC
            sml020Form.setSml020userSidBcc(form.getSml010usrSids());
        }

        req.setAttribute("sml020Form", sml020Form);
        return map.findForward("createMsg");
    }

    /**
     * <br>[機  能] ショートメッセージ画面遷移パラメータ設定(草稿タブ時)
     * <br>[解  説]
     * <br>[備  考] 下書きリンククリック時
     *
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @param form フォーム
     * @param mode 処理モード
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __setSmsgParam2(ActionMapping map,
                                          HttpServletRequest req,
                                          Connection con,
                                          Sml010Form form,
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

        //下書きモード時は宛先を生成する
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {
            Sml010Biz biz = new Sml010Biz();
            ArrayList<AtesakiModel> ret =
                biz.getAtesaki2(form.getSml010SelectedSid(),
                                GSConstSmail.SML_SEND_KBN_ATESAKI, con);
            ArrayList<AtesakiModel> retCc =
                biz.getAtesaki2(form.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_CC, con);
            ArrayList<AtesakiModel> retBcc =
                biz.getAtesaki2(form.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_BCC, con);

            String[] sml020userSid = null;
            String[] sml020userSidCc = null;
            String[] sml020userSidBcc = null;
            if (ret.isEmpty()) {
                sml020userSid = new String[0];
            } else {
                sml020userSid = new String[ret.size()];
                for (int i = 0; i < ret.size(); i++) {
                    AtesakiModel retMdl = (AtesakiModel) ret.get(i);
                    sml020userSid[i] = String.valueOf(retMdl.getUsrSid());
                }
            }

            if (retCc.isEmpty()) {
                sml020userSidCc = new String[0];
            } else {
                sml020userSidCc = new String[retCc.size()];
                for (int i = 0; i < retCc.size(); i++) {
                    AtesakiModel retMdl = (AtesakiModel) retCc.get(i);
                    sml020userSidCc[i] = String.valueOf(retMdl.getUsrSid());
                }
            }
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
        return map.findForward("createMsg");
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
                                                Sml010Form form,
                                                ArrayList<SmailDetailModel> delList,
                                                int kbn) {

        MessageResources msgRes = getResources(req);
        String mode = form.getSml010ProcMode();
        String delMsg = "";
        int midokuCnt = 0;
        GsMessage gsMsg = new GsMessage();

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
                        delMsg += "<font class=\"cl_fontWarn\">";
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
        cmn999Form.addHiddenParam("sml010SelectedDelSid", form.getSml010SelectedDelSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ゴミ箱を空にする確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
     private ActionForward __setClearConfirmationDsp(ActionMapping map,
                                                      HttpServletRequest req,
                                                      Sml010Form form) {

         Cmn999Form cmn999Form = new Cmn999Form();
         cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

         //OKボタンクリック時遷移先
         ActionForward forwardOk = map.findForward("redraw");
         cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=clearOk");

         //キャンセルボタンクリック時遷移先
         cmn999Form.setUrlCancel(forwardOk.getPath() + "?" + GSConst.P_CMD + "=clearCancel");

         //メッセージ
         MessageResources msgRes = getResources(req);
         cmn999Form.setMessage(msgRes.getMessage("conf.clear.gomibako"));

         //画面パラメータをセット
         cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
         cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
         cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
         cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
         cmn999Form.addHiddenParam("sml010SelectedDelSid", form.getSml010SelectedDelSid());
         cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());

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
                                        Sml010Form form,
                                        int kbn) {
        GsMessage gsMsg = new GsMessage();

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath());

        String msg = gsMsg.getMessage(req, "cmn.message");

        //メッセージ
        MessageResources msgRes = getResources(req);
        String mode = form.getSml010ProcMode();
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

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }














    /**   --------------------以下新規関数 --------------------            */



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
    private void __getInitData(ActionMapping map,
                                    Sml010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        JSONObject jsonData = new JSONObject();

        RequestModel reqMdl = getRequestModel(req);
        Sml010Biz biz = new Sml010Biz();
        con.setAutoCommit(true);
        //処理モード取得
        String procMode = form.getSml010ProcMode();
        //初期表示データセット
        Sml010ParamModel paramMdl = new Sml010ParamModel();
        paramMdl.setParam(form);
        jsonData = biz.getInitData(paramMdl, procMode, reqMdl, con);
        jsonData.element("success", true);
        con.setAutoCommit(false);

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
     * <br>[機  能] 左矢印押下処理
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
    private void __getPageMinus(ActionMapping map,
                                         Sml010Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws Exception {

        //ページ数取得
        int page = form.getSml010PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        //調整後ページ数セット
        form.setSml010PageNum(page);

        __getInitData(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 右矢印押下処理
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
    private void __getPagePlus(ActionMapping map,
                                        Sml010Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        //ページ数取得
        int page = form.getSml010PageNum();
        page += 1;

        //調整後ページ数セット
        form.setSml010PageNum(page);

        __getInitData(map, form, req, res, con);
    }

    /**
     * <br>[機  能] フォルダ変更処理
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
    private void __doChangeDir(ActionMapping map,
                                          Sml010Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws Exception {

        form.setSml010PageNum(1);
        form.setSml010Sort_key(GSConstSmail.MSG_SORT_KEY_DATE);
        form.setSml010Order_key(GSConstSmail.ORDER_KEY_DESC);
        form.setSml010DelSid(new String[0]);
        form.setSml010SelectedDelSid(new ArrayList<String>());

        __getInitData(map, form, req, res, con);
    }

    /**
     * <br>[機  能] グループ変更
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
    private void __getGroupData(ActionMapping map,
                                          Sml010Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws Exception {
        JSONObject jsonData = new JSONObject();

        RequestModel reqMdl = getRequestModel(req);
        Sml010Biz biz = new Sml010Biz();
        con.setAutoCommit(true);

        Sml010ParamModel paramMdl = new Sml010ParamModel();
        paramMdl.setParam(form);
        jsonData = biz.getGroupUsrData(paramMdl, reqMdl, con);
        jsonData.element("success", true);
        con.setAutoCommit(false);

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
                                                  Sml010Form form,
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
                form.validateSelectCheck010(deleteMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml010Biz biz = new Sml010Biz();

                //出力対象のメールタイトル一覧を取得する
                Sml010ParamModel paramMdl = new Sml010ParamModel();
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
//                int midokuCnt = 0;

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
//                                midokuCnt += 1;
                                delMsg += "<font class=\"cl_fontWarn\">";
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

                if (delList == null || delList.isEmpty()) {
                    ActionMessage msgs = null;
                    msgs = new ActionMessage("search.notfound.tdfkcode", deleteMessage);
                    StrutsUtil.addMessage(errors, msgs, "mailSid");
                    form.setErrorsList(__getJsonErrorMsg(req, errors));
                }

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
     * <br>[機  能] eml出力確認処理
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
                                                  Sml010Form form,
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
                form.validateSelectCheck010(deleteMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml010Biz biz = new Sml010Biz();

                //削除対象のメールタイトル一覧を取得する
                Sml010ParamModel paramMdl = new Sml010ParamModel();
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
//                int midokuCnt = 0;

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
//                                midokuCnt += 1;
                                delMsg += "<font class=\"cl_fontWarn\">";
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
                                                  Sml010Form form,
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
                form.validateSelectCheck010(deleteMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml010Biz biz = new Sml010Biz();

                //削除対象のメールタイトル一覧を取得する
                Sml010ParamModel paramMdl = new Sml010ParamModel();
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
                int midokuCnt = 0;

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
                                midokuCnt += 1;
                                delMsg += "<font class=\"cl_fontWarn\">";
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
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                        || mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {

                    if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                        messageList.add(msgRes.getMessage(
                                "sakujo.kakunin.list", msg, delMsg));
                    } else {
                        messageList.add(msgRes.getMessage(
                                "move.gomibako.mail", msg, delMsg));
                    }

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
                                        Sml010Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {
            //削除処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            biz.deleteMessage(paramMdl, reqMdl, con);
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

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.delete");
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n");

        //完了画面設定
        String msgs = gsMsg.getMessage(req, "cmn.message");

        //メッセージ
        List<String> messageList = new ArrayList<String>();
        MessageResources msgRes = getResources(req);
        String mode = form.getSml010ProcMode();
        //処理モード = 受信モード or 送信モード or 草稿モード
        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
            || mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

            messageList.add(
                    msgRes.getMessage("move.gomibako.object", msgs));
        //処理モード = ゴミ箱
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                || mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            //削除処理時
            messageList.add(
                    msgRes.getMessage("sakujo.kanryo.object", msgs));
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
            log__.error("jsonデータ送信失敗(削除完了データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] 元に戻す処理確認
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
    private void __doRevivedConfirmationData(ActionMapping map,
                                                   Sml010Form form,
                                                   HttpServletRequest req,
                                                   HttpServletResponse res,
                                                   Connection con)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String revivedMessage = gsMsg.getMessage(req, "cmn.revived.message");

        con.setAutoCommit(true);
        try {
            //復旧対象選択チェック
            ActionErrors errors =
                form.validateSelectCheck010(revivedMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml010Biz biz = new Sml010Biz();

                //復旧対象のメールタイトル一覧を取得する
                Sml010ParamModel paramMdl = new Sml010ParamModel();
                paramMdl.setParam(form);
                ArrayList<SmailDetailModel> delList =
                    biz.getTargetMailList(paramMdl, getRequestModel(req), con);
                paramMdl.setFormData(form);

                //復旧確認画面を設定
                MessageResources msgRes = getResources(req);
                String delMsg = "";

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

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

                String msg = gsMsg.getMessage(req, "cmn.message");

                List<String> messageList = new ArrayList<String>();
                messageList.add(
                        msgRes.getMessage(
                                "move.former.mail", msg, delMsg));
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
                log__.error("jsonデータ送信失敗(復旧データ)");
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
     * <br>[機  能] 復旧確認画面でOKボタン押下
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
    private void __doRevivedDataOk(ActionMapping map,
                                         Sml010Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            //復旧処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            biz.revivedMessage(paramMdl, reqMdl, con);
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
        String msg = gsMsg.getMessage("cmn.edit");
        String msgReturn = gsMsg.getMessage("cmn.undo");

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n" + msgReturn);

        String msgs = gsMsg.getMessage(req, "cmn.message");

        //メッセージ
        List<String> messageList = new ArrayList<String>();
        MessageResources msgRes = getResources(req);

        messageList.add(
                msgRes.getMessage("move.former.object", msgs));
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
            log__.error("jsonデータ送信失敗(復旧データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }




    /**
     * <br>[機  能] メールの選択チェック
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
    private void __doCheckMail(ActionMapping map,
                                                  Sml010Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con,
                                                  int checkKbn)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.mail");

        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheck010(deleteMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml010Biz biz = new Sml010Biz();

                //削除対象のメールタイトル一覧を取得する
                Sml010ParamModel paramMdl = new Sml010ParamModel();
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
//                int midokuCnt = 0;

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
//                                midokuCnt += 1;
                                delMsg += "<font class=\"cl_fontWarn\">";
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

//                    //未読メッセージが存在する場合は注意文言を表示
//                    if (midokuCnt > 0) {
//
//                    } else {
//                        messageList.add(msgRes.getMessage(
//                                        "move.gomibako.mail", msg, delMsg));
//                    }
//
//                //処理モード = ゴミ箱
//                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
//                    messageList.add(msgRes.getMessage(
//                                    "sakujo.kakunin.list", msg, delMsg));
                }


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
                                                  Sml010Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con,
                                                  int checkKbn)
        throws Exception {

        GsMessage gsMsg = new GsMessage();
        String deleteMessage = gsMsg.getMessage(req, "cmn.mail");


        //受信モード以外の場合メールを受信のみに設定
        if (!form.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            if (form.getSml010DelSid() != null && form.getSml010DelSid().length > 0) {
                List<String> jMeis = new ArrayList<String>();
                for (String mailKey : form.getSml010DelSid()) {
                    if (mailKey.startsWith(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                        jMeis.add(mailKey);
                    }
                }
                form.setSml010DelSid(jMeis.toArray(new String[jMeis.size()]));
            }
        }


        con.setAutoCommit(true);
        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheck010(deleteMessage, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                Sml010Biz biz = new Sml010Biz();

                //削除対象のメールタイトル一覧を取得する
                Sml010ParamModel paramMdl = new Sml010ParamModel();
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
//                int midokuCnt = 0;

                if (!delList.isEmpty()) {

                    for (int i = 0; i < delList.size(); i++) {

                        //処理モード = 受信モード
                        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                            SmailDetailModel ret = (SmailDetailModel) delList.get(i);

                            //対象が未読の場合(赤文字で強調表示)
                            if (ret.getSmjOpkbn() == GSConstSmail.OPKBN_UNOPENED) {
//                                midokuCnt += 1;
                                delMsg += "<font class=\"cl_fontWarn\">";
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
     * <br>[機  能] ゴミ箱を空にする確認
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
    private void __doClearConfirmationData(ActionMapping map,
                                                 Sml010Form form,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res,
                                                 Connection con)
        throws Exception {

        con.setAutoCommit(true);
        try {

            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            int cnt = biz.getGomibakoCnt(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);
            if (cnt > 0) {
                //メッセージ
                List<String> messageList = new ArrayList<String>();
                MessageResources msgRes = getResources(req);
                messageList.add(msgRes.getMessage("conf.clear.gomibako"));
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
                log__.error("jsonデータ送信失敗(復旧データ)");
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
     * <br>[機  能] ゴミ箱を空にする確認画面でOKボタン押下
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
    private void __doClearDataOk(ActionMapping map,
                                       Sml010Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            //削除処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            biz.clearGomibako(paramMdl, reqMdl, con);
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

        //メッセージ
        List<String> messageList = new ArrayList<String>();
        MessageResources msgRes = getResources(req);

        messageList.add(
                msgRes.getMessage("conf.clear.comp.gomibako"));
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
            log__.error("jsonデータ送信失敗(復旧データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }



    /**
     * <br>[機  能] 検索グループのユーザを取得する
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
    private void __getSearchGrpUsr(ActionMapping map,
                                       Sml010Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        Sml010Biz biz = new Sml010Biz();
        RequestModel reqMdl = getRequestModel(req);
        Sml010ParamModel paramMdl = new Sml010ParamModel();
        paramMdl.setParam(form);
        biz.setGroupUserCombo(paramMdl, reqMdl.getSmodel().getUsrsid(), con, reqMdl);
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
            log__.error("jsonデータ送信失敗(検索グループのユーザ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }


    /**
     * <br>[機  能] 受信メールの開封区分を変更する（すべて）
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
    private void __doAllRead(ActionMapping map,
                                       Sml010Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con,
                                       int kbn)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            //削除処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            Sml010Biz biz = new Sml010Biz();
            biz.allRead(paramMdl, reqMdl, con, kbn);
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
        String msg = gsMsg.getMessage("cmn.update");

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        String logKbn = "";
        if (kbn == GSConstSmail.OPKBN_OPENED) {
            logKbn += gsMsg.getMessage("cmn.all.read");
        } else if (kbn == GSConstSmail.OPKBN_UNOPENED) {
            logKbn += gsMsg.getMessage("sml.sml010.04");
        }
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, logKbn
                                             + "\n" + "[アカウント]" + sacMdl.getSacName());

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
                                       Sml010Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con,
                                       int kbn)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        Sml010Biz biz = new Sml010Biz();

        RequestModel reqMdl = getRequestModel(req);
        try {

            //受信モード以外の場合メールを受信のみに設定
            if (!form.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                if (form.getSml010DelSid() != null && form.getSml010DelSid().length > 0) {
                    List<String> jMeis = new ArrayList<String>();
                    for (String mailKey : form.getSml010DelSid()) {
                        if (mailKey.startsWith(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                            try {
                                jMeis.add(String.valueOf(Integer.valueOf(mailKey)));
                            } catch (Exception e) {
                                log__.error("メールSIDの変換に失敗");
                            }

                        }
                    }
                    form.setSml010DelSid(jMeis.toArray(new String[jMeis.size()]));
                }
            }

            //削除処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
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
        String msg = gsMsg.getMessage("cmn.update");

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());
        String logKbn = "";
        if (kbn == GSConstSmail.OPKBN_OPENED) {
            logKbn += gsMsg.getMessage("cmn.mark.read");
        } else if (kbn == GSConstSmail.OPKBN_UNOPENED) {
            logKbn += gsMsg.getMessage("sml.sml010.02");
        }
        String logMsg = biz.getLogReadMultiMessage(form.getSml010DelSid(), con);

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, logKbn
                                             + "\r\n[アカウント]" + sacMdl.getSacName()
                                             + "\r\n[対象]" + logMsg);

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

    /**
     * <br>[機  能] 受信メールの開封区分を変更する（指定）
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
    private void __doRead(ActionMapping map,
                                       Sml010Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con,
                                       int kbn)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        Sml010Biz biz = new Sml010Biz();

        RequestModel reqMdl = getRequestModel(req);
        try {

            //削除処理実行
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);
            biz.selRead(paramMdl, reqMdl, con, kbn);
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
        String msg = gsMsg.getMessage("cmn.update");

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());
        String logMsg = biz.getLogReadMessage(form.getSml010SelectedSid(), con);
        String logKbn = "";
        if (kbn == GSConstSmail.OPKBN_OPENED) {
            logKbn += gsMsg.getMessage("cmn.mark.read");
        } else if (kbn == GSConstSmail.OPKBN_UNOPENED) {
            logKbn += gsMsg.getMessage("sml.sml010.02");
        }

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                msg, GSConstLog.LEVEL_TRACE, logKbn
                                             + "\r\n[アカウント]" + sacMdl.getSacName()
                                             + "\r\n[タイトル]" + logMsg);

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
            log__.error("jsonデータ送信失敗(開封区分データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
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
                                            Sml010Form form,
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

            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setParam(form);

            Sml010Biz biz = new Sml010Biz();

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

        //ログ出力
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlViewAccount());

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutLog(map, reqMdl,
                opCode, GSConstLog.LEVEL_TRACE, "アカウント:" + sacMdl.getSacName()
                                             + "\n"
                                             + getInterMessage(req, GSConstSmail.LOG_VALUE_LABEL));

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
    private ActionForward __doExportByPdfDataOk(ActionMapping map,
                                        Sml010Form form,
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
    private ActionForward __createPdf(ActionMapping map, Sml010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOException, IOToolsException,
                    TempFileException, Exception {

        String procMode = "";

        //テンポラリディレクトリを作成
        RequestModel reqMdl = getRequestModel(req);
        Sml010Biz biz = new Sml010Biz();
        biz.clearTempDir(reqMdl);
        String outDir = biz.getTempDir(reqMdl);

        Sml010ParamModel paramMdl = new Sml010ParamModel();
        paramMdl.setParam(form);

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
        String zipDir = IOTools.replaceFileSep(outDir.toString());

        List<SmlPdfModel> smlPdfList = null;
        try {
                smlPdfList = biz.createSmlPdfList(paramMdl, con, reqMdl, appRootPath,
                        outTempDir, zipDir, topStr);
        } catch (IOToolsException | IOException e) {
            log__.error("ZIP作成に失敗", e);
            try {
                biz.clearTempDir(reqMdl);
            } catch (Exception e2) {
                log__.error("テンポラリ初期化に失敗", e);
            }
            return __setZipErrorDsp(map, req, form);

        }
        if (!smlPdfList.isEmpty()) {
            SmlPdfModel smlMdl = smlPdfList.get(0);
            String outBookName = smlMdl.getFileName();

            String outFilePath = IOTools.setEndPathChar(zipDir) + outBookName;
            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

            //TEMPディレクトリ削除
            biz.deleteTempDir(reqMdl);

            //ログ出力処理
            GsMessage gsMsg = new GsMessage();
            String downloadPdf = gsMsg.getMessage(req, "sml.167");
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());

            //ログ出力処理
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            for (SmlPdfModel pdfMdl : smlPdfList) {
                smlBiz.outPutLog(map, reqMdl, downloadPdf, GSConstLog.LEVEL_INFO,
                        "[Subject]"
                                + pdfMdl.getTitle()
                                + "\n" + "[Date]"
                                + pdfMdl.getDate()
                                + "\n" + "[From]"
                                + NullDefault.getString(pdfMdl.getSender(), "")
                                + "\n" + "[To]"
                                + NullDefault.getString(pdfMdl.getAtesaki(), "")
                                + "\n" + "[Cc]"
                                +  NullDefault.getString(pdfMdl.getAtesakiCC(), "")
                                + "\n" + "[Bcc]"
                                +  NullDefault.getString(pdfMdl.getAtesakiBCC(), "")
                        );
            }
            smlBiz.outPutLog(map, reqMdl,
                    downloadPdf, GSConstLog.LEVEL_INFO, "アカウント:" + sacMdl.getSacName()
                    + "\n"
                    + outBookName
                    );
        }
        paramMdl.setFormData(form);
        return null;
    }

    /**
     * <br>[機  能] eml出力確認画面でOKボタン押下
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
                                        Sml010Form form,
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
    private ActionForward __createEml(ActionMapping map, Sml010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        String procMode = "";

        //テンポラリディレクトリを作成
        RequestModel reqMdl = getRequestModel(req);
        Sml010Biz biz = new Sml010Biz();
        biz.clearTempDir(reqMdl);
        String outDir = biz.getTempDir(reqMdl);

        Sml010ParamModel paramMdl = new Sml010ParamModel();
        paramMdl.setParam(form);

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

        String zipDir = IOTools.replaceFileSep(outDir.toString());

        List<SmlPdfModel> smlEmlList = null;
        try {
            smlEmlList = biz.createSmlEml(paramMdl, con, reqMdl, appRootPath,
                        outTempDir, zipDir, topStr);
        } catch (IOToolsException | IOException e) {
            log__.error("ZIP作成に失敗", e);
            try {
                biz.clearTempDir(reqMdl);
            } catch (Exception e2) {
                log__.error("テンポラリ初期化に失敗", e);
            }
            return __setZipErrorDsp(map, req, form);

        }
        if (!smlEmlList.isEmpty()) {
            SmlPdfModel smlMdl = smlEmlList.get(0);
            String outBookName = smlMdl.getFileName();

            String outFilePath = IOTools.setEndPathChar(zipDir) + outBookName;
            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

            //TEMPディレクトリ削除
            biz.deleteTempDir(reqMdl);

            //ログ出力処理
            GsMessage gsMsg = new GsMessage();
            String downloadEml = "eml" + gsMsg.getMessage(req, "main.output");
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSmlViewAccount());
            paramMdl.setFormData(form);

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            for (SmlPdfModel emlDateMdl : smlEmlList) {
                smlBiz.outPutLog(map, reqMdl, downloadEml, GSConstLog.LEVEL_INFO,
                        "[Subject]"
                                + emlDateMdl.getTitle()
                                + "\n" + "[Date]"
                                + emlDateMdl.getDate()
                                + "\n" + "[From]"
                                + emlDateMdl.getSender()
                                + "\n" + "[To]"
                                +  NullDefault.getString(emlDateMdl.getAtesaki(), "")
                                + "\n" + "[Cc]"
                                +  NullDefault.getString(emlDateMdl.getAtesakiCC(), "")
                                + "\n" + "[Bcc]"
                                + NullDefault.getString(emlDateMdl.getAtesakiBCC(), "")
                        );
            }
            smlBiz.outPutLog(map, reqMdl,
                    downloadEml, GSConstLog.LEVEL_INFO, "アカウント:" + sacMdl.getSacName()
                                                 + "\n"
                                                 + outBookName);

        }
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
            HttpServletRequest req, Sml010Form form) {
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
     * <br>[機  能]   エラー処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param kbn 0:共有エラー -100:設定ファイル存在しないエラー -101:設定ファイル読めないエラー
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doErrorSubmit(ActionMapping map,
                                    Sml010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con,
                                    int kbn) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //メッセージセット
        MessageResources msgRes = getResources(req);
        String warnMsg = null;
        if (kbn == 0) {
            warnMsg = msgRes.getMessage(
                    "error.access.window.colse");
            urlForward = map.findForward("redraw");
        } else if (kbn == -100) {
            warnMsg = msgRes.getMessage(
                    "error.can.not.conffile.open.error");
            urlForward = map.findForward("main");
        } else if (kbn == -101) {
            warnMsg = msgRes.getMessage(
                    "error.can.not.conffile.read.error");
            urlForward = map.findForward("main");
        }
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(warnMsg);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");

    }

    /**
     * <br>[機  能] 全てのテンポラリディレクトリを削除する
     * <br>[解  説] 削除対象: ショートメール一覧、作成、詳細、検索画面
     * <br>[備  考] 画面遷移時に使用する
     * @param req リクエスト
     */
    private void __deleteAllTempDir(HttpServletRequest req) {
        RequestModel reqMdl = getRequestModel(req);

        //ショートメール一覧
        Sml010Biz biz010 = new Sml010Biz();
        biz010.deleteTempDir(reqMdl);

        //ショートメール作成
        Sml020Biz biz020 = new Sml020Biz(reqMdl);
        biz020.deleteTempDir();

        //ショートメール詳細
        Sml030Biz biz030 = new Sml030Biz(reqMdl);
        biz030.deleteTempDir();

        //ショートメール検索
        Sml090Biz biz090 = new Sml090Biz(reqMdl);
        biz090.deleteTempDir();
    }
}