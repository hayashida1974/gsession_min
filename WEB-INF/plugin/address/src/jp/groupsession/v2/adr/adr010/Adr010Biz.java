package jp.groupsession.v2.adr.adr010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.AdrUsedDataBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.dao.Adr010Dao;
import jp.groupsession.v2.adr.adr010.model.Adr010ProjectDataModel;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.adr010.model.AdrCategoryLabelModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrBelongLabelDao;
import jp.groupsession.v2.adr.dao.AdrContactBinDao;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrBelongLabelModel;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アドレス帳一覧画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr010Biz.class);

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr010Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr010ParamModel
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @throws Exception 実行例外
     */
    public void setInitData(Adr010ParamModel paramMdl,
                             Connection con,
                             BaseUserModel userMdl)
    throws Exception {

        log__.debug("start");

        GsMessage gsMsg = new GsMessage(reqMdl_);

        //管理者設定ボタン表示フラグを設定
        AddressBiz addressBiz = new AddressBiz(reqMdl_);
        int sessionUserSid = userMdl.getUsrsid();
        boolean adminUser = addressBiz.isAdmin(con, sessionUserSid);

        if (adminUser) {
            paramMdl.setAdr010viewAdminBtn(1);
        }

        AdrAconfDao aconfDao = new AdrAconfDao(con);
        AdrAconfModel aconfMdl = aconfDao.selectAconf();
        //役職情報ボタン表示フラグを設定
        if (adminUser || (aconfMdl == null || aconfMdl.getAacYksEdit() == 0)) {
            paramMdl.setAdr010viewYksBtn(1);
        }
        //業種情報ボタン表示フラグを設定
        if (adminUser || (aconfMdl == null || aconfMdl.getAacAtiEdit() == 0)) {
            paramMdl.setAdr010viewGyosyuBtn(1);
        }
        //会社情報ボタン表示フラグを設定
        if (adminUser || (aconfMdl == null || aconfMdl.getAacAcoEdit() == 0)) {
            paramMdl.setAdr010viewCompanyBtn(1);
        }
        //ラベル設定ボタン表示フラグを設定
        if (adminUser || (aconfMdl == null || aconfMdl.getAacAlbEdit() == 0)) {
            paramMdl.setAdr010viewLabelBtn(1);
        }
        //エクスポートボタン表示フラグを設定
        if (adminUser || (aconfMdl == null || aconfMdl.getAacExport() == 0)) {
            paramMdl.setAdr010viewExportBtn(1);
        }
        //編集権限フラグを設定
        if (adminUser || addressBiz.isHaveArest(con, sessionUserSid)) {
            paramMdl.setAdr010AbleEdit(1);
        } else {
            paramMdl.setAdr010AbleEdit(0);
        }

        //モード別に各種コンボを設定する
        UserBiz userBiz = new UserBiz();
        Adr010Dao adr010Dao = new Adr010Dao(con);
        AdrCommonBiz adrBiz = new AdrCommonBiz();
        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> mylabelList = new ArrayList<LabelValueBean>();
        switch (paramMdl.getAdr010cmdMode()) {
            case Adr010Const.CMDMODE_COMPANY :
                log__.debug("CMDMODE_COMPANY");
                //業種コンボを設定
                paramMdl.setAtiCmbList(addressBiz.getGyosyuLabelList(con));

                //都道府県コンボを設定
                paramMdl.setTdfkCmbList(cmnBiz.getTdfkLabelList(con, gsMsg));

                List<String> comkanaList
                    = adr010Dao.getCompanyInitialList(userMdl.getUsrsid());
                paramMdl.setAdr010cnameKanaList(comkanaList);

                break;

            case Adr010Const.CMDMODE_NAME :
                log__.debug("CMDMODE_NAME");
                List<String> kanaList = adr010Dao.getSeiInitialList(userMdl.getUsrsid());
                paramMdl.setAdr010unameKanaList(kanaList);
                break;

            case Adr010Const.CMDMODE_TANTO :
                log__.debug("CMDMODE_TANTO");

                if (paramMdl.getAdr010tantoGroup().equals("-2")
                        && paramMdl.getAdr010tantoUser() == -2) {

                    CmnBelongmDao belongmDao = new CmnBelongmDao(con);

                    int userSid = userMdl.getUsrsid();
                    int defGrpSid = belongmDao.selectUserBelongGroupDef(userSid);

                    paramMdl.setAdr010tantoGroup(String.valueOf(defGrpSid));
                    paramMdl.setAdr010tantoUser(userSid);
                }

                //グループコンボを設定
                List<LabelValueBean> grpComboTanto = new ArrayList<LabelValueBean>();
                grpComboTanto = addressBiz.getGroupLabelList(con);

                //マイグループを追加
                mylabelList = adrBiz.getMyGroupLabel(userMdl.getUsrsid(), con);
                grpComboTanto.addAll(1, mylabelList);
                paramMdl.setGroupCmbList(grpComboTanto);

                int dspGpSidTanto = 0;
                boolean myGroupFlgTanto = false;
                if (AdrCommonBiz.isMyGroupSid(paramMdl.getAdr010tantoGroup())) {
                    dspGpSidTanto = AdrCommonBiz.getDspGroupSid(paramMdl.getAdr010tantoGroup());
                    myGroupFlgTanto = true;
                } else {
                    dspGpSidTanto = Integer.parseInt(paramMdl.getAdr010tantoGroup());
                }

                //ユーザコンボを設定
                List<UsrLabelValueBean> usrListTanto = new ArrayList<UsrLabelValueBean>();

                if (myGroupFlgTanto) {
                    usrListTanto = userBiz.getMyGroupUserLabelList(con,
                                                              userMdl.getUsrsid(),
                                                              dspGpSidTanto,
                                                              null);
                } else {
                    usrListTanto = userBiz.getNormalUserLabelList(con,
                                                             dspGpSidTanto,
                                                             null, true, gsMsg);
                }

                paramMdl.setUserCmbList(usrListTanto);
                break;
            case Adr010Const.CMDMODE_PROJECT :
                //プロジェクトコンボを設定
                paramMdl.setProjectCmbList(getAddressProjectLabelList(
                        con, paramMdl, userMdl.getUsrsid()));
                paramMdl.setProjectCmbsize(paramMdl.getProjectCmbList().size());
                break;
            case Adr010Const.CMDMODE_DETAILED :
                //役職コンボを設定
                paramMdl.setPositionCmbList(getAddressPositionLabelList(con));

                //グループコンボを設定
                List<LabelValueBean> grpComboDetailed = new ArrayList<LabelValueBean>();
                grpComboDetailed = addressBiz.getGroupLabelList(con);

                //マイグループを追加
                mylabelList = adrBiz.getMyGroupLabel(userMdl.getUsrsid(), con);
                grpComboDetailed.addAll(1, mylabelList);

                paramMdl.setGroupCmbList(grpComboDetailed);

                //ユーザコンボを設定
                int dspGpSidDetailed = 0;
                boolean myGroupFlgDetailed = false;
                if (AdrCommonBiz.isMyGroupSid(paramMdl.getAdr010detailTantoGroup())) {
                    dspGpSidDetailed = AdrCommonBiz.getDspGroupSid(
                                             paramMdl.getAdr010detailTantoGroup());
                    myGroupFlgDetailed = true;
                } else {
                    dspGpSidDetailed = Integer.parseInt(paramMdl.getAdr010detailTantoGroup());
                }

                //ユーザコンボを設定
                List<UsrLabelValueBean> usrListDetailed = new ArrayList<UsrLabelValueBean>();
                if (myGroupFlgDetailed) {
                    usrListDetailed = userBiz.getMyGroupUserLabelList(con,
                                                              userMdl.getUsrsid(),
                                                              dspGpSidDetailed,
                                                              null);
                } else {
                    usrListDetailed = userBiz.getNormalUserLabelList(con,
                                                             dspGpSidDetailed,
                                                             null, true, gsMsg);
                }

                paramMdl.setUserCmbList(usrListDetailed);

                //業種コンボを設定
                paramMdl.setAtiCmbList(addressBiz.getGyosyuLabelList(con));
                break;
            case Adr010Const.CMDMODE_CONTACT :
                log__.debug("CMDMODE_CONTACT");

                if (paramMdl.getAdr010tantoGroupContact().equals("-2")
                        && paramMdl.getAdr010tantoUserContact() == -2) {
                    int userSid = userMdl.getUsrsid();
                    paramMdl.setAdr010tantoGroupContact(
                            String.valueOf(GSConstUser.USER_RESERV_SID));
                    paramMdl.setAdr010tantoUserContact(userSid);
                }
                //グループコンボを設定
                List<LabelValueBean> grpComboContact = new ArrayList<LabelValueBean>();
                grpComboContact = addressBiz.getGroupLabelListContact(con);

                //マイグループを追加
                mylabelList = adrBiz.getMyGroupLabel(userMdl.getUsrsid(), con);
                grpComboContact.addAll(1, mylabelList);

                paramMdl.setGroupCmbList(grpComboContact);

                //ユーザコンボを設定
                int dspGpSidContacted = 0;
                boolean myGroupFlgContacted = false;

                if (AdrCommonBiz.isMyGroupSid(
                        String.valueOf(paramMdl.getAdr010tantoGroupContact()))) {
                    dspGpSidContacted = AdrCommonBiz.getDspGroupSid(
                            String.valueOf(paramMdl.getAdr010tantoGroupContact()));
                    myGroupFlgContacted = true;
                } else {
                    dspGpSidContacted = Integer.parseInt(paramMdl.getAdr010tantoGroupContact());
                }

                List<UsrLabelValueBean> usrListContacted = new ArrayList<UsrLabelValueBean>();
                if (myGroupFlgContacted) {
                    usrListContacted = userBiz.getMyGroupUserLabelList(con,
                                                              userMdl.getUsrsid(),
                                                              dspGpSidContacted,
                                                              null);
                } else {
                    usrListContacted = userBiz.getNormalAllUserLabelList(con,
                                                             gsMsg,
                                                             dspGpSidContacted,
                                                             null, true);
                }

                paramMdl.setUserCmbList(usrListContacted);

                //プロジェクトコンボを設定
                paramMdl.setProjectCmbList(getAddressProjectLabelList(
                        con, paramMdl, userMdl.getUsrsid()));

                //検索対象のデフォルトを設定
                if (paramMdl.getAdr010InitDspContactFlg() == 0) {
                    if (paramMdl.getAdr010SearchTargetContact() == null) {
                        paramMdl.setAdr010SearchTargetContact(getDefultSearchTarget());
                    }

                    paramMdl.setAdr010dateNoKbn(1);
                }

                if (paramMdl.getAdr010dateNoKbn() == 1) {
                    UDate dspDate = new UDate();
                    UDate toDspDate = new UDate();
                    //基準日から１年後
                    toDspDate.addYear(1);

                    //期間設定From
                    paramMdl.setAdr010SltYearFrContact(dspDate.getStrYear());
                    paramMdl.setAdr010SltMonthFrContact(String.valueOf(dspDate.getMonth()));
                    paramMdl.setAdr010SltDayFrContact(String.valueOf(dspDate.getIntDay()));
                    
                    //期間設定To
                    paramMdl.setAdr010SltYearToContact(toDspDate.getStrYear());
                    paramMdl.setAdr010SltMonthToContact(String.valueOf(toDspDate.getMonth()));
                    paramMdl.setAdr010SltDayToContact(String.valueOf(toDspDate.getIntDay()));
                    
                }
                //コンタクト履歴初期表示フラグ
                paramMdl.setAdr010InitDspContactFlg(1);
                break;
            default :
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getAdr010SltFrContact() == null) {
            dateBiz.setDateParam(
                    paramMdl, "adr010SltFrContact", "adr010SltYearFrContact",
                    "adr010SltMonthFrContact", "adr010SltDayFrContact", null);

        }
        if (paramMdl.getAdr010SltToContact() == null) {        
            dateBiz.setDateParam(
                    paramMdl, "adr010SltToContact", "adr010SltYearToContact",
                    "adr010SltMonthToContact", "adr010SltDayToContact", null);
        }

        __setCategoryLabelData(paramMdl, con);
        //カテゴリ開閉フラグが未設定だった場合
        if (paramMdl.getAdr010CategorySetInitFlg() == 0) {
            //カテゴリ開閉フラグの設定
            __setCategoryOpenFlg(paramMdl, con);
            paramMdl.setAdr010CategorySetInitFlg(1);
        }

        //ラベルコンボを設定
        AdrLabelDao labelDao = new AdrLabelDao(con);
        if (paramMdl.getAdr010selectCategory() == -1) {
            paramMdl.setSelectLabelList(labelDao.select());
        } else {
            paramMdl.setSelectLabelList(labelDao.getLabelInCategory(
                    paramMdl.getAdr010selectCategory()));
        }

        //宛先・CC・BCCにセットするアドレス一覧を取得する(webmail)用
        if (paramMdl.getAdr010webmail() == 1) {
            paramMdl.setAdr010AtskList(__getSelAdrList(paramMdl, 0, con));
            paramMdl.setAdr010CcList(__getSelAdrList(paramMdl, 1, con));
            paramMdl.setAdr010BccList(__getSelAdrList(paramMdl, 2, con));
        }

        //検索
        if (paramMdl.getAdr010searchFlg() == 1) {
            Adr010SearchModel searchMdl = createSearchModel(paramMdl, sessionUserSid, con);

            //最大件数
            int searchCnt = getSearchCount(con, searchMdl);
            int maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;
            AdrUconfDao uconfDao = new AdrUconfDao(con);
            AdrUconfModel uconfMdl = uconfDao.select(sessionUserSid);
            if (uconfMdl != null && uconfMdl.getAucAdrcount() > 0) {
                maxCnt = uconfMdl.getAucAdrcount();
            } else {
                maxCnt = Integer.parseInt(GSConstAddress.DEFAULT_ADRCOUNT);
            }

            //ページ調整
            int maxPage = searchCnt / maxCnt;
            if ((searchCnt % maxCnt) > 0) {
                maxPage++;
            }
            int page = paramMdl.getAdr010page();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }
            paramMdl.setAdr010page(page);
            paramMdl.setAdr010pageTop(page);
            paramMdl.setAdr010pageBottom(page);

            //ページコンボ設定
            if (maxPage > 1) {
                paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
            }
            searchMdl.setPage(page);
            searchMdl.setMaxViewCount(maxCnt);

            paramMdl.setDetailList(adr010Dao.getSearchResultList(searchMdl));

            //検索条件文字列を設定
            StringBuilder searchParam = new StringBuilder();

            switch (paramMdl.getAdr010cmdMode()) {
                case Adr010Const.CMDMODE_COMPANY :
                    //会社
                    __getSearchParamCompany(paramMdl, searchParam, con);
                    break;
                case Adr010Const.CMDMODE_NAME :
                    //氏名
                    break;
                case Adr010Const.CMDMODE_TANTO :
                    //担当者
                    __getSearchParamTanto(paramMdl, searchParam, con);
                    break;
                case Adr010Const.CMDMODE_PROJECT :
                    //プロジェクト区分
                    __getSearchParamProject(paramMdl, searchParam, con);
                    break;
                case Adr010Const.CMDMODE_DETAILED :
                    //詳細検索
                    __getSearchParamDetailed(paramMdl, searchParam, con);
                    break;
                case Adr010Const.CMDMODE_CONTACT :
                    //コンタクト履歴
                    __getSearchParamContact(paramMdl, searchParam, con);
                    break;
                default :
            }

            //ラベル
            String[] svLabel = paramMdl.getAdr010svSearchLabel();
            if (svLabel != null && svLabel.length > 0) {
                List<String> selectLabelList = new ArrayList<String>();
                Collections.addAll(selectLabelList, svLabel);

                String labelStr = "";
                for (AdrLabelModel labelData : paramMdl.getSelectLabelList()) {
                    if (selectLabelList.contains(String.valueOf(labelData.getAlbSid()))) {
                        if (labelStr.length() > 0) {
                            labelStr += ",";
                        }
                        labelStr += labelData.getAlbName();
                    }
                }

                if (paramMdl.getAdr010cmdMode() != Adr010Const.CMDMODE_COMPANY
                && paramMdl.getAdr010cmdMode() != Adr010Const.CMDMODE_NAME) {
                    __addSearchParam(searchParam, gsMsg.getMessage("cmn.label"), labelStr);
                }

                StringBuilder labelParam = new StringBuilder("");
                __addSearchParam(labelParam, gsMsg.getMessage("cmn.label"), labelStr);

                String escLabelParam = StringUtilHtml.transToHTmlWithWbr(labelParam.toString(), 10);
                paramMdl.setAdr010searchLabelString(escLabelParam);
            }
            String escSearchParam = StringUtilHtml.transToHTmlWithWbr(searchParam.toString(), 10);
            paramMdl.setAdr010searchParamString(escSearchParam);
        }

        log__.debug("end");

    }

    /**
     * <br>[機  能] 検索条件文字列を取得する（コンタクト履歴検索）
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param searchParam 検索条件文字列パラメータ
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __getSearchParamContact(Adr010ParamModel paramMdl,
             StringBuilder searchParam, Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        Adr010Dao adr010Dao = new Adr010Dao(con);
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        //担当者
        if (paramMdl.getAdr010svTantoUserContact() > 0) {
            CmnUsrmInfModel usrmInfMdl
                = usrmInfDao.select(paramMdl.getAdr010svTantoUserContact());
            if (usrmInfMdl != null) {
                __addSearchParam(searchParam, gsMsg.getMessage("cmn.staff"),
                                usrmInfMdl.getUsiSei() + " " + usrmInfMdl.getUsiMei());
            }

        //所属グループ
        } else if (paramMdl.getAdr010svTantoUserContact() < 1
                && paramMdl.getAdr010svTantoGroupContact() > 0) {

            CmnGroupmDao groupDao = new CmnGroupmDao(con);
            CmnGroupmModel groupMdl
                = groupDao.select(paramMdl.getAdr010svTantoGroupContact());
            if (groupMdl != null) {
                __addSearchParam(searchParam,
                        gsMsg.getMessage("cmn.affiliation.group"),
                        groupMdl.getGrpName());
            }
        }

        //氏名
        if (StringUtil.isNullZeroString(paramMdl.getAdr010svUnameSeiContact())
        || StringUtil.isNullZeroString(paramMdl.getAdr010svUnameMeiContact())) {
            //氏名 姓
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name")
                    + " "
                    + gsMsg.getMessage("cmn.lastname"),
                    paramMdl.getAdr010svUnameSeiContact());
            //氏名 名
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.name")
                    + " "
                    +  gsMsg.getMessage("cmn.name3"),
                    paramMdl.getAdr010svUnameMeiContact());
        } else {
            //氏名
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name"),
                    paramMdl.getAdr010svUnameSeiContact()
                      + " " + paramMdl.getAdr010svUnameMeiContact());
        }

        //会社名
        __addSearchParam(searchParam,
                gsMsg.getMessage("cmn.company.name"),
                paramMdl.getAdr010svCoNameContact());

        //支店・営業所名
        __addSearchParam(searchParam,
                gsMsg.getMessage("address.10"),
                paramMdl.getAdr010svCoBaseNameContact());

        //プロジェクト名
        __addSearchParam(searchParam, gsMsg.getMessage("address.adr010.project.6"),
            adr010Dao.getProjectName(paramMdl.getAdr010svProjectContact()));

        //添付ファイル区分
        String tempFileKbn = "";
        //指定なし
        if (paramMdl.getAdr010SvTempFilekbnContact().equals(
                String.valueOf(GSConstAddress.TEMPFILE_KBN_FREE))) {
            tempFileKbn = gsMsg.getMessage("cmn.specified.no");
        //あり
        } else if (paramMdl.getAdr010SvTempFilekbnContact().equals(
                String.valueOf(GSConstAddress.TEMPFILE_KBN_EXIST))) {
            tempFileKbn = gsMsg.getMessage("address.adr010.contact.5");
        //なし
        } else {
            tempFileKbn = gsMsg.getMessage("cmn.no");
        }
        __addSearchParam(searchParam,
                gsMsg.getMessage("cmn.attach.file"), tempFileKbn);

        if (paramMdl.getAdr010svdateNoKbn() != 1) {
            //日時
            StringBuilder sb = new StringBuilder();
            sb.append(paramMdl.getAdr010svSltYearFrContact());
            sb.append("/");
            sb.append(paramMdl.getAdr010svSltMonthFrContact());
            sb.append("/");
            sb.append(paramMdl.getAdr010svSltDayFrContact());
            sb.append("～");
            sb.append(paramMdl.getAdr010svSltYearToContact());
            sb.append("/");
            sb.append(paramMdl.getAdr010svSltMonthToContact());
            sb.append("/");
            sb.append(paramMdl.getAdr010svSltDayToContact());
            String date = sb.toString();
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.date"), date);
        }

        //種別
        String syubetsuStr = "";
        //電話
        if (paramMdl.getAdr010svSyubetsuContact() == GSConst.CONTYP_TEL) {
            syubetsuStr = gsMsg.getMessage("cmn.phone");
        //メール
        } else if (paramMdl.getAdr010svSyubetsuContact() == GSConst.CONTYP_MAIL) {
            syubetsuStr = gsMsg.getMessage("cmn.mail");
        //WEB
        } else if (paramMdl.getAdr010svSyubetsuContact() == GSConst.CONTYP_WEB) {
            syubetsuStr = "WEB";
        //打ち合わせ
        } else if (paramMdl.getAdr010svSyubetsuContact() == GSConst.CONTYP_MEETING) {
            syubetsuStr = gsMsg.getMessage("address.28");
        //その他
        } else if (paramMdl.getAdr010svSyubetsuContact() == GSConst.CONTYP_OTHER) {
            syubetsuStr = gsMsg.getMessage("cmn.other");
        } else {
            syubetsuStr = gsMsg.getMessage("cmn.without.specifying");
        }
        __addSearchParam(searchParam, gsMsg.getMessage("cmn.type"), syubetsuStr);

        if (!StringUtil.isNullZeroString(paramMdl.getAdr010svSearchWordContact())) {

            //キーワード
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.keyword"),
                    paramMdl.getAdr010svSearchWordContact());

            //検索対象
            String[] targets = paramMdl.getAdr010svSearchTargetContact();
            StringBuilder targetSb = new StringBuilder();
            boolean targetFlg = false;
            if (targets != null && targets.length > 0) {
                for (String target : targets) {
                    if (String.valueOf(
                            GSConstAddress.SEARCH_TARGET_TITLE).equals(target)) {
                        targetSb.append(gsMsg.getMessage("cmn.title"));
                        targetFlg = true;
                    }
                    if (String.valueOf(
                            GSConstAddress.SEARCH_TARGET_BIKO).equals(target)) {
                        if (targetFlg) {
                            targetSb.append(",");
                        }
                        targetSb.append(gsMsg.getMessage("cmn.memo"));
                    }
                }
            }
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.search2"), targetSb.toString());
        }
    }

    /**
     * <br>[機  能] 検索条件文字列を取得する（詳細検索）
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param searchParam 検索条件文字列パラメータ
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __getSearchParamDetailed(Adr010ParamModel paramMdl,
            StringBuilder searchParam,
            Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);

        //氏名
        if (StringUtil.isNullZeroString(paramMdl.getAdr010svUnameSei())
        || StringUtil.isNullZeroString(paramMdl.getAdr010svUnameMei())) {
            //氏名 姓
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.name")
                    + " "
                    + gsMsg.getMessage("cmn.lastname"),
                    paramMdl.getAdr010svUnameSei());
            //氏名 名
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name")
                    + " "
                    + gsMsg.getMessage("cmn.name3"),
                    paramMdl.getAdr010svUnameMei());
        } else {
            //氏名
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name"),
                    paramMdl.getAdr010svUnameSei()
                    + " " + paramMdl.getAdr010svUnameMei());
        }
        //氏名カナ
        if (StringUtil.isNullZeroString(paramMdl.getAdr010svUnameSeiKn())
        || StringUtil.isNullZeroString(paramMdl.getAdr010svUnameMeiKn())) {
            //氏名カナ 姓
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.name.kana")
                    + " "
                    + gsMsg.getMessage("cmn.lastname"),
                    paramMdl.getAdr010svUnameSeiKn());
            //氏名カナ 名
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.name.kana")
                    + " "
                    + gsMsg.getMessage("cmn.name3"),
                    paramMdl.getAdr010svUnameMeiKn());
        } else {
            //氏名カナ
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name.kana"),
                    paramMdl.getAdr010svUnameSeiKn()
                    + " " + paramMdl.getAdr010svUnameMeiKn());
        }
        //会社名
        __addSearchParam(searchParam, gsMsg.getMessage("cmn.company.name"),
                paramMdl.getAdr010svDetailCoName());
        //所属
        __addSearchParam(searchParam, gsMsg.getMessage("cmn.affiliation"),
                paramMdl.getAdr010svSyozoku());
        //役職
        AdrPositionDao positionDao = new AdrPositionDao(con);
        if (paramMdl.getAdr010svPosition() > 0) {
            AdrPositionModel positionMdl
                    = positionDao.select(paramMdl.getAdr010svPosition());
            if (positionMdl != null) {
                __addSearchParam(searchParam, gsMsg.getMessage("cmn.post"),
                        positionMdl.getApsName());
            }
        }
        //E-MAIL
        __addSearchParam(searchParam, "E-MAIL", paramMdl.getAdr010svMail());
        //担当者
        if (paramMdl.getAdr010svDetailTantoUser() > 0) {
            CmnUsrmInfModel usrmInfMdl
                = usrmInfDao.select(paramMdl.getAdr010svDetailTantoUser());
            if (usrmInfMdl != null) {
                __addSearchParam(searchParam, gsMsg.getMessage("cmn.staff"),
                                usrmInfMdl.getUsiSei() + " " + usrmInfMdl.getUsiMei());
            }
        }
        //業種
        if (paramMdl.getAdr010svDetailAtiSid() > 0) {
            AdrTypeindustryModel industryMdl
                    = industryDao.select(paramMdl.getAdr010svDetailAtiSid());
            if (industryMdl != null) {
                __addSearchParam(searchParam,
                                 gsMsg.getMessage("address.11"),
                                 industryMdl.getAtiName());
            }
        }
    }

    /**
     * <br>[機  能] 検索条件文字列を取得する（プロジェクト検索）
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param searchParam 検索条件文字列パラメータ
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __getSearchParamProject(Adr010ParamModel paramMdl,
             StringBuilder searchParam, Connection con)
            throws SQLException {

        Adr010Dao adr010Dao = new Adr010Dao(con);
        GsMessage gsMsg = new GsMessage(reqMdl_);

        //参加プロジェクト
        if (paramMdl.getProjectKbnSv() == GSConstAddress.PROTYPE_ADD) {
            __addSearchParam(searchParam,
                    gsMsg.getMessage("address.adr010.project.1"),
                    gsMsg.getMessage("cmn.join.project"));
        //全て
        } else {
            __addSearchParam(searchParam,
                    gsMsg.getMessage("address.adr010.project.1"),
                    gsMsg.getMessage("cmn.all"));
        }

        //状態
        //未完
        if (paramMdl.getStatusKbnSv() == GSConstAddress.STATUS_NO) {
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.status"),
                    gsMsg.getMessage("address.adr010.project.4"));
        //完了
        } else if (paramMdl.getStatusKbnSv() == GSConstAddress.STATUS_COMP) {
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.status"),
                    gsMsg.getMessage("cmn.complete"));
        //全て
        } else {
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.status"),
                    gsMsg.getMessage("cmn.all"));
        }

        //プロジェクト名
        __addSearchParam(searchParam, gsMsg.getMessage("address.adr010.project.6"),
            adr010Dao.getProjectName(NullDefault.getInt(
                paramMdl.getSelectingProjectSv(), 0)));
    }

    /**
     * <br>[機  能] 検索条件文字列を取得する（会社検索）
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param searchParam 検索条件文字列パラメータ
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __getSearchParamCompany(Adr010ParamModel paramMdl,
            StringBuilder searchParam, Connection con) throws SQLException {
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        GsMessage gsMsg = new GsMessage(reqMdl_);

        if (StringUtil.isNullZeroString(paramMdl.getAdr010SearchComKana())) {
            //企業コード
            __addSearchParam(searchParam,
                    gsMsg.getMessage("address.7"), paramMdl.getAdr010svCode());
            //会社名
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.company.name"),
                    paramMdl.getAdr010svCoName());
            //会社名カナ
            __addSearchParam(searchParam,
                    gsMsg.getMessage("address.9"), paramMdl.getAdr010svCoNameKn());
            //支店・営業所名
            __addSearchParam(searchParam,
                    gsMsg.getMessage("address.10"), paramMdl.getAdr010svCoBaseName());
            //業種
            if (paramMdl.getAdr010svAtiSid() > 0) {
                AdrTypeindustryModel industryMdl
                        = industryDao.select(paramMdl.getAdr010svAtiSid());
                if (industryMdl != null) {
                    __addSearchParam(searchParam,
                                     gsMsg.getMessage("address.11"),
                                     industryMdl.getAtiName());
                }
            }
            //都道府県
            if (paramMdl.getAdr010svTdfk() >= 0) {
                CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
                CmnTdfkModel tdfkMdl = tdfkDao.select(paramMdl.getAdr010svTdfk());
                if (tdfkMdl != null) {
                    __addSearchParam(searchParam,
                            gsMsg.getMessage("cmn.prefectures"),
                            tdfkMdl.getTdfName());
                }
            }
            //備考
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.memo"),
                    paramMdl.getAdr010svBiko());
        }
    }

    /**
     * <br>[機  能] 検索条件文字列を取得する（担当者検索）
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param searchParam 検索条件文字列パラメータ
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __getSearchParamTanto(Adr010ParamModel paramMdl,
            StringBuilder searchParam, Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);

        //担当者
        if (paramMdl.getAdr010svTantoUser() > 0) {
            CmnUsrmInfModel usrmInfMdl = usrmInfDao.select(
                    paramMdl.getAdr010svTantoUser());
            if (usrmInfMdl != null) {
                __addSearchParam(searchParam, gsMsg.getMessage("cmn.staff"),
                                usrmInfMdl.getUsiSei() + " " + usrmInfMdl.getUsiMei());
            }

        //所属グループ
        } else if (paramMdl.getAdr010svTantoUser() < 1
                && paramMdl.getAdr010svTantoGroup() > 0) {

            CmnGroupmDao groupDao = new CmnGroupmDao(con);
            CmnGroupmModel groupMdl = groupDao.select(paramMdl.getAdr010svTantoGroup());
            if (groupMdl != null) {
                __addSearchParam(searchParam,
                        gsMsg.getMessage("cmn.affiliation.group"),
                        groupMdl.getGrpName());
            }
        }
    }

    /**
     * <br>[機  能] 検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param searchMdl 検索条件
     * @return 検索結果件数
     * @throws SQLException SQL実行例外
     */
    public int getSearchCount(Connection con, Adr010SearchModel searchMdl) throws SQLException {
        Adr010Dao adr010Dao = new Adr010Dao(con);
        return adr010Dao.getSearchCount(searchMdl);
    }

    /**
     * <br>[機  能] 検索条件Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr010ParamModel
     * @param userSid ユーザSID
     * @param con コネクション
     * @return 検索条件Model
     * @throws SQLException SQL実行例外
     */
    public Adr010SearchModel createSearchModel(
            Adr010ParamModel paramMdl, int userSid, Connection con)
       throws SQLException {
        Adr010SearchModel searchMdl = new Adr010SearchModel();
        searchMdl.setSessionUser(userSid);
        searchMdl.setSortKey(paramMdl.getAdr010sortKey());
        searchMdl.setOrderKey(paramMdl.getAdr010orderKey());
        searchMdl.setCmdMode(paramMdl.getAdr010cmdMode());

        switch (paramMdl.getAdr010cmdMode()) {
            case Adr010Const.CMDMODE_COMPANY :

                //企業コード
                searchMdl.setCoCode(paramMdl.getAdr010svCode());
                //会社名
                searchMdl.setCoName(paramMdl.getAdr010svCoName());
                //会社名カナ
                searchMdl.setCoNameKn(paramMdl.getAdr010svCoNameKn());
                //支店・営業所名
                searchMdl.setCoBaseName(paramMdl.getAdr010svCoBaseName());
                //業種
                searchMdl.setAtiSid(paramMdl.getAdr010svAtiSid());
                //都道府県
                searchMdl.setTdfk(paramMdl.getAdr010svTdfk());
                //備考
                searchMdl.setBiko(paramMdl.getAdr010svBiko());
                //会社名先頭文字
                searchMdl.setCnameKnHead(paramMdl.getAdr010svSearchComKana());

                break;
            case Adr010Const.CMDMODE_NAME :
                searchMdl.setUnameKnHead(paramMdl.getAdr010svSearchKana());
                break;

            case Adr010Const.CMDMODE_TANTO :
                //グループ
                searchMdl.setGroup(paramMdl.getAdr010svTantoGroup());
                //ユーザ
                searchMdl.setUser(paramMdl.getAdr010svTantoUser());
                break;
            case Adr010Const.CMDMODE_PROJECT :
                //プロジェクトSID
                searchMdl.setPrjSid(NullDefault.getInt(paramMdl.getSelectingProjectSv(), 0));
                //プロジェクト区分
                searchMdl.setProjectKbn(paramMdl.getProjectKbnSv());
                //プロジェクト状態
                searchMdl.setStatusKbn(paramMdl.getStatusKbnSv());

                //プロジェクト検索フラグON
                searchMdl.setPrjSerchFlg(true);
                break;
            case Adr010Const.CMDMODE_DETAILED :
                //氏名 姓
                searchMdl.setUnameSei(paramMdl.getAdr010svUnameSei());
                //氏名 名
                searchMdl.setUnameMei(paramMdl.getAdr010svUnameMei());
                //氏名カナ 姓
                searchMdl.setUnameSeiKn(paramMdl.getAdr010svUnameSeiKn());
                //氏名カナ 名
                searchMdl.setUnameMeiKn(paramMdl.getAdr010svUnameMeiKn());
                //会社名
                searchMdl.setCoName(paramMdl.getAdr010svDetailCoName());
                //所属
                searchMdl.setSyozoku(paramMdl.getAdr010svSyozoku());
                //役職
                searchMdl.setPosition(paramMdl.getAdr010svPosition());
                //E-MAIL
                searchMdl.setMail(paramMdl.getAdr010svMail());
                //担当者グループ
                searchMdl.setGroup(paramMdl.getAdr010svDetailTantoGroup());
                //担当者ユーザ
                searchMdl.setUser(paramMdl.getAdr010svDetailTantoUser());
                //業種
                searchMdl.setAtiSid(paramMdl.getAdr010svDetailAtiSid());
                break;

            case Adr010Const.CMDMODE_CONTACT :
                //グループ
                searchMdl.setGroup(paramMdl.getAdr010svTantoGroupContact());
                //ユーザ
                searchMdl.setUser(paramMdl.getAdr010svTantoUserContact());
                //氏名 姓
                searchMdl.setUnameSei(paramMdl.getAdr010svUnameSeiContact());
                //氏名 名
                searchMdl.setUnameMei(paramMdl.getAdr010svUnameMeiContact());
                //会社名
                searchMdl.setCoName(paramMdl.getAdr010svCoNameContact());
                //支店・営業所名
                searchMdl.setCoBaseName(paramMdl.getAdr010svCoBaseNameContact());
                //プロジェクトSID
                searchMdl.setPrjSid(paramMdl.getAdr010svProjectContact());
                //添付ファイル有無
                searchMdl.setTempFileExist(
                        NullDefault.getInt(paramMdl.getAdr010SvTempFilekbnContact(), -1));
                //添付ファイルが存在するコンタクト履歴SIDリスト
                AdrContactBinDao acbDao = new AdrContactBinDao(con);
                searchMdl.setHaveTmpFileAdcSids(
                        acbDao.getTmpFileAdcSidList(searchMdl.getSessionUser()));

                //日時
                //検索区分
                if (paramMdl.getAdr010svdateNoKbn() == 1) {
                    searchMdl.setDateSchFlg(false);

                } else {
                    searchMdl.setDateSchFlg(true);

                    UDate fromDate = new UDate();
                    fromDate.setTime(0);
                    fromDate.setYear(Integer.parseInt(paramMdl.getAdr010svSltYearFrContact()));
                    fromDate.setMonth(Integer.parseInt(paramMdl.getAdr010svSltMonthFrContact()));
                    fromDate.setDay(Integer.parseInt(paramMdl.getAdr010svSltDayFrContact()));
                    fromDate.setHour(0);
                    fromDate.setMinute(0);
                    fromDate.setSecond(0);
                    fromDate.setMilliSecond(0);
                    searchMdl.setDateFr(fromDate);

                    UDate toDate = new UDate();
                    toDate.setTime(0);
                    toDate.setYear(Integer.parseInt(paramMdl.getAdr010svSltYearToContact()));
                    toDate.setMonth(Integer.parseInt(paramMdl.getAdr010svSltMonthToContact()));
                    toDate.setDay(Integer.parseInt(paramMdl.getAdr010svSltDayToContact()));
                    toDate.setHour(23);
                    toDate.setMinute(59);
                    toDate.setSecond(59);
                    toDate.setMilliSecond(999);
                    searchMdl.setDateTo(toDate);
                }

                //種別
                searchMdl.setSyubetsu(paramMdl.getAdr010svSyubetsuContact());
                //検索対象
                String[] targets = paramMdl.getAdr010svSearchTargetContact();
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
                String keyWord = NullDefault.getString(paramMdl.getAdr010svSearchWordContact(), "");
                CommonBiz cBiz = new CommonBiz();
                searchMdl.setAdrKeyValue(cBiz.setKeyword(keyWord));
                //キーワード区分
                searchMdl.setKeyWordkbn(
                        NullDefault.getInt(paramMdl.getAdr010SvKeyWordkbnContact(), -1));

                break;

            default :
        }

        //ラベル
        searchMdl.setLabel(paramMdl.getAdr010svSearchLabel());

        return searchMdl;
    }

    /**
     * <br>[機  能] 役職コンボに設定する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getAddressPositionLabelList(Connection con)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        ArrayList<LabelValueBean> positionLabelList = new ArrayList<LabelValueBean>();
        //「選択してください」をコンボにセット
        positionLabelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        AdrPositionDao positionDao = new AdrPositionDao(con);
        List<AdrPositionModel> positionList = positionDao.selectPositionList();
        for (AdrPositionModel positionData : positionList) {
            LabelValueBean label = new LabelValueBean(positionData.getApsName(),
                                                    String.valueOf(positionData.getApsSid()));
            positionLabelList.add(label);
        }

        return positionLabelList;
    }

    /**
     * <br>[機  能] プロジェクトコンボに設定する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr010ParamModel
     * @param userSid ログインSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getAddressProjectLabelList(
            Connection con,
            Adr010ParamModel paramMdl,
            int userSid)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        ArrayList<LabelValueBean> positionLabelList = new ArrayList<LabelValueBean>();
        //「選択してください」をコンボにセット
        positionLabelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        List<Adr010ProjectDataModel> projectDataList = __getProjectList(paramMdl, userSid, con);
        for (Adr010ProjectDataModel projectData : projectDataList) {
            LabelValueBean label = new LabelValueBean(projectData.getProjectName(),
                                                    String.valueOf(projectData.getProjectSid()));
            positionLabelList.add(label);
        }

        return positionLabelList;
    }

    /**
     * <br>[機  能] プロジェクト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr010ParamModel
     * @param userSid ログインユーザSID
     * @param con コネクション
     * @return List in ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private List<Adr010ProjectDataModel> __getProjectList(
            Adr010ParamModel paramMdl,
            int userSid,
            Connection con)
    throws SQLException {

        //検索用Modelを作成
        Adr010SearchModel bean = new Adr010SearchModel();
        Adr010Dao dao = new Adr010Dao(con);

        //プロジェクトタブ検索
        if (paramMdl.getAdr010cmdMode() == Adr010Const.CMDMODE_PROJECT) {
            //プロジェクト区分
            bean.setProjectKbn(paramMdl.getProjectKbn());
            //状態
            bean.setStatusKbn(paramMdl.getStatusKbn());

        //コンタクト履歴タブ検索
        } else if (paramMdl.getAdr010cmdMode() == Adr010Const.CMDMODE_CONTACT) {
            //プロジェクト区分
            bean.setProjectKbn(GSConstAddress.PROTYPE_ALL);
            //状態
            bean.setStatusKbn(GSConstAddress.STATUS_ALL);
        }

        //ユーザSID
        bean.setUsrSid(userSid);

        //管理者かどうか
        if (paramMdl.getAdr010viewAdminBtn() == 1) {
            bean.setUsrKbn(true);
        }

        return dao.getDashBoardProjectList(bean);
    }

    /**
     * <br>[機  能] 検索条件文字列の追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param searchParam 検索条件文字列
     * @param paramName 検索条件名称
     * @param paramValue 検索条件
     * @return 検索条件文字列
     */
    private StringBuilder __addSearchParam(StringBuilder searchParam,
                                        String paramName, String paramValue) {

        if (!StringUtil.isNullZeroString(paramValue)) {
            if (searchParam.length() > 0) {
                searchParam.append(" ");
            }
            searchParam.append(paramName);
            searchParam.append("=");
            searchParam.append(paramValue);
        }

        return searchParam;
    }

    /**
     * <br>[機  能] 選択したアドレス情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr010ParamModel
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return ret 選択アドレス情報リスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<AdrAddressModel> getSelectAdrList(Adr010ParamModel paramMdl,
                                                          int sessionUsrSid,
                                                          Connection con)
        throws SQLException {

        AddressBiz adrBiz = new AddressBiz(reqMdl_);
        boolean admFlg = adrBiz.isAdmin(con, sessionUsrSid);

        AdrAddressDao dao = new AdrAddressDao(con);
        ArrayList<AdrAddressModel> adrList
            = dao.selectAdrList(paramMdl.getAdr010selectSid(), sessionUsrSid, admFlg);

        return adrList;
    }

    /**
     * <br>[機  能] 削除処理実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr010ParamModel
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return 削除件数
     */
    public ArrayList<AdrAddressModel> deleteAddress(Adr010ParamModel paramMdl,
            int sessionUsrSid,
            Connection con) throws SQLException {

        ArrayList<AdrAddressModel> delList = getSelectAdrList(paramMdl, sessionUsrSid, con);


        for (AdrAddressModel delAdr : delList) {
            int adrSid = delAdr.getAdrSid();
            if (adrSid == -1) {
                continue;
            }

            //コンタクト履歴情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            AdrContactDao contactDao = new AdrContactDao(con);
            List<Integer> adcSidList = contactDao.selectAdcSid(adrSid);
            AdrUsedDataBiz usedDataBiz = new AdrUsedDataBiz(con);
            usedDataBiz.insertContactSize(adcSidList, false);

            //アドレス帳情報の削除
            AdrAddressDao addressDao = new AdrAddressDao(con);
            addressDao.delete(adrSid);

            //担当者情報を削除する
            AdrPersonchargeDao adrPersonDao = new AdrPersonchargeDao(con);
            adrPersonDao.deleteToAddress(adrSid);

            //ラベル付与情報を削除する
            AdrBelongLabelDao belongLabelDao = new AdrBelongLabelDao(con);
            belongLabelDao.deleteToAddress(adrSid);

            //バイナリー情報を論理削除する
            AddressDao adrDao = new AddressDao(con);
            adrDao.deleteBinData(adrSid);

            //コンタクト履歴添付情報を削除する
            adrDao.deleteContactBinToAddress(adrSid);

            //コンタクト履歴情報を削除する
            contactDao.deleteToAddress(adrSid);

        }
        return delList;
    }

    /**
     * <br>[機  能] ラベル設定（複数）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr010ParamModel
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setLabelMult(Adr010ParamModel paramMdl, Connection con, int userSid)
        throws SQLException {

        String[] adrSid = paramMdl.getAdr010selectSid();
        UDate now = new UDate();

        //ラベル付与情報を登録する
        AdrBelongLabelDao dao = new AdrBelongLabelDao(con);

        if (adrSid != null && paramMdl.getAdr010selectLabelSid() != null) {
            for (int i = 0; i < adrSid.length; i++) {
                AdrBelongLabelModel belongLabelMdl = new AdrBelongLabelModel();
                belongLabelMdl.setAdrSid(Integer.parseInt(adrSid[i]));
                belongLabelMdl.setAblAuid(userSid);
                belongLabelMdl.setAblAdate(now);
                belongLabelMdl.setAblEuid(userSid);
                belongLabelMdl.setAblEdate(now);
                for (String albSid : paramMdl.getAdr010selectLabelSid()) {
                    belongLabelMdl.setAlbSid(Integer.parseInt(albSid));
                    dao.insertMulti(belongLabelMdl);
                }
            }
        }
    }

    /**
     * <br>[機  能] 検索対象のデフォルト値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトターゲット配列
     */
    public static String[] getDefultSearchTarget() {
        String[] targets = {
                String.valueOf(GSConstAddress.SEARCH_TARGET_TITLE),
                String.valueOf(GSConstAddress.SEARCH_TARGET_BIKO)
              };
        return targets;
    }

    /**
     * <br>[機  能] 宛先・CC・BCCにセットするアドレスSIDを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr010ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAddressAdrSid(Adr010ParamModel paramMdl, Connection con)
        throws SQLException {


        int setFlg = GSConst.SEND_KBN_ATESAKI;
        if (paramMdl.getAdr010SendMailMode() == GSConst.SEND_KBN_ATESAKI) {
            //宛先
            if (paramMdl.getAdr010AdrSid() > 0) {
                //アドレスデータ選択
                setFlg = GSConst.SEND_KBN_ATESAKI_1;
            }
        } else if (paramMdl.getAdr010SendMailMode() == GSConst.SEND_KBN_CC) {
            //CC
            setFlg = GSConst.SEND_KBN_CC;
        } else {
            //BCC
            setFlg = GSConst.SEND_KBN_BCC;
        }

        if (setFlg == GSConst.SEND_KBN_ATESAKI
                || setFlg == GSConst.SEND_KBN_ATESAKI_1) {
            //宛先アドレスSID
            String [] adrSidsAtsk = getAdrSid(paramMdl.getAdr010SidsAtsk(),
                                              paramMdl.getAdr010selectSid(),
                                              setFlg,
                                              paramMdl.getAdr010AdrSid(),
                                              paramMdl.getAdr010AdrType());

            paramMdl.setAdr010SidsAtsk(adrSidsAtsk);

        } else if (setFlg == GSConst.SEND_KBN_CC) {
            //CCアドレスSID
            String [] adrSidsCc = getAdrSid(paramMdl.getAdr010SidsCc(),
                                            paramMdl.getAdr010selectSid(),
                                            setFlg,
                                            0,
                                            paramMdl.getAdr010AdrType());

            paramMdl.setAdr010SidsCc(adrSidsCc);

        } else if (setFlg == GSConst.SEND_KBN_BCC)  {
            //BCCアドレスSID
            String [] adrSidsBcc = getAdrSid(paramMdl.getAdr010SidsBcc(),
                                             paramMdl.getAdr010selectSid(),
                                             setFlg,
                                             0,
                                             paramMdl.getAdr010AdrType());

            paramMdl.setAdr010SidsBcc(adrSidsBcc);
        }

        //アドレスSIDリセット
        paramMdl.setAdr010AdrSid(0);

    }

    /**
     * <br>[機  能] 送信先のアドレスSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param oldAdrSids 既に設定しているアドレスSID
     * @param newAdrSids 選択したアドレスSID
     * @param setFlg 選択アドレスセットフラグ
     * @param oneAtskAdrSid 宛先にセットするアドレスSID
     * @param oneAtskAdrType 宛先にセットするアドレスの種別
     * @return 画面に表示するアドレスSID
     */
    public String[] getAdrSid(String[] oldAdrSids,
                               String[] newAdrSids,
                               int setFlg,
                               int oneAtskAdrSid,
                               int oneAtskAdrType) {

        List<String> adrSidList = new ArrayList<String>();

        if (oldAdrSids != null) {
            //選択済のアドレスSID
            for (int i = 0; i < oldAdrSids.length; i++) {
                adrSidList.add(oldAdrSids[i]);
            }
        }

        String selectSid = String.valueOf(oneAtskAdrSid) + "_" + oneAtskAdrType;
        if (setFlg == GSConst.SEND_KBN_ATESAKI_1
        && oneAtskAdrSid > 0
        && !adrSidList.contains(selectSid)) {
            //検索結果データの各リンクをクリックしたアドレスSID
            adrSidList.add(selectSid);
            return (String[]) adrSidList.toArray(new String[adrSidList.size()]);
        }

        for (int i = 0; i < newAdrSids.length; i++) {
            //チェックボックスで選択したアドレスSID
            if (!adrSidList.contains(newAdrSids[i])) {
                adrSidList.add(newAdrSids[i]);
            }
        }

        return (String[]) adrSidList.toArray(new String[adrSidList.size()]);

    }

    /**
     * <br>[機  能] 選択したアドレス情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param type 種別(宛先 or CC or BCC)
     * @param con コネクション
     * @return ret 選択アドレス情報リスト
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<AdrAddressModel> __getSelAdrList(Adr010ParamModel paramMdl,
                                                                                int type,
                                                                                Connection con)
        throws SQLException {

        ArrayList<AdrAddressModel> mailList = new ArrayList<AdrAddressModel>();

        String[] adrSids = null;
        if (type == 1) {
            adrSids = paramMdl.getAdr010SidsCc();
        } else if (type == 2) {
            adrSids = paramMdl.getAdr010SidsBcc();
        } else {
            adrSids = paramMdl.getAdr010SidsAtsk();
        }

        if (adrSids != null && adrSids.length > 0) {
            AdrAddressDao dao = new AdrAddressDao(con);

//            for (String adrSid : adrSids) {
            for (int idx = 0; idx < adrSids.length; idx++) {
                String adrSid = adrSids[idx];
                String addressSid = "";
                String addressType = "";

                //区分の位置
                int kCnt = adrSid.indexOf("_");

                if (kCnt == -1) {
                    //区分位置がない場合
                    addressSid = adrSid;
                    addressType = "0";
                } else {
                    //区分位置がある場合
                    addressSid = adrSid.substring(0, kCnt);
                    addressType = adrSid.substring(kCnt + 1, kCnt + 2);
                }

                ArrayList<AdrAddressModel> adrList
                    = dao.selAdrList(new String[]{addressSid});
                if (adrList != null && !adrList.isEmpty()) {
                    AdrAddressModel adrMdl = adrList.get(0);
                    if (addressType.equals("2")) {
                        adrMdl.setAdrMail1(null);
                        adrMdl.setAdrMail3(null);
                    } else if (addressType.equals("3")) {
                        adrMdl.setAdrMail1(null);
                        adrMdl.setAdrMail2(null);
                    } else if (addressType.equals("0")) {
                        if (!StringUtil.isNullZeroString(adrMdl.getAdrMail1())) {
                            adrMdl.setAdrMail2(null);
                            adrMdl.setAdrMail3(null);
                            addressType = "1";
                        } else if (!StringUtil.isNullZeroString(adrMdl.getAdrMail2())) {
                            adrMdl.setAdrMail1(null);
                            adrMdl.setAdrMail3(null);
                            addressType = "2";
                        } else if (!StringUtil.isNullZeroString(adrMdl.getAdrMail3())) {
                            adrMdl.setAdrMail1(null);
                            adrMdl.setAdrMail2(null);
                            addressType = "3";
                        } else {
                            adrMdl.setAdrMail1(null);
                            adrMdl.setAdrMail2(null);
                            adrMdl.setAdrMail3(null);
                            addressType = "0";
                        }
                    } else {
                        adrMdl.setAdrMail2(null);
                        adrMdl.setAdrMail3(null);
                    }

                    adrSids[idx] = addressSid + "_" + addressType;
                    mailList.add(adrMdl);
                }
            }
        }

        if (type == 1) {
            paramMdl.setAdr010SidsCc(adrSids);
        } else if (type == 2) {
            paramMdl.setAdr010SidsBcc(adrSids);
        } else {
            paramMdl.setAdr010SidsAtsk(adrSids);
        }

        return mailList;
    }

    /**
     * <br>[機  能] 削除するユーザを除くユーザ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr010ParamModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void deleteUserAtesaki(Adr010ParamModel paramMdl, Connection con)
    throws Exception {

        boolean delFlgAtsk = false;
        boolean delFlgCc = false;
        boolean delFlgBcc = false;

        if (paramMdl.getAdr010SendMailMode() == GSConst.SEND_KBN_ATESAKI) {
            //宛先
            delFlgAtsk = true;
        } else if (paramMdl.getAdr010SendMailMode() == GSConst.SEND_KBN_CC) {
            //CC
            delFlgCc = true;
        } else {
            //BCC
            delFlgBcc = true;
        }

        //削除アドレス
        String[] delAdrSid = new String[1];
        delAdrSid[0] = String.valueOf(paramMdl.getAdr010DelAdrSid());

        //宛先アドレスデータ
        String [] adrSidsAtsk = delAdrSid(paramMdl.getAdr010SidsAtsk(),
                                          delAdrSid[0] + "_" + paramMdl.getAdr010AdrType(),
                                          delFlgAtsk);

        paramMdl.setAdr010SidsAtsk(adrSidsAtsk);

        //TOアドレスデータ
        String [] adrSidsCc = delAdrSid(paramMdl.getAdr010SidsCc(),
                                        delAdrSid[0] + "_" + paramMdl.getAdr010AdrType(),
                                        delFlgCc);

        paramMdl.setAdr010SidsCc(adrSidsCc);

        //BCCアドレスデータ
        String [] adrSidsBcc = delAdrSid(paramMdl.getAdr010SidsBcc(),
                                         delAdrSid[0] + "_" + paramMdl.getAdr010AdrType(),
                                         delFlgBcc);

        paramMdl.setAdr010SidsBcc(adrSidsBcc);

    }

    /**
     * <br>[機  能] 削除するユーザを除く送信先のアドレス情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSids アドレスSID
     * @param delAdrSid 削除するアドレスSID
     * @param delFlgAtsk 削除対象フラグ
     * @return 画面に表示するアドレスSID
     */
    public String[] delAdrSid(String[] adrSids, String delAdrSid, boolean delFlgAtsk) {

        List<String> adrSidList = new ArrayList<String>();

        if (adrSids == null) {
            return (String[]) adrSidList.toArray(new String[adrSidList.size()]);
        }

        if (!delFlgAtsk) {
            //削除ユーザなし
            return adrSids;
        }

        for (int i = 0; i < adrSids.length; i++) {

            if (adrSids[i].equals(delAdrSid)) {
                continue;
            }
            adrSidList.add(adrSids[i]);
        }
        return (String[]) adrSidList.toArray(new String[adrSidList.size()]);
    }

    /**
     * <br>[機  能] カテゴリー＆ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行時エラー
     */
    private void __setCategoryLabelData(Adr010ParamModel paramMdl, Connection con)
            throws SQLException {

        ArrayList<AdrCategoryLabelModel> aclMdlList = new ArrayList<AdrCategoryLabelModel>();
        AdrLabelCategoryDao categoryDao = new AdrLabelCategoryDao(con);

        List<AdrLabelCategoryModel> modelList = categoryDao.select();
        for (AdrLabelCategoryModel model : modelList) {

            AdrCategoryLabelModel aclMdl = new AdrCategoryLabelModel();
            aclMdl.setCategorySid(model.getAlcSid());
            aclMdl.setCategoryName(model.getAlcName());

            AdrLabelDao labelDao = new AdrLabelDao(con);
            List<AdrLabelModel> labelMdl = labelDao.getLabelInCategory(model.getAlcSid());
            aclMdl.setLabelList(labelMdl);

            aclMdlList.add(aclMdl);
        }

        paramMdl.setAdr010CaegoryLabelList(aclMdlList);
    }

    /**
     * <br>[機  能] カテゴリー一覧の開閉フラグの初期値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setCategoryOpenFlg(Adr010ParamModel paramMdl, Connection con)
            throws SQLException {

        AdrLabelCategoryDao categoryDao = new AdrLabelCategoryDao(con);
        int cnt = categoryDao.count();
        String [] openFlgs = new String[cnt];
        for (int i = 0; i < cnt; i++) {
            //0:閉  1:開
            openFlgs[i] = "0";
        }
        paramMdl.setAdr010CategoryOpenFlg(openFlgs);
    }
}