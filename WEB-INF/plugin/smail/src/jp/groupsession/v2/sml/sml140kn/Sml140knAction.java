package jp.groupsession.v2.sml.sml140kn;

import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 個人設定 手動削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml140knAction extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml140knAction.class);

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
     * @see jp.groupsession.v2.sml.AbstractSmlAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeSmail(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = null;
        Sml140knForm thisform = (Sml140knForm) form;

        //編集可能かを判定
        SmlCommonBiz cmnBiz = new SmlCommonBiz(getRequestModel(req));
        if ((!_checkAuth(map, req, con)) && thisform.getSml140AccountSid() > 0) {
            if (!cmnBiz.canEditAccount(con, thisform.getSml140AccountSid(),
                                    getSessionUserModel(req).getUsrsid())) {
                return map.findForward("gf_msg");
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("delete")) {
            log__.debug("確定ボタン押下");
            forward = __doDelete(map, thisform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_syudo_input")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_syudo_input");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisform, req, res, con);
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
                                    Sml140knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        Sml140knParamModel paramMdl = new Sml140knParamModel();
        paramMdl.setParam(form);
        Sml140knBiz biz = new Sml140knBiz(con);
        biz.getInitData(getRequestModel(req), paramMdl);
        //年選択、月選択の入力チェックを行う
        biz.updateEgnoreYearMonth(paramMdl);
        paramMdl.setFormData(form);

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
    public ActionForward __doDelete(ActionMapping map,
                                     Sml140knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

        ActionForward forward = null;

        //入力チェック
        ActionErrors errors = form.validateSml140(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("back_syudo_input");
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        con.setAutoCommit(false);
        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);

        try {

            Sml140knParamModel paramMdl = new Sml140knParamModel();
            paramMdl.setParam(form);
            Sml140knBiz biz = new Sml140knBiz(con);
            biz.updateSyudoDelSetting(reqMdl, paramMdl);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage(reqMdl);
            String delete = gsMsg.getMessage("cmn.delete");

            //ログ出力処理
            SmlAccountModel sacMdl = new SmlAccountModel();
            SmlAccountDao sacDao = new SmlAccountDao(con);
            sacMdl = sacDao.select(form.getSml140AccountSid());

            String accountName = sacMdl.getSacName();

            if (paramMdl.getSml140SelKbn() != GSConstSmail.ACCOUNT_SEL) {
                accountName = gsMsg.getMessage("wml.wml120.01");
            }

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl,
                    delete, GSConstLog.LEVEL_INFO, "アカウント:" + accountName
                                                 + "\n[value]"
                    + form.getSml140JdelKbn()
                    + "-" + form.getSml140JYear()
                    + "-" + form.getSml140JMonth()
                    + ", "
                    + form.getSml140SdelKbn()
                    + "-" + form.getSml140SYear()
                    + "-" + form.getSml140SMonth()
                    + ", "
                    + form.getSml140WdelKbn()
                    + "-" + form.getSml140WYear()
                    + "-" + form.getSml140WMonth()
                    + ", "
                    + form.getSml140DdelKbn()
                    + "-" + form.getSml140DYear()
                    + "-" + form.getSml140DMonth()
                    );
            commit = true;

            forward = __doCompDsp(map, form, req, res);
            return forward;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Sml140knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String smail = gsMsg.getMessage(req, "cmn.shortmail");

        urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", smail));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}