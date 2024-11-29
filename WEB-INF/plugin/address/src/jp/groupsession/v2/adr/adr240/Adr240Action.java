package jp.groupsession.v2.adr.adr240;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.AbstractAddressSubAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] アドレス帳 会社選択画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr240Action extends AbstractAddressSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr240Action.class);

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
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Adr240Form thisForm = (Adr240Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setAdr240page(thisForm.getAdr240page() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setAdr240page(thisForm.getAdr240page() + 1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("changePageTop")) {
            //上ページコンボ変更
            thisForm.setAdr240page(thisForm.getAdr240pageTop());
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("changePageBottom")) {
            //下ページコンボ変更
            thisForm.setAdr240page(thisForm.getAdr240pageBottom());
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("search")) {
                log__.debug("検索ボタンクリック");
                forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("changeTab")) {
            //タブ変更
            __resetSvData(thisForm);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("proNoSelTanto")) {
            //プロジェクト担当者登録エラー

            //日本語:担当者
            GsMessage gsMsg = new GsMessage();
            String tantousya = gsMsg.getMessage(req, "cmn.staff");

            ActionErrors errors = new ActionErrors();
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        tantousya);
            errors.add("error.select.required.text", msg);
            addErrors(req, errors);
            thisForm.setAdr240ProAddErrFlg(1);
            forward = __doInit(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }


        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Adr240Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);
        BaseUserModel bumdl = getSessionUserModel(req);
        Adr240Biz biz = new Adr240Biz(con, getRequestModel(req));

        Adr240ParamModel paramMdl = new Adr240ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, bumdl.getUsrsid());
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
      * <br>[機  能] 検索ボタンクリック時処理
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map マップ
      * @param form フォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward フォワード
      * @throws Exception 実行時例外
      */
    private ActionForward __doSearch(ActionMapping map,
                                    Adr240Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {


        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {

            //企業コード
            form.setAdr240svCode(form.getAdr240code());
            //会社名
            form.setAdr240svCoName(form.getAdr240coName());
            //会社名カナ
            form.setAdr240svCoNameKn(form.getAdr240coNameKn());
            //支店・営業所名
            form.setAdr240svCoBaseName(form.getAdr240coBaseName());
            //業種
            form.setAdr240svAtiSid(form.getAdr240atiSid());
            //都道府県
            form.setAdr240svTdfk(form.getAdr240tdfk());
            //備考
            form.setAdr240svBiko(form.getAdr240biko());

            //ページ
            form.setAdr240page(1);
            form.setAdr240searchFlg(1);

        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 保持データリセット
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param form フォーム
    * @throws Exception 実行時例外
    */
    private void __resetSvData(Adr240Form form) throws Exception {
        form.setAdr240page(1);
        form.setAdr240searchFlg(1);
        form.setAdr240svCode(null);
        form.setAdr240svCoName(null);
        form.setAdr240svCoNameKn(null);
        form.setAdr240svCoBaseName(null);
        form.setAdr240svAtiSid(0);
        form.setAdr240svTdfk(0);
        form.setAdr240svBiko(null);
    }
}