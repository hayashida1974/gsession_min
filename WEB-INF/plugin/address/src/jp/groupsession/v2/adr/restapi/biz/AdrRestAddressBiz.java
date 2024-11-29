package jp.groupsession.v2.adr.restapi.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.model.AdrPersonchargeModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.adr.restapi.AdrEnumReasonCode;
import jp.groupsession.v2.adr.restapi.entities.AdrEntitiesResultModel;
import jp.groupsession.v2.adr.restapi.entities.AdrEntitiesResultModel.LabelInfo;
import jp.groupsession.v2.adr.restapi.entities.AdrEntitiesResultModel.TantoUserInfo;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳のRESTAPI アドレス情報に関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrRestAddressBiz {

    /**
     * <br>[機  能] パスパラメータに指定したアドレスSIDを持つアドレス情報が閲覧可能かをチェックし、閲覧権限がなければエラーをthrowします。
     * <br>[解  説]
     * <br>[備  考] 
     * @param ctx RestApiコンテキスト
     * @param addressSid アドレスSID
     * @throws SQLException
     */
    public void validateAddress(RestApiContext ctx, Integer addressSid) throws SQLException {

        boolean accessFlg = false;

        //存在チェック
        AdrAddressDao adrDao = new AdrAddressDao(ctx.getCon());
        AdrAddressModel adrMdl = adrDao.select(addressSid);
        if (adrMdl == null) {
            throw new RestApiPermissionException(
            AdrEnumReasonCode.RESOURCE_CANT_ACCESS_ADDRESS,
            "search.data.notfound",
            new GsMessage(ctx.getRequestModel())
                .getMessage("address.src.2")
            );
        } 

        //編集権限チェック
        AddressBiz addressBiz = new AddressBiz(ctx.getRequestModel());
        accessFlg = addressBiz.isEditAddressData(ctx.getCon(), addressSid, ctx.getRequestUserSid());
        if (!accessFlg) {
            //閲覧権限チェック
            accessFlg = addressBiz.isViewAddressData(ctx.getCon(), addressSid, ctx.getRequestUserSid());
            if (!accessFlg) {
                throw new RestApiPermissionException(
                AdrEnumReasonCode.RESOURCE_CANT_ACCESS_ADDRESS,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("address.src.2")
                );
            }
        }
    }

    /**
     * <br>[機  能] アドレスSIDからアドレス情報を取得する
     * <br>[解  説]
     * <br>[備  考] 
     * @param con コネクション
     * @param addressSid アドレスSID
     * @return アドレス情報モデル
     * @throws SQLException 
     */
    public AdrEntitiesResultModel getAddressInfo(Connection con, Integer addressSid) throws SQLException {

        AdrEntitiesResultModel ret = new AdrEntitiesResultModel();
        
        //アドレス情報を取得
        AdrAddressDao adrDao = new AdrAddressDao(con);
        AdrAddressModel adrMdl = adrDao.select(addressSid);
        
        ret.setSid(adrMdl.getAdrSid());
        ret.setSeiText(adrMdl.getAdrSei());
        ret.setMeiText(adrMdl.getAdrMei());
        ret.setSeiKanaText(adrMdl.getAdrSeiKn());
        ret.setMeiKanaText(adrMdl.getAdrMeiKn());
        ret.setSyozokuText(adrMdl.getAdrSyozoku());
        
        //役職情報を取得
        if (adrMdl.getApsSid() < 0) {
            ret.setYakusyokuSid(-1);
            ret.setYakusyokuName(null);
        } else {
            AdrPositionDao apsDao = new AdrPositionDao(con);
            AdrPositionModel apsMdl = apsDao.select(adrMdl.getApsSid());
            ret.setYakusyokuSid(adrMdl.getApsSid());
            ret.setYakusyokuName(apsMdl.getApsName());
        }

        ret.setMail1Text(adrMdl.getAdrMail1());
        ret.setMail1CommentText(adrMdl.getAdrMailCmt1());
        ret.setMail2Text(adrMdl.getAdrMail2());
        ret.setMail2CommentText(adrMdl.getAdrMailCmt2());
        ret.setMail3Text(adrMdl.getAdrMail3());
        ret.setMail3CommentText(adrMdl.getAdrMailCmt3());
        ret.setTel1Text(adrMdl.getAdrTel1());
        ret.setTel1NaisenText(adrMdl.getAdrTelNai1());
        ret.setTel1CommentText(adrMdl.getAdrTelCmt1());
        ret.setTel2Text(adrMdl.getAdrTel2());
        ret.setTel2NaisenText(adrMdl.getAdrTelNai2());
        ret.setTel2CommentText(adrMdl.getAdrTelCmt2());
        ret.setTel3Text(adrMdl.getAdrTel3());
        ret.setTel3NaisenText(adrMdl.getAdrTelNai3());
        ret.setTel3CommentText(adrMdl.getAdrTelCmt3());
        ret.setFax1Text(adrMdl.getAdrFax1());
        ret.setFax1CommentText(adrMdl.getAdrFaxCmt1());
        ret.setFax2Text(adrMdl.getAdrFax2());
        ret.setFax2CommentText(adrMdl.getAdrFaxCmt2());
        ret.setFax3Text(adrMdl.getAdrFax3());
        ret.setFax3CommentText(adrMdl.getAdrFaxCmt3());
        ret.setZip1Text(adrMdl.getAdrPostno1());
        ret.setZip2Text(adrMdl.getAdrPostno2());
        
        //都道府県名を取得
        if (adrMdl.getTdfSid() > 0) {
            CmnTdfkDao cmnTdfkDao = new CmnTdfkDao(con);
            CmnTdfkModel cmnTdfkMdl = cmnTdfkDao.select(adrMdl.getTdfSid());
            ret.setTodofukenSid(cmnTdfkMdl.getTdfSid());
            ret.setTodofukenName(cmnTdfkMdl.getTdfName());
        } else {
            ret.setTodofukenSid(-1);
            ret.setTodofukenName(null);
        }

        ret.setAddress1Text(adrMdl.getAdrAddr1());
        ret.setAddress2Text(adrMdl.getAdrAddr2());
        ret.setMemoText(adrMdl.getAdrBiko());
    
        //会社情報を取得
        if (adrMdl.getAcoSid() > 0) {
            AdrCompanyDao acoDao = new AdrCompanyDao(con);
            AdrCompanyModel acoMdl = acoDao.select(adrMdl.getAcoSid());
            ret.setCompanyId(acoMdl.getAcoCode());
            ret.setCompanyName(acoMdl.getAcoName());
        } else {
            ret.setCompanyId(null);
            ret.setCompanyName(null);
        }

        //会社拠点情報を取得
        if (adrMdl.getAbaSid() > 0) {
            AdrCompanyBaseDao acbDao = new AdrCompanyBaseDao(con);
            AdrCompanyBaseModel acbMdl = acbDao.select(adrMdl.getAbaSid());
            ret.setBaseSid(acbMdl.getAbaSid());
            ret.setBaseName(acbMdl.getAbaName());
            ret.setBaseType(acbMdl.getAbaType());
        } else {
            ret.setBaseSid(0);
            ret.setBaseName(null);
            ret.setBaseType(-1);
        }
        
        //ラベル情報取得
        List<LabelInfo> labelArray = new ArrayList<LabelInfo>();
        AdrLabelDao albDao = new AdrLabelDao(con);
        List<AdrLabelModel> albMdlList = albDao.selectBelongLabelList(adrMdl.getAdrSid());
        for (AdrLabelModel albMdl : albMdlList) {
            LabelInfo labelInfo = new LabelInfo();
            labelInfo.setSid(albMdl.getAlbSid());
            labelInfo.setName(albMdl.getAlbName());
            //カテゴリ情報取得
            AdrLabelCategoryDao alcDao = new AdrLabelCategoryDao(con);
            AdrLabelCategoryModel alcMdl = alcDao.select(albMdl.getAlcSid());
            labelInfo.setCategorySid(alcMdl.getAlcSid());
            labelInfo.setCategoryName(alcMdl.getAlcName());
            labelArray.add(labelInfo);
        }
        ret.setLabelArray(labelArray);

        //担当者情報取得
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        ret.setTantoUserArray(getTantoUserList(con, addressSid, sortMdl));
        return ret;
    }

    /**
     * アドレス帳 担当者ユーザ情報を取得する
     * @param con DBコネクション
     * @param adrSid アドレス帳SID
     * @param sortMdl ユーザ情報 ソート順
     * @return アドレス帳 担当者ユーザ情報
     * @throws SQLException SQL実行時例外
     */
    public List<TantoUserInfo> getTantoUserList(Connection con, int adrSid, CmnCmbsortConfModel sortMdl)
    throws SQLException {

        List<TantoUserInfo> tantoUserInfoList = new ArrayList<TantoUserInfo>();

        //アドレス帳の担当者ユーザSIDを取得
        AdrPersonchargeDao apcDao = new AdrPersonchargeDao(con);
        List<AdrPersonchargeModel> apcMdlList = apcDao.getTantoListForAddress(adrSid);
        List<Integer> tantoUsrSids
            = apcMdlList.stream()
                .map(t -> t.getUsrSid())
                .collect(Collectors.toList());

        //担当者ユーザの各種情報を取得する
        UserSearchDao userSearchDao = new UserSearchDao(con);
        List<CmnUserModel> userList = userSearchDao.getUsersDataList(tantoUsrSids, true);
        for (CmnUserModel usrInf : userList) {
            TantoUserInfo tantoUserInfo = new TantoUserInfo();
            tantoUserInfo.setId(usrInf.getUsrLgid());
            tantoUserInfo.setSeiText(usrInf.getUsiSei());
            tantoUserInfo.setMeiText(usrInf.getUsiMei());
            tantoUserInfo.setSeiKanaText(usrInf.getUsiSeiKn());
            tantoUserInfo.setMeiKanaText(usrInf.getUsiMeiKn());
            tantoUserInfo.setLoginStopFlg(usrInf.getUsrUkoFlg());
            tantoUserInfoList.add(tantoUserInfo);
        }

        return tantoUserInfoList;
    }
}
