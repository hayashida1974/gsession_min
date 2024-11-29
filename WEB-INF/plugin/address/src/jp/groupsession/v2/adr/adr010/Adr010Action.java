package jp.groupsession.v2.adr.adr010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import jp.groupsession.v2.adr.adr010.csv.Adr010CsvWriter;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.adr020kn.Adr020knForm;
import jp.groupsession.v2.adr.adr160.csv.Adr160CsvWriter;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrContactBinDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrContactBinModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr010Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "adr010";

    /**
     * <p>キャッシュを有効にして良いか判定を行う
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {
        return NullDefault.getString(req.getParameter(GSConst.P_CMD), "").equals("export");
    }

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

        GsMessage gsMsg = new GsMessage();
        Adr010Form thisForm = (Adr010Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("CMD = " + cmd);

        ActionForward forward = null;
        if (cmd.equals("adminMenu")) {
            //管理者設定ボタンクリック
            forward = map.findForward("adminMenu");

        } else if (cmd.equals("personalMenu")) {
            //個人設定ボタンクリック
            forward = map.findForward("personalMenu");

        } else if (cmd.equals("addAdrData")) {
            //新規登録ボタンクリック
            forward = map.findForward("registAddress");

        } else if (cmd.equals("editAdrData")) {
            //アドレス帳名称クリック

            AddressBiz addressBiz = new AddressBiz(getRequestModel(req));
            boolean editFlg =
                addressBiz.isEditAddressData(con, thisForm.getAdr010EditAdrSid(),
                                            getSessionUserModel(req).getUsrsid());


            if (editFlg) {
                forward = map.findForward("registAddress");
            } else {
                thisForm.setAdr020viewFlg(1);
                Adr020knForm adr020knForm = new Adr020knForm();
                BeanUtils.copyProperties(adr020knForm, thisForm);
                req.setAttribute("adr020knForm", adr020knForm);
                forward = map.findForward("viewAddress");
            }

        } else if (cmd.equals("viewCompany")) {
            //会社名称クリック
            forward = map.findForward("inputCompanyConfirm");

        } else if (cmd.equals("import")) {
            //インポートボタンクリック
            forward = map.findForward("addressImport");

        } else if (cmd.equals("export")) {
            //エクスポートボタンクリック
            forward = __doExport(map, thisForm, req, res, con);

        } else if (cmd.equals("exportContact")) {
            //コンタクト履歴エクスポートボタンクリック
            forward = __doExportCsv(map, thisForm, req, res, con);

            //ログ出力処理
            AdrCommonBiz adrBiz = new AdrCommonBiz(con);
            adrBiz.outPutLog(
                    map, req, res,
                    gsMsg.getMessage(req, "cmn.export"),
                    GSConstLog.LEVEL_INFO,
                    Adr160CsvWriter.FILE_NAME);

        } else if (cmd.equals("setupYakusyoku")) {
            //役職情報設定ボタンクリック
            forward = map.findForward("yakusyokuEdit");

        } else if (cmd.equals("setupIndustry")) {
            //業種情報設定ボタンクリック
            forward = map.findForward("industryList");

        } else if (cmd.equals("setupCompany")) {
            //会社情報設定ボタンクリック
            forward = map.findForward("companyList");

        } else if (cmd.equals("setupLabel")) {
            //ラベル設定ボタンクリック
            forward = map.findForward("labelList");

        } else if (cmd.equals("contact")) {
            //コンタクト履歴ボタンクリック
            forward = map.findForward("contact");

        } else if (cmd.equals("search")) {
            //検索ボタンクリック
            forward = __doSearchBtn(map, thisForm, req, res, con);

        } else if (cmd.equals("searchCompanyInit")) {
            //会社名　先頭文字クリック
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setAdr010page(thisForm.getAdr010page() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setAdr010page(thisForm.getAdr010page() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("grpChange")) {
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("chgPrjDspKbn")) {
            thisForm.setAdr010searchFlg(0);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("adrDelete")) {
            //削除ボタンクリック
            forward = __doDeleteConfirmation(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            //削除OKボタンクリック
            forward = __doDeleteOk(map, thisForm, req, res, con);

        } else if (cmd.equals("labelSetMult")) {
            //ラベル複数設定ポップアップOKクリック
            forward = __setLabelMult(map, req, res, thisForm, con);

        } else if (cmd.equals("addUsrAtesaki")
                || cmd.equals("addUsrCc")
                || cmd.equals("addUsrBcc")) {
          //宛先・TO・BCCボタン
          forward = __doSendAddressUsr(map, thisForm, req, res, con);

          //宛先削除リンククリック
        } else if (cmd.equals("deleteSend")) {
            log__.debug("宛先削除リンククリック");
            forward = __doDeleteUser(map, thisForm, req, res, con);

        } else {
            forward = __doInit(map, thisForm, req, res, con);
            if (thisForm.getAdr010cmdMode() == Adr010Const.CMDMODE_TANTO
            || thisForm.getAdr010cmdMode() == Adr010Const.CMDMODE_CONTACT
            || thisForm.getAdr010cmdMode() == Adr010Const.CMDMODE_COMPANY
            || thisForm.getAdr010cmdMode() == Adr010Const.CMDMODE_PROJECT) {
                forward = __doSearch(map, thisForm, req, res, con);
            }
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
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
    private ActionForward __doInit(ActionMapping map,
                                    Adr010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Adr010Biz biz = new Adr010Biz(getRequestModel(req));

        Adr010ParamModel paramMdl = new Adr010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getSessionUserModel(req));
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
                                    Adr010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {


        //入力チェック
        ActionErrors errors = form.validateCheck(con, req);
        GsMessage gsMsg = new GsMessage();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {

            //検索結果件数の確認を行う
            Adr010SearchModel searchMdl = new Adr010SearchModel();
            searchMdl.setSessionUser(getSessionUserModel(req).getUsrsid());
            searchMdl.setSortKey(form.getAdr010sortKey());
            searchMdl.setOrderKey(form.getAdr010orderKey());
            searchMdl.setCmdMode(form.getAdr010cmdMode());

            switch (form.getAdr010cmdMode()) {
                case Adr010Const.CMDMODE_COMPANY :

                    //会社名先頭文字
                    searchMdl.setCnameKnHead(form.getAdr010SearchComKana());
                    //企業コード
                    searchMdl.setCoCode(form.getAdr010code());
                    //会社名
                    searchMdl.setCoName(form.getAdr010coName());
                    //会社名カナ
                    searchMdl.setCoNameKn(form.getAdr010coNameKn());
                    //支店・営業所名
                    searchMdl.setCoBaseName(form.getAdr010coBaseName());
                    //業種
                    searchMdl.setAtiSid(form.getAdr010atiSid());
                    //都道府県
                    searchMdl.setTdfk(form.getAdr010tdfk());
                    //備考
                    searchMdl.setBiko(form.getAdr010biko());

                    break;
                case Adr010Const.CMDMODE_NAME :
                    searchMdl.setUnameKnHead(form.getAdr010SearchKana());
                    break;

                case Adr010Const.CMDMODE_TANTO :
                    if (!AdrCommonBiz.isMyGroupSid(form.getAdr010tantoGroup())) {
                        //グループ
                        searchMdl.setGroup(Integer.parseInt(form.getAdr010tantoGroup()));
                    }
                    //ユーザ
                    searchMdl.setUser(form.getAdr010tantoUser());
                    break;
                case Adr010Const.CMDMODE_PROJECT :
                    //プロジェクトSID
                    searchMdl.setPrjSid(Integer.parseInt(form.getSelectingProject()));
                    searchMdl.setProjectKbn(form.getProjectKbn());
                    searchMdl.setStatusKbn(form.getStatusKbn());
                    searchMdl.setUsrSid(getSessionUserModel(req).getUsrsid());
                    searchMdl.setPrjSerchFlg(true);

                    //プロジェクトコンボにプロジェクトが存在しない
                    if (form.getProjectCmbsize() <= 1) {
                        ActionMessage msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage(req, "address.src.2"));
                        StrutsUtil.addMessage(errors, msg, "addressSearch");
                        addErrors(req, errors);
                        return __doInit(map, form, req, res, con);
                    }
                    break;

                case Adr010Const.CMDMODE_DETAILED :
                    //氏名 姓
                    searchMdl.setUnameSei(form.getAdr010unameSei());
                    //氏名 名
                    searchMdl.setUnameMei(form.getAdr010unameMei());
                    //氏名カナ 姓
                    searchMdl.setUnameSeiKn(form.getAdr010unameSeiKn());
                    //氏名カナ 名
                    searchMdl.setUnameMeiKn(form.getAdr010unameMeiKn());
                    //会社名
                    searchMdl.setCoName(form.getAdr010detailCoName());
                    //所属
                    searchMdl.setSyozoku(form.getAdr010syozoku());
                    //役職
                    searchMdl.setPosition(form.getAdr010position());
                    //E-MAIL
                    searchMdl.setMail(form.getAdr010mail());
                    if (!AdrCommonBiz.isMyGroupSid(form.getAdr010detailTantoGroup())) {
                        //グループ
                        searchMdl.setGroup(Integer.parseInt(form.getAdr010detailTantoGroup()));
                    }
                    //担当者ユーザ
                    searchMdl.setUser(form.getAdr010detailTantoUser());
                    //業種
                    searchMdl.setAtiSid(form.getAdr010detailAtiSid());

                    break;
                case Adr010Const.CMDMODE_CONTACT :
                    //グループ
                    if (!AdrCommonBiz.isMyGroupSid(form.getAdr010tantoGroupContact())) {
                        searchMdl.setGroup(Integer.parseInt(form.getAdr010tantoGroupContact()));
                    }

                    //ユーザ
                    searchMdl.setUser(form.getAdr010tantoUserContact());
                    //氏名 姓
                    searchMdl.setUnameSei(form.getAdr010unameSeiContact());
                    //氏名 名
                    searchMdl.setUnameMei(form.getAdr010unameMeiContact());
                    //会社名
                    searchMdl.setCoName(form.getAdr010CoNameContact());
                    //拠点
                    searchMdl.setCoBaseName(form.getAdr010CoBaseNameContact());
                    //プロジェクトSID
                    searchMdl.setPrjSid(form.getAdr010ProjectContact());
                    //添付ファイル区分
                    searchMdl.setTempFileExist(
                            NullDefault.getInt(form.getAdr010TempFilekbnContact(), -1));
                    //添付ファイルが存在するコンタクト履歴SIDリスト
                    AdrContactBinDao acbDao = new AdrContactBinDao(con);
                    searchMdl.setHaveTmpFileAdcSids(
                            acbDao.getTmpFileAdcSidList(searchMdl.getSessionUser()));

                    //添付ファイルデータが存在しない場合
                    if (searchMdl.getHaveTmpFileAdcSids().size() <= 0) {
                        List<Integer> adcSids = new ArrayList<Integer>();

                        //添付ファイル区分：あり
                        if (searchMdl.getTempFileExist() == GSConstAddress.TEMPFILE_KBN_EXIST) {
                            adcSids.add(-1);
                        } else {
                            //添付ファイル区分：なし
                            List<AdrContactBinModel> acbMdlList = acbDao.select();

                            for (int i = 0; i < acbMdlList.size(); i++) {
                                adcSids.add(acbMdlList.get(i).getAdcSid());
                            }
                        }
                        searchMdl.setHaveTmpFileAdcSids(adcSids);
                    }

                    //日時
                    //検索区分
                    if (form.getAdr010dateNoKbn() == 1) {
                        searchMdl.setDateSchFlg(false);

                    } else {
                        searchMdl.setDateSchFlg(true);

                        UDate fromDate = new UDate();
                        fromDate.setTime(0);
                        fromDate.setYear(Integer.parseInt(form.getAdr010SltYearFrContact()));
                        fromDate.setMonth(Integer.parseInt(form.getAdr010SltMonthFrContact()));
                        fromDate.setDay(Integer.parseInt(form.getAdr010SltDayFrContact()));
                        fromDate.setHour(0);
                        fromDate.setMinute(0);
                        fromDate.setSecond(0);
                        fromDate.setMilliSecond(0);
                        searchMdl.setDateFr(fromDate);

                        UDate toDate = new UDate();
                        toDate.setTime(0);
                        toDate.setYear(Integer.parseInt(form.getAdr010SltYearToContact()));
                        toDate.setMonth(Integer.parseInt(form.getAdr010SltMonthToContact()));
                        toDate.setDay(Integer.parseInt(form.getAdr010SltDayToContact()));
                        toDate.setHour(23);
                        toDate.setMinute(59);
                        toDate.setSecond(59);
                        toDate.setMilliSecond(999);
                        searchMdl.setDateTo(toDate);

                    }

                    //種別
                    searchMdl.setSyubetsu(form.getAdr010SyubetsuContact());
                    //検索対象
                    String[] targets = form.getAdr010SearchTargetContact();
                    boolean targetTitle = false;
                    boolean targetBiko = false;
                    if (targets != null && targets.length > 0) {
                        for (String target : targets) {
                            if (String.valueOf(GSConstAddress.SEARCH_TARGET_TITLE).equals(target)) {
                                targetTitle = true;
                            }
                            if (String.valueOf(GSConstAddress.SEARCH_TARGET_BIKO).equals(target)) {
                                targetBiko = true;
                            }
                        }
                    }
                    searchMdl.setTargetTitle(targetTitle);
                    searchMdl.setTargetBiko(targetBiko);
                    //キーワード
                    String keyWord = NullDefault.getString(form.getAdr010SearchWordContact(), "");
                    CommonBiz cBiz = new CommonBiz();
                    searchMdl.setAdrKeyValue(cBiz.setKeyword(keyWord));
                    //キーワード区分
                    searchMdl.setKeyWordkbn(Integer.parseInt(form.getAdr010KeyWordkbnContact()));

                    break;
                default :
            }
            //ラベル
            searchMdl.setLabel(form.getAdr010searchLabel());

            //検索結果件数の確認
            Adr010Biz biz = new Adr010Biz(getRequestModel(req));
            con.setAutoCommit(true);
            try {

                String adrMsg = "";
                if (form.getAdr010cmdMode() == Adr010Const.CMDMODE_CONTACT) {
                    //コンタクト履歴情報
                    adrMsg = gsMsg.getMessage(req, "address.src.4");

                } else {
                    //アドレス情報
                    adrMsg = gsMsg.getMessage(req, "address.src.2");
                }
                if (biz.getSearchCount(con, searchMdl) <= 0) {
                    ActionMessage msg = new ActionMessage("search.data.notfound", adrMsg);
                    StrutsUtil.addMessage(errors, msg, "addressSearch");
                    addErrors(req, errors);
//                    return __doInit(map, form, req, res, con);
                }
            } finally {
                con.setAutoCommit(false);
            }

            switch (form.getAdr010cmdMode()) {
                case Adr010Const.CMDMODE_COMPANY :
                    //企業コード
                    form.setAdr010svCode(form.getAdr010code());
                    //会社名
                    form.setAdr010svCoName(form.getAdr010coName());
                    //会社名カナ
                    form.setAdr010svCoNameKn(form.getAdr010coNameKn());
                    //支店・営業所名
                    form.setAdr010svCoBaseName(form.getAdr010coBaseName());
                    //業種
                    form.setAdr010svAtiSid(form.getAdr010atiSid());
                    //都道府県
                    form.setAdr010svTdfk(form.getAdr010tdfk());
                    //備考
                    form.setAdr010svBiko(form.getAdr010biko());
                    //会社名 先頭文字
                    form.setAdr010svSearchComKana(
                            StringUtilHtml.transToHTmlPlusAmparsant(form.getAdr010SearchComKana()));

                    break;
                case Adr010Const.CMDMODE_NAME :
                    //カナ
                    form.setAdr010svSearchKana(
                            StringUtilHtml.transToHTmlPlusAmparsant(form.getAdr010SearchKana()));
                    break;

                case Adr010Const.CMDMODE_TANTO :
                    if (!AdrCommonBiz.isMyGroupSid(form.getAdr010tantoGroup())) {
                        //グループ
                        form.setAdr010svTantoGroup(Integer.parseInt(form.getAdr010tantoGroup()));
                    }
                    //ユーザ
                    form.setAdr010svTantoUser(form.getAdr010tantoUser());
                    break;
                case Adr010Const.CMDMODE_PROJECT :
                    //プロジェクトSID
                    form.setSelectingProjectSv(form.getSelectingProject());
                    form.setProjectKbnSv(form.getProjectKbn());
                    form.setStatusKbnSv(form.getStatusKbn());
                    break;
                case Adr010Const.CMDMODE_DETAILED :
                    //氏名 姓
                    form.setAdr010svUnameSei(form.getAdr010unameSei());
                    //氏名 名
                    form.setAdr010svUnameMei(form.getAdr010unameMei());
                    //氏名カナ 姓
                    form.setAdr010svUnameSeiKn(form.getAdr010unameSeiKn());
                    //氏名カナ 名
                    form.setAdr010svUnameMeiKn(form.getAdr010unameMeiKn());
                    //会社名
                    form.setAdr010svDetailCoName(form.getAdr010detailCoName());
                    //所属
                    form.setAdr010svSyozoku(form.getAdr010syozoku());
                    //役職
                    form.setAdr010svPosition(form.getAdr010position());
                    //E-MAIL
                    form.setAdr010svMail(form.getAdr010mail());
                    if (!AdrCommonBiz.isMyGroupSid(form.getAdr010detailTantoGroup())) {
                        //担当者グループ
                        form.setAdr010svDetailTantoGroup(
                                Integer.parseInt(form.getAdr010detailTantoGroup()));
                    }
                    //担当者ユーザ
                    form.setAdr010svDetailTantoUser(form.getAdr010detailTantoUser());
                    //業種
                    form.setAdr010svDetailAtiSid(form.getAdr010detailAtiSid());

                    break;
                case Adr010Const.CMDMODE_CONTACT :
                    if (!AdrCommonBiz.isMyGroupSid(form.getAdr010tantoGroupContact())) {
                        //グループ
                        form.setAdr010svTantoGroupContact(
                                Integer.parseInt(form.getAdr010tantoGroupContact()));
                    }
                    //ユーザ
                    form.setAdr010svTantoUserContact(form.getAdr010tantoUserContact());
                    //氏名 姓
                    form.setAdr010svUnameSeiContact(form.getAdr010unameSeiContact());
                    //氏名 名
                    form.setAdr010svUnameMeiContact(form.getAdr010unameMeiContact());
                    //会社名
                    form.setAdr010svCoNameContact(form.getAdr010CoNameContact());
                    //拠点
                    form.setAdr010svCoBaseNameContact(form.getAdr010CoBaseNameContact());
                    //プロジェクトSID
                    form.setAdr010svProjectContact(form.getAdr010ProjectContact());
                    //添付ファイル区分
                    form.setAdr010SvTempFilekbnContact(form.getAdr010TempFilekbnContact());
                    //日時検索区分
                    form.setAdr010svdateNoKbn(form.getAdr010dateNoKbn());
                    //日時 from
                    form.setAdr010svSltYearFrContact(form.getAdr010SltYearFrContact());
                    form.setAdr010svSltMonthFrContact(form.getAdr010SltMonthFrContact());
                    form.setAdr010svSltDayFrContact(form.getAdr010SltDayFrContact());
                    form.setAdr010svSltFrContact(form.getAdr010SltFrContact());
                    //日時 to
                    form.setAdr010svSltYearToContact(form.getAdr010SltYearToContact());
                    form.setAdr010svSltMonthToContact(form.getAdr010SltMonthToContact());
                    form.setAdr010svSltDayToContact(form.getAdr010SltDayToContact());
                    form.setAdr010svSltToContact(form.getAdr010SltToContact());
                    //種別
                    form.setAdr010svSyubetsuContact(form.getAdr010SyubetsuContact());
                    //検索対象
                    form.setAdr010svSearchTargetContact(form.getAdr010SearchTargetContact());
                    //キーワード
                    form.setAdr010svSearchWordContact(form.getAdr010SearchWordContact());
                    //キーワード区分
                    form.setAdr010SvKeyWordkbnContact(form.getAdr010KeyWordkbnContact());

                    break;
                default :
            }

            //ラベル
            form.setAdr010svSearchLabel(form.getAdr010searchLabel());

            form.setAdr010searchFlg(1);
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
    private ActionForward __doSearchBtn(ActionMapping map,
                                    Adr010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        form.setAdr010SearchComKana(null);

        return __doSearch(map, form, req, res, con);
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
    private ActionForward __doExport(ActionMapping map, Adr010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        GsMessage gsMsg = new GsMessage();
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);
        String fileName = Adr010CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        con.setAutoCommit(true);
        Adr010CsvWriter csvWriter = new Adr010CsvWriter();
        Adr010Biz biz = new Adr010Biz(getRequestModel(req));

        Adr010ParamModel paramMdl = new Adr010ParamModel();
        paramMdl.setParam(form);
        csvWriter.setSearchModel(biz.createSearchModel(
                paramMdl, getSessionUserModel(req).getUsrsid(), con));
        paramMdl.setFormData(form);

        csvWriter.outputCsv(con, tempDir, getRequestModel(req));
        con.setAutoCommit(false);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //ログ出力処理
        AdrCommonBiz adrBiz = new AdrCommonBiz(con);
        adrBiz.outPutLog(
                map, req, res,
                gsMsg.getMessage(req, "cmn.export"), GSConstLog.LEVEL_INFO, fileName);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        return null;
    }

    /**コンタクト履歴情報ダウンロードの処理
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
    private ActionForward __doExportCsv(
        ActionMapping map,
        Adr010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);
        String fileName = Adr160CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        __doExportContact(map, form, req, res, con, tempDir);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstAddress.PLUGIN_ID_ADDRESS, TEMP_DIRECTORY_ID);

        return null;
    }

    /**
     * <br>[機  能] エクスポート処理を実行
     * <br>[解  説] コンタクト履歴で使用
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
    private ActionForward __doExportContact(ActionMapping map, Adr010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {

        log__.debug("コンタクト履歴エクスポート処理(CSV)");

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();

        //CSVファイルを作成
        con.setAutoCommit(true);
        Adr160CsvWriter write = new Adr160CsvWriter();
        write.setSessionUserSid(sessionUsrSid);
        write.setAddressSid(form.getAdr010EditAdrSid());
        write.setContactSchType(GSConstAddress.DSP_CONTACT_ADR010);

        //検索条件をセット
        Adr010Biz biz = new Adr010Biz(getRequestModel(req));

        Adr010ParamModel paramMdl = new Adr010ParamModel();
        paramMdl.setParam(form);
        write.setSearchModel(biz.createSearchModel(
                paramMdl, getSessionUserModel(req).getUsrsid(), con));
        paramMdl.setFormData(form);

        write.outputCsv(con, outDir, getRequestModel(req));
        con.setAutoCommit(false);

        return null;
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
    private ActionForward __doDeleteConfirmation(ActionMapping map,
                                                  Adr010Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        ActionForward forward = null;

        try {
            //削除対象選択チェック
            ActionErrors errors =
                form.validateSelectCheck010(req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            RequestModel reqMdl = getRequestModel(req);

            AdrAddressDao dao = new AdrAddressDao(con);
            //削除対象のアドレス一覧を取得する
            Adr010ParamModel paramMdl = new Adr010ParamModel();
            paramMdl.setParam(form);
            ArrayList<AdrAddressModel> delList =
                dao.selAdrList(paramMdl.getAdr010selectSid());
            AddressBiz adrBiz = new AddressBiz(reqMdl);
            int sessionUserSid = getSessionUserModel(req).getUsrsid();
            StringBuilder delMsg = new StringBuilder();
            if (!delList.isEmpty()) {
                boolean admFlg = adrBiz.isAdmin(con, sessionUserSid);
                boolean arestFlg = adrBiz.isHaveArest(con, sessionUserSid);
                for (AdrAddressModel adrMdl: delList) {

                    if (!adrBiz.isEditAddressData(con, adrMdl.getAdrSid(),
                            sessionUserSid, arestFlg, admFlg)) {
                        //最初の要素以外は改行を挿入
                        if (delMsg.length() != 0) {
                            delMsg.append("<br>");
                        }
                        delMsg.append("・");
                        delMsg.append(StringUtilHtml.transToHTmlPlusAmparsant(
                                NullDefault.getString(adrMdl.getAdrSei(), "")));
                        delMsg.append("&nbsp;");
                        delMsg.append(StringUtilHtml.transToHTmlPlusAmparsant(
                                NullDefault.getString(adrMdl.getAdrMei(), "")));
                    }
                }
            }
            if (delMsg.length() > 0) {
                GsMessage gsMsg = new GsMessage();
                ActionMessage msg = null;
                //アドレス情報
                String textAddress = gsMsg.getMessage(req, "address.src.2");
                //編集
                String textEdit = gsMsg.getMessage(req, "cmn.edit");
                //削除
                String textDel = gsMsg.getMessage(req, "cmn.delete");

                msg = new ActionMessage(
                        "error.edit.power.list", textAddress, textEdit, textDel, delMsg.toString());
                StrutsUtil.addMessage(errors, msg, "adrSid");

            }
            boolean admFlg = adrBiz.isAdmin(con, sessionUserSid);
            ArrayList<AdrAddressModel> delConfirm =
                    dao.selectAdrList(paramMdl.getAdr010selectSid(), sessionUserSid, admFlg);

            if (delMsg.length() > 0
                    || delConfirm.size() == 0) {
                //すでに削除されている場合
                GsMessage gsMsg = new GsMessage();
                ActionMessage msg = null;

                //アドレス情報
                String textAddress = gsMsg.getMessage(req, "address.src.2");
                //変更
                String textDel = gsMsg.getMessage(req, "cmn.delete");

                msg = new ActionMessage(
                        "error.edit.power.notfound", textAddress, textDel);

                StrutsUtil.addMessage(errors, msg, "adrSid");
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);

            }

            paramMdl.setFormData(form);

            //削除確認画面を設定
            forward = __setConfirmationDsp(map, req, form, delList);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return forward;
    }

    /**
     * <br>[機  能] 確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param delList 削除対象リスト
     * @return ActionForward フォワード
     */
    private ActionForward __setConfirmationDsp(ActionMapping map,
                                                HttpServletRequest req,
                                                Adr010Form form,
                                                ArrayList<AdrAddressModel> delList) {

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String delMsg = "";

       /************************************************************************
        *
        * 確認画面に表示するアドレスを作成する
        *
        ************************************************************************/

        if (!delList.isEmpty()) {

            for (int i = 0; i < delList.size(); i++) {

                AdrAddressModel ret = (AdrAddressModel) delList.get(i);

                delMsg += "・";
                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(ret.getAdrSei(), ""));
                delMsg += "&nbsp;";
                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(ret.getAdrMei(), ""));

                //最後の要素以外は改行を挿入
                if (i < delList.size() - 1) {
                    delMsg += "<br>";
                }

            }
        }

       /************************************************************************
        *
        * 確認画面設定
        *
        ************************************************************************/

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = map.findForward("redraw");

        //OKボタンクリック遷移先
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");

        cmn999Form.setMessage(
                msgRes.getMessage(
                        "sakujo.kakunin.list", gsMsg.getMessage(req, "address.src.2"), delMsg));

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //hiddenパラメータ
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下
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
     */
    private ActionForward __doDeleteOk(ActionMapping map,
                                        Adr010Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        ArrayList<AdrAddressModel> delList = new ArrayList<AdrAddressModel>();

        try {
            GsMessage gsMsg = new GsMessage();
            //削除処理実行
            Adr010Biz biz = new Adr010Biz(getRequestModel(req));
            Adr010ParamModel paramMdl = new Adr010ParamModel();
            paramMdl.setParam(form);
            delList = biz.deleteAddress(paramMdl, getSessionUserModel(req).getUsrsid(), con);
            paramMdl.setFormData(form);

            //ログ出力処理
            AdrCommonBiz adrBiz = new AdrCommonBiz(con);
            String msg = "[" + gsMsg.getMessage("cmn.target") + "]";
            boolean multiFlg = false;
            for (AdrAddressModel mdl : delList) {
                if (multiFlg) {
                    msg += "\r\n";
                } else {
                    multiFlg = true;
                }
                msg += mdl.getAdrSei() + " " + mdl.getAdrMei();
            }
            String opCode = gsMsg.getMessage(req, "cmn.delete");
            adrBiz.outPutLog(
                    map, req, res, opCode, GSConstLog.LEVEL_TRACE, msg);

            commitFlg = true;

            //完了画面設定
            return __setCompDsp(map, req, form);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Adr010Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();

        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "address.src.2")));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ラベル複数設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 例外
     */
    private ActionForward __setLabelMult(ActionMapping map,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Adr010Form form,
                                        Connection con) throws Exception {

        //ラベル設定
        Adr010Biz biz = new Adr010Biz(getRequestModel(req));
        Adr010ParamModel paramMdl = new Adr010ParamModel();
        paramMdl.setParam(form);
        biz.setLabelMult(paramMdl, con, getSessionUserModel(req).getUsrsid());
        paramMdl.setFormData(form);


        return __doInit(map, form, req, res, con);
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
                                                  Adr010Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception {

        //送信対象選択チェック
        ActionErrors errors =
            form.validateSelectCheckWebmail(req);
        if (!errors.isEmpty()
                && form.getAdr010AdrSid() == 0) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Adr010Biz biz = new Adr010Biz(getRequestModel(req));

        //宛先・CC・BCCにセットするアドレスSIDを設定する
        Adr010ParamModel paramMdl = new Adr010ParamModel();
        paramMdl.setParam(form);
        biz.setAddressAdrSid(paramMdl, con);
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
            Adr010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws Exception {

        Adr010Biz biz = new Adr010Biz(getRequestModel(req));
        //ユーザを削除する。
        Adr010ParamModel paramMdl = new Adr010ParamModel();
        paramMdl.setParam(form);
        biz.deleteUserAtesaki(paramMdl, con);
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }

}