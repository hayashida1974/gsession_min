package jp.groupsession.v2.usr.usr042;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.AbstractUsrAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ユーザ情報 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr042Action extends AbstractUsrAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr042Action.class);

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

        Usr042Form thisForm = (Usr042Form) form;
        if (cmd.equals("usr042back")) {
            //戻るボタン
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("dspConf")) {
            //表示件数設定
            forward = map.findForward("usr041");

        } else if (cmd.equals("labelConf")) {
            //ラベルカテゴリ設定
            forward = map.findForward("usr043");

        } else {
            //初期表示を行います。
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
    public ActionForward __doInit(ActionMapping map, Usr042Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @return ActionForward
     */
    public ActionForward __doBack(ActionMapping map, Usr042Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            //メイン個人設定メニュー画面へ遷移する。
            return map.findForward("mainPriSetting");
        }

        return map.findForward("usr040");
    }
}
