package jp.groupsession.v2.man.man121;

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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.man.man120.Man120Biz;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] メイン 管理者設定 プラグインマネージャー(制限設定一覧)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man121Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man121Action.class);

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

        log__.debug("START_Man121");
        ActionForward forward = null;

        Man121Form thisForm = (Man121Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backAdminMenu")) {
            log__.debug("戻る");
            forward = map.findForward("menu");

        } else if (cmd.equals("man121edit")) {
            log__.debug("変更ボタンクリック");
            forward = map.findForward("man280");

        } else if (cmd.equals("plugin")) {
            log__.debug("プラグインリンククリック");
            forward = map.findForward("man120");

        } else if (cmd.equals("dspSettei")) {
            log__.debug("メニュー表示設定リンククリック");
            forward = map.findForward("man122");

        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード"
            forward = __doGetImageFile(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Man121");
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
     * @throws IOToolsException 例外処理
     */
    private ActionForward __doInit(
        ActionMapping map,
        Man121Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException,
                        NoSuchMethodException, IOToolsException {

        con.setAutoCommit(true);
        Man121ParamModel paramMdl = new Man121ParamModel();
        paramMdl.setParam(form);
        Man121Biz biz = new Man121Biz();
        biz.setInitData(paramMdl, con, getPluginConfig(req), getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Man121Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        Man120Biz biz = new Man120Biz();
        long binSid = biz.getPluginImageSid(getPluginConfig(req), form.getMan120imgPluginId());

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }
}
