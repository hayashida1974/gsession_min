package jp.groupsession.v2.man.man500;

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
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] メイン 管理者設定 個人情報編集権限設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man500Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man500Action.class);

    /**
     * <br>[機  能] アクションを実行する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        log__.debug("START");
        ActionForward forward = null;
        Man500Form man500Form = (Man500Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("man500Ok")) {
            //OKボタン押下
            forward = __doOk(map, man500Form, req, res, con);
        } else if (cmd.equals("man500Back")) {
            // 戻るボタン押下
            forward = map.findForward("man500Back");
        } else {
            //初期表示
            forward = __doInit(map, man500Form, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Man500Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
                throws SQLException, IOToolsException, TempFileException, IOException {
        con.setAutoCommit(true);

        //初期表示情報を取得する
        Man500Biz biz = new Man500Biz(getRequestModel(req));
        Man500ParamModel paramMdl = new Man500ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(con, paramMdl);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doOk(
        ActionMapping map,
        Man500Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheack(getRequestModel(req), con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            log__.debug("入力エラー");
            return map.getInputForward();
        }
        saveToken(req);

        return map.findForward("man500Ok");
    }
}
