package jp.groupsession.v2.rng.rng150;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiTemplateAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ選択画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng150Action extends AbstractRingiTemplateAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng150Action.class);

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

        Rng150Form thisForm = (Rng150Form) form;
        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        if (cmd.equals("addeditcategory")) {
            log__.debug("*** カテゴリ作成。");
            forward = map.findForward("rng140");

        } else if (cmd.equals("sortUp")) {
            log__.debug("上へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, Rng150Biz.SORT_UP);

        } else if (cmd.equals("sortDown")) {
            log__.debug("下へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, Rng150Biz.SORT_DOWN);

        } else if (cmd.equals("rng150back")) {
            log__.debug("*** 戻るボタンクリック");
            forward = map.findForward("rng060");

        } else {
            log__.debug("*** 初期表示を行います。");
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
    public ActionForward __doInit(ActionMapping map,
                                  Rng150Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        Rng150ParamModel paramMdl = new Rng150ParamModel();
        paramMdl.setParam(form);
        Rng150Biz biz = new Rng150Biz();

        biz.init(paramMdl, con, getRequestModel(req), getSessionUserSid(req));
        paramMdl.setFormData(form);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
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

   protected ActionForward _immigration(ActionMapping map, Rng150Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
       if (form.getRngTemplateMode() != RngConst.RNG_TEMPLATE_SHARE
               && form.getRngTemplateMode() != RngConst.RNG_TEMPLATE_PRIVATE) {
           return getSubmitErrorPage(map, req);
       }
       RequestModel reqMdl = getRequestModel(req);
       CommonBiz cmnBiz = new CommonBiz();
       boolean adminUser = cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), getPluginId());
       if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_SHARE && !adminUser) {
           return getSubmitErrorPage(map, req);
       }

       if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
           RngBiz rngBiz = new RngBiz(con);
           RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
           // 汎用稟議の使用が不可ならエラー
           if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
               return getSubmitErrorPage(map, req);
           }
           // 個人テンプレートの使用が不可ならエラー
           if (aconfMdl.getRarTemplatePersonalFlg()
                   == RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO) {
               return getSubmitErrorPage(map, req);
           }
       }

       return null;
   }


    /**
     * <br>[機  能] 並び順変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng060Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param sortKbn 処理区分
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSortChange(ActionMapping map, Rng150Form form,
                                        HttpServletRequest req, HttpServletResponse res,
                                        Connection con, int sortKbn)
            throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        boolean commit = false;
        try {
            Rng150ParamModel paramMdl = new Rng150ParamModel();
            paramMdl.setParam(form);
            Rng150Biz biz = new Rng150Biz();
            RequestModel reqMdl = getRequestModel(req);
            boolean sortFlg = biz.updateSort(
                                   con, paramMdl, sortKbn, getSessionUserSid(req), reqMdl);
            paramMdl.setFormData(form);

            if (sortFlg) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                String opCode = gsMsg.getMessage("cmn.change");
                String targetName = biz.getTargetName(paramMdl, con);

                //ログ出力処理
                RngBiz rngBiz = new RngBiz(con);
                String kbn = gsMsg.getMessage("cmn.up");
                if (sortKbn == 1) {
                    kbn = gsMsg.getMessage("cmn.down");
                }
                rngBiz.outPutLog(
                        map, opCode,
                        GSConstLog.LEVEL_INFO,
                        "[" + gsMsg.getMessage("cmn.target") + "]" + targetName + "\r\n"
                                + "[" + gsMsg.getMessage("change.sort.order") + "]" + kbn,
                        reqMdl, null, form.getRngTemplateMode());

            }
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.info("稟議テンプレートカテゴリの並び順更新に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        return __doInit(map, form, req, res, con);
    }

}
