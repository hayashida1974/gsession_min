package jp.groupsession.v2.usr.usr900;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GsResourceBundle;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ユーザ数上限再読込画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr900Action extends AbstractGsAction {
    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return __isLocalAccess(req);
    }

    /**
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return __isLocalAccess(req);
    }

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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        //ユーザ数上限の再読み込み
        GsResourceBundle.reloadUserLimit();

        ActionForward forward = map.findForward("gf_msg");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        //URL
        urlForward = map.findForward("gf_menu");
        if (!isSession(req)) {
            //未ログインの場合、ログイン画面へ遷移
            urlForward = map.findForward("gf_logout");
        } else if (getSessionUserSid(req) == GSConst.SYSTEM_USER_ADMIN) {
            //adminユーザの場合、管理者設定メニュー画面へ遷移
            urlForward = map.findForward("gf_main_kanri");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //完了メッセージ
        GsMessage gsMsg = new GsMessage();
        String compMsg = gsMsg.getMessage(req, "cmn.complete.reload");
        cmn999Form.setMessage(compMsg);
        req.setAttribute("cmn999Form", cmn999Form);

        return forward;
    }

    /**
     * <br>[機  能] リクエスト元がAPサーバと一致するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true: 一致する false: 一致しない
     */
    private boolean __isLocalAccess(HttpServletRequest req) {

        RequestModel reqMdl = getRequestModel(req);
        String remoteAddr = NullDefault.getString(reqMdl.getRemoteAddr(), "");
        return remoteAddr.equals("127.0.0.1")
                || remoteAddr.equals("::1")
                || remoteAddr.equals("0:0:0:0:0:0:0:1");
    }
}