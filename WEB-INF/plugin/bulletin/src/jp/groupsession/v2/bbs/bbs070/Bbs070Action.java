package jp.groupsession.v2.bbs.bbs070;

import java.io.File;
import java.io.PrintWriter;
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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsSoukouModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
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
 * <br>[機  能] 掲示板 スレッド登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs070Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs070Action.class);

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

        if (cmd.equals("getBodyFile")) {
            log__.debug("添付ファイルダウンロード");
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
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Bbs070Form bbsForm = (Bbs070Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        //フォーラム編集権限チェック
        if (!cmd.equals("getBodyFile")) {
            con.setAutoCommit(true);
            boolean forumAuthWrite = _checkForumAuth(map, req, con,
                    bbsForm.getBbs010forumSid(), GSConstBulletin.ACCESS_KBN_WRITE);
            if (!forumAuthWrite) {
                return map.findForward("gf_msg");
            }
        }
        //アクセスチェック
        if (bbsForm.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
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

        if (cmd.equals("moveThreadConfirm")) {
            //ＯＫボタンクリック
            forward = __doConfirm(map, bbsForm, req, res, con);
        } else if (cmd.equals("backList")) {
            //戻るボタンクリック
            _deleteBulletinTempDir(req, bbsForm);

            if (bbsForm.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_ADD) {
                forward = map.findForward("moveThreadList");

            } else if (bbsForm.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT
                    || bbsForm.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY) {
                String backID = NullDefault.getString(bbsForm.getBbs070BackID(), "");

                if (backID.equals(GSConstBulletin.DSP_ID_BBS170)) {
                    //掲示予定一覧に戻る
                    forward = map.findForward("rsvThreadList");

                } else if (backID.equals(GSConstBulletin.DSP_ID_BBS041)) {
                    //検索結果一覧に戻る
                    forward = map.findForward("moveSearchList");
                } else {
                    //スレッド一覧に戻る
                    forward = map.findForward("moveThreadList");
                }
            } else if (bbsForm.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
                //草稿一覧に戻る
                forward = map.findForward("moveSoukouList");
            }
        } else if (cmd.equals("getBodyFile")) {
            //添付画像ダウンロード
            forward = __doGetBodyFile(map, bbsForm, req, res, con);

        } else if (cmd.equals("draft")) {
            //草稿に保存
            forward = __doEntry(map, bbsForm, req, res, con, 1);
        } else if (cmd.equals("threadDateCheck")) {
            __editDialogDspCheck(map, bbsForm, req, res, con);
        }  else {
            //初期表示
            if (!cmd.equals("backToInput")) {
                _deleteBulletinTempDir(req, bbsForm);
            }
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
            Bbs070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //投稿一覧(掲示予定一覧)または検索結果一覧からの遷移、かつ処理モード = 編集または複写新規の場合はスレッド情報を取得する
        if (((cmd.equals("editThreOrPost")
                || cmd.equals("moveResult"))
                && (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT))
                ||  (cmd.equals("editThreOrPost")
                        && form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY)) {
            //投稿の存在チェックを行う
            BbsBiz bbsBiz = new BbsBiz();
            if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
                //フォーラム内スレッド件数取得
                return __setAlreadyDelWrite(map, req, form, con);
            }
        }

        _initBulletinTempDir(req, form);
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.createTempDir(getRequestModel(req),
                GSConstBulletin.PLUGIN_ID_BULLETIN, form.getTempDirId(),
                GSConstCommon.EDITOR_BODY_FILE);


        con.setAutoCommit(true);
        Bbs070ParamModel paramMdl = new Bbs070ParamModel();
        paramMdl.setParam(form);
        Bbs070Biz biz = new Bbs070Biz();
        biz.setInitData(cmd, getRequestModel(req), paramMdl, con,
                getAppRootPath(), _getBulletinTempDir(req, form));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        // トランザクショントークン設定
        saveToken(req);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] ダイアログの表示判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL例外発生
     * @throws Exception 実行例外
     */
    private void __editDialogDspCheck(ActionMapping map,
            Bbs070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String responseJson = null;

        Bbs070ParamModel paramMdl = new Bbs070ParamModel();
        Bbs070Biz biz = new Bbs070Biz();

        paramMdl.setParam(form);
        boolean result = biz.dialogDspCheck(paramMdl, con);
        paramMdl.setFormData(form);

        //渡す値のセット
        if (result) {
            responseJson = "{\"rtn\":\"true\"}";
        } else {
            responseJson = "{\"rtn\":\"false\"}";
        }
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(responseJson);
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
            Bbs070Form form,
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
        // 草稿状態のスレッド情報を登録する場合
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            BbsBiz biz = new BbsBiz(con);
            if (!biz.existSoukou(form.getSoukouSid(), userSid)) {
                return getSubmitErrorPage(map, req);
            }
        }
        ActionErrors errors = new ActionErrors();
        
        errors = form.validateCheckDate(con, getRequestModel(req));
        
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        
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
        return map.findForward("moveThreadConfirm");
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
    private ActionForward __setAlreadyDelWrite(
            ActionMapping map,
            HttpServletRequest req,
            Bbs070Form form,
            Connection con
            ) throws SQLException {

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
        String textDel = gsMsg.getMessage(req, "cmn.edit");

        //メッセージセット
        String msgState = "error.none.edit.data";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelWrite,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
    private ActionForward __doGetBodyFile(
            ActionMapping map,
            Bbs070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        String tempSaveId = form.getBbs070TempSaveId();
        //tempSaveIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(tempSaveId)) {
            return getSubmitErrorPage(map, req);
        }

        String tempDir = _getBulletinTempDir(req, form) + GSConstCommon.EDITOR_BODY_FILE
                + File.separator + tempSaveId;

        File bodyFileDir = new File(tempDir);
        File[] files = bodyFileDir.listFiles();
        if (files == null || files.length < 1) {
            return null;
        }

        String downFilePath = "";
        String downFileName = "";
        String tempFileName = "";
        for (File file : files) {
            tempFileName = file.getName();

            if (tempFileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                downFilePath = IOTools.replaceFileSep(tempDir + "/" + file.getName());

            } else if (tempFileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                ObjectFile objFile = new ObjectFile(tempDir, tempFileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                downFileName = fMdl.getFileName();
            }
        }
        TempFileUtil.downloadInline(req, res, downFilePath, downFileName, Encoding.UTF_8);

        return null;
    }


    /**
     * <br>[機  能] スレッド草稿保存を行う
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
            Bbs070Form form,
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
        BbsBiz bbsBiz = new BbsBiz(con);


        // 草稿スレッド情報を再保存する場合
        boolean existSoukou = true;
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            existSoukou = bbsBiz.existSoukou(form.getSoukouSid(), userSid);
        }

        //編集権限チェックを行う
        boolean canEdit = bbsBiz.canEditSoukou(
                form.getBbs010forumSid(),
                -1,
                GSConstBulletin.SOUKOU_TYPE_THREAD,
                buMdl,
                false);



        // 2重投稿、草稿保存不可能、スレッド編集時の場合不正エラー
        if (!isTokenValid(req, true)
                || !existSoukou
                || !canEdit
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            log__.info("草稿の保存に失敗");
            return getSubmitErrorPage(map, req);
        }

        //入力チェックを行う
        String tempDir = _getBulletinTempDir(req, form);
        ActionErrors errors = new ActionErrors();
        
        
        errors = form.validateCheckDate(con, getRequestModel(req));
        
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        
        errors = form.validateCheck(
                con, getRequestModel(req),
                _getBulletinTempDir(req, form),
                true,
                userSid);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        Bbs070Biz biz = new Bbs070Biz();
        MlCountMtController cntCon = getCountMtController(req);

        boolean commit = false;
        try {
            JDBCUtil.autoCommitOff(con);

            Bbs070ParamModel paramMdl = new Bbs070ParamModel();
            paramMdl.setParam(form);
            BbsSoukouModel inMdl = null;
            if (paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
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
            Bbs070Form form) {

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
        cmn999Form.addHiddenParam("bs060postPage1", form.getBbs060postPage1());
        cmn999Form.addHiddenParam("bbs060postOrderKey", form.getBbs060postOrderKey());
        cmn999Form.addHiddenParam("bbs170backForumSid", form.getBbs170backForumSid());
        cmn999Form.addHiddenParam("bbs170allForumFlg", form.getBbs170allForumFlg());
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
        //投稿作成
        sb.append(gsMsg.getMessage("bbs.bbs070.1") + ":");
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
        sb.append("[" + gsMsg.getMessage("bbs.bbsMain.4") + "] ");
        sb.append(inMdl.getBskTitle());
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
                "error.include.fail.data", gsMsg.getMessage("bbs.2"));
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(errorMsg);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
