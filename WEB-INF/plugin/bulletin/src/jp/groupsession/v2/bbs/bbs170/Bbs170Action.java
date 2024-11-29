package jp.groupsession.v2.bbs.bbs170;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Biz;
import jp.groupsession.v2.bbs.bbs070.Bbs070Form;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 掲示予定一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs170Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs170Action.class);

    /** 遷移先 フォーラム一覧画面 */
    private static final int DSP_MODE_FORUM__ = 0;
    /** 遷移先 掲示予約一覧画面 */
    private static final int DSP_MODE_RSVTHREAD__ = 1;

    /**
     * <br>[機  能] Connectionに設定する自動コミットモードを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return AutoCommit設定値
     */
    protected boolean _getAutoCommit() {
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
        Bbs170Form bbsForm = (Bbs170Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        //戻り先設定
        if (bbsForm.getBbs170backForumSid() == 0) {
            bbsForm.setBbs170backForumSid(bbsForm.getBbs010forumSid());
        }

        BbsBiz biz = new BbsBiz();
        int userSid = getRequestModel(req).getSmodel().getUsrsid();

        con.setAutoCommit(true);
        //フォーラム閲覧権限チェック
        boolean readAuth = biz.isForumViewAuth(con, bbsForm.getBbs010forumSid(), userSid);
        con.setAutoCommit(false);

        if (!cmd.equals("getImageFile")
                && !readAuth && bbsForm.getBbs010forumSid() != -1) {
            //指定フォーラムに編集権限がなく、全フォーラム表示でもない場合
            return __setInitDspErrMsgPage(map, req, bbsForm, con);
        }

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bbsForm.setBbs170page1(bbsForm.getBbs170page1() - 1);
            forward = __doInit(map, bbsForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            bbsForm.setBbs170page1(bbsForm.getBbs170page1() + 1);
            forward = __doInit(map, bbsForm, req, res, con);
        } else if (cmd.equals("moveThreadList")) {
            //戻るボタンクリック
            forward = map.findForward("moveThreadList");
        } else if (cmd.equals("delThread")) {
            //スレッド削除ボタンクリック
            forward = __doDeleteThread(map, bbsForm, req, res, con);
        } else if (cmd.equals("delThreadOk")) {
            //スレッド削除ボタンクリック
            forward = __doDeleteThreadOk(map, bbsForm, req, res, con);

        } else if (cmd.equals("editThreOrPost")) {
            //スレッドタイトルクリック
            __setBbs070Form(req, con, bbsForm, GSConstBulletin.BBSCMDMODE_EDIT);
            forward = map.findForward("moveThreadDtl");
        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード
            forward = __doGetImageFile(map, bbsForm, req, res, con);
        } else if (cmd.equals("changeForum")) {
            //フォーラム変更
            forward = __doChangeForum(map, bbsForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, bbsForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] フォーラム変更時の処理
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
    private ActionForward __doChangeForum(ActionMapping map,
            Bbs170Form form, HttpServletRequest req,
            HttpServletResponse res, Connection con)
                    throws Exception {
        int bfiSid = form.getBbs010forumSid();
        if (bfiSid != -1) {
            //全フォーラム表示以外への移動
            form.setBbs170allForumFlg(0);
        }
        return __doInit(map, form, req, res, con);
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
        Bbs170Form form,
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
        Bbs170ParamModel paramMdl = new Bbs170ParamModel();
        paramMdl.setParam(form);
        Bbs170Biz biz = new Bbs170Biz();
        CommonBiz cmnBiz = new CommonBiz();
        //プラグイン管理者
        boolean pluginAdmin = cmnBiz.isPluginAdmin(con, getSessionUserModel(req), getPluginId());

        biz.setInitData(getRequestModel(req), paramMdl, con, userSid, pluginAdmin, buMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] スレッド削除ボタンクリック時処理
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
    private ActionForward __doDeleteThread(ActionMapping map,
        Bbs170Form form,
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

        BbsBiz bbsBiz = new BbsBiz(con);

        //スレッドの存在チェックを行う
        if (!bbsBiz.isCheckExistThread(con, form.getThreadSid())) {
            return __setAlreadyDelWrite(map, req, form, con);
        }

        //ユーザが指定されたスレッドを削除可能か判定する
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, getSessionUserModel(req), getPluginId());
        Bbs060Biz biz = new Bbs060Biz();

        //削除予定のスレッドタイトルを取得する
        BbsThreInfDao dao = new BbsThreInfDao(con);
        BbsThreInfModel threMdl = dao.select(form.getThreadSid());
        String title = null;
        if (threMdl != null) {
            title = StringUtilHtml.transToHTmlPlusAmparsant(threMdl.getBtiTitle());
        }

        if (!adminUser) {
            con.setAutoCommit(true);
            boolean canDelThread = biz.canDeleteThread(con, form.getThreadSid(), userSid);
            con.setAutoCommit(false);
            if (!canDelThread) {
                return __setAuthErrMsgPage(map, req, form, con);
            }
        }

        return __setDeleteKnMsgPageParam(map, req, form, con, title);
    }

    /**
     * <br>[機  能] スレッド削除ボタンクリック時処理
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
    private ActionForward __doDeleteThreadOk(ActionMapping map,
        Bbs170Form form,
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

        BbsBiz bbsBiz = new BbsBiz(con);

        //スレッドの存在チェックを行う
        if (!bbsBiz.isCheckExistThread(con, form.getThreadSid())) {
            return __setAlreadyDelWrite(map, req, form, con);
        }

        //ユーザが指定されたスレッドを削除可能か判定する
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, getSessionUserModel(req), getPluginId());
        Bbs060Biz bbs060biz = new Bbs060Biz();

        if (!adminUser) {
            con.setAutoCommit(true);
            boolean canDelThread = bbs060biz.canDeleteThread(con, form.getThreadSid(), userSid);
            con.setAutoCommit(false);
            if (!canDelThread) {
                return __setAuthErrMsgPage(map, req, form, con);
            }
        }

        //削除予定のスレッドタイトルを取得する
        BbsThreInfDao dao = new BbsThreInfDao(con);
        BbsThreInfModel threMdl = dao.select(form.getThreadSid());
        String title = null;
        if (threMdl != null) {
            title = threMdl.getBtiTitle();
        }
        //投稿者を取得する
        String postUser = dao.getThreUser(form.getThreadSid());
        //フォーラム名を取得する
        BbsForInfModel mdl = new BbsForInfModel();
        BbsForInfDao fDao = new BbsForInfDao(con);
        mdl.setBfiSid(form.getBbs010forumSid());
        BbsForInfModel gMdl = fDao.select(mdl);
        String forumName = gMdl.getBfiName();


        Bbs170Biz biz = new Bbs170Biz();

        Bbs170ParamModel paramMdl = new Bbs170ParamModel();
        paramMdl.setParam(form);
        int cnt = biz.deleteThreadData(paramMdl, userSid, adminUser, con);
        paramMdl.setFormData(form);
        int mode = DSP_MODE_FORUM__;
        if (cnt > 0) {
            mode = DSP_MODE_RSVTHREAD__;
        }


        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.4"));
        sb.append("]");
        sb.append(forumName);
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.bbsMain.4"));
        sb.append("]");
        sb.append(title);
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("cmn.contributor"));
        sb.append("]");
        sb.append(postUser);
        sb.append("\n");

        //ログ出力処理
        bbsBiz.outPutLog(
                map, reqMdl, textDel, GSConstLog.LEVEL_TRACE,
                sb.toString());

        return __setDeleteCompleteMsgPageParam(map, req, form, con, mode);

    }

    /**
     * <br>[機  能] 掲示予定一覧遷移時エラーメッセージの設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setInitDspErrMsgPage(
        ActionMapping map,
        HttpServletRequest req,
        Bbs170Form form,
        Connection con)
                throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);

        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        String msgState = "error.access.double.submit";
        String msg = msgRes.getMessage(msgState);;

        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        cmn999Form.setMessage(msg);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] スレッド or 投稿削除時権限エラーメッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setAuthErrMsgPage(
        ActionMapping map,
        HttpServletRequest req,
        Bbs170Form form,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textDelThread = gsMsg.getMessage(req, "bbs.17");
        String textDel = gsMsg.getMessage(req, "cmn.delete");

        //メッセージセット
        String msgState = "error.edit.power.user";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelThread,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] スレッド削除確認の画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @param title スレッドタイトル
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setDeleteKnMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Bbs170Form form,
        Connection con,
        String title) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        String textWrite = title;
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("mine");

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=delThreadOk");
        cmn999Form.setUrlCancel(urlForward.getPath());

        //メッセージセット
        String msgState = "sakujo.thread.kakunin3";
        cmn999Form.setMessage(msgRes.getMessage(msgState, textWrite));
        cmn999Form.addHiddenParam("bbs010forumSid", form.getBbs010forumSid());
        cmn999Form.addHiddenParam("bbs170backForumSid", form.getBbs170backForumSid());
        cmn999Form.addHiddenParam("threadSid", form.getThreadSid());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除完了の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @param mode 0:フォーラム一覧、1:掲示予定一覧
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setDeleteCompleteMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Bbs170Form form,
        Connection con,
        int mode) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String textThread = gsMsg.getMessage(req, "bbs.2");

        String msgState = "sakujo.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, textThread));

        if (mode == DSP_MODE_RSVTHREAD__) {
            urlForward = map.findForward("mine");
            cmn999Form.addHiddenParam("bbs010forumSid", form.getBbs010forumSid());
            cmn999Form.addHiddenParam("bbs170backForumSid", form.getBbs170backForumSid());
            cmn999Form.addHiddenParam("threadSid", form.getThreadSid());
        } else {
            urlForward = map.findForward("backBBSList");
        }

        cmn999Form.setUrlOK(urlForward.getPath());
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] スレッド作成画面へのフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param form アクションフォーム
     * @param cmdMode 処理モード
     * @throws SQLException SQL実行時例外
     */
    private void __setBbs070Form(
            HttpServletRequest req, Connection con, Bbs170Form form, int cmdMode)
                    throws SQLException {

        Bbs070Form form070 = new Bbs070Form();
        form070.setS_key(form.getS_key());
        form070.setBbs010page1(form.getBbs010page1());
        form070.setBbs010forumSid(form.getBbs010forumSid());
        form070.setBbs060page1(form.getBbs060page1());
        form070.setBbs070cmdMode(cmdMode);

        //フォーラムSID,スレッドSIDより最初の投稿SIDを取得する
        Bbs170Biz biz = new Bbs170Biz();
        int bwiSid = biz.getFirstBwiSid(con, form.getBbs010forumSid(), form.getThreadSid());

        form070.setThreadSid(form.getThreadSid());
        form070.setBbs060postSid(bwiSid);
        form070.setBbs070BackID(GSConstBulletin.DSP_ID_BBS170);

        req.setAttribute("bbs070Form", form070);
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Bbs170Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        con.setAutoCommit(false);

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;
        //画像バイナリSIDとフォーラムSIDの照合チェック
        Bbs170Biz bbs060Biz = new Bbs170Biz();
        boolean icoHnt = bbs060Biz.cheIcoHnt(con,
                form.getBbs170backForumSid(), form.getBbs010BinSid());

        if (!icoHnt) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, form.getBbs010BinSid(),
                    GroupSession.getResourceManager().getDomain(req));
        }

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }

    /**
     * <br>[機  能] スレッド削除時権限エラーメッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setAlreadyDelWrite(
        ActionMapping map,
        HttpServletRequest req,
        Bbs170Form form,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textDelWrite = null;
        String textDel = gsMsg.getMessage("cmn.operations");

        textDelWrite = gsMsg.getMessage("bbs.2");

        //スレッド削除
        textDel = gsMsg.getMessage("bbs.bbs080.1");

        //メッセージセット
        String msgState = "error.none.edit.data";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelWrite,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
