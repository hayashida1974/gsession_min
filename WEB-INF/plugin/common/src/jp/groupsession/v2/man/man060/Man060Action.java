package jp.groupsession.v2.man.man060;

import java.io.IOException;
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
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] メイン 管理者設定 ディスク容量管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man060Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man060Action.class);

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
     * <br>[機  能] アクションを実行します
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーム
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(
                ActionMapping map,
                ActionForm form,
                HttpServletRequest req,
                HttpServletResponse res,
                Connection con) throws Exception {

        log__.debug("START Man050Action");

        //キャスト
        Man060Form thisForm = (Man060Form) form;
        //アクションフォーワード生成
        ActionForward forward = null;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("コマンド cmd = " + cmd);

        if (cmd.equals("backMenu")) {
            //管理者ツールメニュー
            forward = map.findForward("menu");
        } else if (cmd.equals("confirm")) {
            //OKボタンクリック時処理
            forward = __doConfirm(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示を行います");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END Man050Action");
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
     * @throws IOException 空き容量の取得に失敗
     */
    public ActionForward __doInit(
                    ActionMapping map,
                    Man060Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException, IOException {

        con.setAutoCommit(true);
        Man060ParamModel paramMdl = new Man060ParamModel();
        paramMdl.setParam(form);
        Man060Biz biz = new Man060Biz();
        String confDbDir = ConfigBundle.getValue("GSDATA_DIR");
        if (StringUtil.isNullZeroString(confDbDir)) {
            confDbDir = getServlet().getServletContext().getRealPath("/");
        }
        biz.setInitData(con, getRequestModel(req), paramMdl, confDbDir);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理を行います
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
    public ActionForward __doConfirm(
                    ActionMapping map,
                    Man060Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException {

        saveToken(req);
        return map.findForward("confirm");
    }
}
