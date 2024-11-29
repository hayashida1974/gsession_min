package jp.groupsession.v2.bbs.bbs090;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsSoukouModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 投稿登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs090Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs090Action.class);

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
        Bbs090Form bbsForm = (Bbs090Form) form;

        //プラグイン管理者フラグ
        con.setAutoCommit(true);
        try {
            //フォーラム編集権限チェック
            boolean forumAuthWrite = _checkForumAuth(map, req, con,
                    bbsForm.getBbs010forumSid(), GSConstBulletin.ACCESS_KBN_WRITE);
            if (!forumAuthWrite) {
                return map.findForward("gf_msg");
            }
            //アクセスチェック
            if (bbsForm.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
                BbsBiz bbsBiz = new BbsBiz();
                BaseUserModel buMdl = getSessionUserModel(req);
                boolean accessCheck =
                        bbsBiz.checkAccessAuth(
                                con,
                                buMdl,
                                bbsForm.getBbs010forumSid(),
                                bbsForm.getBbs060postSid());
                if (!accessCheck) {
                   return __accessCheckErrorMsgDsp(map, req);
                }
            }

            //投稿可能チェック
            if (!_checkReplyAuth(map, req, con, bbsForm.getBbs010forumSid())) {
                return map.findForward("gf_msg");
            }
        } finally {
            con.setAutoCommit(false);
        }

        //スレッドの存在チェックを行う
        //投稿の存在チェックを行う
        BbsBiz bbsBiz = new BbsBiz(con);
        if (!bbsBiz.isCheckExistThread(con, bbsForm.getThreadSid())) {
            return __setAlreadyDelThread(map, req, bbsForm, con);
        }

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);
        if (cmd.equals("moveWriteConfirm")) {
            //ＯＫボタンクリック
            forward = __doConfirm(map, bbsForm, req, res, con);
        } else if (cmd.equals("backWriteList")) {
            //戻るボタンクリック
            _deleteBulletinTempDir(req, bbsForm);
            if (bbsForm.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
                //草稿一覧に戻る
                forward = map.findForward("moveSoukouList");
            } else {
                forward = map.findForward("moveThreadList");
            }
        } else if (cmd.equals("draft")) {
            //草稿に保存
            forward = __doEntry(map, bbsForm, req, res, con, 1);
        }  else {
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
        Bbs090Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
                throws Exception {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //投稿一覧からの遷移、かつ処理モード = 編集 または
        //投稿一覧からの遷移、かつ処理モード = 引用投稿 の場合
        //投稿情報を取得する
        int cmdMode = form.getBbs090cmdMode();
        if (cmd.equals(String.valueOf(GSConstBulletin.BBS_PARENT_FLG_NO))
                && cmdMode == GSConstBulletin.BBSCMDMODE_EDIT
                || (cmd.equals("inyouWrite") && cmdMode == GSConstBulletin.BBSCMDMODE_INYOU)) {
            //投稿の存在チェックを行う
            BbsBiz bbsBiz = new BbsBiz(con);
            if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
                return __setAlreadyDelWrite(map, req, form, con, cmdMode);
            }
        }
        _initBulletinTempDir(req, form);
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.createTempDir(getRequestModel(req),
                GSConstBulletin.PLUGIN_ID_BULLETIN, form.getTempDirId(),
                GSConstCommon.EDITOR_BODY_FILE);

        con.setAutoCommit(true);
        Bbs090ParamModel paramMdl = new Bbs090ParamModel();
        paramMdl.setParam(form);
        Bbs090Biz biz = new Bbs090Biz();
        biz.setInitData(cmd, getRequestModel(req), paramMdl, con,
                getAppRootPath(), _getBulletinTempDir(req, form));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        saveToken(req);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] ＯＫボタンクリック時処理を行う
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
    private ActionForward __doConfirm(ActionMapping map,
        Bbs090Form form,
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


       // 草稿状態のスレッド情報を登録する場合
       if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
           BbsBiz biz = new BbsBiz(con);
           if (!biz.existSoukou(form.getSoukouSid(), userSid)) {
             return getSubmitErrorPage(map, req);
           }
       }

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(
                con,
                getRequestModel(req),
                _getBulletinTempDir(req, form),
                false,
                userSid);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("moveWriteConfirm");
    }

    /**
     * <br>[機  能] スレッド or 投稿削除時権限エラーメッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @param cmdMode コマンド
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setAlreadyDelWrite(
        ActionMapping map,
        HttpServletRequest req,
        Bbs090Form form,
        Connection con,
        int cmdMode) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textDelWrite = gsMsg.getMessage(req, "bbs.16");
        String textDel = null;
        if (cmdMode == GSConstBulletin.BBSCMDMODE_EDIT) {
            textDel = gsMsg.getMessage(req, "cmn.edit");
        } else {
            textDel = gsMsg.getMessage(req, "bbs.bbs080.5");
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
     * <br>[機  能] スレッド新規作成時エラーメッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setAlreadyDelThread(
        ActionMapping map,
        HttpServletRequest req,
        Bbs090Form form,
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
        String textDelWrite = gsMsg.getMessage("bbs.2");
        String textDel = gsMsg.getMessage("bbs.bbs090.1");

        //メッセージセット
        String msgState = "error.none.edit.data";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelWrite,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] 投稿草稿保存を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 登録モード 0:申請 1:草稿に保存
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doEntry(
        ActionMapping map,
        Bbs090Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int mode) throws Exception {

        //ログインユーザSIDを取得
       int userSid = 0;
       BaseUserModel buMdl = getSessionUserModel(req);
       if (buMdl != null) {
           userSid = buMdl.getUsrsid();
       }
       //プラグイン管理者
       CommonBiz cmnBiz = new CommonBiz();
       boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());
       //草稿保存権限チェックを行う
       BbsBiz bbsBiz = new BbsBiz(con);
       boolean canEdit = bbsBiz.canEditSoukou(
               form.getBbs010forumSid(),
               form.getThreadSid(),
               GSConstBulletin.SOUKOU_TYPE_TOUKOU,
               buMdl,
               adminFlg);

       // 草稿スレッド情報を再保存する場合
       boolean existSoukou = true;
       if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
           existSoukou = bbsBiz.existSoukou(form.getSoukouSid(), userSid);
       }

       // 2重投稿、草稿保存不可能、スレッド編集時の場合不正エラー
       if (!isTokenValid(req, true)
               || !existSoukou
               || !canEdit
               || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
           log__.info("草稿保存に失敗");
           return getSubmitErrorPage(map, req);
       }

       //入力チェックを行う
       String tempDir = _getBulletinTempDir(req, form);
       ActionErrors errors = new ActionErrors();
       errors = form.validateCheck(
               con, getRequestModel(req),
               _getBulletinTempDir(req, form),
               true,
               userSid);

       if (!errors.isEmpty()) {
           addErrors(req, errors);
           return __doInit(map, form, req, res, con);
       }

        Bbs090Biz biz = new Bbs090Biz();
        MlCountMtController cntCon = getCountMtController(req);

        boolean commit = false;
        try {
            JDBCUtil.autoCommitOff(con);

            Bbs090ParamModel paramMdl = new Bbs090ParamModel();
            paramMdl.setParam(form);
            BbsSoukouModel inMdl = null;
            if (paramMdl.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
                //添付ファイル情報の削除
                bbsBiz.deleteSoukouBin(paramMdl.getSoukouSid());
                bbsBiz.deleteSoukouBodyBin(paramMdl.getSoukouSid());
                //更新処理
                inMdl = biz.addEditSoukouData(
                        paramMdl,
                        cntCon,
                        con,
                        userSid,
                        getAppRootPath(),
                        tempDir,
                        getRequestModel(req),
                        GSConstBulletin.BBSCMDMODE_SOUKOU);
            } else {
                //登録処理
                inMdl = biz.addEditSoukouData(
                        paramMdl,
                        cntCon,
                        con,
                        userSid,
                        getAppRootPath(),
                        tempDir,
                        getRequestModel(req),
                        GSConstBulletin.BBSCMDMODE_ADD);
            }
            //ログ出力処理
            __doOutLog(map, inMdl, req, res, con, userSid);

            //テンポラリディレクトリ内のファイルを削除
            _deleteBulletinTempDir(req, form);

            paramMdl.setFormData(form);
            commit = true;
        } catch (Exception e) {
            log__.error("草稿保存に失敗しました", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Bbs090Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();

        //戻り先セット
          urlForward = map.findForward("moveThreadList");
        //メッセージセット
        String msgState = "";
        String msg = gsMsg.getMessage(req, "bbs.2");
        msgState = gsMsg.getMessage("cmn.save.draft2", new String[] {msg});
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgState);
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        cmn999Form.addHiddenParam("bbs010forumSid", form.getBbs010forumSid());
        cmn999Form.addHiddenParam("bbs060page1", form.getBbs060page1());
        cmn999Form.addHiddenParam("searchDspID", form.getSearchDspID());
        cmn999Form.addHiddenParam("bbs040forumSid", form.getBbs040forumSid());
        cmn999Form.addHiddenParam("bbs040keyKbn", form.getBbs040keyKbn());
        cmn999Form.addHiddenParam("bbs040taisyouThread", form.getBbs040taisyouThread());
        cmn999Form.addHiddenParam("bbs040taisyouNaiyou", form.getBbs040taisyouNaiyou());
        cmn999Form.addHiddenParam("bbs040userName", form.getBbs040userName());
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
        cmn999Form.addHiddenParam("bbs060postOrderKey", form.getBbs060postOrderKey());
        cmn999Form.addHiddenParam("threadSid", form.getThreadSid());
        cmn999Form.addHiddenParam("bbsmainFlg", form.getBbsmainFlg());
        req.setAttribute("cmn999Form", cmn999Form);
    }



    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param inMdl 草稿情報
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param userSid ユーザSID
     * @throws Exception 実行時例外
     */
    private void __doOutLog(
            ActionMapping map,
            BbsSoukouModel inMdl,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int userSid)
            throws Exception {
        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        BbsBiz bbsBiz = new BbsBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.save.draft3");
            // ユーザ情報
            CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usiMdl = usiDao.selectUserNameAndJtkbn(userSid);
            String name = NullDefault.getString(usiMdl.getUsiSei(), "")
                    + "　" + NullDefault.getString(usiMdl.getUsiMei(), "");

            StringBuilder sb = new StringBuilder();
            //スレッド作成
            sb.append(gsMsg.getMessage("bbs.bbs090.1") + ":");
            sb.append(System.getProperty("line.separator"));
            // 作成者
            sb.append("[" + gsMsg.getMessage("main.man470.6") + "] ");
            sb.append(name);
            sb.append(System.getProperty("line.separator"));

            // 投稿者
            sb.append("[" + gsMsg.getMessage("cmn.contributor") + "] ");
            int postKbn = inMdl.getBskAgid();
            String postName = null;
            if (postKbn > 0) {
                CmnGroupmDao grpDao = new CmnGroupmDao(con);
                CmnGroupmModel grpMdl = grpDao.selectGroup(postKbn);
                postName = grpMdl.getGrpName();
            } else {
                postName = name;
            }
            sb.append(postName);
            sb.append(System.getProperty("line.separator"));

            // フォーラム名
            sb.append("[" + gsMsg.getMessage("bbs.4") + "] ");

            BulletinDspModel foMdl = bbsBiz.getForumData(con, inMdl.getBfiSid());
            sb.append(foMdl.getBfiName());
            sb.append(System.getProperty("line.separator"));

            // スレッド名
            BulletinDspModel thMdl = bbsBiz.getThreadData(con, inMdl.getBtiSid());
            sb.append("[" + gsMsg.getMessage("bbs.bbsMain.4") + "] ");
            sb.append(thMdl.getBtiTitle());
            sb.append(System.getProperty("line.separator"));

            bbsBiz.outPutLog(
                    map,
                    reqMdl,
                    opCode,
                    GSConstLog.LEVEL_INFO,
                    sb.toString(),
                    null,
                    -1);
    }
    /**
     * <br>[機  能] アクセスチェック時のエラーメッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __accessCheckErrorMsgDsp(
        ActionMapping map,
        HttpServletRequest req) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("backBBSList");
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String errorMsg = msgRes.getMessage(
                "error.include.fail.data", gsMsg.getMessage("bbs.16"));
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(errorMsg);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
