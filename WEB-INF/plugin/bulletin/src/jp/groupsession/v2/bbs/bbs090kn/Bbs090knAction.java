package jp.groupsession.v2.bbs.bbs090kn;

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
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Biz;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 投稿登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs090knAction extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs090knAction.class);

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

        if (cmd.equals("fileDownload")) {
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
        Bbs090knForm bbsForm = (Bbs090knForm) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        con.setAutoCommit(true);
        try {
            //フォーラム編集権限チェック
            boolean forumAuthWrite = _checkForumAuth(map, req, con,
                    bbsForm.getBbs010forumSid(), GSConstBulletin.ACCESS_KBN_WRITE);
            if (!cmd.equals("fileDownload") && !forumAuthWrite) {
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

        if (cmd.equals("decision")) {
            //追加ボタンクリック
            forward = __doDecision(map, bbsForm, req, res, con);
        } else if (cmd.equals("backToInput")) {
            //戻るボタンクリック
            forward = map.findForward("backToInput");
        } else if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            forward = __doDownLoad(map, bbsForm, req, res, con);
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
            Bbs090knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {

        //投稿一覧からの遷移、かつ処理モード = 編集 または
        //投稿一覧からの遷移、かつ処理モード = 引用投稿 の場合
        //投稿情報を取得する
        int cmdMode = form.getBbs090cmdMode();
        if (cmdMode == GSConstBulletin.BBSCMDMODE_EDIT) {
            //投稿の存在チェックを行う
            BbsBiz bbsBiz = new BbsBiz(con);
            if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
                return __setAlreadyDelWrite(map, req, form, con);
            }
        }

        con.setAutoCommit(true);
        Bbs090knParamModel paramMdl = new Bbs090knParamModel();
        paramMdl.setParam(form);
        Bbs090knBiz biz = new Bbs090knBiz(con);
        biz.setInitData(paramMdl, getRequestModel(req), _getBulletinTempDir(req, form));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時の処理
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
    private ActionForward __doDecision(ActionMapping map,
            Bbs090knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {

        //投稿一覧からの遷移、かつ処理モード = 編集 または
        //投稿一覧からの遷移、かつ処理モード = 引用投稿 の場合
        //投稿情報を取得する
        int cmdMode = form.getBbs090cmdMode();
        if (cmdMode == GSConstBulletin.BBSCMDMODE_EDIT) {
            //投稿の存在チェックを行う
            BbsBiz bbsBiz = new BbsBiz(con);
            if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
                return __setAlreadyDelWrite(map, req, form, con);
            }
        }

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

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(
                con, getRequestModel(req),
                _getBulletinTempDir(req, form),
                false,
                userSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //フォーラムSID
        int bfiSid = 0;
        //スレッドSID
        int btiSid = 0;
        //グループSID
        int groupSid = 0;
        String tempDir = _getBulletinTempDir(req, form);
        RequestModel reqMdl = getRequestModel(req);
        Bbs090knParamModel paramMdl = new Bbs090knParamModel();
        paramMdl.setParam(form);
        Bbs090knBiz biz = new Bbs090knBiz(con);

        boolean commit = false;
        try {
            JDBCUtil.autoCommitOff(con);

            //登録処理
            MlCountMtController cntCon = getCountMtController(req);
            if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                    || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_INYOU
                    || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {

                biz.insertWriteData(paramMdl, cntCon, userSid, getAppRootPath(), tempDir);

                //フォーラムSID
                bfiSid = paramMdl.getBbs010forumSid();
                //スレッドSID
                btiSid = paramMdl.getThreadSid();
                //スレッド閲覧情報を更新する
                Bbs060Biz biz060 = new Bbs060Biz();
                biz060.updateView(con, btiSid, userSid, bfiSid);

                BbsBiz bbsBiz = new BbsBiz(con);
                //グループSIDを取得
                if (paramMdl.getBbs090contributor() > 0) {
                    groupSid = paramMdl.getBbs090contributor();
                    userSid = -1;
                } else {
                    groupSid = -1;
                }

                //集計用データを登録する
                bbsBiz.regBbsWriteLogCnt(
                        con, userSid, groupSid, bfiSid, btiSid, new UDate());

                if (paramMdl.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
                    // 草稿を削除
                    BulletinSoukouModel bbsSoukouMdl
                    = bbsBiz.getSoukouData(con, paramMdl.getSoukouSid(), userSid);
                    // 削除
                    bbsBiz.deleteSoukouBin(bbsSoukouMdl.getBskSid());
                    bbsBiz.deleteSoukouBodyBin(bbsSoukouMdl.getBskSid());
                    bbsBiz.deleteSoukou(bbsSoukouMdl.getBskSid());
                }

            } else if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
                //更新処理
                biz.updateWriteData(paramMdl, cntCon, userSid, getAppRootPath(), tempDir);

                //フォーラムSID
                bfiSid = paramMdl.getBbs010forumSid();
                //スレッドSID
                btiSid = paramMdl.getThreadSid();
                //スレッド閲覧情報を更新する
                Bbs060Biz biz060 = new Bbs060Biz();
                biz060.updateView(con, btiSid, userSid, bfiSid);

            }
            paramMdl.setFormData(form);

            commit = true;
        } catch (Exception e) {
            log__.error("投稿作成処理エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_INYOU
                || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            //ショートメールで通知
            BbsBiz bbsBiz = new BbsBiz();
            if (bbsBiz.canSendSmail(con, userSid)) {
                PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
                CommonBiz cmnBiz = new CommonBiz();
                MlCountMtController cntCon = getCountMtController(req);
                if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                    biz.sendSmail(paramMdl, cntCon, userSid, getAppRootPath(),
                            tempDir, getPluginConfig(req), reqMdl);
                }
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);

        //ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        String opCode = "";
        if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_INYOU
                || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            String textEntry = gsMsg.getMessage("cmn.entry");
            opCode = textEntry;
        } else if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            String textEdit = gsMsg.getMessage("cmn.change");
            opCode = textEdit;
        }

        BbsForInfModel mdl = new BbsForInfModel();
        BbsForInfDao dao = new BbsForInfDao(con);
        mdl.setBfiSid(form.getBbs010forumSid());
        BbsForInfModel gMdl = dao.select(mdl);
        String forumName = gMdl.getBfiName();

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.4"));
        sb.append("]");
        sb.append(forumName);
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.bbsMain.4"));
        sb.append("]");
        sb.append(form.getBbs090threTitle());
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("cmn.contributor"));
        sb.append("]");
        sb.append(form.getBbs090knviewContributor());
        sb.append("\n");

        bbsBiz.outPutLog(map, reqMdl, opCode,
                GSConstLog.LEVEL_TRACE, sb.toString());

        //テンポラリディレクトリ内のファイルを削除
        _deleteBulletinTempDir(req, form);

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
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
            Bbs090knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getBbs090knTmpFileId();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        String tempDir = _getBulletinTempDir(req, form);
        BbsBiz bbsBiz = new BbsBiz(con);
        String fileName = bbsBiz.downloadTempFile(req, res, tempDir, fileId);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDownload = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        bbsBiz.outPutLog(
                map, reqMdl, textDownload, GSConstLog.LEVEL_INFO, fileName,
                fileId, GSConstBulletin.BBS_LOG_FLG_DOWNLOAD);

        return null;
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
            Bbs090knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("moveThreadList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_INYOU
                || form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            msgState = "touroku.kanryo.object";
        } else if (form.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }

        GsMessage gsMsg = new GsMessage();
        String textWrite = gsMsg.getMessage(req, "bbs.16");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textWrite));

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
            Bbs090knForm form,
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
        String textDelWrite = gsMsg.getMessage(req, "bbs.16");
        String textDel = gsMsg.getMessage(req, "cmn.delete");

        //メッセージセット
        String msgState = "error.none.edit.data";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelWrite,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
