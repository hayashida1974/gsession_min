package jp.groupsession.v2.cmn.cmn170;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 個人設定 テーマ設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn170Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn170Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {


        ActionForward forward = null;

        Cmn170Form thisForm = (Cmn170Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("cmn170back")) {
            //戻る
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("cmn170change")) {
            //登録（即時反映）
            forward = __doChange(map, thisForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Cmn170Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("cmn170back");
        return forward;
    }

    /**
     * <br>登録処理（即時反映）
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 例外処理
     */
    private ActionForward __doChange(ActionMapping map, Cmn170Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("登録");

        ActionErrors errors = form.validateCheck();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //DB更新
        Cmn170Biz biz = new Cmn170Biz();
        BaseUserModel umodel = getSessionUserModel(req);
        Cmn170ParamModel paramModel = new Cmn170ParamModel();
        paramModel.setParam(form);
        String ctmPath = biz.setPconfSetting(paramModel, umodel, con);
        paramModel.setFormData(form);
        ((BaseUserModel) req.getSession().getAttribute(GSConst.SESSION_KEY))
        .setCtmPath(ctmPath);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();

        String msg = "[" + gsMsg.getMessage("cmn.theme") + "]";
        if (form.getCmn170Dsp1() == GSConstCommon.THEME_DEFAULT) {
            msg += gsMsg.getMessage("cmn.default");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_GRAY) {
            msg += gsMsg.getMessage("cmn.theme.gray");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_GREEN) {
            msg += gsMsg.getMessage("cmn.theme.green");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_RED) {
            msg += gsMsg.getMessage("cmn.theme.red");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_PINK) {
            msg += gsMsg.getMessage("cmn.theme.pink");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_YELLOW) {
            msg += gsMsg.getMessage("cmn.theme.yellow");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_ORIGINAL) {
            msg += gsMsg.getMessage("cmn.theme.original");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_SOUGEN) {
            msg += gsMsg.getMessage("cmn.theme.sougen");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_SUNSET) {
            msg += gsMsg.getMessage("cmn.theme.sunset");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_SAKURA) {
            msg += gsMsg.getMessage("cmn.theme.sakura");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_SKY) {
            msg += gsMsg.getMessage("cmn.theme.sky");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_CITY) {
            msg += gsMsg.getMessage("cmn.theme.city");
        } else if (form.getCmn170Dsp1() == GSConstCommon.THEME_DARK) {
            msg += gsMsg.getMessage("cmn.theme.dark");
        }

        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                textEdit, GSConstLog.LEVEL_INFO, msg);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cmn170Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        BaseUserModel umodel = getSessionUserModel(req);

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Cmn170Biz biz = new Cmn170Biz(con);
        Cmn170ParamModel paramModel = new Cmn170ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, umodel);
        paramModel.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

}
