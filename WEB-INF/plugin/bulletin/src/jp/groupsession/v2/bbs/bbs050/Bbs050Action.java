package jp.groupsession.v2.bbs.bbs050;

import java.sql.Connection;

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
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs050Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs050Action.class);

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
        Bbs050Form bbsForm = (Bbs050Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("bbsPsConf")) {
            //設定ボタンクリック
            forward = __doDecision(map, bbsForm, req, res, con);
        } else if (cmd.equals("backBBSList")) {
            //戻るボタンクリック
            forward = map.findForward("bbs130");
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
        Bbs050Form form,
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
        Bbs050ParamModel paramMdl = new Bbs050ParamModel();
        paramMdl.setParam(form);
        Bbs050Biz biz = new Bbs050Biz();
        biz.setInitData(paramMdl, con, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定ボタンクリック時の処理
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
        Bbs050Form form,
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
        //エラーチェック
        ActionErrors errors = form.validateInputCheck(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        boolean commit = false;
        try {
            Bbs050ParamModel paramMdl = new Bbs050ParamModel();
            paramMdl.setParam(form);
            Bbs050Biz biz = new Bbs050Biz();
            biz.updateBbsUserConf(paramMdl, con, userSid);
            paramMdl.setFormData(form);
            commit = true;
        } catch (Exception e) {
            log__.error("掲示板個人設定更新エラー", e);
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
        String textEdit = gsMsg.getMessage("cmn.change");

        //ログ出力処理
        String value = null;
        BbsBiz bbsBiz = new BbsBiz(con);
        //フォーラム表示件数
        value = "フォーラム表示件数：" + Integer.toString(form.getBbs050forumCnt()) + "\n";
        //スレッド表示件数
        value += "スレッド表示件数：" + Integer.toString(form.getBbs050threCnt()) + "\n";
        //投稿表示件数
        value += "投稿表示件数：" + Integer.toString(form.getBbs050writeCnt()) + "\n";
        //new表示件数
        value += "new表示件数：" + Integer.toString(form.getBbs050newCnt()) + "\n";
        //投稿一覧表示順
        if (form.getBbs050wrtOrder() == GSConstBulletin.BBS_WRTLIST_ORDER_ASC) {
            value += "投稿一覧表示順：昇順\n";
        } else if (form.getBbs050wrtOrder() == GSConstBulletin.BBS_WRTLIST_ORDER_DESC) {
            value += "投稿一覧表示順：降順\n";
        }
        //投稿者画像表示
        if (form.getBbs050threImage() == GSConstBulletin.BBS_IMAGE_DSP) {
            value += "投稿者画像表示：表示\n";
        } else if (form.getBbs050threImage() == GSConstBulletin.BBS_IMAGE_NOT_DSP) {
            value += "投稿者画像表示：非表示\n";
        }
        //添付画像表示
        if (form.getBbs050tempImage() == GSConstBulletin.BBS_IMAGE_TEMP_DSP) {
            value += "添付画像表示：表示\n";
        } else if (form.getBbs050tempImage() == GSConstBulletin.BBS_IMAGE_TEMP_NOT_DSP) {
            value += "添付画像表示：非表示\n";
        }
        //サブコンテンツ新着スレッド一覧
        if (form.getBbs050threadFlg() == GSConstBulletin.BBS_THRED_DSP) {
            value += "サブコンテンツ新着スレッド一覧：表示\n";
        } else if (form.getBbs050threadFlg() == GSConstBulletin.BBS_THRED_NOT_DSP) {
            value += "サブコンテンツ新着スレッド一覧：非表示\n";
        }
        //サブコンテンツフォーラム一覧
        if (form.getBbs050forumFlg() == GSConstBulletin.BBS_FORUM_DSP) {
            value += "サブコンテンツフォーラム一覧：表示\n";
        } else if (form.getBbs050forumFlg() == GSConstBulletin.BBS_FORUM_NOT_DSP) {
            value += "サブコンテンツフォーラム一覧：非表示\n";
        }
        //サブコンテンツ未読スレッド一覧
        if (form.getBbs050midokuTrdFlg() == GSConstBulletin.BBS_MIDOKU_TRD_DSP) {
            value += "サブコンテンツ未読スレッド一覧：表示\n";
        } else if (form.getBbs050midokuTrdFlg() == GSConstBulletin.BBS_MIDOKU_TRD_NOT_DSP) {
            value += "サブコンテンツ未読スレッド一覧：非表示\n";
        }
        //メイン画面スレッド表示件数
        value += "メイン画面スレッド表示件数：";
        value += Integer.toString(form.getBbs050threMainCnt()) + "\n";
        //メイン画面の既読投稿表示
        if (form.getBbs050mainChkedDsp() == GSConstBulletin.BBS_CHECKED_DSP) {
            value += "メイン画面の既読投稿表示：表示\n";
        } else if (form.getBbs050mainChkedDsp() == GSConstBulletin.BBS_CHECKED_NOT_DSP) {
            value += "メイン画面の既読投稿表示：非表示\n";
        }

        bbsBiz.outPutLog(map, reqMdl, textEdit, GSConstLog.LEVEL_INFO, value);

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
        Bbs050Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("bbs130");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textUserConf = gsMsg.getMessage(req, "cmn.display.settings");

        //メッセージセット
        String msgState = "hensyu.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, textUserConf));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        req.setAttribute("cmn999Form", cmn999Form);

    }
}

