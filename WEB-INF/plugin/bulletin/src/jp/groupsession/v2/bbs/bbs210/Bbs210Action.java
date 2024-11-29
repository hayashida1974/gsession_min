package jp.groupsession.v2.bbs.bbs210;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 個人設定 初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs210Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs210Action.class);

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
        Bbs210Form bbsForm = (Bbs210Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("bbs210Decision")) {
            //設定ボタンクリック
            forward = __doOk(map, bbsForm, req, res, con);
        } else if (cmd.equals("backBBSList")) {
            //戻るボタンクリック
            forward = map.findForward("personal");
        } else if (cmd.equals("bbs210Entry")) {
            //登録処理
            forward = __doCommit(map, bbsForm, req, res, con);
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
    private ActionForward __doInit(
            ActionMapping map, Bbs210Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        Bbs210ParamModel paramMdl = new Bbs210ParamModel();
        paramMdl.setParam(form);
        Bbs210Biz biz = new Bbs210Biz(getRequestModel(req));
        biz.setInitData(paramMdl, con, getSessionUserSid(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doOk(
            ActionMapping map, Bbs210Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException {

        log__.debug("確認処理");
        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Bbs210Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=bbs210Entry");
        cmn999Form.setUrlCancel(map.findForward("mine").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        GsMessage gsMsg = new GsMessage();
        //スケジュール 初期値
        String textBbsInitVal = gsMsg.getMessage(req, "bbs.bbs200.1");
        String mkey1 = textBbsInitVal;
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("cmd", "bbs210Entry");
        cmn999Form.addHiddenParam("bbs210IniPostType", form.getBbs210IniPostType());
        cmn999Form.addHiddenParam("bbs210Init", form.getBbs210Init());

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        req.setAttribute("cmn999Form", cmn999Form);

        //         form.setMsgFormParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(
            ActionMapping map, Bbs210Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException {

        log__.debug("登録処理");

        RequestModel reqMdl = getRequestModel(req);

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        boolean commit = false;
        try {
            Bbs210Biz biz = new Bbs210Biz(getRequestModel(req));
            Bbs210ParamModel paramMdl = new Bbs210ParamModel();
            paramMdl.setParam(form);
            biz.entryKbn(con, paramMdl, getSessionUserSid(req));
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //メッセージ 削除
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");
        //ログ出力処理
        String value = null;
        BbsBiz bbsBiz = new BbsBiz(con);

        if (form.getBbs210IniPostType() == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            value = "テキスト形式";
        } else if (form.getBbs210IniPostType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
            value = "HTML形式";
        }

        bbsBiz.outPutLog(
                map, reqMdl,
                change, GSConstLog.LEVEL_INFO, value);

        //共通メッセージ画面(OK キャンセル)を表示
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
            Bbs210Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("personal");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        //掲示板初期値
        String textBbsInitVal = gsMsg.getMessage(req, "bbs.bbs200.2");
        cmn999Form.setMessage(msgRes.getMessage(msgState, textBbsInitVal));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());

        req.setAttribute("cmn999Form", cmn999Form);
    }
}