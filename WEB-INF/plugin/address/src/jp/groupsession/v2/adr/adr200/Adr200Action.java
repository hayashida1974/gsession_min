package jp.groupsession.v2.adr.adr200;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.AbstractAddressSubAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 ラベル登録ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr200Action extends AbstractAddressSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr200Action.class);

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
                                        Connection con)
        throws Exception {

        Adr200Form thisForm = (Adr200Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("CMD = " + cmd);

        ActionForward forward = null;
        if (cmd.equals("registLabel")) {
            //OKボタンクリック
            forward = __doEntry(map, thisForm, req, res, con);
        } else {
            forward = __doInit(map, thisForm, req, res, con);
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
                                    Adr200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        Adr200Biz biz = new Adr200Biz(getRequestModel(req));
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel userMdl = getSessionUserModel(req);

        //セッションユーザが管理者かどうか判定
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, GSConstAddress.PLUGIN_ID_ADDRESS);
        AdrAconfDao aconfDao = new AdrAconfDao(con);
        AdrAconfModel aconfMdl = aconfDao.selectAconf();

        //セッションユーザに編集権限がない場合、エラーメッセージを表示
        if (!adminUser && aconfMdl != null
         && aconfMdl.getAacAlbEdit() == GSConstAddress.POW_LIMIT) {
            ActionErrors errors = new ActionErrors();
            GsMessage gsMsg = new GsMessage();

            String text = gsMsg.getMessage(req, "error.access.power.user");
            ActionMessage msg = new ActionMessage("error.access.power.user", text);

            StrutsUtil.addMessage(errors, msg, "error.access.power.user");
            addErrors(req, errors);

            return map.getInputForward();
        }

        Adr200ParamModel paramMdl = new Adr200ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(con, paramMdl, userMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
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
    private ActionForward __doEntry(ActionMapping map,
                                    Adr200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionErrors errors = form.validateCheck(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Adr200Biz biz = new Adr200Biz(getRequestModel(req));
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel userMdl = getSessionUserModel(req);

        //セッションユーザが管理者かどうか判定
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, GSConstAddress.PLUGIN_ID_ADDRESS);
        AdrAconfDao aconfDao = new AdrAconfDao(con);
        AdrAconfModel aconfMdl = aconfDao.selectAconf();

        //セッションユーザに編集権限がない場合、エラーメッセージを表示
        if (!adminUser && aconfMdl != null
         && aconfMdl.getAacAlbEdit() == GSConstAddress.POW_LIMIT) {
            errors = new ActionErrors();
            GsMessage gsMsg = new GsMessage();

            String text = gsMsg.getMessage(req, "error.access.power.user");
            ActionMessage msg = new ActionMessage("error.access.power.user", text);

            StrutsUtil.addMessage(errors, msg, "error.access.power.user");
            addErrors(req, errors);

            return map.getInputForward();
        }

        boolean commit = false;
        try {

            Adr200ParamModel paramMdl = new Adr200ParamModel();
            paramMdl.setParam(form);
            biz.entryLabelData(con, paramMdl, getCountMtController(req),
                    getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String opCode = gsMsg.getMessage(req, "cmn.entry");
            //ログ出力処理
            AdrCommonBiz adrBiz = new AdrCommonBiz(con);
            String category = biz.getCategoryName(con, form.getAdr200selectCategory());
            adrBiz.outPutLog(
                    map, req, res, opCode,
                    GSConstLog.LEVEL_TRACE,
                    "[" + gsMsg.getMessage("cmn.category") + "]" + category
                    + "\r\n[" + gsMsg.getMessage("cmn.label.name") + "]"
                            + form.getAdr200labelName());

            con.commit();
            commit = true;

        } catch (Exception e) {
            log__.error("ラベル情報の登録に失敗");
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        form.setAdr200closeFlg(true);
        return __doInit(map, form, req, res, con);
    }
}