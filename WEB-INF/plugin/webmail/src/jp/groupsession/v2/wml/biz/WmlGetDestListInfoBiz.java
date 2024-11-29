package jp.groupsession.v2.wml.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.MailBiz;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.wml.dao.WmlGetAddressBookDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAccessConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAddressDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistDao;
import jp.groupsession.v2.wml.model.WmlAddressBookModel;
import jp.groupsession.v2.wml.model.WmlAddressParamModel;
import jp.groupsession.v2.wml.model.WmlDestListInfoModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistAccessConfModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistAddressModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;

/**
 * <br>[機  能] 送信先リストを取得するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlGetDestListInfoBiz {

    /**
     * <br>[機  能] 送信先リスト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdlSid 送信先リストSID
     * @param wdlInfMdl WmlDestListInfoModel
     * @throws SQLException SQL実行時例外
     */
    public void getDestlistInfo(
        Connection con,
        int wdlSid,
        WmlDestListInfoModel wdlInfMdl) throws SQLException {

        //送信先リスト情報を設定する
        WmlDestlistDao destlistDao = new WmlDestlistDao(con);
        WmlDestlistModel destlistMdl = destlistDao.select(wdlSid);

        wdlInfMdl.setDestListName(destlistMdl.getWdlName());
        wdlInfMdl.setDestListBiko(destlistMdl.getWdlBiko());

        //送信先を設定する
        WmlDestlistAddressDao destlistAddressDao = new WmlDestlistAddressDao(con);
        List<WmlDestlistAddressModel> destUserList
            = destlistAddressDao.getDestlistAddress(wdlSid,
                                                        GSConstWebmail.WDA_TYPE_USER);
        wdlInfMdl.setDestUsers(__createDestParamArray(destUserList));
        List<WmlDestlistAddressModel> destAddressList
            = destlistAddressDao.getDestlistAddress(wdlSid,
                                                        GSConstWebmail.WDA_TYPE_ADDRESS);
        wdlInfMdl.setDestAddresses(__createDestParamArray(destAddressList));

        //アクセス権限を設定する
        WmlDestlistAccessConfDao destlistAccessConfDao = new WmlDestlistAccessConfDao(con);
        List<WmlDestlistAccessConfModel> destlistFullAccessList
            = destlistAccessConfDao.getAccessConfList(wdlSid, GSConstWebmail.WLA_AUTH_ALL);
        wdlInfMdl.setAccessFullUsers(__createAccessUserArray(destlistFullAccessList));

        List<WmlDestlistAccessConfModel> destlistReadAccessList
            = destlistAccessConfDao.getAccessConfList(wdlSid, GSConstWebmail.WLA_AUTH_READ);
        wdlInfMdl.setAccessReadUsers(__createAccessUserArray(destlistReadAccessList));
    }

    /**
     * <br>[機  能] 送信先リスト 送信先情報から送信先IDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param type 種別
     * @param sid SID(ユーザSID or アドレス帳SID)
     * @param mailNo メール番号
     * @return 送信先ID
     */
    public String createDestAddressId(int type, int sid, int mailNo) {
        String destAddressId = type + "-" + sid;
        if (type == GSConstWebmail.WDA_TYPE_USER) {
            destAddressId += "-" + mailNo;
        } else if (type == GSConstWebmail.WDA_TYPE_ADDRESS) {
            destAddressId += "_" + mailNo;
        }

        return destAddressId;
    }

    /**
     * <br>[機  能] 取得した送信先情報をパラメータに変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param addressList 送信先
     * @return 送信先(ユーザ情報 or アドレス帳)パラメータ
     */
    private String[] __createDestParamArray(List<WmlDestlistAddressModel> addressList) {
        String[] userArray = new String[addressList.size()];
        WmlDestlistAddressModel addressData = null;
        for (int idx = 0; idx < addressList.size(); idx++) {
            addressData = addressList.get(idx);
            userArray[idx] = createDestAddressId(addressData.getWdaType(),
                                                                    addressData.getWdaSid(),
                                                                    addressData.getWdaAdrno());
        }

        return userArray;
    }

    /**
     * <br>[機  能] 取得したアクセス設定からパラメータ用ユーザ一覧を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param accessUserList アクセス設定
     * @return パラメータ用ユーザ一覧
     */
    private String[] __createAccessUserArray(List<WmlDestlistAccessConfModel> accessUserList) {
        String[] userArray = new String[accessUserList.size()];
        WmlDestlistAccessConfModel accessUser = null;
        for (int idx = 0; idx < accessUserList.size(); idx++) {
            accessUser = accessUserList.get(idx);
            if (accessUser.getWlaKbn() == GSConstWebmail.WLA_KBN_GROUP) {
                userArray[idx] = "G" + accessUser.getWlaUsrSid();
            } else {
                userArray[idx] = String.valueOf(accessUser.getWlaUsrSid());
            }
        }

        return userArray;
    }

    /**
     * <br>[機  能] 送信先パラメータ情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdlInfMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setDestListParam(Connection con, WmlDestListInfoModel wdlInfMdl)
            throws SQLException {

        //ユーザ情報
        CmnUsrmInfDao userInfDao = new CmnUsrmInfDao(con);
        List<CmnUsrmInfModel> userDataList = null;

        StringTokenizer st = null;

        List<WmlAddressParamModel> addressParamList = new ArrayList<WmlAddressParamModel>();
        List<String> destIdList = new ArrayList<String>();
        List<WmlAddressParamModel> userList = new ArrayList<WmlAddressParamModel>();

        String mailAddress = null;
        for (String destId : wdlInfMdl.getDestUsers()) {
            //重複チェック
            if (destIdList.indexOf(destId) >= 0) {
                continue;
            }
            WmlAddressParamModel addressModel = new WmlAddressParamModel();
            addressModel.setDestId(destId);
            destIdList.add(destId);

            st = new StringTokenizer(destId, "-");
            addressModel.setType(Integer.parseInt(st.nextToken()));
            addressModel.setSid(Integer.parseInt(st.nextToken()));
            addressModel.setMailNo(Integer.parseInt(st.nextToken()));
            String [] usids = new String[] {String.valueOf(addressModel.getSid())};
            userDataList = userInfDao.getUsersInfList(usids);
            if (userDataList.isEmpty()) {
                continue;
            }
            CmnUsrmInfModel  userData = userDataList.get(0);
            addressModel.setName(userData.getUsiSei() + " " + userData.getUsiMei());
            addressModel.setSei(userData.getUsiSei());
            addressModel.setMei(userData.getUsiMei());
            addressModel.setSeiKn(userData.getUsiSeiKn());
            addressModel.setMeiKn(userData.getUsiMeiKn());

            switch (addressModel.getMailNo()) {
            case 1:
                addressModel.setMailAddressText(userData.getUsiMail1());
                break;
            case 2:
                addressModel.setMailAddressText(userData.getUsiMail2());
                break;
            case 3:
                addressModel.setMailAddressText(userData.getUsiMail3());
                break;
            default:
            }

            if (!StringUtil.isNullZeroString(addressModel.getMailAddressText())) {
                mailAddress = " <" + addressModel.getMailAddressText() + ">";
                addressModel.setMailAddress(addressModel.getName()
                        + mailAddress);
                addressModel.setSendMailAddress(
                        MailBiz.formatPersonal(addressModel.getName())
                        + mailAddress);
            }

            addressModel.setUsrUkoFlg(userData.getUsrUkoFlg());

            //役職情報を設定
            if (!userData.getUsiYakusyoku().isEmpty()) {
                addressModel.setYakusyokuFlg(WmlAddressParamModel.YAKUSYOKU_EXIST);
                addressModel.setYakusyoku(userData.getUsiYakusyoku());
                CmnPositionDao dao = new CmnPositionDao(con);
                CmnPositionModel mdl = dao.getPosInfo(userData.getUsiYakusyoku());
                addressModel.setYakusyokuSid(mdl.getPosSid());
                addressModel.setYakusyokuId(mdl.getPosCode());
                addressModel.setYakusyokuSort(mdl.getPosSort());
            } else {
                addressModel.setYakusyokuFlg(WmlAddressParamModel.YAKUSYOKU_NOT_EXIST);
            }

            userList.add(addressModel);
            
            //役職降順>名前昇順でソート
            userList.sort(Comparator.comparing(WmlAddressParamModel::getYakusyokuFlg)
                .thenComparing(WmlAddressParamModel::getYakusyokuSort)
                .thenComparing(WmlAddressParamModel::getSeiKn)
                .thenComparing(WmlAddressParamModel::getMeiKn));
        }

        //アドレス帳
        WmlGetAddressBookDao adrDao = new WmlGetAddressBookDao(con);
        List<WmlAddressParamModel> addressList = new ArrayList<WmlAddressParamModel>();
        for (String destId : wdlInfMdl.getDestAddresses()) {
            //重複チェック
            if (destIdList.indexOf(destId) >= 0) {
                continue;
            }

            WmlAddressParamModel addressModel = new WmlAddressParamModel();
            addressModel.setDestId(destId);
            destIdList.add(destId);

            addressModel.setType(Integer.parseInt(destId.substring(0, destId.indexOf("-"))));
            destId = destId.substring(destId.indexOf("-") + 1);
            if (destId.indexOf("_") > 0) {
                addressModel.setSid(Integer.parseInt(destId.substring(0, destId.indexOf("_"))));
                addressModel.setMailNo(Integer.parseInt(destId.substring(destId.indexOf("_") + 1)));
            } else {
                addressModel.setSid(Integer.parseInt(destId));
                addressModel.setMailNo(1);
            }
            WmlAddressBookModel addressBookData = adrDao.getAddressBookData(addressModel.getSid());
            if (addressBookData != null) {
                addressModel.setName(addressBookData.getAdrSei()
                        + " " + addressBookData.getAdrMei());
                addressModel.setSei(addressBookData.getAdrSei());
                addressModel.setMei(addressBookData.getAdrMei());
                addressModel.setSeiKn(addressBookData.getAdrSeiKn());
                addressModel.setMeiKn(addressBookData.getAdrMeiKn());

                switch (addressModel.getMailNo()) {
                case 1:
                    addressModel.setMailAddressText(addressBookData.getAdrMail1());
                    break;
                case 2:
                    addressModel.setMailAddressText(addressBookData.getAdrMail2());
                    break;
                case 3:
                    addressModel.setMailAddressText(addressBookData.getAdrMail3());
                    break;
                default:
                }
            }

            mailAddress = null;
            if (!StringUtil.isNullZeroString(addressModel.getMailAddressText())) {
                mailAddress = " <" + addressModel.getMailAddressText() + ">";
                addressModel.setMailAddress(addressModel.getName()
                        + mailAddress);
                addressModel.setSendMailAddress(
                        MailBiz.formatPersonal(addressModel.getName())
                        + mailAddress);
            }

            
            //役職情報を設定
            if (addressBookData.getYakusyoku() != null) {
                addressModel.setYakusyokuFlg(WmlAddressParamModel.YAKUSYOKU_EXIST);
                addressModel.setYakusyokuSid(addressBookData.getYakusyokuSid());
                addressModel.setYakusyoku(addressBookData.getYakusyoku());
                addressModel.setYakusyokuSort(addressBookData.getYakusyokuSort());
            } else {
                addressModel.setYakusyokuFlg(WmlAddressParamModel.YAKUSYOKU_NOT_EXIST);
                addressModel.setYakusyokuSid(-1);
            }
            
            //会社情報を設定
            if (addressBookData.getAcoId() != null) {
                addressModel.setAcoFlg(WmlAddressParamModel.COMPANY_EXIST);
                addressModel.setAcoId(addressBookData.getAcoId());
                addressModel.setAcoName(addressBookData.getAcoName());
                addressModel.setAcoNameKn(addressBookData.getAcoNameKn());
                if (addressBookData.getAbaSid() > 0) {
                    addressModel.setAbaName(addressBookData.getAbaName());
                    addressModel.setAbaType(addressBookData.getAbaType());
                    addressModel.setAbaId(addressBookData.getAcoId() + ":" + addressBookData.getAbaSid());
                }
            } else {
                addressModel.setAcoFlg(WmlAddressParamModel.COMPANY_NOT_EXIST);
                addressModel.setAcoNameKn("");
                addressModel.setYakusyokuSort(0);
            }

            addressList.add(addressModel);

            //会社名昇順>役職降順>名前昇順でソート
            addressList.sort(Comparator.comparing(WmlAddressParamModel::getAcoFlg)
                .thenComparing(WmlAddressParamModel::getAcoNameKn)
                .thenComparing(WmlAddressParamModel::getYakusyokuFlg)
                .thenComparing(WmlAddressParamModel::getYakusyokuSort)
                .thenComparing(WmlAddressParamModel::getSeiKn)
                .thenComparing(WmlAddressParamModel::getMeiKn));
        }

        addressParamList.addAll(userList);
        addressParamList.addAll(addressList);
        wdlInfMdl.setDestUserList(addressParamList);
    }
}
