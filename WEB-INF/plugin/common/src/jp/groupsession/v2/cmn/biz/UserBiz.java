package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupDao;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.BelongUserSearchModel;
import jp.groupsession.v2.cmn.model.PrjMemberEditModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupMsModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.usr.usr040.ShainSearchModel;


/**
 * <br>[機  能] ユーザ関連で使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserBiz {

    /**
     * <br>[機  能] 表示用のユーザモデルリスト一覧を取得する
     * <br>[解  説] 指定したユーザの表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     *
     * @param con コネクション
     * @param userSidList ユーザModelリスト一覧
     * @param userKeyMap ユーザKeyマップ
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List<PrjMemberEditModel> getUserModelList(Connection con,
                                                      String[] userSidList,
                                                      HashMap<String, String> userKeyMap)
        throws SQLException {

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        List<PrjMemberEditModel> settledUserList =
            usiDao.selectPrjMemberModel(userSidList, userKeyMap, sortMdl);

        return settledUserList;
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] 指定したユーザの表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID一覧
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList < UsrLabelValueBean > getUserLabelList(Connection con, String[] userSidList)
    throws SQLException {

        List<CmnUsrmInfModel> settledUserList = getUserList(con, userSidList);
        ArrayList<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();

        for (CmnUsrmInfModel usrMdl : settledUserList) {
            userLabelList.add(new UsrLabelValueBean(usrMdl));
        }

        return userLabelList;
    }

    /**
     * <br>[機  能] ユーザ情報一覧を取得する
     * <br>[解  説] 指定したユーザのユーザ情報一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID一覧
     * @return ユーザ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<CmnUsrmInfModel> getUserList(Connection con, List<Integer> userSidList)
    throws SQLException {
        String[] strUserSidList = null;
        if (userSidList != null) {
            strUserSidList = new String[userSidList.size()];
            int index = 0;
            for (int userSid : userSidList) {
                strUserSidList[index] = String.valueOf(userSid);
                index++;
            }
        }

        return getUserList(con, strUserSidList);
    }

    /**
     * <br>[機  能] ユーザ情報一覧を取得する
     * <br>[解  説] 指定したユーザのユーザ情報一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID一覧
     * @return ユーザ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<CmnUsrmInfModel> getUserList(Connection con, String[] userSidList)
    throws SQLException {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        ArrayList <CmnUsrmInfModel> userDataList = usiDao.getUsersInfList(userSidList, sortMdl);

        return userDataList;
    }
    /**
     * <br>[機  能] ユーザ情報一覧を取得する
     * <br>[解  説] 指定したユーザのユーザ情報一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID一覧
     * @param jKbn 削除区分
     * @return ユーザ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<CmnUsrmInfModel> getUserList(Connection con, String[] userSidList, int jKbn)
    throws SQLException {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        ArrayList <CmnUsrmInfModel> userDataList =
                usiDao.getUsersInfList(userSidList, sortMdl, jKbn);

        return userDataList;
    }

    /**
     * <br>[機  能] ユーザ情報一覧を取得する
     * <br>[解  説] 指定したユーザのユーザ情報一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID一覧
     * @return ユーザ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<BaseUserModel> getBaseUserList(Connection con, String[] userSidList)
    throws SQLException {

        List<CmnUsrmInfModel> userList = getUserList(con, userSidList);

        ArrayList<BaseUserModel> baseUserList = new ArrayList<BaseUserModel>();
        for (CmnUsrmInfModel usrMdl : userList) {
            BaseUserModel admUserMdl = new BaseUserModel();
            admUserMdl.setUsrsid(usrMdl.getUsrSid());
            admUserMdl.setUsisei(usrMdl.getUsiSei());
            admUserMdl.setUsimei(usrMdl.getUsiMei());
            baseUserList.add(admUserMdl);
        }

        return baseUserList;
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] 指定したユーザの表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID一覧
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getUserPrjLabelList(Connection con, String[] userSidList)
    throws SQLException {

        List<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();

        String[] spUsrSidList = null;
        HashMap<String, String> spUsrKeyMap = null;

        if (userSidList != null && userSidList.length > 0) {

            int idx = 0;
            spUsrSidList = new String[userSidList.length];
            spUsrKeyMap = new HashMap<String, String>();

            for (String hdn : userSidList) {

                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                spUsrSidList[idx] = String.valueOf(splitStr[0]);

                if (splitStr.length > 1) {
                    spUsrKeyMap.put(spUsrSidList[idx], splitStr[1]);
                } else {
                    spUsrKeyMap.put(spUsrSidList[idx], "");
                }
                idx += 1;
            }
        }

        List<PrjMemberEditModel> ret =
            getUserModelList(con, spUsrSidList, spUsrKeyMap);

        StringBuilder fullName = null;
        for (PrjMemberEditModel usrMdl : ret) {
            fullName = new StringBuilder("");
            fullName.append(usrMdl.getUsiSei());
            fullName.append(" ");
            fullName.append(usrMdl.getUsiMei());
            userLabelList.add(
                    new UsrLabelValueBean(
                            fullName.toString(),
                            String.valueOf(usrMdl.getUsrSid())
                            + GSConst.GSESSION2_ID
                            + NullDefault.getString(usrMdl.getMemberKey(), ""),
                            usrMdl.getUsrUkoFlg()
                            ));
        }

        return userLabelList;
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSidList ユーザSID一覧
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List < UsrLabelValueBean > getUserLabelList(Connection con,
                                                    int groupSid,
                                                    String[] userSidList)
    throws SQLException {
        List<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();

        List <SltUserPerGroupModel> addUserList
            = getUserPerGroupList(con, groupSid, userSidList, false);

        for (SltUserPerGroupModel usrMdl : addUserList) {
            UsrLabelValueBean label = new UsrLabelValueBean(usrMdl.getFullName(),
                    String.valueOf(usrMdl.getUsrsid()));
            label.setUsrUkoFlg(usrMdl.getUsrUkoFlg());
            userLabelList.add(label);
        }

        return userLabelList;
    }

    /**
     * <br>[機  能] グループに所属するユーザのうち、指定されたユーザのみ
     *              もしくは指定されたユーザを除いたユーザ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSidList ユーザSID一覧
     * @param userFlg true:指定されたユーザのみ、false:指定されたユーザを除く
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List <SltUserPerGroupModel> getUserPerGroupList(Connection con,
                                                            int groupSid,
                                                            String[] userSidList,
                                                            boolean userFlg)
    throws SQLException {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        SltUserPerGroupDao sltDao = new SltUserPerGroupDao(con);
        List <SltUserPerGroupModel> userList
            = sltDao.selectGroupList(groupSid, userSidList, userFlg, sortMdl);

        return userList;
    }

    /**
     * <br>[機  能] グループに所属するユーザ情報一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSidList 除外するユーザSID一覧
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(Connection con,
                                                        int groupSid,
                                                        List<Integer> userSidList)
    throws SQLException {

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        int sortKey1 = getSortKey(sortMdl.getCscUserSkey1());
        int order1 = sortMdl.getCscUserOrder1();
        int sortKey2 = getSortKey(sortMdl.getCscUserSkey2());
        int order2 = sortMdl.getCscUserOrder2();

        UserSearchDao searchDao = new UserSearchDao(con);
        return searchDao.getBelongUserList(groupSid, (ArrayList<Integer>) userSidList,
                                        false, sortKey1, order1, sortKey2, order2);
    }

    /**
     * <br>[機  能] グループに所属するユーザ情報一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSidList 除外するユーザSID一覧
     * @param admFlg 管理者権限フラグ
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(Connection con,
                                                        int groupSid,
                                                        List<Integer> userSidList,
                                                        boolean admFlg)
    throws SQLException {

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        int sortKey1 = getSortKey(sortMdl.getCscUserSkey1());
        int order1 = sortMdl.getCscUserOrder1();
        int sortKey2 = getSortKey(sortMdl.getCscUserSkey2());
        int order2 = sortMdl.getCscUserOrder2();

        UserSearchDao searchDao = new UserSearchDao(con);
        if (admFlg) {
            return searchDao.getBelongUserList(groupSid, (ArrayList<Integer>) userSidList,
                    false, sortKey1, order1, sortKey2, order2);
        }
        return searchDao.getBelongUserList(groupSid, (ArrayList<Integer>) userSidList,
                true, sortKey1, order1, sortKey2, order2);
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param groupSid グループSID
     * @param userSidList 除外するユーザSID一覧
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List < UsrLabelValueBean > getMyGroupUserLabelList(
        Connection con,
        int userSid,
        int groupSid,
        String[] userSidList) throws SQLException {
        return getMyGroupUserLabelList(con, userSid, groupSid, userSidList, -1, -1);
    }
    
    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param groupSid グループSID
     * @param userSidList 除外するユーザSID一覧
     * @param limit リミット
     * @param offset オフセット
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List < UsrLabelValueBean > getMyGroupUserLabelList(
        Connection con,
        int userSid,
        int groupSid,
        String[] userSidList,
        int limit,
        int offset) throws SQLException {

        List<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
        List<CmnMyGroupMsModel> cmgmList = cmgmDao.getMyGroupMsInfo(userSid,
                groupSid,
                userSidList,
                false,
                limit,
                offset,
                sortMdl);

        String[] users = new String[cmgmList.size()];
        for (int i = 0; i < cmgmList.size(); i++) {
            CmnMyGroupMsModel cmgmMdl = cmgmList.get(i);
            users[i] = String.valueOf(cmgmMdl.getMgmSid());
        }

        CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
        List<CmnUsrmInfModel> cuiList = cuiDao.getUsersInfList(users, sortMdl);

        for (CmnUsrmInfModel cuiMdl : cuiList) {
            userLabelList.add(new UsrLabelValueBean(cuiMdl));
        }

        return userLabelList;
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、システムユーザ or 指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSidList ユーザSID一覧
     * @param defFlg 1項目目に「選択してください」を設定するか true:設定する false:設定しない
     * @param gsMsg GSメッセージ
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List <UsrLabelValueBean> getNormalUserLabelList(
        Connection con,
        int groupSid,
        String[] userSidList,
        boolean defFlg,
        GsMessage gsMsg) throws SQLException {
            return getNormalUserLabelList(con, groupSid, userSidList, defFlg, gsMsg, -1, -1);
    }
    
    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、システムユーザ or 指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSidList ユーザSID一覧
     * @param defFlg 1項目目に「選択してください」を設定するか true:設定する false:設定しない
     * @param gsMsg GSメッセージ
     * @param limit リミット
     * @param offset オフセット
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List <UsrLabelValueBean> getNormalUserLabelList(Connection con,
                                                        int groupSid,
                                                        String[] userSidList,
                                                        boolean defFlg,
                                                        GsMessage gsMsg,
                                                        int limit,
                                                        int offset)
    throws SQLException {
        List<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();
        if (defFlg) {

            //選択してください
            String textSelect = gsMsg.getMessage("cmn.select.plz");

            userLabelList.add(0, new UsrLabelValueBean(textSelect, "-1"));
        }

        UserSearchDao usDao = new UserSearchDao(con);
        ArrayList<Integer> userList = new ArrayList<Integer>();
        if (userSidList != null) {
            for (String userSid : userSidList) {
                userList.add(new Integer(userSid));
            }
        }

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        ShainSearchModel searchMdl = new ShainSearchModel();
        searchMdl.setSortKey(getSortKey(sortMdl.getCscUserSkey1()));
        searchMdl.setSortOrder(sortMdl.getCscUserOrder1());
        searchMdl.setSortKey2(getSortKey(sortMdl.getCscUserSkey2()));
        searchMdl.setSortOrder2(sortMdl.getCscUserOrder2());

        List<CmnUsrmInfModel> addUserList
            = usDao.getBelongUserSearchList(groupSid, userList, false, searchMdl, offset + 1, limit);

        for (CmnUsrmInfModel usrMdl : addUserList) {
            userLabelList.add(
                    new UsrLabelValueBean(usrMdl));
        }
        return userLabelList;
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、システムユーザ or 指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @param groupSid グループSID
     * @param userSidList ユーザSID一覧
     * @param defFlg 1項目目に「選択してください」を設定するか true:設定する false:設定しない
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List <UsrLabelValueBean> getNormalAllUserLabelList(Connection con,
                                                        GsMessage gsMsg,
                                                        int groupSid,
                                                        String[] userSidList,
                                                        boolean defFlg)
    throws SQLException {
        List<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();
        if (defFlg) {

            //選択してください
            String textSelect = gsMsg.getMessage("cmn.select.plz");

            userLabelList.add(0, new UsrLabelValueBean(textSelect, "-1"));
        }

        UserSearchDao usDao = new UserSearchDao(con);
        ArrayList<Integer> userList = new ArrayList<Integer>();
        if (userSidList != null) {
            for (String userSid : userSidList) {
                userList.add(new Integer(userSid));
            }
        }

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        List<CmnUsrmInfModel> addUserList
            = usDao.getBelongAllUserList(groupSid, userList, false,
                                    getSortKey(sortMdl.getCscUserSkey1()),
                                    sortMdl.getCscUserOrder1(),
                                    getSortKey(sortMdl.getCscUserSkey2()),
                                    sortMdl.getCscUserOrder2());

        for (CmnUsrmInfModel usrMdl : addUserList) {
            userLabelList.add(
                    new UsrLabelValueBean(usrMdl));
        }

        return userLabelList;
    }

    /**
     * <br>[機  能] コンボボックスソート設定のソートキーに対応するユーザソートキーを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmbSortKey コンボボックスソート設定のソートキー
     * @return ユーザソートキー
     */
    public static int getSortKey(int cmbSortKey) {
        int sortKey = -1;
        switch (cmbSortKey) {
            case GSConst.USERCMB_SKEY_NAME:
                sortKey = GSConstUser.USER_SORT_NAME;
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sortKey = GSConstUser.USER_SORT_SNO;
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sortKey = GSConstUser.USER_SORT_YKSK;
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sortKey = GSConstUser.USER_SORT_BDATE;
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sortKey = GSConstUser.USER_SORT_SORTKEY1;
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sortKey = GSConstUser.USER_SORT_SORTKEY2;
                break;
            default:
                break;
        }

        return sortKey;
    }

    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説] 検索画面等で使用
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @param groupSid グループSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getUserLabelList(
            Connection con, GsMessage gsMsg, int groupSid)
    throws SQLException {

        //指定無し
        String textSiteiNasi = gsMsg.getMessage("cmn.without.specifying");

        List < UsrLabelValueBean > labelList = getUserLabelList(con, groupSid, null);
        labelList.add(0, new UsrLabelValueBean(textSiteiNasi, "-1"));
        return labelList;
    }

    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する(GS 管理者、システムメールを除く)
     * <br>[解  説] 検索画面等で使用
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @param groupSid グループSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getUserLabelListNoSysUser(
            Connection con, GsMessage gsMsg, int groupSid)
    throws SQLException {

        //指定無し
        String textSiteiNasi = gsMsg.getMessage("cmn.without.specifying");
        UsrLabelValueBean noUsrLabel = new UsrLabelValueBean(textSiteiNasi, "-1");

        List < UsrLabelValueBean > labelList;
        if (groupSid >= 0) {
            String[] execludeusid = new String[] {
                    Integer.toString(GSConst.SYSTEM_USER_ADMIN),
                    Integer.toString(GSConst.SYSTEM_USER_MAIL) };
            labelList = getUserLabelList(con, groupSid, execludeusid);
            labelList.add(0, noUsrLabel);
        } else {
            labelList = new ArrayList<UsrLabelValueBean>();
            labelList.add(noUsrLabel);
        }
        return labelList;
    }

    /**
     * <br>[機  能] 有効なユーザ数(削除されていない かつ システムユーザ以外)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 有効なユーザ数
     * @throws SQLException SQL実行時例外
     */
    public int getActiveUserCount(Connection con)
    throws SQLException {
        CmnUsrmDao dao = new CmnUsrmDao(con);
        return dao.getActiveUserCount();
    }

    /**
     * <br>[機  能] 指定したユーザSIDのうち、削除されていないものを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSids ユーザSID
     * @return ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public String[] getNormalUser(Connection con, String[] userSids)
    throws SQLException {
        CmnUsrmDao usrDao = new CmnUsrmDao(con);
        return usrDao.getNoDeleteUser(userSids);
    }

    /**
     * <p>マイグループ又はグループに所属するユーザ情報一覧を取得する。
     * @param con コネクション
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sessionUsrSid セッションユーザSID
     * @param myGroupFlg マイグループ選択フラグ
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(Connection con, int gpSid,
            ArrayList<Integer> usrSids, int sessionUsrSid, boolean myGroupFlg) throws SQLException {

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        int sortKey1 = UserBiz.getSortKey(sortMdl.getCscUserSkey1());
        int order1 = sortMdl.getCscUserOrder1();
        int sortKey2 = UserBiz.getSortKey(sortMdl.getCscUserSkey2());
        int order2 = sortMdl.getCscUserOrder2();

        UserSearchDao userSearchDao = new UserSearchDao(con);
        if (myGroupFlg) {
            BelongUserSearchModel model = new BelongUserSearchModel();
            model.setGpSid(gpSid);
            model.setSessionUsrSid(sessionUsrSid);
            model.setUsrSids(usrSids);
            model.setKfFlg(false);
            model.setSortKey(sortKey1);
            model.setOrderKey(order1);
            model.setSortKey2(sortKey2);
            model.setOrderKey2(order2);
            return userSearchDao.getMyGroupBelongUserList(model);
        } else {
            return userSearchDao.getBelongUserList(gpSid, usrSids, false,
                                    sortKey1, order1, sortKey2, order2);
        }
    }
    /**
     * <br>[機  能] ユーザ情報、アクセス権限の候補一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param left 除外するSID
     * @param groupSid グループコンボの選択値
     * @return 候補一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<UsrLabelValueBean> getRightLabel(
            Connection con, String[] left, int groupSid) throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
        if (groupSid == GSConst.GROUP_COMBO_VALUE) {
            //グループを全て取得
            GroupDao dao = new GroupDao(con);
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            ArrayList<GroupModel> allGpList = dao.getGroupTree(sortMdl);

            //選択済みのSIDを除外する
            List<String> excludeList = new ArrayList<String>();
            if (left != null && left.length > 0) {
                excludeList.addAll(Arrays.asList(left));
            }

            for (GroupModel bean : allGpList) {
                if (!excludeList.contains(String.valueOf("G" + bean.getGroupSid()))) {
                    ret.add(new UsrLabelValueBean(
                            bean.getGroupName(), String.valueOf("G" + bean.getGroupSid())));
                }
            }

        } else {

            //除外するユーザSID
            ArrayList<Integer> excludeList = new ArrayList<Integer>();
            if (left != null) {
                for (String usrSid : left) {
                    excludeList.add(new Integer(NullDefault.getInt(usrSid, -1)));
                }
            }
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> userList
                = userBiz.getBelongUserList(con, groupSid, excludeList);

            UsrLabelValueBean label = null;
            for (CmnUsrmInfModel userData : userList) {
                label = new UsrLabelValueBean();
                label.setLabel(userData.getUsiSei() + " " + userData.getUsiMei());
                label.setValue(String.valueOf(userData.getUsrSid()));
                label.setUsrUkoFlg(userData.getUsrUkoFlg());
                ret.add(label);
            }
        }

        return ret;
    }

}
