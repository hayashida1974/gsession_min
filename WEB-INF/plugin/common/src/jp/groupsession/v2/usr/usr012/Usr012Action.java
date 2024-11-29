package jp.groupsession.v2.usr.usr012;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 管理者設定 グループマネージャー（所属ユーザ一覧）画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr012Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr012Action.class);

    /**
     * <br>[機  能]Usr012のアクションです
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public ActionForward executeAction(
                    ActionMapping map,
                    ActionForm form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException {

        log__.debug("START Usr012Action");

        //キャスト
        Usr012Form thisForm = (Usr012Form) form;
        //アクションフォーワード生成
        ActionForward forward = null;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("コマンド cmd = " + cmd);

        if (cmd.equals("back")) {
            log__.debug("グループマネージャに戻ります");
            forward = map.findForward("back");
        } else {
            log__.debug("初期表示を行います");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END Usr012Action");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     */
    public ActionForward __doInit(
                    ActionMapping map,
                    Usr012Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException {

        ActionForward forward = null;

        //グループSIDをセット
        form.setUsr010grpSid(
                NullDefault.getInt(
                        req.getParameter("usr010grpSid"), -1));

        //初期表示処理
        con.setAutoCommit(true);
        Usr012Biz biz = new Usr012Biz();

        Usr012ParamModel paramMdl = new Usr012ParamModel();
        paramMdl.setParam(form);
        biz.setUsr012List(con, paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        forward = map.getInputForward();
        return forward;
    }
}
