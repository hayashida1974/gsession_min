package jp.groupsession.v2.adr.adr100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.AbstractAddressAction;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr100.model.Adr100SearchModel;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrBelongIndustryDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr100Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr100Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "adr100";
    /** 処理種別 確認 */
    private static final int MSGTYPE_CONFIRM__ = 1;
    /** 処理種別 完了 */
    private static final int MSGTYPE_COMPLETE__ = 2;

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

        log__.debug("START");

        ActionForward forward = null;

        Adr100Form thisForm = (Adr100Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("search")) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("backAddressList")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("adrList");

        } else if (cmd.equals("backInputAddress")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("inputAddress");

        } else if (cmd.equals("addCompany")) {
            log__.debug("追加ボタンクリック");
            forward = map.findForward("inputCompany");

        } else if (cmd.equals("editCompany")) {
            log__.debug("会社名クリック");
            forward = map.findForward("inputCompany");

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setAdr100page(thisForm.getAdr100page() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setAdr100page(thisForm.getAdr100page() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("importCompany")) {
            //インポートボタンクリック
            return map.findForward("importCompany");

        } else if (cmd.equals("exportCompany")) {
            //インポートボタンクリック

            return __doExport(map, thisForm, req, res, con);

        } else if (cmd.equals("search50tab")) {
            //イニシャルリンクリック
            return __doSearch50tab(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteCompanies")) {
            //削除ボタンクリック
            return __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteCompanyComplete")) {
            //削除OKボタンクリック
            return __doDeleteComplete(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
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
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Adr100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Adr100Biz biz = new Adr100Biz(getRequestModel(req));

        Adr100ParamModel paramMdl = new Adr100ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl, sessionUserSid);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
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
    private ActionForward __doSearch(ActionMapping map,
                                    Adr100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {


        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        GsMessage gsMsg = new GsMessage();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {

            //検索結果の件数を確認する
            Adr100Biz biz = new Adr100Biz(getRequestModel(req));
            Adr100SearchModel searchMdl = new Adr100SearchModel();
            //企業コード
            searchMdl.setCoCode(form.getAdr100code());
            //会社名
            searchMdl.setCoName(form.getAdr100coName());
            //会社名カナ
            searchMdl.setCoNameKn(form.getAdr100coNameKn());
            //支店・営業所名
            searchMdl.setCoBaseName(form.getAdr100coBaseName());
            //業種
            searchMdl.setAtiSid(form.getAdr100atiSid());
            //都道府県
            searchMdl.setTdfk(form.getAdr100tdfk());
            //備考
            searchMdl.setBiko(form.getAdr100biko());

            //検索結果件数の確認
            con.setAutoCommit(true);
            int searchCount = biz.getSearchCount(con, searchMdl);
            con.setAutoCommit(false);
            if (searchCount <= 0) {
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                       gsMsg.getMessage(req, "address.118"));
                StrutsUtil.addMessage(errors, msg, "companySearch");
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            //企業コード
            form.setAdr100svCode(form.getAdr100code());
            //会社名
            form.setAdr100svCoName(form.getAdr100coName());
            //会社名カナ
            form.setAdr100svCoNameKn(form.getAdr100coNameKn());
            //支店・営業所名
            form.setAdr100svCoBaseName(form.getAdr100coBaseName());
            //業種
            form.setAdr100svAtiSid(form.getAdr100atiSid());
            //都道府県
            form.setAdr100svTdfk(form.getAdr100tdfk());
            //備考
            form.setAdr100svBiko(form.getAdr100biko());

            //ページ
            form.setAdr100page(1);
            form.setAdr100searchFlg(1);

        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
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
    private ActionForward __doSearch50tab(ActionMapping map,
                                    Adr100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        //ページ
        form.setAdr100page(1);
        form.setAdr100searchFlg(1);

        return __doInit(map, form, req, res, con);
    }


    /**
     * <br>[機  能] 削除確認処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map,
                                                  Adr100Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
                                                  throws Exception {

        //削除対象選択チェック
        ActionErrors errors =
            form.validateSelectCheck100(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //削除対象の会社情報を取得する
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        int index = form.getAdr100SelectCom().length;
        ArrayList<String> comNameList = new ArrayList<String>();
        for (int i = 0; i < index; i++) {
            AdrCompanyModel model = new AdrCompanyModel();
            int intSid = Integer.parseInt(form.getAdr100SelectCom()[i]);
            model = companyDao.select(intSid);
            if (model != null) {
                comNameList.add(model.getAcoName());
            }
        }
        String[] selectComName = new String[comNameList.size()];
        selectComName = comNameList.toArray(selectComName);
        if (selectComName.length < form.getAdr100SelectCom().length) {
            //すでに削除されている場合
            GsMessage gsMsg = new GsMessage();
            ActionMessage msg = null;

            //会社情報
            String textAddress = gsMsg.getMessage(req, "address.118");
            //変更
            String textDel = gsMsg.getMessage(req, "cmn.delete");

            msg = new ActionMessage(
                    "error.edit.power.notfound", textAddress, textDel);

            StrutsUtil.addMessage(errors, msg, "acoSid");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);

        }

        form.setAdr100SelectComName(selectComName);

        return __doDeleteMessage(map, form, req, con, MSGTYPE_CONFIRM__);
    }

    /**
     * <br>[機  能] 削除確認処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDeleteComplete(ActionMapping map,
                                                  Adr100Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
                                                  throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            //削除対象の会社情報一覧を取得する
            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
            AdrBelongIndustryDao blgIndustryDao = new AdrBelongIndustryDao(con);
            AdrAddressDao addressDao = new AdrAddressDao(con);

            ArrayList<Integer> comSidList = new ArrayList<Integer>();
            for (String strSid : form.getAdr100SelectCom()) {
                AdrCompanyModel model = new AdrCompanyModel();
                int intSid = Integer.parseInt(strSid);
                model = companyDao.select(intSid);
                if (model != null) {
                    comSidList.add(model.getAcoSid());
                }
            }

            if (!comSidList.isEmpty()) {
                int sessionUserSid = getSessionUserSid(req);
                UDate now = new UDate();

                for (int sid : comSidList) {
                    //会社情報の削除
                    companyDao.delete(sid);

                    //拠点情報の削除
                    companyBaseDao.deleteCompany(sid);

                    //所属業種情報の削除
                    blgIndustryDao.delete(sid);

                    //アドレス情報の"会社"を未設定に更新する
                    addressDao.resetCompany(sid, sessionUserSid, now);
                }
            }
            con.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        //削除終了画面を設定
        return __doDeleteMessage(map, form, req, con, MSGTYPE_COMPLETE__);
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
    private ActionForward __doExport(ActionMapping map,
            Adr100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws Exception {

        //検索条件をサーチモデルにセット
        Adr100SearchModel searchMdl = __createSearchModel(form);

        GsMessage gsMsg = new GsMessage();

        log__.debug("エクスポート処理実行");
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        //CSVファイルを作成
        RequestModel reqMdl = getRequestModel(req);
        String fileName = CompanyCsvWriter.FILE_NAME;
        CompanyCsvWriter write = new CompanyCsvWriter(searchMdl);
        write.outputCsv(con, tempDir, reqMdl);

        String fullPath = tempDir + fileName;
        //ダウンロード
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);


        //TEMPディレクトリ削除
        temp.deleteTempPath(reqMdl,
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage("cmn.export");

        //ログ出力
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        adrBiz.outPutLog(
                map, req, res,
                export, GSConstLog.LEVEL_TRACE, gsMsg.getMessage("address.118"));
        return null;
    }

    /**
     * <br>[機  能] 削除時の共通メッセージ画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @param msgType メッセージ種別
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteMessage(ActionMapping map,
                                          Adr100Form form,
                                          HttpServletRequest req,
                                          Connection con, int msgType) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();

        if (msgType == MSGTYPE_CONFIRM__) {
            String comName = "";
            String[] comNameArray = form.getAdr100SelectComName();
            for (int i = 0; i < comNameArray.length; i++) {
                comName += "・";
                comName += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(comNameArray[i], ""));
                //最後の要素以外は改行を挿入
                if (i < comNameArray.length - 1) {
                    comName += "<br>";
                }
            }
            cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
            cmn999Form.setMessage(
                 msgRes.getMessage("sakujo.kakunin.list",
                         gsMsg.getMessage(req, "address.118"), comName));
            cmn999Form.setUrlOK(map.findForward("adr100").getPath()
                                + "?CMD=deleteCompanyComplete");
            cmn999Form.setUrlCancel(map.findForward("adr100").getPath()
                                + "?CMD=search");

            String[] comSidArray = form.getAdr100SelectCom();
            //hiddenパラメータ
            for (String comSid : comSidArray) {
                cmn999Form.addHiddenParam("adr100SelectCom", comSid);
            }

        } else {
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "address.118")));
            cmn999Form.setUrlOK(map.findForward("adr100").getPath()
                                + "?CMD=search");
        }

        //hiddenパラメータ
        cmn999Form.addHiddenParam("adr100mode", form.getAdr100mode());
        //詳細検索の場合、入力項目記憶
        if (form.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) {
            cmn999Form.addHiddenParam("adr100code", form.getAdr100code());
            cmn999Form.addHiddenParam("adr100atiSid", form.getAdr100atiSid());
            cmn999Form.addHiddenParam("adr100coName", form.getAdr100coName());
            cmn999Form.addHiddenParam("adr100tdfk", form.getAdr100tdfk());
            cmn999Form.addHiddenParam("adr100coNameKn", form.getAdr100coNameKn());
            cmn999Form.addHiddenParam("adr100coBaseName", form.getAdr100coBaseName());
            cmn999Form.addHiddenParam("adr100biko", form.getAdr100biko());

        } else if (form.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_50) {
            cmn999Form.addHiddenParam("adr100SearchKana", form.getAdr100SearchKana());
        }
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] 検索条件Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param form Adr100ParamModel
     * @return 検索条件Model
     */
    private Adr100SearchModel __createSearchModel(Adr100Form form) {
        Adr100SearchModel searchMdl = new Adr100SearchModel();

        //モード
        searchMdl.setMode(form.getAdr100mode());
        //ソートキー
        searchMdl.setSortKey(form.getAdr100SortKey());
        //オーダーキー
        searchMdl.setOrderKey(form.getAdr100OrderKey());

        if (form.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_50) {
            //会社名先頭文字
            searchMdl.setCoSini(form.getAdr100SearchKana());

        } else if (form.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) {

            //企業コード
            searchMdl.setCoCode(form.getAdr100svCode());
            //会社名
            searchMdl.setCoName(form.getAdr100svCoName());
            //会社名カナ
            searchMdl.setCoNameKn(form.getAdr100svCoNameKn());
            //支店・営業所名
            searchMdl.setCoBaseName(form.getAdr100svCoBaseName());
            //業種
            searchMdl.setAtiSid(form.getAdr100svAtiSid());
            //都道府県
            searchMdl.setTdfk(form.getAdr100svTdfk());
            //備考
            searchMdl.setBiko(form.getAdr100svBiko());
        }

        return searchMdl;
    }
}