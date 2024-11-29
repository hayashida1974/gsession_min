package jp.groupsession.v2.man.man240;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man240Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man240Action.class);

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "ktool";
    /** CMD:OKクリック */
    public static final String CMD_OK = "ok";
    /** CMD:画面再表示(初期表示以外) */
    public static final String CMD_BACK_REDRAW = "backRedraw";

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("Man240Action start");
        ActionForward forward = null;

        Man240Form thisForm = (Man240Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (CMD_BACK.equals(cmd)) {
            //管理者設定メニューへ戻る
            forward = map.findForward(CMD_BACK);

        } else if (CMD_OK.equals(cmd)) {
            //OKボタンクリック
            forward = __doOk(map, thisForm, req, res, con);

        } else if (CMD_BACK_REDRAW.equals(cmd)) {
            //確認画面から戻る
            forward = map.getInputForward();

        } else {
            log__.debug("初期表示");
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Man240Action end");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Man240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {
        PluginConfig pconf = getPluginConfig(req);

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Man240ParamModel paramMdl = new Man240ParamModel();
        paramMdl.setParam(form);
        Man240Biz biz = new Man240Biz();
        biz.setInitData(con, paramMdl, pconf, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doOk(
            ActionMapping map,
            Man240Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward(CMD_OK);
    }
}
