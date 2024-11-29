package jp.groupsession.v2.adr.adr270;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Biz;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrBelongLabelDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.model.AdrPersonchargeModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;

/**
 * <br>[機  能] アドレス帳ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr270Biz extends Adr010Biz {
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr270Biz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }
    /**
     * <br>[機  能] 閲覧可能なアドレスかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr270ParamModel
     * @param buMdl セッションユーザ情報
     * @return true:閲覧可能 false:閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canViewAddress(Connection con, Adr270ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        int adrSid = paramMdl.getAdrSid();

        //指定されたアドレス帳情報が存在するか
        AdrAddressDao adrDao = new AdrAddressDao(con);
        if (!adrDao.existAddress(adrSid)) {
            return false;
        }

        //管理者の場合は無条件で閲覧可能
        CommonBiz cmnBiz = new CommonBiz();
        boolean gsAdmFlg = cmnBiz.isPluginAdmin(con, buMdl, GSConstAddress.PLUGIN_ID_ADDRESS);

        if (gsAdmFlg) {
            return true;
        }

        //アドレス帳情報の閲覧が可能か
        AddressDao addressDao = new AddressDao(con);
        return addressDao.isViewAddressData(adrSid, buMdl.getUsrsid());
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr270ParamModel
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @throws Exception 実行例外
     */
    public void setInitData(Adr270ParamModel paramMdl,
                             Connection con,
                             BaseUserModel userMdl)
    throws Exception {

        //役職コンボを設定
        paramMdl.setPositionCmbList(getAddressPositionLabelList(con));

        //DBからアドレス帳情報を読み込む
        _setAddressData(con, paramMdl);

        //会社名を設定
        _setCompanyData(con, paramMdl);

        //担当者情報を設定
        UserBiz userBiz = new UserBiz();
        paramMdl.setSelectTantoCombo(userBiz.getUserLabelList(con, paramMdl.getAdr270tantoList()));

        //ラベル情報一覧を設定
        _setLabelList(con, paramMdl);
    }

    /**
     * <br>[機  能] 指定された会社情報をDBから読み込み、パラメータに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr270ParamModel
     * @throws SQLException SQL実行時例外
     */
    protected void _setAddressData(Connection con, Adr270ParamModel paramMdl) throws SQLException {
        AdrAddressDao addressDao = new AdrAddressDao(con);
        //アドレス帳状報の設定
        int adrSid = paramMdl.getAdrSid();
        AdrAddressModel addressMdl = addressDao.select(adrSid);
        if (addressMdl == null) {
            return;
        }

        paramMdl.setAdr270unameSei(addressMdl.getAdrSei());
        paramMdl.setAdr270unameMei(addressMdl.getAdrMei());
        paramMdl.setAdr270unameSeiKn(addressMdl.getAdrSeiKn());
        paramMdl.setAdr270unameMeiKn(addressMdl.getAdrMeiKn());
        paramMdl.setAdr270selectCompany(String.valueOf(addressMdl.getAcoSid()));
        paramMdl.setAdr270selectCompanyBase(String.valueOf(addressMdl.getAbaSid()));
        paramMdl.setAdr270syozoku(addressMdl.getAdrSyozoku());


        paramMdl.setAdr270position(addressMdl.getApsSid());
        AdrPositionDao positionDao = new AdrPositionDao(con);
        AdrPositionModel positionMdl
            = positionDao.select(addressMdl.getApsSid());
        if (positionMdl != null) {
            paramMdl.setAdr270positionName(positionMdl.getApsName());
        }

        paramMdl.setAdr270mail1(addressMdl.getAdrMail1());
        paramMdl.setAdr270mail1Comment(addressMdl.getAdrMailCmt1());
        paramMdl.setAdr270mail2(addressMdl.getAdrMail2());
        paramMdl.setAdr270mail2Comment(addressMdl.getAdrMailCmt2());
        paramMdl.setAdr270mail3(addressMdl.getAdrMail3());
        paramMdl.setAdr270mail3Comment(addressMdl.getAdrMailCmt3());

        paramMdl.setAdr270postno1(addressMdl.getAdrPostno1());
        paramMdl.setAdr270postno2(addressMdl.getAdrPostno2());

        int tdfSid = addressMdl.getTdfSid();
        paramMdl.setAdr270tdfk(tdfSid);
        if (tdfSid > 0) {
            CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
            CmnTdfkModel tdfkData = tdfkDao.select(tdfSid);
            if (tdfkData != null) {
                paramMdl.setAdr270tdfkName(tdfkData.getTdfName());
            }
        }

        paramMdl.setAdr270address1(addressMdl.getAdrAddr1());
        paramMdl.setAdr270address2(addressMdl.getAdrAddr2());
        paramMdl.setAdr270tel1(addressMdl.getAdrTel1());
        paramMdl.setAdr270tel1Nai(addressMdl.getAdrTelNai1());
        paramMdl.setAdr270tel1Comment(addressMdl.getAdrTelCmt1());
        paramMdl.setAdr270tel2(addressMdl.getAdrTel2());
        paramMdl.setAdr270tel2Nai(addressMdl.getAdrTelNai2());
        paramMdl.setAdr270tel2Comment(addressMdl.getAdrTelCmt2());
        paramMdl.setAdr270tel3(addressMdl.getAdrTel3());
        paramMdl.setAdr270tel3Nai(addressMdl.getAdrTelNai3());
        paramMdl.setAdr270tel3Comment(addressMdl.getAdrTelCmt3());
        paramMdl.setAdr270fax1(addressMdl.getAdrFax1());
        paramMdl.setAdr270fax1Comment(addressMdl.getAdrFaxCmt1());
        paramMdl.setAdr270fax2(addressMdl.getAdrFax2());
        paramMdl.setAdr270fax2Comment(addressMdl.getAdrFaxCmt2());
        paramMdl.setAdr270fax3(addressMdl.getAdrFax3());
        paramMdl.setAdr270fax3Comment(addressMdl.getAdrFaxCmt3());
        paramMdl.setAdr270biko(StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(addressMdl.getAdrBiko(), "")));

        //担当者の設定
        AdrPersonchargeDao tantoDao = new AdrPersonchargeDao(con);
        List<AdrPersonchargeModel> tantoDataList
                = tantoDao.getTantoListForAddress(adrSid);
        List<String> tantoList = new ArrayList<String>();
        for (AdrPersonchargeModel tantoData : tantoDataList) {
            tantoList.add(String.valueOf(tantoData.getUsrSid()));
        }
        paramMdl.setAdr270tantoList(tantoList.toArray(new String[tantoList.size()]));

        //ラベル情報の設定
        AdrBelongLabelDao belongLabelDao = new AdrBelongLabelDao(con);
        paramMdl.setAdr270label(belongLabelDao.getLabelSidList(adrSid));

    }

    /**
     * <br>[機  能] 選択された会社情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr270ParamModel
     * @throws SQLException SQL実行時例外
     */
    protected void _setCompanyData(Connection con, Adr270ParamModel paramMdl) throws SQLException {
        if (!StringUtil.isNullZeroString(paramMdl.getAdr270selectCompany())) {
            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            AdrCompanyModel companyMdl
                = companyDao.select(Integer.parseInt(paramMdl.getAdr270selectCompany()));
            if (companyMdl != null) {
                paramMdl.setAdr270companyCode(companyMdl.getAcoCode());
                paramMdl.setAdr270companyName(companyMdl.getAcoName());

                if (!StringUtil.isNullZeroString(paramMdl.getAdr270selectCompanyBase())) {
                    AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
                    AdrCompanyBaseModel companyBaseMdl
                        = companyBaseDao.select(
                                Integer.parseInt(paramMdl.getAdr270selectCompanyBase()));
                    if (companyBaseMdl != null) {
                        String companyBaseName = companyBaseMdl.getAbaName();
                        String companyBaseType
                            = AddressBiz.getCompanyBaseTypeName(
                                    companyBaseMdl.getAbaType(), reqMdl_);
                        if (!StringUtil.isNullZeroString(companyBaseType)) {
                            companyBaseName = companyBaseType + " ： " + companyBaseName;
                        }
                        paramMdl.setAdr270companyBaseName(companyBaseName);
                    }
                }
            }
        }
    }

    /**
     * <br>[機  能] ラベル情報一覧を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr270ParamModel
     * @throws SQLException SQL実行時例外
     */
    protected void _setLabelList(Connection con, Adr270ParamModel paramMdl) throws SQLException {
        AdrLabelDao labelDao = new AdrLabelDao(con);
        List<AdrLabelModel> selectLabelList = new ArrayList<AdrLabelModel>();
        if (paramMdl.getAdr270label() != null) {
            String[] selectLabel = paramMdl.getAdr270label();
            Arrays.sort(selectLabel);
            List<AdrLabelModel> allLabelList = labelDao.select();
            for (AdrLabelModel labelData : allLabelList) {
                if (Arrays.binarySearch(selectLabel, String.valueOf(labelData.getAlbSid())) >= 0) {
                    selectLabelList.add(labelData);
                }
            }
        }
        paramMdl.setSelectLabelList(selectLabelList);

    }

}
