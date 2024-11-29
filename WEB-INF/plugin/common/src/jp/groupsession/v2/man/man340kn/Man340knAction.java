package jp.groupsession.v2.man.man340kn;

import java.io.IOException;
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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] メイン 管理者設定 プラグイン追加確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man340knAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man340";

    /** アイコン画像名 */
    public String imageFileName__ = "";
    /** アイコン画像保存名 */
    public String imageFileSaveName__ = "";

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

        log__.debug("START_Man340kn");
        ActionForward forward = null;

        Man340knForm thisForm = (Man340knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("man340")) {
            log__.debug("戻る");
            forward = map.findForward("man340");

        } else if (cmd.equals("340kn_ok")) {
            log__.debug("プラグイン作成");
            forward = __doDecision(map, thisForm, req, res, con);
        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード"
            forward = __doGetImageFile(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }
        log__.debug("END_Man340kn");
        return forward;
    }

    /**
     * 初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException 例外
     * @throws IOException 例外
     * @throws IOToolsException 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map,
            Man340knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException,
                    IOException,
                    IOToolsException {
        con.setAutoCommit(true);

        //画像ファイルを設定
        if (!NullDefault.getString(form.getMan340file(), "").equals("")
                && !NullDefault.getString(form.getMan340SaveFile(), "").equals("")) {
            imageFileName__ = form.getMan340file();
            imageFileSaveName__ = form.getMan340SaveFile();
        }

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
            Man340knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //imageFileSaveName__の半角数字チェック処理
        if (!ValidateUtil.isNumber(imageFileSaveName__)) {
            return getSubmitErrorPage(map, req);
        }

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        String fullPath = tempDir + "/" + imageFileSaveName__ + GSConstCommon.ENDSTR_SAVEFILE;

        //画像ファイル読込
        TempFileUtil.downloadInline(req, res, fullPath, imageFileName__, Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説] 処理モード = 編集の場合、スレッド情報を設定する
     * <br>[備  考]
     * @param req リクエスト
     * @return テンポラリディレクトリパス
     */
    protected String _getMainTempDir(HttpServletRequest req) {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        log__.debug("テンポラリディレクトリ = " + tempDir);

        return tempDir;
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
            ActionMapping map,
            Man340knForm form,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("man120");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        int procMode = form.getMan340cmdMode();
        if (procMode == GSConstMain.CMD_MAIN_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object",
                            getInterMessage(req, GSConstMain.PLUGIN_MSG)));
        } else if (procMode == GSConstMain.CMD_MAIN_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("comp.regorupd.data",
                            getInterMessage(req, GSConstMain.PLUGIN_MSG)));
        }

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 確定ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDecision(ActionMapping map,
            Man340knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = new ActionErrors();
        String tempDir = _getMainTempDir(req);
        errors = form.validateCheck(tempDir, getRequestModel(req),
                con, getAppRootPath());
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        boolean commit = false;

        //アプリケーションのルートパス
        String appRoot = getAppRootPath();

        //オペレーションログ出力用パラメータ
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = null;

        Man340knParamModel paramMdl = new Man340knParamModel();
        paramMdl.setParam(form);
        Man340knBiz biz = new Man340knBiz();
        Plugin pg = new Plugin();
        try {
            MlCountMtController cntCon = getCountMtController(req);
            if (form.getMan340cmdMode() == GSConstMain.CMD_MAIN_ADD) {

                //登録処理
                biz.insertData(paramMdl, con, cntCon, userSid, appRoot, tempDir);
                pg = biz.setPlugin(paramMdl);
                getPluginConfig(req).addPlugin(pg);

                opCode = gsMsg.getMessage("cmn.entry");
            } else if (form.getMan340cmdMode() == GSConstMain.CMD_MAIN_EDIT) {

                //更新処理
                biz.updateData(paramMdl, con, cntCon, userSid, appRoot, tempDir);
                pg = biz.setPlugin(paramMdl);
                getPluginConfig(req).removePlugin(pg.getId());
                getPluginConfig(req).addPlugin(pg);

                opCode = gsMsg.getMessage("cmn.change");
            }
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("プラグイン登録処理エラー", e);
            throw e;
        } finally {
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN,
                    TEMP_DIRECTORY_ID);
            if (!commit) {
                con.rollback();
            }
        }

        //プラグイン並び順設定
        if (form.getMan340cmdMode() == GSConstMain.CMD_MAIN_ADD) {
            PluginConfig pconfig = getPluginConfig(req);
            biz.setSort(paramMdl, con, pconfig, getSessionUserSid(req));
            paramMdl.setFormData(form);
        }

        //ログ出力
        if (pg != null) {
            CommonBiz cmnBiz = new CommonBiz();
            String value = "";
            value = "[" + gsMsg.getMessage("cmn.plugin") + gsMsg.getMessage("cmn.id") + "] ";
            value += pg.getId();
            value += "\r\n" + "[" + gsMsg.getMessage("main.man340.12") + "] ";
            value += pg.getName(reqMdl);
            String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("cmn.plugin") + gsMsg.getMessage("cmn.add");
            cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                    opCode, GSConstLog.LEVEL_INFO, value, dspName);
        }

        __setKanryoDsp(map, form, req);
        return map.findForward("gf_msg");
    }
}
