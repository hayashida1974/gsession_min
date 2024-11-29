package jp.groupsession.v2.man.man500kn;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 個人情報編集権限設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man500knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man500knAction.class);

    /**
     * <br>[機  能] アクションを実行します。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        log__.debug("START");
        ActionForward forward = null;
        Man500knForm thisForm = (Man500knForm) form;

        //コマンド処理
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("man500knOk")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);
        } else if (cmd.equals("man500knBack")) {
            //戻るボタンクリック
            forward = map.findForward("man500knBack");
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Man500knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doDecision(ActionMapping map, Man500knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //入力チェック
        ActionErrors errors = form.validateCheack(getRequestModel(req), con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            log__.debug("入力エラー");
            return map.getInputForward();
        }

        //DBにデータを登録する
        Man500knParamModel paramMdl = new Man500knParamModel();
        paramMdl.setParam(form);
        Man500knBiz biz = new Man500knBiz(con, getRequestModel(req));
        biz.setData(paramMdl);
        paramMdl.setFormData(form);

        //ログ出力
        GsMessage gsMsg = new GsMessage(req);
        CommonBiz cmnBiz = new CommonBiz();

        //メッセージ作成
        String pconfEditKbnMsg = "";
        String passKbnMsg = "";
        String otpToAddressMsg = "";
        if (form.getMan500EditKbn() == GSConstMain.PCONF_EDIT_ADM) {
            pconfEditKbnMsg = gsMsg.getMessage("cmn.set.the.admin");
        } else {
            pconfEditKbnMsg = gsMsg.getMessage("cmn.set.eachuser");
        }
        if (form.getMan500PasswordKbn() == GSConstMain.PASSWORD_EDIT_ADM) {
            passKbnMsg = gsMsg.getMessage("cmn.set.the.admin");
        } else {
            passKbnMsg = gsMsg.getMessage("cmn.set.eachuser");
        }
        if (form.getMan500OtpAddressKbn() == GSConstMain.OTPTOADDRES_EDIT_ADM) {
            otpToAddressMsg = gsMsg.getMessage("cmn.set.the.admin");
        } else {
            otpToAddressMsg = gsMsg.getMessage("cmn.set.eachuser");
        }

        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                gsMsg.getMessage("cmn.entry"), GSConstLog.LEVEL_INFO,
                "[" + gsMsg.getMessage("main.man500.1") + "]" + pconfEditKbnMsg
                + " [" + gsMsg.getMessage("main.man500.4") + "]" + passKbnMsg
                + " [" + gsMsg.getMessage("user.157") + "]" + otpToAddressMsg
                );

        //完了メッセージ画面へ遷移
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
        Man500knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "main.man500.1")));
        req.setAttribute("cmn999Form", cmn999Form);
    }

}
