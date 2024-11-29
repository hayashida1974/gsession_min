package jp.groupsession.v2.bbs.ptl010;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;

/**
 * <br>[機  能] ポートレット スレッド一覧のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsPtl010Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsPtl010Action.class);

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
        BbsPtl010Form bbsForm = (BbsPtl010Form) form;

        //フォーラム閲覧権限チェック
        con.setAutoCommit(true);
        boolean forumAuth = _checkForumAuth(
                map, req, con, bbsForm.getBbsPtlBfiSid(), GSConstBulletin.ACCESS_KBN_READ);
        con.setAutoCommit(false);

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bbsForm.setBbsPtl010page1(bbsForm.getBbsPtl010page1() - 1);
            forward = __doInit(map, bbsForm, req, con, forumAuth);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            bbsForm.setBbsPtl010page1(bbsForm.getBbsPtl010page1() + 1);
            forward = __doInit(map, bbsForm, req, con, forumAuth);

        } else {
            //初期表示
            forward = __doInit(map, bbsForm, req, con, forumAuth);

        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @param forumAuth 閲覧権限
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        BbsPtl010Form form,
        HttpServletRequest req,
        Connection con,
        boolean forumAuth
        )
        throws Exception {

        //ログインユーザ情報を取得
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        BbsPtl010ParamModel paramMdl = new BbsPtl010ParamModel();
        paramMdl.setParam(form);
        BbsPtl010Biz biz = new BbsPtl010Biz();
        con.setAutoCommit(true);
        biz.setInitData(paramMdl, con, userMdl, forumAuth);
        paramMdl.setFormData(form);
        form.setBbsTopUrl(getPluginConfig(req).getPlugin(
                GSConstBulletin.PLUGIN_ID_BULLETIN).getTopMenuInfo().getUrl()
                + "?CMD=moveThreadList&bbs010forumSid=" + form.getBbsPtlBfiSid());
        con.setAutoCommit(false);
        return map.getInputForward();
    }

}

