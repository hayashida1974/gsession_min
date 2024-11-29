package jp.groupsession.v2.rng.rng160;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 稟議 自動削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng160Action extends AbstractRingiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng160Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Rng160Form thisForm = (Rng160Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            // トランザクショントークン設定
            this.saveToken(req);
            //設定ボタンクリック
            forward = map.findForward("confirm");

        } else if (cmd.equals("backMenu")) {
            //戻るボタンクリック
            forward = map.findForward("rngAdmMenu");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }


    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng160Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //初期表示情報を設定
        RequestModel reqMdl = getRequestModel(req);
        Rng160ParamModel paramMdl = new Rng160ParamModel();
        paramMdl.setParam(form);
        Rng160Biz biz = new Rng160Biz();

        con.setAutoCommit(true);
        biz.setInitData(con, paramMdl, reqMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }
}
