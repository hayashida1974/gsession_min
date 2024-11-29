package jp.groupsession.v2.adr.restapi.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import jp.groupsession.v2.adr.dao.AdrBelongIndustryDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.adr.restapi.AdrEnumReasonCode;
import jp.groupsession.v2.adr.restapi.companies.AdrCompaniesResultModel;
import jp.groupsession.v2.adr.restapi.model.BaseInfo;
import jp.groupsession.v2.adr.restapi.model.IndustryInfo;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳のRESTAPI 会社情報に関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrRestCompanyBiz {

    /**
     * <br>[機  能] パスパラメータに指定した企業コードが存在するかをチェックし、存在しなければエラーをthrowします。
     * <br>[解  説]
     * <br>[備  考] エラーコードはADDRESS-101です
     * @param ctx RestApiコンテキスト
     * @param companyId 企業コード
     * @throws SQLException 
     */
    public void validateCompany(RestApiContext ctx, String companyId) throws SQLException {
        
        AdrCompanyDao dao = new AdrCompanyDao(ctx.getCon());
        AdrCompanyModel mdl = dao.select(companyId);
        if (mdl == null) {
            throw new RestApiPermissionException(
                AdrEnumReasonCode.RESOURCE_CANT_ACCESS_COMPANY,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("address.118")
            );
        }
    }

    /**
     * <br>[機  能] 企業コードから会社情報を取得する
     * <br>[解  説]
     * <br>[備  考] 
     * @param con コネクション
     * @param acoSid 会社SID
     * @return AdrCompaniesResultModel 会社情報モデル
     * @throws SQLException 
     */
    public AdrCompaniesResultModel getCompanyInfo(Connection con, String companyId) throws SQLException {

        AdrCompaniesResultModel ret = new AdrCompaniesResultModel();

        //会社情報を取得
        AdrCompanyDao acoDao = new AdrCompanyDao(con);
        AdrCompanyModel acoMdl = acoDao.select(companyId);
        
        //会社情報モデルにセット
        __setCompanyInfo(con, acoMdl, ret);
        
        return ret;
    }

    /**
     * <br>[機  能] 会社SIDから会社情報を取得する
     * <br>[解  説]
     * <br>[備  考] 
     * @param con コネクション
     * @param acoSid 会社SID
     * @return CompanyInfo 会社情報モデル
     * @throws SQLException 
     */
    public AdrCompaniesResultModel getCompanyInfo(Connection con, Integer acoSid) throws SQLException {
        AdrCompaniesResultModel ret = new AdrCompaniesResultModel();
        
        //会社情報を取得
        AdrCompanyDao acoDao = new AdrCompanyDao(con);
        AdrCompanyModel acoMdl = acoDao.select(acoSid);

        //会社情報モデルにセット
        __setCompanyInfo(con, acoMdl, ret);
        
        return ret;
    }

    /**
     * <br>[機  能] 取得した会社情報を会社情報モデルにセットする
     * <br>[解  説]
     * <br>[備  考] 
     * @param con コネクション
     * @param acoSid 会社SID
     * @return resultMdl 会社情報モデル
     * @throws SQLException 
     */
    private void __setCompanyInfo(Connection con, AdrCompanyModel acoMdl, AdrCompaniesResultModel resultMdl) throws SQLException {

        resultMdl.setId(acoMdl.getAcoCode());
        resultMdl.setName(acoMdl.getAcoName());
        resultMdl.setKanaName(acoMdl.getAcoNameKn());
        resultMdl.setZip1Text(acoMdl.getAcoPostno1());
        resultMdl.setZip2Text(acoMdl.getAcoPostno2());
        resultMdl.setAddress1Text(acoMdl.getAcoAddr1());
        resultMdl.setAddress2Text(acoMdl.getAcoAddr2());
        resultMdl.setUrlText(acoMdl.getAcoUrl());
        resultMdl.setMemoText(acoMdl.getAcoBiko());

        //都道府県名を取得
        CmnTdfkDao cmnTdfkDao = new CmnTdfkDao(con);
        CmnTdfkModel cmnTdfkMdl = cmnTdfkDao.select(acoMdl.getTdfSid());
        if (cmnTdfkMdl != null) {
            resultMdl.setTodofukenSid(acoMdl.getTdfSid());
            resultMdl.setTodofukenName(cmnTdfkMdl.getTdfName());
        } else {
            resultMdl.setTodofukenSid(-1);
            resultMdl.setTodofukenName(null);
        }

        //業種情報を取得
        List<IndustryInfo> industries = new ArrayList<IndustryInfo>();
        AdrBelongIndustryDao abiDao = new AdrBelongIndustryDao(con);
        String[] atiSids = abiDao.getAtiSidList(acoMdl.getAcoSid());
        if (atiSids != null && atiSids.length > 0) {
            AdrTypeindustryDao atiDao = new AdrTypeindustryDao(con);
            List<AdrTypeindustryModel> atiMdlList = 
                atiDao.getIndustryList(Stream.of(atiSids).mapToInt(Integer::parseInt).toArray());
            for (AdrTypeindustryModel atiMdl : atiMdlList) {
                IndustryInfo industryInfo = new IndustryInfo();
                industryInfo.setSid(atiMdl.getAtiSid());
                industryInfo.setName(atiMdl.getAtiName());
                industries.add(industryInfo);
            }
            resultMdl.setIndustryArray(industries);
        }
        
        //会社拠点情報を取得
        List<BaseInfo> baseInfoList = new ArrayList<BaseInfo>();
        AdrCompanyBaseDao acbDao = new AdrCompanyBaseDao(con);
        List<AdrCompanyBaseModel> acbMdlList = acbDao.getCompanyBaseList(acoMdl.getAcoSid());
        for (AdrCompanyBaseModel acbMdl : acbMdlList) {
            BaseInfo baseInfo = new BaseInfo();
            baseInfo.setSid(acbMdl.getAbaSid());
            baseInfo.setType(acbMdl.getAbaType());
            baseInfo.setName(acbMdl.getAbaName());
            baseInfo.setZip1Text(acbMdl.getAbaPostno1());
            baseInfo.setZip2Text(acbMdl.getAbaPostno2());
            CmnTdfkModel mdl = cmnTdfkDao.select(acbMdl.getTdfSid());
            if (mdl != null) {
                baseInfo.setTodofukenSid(acbMdl.getTdfSid());
                baseInfo.setTodofukenName(mdl.getTdfName());
            } else {
                baseInfo.setTodofukenSid(-1);
                baseInfo.setTodofukenName(null);
            }
            baseInfo.setAddress1Text(acbMdl.getAbaAddr1());
            baseInfo.setAddress2Text(acbMdl.getAbaAddr2());
            baseInfo.setMemoText(acbMdl.getAbaBiko());
            baseInfoList.add(baseInfo);
        }
        resultMdl.setBaseArray(baseInfoList); 
    }
}
