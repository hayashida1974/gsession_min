package jp.groupsession.v2.man.man020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.struts.AdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メイン 管理者設定 休日設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man020Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man020Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
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
        log__.debug("START_MAN020");
        ActionForward forward = null;
        Man020Form thisForm = (Man020Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("template")) {
            //休日テンプレートボタン押下
            forward = map.findForward("template");
        } else if (cmd.equals("backAdmTool")) {
            //戻るボタン押下
            forward = map.findForward("back");
        } else if (cmd.equals("add")) {
            //追加ボタン押下
            this.saveToken(req);
            forward = map.findForward("add");
        } else if (cmd.equals("delHol")) {
            //削除ボタン押下
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("edit")) {
            //編集ボタン押下
            this.saveToken(req);
            forward = map.findForward("edit");
        } else if (cmd.equals("beforeYear")) {
            //「左矢印」処理
            log__.debug("「左矢印」処理");
            forward = __doLeft(map, thisForm, req, res, con);
        } else if (cmd.equals("nextYear")) {
            //「右矢印」処理
            log__.debug("「右矢印」処理");
            forward = __doRight(map, thisForm, req, res, con);
        } else if (cmd.equals("csv")) {
            //CSVインポート押下
            log__.debug("「CSVインポート」処理");
            forward = map.findForward("csv");
        } else {
            //初期表示
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }
        log__.debug("END_MAN020");
        return forward;
    }

    /**
     * 初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Man020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        Man020ParamModel paramMdl = new Man020ParamModel();
        paramMdl.setParam(form);
        Man020Biz biz = new Man020Biz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

    }

    /**
     * 削除ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Man020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;

        //休日が選択されていない場合はエラー

        if (form.getHolDate() == null || form.getHolDate().length <= 0) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    getInterMessage(req, "main.holiday.setting"));
            errors.add("holDate.error.select.required.text", msg);

            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }

        //日付削除確認画面へ
        saveToken(req);
        forward = map.findForward("delete");
        return forward;
    }

    /**
     * <br>[機  能] 「左矢印」処理
     * <br>[解  説] ・選択した同時登録ユーザを同時登録ユーザ一覧から除外する
     * <br>         ・画面を再表示
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doLeft(ActionMapping map,
            Man020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {
        log__.debug("__doLeft start");

        //表示開始日を-1する
        String strDspYear = NullDefault.getString(
                form.getMan020DspYear(), new UDate().getStrYear());

        int dspYear = Integer.parseInt(strDspYear) - 1;
        if (dspYear >= 0) {
            form.setMan020DspYear(String.valueOf(dspYear));
        }

        __doInit(map, form, req, res, con);
        log__.debug("__doLeft end");

        // 自画面再表示
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 「右矢印」処理
     * <br>[解  説] ・所属エリアから同時登録ユーザ一覧へ追加
     * <br>         ・画面を再表示
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doRight(ActionMapping map,
            Man020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        log__.debug("__doLeft start");

        //表示開始日を+1する
        String strDspYear = NullDefault.getString(
                form.getMan020DspYear(), new UDate().getStrYear());

        int dspYear = Integer.parseInt(strDspYear) + 1;
        if (dspYear <= 9999) {
            form.setMan020DspYear(String.valueOf(dspYear));
        }

        __doInit(map, form, req, res, con);
        log__.debug("__doRight start");

        // 自画面再表示
        return map.getInputForward();
    }

}
