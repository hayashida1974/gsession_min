package jp.groupsession.v2.cmn.cmn400;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 *
 * <br>[機  能] ブラウザ通知設定 Actionクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn400Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn400Action.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        ActionForward forward = null;

        if (cmd.equals("cmn400Back")) {
            //「戻る」ボタンクリック時
            log__.debug("戻るボタン押下");
            forward = __doBack(map, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, req, res, con);
        } 
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理の実行
     * <br>[解  説]
     * <br>[備  考]
     */
    private ActionForward __doInit(ActionMapping map,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 戻るボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doBack(ActionMapping map,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig = getPluginConfig(req);
        
        CommonBiz cmnBiz = new CommonBiz();
        ActionForward forward = cmnBiz.getBackUrl(map, reqMdl, pconfig);
        return forward;
    }

}