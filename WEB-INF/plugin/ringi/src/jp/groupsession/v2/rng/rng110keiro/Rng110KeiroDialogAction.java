package jp.groupsession.v2.rng.rng110keiro;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAction;
/**
 *
 * <br>[機  能] 稟議経路詳細設定ダイアログ用 Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng110KeiroDialogAction extends AbstractRingiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng110KeiroDialogAction.class);

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

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        //描画設定処理
        RequestModel reqMdl = getRequestModel(req);
        Rng110KeiroDialogParamModel model = new Rng110KeiroDialogParamModel();

        model.setParam(form);
        Rng110KeiroDialogBiz biz = new Rng110KeiroDialogBiz(con, reqMdl);
            biz.setDispData(model);
        model.setFormData(form);

        if (cmd.equals("dsp")) {
            return map.findForward(cmd);
        }
        if (cmd.equals("dialogSubmit")) {
            Rng110KeiroDialogValidater chcker = new Rng110KeiroDialogValidater();

            ActionErrors err = chcker.validate(getRequestModel(req),
                    "err_keiro", (Rng110KeiroDialogForm) form);
            if (err.size() > 0) {
                addErrors(req, err);
            }

        }

        return map.getInputForward();
    }
}
