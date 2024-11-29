package jp.groupsession.v2.cmn.cmn996;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ＤＢ接続エラー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn996Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn996Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <p>アクセス可能なドメインかを判定
     * @param req リクエスト
     * @return true:許可する,false:許可しない
     */
    public boolean canAccessDomain(HttpServletRequest req) {
        return true;
    }

    /**
     * <p>データベースへのコネクションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoConnection(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                     org.apache.struts.action.ActionForm,
     *                     javax.servlet.http.HttpServletRequest,
     *                     javax.servlet.http.HttpServletResponse,
     *                     java.sql.Connection)
    */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        ActionForward forward = null;
        Cmn996Form cmn996Form = (Cmn996Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("cmd = " + cmd);
        //DBコネクション取得エラー画面
        if (cmd.equals("CON_ERROR")) {
            log__.debug("コネクションエラーメッセージ");
            __setDomainErrorDispParam(map, req);
            forward = map.getInputForward();
        //バックアップ中エラー画面
        } else if (cmd.equals("BACKUP_PROG")) {
            log__.debug("バックアップ実行中エラーメッセージ");
            __setBackupProgressDispParam(map, cmn996Form, req, res);
            forward = map.getInputForward();
        //リダイレクト
        } else if (cmd.equals("cmn996ok")) {
            forward = map.findForward("gf_domain_logout");
        } else {
            log__.debug("デフォルト");
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * <br>[機  能] ドメインエラー画面の設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setDomainErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {


        Cmn996Form cmn996Form = new Cmn996Form();
        ActionForward urlForward = null;

        //ドメインエラー警告画面パラメータの設定
        cmn996Form.setType(Cmn996Form.TYPE_OK);
        cmn996Form.setIcon(Cmn996Form.ICON_WARN);
        cmn996Form.setWtarget(Cmn996Form.WTARGET_TOP);

        urlForward = map.findForward("gf_domain_logout");
        cmn996Form.setUrlOK(urlForward.getPath());
        MessageResources msgRes = getResources(req);
        cmn996Form.setMessage(msgRes.getMessage("error.busy.line"));

        //別ウィンドウで表示している場合、「閉じる」ボタンを表示する。
        ILogin loginBiz = _getLoginInstance();
        if (loginBiz.isPopup()) {
            cmn996Form.setType_popup(Cmn999Form.POPUP_TRUE);
        }

        req.setAttribute("cmn996Form", cmn996Form);
    }

    /**
     * <br>[機  能] バックアップ実行中エラー画面の設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     */
    private void __setBackupProgressDispParam(
            ActionMapping map,
            Cmn996Form form,
            HttpServletRequest req,
            HttpServletResponse res) {

            ActionForward urlForward = null;

        //ドメインエラー警告画面パラメータの設定
        form.setType(Cmn996Form.TYPE_OK);
        form.setIcon(Cmn996Form.ICON_INFO);
        form.setWtarget(Cmn996Form.WTARGET_TOP);

        urlForward = map.findForward("gf_domain_logout");
        form.setUrlOK(urlForward.getPath());
        MessageResources msgRes = getResources(req);
        form.setMessage(msgRes.getMessage("error.backing.up"));

        //別ウィンドウで表示している場合、「閉じる」ボタンを表示する。
        ILogin loginBiz = _getLoginInstance();
        if (loginBiz.isPopup()) {
            form.setType_popup(Cmn999Form.POPUP_TRUE);
        }

        req.setAttribute("cmn996Form", form);
    }

}