package jp.groupsession.v2.wml.wml140;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.wml.AbstractWebmailAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] WEBメール フィルタ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml140Action extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml140Action.class);

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
        Wml140Form thisForm = (Wml140Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);

        } else if (cmd.equals("filterList")) {
            //戻るボタンクリック
            forward = map.findForward("filterList");

        } else if (cmd.equals("filterSearch")) {
            //フィルターテストボタンクリック
            forward = __doFilterSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページボタンクリック
            thisForm.setWml140mailListPageTop(thisForm.getWml140mailListPageTop() - 1);
            thisForm.setWml140mailListPageBottom(thisForm.getWml140mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            thisForm.setWml140mailListPageTop(thisForm.getWml140mailListPageTop() + 1);
            thisForm.setWml140mailListPageBottom(thisForm.getWml140mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("addFwAddress")) {
            //転送先メールアドレスの追加
            String[] fwAddress = thisForm.getWml140actionSendValue();
            String[] newFwAddress = new String[2];
            if (fwAddress != null) {
                newFwAddress = new String[fwAddress.length + 1];
                System.arraycopy(fwAddress, 0, newFwAddress, 0, fwAddress.length);
            }
            thisForm.setWml140actionSendValue(newFwAddress);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("delFwAddress")) {
            //転送先メールアドレスの削除
            int delIdx = thisForm.getWml140actionSendValueDelIdx();
            String[] fwAddress = thisForm.getWml140actionSendValue();
            if (fwAddress != null && fwAddress.length > 1 && delIdx > 0) {
                String[] newFwAddress = new String[fwAddress.length - 1];
                System.arraycopy(fwAddress, 0, newFwAddress, 0, delIdx);

                delIdx++;
                if (delIdx < fwAddress.length) {
                    System.arraycopy(fwAddress, delIdx, newFwAddress,
                                                delIdx - 1, fwAddress.length - delIdx);
                }
                thisForm.setWml140actionSendValue(newFwAddress);
            }

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
    public ActionForward __doInit(ActionMapping map, Wml140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Wml140ParamModel paramMdl = new Wml140ParamModel();
        paramMdl.setParam(form);
        Wml140Biz biz = new Wml140Biz();
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
    public ActionForward __doOK(ActionMapping map, Wml140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        if (form.getWml140viewMailList() == 1) {
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
    public ActionForward __doFilterSearch(ActionMapping map, Wml140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        form.validateCondition(errors, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            form.setWml140viewMailList(1);
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
    private void __saveFilterCondition(Wml140Form form) {
        form.setWml140svFilterType(form.getWml140filterType());
        form.setWml140svCondition1(form.getWml140condition1());
        form.setWml140svConditionType1(form.getWml140conditionType1());
        form.setWml140svConditionExs1(form.getWml140conditionExs1());
        form.setWml140svConditionText1(form.getWml140conditionText1());
        form.setWml140svCondition2(form.getWml140condition2());
        form.setWml140svConditionType2(form.getWml140conditionType2());
        form.setWml140svConditionExs2(form.getWml140conditionExs2());
        form.setWml140svConditionText2(form.getWml140conditionText2());
        form.setWml140svCondition3(form.getWml140condition3());
        form.setWml140svConditionType3(form.getWml140conditionType3());
        form.setWml140svConditionExs3(form.getWml140conditionExs3());
        form.setWml140svConditionText3(form.getWml140conditionText3());
        form.setWml140svCondition4(form.getWml140condition4());
        form.setWml140svConditionType4(form.getWml140conditionType4());
        form.setWml140svConditionExs4(form.getWml140conditionExs4());
        form.setWml140svConditionText4(form.getWml140conditionText4());
        form.setWml140svCondition5(form.getWml140condition5());
        form.setWml140svConditionType5(form.getWml140conditionType5());
        form.setWml140svConditionExs5(form.getWml140conditionExs5());
        form.setWml140svConditionText5(form.getWml140conditionText5());
        form.setWml140svTempFile(form.getWml140tempFile());
    }
}
