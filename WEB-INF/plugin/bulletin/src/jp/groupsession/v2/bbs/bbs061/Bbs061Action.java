package jp.groupsession.v2.bbs.bbs061;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 スレッド一覧・投稿一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs061Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs061Action.class);

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
        Bbs061Form bbsForm = (Bbs061Form) form;
        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bbsForm.setBbs060page1(bbsForm.getBbs060page1() - 1);
            forward = __doInit(cmd, map, bbsForm, req, res, con);
        } else if (cmd.equals("moveForumComp")) {
            //確認画面からの遷移
            __doMoveComp(map, bbsForm, req, res, con);
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
            Bbs061Form form,
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

        Bbs061ParamModel paramMdl = new Bbs061ParamModel();
        paramMdl.setParam(form);
        Bbs061Biz biz = new Bbs061Biz();
        CommonBiz cmnBiz = new CommonBiz();
        //プラグイン管理者
        boolean pluginAdmin = cmnBiz.isPluginAdmin(con, getSessionUserModel(req), getPluginId());

        biz.setInitData(cmd, paramMdl, con, getRequestModel(req), userSid, pluginAdmin, buMdl);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 移動処理を行う(移動実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception エラー
     */
    private void __doMoveComp(
        ActionMapping map,
        Bbs061Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        BaseUserModel buMdl = getSessionUserModel(req);
        Bbs061Biz biz = new Bbs061Biz();

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(con, buMdl, getPluginId());
        if (form.getCheckForum() == 0) {
            GsMessage gsMsg = new GsMessage();
            String errorMsg = gsMsg.getMessage("cmn.select.plz");
            __doResponse(map, form, req, res, con, 1, errorMsg, false);
            return;
        }
        // 移動対象スレッド権限チェック
        ActionErrors errors = form.validateMove(getRequestModel(req), con, adminFlg);
        if (!errors.isEmpty()) {
            MessageResources msgRes = getResources(req);
            String msgState = "error.include.fail.data";
            GsMessage gsMsg = new GsMessage();
            String errorMsg = msgRes.getMessage(msgState, gsMsg.getMessage("bbs.2"));
            __doResponse(map, form, req, res, con, 1, errorMsg, true);
            return;
        }
        // 移動先フォーラム権限チェック
        errors = form.validateForum(req, con, buMdl, adminFlg);
        if (!errors.isEmpty()) {
            List<String> errMsgList = new ArrayList<String>();
            MessageResources msgRes = getResources(req);
            Iterator it = errors.get();
            while (it.hasNext()) {
                ActionMessage error = (ActionMessage) it.next();
                errMsgList.add(msgRes.getMessage(error.getKey(), error.getValues()));
            }
            String errorMsg = errMsgList.get(0);
            __doResponse(map, form, req, res, con, 1, errorMsg, true);
            return;
        }


        //DB更新
        boolean commit = false;
        Bbs061ParamModel paramMdl = new Bbs061ParamModel();
        paramMdl.setParam(form);
        try {
            //スレッド移動処理
            biz.moveThread(con, paramMdl, buMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //オペレーションログ
        RequestModel reqMdl = getRequestModel(req);
        BbsBiz bbsBiz = new BbsBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");
        String threadNameList = "";
        List<Integer> threadSidList = new ArrayList<Integer>();
        for (String chkSid: paramMdl.getBbs060ChkInfSid()) {
            threadSidList.add(Integer.parseInt(chkSid));
        }
        for (int threadSid : threadSidList) {
            BulletinDspModel mdl = bbsBiz.getThreadData(con, threadSid);
            if (threadNameList.length() > 0) {
                threadNameList += "\r\n";
            }
            threadNameList += mdl.getBtiTitle();
        }
        String msg = "[" + gsMsg.getMessage("bbs.bbs061.4") + "]"
                             + biz.getForumName(con, form.getCheckForum())
                             + "\r\n[" + gsMsg.getMessage("bbs.bbs061.5") + "]"
                             + biz.getForumName(con, form.getBbs010forumSid())
                             + "\r\n[" + gsMsg.getMessage("bbs.bbsMain.4") + "]"
                             + threadNameList;

        bbsBiz.outPutLog(
                map,
                reqMdl,
                opCode,
                GSConstLog.LEVEL_INFO,
                msg,
                null,
                -1);

        __doResponse(map, form, req, res, con, 0, "", true);
    }

    /**
     * <br>[機  能] レスポンス処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param msg メッセージ
     * @param jsonMode json処理モード
     * @param closeFlg 画面を閉じるフラグ
     * @throws Exception 実行時例外
     */
    private void __doResponse(ActionMapping map,
                                      Bbs061Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      int jsonMode,
                                      String msg,
                                      boolean closeFlg)
        throws Exception {
        JSONObject jsonData = new JSONObject();
        con.setAutoCommit(true);
        Bbs061ParamModel paramMdl = new Bbs061ParamModel();
        paramMdl.setParam(form);
        //トランザクショントークン設定
        this.saveToken(req);
        switch (jsonMode) {
        case 0:
            jsonData.element("success", true);
            break;
        case 1:
            jsonData.element("validate_error", true);
            jsonData.element("error_text", msg);
            jsonData.element("close_flg", closeFlg);
            break;
        default:
            break;
        }
        paramMdl.setFormData(form);
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
}
