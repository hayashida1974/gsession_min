package jp.groupsession.v2.man.man050;

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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.LoginHistorySearchModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 最終ログイン時間画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man050Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man050Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man050";

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <br>[機  能] アクションを実行します
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーム
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(
                ActionMapping map,
                ActionForm form,
                HttpServletRequest req,
                HttpServletResponse res,
                Connection con) throws Exception, SQLException {

        log__.debug("START Man050Action");

        //キャスト
        Man050Form thisForm = (Man050Form) form;
        //アクションフォーワード生成
        ActionForward forward = null;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("コマンド cmd = " + cmd);

        if (cmd.equals("menu")) {
            //管理者ツールメニュー
            forward = map.findForward("menu");
        } else if (cmd.equals("main")) {
            if (thisForm.getMan050Backurl() == 3) {
                //管理者設定画面からの遷移時
                forward = map.findForward("menu");
            } else {
                forward = map.findForward("main");
            }
        } else if (cmd.equals("detail")) {
            forward = map.findForward("detail");
        } else if (cmd.equals("initsearch")) {
            log__.debug("ログイン履歴検索タブクリック");
            thisForm.setMan050cmdMode(GSConstMain.MODE_SEARCH);
            forward = __doInitSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("list")) {
            log__.debug("最終ログイン時間タブクリック");
            thisForm.setMan050cmdMode(GSConstMain.MODE_LIST);
            thisForm.setMan050OrderKey(GSConst.ORDER_KEY_ASC);
            thisForm.setMan050SortKey(GSConstUser.USER_SORT_LALG);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("search")) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);
        } else  if (cmd.equals("pageleft")) {
            log__.debug("前ページボタン押下");
            thisForm.setMan050PageTop(thisForm.getMan050PageTop() - 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("pageright")) {
            log__.debug("次ページボタン押下");
            thisForm.setMan050PageTop(thisForm.getMan050PageTop() + 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward  = map.findForward("month");
        } else if (cmd.equals("man050export")) {
            log__.debug("エクスポートボタン押下");
            forward = __doDownLoad(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示を行います");
            if (thisForm.getMan050cmdMode().equals(GSConstMain.MODE_LIST)) {
                forward = __doInit(map, thisForm, req, res, con);
            } else {
                if (thisForm.getMan050SearchFlg() == GSConstUser.SEARCH_MI) {
                    forward = __doInitSearch(map, thisForm, req, res, con);
                } else {
                    forward = __doSearch(map, thisForm, req, res, con);
                }
            }
        }

        log__.debug("END Man050Action");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     */
    public ActionForward __doInit(
                    ActionMapping map,
                    Man050Form form,
                    HttpServletRequest req,
                    HttpServletResponse res,
                    Connection con) throws SQLException {

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel umodel = reqMdl.getSmodel();
        int sessionUserSid = umodel.getUsrsid();

        Man050ParamModel paramMdl = new Man050ParamModel();
        paramMdl.setParam(form);

        Man050Biz biz = new Man050Biz(reqMdl);
        //グループSIDがセットされていない場合、デフォルトグループSIDを取得します
        if (form.getMan050grpSid() == -1) {
            log__.debug("デフォルトグループIDを取得します。");
            int sid = getSessionUserSid(req);
            biz.setDefaultGroupSid(con, sid, paramMdl);
        }

        //グループ情報を取得
        boolean adminFlg = umodel.isAdmin();
        biz.setGroupModel(con, paramMdl, adminFlg, sessionUserSid);

        //グループSIDから所属ユーザ情報を取得､セットします
        biz.setMan050List(con, paramMdl);

        paramMdl.setFormData(form);

        //セッションユーザが管理者かを判定し、結果をフォームに設定します
        form.setMan050adminFlg(adminFlg);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ログイン履歴タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public ActionForward __doInitSearch(
            ActionMapping map,
            Man050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel umodel = reqMdl.getSmodel();
        int sessionUserSid = umodel.getUsrsid();

        Man050ParamModel paramMdl = new Man050ParamModel();
        paramMdl.setParam(form);

        Man050Biz biz = new Man050Biz(reqMdl);

        //グループSIDがセットされていない場合、デフォルトグループSIDを取得します
        if (form.getMan050grpSid() == -1) {
            log__.debug("デフォルトグループIDを取得します。");
            biz.setDefaultGroupSid(con, sessionUserSid, paramMdl);
        }

        //グループ情報を取得
        boolean adminFlg = umodel.isAdmin();
        biz.setGroupModel(con, paramMdl, adminFlg, sessionUserSid);

        biz.setSearchInitData(paramMdl, con);
        paramMdl.setFormData(form);

        //セッションユーザが管理者かを判定し、結果をフォームに設定します
        form.setMan050adminFlg(getSessionUserModel(req).isAdmin());
        form.setMan050OrderKey(GSConst.ORDER_KEY_ASC);
        form.setMan050SortKey(GSConstUser.USER_SORT_LALG);
        //未検索にする
        form.setMan050SearchFlg(GSConstUser.SEARCH_MI);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public ActionForward __doSearch(
            ActionMapping map,
            Man050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        RequestModel reqMdl = getRequestModel(req);
        Man050ParamModel paramMdl = new Man050ParamModel();
        paramMdl.setParam(form);

        con.setAutoCommit(true);
        Man050Biz biz = new Man050Biz(reqMdl);
        biz.setSearchInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //入力チェック&日付文字列から各パラメータへの設定
        ActionErrors errors = form.validateCheck(req);
        
        ActionForward forward = __doInit(map, form, req, res, con);

        //入力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return forward;
        }
        //検索済みにする
        form.setMan050SearchFlg(GSConstUser.SEARCH_ZUMI);

        return forward;
    }

    /**
     * エクスポート処理実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doDownLoad(ActionMapping map,
                                        Man050Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        //管理者以外はエクスポート不可
        if (!reqMdl.getSmodel().isAdmin()) {
            return getSubmitErrorPage(map, req);
        }
        //検索条件をサーチモデルにセット
        LoginHistorySearchModel searchMdl = setSearchData(form);

        GsMessage gsMsg = new GsMessage();
//        //グループ情報
//        String textGroupInfo = gsMsg.getMessage(req, "user.src.59");

        log__.debug("エクスポート処理実行");
        //テンポラリディレクトリパスを取得
        CommonBiz cmnBiz = new CommonBiz();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        //CSVファイルを作成
        String fileName = LoginHistoryCsvWriter.FILE_NAME_LIST;

        if (form.getMan050cmdMode().equals(GSConstMain.MODE_SEARCH)) {
            fileName = LoginHistoryCsvWriter.FILE_NAME_SEARCH;
        }

        LoginHistoryCsvWriter write = new LoginHistoryCsvWriter(reqMdl, searchMdl);
        write.outputCsv(con, tempDir);

        String fullPath = tempDir + fileName;
        //ダウンロード
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage(req, "cmn.export");

        //ログ出力
        String dspName = gsMsg.getMessage("cmn.last.2");
        cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                export,
                GSConstLog.LEVEL_INFO, fileName, dspName);
        return null;
    }

    /**
     * <br>[機  能] フォームからわたってくる検索条件をサーチモデルにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param form LoginHistorySearchModel
     * @return serchMdl サーチモデル
     */
    private LoginHistorySearchModel setSearchData(Man050Form form) {

        LoginHistorySearchModel searchModel = new LoginHistorySearchModel();

        int grpSid = form.getMan050grpSid();
        int sortKey = form.getMan050SortKey();
        int orderKey = form.getMan050OrderKey();

        searchModel.setGsid(grpSid);
        searchModel.setSortKey(sortKey);
        searchModel.setOrderKey(orderKey);

        if (form.getMan050cmdMode().equals(GSConstMain.MODE_LIST)) {

            searchModel.setMode(Integer.valueOf(GSConstMain.MODE_LIST));

        } else {

            searchModel.setMode(Integer.valueOf(GSConstMain.MODE_SEARCH));

            int uSid = form.getMan050usrSid();
            int terminal = form.getMan050Terminal();
            int car = form.getMan050Car();

            String strFrDate = form.getMan050FrYear() + "/";
            strFrDate += form.getMan050FrMonth() + "/";
            strFrDate += form.getMan050FrDay();

            UDate frDate = UDate.getInstanceStr(strFrDate);
            frDate.setZeroHhMmSs();

            String strToDate = form.getMan050ToYear() + "/";
            strToDate += form.getMan050ToMonth() + "/";
            strToDate += form.getMan050ToDay();

            UDate toDate = UDate.getInstanceStr(strToDate);
            toDate.setMaxHhMmSs();


            searchModel.setUsid(uSid);

            searchModel.setTerminal(terminal);
            searchModel.setCar(car);
            searchModel.setFrDate(frDate);
            searchModel.setToDate(toDate);
        }

        return searchModel;
    }
}
