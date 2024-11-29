package jp.groupsession.v2.bbs.bbs220;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs070.Bbs070Form;
import jp.groupsession.v2.bbs.bbs090.Bbs090Form;
import jp.groupsession.v2.bbs.dao.BbsSoukouBodyBinDao;
import jp.groupsession.v2.bbs.model.BbsSoukouBodyBinModel;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 草稿一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs220Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs220Action.class);

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
        Bbs220Form thisForm = (Bbs220Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("getSoukouInfo")) {
            // 内容アイコンクリック
            __doGetSoukouInfo(map, thisForm, req, res, con);
        } else if (cmd.equals("getBodyFile")) {
            //本文内添付ファイルダウンロード
            forward = __doGetBodyFile(map, thisForm, req, res, con);
        } else if (cmd.equals("fileDownload")) {
            //添付ファイルリンククリッククリック
            forward = __doDownLoad(map, thisForm, req, res, con);
        } else if (cmd.equals("soukouDelete")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteExe")) {
            //削除実行
            forward = __doDeleteExe(map, thisForm, req, res, con);
        } else if (cmd.equals("soukouEdit")) {
            //編集ボタンクリック
            forward = __doClickLink(map, thisForm, req, res, con);
        } else if (cmd.equals("backPage")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, con);
        } else if (cmd.equals("backList") || cmd.equals("backWriteList")) {
            // 新規スレッド作成画面、新規投稿画面から遷移
            String backDsp = NullDefault.getString(thisForm.getBbs220BackDsp(), "");
            if (backDsp.equals(Bbs220Form.BACK_TOKO)) {
                thisForm.setThreadSid(thisForm.getBbs220BackThreadSid());
            } else {
                thisForm.setThreadSid(-1);
            }
            thisForm.setBbs010forumSid(thisForm.getBbs220BackForumSid());
            forward = __doInit(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] ユーザSIDを取得しましす
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ユーザSID
     */
    private int __getUserSid(
            HttpServletRequest req) {
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        return userSid;
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
    private ActionForward __doInit(
            ActionMapping map,
            Bbs220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        con.setAutoCommit(true);
        Bbs220ParamModel paramMdl = new Bbs220ParamModel();
        paramMdl.setParam(form);
        //プラグイン管理者
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel buMdl = getSessionUserModel(req);
        boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());
        int userSid = __getUserSid(req);
        Bbs220Biz biz = new Bbs220Biz(con, getRequestModel(req));
        biz.setInitData(paramMdl, userSid, getSessionUserModel(req), adminFlg);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 草稿情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doGetSoukouInfo(
                    ActionMapping map,
                    Bbs220Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws Exception {
            JSONObject jsonData = new JSONObject();
            Bbs220ParamModel paramMdl = new Bbs220ParamModel();
            paramMdl.setParam(form);
            con.setAutoCommit(true);
            try {
                int userSid = __getUserSid(req);
                Bbs220Biz biz = new Bbs220Biz(con, getRequestModel(req));
                jsonData = biz.getSelectSoukouInfo(paramMdl, userSid);
                if (jsonData.size() > 0) {
                    jsonData.element("success", true);
                }
            } finally {
                if (con.getAutoCommit()) {
                    con.setAutoCommit(false);
                }
            }
            PrintWriter out = null;
            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData);
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗");
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }
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
            Bbs220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {

        //添付ファイルSIDチェック
        int userSid = __getUserSid(req);
        Bbs220Biz biz = new Bbs220Biz();
        boolean chk = biz.cheTmpBodyBin(
                con,
                form.getSoukouSid(),
                form.getBbs220BodyFileId(),
                userSid);
        if (!chk) {
            return null;
        }

        //本文添付情報のバイナリSIDを取得する
        BbsSoukouBodyBinDao bbbDao = new BbsSoukouBodyBinDao(con);
        BbsSoukouBodyBinModel bbbModel = new BbsSoukouBodyBinModel();
        bbbModel = bbbDao.select(
                form.getSoukouSid(),
                form.getBbs220BodyFileId());
        Long binSid = bbbModel.getBinSid();

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
            // ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                    Encoding.UTF_8);
        }
        return null;
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
        Bbs220Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
                throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        //添付ファイルバイナリSIDチェック
        int userSid = __getUserSid(req);
        Bbs220Biz biz = new Bbs220Biz();
        boolean chk = biz.cheTmpBin(con,
                form.getSoukouSid(), form.getBbs220SelectBinSid(),
                userSid);

        if (!chk) {
            return null;
        }

        cbMdl = cmnBiz.getBinInfo(con, form.getBbs220SelectBinSid(),
                    GroupSession.getResourceManager().getDomain(req));

        if (cbMdl == null) {
            return null;
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDownload = gsMsg.getMessage("cmn.download");

        // ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        bbsBiz.outPutLog(map, reqMdl, textDownload, GSConstLog.LEVEL_INFO,
                cbMdl.getBinFileName(), String.valueOf(cbMdl.getBinSid()),
                GSConstBulletin.BBS_LOG_FLG_DOWNLOAD);

        JDBCUtil.closeConnectionAndNull(con);

        // ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(),
                Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] 削除時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception エラー
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Bbs220Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        int userSid = __getUserSid(req);

        //入力チェック
        ActionErrors errors = form.validateDeleteBbs220(reqMdl, con, userSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //削除確認画面を表示
        return __setKakuninDsp(map,
                req,
                form,
                con,
                res);
    }


    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param con Connection
     * @param res HttpServletResponse
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __setKakuninDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Bbs220Form form,
                                          Connection con,
                                          HttpServletResponse res) throws Exception {

        //削除チェック
        GsMessage gsMsg = new GsMessage(req);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("init");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");
        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("init");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = "";
        msg = msgRes.getMessage("sakujo.bbs.soukou.kakunin",
                gsMsg.getMessage("cmn.draft"),
                form.getBbs220delInfSid().length);
        //画面パラメータをセット
        cmn999Form.setMessage(msg);
        form.setHiddenParamBbs220(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Bbs220Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        int userSid = __getUserSid(req);
        //入力チェック
        ActionErrors errors = form.validateDeleteBbs220(reqMdl, con, userSid);
        if (!errors.isEmpty()) {
            return getSubmitErrorPage(map, req);
        }
        ActionForward forward = null;
        boolean commit = false;
        try {
            Bbs220ParamModel paramMdl = new Bbs220ParamModel();
            paramMdl.setParam(form);
            Bbs220Biz biz = new Bbs220Biz(con, reqMdl);
            // 削除する草稿情報を取得
            List<BulletinSoukouModel> delSoukouInfo
                = biz.getDeleteSoukouInfo(paramMdl.getBbs220delInfSid(), userSid);
            // 削除
            biz.deleteSoukou(delSoukouInfo);
            paramMdl.setFormData(form);
            forward = __setCompDsp(map, req, form);
            //ログ出力処理
            __doOutLog(map, delSoukouInfo, req, res, con, userSid);
            commit = true;
        } catch (Exception e) {
            log__.error("草稿の削除に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Bbs220Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("init");
        cmn999Form.setUrlOK(forwardOk.getPath());
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.delete");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", msg));
        //画面パラメータをセット
        form.setHiddenParamBbs220(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param delSoukouInfo 削除草稿情報
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param userSid ユーザSID
     * @throws Exception 実行時例外
     */
    private void __doOutLog(ActionMapping map,
            List<BulletinSoukouModel> delSoukouInfo,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int userSid)
            throws Exception {
        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        BbsBiz bbsBiz = new BbsBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.delete");
        // ユーザ情報
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usiMdl = usiDao.selectUserNameAndJtkbn(userSid);
        String name = NullDefault.getString(usiMdl.getUsiSei(), "")
                + "　" + NullDefault.getString(usiMdl.getUsiMei(), "");
        for (BulletinSoukouModel delMdl: delSoukouInfo) {
            StringBuilder sb = new StringBuilder();
            boolean delFlg = false;
            // フォーラム
            if (delMdl.getForumName() == null) {
                sb.append(gsMsg.getMessage("bbs.bbs220.8"));
                delFlg = true;
            }

            // スレッド
            if (!delFlg
               && delMdl.getBskSoukouType() == GSConstBulletin.SOUKOU_TYPE_TOUKOU
               && delMdl.getThreadName() == null) {
                sb.append(gsMsg.getMessage("bbs.bbs220.9"));
                delFlg = true;
            }
            if (delFlg) {
                bbsBiz.outPutLog(
                        map,
                        reqMdl,
                        opCode,
                        GSConstLog.LEVEL_INFO,
                        sb.toString(),
                        null,
                        -1);
                continue;
            }
            // ユーザ名
            sb.append("[" + gsMsg.getMessage("bbs.bbs220.10") + "] ");
            sb.append(name);
            sb.append(System.getProperty("line.separator"));

            // フォーラム名
            sb.append("[" + gsMsg.getMessage("bbs.4") + "] ");
            sb.append(delMdl.getForumName());
            sb.append(System.getProperty("line.separator"));

            // スレッド名
            sb.append("[" + gsMsg.getMessage("bbs.bbsMain.4") + "] ");
            if (delMdl.getBskSoukouType() == GSConstBulletin.SOUKOU_TYPE_THREAD) {
                sb.append(delMdl.getBskTitle());
            } else {
                sb.append(delMdl.getThreadName());
            }
            sb.append(System.getProperty("line.separator"));

            // 種別
            sb.append("[" + gsMsg.getMessage("cmn.type") + "] ");
            if (delMdl.getBskSoukouType() == GSConstBulletin.SOUKOU_TYPE_THREAD) {
                sb.append(gsMsg.getMessage("bbs.2"));
            } else {
                sb.append(gsMsg.getMessage("bbs.16"));
            }
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
    }



    /**
     * <br>[機  能] 編集ボタンクリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Bbs220Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doClickLink(ActionMapping map,
                                Bbs220Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {
        con.setAutoCommit(true);
        Bbs220ParamModel paramMdl = new Bbs220ParamModel();
        RequestModel reqMdl = getRequestModel(req);
        paramMdl.setParam(form);
        int userSid = __getUserSid(req);
        ActionErrors errors = new ActionErrors();
        errors = form.validateEditBbs220(reqMdl, con, userSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        // アクセスチェック
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel buMdl = getSessionUserModel(req);
        boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());
        Bbs220Biz biz = new Bbs220Biz(con, getRequestModel(req));
        if (!biz.chkAccess(paramMdl, userSid, buMdl, adminFlg)) {
            return getSubmitErrorPage(map, req);
        }
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        if (form.getBbs220SelectType() == GSConstBulletin.SOUKOU_TYPE_THREAD) {
            Bbs070Form form070 = new Bbs070Form();
            form070.copyFormParam(form);
            form070.setBbs060postPage1(form.getBbs060postPage1());
            form070.setBbs070cmdMode(GSConstBulletin.BBSCMDMODE_SOUKOU);
            req.setAttribute("bbs070Form", form070);
            return map.findForward("moveAddSoukouThread");
        } else {
            Bbs090Form form090 = new Bbs090Form();
            form090.copyFormParam(form);
            form090.setBbs060postPage1(form.getBbs060postPage1());
            form090.setBbs060postSid(form090.getBbs060postSid());
            form090.setBbs090cmdMode(GSConstBulletin.BBSCMDMODE_SOUKOU);
            req.setAttribute("bbs090Form", form090);
            return map.findForward("moveAddSoukouToko");
        }
    }


    /**
     * <br>[機  能] 戻る処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(
            ActionMapping map,
            Bbs220Form form,
            HttpServletRequest req,
            Connection con) {
        ActionForward forward = null;
        String backDsp = NullDefault.getString(form.getBbs220BackDsp(), "");
        if (backDsp.equals(Bbs220Form.BACK_THREAD)) {
            //戻り先：スレッド一覧
            forward = map.findForward("backThreadList");
        } else if (backDsp.equals(Bbs220Form.BACK_TOKO)) {
            //戻り先：投稿一覧
            forward = map.findForward("backTokoList");
        } else {
            //戻り先：フォーラム一覧
            forward = map.findForward("backForumList");
        }
        return forward;
    }


}