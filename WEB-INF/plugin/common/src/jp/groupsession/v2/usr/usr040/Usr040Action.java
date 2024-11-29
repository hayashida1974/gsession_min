package jp.groupsession.v2.usr.usr040;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.AbstractUsrAction;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.usr041.Usr041Biz;

/**
 * <br>[機  能] ユーザ情報一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr040Action extends AbstractUsrAction {

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "usr040";

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

        if (cmd.equals("usr040export")) {
            log__.debug("エクスポート");
            return true;

        }
        return false;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr040Action.class);

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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        Usr040Form usr040Form = (Usr040Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        RequestModel reqMdl = getRequestModel(req);
        Usr040Biz biz = new Usr040Biz(reqMdl);
        BaseUserModel smodel = getSessionUserModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, smodel, GSConstUser.PLUGIN_ID_USER);

        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(usr040Form);
        biz.setLabelEditBtn(paramMdl, con, adminUser);
        //アドレス一覧への遷移設定(webmail)用
        if (paramMdl.getUsr040webmail() == 1) {

            //アドレス帳一覧への遷移可能判定
            PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
            paramMdl.setUsr040webmailCanUseAddress(cmnBiz.isCanUsePlugin(GSConst.PLUGINID_ADDRESS,
                    pconfig));
        }

        paramMdl.setFormData(usr040Form);

        if (cmd.equals("name")) {
            //氏名タブクリック
            //詳細検索実行フラグ
            usr040Form.setUsr040DetailExeFlg(0);
            forward = __doNameTab(map, usr040Form, req, res, con);
        } else if (cmd.equals("group")) {
            //グループタブクリック
            //詳細検索実行フラグ
            usr040Form.setUsr040DetailExeFlg(0);
            usr040Form.setUsr040GrpSearchGIdSave("");
            usr040Form.setUsr040GrpSearchGNameSave("");
            forward = __doGroupTab(map, usr040Form, req, res, con);
        } else if (cmd.equals("shousai")) {
            //詳細タブクリック
            //詳細検索実行フラグ
            usr040Form.setUsr040DetailExeFlg(0);
            usr040Form.setUsr040KeyKbnName(1);
            forward = __doShousaiTab(map, usr040Form, req, res, con);
        } else if (cmd.equals("search")) {
            //検索
            usr040Form.setUsr040pageNum1(1);
            forward = __doSearch(map, usr040Form, req, res, con);
        } else if (cmd.equals("searchKn")) {
            //カナインデックスクリック(検索)
            usr040Form.setUsr040pageNum1(1);
            forward = __doSearchKana(map, usr040Form, req, res, con);
        } else if (cmd.equals("selectgroup")) {
            //グループ検索
            usr040Form.setUsr040pageNum1(1);
            forward = __doSearchGroup(map, usr040Form, req, res, con);
        } else if (cmd.equals("searchSyosai")) {
            //詳細検索実行
            usr040Form.setUsr040pageNum1(1);
            usr040Form.setUsr040DetailExeFlg(1);
            forward = __doSearchSyosai(map, usr040Form, req, res, con);
        } else if (cmd.equals("arrorw_right")) {
            //次のページ
            usr040Form.setUsr040saveFlg(1);
            forward = __doNext(map, usr040Form, req, res, con);
        } else if (cmd.equals("arrorw_left")) {
            //前のページ
            usr040Form.setUsr040saveFlg(1);
            forward = __doPrev(map, usr040Form, req, res, con);
        } else if (cmd.equals("psetting")) {
            //個人設定
            forward = __doPsetting(map, usr040Form, req, res, con);
        } else if (cmd.equals("asetting")) {
            //個人設定
            forward = __doAsetting(map, usr040Form, req, res, con);
        } else if (cmd.equals("usr040export")) {
            //エクスポート
            forward = __doExport(map, usr040Form, req, res, con);
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward = __doScheduleMonth(map, usr040Form, req, res, con);
        } else if (cmd.equals("setLabel")) {
            //ユーザラベル設定
            //権限チェック
            forward = checkPow(map, req, con);
            if (forward != null) {
                return forward;
            }
            forward = __doUsrLabSet(map, usr040Form, req, res, con);
        } else if (cmd.equals("labelEdit")) {
            //ユーザラベル編集
            forward = map.findForward("labelEdit");

        } else if (cmd.equals("addUsrAtesaki")
                || cmd.equals("addUsrCc")
                || cmd.equals("addUsrBcc")) {
            //宛先・TO・BCCボタン
            forward = __doSendAddressUsr(map, usr040Form, req, res, con);
            //宛先削除リンククリック
        } else if (cmd.equals("deleteSend")) {
            log__.debug("宛先削除リンククリック");
            forward = __doDeleteUser(map, usr040Form, req, res, con);
        } else if (cmd.equals("grpSearch")) {
            //グループの絞込みボタン押下
            forward = __setGrpSearch(map, usr040Form, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, usr040Form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期パラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("初期表示処理");
        ActionForward forward = null;

        int mode = form.getUsr040cmdMode();
        int serchFlg = form.getUsr040SearchFlg();
        Usr040Biz biz = new Usr040Biz(getRequestModel(req));

        //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
        if (form.getUsr040webmail() == 1) {
            form.setUsr040AtskList(biz.getSelUsrList(form.getUsr040SidsAtsk(), con));
            form.setUsr040CcList(biz.getSelUsrList(form.getUsr040SidsCc(), con));
            form.setUsr040BccList(biz.getSelUsrList(form.getUsr040SidsBcc(), con));
        }

        Usr040ParamModel paramMdl = new Usr040ParamModel();
        //詳細検索実行フラグ
        form.setUsr040DetailExeFlg(0);
        if (form.getUsr040DspFlg() == 0) {

            //ソート順をセットする
            con.setAutoCommit(true);

            paramMdl.setParam(form);
            biz.setSort(paramMdl, con, getSessionUserModel(req));
            paramMdl.setFormData(form);

            con.setAutoCommit(false);

            //初期表示フラグを１にセット
            form.setUsr040DspFlg(1);
        }

        //カテゴリー一覧の開閉フラグの初期値を設定する
        if (form.getUsr040CategorySetInitFlg() == 0) {
            paramMdl.setParam(form);
            biz.setCategoryOpenFlg(paramMdl, con);
            paramMdl.setFormData(form);
            form.setUsr040CategorySetInitFlg(1);
        }

        //ベースURL取得
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        String url = urlBiz.getBaseUrl(getRequestModel(req));

        form.setUsr040BaseUrl(url);

        if (mode == GSConstUser.MODE_NAME) {
            //氏名検索
            if (serchFlg == GSConstUser.SEARCH_ZUMI) {
                //検索実行済みの場合
                forward = __doSearchKana(map, form, req, res, con);
            } else {
                //未検索の場合
                forward = __doNameTab(map, form, req, res, con);
            }
        } else if (mode == GSConstUser.MODE_GROUP) {
            //グループ
            if (serchFlg == GSConstUser.SEARCH_ZUMI) {
                //検索実行済みの場合
                forward = __doSearchGroup(map, form, req, res, con);
            } else {
                //未検索の場合
                forward = __doGroupTab(map, form, req, res, con);
            }
        } else if (mode == GSConstUser.MODE_SHOUSAI) {
            //詳細
            if (serchFlg == GSConstUser.SEARCH_ZUMI) {
                //検索実行済みの場合
                forward = __doSearchSyosai(map, form, req, res, con);
            } else {
                //未検索の場合
                forward = __doShousaiTab(map, form, req, res, con);
            }
        }


        return forward;
    }

    /**
     * 月間スケジュールへ遷移
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doScheduleMonth(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {
        ActionForward forward = null;
        forward = map.findForward("month");
        return forward;
    }

    /**
     * <br>[機  能] エクスポート処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("エクスポート処理");
        ActionForward forward = null;

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstUser.PLUGIN_ID_USER, TEMP_DIRECTORY_ID);
        String fileName = UsrCsvWriterIppan.FILE_NAME;
        String fullPath = tempDir + fileName;

        con.setAutoCommit(true);
        if (form.getUsr040cmdMode() == GSConstUser.MODE_NAME) {
            //氏名
            forward = __doExportName(map, form, req, res, con, tempDir);
        } else if (form.getUsr040cmdMode() == GSConstUser.MODE_GROUP) {
            //グループ
            forward = __doExportGroup(map, form, req, res, con, tempDir);
        } else if (form.getUsr040cmdMode() == GSConstUser.MODE_SHOUSAI) {
            //詳細
            forward = __doExportShousai(map, form, req, res, con, tempDir);
        }
        con.setAutoCommit(false);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstUser.PLUGIN_ID_USER, TEMP_DIRECTORY_ID);

        //初期設定
        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage(req, "cmn.export");

        //ログ出力処理
        UsrCommonBiz usrBiz = new UsrCommonBiz(con, getRequestModel(req));
        usrBiz.outPutLog(
                export, GSConstLog.LEVEL_INFO, fileName, map.getType());
        return forward;
    }

    /**
     * <br>[機  能] エクスポート処理を実行(氏名カナ)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExportName(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {

        log__.debug("エクスポート処理(氏名)");

        //検索条件をセット(SAVEより)
        ShainSearchModel searchModel = new ShainSearchModel();
        searchModel.setSearchKana(form.getUsr040SearchKanaSave());
        //ソート
        searchModel.setSortKey(form.getUsr040sortKeySave());
        searchModel.setSortOrder(form.getUsr040orderKeySave());
        searchModel.setSortKey2(form.getUsr040sortKey2Save());
        searchModel.setSortOrder2(form.getUsr040orderKey2Save());
        //MODE
        searchModel.setOutputCsvMode(form.getUsr040cmdMode());
        //ラベル
        searchModel.setLabelSid(form.getUsr040labSidSave());

        //性別
        searchModel.setSeibetu(form.getUsr040seibetuSave());

        UDate date1 = null;

        //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(Integer.parseInt(form.getUsr040entranceMonthFrSave()));
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(1);
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {
            date1 = new UDate();
            date1.setDate(
                    Integer.parseInt(form.getUsr040entranceYearFrSave()),
                    Integer.parseInt(form.getUsr040entranceMonthFrSave()),
                    Integer.parseInt(form.getUsr040entranceDayFrSave()));
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }


        UDate date2 = null;

      //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(Integer.parseInt(form.getUsr040entranceMonthToSave()));
            date2.setDay(1);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(12);
            date2.setDay(31);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {
            date2 = new UDate();
            date2.setDate(
                    Integer.parseInt(form.getUsr040entranceYearToSave()),
                    Integer.parseInt(form.getUsr040entranceMonthToSave()),
                    Integer.parseInt(form.getUsr040entranceDayToSave()));
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
          }

        //CSVファイルを作成
        UsrCsvWriterIppan write = new UsrCsvWriterIppan(con, getRequestModel(req));
        write.setSearchModel(searchModel);
        write.outputCsv(con, outDir);

        return null;
    }

    /**
     * <br>[機  能] エクスポート処理を実行(グループ)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExportGroup(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {
        log__.debug("エクスポート処理(グループ)");

        //検索条件作成
        ShainSearchModel searchModel = new ShainSearchModel();
        searchModel.setSelectgsid(form.getSelectgsidSave());
        //ソート
        searchModel.setSortKey(form.getUsr040sortKeySave());
        searchModel.setSortOrder(form.getUsr040orderKeySave());
        searchModel.setSortKey2(form.getUsr040sortKey2Save());
        searchModel.setSortOrder2(form.getUsr040orderKey2Save());
        //MODE
        searchModel.setOutputCsvMode(form.getUsr040cmdMode());
        //ラベル
        searchModel.setLabelSid(form.getUsr040labSidSave());
        //性別
        searchModel.setSeibetu(form.getUsr040seibetuSave());
        UDate date1 = null;

        //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(Integer.parseInt(form.getUsr040entranceMonthFrSave()));
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(1);
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {
            date1 = new UDate();
            date1.setDate(
                    Integer.parseInt(form.getUsr040entranceYearFrSave()),
                    Integer.parseInt(form.getUsr040entranceMonthFrSave()),
                    Integer.parseInt(form.getUsr040entranceDayFrSave()));
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }


        UDate date2 = null;

      //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(Integer.parseInt(form.getUsr040entranceMonthToSave()));
            date2.setDay(1);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(12);
            date2.setDay(31);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {
            date2 = new UDate();
            date2.setDate(
                    Integer.parseInt(form.getUsr040entranceYearToSave()),
                    Integer.parseInt(form.getUsr040entranceMonthToSave()),
                    Integer.parseInt(form.getUsr040entranceDayToSave()));
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
          }

        //CSVファイルを作成
        UsrCsvWriterIppan write = new UsrCsvWriterIppan(con, getRequestModel(req));
        write.setSearchModel(searchModel);
        write.outputCsv(con, outDir);

        return null;
    }

    /**
     * <br>[機  能] エクスポート処理を実行(詳細)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExportShousai(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {
        log__.debug("エクスポート処理(詳細)");

        //検索条件作成
        ShainSearchModel searchModel = new ShainSearchModel();
        searchModel.setSelectgsid(form.getSelectgsidSave());
        String escKeyword = __getEscKeyword(form.getUsr040KeywordSave());
        searchModel.setKeyword(escKeyword);
        searchModel.setKeyKbnShainno(form.getUsr040KeyKbnShainnoSave());
        searchModel.setKeyKbnName(form.getUsr040KeyKbnNameSave());
        searchModel.setKeyKbnNameKn(form.getUsr040KeyKbnNameKnSave());
        searchModel.setKeyKbnMail(form.getUsr040KeyKbnMailSave());
        searchModel.setKeyKbnTel(form.getUsr040KeyKbnTelSave());
        searchModel.setAgefrom(form.getUsr040agefromSave());
        searchModel.setAgeto(form.getUsr040agetoSave());
        searchModel.setYakushoku(form.getUsr040yakushokuSave());
        searchModel.setTdfkCd(form.getUsr040tdfkCdSave());
        searchModel.setSeibetu(form.getUsr040seibetuSave());

        UDate date1 = null;

        //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(Integer.parseInt(form.getUsr040entranceMonthFrSave()));
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(1);
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {
            date1 = new UDate();
            date1.setDate(
                    Integer.parseInt(form.getUsr040entranceYearFrSave()),
                    Integer.parseInt(form.getUsr040entranceMonthFrSave()),
                    Integer.parseInt(form.getUsr040entranceDayFrSave()));
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }


        UDate date2 = null;

      //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(Integer.parseInt(form.getUsr040entranceMonthToSave()));
            date2.setDay(1);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(12);
            date2.setDay(31);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {
            date2 = new UDate();
            date2.setDate(
                    Integer.parseInt(form.getUsr040entranceYearToSave()),
                    Integer.parseInt(form.getUsr040entranceMonthToSave()),
                    Integer.parseInt(form.getUsr040entranceDayToSave()));
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
          }

        //ソート
        searchModel.setSortKey(form.getUsr040sortKeySave());
        searchModel.setSortOrder(form.getUsr040orderKeySave());
        searchModel.setSortKey2(form.getUsr040sortKey2Save());
        searchModel.setSortOrder2(form.getUsr040orderKey2Save());
        //MODE
        searchModel.setOutputCsvMode(form.getUsr040cmdMode());
        searchModel.setLabelSid(form.getUsr040labSidSave());

        //CSVファイルを作成
        UsrCsvWriterIppan write = new UsrCsvWriterIppan(con, getRequestModel(req));
        write.setSearchModel(searchModel);
        write.outputCsv(con, outDir);

        return null;
    }

    /**
     * <br>[機  能] 氏名タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doNameTab(
        ActionMapping map,
        Usr040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("氏名タブクリック");

        //カナインデックス作成
        con.setAutoCommit(true);
        Usr040Biz biz = new Usr040Biz(getRequestModel(req));

        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        biz.setKanaIndex(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        //ソートコンボ
        biz.setSortCombo(paramMdl);

        //カテゴリー・ラベルデータ取得
        biz.setCategoryLabelData(paramMdl, con);

        paramMdl.setFormData(form);


        //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
        if (form.getUsr040webmail() == 1) {
            form.setUsr040AtskList(biz.getSelUsrList(form.getUsr040SidsAtsk(), con));
            form.setUsr040CcList(biz.getSelUsrList(form.getUsr040SidsCc(), con));
            form.setUsr040BccList(biz.getSelUsrList(form.getUsr040SidsBcc(), con));
        }

        form.setUsr040cmdMode(GSConstUser.MODE_NAME);
        form.setUsr040SearchFlg(GSConstUser.SEARCH_MI);

        //初期設定
        paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] グループタブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doGroupTab(
        ActionMapping map,
        Usr040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("グループタブクリック");
        form.setUsr040cmdMode(GSConstUser.MODE_GROUP);

        Usr040Biz biz = new Usr040Biz(getRequestModel(req));

        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        //ソートコンボ
        biz.setSortCombo(paramMdl);

        //カテゴリー・ラベルデータ取得
        biz.setCategoryLabelData(paramMdl, con);

        paramMdl.setFormData(form);

        //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
        if (form.getUsr040webmail() == 1) {
            form.setUsr040AtskList(biz.getSelUsrList(form.getUsr040SidsAtsk(), con));
            form.setUsr040CcList(biz.getSelUsrList(form.getUsr040SidsCc(), con));
            form.setUsr040BccList(biz.getSelUsrList(form.getUsr040SidsBcc(), con));
        }

        //選択グループ初期化
        form.setSelectgsid(-1);

        form.setUsr040SearchFlg(GSConstUser.SEARCH_MI);

        //初期設定
        paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 詳細検索タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doShousaiTab(
        ActionMapping map,
        Usr040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("詳細タブクリック");

        Usr040Biz biz = new Usr040Biz(getRequestModel(req));

        //ソートコンボ
        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        biz.setSortCombo(paramMdl);
        paramMdl.setFormData(form);

        form.setUsr040cmdMode(GSConstUser.MODE_SHOUSAI);

        //画面に常に表示する値をセットする
        con.setAutoCommit(true);

        paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        biz.setDspData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
        if (form.getUsr040webmail() == 1) {
            form.setUsr040AtskList(biz.getSelUsrList(form.getUsr040SidsAtsk(), con));
            form.setUsr040CcList(biz.getSelUsrList(form.getUsr040SidsCc(), con));
            form.setUsr040BccList(biz.getSelUsrList(form.getUsr040SidsBcc(), con));
        }

        form.setUsr040SearchFlg(GSConstUser.SEARCH_MI);

        //初期設定
        paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <p>検索
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doSearch(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;
        log__.debug("START");
        log__.debug("検索");

        if (form.getUsr040cmdMode() == GSConstUser.MODE_NAME) {
            //氏名
            forward = __doSearchKana(map, form, req, res, con);
        } else if (form.getUsr040cmdMode() == GSConstUser.MODE_GROUP) {
            //グループ
            forward = __doSearchGroup(map, form, req, res, con);
        } else if (form.getUsr040cmdMode() == GSConstUser.MODE_SHOUSAI) {
            //詳細
            forward = __doSearchSyosai(map, form, req, res, con);
        }

        //初期設定
        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        log__.debug("END");
        return forward;
    }

    /**
     * <p>カナ検索
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doSearchKana(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        ActionForward forward = null;

        try {
            log__.debug("START");
            log__.debug("カナ検索");
            //検索済にする
            form.setUsr040SearchFlg(GSConstUser.SEARCH_ZUMI);

            Usr040ParamModel paramMdl = new Usr040ParamModel();
            paramMdl.setParam(form);
            //初期設定
            Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
            //ソートコンボ
            Usr040Biz biz = new Usr040Biz(getRequestModel(req));
            biz.setSortCombo(paramMdl);

            //カテゴリー・ラベルデータ取得
            biz.setCategoryLabelData(paramMdl, con);

            paramMdl.setFormData(form);

            //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
            if (form.getUsr040webmail() == 1) {
                form.setUsr040AtskList(biz.getSelUsrList(form.getUsr040SidsAtsk(), con));
                form.setUsr040CcList(biz.getSelUsrList(form.getUsr040SidsCc(), con));
                form.setUsr040BccList(biz.getSelUsrList(form.getUsr040SidsBcc(), con));
            }

            //画面設定
            BaseUserModel smodel = getSessionUserModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(con, smodel, GSConstUser.PLUGIN_ID_USER);

            paramMdl = new Usr040ParamModel();
            paramMdl.setParam(form);
            biz.setExportBtn(paramMdl, con, adminUser);
            biz.setLabelSetBtn(paramMdl, con, adminUser);
            biz.setLabelEditBtn(paramMdl, con, adminUser);

            //五十音リストの作成
            biz.setKanaIndex(paramMdl, con);

            paramMdl.setFormData(form);

            if (form.getUsr040saveFlg() == 0) {
                ActionErrors errors = form.validateCheckDoubleSort(map, getRequestModel(req));
                if (!errors.isEmpty()) {
                    //エラーあり
                    addErrors(req, errors);
                    form.setUsr040saveFlg(1);
                    forward = __doInit(map, form, req, res, con);
                    return forward;
                }

                //検索条件を保存
                form.setSelectgsidSave(-1); //GSID
                form.setUsr040SearchKanaSave(form.getUsr040SearchKana()); //カナ
                form.setUsr040agefromSave(null); //年齢FROM
                form.setUsr040agetoSave(null); //年齢TO
                form.setUsr040yakushokuSave(GSConstCommon.NUM_INIT); //役職
                form.setUsr040tdfkCdSave(null); //都道府県コード
                form.setUsr040seibetuSave(String.valueOf(GSConstCommon.NUM_INIT)); //性別
                form.setUsr040entranceYearFrSave(""); //入社年月日
                form.setUsr040entranceMonthFrSave(""); //入社年月日
                form.setUsr040entranceDayFrSave(""); //入社年月日
                form.setUsr040entranceYearToSave(""); //入社年月日
                form.setUsr040entranceMonthToSave(""); //入社年月日
                form.setUsr040entranceDayToSave(""); //入社年月日

                form.setUsr040sortKeySave(form.getUsr040sortKey());
                form.setUsr040orderKeySave(form.getUsr040orderKey());
                form.setUsr040sortKey2Save(form.getUsr040sortKey2());
                form.setUsr040orderKey2Save(form.getUsr040orderKey2());
                form.setUsr040labSidSave(form.getUsr040labSid());
            }

            form.setUsr040saveFlg(0);

            UserSearchDao udao = new UserSearchDao(con);
            String kanaKey = form.getUsr040SearchKanaSave();
            int sortKey = form.getUsr040sortKeySave();
            int orderKey = form.getUsr040orderKeySave();
            int sortKey2 = form.getUsr040sortKey2Save();
            int orderKey2 = form.getUsr040orderKey2Save();

            log__.debug("kanaKey :" + kanaKey);
            log__.debug("sortKey :" + sortKey);
            log__.debug("orderKey :" + orderKey);
            log__.debug("sortKey2 :" + sortKey2);
            log__.debug("orderKey2 :" + orderKey2);

            //設定された表示件数を取得
            Usr041Biz biz41 = new Usr041Biz(getRequestModel(req));
            BaseUserModel umodel = getSessionUserModel(req);
            int limit = biz41.getDspCnt(umodel.getUsrsid(), con);
            int nowPage = form.getUsr040pageNum1();
            int start = PageUtil.getRowNumber(nowPage, limit);
            //カウント取得
            int maxCount = 0;
            ShainSearchModel searchModel = __getSearchModel(form);
            if (kanaKey.equals("")) {
                maxCount = udao.getSyousaiSearchCount(searchModel);
            } else {
                maxCount = udao.getUserKanaIndex2Count(kanaKey, form.getUsr040labSidSave());
            }

            log__.debug("件数 = " + maxCount);

            //ページあふれ制御
            int maxPageNum = PageUtil.getPageCount(maxCount, limit);
            int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);

            log__.debug("nowPage1 = " + nowPage);
            log__.debug("start1 = " + start);

            if (maxPageStartRow < start) {
                nowPage = maxPageNum;
                start = maxPageStartRow;
            }

            form.setUsr040PageLabel(PageUtil.createPageOptions(maxCount, limit));
            form.setUsr040pageNum1(nowPage);
            form.setUsr040pageNum2(nowPage);

            //検索実行
            boolean sortFlg = true;
            if (sortKey == GSConstCommon.NUM_INIT || orderKey == GSConstCommon.NUM_INIT
                    || sortKey2 == GSConstCommon.NUM_INIT || orderKey2 == GSConstCommon.NUM_INIT) {
                sortFlg = false;
            }

            if (kanaKey != null && sortFlg) {
                if (kanaKey.equals("")) {
                    List<CmnUsrmInfModel> ulist =
                        udao.getSyousaiSearchList(searchModel, start, limit);
                    form.setUsr040users(__getDspMdlList(con, ulist));

                } else {
                    List<CmnUsrmInfModel> ulist =
                        udao.getUserKanaIndex2(kanaKey, sortKey,
                                orderKey, sortKey2, orderKey2,
                                start, limit, form.getUsr040labSidSave());
                    form.setUsr040users(__getDspMdlList(con, ulist));
                }
            }
            if (form.getUsr040users() == null || form.getUsr040users().size() < 1) {
                form.setUsr040SearchKanaSave(null);
            }

            //エスケープ処理＋wbrタグの挿入
            String escJouken = StringUtilHtml.transToHTmlWithWbr(
                    __getStrJoukenLabel(con, form, req), 10);

            //検索条件文字列(ラベル)を設定
            form.setUsr040DispKensakuJouken(escJouken);


            forward = map.getInputForward();

            log__.debug("END");
        } finally {
            con.setAutoCommit(false);
        }

        return forward;
    }

    /**
     * <br>[機  能] セーブパラメータより検索条件モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param form Usr040Form
     * @return ユーザ情報検索モデル
     */
    private ShainSearchModel __getSearchModel(Usr040Form form) {
        ShainSearchModel searchModel = new ShainSearchModel();
        searchModel.setSelectgsid(form.getSelectgsidSave());
        searchModel.setAgefrom(form.getUsr040agefromSave());
        searchModel.setAgeto(form.getUsr040agetoSave());
        searchModel.setYakushoku(form.getUsr040yakushokuSave());
        searchModel.setTdfkCd(form.getUsr040tdfkCdSave());
        searchModel.setSortKey(form.getUsr040sortKeySave());
        searchModel.setSortOrder(form.getUsr040orderKeySave());
        searchModel.setSortKey2(form.getUsr040sortKey2Save());
        searchModel.setSortOrder2(form.getUsr040orderKey2Save());
        searchModel.setLabelSid(form.getUsr040labSidSave());
        searchModel.setSeibetu(form.getUsr040seibetuSave());
        UDate date1 = null;

        //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(Integer.parseInt(form.getUsr040entranceMonthFrSave()));
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

            date1 = new UDate();
            date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
            date1.setMonth(1);
            date1.setDay(1);
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
              && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {
            date1 = new UDate();
            date1.setDate(
                    Integer.parseInt(form.getUsr040entranceYearFrSave()),
                    Integer.parseInt(form.getUsr040entranceMonthFrSave()),
                    Integer.parseInt(form.getUsr040entranceDayFrSave()));
            date1.setZeroHhMmSs();
            searchModel.setEntranceDateFr(date1);
        }


        UDate date2 = null;

              //年月のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(Integer.parseInt(form.getUsr040entranceMonthToSave()));
            date2.setDay(1);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
            && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

            date2 = new UDate();
            date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
            date2.setMonth(12);
            date2.setDay(31);
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
        }

        if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
             && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {
            date2 = new UDate();
            date2.setDate(
                    Integer.parseInt(form.getUsr040entranceYearToSave()),
                    Integer.parseInt(form.getUsr040entranceMonthToSave()),
                    Integer.parseInt(form.getUsr040entranceDayToSave()));
            date2.setZeroHhMmSs();
            searchModel.setEntranceDateTo(date2);
          }

        return searchModel;
    }

    /**
     * <p>所属グループ検索
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doSearchGroup(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        ActionForward forward = null;

        try {
            log__.debug("グループ検索");
            //検索済にする
            form.setUsr040SearchFlg(GSConstUser.SEARCH_ZUMI);

            Usr040ParamModel paramMdl = new Usr040ParamModel();
            paramMdl.setParam(form);

            //初期設定
            Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
            //ソートコンボ
            Usr040Biz biz = new Usr040Biz(getRequestModel(req));
            biz.setSortCombo(paramMdl);

            //カテゴリー・ラベルデータ取得
            biz.setCategoryLabelData(paramMdl, con);

            paramMdl.setFormData(form);


            //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
            if (form.getUsr040webmail() == 1) {
                form.setUsr040AtskList(biz.getSelUsrList(form.getUsr040SidsAtsk(), con));
                form.setUsr040CcList(biz.getSelUsrList(form.getUsr040SidsCc(), con));
                form.setUsr040BccList(biz.getSelUsrList(form.getUsr040SidsBcc(), con));
            }

            //画面設定
            BaseUserModel smodel = getSessionUserModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(con, smodel, GSConstUser.PLUGIN_ID_USER);


            paramMdl = new Usr040ParamModel();
            paramMdl.setParam(form);
            biz.setExportBtn(paramMdl, con, adminUser);
            biz.setLabelSetBtn(paramMdl, con, adminUser);
            biz.setLabelEditBtn(paramMdl, con, adminUser);
            paramMdl.setFormData(form);


            if (form.getUsr040saveFlg() == 0) {
                ActionErrors errors = form.validateCheckDoubleSort(map, getRequestModel(req));
                if (!errors.isEmpty()) {
                    //エラーあり
                    addErrors(req, errors);
                    form.setUsr040saveFlg(1);
                    forward = __doInit(map, form, req, res, con);
                    return forward;
                }

                //検索条件を保存
                form.setSelectgsidSave(form.getSelectgsid()); //GSID
                form.setUsr040SearchKanaSave(null); //カナ
                form.setUsr040agefromSave(null); //年齢FROM
                form.setUsr040agetoSave(null); //年齢TO
                form.setUsr040yakushokuSave(GSConstCommon.NUM_INIT); //役職
                form.setUsr040tdfkCdSave(null); //都道府県コード
                form.setUsr040seibetuSave(String.valueOf(GSConstCommon.NUM_INIT)); //性別
                form.setUsr040entranceYearFrSave(""); //入社年月日
                form.setUsr040entranceMonthFrSave(""); //入社年月日
                form.setUsr040entranceDayFrSave(""); //入社年月日
                form.setUsr040entranceYearToSave(""); //入社年月日
                form.setUsr040entranceMonthToSave(""); //入社年月日
                form.setUsr040entranceDayToSave(""); //入社年月日
                form.setUsr040sortKeySave(form.getUsr040sortKey());
                form.setUsr040orderKeySave(form.getUsr040orderKey());
                form.setUsr040sortKey2Save(form.getUsr040sortKey2());
                form.setUsr040orderKey2Save(form.getUsr040orderKey2());
                form.setUsr040labSidSave(form.getUsr040labSid());

            }
            form.setUsr040saveFlg(0);

            int gsid = form.getSelectgsidSave();
            int sortKey = form.getUsr040sortKeySave();
            int orderKey = form.getUsr040orderKeySave();
            int sortKey2 = form.getUsr040sortKey2Save();
            int orderKey2 = form.getUsr040orderKey2Save();

            log__.debug("GSID :" + gsid);
            log__.debug("sortKey :" + sortKey);
            log__.debug("orderKey :" + orderKey);
            log__.debug("sortKey2 :" + sortKey2);
            log__.debug("orderKey2 :" + orderKey2);

            UserSearchDao udao = new UserSearchDao(con);
            //設定された表示件数を取得
            Usr041Biz biz41 = new Usr041Biz(getRequestModel(req));
            BaseUserModel umodel = getSessionUserModel(req);
            int limit = biz41.getDspCnt(umodel.getUsrsid(), con);
            int nowPage = form.getUsr040pageNum1();
            int start = PageUtil.getRowNumber(nowPage, limit);
            //カウント取得
            int maxCount = 0;
            //検索条件作成
            ShainSearchModel searchModel =  __getSearchModel(form);
            if (gsid == -1) {
                maxCount = udao.getSyousaiSearchCount(searchModel);
            } else {
                maxCount = udao.getBelongUserCount(gsid, null, form.getUsr040labSidSave());
            }
            log__.debug("件数 = " + maxCount);

            //ページあふれ制御
            int maxPageNum = PageUtil.getPageCount(maxCount, limit);
            int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
            if (maxPageStartRow < start) {
                nowPage = maxPageNum;
                start = maxPageStartRow;
            }
            form.setUsr040PageLabel(PageUtil.createPageOptions(maxCount, limit));
            form.setUsr040pageNum1(nowPage);
            form.setUsr040pageNum2(nowPage);

            //検索実行
            boolean sortFlg = true;
            if (sortKey == GSConstCommon.NUM_INIT || orderKey == GSConstCommon.NUM_INIT
                    || sortKey2 == GSConstCommon.NUM_INIT || orderKey2 == GSConstCommon.NUM_INIT) {
                sortFlg = false;
            }

            //検索実行
            if (gsid == -1 && sortFlg) {

                List<CmnUsrmInfModel> ulist =
                    udao.getSyousaiSearchList(searchModel, start, limit);
                form.setUsr040users(__getDspMdlList(con, ulist));

            } else if (gsid != -1 && sortFlg) {
                //個人情報フラグを適用し、ユーザを検索
                ShainSearchModel searchMdl = new ShainSearchModel();
                searchMdl.setSortKey(sortKey);
                searchMdl.setSortOrder(orderKey);
                searchMdl.setSortKey2(sortKey2);
                searchMdl.setSortOrder2(orderKey2);

                List<CmnUsrmInfModel> ulist = udao.getBelongUserSearchList(gsid, null,
                        true, searchMdl, start, limit, form.getUsr040labSidSave());
                form.setUsr040users(__getDspMdlList(con, ulist));

                //検索条件に表示するグループ名を取得
                GroupDao gdao = new GroupDao(con);
                CmnGroupmModel gmodel = gdao.getGroup(gsid);
                if (gmodel != null) {
                    form.setSelectgpname(
                            StringUtilHtml.transToHTmlPlusAmparsant(gmodel.getGrpName()));
                }
            }

            //エスケープ処理＋wbrタグの挿入
            String escJouken = StringUtilHtml.transToHTmlWithWbr(
                    __getStrJoukenLabel(con, form, req), 10);

            //検索条件文字列(ラベル)を設定
            form.setUsr040DispKensakuJouken(escJouken);

            forward = map.getInputForward();
        } finally {
            con.setAutoCommit(false);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <p>詳細検索
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doSearchSyosai(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        ActionForward forward = null;

        try {
            log__.debug("詳細検索");

            //検索済にする
            form.setUsr040SearchFlg(GSConstUser.SEARCH_ZUMI);

            //画面設定
            Usr040Biz biz = new Usr040Biz(getRequestModel(req));
            BaseUserModel smodel = getSessionUserModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(con, smodel, GSConstUser.PLUGIN_ID_USER);

            Usr040ParamModel paramMdl = new Usr040ParamModel();
            paramMdl.setParam(form);

            //初期設定
            Usr040Biz.setDsp(this, getRequestModel(req), paramMdl, con);
            //ソートコンボ
            biz.setSortCombo(paramMdl);
            //カテゴリー・ラベルデータをセット
            biz.setCategoryLabelData(paramMdl, con);

            paramMdl.setFormData(form);


            //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
            if (form.getUsr040webmail() == 1) {
                form.setUsr040AtskList(biz.getSelUsrList(form.getUsr040SidsAtsk(), con));
                form.setUsr040CcList(biz.getSelUsrList(form.getUsr040SidsCc(), con));
                form.setUsr040BccList(biz.getSelUsrList(form.getUsr040SidsBcc(), con));
            }

            String searchCmd = NullDefault.getString(req.getParameter("pushSearch"), "");
            //画面に常に表示する値をセットする
            paramMdl = new Usr040ParamModel();
            paramMdl.setParam(form);
            biz.setDspData(paramMdl, con);
            biz.setExportBtn(paramMdl, con, adminUser);
            biz.setLabelSetBtn(paramMdl, con, adminUser);
            biz.setLabelEditBtn(paramMdl, con, adminUser);
            paramMdl.setFormData(form);

            if (form.getUsr040saveFlg() == 0) {
                if (searchCmd.equals("searchSyosai")) {
                    ActionErrors errors = form.validateSearchSyosai(map, getRequestModel(req), con);
                    if (!errors.isEmpty()) {
                        //エラーあり
                        addErrors(req, errors);
                        form.setUsr040saveFlg(1);
                        form.setUsr040DetailExeFlg(0);
                        forward = __doInit(map, form, req, res, con);
                        return forward;
                    }
                }

                if (form.getUsr040DetailExeFlg() == 1) {
                    //検索条件を保存
                    form.setSelectgsidSave(form.getSelectgsid()); //GSID
                    form.setUsr040SearchKanaSave(null); //カナ
                    form.setUsr040KeywordSave(form.getUsr040Keyword()); //キーワード
                    form.setUsr040KeyKbnShainnoSave(
                            form.getUsr040KeyKbnShainno()); //キーワード区分(社員/職員番号)
                    form.setUsr040KeyKbnNameSave(form.getUsr040KeyKbnName()); //キーワード区分(氏名)
                    form.setUsr040KeyKbnNameKnSave(form.getUsr040KeyKbnNameKn()); //キーワード区分(氏名カナ)
                    form.setUsr040KeyKbnMailSave(form.getUsr040KeyKbnMail()); //キーワード区分(E-MAIL)
                    form.setUsr040KeyKbnTelSave(form.getUsr040KeyKbnTel()); //キーワード区分(電話番号)
                    form.setUsr040agefromSave(form.getUsr040agefrom()); //年齢FROM
                    form.setUsr040agetoSave(form.getUsr040ageto()); //年齢TO
                    form.setUsr040yakushokuSave(form.getUsr040yakushoku()); //役職
                    form.setUsr040tdfkCdSave(form.getUsr040tdfkCd()); //都道府県コード
                    form.setUsr040labSidSave(form.getUsr040labSid());
                    form.setUsr040seibetuSave(form.getUsr040seibetu()); //性別
                    form.setUsr040entranceYearFrSave(form.getUsr040entranceYearFr()); //入社年月日
                    form.setUsr040entranceMonthFrSave(form.getUsr040entranceMonthFr()); //入社年月日
                    form.setUsr040entranceDayFrSave(form.getUsr040entranceDayFr()); //入社年月日
                    form.setUsr040entranceYearToSave(form.getUsr040entranceYearTo()); //入社年月日
                    form.setUsr040entranceMonthToSave(form.getUsr040entranceMonthTo()); //入社年月日
                    form.setUsr040entranceDayToSave(form.getUsr040entranceDayTo()); //入社年月日

                }

                form.setUsr040sortKeySave(form.getUsr040sortKey());
                form.setUsr040orderKeySave(form.getUsr040orderKey());
                form.setUsr040sortKey2Save(form.getUsr040sortKey2());
                form.setUsr040orderKey2Save(form.getUsr040orderKey2());

            }
            form.setUsr040saveFlg(0);

            //エラーなし
            UserSearchDao udao = new UserSearchDao(con);
            //設定された表示件数を取得
            Usr041Biz biz41 = new Usr041Biz(getRequestModel(req));
            BaseUserModel umodel = getSessionUserModel(req);
            int limit = biz41.getDspCnt(umodel.getUsrsid(), con);
            int nowPage = form.getUsr040pageNum1();
            int start = PageUtil.getRowNumber(nowPage, limit);

            //検索条件作成
            ShainSearchModel searchModel = new ShainSearchModel();
            searchModel.setSelectgsid(form.getSelectgsidSave());
            searchModel.setKeyword(__getEscKeyword(form.getUsr040KeywordSave()));
            searchModel.setKeyKbnShainno(form.getUsr040KeyKbnShainnoSave());
            searchModel.setKeyKbnName(form.getUsr040KeyKbnNameSave());
            searchModel.setKeyKbnNameKn(form.getUsr040KeyKbnNameKnSave());
            searchModel.setKeyKbnMail(form.getUsr040KeyKbnMailSave());
            searchModel.setKeyKbnTel(form.getUsr040KeyKbnTelSave());
            searchModel.setAgefrom(form.getUsr040agefromSave());
            searchModel.setAgeto(form.getUsr040agetoSave());
            searchModel.setYakushoku(form.getUsr040yakushokuSave());
            searchModel.setTdfkCd(form.getUsr040tdfkCdSave());
            searchModel.setSortKey(form.getUsr040sortKeySave());
            searchModel.setSortOrder(form.getUsr040orderKeySave());
            searchModel.setSortKey2(form.getUsr040sortKey2Save());
            searchModel.setSortOrder2(form.getUsr040orderKey2Save());
            searchModel.setLabelSid(form.getUsr040labSidSave());
            searchModel.setSeibetu(form.getUsr040seibetuSave());
            UDate date1 = null;

            //年月のみ
            if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
                && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
                && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

                date1 = new UDate();
                date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
                date1.setMonth(Integer.parseInt(form.getUsr040entranceMonthFrSave()));
                date1.setDay(1);
                date1.setZeroHhMmSs();
                searchModel.setEntranceDateFr(date1);
            }

            //年のみ
            if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
                && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
                && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {

                date1 = new UDate();
                date1.setYear(Integer.parseInt(form.getUsr040entranceYearFrSave()));
                date1.setMonth(1);
                date1.setDay(1);
                date1.setZeroHhMmSs();
                searchModel.setEntranceDateFr(date1);
            }

            if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())
                  && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())
                  && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {
                date1 = new UDate();
                date1.setDate(
                        Integer.parseInt(form.getUsr040entranceYearFrSave()),
                        Integer.parseInt(form.getUsr040entranceMonthFrSave()),
                        Integer.parseInt(form.getUsr040entranceDayFrSave()));
                date1.setZeroHhMmSs();
                searchModel.setEntranceDateFr(date1);
            }


            UDate date2 = null;

          //年月のみ
            if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
                && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
                && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

                date2 = new UDate();
                date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
                date2.setMonth(Integer.parseInt(form.getUsr040entranceMonthToSave()));
                date2.setDay(1);
                date2.setZeroHhMmSs();
                searchModel.setEntranceDateTo(date2);
            }

            //年のみ
            if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
                && StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
                && StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {

                date2 = new UDate();
                date2.setYear(Integer.parseInt(form.getUsr040entranceYearToSave()));
                date2.setMonth(12);
                date2.setDay(31);
                date2.setZeroHhMmSs();
                searchModel.setEntranceDateTo(date2);
            }

            if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())
                 && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())
                 && !StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {
                date2 = new UDate();
                date2.setDate(
                        Integer.parseInt(form.getUsr040entranceYearToSave()),
                        Integer.parseInt(form.getUsr040entranceMonthToSave()),
                        Integer.parseInt(form.getUsr040entranceDayToSave()));
                date2.setZeroHhMmSs();
                searchModel.setEntranceDateTo(date2);
              }

            //カウント取得
            int maxCount = udao.getSyousaiSearchCount(searchModel);
            log__.debug("件数 = " + maxCount);

            //ページあふれ制御
            int maxPageNum = PageUtil.getPageCount(maxCount, limit);
            int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
            if (maxPageStartRow < start) {
                nowPage = maxPageNum;
                start = maxPageStartRow;
            }
            form.setUsr040PageLabel(PageUtil.createPageOptions(maxCount, limit));
            form.setUsr040pageNum1(nowPage);
            form.setUsr040pageNum2(nowPage);

            int sortKey = form.getUsr040sortKeySave();
            int orderKey = form.getUsr040orderKeySave();
            int sortKey2 = form.getUsr040sortKey2Save();
            int orderKey2 = form.getUsr040orderKey2Save();

            log__.debug("sortKey :" + sortKey);
            log__.debug("orderKey :" + orderKey);
            log__.debug("sortKey2 :" + sortKey2);
            log__.debug("orderKey2 :" + orderKey2);

            //検索実行
            boolean sortFlg = true;
            if (sortKey == GSConstCommon.NUM_INIT || orderKey == GSConstCommon.NUM_INIT
                    || sortKey2 == GSConstCommon.NUM_INIT || orderKey2 == GSConstCommon.NUM_INIT) {
                sortFlg = false;
            }

            if (!sortFlg) {
                forward = map.getInputForward();
                return forward;
            }

            //検索実行
            List<CmnUsrmInfModel> ulist = udao.getSyousaiSearchList(searchModel, start, limit);
            form.setUsr040users(__getDspMdlList(con, ulist));

            //表示用検索条件文字列を作成
            StringBuilder joukenBuild = new StringBuilder();
            GsMessage gsMsg = new GsMessage(req);

            String textKeyword = gsMsg.getMessage("cmn.keyword");
            String escKeyword = __getEscKeyword(form.getUsr040KeywordSave());
            if (!StringUtil.isNullZeroStringSpace(escKeyword)) {
                joukenBuild.append(textKeyword);
                StringBuilder sbSearchKbn = new StringBuilder();

                int searchKbnCnt = 0;
                //社員/職員番号
                if (form.getUsr040KeyKbnShainnoSave() == 1) {
                    if (searchKbnCnt == 0) {
                        sbSearchKbn.append("(");
                        searchKbnCnt++;
                    }
                    sbSearchKbn.append(gsMsg.getMessage("cmn.employee.staff.number"));
                }
                //氏名
                if (form.getUsr040KeyKbnNameSave() == 1) {
                    if (searchKbnCnt == 0) {
                        sbSearchKbn.append("(");
                        searchKbnCnt++;
                    } else if (searchKbnCnt != 0) {
                        sbSearchKbn.append(" ,");
                    }
                    sbSearchKbn.append(gsMsg.getMessage("cmn.name"));
                }
                //氏名カナ
                if (form.getUsr040KeyKbnNameKnSave() == 1) {
                    if (searchKbnCnt == 0) {
                        sbSearchKbn.append("(");
                        searchKbnCnt++;
                    } else if (searchKbnCnt != 0) {
                        sbSearchKbn.append(" ,");
                    }
                    sbSearchKbn.append(gsMsg.getMessage("cmn.name.kana"));
                }
                //E-MAIL
                if (form.getUsr040KeyKbnMailSave() == 1) {
                    if (searchKbnCnt == 0) {
                        sbSearchKbn.append("(");
                        searchKbnCnt++;
                    } else if (searchKbnCnt != 0) {
                        sbSearchKbn.append(" ,");
                    }
                    sbSearchKbn.append("E-MAIL");
                }
                //電話番号
                if (form.getUsr040KeyKbnTelSave() == 1) {
                    if (searchKbnCnt == 0) {
                        sbSearchKbn.append("(");
                        searchKbnCnt++;
                    } else if (searchKbnCnt != 0) {
                        sbSearchKbn.append(" ,");
                    }
                    sbSearchKbn.append(gsMsg.getMessage("cmn.tel"));
                }
                sbSearchKbn.append(")");
                sbSearchKbn.append("=");
                sbSearchKbn.append(form.getUsr040KeywordSave());

                joukenBuild.append(sbSearchKbn.toString());
            }

            //年齢
            String textAge = gsMsg.getMessage("user.3");
            //年齢FROM
            if (!StringUtil.isNullZeroString(form.getUsr040agefromSave())) {
                __setComma(joukenBuild);
                joukenBuild.append(textAge);
                joukenBuild.append("(FROM)");
                joukenBuild.append("=");
                joukenBuild.append(form.getUsr040agefromSave());
            }
            //年齢TO
            if (!StringUtil.isNullZeroString(form.getUsr040agetoSave())) {
                __setComma(joukenBuild);
                joukenBuild.append(textAge);
                joukenBuild.append("(TO)");
                joukenBuild.append("=");
                joukenBuild.append(form.getUsr040agetoSave());
            }
            //所属グループ
            String textAffiliationGroup = gsMsg.getMessage("cmn.affiliation.group");
            //所属グループ
            if (form.getSelectgsidSave() != -1) {
                //グループ名を取得
                GroupDao gdao = new GroupDao(con);
                CmnGroupmModel gmodel = gdao.getGroup(form.getSelectgsidSave());
                String gname = gmodel.getGrpName();
                __setComma(joukenBuild);
                joukenBuild.append(textAffiliationGroup);
                joukenBuild.append("=");
                joukenBuild.append(gname);
            }

            //性別追加
            if (!form.getUsr040seibetuSave().equals(
                    String.valueOf(GSConstCommon.NUM_INIT))) {
                __setComma(joukenBuild);
                joukenBuild.append(gsMsg.getMessage("user.123"));
                joukenBuild.append("=");
                switch (Integer.valueOf(form.getUsr040seibetuSave())) {
                case GSConstUser.SEIBETU_UNSET:
                    joukenBuild.append(gsMsg.getMessage("cmn.notset"));
                    break;
                case GSConstUser.SEIBETU_MAN:
                    joukenBuild.append(gsMsg.getMessage("user.124"));
                    break;
                case GSConstUser.SEIBETU_WOMAN:
                    joukenBuild.append(gsMsg.getMessage("user.125"));
                    break;
                default:
                    break;
                }
            }

            //役職
            String textPost = gsMsg.getMessage("cmn.post");
            //役職
            if (form.getUsr040yakushokuSave() != GSConstCommon.NUM_INIT) {
                //役職名称を取得
                PosBiz pBiz = new PosBiz();
                String posName = pBiz.getPosName(con, form.getUsr040yakushokuSave());

                __setComma(joukenBuild);
                joukenBuild.append(textPost);
                joukenBuild.append("=");
                joukenBuild.append(NullDefault.getString(posName, ""));
            }

            //都道府県
            String textTkfk = gsMsg.getMessage("cmn.prefectures");
            //都道府県
            if (!StringUtil.isNullZeroString(form.getUsr040tdfkCdSave())) {
                int tdfkCd = NullDefault.getInt(form.getUsr040tdfkCdSave(), 0);
                if (tdfkCd > 0) {
                    __setComma(joukenBuild);
                    CmnTdfkDao dao = new CmnTdfkDao(con);
                    CmnTdfkModel model = dao.select(tdfkCd);
                    if (model != null) {
                        joukenBuild.append(textTkfk);
                        joukenBuild.append("=");
                        joukenBuild.append(model.getTdfName());
                    }
                }
            }

            //入社年月日
            if (!(StringUtil.isNullZeroString(form.getUsr040entranceYearFrSave())
                    && StringUtil.isNullZeroString(form.getUsr040entranceYearToSave()))) {
                __setComma(joukenBuild);
                joukenBuild.append(gsMsg.getMessage("user.156"));
                joukenBuild.append("=");

                StringBuilder sbFromTo = new StringBuilder();
                //入社年月日From
                if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearFrSave())) {
                    sbFromTo.append("From:");
                    sbFromTo.append(form.getUsr040entranceYearFrSave());
                    sbFromTo.append(gsMsg.getMessage("cmn.year2"));

                    if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthFrSave())) {
                        sbFromTo.append(form.getUsr040entranceMonthFrSave());
                        sbFromTo.append(gsMsg.getMessage("cmn.month"));
                    }

                    if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayFrSave())) {
                        sbFromTo.append(form.getUsr040entranceDayFrSave());
                        sbFromTo.append(gsMsg.getMessage("cmn.day"));
                    }
                }

                //入社年月日To
                if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceYearToSave())) {
                    if (sbFromTo.toString().length() > 0) {
                        sbFromTo.append(" ");
                    }

                    sbFromTo.append("To:");
                    sbFromTo.append(form.getUsr040entranceYearToSave());
                    sbFromTo.append(gsMsg.getMessage("cmn.year2"));

                    if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceMonthToSave())) {
                        sbFromTo.append(form.getUsr040entranceMonthToSave());
                        sbFromTo.append(gsMsg.getMessage("cmn.month"));
                    }

                    if (!StringUtil.isNullZeroStringSpace(form.getUsr040entranceDayToSave())) {
                        sbFromTo.append(form.getUsr040entranceDayToSave());
                        sbFromTo.append(gsMsg.getMessage("cmn.day"));
                    }
                }
                joukenBuild.append(sbFromTo.toString());
            }

            //ラベル
            String joukenLabel = __getStrJoukenLabel(con, form, req);
            if (!StringUtil.isNullZeroString(joukenLabel)) {
                __setComma(joukenBuild);
                joukenBuild.append(joukenLabel);
            }

            //検索条件なしで検索した場合
            if (joukenBuild.length() <= 0) {
                joukenBuild.append(gsMsg.getMessage("user.151"));
            }

            //エスケープ処理＋wbrタグの挿入
            String escJouken = StringUtilHtml.transToHTmlWithWbr(joukenBuild.toString(), 10);

            //Formにセット
            form.setUsr040DispKensakuJouken(escJouken);


            forward = map.getInputForward();
        } finally {
            con.setAutoCommit(false);
        }

       return forward;
    }

    /**
     * <br>[機  能] ラベルの検索条件文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param form フォーム
     * @param req リクエスト
     * @return 検索条件文字列
     * @throws SQLException SQL実行時例外
     */
    private String __getStrJoukenLabel(Connection con, Usr040Form form, HttpServletRequest req)
    throws SQLException {

        StringBuilder joukenBuf = new StringBuilder("");
        GsMessage gsMsg = new GsMessage(req);
        //ラベル
        String textLabel = gsMsg.getMessage("cmn.label");
        if (form.getUsr040labSidSave() != null && form.getUsr040labSidSave().length != 0) {
            //ラベル名を取得
            CmnLabelUsrDao dao = new CmnLabelUsrDao(con);
            int labSid = Integer.parseInt(form.getUsr040labSidSave()[0]);
            CmnLabelUsrModel model = dao.selectOneLabel(labSid);
            String labelName = model.getLabName();
            joukenBuf.append(textLabel);
            joukenBuf.append("=");
            joukenBuf.append(NullDefault.getString(labelName, ""));

            if (form.getUsr040labSidSave().length >= 1) {
                for (int i = 1; i < form.getUsr040labSidSave().length; i++) {
                    int labSidNe = Integer.parseInt(form.getUsr040labSidSave()[i]);
                    CmnLabelUsrModel modelNe = dao.selectOneLabel(labSidNe);
                    String labelNameNe = modelNe.getLabName();
                    __setComma(joukenBuf);
                    joukenBuf.append(NullDefault.getString(labelNameNe, ""));
                }
            }
        }

        return joukenBuf.toString();
    }

    /**
     * <br>[機  能] 引数のbufが長さ0以上の場合にカンマを付加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param buf 対象のStringBuffer
     * @return カンマを付加したStringBuffer
     */
    private StringBuilder __setComma(StringBuilder buf) {
        //
        if (buf.length() > 0) {
            buf.append(",");
        }
        return buf;
    }

    /**
     * <p>個人設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doPsetting(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;
        log__.debug("個人設定へ遷移");

        forward = map.findForward("psetting");

        log__.debug("END");
        return forward;
    }

    /**
     * <p>管理者設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doAsetting(ActionMapping map, Usr040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;
        log__.debug("管理者設定へ遷移");

        forward = map.findForward("asetting");

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Usr040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;

        //ページ設定
        int page = form.getUsr040pageNum1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setUsr040pageNum1(page);
        form.setUsr040pageNum2(page);

        forward = __doSearch(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doNext(
        ActionMapping map,
        Usr040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;

        //ページ設定
        int page = form.getUsr040pageNum1();
        page += 1;
        if (page < 1) {
            page = 1;
        }
        form.setUsr040pageNum1(page);
        form.setUsr040pageNum2(page);

        forward = __doSearch(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] ユーザラベル設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doUsrLabSet(
        ActionMapping map,
        Usr040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;

        Usr040Biz biz = new Usr040Biz(getRequestModel(req));

        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        biz.setUsrLabel(paramMdl, con, getSessionUserModel(req).getUsrsid());
        paramMdl.setFormData(form);

        forward = __doSearch(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward checkPow(ActionMapping map,
            HttpServletRequest req, Connection con)
    throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(
                con, getSessionUserModel(req), GSConstUser.PLUGIN_ID_USER);

        if (!adminUser) {
            con.setAutoCommit(true);
            CmnLabelUsrConfDao dao = new CmnLabelUsrConfDao(con);
            CmnLabelUsrConfModel model = dao.select();
            con.setAutoCommit(false);
            if (model != null && model.getLufSet() == GSConstUser.POW_LIMIT) {
                return getNotAdminSeniPage(map, req);
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 送信先アドレス設定処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doSendAddressUsr(ActionMapping map,
                                                  Usr040Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        //送信対象選択チェック
        ActionErrors errors =
            form.validateSelectCheckWebmail(req);
        if (!errors.isEmpty()
                && form.getUsr040UsrSid() == 0) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Usr040Biz biz = new Usr040Biz(getRequestModel(req));

        //宛先・CC・BCCにセットするユーザSIDを設定する
        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        biz.setAddressUsrSid(paramMdl, con);
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 宛先CCBCCユーザ削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteUser(ActionMapping map,
            Usr040Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws Exception {

        Usr040Biz biz = new Usr040Biz(getRequestModel(req));
        //ユーザを削除する。
        Usr040ParamModel paramMdl = new Usr040ParamModel();
        paramMdl.setParam(form);
        biz.deleteUserAtesaki(paramMdl, con);
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] グループ絞込みボタン押下
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
    private ActionForward __setGrpSearch(
            ActionMapping map,
            Usr040Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws Exception {

        ActionForward forward = null;

        //送信対象選択チェック
        ActionErrors errors =
                form.validateGroupSearch(map, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //セーブパラメータへセット
        form.setUsr040GrpSearchGIdSave(form.getUsr040GrpSearchGId());
        String serchGpName = URLEncoder.encode(form.getUsr040GrpSearchGName(), Encoding.UTF_8);
        form.setUsr040GrpSearchGNameSave(serchGpName);

        forward = __doInit(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 全角スペース、タブを半角スペースに変換する
     * <br>[解  説] "　","\t"を" "で置換する
     * <br>[備  考]
     * @param str キーワード
     * @return エスケープ処理後の文字列
     */
    private String __getEscKeyword(String str) {
        str = str.replaceAll("　", " ");
        str = str.replaceAll("\t", " ");
        return str;
    }

    /**
     * <br>[機  能] ユーザリストに所属グループ情報を付加する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param ulist ユーザリスト
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     * @return 表示リスト
     */
    private ArrayList<Usr040DspModel> __getDspMdlList(Connection con, List<CmnUsrmInfModel> ulist)
            throws SQLException, Exception {

        ArrayList<Usr040DspModel> ret = new ArrayList<Usr040DspModel>();
        Usr040DspModel dspMdl = null;
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> gpList = null;
        if (sortMdl.getCscGroupSkbn() == GSConst.GROUPCMB_SKBN_SET) {
            gpList = dao.getGroupTree(sortMdl);
        } else {
            gpList = dao.getGroupList(sortMdl);
        }

        for (CmnUsrmInfModel mdl : ulist) {
            dspMdl = new Usr040DspModel();

            //CmnUsrmInfModel → Usr040DspModel パラメータのコピー
            BeanUtils.copyProperties(dspMdl, mdl);
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongList = belongDao.selectUserBelongGroupSid(mdl.getUsrSid());
            ArrayList<GroupModel> groupList = new ArrayList<GroupModel>();

            for (GroupModel gpMdl : gpList) {
                if (belongList.indexOf(new Integer(gpMdl.getGroupSid())) >= 0) {
                    groupList.add(gpMdl);
                }

            }
            dspMdl.setBelongGrpList(groupList);
            ret.add(dspMdl);
        }

        return ret;
    }
    @Override
    public boolean canAccessPlugin(List<String> plugins) {
        return true;
    }
}