package jp.groupsession.v2.cmn.cmn270;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] OAuth認証画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn270Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn270Action.class);

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
     * <br>アクション実行
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
        Cmn270Form thisForm = (Cmn270Form) form;
        String cmd = NullDefault.getString(req.getParameter("cmn270CMD"), "");
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        Cmn270Biz biz = new Cmn270Biz();

//        //アクセス権限チェック
//        if (!_checkAuth(map, req, con)) {
//            //一般ユーザの場合
//            if (!biz.accessCheck(con)) {
//                MessageResources msgRes = getResources(req);
//                thisForm.setCmn270Message(
//                        msgRes.getMessage("error.oauth.authorization"));
//                return map.getInputForward();
//            }
//        }
        if (cmd.equals("checkOauth")) {
            //親画面からの呼び出し(入力チェック)

            List<String> errMsgList = null;
            try {
                //不正なパラメータが存在するかを確認する
                errMsgList = thisForm.validateCheck(reqMdl, con);
            } catch (Exception e) {
                log__.error("OAuth入力チェック時にエラー発生", e);
            }

            res.setContentType("text/json; charset=UTF-8");
            PrintWriter writer = null;
            try {
                writer = res.getWriter();
                String result = "{";
                if (errMsgList != null && !errMsgList.isEmpty()) {
                    result += "\"errorCode\" : \"1\""
                            + "\"errorMessage\" : \""
                            + errMsgList.get(0)
                            + "\"";
                } else {
                    result += "\"errorCode\" : \"0\"";
                }
                result += "}";
                writer.write(result);
                writer.flush();

            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
            return null;

        } else if (cmd.equals("doAuth")) {
            //親画面からの呼び出し(プロバイダの認可ページ表示)
            Cmn270ParamModel paramMdl = new Cmn270ParamModel();
            paramMdl.setParam(thisForm);
            //不正なパラメータが存在する場合、エラー画面(cmn999)を表示する
            List<String> errMsgList = thisForm.validateCheck(reqMdl, con);
            if (!errMsgList.isEmpty()) {
                __setErrorPageParam(map, req, errMsgList);
                return map.findForward("gf_msg");
            }

            forward = biz.setRedirectAuth(con, reqMdl, paramMdl);
            if (forward == null) {
                MessageResources msgRes = getResources(req);
                thisForm.setCmn270Message(
                        msgRes.getMessage("error.oauth.authorization"));
                forward = map.getInputForward();

                //認証失敗時のパラメータを記録
                log__.warn("OAuth認証失敗");
                Iterator iter = req.getParameterNames().asIterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    log__.warn("  " + key + " = " + req.getParameter(key));
                }
            }

        } else {
            //各プロバイダ認可ページからの遷移
            Cmn270ParamModel paramMdl = new Cmn270ParamModel();
            paramMdl.setParam(thisForm);

            boolean commit = false;
            try {
                MlCountMtController mtCon = getCountMtController(req);
                biz.getToken(con, mtCon, reqMdl, paramMdl);
                con.commit();
                commit = true;
            } catch (Exception e) {
                log__.error("OAuth認証時に例外発生", e);
            } finally {
                if (!commit) {
                    JDBCUtil.rollback(con);
                }
            }

            paramMdl.setFormData(thisForm);

            if (paramMdl.getCmn270AuthComplete() != 1) {
                //既に認証済みのアカウントで認証エラーが発生した場合、認証トークン情報初期化
                if (thisForm.getCotSid() > 0) {
                    biz.resetToken(con, thisForm.getCotSid());
                    con.commit();
                }
                
                MessageResources msgRes = getResources(req);
                thisForm.setCmn270Message(
                        msgRes.getMessage("error.oauth.authorization"));
            }
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] パラメータエラー画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req リクエスト
     * @param errMsgList エラーメッセージリスト
     */
    private void __setErrorPageParam(
            ActionMapping map,
            HttpServletRequest req,
            List<String> errMsgList) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);

        //メッセージセット
        String errorMessage = errMsgList.get(0);
        for (int index = 1; index < errMsgList.size(); index++) {
            errorMessage += "<br>" + errMsgList.get(index);
        }
        cmn999Form.setMessage(errorMessage);

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
    }
}
