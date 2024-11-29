package jp.groupsession.v2.man.man027kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man027knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man027knAction.class);

    /** 戻るボタン押下時CMD */
    private static String cmd_BACK = "backTemp";

    /** テンプレート削除ボタン押下時CMD */
    private static String cmd_DEL = "delTempCommit";

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
        Man027knForm thisForm = (Man027knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        log__.debug(">>>tempSid :" + thisForm.getEditHltSid());

        if (cmd.equals(cmd_BACK)) {
            log__.debug(">>>戻るボタン押下");
            forward = map.findForward(cmd_BACK);
        } else if (cmd.equals(cmd_DEL)) {
            log__.debug(">>>削除ボタン押下");
            forward = __doCommitDelete(map, thisForm, req, res, con);
        } else {
            log__.debug(">>>初期表示");
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Man027knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        Man027knParamModel paramMdl = new Man027knParamModel();
        paramMdl.setParam(form);
        Man027knBiz biz = new Man027knBiz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

    }

    /**
     * <br>[機  能] 削除ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommitDelete(ActionMapping map, Man027knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Man027knParamModel paramMdl = new Man027knParamModel();
        paramMdl.setParam(form);
        Man027knBiz biz = new Man027knBiz();
        ActionForward forward = null;

        // 削除対象取得
        CmnHolidayTemplateDao holTempDao = new CmnHolidayTemplateDao(con);
        List<CmnHolidayTemplateModel> holTempList
                            = holTempDao.selectSortedHoliday(paramMdl.getMan023hltSid());

        // 削除
        boolean result = biz.execDataTran(paramMdl, con);
        paramMdl.setFormData(form);
        if (result) {
            __setKanryou(map, req, form);
            forward = map.findForward("gf_msg");
        }

        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<String> valueList = new ArrayList<String>();
        for (CmnHolidayTemplateModel holTempMdl : holTempList) {
            String value = "";
            MainCommonBiz mainBiz = new MainCommonBiz();
            String date = mainBiz.getHolidayDate(reqMdl, holTempMdl);
            value += "[" + gsMsg.getMessage("cmn.date2") + "] " + date;
            value += "\r\n"
                     + "[" + gsMsg.getMessage("cmn.holiday.name") + "] " + holTempMdl.getHltName();
            valueList.add(value);
        }

        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.delete"), GSConstLog.LEVEL_INFO, valueList, null);

        return forward;
    }
    /**
     * <br>[機  能] 削除完了画面設定処理
     * <br>[解  説] 削除完了画面のパラメータセット
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKanryou(
        ActionMapping map,
        HttpServletRequest req,
        Man027knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("list");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "sakujo.kanryo.object";
        log__.debug("■完了画面msgState :" + msgState);
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.HOLIDAY_TEMPLATE_MSG)));

        cmn999Form.addHiddenParam("man020DspYear", form.getMan020DspYear());
        req.setAttribute("cmn999Form", cmn999Form);

    }

}
