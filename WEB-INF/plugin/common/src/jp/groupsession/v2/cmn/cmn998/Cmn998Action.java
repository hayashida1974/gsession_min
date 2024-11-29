package jp.groupsession.v2.cmn.cmn998;

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
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ドメインエラーメッセージ画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn998Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn998Action.class);

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

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("cmd = " + cmd);

        //ドメインエラー画面
        if (cmd.equals("DOMAIN_ERROR")) {
            log__.debug("ドメインエラーメッセージ");
            __setDomainErrorDispParam(map, req);
            forward = map.getInputForward();
          //リダイレクト
        } else if (cmd.equals("cmn998ok")) {
            forward = map.findForward("gf_logout");
        } else {
            log__.debug("デフォルト");
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * [機  能] ドメインエラー画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setDomainErrorDispParam(
        ActionMapping map,
        HttpServletRequest req) {


        Cmn998Form cmn998Form = new Cmn998Form();
        ActionForward urlForward = null;

        //ドメインエラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn998Form.setType(Cmn998Form.TYPE_OK);
        cmn998Form.setIcon(Cmn998Form.ICON_WARN);
        cmn998Form.setWtarget(Cmn998Form.WTARGET_TOP);

        urlForward = map.findForward("gf_domain_logout");
        cmn998Form.setUrlOK(urlForward.getPath());
        cmn998Form.setMessage(msgRes.getMessage("error.access.login.user"));
        req.setAttribute("cmn998Form", cmn998Form);
    }

}