package jp.groupsession.v2.man.man070;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 管理者設定 プロキシサーバ設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man070Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man070Action.class);

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
        Man070Form thisForm = (Man070Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //クラウド版の場合は不正なアクセス
        if (!GroupSession.getResourceManager().getDomain(req).equals(GSConst.GS_DOMAIN)) {
            forward = map.findForward("gf_submit");
            return forward;
        }

        //OKボタン押下
        if (cmd.equals("kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doKakunin(map, thisForm, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_kanri_menu")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_to_kanri_menu");
        //確認画面から戻ってきた場合
        } else if (cmd.equals("input")) {
            log__.debug("確認画面から戻ってきた");
            forward = map.getInputForward();
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisForm, req, res, con);
        }

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
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws EncryptionException パスワードの復号化に失敗
     */
    private ActionForward __doInit(ActionMapping map,
                                    Man070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, EncryptionException {

        try {

            //初期表示データセット
            con.setAutoCommit(true);
            Man070Biz biz = new Man070Biz(req);
            biz.setInitData(form, con);
            con.setAutoCommit(false);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;

        } catch (EncryptionException e) {
            log__.error("EncryptionException", e);
            throw e;
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確認ボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doKakunin(ActionMapping map,
                                       Man070Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con) {

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        saveToken(req);

        return map.findForward("kakunin");
    }
}