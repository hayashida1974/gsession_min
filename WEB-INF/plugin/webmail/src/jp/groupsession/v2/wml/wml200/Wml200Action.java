package jp.groupsession.v2.wml.wml200;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailAdminAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

/**
 * <br>[機  能] WEBメール 管理者設定 ラベル管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml200Action extends AbstractWebmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml200Action.class);

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
        Wml200Form thisForm = (Wml200Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("configLabel")) {
            //追加ボタン、修正ボタンクリック
            forward = __doConfig(map, thisForm, req, res, con);

        } else if (cmd.equals("wml030Back")) {
            //戻るボタンクリック
            forward = map.findForward("accountManager");

        } else if (cmd.equals("deleteLabel")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteLabelComp")) {
            //削除確認画面からの遷移
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("upLabelData")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConstWebmail.SORT_UP);

        } else if (cmd.equals("downLabelData")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConstWebmail.SORT_DOWN);

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
    private ActionForward __doInit(ActionMapping map, Wml200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        Wml200ParamModel paramMdl = new Wml200ParamModel();
        paramMdl.setParam(form);
        Wml200Biz biz = new Wml200Biz();
        biz.setInitData(con, paramMdl, userSid, getRequestModel(req));
        paramMdl.setFormData(form);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 追加ボタン、修正ボタンクリック時処理
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
    public ActionForward __doConfig(ActionMapping map, Wml200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //アカウント選択チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("confLabel");
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Wml200Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Wml200Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログ用 ラベル名退避
        WmlLabelDao wlDao = new WmlLabelDao(con);
        WmlLabelModel delLabelMdl = wlDao.select(form.getWmlEditLabelId());
        String delLabelName = delLabelMdl.getWlbName();

        //ラベルを削除する
        Wml200ParamModel paramMdl = new Wml200ParamModel();
        paramMdl.setParam(form);
        Wml200Biz biz = new Wml200Biz();
        biz.deleteLabel(con, paramMdl);
        paramMdl.setFormData(form);

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        // アカウント名
        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountModel accountMdl = accountDao.select(paramMdl.getWmlAccountSid());
        String accountName = accountMdl.getWacName();
        // 出力メッセージ
        String msg = "[" + gsMsg.getMessage("wml.96") + "] " + accountName;
        msg += "\r\n[" + gsMsg.getMessage("wml.74") + "]" + delLabelName;
        wmlBiz.outPutLog(map, getRequestModel(req), con,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO,
                msg);

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Wml200Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forward = map.findForward("mine");
        cmn999Form.setUrlCancel(forward.getPath());

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteLabelComp");

        //メッセージ
//        MessageResources msgRes = getResources(req);
//        Adr090Biz biz = new Adr090Biz();
//        String msg = biz.getDeletePosMsg(con, form.getAdr080EditAtiSid(), msgRes);
        String msg = getInterMessage(req, "wml.164");
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("wml200SortRadio", form.getWml200SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Wml200Form form,
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
        Wml200ParamModel paramMdl = new Wml200ParamModel();
        paramMdl.setParam(form);
        
        SortResult<WmlLabelModel> result = null;
        
        try {
            Wml200Biz biz = new Wml200Biz();
            result = biz.updateSort(con, paramMdl, changeKbn);
            paramMdl.setFormData(form);
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
        if (result != null) {
            //ログ出力
            WmlBiz wmlBiz = new WmlBiz();
            // アカウント名
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountMdl = accountDao.select(paramMdl.getWmlAccountSid());
            String accountName = accountMdl.getWacName();
            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            // ラベル名
            String labelName = result.getMdl().getWlbName();
            // ソート順変更
            String sort = null;
            // 順番が変わらない場合はログを出力しない
            if (result.getBeforeSort() == result.getAfterSort()) {
                return __doInit(map, form, req, res, con);
            } else if (changeKbn == GSConstWebmail.SORT_UP) {
                sort = gsMsg.getMessage("cmn.up");
            } else if (changeKbn == GSConstWebmail.SORT_DOWN) {
                sort = gsMsg.getMessage("cmn.down");
            }
            // 出力メッセージ
            String message = "[" + gsMsg.getMessage("wml.96") + "] " + accountName;
            message += "\r\n[" + gsMsg.getMessage("cmn.label.name") + "] " + labelName;
            message += "\r\n[" + gsMsg.getMessage("change.sort.order") + "] " + sort;
            wmlBiz.outPutLog(map, getRequestModel(req), con,
                    getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, message);
        }
        
        return __doInit(map, form, req, res, con);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Wml200Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", getInterMessage(req, "cmn.label")));

        form.setHiddenParam(cmn999Form);

        cmn999Form.addHiddenParam("wml200SortRadio", form.getWml200SortRadio());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());

        cmn999Form.addHiddenParam("wmlViewAccount", form.getWmlViewAccount());
        cmn999Form.addHiddenParam("wmlCmdMode", form.getWmlCmdMode());
        cmn999Form.addHiddenParam("wmlAccountMode", form.getWmlAccountMode());
        cmn999Form.addHiddenParam("wmlAccountSid", form.getWmlAccountSid());

        cmn999Form.addHiddenParam("wml010viewDirectory", form.getWml010viewDirectory());
        cmn999Form.addHiddenParam("wml010viewDirectoryType", form.getWml010viewDirectoryType());
        cmn999Form.addHiddenParam("wml010viewMailNum", form.getWml010viewMailNum());
        cmn999Form.addHiddenParam("wml010pageTop", form.getWml010pageTop());
        cmn999Form.addHiddenParam("wml010pageBottom", form.getWml010pageBottom());
        cmn999Form.addHiddenParam("wml010searchFrom", form.getWml010searchFrom());
        cmn999Form.addHiddenParam("wml010searchTo", form.getWml010searchTo());
        cmn999Form.addHiddenParam("wml010searchToKbnTo", form.getWml010searchToKbnTo());
        cmn999Form.addHiddenParam("wml010searchToKbnCc", form.getWml010searchToKbnCc());
        cmn999Form.addHiddenParam("wml010searchToKbnBcc", form.getWml010searchToKbnBcc());
        cmn999Form.addHiddenParam("wml010searchDateType", form.getWml010searchDateType());
        cmn999Form.addHiddenParam("wml010searchDateYearFr", form.getWml010searchDateYearFr());
        cmn999Form.addHiddenParam("wml010searchDateMonthFr", form.getWml010searchDateMonthFr());
        cmn999Form.addHiddenParam("wml010searchDateDayFr", form.getWml010searchDateDayFr());

        cmn999Form.addHiddenParam("wml010searchDateYearTo", form.getWml010searchDateYearTo());
        cmn999Form.addHiddenParam("wml010searchDateMonthTo", form.getWml010searchDateMonthTo());
        cmn999Form.addHiddenParam("wml010searchDateDayTo", form.getWml010searchDateDayTo());
        cmn999Form.addHiddenParam("wml010searchTempFile", form.getWml010searchTempFile());
        cmn999Form.addHiddenParam("wml010searchKeywordKbn", form.getWml010searchKeywordKbn());
        cmn999Form.addHiddenParam("wml010searchKeyword", form.getWml010searchKeyword());
        cmn999Form.addHiddenParam("wml010svSearchFrom", form.getWml010svSearchFrom());
        cmn999Form.addHiddenParam("wml010svSearchTo", form.getWml010svSearchTo());
        cmn999Form.addHiddenParam("wml010svSearchToKbnTo", form.getWml010svSearchToKbnTo());
        cmn999Form.addHiddenParam("wml010svSearchToKbnCc", form.getWml010svSearchToKbnCc());
        cmn999Form.addHiddenParam("wml010svSearchToKbnBcc", form.getWml010svSearchToKbnBcc());
        cmn999Form.addHiddenParam("wml010svSearchDateType", form.getWml010svSearchDateType());
        cmn999Form.addHiddenParam("wml010svSearchDateYearFr", form.getWml010svSearchDateYearFr());

        cmn999Form.addHiddenParam("wml010svSearchDateMonthFr", form.getWml010svSearchDateMonthFr());
        cmn999Form.addHiddenParam("wml010svSearchDateDayFr", form.getWml010svSearchDateDayFr());
        cmn999Form.addHiddenParam("wml010svSearchDateYearTo", form.getWml010svSearchDateYearTo());
        cmn999Form.addHiddenParam("wml010svSearchDateMonthTo", form.getWml010svSearchDateMonthTo());
        cmn999Form.addHiddenParam("wml010svSearchDateDayTo", form.getWml010svSearchDateDayTo());
        cmn999Form.addHiddenParam("wml010svSearchTempFile", form.getWml010svSearchTempFile());
        cmn999Form.addHiddenParam("wml010svSearchKeywordKbn", form.getWml010svSearchKeywordKbn());
        cmn999Form.addHiddenParam("wml010svSearchKeyword", form.getWml010svSearchKeyword());
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
