package jp.groupsession.v2.wml.wml041;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;

/**
 * <br>[機  能] WEBメール アカウント 署名登録(ポップアップ)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml041Action extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml041Action.class);

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
        Wml041Form thisForm = (Wml041Form) form;

        String cmd = NullDefault.getString(req.getParameter("wml041CMD"), "");
        if (cmd.equals("saveSign")) {
            forward = __saveAccountSign(map, thisForm, req);
        } else {
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
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
    public ActionForward __doInit(ActionMapping map, Wml041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Wml041Biz biz = new Wml041Biz();
        Wml041ParamModel paramMdl = new Wml041ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(getRequestModel(req),  paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 署名情報の登録
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __saveAccountSign(ActionMapping map, Wml041Form form,
                                                                HttpServletRequest req)
        throws Exception {

        //入力チェック
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheck(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        Wml041Biz biz = new Wml041Biz();
        Wml041ParamModel paramMdl = new Wml041ParamModel();
        paramMdl.setParam(form);
        biz.saveSignData(reqMdl, paramMdl);
        form.setWml041endFlg(1);

        return map.getInputForward();
    }
}
