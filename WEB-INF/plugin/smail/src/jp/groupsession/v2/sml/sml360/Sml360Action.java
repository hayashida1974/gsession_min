package jp.groupsession.v2.sml.sml360;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ショートメール 個人設定 フィルタ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml360Action extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml360Action.class);

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
    public ActionForward executeSmail(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Sml360Form thisForm = (Sml360Form) form;

        //編集可能かを判定
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(con, getRequestModel(req));
        if ((!_checkAuth(map, req, con)) && thisForm.getSmlAccountSid() > 0) {
            if (!smlCmnBiz.canEditAccount(con, thisForm.getSmlAccountSid(),
                                    getSessionUserModel(req).getUsrsid())) {
                return map.findForward("gf_msg");
            }
        }

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
            thisForm.setSml360mailListPageTop(thisForm.getSml360mailListPageTop() - 1);
            thisForm.setSml360mailListPageBottom(thisForm.getSml360mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            thisForm.setSml360mailListPageTop(thisForm.getSml360mailListPageTop() + 1);
            thisForm.setSml360mailListPageBottom(thisForm.getSml360mailListPageTop());
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
    public ActionForward __doInit(ActionMapping map, Sml360Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Sml360ParamModel paramMdl = new Sml360ParamModel();
        paramMdl.setParam(form);
        Sml360Biz biz = new Sml360Biz();
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
    public ActionForward __doOK(ActionMapping map, Sml360Form form,
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

        if (form.getSml360viewMailList() == 1) {
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
    public ActionForward __doFilterSearch(ActionMapping map, Sml360Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        form.validateCondition(errors, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            form.setSml360viewMailList(1);
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
    private void __saveFilterCondition(Sml360Form form) {
        form.setSml360svFilterType(form.getSml360filterType());
        form.setSml360svCondition1(form.getSml360condition1());
        form.setSml360svConditionType1(form.getSml360conditionType1());
        form.setSml360svConditionExs1(form.getSml360conditionExs1());
        form.setSml360svConditionText1(form.getSml360conditionText1());
        form.setSml360svCondition2(form.getSml360condition2());
        form.setSml360svConditionType2(form.getSml360conditionType2());
        form.setSml360svConditionExs2(form.getSml360conditionExs2());
        form.setSml360svConditionText2(form.getSml360conditionText2());
        form.setSml360svCondition3(form.getSml360condition3());
        form.setSml360svConditionType3(form.getSml360conditionType3());
        form.setSml360svConditionExs3(form.getSml360conditionExs3());
        form.setSml360svConditionText3(form.getSml360conditionText3());
        form.setSml360svCondition4(form.getSml360condition4());
        form.setSml360svConditionType4(form.getSml360conditionType4());
        form.setSml360svConditionExs4(form.getSml360conditionExs4());
        form.setSml360svConditionText4(form.getSml360conditionText4());
        form.setSml360svCondition5(form.getSml360condition5());
        form.setSml360svConditionType5(form.getSml360conditionType5());
        form.setSml360svConditionExs5(form.getSml360conditionExs5());
        form.setSml360svConditionText5(form.getSml360conditionText5());
        form.setSml360svTempFile(form.getSml360tempFile());
    }
}
