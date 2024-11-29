package jp.groupsession.v2.man.man360;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.struts.AbstractGsAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メイン 個人設定 メイン画面レイアウト設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man360Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man360Action.class);

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Man360Form manForm = (Man360Form) form;

        //アクセス権限チェック
        Man360Biz biz = new Man360Biz();
        if (!biz.checkPow(con)) {
            return map.findForward("gf_submit");
        }

        if (cmd.equals("man360edit")) {
            //OKボタンクリック
            forward = __doConfirm(map, manForm, req, res, con);

        } else if (cmd.equals("man360back")) {
            //戻るボタンクリック
            forward = map.findForward("pset");

        } else {
            //初期表示
            forward = __doInit(map, manForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws GSException GS汎用実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Man360Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        ) throws SQLException, GSException {

        //ログインユーザSIDを取得
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        Man360ParamModel paramMdl = new Man360ParamModel();
        paramMdl.setParam(form);
        Man360Biz biz = new Man360Biz();
        biz.setInitData(con, paramMdl, buMdl.getUsrsid());
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ＯＫボタンクリック時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws GSException GS汎用実行例外
     */
    private ActionForward __doConfirm(ActionMapping map,
        Man360Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        ) throws SQLException, GSException {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("moveConfirm");
    }
}

