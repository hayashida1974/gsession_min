package jp.groupsession.v2.usr.usr043;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.AbstractUsrAction;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.usr040.Usr040Form;

/**
 * <br>[機  能] ユーザ情報 カテゴリ設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr043Action extends AbstractUsrAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr043Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;
        Usr043Form usr043Form = (Usr043Form) form;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("usr041back")) {
            //戻る
            forward = __doBack(map, usr043Form, req, res, con);

        } else if (cmd.equals("addCategory")) {
            //追加
            forward = map.findForward("add");

        } else if (cmd.equals("usr043edit")) {
            //カテゴリ名クリック
            forward = map.findForward("usr043edit");

        } else if (cmd.equals("categoryEdit")) {
            //カテゴリ編集
            forward = map.findForward("edit");

        } else if (cmd.equals("usr043up")) {
            log__.debug("上へボタンクリック");
            forward = __doSortChange(map, usr043Form, req, res, con, Usr043Biz.SORT_UP);

        } else if (cmd.equals("usr043down")) {
            log__.debug("下へボタンクリック");
            forward = __doSortChange(map, usr043Form, req, res, con, Usr043Biz.SORT_DOWN);

        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, usr043Form, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期パラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map, Usr043Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //カテゴリ情報を取得する。
        con.setAutoCommit(true);
        Usr043Biz biz = new Usr043Biz();

        Usr043ParamModel paramMdl = new Usr043ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return ActionForward
     * @throws Exception 例外
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Usr043Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }


        try {
            Usr043Biz biz = new Usr043Biz();

            Usr043ParamModel paramMdl = new Usr043ParamModel();
            paramMdl.setParam(form);
            int logOutput = biz.updateSort(con, paramMdl, changeKbn);
            paramMdl.setFormData(form);

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String opCode = gsMsg.getMessage("cmn.change");

            //ログ出力処理
            if (logOutput == 0) {
                String targetName = biz.getTargetName(paramMdl, con);
                UsrCommonBiz usrBiz = new UsrCommonBiz(con, reqMdl);
                String kbn = gsMsg.getMessage("cmn.up");
                if (changeKbn == 1) {
                    kbn = gsMsg.getMessage("cmn.down");
                }
                usrBiz.outPutLog(opCode,
                        GSConstLog.LEVEL_INFO,
                        "[" + gsMsg.getMessage("cmn.target") + "]" + targetName + "\r\n"
                                + "[" + gsMsg.getMessage("change.sort.order") + "]" + kbn,
                                map.getType());
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doBack(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        return map.findForward("back");
    }

    /**
     * <br>[機  能] 権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward checkPow(ActionMapping map,
            HttpServletRequest req, Connection con)
    throws Exception {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd.trim();
        BaseUserModel buMdl = getSessionUserModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstUser.PLUGIN_ID_USER);

        if (!adminUser) {
            con.setAutoCommit(true);
            CmnLabelUsrConfDao dao = new CmnLabelUsrConfDao(con);
            CmnLabelUsrConfModel model = dao.select();
            con.setAutoCommit(false);
            if (model != null && model.getLufEdit() == GSConstUser.POW_LIMIT) {
                return getNotAdminSeniPage(map, req);
            }
        }
        return null;
    }

}