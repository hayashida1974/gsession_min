package jp.groupsession.v2.wml.wml012;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.AbstractWebmailAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.wml010.Wml010Biz;
import jp.groupsession.v2.wml.wml010.Wml010ParamModel;

/**
 * <br>[機  能] WEBメール 送信メール確認(ポップアップ)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml012Action extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml012Action.class);

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
        log__.debug("START");

        ActionForward forward = null;
        Wml012Form thisForm = (Wml012Form) form;

        String cmd = NullDefault.getString(req.getParameter("wml012CMD"), "");

        //テンポラリディレクトリIDのチェック
        RequestModel reqMdl = getRequestModel(req);
        String tempDirId = __getTempDirId(thisForm);

        boolean result = false;
        try {
            Wml010Biz biz = new Wml010Biz();
            biz.checkDirId(tempDirId);

            WmlBiz wmlBiz = new WmlBiz();
            String tempDir = wmlBiz.getTempDir(reqMdl, tempDirId);
            result = wmlBiz.checkTempDir(tempDir, reqMdl, tempDirId);
        } catch (IOToolsException e) {
        }

        if (!result) {
            log__.error("メールの送信に失敗 - テンポラリディレクトリIDが不正 : " + tempDirId);
            PrintWriter writer = null;
            res.setContentType("text/json; charset=UTF-8");
            writer = res.getWriter();
            writer.write("{");
            writer.write("\"error\" : \"1\",");
            writer.write("\"errorMessage\" : [");
            writer.write("\"" + Wml012Biz.escapeText(getInterMessage(req, "wml.js.40")) + "\"");
            writer.write("]");
            writer.write("}");
        }

        if (cmd.equals("saveMail")) {
            __saveSendMailData(map, thisForm, req, res, con);
        } else {
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
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
    public ActionForward __doInit(ActionMapping map, Wml012Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        String tempDirId = __getTempDirId(form);
        log__.info("WML PARAM: " + tempDirId);
        Wml012Biz biz = new Wml012Biz();
        Wml012ParamModel paramMdl = biz.getInitData(con, getRequestModel(req), tempDirId);
        if (paramMdl != null) {
            paramMdl.setFormData(form);
        }
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 送信メール情報の保存
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
    public ActionForward __saveSendMailData(ActionMapping map, Wml012Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        PrintWriter writer = null;
        RequestModel reqMdl = getRequestModel(req);
        try {
            //入力チェック
            if (!__validateSendMail(map, form, req, res, con)) {
                return null;
            }

            Wml012ParamModel paramMdl = new Wml012ParamModel();
            paramMdl.setParam(form);
            Wml012Biz biz = new Wml012Biz();
            String tempDirId = __getTempDirId(form);
            biz.saveSendMailData(paramMdl, reqMdl, tempDirId);
            paramMdl.setFormData(form);
            res.setContentType("text/json; charset=UTF-8");
            writer = res.getWriter();
            writer.write("{");
            writer.write("\"error\" : \"0\"");
            writer.write("}");
        } catch (Exception e) {
            log__.error("メールの送信に失敗", e);
            res.setContentType("text/json; charset=UTF-8");
            writer = res.getWriter();
            writer.write("{");
            writer.write("\"error\" : \"1\",");
            writer.write("\"errorMessage\" : [");
            writer.write("\"" + Wml012Biz.escapeText(getInterMessage(req, "wml.js.40")) + "\"");
            writer.write("]");
            writer.write("}");

            //エラーログ出力
            String value = getInterMessage(req, "wml.js.40");
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_ERROR,
                    StringUtil.trimRengeString(value, 3000));
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 送信メール情報の入力チェックを行う
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
                                        Wml012Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws SQLException, Exception {

        PrintWriter writer = null;
        RequestModel reqMdl = getRequestModel(req);

        boolean result = false;
        try {
            String tempDirId = __getTempDirId(form);
            Wml010Biz biz = new Wml010Biz();
            String tempDir = biz.getSendTempDir(reqMdl, tempDirId);
            String[] message = form.validateSendMail(con, getGsContext(),
                                                        getSessionUserSid(req),
                                                        getAppRootPath(),
                                                        tempDir,
                                                        reqMdl);
            //宛先やタイトルなどに問題がない場合、添付ファイルのチェックを行う
            if (message == null) {
                Wml010ParamModel paramMdl = new Wml010ParamModel(form);
                message = biz.checkSendMailSize(con, paramMdl,
                    getGsContext(), getSessionUserSid(req), reqMdl, tempDirId);
            }
            result = message == null;
            if (!result) {
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
                    writer.write("\"" + Wml012Biz.escapeText(message[index]) + "\"");
                }
                writer.write("]");
                writer.write("}");
            }

            if (writer != null) {
                writer.flush();
            }

        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return result;
    }

    /**
     * <br>[機  能] テンポラリディレクトリIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @return テンポラリディレクトリID
     */
    private String __getTempDirId(Wml012Form form) {
        return form.getWml012tempDirId();
    }
}
