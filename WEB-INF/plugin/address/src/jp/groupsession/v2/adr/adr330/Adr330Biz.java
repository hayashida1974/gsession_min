package jp.groupsession.v2.adr.adr330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Biz;
import jp.groupsession.v2.adr.adr010.dao.Adr010Dao;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.adr330.model.Adr330ViewSearchModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrBelongLabelDao;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] 管理者設定 アドレス管理画面 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr330Biz extends Adr010Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr330Biz.class);
    /**
     * コンストラクタ
     * @param reqMdl リクエストモデル
     */
    public Adr330Biz(RequestModel reqMdl) {
        super(reqMdl);
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
    public void setInitData(Adr330ParamModel paramMdl,
                             Connection con,
                             BaseUserModel userMdl)
    throws Exception {

        log__.debug("start");

        GsMessage gsMsg = new GsMessage(reqMdl_);


        //モード別に各種コンボを設定する
        AddressBiz addressBiz = new AddressBiz(reqMdl_);
        UserBiz userBiz = new UserBiz();
        Adr010Dao adr010Dao = new Adr010Dao(con);
        AdrCommonBiz adrBiz = new AdrCommonBiz();
        List<LabelValueBean> mylabelList = new ArrayList<LabelValueBean>();
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
        if (AdrCommonBiz.isMyGroupSid(paramMdl.getAdr330searchBean().getGroupSidStr())) {
            dspGpSidDetailed = AdrCommonBiz.getDspGroupSid(
                    paramMdl.getAdr330searchBean().getGroupSidStr());
            myGroupFlgDetailed = true;
        } else {
            dspGpSidDetailed = Integer.parseInt(paramMdl.getAdr330searchBean().getGroupSidStr());
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

        int sessionUserSid = userMdl.getUsrsid();
        Adr330ViewSearchModel vsearchMdl = paramMdl.getAdr330searchSVBean();
        Adr010SearchModel searchMdl = vsearchMdl.createSearchModel(sessionUserSid);

        //検索
        if (paramMdl.getAdr330searchFlg() == 1) {
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
            int page = searchMdl.getPage();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }

            //ページコンボ設定
            if (maxPage > 1) {
                paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
            }
            vsearchMdl.setPage(page);
            vsearchMdl.setMaxViewCount(maxCnt);
            searchMdl.setPage(page);
            searchMdl.setMaxViewCount(maxCnt);

            paramMdl.setDetailList(adr010Dao.getSearchResultList(searchMdl));

            //検索条件文字列を設定
            StringBuilder searchParam = new StringBuilder();

            //詳細検索
            __makeSearchParamString(searchMdl, searchParam, con);

            String escSearchParam = StringUtilHtml.transToHTmlWithWbr(searchParam.toString(), 10);
            paramMdl.setAdr010searchParamString(escSearchParam);
        }

        log__.debug("end");
    }

    /**
     * <br>[機  能] 検索条件文字列を取得する（詳細検索）
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索パラメータモデル
     * @param searchParam 検索条件文字列パラメータ
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __makeSearchParamString(Adr010SearchModel searchMdl,
            StringBuilder searchParam,
            Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);

        //氏名
        if (StringUtil.isNullZeroString(searchMdl.getUnameSei())
        || StringUtil.isNullZeroString(searchMdl.getUnameMei())) {
            //氏名 姓
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.name")
                    + " "
                    + gsMsg.getMessage("cmn.lastname"),
                    searchMdl.getUnameSei());
            //氏名 名
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name")
                    + " "
                    + gsMsg.getMessage("cmn.name3"),
                    searchMdl.getUnameMei());
        } else {
            //氏名
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name"),
                    searchMdl.getUnameSei()
                    + " " + searchMdl.getUnameMei());
        }
        //氏名カナ
        if (StringUtil.isNullZeroString(searchMdl.getUnameSeiKn())
        || StringUtil.isNullZeroString(searchMdl.getUnameMeiKn())) {
            //氏名カナ 姓
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.name.kana")
                    + " "
                    + gsMsg.getMessage("cmn.lastname"),
                    searchMdl.getUnameSeiKn());
            //氏名カナ 名
            __addSearchParam(searchParam,
                    gsMsg.getMessage("cmn.name.kana")
                    + " "
                    + gsMsg.getMessage("cmn.name3"),
                    searchMdl.getUnameMeiKn());
        } else {
            //氏名カナ
            __addSearchParam(searchParam, gsMsg.getMessage("cmn.name.kana"),
                    searchMdl.getUnameSeiKn()
                    + " " + searchMdl.getUnameMeiKn());
        }
        //会社名
        __addSearchParam(searchParam, gsMsg.getMessage("cmn.company.name"),
                searchMdl.getCoName());
        //所属
        __addSearchParam(searchParam, gsMsg.getMessage("cmn.affiliation"),
                searchMdl.getSyozoku());
        //役職
        AdrPositionDao positionDao = new AdrPositionDao(con);
        if (searchMdl.getPosition() > 0) {
            AdrPositionModel positionMdl
                    = positionDao.select(searchMdl.getPosition());
            if (positionMdl != null) {
                __addSearchParam(searchParam, gsMsg.getMessage("cmn.post"),
                        positionMdl.getApsName());
            }
        }
        //E-MAIL
        __addSearchParam(searchParam, "E-MAIL", searchMdl.getMail());
        //担当者
        if (searchMdl.getUser() > 0) {
            CmnUsrmInfModel usrmInfMdl
                = usrmInfDao.select(searchMdl.getUser());
            if (usrmInfMdl != null) {
                __addSearchParam(searchParam, gsMsg.getMessage("cmn.staff"),
                                usrmInfMdl.getUsiSei() + " " + usrmInfMdl.getUsiMei());
            }
        }
        //業種
        if (searchMdl.getAtiSid() > 0) {
            AdrTypeindustryModel industryMdl
                    = industryDao.select(searchMdl.getAtiSid());
            if (industryMdl != null) {
                __addSearchParam(searchParam,
                                 gsMsg.getMessage("address.11"),
                                 industryMdl.getAtiName());
            }
        }
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
    public ArrayList<AdrAddressModel> deleteAddress(Adr330ParamModel paramMdl,
            int sessionUsrSid,
            Connection con) throws SQLException {

        ArrayList<AdrAddressModel> delList = getSelectAdrList(paramMdl, sessionUsrSid, con);

        for (AdrAddressModel delAdr : delList) {
            int adrSid = delAdr.getAdrSid();
            if (adrSid == -1) {
                continue;
            }

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
            AdrContactDao contactDao = new AdrContactDao(con);
            contactDao.deleteToAddress(adrSid);

        }
        return delList;
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
    public ArrayList<AdrAddressModel> getSelectAdrList(Adr330ParamModel paramMdl,
                                                          int sessionUsrSid,
                                                          Connection con)
        throws SQLException {

        AdrAddressDao dao = new AdrAddressDao(con);
        ArrayList<AdrAddressModel> adrList
            = dao.selAdrList(paramMdl.getAdr330selectSid());

        return adrList;
    }

}
