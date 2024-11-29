package jp.groupsession.v2.adr.adr120kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr120.Adr120Biz;
import jp.groupsession.v2.adr.adr120.CompanyCsvModel;
import jp.groupsession.v2.adr.adr120.CompanyCsvReader;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrBelongIndustryDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrBelongIndustryModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社インポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr120knBiz extends Adr120Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Adr120knBiz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr120knBiz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr120knParamModel
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con,
                             Adr120knParamModel paramMdl,
                             String tempDir)
    throws Exception {

        log__.debug("START");

        GsMessage gsMsg = new GsMessage(reqMdl_);
        AddressBiz addressBiz = new AddressBiz(reqMdl_);

        //取込ファイル名を設定する
        List<LabelValueBean> fileLabel = addressBiz.getFileCombo(tempDir);
        paramMdl.setAdr120knFileName(fileLabel.get(0).getLabel());

        //業種情報を設定する
        __setGyosyuList(con, paramMdl);

        //会社情報を設定する
        List<Adr120knCompanyData> companyDataList = new ArrayList<Adr120knCompanyData>();
        Map<String, List<CompanyCsvModel>> companyMap = getCompanyMap(con, tempDir);
        Iterator<String> companyCodeIterator = companyMap.keySet().iterator();

        AdrCompanyDao companyDao = new AdrCompanyDao(con);

        //読み込み件数
        int readCnt = 0;
        while (companyCodeIterator.hasNext()) {
            Adr120knCompanyData companyData = new Adr120knCompanyData();
            companyData.setCompanyBaseNameList(new ArrayList<String>());

            //会社拠点名称を設定
            List<CompanyCsvModel> companyList = companyMap.get(companyCodeIterator.next());
            for (CompanyCsvModel companyCsvData : companyList) {

//                if (!StringUtil.isNullZeroString(companyCsvData.getCompanyBaseType())
//                && !StringUtil.isNullZeroString(companyCsvData.getCompanyBaseName())) {
//                    companyData.getCompanyBaseNameList().add(companyCsvData.getCompanyBaseName());
//                }

                int companyBaseType =
                    NullDefault.getInt(companyCsvData.getCompanyBaseType(), 0);

                if (companyBaseType == 0
                && !StringUtil.isNullZeroStringSpace(companyCsvData.getCompanyBaseName())
                && (!StringUtil.isNullZeroStringSpace(companyCsvData.getCompanyBasePostNo())
                || NullDefault.getInt(companyCsvData.getCompanyBaseTdfk(), 0) > 0
                || !StringUtil.isNullZeroStringSpace(companyCsvData.getCompanyBaseAddress1())
                || !StringUtil.isNullZeroStringSpace(companyCsvData.getCompanyBaseAddress2())
                || !StringUtil.isNullZeroStringSpace(companyCsvData.getCompanyBiko()))) {

                    companyData.getCompanyBaseNameList().add(
                            gsMsg.getMessage("address.122"));
                } else {
                    companyData.getCompanyBaseNameList().add(
                            companyCsvData.getCompanyBaseName());
                }

                //会社名を設定
                String companyName = companyCsvData.getCompanyName();
                if (!StringUtil.isNullZeroString(companyName)) {
                    companyData.setCompanyName(companyName);
                } else {
                    AdrCompanyModel companyMdl = companyDao.select(companyCsvData.getCompanyCode());
                    companyData.setCompanyName(companyMdl.getAcoName());
                }
                readCnt++;
            }
            companyDataList.add(companyData);
        }
        paramMdl.setAdr120knCompanyList(companyDataList);

        //取り込み会社件数
        paramMdl.setAdr120knCompanyCnt(readCnt);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 会社情報のインポートを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr120knParamModel
     * @param tempDir テンポラリディレクトリ
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行時例外
     */
    public void importCompany(Connection con, Adr120knParamModel paramMdl, String tempDir,
                            MlCountMtController mtCon, int sessionUserSid)
    throws Exception {
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        List<Integer> acoSidList = new ArrayList<Integer>();
        boolean existCompany = false;
        UDate now = new UDate();
        Map<String, List<CompanyCsvModel>> companyMap = getCompanyMap(con, tempDir);

        Iterator<String> companyCodeIterator = companyMap.keySet().iterator();
        while (companyCodeIterator.hasNext()) {
            String companyCode = companyCodeIterator.next();

            int acoSid = 0;
            AdrCompanyModel companyMdl = companyDao.select(companyCode);
            if (companyMdl != null) {
                acoSid = companyMdl.getAcoSid();
                existCompany = true;
            } else {
                acoSid = (int) mtCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                    GSConstAddress.SBNSID_SUB_COMPANY,
                                                    sessionUserSid);
                companyMdl = new AdrCompanyModel();
                companyMdl.setAcoSid(acoSid);
                companyMdl.setAcoCode(companyCode);
                existCompany = false;
            }

            List<CompanyCsvModel> companyList = companyMap.get(companyCode);

            //会社情報初期登録フラグ
            int firstFlg = 0;

            for (CompanyCsvModel companyCsvData : companyList) {
                if (!StringUtil.isNullZeroString(companyCsvData.getCompanyBaseType())
                        && !StringUtil.isNullZeroString(companyCsvData.getCompanyBaseName())) {

                    //会社拠点情報を登録
                    AdrCompanyBaseModel companyBaseModel = new AdrCompanyBaseModel();
                    int abaSid = (int) mtCon.getSaibanNumber(
                                            GSConst.SBNSID_ADDRESS,
                                            GSConstAddress.SBNSID_SUB_CO_BASE,
                                            sessionUserSid);
                    companyBaseModel.setAcoSid(acoSid);
                    companyBaseModel.setAbaSid(abaSid);

                    companyBaseModel.setAbaName(companyCsvData.getCompanyBaseName());
                    companyBaseModel.setTdfSid(
                            NullDefault.getInt(companyCsvData.getCompanyBaseTdfk(), 0));
                    String postNo = companyCsvData.getCompanyBasePostNo();
                    if (!StringUtil.isNullZeroString(postNo)) {
                        companyBaseModel.setAbaPostno1(postNo.substring(0, 3));
                        companyBaseModel.setAbaPostno2(postNo.substring(4));
                    }
                    companyBaseModel.setAbaAddr1(companyCsvData.getCompanyBaseAddress1());
                    companyBaseModel.setAbaAddr2(companyCsvData.getCompanyBaseAddress2());
                    companyBaseModel.setAbaBiko(companyCsvData.getCompanyBaseBiko());
                    companyBaseModel.setAbaAuid(sessionUserSid);
                    companyBaseModel.setAbaAdate(now);
                    companyBaseModel.setAbaEuid(sessionUserSid);
                    companyBaseModel.setAbaEdate(now);

                    int companyBaseType =
                        NullDefault.getInt(companyCsvData.getCompanyBaseType(), 0);

                    if (companyBaseType == 0
                            && !StringUtil.isNullZeroStringSpace(companyBaseModel.getAbaName())
                            && (!StringUtil.isNullZeroStringSpace(companyBaseModel.getAbaPostno1())
                            || !StringUtil.isNullZeroStringSpace(companyBaseModel.getAbaPostno2())
                            || companyBaseModel.getTdfSid() > 0
                            || !StringUtil.isNullZeroStringSpace(companyBaseModel.getAbaAddr1())
                            || !StringUtil.isNullZeroStringSpace(companyBaseModel.getAbaAddr2())
                            || !StringUtil.isNullZeroStringSpace(companyBaseModel.getAbaBiko()))) {

                        companyBaseModel.setAbaType(GSConstAddress.ABATYPE_HEADOFFICE);
                    } else {
                        companyBaseModel.setAbaType(companyBaseType);
                    }

                    companyBaseDao.insert(companyBaseModel);
                }

                //複数行同じ企業コードのデータがあった場合、一番上(初め)の会社情報だけを登録する
                if (firstFlg == 0) {
                    if (!StringUtil.isNullZeroString(companyCsvData.getCompanyName())) {
                        companyMdl.setAcoName(companyCsvData.getCompanyName());
                    }
                    if (!StringUtil.isNullZeroString(companyCsvData.getCompanyNameKn())) {
                        String companyNameKn = companyCsvData.getCompanyNameKn();
                        companyMdl.setAcoNameKn(companyNameKn);
                        companyMdl.setAcoSini(StringUtilKana.getInitKanaChar(companyNameKn));
                    }
                    String coPostNo = companyCsvData.getCompanyPostNo();
                    if (!StringUtil.isNullZeroString(coPostNo)) {
                        companyMdl.setAcoPostno1(coPostNo.substring(0, 3));
                        companyMdl.setAcoPostno2(coPostNo.substring(4));
                    }
                    if (!StringUtil.isNullZeroString(companyCsvData.getCompanyTdfk())) {
                        companyMdl.setTdfSid(Integer.parseInt(companyCsvData.getCompanyTdfk()));
                    }
                    if (!StringUtil.isNullZeroString(companyCsvData.getCompanyAddress1())) {
                        companyMdl.setAcoAddr1(companyCsvData.getCompanyAddress1());
                    }
                    if (!StringUtil.isNullZeroString(companyCsvData.getCompanyAddress2())) {
                        companyMdl.setAcoAddr2(companyCsvData.getCompanyAddress2());
                    }
                    if (!StringUtil.isNullZeroString(companyCsvData.getCompanyUrl())) {
                        companyMdl.setAcoUrl(companyCsvData.getCompanyUrl());
                    }
                    if (!StringUtil.isNullZeroString(companyCsvData.getCompanyBiko())) {
                        companyMdl.setAcoBiko(companyCsvData.getCompanyBiko());
                    }
                    firstFlg = 1;
                }
            }

            //会社情報を登録
            companyMdl.setAcoEuid(sessionUserSid);
            companyMdl.setAcoEdate(now);
            if (existCompany) {
                if (paramMdl.getAdr120updateFlg() == 1) {
                    companyDao.update(companyMdl);
                }
            } else {
                companyMdl.setAcoAuid(sessionUserSid);
                companyMdl.setAcoAdate(now);
                companyDao.insert(companyMdl);
            }

            acoSidList.add(Integer.valueOf(acoSid));
        }

        //所属業種を設定
        String[] atiSidArray = paramMdl.getAdr120atiSid();
        if (atiSidArray != null && atiSidArray.length > 0) {
            AdrBelongIndustryDao belongIndustryDao = new AdrBelongIndustryDao(con);

            AdrBelongIndustryModel blgIndustryModel = new AdrBelongIndustryModel();
            blgIndustryModel.setAbiAuid(sessionUserSid);
            blgIndustryModel.setAbiAdate(now);
            blgIndustryModel.setAbiEuid(sessionUserSid);
            blgIndustryModel.setAbiEdate(now);

            for (Integer acoSid : acoSidList) {
                if (existCompany) {
                    belongIndustryDao.delete(acoSid);
                }

                blgIndustryModel.setAcoSid(acoSid);
                for (String atiSid : atiSidArray) {
                    blgIndustryModel.setAtiSid(Integer.parseInt(atiSid));
                    belongIndustryDao.insert(blgIndustryModel);
                }
            }
        }
    }

    /**
     * <br>[機  能] ファイルから会社情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tempDir テンポラリディレクトリ
     * @return 会社情報
     * @throws Exception 実行時例外
     */
    public Map<String, List<CompanyCsvModel>> getCompanyMap(Connection con, String tempDir)
    throws Exception {
        AddressBiz addressBiz = new AddressBiz(reqMdl_);
        CompanyCsvReader csvReader = new CompanyCsvReader(con);
        List<Cmn110FileModel> fileDataList = addressBiz.getFileData(tempDir);
        csvReader.readCsvFile(tempDir + fileDataList.get(0).getSaveFileName());

        Map<String, List<CompanyCsvModel>> companyMap = csvReader.getCompanyMap();

        return companyMap;
    }

    /**
     * <br>[機  能] 取込みファイル名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     * @return String 保存しているファイル名
     */
    public String getImportFileName(String tempDir)
        throws SQLException, IOToolsException {

        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                    continue;
                }
                ret = fileName.substring(0, 11);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 業種一覧を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr120knParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setGyosyuList(Connection con, Adr120knParamModel paramMdl) throws SQLException {

        AddressBiz addressBiz = new AddressBiz(reqMdl_);
        List<LabelValueBean> allGyosyuCombo = addressBiz.getGyosyuLabelList(con, false);

        String[] selectAtiArray = paramMdl.getAdr120atiSid();
        Arrays.sort(selectAtiArray);

        List<LabelValueBean> selectGyosyuList = new ArrayList<LabelValueBean>();
        for (LabelValueBean gyosyuLabel : allGyosyuCombo) {
            if (Arrays.binarySearch(selectAtiArray, gyosyuLabel.getValue()) >= 0) {
                selectGyosyuList.add(gyosyuLabel);
            }
        }

        paramMdl.setAdr120knAtiList(selectGyosyuList);
    }
}
