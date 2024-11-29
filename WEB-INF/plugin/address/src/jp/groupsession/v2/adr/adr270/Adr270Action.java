package jp.groupsession.v2.adr.adr270;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] アドレス帳ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr270Action extends AbstractGsAction {

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map,
                                         ActionForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws Exception {

        Adr270Form thisForm = (Adr270Form) form;

        //アドレス帳情報を閲覧可能か判定
        Adr270Biz biz = new Adr270Biz(getRequestModel(req));

        boolean viewFlg = false;

        Adr270ParamModel paramMdl = new Adr270ParamModel();
        paramMdl.setParam(thisForm);
        viewFlg = biz.canViewAddress(con, paramMdl, getSessionUserModel(req));
        paramMdl.setFormData(thisForm);

        if (!viewFlg) {
            Cmn999Form cmn999Form = new Cmn999Form();
            MessageResources msgRes = getResources(req);
            cmn999Form.setMessage(
                    msgRes.getMessage("error.access.double.submit.popup"));

            //ポップアップフラグをTRUEに
            cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);

            req.setAttribute("cmn999Form", cmn999Form);

            return map.findForward("gf_msg");
        }

        ActionForward forward = __doInit(map, thisForm, req, res, con);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Adr270Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Adr270Biz biz = new Adr270Biz(reqMdl);

        Adr270ParamModel paramMdl = new Adr270ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getSessionUserModel(req));
        paramMdl.setFormData(form);

        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        form.setAdr270searchUse(CommonBiz.getWebSearchUse(pconfig));
        con.setAutoCommit(false);

        return map.getInputForward();
    }
}