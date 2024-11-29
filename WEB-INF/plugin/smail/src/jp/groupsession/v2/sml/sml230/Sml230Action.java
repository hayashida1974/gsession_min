package jp.groupsession.v2.sml.sml230;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ショートメール ひな形一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml230Action extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml230Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return (form != null
                && ((Sml230Form) form).getSml050HinaKbn() == GSConstSmail.HINA_KBN_PRI);
    }

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
     * @see jp.groupsession.v2.sml.AbstractSmlAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeSmail(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        log__.debug("START_SML050");

        ActionForward forward = null;
        Sml230Form smlform = (Sml230Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

//        //追加ボタン押下
//        if (cmd.equals("labelAdd")) {
//            log__.debug("追加ボタン押下");
//            forward = map.findForward("labelEdit");
//        //戻るボタン押下
//        } else
            if (cmd.equals("backToList")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("backToList");
//        //編集ボタン押下
//        } else if (cmd.equals("edit")) {
//            log__.debug("編集ボタン押下");
//            forward = map.findForward("hinaInput");
        //初期表示
        } else {
            log__.debug("初期表示");

            smlform.setSelectedHinaSid(0);
            int page = smlform.getSml050PageNum();

            //左矢印ボタン押下
            if (cmd.equals("arrorw_left")) {
                log__.debug("左矢印ボタン押下");
                page -= 1;
            //右矢印ボタン押下
            } else if (cmd.equals("arrorw_right")) {
                log__.debug("右矢印ボタン押下");
                page += 1;
            }

            if (page < 1) {
                page = 1;
            }

            //ページセット
            smlform.setSml050PageNum(page);

            forward = __doInit(map, smlform, req, res, con);
        }

        log__.debug("END_SML050");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Sml230Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);

        Sml230ParamModel paramMdl = new Sml230ParamModel();
        paramMdl.setParam(form);
        //ひな形一覧を取得する
        Sml230Biz biz = new Sml230Biz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }
}