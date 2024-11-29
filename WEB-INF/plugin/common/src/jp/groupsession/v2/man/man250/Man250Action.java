package jp.groupsession.v2.man.man250;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainUsedDataBiz;
import jp.groupsession.v2.man.man250.dao.Man250Dao;
import jp.groupsession.v2.man.man250.model.Man250SearchModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man250Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man250Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man250";

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("csvOut"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("man250export")) {
            log__.debug("CSVファイルダウンロード");
            return true;
        }
        return false;
    }

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
        Man250Form thisForm = (Man250Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("cmd = " + cmd);

        if (cmd.equals("backKtool")) {
            //戻るボタンクリック
            forward = map.findForward("ktool");

        } else if (cmd.equals("changeMode")) {
            //ページコンボ変更
            forward = __doChangeMode(map, thisForm, req, res, con);

        } else if (cmd.equals("arrorw_left")) {
            //左矢印押下
            forward = __doPageMinus(map, thisForm, req, res, con);

        } else if (cmd.equals("arrorw_right")) {
            //右矢印押下
            forward = __doPagePlus(map, thisForm, req, res, con);

        } else if (cmd.equals("man250search")) {
            //検索ボタン押下
            __doSearch(map, thisForm, req, res, con, true);
            forward = map.getInputForward();

        } else if (cmd.equals("man250export")) {
            //エクスポートボタン押下
            __doDownLoad(map, thisForm, req, res, con);

        } else if (cmd.equals("man250delete")) {
            //一括削除ボタン押下
            forward = __doDeleteKn(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            //一括削除実行
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("research")) {
            //再表示
            __doSearch(map, thisForm, req, res, con, false);
            forward = map.getInputForward();

        } else {
            //検索対象
            Man250Biz biz = new Man250Biz(getRequestModel(req));
            Man250ParamModel paramMdl = new Man250ParamModel();
            biz.setDefultSearchTarget(paramMdl);
            paramMdl.setFormData(form);

            //初期表示
            __doSearch(map, thisForm, req, res, con, true);
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doInit(ActionMapping map,
            Man250Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        log__.debug("初期表示");

        con.setAutoCommit(true);
        //初期表示設定
        Man250ParamModel paramMdl = new Man250ParamModel();
        paramMdl.setParam(form);
        Man250Biz biz = new Man250Biz(getRequestModel(req));
        biz.setInitData(paramMdl, con, getPluginConfig(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * <br>検索処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param save パラメータをセーブする場合true
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private void __doSearch(ActionMapping map, Man250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, boolean save)
                    throws SQLException, IllegalAccessException,
                        InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);

        try {
            RequestModel reqMdl = getRequestModel(req);
            Man250ParamModel paramMdl = new Man250ParamModel();
            paramMdl.setParam(form);
            Man250Biz biz = new Man250Biz(reqMdl);
            biz.setInitData(paramMdl, con, getPluginConfig(req));
            paramMdl.setFormData(form);

            if (save) {
                //入力チェック
                ActionErrors errors = form.validateMan250Check(reqMdl);
                if (!errors.isEmpty()) {
                    log__.debug("入力エラー");
                    addErrors(req, errors);
                    __doInit(map, form, req, res, con);
                    return;
                }
                form.setMan250PageNum(1);
                //検索条件SAVE
                form.saveSearchParm();
                paramMdl.setParam(form);
            }

            int count = biz.getSearchResult(paramMdl, con);
            paramMdl.setFormData(form);

            if (count < 1) {
                ActionErrors errors = new ActionErrors();
                ActionMessage msg = new ActionMessage(
                        "search.data.notfound", getInterMessage(reqMdl, "main.src.man250.11"),
                        "resultCnt");
                errors.add("resultCnt" + "error.input.length.text", msg);
                addErrors(req, errors);
                __doInit(map, form, req, res, con);
                return;
            }
        } finally {
            con.setAutoCommit(false);
        }
    }

    /**
     * <br>[機  能] ページコンボ変更処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doChangeMode(
            ActionMapping map,
            Man250Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 左矢印押下処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doPageMinus(
            ActionMapping map,
            Man250Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //ページ数取得
        int page = form.getMan250PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        //調整後ページ数セット
        form.setMan250PageNum(page);
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 右矢印押下処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doPagePlus(
            ActionMapping map,
            Man250Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //ページ数取得
        int page = form.getMan250PageNum();
        page += 1;

        //調整後ページ数セット
        form.setMan250PageNum(page);
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] オペレーションログエクスポートの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(
        ActionMapping map,
        Man250Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        CommonBiz cmnBiz = new CommonBiz();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        String fileName = LogCsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        __doExport(form, con, tempDir, req);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //ログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.export"), GSConstLog.LEVEL_INFO, fileName);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        return null;
    }

    /**
     * <br>[機  能] エクスポート処理を実行(氏名カナ)
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @param req リクエスト
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(Man250Form form, Connection con,
            String outDir, HttpServletRequest req)
            throws Exception {
        log__.debug("エクスポート処理(CSV)");

        RequestModel reqMdl = getRequestModel(req);
        Man250ParamModel paramMdl = new Man250ParamModel();
        paramMdl.setParam(form);
        Man250Biz biz = new Man250Biz(reqMdl);

        //検索条件をセット(SAVEより)
        Man250SearchModel searchMdl = biz.getMan250SearchModel(paramMdl);
        paramMdl.setFormData(form);

        //CSVファイルを作成
        LogCsvWriter write = new LogCsvWriter();
        write.setSearchModel(searchMdl);
        write.outputCsv(con, outDir, reqMdl);

        return null;
    }

    /**
     * <br>[機  能] 一括削除処理
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
    private ActionForward __doDelete(ActionMapping map, Man250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("一括削除");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        Man250ParamModel paramMdl = new Man250ParamModel();
        paramMdl.setParam(form);
        Man250Biz biz = new Man250Biz(reqMdl);

        //検索モデルを設定
        Man250SearchModel searchMdl = biz.getMan250SearchModel(paramMdl);
        paramMdl.setFormData(form);

        boolean commit = false;
        try {

            //オペレーションログ削除処理
            Man250Dao dao = new Man250Dao(con);
            int delCount = dao.delete(searchMdl);

            //オペレーションログのデータ使用量を登録(削除対象のデータ使用量を減算)
            MainUsedDataBiz usedDataBiz = new MainUsedDataBiz(con);
            usedDataBiz.insertOpLogDataSize(delCount, false);

            commit = true;

        } catch (SQLException e) {
            log__.error("オペレーションログの一括削除に失敗", e);
            throw e;

        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.delete"),
                GSConstLog.LEVEL_INFO, gsMsg.getMessage("man.purge"));

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @exception SQLException SQL実行時例外
     */
    private ActionForward __doDeleteKn(
        ActionMapping map,
        Man250Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("deleteOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("research").getPath());


        //メッセージセット
        RequestModel reqMdl = getRequestModel(req);
        Man250ParamModel paramMdl = new Man250ParamModel();
        paramMdl.setParam(form);
        Man250Biz biz = new Man250Biz(reqMdl);
        String condition = biz.getKakuninMessage(paramMdl, con, getPluginConfig(req));
        paramMdl.setFormData(form);

        String msgState = "sakujo.kakunin.list";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(reqMdl, "cmn.operations.log"), condition));

        //hiddenパラメータ設定
        __setHiddenParam(cmn999Form, form);

        req.setAttribute("cmn999Form", cmn999Form);

        //トランザクショントークン設定
        saveToken(req);

        return map.findForward("gf_msg");
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
        Man250Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("research");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        RequestModel reqMdl = getRequestModel(req);
        String msgState = "sakujo.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                              getInterMessage(reqMdl, "cmn.operations.log")));

        //hiddenパラメータ設定
        __setHiddenParam(cmn999Form, form);

        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     * @param form Man250Form
     * @return cmn999Form Cmn999Form
     */
    private Cmn999Form __setHiddenParam(Cmn999Form cmn999Form, Man250Form form) {

        cmn999Form.addHiddenParam("man250SltGroup", form.getMan250SltGroup());
        cmn999Form.addHiddenParam("man250SltUser", form.getMan250SltUser());
        cmn999Form.addHiddenParam("man250SltYearFr", form.getMan250SltYearFr());
        cmn999Form.addHiddenParam("man250SltMonthFr", form.getMan250SltMonthFr());
        cmn999Form.addHiddenParam("man250SltDayFr", form.getMan250SltDayFr());
        cmn999Form.addHiddenParam("man250SltHourFr", form.getMan250SltHourFr());
        cmn999Form.addHiddenParam("man250SltMinFr", form.getMan250SltMinFr());
        cmn999Form.addHiddenParam("man250SltYearTo", form.getMan250SltYearTo());
        cmn999Form.addHiddenParam("man250SltMonthTo", form.getMan250SltMonthTo());
        cmn999Form.addHiddenParam("man250SltDayTo", form.getMan250SltDayTo());
        cmn999Form.addHiddenParam("man250SltHourTo", form.getMan250SltHourTo());
        cmn999Form.addHiddenParam("man250SltMinTo", form.getMan250SltMinTo());
        cmn999Form.addHiddenParam("man250SltDateFr", form.getMan250SltDateFr());
        cmn999Form.addHiddenParam("man250SltTimeFr", form.getMan250SltTimeFr());
        cmn999Form.addHiddenParam("man250SltDateTo", form.getMan250SltDateTo());
        cmn999Form.addHiddenParam("man250SltTimeTo", form.getMan250SltTimeTo());
        cmn999Form.addHiddenParam("man250SltPlugin", form.getMan250SltPlugin());
        cmn999Form.addHiddenParam("man250SltLogError", form.getMan250SltLogError());
        cmn999Form.addHiddenParam("man250SltLogWarn", form.getMan250SltLogWarn());
        cmn999Form.addHiddenParam("man250SltLogInfo", form.getMan250SltLogInfo());
        cmn999Form.addHiddenParam("man250SltLogTrace", form.getMan250SltLogTrace());
        cmn999Form.addHiddenParam("man250OrderKey1", form.getMan250OrderKey1());
        cmn999Form.addHiddenParam("man250SortKey1", form.getMan250SortKey1());
        cmn999Form.addHiddenParam("man250OrderKey2", form.getMan250OrderKey2());
        cmn999Form.addHiddenParam("man250SortKey2", form.getMan250SortKey2());
        cmn999Form.addHiddenParam("man250SltPage1", form.getMan250SltPage1());
        cmn999Form.addHiddenParam("man250SltPage2", form.getMan250SltPage2());
        cmn999Form.addHiddenParam("man250PageNum", form.getMan250PageNum());

        cmn999Form.addHiddenParam("man250SvSltGroup", form.getMan250SvSltGroup());
        cmn999Form.addHiddenParam("man250SvSltUser", form.getMan250SvSltUser());
        cmn999Form.addHiddenParam("man250SvSltYearFr", form.getMan250SvSltYearFr());
        cmn999Form.addHiddenParam("man250SvSltMonthFr", form.getMan250SvSltMonthFr());
        cmn999Form.addHiddenParam("man250SvSltDayFr", form.getMan250SvSltDayFr());
        cmn999Form.addHiddenParam("man250SvSltHourFr", form.getMan250SvSltHourFr());
        cmn999Form.addHiddenParam("man250SvSltMinFr", form.getMan250SvSltMinFr());
        cmn999Form.addHiddenParam("man250SvSltYearTo", form.getMan250SvSltYearTo());
        cmn999Form.addHiddenParam("man250SvSltMonthTo", form.getMan250SvSltMonthTo());
        cmn999Form.addHiddenParam("man250SvSltDayTo", form.getMan250SvSltDayTo());
        cmn999Form.addHiddenParam("man250SvSltHourTo", form.getMan250SvSltHourTo());
        cmn999Form.addHiddenParam("man250SvSltMinTo", form.getMan250SvSltMinTo());
        cmn999Form.addHiddenParam("man250SvSltDateFr", form.getMan250SvSltDateFr());
        cmn999Form.addHiddenParam("man250SvSltTimeFr", form.getMan250SvSltTimeFr());
        cmn999Form.addHiddenParam("man250SvSltDateTo", form.getMan250SvSltDateTo());
        cmn999Form.addHiddenParam("man250SvSltTimeTo", form.getMan250SvSltTimeTo());
        cmn999Form.addHiddenParam("man250SvSltPlugin", form.getMan250SvSltPlugin());
        cmn999Form.addHiddenParam("man250SvSltLogError", form.getMan250SvSltLogError());
        cmn999Form.addHiddenParam("man250SvSltLogWarn", form.getMan250SvSltLogWarn());
        cmn999Form.addHiddenParam("man250SvSltLogInfo", form.getMan250SvSltLogInfo());
        cmn999Form.addHiddenParam("man250SvSltLogTrace", form.getMan250SvSltLogTrace());
        cmn999Form.addHiddenParam("man250SvOrderKey1", form.getMan250SvOrderKey1());
        cmn999Form.addHiddenParam("man250SvSortKey1", form.getMan250SvSortKey1());
        cmn999Form.addHiddenParam("man250SvOrderKey2", form.getMan250SvOrderKey2());
        cmn999Form.addHiddenParam("man250SvSortKey2", form.getMan250SvSortKey2());

        return cmn999Form;
    }
}
