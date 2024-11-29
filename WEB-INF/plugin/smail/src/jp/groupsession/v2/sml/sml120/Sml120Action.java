package jp.groupsession.v2.sml.sml120;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 * <br>[機  能] ショートメール 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml120Action extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml120Action.class);

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

        ActionForward forward = null;
        Sml120Form thisForm = (Sml120Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //戻るボタン押下
        if (cmd.equals("backToMailList")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, thisForm);
        //メール表示件数・自動リロード時間設定
        } else if (cmd.equals("smailKsetting")) {
            log__.debug("メール表示件数・自動リロード時間設定");
            forward = map.findForward("smailKsetting");
        //メール転送設定
        } else if (cmd.equals("smailForwardsetting")) {
            log__.debug("メール転送設定");
            forward = map.findForward("smailForwardsetting");
        //メール自動削除設定
        } else if (cmd.equals("smailAutoDelete")) {
            log__.debug("メール自動削除設定");
            forward = map.findForward("smailAutoDelete");
        //メール手動削除
        } else if (cmd.equals("smailManualDelete")) {
            log__.debug("メール手動削除");
            forward = map.findForward("smailManualDelete");
        //アカウント管理
        } else if (cmd.equals("smailAccount")) {
            log__.debug("アカウント管理");
            forward = map.findForward("smailAccount");
        } else {
            log__.debug("初期表示");
            //forward = map.getInputForward();
            ((Sml120Form) form).setSmlAccountMode(GSConstSmail.ACCOUNTMODE_PSNLSETTING);
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Sml120Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        Sml120ParamModel paramMdl = new Sml120ParamModel();
        paramMdl.setParam(form);
        Sml120Biz biz = new Sml120Biz();
        biz.setInitData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __doBack(ActionMapping map, Sml120Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            //メイン個人設定メニューへ戻る。
            return map.findForward("mainPriSetting");
        }

        return map.findForward("backToMailList");
    }
}