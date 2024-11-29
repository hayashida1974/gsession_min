package jp.groupsession.v2.sml.sml100;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmlAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ショートメール 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml100Action extends AbstractSmailAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml100Action.class);

    /**
     * <br>アクション実行
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

        ActionForward forward = null;
        Sml100Form smlForm = (Sml100Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("fwconf")) {
            //メール転送設定
            forward = map.findForward("fwconf");
        } else if (cmd.equals("sml100back")) {
            //戻る
            forward = __doBack(map, smlForm, req, res, con);

        } else if (cmd.equals("smailAutoDelete")) {
            //メール自動削除設定
            forward = map.findForward("smailAutoDelete");

        } else if (cmd.equals("smailManualDelete")) {
            //メール手動削除設定
            forward = map.findForward("smailManualDelete");

        } else if (cmd.equals("hina_edit")) {
            //共通ひな形管理
            forward = map.findForward("cmnSmailTemplate");

        } else if (cmd.equals("fwconf_plurals")) {
            //メール転送一括設定
            forward = map.findForward("fwconfPlurals");



        } else if (cmd.equals("accountList")) {
            //アカウント管理
            forward = map.findForward("accountList");

        } else if (cmd.equals("smlConfAccount")) {
            //アカウント基本設定
            forward = map.findForward("smlConfAccount");

        } else if (cmd.equals("smlAdmDspConf")) {
            //表示設定
            forward = map.findForward("smlAdmDspConf");

        } else if (cmd.equals("smlBanDestination")) {
            //送信先制限設定
            forward = map.findForward("smlBanDestination");

        } else if (cmd.equals("smlLogCount")) {
            //統計情報
            forward = map.findForward("smlLogCount");

        } else {
            //メニュー表示
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
            SmlAdminModel mdl = new SmlAdminModel();
            mdl = smlBiz.getSmailAdminConf(getRequestModel(req).getSmodel().getUsrsid(), con);
            smlForm.setSml100autoDelKbn(mdl.getSmaAutoDelKbn());

            //GS管理者情報を設定
            BaseUserModel buMdl = getSessionUserModel(req);
            smlForm.setSml100GsAdminFlg(buMdl.getAdminFlg());

            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Sml100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            //メイン管理者設定画面へ戻る。
            return map.findForward("mainAdmSetting");
        }
        return map.findForward("backToList");
    }
}
