package jp.groupsession.v2.bbs.bbs140;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.GSConstBulletin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 掲示板 フォーラムメンバー閲覧状況ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs140Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs140Action.class);

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
        Bbs140Form bbs140Form = (Bbs140Form) form;

        //フォーラム閲覧権限チェック
        boolean forumAuthRead = _checkForumAuth(map, req, con,
                bbs140Form.getBbs010forumSid(), GSConstBulletin.ACCESS_KBN_READ);
        if (!forumAuthRead) {
            return map.findForward("gf_msg");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);


        if (cmd.equals("arrorw_right")) {
            //次のページ
            forward = __doNext(map, bbs140Form, req, res, con);
        } else if (cmd.equals("arrorw_left")) {
            //前のページ
            forward = __doPrev(map, bbs140Form, req, res, con);
        } else if (cmd.equals("pageChange")) {
            log__.debug("ページコンボチェンジ");
            forward = __doInit(map, bbs140Form, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, bbs140Form, req, res, con);
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
    private ActionForward __doInit(ActionMapping map, Bbs140Form form,
                                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示処理");

        con.setAutoCommit(true);
        Bbs140ParamModel paramMdl = new Bbs140ParamModel();
        paramMdl.setParam(form);
        Bbs140Biz.setDsp(con, paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Bbs140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;

        //ページ設定
        int page = form.getBbs140pageNum1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setBbs140pageNum1(page);
        form.setBbs140pageNum2(page);

        forward = __doInit(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doNext(
        ActionMapping map,
        Bbs140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;

        //ページ設定
        int page = form.getBbs140pageNum1();
        page += 1;
        if (page < 1) {
            page = 1;
        }

        form.setBbs140pageNum1(page);
        form.setBbs140pageNum2(page);

        forward = __doInit(map, form, req, res, con);
        return forward;
    }
}