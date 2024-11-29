package jp.groupsession.v2.rng.rng100;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngAconfModel;

/**
 * <br>[機  能] 稟議 経路テンプレート一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng100Action extends AbstractRingiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng100Action.class);
    /**
    *
    * <br>[機  能] アクション実行前の事前処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @throws Exception 実行時例外
    */
   protected void _prepareAction(ActionMapping map, Rng100Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {

       //テンプレートユーザSIDはセッションユーザSID固定
       form.setUsrSid(getSessionUserSid(req));

   }
   /**
    *
    * <br>[機  能] アクション実行前の事前処理 アクセス制限を行う
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
   protected ActionForward _immigration(ActionMapping map, Rng100Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {
       // 個人設定で個人経路テンプレート使用許可がない場合エラー画面に遷移
       RngBiz rngBiz = new RngBiz(con);
           RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
           if (aconfMdl.getRarKeiroPersonalFlg()
                   == RngConst.RAR_KEIRO_PERSONAL_FLG_NO) {
               return getSubmitErrorPage(map, req);
           }
       return null;
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Rng100Form thisForm = (Rng100Form) form;

        _prepareAction(map, thisForm, req, res, con);

        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }
        if (cmd.equals("addkeiro")) {
            log__.debug("追加ボタンクリック");
            forward = map.findForward("entry");

        } else if (cmd.equals("editkeiro")) {
            log__.debug("経路テンプレート名称クリック");
            forward = map.findForward("entry");

        } else if (cmd.equals("backMenu")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("backMenu");

        } else {
            log__.debug("初期表示");

            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Rng100ParamModel paramMdl = new Rng100ParamModel();
        paramMdl.setParam(form);
        Rng100Biz biz = new Rng100Biz();
        biz.initDsp(con, paramMdl, form.getUsrSid());
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

}
