package jp.groupsession.v2.sml.sml400;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.AbstractSmlSubAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ショートメール 管理者設定 表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml400Action extends AbstractSmlSubAction {

//    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Sml400Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーム
     * @throws Exception 実行時例外
     */
    public ActionForward executeSmail(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        ActionForward forward = null;
        Sml400Form thisForm = (Sml400Form) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOk(map, thisForm , req);

        } else if (cmd.equals("backToMenu")) {
            //戻るボタンクリック
            forward = map.findForward("smlAdmConf");

        } else if (cmd.equals("backInput")) {
            //確認画面からの遷移時
            forward = __doDisplay(map, thisForm, req);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

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
            Sml400Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException {

        Sml400ParamModel paramMdl = new Sml400ParamModel();
        paramMdl.setParam(form);
        RequestModel reqMdl = getRequestModel(req);
        Sml400Biz biz = new Sml400Biz(reqMdl);
        biz.setInitData(reqMdl, paramMdl, con);
        biz.setDisplayData(paramMdl);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 表示のみ行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDisplay(
            ActionMapping map,
            Sml400Form form,
            HttpServletRequest req)
                    throws SQLException {

        Sml400ParamModel paramMdl = new Sml400ParamModel();
        paramMdl.setParam(form);
        RequestModel reqMdl = getRequestModel(req);
        Sml400Biz biz = new Sml400Biz(reqMdl);
        biz.setDisplayData(paramMdl);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    public ActionForward __doOk(
            ActionMapping map,
            Sml400Form form,
            HttpServletRequest req)
                    throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateInputCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDisplay(map, form, req);
        }

        // トランザクショントークン設定
        this.saveToken(req);
        return map.findForward("smlConfirm");
    }
}