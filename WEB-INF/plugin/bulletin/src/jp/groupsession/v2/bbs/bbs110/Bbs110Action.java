package jp.groupsession.v2.bbs.bbs110;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 掲示板 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs110Action extends AbstractBulletinAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs110Action.class);

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
        log__.debug("Bbs110Action_START");
        ActionForward forward = null;
        Bbs110Form bbsForm = (Bbs110Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("confForum")) {
            //フォーラム設定
            forward = map.findForward("confForum");
        } else if (cmd.equals("delConf")) {
            //自動データ削除
            forward = map.findForward("delConf");
        } else if (cmd.equals("iniSetting")) {
            //初期値設定(管理者)
            forward = map.findForward("admIniSetting");
        } else if (cmd.equals("manualDelConf")) {
            //手動データ削除
            forward = map.findForward("manualDelConf");
        } else if (cmd.equals("smlSetting")) {
            //ショートメール通知設定
            forward = map.findForward("smlSetting");
        } else if (cmd.equals("bbsLogCount")) {
            //統計情報
            forward = map.findForward("bbsLogCount");
        } else if (cmd.equals("backBBSList")) {
            //戻る
            forward = __doBack(map, bbsForm);
        } else {
            __setCanUsePluginFlg(bbsForm, req, con);

            //GS管理者情報を設定
            BaseUserModel buMdl = getSessionUserModel(req);
            bbsForm.setBbs110GsAdminFlg(buMdl.getAdminFlg());

            //メニュー表示
            forward = map.getInputForward();
        }
        log__.debug("Bbs110Action_END");
        return forward;
    }
    /**
     * <br>[機  能] ショートメールプラグインが利用可能かフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Bbs110Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig)) {
            form.setSmailUseOk(GSConst.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConst.PLUGIN_NOT_USE);
        }

    }


    /**
     * <br>戻るボタンクリック時の処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doBack(ActionMapping map, Bbs110Form form)
            throws Exception {
        ActionForward forward = null;

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            forward = map.findForward("mainAdmSetting");
        } else {
            forward = map.findForward("backBBSList");
        }

        return forward;
    }
}
