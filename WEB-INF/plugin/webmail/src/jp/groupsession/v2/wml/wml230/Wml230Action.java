package jp.groupsession.v2.wml.wml230;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.wml.AbstractWebmailAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] WEBメール 管理者設定 フィルタ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml230Action extends AbstractWebmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml230Action.class);

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
        Wml230Form thisForm = (Wml230Form) form;

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
            thisForm.setWml230mailListPageTop(thisForm.getWml230mailListPageTop() - 1);
            thisForm.setWml230mailListPageBottom(thisForm.getWml230mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            thisForm.setWml230mailListPageTop(thisForm.getWml230mailListPageTop() + 1);
            thisForm.setWml230mailListPageBottom(thisForm.getWml230mailListPageTop());
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("addFwAddress")) {
            //転送先メールアドレスの追加
            String[] fwAddress = thisForm.getWml230actionSendValue();
            String[] newFwAddress = new String[2];
            if (fwAddress != null) {
                newFwAddress = new String[fwAddress.length + 1];
                System.arraycopy(fwAddress, 0, newFwAddress, 0, fwAddress.length);
            }
            thisForm.setWml230actionSendValue(newFwAddress);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("delFwAddress")) {
            //転送先メールアドレスの削除
            int delIdx = thisForm.getWml230actionSendValueDelIdx();
            String[] fwAddress = thisForm.getWml230actionSendValue();
            if (fwAddress != null && fwAddress.length > 1 && delIdx > 0) {
                String[] newFwAddress = new String[fwAddress.length - 1];
                System.arraycopy(fwAddress, 0, newFwAddress, 0, delIdx);

                delIdx++;
                if (delIdx < fwAddress.length) {
                    System.arraycopy(fwAddress, delIdx, newFwAddress,
                                                delIdx - 1, fwAddress.length - delIdx);
                }
                thisForm.setWml230actionSendValue(newFwAddress);
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
    public ActionForward __doInit(ActionMapping map, Wml230Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Wml230ParamModel paramMdl = new Wml230ParamModel();
        paramMdl.setParam(form);
        Wml230Biz biz = new Wml230Biz();
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
    public ActionForward __doOK(ActionMapping map, Wml230Form form,
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

        if (form.getWml230viewMailList() == 1) {
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
    public ActionForward __doFilterSearch(ActionMapping map, Wml230Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        form.validateCondition(errors, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            form.setWml230viewMailList(1);
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
    private void __saveFilterCondition(Wml230Form form) {
        form.setWml230svFilterType(form.getWml230filterType());
        form.setWml230svCondition1(form.getWml230condition1());
        form.setWml230svConditionType1(form.getWml230conditionType1());
        form.setWml230svConditionExs1(form.getWml230conditionExs1());
        form.setWml230svConditionText1(form.getWml230conditionText1());
        form.setWml230svCondition2(form.getWml230condition2());
        form.setWml230svConditionType2(form.getWml230conditionType2());
        form.setWml230svConditionExs2(form.getWml230conditionExs2());
        form.setWml230svConditionText2(form.getWml230conditionText2());
        form.setWml230svCondition3(form.getWml230condition3());
        form.setWml230svConditionType3(form.getWml230conditionType3());
        form.setWml230svConditionExs3(form.getWml230conditionExs3());
        form.setWml230svConditionText3(form.getWml230conditionText3());
        form.setWml230svCondition4(form.getWml230condition4());
        form.setWml230svConditionType4(form.getWml230conditionType4());
        form.setWml230svConditionExs4(form.getWml230conditionExs4());
        form.setWml230svConditionText4(form.getWml230conditionText4());
        form.setWml230svCondition5(form.getWml230condition5());
        form.setWml230svConditionType5(form.getWml230conditionType5());
        form.setWml230svConditionExs5(form.getWml230conditionExs5());
        form.setWml230svConditionText5(form.getWml230conditionText5());
        form.setWml230svTempFile(form.getWml230tempFile());
    }
}
