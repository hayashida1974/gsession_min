package jp.groupsession.v2.cmn.cmn260;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] OAuth認証情報登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn260Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn260Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Cmn260Form thisForm = (Cmn260Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("cmn260Next")) {
            //OKボタン押下時
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("cmn260Back")) {
            //戻るボタン押下時
            forward = map.findForward("cmn260Back");

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
    private ActionForward __doInit(ActionMapping map, Cmn260Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Cmn260ParamModel paramMdl = new Cmn260ParamModel();
        paramMdl.setParam(form);
        Cmn260Biz biz = new Cmn260Biz();
        biz._setInitData(con, paramMdl, reqMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
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
    public ActionForward __doOk(ActionMapping map, Cmn260Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        //Oauth認証情報の重複チェック
        Cmn260ParamModel paramMdl = new Cmn260ParamModel();
        Cmn260Biz biz = new Cmn260Biz();
        paramMdl.setParam(form);
        boolean result = biz._duplicateCheck(con, paramMdl);
        if (result) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = null;
            msg = new ActionMessage("error.oauth.provider.check");
            errors.add("error.oauth.provider.check", msg);
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //トランザクショントークン設定
        this.saveToken(req);

        return map.findForward("cmn260Next");
    }
}