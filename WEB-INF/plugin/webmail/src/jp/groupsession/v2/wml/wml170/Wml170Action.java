package jp.groupsession.v2.wml.wml170;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] WEBメール 送受信ログ自動削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml170Action extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml170Action.class);

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
        Wml170Form thisForm = (Wml170Form) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            // トランザクショントークン設定
            this.saveToken(req);
            //設定ボタンクリック
            forward = map.findForward("confirm");

        } else if (cmd.equals("admTool")) {
            //戻るボタンクリック
            forward = map.findForward("admTool");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }


    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Wml170Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        //初期表示情報を設定
        Wml170ParamModel paramMdl = new Wml170ParamModel();
        paramMdl.setParam(form);
        Wml170Biz biz = new Wml170Biz();
        biz.setInitData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);

        //初期表示の場合
        if (form.getWml170initFlg().equals(String.valueOf(GSConstWebmail.DSP_FIRST))) {
            log__.debug("初期表示");

            //メール自動削除設定取得
            biz.getAutoDelete(paramMdl, con);
            paramMdl.setFormData(form);

            //初期表示完了
            form.setWml170initFlg(String.valueOf(GSConstWebmail.DSP_ALREADY));

        //初期表示完了の場合
         } else {
             log__.debug("初期表示完了");

        }
        return map.getInputForward();
    }
}
