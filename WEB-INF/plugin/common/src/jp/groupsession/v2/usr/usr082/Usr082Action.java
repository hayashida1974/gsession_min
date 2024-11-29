package jp.groupsession.v2.usr.usr082;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.AbstractUsrAdminAction;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

/**
 * <br>[機  能] ユーザ情報 管理者設定 デフォルト表示順設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr082Action extends AbstractUsrAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr082Action.class);

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
        Usr082Form schForm = (Usr082Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("usr082kakunin")) {
            //確認
            forward = __doKakunin(map, schForm, req, res, con);
        } else if (cmd.equals("usr082commit")) {
            //登録
            forward = __doCommit(map, schForm, req, res, con);
        } else if (cmd.equals("usr082back")) {
            //戻るボタンクリック
            forward = __doBack(map, schForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, schForm, req, res, con);
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
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Usr082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, Exception {

        log__.debug("確認処理");
        //QRコードの値が不正ではないかチェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @throws Exception 実行時例外
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Usr082Form form) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        GsMessage gsMsg = new GsMessage();
        String textDefaultDspSetting = gsMsg.getMessage(req, "cmn.display.settings");
        String mkey1 = textDefaultDspSetting;
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("usr082initKbn", form.getUsr082initKbn());
        cmn999Form.addHiddenParam("usr082DefoDspKbn", form.getUsr082DefoDspKbn());
        cmn999Form.addHiddenParam("usr082AdSortKey1", form.getUsr082AdSortKey1());
        cmn999Form.addHiddenParam("usr082AdSortOrder1", form.getUsr082AdSortOrder1());
        cmn999Form.addHiddenParam("usr082AdSortKey2", form.getUsr082AdSortKey2());
        cmn999Form.addHiddenParam("usr082AdSortOrder2", form.getUsr082AdSortOrder2());
        cmn999Form.addHiddenParam("usr082QrDisp", form.getUsr082QrDisp());
        cmn999Form.addHiddenParam("usr040DspFlg", form.getUsr040DspFlg());

        __subPageAddHiddenParams(cmn999Form, form);

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
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Usr082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, Exception {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        BaseUserModel umodel = getSessionUserModel(req);
        Usr082Biz biz = new Usr082Biz(getRequestModel(req));

        Usr082ParamModel paramMdl = new Usr082ParamModel();
        paramMdl.setParam(form);
        biz.setSortAdmConfig(paramMdl, umodel, con);
        biz.qrSetting(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        /** メッセージ 変更 **/
        String change = gsMsg.getMessage(req, "cmn.change");

        String message = "[" + gsMsg.getMessage(req, "user.usr082.2") + "]";
        if (paramMdl.getUsr082DefoDspKbn() == GSConstUser.DEFO_DSP_ADM) {
            message += gsMsg.getMessage(req, "cmn.set.the.admin") + "\r\n";
        } else {
            message += gsMsg.getMessage(req, "cmn.set.eachuser") + "\r\n";
        }
        message += gsMsg.getMessage(req, "cmn.first.key") + ":";
        if (paramMdl.getUsr082AdSortKey1() == GSConstUser.USER_SORT_SNO) {
            message += gsMsg.getMessage(req, "cmn.employee.staff.number");
        } else if (paramMdl.getUsr082AdSortKey1() == GSConstUser.USER_SORT_NAME) {
            message += gsMsg.getMessage(req, "cmn.name");
        } else if (paramMdl.getUsr082AdSortKey1() == GSConstUser.USER_SORT_YKSK) {
            message += gsMsg.getMessage(req, "cmn.post");
        } else if (paramMdl.getUsr082AdSortKey1() == GSConstUser.USER_SORT_BDATE) {
            message += gsMsg.getMessage(req, "cmn.birthday");
        } else if (paramMdl.getUsr082AdSortKey1() == GSConstUser.USER_SORT_SORTKEY1) {
            message += gsMsg.getMessage(req, "cmn.sortkey") + "1";
        } else if (paramMdl.getUsr082AdSortKey1() == GSConstUser.USER_SORT_SORTKEY2) {
            message += gsMsg.getMessage(req, "cmn.sortkey") + "2";
        }
        if (paramMdl.getUsr082AdSortOrder1() == 0) {
            message += " " + gsMsg.getMessage(req, "cmn.order.asc") + "\r\n";
        } else {
            message += " " + gsMsg.getMessage(req, "cmn.order.desc") + "\r\n";
        }
        message += gsMsg.getMessage(req, "cmn.second.key") + ":";
        if (paramMdl.getUsr082AdSortKey2() == GSConstUser.USER_SORT_SNO) {
            message += gsMsg.getMessage(req, "cmn.employee.staff.number");
        } else if (paramMdl.getUsr082AdSortKey2() == GSConstUser.USER_SORT_NAME) {
            message += gsMsg.getMessage(req, "cmn.name");
        } else if (paramMdl.getUsr082AdSortKey2() == GSConstUser.USER_SORT_YKSK) {
            message += gsMsg.getMessage(req, "cmn.post");
        } else if (paramMdl.getUsr082AdSortKey2() == GSConstUser.USER_SORT_BDATE) {
            message += gsMsg.getMessage(req, "cmn.birthday");
        } else if (paramMdl.getUsr082AdSortKey2() == GSConstUser.USER_SORT_SORTKEY1) {
            message += gsMsg.getMessage(req, "cmn.sortkey") + "1";
        } else if (paramMdl.getUsr082AdSortKey2() == GSConstUser.USER_SORT_SORTKEY2) {
            message += gsMsg.getMessage(req, "cmn.sortkey") + "2";
        }
        if (paramMdl.getUsr082AdSortOrder2() == 0) {
            message += " " + gsMsg.getMessage(req, "cmn.order.asc") + "\r\n";
        } else {
            message += " " + gsMsg.getMessage(req, "cmn.order.desc") + "\r\n";
        }
        message += "[" + gsMsg.getMessage(req, "cmn.cmn100.4")
        + gsMsg.getMessage(req, "cmn.display.settings") + "]";

        if (paramMdl.getUsr082QrDisp() == GSConstUser.QR_CODE_DISP) {
            message += gsMsg.getMessage(req, "cmn.display.ok");
        } else {
            message += gsMsg.getMessage(req, "cmn.dont.show");
        }

        UsrCommonBiz usrBiz = new UsrCommonBiz(con, getRequestModel(req));
        usrBiz.outPutLog(
                change, GSConstLog.LEVEL_INFO, message, map.getType());

        //共通メッセージ画面(OK キャンセル)を表示
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
     * @throws Exception 実行時例外
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Usr082Form form) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ktool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        String textDefaultDspSetting = gsMsg.getMessage(req, "cmn.display.settings");
        String key1 = textDefaultDspSetting;
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        __subPageAddHiddenParams(cmn999Form, form);

        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Usr082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        con.setAutoCommit(true);
        Usr082Biz biz = new Usr082Biz(getRequestModel(req));

        Usr082ParamModel paramMdl = new Usr082ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
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
    private ActionForward __doBack(ActionMapping map, Usr082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("back");
    }


    /**
     * <br>[機  能] サブ画面への引継ぎパラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     * @param form Usr081Form
     */
    private void __subPageAddHiddenParams(Cmn999Form cmn999Form, Usr082Form form) {
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

    }
}