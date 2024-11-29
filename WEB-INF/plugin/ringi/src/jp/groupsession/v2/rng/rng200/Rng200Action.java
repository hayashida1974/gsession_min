package jp.groupsession.v2.rng.rng200;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.rng.AbstractRingiAdminAction;

/**
 * <br>[機  能] 稟議 管理者設定 申請フォーマット画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng200Action extends AbstractRingiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng200Action.class);

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
        Rng200Form thisForm = (Rng200Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("Rng200Action CMD==>" + cmd);
        if (cmd.equals("backMenu")) {
            log__.debug("戻るボタン押下");
            return map.findForward("rngAdmMenu");

        } else if (cmd.equals("add")) {
            log__.debug("追加ボタン押下");
            return map.findForward("add");

        } else if (cmd.equals("edit")) {
            log__.debug("編集ボタン押下");
            return map.findForward("edit");

        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisForm, req, res, con);
        }
        /*
         * デフォルト設定は申請ID設定にて操作に変更
         * 検索機能は現在不要
        //OKボタン押下
        if (cmd.equals("ok")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("search")) {
            //log__.debug("検索ボタン押下");
            //forward = __doSearch(map, thisForm, req, res, con);

        }*/

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
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rng200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Rng200Biz biz = new Rng200Biz();
        Rng200ParamModel paramMdl = new Rng200ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索表示を行う
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
    /*private ActionForward __doSearch(ActionMapping map,
                                    Rng200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Rng200Biz biz = new Rng200Biz();
        Rng200ParamModel paramMdl = new Rng200ParamModel();
        paramMdl.setParam(form);
        biz.searchData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }*/

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward 画面遷移先
     */
    /*private ActionForward __doOk(ActionMapping map,
                                  Rng200Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con) throws Exception {

        // トランザクショントークン設定
        saveToken(req);

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req));
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            ActionForward forward = __doInit(map, form, req, res, con);
            return forward;
        }

        log__.debug("入力エラーなし、登録を行う");

        Rng200Biz biz = new Rng200Biz();
        Rng200ParamModel paramMdl = new Rng200ParamModel();
        paramMdl.setParam(form);
        biz.setOkData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(true);

        biz.entryAdmConf(con, paramMdl, getSessionUserSid(req));
        con.setAutoCommit(false);

        //完了画面を表示
        return __doCompDsp(map, form, req, res);
    }*/

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
     */
    /*private ActionForward __doCompDsp(ActionMapping map,
                                       Rng200Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String textBaseConf = gsMsg.getMessage(req, "cmn.preferences");

        urlForward = map.findForward("ok");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("touroku.kanryo.object", textBaseConf));

        //hiddenパラメータ
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }*/
}
