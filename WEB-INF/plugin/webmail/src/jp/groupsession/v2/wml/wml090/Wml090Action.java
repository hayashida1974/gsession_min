package jp.groupsession.v2.wml.wml090;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.wml.AbstractWebmailAction;

/**
 * <br>[機  能] WEBメール 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml090Action extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml090Action.class);

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("accountList")) {
            //「アカウント管理」クリック
            forward = map.findForward("userAccountList");

        } else if (cmd.equals("labelList")) {
            //「ラベル管理」クリック
            forward = map.findForward("labelList");

        } else if (cmd.equals("filterList")) {
            //「フィルタ設定」クリック
            forward = map.findForward("filterList");

        } else if (cmd.equals("confSendList")) {
            //「送信先リスト設定」クリック
            forward = map.findForward("confSendList");

        } else if (cmd.equals("deleteMailList")) {
            //「メール情報一括削除」クリック
            forward = map.findForward("deleteMailList");

        // *** add(s) システムプロステージ ***
        } else if (cmd.equals("importEmlFiles")) {
            //「メールインポート」クリック
            forward = map.findForward("importEmlFiles");

        // *** add(e) システムプロステージ ***
        } else if (cmd.equals("mailList")) {
            //戻るボタンクリック
            forward = __doBack(map, (Wml090Form) form);

        } else {
            //初期表示
            ((Wml090Form) form).setWmlAccountMode(GSConstWebmail.ACCOUNTMODE_PSNLSETTING);
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
    private ActionForward __doBack(ActionMapping map, Wml090Form form)
            throws SQLException {
        log__.debug("戻る");

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            return map.findForward("mainPriSetting");
        }
        RedirectingActionForward redirectForward
                         = new RedirectingActionForward("/webmail/wml010.do");
        return redirectForward;
    }
}

