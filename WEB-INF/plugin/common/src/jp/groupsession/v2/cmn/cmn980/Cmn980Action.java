package jp.groupsession.v2.cmn.cmn980;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] GSファイアウォール設定画面 Actionクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn980Action extends AdminAction {


    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        FirewallBiz.loadConf(getAppRootPath());

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
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return __isLocalAccess(req);
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
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return __isLocalAccess(req);
    }
}
