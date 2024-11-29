package jp.groupsession.v2.man.man140;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 管理者設定 セッション保持時間設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man140Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man140Action.class);

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "back140";
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

        log__.debug("Man140Action start");
        ActionForward forward = null;

        Man140Form thisForm = (Man140Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = map.findForward(CMD_BACK);

        } else if (CMD_OK.equals(cmd)) {
            log__.debug("OK");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (CMD_BACK_REDRAW.equals(cmd)) {
            log__.debug("確認画面から戻る");
            forward = __doDspSet(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Man140Action end");
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
        Man140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Man140ParamModel paramMdl = new Man140ParamModel();
        paramMdl.setParam(form);
        Man140Biz biz = new Man140Biz(con, getRequestModel(req));
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
        Man140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) {

        //画面に常に表示する情報を取得する
        Man140ParamModel paramMdl = new Man140ParamModel();
        paramMdl.setParam(form);
        Man140Biz biz = new Man140Biz(con, getRequestModel(req));
        biz.getDspData(paramMdl);
        paramMdl.setFormData(form);

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
            Man140Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward(CMD_OK);
    }
}
