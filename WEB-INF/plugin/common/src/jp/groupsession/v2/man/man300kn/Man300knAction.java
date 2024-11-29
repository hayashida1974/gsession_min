package jp.groupsession.v2.man.man300kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン インフォメーション 管理者設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man300knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man300knAction.class);

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
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        Man300knForm thisForm = (Man300knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        log__.debug("CMD==>" + cmd);
        if (cmd.equals("300kn_commit")) {
            //登録実行
            forward = __doCommit(map, thisForm, req, res, con);
        } else if (cmd.equals("300kn_back")) {
            //戻る
            forward = __doBack(map, thisForm, req, res, con);
        } else {
            //初期表示
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }

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
    private void __doInit(ActionMapping map, Man300knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        Man300knParamModel paramMdl = new Man300knParamModel();
        paramMdl.setParam(form);
        Man300knBiz biz = new Man300knBiz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Man300knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;

        boolean commitFlg = false;
        con.setAutoCommit(false);

        Man300knParamModel paramMdl = new Man300knParamModel();
        paramMdl.setParam(form);
        Man300knBiz biz = new Man300knBiz();
        try {
            //管理者設定を更新
            biz.update(paramMdl, con);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("インフォメーションメッセージの登録に失敗しました", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        String opCode = getInterMessage(reqMdl, "cmn.change");
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(
                map, reqMdl, gsMsg, con, opCode, GSConstLog.LEVEL_INFO,
                getInterMessage(reqMdl, "cmn.information.admin"));

        forward = __doCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] リクエストを解析し画面遷移先を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     */
    private ActionForward __doBack(ActionMapping map, Man300knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;

        forward = map.findForward("300kn_back");
        return forward;
    }

    /**
     * <br>[機  能] 登録・更新完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Man300knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //遷移元より遷移先を決定
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            urlForward = map.findForward("mainAdmSetting");
        } else {
            urlForward = map.findForward("300kn_comp");
        }
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("hensyu.kanryo.object",
                    getInterMessage(req, GSConstMain.TEXT_INFO_ADMIN)));


        //hiddenパラメータ
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("man320SortKey", form.getMan320SortKey());
        cmn999Form.addHiddenParam("man320OrderKey", form.getMan320OrderKey());
        cmn999Form.addHiddenParam("man320PageNum", form.getMan320PageNum());
        cmn999Form.addHiddenParam("man320SelectedSid", form.getMan320SelectedSid());
        cmn999Form.addHiddenParam("selectMsg", form.getSelectMsg());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

}
