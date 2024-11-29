package jp.groupsession.v2.sml.sml340;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml340Action extends AbstractSmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml340Action.class);

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
        Sml340Form thisForm = (Sml340Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);

        } else if (cmd.equals("filterList")) {
            //戻るボタンクリック
            forward = map.findForward("filterConf");

        } else if (cmd.equals("filterSearch")) {
            //フィルターテストボタンクリック
            forward = __doFilterSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページボタンクリック
            thisForm.setSml340mailListPageTop(thisForm.getSml340mailListPageTop() - 1);
            thisForm.setSml340mailListPageBottom(thisForm.getSml340mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            thisForm.setSml340mailListPageTop(thisForm.getSml340mailListPageTop() + 1);
            thisForm.setSml340mailListPageBottom(thisForm.getSml340mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

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
    public ActionForward __doInit(ActionMapping map, Sml340Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Sml340ParamModel paramMdl = new Sml340ParamModel();
        paramMdl.setParam(form);
        Sml340Biz biz = new Sml340Biz();
        biz.setInitData(con, paramMdl, getSessionUserSid(req),
                        getRequestModel(req));
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
    public ActionForward __doOK(ActionMapping map, Sml340Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        if (form.getSml340viewMailList() == 1) {
            __saveFilterCondition(form);
        }

        return map.findForward("confirm");
    }

    /**
     * <br>[機  能] フィルターテストボタンクリック時処理
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
    public ActionForward __doFilterSearch(ActionMapping map, Sml340Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        form.validateCondition(errors, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            form.setSml340viewMailList(1);
            __saveFilterCondition(form);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] フィルター条件の保存を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    private void __saveFilterCondition(Sml340Form form) {
        form.setSml340svFilterType(form.getSml340filterType());
        form.setSml340svCondition1(form.getSml340condition1());
        form.setSml340svConditionType1(form.getSml340conditionType1());
        form.setSml340svConditionExs1(form.getSml340conditionExs1());
        form.setSml340svConditionText1(form.getSml340conditionText1());
        form.setSml340svCondition2(form.getSml340condition2());
        form.setSml340svConditionType2(form.getSml340conditionType2());
        form.setSml340svConditionExs2(form.getSml340conditionExs2());
        form.setSml340svConditionText2(form.getSml340conditionText2());
        form.setSml340svCondition3(form.getSml340condition3());
        form.setSml340svConditionType3(form.getSml340conditionType3());
        form.setSml340svConditionExs3(form.getSml340conditionExs3());
        form.setSml340svConditionText3(form.getSml340conditionText3());
        form.setSml340svCondition4(form.getSml340condition4());
        form.setSml340svConditionType4(form.getSml340conditionType4());
        form.setSml340svConditionExs4(form.getSml340conditionExs4());
        form.setSml340svConditionText4(form.getSml340conditionText4());
        form.setSml340svCondition5(form.getSml340condition5());
        form.setSml340svConditionType5(form.getSml340conditionType5());
        form.setSml340svConditionExs5(form.getSml340conditionExs5());
        form.setSml340svConditionText5(form.getSml340conditionText5());
        form.setSml340svTempFile(form.getSml340tempFile());
    }
}
