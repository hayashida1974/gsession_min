package jp.groupsession.v2.cmn.cmn999;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.exception.GSInvalidPathException;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.convert.dao.VersionDao;
import jp.groupsession.v2.convert.model.VersionModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 共通メッセージ画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn999Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn999Action.class);

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
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                     org.apache.struts.action.ActionForm,
     *                     javax.servlet.http.HttpServletRequest,
     *                     javax.servlet.http.HttpServletResponse,
     *                     java.sql.Connection)
    */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        ActionForward forward = null;
        Cmn999Form cmn999Form = (Cmn999Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("cmd = " + cmd);
        CommonBiz cmnBiz = new CommonBiz();

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String message1 = "";
        /** メッセージ 参照 **/
        String message2 = gsMsg.getMessage("address.adr110.2");

        //キャンセルボタンのデフォルトテキストを設定
        if (StringUtil.isNullZeroString(cmn999Form.getCancelBtnValue())) {
            cmn999Form.setCancelBtnValue(gsMsg.getMessage("cmn.cancel"));
        }

        //システムエラー画面
        if (cmd.equals("SYSETEM_ERROR")) {
            log__.debug("システムエラーメッセージ");
            __setExceptionDispParam(map, req, getServlet().getServletContext(), con);
            //ログ出力
            message1 = gsMsg.getMessage("cmn.sys.error");
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_ERROR, message1);
            forward = map.getInputForward();
        //認証エラー警告画面
        } else if (cmd.equals("AUTH_ERROR")
        || cmd.equals("AUTH_POPUP_ERROR")) {
            log__.debug("認証エラーメッセージ");
            __setAuthErrorDispParam(map, req);
            //ログ出力
            message1 = gsMsg.getMessage("cmn.ninsyo.error");
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_WARN, message1);
            forward = map.getInputForward();
            if (cmd.equals("AUTH_POPUP_ERROR")) {
                cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
                cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
            }

        //ライセンスエラー
        } else if (cmd.equals("LICENSE_ERROR")) {
            log__.debug("ライセンスエラーメッセージ");
            __setLicenseErrorDispParam(map, req);
            //ログ出力
            message1 = gsMsg.getMessage("cmn.licence.error");
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_WARN, message1);
            forward = map.getInputForward();
        //２重投稿エラー画面
        } else if (cmd.equals("SUBMIT_ERROR")) {
            log__.debug("２重投稿エラーメッセージ");
            __setSubmitErrorDispParam(map, req);
            //ログ出力
            message1 = gsMsg.getMessage("cmn.double.error");
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_WARN, message1);
            forward = map.getInputForward();
        //警告画面
        } else if (cmd.equals("WARNING")) {
            log__.debug("警告メッセージ");
            //ログ出力
            message1 = gsMsg.getMessage("cmn.sys.keikoku");
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_WARN, message1);
            __setWarnDispParam(map, req);
            forward = map.getInputForward();
        //権限不足エラー
        } else if (cmd.equals("POWER_ERROR")) {
            log__.debug("権限不足エラー");
            __setPowerErrorDispParam(map, req);
            //ログ出力
            message1 = gsMsg.getMessage("cmn.access.kengen");
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_WARN, message1);
            forward = map.getInputForward();
        //ファイル容量エラー
        } else if (cmd.equals("FILE_SIZE_ERROR")) {
            log__.debug("ファイル容量エラー");
            __setFileSizeErrorDispParam(map, req);
          //ログ出力
            message1 = gsMsg.getMessage("cmn.file.size");
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_WARN, message1);
            forward = map.getInputForward();
        //LDAP接続エラー警告画面
        } else if (cmd.equals("LDAP_ERROR")) {
            log__.debug("LDAP接続エラーメッセージ");
            __setLdapErrorDispParam(map, req);
            //ログ出力
            cmnBiz.outPutSystemLog(map, reqMdl, gsMsg, con,
                    message2, GSConstLog.LEVEL_WARN, message1);
            forward = map.getInputForward();
        //リダイレクト
        } else if (!StringUtil.isNullZeroString(cmn999Form.getDirectURL())
                && !cmd.equals("INFO")) {
            log__.debug("リダイレクト");
            //リダイレクト先が格納されているならそのURLへ遷移
            log__.debug("遷移先 = " + cmn999Form.getDirectURL());
            String url = cmn999Form.getDirectURL();
            PluginConfig pconfig = getPluginConfig(req);
            CommonBiz cBiz = new CommonBiz();
            forward = cBiz.saveForwardUrl(map, pconfig, url);
        } else {
            log__.debug("デフォルト");
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * [機  能] システムエラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param context コンテキスト
     * @param con コネクション
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private void __setExceptionDispParam(
        ActionMapping map,
        HttpServletRequest req,
        ServletContext context,
        Connection con
        ) throws UnsupportedEncodingException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();

        //システムエラー画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_ERROR_REPORT);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN); //警告アイコン
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        urlForward = map.findForward("gf_logout");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.unknown.server"));

        //エラーログを画面表示用に作成・セット
        Object oerror = req.getAttribute(GSConst.ERROR_KEY);
        if (oerror != null) {
            Throwable terror = (Throwable) oerror;
            StackTraceElement[] stels = terror.getStackTrace();
            StringBuilder ebuf = new StringBuilder();

            ebuf.append(terror.toString());
            ebuf.append("\r\n");
            for (StackTraceElement stel : stels) {
                ebuf.append("    at " + stel.toString());
                ebuf.append("\r\n");
            }

            //原因
            Throwable cause = null;
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    cause = terror.getCause();
                } else {
                    cause = cause.getCause();
                }
                if (cause != null) {

                    ebuf.append("Caused by: " + cause.toString());
                    ebuf.append("\r\n");
                    StackTraceElement[] stelsCause = cause.getStackTrace();
                    int count = 0;
                    int passCount = 0;
                    for (StackTraceElement stelc : stelsCause) {
                        count++;
                        if (count < 6) {
                            ebuf.append("    at " + stelc.toString());
                            ebuf.append("\r\n");
                        } else {
                            passCount++;
                        }
                    }
                    //省略があった場合
                    if (passCount > 0) {
                        ebuf.append("... " +  passCount + " more");
                        ebuf.append("\r\n");
                    }
                } else {
                    i = 3;
                }
            }

            ebuf.append("\r\n");
            Object oerrorPara = req.getAttribute(GSConst.ERROR_PARA_KEY);
            if (oerrorPara != null && oerrorPara instanceof String) {
                String textReqParam = gsMsg.getMessage(req, "cmn.cmn999.10");
                ebuf.append(textReqParam);
                ebuf.append("\r\n");
                ebuf.append((String) oerrorPara);
                ebuf.append("\r\n");
            }

            cmn999Form.setErrorLogOnly(ebuf.toString());

            try {
                // 現在のシステムプロパティを取得します
                Properties props = System.getProperties();
                StringBuilder ibuf = new StringBuilder();

                //OS
                String osName = props.getProperty("os.name");
                ibuf.append("OS： " + osName);

                //アーキテクチャー
                String osArch = props.getProperty("os.arch");
                ibuf.append(" " + osArch);

                //バージョン
                String osVersion = props.getProperty("os.version");
                ibuf.append(" " + osVersion);
                ibuf.append("\r\n");

                //サーブレットコンテナバージョン
                String textContena = gsMsg.getMessage(req, "cmn.cmn999.9");
                String servletInfo = context.getServerInfo();
                ibuf.append(textContena + "： " + servletInfo);
                ibuf.append("\r\n");

                //javaバージョン
                String jversion = props.getProperty("java.version");
                ibuf.append("Java： " + jversion);

                //javaベンダー
                String jvendor = props.getProperty("java.vendor");
                ibuf.append(" " + jvendor);
                ibuf.append("\r\n");

                //メモリ情報
                String memoryInfo = __getMemoryInfo(req);
                ibuf.append(memoryInfo);
                ibuf.append("\r\n");

                //リファラー
                String textReferer = gsMsg.getMessage(req, "cmn.cmn999.8");
                String referer = req.getHeader("referer");
                ibuf.append(textReferer + "：" + NullDefault.getString(referer, ""));
                ibuf.append("\r\n");
                
                //URL
                if (oerror instanceof GSInvalidPathException) {
                    String url = gsMsg.getMessage("cmn.url");
                    GSInvalidPathException gipException = (GSInvalidPathException) oerror;
                    ibuf.append(url + "： " + gipException.getUrl());
                }

                cmn999Form.setDetailInfo(ibuf.toString());
                ebuf.append("\r\n");
                ebuf.append("\r\n");
                ebuf.append(ibuf.toString());
                ebuf.append("\r\n");
                ebuf.append("GroupSession Version " + GSConst.VERSION);

                //バージョン(DB)
                VersionDao verDao = new VersionDao(con);
                VersionModel verMdl = verDao.select();
                ebuf.append("(" + verMdl.getVerVersion() + ")");

                cmn999Form.setErrorLog(ebuf.toString());

            } catch (Exception e) {

            }

            //エラー報告先を指定
            String errorReportUrl = null;
            if (ConfigBundle.getValue("ERROR_REPORT_URL") != null) {
                //設定ファイル(servername.conf)の指定ディレクトリ
                errorReportUrl = ConfigBundle.getValue("ERROR_REPORT_URL");
                cmn999Form.setUrlReport(errorReportUrl);
            }

            //OutOfMemoryErrorチェック
            if (__checkOutOfMemory(terror.toString())) {
                cmn999Form.setOutOfMemory(Cmn999Form.OUT_OF_MEMORY);
                //GS設定ドキュメントURLを指定
                String settingDocUrl = null;
                if (ConfigBundle.getValue("GS_SETTING_DOC_URL") != null) {
                    //設定ファイル(servername.conf)の指定ディレクトリ
                    settingDocUrl = ConfigBundle.getValue("GS_SETTING_DOC_URL");
                    cmn999Form.setUrlGsSetting(settingDocUrl);
                }
            }

        }
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * [機  能] 警告画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setWarnDispParam(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN); //警告アイコン
        urlForward = map.findForward("gf_logout");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("cmn.system.access.error"));

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * [機  能] 認証エラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setAuthErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //認証エラー警告画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        urlForward = map.findForward("gf_logout");
        cmn999Form.setUrlOK(urlForward.getPath());

        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.access.login.user"));

        ILogin loginBiz = _getLoginInstance();
        if (loginBiz.isPopup()) {
            cmn999Form.setMessage("ユーザＩＤまたはパスワードが不正です。"
                                                + "<br>入力内容を確認し再度ログインしてください。"
                                                + "<br>閉じるボタンをクリックして画面を閉じてください。");
            cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        }

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * [機  能] ライセンスエラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setLicenseErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //認証エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("gf_main_kanri");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.license.file"));

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * [機  能] ２重投稿エラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setSubmitErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //２重投稿エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        urlForward = map.findForward("gf_menu");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.access.double.submit"));
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * [機  能] 権限不足エラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setPowerErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        urlForward = map.findForward("gf_menu");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.access.power.user"));
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * [機  能] ファイル容量エラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setFileSizeErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();

        //ファイル容量エラー警告画面パラメータの設定
        cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);

        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("error.input.capacity.over", GSConstCommon.TEXT_FILE_MAX_SIZE));

        req.setAttribute("cmn999Form", cmn999Form);
    }
    /**
     * [機  能] LDAP接続エラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setLdapErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //認証エラー警告画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        urlForward = map.findForward("gf_logout");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage("LDAPサーバ接続に失敗しました。");
        req.setAttribute("cmn999Form", cmn999Form);
    }
    /**
     * [機  能] 使用量、使用を試みる最大メモリ容量の情報を返します。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param req リクエスト
     * @return mBuf 仮想マシンのメモリ情報
     */
    private String __getMemoryInfo(HttpServletRequest req) {
        DecimalFormat format1 = new DecimalFormat("#,###KB");
        DecimalFormat format2 = new DecimalFormat("##.#");
        long free = Runtime.getRuntime().freeMemory() / 1024;
        long max = Runtime.getRuntime().maxMemory() / 1024;
        long used = max - free;
        double ratio = (used * 100 / (double) max);
        GsMessage gsMsg = new GsMessage();
        //メモリ使用
        String textUseMemory = gsMsg.getMessage(req, "cmn.cmn999.11");
        //メモリ最大
        String textMaxMemory = gsMsg.getMessage(req, "cmn.cmn999.12");

        StringBuilder mBuf = new StringBuilder();
        mBuf.append(textUseMemory + "：" + format1.format(used));
        mBuf.append(" (");
        mBuf.append(format2.format(ratio));
        mBuf.append(" %)");
        mBuf.append("\r\n");
        mBuf.append(textMaxMemory + "：" + format1.format(max));
        return mBuf.toString();
    }
    /**
     * [機  能] OutOfMemoryErrorかの判定。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param errorStr エラー文字列
     * @return flg true:OutOfMemory flase:OutOfMemory以外
     */
    private boolean __checkOutOfMemory(String errorStr) {
        boolean flg = false;
        if (errorStr.indexOf(GSConstCommon.OUT_OF_MEMORY) != -1) {
            flg = true;
        }
        return flg;
    }

}