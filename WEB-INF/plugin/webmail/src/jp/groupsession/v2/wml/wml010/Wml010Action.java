package jp.groupsession.v2.wml.wml010;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.exception.GSAttachFileNotExistException;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlTempFileBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.exception.WmlDiskSizeOverException;
import jp.groupsession.v2.wml.exception.WmlMailServerConnectException;
import jp.groupsession.v2.wml.exception.WmlMailSizeOverException;
import jp.groupsession.v2.wml.exception.WmlTempDirNoneException;
import jp.groupsession.v2.wml.exception.WmlTempFileNameException;
import jp.groupsession.v2.wml.model.WmlSendResultModel;
import jp.groupsession.v2.wml.pdf.WmlPdfModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;
import jp.groupsession.v2.wml.wml010.model.Wml010ExportFileModel;

/**
 * <br>[機  能] WEBメール メール一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010Action extends AbstractWebmailAction {

    /** メッセージに対するラベル付与 ラベル追加 */
    private static final int MSG_LABEL_ADD__ = 0;
    /** メッセージに対するラベル付与 ラベル削除 */
    private static final int MSG_LABEL_DEL__ = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml010Action.class);

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

        return (cmd.equals("downloadFile") || cmd.equals("sendFileDownload")
                || cmd.equals("exportMail") || cmd.equals("exportPdf"));
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        ActionForward forward = null;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Wml010Form thisForm = (Wml010Form) form;

        con.setAutoCommit(true);
        try {
            //ユーザが利用可能なアカウントが存在するかを判定する
            forward = __desideViewMailList(con, map, req);
            if (forward != null) {
                return forward;
            }

            //アカウントが未選択の場合、デフォルトアカウントを設定する
            if ((cmd.length() == 0 || cmd.equals("back") || cmd.equals("man002back"))
                    && StringUtil.isNullZeroString(thisForm.getWmlViewAccount())) {
                Wml010Biz biz = new Wml010Biz();
                thisForm.setWmlViewAccount(
                        String.valueOf(biz.getDefaultAccount(con, getSessionUserSid(req))));
            }

            //選択されているアカウントが使用可能かを判定する
            Wml010ParamModel paramMdl = new Wml010ParamModel(thisForm);
            Wml010Biz biz = new Wml010Biz();
            if (!biz.canUseAccount(con, paramMdl, getSessionUserSid(req))) {
                return null;
            }
        } finally {
            con.setAutoCommit(false);
        }

        //ファイルサイズがMAXサイズを超えた場合、共通メッセージ画面でエラーメッセージを表示
        Object obj = req.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
        if (obj != null) {
            Boolean maxlength = (Boolean) obj;
            if (maxlength.booleanValue()) {
                return getFileSizeErrorPage(map, req, res);
            }
        }

        if (cmd.equals("accountConf")) {
            //アカウント管理ボタンクリック
            forward = map.findForward("userAccountList");

            //テンポラリディレクトリ削除
            __deleteTempDir(req, thisForm);
        } else if (cmd.equals("admTool")) {
            //管理者設定ボタンクリック
            forward = map.findForward("admTool");

            //テンポラリディレクトリ削除
            __deleteTempDir(req, thisForm);
        } else if (cmd.equals("psnTool")) {
            //個人設定ボタンクリック
            forward = map.findForward("psnTool");

            //テンポラリディレクトリ削除
            __deleteTempDir(req, thisForm);
        } else if (cmd.equals("getNewMail")) {
            //新着メールを確認ボタンクリック
            forward = __doGetNewMail(map, thisForm, req, res, con);

        } else if (cmd.equals("getMailList")) {
            //メール一覧取得
            forward = __doGetMailList(map, thisForm, req, res, con);

        } else if (cmd.equals("getTreeData")) {
            //ディレクトリ、ラベル情報(ツリー情報)取得
            forward = __doGetTreeList(map, thisForm, req, res, con);

        } else if (cmd.equals("getShainTreeData")) {
            //ユーザ情報取得
            forward = __doGetShainTreeList(map, thisForm, req, res, con);

        } else if (cmd.equals("getAddressTreeData")) {
            //アドレス帳情報取得
            forward = __doGetAddressTreeList(map, thisForm, req, res, con);

        } else if (cmd.equals("getDestlistTreeData")) {
            //送信先リスト取得
            forward = __doGetDestlistTreeList(map, thisForm, req, res, con);

        } else if (cmd.equals("getDestlistAddress")) {
            //送信先リスト メールアドレス取得(送信先リスト一覧のリンクをクリック)
            forward = __doGetDestlistAddress(map, thisForm, req, res, con);

        } else if (cmd.equals("downloadFile")) {
            //添付ファイルダウンロード
            forward = __doDownloadFile(map, thisForm, req, res, con);

        } else if (cmd.equals("allTmpExp")) {
            //添付ファイル一括ダウンロード
            forward = __doDownloadAllFile(map, thisForm, req, res, con);

        } else if (cmd.equals("detailSearch")) {
            //詳細検索
            forward = __doSearchDetail(map, thisForm, req, res, con);

        } else if (cmd.equals("dustMail")) {
            //メールの削除
            forward = __doDustMail(map, thisForm, req, res, con);

        } else if (cmd.equals("emptyTrash")) {
            //ゴミ箱を空にする
            forward = __doEmptyTrash(map, thisForm, req, res, con);

        } else if (cmd.equals("addMessageLabel")) {
            //ラベル追加
            forward = __doMessageLabel(map, thisForm, req, res, con, MSG_LABEL_ADD__);

        } else if (cmd.equals("delMessageLabel")) {
            //ラベル削除
            forward = __doMessageLabel(map, thisForm, req, res, con, MSG_LABEL_DEL__);

        } else if (cmd.equals("createSendMail")) {
            //メール作成
            __setSendMailInitData(map, thisForm, req, res, con, GSConstWebmail.SEND_TYPE_NORMAL);

        } else if (cmd.equals("replyMail")) {
            //メール返信
            __setSendMailInitData(map, thisForm, req, res, con, GSConstWebmail.SEND_TYPE_REPLY);

        } else if (cmd.equals("replyMailAll")) {
            //全員に返信
            __setSendMailInitData(map, thisForm, req, res, con, GSConstWebmail.SEND_TYPE_REPLY_ALL);

        } else if (cmd.equals("forwardMail")) {
            //メール転送
            __setSendMailInitData(map, thisForm, req, res, con, GSConstWebmail.SEND_TYPE_FORWARD);

        } else if (cmd.equals("editMail")) {
            //メール編集
            __setSendMailInitData(map, thisForm, req, res, con, GSConstWebmail.SEND_TYPE_EDIT);

        } else if (cmd.equals("exportMailCheck")) {
            //eml形式でエクスポート チェック
            __doExportCheck(map, thisForm, req, res, con);

        } else if (cmd.equals("exportPdf")) {
            //pdf形式でエクスポート
            __doExportPdf(map, thisForm, req, res, con);

        } else if (cmd.equals("exportMail")) {
            //eml形式でエクスポート
            __doExport(map, thisForm, req, res, con);

        } else if (cmd.equals("emlOutput")) {
            //eml出力
            forward = __doEmlOutput(map, thisForm, req, res, con);

        } else if (cmd.equals("readedMail")) {
            //メールを既読にする
            __doChangeMailReaded(map, thisForm, req, res, con, Wml010Const.MAIL_READTYPE_READED);

        } else if (cmd.equals("noReadMail")) {
            //メールを未読にする
            __doChangeMailReaded(map, thisForm, req, res, con, Wml010Const.MAIL_READTYPE_NOREAD);

        } else if (cmd.equals("readedMailAll")) {
            //メールを既読にする(ディレクトリ)
            __doChangeMailReadedInDirectory(map, thisForm, req, res, con,
                    Wml010Const.MAIL_READTYPE_READED);

        } else if (cmd.equals("noReadMailAll")) {
            //メールを未読にする
            __doChangeMailReadedInDirectory(map, thisForm, req, res, con,
                    Wml010Const.MAIL_READTYPE_NOREAD);

        } else if (cmd.equals("sendMail")) {
            //メール送信
            __doSendMail(map, thisForm, req, res, con);

        } else if (cmd.equals("validateSendMail")) {
            //メール送信 入力チェック
            __validateSendMail(map, thisForm, req, res, con);

        } else if (cmd.equals("draftMail")) {
            //草稿に保存
            __doDraftMail(map, thisForm, req, res, con);

        } else if (cmd.equals("keepMail")) {
            //メール保管
            __doKeepMail(map, thisForm, req, res, con);

        } else if (cmd.equals("sendFileUpload")) {
            //送信メール添付ファイルアップロード
            __doSendFileUpload(map, thisForm, req, res, con);

        } else if (cmd.equals("sendFileDownload")) {
            //送信メール添付ファイルダウンロード
            forward = __doSendFileDownload(map, thisForm, req, res, con);

        } else if (cmd.equals("sendFileDelete")) {
            //送信メール添付ファイル削除
            forward = __doSendFileDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("fileDeleteFromDetail")) {
            //メール添付ファイル削除(詳細画面から)
            forward = __doFileDeleteFromDetail(map, thisForm, req, res, con);

        } else if (cmd.equals("moveMessage")) {
            //メールの移動
            forward = __doMoveMessage(map, thisForm, req, res, con);

        } else if (cmd.equals("changeSendAccount")) {
            //送信アカウントの変更
            __doChangeSendAccount(thisForm, res, con);

        } else if (cmd.equals("changeSendSign")) {
            //送信アカウント 署名の変更
            __doChangeSendSign(thisForm, res, con);

        } else if (cmd.equals("setMailTemplate")) {
            //送信アカウント メールテンプレートの反映
            __doSetMailTemplate(thisForm, req, res, con);

        } else if (cmd.equals("readSelectMail")) {
            //既読(複数)
            __doChangeSelectMailReaded(map, thisForm, req, res, con,
                    Wml010Const.MAIL_READTYPE_READED);

        } else if (cmd.equals("unreadSelectMail")) {
            //未読(複数)
            __doChangeSelectMailReaded(map, thisForm, req, res, con,
                    Wml010Const.MAIL_READTYPE_NOREAD);
        } else if (cmd.equals("shareDialogClose")) {
            //共有ダイアログ閉じる
            __doTempDirDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("editTabClose")) {
            //タブ閉じる
            __doTempDirDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("changeAccount")) {
            //アカウント変更
            __deleteTempDir(req, thisForm);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("autoSave")) {
            //自動保存処理
            __doAutoSave(map, thisForm, req, res, con);

        } else if (cmd.equals("sendBodyFileUpload")) {
            //メール本文画像ファイルファイルアップロード
            __doSendBodyFileUpload(map, thisForm, req, res, con);

        } else if (cmd.equals("getBodyFile")) {
            //メール本文内画像ダウンロード
            forward = __doGetBodyFile(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 表示アカウントが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param map アクションマッピング
     * @param req リクエスト
     * @return ActionForward 表示アカウントが存在する場合はnull
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __desideViewMailList(Connection con, ActionMapping map,
            HttpServletRequest req)
                    throws SQLException {

        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            return getAuthErrorPage(map, req);
        }

        ActionForward forward = null;
        Wml010Biz biz = new Wml010Biz();
        int result = biz.checkViewMailList(con, userMdl);

        if (result != Wml010Const.MAILLIST_VIEW_OK) {

            Cmn999Form cmn999Form = new Cmn999Form();
            String msgKey = null;

            if (result == Wml010Const.MAILLIST_VIEW_MOVEMAIN) {
                msgKey = "warn.not.use.webmail";
                forward = map.findForward("gf_main");
            } else {

                CommonBiz cmnBiz = new CommonBiz();
                boolean adminUser
                = cmnBiz.isPluginAdmin(con, userMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);

                if (adminUser) {
                    cmn999Form.addHiddenParam("wmlAccountMode",
                            GSConstWebmail.ACCOUNTMODE_COMMON);
                } else {
                    cmn999Form.addHiddenParam("wmlAccountMode",
                            GSConstWebmail.ACCOUNTMODE_NORMAL);
                }

                msgKey = "warn.not.exist.account";
                forward = map.findForward("confAccount");
            }

            ActionMessage msg = new ActionMessage(msgKey);
            ActionMessages messages = new ActionMessages();
            messages.add(msgKey, msg);
            addMessages(req, messages);

            cmn999Form.setType(Cmn999Form.TYPE_OK);
            MessageResources msgRes = getResources(req);
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            cmn999Form.setUrlOK(forward.getPath());

            //メッセージセット
            cmn999Form.setMessage(msgRes.getMessage(msgKey));

            //画面パラメータをセット
            req.setAttribute("cmn999Form", cmn999Form);
            forward = map.findForward("gf_msg");
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        BaseUserModel buMdl = getSessionUserModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        form.setWml010adminUser(adminUser);

        con.setAutoCommit(true);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        biz.setInitData(con, paramMdl, getAppRootPath(),
                getSessionUserSid(req), getRequestModel(req));
        paramMdl.setFormData(form);
        form.setWml010pluginAddressUse(_getUseAddressPluginKbn(req, con));
        form.setWml010pluginUserUse(_getUseUserPluginKbn(req, con));
        form.setWml010pluginCircularUse(__getUsePluginKbn(req, con, GSConst.PLUGINID_CIR));
        form.setWml010pluginSmailUse(__getUsePluginKbn(req, con, GSConst.PLUGINID_SML));
        form.setWml010pluginFilekanriUse(__getUsePluginKbn(req, con, GSConst.PLUGIN_ID_FILE));
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 新着メールを確認ボタンクリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetNewMail(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        boolean commit = false;
        Connection receiveCon = null;
        RequestModel reqMdl = getRequestModel(req);
        try {
            JDBCUtil.closeConnection(con);
            con = null;

            receiveCon = getConnection(req);
            Wml010ParamModel paramMdl = new Wml010ParamModel(form);
            Wml010Biz biz = new Wml010Biz();
            biz.readNewMail(receiveCon, paramMdl, getGsContext(),
                    (MessageResources) getGsContext().get(GSContext.MSG_RESOURCE), reqMdl);
            paramMdl.setFormData(form);

            if (receiveCon != null && !receiveCon.isClosed()) {
                receiveCon.commit();
                commit = true;
            }
        } catch (Exception e) {
            log__.info("新着メールの取得に失敗", e);
            throw e;
        } finally {
            try {
                if (!commit && !receiveCon.isClosed()) {
                    receiveCon.rollback();
                }
            } finally {
                closeConnection(receiveCon);
            }
        }

        ActionForward forward = null;
        try {
            con = getConnection(req);
            //ログ出力
            GsMessage gsMsg = new GsMessage(req);
            Wml010ParamModel paramMdl = new Wml010ParamModel(form);

            int account = NullDefault.getInt(paramMdl.getWmlViewAccount(), 0);

            //アカウント名を出力する
            String accountName = "[" + gsMsg.getMessage("wml.102") + "] ";
            WmlAccountDao accountDao = new WmlAccountDao(con);
            accountName += accountDao.getAccountName(account);

            WmlBiz wbiz = new WmlBiz();
            wbiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.update"), GSConstLog.LEVEL_TRACE,
                    getInterMessage(req, GSConstWebmail.LOG_VALUE_NEWMAIL)
                    + accountName);

            PrintWriter writer = null;
            try {
                res.setContentType("text/json; charset=UTF-8");
                writer = res.getWriter();
                writer.write("{\"success\" : \"1\"}");
                writer.flush();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }


        } finally {
            closeConnection(con);
        }
        return forward;
    }

    /**
     * <br>[機  能] メール一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetMailList(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {
        con.setAutoCommit(true);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        biz.setMailData(paramMdl, getRequestModel(req), res, con, getSessionUserSid(req),
                getAppRootPath());
        paramMdl.setFormData(form);
        return null;
    }

    /**
     * <br>[機  能] ディレクトリ、ラベル情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetTreeList(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        Wml010Biz biz = new Wml010Biz();
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        biz.setTreeData(paramMdl, getRequestModel(req), res, con, getSessionUserSid(req),
                _getUseUserPluginKbn(req, con), _getUseAddressPluginKbn(req, con));
        paramMdl.setFormData(form);

        return null;
    }

    /**
     * <br>[機  能] ユーザ情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    protected ActionForward __doGetShainTreeList(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        biz.setShainTreeData(paramMdl, res, con,
                getSessionUserSid(req), _getUseUserPluginKbn(req, con));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return null;
    }

    /**
     * <br>[機  能] アドレス帳情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetAddressTreeList(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        biz.setAddressTreeData(paramMdl, res, con,
                getSessionUserSid(req), _getUseAddressPluginKbn(req, con));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return null;
    }

    /**
     * <br>[機  能] 送信先リスト情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetDestlistTreeList(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        biz.setDestlistTreeData(paramMdl, res, con,
                getSessionUserSid(req),
                _getUseUserPluginKbn(req, con),
                _getUseAddressPluginKbn(req, con));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return null;
    }

    /**
     * <br>[機  能] 送信先リスト メールアドレス取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetDestlistAddress(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {
        con.setAutoCommit(true);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        biz.setDestlistAddress(paramMdl, res, con,
                getSessionUserSid(req),
                _getUseUserPluginKbn(req, con),
                _getUseAddressPluginKbn(req, con));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return null;
    }

    /**
     * <br>[機  能] 詳細検索実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doSearchDetail(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        PrintWriter writer = null;
        RequestModel reqMdl = getRequestModel(req);
        try {

            String[] message = null;
            if (form.getWml010searchType() == Wml010Const.SEARCHTYPE_DETAIL) {
                message = form.validateSearchDetail(reqMdl);
            } else {
                message = form.validateSearchKeyword(reqMdl);
            }

            if (message == null) {
                if (form.getWml010searchType() == Wml010Const.SEARCHTYPE_DETAIL) {
                    form.setWml010svSearchFrom(form.getWml010searchFrom());
                    form.setWml010svSearchTo(form.getWml010searchTo());
                    form.setWml010svSearchToKbnTo(form.getWml010searchToKbnTo());
                    form.setWml010svSearchToKbnCc(form.getWml010searchToKbnCc());
                    form.setWml010svSearchToKbnBcc(form.getWml010searchToKbnBcc());
                    form.setWml010svSearchDateType(form.getWml010searchDateType());
                    form.setWml010svSearchDateYearFr(form.getWml010searchDateYearFr());
                    form.setWml010svSearchDateMonthFr(form.getWml010searchDateMonthFr());
                    form.setWml010svSearchDateDayFr(form.getWml010searchDateDayFr());
                    form.setWml010svSearchDateYearTo(form.getWml010searchDateYearTo());
                    form.setWml010svSearchDateMonthTo(form.getWml010searchDateMonthTo());
                    form.setWml010svSearchDateDayTo(form.getWml010searchDateDayTo());
                    form.setWml010svSearchTempFile(form.getWml010searchTempFile());
                    form.setWml010svSearchKeywordKbn(form.getWml010searchKeywordKbn());
                    form.setWml010svSearchKeyword(form.getWml010searchKeyword());
                    form.setWml010svSearchReadKbn(form.getWml010searchReadKbn());
                } else {
                    form.setWml010svSearchKeywordNml(form.getWml010searchKeywordNml());
                }

                form.setWml010searchFlg(1);
                __doGetMailList(map, form, req, res, con);
            } else {
                res.setContentType("text/json; charset=UTF-8");
                writer = res.getWriter();
                writer.write("{");
                writer.write("\"error\" : \"1\",");
                writer.write("\"errorMessage\" : [");
                for (int index = 0; index < message.length; index++) {
                    if (index > 0) {
                        writer.write(",");
                    }
                    writer.write("\"" + Wml010Biz._escapeText(message[index]) + "\"");
                }
                writer.write("]");
                writer.write("}");
                writer.flush();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }
    /**
     * <br>[機  能] 添付ファイルの一括ダウンロードを行う
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
    private ActionForward __doDownloadAllFile(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        log__.debug("添付ファイルの一括ダウンロード START");
        RequestModel reqMdl = getRequestModel(req);

        WmlDao wmlDao = new WmlDao(con);

        //メール権限チェック
        if (!wmlDao.canReadMail(form.getWml010downloadMessageNum(),
                reqMdl.getSmodel().getUsrsid())) {
            return getSubmitErrorPage(map, req);
        }


        Wml010Biz wml010Biz = new Wml010Biz();
        String appRootPath = getAppRootPath();
        //ディレクトリIDを生成
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDirId = tempPathUtil.getTempDirID(reqMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqMdl, tempDirId);
        try {
            LabelValueBean fileLabel = null;
            try {
                fileLabel = wml010Biz.doMakeZipAllTemp(con, form.getWml010downloadMessageNum(),
                        tempDir, appRootPath, reqMdl);
            } catch (IOToolsException | IOException e) {
                log__.error("ZIP作成に失敗", e);
                return __setZipErrorDsp(map, req, form);

            }
            if (fileLabel == null) {
                log__.error("添付ファイルが存在しないため、ダウンロードに失敗しました。");
                throw new Exception("添付ファイルが存在しないため、ダウンロードに失敗しました。");
            }

            //ログ出力
            log__.debug("ログ出力　開始");
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.download"), GSConstLog.LEVEL_INFO,
                    fileLabel.getLabel(),
                    "",
                    GSConstWebmail.WML_LOG_FLG_DOWNLOAD);
            log__.debug("ログ出力　完了");

            JDBCUtil.closeConnectionAndNull(con);
            log__.debug("コネクション切断");

            log__.debug("添付ファイルのダウンロード処理　実行");
            try {
                //ファイルをダウンロードする
                String charset = Encoding.UTF_8;
                TempFileUtil.downloadAtachment(req, res,
                        new File(fileLabel.getValue()),
                        fileLabel.getLabel(), charset);
                log__.debug("添付ファイルのダウンロード処理　完了");

                log__.info("添付ファイルのダウンロードが完了しました。");
            } catch (Throwable e) {
                log__.error("添付ファイルのダウンロードに失敗", e);
                con = null;
            }
        } finally {
            log__.debug("テンポラリの一時ファイルを削除　実行");
            wmlBiz.deleteTempDir(reqMdl, tempDirId);
            log__.debug("テンポラリの一時ファイルを削除　完了");

        }
        log__.debug("添付ファイルの一括ダウンロード END");
        return null;
    }

    /**
     * <br>[機  能] 添付ファイルのダウンロードを行う
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
    private ActionForward __doDownloadFile(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        log__.debug("添付ファイルのダウンロード START");

        RequestModel reqMdl = getRequestModel(req);
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();

        //テンポラリディレクトリを削除する
        tempPathUtil.deleteTempPath(reqMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL, "wml010", "html");

        String tempDir = tempPathUtil.getTempPath(reqMdl,
                GSConstWebmail.PLUGIN_ID_WEBMAIL,
                "wml010",
                "html");

        //メール権限チェック
        WmlDao wmlDao = new WmlDao(con);
        if (!wmlDao.canReadMail(form.getWml010downloadMessageNum(),
                reqMdl.getSmodel().getUsrsid())) {
            return getSubmitErrorPage(map, req);
        }

        WmlBiz wmlBiz = new WmlBiz();
        WmlTempfileModel fileMdl
        = wmlBiz.getTempFileData(con, form.getWml010downloadMessageNum(),
                form.getWml010downloadFileId(), reqMdl);

        if (fileMdl != null) {


            //ログ出力
            log__.debug("ログ出力　開始");
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.download"), GSConstLog.LEVEL_INFO,
                    fileMdl.getWtfFileName(),
                    String.valueOf(form.getWml010downloadFileId()),
                    GSConstWebmail.WML_LOG_FLG_DOWNLOAD);
            log__.debug("ログ出力　完了");

            JDBCUtil.closeConnectionAndNull(con);
            log__.debug("コネクション切断");

            log__.debug("添付ファイルのダウンロード処理　実行");
            try {
                //ファイルをダウンロードする
                String charset = null;
                if (!StringUtil.isNullZeroString(fileMdl.getWtfCharset())) {
                    charset = fileMdl.getWtfCharset();
                }
                if (fileMdl.getWtfFileName().equals(GSConstWebmail.HTMLMAIL_FILENAME)) {
                    wmlBiz.downloadHtmlForWebmail(
                            req, res, fileMdl, getAppRootPath(), charset, tempDir, false);
                } else {
                    TempFileUtil.downloadAtachmentForWebmail(
                            req, res, fileMdl, getAppRootPath(), charset);
                }
                log__.debug("添付ファイルのダウンロード処理　完了");
                log__.debug("テンポラリの一時ファイルを削除　実行");
                fileMdl.removeTempFile();
                log__.debug("テンポラリの一時ファイルを削除　完了");

                log__.info("添付ファイルのダウンロードが完了しました。");
            } catch (Throwable e) {
                log__.error("添付ファイルのダウンロードに失敗", e);
                con = null;
            }
        } else {
            log__.error("添付ファイルが存在しないため、ダウンロードに失敗しました。");
            throw new Exception("添付ファイルが存在しないため、ダウンロードに失敗しました。");
        }
        log__.debug("添付ファイルのダウンロード END");
        return null;
    }

    /**
     * <br>[機  能] メールの削除(ゴミ箱へ移動)を行う
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
    private ActionForward __doDustMail(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;
        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        try {
            String message = "";
            message = biz.dustMail(con, paramMdl, getSessionUserSid(req), reqMdl);

            writer = res.getWriter();
            writer.write("{\"message\":\"" + message + "\"}");
            writer.flush();

            if (message.equals("success")) {
                con.commit();
                commit = true;
            }
        } finally {
            paramMdl.setFormData(form);
            if (writer != null) {
                writer.close();
            }

            if (!commit) {
                con.rollback();
            }
        }
        //ログ出力
        biz._outputOpLog(map, con, reqMdl, paramMdl,
                Wml010Biz.LOGTYPE_MAILDELETE_);

        return null;
    }

    /**
     * <br>[機  能] 「ゴミ箱を空にする」処理を行う
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
    private ActionForward __doEmptyTrash(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {
        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;
        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();

        try {

            String message = "";
            message = biz.emptyTrash(con, paramMdl, getSessionUserSid(req), reqMdl);

            writer = res.getWriter();
            writer.write("{\"message\":\"" + message + "\"}");
            writer.flush();

            if (message.equals("success")) {
                con.commit();
                commit = true;
            }
        } finally {
            paramMdl.setFormData(form);
            if (writer != null) {
                writer.close();
            }

            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        GsMessage gsMsg = new GsMessage(req);
        String msg = gsMsg.getMessage("cmn.empty.trash") + "\r\n["
                + gsMsg.getMessage("wml.96") + "]" + biz.getAccountName(con, paramMdl);

        wmlBiz.outPutLog(map, getRequestModel(req), con,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_TRACE,
                msg);
        return null;
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
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int type) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;
        String opCode = "";

        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        try {
            con.setAutoCommit(false);

            String result = "";
            if (type == MSG_LABEL_ADD__) {
                result = biz.setLabelForMessage(map, reqMdl, res, con, paramMdl,
                        getCountMtController(req),
                        getSessionUserSid(req));
                opCode = getInterMessage(req, "cmn.entry");
            } else if (type == MSG_LABEL_DEL__) {
                result = biz.deleteLabelForMessage(con, paramMdl, reqMdl);
                opCode = getInterMessage(req, "cmn.delete");
            }

            writer = res.getWriter();
            writer.write(result);
            writer.flush();

            con.commit();
            commit = true;
        } finally {
            paramMdl.setFormData(form);

            if (writer != null) {
                writer.close();
            }

            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }
        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();

        long[] messageNum = form.getWml010selectMessageNum();
        int labelSid = 0;
        if (type == MSG_LABEL_ADD__) {
            labelSid = form.getWml010addLabel();
        } else {
            labelSid = form.getWml010delLabel();
        }
        int accountSid = Integer.parseInt(paramMdl.getWmlViewAccount());
        String msg = biz.getLabelLogMessage(getRequestModel(req), con, messageNum,
                labelSid, accountSid);

        wmlBiz.outPutLog(map, reqMdl, con,
                opCode, GSConstLog.LEVEL_TRACE,
                msg);
        return null;
    }

    /**
     * <br>[機  能] メールの未読/既読を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param readType メール未読/既読
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doChangeMailReaded(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int readType) throws SQLException, Exception {

        boolean commit = false;
        try {
            Wml010Biz biz = new Wml010Biz();
            Wml010ParamModel paramMdl = new Wml010ParamModel(form);
            biz.changeMailReaded(con, paramMdl, getRequestModel(req), readType);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("メールの未読/既読変更処理で例外発生", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] メールの未読/既読を変更する
     * <br>[解  説]
     * <br>[備  考] 選択されたメールを変更対象とする
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param readType メール未読/既読
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doChangeSelectMailReaded(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int readType) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;
        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);

        String message = "";
        boolean commit = false;
        try {
            Wml010Biz biz = new Wml010Biz();
            message = biz.changeMailReaded(con, paramMdl, reqMdl, readType);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("メールの未読/既読変更処理で例外発生", e);
            message = getInterMessage(reqMdl, "wml.failed.changemail");
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        try {
            writer = res.getWriter();
            writer.write("{\"message\":\"" + message + "\"}");
            writer.flush();
        } finally {
            paramMdl.setFormData(form);
            if (writer != null) {
                writer.close();
            }
        }
        return null;
    }

    /**
     * <br>[機  能] ディレクトリ内のメール未読/既読を全て変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param readType メール未読/既読
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doChangeMailReadedInDirectory(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int readType) throws SQLException, Exception {

        Wml010Biz biz = new Wml010Biz();
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        biz.changeMailReadedInDirectory(con, paramMdl, readType);
        paramMdl.setFormData(form);
        return __doGetTreeList(map, form, req, res, con);
    }

    /**
     * <br>[機  能] メールの保管(保管ディレクトリへ移動)を行う
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
    private ActionForward __doKeepMail(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;
        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        try {
            Wml010Biz biz = new Wml010Biz();
            String message = "";
            message = biz.keepMail(con, paramMdl, reqMdl);

            writer = res.getWriter();
            writer.write("{\"message\":\"" + message + "\"}");
            writer.flush();

            con.commit();
            commit = true;
        } finally {
            paramMdl.setFormData(form);
            if (writer != null) {
                writer.close();
            }

            if (!commit) {
                con.rollback();
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 送信メールの初期情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param type 送信種別
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __setSendMailInitData(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con,
            int type)
                    throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);

        //テンポラリディレクトリの削除 (テンポラリディレクトリIDが正常な場合のみ)
        Wml010Biz biz = new Wml010Biz();
        String wml010TempDirId = form.getWml010tempDirId();
        try {
            biz.checkDirId(wml010TempDirId);
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.deleteTempDir(reqMdl, wml010TempDirId);
        } catch (IOToolsException e) {
        }

        // 送信メール作成(テンポラリディレクトリIDは再生成する)
        biz.setSendMailData(paramMdl, reqMdl, res, con, type, getSessionUserSid(req),
                getAppRootPath());
        paramMdl.setFormData(form);

        return null;
    }

    /**
     * <br>[機  能] メールの送信処理を行う
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
    private ActionForward __doSendMail(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        PrintWriter writer = null;
        SmtpSendModel sendData = null;
        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        WmlBiz wmlBiz = new WmlBiz();
        boolean timeSent = false;

        try {
            //入力チェック
            if (!__validateSendMail(map, form, req, res, con)) {
                return null;
            }

            int sendResult = WmlSendResultModel.RESULT_SUCCESS;
            String[] message = null;
            boolean commit = false;
            try {
                boolean draft = false;
                boolean sentFlg = true;
                boolean beforeMail = false;

                String dirId = paramMdl.getWml010tempDirId();

                //ディレクトリIDのチェック
                biz.checkDirId(dirId);

                //HTMLメールの場合、メール本文内の画像ファイルリンクを変換する
                if (paramMdl.getWml010sendMailHtml() == GSConstWebmail.SEND_HTMLMAIL_HTML) {
                    String body = paramMdl.getWml010svSendContent();
                    body = biz._replaceBodyImageLink(body, dirId, reqMdl);
                    paramMdl.setWml010svSendContent(body);
                }

                String tempDir = biz.getSendTempDir(reqMdl, dirId);
                //通常の送信
                GsMessage gsMsg = new GsMessage(reqMdl);
                WmlSendResultModel resultMdl = null;
                try {
                    resultMdl = biz.sendMail(con, paramMdl, getGsContext(),
                            getSessionUserSid(req),
                            getAppRootPath(),
                            tempDir,
                            reqMdl);
                    boolean sendNowFlg = resultMdl.getSendMailData().getSendPlanDate() == null;
                    if (sendNowFlg) {
                        //通常送信の場合
                        sendResult = resultMdl.getResult();
                        sentFlg = sendResult == WmlSendResultModel.RESULT_SUCCESS;
                    } else {
                        //時間差送信，予約送信の場合
                        draft = true;
                        timeSent = true;
                        long beforeMailNum = paramMdl.getWml010sendMessageNum();
                        beforeMail = biz.existsMailData(con, beforeMailNum);
                    }

                } catch (WmlTempDirNoneException e) {
                    //テンポラリディレクトリがなかった時
                    String errorMessage = gsMsg.getMessage("wml.222", new String[] {
                                                    gsMsg.getMessage("cmn.main.temp.path")});
                    __writeErrorResponse(errorMessage, res);
                    return null;

                } catch (WmlMailSizeOverException e) {
                    //メールのサイズが上限を超えていた場合
                    String errorMessage
                        = gsMsg.getMessageVal0("wml.245", e.getMaxSizeText());
                    __writeErrorResponse(errorMessage, res);

                    return null;

                } catch (WmlMailServerConnectException e) {
                    //メールサーバへアクセスできなかった場合
                    String errorMessage = gsMsg.getMessage("wml.js.40");
                    __writeErrorResponse(errorMessage, res);
                    return null;

                } catch (WmlDiskSizeOverException e) {
                    //ディスクの容量制限を超えていた場合
                    String errorMessage = e.getErrorMsg();
                    __writeErrorResponse(errorMessage, res);

                    return null;

                } catch (WmlTempFileNameException e) {
                    //添付ファイル名が不正の場合
                    String errorMessage = e.getMessage();
                    __writeErrorResponse(errorMessage, res);

                    return null;
                }

                if (sentFlg) {
                    //送信成功
                    sendData = resultMdl.getSendMailData();
                    draft = sendData.isSendToDraft();
                } else {
                    //送信失敗
                    if (sendResult == WmlSendResultModel.RESULT_SIZEOVER) {
                        String[] mailSize = {Long.toString(
                                resultMdl.getMailMaxSize() / (1024 * 1024))
                                + "MB"};
                        message = new String[] {
                                gsMsg.getMessage("wml.245", mailSize)};
                    } else {
                        message = new String[] {gsMsg.getMessage("wml.js.40")};
                    }
                }

                if (sentFlg) {
                    con.commit();
                    commit = true;

                    res.setContentType("text/json; charset=UTF-8");
                    writer = res.getWriter();
                    writer.write("{");
                    writer.write("\"error\" : \"0\",");
                    writer.write("\"sendToDraft\" : " + draft + ",");
                    writer.write("\"timeSent\" : " + timeSent + ",");
                    writer.write("\"beforeMail\" : " + beforeMail);
                    writer.write("}");
                }
            } catch (Exception e) {
                log__.error("メールの送信に失敗", e);
                Throwable cause = GSAttachFileNotExistException.searchCause(e);
                if (cause != null) {
                    log__.error("", cause);
                    throw e;
                } else {
                    message = new String[] {getInterMessage(req, "wml.js.40")};

                }

            } finally {
                if (!commit) {
                    con.rollback();
                }
            }

            if (message != null) {
                //エラーログ出力
                if (sendResult != WmlSendResultModel.RESULT_SIZEOVER) {
                    String value = getInterMessage(req, "wml.js.40");
                    wmlBiz.outPutLog(map, reqMdl, con,
                            getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_ERROR,
                            StringUtil.trimRengeString(value, 3000));
                }

                //送信エラー
                res.setContentType("text/json; charset=UTF-8");
                if (writer == null) {
                    writer = res.getWriter();
                }
                writer.write("{");
                writer.write("\"error\" : \"1\",");
                writer.write("\"errorMessage\" : [");
                for (int index = 0; index < message.length; index++) {
                    if (index > 0) {
                        writer.write(",");
                    }
                    writer.write("\"" + Wml010Biz._escapeText(message[index]) + "\"");
                }
                writer.write("]");
                writer.write("}");
            }

            if (writer != null) {
                writer.flush();
            }

        } finally {
            paramMdl.setFormData(form);
            if (writer != null) {
                writer.close();
            }
        }

        //ログ出力
        if (sendData != null) {
            String value = null;
            if (timeSent) {
                value = getInterMessage(req, "wml.211") + "：";
                UDate sendPlanDate = sendData.getSendPlanDate();
                if (sendPlanDate == null) {
                    sendPlanDate = biz.getSendPlanDate(paramMdl);
                }
                value += " [" + getInterMessage(req, "wml.260") + "]"
                        + UDateUtil.getSlashYYMD(sendPlanDate)
                        + " " + UDateUtil.getSeparateHM(sendPlanDate)
                        + " ";
            } else {
                value = getInterMessage(req, "cmn.mail.send") + "：";
            }
            value += "[" + getInterMessage(req, "cmn.subject") + "]" + sendData.getSubject();
            value += " [From]" + NullDefault.getString(sendData.getFrom(), "");
            value += " [To]" + NullDefault.getString(sendData.getTo(), "");
            value += " [Cc]" + NullDefault.getString(sendData.getCc(), "");
            value += " [Bcc]" + NullDefault.getString(sendData.getBcc(), "");
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_TRACE,
                    StringUtil.trimRengeString(value, 3000));
        }

        return null;
    }

    /**
     * <br>[機  能] メール送信時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return true: エラーなし false:エラーあり
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private boolean __validateSendMail(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);

        boolean result = false;
        Wml010Biz biz = new Wml010Biz();
        String tempDir = biz.getSendTempDir(reqMdl,
                form.getWml010tempDirId());

        String[] message = form.validateSendMail(con, getGsContext(),
                getSessionUserSid(req),
                getAppRootPath(),
                tempDir,
                reqMdl);
        result = message == null;
        if (!result) {
            __writeErrorResponse(message, res);
        }
        return result;
    }

    /**
     * <br>[機  能] 入力チェック系の処理の結果を出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param message メッセージ
     * @param res レスポンス
     * @throws IOException 入出力例外
     */
    private void __writeErrorResponse(
        String[] message,
        HttpServletResponse res) throws IOException {

        PrintWriter writer = null;
        try {
            //送信エラー
            res.setContentType("text/json; charset=UTF-8");
            writer = res.getWriter();
            writer.write("{");
            writer.write("\"error\" : \"1\",");
            writer.write("\"errorMessage\" : [");
            for (int index = 0; index < message.length; index++) {
                if (index > 0) {
                    writer.write(",");
                }
                writer.write("\"" + Wml010Biz._escapeText(message[index]) + "\"");
            }
            writer.write("]");
            writer.write("}");

            if (writer != null) {
                writer.flush();
            }

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * <br>[機  能] 入力チェック系の処理の結果を出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param message メッセージ
     * @param res レスポンス
     * @throws IOException 入出力例外
     */
    private void __writeErrorResponse(
        String message,
        HttpServletResponse res) throws IOException {

        String[] messageAry = new String[] {message};
        __writeErrorResponse(messageAry, res);
    }

    /**
     * <br>[機  能] メールの草稿保存処理を行う
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
    private ActionForward __doDraftMail(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        paramMdl.setParam(form);

        PrintWriter writer = null;
        WmlSendResultModel result = null;
        SmtpSendModel sendData = null;
        try {
            //ディレクトリIDチェック
            String dirId = paramMdl.getWml010tempDirId();
            Wml010Biz biz = new Wml010Biz();
            biz.checkDirId(dirId);

            String tempDir = biz.getSendTempDir(reqMdl, dirId);

            String[] message = form.validateSendMailDraft(con, getGsContext(),
                    getSessionUserSid(req),
                    getAppRootPath(),
                    tempDir,
                    reqMdl, 0);

            if (message == null) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                boolean commit = false;
                try {
                    result = biz.saveDraftMail(con, paramMdl, getGsContext(),
                            getSessionUserSid(req), reqMdl, dirId);
                    sendData = result.getSendMailData();
                    con.commit();
                    commit = true;

                    res.setContentType("text/json; charset=UTF-8");
                    writer = res.getWriter();
                    writer.write("{");
                    writer.write("\"error\" : \"0\",");
                    writer.write("\"sendToDraft\" : true,");
                    writer.write("\"timeSent\" : false,");
                    writer.write("\"beforeMail\" : false");
                    writer.write("}");
                } catch (WmlTempDirNoneException e) {
                    //テンポラリディレクトリがなかった時
                    String errorMessage = gsMsg.getMessage("wml.222", new String[] {
                                                    gsMsg.getMessage("cmn.main.temp.path")});
                    __writeErrorResponse(errorMessage, res);
                    return null;

                } catch (WmlMailSizeOverException e) {
                    //メールのサイズが上限を超えていた場合
                    String errorMessage =
                        gsMsg.getMessageVal0("wml.245", e.getMaxSizeText());
                    __writeErrorResponse(errorMessage, res);

                    return null;

                } catch (WmlDiskSizeOverException e) {
                    //ディスクの容量制限を超えていた場合
                    String errorMessage = e.getErrorMsg();
                    __writeErrorResponse(errorMessage, res);
                    return null;

                } catch (WmlTempFileNameException e) {
                    //添付ファイル名が不正の場合
                    String errorMessage = e.getMessage();
                    __writeErrorResponse(errorMessage, res);

                    return null;

                } catch (Exception e) {
                    log__.error("メールの草稿保存に失敗", e);
                    Throwable cause = GSAttachFileNotExistException.searchCause(e);
                    if (cause != null) {
                        log__.error("", cause);
                        throw e;
                    } else {
                        message = new String[] {getInterMessage(req, "wml.wml010.29")};

                    }
                } finally {
                    if (!commit) {
                        con.rollback();
                    }
                }
            }

            if (message != null) {
                res.setContentType("text/json; charset=UTF-8");
                if (writer == null) {
                    writer = res.getWriter();
                }
                writer.write("{");
                writer.write("\"error\" : \"1\",");
                writer.write("\"errorMessage\" : [");
                for (int index = 0; index < message.length; index++) {
                    if (index > 0) {
                        writer.write(",");
                    }
                    writer.write("\"" + Wml010Biz._escapeText(message[index]) + "\"");
                }
                writer.write("]");
                writer.write("}");
            }
            writer.flush();
        } finally {
            paramMdl.setFormData(form);
            if (writer != null) {
                writer.close();
            }
        }
        //ログ出力
        if (sendData != null) {
            String value = getInterMessage(req, "cmn.save.draft") + "："
                    + " [" + getInterMessage(req, "cmn.subject") + "]" + sendData.getSubject()
                    + " [To]" + NullDefault.getString(sendData.getTo(), "")
                    + " [Cc]" + NullDefault.getString(sendData.getCc(), "")
                    + " [Bcc]" + NullDefault.getString(sendData.getBcc(), "");
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.entry"), GSConstLog.LEVEL_TRACE,
                    StringUtil.trimRengeString(value, 3000));
        }
        return null;
    }

    /**
     * <br>[機  能] 送信メール添付ファイルアップロード処理を行う
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
    private ActionForward __doSendFileUpload(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        PrintWriter writer = null;
        try {

            CmnFileConfDao cfcDao = new CmnFileConfDao(con);
            CmnFileConfModel cfcMdl = cfcDao.select();
            int ficMaxSize = cfcMdl.getFicMaxSize();
            long fileMaxSize = ficMaxSize * GSConstCommon.FILE_SIZE_1MB;

            Wml010Biz biz = new Wml010Biz();
            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);

            String tempDirId = "";
            if (form.getWml010smlShareFlg() == 1
                    && form.getWml010smlShareCloseFlg() == 0
                    && !StringUtil.isNullZeroString(form.getWml010smlShareTemp())
                    && !form.getWml010smlShareTemp().equals("undefined")) {
                tempDirId = form.getWml010smlShareTemp();
            } else {
                tempDirId = form.getWml010tempDirId();
                form.setWml010smlShareTemp(form.getWml010tempDirId());
            }
            String tempDirPath = biz.getSendTempDir(reqMdl, tempDirId);

            StringBuilder fileMessage = new StringBuilder();
            fileMessage.append("[");

            if (!tempDirPath.isEmpty()) {
                int fileIdx = 0;
                for (; fileIdx < form.getWml010sendMailFileForm().size(); fileIdx++) {
                    if (fileIdx > 0) {
                        fileMessage.append(",");
                    }

                    long fileSize = 0;
                    FormFile tempFile = form.getWml010sendMailFileForm().get(fileIdx);
                    if (tempFile != null) {
                        fileSize = tempFile.getFileSize();
                    }

                    if (fileSize <= 0) {
                        fileMessage.append("{");
                        fileMessage.append("\"error\" : \"1\",");
                        fileMessage.append("\"errorMessage\" : \""
                                + gsMsg.getMessage("cmn.select.file") + "\"");
                        fileMessage.append("}");
                    } else if (fileSize > fileMaxSize) {
                        //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
                        fileMessage.append("{");
                        fileMessage.append("\"error\" : \"1\",");
                        fileMessage.append("\"errorMessage\" : \""
                                + gsMsg.getMessage("cmn.cmn110.error.input.capacity.over",
                                        new String[] {ficMaxSize + "MB"})
                                + "\",");
                        fileMessage.append("\"fileName\" : \""
                                + gsMsg.getMessage("cmn.cmn110.8",
                                        new String[] {Wml010Biz._escapeTextView(
                                                tempFile.getFileName())})
                                + "\"");
                        fileMessage.append("}");
                    } else {
                        fileMessage.append("{");
                        fileMessage.append("\"error\" : \"0\",");
                        fileMessage.append("\"fileName\" : \""
                                + Wml010Biz._escapeTextView(tempFile.getFileName()) + "\",");
                        if (fileSize < 1024) {
                            fileMessage.append("\"fileSize\" : \"" + fileSize + " Byte\",");
                        } else {
                            BigDecimal decFileSize = new BigDecimal(fileSize);
                            decFileSize = decFileSize.divide(new BigDecimal(1024), 1,
                                    RoundingMode.HALF_UP);
                            fileMessage.append("\"fileSize\" : \""
                                    + decFileSize.toString() + " KByte\",");
                        }

                        fileMessage.append("\"tempDirId\" : \"" + tempDirId + "\",");

                        //テンポラリディレクトリへファイルを保存
                        WmlTempFileBiz tempBinBiz = new WmlTempFileBiz();

                        String saveFileName = tempBinBiz.deproyFormFileData(tempFile, tempDirPath);

                        fileMessage.append("\"saveFileName\" : \"" + saveFileName + "\"");
                        fileMessage.append("}");
                    }
                }
            } else {
                // テンポラリディレクトリパスがないのでエラーを返す
                String targetName = gsMsg.getMessage("cmn.main.temp.path");
                fileMessage.append("{");
                fileMessage.append("\"error\" : \"1\",");
                fileMessage.append("\"errorMessage\" : \""
                        + gsMsg.getMessage("wml.222", new String[] {targetName})
                        + "\"");
                fileMessage.append("}");
            }

            fileMessage.append("]");

            //res.setContentType("text/json; charset=UTF-8");
            res.setCharacterEncoding("UTF-8");
            writer = res.getWriter();
            writer.write(fileMessage.toString());
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 送信メール 本文内画像ファイルアップロード処理を行う
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
    private ActionForward __doSendBodyFileUpload(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        PrintWriter writer = null;
        try {

            CmnFileConfDao cfcDao = new CmnFileConfDao(con);
            CmnFileConfModel cfcMdl = cfcDao.select();
            int ficMaxSize = cfcMdl.getFicMaxSize();
            long fileMaxSize = ficMaxSize * GSConstCommon.FILE_SIZE_1MB;

            Wml010Biz biz = new Wml010Biz();
            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);

            String tempDirId = "";
            tempDirId = form.getWml010tempDirId();
            String tempDirPath = biz.getSendTempDir(reqMdl, tempDirId);

            StringBuilder fileMessage = new StringBuilder();
            fileMessage.append("[");

            if (!tempDirPath.isEmpty()) {
                tempDirPath += "/bodyFile";
                IOTools.isDirCheck(tempDirPath, true);

                int fileIdx = 0;
                for (; fileIdx < form.getWml010sendMailFileForm().size(); fileIdx++) {
                    if (fileIdx > 0) {
                        fileMessage.append(",");
                    }

                    long fileSize = 0;
                    FormFile tempFile = form.getWml010sendMailFileForm().get(fileIdx);
                    if (tempFile != null) {
                        fileSize = tempFile.getFileSize();
                    }

                    if (fileSize <= 0) {
                        fileMessage.append("{");
                        fileMessage.append("\"error\" : \"1\",");
                        fileMessage.append("\"errorMessage\" : \""
                                + gsMsg.getMessage("cmn.select.file") + "\"");
                        fileMessage.append("}");
                    } else if (fileSize > fileMaxSize) {
                        //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
                        fileMessage.append("{");
                        fileMessage.append("\"error\" : \"1\",");
                        fileMessage.append("\"errorMessage\" : \""
                                + gsMsg.getMessage("cmn.cmn110.error.input.capacity.over",
                                        new String[] {ficMaxSize + "MB"})
                                + "\"");
                        fileMessage.append("\"fileName\" : \""
                                + gsMsg.getMessage("cmn.cmn110.8",
                                        new String[] {Wml010Biz._escapeTextView(
                                                tempFile.getFileName())})
                                + "\"");
                        fileMessage.append("}");

                    } else if (!Cmn110Biz.isExtensionForPhotoOk(tempFile.getFileName())) {
                        fileMessage.append("{");
                        fileMessage.append("\"error\" : \"1\",");
                        fileMessage.append("\"errorMessage\" : \""
                                + gsMsg.getMessage("cmn.cmn110.error.select2.required.extent2")
                                + "\",");
                        fileMessage.append("\"fileName\" : \""
                                + gsMsg.getMessage("cmn.cmn110.8",
                                        new String[] {Wml010Biz._escapeTextView(
                                                tempFile.getFileName())})
                                + "\"");
                        fileMessage.append("}");

                    } else {
                        fileMessage.append("{");
                        fileMessage.append("\"error\" : \"0\",");
                        fileMessage.append("\"fileName\" : \""
                                + Wml010Biz._escapeTextView(tempFile.getFileName()) + "\",");


                        //テンポラリディレクトリへファイルを保存
                        StringBuilder tempDirBuilder = new StringBuilder(tempDirPath);
                        tempDirBuilder = tempDirBuilder.append("/");
                        //連番フォルダの存在チェック
                        int dirIdx = 1;
                        while (new File(tempDirBuilder.toString()
                                        + String.valueOf(dirIdx)).exists()) {
                            ++dirIdx;
                        }
                        //連番付与
                        tempDirBuilder.append(String.valueOf(dirIdx));
                        tempDirBuilder.append("/");

                        WmlTempFileBiz tempBinBiz = new WmlTempFileBiz();

                        tempBinBiz.deproyFormFileData(tempFile, tempDirBuilder.toString());

                        fileMessage.append("\"saveFileId\" : \"" + dirIdx + "\"");
                        fileMessage.append("}");
                    }
                }
            } else {
                // テンポラリディレクトリパスがないのでエラーを返す
                String targetName = gsMsg.getMessage("cmn.main.temp.path");
                fileMessage.append("{");
                fileMessage.append("\"error\" : \"1\",");
                fileMessage.append("\"errorMessage\" : \""
                        + gsMsg.getMessage("wml.222", new String[] {targetName})
                        + "\"");
                fileMessage.append("}");
            }

            fileMessage.append("]");

            //res.setContentType("text/json; charset=UTF-8");
            res.setCharacterEncoding("UTF-8");
            writer = res.getWriter();
            writer.write(fileMessage.toString());
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 送信メール添付ファイルのダウンロードを行う
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
    private ActionForward __doSendFileDownload(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        Wml010Biz biz = new Wml010Biz();

        String tempDirPath = "";
        String tempDirId = "";
        if (form.getWml010smlShareFlg() == 1
                && form.getWml010smlShareCloseFlg() == 0
                && !StringUtil.isNullZeroString(form.getWml010smlShareTemp())
                && !form.getWml010smlShareTemp().equals("undefined")) {
            tempDirId = form.getWml010smlShareTemp();
        } else {
            tempDirId = form.getWml010tempDirId();
            form.setWml010smlShareTemp(tempDirId);
        }
        tempDirPath = biz.getSendTempDir(reqMdl, tempDirId);
        Cmn110FileModel fileMdl = biz.getSendFileData(tempDirPath,
                form.getWml010sendMailDownloadFile());

        if (fileMdl != null) {
            //ログ出力
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.download"), GSConstLog.LEVEL_INFO,
                    fileMdl.getFileName());

            JDBCUtil.closeConnectionAndNull(con);

            String filePath = fileMdl.getBinFilePath();
            //ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, filePath,
                    fileMdl.getFileName(), Encoding.UTF_8);

        }
        return null;
    }

    /**
     * <br>[機  能] 送信メール添付ファイルの削除を行う
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
    private ActionForward __doSendFileDelete(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        Wml010Biz biz = new Wml010Biz();
        //テンポラリディレクトリID取得
        GSTemporaryPathModel tempDir = biz.getSendTempPathMdl(getRequestModel(req),
                form.getWml010tempDirId());
        String fileName = form.getWml010sendMailDownloadFile();
        GSTemporaryPathUtil.getInstance().deleteFile(
                new String[] {fileName.replaceFirst(GSConstCommon.ENDSTR_SAVEFILE, "")},
                tempDir);

        return null;
    }

    /**
     * <br>[機  能] メール添付ファイルの削除(メール詳細画面から)を行う
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
    private ActionForward __doFileDeleteFromDetail(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;

        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);

        Wml010Biz biz = new Wml010Biz();
        con.setAutoCommit(false);
        boolean commit = false;
        try {
            String result = biz.deleteTempFileFromDetail(con, paramMdl, reqMdl,
                    getAppRootPath());
            writer = res.getWriter();
            writer.write(result);
            writer.flush();
            con.commit();
            commit = true;
        } finally {
            paramMdl.setFormData(form);
            try {
                if (!commit) {
                    con.rollback();
                }
            } catch (Exception e) {
                log__.error("コネクションのロールバックに失敗", e);
            }

            if (writer != null) {
                writer.close();
            }
        }

        //ログ出力
        biz._outputOpLog(map, con, reqMdl, paramMdl,
                Wml010Biz.LOGTYPE_FILEDELETE_);
        return null;
    }

    /**
     * <br>[機  能] メール情報のエクスポートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOException メール情報のファイル出力に失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws Exception エクスポートファイルのダウンロードに失敗
     * @return ActionForward
     */
    private ActionForward __doExport(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOException, IOToolsException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);

        //ディレクトリIDを生成
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDirId = tempPathUtil.getTempDirID(reqMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqMdl, tempDirId);
        Wml010Biz biz = new Wml010Biz();
        Wml010ExportFileModel exportFileData
        = biz.createExportFile(con, reqMdl, paramMdl, getAppRootPath(), tempDir);
        paramMdl.setFormData(form);

        if (exportFileData != null) {
            //ログ出力
            String value = "[" + getInterMessage(req, "wml.102") + "] "
                    + exportFileData.getAccountName();
            value += " [" + getInterMessage(req, "cmn.format") + "] eml ";
            value += " [" + getInterMessage(req, "wml.275") + "] " + exportFileData.getMessageNum();

            String date = "";
            if (exportFileData.getSdate() != null) {
                date = UDateUtil.getSlashYYMD(exportFileData.getSdate());
                date += " " + UDateUtil.getSeparateHMS(exportFileData.getSdate());
            }
            value += " [" + getInterMessage(req, "cmn.subject") + "]"
                    + NullDefault.getString(exportFileData.getSubject(), "");
            value += " [" + getInterMessage(req, "cmn.date2") + "]" + date;
            value += " [From]" + NullDefault.getString(exportFileData.getFrom(), "");
            value += " [To]"
                    + NullDefault.getString(biz.joinAddress(exportFileData.getToList()), "");
            value += " [Cc]"
                    + NullDefault.getString(biz.joinAddress(exportFileData.getCcList()), "");
            value += " [Bcc]"
                    + NullDefault.getString(biz.joinAddress(exportFileData.getBccList()), "");
            value = StringUtil.trimRengeString(value, 3000);

            String wmlSid = String.valueOf(form.getWml010selectMessageNum()[0]);
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.export"), GSConstLog.LEVEL_INFO,
                    value, wmlSid, GSConstWebmail.WML_LOG_FLG_EML);

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
            fileName = wmlBiz.fileNameCheck(fileName);
            fileName += ".eml";

            TempFileUtil.downloadAtachment(req, res, exportFileData.getFilePath().getPath(),
                    fileName, Encoding.UTF_8);

            //テンポラリディレクトリを削除
            wmlBiz.deleteTempDir(reqMdl, tempDirId);

        }
        return null;
    }

    /**
     * <br>[機  能] eml出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOException メール情報のファイル出力に失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws Exception エクスポートファイルのダウンロードに失敗
     * @return ActionForward
     */
    private ActionForward __doEmlOutput(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOException, IOToolsException, Exception {

        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        Wml010Biz biz = new Wml010Biz();
        long[] messageNumList = biz.getSelectMessageNum(paramMdl);

        //メッセージが未選択の場合、処理を終了する
        if (messageNumList.length <= 0) {
            return null;
        }

        //選択されたメッセージが1件の場合
        if (messageNumList.length == 1) {
            return __doExport(map, form, req, res, con);
        }

        //ディレクトリIDを生成
        RequestModel reqMdl = getRequestModel(req);
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDirId = tempPathUtil.getTempDirID(reqMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqMdl, tempDirId);
        Wml010ExportFileModel exportFileData = null;

        try {
            exportFileData
            = biz.createExportFileList(con, reqMdl, paramMdl, getAppRootPath(), tempDir);
        } catch (IOToolsException | IOException e) {
            log__.error("ZIP作成に失敗", e);
            try {
                wmlBiz.deleteTempDir(reqMdl, tempDirId);
            } catch (Exception e2) {
                log__.error("テンポラリ初期化に失敗", e);
            }

            return __setZipErrorDsp(map, req, form);
        }


        paramMdl.setFormData(form);

        if (exportFileData != null) {
            String logMsgNum = "";
            boolean sepFlg = false;
            for (long messageNum : messageNumList) {
                if (sepFlg) {
                    logMsgNum += ", ";
                } else {
                    sepFlg = true;
                }
                logMsgNum += messageNum;
            }

            //ログ出力
            WmlAccountDao accountDao = new WmlAccountDao(con);
            String msg = " [" + getInterMessage(req, "wml.102") + "]"
                    + accountDao.getAccountName(biz._getViewAccountSid(paramMdl))
                    + " [" + getInterMessage(req, "cmn.format") + "] eml "
                    +  " [" + getInterMessage(req, "wml.275") + "] " + logMsgNum;

            msg = StringUtil.trimRengeString(msg, 3000);

            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.export"), GSConstLog.LEVEL_INFO,
                    msg);

            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            String fileName = exportFileData.getFilePath().getName();
            TempFileUtil.downloadAtachment(req, res, exportFileData.getFilePath().getPath(),
                    fileName, Encoding.UTF_8);
            //テンポラリディレクトリ削除
            wmlBiz.deleteTempDir(reqMdl, tempDirId);
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
            HttpServletRequest req, Wml010Form form) throws Exception {
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("mailList");

        cmn999Form.setUrlOK(urlForward.getPath());



        //メッセージセット
        String msgState = "error.fail";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, "cmn.zip.create")));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] メールの移動(別フォルダへ移動) 処理を行う
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
    private ActionForward __doMoveMessage(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        PrintWriter writer = null;

        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);

        Wml010Biz biz = new Wml010Biz();
        con.setAutoCommit(false);
        boolean commit = false;
        try {
            String result = biz.changeFolderInMail(con, paramMdl, reqMdl);

            res.setContentType("text/json; charset=UTF-8");
            writer = res.getWriter();
            writer.write(result);
            writer.flush();
            con.commit();
            commit = true;
        } finally {
            paramMdl.setFormData(form);
            try {
                if (!commit) {
                    con.rollback();
                }
            } catch (Exception e) {
                log__.error("コネクションのロールバックに失敗", e);
            }

            if (writer != null) {
                writer.close();
            }
        }

        //ログ出力
        biz._outputOpLog(map, con, reqMdl, paramMdl,
                Wml010Biz.LOGTYPE_MOVEMAIL_);
        return null;
    }

    /**
     * <br>[機  能] ユーザ情報プラグインの使用可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return ユーザ情報プラグインの使用可否
     * @throws SQLException SQL実行時例外
     */
    protected int _getUseUserPluginKbn(HttpServletRequest req, Connection con)
            throws SQLException {
        PluginConfig pconfig
        = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req),
                                getRequestModel(req));
        return CommonBiz.getUserPluginUse(pconfig);
    }

    /**
     * <br>[機  能] アドレス帳プラグインの使用可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return アドレス帳プラグインの使用可否
     * @throws SQLException SQL実行時例外
     */
    protected int _getUseAddressPluginKbn(HttpServletRequest req, Connection con)
            throws SQLException {
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
        = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        return CommonBiz.getAddressPluginUse(pconfig);
    }

    /**
     * <br>[機  能] 指定したプラグインが使用可否を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param pluginId プラグインID
     * @return プラグインの使用可否
     * @throws SQLException SQL実行時例外
     */
    private int __getUsePluginKbn(HttpServletRequest req, Connection con, String pluginId)
            throws SQLException {
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
        = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        int pluginUse = GSConst.PLUGIN_NOT_USE;
        if (pconfig.getPlugin(pluginId) != null) {
            pluginUse = GSConst.PLUGIN_USE;
        }
        return pluginUse;
    }

    /**
     * <br>[機  能] メール情報のエクスポートチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOException メール情報のファイル出力に失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws Exception エクスポートファイルのダウンロードに失敗
     * @return ActionForward
     */
    private ActionForward __doExportCheck(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOException, IOToolsException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);

        PrintWriter writer = null;
        try {
            Wml010Biz biz = new Wml010Biz();

            StringBuilder resultMsg = new StringBuilder();
            resultMsg.append("{");
            if (biz.checkExport(con, reqMdl, paramMdl)) {
                resultMsg.append("\"error\" : \"0\"");
            } else {
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                resultMsg.append("\"error\" : \"1\",");
                resultMsg.append("\"errorMessage\" : \""
                        + gsMsg.getMessage("wml.wml010.34")
                        + "\"");
            }
            resultMsg.append("}");

            res.setCharacterEncoding("UTF-8");
            writer = res.getWriter();
            writer.write(resultMsg.toString());
            writer.flush();
        } catch (IOException e) {
            log__.error("メール情報のエクスポートチェックに失敗", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] メール情報のエクスポート(pdf形式)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOException メール情報のファイル出力に失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws Exception エクスポートファイルのダウンロードに失敗
     * @return ActionForward
     */
    private ActionForward __doExportPdf(ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOException, IOToolsException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);

        Wml010Biz biz = new Wml010Biz();

        log__.debug("ファイルダウンロード処理(PDF)");

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();

        //ディレクトリIDを生成
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDirId = tempPathUtil.getTempDirID(reqMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqMdl, tempDirId);
        String outTempDir = IOTools.replaceFileSep(tempDir + "exppdf/");
        WmlPdfModel pdfMdl =
                biz.createExportPdfFile(con, reqMdl, paramMdl, appRootPath, outTempDir);
        paramMdl.setFormData(form);

        if (pdfMdl != null) {
            //ログ出力
            String value = "[" + getInterMessage(req, "wml.102") + "] "
                    + pdfMdl.getAccName();
            value += " [" + getInterMessage(req, "cmn.format") + "] pdf ";
            value += " [" + getInterMessage(req, "wml.275") + "] "
                    + form.getWml010selectMessageNum()[0];

            value += " [" + getInterMessage(req, "cmn.subject") + "]"
                    + NullDefault.getString(pdfMdl.getTitle(), "");
            value += " [" + getInterMessage(req, "cmn.date2") + "]"
                    + NullDefault.getString(pdfMdl.getDate(), "");
            value += " [From]" + NullDefault.getString(pdfMdl.getSender(), "");
            value += " [To]" + NullDefault.getString(pdfMdl.getAtesaki(), "");
            value += " [Cc]" + NullDefault.getString(pdfMdl.getAtesakiCC(), "");
            value += " [Bcc]" + NullDefault.getString(pdfMdl.getAtesakiBCC(), "");
            value = StringUtil.trimRengeString(value, 3000);

            String wmlSid = String.valueOf(form.getWml010selectMessageNum()[0]);
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.export"), GSConstLog.LEVEL_INFO,
                    value, wmlSid, GSConstWebmail.WML_LOG_FLG_PDF);

            JDBCUtil.closeConnectionAndNull(con);

            String outBookName = pdfMdl.getFileName();
            String outFilePath = IOTools.setEndPathChar(outTempDir) + pdfMdl.getSaveFileName();
            //ファイルのダウンロード
            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
            //TEMPディレクトリ削除
            wmlBiz.deleteTempDir(reqMdl, tempDirId);
        }

        return null;
    }

    /**
     * <p>ファイル容量エラーの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward フォワード
     */
    public ActionForward getFileSizeErrorPage(ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res) {
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        StringBuilder fileMessage = new StringBuilder();
        fileMessage.append("[");
        fileMessage.append("{");
        fileMessage.append("\"error\" : \"1\",");
        fileMessage.append("\"errorMessage\" : \""
                + gsMsg.getMessage("cmn.upload.totalcapacity.over",
                        new String[]{GSConstCommon.TEXT_FILE_MAX_SIZE})
                + "\"");
        fileMessage.append("}");
        fileMessage.append("]");

        PrintWriter writer = null;
        try {
            res.setCharacterEncoding("UTF-8");
            writer = res.getWriter();
            writer.write(fileMessage.toString());
            writer.flush();
        } catch (IOException e) {
            log__.error("ファイル容量エラーの処理に失敗", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 送信メールアカウントの変更処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doChangeSendAccount(
            Wml010Form form,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {
        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;
        con.setAutoCommit(true);
        try {
            Wml010Biz biz = new Wml010Biz();
            String sign = biz.createAccountSignText(con, form.getWml010sendAccount());
            writer = res.getWriter();
            writer.write("{");
            writer.write("\"sign\" : [");
            writer.write(sign);
            writer.write("],");

            String template = biz.createMailTemplateText(con, form.getWml010sendAccount());
            writer.write("\"template\" : [");
            writer.write(template);
            writer.write("]");
            writer.write("}");
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 送信メールアカウントの変更処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doChangeSendSign(
            Wml010Form form,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = null;
        con.setAutoCommit(true);
        try {
            Wml010ParamModel paramMdl = new Wml010ParamModel();
            paramMdl.setParam(form);
            Wml010Biz biz = new Wml010Biz();
            String body = biz.replaceSendBodySign(con, paramMdl);
            writer = res.getWriter();
            writer.write("{\"sendBody\" : \"");
            writer.write(body);
            writer.write("\"}");
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 送信メール メールテンプレートの反映処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doSetMailTemplate(
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        con.setAutoCommit(true);
        Wml010ParamModel paramMdl = new Wml010ParamModel();
        paramMdl.setParam(form);
        Wml010Biz biz = new Wml010Biz();
        biz.setMailTemplate(con, res, paramMdl,
                getRequestModel(req),
                getAppRootPath());
        return null;
    }

    /**
     * <br>[機  能] 共有ダイアログ削除時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @throws Exception 実行時例外
     */
    private void __doTempDirDelete(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con)
                    throws Exception {

        JSONObject jsonData = new JSONObject();
        con.setAutoCommit(true);
        //トランザクショントークン設定
        this.saveToken(req);
        PrintWriter out = null;
        String tempDirUuid = "";
        if (form.getWml010smlShareFlg() == 1
                && form.getWml010smlShareCloseFlg() == 0
                && !StringUtil.isNullZeroString(form.getWml010smlShareTemp())
                && !form.getWml010smlShareTemp().equals("undefined")) {
            tempDirUuid = form.getWml010smlShareTemp();
        } else {
            Wml010ParamModel paramMdl = new Wml010ParamModel(form);
            tempDirUuid = paramMdl.getWml010tempDirId();
            form.setWml010smlShareTemp(tempDirUuid);
        }
        //テンポラリディレクトリの削除
        __deleteTempDir(req, tempDirUuid);
        try {
            jsonData.element("success", true);
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
     * <br>[機  能] テンポラリディレクトリの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param form フォーム
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     */
    private void __deleteTempDir(HttpServletRequest req, Wml010Form form)
            throws IOToolsException {
        __deleteTempDir(req, form.getWml010tempDirId());
    }

    /**
     * <br>[機  能] テンポラリディレクトリの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param tempDirId テンポラリディレクトリID
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     */
    private void __deleteTempDir(HttpServletRequest req, String tempDirId)
            throws IOToolsException {

        if (tempDirId == null
                || tempDirId.isEmpty()
                || !ValidateUtil.isAlphaNum(tempDirId)) {
            return;
        }
        Wml010Biz biz = new Wml010Biz();
        biz.deleteTempDir(getRequestModel(req), tempDirId);
    }

    /**
     * <br>[機  能] 自動保存処理
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
    private ActionForward __doAutoSave(ActionMapping map, Wml010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        Wml010ParamModel paramMdl = new Wml010ParamModel(form);
        paramMdl.setParam(form);
        PrintWriter writer = null;

        try {
            //ディレクトリIDチェック
            String dirId = paramMdl.getWml010tempDirId();
            Wml010Biz biz = new Wml010Biz();
            biz.checkDirId(dirId);

            String tempDir = biz.getSendTempDir(reqMdl, dirId);

            String[] message = form.validateSendMailDraft(con, getGsContext(),
                    getSessionUserSid(req),
                    getAppRootPath(),
                    tempDir,
                    reqMdl, 0);

            if (message == null) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                boolean commit = false;
                try {
                    //件名が空白の時は"[自動保存]"を代入する
                    if (StringUtil.isNullZeroStringSpace(form.getWml010sendSubject())) {
                        form.setWml010sendSubject(gsMsg.getMessage("wml.js.43"));
                    }
                    paramMdl.setParam(form);
                    WmlSendResultModel result = biz._autoSaveDraftMail(
                            con, paramMdl, getGsContext(),
                            getSessionUserSid(req), reqMdl, dirId, getAppRootPath());
                    form.setWml010sendMessageNum(result.getMailNum());

                    con.commit();
                    commit = true;
                    __doAutoSaveJson(form, req, res);

                    res.setContentType("text/json; charset=UTF-8");
                    writer = res.getWriter();
                    writer.write("{");
                    writer.write("\"error\" : \"0\",");
                    writer.write("\"sendToDraft\" : true,");
                    writer.write("\"timeSent\" : false,");
                    writer.write("\"beforeMail\" : false");
                    writer.write("}");
                } catch (WmlTempDirNoneException e) {
                    //テンポラリディレクトリがなかった時
                    String errorMessage = gsMsg.getMessage("wml.222", new String[] {
                                                    gsMsg.getMessage("cmn.main.temp.path")});
                    message = new String[]{errorMessage};

                } catch (WmlMailSizeOverException e) {
                    //メールのサイズが上限を超えていた場合
                    String errorMessage = gsMsg.getMessageVal0("wml.245", e.getMaxSizeText());
                    message = new String[]{errorMessage};

                } catch (WmlDiskSizeOverException e) {
                    //ディスクの容量制限を超えていた場合
                    String errorMessage = e.getErrorMsg();
                    message = new String[]{errorMessage};

                } catch (WmlTempFileNameException e) {
                    //添付ファイル名が不正の場合
                    String errorMessage = e.getMessage();
                    message = new String[]{errorMessage};

                } catch (Exception e) {
                    log__.error("メールの草稿保存に失敗", e);
                    Throwable cause = GSAttachFileNotExistException.searchCause(e);
                    if (cause != null) {
                        log__.error("", cause);
                        throw e;
                    } else {
                        message = new String[] {getInterMessage(req, "wml.wml010.29")};
                    }
                } finally {
                    if (!commit) {
                        con.rollback();
                    }
                }
            }

            if (message != null) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                String errorMsg = gsMsg.getMessage("wml.306");

                res.setContentType("text/json; charset=UTF-8");
                if (writer == null) {
                    writer = res.getWriter();
                }
                writer.write("{");
                writer.write("\"error\" : \"1\",");
                writer.write("\"errorMessage\" : [");
                for (int index = 0; index < message.length; index++) {
                    if (index > 0) {
                        writer.write(",");
                    } else if (index == 0) {
                        writer.write("\"" + Wml010Biz._escapeText(errorMsg) + "\"");
                        writer.write(",");
                    }
                    writer.write("\"" + Wml010Biz._escapeText(message[index]) + "\"");
                }
                writer.write("]");
                writer.write("}");
            }
        } finally {
            paramMdl.setFormData(form);
            if (writer != null) {
                writer.close();
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 自動保存のjsonデータの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception 実行時例外
     */
    private void __doAutoSaveJson(Wml010Form form, HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        JSONObject jsonData = new JSONObject();
        Wml010ParamModel paramMdl = new Wml010ParamModel();

        paramMdl.setParam(form);
        jsonData = JSONObject.fromObject(form);

        jsonData.element("success", true);
        jsonData.element("svSendNum", paramMdl.getWml010sendMessageNum());
        jsonData.element("svFrom", paramMdl.getWml010sendAccount());
        jsonData.element("svTo", paramMdl.getWml010sendAddressTo());
        jsonData.element("svCc", paramMdl.getWml010sendAddressCc());
        jsonData.element("svBcc", paramMdl.getWml010sendAddressBcc());
        jsonData.element("svSubject", paramMdl.getWml010sendSubject());
        jsonData.element("svContent", paramMdl.getWml010svSendContent());

        //レスポンスの出力
        __writeResp(res, jsonData);
    }

    /**
     *
     * <br>[機  能] jsonレスポンスの書き込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param json jsonオブジェクト
     */
    private void __writeResp(HttpServletResponse res, JSONObject json) {
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetBodyFile(
            ActionMapping map,
            Wml010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        String tempSaveId = form.getWml010TempSaveId();
        //tempSaveIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(tempSaveId)) {
            return getSubmitErrorPage(map, req);
        }

        //ディレクトリIDのチェック
        Wml010Biz biz = new Wml010Biz();
        String dirId = form.getWml010tempDirId();
        biz.checkDirId(dirId);
        String tempDir = biz.getSendBodyTempDir(getRequestModel(req), dirId);
        tempDir += File.separator
                + tempSaveId;
        File bodyFileDir = new File(tempDir);
        File[] files = bodyFileDir.listFiles();
        if (files == null || files.length < 1) {
            return null;
        }

        String downFilePath = "";
        String downFileName = "";
        String tempFileName = "";
        for (File file : files) {
            tempFileName = file.getName();

            if (tempFileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                downFilePath = IOTools.replaceFileSep(tempDir + "/" + file.getName());

            } else if (tempFileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                ObjectFile objFile = new ObjectFile(tempDir, tempFileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                downFileName = fMdl.getFileName();
            }
        }
        TempFileUtil.downloadInline(req, res, downFilePath, downFileName, Encoding.UTF_8);

        return null;
    }
}
