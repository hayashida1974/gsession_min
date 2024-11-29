package jp.groupsession.v2.bbs.bbs130;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 掲示板 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs130Action extends AbstractBulletinAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs130Action.class);

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
        log__.debug("Bbs130Action_START");
        ActionForward forward = null;
        Bbs130Form bbsForm = (Bbs130Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("dspSetting")) {
            //表示設定
            forward = map.findForward("bbs050");

        } else if (cmd.equals("smailSetting")) {
            //ショートメール通知設定
            forward = map.findForward("bbs052");

        } else if (cmd.equals("iniSetting")) {
            //掲示板 初期値設定(個人)
            forward = map.findForward("bbs210");

        } else if (cmd.equals("bbs130back")) {
            //戻る
            forward = __doBack(map, bbsForm);

        } else {
            //メニュー表示
            forward = __doInit(map, req, con, bbsForm);
        }
        log__.debug("Bbs130Action_END");
        return forward;
    }

    /**
     * <br>初期表示処理
     * @param map アクションマッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doInit(ActionMapping map, HttpServletRequest req, Connection con,
                                                Bbs130Form form)
    throws Exception {
        Bbs130ParamModel paramMdl = new Bbs130ParamModel();
        paramMdl.setParam(form);
        Bbs130Biz biz = new Bbs130Biz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        __setCanUsePluginFlg(form, req, con);
        return map.getInputForward();
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
    private void __setCanUsePluginFlg(Bbs130Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig)) {
            form.setBbs130smlKbn(false);
        }

    }

    /**
     * <br>戻るボタンクリック時の処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doBack(ActionMapping map, Bbs130Form form)
            throws Exception {
        ActionForward forward = null;

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            forward = map.findForward("mainPriSetting");
        } else {
            forward = map.findForward("backBBSList");
        }

        return forward;
    }
}
