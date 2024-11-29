package jp.groupsession.v2.man.man290;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnInfoBinDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoMsgDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoTagDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man320.Man320Biz;
import jp.groupsession.v2.man.man320.Man320Form;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン インフォメーション登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man290Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man290Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man290";

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

        log__.debug("START_MAN290");

        //アクセス権限チェック　※管理者or登録許可ユーザ
        RequestModel reqMdl = getRequestModel(req);
        Man320Biz biz = new Man320Biz();
        if (!biz.isInfoAdminAuth(reqMdl, con)) {
            //権限エラー
            return getNotAdminSeniPage(map, req);
        }

        //更新処理が無いのでAutoCommitはtrue
        con.setAutoCommit(true);

        ActionForward forward = null;
        Man290Form thisForm = (Man290Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        log__.debug("CMD==>" + cmd);
        if (cmd.equals("290_ok")) {
            //インフォメーション登録確認
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("290_del")) {
            //削除確認画面
            forward = __doDeleteKn(map, thisForm, req, res, con);
        } else if (cmd.equals("290_delok")) {
            //削除実行
            forward = __doDeleteOk(map, thisForm, req, res, con);
        } else if (cmd.equals("290_back")) {
            //戻る
            __deleteTempDir(req);
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("290_copy")) {
            //「複写して登録」
            log__.debug("「複写して登録」");
            forward = __doCopy(map, thisForm, req, res, con);
        } else {
            //添付ファイル情報取得のためfalseにする
            con.setAutoCommit(false);

            //初期表示
            __doInit(map, thisForm, req, res, con);

            //元に戻す
            con.setAutoCommit(true);

            forward = map.getInputForward();
        }
        log__.debug("END_MAN290");
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
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル処理例外
     * @throws TempFileException 添付ファイル処理例外
     * @throws IOException 添付ファイル処理例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doInit(ActionMapping map, Man290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOToolsException, TempFileException,
        IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Man290ParamModel paramMdl = new Man290ParamModel();
        paramMdl.setParam(form);
        RequestModel reqMdl = getRequestModel(req);
        Man290Biz biz = new Man290Biz(reqMdl);
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
//        __deleteTempDir(req);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.createTempDir(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        biz.setInitData(paramMdl, getAppRootPath(), __getTempDir(req), con,
                        GroupSession.getResourceManager().getDomain(req),
                        cmd);
        paramMdl.setFormData(form);

        //WEB検索プラグインを使用可能か設定
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        form.setWebSearchUse(CommonBiz.getWebSearchUse(pconfig));

        //ヘルプパラメータを設定する。
        if (cmd.equals("edit")) {
            form.setMan290helpMode(GSConstMain.MAN_HELP_HENSYUU);
        } else {
            form.setMan290helpMode(GSConstMain.MAN_HELP_TOUROKU);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル処理例外
     * @throws TempFileException 添付ファイル処理例外
     * @throws IOException 添付ファイル処理例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doOk(ActionMapping map, Man290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOToolsException, TempFileException, IOException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheckDate(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }
                
        errors = form.validateCheck(reqMdl, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }

        saveToken(req);
        return map.findForward("290_ok");
    }

    /**
     * <br>[機  能] 「複写して登録」処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doCopy(ActionMapping map,
            Man290Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        form.setMan320SelectedSid("");
        return __doInit(map, form, req, res, con);
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
    private ActionForward __doDeleteKn(ActionMapping map, Man290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("290_delok").getPath());
        cmn999Form.setUrlCancel(map.findForward("redsp").getPath());

        //hiddenパラメータ
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("man320SortKey", form.getMan320SortKey());
        cmn999Form.addHiddenParam("man320OrderKey", form.getMan320OrderKey());
        cmn999Form.addHiddenParam("man320PageNum", form.getMan320PageNum());
        cmn999Form.addHiddenParam("man320SelectedSid", form.getMan320SelectedSid());
        cmn999Form.addHiddenParam("selectMsg", form.getSelectMsg());
        //画面項目
        cmn999Form.addHiddenParam("man290ExtKbn", form.getMan290ExtKbn());
        cmn999Form.addHiddenParam("man290Dweek", form.getMan290Dweek());
        cmn999Form.addHiddenParam("man290Week", form.getMan290Week());
        cmn999Form.addHiddenParam("man290Day", form.getMan290Day());
        cmn999Form.addHiddenParam("man290FrYear", form.getMan290FrYear());
        cmn999Form.addHiddenParam("man290FrMonth", form.getMan290FrMonth());
        cmn999Form.addHiddenParam("man290FrDay", form.getMan290FrDay());
        cmn999Form.addHiddenParam("man290FrHour", form.getMan290FrHour());
        cmn999Form.addHiddenParam("man290FrMin", form.getMan290FrMin());
        cmn999Form.addHiddenParam("man290ToYear", form.getMan290ToYear());
        cmn999Form.addHiddenParam("man290ToMonth", form.getMan290ToMonth());
        cmn999Form.addHiddenParam("man290ToDay", form.getMan290ToDay());
        cmn999Form.addHiddenParam("man290ToHour", form.getMan290ToHour());
        cmn999Form.addHiddenParam("man290ToMin", form.getMan290ToMin());
        cmn999Form.addHiddenParam("man290FrDate", form.getMan290FrDate());
        cmn999Form.addHiddenParam("man290FrTime", form.getMan290FrTime());
        cmn999Form.addHiddenParam("man290ToDate", form.getMan290ToDate());
        cmn999Form.addHiddenParam("man290ToTime", form.getMan290ToTime());
        cmn999Form.addHiddenParam("man290Msg", form.getMan290Msg());
        cmn999Form.addHiddenParam("man290Value", form.getMan290Value());
        cmn999Form.addHiddenParam("man290Jtkbn", form.getMan290Jtkbn());
        cmn999Form.addHiddenParam("man290memberSid", form.getMan290memberSid());
        cmn999Form.addHiddenParam("man290groupSid", form.getMan290groupSid());

        //メッセージセット
        String msgState = "sakujo.kakunin.once";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
            getInterMessage(req, GSConstMain.TEXT_INFO)));

        req.setAttribute("cmn999Form", cmn999Form);

        //トランザクショントークン設定
        saveToken(req);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDeleteOk(ActionMapping map, Man320Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("一括削除");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commit = false;
        try {

            //インフォメーションを削除
            if (form.getMan320SelectedSid() != null) {
                int imsSid = NullDefault.getInt(form.getMan320SelectedSid(), -1);
                CmnInfoMsgDao msgDao = new CmnInfoMsgDao(con);
                CmnInfoTagDao tagDao = new CmnInfoTagDao(con);
                CmnInfoBinDao binDao = new CmnInfoBinDao(con);
                msgDao.delete(imsSid);
                tagDao.delete(imsSid);
                //添付ファイルを削除
                binDao.deleteBinf(imsSid);
                binDao.delete(imsSid);
            }

            __deleteTempDir(req);

            commit = true;

        } catch (SQLException e) {
            log__.error("オペレーションログの削除に失敗", e);
            throw e;

        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.delete"), GSConstLog.LEVEL_INFO,
                gsMsg.getMessage("main.src.man290.1"));

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);

        return map.findForward("gf_msg");
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
    private ActionForward __doBack(ActionMapping map, Man290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        return map.findForward("290_back");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Man320Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("290_back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //hiddenパラメータ
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("man320SortKey", form.getMan320SortKey());
        cmn999Form.addHiddenParam("man320OrderKey", form.getMan320OrderKey());
        cmn999Form.addHiddenParam("man320PageNum", form.getMan320PageNum());

        //メッセージセット
        String msgState = "sakujo.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.TEXT_INFO)));

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
