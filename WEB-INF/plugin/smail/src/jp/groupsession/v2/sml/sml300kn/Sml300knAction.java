package jp.groupsession.v2.sml.sml300kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ショートメール 管理者設定 ラベル登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml300knAction extends AbstractSmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml300knAction.class);

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
        Sml300knForm thisForm = (Sml300knForm) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

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
    public ActionForward __doInit(ActionMapping map, Sml300knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Sml300knParamModel paramMdl = new Sml300knParamModel();
        paramMdl.setParam(form);
        Sml300knBiz biz = new Sml300knBiz();
        biz.setInitData(con, paramMdl);
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
    public ActionForward __doDecision(ActionMapping map, Sml300knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //登録処理
        MlCountMtController cntCon = getCountMtController(req);
        //登録、または更新処理を行う
        Sml300knParamModel paramMdl = new Sml300knParamModel();
        paramMdl.setParam(form);
        Sml300knBiz biz = new Sml300knBiz(con);
        biz.doAddEdit(paramMdl, userSid, cntCon);
        paramMdl.setFormData(form);

        String opCode = "";
        if (form.getSmlLabelCmdMode() == GSConstSmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else if (form.getSmlLabelCmdMode() == GSConstSmail.CMDMODE_EDIT) {
            opCode = getInterMessage(req, "cmn.change");
        }
        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlAccountSid());

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
        smlBiz.outPutLog(map, getRequestModel(req),
                opCode, GSConstLog.LEVEL_INFO, "アカウント:" + sacMdl.getSacName()
                                         + "\n[name]"
                                         + NullDefault.getString(form.getSml300LabelName(), ""));



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
        Sml300knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("labelConf");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getSmlLabelCmdMode() == GSConstSmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getSmlLabelCmdMode() == GSConstSmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.label")));

        cmn999Form.addHiddenParam("sml290SortRadio", form.getSml290SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());
        cmn999Form.addHiddenParam("sml240keyword", form.getSml240keyword());
        cmn999Form.addHiddenParam("sml240group", form.getSml240group());
        cmn999Form.addHiddenParam("sml240user", form.getSml240user());
        cmn999Form.addHiddenParam("sml240svKeyword", form.getSml240svKeyword());
        cmn999Form.addHiddenParam("sml240svGroup", form.getSml240svGroup());
        cmn999Form.addHiddenParam("sml240svUser", form.getSml240svUser());
        cmn999Form.addHiddenParam("sml240sortKey", form.getSml240sortKey());
        cmn999Form.addHiddenParam("sml240order", form.getSml240order());
        cmn999Form.addHiddenParam("sml240searchFlg", form.getSml240searchFlg());
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());
        cmn999Form.addHiddenParam("smlCmdMode", form.getSmlCmdMode());
        cmn999Form.addHiddenParam("smlAccountMode", form.getSmlAccountMode());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

    }
}
