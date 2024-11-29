package jp.groupsession.v2.sml.sml060;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmlSubAction;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール ひな形登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml060Action extends AbstractSmlSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml060Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return (form != null
                && ((Sml060Form) form).getSml050HinaKbn() == GSConstSmail.HINA_KBN_PRI);
    }

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

        log__.debug("START_SML060");

        ActionForward forward = null;
        Sml060Form smlform = (Sml060Form) form;

        smlform.setSmlViewUser(getRequestModel(req).getSmodel().getUsrsid());

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //戻るボタン押下
        if (cmd.equals("back_from_hina_add")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("backToHinaList");
        //OKボタン押下
        } else if (cmd.equals("add_kn")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, smlform, req, res, con);
        //削除ボタン押下
        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタン押下");
            forward = __doDeleteInfomation(map, smlform, req, res, con);
        //削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteOk")) {
            log__.debug("削除OK押下");
            forward = __doDeleteOk(map, smlform, req, res, con);
        //削除確認画面でキャンセルボタン押下
        } else if (cmd.equals("deleteCancel")) {
            log__.debug("削除キャンセル押下");
            forward = map.getInputForward();
        //初期表示
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, smlform, req, res, con);
        }

        log__.debug("END_SML060");
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Sml060Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //編集時はDBから既存データ取得
        int hinaSid = form.getSelectedHinaSid();

        log__.debug("編集モード");
        con.setAutoCommit(true);
        Sml060ParamModel paramMdl = new Sml060ParamModel();
        paramMdl.setParam(form);

        Sml060Biz biz = new Sml060Biz();
        biz.setInitData(hinaSid, paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

   /**
    * <br>[機  能] 削除ボタン押下処理
    * <br>[解  説]
    * <br>[備  考] 削除確認画面の表示
    *
    * @param map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return ActionForward フォワード
    * @throws SQLException SQL実行時例外
    */
    private ActionForward __doDeleteInfomation(ActionMapping map,
                                                Sml060Form form,
                                                HttpServletRequest req,
                                                HttpServletResponse res,
                                                Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        //削除するひな形の名称を取得する
        Sml060Biz biz = new Sml060Biz();
        String hinaName =
            biz.getHinaName(form.getSelectedHinaSid(), con);

        //削除確認画面
        return __setDeleteDsp(map, req, form, hinaName);
    }

   /**
    * <br>[機  能] 削除確認画面でOKボタン押下
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return ActionForward フォワード
    * @throws SQLException SQL実行時例外
    */
    private ActionForward __doDeleteOk(ActionMapping map,
                                        Sml060Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            //ひな形データを物理削除する
            Sml060Biz biz = new Sml060Biz();
            biz.deleteHinaData(form.getSelectedHinaSid(), con);

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String msg = "";

            //ログ出力処理
            String logLevel = "";
            String hinaKbn = "";
            if (form.getSml050HinaKbn() == GSConstSmail.HINA_KBN_CMN) {
                logLevel = GSConstLog.LEVEL_INFO;
                msg = gsMsg.getMessage(req, "sml.138");
                hinaKbn = msg;
            } else {
                logLevel = GSConstLog.LEVEL_TRACE;
                msg = gsMsg.getMessage("sml.147");
                hinaKbn = msg;
            }
            String delete = gsMsg.getMessage("cmn.delete");
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl, delete, logLevel,
                    hinaKbn + "[name]" + form.getSml060HinaName());

            commitFlg = true;

            //完了画面設定
            return __setCompDsp(map, req, form, true);

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
    }

    /**
     * <br>[機  能] OKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doOk(ActionMapping map,
                                  Sml060Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        RequestModel reqMdl = getRequestModel(req);
        try {

            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //入力チェック
            ActionErrors errors = form.validateCheck060(reqMdl, getAppRootPath());
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                //トランザクショントークン設定
                this.saveToken(req);
                return map.getInputForward();
            }

            GsMessage gsMsg = new GsMessage();
            String msg = "";

            Sml060ParamModel paramMdl = new Sml060ParamModel();
            paramMdl.setParam(form);

            Sml060Biz biz = new Sml060Biz();
            //処理モード判定
            String opCode = "";
            int hinaSid = form.getSelectedHinaSid();
            if (hinaSid > 0) {
                //更新処理
                biz.updateHinaData(paramMdl, reqMdl, con);
                opCode = gsMsg.getMessage(req, "cmn.delete");
            } else {
                //追加処理
                MlCountMtController cntCon = getCountMtController(req);
                biz.insertHinaData(paramMdl, reqMdl, cntCon, con);
                opCode = gsMsg.getMessage(req, "cmn.entry");
            }
            paramMdl.setFormData(form);


            //ログ出力処理
            String logLevel = "";
            String hinaKbn = "";
            if (form.getSml050HinaKbn() == GSConstSmail.HINA_KBN_CMN) {
                logLevel = GSConstLog.LEVEL_INFO;
                msg = gsMsg.getMessage(req, "sml.138");
                hinaKbn = msg;
            } else {
                logLevel = GSConstLog.LEVEL_TRACE;
                msg = gsMsg.getMessage(req, "sml.147");
                hinaKbn = msg;
            }
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutLog(map, reqMdl, opCode, logLevel,
                    hinaKbn + "[name]" + form.getSml060HinaName());

            commitFlg = true;

            //完了画面設定
            return __setCompDsp(map, req, form, false);

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
    }

   /**
    * <br>[機  能] 削除確認画面
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param map マップ
    * @param req リクエスト
    * @param form フォーム
    * @param delHinaName 削除するひな形の名称
    * @return ActionForward フォワード
    */
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Sml060Form form,
                                          String delHinaName) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");

        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteCancel");

        GsMessage gsMsg = new GsMessage();
        String hinagata = gsMsg.getMessage(req, "sml.sml010.03");
        String ten = gsMsg.getMessage(req, "wml.231");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("sakujo.kakunin.list",
                               hinagata,
                               ten + StringUtilHtml.transToHTmlPlusAmparsant(delHinaName)));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("selectedHinaSid", form.getSelectedHinaSid());
        cmn999Form.addHiddenParam("sml050ProcMode", form.getSml050ProcMode());
        cmn999Form.addHiddenParam("sml050Sort_key", form.getSml050Sort_key());
        cmn999Form.addHiddenParam("sml050Order_key", form.getSml050Order_key());
        cmn999Form.addHiddenParam("sml050PageNum", form.getSml050PageNum());
        cmn999Form.addHiddenParam("sml060HinaName", form.getSml060HinaName());
        cmn999Form.addHiddenParam("sml060HinaTitle", form.getSml060HinaTitle());
        cmn999Form.addHiddenParam("sml060HinaMark", form.getSml060HinaMark());
        cmn999Form.addHiddenParam("sml060HinaBody", form.getSml060HinaBody());
        cmn999Form.addHiddenParam("sml050HinaKbn", form.getSml050HinaKbn());
        cmn999Form.addHiddenParam("sml050InitFlg", form.getSml050InitFlg());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param delFlg 削除処理フラグ true:削除処理 false:登録・更新処理
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Sml060Form form,
                                        boolean delFlg) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("backToHinaList");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = "";

        if (delFlg) {
            msg = "sakujo.kanryo.object";
        } else {
            if (form.getSelectedHinaSid() > 0) {
                msg = "hensyu.kanryo.object";
            } else {
                msg = "touroku.kanryo.object";
            }
        }
        GsMessage gsMsg = new GsMessage();
        String hinagata = gsMsg.getMessage(req, "sml.sml010.03");
        cmn999Form.setMessage(
                msgRes.getMessage(msg, hinagata));
            //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sml010ProcMode", form.getSml010ProcMode());
        cmn999Form.addHiddenParam("sml010Sort_key", form.getSml010Sort_key());
        cmn999Form.addHiddenParam("sml010Order_key", form.getSml010Order_key());
        cmn999Form.addHiddenParam("sml010PageNum", form.getSml010PageNum());
        cmn999Form.addHiddenParam("sml010SelectedSid", form.getSml010SelectedSid());
        cmn999Form.addHiddenParam("sml010DelSid", form.getSml010DelSid());
        cmn999Form.addHiddenParam("sml050ProcMode", form.getSml050ProcMode());
        cmn999Form.addHiddenParam("sml050Sort_key", form.getSml050Sort_key());
        cmn999Form.addHiddenParam("sml050Order_key", form.getSml050Order_key());
        cmn999Form.addHiddenParam("sml050PageNum", form.getSml050PageNum());
        cmn999Form.addHiddenParam("sml050HinaKbn", form.getSml050HinaKbn());
        cmn999Form.addHiddenParam("sml050InitFlg", form.getSml050InitFlg());
        cmn999Form.addHiddenParam("smlAccountSid", form.getSmlAccountSid());
        cmn999Form.addHiddenParam("smlViewAccount", form.getSmlViewAccount());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}