package jp.groupsession.v2.sml.sml290;

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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.AbstractSmailAdminAction;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;

/**
 * <br>[機  能] ショートメール 管理者設定 ラベル管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml290Action extends AbstractSmailAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml290Action.class);

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
        Sml290Form thisForm = (Sml290Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("configLabel")) {
            //追加ボタン、修正ボタンクリック
            forward = __doConfig(map, thisForm, req, res, con);

        } else if (cmd.equals("sml240Back")) {
            //戻るボタンクリック
            forward = map.findForward("smlAccountManager");

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
    private ActionForward __doInit(ActionMapping map, Sml290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        Sml290ParamModel paramMdl = new Sml290ParamModel();
        paramMdl.setParam(form);
        Sml290Biz biz = new Sml290Biz();
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
    public ActionForward __doConfig(ActionMapping map, Sml290Form form,
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
        Sml290Form form,
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
        Sml290Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ラベルを削除する
        Sml290ParamModel paramMdl = new Sml290ParamModel();
        paramMdl.setParam(form);
        Sml290Biz biz = new Sml290Biz();
        biz.deleteLabel(con, paramMdl);
        paramMdl.setFormData(form);

        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlAccountSid());

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
        smlBiz.outPutLog(map, getRequestModel(req),
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO, "アカウント:"
         + sacMdl.getSacName()
         + "\n");


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
        Sml290Form form,
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
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("smlAccountMode", form.getSmlAccountMode());
        cmn999Form.addHiddenParam("dspCount", form.getDspCount());
        cmn999Form.addHiddenParam("smlCmdMode", form.getSmlCmdMode());

        cmn999Form.addHiddenParam("sml240keyword", form.getSml240keyword());
        cmn999Form.addHiddenParam("sml240group", form.getSml240group());
        cmn999Form.addHiddenParam("sml240user", form.getSml240user());
        cmn999Form.addHiddenParam("sml240svKeyword", form.getSml240svKeyword());
        cmn999Form.addHiddenParam("sml240svGroup", form.getSml240svGroup());
        cmn999Form.addHiddenParam("sml240svUser", form.getSml240svUser());
        cmn999Form.addHiddenParam("sml240sortKey", form.getSml240sortKey());
        cmn999Form.addHiddenParam("sml240order", form.getSml240order());
        cmn999Form.addHiddenParam("sml240searchFlg", form.getSml240searchFlg());
        cmn999Form.addHiddenParam("sml290SortRadio", form.getSml290SortRadio());

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
        Sml290Form form,
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

            Sml290ParamModel paramMdl = new Sml290ParamModel();
            paramMdl.setParam(form);
            Sml290Biz biz = new Sml290Biz();
            biz.updateSort(con, paramMdl, changeKbn);
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
        //ログ出力処理
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(form.getSmlAccountSid());

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
        smlBiz.outPutLog(map, getRequestModel(req),
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, "アカウント:"
         + sacMdl.getSacName()
         + "\n" + getInterMessage(req, "change.sort.order"));


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
        Sml290Form form,
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
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("smlCmdMode", form.getSmlCmdMode());
        cmn999Form.addHiddenParam("smlAccountMode", form.getSmlAccountMode());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
