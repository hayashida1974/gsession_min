package jp.groupsession.v2.man.man122;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 プラグインマネージャー(メニュー表示設定)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man122Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man122Action.class);

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

        log__.debug("START_Man122");
        ActionForward forward = null;

        Man122Form thisForm = (Man122Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backAdminMenu")) {
            log__.debug("管理者設定へ戻る");
            forward = map.findForward("menu");

        } else if (cmd.equals("man122edit")) {
            log__.debug("変更ボタン");
            forward = __doConf(map, thisForm, req, res, con);
        } else if (cmd.equals("122_commit_ok")) {
            log__.debug("登録処理");
            forward = __doCommit(map, thisForm, req, res, con);
        } else if (cmd.equals("plugin")) {
            log__.debug("プラグインリンククリック");
            forward = map.findForward("man120");
        } else if (cmd.equals("seigenSettei")) {
            log__.debug("制限設定リンククリック");
            forward = map.findForward("man121");
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Man122");
        return forward;
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
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Man122Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);
        Man122ParamModel paramMdl = new Man122ParamModel();
        paramMdl.setParam(form);
        Man122Biz biz = new Man122Biz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * 入力チェックを行い確認画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     */
    private ActionForward __doConf(
            ActionMapping map,
            Man122Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionForward forward = null;
        //入力チェック
        log__.debug("確認画面へ遷移");
        // トランザクショントークン設定
        saveToken(req);

        //確認画面へ
        log__.debug("削除確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("122_commit");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("man122");
        cmn999Form.setUrlCancel(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("edit.henkou.kakunin.once",
                getInterMessage(req, GSConstMain.TEXT_SYSCONF_PLUGIN_DSP)));
        cmn999Form.addHiddenParam("menuEditOk", form.getMenuEditOk());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * 登録処理を行い管理者設定メニューへ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     */
    private ActionForward __doCommit(
            ActionMapping map,
            Man122Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionForward forward = null;

        log__.debug("登録処理");

        Man122ParamModel paramMdl = new Man122ParamModel();
        paramMdl.setParam(form);
        Man122Biz biz = new Man122Biz();
        biz.updateCtrmst(con, paramMdl);
        paramMdl.setFormData(form);

        //完了メッセージ画面
        forward = __doCompDsp(map, form, req, res, con);

        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String dspName = gsMsg.getMessage("main.man002.19")
                + " " + gsMsg.getMessage("main.man120.2");
        String value = "[" + gsMsg.getMessage("main.man122.1") + "] ";
        CmnContmDao contDao = new CmnContmDao(con);
        CmnContmModel contMdl = contDao.select();
        if (contMdl.getCntMenuStatic() == GSConstMain.MENU_STATIC_NOT_USE) {
            value += gsMsg.getMessage("main.man122.5");
        } else {
            value += gsMsg.getMessage("main.man122.2");
        }
        cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, value, dspName);
        return forward;
    }

    /**
     * 変更完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Man122Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("man122");

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                getInterMessage(req, GSConstMain.TEXT_SYSCONF_PLUGIN_DSP)));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

}
