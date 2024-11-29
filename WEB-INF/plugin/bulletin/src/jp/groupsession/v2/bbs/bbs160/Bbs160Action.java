package jp.groupsession.v2.bbs.bbs160;

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
import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 管理者設定 ショートメール通知設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs160Action extends AbstractBulletinAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs160Action.class);

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
        Bbs160Form bbsForm = (Bbs160Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("bbs160Decision")) {
            //設定ボタンクリック
            forward = __doDecision(map, bbsForm, req, res, con);
        } else if (cmd.equals("backAdmMenu")) {
            //戻るボタンクリック
            forward = map.findForward("backToMenu");
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
        Bbs160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        if (!__canUseSmlPlugin(form, req, con)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        Bbs160ParamModel paramMdl = new Bbs160ParamModel();
        paramMdl.setParam(form);
        Bbs160Biz biz = new Bbs160Biz();
        biz.setInitData(paramMdl, con, getSessionUserSid(req));
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
        Bbs160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {


        if (!__canUseSmlPlugin(form, req, con)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        boolean commit = false;
        try {
            Bbs160ParamModel paramMdl = new Bbs160ParamModel();
            paramMdl.setParam(form);
            Bbs160Biz biz = new Bbs160Biz();
            biz.updateBbsSmailSetting(paramMdl, reqMdl.getSmodel(), con);
            paramMdl.setFormData(form);
            commit = true;
        } catch (Exception e) {
            log__.error("掲示板 管理者設定 ショートメール通知更新エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");

        //ログ出力処理
        String value = null;
        BbsBiz bbsBiz = new BbsBiz(con);

        if (form.getBbs160smlNtf() == GSConstBulletin.BAC_SML_NTF_USER) {
            value = gsMsg.getMessage("cmn.set.eachuser");
        } else if (form.getBbs160smlNtf() == GSConstBulletin.BAC_SML_NTF_ADMIN) {
            value = gsMsg.getMessage("cmn.set.the.admin");
            if (form.getBbs160smlNtfKbn() == GSConstBulletin.BAC_SML_NTF_KBN_YES) {
                value = value + " -> " + gsMsg.getMessage("cmn.notify");
            } else if (form.getBbs160smlNtfKbn() == GSConstBulletin.BAC_SML_NTF_KBN_NO) {
                value = value + " -> " + gsMsg.getMessage("cmn.dont.notify");
            }
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
        Bbs160Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backToMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textUserConf = gsMsg.getMessage(req, "cmn.sml.notification.setting");

        //メッセージセット
        String msgState = "hensyu.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, textUserConf));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>[機  能] ショートメールプラグインが利用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     *
     */
    private boolean __canUseSmlPlugin(Bbs160Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }

}

