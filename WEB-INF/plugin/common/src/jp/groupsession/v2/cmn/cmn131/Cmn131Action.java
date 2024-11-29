package jp.groupsession.v2.cmn.cmn131;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 個人設定 マイグループ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn131Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn131Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Cmn131");
        ActionForward forward = null;

        Cmn131Form thisForm = (Cmn131Form) form;

        //編集権限チェック
        forward = __doCheck(map, req, thisForm, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backToList")) {
            log__.debug("戻る");
            forward = map.findForward("backToList");

        } else if (cmd.equals("ok")) {
            log__.debug("OK");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("changeGroup")) {
            log__.debug("コンボ変更");
            forward = __doDsp(map, thisForm, req, res, con);

        } else if (cmd.equals("backToInput")) {
            log__.debug("確認画面から戻る");
            forward = __doDsp(map, thisForm, req, res, con);

        } else if (cmd.equals("groupDelete")) {
            log__.debug("削除");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cmn131");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
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
    private ActionForward __doInit(
        ActionMapping map,
        Cmn131Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //編集の場合、グループ情報をセットする
        con.setAutoCommit(true);
        Cmn131Biz biz = new Cmn131Biz(new GsMessage(req));
        Cmn131ParamModel paramModel = new Cmn131ParamModel();
        paramModel.setParam(form);
        biz.setGroupInfo(paramModel, con);
        paramModel.setFormData(form);
        con.setAutoCommit(false);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画面再表示を行う
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
    private ActionForward __doDsp(
        ActionMapping map,
        Cmn131Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ログインユーザSIDを取得
        int userSid = -1;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Cmn131Biz biz = new Cmn131Biz(new GsMessage(req));
        Cmn131ParamModel paramModel = new Cmn131ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, con, userSid);
        paramModel.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理
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
    private ActionForward __doOk(
        ActionMapping map,
        Cmn131Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        log__.debug("入力エラーなし、登録を行う");

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("ok");
    }

    /**
     * <br>[機  能] 削除処理を行う(確認画面表示まで)
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
        Cmn131Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //トランザクショントークン設定
        saveToken(req);

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //削除するマイグループ名を取得する
        con.setAutoCommit(true);
        Cmn131Biz biz = new Cmn131Biz(new GsMessage(req));
        Cmn131ParamModel paramModel = new Cmn131ParamModel();
        paramModel.setParam(form);
        String deleteGroup = biz.getDeleteGroupName(paramModel, con);
        paramModel.setFormData(form);
        con.setAutoCommit(false);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, deleteGroup);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param deleteGroup 削除するマイグループ名
     * @return ActionForward
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Cmn131Form form,
        HttpServletRequest req,
        String deleteGroup) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("init");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("init");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        GsMessage gsMsg = new GsMessage();
        /** メッセージ マイグループ **/
        String myGroup = gsMsg.getMessage(req, "cmn.mygroup");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kakunin.list", myGroup,
                        StringUtilHtml.transToHTmlPlusAmparsant(deleteGroup)));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("cmn131name", form.getCmn131name());
        cmn999Form.addHiddenParam("cmn131memo", form.getCmn131memo());
        cmn999Form.addHiddenParam("cmn131groupSid", form.getCmn131groupSid());
        cmn999Form.addHiddenParam("cmn131addUserSid", form.getCmn131addUserSid());
        cmn999Form.addHiddenParam("cmn131userSid", form.getCmn131userSid());
        cmn999Form.addHiddenParam("cmn131selectUserSid", form.getCmn131selectUserSid());
        cmn999Form.addHiddenParam("cmn130cmdMode", form.getCmn130cmdMode());
        cmn999Form.addHiddenParam("cmn130selectGroupSid", form.getCmn130selectGroupSid());
        cmn999Form.addHiddenParam("cmn131initFlg", form.getCmn131initFlg());
        cmn999Form.addHiddenParam("cmn131refUserSid", form.getCmn131refUserSid());
        cmn999Form.addHiddenParam("cmn131refSelectUserSid", form.getCmn131refSelectUserSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
     * @return ActionForward
     */
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Cmn131Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {


        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログ用退避
        Cmn131Biz biz = new Cmn131Biz(new GsMessage(req));
        String[] delGroupSid = {form.getCmn130selectGroupSid()};
        String logMsg = biz.getLogMessage(delGroupSid, con);

        //選択されたマイグループを削除する
        Cmn131ParamModel paramModel = new Cmn131ParamModel();
        paramModel.setParam(form);
        biz.deleteGroup(paramModel, con);
        paramModel.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        String delete = gsMsg.getMessage(req, "cmn.delete");

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        String msg = "[" + gsMsg.getMessage("cmn.cmn130.1") + "]";
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
               delete, GSConstLog.LEVEL_INFO, msg + logMsg);

        //削除完了画面を表示
        return __setKanryoDsp(map, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("backToList");
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String textMyGroup = gsMsg.getMessage(req, "cmn.mygroup");

        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", textMyGroup));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 編集権限チェックを行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param form Cmn131Form
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException
     */
    private ActionForward __doCheck(
        ActionMapping map,
        HttpServletRequest req,
        Cmn131Form form,
        Connection con) throws SQLException {

        //ログインユーザSIDを取得
        int userSid = -1;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Cmn131Biz biz = new Cmn131Biz(new GsMessage(req));
        Cmn131ParamModel paramModel = new Cmn131ParamModel();
        paramModel.setParam(form);
        boolean editKengen = false;
        editKengen = biz.checkEditKengen(paramModel, con, userSid);
        if (!editKengen) {
            return __setErrorPage(map, req);
        }
        return null;
    }

    /**
     * [機  能] 警告画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setErrorPage(
        ActionMapping map,
        HttpServletRequest req) {
        
        MessageResources msgRes = getResources(req);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        ActionForward urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.mygroup");
        cmn999Form.setMessage(msgRes.getMessage("search.data.notfound", msg));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
