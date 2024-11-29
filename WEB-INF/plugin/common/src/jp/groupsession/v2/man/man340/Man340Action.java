package jp.groupsession.v2.man.man340;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man340.model.Man340UrlParamModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン追加画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man340Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man340";

    /** 共通メッセージ画面種別 削除確認画面 */
    private static final int MSGTYPE_KN__ = 1;
    /** 共通メッセージ画面種別 削除完了画面 */
    private static final int MSGTYPE_KR__ = 2;
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

        log__.debug("START_Man340");
        ActionForward forward = null;
        Man340Form thisForm = (Man340Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("man120")) {
            //戻る
            forward = map.findForward("man120");
            __deleteTempDir(req);
        } else if (cmd.equals("man340kn")) {
            //プラグイン追加
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("edit")) {
            //プラグイン編集
            thisForm.setMan340cmdMode(GSConstMain.CMD_MAIN_EDIT);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("man340_del")) {
            //プラグイン削除
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("man340_delDecision")) {
            //プラグイン削除
            forward = __doDeleteDecision(map, thisForm, req, res, con);
        } else if (cmd.equals("getImg")) {
            //画像添付
            forward = __doDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード"
            forward = __doGetImageFile(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }
        log__.debug("END_Man340");
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
     * @throws SQLException 例外
     * @throws IOException 例外
     * @throws IOToolsException 例外
     * @return Forward
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doInit(ActionMapping map,
            Man340Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException,
                    IOException,
                    IOToolsException,
                    TempFileException {
        con.setAutoCommit(true);

        //添付ファイルを削除する
        //選択された添付ファイルを削除する
        __deleteTempDir(req);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.createTempDir(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        imageFileName__ = "";
        imageFileSaveName__ = "";
        form.setMan340file("");
        form.setMan340SaveFile("");

        con.setAutoCommit(false);
        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 再表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException 例外
     * @throws IOException 例外
     * @throws IOToolsException 例外
     * @return Forward
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doDsp(ActionMapping map,
            Man340Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException,
                    IOException,
                    IOToolsException,
                    TempFileException {
        con.setAutoCommit(true);


        //テンポラリディレクトリパスを取得
        String tempDir = __getTempDir(req);
        con.setAutoCommit(true);

        Man340ParamModel paramMdl = new Man340ParamModel();
        paramMdl.setParam(form);
        Man340Biz biz = new Man340Biz();
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        biz.setInitData(paramMdl, con, getAppRootPath(), tempDir,
                GroupSession.getResourceManager().getDomain(req),
                cmd);
        paramMdl.setFormData(form);

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
     * <br>[機  能] アイコン登録チェックを行いエラーが無い場合は確認画面へ遷移する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return Forward
     * @throws SQLException 例外
     * @throws IOException ファイル書込み時例外
     * @throws IOToolsException ファイル書込み時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doOk(ActionMapping map,
            Man340Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException,
                    IOException,
                    IOToolsException,
                    TempFileException {

        RequestModel reqMdl = getRequestModel(req);

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        //テンポラリディレクトリパスを取得
        String tempDir = __getTempDir(req);

        errors = form.validateCheck(tempDir, reqMdl, con, this.getAppRootPath());
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        saveToken(req);

        forward = map.findForward("man340kn");
        return forward;
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
            Man340Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //imageFileSaveName__の半角数字チェック処理
        if (!ValidateUtil.isNumber(imageFileSaveName__)) {
            return getSubmitErrorPage(map, req);
        }

        String fullPath = IOTools.replaceFileSep(
                __getTempDir(req)
                + "/" + imageFileSaveName__ + GSConstCommon.ENDSTR_SAVEFILE);

        //画像ファイル読込
        TempFileUtil.downloadInline(req, res, fullPath, imageFileName__, Encoding.UTF_8);
        return null;
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理
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
    private ActionForward __doDelete(ActionMapping map,
            Man340Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
                    throws Exception {

        __setMsgPageParam(map, req, form, con, MSGTYPE_KN__);

        saveToken(req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理実施
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
    private ActionForward __doDeleteDecision(ActionMapping map,
            Man340Form form,
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

        boolean commit = false;
        try {
            Man340Biz biz = new Man340Biz();

            Man340ParamModel paramMdl = new Man340ParamModel();
            paramMdl.setParam(form);
            biz.deletePluginData(paramMdl, con);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;

        } catch (Exception e) {
            log__.error("プラグインの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        __setMsgPageParam(map, req, form, con, MSGTYPE_KR__);

        RequestModel reqMdl = getRequestModel(req);
        String pluginId = form.getMan340funcId();
        String pluginName = getPluginConfig(req).getPlugin(pluginId).getName(reqMdl);
        getPluginConfig(req).removePlugin(pluginId);

        //ログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.plugin") + gsMsg.getMessage("cmn.add");
        String value = "";
        value = gsMsg.getMessage("cmn.plugin") + gsMsg.getMessage("cmn.id") + ": " + pluginId
                + "\r\n" + gsMsg.getMessage("main.man340.12") +  ": " + pluginName;
        cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                gsMsg.getMessage("cmn.delete"), GSConstLog.LEVEL_INFO, value, dspName);

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @param msgType メッセージ画面の種別 0:削除確認画面、1:削除完了画面
     * @throws SQLException SQL例外発生
     */
    private void __setMsgPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Man340Form form,
            Connection con,
            int msgType) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);


        if (msgType == MSGTYPE_KN__) {
            urlForward = map.findForward("mine");
            cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
            cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=man340_delDecision");
            cmn999Form.setUrlCancel(urlForward.getPath() + "?CMD=getImg");

            //メッセージセット
            cmn999Form.setMessage(
                    msgRes.getMessage("sakujo.kakunin.once",
                            StringUtilHtml.transToHTmlPlusAmparsant(form.getMan340title())));

        } else if (msgType == MSGTYPE_KR__) {
            urlForward = map.findForward("man120");
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setUrlOK(urlForward.getPath());

            GsMessage gsMsg = new GsMessage();
            String textForum = gsMsg.getMessage(req, "cmn.plugin");

            //メッセージセット
            String msgState = "sakujo.kanryo.object";
            cmn999Form.setMessage(msgRes.getMessage(msgState, textForum));
        }

        cmn999Form.addHiddenParam("man340initFlg", form.getMan340initFlg());
        cmn999Form.addHiddenParam("man120pluginId", form.getMan120pluginId());
        cmn999Form.addHiddenParam("man340cmdMode", form.getMan340cmdMode());
        cmn999Form.addHiddenParam("man340file", form.getMan340file());
        cmn999Form.addHiddenParam("man340SaveFile", form.getMan340SaveFile());
        cmn999Form.addHiddenParam("man340funcId", form.getMan340funcId());
        cmn999Form.addHiddenParam("man340title", form.getMan340title());
        cmn999Form.addHiddenParam("man340url", form.getMan340url());
        cmn999Form.addHiddenParam("man340openKbn", form.getMan340openKbn());

        cmn999Form.addHiddenParam("man340paramKbn", form.getMan340paramKbn());
        cmn999Form.addHiddenParam("man340sendKbn", form.getMan340sendKbn());

        if (form.getMan340urlParamListToList() != null) {
            int index = 0;
            for (Man340UrlParamModel upMdl : form.getMan340urlParamListToList()) {
                cmn999Form.addHiddenParam(
                        "man340urlParamList[" + index + "].paramName", upMdl.getParamName());
                cmn999Form.addHiddenParam(
                        "man340urlParamList[" + index + "].paramValue", upMdl.getParamValue());

                index++;
            }
        }

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能]テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return GSTemporaryPathModel
     */
    private String __getTempDir(HttpServletRequest req) {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        return tempDir;
    }

    /**
     * <br>[機  能]テンポラリディレクトリパスを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    private void __deleteTempDir(HttpServletRequest req) {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
    }
}
