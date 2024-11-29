package jp.groupsession.v2.man.man510;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.struts.AdminAction;
/**
 *
 * <br>[機  能] ワンタイムパスワード設定画面 アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man510Action extends AdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man510Action.class);

    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        Man510Form thisForm = (Man510Form) form;
        //前提設定
        __doPrepareSetting(thisForm, req, con);

        //初回表示
        if (cmd.equals("otpConf")) {
            forward = __doInit(map, thisForm, req, res, con);
        //戻る
        } else if (cmd.equals("man510Back")) {
            forward = map.findForward("back");
        //OK
        } else if (cmd.equals("man510Ok")) {
            forward = __doOk(map, thisForm, req, res, con);
        //再描画
        } else {
            forward = __doDsp(map, thisForm, req, res, con);
        }

        return forward;
    }
    /**
     * <br>[機  能] OKボタン処理を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param thisForm アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doOk(ActionMapping map, Man510Form thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        ActionErrors errors = thisForm.validateCheck(getRequestModel(req), con, getAppRootPath());
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, thisForm, req, res, con);
        }
        saveToken(req);

        return map.findForward("ok");
    }
    /**
     * <br>[機  能] 初期表示を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param thisForm アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Man510Form thisForm,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
                throws Exception {
        Man510ParamModel param = new Man510ParamModel();
        param.setParam(thisForm);

        Man510Biz biz = new Man510Biz(getRequestModel(req), con);
        biz.doInit(param);
        biz.doDsp(param);
        param.setFormData(thisForm);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] 初期表示を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param thisForm アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Man510Form thisForm,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
                throws Exception {
        Man510ParamModel param = new Man510ParamModel();
        param.setParam(thisForm);

        Man510Biz biz = new Man510Biz(getRequestModel(req), con);
        biz.doDsp(param);

        param.setFormData(thisForm);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] 前提設定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param thisForm アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doPrepareSetting(
        Man510Form thisForm,
        HttpServletRequest req,
        Connection con)
                throws Exception {
        Man510ParamModel param = new Man510ParamModel();
        param.setParam(thisForm);

        Man510Biz biz = new Man510Biz(getRequestModel(req), con);
        biz.doPrepareSetting(param);

        param.setFormData(thisForm);
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }
}
