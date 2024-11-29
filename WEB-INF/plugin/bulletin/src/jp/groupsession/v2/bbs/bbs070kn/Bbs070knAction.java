package jp.groupsession.v2.bbs.bbs070kn;

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
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Biz;
import jp.groupsession.v2.bbs.bbs220.Bbs220Form;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
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
 * <br>[機  能] 掲示板 スレッド登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs070knAction extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs070knAction.class);

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
        Bbs070knForm bbsForm = (Bbs070knForm) form;


        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        //フォーラム編集権限チェック
        if (!cmd.equals("fileDownload")) {
            con.setAutoCommit(true);
            boolean forumAuthWrite = _checkForumAuth(map, req, con,
                    bbsForm.getBbs010forumSid(), GSConstBulletin.ACCESS_KBN_WRITE);
            con.setAutoCommit(false);
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

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminFlg = cmnBiz.isPluginAdmin(con, getSessionUserModel(req), getPluginId());
            forward = __doDecision(map, bbsForm, req, res, con, adminFlg);
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
            Bbs070knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {
        //投稿一覧からの遷移、かつ処理モード = 編集の場合はスレッド情報を取得する
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            //投稿の存在チェックを行う
            BbsBiz bbsBiz = new BbsBiz();
            if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
                //フォーラム内スレッド件数取得
                return __setAlreadyDelWrite(map, req, form, con);
            }
        }

        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            Bbs220Form bbs220form = new Bbs220Form();
            int bbs220OrderKey = NullDefault.getInt(req.getParameter("bbs220OrderKey"),
                    GSConstBulletin.SOUKOU_SORT_KEY_ADATE);
            int bbs220SortKey = NullDefault.getInt(req.getParameter("bbs220SortKey"),
                    GSConstBulletin.ORDER_KEY_DESC);
            bbs220form.setBbs220SortKey(bbs220SortKey);
            bbs220form.setBbs220OrderKey(bbs220OrderKey);
            req.setAttribute("bbs220Form", bbs220form);
        }

        Bbs070knParamModel paramMdl = new Bbs070knParamModel();
        paramMdl.setParam(form);
        Bbs070knBiz biz = new Bbs070knBiz(con);
        biz.setInitData(paramMdl, getRequestModel(req), _getBulletinTempDir(req, form));
        paramMdl.setFormData(form);
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
     * @param adminFlg プラグイン管理者フラグ
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDecision(ActionMapping map,
            Bbs070knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            boolean adminFlg)
                    throws Exception {

        //投稿一覧からの遷移、かつ処理モード = 編集の場合はスレッド情報を取得する
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            //投稿の存在チェックを行う
            BbsBiz bbsBiz = new BbsBiz();
            if (!bbsBiz.isCheckExistWrite(con, form.getBbs060postSid())) {
                //フォーラム内スレッド件数取得
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
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            BbsBiz biz = new BbsBiz(con);
            if (!biz.existSoukou(form.getSoukouSid(), userSid)) {
                return getSubmitErrorPage(map, req);
            }
        }

        //入力チェック
        String tempDir = _getBulletinTempDir(req, form);
        ActionErrors errors = new ActionErrors();
        RequestModel reqMdl = getRequestModel(req);
        errors = form.validateCheck(con, reqMdl, tempDir, false, userSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        int backFlg = 0;

        Bbs070knBiz biz = new Bbs070knBiz(con);
        MlCountMtController cntCon = getCountMtController(req);
        BbsBiz bbsBiz = new BbsBiz(con);

        int btiSid = 0;
        boolean rsvThrFlg = false;
        boolean commit = false;
        UDate now = new UDate();
        String beforeName = "";
        try {
            JDBCUtil.autoCommitOff(con);

            Bbs070knParamModel paramMdl = new Bbs070knParamModel();
            paramMdl.setParam(form);

            if (paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                    || paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY
                    || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {

                //登録処理
                //スレッドSIDを取得
                btiSid = biz.insertThreadData(paramMdl, cntCon, userSid,
                        getAppRootPath(),
                        tempDir, now, reqMdl);

                //フォーラムSID
                int bfiSid = paramMdl.getBbs010forumSid();

                //スレッド閲覧情報を更新する
                Bbs060Biz biz060 = new Bbs060Biz();
                biz060.updateView(con, btiSid, userSid, bfiSid);

                if (paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
                    // 草稿を削除
                    BulletinSoukouModel bbsSoukouMdl
                    = bbsBiz.getSoukouData(con, paramMdl.getSoukouSid(), userSid);
                    // 削除
                    bbsBiz.deleteSoukouBin(bbsSoukouMdl.getBskSid());
                    bbsBiz.deleteSoukouBodyBin(bbsSoukouMdl.getBskSid());
                    bbsBiz.deleteSoukou(bbsSoukouMdl.getBskSid());
                }

                //グループSIDを取得
                int groupSid = 0;
                //グループSIDを取得
                if (paramMdl.getBbs070contributor() > 0) {
                    groupSid = paramMdl.getBbs070contributor();
                    userSid = -1;
                } else {
                    groupSid = -1;
                }

                //集計用データを登録する
                bbsBiz.regBbsWriteLogCnt(
                        con, userSid, groupSid, bfiSid, btiSid, new UDate());

                if (paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY) {
                    //複写新規完了後はスレッド一覧へ移動する
                    paramMdl.setThreadSid(0);
                }

            } else if (paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {

                //フォーラムSID
                int bfiSid = paramMdl.getBbs010forumSid();
                //スレッドSID
                btiSid = paramMdl.getThreadSid();

                //旧スレッド名取得
                BbsThreInfDao iDao = new BbsThreInfDao(con);
                BbsThreInfModel thMdl = iDao.select(form.getThreadSid());
                if (thMdl != null) {
                    beforeName = thMdl.getBtiTitle();
                }

                //更新処理
                backFlg = biz.updateThreadData(paramMdl, cntCon, userSid,
                        getAppRootPath(), tempDir, reqMdl, map.getPath());

                //スレッド閲覧情報を更新する
                Bbs060Biz biz060 = new Bbs060Biz();
                biz060.updateView(con, btiSid, userSid, bfiSid);

                //遷移元が掲示予定一覧の場合
                String backID = NullDefault.getString(form.getBbs070BackID(), "");
                if (backID.equals(GSConstBulletin.DSP_ID_BBS170)) {
                    //変更後、掲示予定スレッド件数を取得する
                    BulletinDao btDao = new BulletinDao(con);
                    boolean allFlg = false;
                    if (adminFlg) {
                        allFlg = true;
                    }

                    int rsvThrNum = btDao.countRsvThreData(
                            paramMdl.getBbs010forumSid(), userSid, allFlg, new UDate());

                    if (rsvThrNum > 0) {
                        rsvThrFlg = true;
                    }
                }
            }
            paramMdl.setFormData(form);
            commit = true;
        } catch (Exception e) {
            log__.error("スレッド作成処理エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        UDate limitFrDate = now.cloneUDate();
        limitFrDate.setDate(form.getBbs070limitFrYear(),
                form.getBbs070limitFrMonth(),
                form.getBbs070limitFrDay());
        limitFrDate.setHour(form.getBbs070limitFrHour());
        limitFrDate.setMinute(form.getBbs070limitFrMinute());

        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {

            //メール送信区分
            boolean mailSendKbn = true;
            if (form.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_YES
                    && bbsBiz.checkReserveDate(limitFrDate, now)) {
                mailSendKbn = false;
            }

            if (mailSendKbn) {
                Bbs070knParamModel paramMdl = new Bbs070knParamModel();
                paramMdl.setParam(form);
                //ショートメールで通知
                if (bbsBiz.canSendSmail(con, userSid)) {
                    PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
                    CommonBiz cmnBiz = new CommonBiz();
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                        biz.sendSmail(paramMdl, cntCon, btiSid, userSid,
                                getAppRootPath(), tempDir, getPluginConfig(req),
                                getRequestModel(req));
                    }
                }
                paramMdl.setFormData(form);
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);

        UDate limitToDate = now.cloneUDate();
        limitToDate.setDate(form.getBbs070limitYear(),
                form.getBbs070limitMonth(),
                form.getBbs070limitDay());
        limitToDate.setHour(form.getBbs070limitHour());
        limitToDate.setMinute(form.getBbs070limitMinute());

        //ログ出力処理
        String opCode = "";
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            opCode = gsMsg.getMessage("cmn.change");
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
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT
                && !beforeName.equals(form.getBbs070title())) {
            sb.append("[");
            sb.append(gsMsg.getMessage("bbs.70"));
            sb.append("]");
            sb.append(beforeName);
            sb.append("\n");
        }
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.bbsMain.4"));
        sb.append("]");
        sb.append(form.getBbs070title());
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("cmn.contributor"));
        sb.append("]");
        sb.append(form.getBbs070knviewContributor());
        sb.append("\n");
        if (form.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_YES
                && UDateUtil.diffMinute(limitFrDate, limitToDate) != 0
                && limitFrDate != null && limitToDate != null) {
            sb.append("[");
            sb.append(gsMsg.getMessage("bbs.bbs070.5"));
            sb.append("]");
            sb.append(form.getBbs070limitFrYear());
            sb.append(gsMsg.getMessage("cmn.year2"));
            sb.append(form.getBbs070limitFrMonth());
            sb.append(gsMsg.getMessage("cmn.month"));
            sb.append(form.getBbs070limitFrDay());
            sb.append(gsMsg.getMessage("cmn.day"));
            sb.append(" " + form.getBbs070limitFrHour());
            sb.append(gsMsg.getMessage("cmn.hour"));
            sb.append(form.getBbs070limitFrMinute());
            sb.append(gsMsg.getMessage("cmn.minute"));
            sb.append("\n");
            sb.append("[");
            sb.append(gsMsg.getMessage("bbs.bbs070.6"));
            sb.append("]");
            sb.append(form.getBbs070limitYear());
            sb.append(gsMsg.getMessage("cmn.year2"));
            sb.append(form.getBbs070limitMonth());
            sb.append(gsMsg.getMessage("cmn.month"));
            sb.append(form.getBbs070limitDay());
            sb.append(gsMsg.getMessage("cmn.day"));
            sb.append(" " + form.getBbs070limitHour());
            sb.append(gsMsg.getMessage("cmn.hour"));
            sb.append(form.getBbs070limitMinute());
            sb.append(gsMsg.getMessage("cmn.minute"));
            sb.append("\n");
        }

        bbsBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_TRACE, sb.toString());

        //テンポラリディレクトリ内のファイルを削除
        _deleteBulletinTempDir(req, form);

        __setCompPageParam(map, req, form, rsvThrFlg, backFlg);
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
            Bbs070knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getBbs070knTmpFileId();
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
                map, reqMdl, textDownload,
                GSConstLog.LEVEL_INFO, fileName, fileId, GSConstBulletin.BBS_LOG_FLG_DOWNLOAD);

        return null;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param rsvThrFlg 掲示予定一覧遷移フラグ（遷移元が掲示予定一覧の場合のみ使用）
     * @param backFlg 戻り画面フラグ 0:投稿一覧 1:スレッド一覧 2:フォーラム一覧
     */
    private void __setCompPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Bbs070knForm form,
            boolean rsvThrFlg,
            int backFlg) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String textthread = gsMsg.getMessage(req, "bbs.2");

        //戻り先セット
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            urlForward = map.findForward("moveThreadList");

        } else if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY) {
            //編集処理後の場合
            String backID = NullDefault.getString(form.getBbs070BackID(), "");

            if (backID.equals(GSConstBulletin.DSP_ID_BBS170)) {
                //戻り先が掲示予定一覧の場合
                if (rsvThrFlg) {
                    urlForward = map.findForward("rsvThreadList");
                } else {
                    urlForward = map.findForward("backBBSList");
                }

            } else if (backID.equals(GSConstBulletin.DSP_ID_BBS041)) {
                //戻り先が検索結果一覧の場合
                urlForward = map.findForward("moveSearchList");

            } else {
                if (backFlg == 2) {
                    urlForward = map.findForward("backBBSList");
                } else if (backFlg == 1) {
                    urlForward = map.findForward("moveThreadList");
                } else {
                    urlForward = map.findForward("moveThreadList");
                }
            }
        }

        //メッセージセット
        String msgState = "";
        if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_ADD
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY
                || form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            msgState = "touroku.kanryo.object";
        } else if (form.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textthread));

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
        //        cmn999Form.addHiddenParam("threadSid", form.getThreadSid());

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
            Bbs070knForm form,
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
