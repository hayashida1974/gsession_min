package jp.groupsession.v2.rng.rng290;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.rng020.Rng020Action;

/**
 * <br>[機  能] 稟議テンプレート プレビュー(ポップアップ)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng290Action extends Rng020Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng290Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        if (_isSystemAdmin(req, con)) {
            return true; //システム管理者の場合はプラグインチェックをしない
        }
        return super._isAccessPlugin(req, form, con);
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
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        Rng290Form thisForm = (Rng290Form) form;

        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        try {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        } catch (RtpNotfoundException rtpe) {
            log__.debug("*** テンプレートが削除されました。");
            forward = getSubmitErrorPage(map, req);
        }


        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws RtpNotfoundException テンプレート削除済み例外
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws RtpNotfoundException, Exception {

        Rng290ParamModel paramMdl = new Rng290ParamModel();
        paramMdl.setParam(form);
        Rng290Biz biz = new Rng290Biz(con, getRequestModel(req));
        String appRoot = getAppRootPath();
        GSTemporaryPathModel tempDir = _getRingiDir(req);
        biz.setInitData(req, paramMdl, getSessionUserModel(req), appRoot, tempDir);
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }
}
