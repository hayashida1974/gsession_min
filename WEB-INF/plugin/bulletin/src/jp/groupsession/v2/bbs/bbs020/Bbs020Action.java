package jp.groupsession.v2.bbs.bbs020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs030.Bbs030Form;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 フォーラム管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs020Action extends AbstractBulletinAdminAction {

    /** 共通メッセージ画面種別 削除確認画面 */
    private static final int MSGTYPE_KN__ = 1;
    /** 共通メッセージ画面種別 削除完了画面 */
    private static final int MSGTYPE_KR__ = 2;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs020Action.class);

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
        Bbs020Form bbsForm = (Bbs020Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bbsForm.setBbs020page1(bbsForm.getBbs020page1() - 1);
            forward = __doInit(map, bbsForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            bbsForm.setBbs020page1(bbsForm.getBbs020page1() + 1);
            forward = __doInit(map, bbsForm, req, res, con);
        } else if (cmd.equals("up")) {
            //表示順上へボタンクリック
            forward = __doUpdateSortUp(req, res, bbsForm, con, map);
        } else if (cmd.equals("down")) {
            //表示順下へボタンクリック
            forward = __doUpdateSortDown(req, res, bbsForm, con, map);
        } else if (cmd.equals("addForum")) {
            //追加ボタンクリック
            __setBbs030Form(req, bbsForm, GSConstBulletin.BBSCMDMODE_ADD);
            forward = map.findForward("addForum");
        } else if (cmd.equals("confMenu")) {
            //戻るボタンクリック
            forward = map.findForward("confMenu");
        } else if (cmd.equals("editForum")) {
            //編集ボタンクリック
            __setBbs030Form(req, bbsForm, GSConstBulletin.BBSCMDMODE_EDIT);
            forward = map.findForward("editForum");
        } else if (cmd.equals("delForum")) {
            //削除ボタンクリック
            forward = __doDelete(map, bbsForm, req, res, con);
        } else if (cmd.equals("delForumDecision")) {
            //削除ボタンクリック
            forward = __doDeleteDecision(map, bbsForm, req, res, con);
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
        Bbs020Form form,
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
        Bbs020ParamModel paramMdl = new Bbs020ParamModel();
        paramMdl.setParam(form);
        Bbs020Biz biz = new Bbs020Biz();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        biz.setInitData(paramMdl, con, userSid, adminUser);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理
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
    private ActionForward __doDelete(
            ActionMapping map,
            Bbs020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        __setMsgPageParam(map, req, form, con, MSGTYPE_KN__);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理実施
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
    private ActionForward __doDeleteDecision(ActionMapping map,
        Bbs020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        boolean commit = false;
        String parentForum = "";
        try {
            Bbs020ParamModel paramMdl = new Bbs020ParamModel();
            paramMdl.setParam(form);
            BbsBiz bBiz = new BbsBiz(con);
            parentForum = bBiz.getParentForumName(paramMdl.getBbs020forumSid());
            Bbs020Biz biz = new Bbs020Biz();
            biz.deleteForumData(paramMdl, con);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("フォーラムの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.4"));
        sb.append("]");
        sb.append(form.getBbs020delForumName());
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.40"));
        sb.append("]");
        sb.append(parentForum);
        //ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        bbsBiz.outPutLog(map, reqMdl, textDel,
                GSConstLog.LEVEL_INFO, sb.toString());

        __setMsgPageParam(map, req, form, con, MSGTYPE_KR__);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @param msgType メッセージ画面の種別 0:削除確認画面、1:削除完了画面
     * @throws SQLException SQL例外発生
     */
    private void __setMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Bbs020Form form,
        Connection con,
        int msgType) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("mine");
        if (msgType == MSGTYPE_KN__) {
            cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
            cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=delForumDecision");
            cmn999Form.setUrlCancel(urlForward.getPath());

            con.setAutoCommit(true);
            BbsBiz biz = new BbsBiz();
            BulletinDspModel btMdl = biz.getForumData(con, form.getBbs020forumSid());
            con.setAutoCommit(false);

            if (btMdl == null) {
                throw new SQLException("フォーラム情報の取得に失敗");
            }

            form.setBbs020delForumName(StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBfiName()));

            //メッセージセット
            if (btMdl.getWriteCnt() > 0) {
                String msgState = "sakujo.forum.kakunin2";
                cmn999Form.setMessage(msgRes.getMessage(msgState,
                        StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBfiName()),
                        String.valueOf(btMdl.getWriteCnt())));
            } else {
                String msgState = "sakujo.forum.kakunin1";
                cmn999Form.setMessage(msgRes.getMessage(msgState,
                        StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBfiName())));
            }

        } else if (msgType == MSGTYPE_KR__) {
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setUrlOK(urlForward.getPath());

            GsMessage gsMsg = new GsMessage();
            String textForum = gsMsg.getMessage(req, "bbs.3");

            //メッセージセット
            String msgState = "sakujo.kanryo.object";
            cmn999Form.setMessage(msgRes.getMessage(msgState, textForum));
        }

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        cmn999Form.addHiddenParam("bbs020page1", form.getBbs020page1());
        cmn999Form.addHiddenParam("bbs020indexRadio", form.getBbs020indexRadio());
        cmn999Form.addHiddenParam("bbs020forumSid", form.getBbs020forumSid());
        cmn999Form.addHiddenParam("bbs020delForumName", form.getBbs020delForumName());
        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>[機  能] フォーラム登録画面へのフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @param cmdMode 処理モード
     */
    private void __setBbs030Form(HttpServletRequest req, Bbs020Form form, int cmdMode) {

        Bbs030Form form030 = new Bbs030Form();
        form030.setS_key(form.getS_key());
        form030.setBbs010page1(form.getBbs010page1());
        form030.setBbs020page1(form.getBbs020page1());
        form030.setBbs020forumSid(form.getBbs020forumSid());
        form030.setBbs030cmdMode(cmdMode);

        req.setAttribute("bbs030Form", form030);
    }

    /**
     * <br>[機  能] 上へボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @param map マッピング
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doUpdateSortUp(
            HttpServletRequest req, HttpServletResponse res, Bbs020Form form,
            Connection con, ActionMapping map)
                    throws Exception {

        return __doUpdateSort(req, res, form, con, map, GSConstBulletin.FORUM_SORT_UP);
    }

    /**
     * <br>[機  能] 下へボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @param map マッピング
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doUpdateSortDown(
            HttpServletRequest req, HttpServletResponse res, Bbs020Form form,
            Connection con, ActionMapping map)
                    throws Exception {

        return __doUpdateSort(req, res, form, con, map, GSConstBulletin.FORUM_SORT_DOWN);
    }

    /**
     * <br>[機  能] 表示順変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @param map マッピング
     * @param mode モード
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doUpdateSort(
            HttpServletRequest req, HttpServletResponse res, Bbs020Form form,
            Connection con, ActionMapping map, String mode)
                    throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
        
        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            //ログインユーザSIDを取得
            int userSid = 0;
            BaseUserModel buMdl = getSessionUserModel(req);
            if (buMdl != null) {
                userSid = buMdl.getUsrsid();
            }
            Bbs020Biz biz = new Bbs020Biz();
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl,
                    GSConstBulletin.PLUGIN_ID_BULLETIN);

            Bbs020ParamModel paramMdl = new Bbs020ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(paramMdl, con, mode, userSid, adminUser);
            paramMdl.setFormData(form);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");
        String textForumSort = gsMsg.getMessage("bbs.25");

        //ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        bbsBiz.outPutLog(map, reqMdl,
                textEdit, GSConstLog.LEVEL_INFO, textForumSort);
        
        return __doInit(map, form, req, res, con);
    }
    
}

