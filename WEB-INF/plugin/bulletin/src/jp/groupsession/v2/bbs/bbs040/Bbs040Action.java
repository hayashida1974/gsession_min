package jp.groupsession.v2.bbs.bbs040;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs041.Bbs041Form;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 掲示板 詳細検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs040Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs040Action.class);

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
        Bbs040Form bbsForm = (Bbs040Form) form;


        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("dtlSearch")) {
            //検索ボタンクリック
            forward = __doSearch(map, bbsForm, req, res, con);
        } else if (cmd.equals("backToList")) {
            //戻るボタンクリック
            String searchDsp = NullDefault.getString(bbsForm.getSearchDspID(), "");
            if (searchDsp.startsWith("060")) {
                forward = map.findForward("moveThreadList");
            } else {
                forward = map.findForward("backBBSList");
            }
        } else {
            //初期表示
            forward = __doInit(map, bbsForm, req, res, con);
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
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Bbs040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        con.setAutoCommit(true);
        Bbs040ParamModel paramMdl = new Bbs040ParamModel();
        paramMdl.setParam(form);
        Bbs040Biz biz = new Bbs040Biz();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        biz.setInitData(paramMdl, con, userSid, adminUser, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doSearch(ActionMapping map,
        Bbs040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheckSearch(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        log__.debug("フォーラム = " + form.getBbs040forumSid());
        log__.debug("キーワード区分 = " + form.getBbs040keyKbn());
        log__.debug("検索対象(スレッドタイトル) = " + form.getBbs040taisyouThread());
        log__.debug("検索対象(内容) = " + form.getBbs040taisyouNaiyou());
        log__.debug("投稿者名 = " + form.getBbs040userName());
        log__.debug("未読・既読 = " + form.getBbs040readKbn());
        log__.debug("投稿日時指定無し = " + form.getBbs040dateNoKbn());
        log__.debug("投稿日時From年 = " + form.getBbs040fromYear());
        log__.debug("投稿日時From月 = " + form.getBbs040fromMonth());
        log__.debug("投稿日時From日 = " + form.getBbs040fromDay());
        log__.debug("投稿日時To年 = " + form.getBbs040toYear());
        log__.debug("投稿日時To月 = " + form.getBbs040toMonth());
        log__.debug("投稿日時To日 = " + form.getBbs040toDay());
        log__.debug("検索画面ID = " + form.getSearchDspID());

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Bbs040ParamModel paramMdl = new Bbs040ParamModel();
        paramMdl.setParam(form);
        Bbs040Biz biz = new Bbs040Biz();
        //検索件数チェック
        con.setAutoCommit(true);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        int searchCount = biz.getSearchCount(paramMdl, con, userSid, adminUser, new UDate());
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        if (searchCount <= 0) {

            GsMessage gsMsg = new GsMessage();
            String textThread = gsMsg.getMessage(req, "bbs.2");

            ActionMessage msg = new ActionMessage("search.data.notfound",
                                                   textThread);
            StrutsUtil.addMessage(errors, msg, "bbs040forumSid");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Bbs041Form form041 = new Bbs041Form();
        form041.copyFormParam(form);

        return map.findForward("moveSearchList");
    }

}
