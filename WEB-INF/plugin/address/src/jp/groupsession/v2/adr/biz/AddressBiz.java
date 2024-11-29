package jp.groupsession.v2.adr.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrArestDao;
import jp.groupsession.v2.adr.dao.AdrContactBinDao;
import jp.groupsession.v2.adr.dao.AdrPermitViewDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アドレス帳プラグインの共通機能を実装したクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AddressBiz {

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

//    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     *
//     */
//    public AddressBiz() {
//    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public AddressBiz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] グループコンボに設定する状報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabelList(Connection con)
        throws SQLException {
        return getGroupLabelList(con, true);
    }

    /**
     * <br>[機  能] グループコンボに設定する状報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabelListNotMessage(Connection con)
        throws SQLException {
        return getGroupLabelList(con, false);
    }

    /**
     * <br>[機  能] コンタクト履歴グループコンボに設定する状報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabelListContact(Connection con)
        throws SQLException {
        return getGroupLabelListContact(con, true);
    }

//    /**
//     * <br>[機  能] 会社拠点種別名称を取得する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param companyBaseType 会社拠点種別
//     * @param req リクエスト
//     * @return 会社拠点種別名称
//     */
//    public static String getCompanyBaseTypeName(int companyBaseType, RequestModel reqMdl) {
//        String companyBaseName = null;
//        GsMessage gsMsg = new GsMessage();
//        switch (companyBaseType) {
//            case GSConstAddress.ABATYPE_HEADOFFICE :
//                //本社
//                companyBaseName = gsMsg.getMessage(req, "address.122");
//                break;
//            case GSConstAddress.ABATYPE_BRANCH :
//                //支店
//                companyBaseName = gsMsg.getMessage(req, "address.123");
//                break;
//            case GSConstAddress.ABATYPE_BUSINESSOFFICE :
//                //営業所
//                companyBaseName = gsMsg.getMessage(req, "address.124");
//                break;
//            default :
//                break;
//        }
//
//        return companyBaseName;
//    }


    /**
     * <br>[機  能] 会社拠点種別名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param companyBaseType 会社拠点種別
     * @param req リクエスト
     * @return 会社拠点種別名称
     */
    public static String getCompanyBaseTypeName(int companyBaseType, RequestModel req) {
        String companyBaseName = null;
        GsMessage gsMsg = new GsMessage(req);
        switch (companyBaseType) {
            case GSConstAddress.ABATYPE_HEADOFFICE :
                //本社
                companyBaseName = gsMsg.getMessage("address.122");
                break;
            case GSConstAddress.ABATYPE_BRANCH :
                //支店
                companyBaseName = gsMsg.getMessage("address.123");
                break;
            case GSConstAddress.ABATYPE_BUSINESSOFFICE :
                //営業所
                companyBaseName = gsMsg.getMessage("address.124");
                break;
            default :
                break;
        }

        return companyBaseName;
    }

    /**
     * <br>[機  能] グループコンボに設定する状報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param defGroup "選択してください"をコンボに含めるか
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabelList(Connection con, boolean defGroup)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (defGroup) {
            labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        }

        //グループリスト取得
//        GroupDao dao = new GroupDao(con);
//        ArrayList<GroupModel> gpList = dao.getGroupTree();
        GroupBiz groupBiz = new GroupBiz();
        ArrayList<GroupModel> gpList = groupBiz.getGroupCombList(con);

        GroupModel gpMdl = null;
        for (int i = 0; i < gpList.size(); i++) {
            gpMdl = gpList.get(i);
            labelList.add(
                    new LabelValueBean(gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] グループコンボに設定する状報を取得する
     * <br>[解  説] コンタクト履歴検索画面で使用
     * <br>[備  考]
     * @param con コネクション
     * @param defGroup "選択してください"をコンボに含めるか
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabelListContact(Connection con, boolean defGroup)
        throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl_);

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.specified.no"), "100"));

        //グループリスト取得
//        GroupDao dao = new GroupDao(con);
//        ArrayList<GroupModel> gpList = dao.getGroupTree();
        GroupBiz groupBiz = new GroupBiz();
        ArrayList<GroupModel> gpList = groupBiz.getGroupCombList(con);

        GroupModel gpMdl = null;
        for (int i = 0; i < gpList.size(); i++) {
            gpMdl = gpList.get(i);
            labelList.add(
                    new LabelValueBean(gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 業種コンボに設定する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGyosyuLabelList(Connection con)
        throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl_);

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        List<AdrTypeindustryModel> industryList = industryDao.select();
        for (AdrTypeindustryModel industryModel : industryList) {
            labelList.add(new LabelValueBean(industryModel.getAtiName(),
                                            String.valueOf(industryModel.getAtiSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 業種コンボに設定する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGyosyuLabelList(Connection con,
                                                        RequestModel reqMdl)
        throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        List<AdrTypeindustryModel> industryList = industryDao.select();
        for (AdrTypeindustryModel industryModel : industryList) {
            labelList.add(new LabelValueBean(industryModel.getAtiName(),
                                            String.valueOf(industryModel.getAtiSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 業種コンボに設定する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param defFlg 1項目目に「選択してください」を設定するか true:設定する false:設定しない
     * @return 業種コンボ
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGyosyuLabelList(Connection con, boolean defFlg)
        throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl_);

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (defFlg) {
            labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        }
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        List<AdrTypeindustryModel> industryList = industryDao.select();
        for (AdrTypeindustryModel industryModel : industryList) {
            labelList.add(new LabelValueBean(industryModel.getAtiName(),
                                            String.valueOf(industryModel.getAtiSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 選択用グループコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param allGroupCombo ユーザコンボ
     * @param selectGroupSid 選択済みユーザSID
     * @return 選択用グループコンボ index=0:選択済 index=1:未選択
     * @throws SQLException SQL実行時例外
     */
    public List<List<LabelValueBean>> getGroupCombo(Connection con,
                                                    List<LabelValueBean> allGroupCombo,
                                                    String[] selectGroupSid)
    throws SQLException {

        List<String> selectGroupList = new ArrayList<String>();
        if (selectGroupSid != null) {
            selectGroupList = Arrays.asList(selectGroupSid);
        }

        //選択済みグループ、未選択グループのコンボ情報を作成する
        List<LabelValueBean> selectGroupCombo = new ArrayList<LabelValueBean>();
        List<LabelValueBean> noSelectGroupCombo = new ArrayList<LabelValueBean>();
        for (LabelValueBean groupMdl : allGroupCombo) {
            String grpSid = groupMdl.getValue();
            if (selectGroupList.contains(grpSid)) {
                selectGroupCombo.add(groupMdl);
            } else {
                noSelectGroupCombo.add(groupMdl);
            }
        }

        List<List<LabelValueBean>> groupComboList = new ArrayList<List<LabelValueBean>>();
        groupComboList.add(selectGroupCombo);
        groupComboList.add(noSelectGroupCombo);

        return groupComboList;
    }

    /**
     * <br>[機  能] 選択用ユーザコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param allUserCombo ユーザコンボ
     * @param grpSid グループSID
     * @param selectUserSid 選択済みユーザSID
     * @return 選択用ユーザコンボ index=0:選択済 index=1:未選択
     * @throws SQLException SQL実行時例外
     */
    public List<List<UsrLabelValueBean>> getUserCombo(Connection con,
                                                    List<LabelValueBean> allUserCombo,
                                                    int grpSid,
                                                    String[] selectUserSid)
    throws SQLException {
        //選択済みユーザのコンボ情報を設定する
        UserGroupSelectBiz selBiz = new UserGroupSelectBiz();
        List<UsrLabelValueBean> selectUserCombo =
                selBiz.getSelectedLabel(selectUserSid, con);
        //未選択ユーザのコンボ情報を作成する
        List<UsrLabelValueBean> noSelectUserCombo =
                selBiz.getNonSelectLabel(grpSid, selectUserSid, con);


        List<List<UsrLabelValueBean>> userComboList = new ArrayList<List<UsrLabelValueBean>>();
        userComboList.add(selectUserCombo);
        userComboList.add(noSelectUserCombo);
        return userComboList;
    }
    /**
     * <br>[機  能] グループラベル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param grpSidList グループSID
     * @return グループ名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getGroupLabelList(Connection con, String[] grpSidList)
    throws SQLException {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (grpSidList != null && grpSidList.length > 0) {

            GroupDao grpDao = new GroupDao(con);
            int[] intGrpSidList = new int[grpSidList.length];
            int index = 0;
            for (String grpSid : grpSidList) {
                intGrpSidList[index] = Integer.parseInt(grpSid);
                index++;
            }
            List <CmnGroupmModel> grpDataList = grpDao.getGroups(intGrpSidList);
            for (CmnGroupmModel grpData : grpDataList) {
                labelList.add(new LabelValueBean(grpData.getGrpName(),
                        String.valueOf(grpData.getGrpSid())));
            }
        }

        return labelList;

    }

    /**
     * <br>[機  能] グループ名称一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param grpSidList グループSID
     * @return グループ名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getGroupNameList(Connection con, String[] grpSidList)
    throws SQLException {

        List<String> grpNameList = new ArrayList<String>();

        if (grpSidList != null && grpSidList.length > 0) {

            GroupDao grpDao = new GroupDao(con);
            int[] intGrpSidList = new int[grpSidList.length];
            int index = 0;
            for (String grpSid : grpSidList) {
                intGrpSidList[index] = Integer.parseInt(grpSid);
                index++;
            }
            List <CmnGroupmModel> grpDataList = grpDao.getGroups(intGrpSidList);
            for (CmnGroupmModel grpData : grpDataList) {
                grpNameList.add(grpData.getGrpName());
            }
        }

        return grpNameList;
    }

    /**
     * <br>[機  能] ユーザ名称一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSidList ユーザSID
     * @return ユーザ名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getUserNameList(Connection con, String[] usrSidList)
    throws SQLException {

        List<String> userNameList = new ArrayList<String>();

        if (usrSidList != null && usrSidList.length > 0) {

            UserBiz userBiz = new UserBiz();
            List<UsrLabelValueBean> userLabelList = userBiz.getUserLabelList(con, usrSidList);
            for (LabelValueBean userLabel : userLabelList) {
                userNameList.add(userLabel.getLabel());
            }
        }

        return userNameList;
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付ファイル情報
     * @throws Exception 実行時例外
     */
    public List<Cmn110FileModel> getFileData(String tempDir) throws Exception {

        List<Cmn110FileModel> fileDataList = new ArrayList<Cmn110FileModel>();
        List<String> fileNameList = IOTools.getFileNames(tempDir);

        if (fileNameList != null) {
            for (String fileName : fileNameList) {
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                //表示用リストへ追加
                fileDataList.add((Cmn110FileModel) fObj);
            }
        }

        return fileDataList;
    }

    /**
     * <br>[機  能] ファイルコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return ファイルコンボ
     * @throws Exception 実行時例外
     */
    public List<LabelValueBean> getFileCombo(String tempDir) throws Exception {

        ArrayList<LabelValueBean> fileCombo = new ArrayList<LabelValueBean>();
        List<Cmn110FileModel> fileDataList = getFileData(tempDir);
        if (fileDataList != null && !fileDataList.isEmpty()) {
            for (Cmn110FileModel fileData : fileDataList) {
                fileCombo.add(new LabelValueBean(fileData.getFileName(),
                                                fileData.getSaveFileName()));
            }
        }

        return fileCombo;
    }
    /**
     *
     * <br>[機  能] アドレス登録者に含まれているかを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return 判定結果
     * @throws SQLException SQL実行時例外
     */
    public boolean isHaveArest(Connection con, int userSid)
            throws SQLException {
        AdrAconfDao aconfDao = new AdrAconfDao(con);
        AdrAconfModel aconf = aconfDao.selectAconf(true);
        if (aconf.getAacArestKbn() == GSConstAddress.ARESTKBN_NONE) {
            return true;
        }
        AdrArestDao arestDao = new AdrArestDao(con);
        return arestDao.isBelongArest(userSid);
    }
    /**
    *
    * <br>[機  能] アドレス登録権限があるかを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param userSid ユーザSID
    * @return 判定結果
    * @throws SQLException SQL実行時例外
    */
    public boolean isAbleAdd(Connection con, int userSid)
            throws SQLException {
        if (isHaveArest(con, userSid)) {
            return true;
        }
        if (isAdmin(con, userSid)) {
            return true;
        }
        return false;
    }

    /**
     *
     * <br>[機  能] 管理者に含まれているかを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return 判定結果
     * @throws SQLException SQL実行時例外
     */
    public boolean isAdmin(Connection con, int userSid)
            throws SQLException {
        BaseUserModel usModel = reqMdl_.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adrAdminFlg
                = cmnBiz.isPluginAdmin(con, usModel, GSConstAddress.PLUGIN_ID_ADDRESS);

        return adrAdminFlg;
    }
    /**
     * <br>[機  能] 指定したアドレス帳が閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adrSid アドレス帳SID
     * @param userSid ユーザSID
     * @return 判定結果 true:編集可能 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isViewAddressData(Connection con, int adrSid, int userSid)
        throws SQLException {
        if (isAdmin(con, userSid)) {
            return true;
        }
        AddressDao addressDao = new AddressDao(con);
        return addressDao.isViewAddressData(adrSid, userSid);
    }


    /**
     * <br>[機  能] 指定したアドレス帳が編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adrSid アドレス帳SID
     * @param userSid ユーザSID
     * @return 判定結果 true:編集可能 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditAddressData(Connection con, int adrSid, int userSid)
        throws SQLException {
        return isEditAddressData(con, adrSid, userSid,
                isHaveArest(con, userSid),
                isAdmin(con, userSid));
    }
    /**
     * <br>[機  能] 指定したアドレス帳が編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adrSid アドレス帳SID
     * @param userSid ユーザSID
     * @param adrAdmin 管理者権限
     * @param arest 管理者設定 登録権限
     * @return 判定結果 true:編集可能 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditAddressData(Connection con, int adrSid,
            int userSid, boolean arest, boolean adrAdmin)
    throws SQLException {
        AddressDao addressDao = new AddressDao(con);
        //システム管理者及びプラグイン管理者はアドレス編集可能
        if (adrAdmin) {
            return true;
        }
        boolean editFlg = addressDao.isEditAddressData(adrSid, userSid);

        return (editFlg && arest);
    }

    /**
     * <br>[機  能] コンタクト履歴の添付ファイルがダウンロード可能か判定する
     * <br>[解  説]
     * <br>[備  考] コンタクト履歴画面に遷移する事が出来るユーザのみダウンロード可能とする。
     * @param con コネクション
     * @param adrSid アドレス帳SID
     * @param adcSid コンタクト履歴SID
     * @param userSid ユーザSID
     * @param binSid バイナリSID
     * @return 判定結果 true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isDownloadContactTmp(
            Connection con, int adrSid, int adcSid, int userSid, Long binSid)
            throws SQLException {

        AdrContactBinDao dao = new AdrContactBinDao(con);

        if (isCheckAcsessContact(con, adrSid, adcSid, userSid)
                && dao.isCheckContactFile(adcSid, binSid)) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] コンタクト履歴の添付ファイルがダウンロード可能か判定する
     * <br>[解  説]
     * <br>[備  考] コンタクト履歴画面に遷移する事が出来るユーザのみダウンロード可能とする。
     * @param con コネクション
     * @param adrSid アドレス帳SID
     * @param adcSid コンタクト履歴SID
     * @param userSid ユーザSID
     * @return 判定結果 true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckAcsessContact(Connection con, int adrSid, int adcSid, int userSid)
            throws SQLException {
        //アドレス帳情報取得
        AdrAddressDao addressDao = new AdrAddressDao(con);
        AdrAddressModel addressMdl = addressDao.select(adrSid);

        //アドレス帳情報なし
        if (addressMdl == null) {
            return false;
        }

        //閲覧権限=担当者のみ
        AddressBiz adrBiz = new AddressBiz(reqMdl_);
        if (addressMdl.getAdrPermitView() == 0
                && adrBiz.isCheckTanto(con, adrSid, userSid)) {
            return true;
        }

        //閲覧権限=グループ指定
        if (addressMdl.getAdrPermitView() == 1) {
            //閲覧権限情報チェック
            AdrPermitViewDao apvDao = new AdrPermitViewDao(con);
            int count = apvDao.checkPowGrp(adrSid, userSid);
            if (count > 0) {
                return true;
            }
        }

        //閲覧権限=ユーザ指定
        if (addressMdl.getAdrPermitView() == 2) {
            //閲覧権限情報チェック
            AdrPermitViewDao apvDao = new AdrPermitViewDao(con);
            int count = apvDao.checkPowUsr(adrSid, userSid);
            if (count > 0) {
                return true;
            }
        }

        //閲覧権限=設定なし
        if (addressMdl.getAdrPermitView() == 3) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 指定したユーザがアドレス帳の担当者かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adrSid アドレスSID
     * @param usrSid ユーザSID
     * @return true:担当者  false:担当外
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckTanto(Connection con, int adrSid, int usrSid) throws SQLException {
        AdrPersonchargeDao dao = new AdrPersonchargeDao(con);
        return dao.isCheckTanto(adrSid, usrSid);
    }
}
