package jp.groupsession.v2.wml.wml020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RedirectingActionForward;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.wml.AbstractWebmailAdminAction;

/**
 * <br>[機  能] WEBメール 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml020Action extends AbstractWebmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml020Action.class);

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
        Wml020Form wmlForm = (Wml020Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("accountManager")) {
            //「アカウントマネージャー」クリック
            forward = map.findForward("accountManager");

        } else if (cmd.equals("confAccount")) {
            //「アカウント設定」クリック
            forward = map.findForward("confAccount");

        } else if (cmd.equals("confMailTemplate")) {
            //「メールテンプレート管理」クリック
            forward = map.findForward("mailTemplateConf");

        } else if (cmd.equals("confSendList")) {
            //「送信先リスト管理」クリック
            forward = map.findForward("confSendList");

        } else if (cmd.equals("autoDelete")) {
            //「自動データ削除設定」クリック
            forward = map.findForward("autoDelete");

        } else if (cmd.equals("manualDelete")) {
            //「手動データ削除」クリック
            forward = map.findForward("manualDelete");

        } else if (cmd.equals("mailLog")) {
            //「送受信ログ管理」クリック
            forward = map.findForward("mailLog");

        } else if (cmd.equals("autoDeleteHistory")) {
            //「送受信ログ自動削除設定」クリック
            forward = map.findForward("autoDeleteHistory");

        } else if (cmd.equals("manualDeleteHistory")) {
            //「送受信ログ手動削除」クリック
            forward = map.findForward("manualDeleteHistory");

        } else if (cmd.equals("timesentManager")) {
            //「送信予定メール管理」クリック
            forward = map.findForward("timesentManager");

        } else if (cmd.equals("mailList")) {
            //戻るボタンクリック
            forward = __doBack(map, wmlForm);

        } else if (cmd.equals("wmlLogCount")) {
            //統計情報リンククリック
            forward = map.findForward("wmlLogCount");

        } else {
            //GS管理者情報を設定
            BaseUserModel buMdl = getSessionUserModel(req);
            wmlForm.setWml020GsAdminFlg(buMdl.getAdminFlg());

            //初期表示
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 戻るボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Wml020Form form)
            throws SQLException {
        log__.debug("戻る");

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            return map.findForward("mainAdmSetting");
        }
        RedirectingActionForward redirectForward
                          = new RedirectingActionForward("/webmail/wml010.do");
        return redirectForward;
    }
}

