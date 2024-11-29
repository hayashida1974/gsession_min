package jp.groupsession.v2.man.man520;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.struts.AdminAction;
/**
 *
 * <br>[機  能] データ使用量一覧画面 アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man520Action extends AdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man520Action.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        Man520Form thisForm = (Man520Form) form;

        //戻る
        if (cmd.equals("man520Back")) {
            forward = map.findForward("gf_main_kanri");
        //画面表示
        } else {
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 画面表示処理を行う。
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
        Man520Form thisForm,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        Man520ParamModel param = new Man520ParamModel();
        param.setParam(thisForm);

        Man520Biz biz = new Man520Biz();
        PluginConfig pconfig = getPluginConfig(req);

        biz.setInitData(con, getRequestModel(req), param, pconfig);
        param.setFormData(thisForm);

        return map.getInputForward();
    }
}
