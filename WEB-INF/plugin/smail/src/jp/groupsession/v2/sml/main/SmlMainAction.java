package jp.groupsession.v2.sml.main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.sml.AbstractSmlAction;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 * <br>[機  能] ショートメール(メイン画面表示用)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlMainAction extends AbstractSmlAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlMainAction.class);

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

    /**
     * <br>[機  能] ショートメールアクション
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeSmail(ActionMapping map,
                                                 ActionForm form,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res,
                                                 Connection con)
        throws Exception {

        log__.debug("START");

        ActionForward forward = null;
        SmlMainForm smlForm = (SmlMainForm) form;

        //初期表示
        forward = __doInit(map, smlForm, req, res, con);

        log__.debug("END");
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
                                    SmlMainForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        SmlMainBiz biz = new SmlMainBiz();

        //初期表示データセット
        SmlMainParamModel paramMdl = new SmlMainParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        form.setSmlTopUrl(getPluginConfig(req).getPlugin(
                GSConstSmail.PLUGIN_ID_SMAIL).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);
        return map.getInputForward();
    }
}
