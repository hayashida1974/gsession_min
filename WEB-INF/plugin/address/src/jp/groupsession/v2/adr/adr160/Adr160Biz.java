package jp.groupsession.v2.adr.adr160;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr160.dao.Adr160ContactDao;
import jp.groupsession.v2.adr.adr160.model.Adr160ContactModel;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.model.AdrPersonchargeModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr160Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr160Biz.class);

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr160Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr160ParamModel
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return Adr160Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Adr160ParamModel getInitData(Adr160ParamModel paramMdl, Connection con, int usrSid)
    throws SQLException {

        //コンタクト履歴を設定
        __setContactInf(paramMdl, con, usrSid);

        //アドレス帳情報を設定
        __setAddressInf(paramMdl, con);

        //ラベル一覧を設定
        __setLabelInf(paramMdl, con);

        return paramMdl;
    }

    /**
     * <br>[機  能] コンタクト履歴を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr160ParamModel
     * @param con コネクション
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setContactInf(
            Adr160ParamModel paramMdl, Connection con, int usrSid) throws SQLException {

        //コンタクト履歴を作成
        List <Adr160ContactModel> contactList = __getContactList(paramMdl, con, usrSid);

        //コンタクト履歴有無セット
        if (contactList.size() != 0) {
            paramMdl.setAdr160dataExist(1);
        }

        //コンタクト履歴をフォームへセット
        paramMdl.setAdr160contactList(contactList);
    }

    /**
     * <br>[機  能] アドレス帳情報を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr160ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setAddressInf(Adr160ParamModel paramMdl, Connection con) throws SQLException {

        String kaisya = "";
        String kaisyakyoten = "";
        String yakusyoku = "";

        //アドレス帳情報を作成
        AdrAddressDao addressDao = new AdrAddressDao(con);
        AdrAddressModel addressMdl = addressDao.select(paramMdl.getAdr010EditAdrSid());

        if (addressMdl == null) {
            return;
        }

        //会社情報を取得
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        AdrCompanyModel companyModel = companyDao.select(addressMdl.getAcoSid());
        if (companyModel != null) {
            kaisya = companyModel.getAcoName();
        }
        //会社拠点情報を取得
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        AdrCompanyBaseModel companyBaseModel = companyBaseDao.select(addressMdl.getAbaSid());
        if (companyBaseModel != null) {
            kaisyakyoten = companyBaseModel.getAbaName();
        }
        //役職情報を取得
        AdrPositionDao positionDao = new AdrPositionDao(con);
        AdrPositionModel positionModel = positionDao.select(addressMdl.getApsSid());
        if (positionModel != null) {
            yakusyoku = positionModel.getApsName();
        }

        //アドレス帳情報をフォームへセット
        paramMdl.setAdr160simei(addressMdl.getAdrSei() + " " + addressMdl.getAdrMei());
        paramMdl.setAdr160simeikana(addressMdl.getAdrSeiKn() + " " + addressMdl.getAdrMeiKn());
        paramMdl.setAdr160kaisya(kaisya);
        paramMdl.setAdr160kaisyakyoten(kaisyakyoten);
        paramMdl.setAdr160syozoku(addressMdl.getAdrSyozoku());
        paramMdl.setAdr160yakusyoku(yakusyoku);

        //メールアドレス
        paramMdl.setAdr160MailAddress1(
                NullDefault.getString(addressMdl.getAdrMail1(), ""));
        paramMdl.setAdr160MailComment1(
                NullDefault.getString(addressMdl.getAdrMailCmt1(), ""));
        paramMdl.setAdr160MailAddress2(
                NullDefault.getString(addressMdl.getAdrMail2(), ""));
        paramMdl.setAdr160MailComment2(
                NullDefault.getString(addressMdl.getAdrMailCmt2(), ""));
        paramMdl.setAdr160MailAddress3(
                NullDefault.getString(addressMdl.getAdrMail3(), ""));
        paramMdl.setAdr160MailComment3(
                NullDefault.getString(addressMdl.getAdrMailCmt3(), ""));

        //電話番号
        paramMdl.setAdr160Tel1(
                NullDefault.getString(addressMdl.getAdrTel1(), ""));
        paramMdl.setAdr160TelNaisen1(
                NullDefault.getString(addressMdl.getAdrTelNai1(), ""));
        paramMdl.setAdr160TelComment1(
                NullDefault.getString(addressMdl.getAdrTelCmt1(), ""));
        paramMdl.setAdr160Tel2(
                NullDefault.getString(addressMdl.getAdrTel2(), ""));
        paramMdl.setAdr160TelNaisen2(
                NullDefault.getString(addressMdl.getAdrTelNai2(), ""));
        paramMdl.setAdr160TelComment2(
                NullDefault.getString(addressMdl.getAdrTelCmt2(), ""));
        paramMdl.setAdr160Tel3(
                NullDefault.getString(addressMdl.getAdrTel3(), ""));
        paramMdl.setAdr160TelNaisen3(
                NullDefault.getString(addressMdl.getAdrTelNai3(), ""));
        paramMdl.setAdr160TelComment3(
                NullDefault.getString(addressMdl.getAdrTelCmt3(), ""));

        //FAX
        paramMdl.setAdr160Fax1(
                NullDefault.getString(addressMdl.getAdrFax1(), ""));
        paramMdl.setAdr160FaxComment1(
                NullDefault.getString(addressMdl.getAdrFaxCmt1(), ""));
        paramMdl.setAdr160Fax2(
                NullDefault.getString(addressMdl.getAdrFax2(), ""));
        paramMdl.setAdr160FaxComment2(
                NullDefault.getString(addressMdl.getAdrFaxCmt2(), ""));
        paramMdl.setAdr160Fax3(
                NullDefault.getString(addressMdl.getAdrFax3(), ""));
        paramMdl.setAdr160FaxComment3(
                NullDefault.getString(addressMdl.getAdrFaxCmt3(), ""));

        //郵便番号
        String postNo1 = NullDefault.getString(addressMdl.getAdrPostno1(), "");
        String postNo2 = NullDefault.getString(addressMdl.getAdrPostno2(), "");
        String postNo = "";

        if (!StringUtil.isNullZeroStringSpace(postNo1)
                && !StringUtil.isNullZeroStringSpace(postNo2)) {
            postNo = postNo1 + "-" + postNo2;
        }
        paramMdl.setAdr160PostNo(postNo);

        //都道府県
        String tdfName = "";
        int tdfSid = addressMdl.getTdfSid();
        if (tdfSid > 0) {
            CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
            CmnTdfkModel tdfkRet = tdfkDao.select(tdfSid);
            if (tdfkRet != null) {
                tdfName = tdfkRet.getTdfName();
            }
        }
        paramMdl.setAdr160TdfName(tdfName);

        //住所
        paramMdl.setAdr160Address1(
                NullDefault.getString(addressMdl.getAdrAddr1(), ""));
        paramMdl.setAdr160Address2(
                NullDefault.getString(addressMdl.getAdrAddr2(), ""));

        //備考
        paramMdl.setAdr160Biko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(addressMdl.getAdrBiko(), "")));

        //担当者名
        AdrPersonchargeDao pchargeDao = new AdrPersonchargeDao(con);
        List<AdrPersonchargeModel> pchargeRet =
            pchargeDao.getTantoListForAddress(paramMdl.getAdr010EditAdrSid());

        ArrayList<UsrLabelValueBean> tatoUserName = new ArrayList<UsrLabelValueBean>();

        if (!pchargeRet.isEmpty()) {
            String[] userSid = new String[pchargeRet.size()];
            int idx = 0;
            for (AdrPersonchargeModel mdl : pchargeRet) {
                userSid[idx] = String.valueOf(mdl.getUsrSid());
                idx += 1;
            }
            UserBiz usrBiz = new UserBiz();
            List<CmnUsrmInfModel> users = usrBiz.getUserList(con, userSid);
            for (CmnUsrmInfModel user: users) {
                tatoUserName.add(new UsrLabelValueBean(user));
            }
        }

        paramMdl.setAdr160TantoUserName(tatoUserName);
    }

    /**
     * <br>[機  能] ラベル一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr160ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setLabelInf(Adr160ParamModel paramMdl, Connection con) throws SQLException {

        //ラベル一覧を作成
        AdrLabelDao dao = new AdrLabelDao(con);
        List<AdrLabelModel> labelList = dao.selectBelongLabelList(paramMdl.getAdr010EditAdrSid());

        //ラベル有無セット
        if (labelList.size() != 0) {
            paramMdl.setAdr160labelExist(1);
        }

        //ラベル一覧をフォームへセット
        paramMdl.setAdr160labelList(labelList);
    }

    /**
     * <br>[機  能] コンタクト履歴を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr160ParamModel
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return List in Adr160ContactModel
     * @throws SQLException SQL実行例外
     */
    private List <Adr160ContactModel> __getContactList(Adr160ParamModel paramMdl,
                                                        Connection con,

                                                        int usrSid) throws SQLException {
        //一項の表示件数
        Integer limit = 10;
        //１ページの表示件数を取得
        AdrUconfDao uDao = new AdrUconfDao(con);
        AdrUconfModel uModel = uDao.select(usrSid);
        if (uModel != null && uModel.getAucAdrcount() != 0) {
            limit = uModel.getAucAdrcount();
        } else {
            limit = Integer.parseInt(GSConstAddress.DEFAULT_ADRCOUNT);
        }

        int nowPage = paramMdl.getAdr160pageNum1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //件数カウント
        Adr160ContactDao cDao = new Adr160ContactDao(con);
        long maxCount = cDao.getContactCount(paramMdl.getAdr010EditAdrSid());
        log__.debug("件数 = " + maxCount);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }

        paramMdl.setAdr160pageNum1(nowPage);
        paramMdl.setAdr160pageNum2(nowPage);
        paramMdl.setAdr160PageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<Adr160ContactModel>();
        }

        //コンタクト履歴情報作成
        return cDao.getContactList(paramMdl.getAdr010EditAdrSid(),
                                    start,
                                    limit,
                                    paramMdl.getSortKey(),
                                    paramMdl.getOrderKey());
    }
}
