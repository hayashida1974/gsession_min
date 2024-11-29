package jp.groupsession.v2.usr.usr041;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.AbstractUsrAction;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.usr040.Usr040Form;

/**
 * <br>[機  能] ユーザ情報 個人設定 表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr041Action extends AbstractUsrAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr041Action.class);

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
        Usr041Form usr041Form = (Usr041Form) form;

        GsMessage gsMsg = new GsMessage();
        //社員/職員番号
        String textStaffNumber = gsMsg.getMessage(req, "cmn.employee.staff.number");
        //氏名
        String textName = gsMsg.getMessage(req, "cmn.name");
        //役職
        String textPost = gsMsg.getMessage(req, "cmn.post");
        //生年月日
        String textBirthday = gsMsg.getMessage(req, "cmn.birthday");
        //ソートキー1
        String textSortkey1 = gsMsg.getMessage("cmn.sortkey") + 1;
        //ソートキー2
        String textSortkey2 = gsMsg.getMessage("cmn.sortkey") + 2;

        String[] listSortKeyUsrText = new String[] {
                textStaffNumber, textName, textPost,
                           textBirthday, textSortkey1, textSortkey2 };
        //ソートキーラベル
        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstUser.LIST_SORT_KEY_USR.length; i++) {
            String label = listSortKeyUsrText[i];
            String value = Integer.toString(GSConstUser.LIST_SORT_KEY_USR[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        usr041Form.setUsr041SortKeyLabel(sortLabel);
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("usr041back")) {
            //戻る
            forward = __doBack(map, usr041Form, req, res, con);
        } else if (cmd.equals("usr041commit")) {
            //登録
            forward = __doEdit(map, usr041Form, req, res, con);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, usr041Form, req, res, con);
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
    private ActionForward __doInit(ActionMapping map, Usr041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //現在の件数を取得し画面にセットする。
        con.setAutoCommit(true);
        BaseUserModel umodel = getSessionUserModel(req);
        Usr041Biz biz = new Usr041Biz(getRequestModel(req));

        Usr041ParamModel paramMdl = new Usr041ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, umodel);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定
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
    private ActionForward __doEdit(ActionMapping map, Usr041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //
        //現在の件数を取得する。
        BaseUserModel umodel = getSessionUserModel(req);

        ActionForward forward = null;
        boolean commit = false;

        try {
            if (!isTokenValid(req, true)) {
                log__.info("2重投稿");
                return getSubmitErrorPage(map, req);
            }
            // 入力チェック
            ActionErrors errors = form.validateCheck(req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
            //更新処理
            Usr041Biz biz = new Usr041Biz(getRequestModel(req));
            Usr041ParamModel paramMdl = new Usr041ParamModel();
            paramMdl.setParam(form);
            biz.updateDspCount(paramMdl, con, umodel);
            biz.setSortPriConfig(paramMdl, umodel, con);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            /** メッセージ 変更 **/
            String change = gsMsg.getMessage(req, "cmn.change");

            //ログ出力処理
            String detail = "[" + gsMsg.getMessage(req, "cmn.sortby.members") + "]\r\n"
            + gsMsg.getMessage(req, "cmn.first.key") + ":";
            if (paramMdl.getUsr041SortKey1() == GSConstUser.USER_SORT_SNO) {
                detail += gsMsg.getMessage(req, "cmn.employee.staff.number");
            } else if (paramMdl.getUsr041SortKey1() == GSConstUser.USER_SORT_NAME) {
                detail += gsMsg.getMessage(req, "cmn.name");
            } else if (paramMdl.getUsr041SortKey1() == GSConstUser.USER_SORT_YKSK) {
                detail += gsMsg.getMessage(req, "cmn.post");
            } else if (paramMdl.getUsr041SortKey1() == GSConstUser.USER_SORT_BDATE) {
                detail += gsMsg.getMessage(req, "cmn.birthday");
            } else if (paramMdl.getUsr041SortKey1() == GSConstUser.USER_SORT_SORTKEY1) {
                detail += gsMsg.getMessage(req, "cmn.sortkey") + "1";
            } else if (paramMdl.getUsr041SortKey1() == GSConstUser.USER_SORT_SORTKEY2) {
                detail += gsMsg.getMessage(req, "cmn.sortkey") + "2";
            }
            if (paramMdl.getUsr041SortOrder1() == 0) {
                detail += " " + gsMsg.getMessage(req, "cmn.order.asc");
            } else {
                detail += " " + gsMsg.getMessage(req, "cmn.order.desc");
            }
            detail += "\r\n" + gsMsg.getMessage(req, "cmn.second.key") + ":";
            if (paramMdl.getUsr041SortKey2() == GSConstUser.USER_SORT_SNO) {
                detail += gsMsg.getMessage(req, "cmn.employee.staff.number");
            } else if (paramMdl.getUsr041SortKey2() == GSConstUser.USER_SORT_NAME) {
                detail += gsMsg.getMessage(req, "cmn.name");
            } else if (paramMdl.getUsr041SortKey2() == GSConstUser.USER_SORT_YKSK) {
                detail += gsMsg.getMessage(req, "cmn.post");
            } else if (paramMdl.getUsr041SortKey2() == GSConstUser.USER_SORT_BDATE) {
                detail += gsMsg.getMessage(req, "cmn.birthday");
            } else if (paramMdl.getUsr041SortKey2() == GSConstUser.USER_SORT_SORTKEY1) {
                detail += gsMsg.getMessage(req, "cmn.sortkey") + "1";
            } else if (paramMdl.getUsr041SortKey2() == GSConstUser.USER_SORT_SORTKEY2) {
                detail += gsMsg.getMessage(req, "cmn.sortkey") + "2";
            }
            if (paramMdl.getUsr041SortOrder2() == 0) {
                detail += " " + gsMsg.getMessage(req, "cmn.order.asc");
            } else {
                detail += " " + gsMsg.getMessage(req, "cmn.order.desc");
            }
            detail += "\r\n[" + gsMsg.getMessage(req, "cmn.number.display") + "]";
            detail += String.valueOf(paramMdl.getUsr041DspCnt());

            UsrCommonBiz usrBiz = new UsrCommonBiz(con, getRequestModel(req));
            usrBiz.outPutLog(
                    change, GSConstLog.LEVEL_INFO, detail, map.getType());

            //完了画面
            forward = __doCompDsp(map, form, req, res);
            commit = true;
        } catch (SQLException e) {
            log__.error("ユーザ情報個人設定更新失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        return forward;
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
     * @throws Exception 実行例外
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Usr041Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) throws Exception {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("back");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textSetteiComp = gsMsg.getMessage(req, "user.src.33");

        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object", textSetteiComp));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("usr040cmdMode", form.getUsr040cmdMode());
        cmn999Form.addHiddenParam("usr040orderKey", form.getUsr040orderKey());
        cmn999Form.addHiddenParam("usr040sortKey", form.getUsr040sortKey());
        cmn999Form.addHiddenParam("usr040orderKey2", form.getUsr040orderKey2());
        cmn999Form.addHiddenParam("usr040sortKey2", form.getUsr040sortKey2());
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

        cmn999Form.addHiddenParam("usr040pageNum1", form.getUsr040pageNum1());
        cmn999Form.addHiddenParam("usr040pageNum2", form.getUsr040pageNum2());
        if (form.getUsr040labSid() != null) {
            for (int i = 0; i < form.getUsr040labSid().length; i++) {
                cmn999Form.addHiddenParam("usr040labSid", form.getUsr040labSid()[i]);
            }
        }

        cmn999Form.addHiddenParam("usr040SearchKanaSave", form.getUsr040SearchKanaSave());
        cmn999Form.addHiddenParam("selectgsidSave", form.getSelectgsidSave());
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

        cmn999Form.addHiddenParam("usr040CategorySetInitFlg", form.getUsr040CategorySetInitFlg());
        if (form.getUsr040CategoryOpenFlg() != null
                && form.getUsr040CategoryOpenFlg().length != 0) {
            for (int i = 0; i < form.getUsr040CategoryOpenFlg().length; i++) {
                cmn999Form.addHiddenParam(
                        "usr040CategoryOpenFlg", form.getUsr040CategoryOpenFlg()[i]);
            }
        }

        cmn999Form.addHiddenParam("usr040GrpSearchGId", form.getUsr040GrpSearchGId());
        cmn999Form.addHiddenParam("usr040GrpSearchGName", form.getUsr040GrpSearchGName());
        cmn999Form.addHiddenParam("usr040GrpSearchGIdSave", form.getUsr040GrpSearchGIdSave());
        cmn999Form.addHiddenParam("usr040GrpSearchGNameSave", form.getUsr040GrpSearchGNameSave());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 戻る処理
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
    private ActionForward __doBack(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        return map.findForward("back");
    }
}