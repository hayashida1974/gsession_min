package jp.groupsession.v2.adr.adr070kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr070.AddressCsvModel;
import jp.groupsession.v2.adr.adr070.AddressCsvReader;
import jp.groupsession.v2.adr.adr070.Adr070Biz;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrPermitEditDao;
import jp.groupsession.v2.adr.dao.AdrPermitViewDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrPermitEditModel;
import jp.groupsession.v2.adr.model.AdrPermitViewModel;
import jp.groupsession.v2.adr.model.AdrPersonchargeModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アドレス帳 アドレスインポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr070knBiz extends Adr070Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Adr070knBiz.class);

    /** 登録更新済み会社SIDリスト */
    private List<Integer> acoSidList__ = new ArrayList<Integer>();

    /** 登録更新済み拠点SIDリスト */
    private List<Integer> abaSidList__ = new ArrayList<Integer>();

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr070knBiz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr070knParamModel
     * @param tempDir テンポラリディレクトリパス
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Adr070knParamModel paramMdl, String tempDir)
    throws Exception {

        log__.debug("START");

        AddressBiz addressBiz = new AddressBiz(reqMdl_);

        //取込ファイル名を設定する
        List<LabelValueBean> fileLabel = addressBiz.getFileCombo(tempDir);
        paramMdl.setAdr070knFileName(fileLabel.get(0).getLabel());

        //取込アドレス氏名を設定する
        List<AddressCsvModel> addressList = getAddressList(tempDir, paramMdl);
        List<String> nameList = new ArrayList<String>();
        for (AddressCsvModel model : addressList) {
            nameList.add(model.getNameSei() + " " + model.getNameMei());
        }
        paramMdl.setAdr070knNameList(nameList);

        if (paramMdl.getAdr070cmdMode() == 0) {
            //会社名を設定する
            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            AdrCompanyModel companyModel = companyDao.select(paramMdl.getAdr070selectCompany());
            if (companyModel != null) {
                paramMdl.setAdr070knCompanyName(companyModel.getAcoName());
            }

            //支店・営業所名を設定する
            AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
            AdrCompanyBaseModel companyBaseModel
                = companyBaseDao.select(paramMdl.getAdr070selectCompanyBase());
            if (companyBaseModel != null) {
                paramMdl.setAdr070knCompanyBaseName(companyBaseModel.getAbaName());
            }
        } else {
            //取り込む会社情報一覧を取得する。

            List<String> companyList = new ArrayList<String>();
            List<String> companyCodeList = new ArrayList<String>();
            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            GsMessage gsMsg = new GsMessage(reqMdl_);
            String textUpdate = gsMsg.getMessage("cmn.update");

            for (AddressCsvModel model : addressList) {
                if (StringUtil.isNullZeroString(model.getCompanyCode())) {
                    continue;
                }
                if (companyCodeList.contains(model.getCompanyCode())) {
                    continue;
                }
                AdrCompanyModel companyMdl = companyDao.select(model.getCompanyCode());
                if (companyMdl != null && paramMdl.getAdr070updateFlg() == 1) {
                    companyList.add(model.getCompanyName() + "(" + textUpdate + ")");
                } else if (companyMdl == null) {
                    companyList.add(model.getCompanyName());
                }
                companyCodeList.add(model.getCompanyCode());
            }
            paramMdl.setAdr070knComList(companyList);
        }

        //新規追加される役職の表示を設定する
        AdrPositionDao positionDao = new AdrPositionDao(con);
        List<String> newPositionList = new ArrayList<String>();
        for (AddressCsvModel model : addressList) {
            String positionName = model.getYakusyoku();
            if (!StringUtil.isNullZeroString(positionName)
                    && !positionDao.isExistPositionName(positionName, 0)
                    && !newPositionList.contains(positionName)) {
                //役職名が未登録の場合、新規追加される役職リストに加える
                newPositionList.add(positionName);
            }
        }
        paramMdl.setAdr070knPositionList(newPositionList);

        //担当者名を設定する
        List<List<UsrLabelValueBean>> tantoComboList = addressBiz.getUserCombo(con,
                                                                null,
                                                                paramMdl.getAdr070tantoGroup(),
                                                                paramMdl.getAdr070tantoList());
        paramMdl.setSelectTantoCombo(tantoComboList.get(0));

        //閲覧グループを設定する
        List<LabelValueBean> viewPermitGroup
                = addressBiz.getGroupLabelList(con, paramMdl.getAdr070permitViewGroup());
        paramMdl.setSelectPermitViewGroup(viewPermitGroup);

        //編集グループを設定する
        List<LabelValueBean> editPermitGroup
        = addressBiz.getGroupLabelList(con, paramMdl.getAdr070permitEditGroup());
        paramMdl.setSelectPermitEditGroup(editPermitGroup);

        //閲覧ユーザを設定する
        List<List<UsrLabelValueBean>> viewPermitUserComboList
                = addressBiz.getUserCombo(con, null,
                                paramMdl.getAdr070permitViewUserGroup(),
                                paramMdl.getAdr070permitViewUser());
        paramMdl.setSelectPermitViewUser(viewPermitUserComboList.get(0));

        //編集ユーザを設定する
        List<List<UsrLabelValueBean>> editPermitUserComboList
                = addressBiz.getUserCombo(con, null,
                                paramMdl.getAdr070permitEditUserGroup(),
                                paramMdl.getAdr070permitEditUser());
        paramMdl.setSelectPermitEditUser(editPermitUserComboList.get(0));

        log__.debug("End");
    }

    /**
     * <br>[機  能] アドレス帳情報のインポートを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr070knParamModel
     * @param tempDir テンポラリディレクトリ
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行時例外
     * @return count 登録件数
     */
    public int importAddress(Connection con, Adr070knParamModel paramMdl, String tempDir,
                            MlCountMtController mtCon, int sessionUserSid)
    throws Exception {

        UDate now = new UDate();
        CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
        AdrAddressDao addressDao = new AdrAddressDao(con);
        AdrPositionDao positionDao = new AdrPositionDao(con);
        AdrPersonchargeDao paersonChargeDao = new AdrPersonchargeDao(con);
        AdrPermitViewDao permitViewDao = new AdrPermitViewDao(con);
        AdrPermitEditDao permitEditDao = new AdrPermitEditDao(con);
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        List<AddressCsvModel> addressList = getAddressList(tempDir, paramMdl);
        int count = 0;
        for (AddressCsvModel addressData : addressList) {

            AdrAddressModel addressMdl = new AdrAddressModel();

            addressMdl.setAdrSei(addressData.getNameSei());
            addressMdl.setAdrMei(addressData.getNameMei());
            addressMdl.setAdrSeiKn(addressData.getNameSeiKn());
            addressMdl.setAdrMeiKn(addressData.getNameMeiKn());
            addressMdl.setAdrSini(StringUtilKana.getInitKanaChar(addressData.getNameSeiKn()));

            addressMdl.setAdrSyozoku(addressData.getSyozoku());

            //役職
            String positionName = addressData.getYakusyoku();
            int positionSid = 0;
            if (!StringUtil.isNullZeroString(positionName)) {
                if (positionDao.isExistPositionName(positionName, 0)) {
                    //役職名が存在する場合、SIDを取得する
                    positionSid = __getPositionSid(positionDao, positionName);
                } else {
                    //役職名が存在しない場合、新しく役職を登録する
                    positionSid = __insertPosition(positionName, con, mtCon, positionSid);
                }
            }
            addressMdl.setApsSid(positionSid);

            addressMdl.setAdrMail1(addressData.getMail1());
            addressMdl.setAdrMailCmt1(addressData.getMail1Comment());
            addressMdl.setAdrMail2(addressData.getMail2());
            addressMdl.setAdrMailCmt2(addressData.getMail2Comment());
            addressMdl.setAdrMail3(addressData.getMail3());
            addressMdl.setAdrMailCmt3(addressData.getMail3Comment());

            String postNo = addressData.getPostNo();
            if (!StringUtil.isNullZeroString(postNo)) {
                addressMdl.setAdrPostno1(postNo.substring(0, 3));
                addressMdl.setAdrPostno2(postNo.substring(4));
            }
            addressMdl.setTdfSid(getTdfkSid(tdfkDao, addressData.getTdfk()));
            addressMdl.setAdrAddr1(addressData.getAddress1());
            addressMdl.setAdrAddr2(addressData.getAddress2());
            addressMdl.setAdrTel1(addressData.getTel1());
            addressMdl.setAdrTelNai1(addressData.getNai1());
            addressMdl.setAdrTelCmt1(addressData.getTel1Comment());
            addressMdl.setAdrTel2(addressData.getTel2());
            addressMdl.setAdrTelNai2(addressData.getNai2());
            addressMdl.setAdrTelCmt2(addressData.getTel2Comment());
            addressMdl.setAdrTel3(addressData.getTel3());
            addressMdl.setAdrTelNai3(addressData.getNai3());
            addressMdl.setAdrTelCmt3(addressData.getTel3Comment());
            addressMdl.setAdrFax1(addressData.getFax1());
            addressMdl.setAdrFaxCmt1(addressData.getFax1Comment());
            addressMdl.setAdrFax2(addressData.getFax2());
            addressMdl.setAdrFaxCmt2(addressData.getFax2Comment());
            addressMdl.setAdrFax3(addressData.getFax3());
            addressMdl.setAdrFaxCmt3(addressData.getFax3Comment());
            addressMdl.setAdrBiko(addressData.getBiko());

            addressMdl.setAdrPermitView(paramMdl.getAdr070permitView());
            addressMdl.setAdrPermitEdit(paramMdl.getAdr070permitEdit());
            addressMdl.setAdrAuid(sessionUserSid);
            addressMdl.setAdrAdate(now);
            addressMdl.setAdrEuid(sessionUserSid);
            addressMdl.setAdrEdate(now);

            if (paramMdl.getAdr070cmdMode() == 0) {
                //会社選択で『選択してください。』を選択した場合
                if (paramMdl.getAdr070selectCompany() == -1) {
                    addressMdl.setAcoSid(0);
                } else {
                    addressMdl.setAcoSid(paramMdl.getAdr070selectCompany());
                }

                //拠点選択で『選択してください。』を選択した場合
                if (paramMdl.getAdr070selectCompanyBase() == -1) {
                    addressMdl.setAbaSid(0);
                } else {
                    addressMdl.setAbaSid(paramMdl.getAdr070selectCompanyBase());
                }
            } else {
                //アドレス・会社情報同時の場合
                addressMdl = __insertCompany(
                                 paramMdl, addressData, mtCon, sessionUserSid,
                                 companyDao, companyBaseDao, tdfkDao, addressMdl);

            }

            int adrSid = (int) mtCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                    GSConstAddress.SBNSID_SUB_ADDRESS,
                                                    sessionUserSid);
            addressMdl.setAdrSid(adrSid);
            addressDao.insert(addressMdl);



            //担当者情報を登録する
            String[] tantoUserSid = paramMdl.getAdr070tantoList();
            if (tantoUserSid != null && tantoUserSid.length > 0) {

                for (String sid : tantoUserSid) {
                    AdrPersonchargeModel parsonChargeMdl = new AdrPersonchargeModel();
                    parsonChargeMdl.setAdrSid(adrSid);
                    parsonChargeMdl.setUsrSid(Integer.parseInt(sid));
                    parsonChargeMdl.setApcAuid(sessionUserSid);
                    parsonChargeMdl.setApcAdate(now);
                    parsonChargeMdl.setApcEuid(sessionUserSid);
                    parsonChargeMdl.setApcEdate(now);

                    paersonChargeDao.insert(parsonChargeMdl);
                }
            }

            //閲覧権限情報を登録する
            permitViewDao.deleteToAddress(adrSid);

            int permitView = paramMdl.getAdr070permitView();
            AdrPermitViewModel permitViewModel = new AdrPermitViewModel();
            permitViewModel.setAdrSid(adrSid);
            permitViewModel.setAdrPermitView(permitView);
            permitViewModel.setApvAuid(sessionUserSid);
            permitViewModel.setApvAdate(now);
            permitViewModel.setApvEuid(sessionUserSid);
            permitViewModel.setApvEdate(now);
            if (permitView == GSConst.ADR_VIEWPERMIT_GROUP) {
                for (String grpSid : paramMdl.getAdr070permitViewGroup()) {
                    permitViewModel.setGrpSid(Integer.parseInt(grpSid));
                    permitViewModel.setUsrSid(-1);
                    permitViewDao.insert(permitViewModel);
                }
            } else if (permitView == GSConst.ADR_VIEWPERMIT_USER) {
                for (String userSid : paramMdl.getAdr070permitViewUser()) {
                    permitViewModel.setGrpSid(-1);
                    permitViewModel.setUsrSid(Integer.parseInt(userSid));
                    permitViewDao.insert(permitViewModel);
                }
            }

            //編集権限情報を登録する
            permitEditDao.deleteToAddress(adrSid);

            int permitEdit = paramMdl.getAdr070permitEdit();
            AdrPermitEditModel permitEditModel = new AdrPermitEditModel();
            permitEditModel.setAdrSid(adrSid);
            permitEditModel.setAdrPermitEdit(permitEdit);
            permitEditModel.setApeAuid(sessionUserSid);
            permitEditModel.setApeAdate(now);
            permitEditModel.setApeEuid(sessionUserSid);
            permitEditModel.setApeEdate(now);
            if (permitEdit == GSConstAddress.EDITPERMIT_GROUP) {
                for (String grpSid : paramMdl.getAdr070permitEditGroup()) {
                    permitEditModel.setGrpSid(Integer.parseInt(grpSid));
                    permitEditModel.setUsrSid(-1);
                    permitEditDao.insert(permitEditModel);
                }
            } else if (permitEdit == GSConstAddress.EDITPERMIT_USER) {
                for (String userSid : paramMdl.getAdr070permitEditUser()) {
                    permitEditModel.setGrpSid(-1);
                    permitEditModel.setUsrSid(Integer.parseInt(userSid));
                    permitEditDao.insert(permitEditModel);
                }
            }

            count++;
        }
        return count;
    }

    /**
     * <br>[機  能] 役職名から役職SIDを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param positionDao AdrPositionDao
     * @param positionName 役職名
     * @throws SQLException SQL実行時例外
     * @return 役職SID
     */
    private int __getPositionSid(
            AdrPositionDao positionDao, String positionName)
                    throws SQLException {
        int ret = 0;
        ArrayList<AdrPositionModel> positionList = positionDao.selectPositionList();
        for (AdrPositionModel mdl : positionList) {
            if (mdl.getApsName().equals(positionName)) {
                ret = mdl.getApsSid();
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 役職登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param positionName 役職名
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     * @return 役職SID
     */
    private int __insertPosition(String positionName,
                             Connection con,
                             MlCountMtController cntCon,
                             int userSid) throws SQLException {
        int posSid = 0;
        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //役職SID採番
            posSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                       GSConstAddress.SBNSID_SUB_POSITION,
                                                       userSid);
            //登録用Model作成
            AdrPositionModel apMdl = __getPositionModel(con, posSid, positionName, userSid);

            //insert
            AdrPositionDao apDao = new AdrPositionDao(con);
            apDao.insertNewYakusyoku(apMdl);
            commitFlg = true;

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

        return posSid;
    }

    /**
     * <br>[機  能] 役職Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param posSid 役職SID
     * @param positionName 役職名
     * @param userSid ログインユーザSID
     * @return AdrPositionModel 役職Model
     * @throws SQLException SQL実行例外
     */
    private AdrPositionModel __getPositionModel(Connection con,
                                               int posSid,
                                               String positionName,
                                               int userSid) throws SQLException {

        UDate now = new UDate();

        AdrPositionModel mdl = new AdrPositionModel();
        mdl.setApsSid(posSid);
        mdl.setApsName(NullDefault.getString(positionName, ""));
        mdl.setApsAuid(userSid);
        mdl.setApsAdate(now);
        mdl.setApsEuid(userSid);
        mdl.setApsEdate(now);
        mdl.setApsBiko("");
        return mdl;
    }

    /**
     * <br>[機  能] 会社情報のインポートを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr070knParamModel
     * @param addressData AddressCsvModel
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @param companyDao AdrCompanyDao
     * @param companyBaseDao AdrCompanyBaseDao
     * @param tdfkDao CmnTdfkDao
     * @param addressMdl アドレス情報登録モデル
     * @return addressMdl アドレス情報登録モデル
     * @throws Exception 実行時例外
     */
    private AdrAddressModel __insertCompany(
            Adr070knParamModel paramMdl, AddressCsvModel addressData,
                            MlCountMtController mtCon, int sessionUserSid,
                            AdrCompanyDao companyDao, AdrCompanyBaseDao companyBaseDao,
                            CmnTdfkDao tdfkDao, AdrAddressModel addressMdl)
    throws Exception {

        addressMdl.setAcoSid(0);
        addressMdl.setAbaSid(0);
        if (StringUtil.isNullZeroString(addressData.getCompanyCode())) {
            return addressMdl;
        }

        UDate now = new UDate();

        //会社情報存在フラグ
        boolean exist = false;
        AdrCompanyModel companyMdl = companyDao.select(addressData.getCompanyCode());

        int acoSid = 0;

        if (companyMdl != null) {
            //会社情報を取得
            acoSid = companyMdl.getAcoSid();
            exist = true;

        } else {
            //会社SIDを採番
            acoSid = (int) mtCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                    GSConstAddress.SBNSID_SUB_COMPANY,
                    sessionUserSid);
        }

        //会社情報登録モデルを生成
        AdrCompanyModel compaMdl = new AdrCompanyModel();
        compaMdl.setAcoSid(acoSid);
        compaMdl.setAcoCode(addressData.getCompanyCode());
        compaMdl.setAcoEuid(sessionUserSid);
        compaMdl.setAcoEdate(now);
        compaMdl.setAcoName(addressData.getCompanyName());
        String companyNameKn = addressData.getCompanyNameKn();
        compaMdl.setAcoNameKn(companyNameKn);
        compaMdl.setAcoSini(StringUtilKana.getInitKanaChar(companyNameKn));
        if (!StringUtil.isNullZeroString(addressData.getCompanyUrl())) {
            compaMdl.setAcoUrl(addressData.getCompanyUrl());
        }
        if (!StringUtil.isNullZeroString(addressData.getCompanyBiko())) {
            compaMdl.setAcoBiko(addressData.getCompanyBiko());
        }

        String coPostNo = addressData.getCompanyPostNo();
        if (!StringUtil.isNullZeroString(coPostNo)) {
            compaMdl.setAcoPostno1(coPostNo.substring(0, 3));
            compaMdl.setAcoPostno2(coPostNo.substring(4));
        }
        if (!StringUtil.isNullZeroString(addressData.getCompanyTdfk())) {
            compaMdl.setTdfSid(getTdfkSid(tdfkDao, addressData.getCompanyTdfk()));
        }
        if (!StringUtil.isNullZeroString(addressData.getCompanyAddress1())) {
            compaMdl.setAcoAddr1(addressData.getCompanyAddress1());
        }
        if (!StringUtil.isNullZeroString(addressData.getCompanyAddress2())) {
            compaMdl.setAcoAddr2(addressData.getCompanyAddress2());
        }

        //会社情報を登録
        if (!acoSidList__.contains(acoSid)) {
            if (exist) {
                if (paramMdl.getAdr070updateFlg() == 1) {
                    companyDao.update(compaMdl);
                }
            } else {
                compaMdl.setAcoAuid(sessionUserSid);
                compaMdl.setAcoAdate(now);
                companyDao.insert(compaMdl);
            }
            acoSidList__.add(acoSid);
        }

        addressMdl.setAcoSid(acoSid);


        //拠点情報
        if (StringUtil.isNullZeroStringSpace(addressData.getCompanyBaseName())) {
            //拠点情報未入力の場合
            return addressMdl;
        }

        int abaSid = 0;
        boolean comBaseExist = false;

        //拠点SIDを取得する
        if (exist) {
            //拠点情報を取得する。
            AdrCompanyBaseModel compaBaseModel
                = companyBaseDao.getCompanyBaseModel(acoSid, addressData.getCompanyBaseName());

            if (compaBaseModel == null) {
                //拠点SIDを採番
                abaSid = (int) mtCon.getSaibanNumber(
                        GSConst.SBNSID_ADDRESS,
                        GSConstAddress.SBNSID_SUB_CO_BASE,
                        sessionUserSid);
            } else {
                abaSid = compaBaseModel.getAbaSid();
                comBaseExist = true;
            }
        } else {

            //拠点SIDを採番
            abaSid = (int) mtCon.getSaibanNumber(
                    GSConst.SBNSID_ADDRESS,
                    GSConstAddress.SBNSID_SUB_CO_BASE,
                    sessionUserSid);
        }

        addressMdl.setAbaSid(abaSid);

        //更新済みリストにないか判定
        if (abaSidList__.contains(abaSid)) {
            //更新済みの拠点情報の場合先のものを優先する。
            return addressMdl;
        }


        //拠点登録モデルを生成する。
        AdrCompanyBaseModel companyBaseModel = new AdrCompanyBaseModel();
        companyBaseModel.setAcoSid(acoSid);
        companyBaseModel.setAbaSid(abaSid);
        companyBaseModel.setAbaName(addressData.getCompanyBaseName());
        companyBaseModel.setTdfSid(getTdfkSid(tdfkDao, addressData.getCompanyBaseTdfk()));
        String postNo = addressData.getCompanyBasePostNo();
        if (!StringUtil.isNullZeroString(postNo)) {
            companyBaseModel.setAbaPostno1(postNo.substring(0, 3));
            companyBaseModel.setAbaPostno2(postNo.substring(4));
        }
        companyBaseModel.setAbaAddr1(addressData.getCompanyBaseAddress1());
        companyBaseModel.setAbaAddr2(addressData.getCompanyBaseAddress2());
        companyBaseModel.setAbaBiko(addressData.getCompanyBaseBiko());
        companyBaseModel.setAbaAuid(sessionUserSid);
        companyBaseModel.setAbaAdate(now);
        companyBaseModel.setAbaEuid(sessionUserSid);
        companyBaseModel.setAbaEdate(now);

        //企業拠点種別
        int companyBaseType = __getCompanyBaseType(addressData.getCompanyBaseType());
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

        //拠点情報を登録
        if (comBaseExist) {
            if (paramMdl.getAdr070updateFlg() == 1) {
                companyBaseDao.update(companyBaseModel);
            }
        } else {
            companyBaseDao.insert(companyBaseModel);

        }
        abaSidList__.add(abaSid);

        return addressMdl;
    }

    /**
     * <br>[機  能] 拠点種別名から拠点種別を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param typeName 拠点種別名
     * @return 拠点種別
     */
    private int __getCompanyBaseType(String typeName) {
        int ret = 0;
        typeName = NullDefault.getString(typeName, "");

        if (typeName.length() > 0) {

            if (typeName.equals(GSConstAddress.ABATYPE_HEADOFFICE_STR)) {
                //本社
                ret = GSConstAddress.ABATYPE_HEADOFFICE;

            } else if (typeName.equals(GSConstAddress.ABATYPE_BRANCH_STR)) {
                //支店
                ret = GSConstAddress.ABATYPE_BRANCH;

            } else if (typeName.equals(GSConstAddress.ABATYPE_BUSINESSOFFICE_STR)) {
                //営業所
                ret = GSConstAddress.ABATYPE_BUSINESSOFFICE;

            } else {
                //なし
                ret = GSConstAddress.ABATYPE_NONE;
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] CSVファイルからアドレス帳情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param paramMdl Adr070knParamModel
     * @return アドレス帳情報一覧
     * @throws Exception 実行時例外
     */
    public List<AddressCsvModel> getAddressList(String tempDir, Adr070knParamModel paramMdl)
    throws Exception {

        AddressBiz addressBiz = new AddressBiz(reqMdl_);
        List<Cmn110FileModel> fileDataList = addressBiz.getFileData(tempDir);
        String fullPath = tempDir + fileDataList.get(0).getSaveFileName();
        AddressCsvReader csvReader = new AddressCsvReader(paramMdl.getAdr070cmdMode());
        csvReader.readCsvFile(fullPath);

        return csvReader.getAddressList();
    }

    /**
     * <p>acoSidList を取得します。
     * @return acoSidList
     */
    public List<Integer> getAcoSidList() {
        return acoSidList__;
    }

    /**
     * <p>acoSidList をセットします。
     * @param acoSidList acoSidList
     */
    public void setAcoSidList(List<Integer> acoSidList) {
        acoSidList__ = acoSidList;
    }

    /**
     * <p>abaSidList を取得します。
     * @return abaSidList
     */
    public List<Integer> getAbaSidList() {
        return abaSidList__;
    }

    /**
     * <p>abaSidList をセットします。
     * @param abaSidList abaSidList
     */
    public void setAbaSidList(List<Integer> abaSidList) {
        abaSidList__ = abaSidList;
    }

}

