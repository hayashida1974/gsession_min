package jp.groupsession.v2.cmn.cmn260kn;

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
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] OAuth認証情報登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn260knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn260knAction.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Cmn260knForm thisForm = (Cmn260knForm) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("cmn260knNext")) {
            //確定ボタン押下時
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("cmn260knBack")) {
            //戻るボタン押下時
            forward = map.findForward("cmn260knBack");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
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
    public ActionForward __doInit(ActionMapping map, Cmn260knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        Cmn260knParamModel paramMdl = new Cmn260knParamModel();
        paramMdl.setParam(form);
        Cmn260knBiz biz = new Cmn260knBiz(con);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

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
    public ActionForward __doDecision(ActionMapping map, Cmn260knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //登録処理
        RequestModel reqMdl = getRequestModel(req);
        MlCountMtController cntCon = getCountMtController(req);
        //登録、または更新処理を行う
        Cmn260knParamModel paramMdl = new Cmn260knParamModel();
        paramMdl.setParam(form);
        Cmn260knBiz biz = new Cmn260knBiz(con);

        //処理モード(新規登録）
        if (paramMdl.getCmn250CmdMode() == GSConstCommon.MODE_ADD) {
            //新規登録
            log__.debug("新規登録");
            biz._doInsert(paramMdl, userSid, cntCon);

            //編集
        } else if (paramMdl.getCmn250CmdMode() == GSConstCommon.MODE_EDIT) {
            log__.debug("編集");
            biz._doUpdate(paramMdl, userSid);
        }
        paramMdl.setFormData(form);

        //ログ出力
        outPutCULog(map, form, req, con, reqMdl);

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 管理者設定 認証情報登録(更新)のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param con DB Connection
     * @param reqMdl リクエストモデル
     */
    private void outPutCULog(
            ActionMapping map,
            Cmn260knForm form,
            HttpServletRequest req,
            Connection con,
            RequestModel reqMdl) {

        //ログ出力処理
        CommonBiz cmnBiz = new CommonBiz();
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();

        //内容セット
        String opCode = "";
        if (form.getCmn250CmdMode() == GSConstCommon.MODE_ADD) {
            opCode = gsMsg.getMessage(req, "cmn.entry");
        } else if (form.getCmn250CmdMode() == GSConstCommon.MODE_EDIT) {
            opCode = gsMsg.getMessage(req, "cmn.change");
        }

        String authName = null;
        if (form.getCmn260provider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
            authName = getInterMessage(req, GSConstCommon.PROVIDER_NAME_GOOGLE);
        } else {
            authName = getInterMessage(req, GSConstCommon.PROVIDER_NAME_MICROSOFT);
        }

        values.append("[" + gsMsg.getMessage("cmn.cmn260.04") + "]"
                + NullDefault.getString(authName, ""));

        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con, opCode,
                            GSConstLog.LEVEL_INFO, values.toString());
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
            Cmn260knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("cmn260knNext");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getCmn250CmdMode() == GSConstCommon.MODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getCmn250CmdMode() == GSConstCommon.MODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.cmn260.04")));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

}