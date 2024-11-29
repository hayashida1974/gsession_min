package jp.groupsession.v2.usr.usr044;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.AbstractUsrAction;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

/**
 * <br>[機  能] ユーザ情報 ラベル一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr044Action extends AbstractUsrAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr044Action.class);

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

        ActionForward forward = null;
        Usr044Form usr044Form = (Usr044Form) form;

        UsrCommonBiz cmnBiz = new UsrCommonBiz(con, getRequestModel(req));
        //カテゴリの存在チェックを行う
        int lucSid = usr044Form.getUsr043EditSid();
        if (!cmnBiz.isCheckExistUsrCategory(con, lucSid)) {
            return __setAlreadyDeletePageParam(map, req, usr044Form);
        }

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }


        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("usr043back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("back");

        } else if (cmd.equals("addLabel")) {
            log__.debug("追加ボタンクリック");
            forward = map.findForward(cmd);

        } else if (cmd.equals("labelEdit")) {
            log__.debug("ラベル名クリック");
            forward = map.findForward(cmd);

        } else if (cmd.equals("usr044up")) {
            log__.debug("上へボタンクリック");
            forward = __doSortChange(map, usr044Form, req, res, con, Usr044Biz.SORT_UP);

        } else if (cmd.equals("usr044down")) {
            log__.debug("下へボタンクリック");
            forward = __doSortChange(map, usr044Form, req, res, con, Usr044Biz.SORT_DOWN);

        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, usr044Form, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期パラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map, Usr044Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //ラベル一覧取得し画面にセットする。
        con.setAutoCommit(true);
        Usr044Biz biz = new Usr044Biz();

        Usr044ParamModel paramMdl = new Usr044ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return ActionForward
     * @throws Exception 例外
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Usr044Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;


        try {
            Usr044Biz biz = new Usr044Biz();

            Usr044ParamModel paramMdl = new Usr044ParamModel();
            paramMdl.setParam(form);
            int sortFlg = biz.updateSort(con, paramMdl, changeKbn);
            paramMdl.setFormData(form);

            if (sortFlg == 0) {
                RequestModel reqMdl = getRequestModel(req);
                GsMessage gsMsg = new GsMessage(reqMdl);
                String opCode = gsMsg.getMessage("cmn.change");
                String targetName = biz.getTargetName(paramMdl, con);

                //ログ出力処理
                UsrCommonBiz usrBiz = new UsrCommonBiz(con, reqMdl);
                String kbn = gsMsg.getMessage("cmn.up");
                if (changeKbn == 1) {
                    kbn = gsMsg.getMessage("cmn.down");
                }
                usrBiz.outPutLog(opCode,
                        GSConstLog.LEVEL_INFO,
                        "[" + gsMsg.getMessage("cmn.target") + "]" + targetName + "\r\n"
                                + "[" + gsMsg.getMessage("change.sort.order") + "]" + kbn,
                                map.getType());
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        return __doInit(map, form, req, res, con);
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
            HttpServletRequest req, Connection con)
    throws Exception {

        BaseUserModel buMdl = getSessionUserModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstUser.PLUGIN_ID_USER);

        if (!adminUser) {
            con.setAutoCommit(true);
            CmnLabelUsrConfDao dao = new CmnLabelUsrConfDao(con);
            CmnLabelUsrConfModel model = dao.select();
            con.setAutoCommit(false);
            if (model != null && model.getLufEdit() == GSConstUser.POW_LIMIT) {
                return getNotAdminSeniPage(map, req);
            }
        }
        return null;
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
    private ActionForward __setAlreadyDeletePageParam(
        ActionMapping map,
        HttpServletRequest req,
        Usr044Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("error.none.category.data2"));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("usr040cmdMode", form.getUsr040cmdMode());
        cmn999Form.addHiddenParam("usr040orderKey", form.getUsr040orderKey());
        cmn999Form.addHiddenParam("usr040sortKey", form.getUsr040sortKey());
        cmn999Form.addHiddenParam("usr040orderKey2", form.getUsr040orderKey2());
        cmn999Form.addHiddenParam("usr040sortKey2", form.getUsr040sortKey2());
        cmn999Form.addHiddenParam("usr040pageNum1", form.getUsr040pageNum1());
        cmn999Form.addHiddenParam("usr040pageNum2", form.getUsr040pageNum2());
        cmn999Form.addHiddenParam("usr040SearchKana", form.getUsr040SearchKana());
        cmn999Form.addHiddenParam("selectgsid", form.getSelectgsid());
        cmn999Form.addHiddenParam("usr040Keyword", form.getUsr040Keyword());
        cmn999Form.addHiddenParam("usr040KeyKbnShainno", form.getUsr040KeyKbnShainno());
        cmn999Form.addHiddenParam("usr040KeyKbnName", form.getUsr040KeyKbnName());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKn", form.getUsr040KeyKbnNameKn());
        cmn999Form.addHiddenParam("usr040KeyKbnMail", form.getUsr040KeyKbnMail());
        cmn999Form.addHiddenParam("usr040KeyKbnTel", form.getUsr040KeyKbnTel());
        cmn999Form.addHiddenParam("usr040agefrom", form.getUsr040agefrom());
        cmn999Form.addHiddenParam("usr040ageto", form.getUsr040ageto());
        cmn999Form.addHiddenParam("usr040yakushoku", form.getUsr040yakushoku());
        cmn999Form.addHiddenParam("usr040tdfkCd", form.getUsr040tdfkCd());
        cmn999Form.addHiddenParam("usr040seibetu", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFr", form.getUsr040entranceYearFr());
        cmn999Form.addHiddenParam("usr040entranceMonthFr", form.getUsr040entranceMonthFr());
        cmn999Form.addHiddenParam("usr040entranceDayFr", form.getUsr040entranceDayFr());
        cmn999Form.addHiddenParam("usr040entranceYearTo", form.getUsr040entranceYearTo());
        cmn999Form.addHiddenParam("usr040entranceMonthTo", form.getUsr040entranceMonthTo());
        cmn999Form.addHiddenParam("usr040entranceDayTo", form.getUsr040entranceDayTo());
        if (form.getUsr040labSid() != null) {
            for (int i = 0; i < form.getUsr040labSid().length; i++) {
                cmn999Form.addHiddenParam("usr040labSid", form.getUsr040labSid()[i]);
            }
        }

        cmn999Form.addHiddenParam("selectgsidSave", form.getSelectgsidSave());
        cmn999Form.addHiddenParam("usr040SearchKanaSave", form.getUsr040SearchKanaSave());
        cmn999Form.addHiddenParam("usr040KeywordSave", form.getUsr040KeywordSave());
        cmn999Form.addHiddenParam("usr040KeyKbnShainnoSave", form.getUsr040KeyKbnShainnoSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameSave", form.getUsr040KeyKbnNameSave());
        cmn999Form.addHiddenParam("usr040KeyKbnNameKnSave", form.getUsr040KeyKbnNameKnSave());
        cmn999Form.addHiddenParam("usr040KeyKbnMailSave", form.getUsr040KeyKbnMailSave());
        cmn999Form.addHiddenParam("usr040KeyKbnTelSave", form.getUsr040KeyKbnTelSave());
        cmn999Form.addHiddenParam("usr040agefromSave", form.getUsr040agefromSave());
        cmn999Form.addHiddenParam("usr040agetoSave", form.getUsr040agetoSave());
        cmn999Form.addHiddenParam("usr040yakushokuSave", form.getUsr040yakushokuSave());
        cmn999Form.addHiddenParam("usr040tdfkCdSave", form.getUsr040tdfkCdSave());
        cmn999Form.addHiddenParam("usr040seibetuSave", form.getUsr040seibetu());
        cmn999Form.addHiddenParam("usr040entranceYearFrSave", form.getUsr040entranceYearFrSave());
        cmn999Form.addHiddenParam("usr040entranceMonthFrSave", form.getUsr040entranceMonthFrSave());
        cmn999Form.addHiddenParam("usr040entranceDayFrSave", form.getUsr040entranceDayFrSave());
        cmn999Form.addHiddenParam("usr040entranceYearToSave", form.getUsr040entranceYearToSave());
        cmn999Form.addHiddenParam("usr040entranceMonthToSave", form.getUsr040entranceMonthToSave());
        cmn999Form.addHiddenParam("usr040entranceDayToSave", form.getUsr040entranceDayToSave());
        if (form.getUsr040labSidSave() != null && form.getUsr040labSidSave().length != 0) {
            for (int n = 0; n < form.getUsr040labSidSave().length; n++) {
                cmn999Form.addHiddenParam("usr040labSidSave", form.getUsr040labSidSave()[n]);
            }
        }

        cmn999Form.addHiddenParam("usr040SearchFlg", form.getUsr040SearchFlg());
        cmn999Form.addHiddenParam("usr040DspFlg", form.getUsr040DspFlg());
        cmn999Form.addHiddenParam("usr040GrpSearchGId", form.getUsr040GrpSearchGId());
        cmn999Form.addHiddenParam("usr040GrpSearchGName", form.getUsr040GrpSearchGName());
        cmn999Form.addHiddenParam("usr040GrpSearchGIdSave", form.getUsr040GrpSearchGIdSave());
        cmn999Form.addHiddenParam("usr040GrpSearchGNameSave", form.getUsr040GrpSearchGNameSave());


        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}