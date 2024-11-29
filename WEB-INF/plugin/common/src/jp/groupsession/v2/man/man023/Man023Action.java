package jp.groupsession.v2.man.man023;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メイン 管理者設定 休日テンプレート一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man023Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man023Action.class);

    /** 戻るボタン押下時CMD */
    private static String cmd_BACK = "backHoliday";
    /** 休日に反映ボタン押下時CMD */
    private static String cmd_REFLECT = "reflect";

    /** テンプレート追加ボタン押下時CMD */
    private static String cmd_ADD = "addTemp";
    /** テンプレート削除ボタン押下時CMD */
    private static String cmd_DEL = "delTemp";
    /** テンプレート変更ボタン押下時CMD */
    private static String cmd_EDIT = "editTemp";
    /** テンプレート変更ボタン押下時CMD拡張 */
    private static String cmd_EDIT_KAKU = "editTempKaku";
    /** テンプレート変更ボタン押下時CMD拡張 */
    private static String cmd_IMPORT = "import";

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
        Man023Form thisForm = (Man023Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        log__.debug(">>>tempSid :" + thisForm.getEditHltSid());

        if (cmd.equals(cmd_BACK)) {
            log__.debug(">>>戻るボタン押下");
            forward = map.findForward(cmd_BACK);
        } else if (cmd.equals(cmd_REFLECT)) {
            log__.debug(">>>休日に反映ボタン押下");
            forward = __doReflect(map, thisForm, req, res, con);
        } else if (cmd.equals(cmd_ADD)) {
            log__.debug(">>>追加ボタン押下");
            saveToken(req);
            forward = map.findForward(cmd_ADD);
        } else if (cmd.equals(cmd_DEL)) {
            log__.debug(">>>削除ボタン押下");
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals(cmd_EDIT)) {
            log__.debug(">>>変更ボタン押下");
            forward = __doEdit(map, thisForm, req, res, con);
        } else if (cmd.equals(cmd_IMPORT)) {
            log__.debug(">>>変更ボタン押下");
            forward = map.findForward(cmd_IMPORT);

        } else {
            log__.debug(">>>初期表示");
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Man023Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        Man023ParamModel paramMdl = new Man023ParamModel();
        paramMdl.setParam(form);
        Man023Biz biz = new Man023Biz();
        biz.getInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

    }

    /**
     * <br>[機  能] 変更ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doEdit(ActionMapping map, Man023Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        ActionForward forward = null;

        saveToken(req);

        CmnHolidayTemplateDao dao = new CmnHolidayTemplateDao(con);
        CmnHolidayTemplateModel model = new CmnHolidayTemplateModel();

        con.setAutoCommit(true);
        model.setHltSid(form.getEditHltSid());
        model = dao.select(model);
        con.setAutoCommit(false);

        forward = map.findForward(cmd_EDIT);
        if (model.getHltExflg() == 1) {
            forward = map.findForward(cmd_EDIT_KAKU);
        }

        return forward;
    }

    /**
     * <br>[機  能] 削除ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Man023Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        log__.debug("form.getMan023CheckAll()     :" + form.getMan023CheckAll());
        log__.debug("form.getMan023hltSid()       :" + form.getMan023hltSid());
        log__.debug("form.getMan023TemplateList() :" + form.getMan023TemplateList());
        //休日が選択されていない場合はエラー
        boolean err = false;
        if (form.getMan023hltSid() == null || form.getMan023hltSid().length <= 0) {
            err = true;
        }

        int length = NullDefault.getInt(req.getParameter("length"), -1);
        if (form.getMan023CheckAll() == 1) {
            if (length <= 0) {
                log__.debug("■休日テンプレートゼロ件です");
                err = true;

            }
        }

        if (err) {

            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    getInterMessage(req, "main.holiday.template"));
            errors.add("hltSid.error.select.required.text", msg);

            addErrors(req, errors);
            __doInit(map, form, req, res, con);

            return map.getInputForward();
        }

        //日付削除確認画面へ
        saveToken(req);
        return map.findForward(cmd_DEL);
    }

    /**
     * <br>[機  能] 休日に反映ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doReflect(ActionMapping map, Man023Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        //休日が選択されていない場合はエラー
        boolean err = false;
        if (form.getMan023hltSid() == null || form.getMan023hltSid().length <= 0) {
            err = true;
        }

        int length = NullDefault.getInt(req.getParameter("length"), -1);
        if (form.getMan023CheckAll() == 1) {
            if (length <= 0) {
                log__.debug("■休日テンプレートゼロ件です");
                err = true;

            }
        }

        if (err) {

            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    getInterMessage(req, "main.holiday.template"));
            errors.add("hltSid.error.select.required.text", msg);

            addErrors(req, errors);
            __doInit(map, form, req, res, con);

            return map.getInputForward();
        }

        //休日に反映確認画面へ
        saveToken(req);
        return map.findForward(cmd_REFLECT);
    }

}
