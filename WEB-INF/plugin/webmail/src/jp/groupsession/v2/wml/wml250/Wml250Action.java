package jp.groupsession.v2.wml.wml250;

import java.io.IOException;
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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.wml240.Wml240Action;

/**
 * <br>[機  能] WEBメール メールテンプレート登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml250Action extends Wml240Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml250Action.class);

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

        //アクセス可能かをチェック
        Wml250Form thisForm = (Wml250Form) form;
        if (!_canAccess(con, req, thisForm)) {
            return getAuthErrorPage(map, req);
        }

        ActionForward forward = null;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);

        } else if (cmd.equals("wml250back")) {
            //戻るボタンクリック
            forward = map.findForward("mailTemplateConf");

            //テンポラリディレクトリを削除
            Wml250Biz biz = new Wml250Biz();
            biz.deleteTempDir(getRequestModel(req));

        } else if (cmd.equals("delTempFile")) {
            //添付ファイル削除
            forward = __doDeleteTempFile(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
    public ActionForward __doInit(ActionMapping map, Wml250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Wml250ParamModel paramMdl = new Wml250ParamModel();
        paramMdl.setParam(form);
        Wml250Biz biz = new Wml250Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req),
                            getAppRootPath());
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
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
    public ActionForward __doOK(ActionMapping map, Wml250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        // トランザクショントークン設定
        saveToken(req);

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), getAppRootPath());
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("confirm");
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
     * @throws IOException 添付ファイルの削除に失敗
     * @throws IOToolsException 添付ファイルの削除に失敗
     * @throws SQLException 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDeleteTempFile(
        ActionMapping map,
        Wml250Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
    throws IOException, IOToolsException, SQLException, TempFileException, Exception {

        //削除対象のファイルが選択されているかをチェック
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateFileDelete(reqMdl);
        if (!errors.isEmpty()) {
            //ファイル未選択
            addErrors(req, errors);
        } else {
            //選択された添付ファイルを削除する
            GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
            tempPathUtil.deleteFile(form.getWml250files(),
                                    reqMdl,
                                    GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                    Wml250Biz.SCR_ID);
        }

        return __doInit(map, form, req, res, con);
    }
}
