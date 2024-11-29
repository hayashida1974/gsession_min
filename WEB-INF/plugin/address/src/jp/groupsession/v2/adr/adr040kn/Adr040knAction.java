package jp.groupsession.v2.adr.adr040kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.AbstractAddressAdminAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 管理者設定 権限設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Adr040knAction extends AbstractAddressAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr040knAction.class);

    /**
    * <br>[機  能] アクションを実行する
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @return ActionForward
    * @throws Exception 実行時例外
    */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Adr040knForm rsvform = (Adr040knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("adr040knkakutei")) {
            log__.debug("確定ボタン押下");
            forward = __doKakutei(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("adr040knback")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("adr040");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Adr040knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        ActionForward forward = null;

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Adr040knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map, Adr040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;

        //セッションユーザSIDを取得する。
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        //DB更新
        Adr040knBiz biz = new Adr040knBiz(getRequestModel(req));

        Adr040knParamModel paramMdl = new Adr040knParamModel();
        paramMdl.setParam(form);
        AdrAconfModel adrAconfMdl = biz.setAdrAconf(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        forward = __setCompPageParam(map, req, form);

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        String opCode = "";
        GsMessage gsMsg = new GsMessage();
        if (adrAconfMdl == null) {
            opCode = gsMsg.getMessage(req, "cmn.entry");
        } else {
            opCode = gsMsg.getMessage(req, "cmn.change");
        }

        String msg = __getLogMessage(form, req);

        adrBiz.outPutLog(
                map, req, res, opCode, GSConstLog.LEVEL_INFO, msg);

        return forward;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Adr040knForm form) {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("adr030");
        cmn999Form.setUrlOK(urlForward.getPath());

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                gsMsg.getMessage(req, "cmn.setting.permissions")));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private String __getLogMessage(
        Adr040knForm form,
        HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //役職編集権限
        msg += "[" + gsMsg.getMessage("address.85") + "]";
        if (form.getAdr040Pow5() == GSConstAddress.ADR_POSITION_EDIT_ADMIN) {
            msg += gsMsg.getMessage("cmn.only.admin.editable");
        } else if (form.getAdr040Pow5() == GSConstAddress.ADR_POSITION_EDIT) {
            msg += gsMsg.getMessage("address.87");
        }
        //業種編集権限
        msg += "\r\n[" + gsMsg.getMessage("address.88") + "]";
        if (form.getAdr040Pow1() == GSConstAddress.ADR_INDUSTRY_EDIT_ADMIN) {
            msg += gsMsg.getMessage("cmn.only.admin.editable");
        } else if (form.getAdr040Pow1() == GSConstAddress.ADR_INDUSTRY_EDIT) {
            msg += gsMsg.getMessage("address.89");
        }
        //会社編集権限
        msg += "\r\n[" + gsMsg.getMessage("address.90") + "]";
        if (form.getAdr040Pow2() == GSConstAddress.ADR_COMPANY_EDIT_ADMIN) {
            msg += gsMsg.getMessage("cmn.only.admin.editable");
        } else if (form.getAdr040Pow2() == GSConstAddress.ADR_COMPANY_EDIT) {
            msg += gsMsg.getMessage("address.91");
        }
        //ラベル編集権限
        msg += "\r\n[" + gsMsg.getMessage("cmn.edit.permissions.label") + "]";
        if (form.getAdr040Pow3() == GSConstAddress.ADR_LABEL_EDIT_ADMIN) {
            msg += gsMsg.getMessage("cmn.only.admin.editable");
        } else if (form.getAdr040Pow3() == GSConstAddress.ADR_LABEL_EDIT) {
            msg += gsMsg.getMessage("cmn.noset.edit.permissions.label");
        }
        //エクスポート権限
        msg += "\r\n[" + gsMsg.getMessage("address.94") + "]";
        if (form.getAdr040Pow4() == GSConstAddress.ADR_EXPORT_ADMIN) {
            msg += gsMsg.getMessage("address.140");
        } else if (form.getAdr040Pow4() == GSConstAddress.ADR_EXPORT) {
            msg += gsMsg.getMessage("address.95");
        }
        return msg;
    }
}