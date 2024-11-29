package jp.groupsession.v2.cmn.cmn210;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] グループ選択ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn210Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn210Action.class);

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

        ActionForward forward = null;
        Cmn210Form thisForm = (Cmn210Form) form;

        forward = __doInit(map, thisForm, req, res, con);

        return forward;
    }

    /**
     * <p>グループリスト取得
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doInit(ActionMapping map, Cmn210Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);

        ActionForward forward = null;

        //セッション情報を取得
        int sessionUsrSid = __getSessionUserSid(req);
        Cmn210ParamModel paramMdl = new Cmn210ParamModel();
        paramMdl.setParam(form);
        Cmn210Biz biz = new Cmn210Biz();
        biz.setInitData(paramMdl, con, sessionUsrSid);
        paramMdl.setFormData(form);

        //javaScript文字列
        String prm = "";
        if (form.getPrtPrm() != null && !form.getPrtPrm().equals("")) {
            prm = "javascript:function v(){var formNode = self.opener.document.forms['"
                + form.getParentDspID()
                + "'];if(formNode."
                + form.getSelBoxName()
                + "==undefined)formNode=self.opener.document."
                + form.getParentDspID()
                + "[0];formNode."
                + form.getSelBoxName();
        } else {
            prm = "javascript:function v(){self.opener.document."
                + form.getParentDspID()
                + "."
                + form.getSelBoxName();
        }
        form.setScriptStr(prm);

        //javaScript文字列
        String prm2 = "";
        if (form.getPrtPrm() != null && !form.getPrtPrm().equals("")) {
                prm2 = "opener.document.getElementById('"
                     + form.getPrtPrm() + "').click();self.close()};v()";
        } else {
            if (form.getSubmitFlg() == 0) {
                prm2 = "opener.document." + form.getParentDspID() + ".submit();self.close()};v()";
            } else {
                prm2 = "self.close()};v()";
            }
        }
        form.setScriptStr2(prm2);

        con.setAutoCommit(false);

        forward = map.getInputForward();
        return forward;
    }
    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req リクエスト
     * @return sessionUsrSid セッションユーザSID
     */
    private int __getSessionUserSid(HttpServletRequest req) {

        log__.debug("セッションユーザSID取得");

        int sessionUsrSid = -1;

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid();
        }

        return sessionUsrSid;
    }
}