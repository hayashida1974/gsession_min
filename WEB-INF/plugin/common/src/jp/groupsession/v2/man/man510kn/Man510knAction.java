package jp.groupsession.v2.man.man510kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.man.man510.Man510Biz;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] ワンタイムパスワード設定 アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man510knAction extends AdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man510knAction.class);

    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        Man510knForm thisForm = (Man510knForm) form;
        //前提設定
        __doPrepareSetting(thisForm, req, con);

        //再描画
        if (cmd.equals("dsp")) {
            forward = __doDsp(map, thisForm, req, res, con);
        //戻る
        } else if (cmd.equals("man510knBack")) {
            forward = map.findForward("back");
        //OK
        } else if (cmd.equals("man510knOk")) {
            forward = __doOk(map, thisForm, req, res, con);
        } else {
            forward = __doDsp(map, thisForm, req, res, con);
        }

        return forward;
    }
    /**
     * <br>[機  能] OKボタン処理を行う。
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
    private ActionForward __doOk(ActionMapping map, Man510knForm thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        con.setAutoCommit(false);
        
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        
        ActionErrors errors = thisForm.validateCheck(getRequestModel(req), con, getAppRootPath());
        if (errors.size() > 0) {
            addErrors(req, errors);
            con.setAutoCommit(true);
            return map.findForward("back");
        }
        Man510knParamModel param = new Man510knParamModel();
        param.setParam(thisForm);

        Man510Biz biz = new Man510Biz(getRequestModel(req), con);
        biz.doUpdate(param);

        //ログ出力
        GsMessage gsMsg = new GsMessage(req);
        CommonBiz cmnBiz = new CommonBiz();
        

        //メッセージ作成        
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                gsMsg.getMessage("cmn.change"), GSConstLog.LEVEL_INFO,
                biz.makeLogMessage(param));

        //完了メッセージ画面へ遷移
        __setCompPageParam(map, req, thisForm);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 表示設定処理を行う。
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
    private ActionForward __doDsp(
        ActionMapping map,
        Man510knForm thisForm,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
                throws Exception {
        Man510knParamModel param = new Man510knParamModel();
        param.setParam(thisForm);

        Man510Biz biz = new Man510Biz(getRequestModel(req), con);
        biz.doDsp(param);
        Man510knBiz knBiz = new Man510knBiz(getRequestModel(req));
        knBiz.setDspData(con, param);
        param.setFormData(thisForm);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] 前提設定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param thisForm アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doPrepareSetting(
        Man510knForm thisForm,
        HttpServletRequest req,
        Connection con)
                throws Exception {
        Man510knParamModel param = new Man510knParamModel();
        param.setParam(thisForm);

        Man510Biz biz = new Man510Biz(getRequestModel(req), con);
        biz.doPrepareSetting(param);

        param.setFormData(thisForm);
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }
    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Man510knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("comp");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "main.man510.1")));
        req.setAttribute("cmn999Form", cmn999Form);
    }

}
