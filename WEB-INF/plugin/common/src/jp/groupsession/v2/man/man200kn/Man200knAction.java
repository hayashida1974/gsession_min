package jp.groupsession.v2.man.man200kn;

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
import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 パスワードルール設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man200knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man200knAction.class);

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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        //パスワード変更を許可しない場合、アクセスエラーとする
        if (!canChangePassword(con, 0)) {
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        Man200knForm manform = (Man200knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("setting")) {
            log__.debug("確定ボタン押下");
            forward = __doSetting(map, manform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_pswdConf")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_pswdConf");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, manform, req, res, con);
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
                                    Man200knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param map ActionMapping
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return forward アクションフォワード
    * @throws Exception 実行時例外
    */
    public ActionForward __doSetting(ActionMapping map,
                                    Man200knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionForward forward = null;
        ActionErrors errors = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        errors = form.validateCheck(reqMdl);

        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            log__.debug("入力エラー");
            return map.getInputForward();
        }

        //ログインユーザSIDを取得
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();

        //パスワードルールの更新を行う
        Man200knParamModel paramMdl = new Man200knParamModel();
        paramMdl.setParam(form);
        Man200knBiz biz = new Man200knBiz(con);

        CmnPswdConfDao dao = new CmnPswdConfDao(con);
        if (dao.select() != null) {
            log__.debug("更新");
            biz.update(paramMdl, sessionUserSid);
        } else {
            log__.debug("新規");
            biz.insert(paramMdl, sessionUserSid);
        }
        paramMdl.setFormData(form);

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);

        String value = "";
        // 桁数
        String wordNum = String.valueOf(paramMdl.getMan200Digit());
        value = "[" + gsMsg.getMessage("main.man200.2") + "] "
            + gsMsg.getMessageVal0("cmn.digit.more", wordNum);
        // 英数記号混在
        String[] coeKbn = {
            gsMsg.getMessage("cmn.no"),
            gsMsg.getMessage("main.man200.17"),
            gsMsg.getMessage("main.man200.18")
        };
        value += "\r\n" + "[" + gsMsg.getMessage("main.man200.4") + "] "
            + coeKbn[paramMdl.getMan200CoeKbn()];
        // 大文字小文字混在
        String[] uppercaseKbn = {
            gsMsg.getMessage("cmn.no"),
            gsMsg.getMessage("cmn.required")
        };
        value += "\r\n" + "[" + gsMsg.getMessage("main.man200.20") + "] "
            + uppercaseKbn[paramMdl.getMan200UppercaseKbn()];
        // 有効期限
        String[] limit = {
            gsMsg.getMessage("main.man200.9"),
            gsMsg.getMessage("main.man200.10")
        };
        value += "\r\n" + "[" + gsMsg.getMessage("man.expiration.date") + "] "
            + limit[paramMdl.getMan200LimitKbn()];
        if (paramMdl.getMan200LimitKbn() == GSConstMain.PASSWORD_RULE_SET_LIMIT) {
            value += " " + gsMsg.getMessage("main.man200.11")
                + paramMdl.getMan200LimitDay() + gsMsg.getMessage("main.man200.12");
        }
        // ユーザID同一
        String[] pswdKbn = {
            gsMsg.getMessage("cmn.permit"),
            gsMsg.getMessage("cmn.not.permit")
        };
        value += "\r\n" + "[" + gsMsg.getMessage("main.man200.13") + "] "
            + pswdKbn[paramMdl.getMan200UidPswdKbn()];
        // 旧パスワード同一
        value += "\r\n" + "[" + gsMsg.getMessage("main.man200.15") + "] "
            + pswdKbn[paramMdl.getMan200OldPswdKbn()];

        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
            getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, value);

        return __setUpdateComp(map, form, req);
    }

    /**
     * <br>[機  能] 更新完了後画面設定
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param map マップ
    * @param form フォーム
    * @param req リクエスト
    * @return ActionForward フォワード
    * @throws Exception 実行時例外
    */
    private ActionForward __setUpdateComp(ActionMapping map,
                                        Man200knForm form,
                                        HttpServletRequest req)
        throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("updateComp");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);

        cmn999Form.setMessage(
                msgRes.getMessage(
                        "settei.kanryo.object",
                        getInterMessage(req, GSConstMain.TEXT_PWC_RULE)));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
