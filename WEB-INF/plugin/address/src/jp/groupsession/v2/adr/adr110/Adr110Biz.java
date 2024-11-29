package jp.groupsession.v2.adr.adr110;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrBelongIndustryDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrBelongIndustryModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110Biz {

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Adr110Biz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr110Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr110ParamModel
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Adr110ParamModel paramMdl)
    throws SQLException {

        //処理モード = 編集 かつ 初期表示の場合は会社情報を設定する
        if (paramMdl.getAdr110ProcMode() == GSConstAddress.PROCMODE_EDIT
        && paramMdl.getAdr110initFlg() == 0) {
            _readCompanyData(con, paramMdl);
        }

        //都道府県コンボを設定
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        paramMdl.setTdfkCmbList(cmnBiz.getTdfkLabelList(con, gsMsg));

        //拠点種別コンボを設定
        ArrayList<LabelValueBean> abaTypeList = new ArrayList<LabelValueBean>();
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.122"),
                                        String.valueOf(GSConstAddress.ABATYPE_HEADOFFICE)));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.123"),
                                        String.valueOf(GSConstAddress.ABATYPE_BRANCH)));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.124"),
                                        String.valueOf(GSConstAddress.ABATYPE_BUSINESSOFFICE)));
        paramMdl.setAbaTypeList(abaTypeList);

        //会社拠点情報の都道府県名を設定する
        List<Adr110BaseForm> baseList = paramMdl.getAbaListToList();
        if (baseList != null) {
            CmnTdfkDao tdfkDao = new CmnTdfkDao(con);

            for (Adr110BaseForm baseForm : baseList) {
                int tdfkSid = baseForm.getAdr110abaTdfk();
                CmnTdfkModel tdfkMdl = tdfkDao.select(tdfkSid);
                if (tdfkMdl != null) {
                    baseForm.setAdr110abaTdfkName(tdfkMdl.getTdfName());
                }
            }
        }
    }

    /**
     * <br>[機  能] 会社情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr110ParamModel
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void deleteCompany(Connection con, Adr110ParamModel paramMdl, int sessionUserSid)
    throws Exception {
        int acoSid = paramMdl.getAdr110editAcoSid();

        //会社情報の削除
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        companyDao.delete(acoSid);

        //拠点情報の削除
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        companyBaseDao.deleteCompany(acoSid);

        //所属業種情報の削除
        AdrBelongIndustryDao blgIndustryDao = new AdrBelongIndustryDao(con);
        blgIndustryDao.delete(acoSid);

        //アドレス情報の"会社"を未設定に更新する
        UDate now = new UDate();
        AdrAddressDao addressDao = new AdrAddressDao(con);
        addressDao.resetCompany(acoSid, sessionUserSid, now);

    }

    /**
     * <br>[機  能] 会社拠点情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr110ParamModel
     */
    public void deleteCompanyBase(Adr110ParamModel paramMdl) {

        List<Adr110BaseForm> abaList = paramMdl.getAbaListToList();
        List<Adr110BaseForm> newAbaList = new ArrayList<Adr110BaseForm>();
        int deleteIndex = paramMdl.getAdr110deleteCompanyBaseIndex();
        for (Adr110BaseForm baseForm : abaList) {
            if (baseForm.getAdr110abaIndex() != deleteIndex) {
                newAbaList.add(baseForm);
            }
        }

        paramMdl.setAbaListForm(newAbaList);
    }

    /**
     * <br>[機  能] 指定したインデックスの支店・営業所名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr110ParamModel
     * @param index インデックス
     * @return 支店・営業所名
     */
    public String getCoBaseName(Adr110ParamModel paramMdl, int index) {

        List<Adr110BaseForm> abaList = paramMdl.getAbaListToList();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String coBaseName = "";
        for (Adr110BaseForm baseForm : abaList) {
            if (baseForm.getAdr110abaIndex() == index) {
                String typeName = gsMsg.getMessage(baseForm.getAdr110abaTypeNameDetail());
                if (!StringUtil.isNullZeroString(typeName)) {
                    coBaseName = typeName + " ";
                }
                coBaseName += baseForm.getAdr110abaName();
            }
        }

        return coBaseName;
    }

    /**
     * <br>[機  能] DBから会社情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr110ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void _readCompanyData(Connection con, Adr110ParamModel paramMdl) throws SQLException {

        //会社情報
        int acoSid = paramMdl.getAdr110editAcoSid();
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        AdrCompanyModel companyModel = companyDao.select(acoSid);
        if (companyModel != null) {
            paramMdl.setAdr110coCode(companyModel.getAcoCode());
            paramMdl.setAdr110coName(companyModel.getAcoName());
            paramMdl.setAdr110coNameKn(companyModel.getAcoNameKn());
            paramMdl.setAdr110coPostno1(companyModel.getAcoPostno1());
            paramMdl.setAdr110coPostno2(companyModel.getAcoPostno2());
            paramMdl.setAdr110coTdfk(companyModel.getTdfSid());
            paramMdl.setAdr110coAddress1(companyModel.getAcoAddr1());
            paramMdl.setAdr110coAddress2(companyModel.getAcoAddr2());
            paramMdl.setAdr110url(companyModel.getAcoUrl());
            paramMdl.setAdr110biko(companyModel.getAcoBiko());
        }
        //所属業種
        AdrBelongIndustryDao blgIndustryDao = new AdrBelongIndustryDao(con);
        paramMdl.setAdr110atiList(blgIndustryDao.getAtiSidList(paramMdl.getAdr110editAcoSid()));

        paramMdl.setAdr110initFlg(1);

        //会社拠点情報
        int abaIndex = 0;
        List<Adr110BaseForm> abaList = new ArrayList<Adr110BaseForm>();
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        List<AdrCompanyBaseModel> baseList = companyBaseDao.getCompanyBaseList(acoSid);
        for (AdrCompanyBaseModel baseModel : baseList) {
            Adr110BaseForm baseForm = new Adr110BaseForm();
            baseForm.setAdr110abaIndex(abaIndex);
            baseForm.setAdr110abaSidDetail(baseModel.getAbaSid());
            baseForm.setAdr110abaTypeDetail(baseModel.getAbaType());
            baseForm.setAdr110abaName(baseModel.getAbaName());
            baseForm.setAdr110abaTdfk(baseModel.getTdfSid());
            baseForm.setAdr110abaPostno1(baseModel.getAbaPostno1());
            baseForm.setAdr110abaPostno2(baseModel.getAbaPostno2());
            baseForm.setAdr110abaAddress1(baseModel.getAbaAddr1());
            baseForm.setAdr110abaAddress2(baseModel.getAbaAddr2());
            baseForm.setAdr110abaBiko(baseModel.getAbaBiko());

            //地図検索ワード
            String address = baseModel.getAbaAddr1() + baseModel.getAbaAddr2();
            baseForm.setAdr110abaWebSearchWord(StringUtil.toSingleCortationEscape(address));

            abaList.add(baseForm);
            abaIndex++;
        }
        paramMdl.setAbaListForm(abaList);
    }
    /**
     * 
     * <br>[機  能] 会社情報の表示可能判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     * @return 0：表示可能 1:データがないため表示不可
     */
    public int canViewCompanyData(Connection con, Adr110ParamModel paramMdl) throws SQLException {
        int acoSid = paramMdl.getAdr110editAcoSid();
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        AdrCompanyModel companyModel = companyDao.select(acoSid);
        if (companyModel == null) {
            return 1;
        }
        return 0;
    }
    
    /**
     * <br>[機  能] アドレス帳の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr110ParamModel
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void entryCompanyData(
            Connection con, Adr110ParamModel paramMdl, MlCountMtController mtCon,
                                int sessionUserSid)
    throws IOException, IOToolsException, SQLException {

        UDate now = new UDate();

        //会社情報の登録を行う
        AdrCompanyModel companyMdl = new AdrCompanyModel();

        companyMdl.setAcoCode(paramMdl.getAdr110coCode());
        companyMdl.setAcoName(paramMdl.getAdr110coName());
        companyMdl.setAcoNameKn(paramMdl.getAdr110coNameKn());
        companyMdl.setAcoSini(StringUtilKana.getInitKanaChar(paramMdl.getAdr110coNameKn()));

        companyMdl.setAcoPostno1(paramMdl.getAdr110coPostno1());
        companyMdl.setAcoPostno2(paramMdl.getAdr110coPostno2());
        companyMdl.setTdfSid(paramMdl.getAdr110coTdfk());
        companyMdl.setAcoAddr1(paramMdl.getAdr110coAddress1());
        companyMdl.setAcoAddr2(paramMdl.getAdr110coAddress2());

        companyMdl.setAcoUrl(paramMdl.getAdr110url());
        companyMdl.setAcoBiko(paramMdl.getAdr110biko());
        companyMdl.setAcoAuid(sessionUserSid);
        companyMdl.setAcoAdate(now);
        companyMdl.setAcoEuid(sessionUserSid);
        companyMdl.setAcoEdate(now);

        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        int acoSid = paramMdl.getAdr110editAcoSid();
        if (paramMdl.getAdr110ProcMode() == GSConstAddress.PROCMODE_ADD) {
            acoSid = (int) mtCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                GSConstAddress.SBNSID_SUB_COMPANY, sessionUserSid);
            companyMdl.setAcoSid(acoSid);
            companyDao.insert(companyMdl);
        } else {
            companyMdl.setAcoSid(acoSid);
            companyDao.update(companyMdl);
        }

        //所属業種情報を登録する
        AdrBelongIndustryDao blgIndustryDao = new AdrBelongIndustryDao(con);
        blgIndustryDao.delete(acoSid);

        String[] atiSidList = paramMdl.getAdr110atiList();
        if (atiSidList != null && atiSidList.length > 0) {
            AdrBelongIndustryModel blgIndustryModel = new AdrBelongIndustryModel();
            blgIndustryModel.setAcoSid(acoSid);
            blgIndustryModel.setAbiAuid(sessionUserSid);
            blgIndustryModel.setAbiAdate(now);
            blgIndustryModel.setAbiEuid(sessionUserSid);
            blgIndustryModel.setAbiEdate(now);

            for (String atiSid : atiSidList) {
                blgIndustryModel.setAtiSid(Integer.parseInt(atiSid));
                blgIndustryDao.insert(blgIndustryModel);
            }
        }


        //会社拠点情報を取得する
        List<Integer> editSidList = new ArrayList<Integer>();
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        if (paramMdl.getAbaListToList() != null) {
            List<Adr110BaseForm> abaList = paramMdl.getAbaListToList();
            AdrCompanyBaseModel companyBaseModel = new AdrCompanyBaseModel();
            companyBaseModel.setAcoSid(acoSid);
            companyBaseModel.setAbaAuid(sessionUserSid);
            companyBaseModel.setAbaAdate(now);
            companyBaseModel.setAbaEuid(sessionUserSid);
            companyBaseModel.setAbaEdate(now);

            for (Adr110BaseForm baseForm : abaList) {
                int abaSid = baseForm.getAdr110abaSidDetail();
                companyBaseModel.setAbaType(baseForm.getAdr110abaTypeDetail());
                companyBaseModel.setAbaName(baseForm.getAdr110abaName());
                companyBaseModel.setTdfSid(baseForm.getAdr110abaTdfk());
                companyBaseModel.setAbaPostno1(baseForm.getAdr110abaPostno1());
                companyBaseModel.setAbaPostno2(baseForm.getAdr110abaPostno2());
                companyBaseModel.setAbaAddr1(baseForm.getAdr110abaAddress1());
                companyBaseModel.setAbaAddr2(baseForm.getAdr110abaAddress2());
                companyBaseModel.setAbaBiko(baseForm.getAdr110abaBiko());
                if (abaSid > 0) {
                    companyBaseModel.setAbaSid(abaSid);
                    companyBaseDao.update(companyBaseModel);
                } else {
                    abaSid = (int) mtCon.getSaibanNumber(
                                            GSConst.SBNSID_ADDRESS,
                                            GSConstAddress.SBNSID_SUB_CO_BASE,
                                            sessionUserSid);
                    companyBaseModel.setAbaSid(abaSid);
                    companyBaseDao.insert(companyBaseModel);
                }
                editSidList.add(Integer.valueOf(abaSid));
            }
        }
        if (paramMdl.getAdr110ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            companyBaseDao.delete(acoSid, editSidList);
        }

    }
}
