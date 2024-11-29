package jp.groupsession.v2.bbs.bbs060;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs040.Bbs040Biz;
import jp.groupsession.v2.bbs.bbs040.Bbs040Form;
import jp.groupsession.v2.bbs.bbs041.Bbs041Form;
import jp.groupsession.v2.bbs.bbs041.Bbs041ParamModel;
import jp.groupsession.v2.bbs.bbs070.Bbs070Form;
import jp.groupsession.v2.bbs.bbs090.Bbs090Form;
import jp.groupsession.v2.bbs.bbs220.Bbs220Form;
import jp.groupsession.v2.bbs.dao.BbsBodyBinDao;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.model.BbsBodyBinModel;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.bbs.model.BbsThreViewModel;
import jp.groupsession.v2.bbs.model.BbsWriteInfModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.pdf.BbsListPdfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 スレッド一覧・投稿一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs060Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs060Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "bbs060";

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
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fileDownload") || cmd.equals("pdf")) {
            return true;

        }
        return false;
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
    public ActionForward executeAction(
            ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Bbs060Form bbsForm = (Bbs060Form) form;
        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);
        //投稿SIDからフォーラムSIDとスレッドSIDを取得します。
        if (cmd.equals("getBodyFile")) {
            __setParamFromBwisid(bbsForm, con);
        }

        //フォーラムの存在チェックを行う
        BbsBiz bbsBiz = new BbsBiz(con);
        if (!cmd.equals("backThreadList")
                && !bbsBiz.isCheckExistForum(con, bbsForm.getBbs010forumSid())) {
            return __setAlreadyDelPost(map, req, bbsForm, con, GSConstBulletin.TYPE_FORUM, cmd);
        }

        //フォーラム閲覧権限チェック
        con.setAutoCommit(true);
        boolean forumAuth = _checkForumAuth(
                map, req, con, bbsForm.getBbs010forumSid(), GSConstBulletin.ACCESS_KBN_READ);
        con.setAutoCommit(false);
        if (!forumAuth) {
            return map.findForward("gf_msg");
        }

        con.setAutoCommit(true);
        //スレッドの存在チェックを行う
        if (bbsForm.getThreadSid() > 0
                && (!cmd.equals("fileDownload")
                        && !cmd.equals("tempview")
                        && !cmd.equals("backThreadList")
                        && !cmd.equals("moveRsvThreadList")
                        && !cmd.equals("delThreadComplete")
                        && !bbsBiz.isCheckExistThread(con, bbsForm.getThreadSid()))) {
            return __setAlreadyDelPost(map, req, bbsForm, con, GSConstBulletin.TYPE_THREAD, cmd);
        }
        con.setAutoCommit(false);

        //スレッドの掲示開始期間チェック
        if (bbsForm.getThreadSid() > 0
                && !(cmd.equals("fileDownload")
                        || cmd.equals("tempview")
                        || cmd.equals("backThreadList")
                        || cmd.equals("moveRsvThreadList")
                        || cmd.equals("backPage")
                        || cmd.equals("delThreadComplete")
                        || __checkLimitFromDate(map, req, con, bbsForm.getThreadSid()))) {
            return map.findForward("gf_msg");
        }

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bbsForm.setBbs060page1(bbsForm.getBbs060page1() - 1);
            forward = __doInit(cmd, map, bbsForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            bbsForm.setBbs060page1(bbsForm.getBbs060page1() + 1);
            forward = __doInit(cmd, map, bbsForm, req, res, con);
        } else if (cmd.equals("searchThre")) {
            //検索ボタンクリック
            forward = __doSearch(cmd, map, bbsForm, req, res, con);
        } else if (cmd.equals("searchThreDtl")) {
            //詳細検索ボタンクリック
            Bbs040Form form040 = new Bbs040Form();
            form040.setS_key(bbsForm.getS_key());
            form040.setBbs010forumSid(bbsForm.getBbs010forumSid());
            form040.setBbs010page1(bbsForm.getBbs010page1());
            form040.setBbs060page1(bbsForm.getBbs060page1());
            if (form040.getBbs040forumSid() <= 0) {
                form040.setBbs040dateNoKbn(1);
                form040.setBbs040keyKbn(GSConstBulletin.KEYWORDKBN_AND);
                form040.setBbs040readKbn(0);
                form040.setBbs040taisyouThread(1);
                form040.setBbs040taisyouNaiyou(1);
            }
            form040.setBbs040forumSid(bbsForm.getBbs010forumSid());
            form040.setSearchDspID(GSConstBulletin.SERCHDSPID_THREADDTL);
            req.setAttribute("bbs040Form", form040);
            forward = map.findForward("moveSearchDtl");
        } else if (cmd.equals("backBBSList")) {
            //戻るボタンクリック
            if (bbsForm.getBbsmainFlg() == 1) {
                forward = map.findForward("gf_main");
            } else {
                forward = map.findForward("backBBSList");
            }
        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード
            forward = __doGetImageFile(map, bbsForm, req, res, con);
        } else if (cmd.equals("bbs060allRead")) {
            //全て既読にするボタンクリック
            forward = __doAllRead(cmd, map, bbsForm, req, res, con);

        } else if (cmd.equalsIgnoreCase("getThreadData")) {
            //スレッド情報取得
            __doGetThreadData(map, bbsForm, req, res, con);
        } else if (cmd.equals("addThread")) {
            //スレッド新規作成ボタンクリック
            __setBbs070Form(req, bbsForm, GSConstBulletin.BBSCMDMODE_ADD);
            forward = map.findForward("addThread");
        } else if (cmd.equals("editThreOrPost")) {

            Bbs060Biz biz = new Bbs060Biz();
            int bwiSid = bbsForm.getBbs060postSid();

            if (biz.getDbTokoInf(con, bwiSid)) {
                //編集ボタン(スレッド)クリック(親)
                Bbs070Form form070 = new Bbs070Form();
                form070.copyFormParam(bbsForm);
                form070.setBbs060postPage1(bbsForm.getBbs060postPage1());
                form070.setBbs070cmdMode(GSConstBulletin.BBSCMDMODE_EDIT);
                req.setAttribute("bbs070Form", form070);
                forward = map.findForward("editThread");
            } else {
                //編集ボタン(投稿)クリック(子)
                Bbs090Form form090 = __createBbs090Form(bbsForm);
                form090.setBbs090cmdMode(GSConstBulletin.BBSCMDMODE_EDIT);
                req.setAttribute("bbs090Form", form090);
                forward = map.findForward("movePostDtl");
            }
        } else if (cmd.equals("copyThread")) {
            //複写して新規作成ボタン(スレッド)クリック
            Bbs070Form form070 = new Bbs070Form();
            form070.copyFormParam(bbsForm);
            form070.setBbs060postPage1(bbsForm.getBbs060postPage1());
            form070.setBbs070cmdMode(GSConstBulletin.BBSCMDMODE_COPY);
            req.setAttribute("bbs070Form", form070);
            forward = map.findForward("addThread");
        } else if (cmd.equalsIgnoreCase("getPostData")) {
            //投稿情報取得
            __doGetPostData(map, bbsForm, req, res, con);
        } else if (cmd.equals("addPost")) {
            //返信ボタンクリック
            forward = map.findForward("addPost");
        } else if (cmd.equals("inyouWrite")) {
            //引用投稿ボタンクリック
            Bbs090Form form090 = __createBbs090Form(bbsForm);
            form090.setBbs090cmdMode(GSConstBulletin.BBSCMDMODE_INYOU);
            req.setAttribute("bbs090Form", form090);
            forward = map.findForward("movePostDtl");
        } else if (cmd.equals("fileDownload")) {
            //添付ファイルリンククリッククリック
            forward = __doDownLoad(map, bbsForm, req, res, con);
        } else if (cmd.equals("pdf")) {
            //pdfダウンロード
            forward = __doDownLoadPdf(map, bbsForm, req, res, con);
        } else if (cmd.equals("tempview")) {
            //添付ファイル表示
            forward = __doTempView(map, bbsForm, req, res, con);
        } else if (cmd.equals("getBodyFile")) {
            //本文内添付ファイルダウンロード
            forward = __doGetBodyFile(map, bbsForm, req, res, con);
        } else if (cmd.equals("delThread")) {
            //スレッド削除ボタンクリック
            forward = __doDelThread(map, bbsForm, req, res, con);
        } else if (cmd.equals("delPost")) {
            //削除ボタン（投稿)クリック
            forward = __doDelPost(map, bbsForm, req, res, con, cmd);
        } else if (cmd.equals("delThreadDecision")) {
            //スレッド削除確認画面からの遷移
            forward = __doDelThreadComplete(map, bbsForm, req, res, con);
        } else if (cmd.equals("delPostDecision")) {
            //投稿削除確認画面からの遷移
            forward = __doDelPostComplete(map, bbsForm, req, res, con, cmd);
        } else if (cmd.equals("delThreadComplete")) {
            //スレッド削除完了後の画面遷移
            bbsForm.setThreadSid(GSConstBulletin.THREAD_SID_NONE);
            forward = __doInit(cmd, map, bbsForm, req, res, con);
        } else if (cmd.equals("moveRsvThreadList")) {
            //掲示予定リンククリック
            forward = map.findForward("rsvThreadList");
        } else if (cmd.equals("moveRsvThreadList")) {
            //掲示予定リンククリック
            forward = map.findForward("rsvThreadList");
        }  else if (cmd.equals("soukou")) {
            //草稿ボタンボタンクリック
            Bbs220Form form220 = new Bbs220Form();
            form220.copyFormParam(bbsForm);
            if (bbsForm.getThreadSid() > 0) {
                form220.setBbs220BackDsp(Bbs220Form.BACK_TOKO);
            } else {
                form220.setBbs220BackDsp(Bbs220Form.BACK_THREAD);
            }
            forward = map.findForward("moveSoukouList");
            req.setAttribute("bbs220Form", form220);
        } else if (cmd.equals("backThreadList")) {
            //戻るボタンクリック
            if (bbsForm.getBbsmainFlg() == 1) {
                forward = map.findForward("gf_main");
            } else if (!StringUtil.isNullZeroString(bbsForm.getSearchDspID())) {
                forward = map.findForward("moveSearchList");
            } else {
                forward = __doInit(cmd, map, bbsForm, req, res, con);
            }
        }  else if (cmd.equals("delMsg")) {
            //スレッド削除されていた時
            return __setAlreadyDelPost(map, req, bbsForm, con, GSConstBulletin.TYPE_THREAD, cmd);
        } else if (cmd.equals("unReadThread")) {
            //未読ボタンクリック
            forward = __doUnRead(cmd, map, bbsForm, req, res, con);
        } else if (cmd.equals("chkMovePopup")) {
            //移動ボタンクリック（入力チェック）
            return __doChkMove(cmd, map, bbsForm, req, res, con);

        } else if (cmd.equals("openMovePopup")) {
            // スレッド移動ボタンクリック
            forward = map.findForward("moveThreadMove");

        } else {
            //初期表示
            forward = __doInit(cmd, map, bbsForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(
            String cmd,
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
//      con.setAutoCommit(true);
        Bbs060ParamModel paramMdl = new Bbs060ParamModel();
        paramMdl.setParam(form);
        Bbs060Biz biz = new Bbs060Biz();
        CommonBiz cmnBiz = new CommonBiz();
        //プラグイン管理者
        boolean pluginAdmin = cmnBiz.isPluginAdmin(con, getSessionUserModel(req), getPluginId());

        biz.setInitData(cmd, paramMdl, con, userSid, pluginAdmin);
        paramMdl.setFormData(form);
//      con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doSearch(
            String cmd, ActionMapping map, Bbs060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(cmd, map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }


        //検索結果が存在するかを確認
        Bbs041Form form041 = new Bbs041Form();
        form041.setS_key(form.getS_key());
        form041.setBbs010forumSid(form.getBbs010forumSid());
        form041.setBbs010page1(form.getBbs010page1());
        form041.setBbs060page1(form.getBbs060page1());
        form041.setSearchDspID(GSConstBulletin.SERCHDSPID_THREADLIST);
        form041.setBbs040dateNoKbn(1);
        form041.setBbs040forumSid(form.getBbs010forumSid());
        form041.setBbs040keyKbn(GSConstBulletin.KEYWORDKBN_AND);
        form041.setBbs040readKbn(0);
        form041.setBbs040taisyouThread(1);
        form041.setBbs040taisyouNaiyou(1);

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        Bbs041ParamModel paramMdl = new Bbs041ParamModel();
        paramMdl.setParam(form041);
        Bbs040Biz biz = new Bbs040Biz();
        con.setAutoCommit(true);
        int count = biz.getSearchCount(paramMdl, con, userSid, adminUser, new UDate());
        paramMdl.setFormData(form041);
        con.setAutoCommit(false);
        if (count <= 0) {

            GsMessage gsMsg = new GsMessage();
            String textThread = gsMsg.getMessage(req, "bbs.2");

            ActionMessage msg = new ActionMessage("search.data.notfound", textThread);
            StrutsUtil.addMessage(errors, msg, "s_key");
            addErrors(req, errors);
            return __doInit(cmd, map, form, req, res, con);
        }

        req.setAttribute("bbs041Form", form041);
        return map.findForward("moveSearchList");
    }

    /**
     * <br>[機  能] スレッド作成画面へのフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @param cmdMode 処理モード
     */
    private void __setBbs070Form(HttpServletRequest req, Bbs060Form form, int cmdMode) {

        Bbs070Form form070 = new Bbs070Form();
        form070.setS_key(form.getS_key());
        form070.setBbs010page1(form.getBbs010page1());
        form070.setBbs010forumSid(form.getBbs010forumSid());
        form070.setBbs060page1(form.getBbs060page1());
        form070.setBbs070cmdMode(cmdMode);
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
    private ActionForward __doGetImageFile(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        con.setAutoCommit(false);

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;
        //画像バイナリSIDとフォーラムSIDの照合チェック
        Bbs060Biz bbs060Biz = new Bbs060Biz();
        boolean icoHnt = bbs060Biz.cheIcoHnt(con,
                form.getBbs010forumSid(), form.getBbs010BinSid());

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
     * <br>[機  能] 全て既読にするボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doAllRead(
            String cmd,
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //表示フォーラム内のスレッドを全て既読にする
        boolean commit = false;
        try {
            Bbs060ParamModel paramMdl = new Bbs060ParamModel();
            paramMdl.setParam(form);
            Bbs060Biz biz = new Bbs060Biz();
            biz.allReadThread(con, paramMdl, getSessionUserSid(req));
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        BbsBiz bbsBiz = new BbsBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.all.read");
        BulletinDspModel forumData = bbsBiz.getForumData(con, form.getBbs010forumSid());
        bbsBiz.outPutLog(map, reqMdl, opCode,
                GSConstLog.LEVEL_TRACE, "[title]" + forumData.getBfiName());

        return __doInit(cmd, map, form, req, res, con);
    }

    /**
     * <br>[機  能] 未読ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doUnRead(
            String cmd,
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        //プラグイン管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());
        //入力チェック
        ActionErrors errors = form.validateUnRead(req, con, adminFlg, userSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(cmd, map, form, req, res, con);
        }
        // ログ出力用 変更前の閲覧状態を取得する
        Map<Integer, BbsThreViewModel> logViewMdl = new HashMap<Integer, BbsThreViewModel>();
        BbsThreViewDao viDao = new BbsThreViewDao(con);
        for (String strSid: form.getBbs060ChkInfSid()) {
            int btiSid = NullDefault.getInt(strSid, -1);
            BbsThreViewModel viGetMdl = new BbsThreViewModel();
            viGetMdl.setBtiSid(btiSid);
            viGetMdl.setUsrSid(userSid);
            BbsThreViewModel mdl = viDao.select(viGetMdl);
            if (mdl != null) {
                logViewMdl.put(btiSid, mdl);
            }
        }
        //未読にする
        boolean commit = false;
        try {
            Bbs060ParamModel paramMdl = new Bbs060ParamModel();
            paramMdl.setParam(form);
            Bbs060Biz biz = new Bbs060Biz();
            biz.unReadThread(con, paramMdl, userSid);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        BbsBiz bbsBiz = new BbsBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("wml.js.10");
        StringBuilder sb = new StringBuilder();

        // フォーラム名
        BulletinDspModel forumData = bbsBiz.getForumData(con, form.getBbs010forumSid());
        sb.append("[" + gsMsg.getMessage("bbs.4") + "] ");
        sb.append(forumData.getBfiName());
        sb.append(System.getProperty("line.separator"));
        for (String strSid: form.getBbs060ChkInfSid()) {
            int btiSid = NullDefault.getInt(strSid, -1);
            if (btiSid == -1) {
                continue;
            }
            // スレッド名
            BulletinDspModel thMdl = bbsBiz.getThreadData(con, btiSid);
            if (thMdl == null) {
                continue;
            }
            sb.append("  [" + gsMsg.getMessage("bbs.bbsMain.4") + "] ");
            sb.append(thMdl.getBtiTitle());
            sb.append(System.getProperty("line.separator"));
            sb.append("   " + gsMsg.getMessage("reserve.rsv111kn.7") + "：");
            // スレッド区分
            BbsThreViewModel viMdl = logViewMdl.get(btiSid);
            if (viMdl == null) {
                sb.append(__getViewStateKbn(GSConstBulletin.BBS_THRE_VIEW_NO, gsMsg));
            } else {
                sb.append(__getViewStateKbn(viMdl.getBivViewKbn(), gsMsg));
            }
            sb.append(System.getProperty("line.separator"));
        }
        bbsBiz.outPutLog(
                map,
                reqMdl,
                opCode,
                GSConstLog.LEVEL_INFO,
                sb.toString(),
                null,
                -1);
        return __doInit(cmd, map, form, req, res, con);
    }
    /**
     * <br>[機  能] 移動ボタンクリック時処理
     * <br>[解  説] 入力チェック結果のメッセージ部HTMLを返却
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doChkMove(
            String cmd,
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        //ログインユーザSIDを取得
        BaseUserModel buMdl = getSessionUserModel(req);
        //プラグイン管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());
        //入力チェック
        ActionErrors errors = form.validateMove(getRequestModel(req), con, adminFlg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        }

        return map.findForward("errMsg");
    }

    /**
     * <br>[機  能] スレッドの情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 閲覧状態
     * @param gsMsg GsMessage
     * @return 閲覧状態
     */
    private String __getViewStateKbn(int kbn, GsMessage gsMsg) {
        String state = "";
        switch (kbn) {
        case GSConstBulletin.BBS_THRE_VIEW_YES:
            state =  gsMsg.getMessage("cht.cht010.04");
            break;
        case GSConstBulletin.BBS_THRE_VIEW_NO:
            state = gsMsg.getMessage("cmn.read.yet");
            break;
        case GSConstBulletin.BBS_THRE_VIEW_UNREAD:
            state = gsMsg.getMessage("bbs.bbs060.6");
            break;
        default:
            break;
        }
        return state;
    }


    /**
     * <br>[機  能] スレッドの情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @throws Exception 実行時例外
     */
    private void __doGetThreadData(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        JSONObject jsonData = new JSONObject();

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        con.setAutoCommit(true);

        try {
            CommonBiz cmnBiz = new CommonBiz();
            //プラグイン管理者
            boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());

            Bbs060ParamModel paramMdl = new Bbs060ParamModel();
            paramMdl.setParam(form);
            Bbs060Biz biz = new Bbs060Biz();
            jsonData = biz.getThreadData(con, paramMdl, userSid, adminFlg, buMdl);
            jsonData.element("success", true);
        } finally {
            con.setAutoCommit(false);
        }
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(初期データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * <br>[機  能] 投稿の情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @throws Exception 実行時例外
     */
    private void __doGetPostData(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        JSONObject jsonData = new JSONObject();

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Bbs060ParamModel paramMdl = new Bbs060ParamModel();
        paramMdl.setParam(form);

        con.setAutoCommit(true);
        try {
            CommonBiz cmnBiz = new CommonBiz();
            //プラグイン管理者
            boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());

            Bbs060Biz biz = new Bbs060Biz();
            jsonData = biz.getPostData(getRequestModel(req), con, paramMdl,
                    userSid, adminFlg, getAppRootPath(),
                    _getBulletinTempDir(req, TEMP_DIRECTORY_ID), buMdl);
            if (jsonData.size() > 0) {
                jsonData.element("success", true);
            }
        } finally {
            if (con.getAutoCommit()) {
                con.setAutoCommit(false);
            }
        }
        int bfiSid = paramMdl.getBbs010forumSid();
        int btiSid = paramMdl.getThreadSid();
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(初期データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

        BbsBiz bbsBiz = new BbsBiz(con);
        //集計用データ（閲覧）を登録する
        bbsBiz.regBbsViewLogCnt(con, getSessionUserSid(req), bfiSid, btiSid, new UDate());
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(
        ActionMapping map,
        Bbs060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
                throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        //添付ファイルバイナリSIDチェック
        Bbs060Biz bbs060Biz = new Bbs060Biz();
        boolean tmpHnt = bbs060Biz.cheTmpHnt(con,
                form.getBbs060postSid(), form.getBbs060postBinSid(),
                form.getBbs010forumSid(), form.getThreadSid());

        if (!tmpHnt) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, form.getBbs060postBinSid(),
                    GroupSession.getResourceManager().getDomain(req));
        }

        if (cbMdl != null) {

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textDownload = gsMsg.getMessage("cmn.download");

            //ログ出力処理
            BbsBiz bbsBiz = new BbsBiz(con);
            bbsBiz.outPutLog(
                    map, reqMdl, textDownload,
                    GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(),
                    String.valueOf(cbMdl.getBinSid()),
                    GSConstBulletin.BBS_LOG_FLG_DOWNLOAD);

            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);

        }

        return null;
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理を行います。
     * <br>[解  説]
     * <br>[備  考]
     *
     * -------テンポラリファイル名ルール-------
     * ○ダウンロード用PDFファイル一時保存ディレクトリ
     *    プラグインID/セッションID/bbspdf/pdfBbs_{フォーラムSID}_{スレッドSID}.pdf
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイルの書き出しに失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws TempFileException 添付ファイル情報の取得に失敗
     * @throws Exception 実行例外
     */
    private ActionForward __doDownLoadPdf(
            ActionMapping map, Bbs060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOException,
                    IOToolsException, TempFileException, Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        RequestModel reqMdl = getRequestModel(req);

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        CommonBiz cmnBiz = new CommonBiz();
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();

        String outTempDir = pathUtil.getTempPath(reqMdl,
                GSConstBulletin.PLUGIN_ID_BULLETIN,
                TEMP_DIRECTORY_ID
                );

        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        //PDF生成
        Bbs060Biz biz = new Bbs060Biz();
        Bbs060ParamModel paramMdl = new Bbs060ParamModel();
        paramMdl.setParam(form);
        BbsListPdfModel pdfMdl =
                biz.createBbsListPdf(con, reqMdl, paramMdl,
                        appRootPath, outTempDir, userSid, adminUser);
        paramMdl.setFormData(form);

        String downloadFileName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSaveFileName();

        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFileName;
        GsMessage gsMsg = new GsMessage(reqMdl);
        String pdfDownload = gsMsg.getMessage("cmn.pdf");

        //ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        bbsBiz.outPutLog(
                map, reqMdl, pdfDownload,
                GSConstLog.LEVEL_INFO, downloadFileName,
                form.getThreadSid(), null, GSConstBulletin.BBS_LOG_FLG_PDF);

        TempFileUtil.downloadAtachment(req, res, outFilePath, downloadFileName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        pathUtil.deleteTempPath(reqMdl,
                GSConstBulletin.PLUGIN_ID_BULLETIN, TEMP_DIRECTORY_ID);

        IOTools.deleteDir(IOTools.setEndPathChar(outTempDir));

        return null;
    }

    /**
     * <br>[機  能] 添付ファイルをブラウザ内に表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doTempView(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        //添付ファイルバイナリSIDチェック
        Bbs060Biz bbs060Biz = new Bbs060Biz();
        boolean tmpHnt = bbs060Biz.cheTmpHnt(con,
                form.getBbs060postSid(), form.getBbs060postBinSid(),
                form.getBbs010forumSid(), form.getThreadSid());

        if (!tmpHnt) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, form.getBbs060postBinSid(),
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
     * <br>[機  能] 本文内添付ファイルを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doGetBodyFile(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {




        Long binSid = null;


        if (form.isSoukouFlg()) {

        } else {
            //本文添付情報のバイナリSIDを取得する
            BbsBodyBinDao bbbDao = new BbsBodyBinDao(con);
            BbsBodyBinModel bbbModel = new BbsBodyBinModel();
            bbbModel.setBwiSid(form.getBbs060postSid());
            bbbModel.setBbbFileSid(form.getBodyFileId());
            bbbModel = bbbDao.select(bbbModel);
            binSid = bbbModel.getBinSid();
        }



        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        if (binSid == null || binSid < 0L) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, binSid,
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
    private ActionForward __doDelThread(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //ユーザが指定されたスレッドを削除可能か判定する
        Bbs060Biz biz = new Bbs060Biz();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        if (!adminUser) {
            con.setAutoCommit(true);
            boolean canDelThread = biz.canDeleteThread(con, form.getThreadSid(), userSid);
            con.setAutoCommit(false);
            if (!canDelThread) {
                return __setAuthErrMsgPage(map, req, form, con, GSConstBulletin.TYPE_THREAD);
            }
        }

        return __setDeleteConfirmMsgPageParam(map, req, form, con, GSConstBulletin.TYPE_THREAD);
    }

    /**
     * <br>[機  能] スレッド削除完了時処理
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
    private ActionForward __doDelThreadComplete(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //ユーザが指定されたスレッドを削除可能か判定する
        Bbs060Biz biz = new Bbs060Biz();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        if (!adminUser) {
            con.setAutoCommit(true);
            boolean canDelThread = biz.canDeleteThread(con, form.getThreadSid(), userSid);
            con.setAutoCommit(false);
            if (!canDelThread) {
                return __setAuthErrMsgPage(map, req, form, con, GSConstBulletin.TYPE_THREAD);
            }
        }

        //ログ情報の取得
        BbsForInfModel mdl = new BbsForInfModel();
        BbsForInfDao dao = new BbsForInfDao(con);
        mdl.setBfiSid(form.getBbs010forumSid());
        BbsForInfModel gMdl = dao.select(mdl);
        String forumName = gMdl.getBfiName();
        BbsThreInfDao iDao = new BbsThreInfDao(con);
        String postName = iDao.getThreUser(form.getThreadSid());

        //スレッド情報の削除
        boolean commit = false;
        try {
            biz.deleteThreadData(form.getBbs010forumSid(), form.getThreadSid(), con, userSid);
            commit = true;
        } catch (Exception e) {
            log__.error("スレッドの削除に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
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
        sb.append(forumName);
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.bbsMain.4"));
        sb.append("]");
        sb.append(form.getBbs060delTitle());
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("cmn.contributor"));
        sb.append("]");
        sb.append(postName);
        sb.append("\n");

        //ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        bbsBiz.outPutLog(
                map, reqMdl, textDel, GSConstLog.LEVEL_TRACE,
                sb.toString());

        return __setDeleteCompleteMsgPageParam(map, req, form, con, GSConstBulletin.TYPE_THREAD);
    }

    /**
     * <br>[機  能] 削除ボタン(投稿)クリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cmd コマンド
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDelPost(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            String cmd)
                    throws Exception {

        //投稿の存在チェックを行う
        BbsBiz bbsBiz = new BbsBiz(con);
        if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
            return __setAlreadyDelPost(map, req, form, con, GSConstBulletin.TYPE_POST, cmd);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //ユーザが指定された投稿を削除可能か判定する
        Bbs060Biz biz = new Bbs060Biz();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        if (!adminUser && !biz.canDeletePost(con, form.getBbs060postSid(), userSid)) {
            return __setAuthErrMsgPage(map, req, form, con, GSConstBulletin.TYPE_POST);
        }

        return __setDeleteConfirmMsgPageParam(map, req, form, con, GSConstBulletin.TYPE_POST);
    }

    /**
     * <br>[機  能] 投稿削除完了時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cmd コマンド
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDelPostComplete(
            ActionMapping map,
            Bbs060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            String cmd)
                    throws Exception {

        //投稿の存在チェックを行う
        BbsBiz bbsBiz = new BbsBiz(con);
        if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
            return __setAlreadyDelPost(map, req, form, con, GSConstBulletin.TYPE_POST, cmd);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //ユーザが指定されたスレッドを削除可能か判定する
        Bbs060ParamModel paramMdl = new Bbs060ParamModel();
        paramMdl.setParam(form);
        Bbs060Biz biz = new Bbs060Biz();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        if (!adminUser && !biz.canDeletePost(con, form.getBbs060postSid(), userSid)) {
            return __setAuthErrMsgPage(map, req, form, con, GSConstBulletin.TYPE_POST);
        }

        //ログ出力用データ取得
        BbsForInfModel mdl = new BbsForInfModel();
        BbsForInfDao dao = new BbsForInfDao(con);
        mdl.setBfiSid(form.getBbs010forumSid());
        BbsForInfModel gMdl = dao.select(mdl);
        String forumName = gMdl.getBfiName();
        BbsThreInfDao iDao = new BbsThreInfDao(con);
        BbsThreInfModel thMdl = iDao.select(form.getThreadSid());
        String thName = thMdl.getBtiTitle();
        BbsWriteInfDao wDao = new BbsWriteInfDao(con);
        String postName = wDao.getPostUser(form.getBbs060postSid());

        //投稿情報の削除
        boolean commit = false;
        try {
            biz.deletePostData(paramMdl, con, userSid);
            paramMdl.setFormData(form);
            commit = true;
        } catch (Exception e) {
            log__.error("投稿の削除に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
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
        sb.append(forumName);
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.bbsMain.4"));
        sb.append("]");
        sb.append(thName);
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("cmn.contributor"));
        sb.append("]");
        sb.append(postName);
        sb.append("\n");

        //ログ出力処理
        bbsBiz.outPutLog(
                map, reqMdl, textDel,
                GSConstLog.LEVEL_TRACE, sb.toString());

        return __setDeleteCompleteMsgPageParam(map, req, form, con, GSConstBulletin.TYPE_POST);
    }

    /**
     * <br>[機  能] スレッドを掲示していいかのチェックを行う
     * <br>[解  説]
     * <br>[備  考] 掲示開始日が未来の日付の場合は表示不可能
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @param btiSid スレッドSID
     * @return 権限の有無 true:掲示OK false:掲示NG
     * @throws Exception 実行例外
     */
    protected boolean __checkLimitFromDate(
            ActionMapping map,
            HttpServletRequest req,
            Connection con,
            int btiSid)
                    throws Exception {

        BbsThreInfDao dao = new BbsThreInfDao(con);
        BbsThreInfModel btiMdl = dao.select(btiSid);
        BbsBiz bbsBiz = new BbsBiz();

        //掲示期限 期限なしの場合
        if (btiMdl.getBtiLimit() == GSConstBulletin.THREAD_LIMIT_NO) {
            return true;
        }

        //掲示開始日 < 現在の日時の場合
        if (!bbsBiz.checkReserveDate(btiMdl.getBtiLimitFrDate(), new UDate())) {
            return true;
        }

        //権限なしの場合はメッセージページのパラメータを設定する
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.access.double.submit";
        cmn999Form.setMessage(msgRes.getMessage(msgState));

        req.setAttribute("cmn999Form", cmn999Form);

        return false;
    }

    /**
     * <br>[機  能] スレッド or 投稿削除時権限エラーメッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @param mode コマンドモード 0:フォーラム 1:スレッド 3:投稿
     * @param cmd コマンド
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setAlreadyDelPost(
            ActionMapping map,
            HttpServletRequest req,
            Bbs060Form form,
            Connection con,
            int mode,
            String cmd)
                    throws SQLException {

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

        if (mode == GSConstBulletin.TYPE_FORUM) {
            textDelWrite = gsMsg.getMessage("bbs.3");
        } else if (mode == GSConstBulletin.TYPE_THREAD) {
            textDelWrite = gsMsg.getMessage("bbs.2");
        } else {
            textDelWrite = gsMsg.getMessage("bbs.16");
        }

        //PDF出力
        if (cmd.equals("pdf")) {
            textDel = gsMsg.getMessage("cmn.pdf");
        } else if (cmd.equals("delThread")) {
            //スレッド削除
            textDel = gsMsg.getMessage("bbs.bbs080.1");
        } else if (cmd.equals("delPost")) {
            //投稿削除
            textDel = gsMsg.getMessage("cmn.delete");
        }

        //メッセージセット
        String msgState = "error.none.edit.data";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelWrite,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * 投稿作成画面へ渡すパラメータをフォームに設定
     * @param form フォーム
     * @return 投稿作成画面のフォーム
     */
    private Bbs090Form __createBbs090Form(Bbs060Form form) {
        Bbs090Form form090 = new Bbs090Form();
        form090.copyFormParam(form);
        form090.setBbs060postPage1(form.getBbs060postPage1());
        form090.setBbs060postSid(form090.getBbs060postSid());

        return form090;
    }

    /**
     * <br>[機  能] 削除確認の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @param type 0:スレッド、1:投稿
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setDeleteConfirmMsgPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Bbs060Form form,
            Connection con,
            int type)
                    throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        GsMessage gsMsg = new GsMessage();
        String textWrite = gsMsg.getMessage(req, "bbs.16");

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("mine");
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setUrlCancel(urlForward.getPath());

        if (type == GSConstBulletin.TYPE_THREAD) {
            cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=delThreadDecision");

            BbsBiz biz = new BbsBiz();
            BulletinDspModel btMdl = biz.getThreadData(con, form.getThreadSid());
            if (btMdl == null) {
                throw new SQLException("スレッド情報の取得に失敗");
            }

            //メッセージセット
            if (btMdl.getWriteCnt() > 1) {
                String msgState = "sakujo.thread.kakunin2";
                cmn999Form.setMessage(msgRes.getMessage(msgState,
                        StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBtiTitle()),
                        String.valueOf(btMdl.getWriteCnt())));
            } else {
                String msgState = "sakujo.thread.kakunin1";
                cmn999Form.setMessage(msgRes.getMessage(msgState,
                        StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBtiTitle())));
            }
            form.setBbs060delTitle(StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBtiTitle()));

        } else if (type == GSConstBulletin.TYPE_POST) {
            cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=delPostDecision");

            //メッセージセット
            String msgState = "sakujo.kakunin.once";
            cmn999Form.setMessage(msgRes.getMessage(msgState, textWrite));
        }


        cmn999Form = __setFormParam(cmn999Form, form);
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
     * @param type 0:スレッド、1:投稿
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setDeleteCompleteMsgPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Bbs060Form form,
            Connection con,
            int type)
                    throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();

        String msgState = "sakujo.kanryo.object";
        if (type == GSConstBulletin.TYPE_THREAD) {
            String textThread = gsMsg.getMessage(req, "bbs.2");
            urlForward = map.findForward("moveThreadList");
            cmn999Form.setMessage(msgRes.getMessage(msgState, textThread));
            cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=delThreadComplete");
        } else if (type == GSConstBulletin.TYPE_POST) {
            String textWrite = gsMsg.getMessage(req, "bbs.16");
            urlForward = map.findForward("mine");
            cmn999Form.setMessage(msgRes.getMessage(msgState, textWrite));
            cmn999Form.setUrlOK(urlForward.getPath());
        }

        cmn999Form = __setFormParam(cmn999Form, form);
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
     * @param type 0:スレッド、1:投稿
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setAuthErrMsgPage(
            ActionMapping map,
            HttpServletRequest req,
            Bbs060Form form,
            Connection con,
            int type)
                    throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("mine");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textDelThread = gsMsg.getMessage(req, "bbs.17");
        String textDelWrite = gsMsg.getMessage(req, "bbs.18");
        String textDel = gsMsg.getMessage(req, "cmn.delete");

        //メッセージセット
        String msgState = "error.edit.power.user";
        if (type == GSConstBulletin.TYPE_THREAD) {
            cmn999Form.setMessage(msgRes.getMessage(
                    msgState, textDelThread, textDel));
        } else if (type == GSConstBulletin.TYPE_POST) {
            cmn999Form.setMessage(msgRes.getMessage(
                    msgState, textDelWrite, textDel));
        }

        cmn999Form = __setFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 投稿SIDからフォーラムSIDとスレッドSIDを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setParamFromBwisid(
            Bbs060Form form, Connection con)
                    throws SQLException {
        BbsWriteInfDao bwiDao = new BbsWriteInfDao(con);
        BbsWriteInfModel bwiModel = new BbsWriteInfModel();

        bwiModel = bwiDao.select(form.getBbs060postSid());

        //フォーラムSIDをセットします
        form.setBbs010forumSid(bwiModel.getBfiSid());
        //スレッドSIDをセットします
        form.setThreadSid(bwiModel.getBtiSid());
    }

    /**
     * <br>[機  能] 共通メッセージフォームにフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     * @param form アクションフォーム
     * @return 共通メッセージフォーム
     */
    private Cmn999Form __setFormParam(Cmn999Form cmn999Form, Bbs060Form form) {

        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        cmn999Form.addHiddenParam("bbs010forumSid", form.getBbs010forumSid());
        cmn999Form.addHiddenParam("bbs060page1", form.getBbs060page1());
        cmn999Form.addHiddenParam("bbs040forumSid", form.getBbs040forumSid());
        cmn999Form.addHiddenParam("bbs040keyKbn", form.getBbs040keyKbn());
        cmn999Form.addHiddenParam("bbs040taisyouThread", form.getBbs040taisyouThread());
        cmn999Form.addHiddenParam("bbs040taisyouNaiyou", form.getBbs040taisyouNaiyou());
        cmn999Form.addHiddenParam("bbs040userSei", form.getBbs040userName());
        cmn999Form.addHiddenParam("bbs040readKbn", form.getBbs040readKbn());
        cmn999Form.addHiddenParam(
                "bbs040publicStatusOngoing", form.getBbs040publicStatusOngoing());
        cmn999Form.addHiddenParam(
                "bbs040publicStatusScheduled", form.getBbs040publicStatusScheduled());
        cmn999Form.addHiddenParam("bbs040publicStatusOver", form.getBbs040publicStatusOver());
        cmn999Form.addHiddenParam("bbs040dateNoKbn", form.getBbs040dateNoKbn());
        cmn999Form.addHiddenParam("bbs040fromYear", form.getBbs040fromYear());
        cmn999Form.addHiddenParam("bbs040fromMonth", form.getBbs040fromMonth());
        cmn999Form.addHiddenParam("bbs040fromDay", form.getBbs040fromDay());
        cmn999Form.addHiddenParam("bbs040toYear", form.getBbs040toYear());
        cmn999Form.addHiddenParam("bbs040toMonth", form.getBbs040toMonth());
        cmn999Form.addHiddenParam("bbs040toDay", form.getBbs040toDay());
        cmn999Form.addHiddenParam("bbs041page1", form.getBbs041page1());
        cmn999Form.addHiddenParam("bbs060postPage1", form.getBbs060postPage1());
        cmn999Form.addHiddenParam("bbs060postSid", form.getBbs060postSid());
        cmn999Form.addHiddenParam("bbs060postOrderKey", form.getBbs060postOrderKey());
        cmn999Form.addHiddenParam("threadSid", form.getThreadSid());
        cmn999Form.addHiddenParam("bbs060delTitle", form.getBbs060delTitle());
        cmn999Form.addHiddenParam("searchDspID", form.getSearchDspID());
        cmn999Form.addHiddenParam("bbsmainFlg", form.getBbsmainFlg());
        return cmn999Form;
    }
}
