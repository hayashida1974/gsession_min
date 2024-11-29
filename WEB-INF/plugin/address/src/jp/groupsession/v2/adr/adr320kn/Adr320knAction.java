package jp.groupsession.v2.adr.adr320kn;


import java.lang.reflect.InvocationTargetException;
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
import jp.groupsession.v2.adr.AbstractAddressAdminAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr320.Adr320Form;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] アドレス帳 管理者設定 権限設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr320knAction extends AbstractAddressAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr320knAction.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;
        Adr320knForm adrForm = (Adr320knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ok")) {
            //確定
            forward = __doKakutei(map, adrForm, req, res, con);
        } else if (cmd.equals("adr320knback")) {
            //戻る
            forward = __doBack(map, adrForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, adrForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Adr320knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("adr320");
        return forward;
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Adr320knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        Adr320knBiz biz = new Adr320knBiz();
        Adr320knParamModel paramMdl = new Adr320knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, reqMdl, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }
    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doKakutei(ActionMapping map, Adr320knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        // 入力チェック
        ActionErrors errors = form.validateCommit(getRequestModel(req), con);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        Adr320knParamModel paramMdl = new Adr320knParamModel();
        paramMdl.setParam(form);

        Adr320knBiz biz = new Adr320knBiz();
        biz.comitData(reqMdl, con, paramMdl.getAdr320AacArestKbn(),
                paramMdl.getAdr320AdrArestList());

        paramMdl.setFormData(form);

        con.commit();

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        GsMessage gsMsg = new GsMessage();
        String msg = __getLogMessage(form, req, con);
        adrBiz.outPutLog(
                map, req, res,
                gsMsg.getMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, msg);

        return __setKanryoDsp(map, form, req);

    }

    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Adr320knForm form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("adr030");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //登録完了
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object",
                        gsMsg.getMessage(req, "address.adr320.1")));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("adr010cmdMode", form.getAdr010cmdMode());
        cmn999Form.addHiddenParam("adr010searchFlg", form.getAdr010searchFlg());
        cmn999Form.addHiddenParam("adr010EditAdrSid", form.getAdr010EditAdrSid());
        cmn999Form.addHiddenParam("adr010orderKey", form.getAdr010orderKey());
        cmn999Form.addHiddenParam("adr010sortKey", form.getAdr010sortKey());
        cmn999Form.addHiddenParam("adr010page", form.getAdr010page());
        cmn999Form.addHiddenParam("adr010pageTop", form.getAdr010pageTop());
        cmn999Form.addHiddenParam("adr010pageBottom", form.getAdr010pageBottom());
        cmn999Form.addHiddenParam("adr010code", form.getAdr010code());
        cmn999Form.addHiddenParam("adr010coName", form.getAdr010coName());
        cmn999Form.addHiddenParam("adr010coNameKn", form.getAdr010coNameKn());
        cmn999Form.addHiddenParam("adr010coBaseName", form.getAdr010coBaseName());
        cmn999Form.addHiddenParam("adr010atiSid", form.getAdr010atiSid());
        cmn999Form.addHiddenParam("adr010tdfk", form.getAdr010tdfk());
        cmn999Form.addHiddenParam("adr010biko", form.getAdr010biko());
        cmn999Form.addHiddenParam("adr010syozoku", form.getAdr010syozoku());
        cmn999Form.addHiddenParam("adr010svCode", form.getAdr010svCode());
        cmn999Form.addHiddenParam("adr010svCoName", form.getAdr010svCoName());
        cmn999Form.addHiddenParam("adr010svCoNameKn", form.getAdr010svCoNameKn());
        cmn999Form.addHiddenParam("adr010svCoBaseName", form.getAdr010svCoBaseName());
        cmn999Form.addHiddenParam("adr010svAtiSid", form.getAdr010svAtiSid());
        cmn999Form.addHiddenParam("adr010svTdfk", form.getAdr010svTdfk());
        cmn999Form.addHiddenParam("adr010svBiko", form.getAdr010svBiko());
        cmn999Form.addHiddenParam("adr010SearchComKana", form.getAdr010SearchComKana());
        cmn999Form.addHiddenParam("adr010svSearchComKana", form.getAdr010svSearchComKana());
        cmn999Form.addHiddenParam("adr010SearchKana", form.getAdr010SearchKana());
        cmn999Form.addHiddenParam("adr010svSearchKana", form.getAdr010svSearchKana());
        cmn999Form.addHiddenParam("adr010tantoGroup", form.getAdr010tantoGroup());
        cmn999Form.addHiddenParam("adr010tantoUser", form.getAdr010tantoUser());
        cmn999Form.addHiddenParam("adr010svTantoGroup", form.getAdr010svTantoGroup());
        cmn999Form.addHiddenParam("adr010svTantoUser", form.getAdr010svTantoUser());
        cmn999Form.addHiddenParam("adr010unameSei", form.getAdr010unameSei());
        cmn999Form.addHiddenParam("adr010unameMei", form.getAdr010unameMei());
        cmn999Form.addHiddenParam("adr010unameSeiKn", form.getAdr010unameSeiKn());
        cmn999Form.addHiddenParam("adr010unameMeiKn", form.getAdr010unameMeiKn());
        cmn999Form.addHiddenParam("adr010detailCoName", form.getAdr010detailCoName());
        cmn999Form.addHiddenParam("adr010position", form.getAdr010position());
        cmn999Form.addHiddenParam("adr010mail", form.getAdr010mail());
        cmn999Form.addHiddenParam("adr010detailTantoGroup", form.getAdr010detailTantoGroup());
        cmn999Form.addHiddenParam("adr010detailTantoUser", form.getAdr010detailTantoUser());
        cmn999Form.addHiddenParam("adr010detailAtiSid", form.getAdr010detailAtiSid());
        cmn999Form.addHiddenParam("adr010svUnameSei", form.getAdr010svUnameSei());
        cmn999Form.addHiddenParam("adr010svUnameMei", form.getAdr010svUnameMei());
        cmn999Form.addHiddenParam("adr010svUnameSeiKn", form.getAdr010svUnameSeiKn());
        cmn999Form.addHiddenParam("adr010svUnameMeiKn", form.getAdr010svUnameMeiKn());
        cmn999Form.addHiddenParam("adr010svDetailCoName", form.getAdr010svDetailCoName());
        cmn999Form.addHiddenParam("adr010svPosition", form.getAdr010svPosition());
        cmn999Form.addHiddenParam("adr010svMail", form.getAdr010svMail());
        cmn999Form.addHiddenParam("adr010svDetailTantoGroup", form.getAdr010svDetailTantoGroup());
        cmn999Form.addHiddenParam("adr010svDetailTantoUser", form.getAdr010svDetailTantoUser());
        cmn999Form.addHiddenParam("adr010svDetailAtiSid", form.getAdr010svDetailAtiSid());
        cmn999Form.addHiddenParam("adr010tantoGroupContact", form.getAdr010tantoGroupContact());
        cmn999Form.addHiddenParam("adr010tantoUserContact", form.getAdr010tantoUserContact());
        cmn999Form.addHiddenParam("adr010unameSeiContact", form.getAdr010unameSeiContact());
        cmn999Form.addHiddenParam("adr010unameMeiContact", form.getAdr010unameMeiContact());
        cmn999Form.addHiddenParam("adr010CoNameContact", form.getAdr010CoNameContact());
        cmn999Form.addHiddenParam("adr010CoBaseNameContact", form.getAdr010CoBaseNameContact());
        cmn999Form.addHiddenParam("adr010ProjectContact", form.getAdr010ProjectContact());
        cmn999Form.addHiddenParam("adr010TempFilekbnContact", form.getAdr010TempFilekbnContact());
        cmn999Form.addHiddenParam("adr010SltYearFrContact", form.getAdr010SltYearFrContact());
        cmn999Form.addHiddenParam("adr010SltMonthFrContact", form.getAdr010SltMonthFrContact());
        cmn999Form.addHiddenParam("adr010SltDayFrContact", form.getAdr010SltDayFrContact());
        cmn999Form.addHiddenParam("adr010SltYearToContact", form.getAdr010SltYearToContact());
        cmn999Form.addHiddenParam("adr010SltMonthToContact", form.getAdr010SltMonthToContact());
        cmn999Form.addHiddenParam("adr010SltDayToContact", form.getAdr010SltDayToContact());
        cmn999Form.addHiddenParam("adr010SyubetsuContact", form.getAdr010SyubetsuContact());
        cmn999Form.addHiddenParam("adr010SearchWordContact", form.getAdr010SearchWordContact());
        cmn999Form.addHiddenParam("adr010KeyWordkbnContact", form.getAdr010KeyWordkbnContact());
        cmn999Form.addHiddenParam("adr010dateNoKbn", form.getAdr010dateNoKbn());
        cmn999Form.addHiddenParam("adr010svTantoGroupContact", form.getAdr010svTantoGroupContact());
        cmn999Form.addHiddenParam("adr010svTantoUserContact", form.getAdr010svTantoUserContact());
        cmn999Form.addHiddenParam("adr010svUnameSeiContact", form.getAdr010svUnameSeiContact());
        cmn999Form.addHiddenParam("adr010svUnameMeiContact", form.getAdr010svUnameMeiContact());
        cmn999Form.addHiddenParam("adr010svCoNameContact", form.getAdr010svCoNameContact());
        cmn999Form.addHiddenParam("adr010svCoBaseNameContact", form.getAdr010svCoBaseNameContact());
        cmn999Form.addHiddenParam("adr010svProjectContact", form.getAdr010svProjectContact());
        cmn999Form.addHiddenParam("adr010SvTempFilekbnContact",
                                                             form.getAdr010SvTempFilekbnContact());
        cmn999Form.addHiddenParam("adr010svSltYearFrContact", form.getAdr010svSltYearFrContact());
        cmn999Form.addHiddenParam("adr010svSltMonthFrContact", form.getAdr010svSltMonthFrContact());
        cmn999Form.addHiddenParam("adr010svSltDayFrContact", form.getAdr010svSltDayFrContact());
        cmn999Form.addHiddenParam("adr010svSltYearToContact", form.getAdr010svSltYearToContact());
        cmn999Form.addHiddenParam("adr010svSltMonthToContact", form.getAdr010svSltMonthToContact());
        cmn999Form.addHiddenParam("adr010svSltDayToContact", form.getAdr010svSltDayToContact());
        cmn999Form.addHiddenParam("adr010svSyubetsuContact", form.getAdr010svSyubetsuContact());
        cmn999Form.addHiddenParam("adr010svSearchWordContact", form.getAdr010svSearchWordContact());
        cmn999Form.addHiddenParam("adr010SvKeyWordkbnContact", form.getAdr010SvKeyWordkbnContact());
        cmn999Form.addHiddenParam("adr010svdateNoKbn", form.getAdr010svdateNoKbn());
        cmn999Form.addHiddenParam("adr010svSyozoku", form.getAdr010svSyozoku());
        cmn999Form.addHiddenParam("adr010InitDspContactFlg", form.getAdr010InitDspContactFlg());
        cmn999Form.addHiddenParam("projectKbnSv", form.getProjectKbnSv());
        cmn999Form.addHiddenParam("statusKbnSv", form.getStatusKbnSv());
        cmn999Form.addHiddenParam("selectingProjectSv", form.getSelectingProjectSv());
        cmn999Form.addHiddenParam("projectKbn", form.getProjectKbn());
        cmn999Form.addHiddenParam("statusKbn", form.getStatusKbn());
        cmn999Form.addHiddenParam("selectingProject", form.getSelectingProject());
        cmn999Form.addHiddenParam("adr010selectCategory", form.getAdr010selectCategory());
        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Adr320Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        //選択リスト
        Adr320knBiz biz = new Adr320knBiz();
        String selectMsg = biz.getLogMessage(getRequestModel(req),
                con, form.getAdr320AdrArestList());
        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //登録制限区分
        msg += "[" + gsMsg.getMessage("address.adr320.4") + "]";
        if (form.getAdr320AacArestKbn() == GSConstAddress.POW_ADR_ALL) {
            msg += gsMsg.getMessage("address.adr320.7");
        } else if (form.getAdr320AacArestKbn() == GSConstAddress.POW_ADR_LIMIT) {
            msg += gsMsg.getMessage("address.adr320.6");
            msg += "\r\n" + selectMsg;
        }
        return msg;
    }
}
