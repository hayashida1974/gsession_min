package jp.groupsession.v2.adr.adr280;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.AbstractAddressAction;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] アドレス帳 カテゴリ設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr280Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr280Action.class);

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
        Adr280Form thisForm = (Adr280Form) form;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("adr280back")) {
            //戻る
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals("addCategory")) {
            //追加
            forward = map.findForward("add");

        } else if (cmd.equals("adr280edit")) {
            //ラベルボタンクリック
            forward = map.findForward("label");

        } else if (cmd.equals("categoryEdit")) {
            //カテゴリ編集
            forward = map.findForward("edit");

        } else if (cmd.equals("adr280up")) {
            log__.debug("上へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, Adr280Biz.SORT_UP);

        } else if (cmd.equals("adr280down")) {
            log__.debug("下へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, Adr280Biz.SORT_DOWN);

        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, thisForm, req, res, con);
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
    private ActionForward __doInit(ActionMapping map, Adr280Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //カテゴリ情報を取得する。
        con.setAutoCommit(true);
        Adr280Biz biz = new Adr280Biz(getRequestModel(req));

        Adr280ParamModel paramMdl = new Adr280ParamModel();
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
        Adr280Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {
        
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            Adr280Biz biz = new Adr280Biz(getRequestModel(req));

            Adr280ParamModel paramMdl = new Adr280ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(con, paramMdl, changeKbn);
            paramMdl.setFormData(form);

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

        //カテゴリー開閉フラグの設定フラグを未設定状態にする
        form.setAdr010CategorySetInitFlg(0);

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
    private ActionForward __doBack(ActionMapping map, Adr280Form form,
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
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel umodel = getSessionUserModel(req);
        boolean adminUser = cmnBiz.isPluginAdmin(con, umodel, GSConstAddress.PLUGIN_ID_ADDRESS);
        if (!adminUser) {
            con.setAutoCommit(true);
            AdrAconfDao dao = new AdrAconfDao(con);
            AdrAconfModel model = dao.selectAconf();
            con.setAutoCommit(false);
            if (model != null && model.getAacAlbEdit() == GSConstAddress.POW_LIMIT) {
                return getNotAdminSeniPage(map, req);
            }
        }
        return null;
    }

}