package jp.groupsession.v2.man.man130;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 管理者設定 添付ファイル設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man130Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man130Action.class);

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "back130";
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

        log__.debug("Man130Action start");
        ActionForward forward = null;

        Man130Form thisForm = (Man130Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = map.findForward(CMD_BACK);

        } else if (CMD_OK.equals(cmd)) {
            log__.debug("OK");
            forward = __doKakunin(map, thisForm, req, res, con);

        } else if (CMD_BACK_REDRAW.equals(cmd)) {
            log__.debug("確認画面から戻る");
            forward = __doDspSet(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Man130Action end");
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
        Man130Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Man130ParamModel paramMdl = new Man130ParamModel();
        paramMdl.setParam(form);
        Man130Biz biz = new Man130Biz(con);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return __doDspSet(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doDspSet(
        ActionMapping map,
        Man130Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) {

        //画面に常に表示する情報を取得する
        Man130ParamModel paramMdl = new Man130ParamModel();
        paramMdl.setParam(form);
        Man130Biz biz = new Man130Biz(con);
        biz.getDspData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws IOToolsException 入出力時例外
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(
            ActionMapping map,
            Man130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(errors, con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDspSet(map, form, req, res, con);
        }
        return map.findForward(CMD_OK);
    }

}
