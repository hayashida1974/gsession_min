package jp.groupsession.v2.adr.adr310;

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
import jp.groupsession.v2.adr.AbstractAddressAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 個人設定 初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr310Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr310Action.class);

    /**
     * <br>アクション実行
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

        ActionForward forward = null;
        Adr310Form adrForm = (Adr310Form) form;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("adr310kakunin")) {
            //OKボタンクリック
            forward = __doOk(map, adrForm, req, res, con);
        } else if (cmd.equals("adr310commit")) {
            //登録 実行
            forward = __doCommit(map, adrForm, req, res, con);
        } else if (cmd.equals("adr310back")) {
            //戻る
            forward = __doBack(map, adrForm, req, res, con);
        } else if (cmd.equals("adr310reload")) {
            //再表示
            forward = __doReload(map, adrForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, adrForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doOk(ActionMapping map, Adr310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("確認");
        ActionErrors errors = form.validateCheck(req);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
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
    private ActionForward __doBack(ActionMapping map, Adr310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("adr050");
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
    private ActionForward __doInit(ActionMapping map, Adr310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示");
        ActionForward forward = null;

        con.setAutoCommit(true);
        Adr310Biz biz = new Adr310Biz(getRequestModel(req));

        Adr310ParamModel paramMdl = new Adr310ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }
    /**
     * <br>再表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doReload(ActionMapping map, Adr310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("再表示");
        ActionForward forward = null;

        //コンボデータセット
        Adr310Biz biz = new Adr310Biz(getRequestModel(req));

        Adr310ParamModel paramMdl = new Adr310ParamModel();
        paramMdl.setParam(form);
        biz.setAdr310CombData(paramMdl);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        return forward;
    }
    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Adr310Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
//        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage();
        String msgState = "edit.kakunin.once";
        //初期値設定
        String mkey1 = gsMsg.getMessage(req, "cmn.default.setting");
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));
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
        cmn999Form.addHiddenParam("adr010svSyozoku", form.getAdr010svSyozoku());
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
        cmn999Form.addHiddenParam("adr010InitDspContactFlg", form.getAdr010InitDspContactFlg());
        cmn999Form.addHiddenParam("projectKbnSv", form.getProjectKbnSv());
        cmn999Form.addHiddenParam("statusKbnSv", form.getStatusKbnSv());
        cmn999Form.addHiddenParam("selectingProjectSv", form.getSelectingProjectSv());
        cmn999Form.addHiddenParam("projectKbn", form.getProjectKbn());
        cmn999Form.addHiddenParam("statusKbn", form.getStatusKbn());
        cmn999Form.addHiddenParam("selectingProject", form.getSelectingProject());
        cmn999Form.addHiddenParam("adr010selectCategory", form.getAdr010selectCategory());
        cmn999Form.addHiddenParam("adr310MemDspKbn", form.getAdr310MemDspKbn());
        cmn999Form.addHiddenParam("adr310PermitKbn", form.getAdr310PermitKbn());
        cmn999Form.addHiddenParam("adr310EditKbn", form.getAdr310EditKbn());
        req.setAttribute("cmn999Form", cmn999Form);
    }
    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doCommit(
        ActionMapping map,
        Adr310Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("登録");
        ActionForward forward = null;

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        BaseUserModel umodel = getSessionUserModel(req);
        Adr310Biz biz = new Adr310Biz(getRequestModel(req));

        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //DB更新
        Adr310ParamModel paramMdl = new Adr310ParamModel();
        paramMdl.setParam(form);
        biz.setAdrUconf(paramMdl, umodel.getUsrsid(), con);
        paramMdl.setFormData(form);

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        GsMessage gsMsg = new GsMessage();
        String msg = __getLogMessage(form, req);
        adrBiz.outPutLog(
                map, req, res,
                gsMsg.getMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, msg);

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);
        forward = map.findForward("gf_msg");
        return forward;
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
        Adr310Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("adr310back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage();
        String msgState = "touroku.kanryo.object";
        //初期値設定
        String key1 = gsMsg.getMessage(req, "cmn.default.setting");
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
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
        cmn999Form.addHiddenParam("adr010svSyozoku", form.getAdr010svSyozoku());
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
        cmn999Form.addHiddenParam("adr010InitDspContactFlg", form.getAdr010InitDspContactFlg());
        cmn999Form.addHiddenParam("projectKbnSv", form.getProjectKbnSv());
        cmn999Form.addHiddenParam("statusKbnSv", form.getStatusKbnSv());
        cmn999Form.addHiddenParam("selectingProjectSv", form.getSelectingProjectSv());
        cmn999Form.addHiddenParam("projectKbn", form.getProjectKbn());
        cmn999Form.addHiddenParam("statusKbn", form.getStatusKbn());
        cmn999Form.addHiddenParam("selectingProject", form.getSelectingProject());
        cmn999Form.addHiddenParam("adr010selectCategory", form.getAdr010selectCategory());
        cmn999Form.addHiddenParam("adr310MemDspKbn", form.getAdr310MemDspKbn());
        cmn999Form.addHiddenParam("adr310PermitKbn", form.getAdr310PermitKbn());
        cmn999Form.addHiddenParam("adr310EditKbn", form.getAdr310EditKbn());
        req.setAttribute("cmn999Form", cmn999Form);

    }
    /**
     * <br>[機  能] 権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward checkPow(ActionMapping map,
                                   HttpServletRequest req,
                                   Connection con) throws Exception {

        //閲覧・編集権限を取得
        AdrAconfDao dao = new AdrAconfDao(con);
        AdrAconfModel model = dao.selectAconf();

        if (model != null && model.getAacVrmEdit() != GSConstAddress.MEM_DSP_USR) {
            return map.findForward("gf_power");
        }

        return null;
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
        Adr310Form form,
        HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //アドレス閲覧･編集権限
        msg += gsMsg.getMessage("cmn.reading") + " : ";
        if (form.getAdr310PermitKbn() == GSConstAddress.MEM_OP_TANTO) {
            msg += gsMsg.getMessage("address.62");
            msg += "\r\n" + gsMsg.getMessage("cmn.edit") + " : ";
            msg += gsMsg.getMessage("address.62");
        } else if (form.getAdr310PermitKbn() == GSConstAddress.MEM_OP_GROUP) {
            msg += gsMsg.getMessage("group.designation");
            msg += "\r\n" + gsMsg.getMessage("cmn.edit") + " : ";
            msg += gsMsg.getMessage("group.designation");
        } else if (form.getAdr310PermitKbn() == GSConstAddress.MEM_OP_USER) {
            msg += gsMsg.getMessage("cmn.user.specified");
            msg += "\r\n" + gsMsg.getMessage("cmn.edit") + " : ";
            msg += gsMsg.getMessage("cmn.user.specified");
        } else if (form.getAdr310PermitKbn() == GSConstAddress.MEM_OP_ALL) {
            msg += gsMsg.getMessage("cmn.no.setting.permission");
            msg += "\r\n" + gsMsg.getMessage("cmn.edit") + " : ";
            if (form.getAdr310EditKbn() == GSConstAddress.MEM_OP_TANTO) {
                msg += gsMsg.getMessage("address.62");
            } else if (form.getAdr310EditKbn() == GSConstAddress.MEM_OP_GROUP) {
                msg += gsMsg.getMessage("group.designation");
            } else if (form.getAdr310EditKbn() == GSConstAddress.MEM_OP_USER) {
                msg += gsMsg.getMessage("cmn.user.specified");
            } else if (form.getAdr310EditKbn() == GSConstAddress.MEM_OP_ALL) {
                msg += gsMsg.getMessage("cmn.no.setting.permission");
            }
        }
        return msg;
    }
}
