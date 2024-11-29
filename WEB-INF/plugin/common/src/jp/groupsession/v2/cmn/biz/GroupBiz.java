package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] グループ関連で使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GroupBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GroupBiz.class);

    /**
     * 表示グループ用のグループリストを取得する(全て)
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList <LabelValueBean> getGroupLabelList(Connection con, GsMessage gsMsg)
    throws SQLException {
        return getGroupLabelList(con, true, gsMsg);
    }

    /**
     * 表示グループ用のグループリストを取得する(全て)
     * @param con コネクション
     * @param defFlg true:「選択してください」を含める false:グループリストのみ
     * @param gsMsg GSメッセージ
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList < LabelValueBean > getGroupLabelList(
            Connection con, boolean defFlg, GsMessage gsMsg)
    throws SQLException {


        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (defFlg) {

            //選択してください
            String textSelect = gsMsg.getMessage("cmn.select.plz");

            labelList.add(new LabelValueBean(textSelect, String.valueOf(-1)));
        }

        //グループリスト取得
        ArrayList <GroupModel> gpList = getGroupList(con);

        GroupModel gpMdl = null;
        for (int i = 0; i < gpList.size(); i++) {
            gpMdl = gpList.get(i);
            labelList.add(
                    new LabelValueBean(gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
        }
        log__.debug("labelList.size()=>" + labelList.size());
        return labelList;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getGroupList(Connection con)
    throws SQLException {

        return getGroupList(con, -1);
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getGroupCombList(Connection con)
    throws SQLException {

        return getGroupCombList(con, -1);
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する
     * <br>[解  説] 指定したユーザが属するグループの一覧を取得する
     * <br>         ユーザSID < 0 の場合は全グループの一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getGroupList(Connection con, int userSid)
    throws SQLException {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> gpList = null;
        if (sortMdl.getCscGroupSkbn() == GSConst.GROUPCMB_SKBN_SET) {
            gpList = dao.getGroupTree(sortMdl);
        } else {
            gpList = dao.getGroupList(sortMdl);
        }

        if (userSid >= 0) {
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongList = belongDao.selectUserBelongGroupSid(userSid);
            ArrayList<GroupModel> groupList = new ArrayList<GroupModel>();

            for (GroupModel gpMdl : gpList) {
                if (belongList.indexOf(new Integer(gpMdl.getGroupSid())) >= 0) {
                    groupList.add(gpMdl);
                }

            }
            return groupList;
        }

        return gpList;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する(コンボボックス用)
     * <br>[解  説] 指定したユーザが属するグループの一覧を取得する
     * <br>         ユーザSID < 0 の場合は全グループの一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getGroupCombList(Connection con, int userSid)
    throws SQLException {

        int level = 1;
        String space = "";
        ArrayList<GroupModel> groupList = new ArrayList<GroupModel>();
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> gpList = null;
        if (sortMdl.getCscGroupSkbn() == GSConst.GROUPCMB_SKBN_SET) {
            gpList = dao.getGroupTree(sortMdl);
        } else {
            gpList = dao.getGroupList(sortMdl);
        }

        if (userSid >= 0) {
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongList = belongDao.selectUserBelongGroupSid(userSid);


            for (GroupModel gpMdl : gpList) {
                if (belongList.indexOf(new Integer(gpMdl.getGroupSid())) >= 0) {
                    level = gpMdl.getClassLevel();
                    String gpName = gpMdl.getGroupName();
                    space = getCombSpace(level);
                    gpMdl.setGroupName(space + gpName);
                    groupList.add(gpMdl);
                }

            }
            return groupList;
        } else {
            for (GroupModel gpMdl : gpList) {
                level = gpMdl.getClassLevel();
                String gpName = gpMdl.getGroupName();
                space = getCombSpace(level);
                gpMdl.setGroupName(space + gpName);
                groupList.add(gpMdl);
            }
        }

        return groupList;
    }


    /**
     * 表示グループ用(コンボボックス)のグループリストを取得する(全て)
     * @param con コネクション
     * @param defFlg true:「選択してください」を含める false:グループリストのみ
     * @param gsMsg GSメッセージ
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList < LabelValueBean > getGroupCombLabelList(
            Connection con, boolean defFlg, GsMessage gsMsg)
    throws SQLException {


        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (defFlg) {
            //選択してください
            String textSelect = gsMsg.getMessage("cmn.select.plz");

            labelList.add(new LabelValueBean(textSelect, String.valueOf(-1)));
        }

        int level = 1;
        String space = "";
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> gpList = null;
        if (sortMdl.getCscGroupSkbn() == GSConst.GROUPCMB_SKBN_SET) {
            gpList = dao.getGroupTree(sortMdl);
        } else {
            gpList = dao.getGroupList(sortMdl);
        }

        if (!gpList.isEmpty()) {
            for (GroupModel gpMdl : gpList) {
                level = gpMdl.getClassLevel();
                String gpName = gpMdl.getGroupName();
                space = getCombSpace(level);
                gpMdl.setGroupName(space + gpName);
                labelList.add(new LabelValueBean(gpMdl.getGroupName(),
                        String.valueOf(gpMdl.getGroupSid())));
            }
        }

        return labelList;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する(コンボボックス用)
     * <br>[解  説] 指定したユーザが属するグループの一覧を取得する
     * <br>         ユーザSID < 0 の場合は全グループの一覧を取得する
     * <br>[備  考]
     * @param level 階層ﾚﾍﾞﾙ
     * @return グループ情報一覧
     */
    public String getCombSpace(int level) {

        String space = "";

        switch (level) {
            case 1:
            default :
                space = "";
                break;
            case 2:
                space = "　";
                break;
            case 3:
                space = "　　";
                break;
            case 4:
                space = "　　　";
                break;
            case 5:
                space = "　　　　";
                break;
            case 6:
                space = "　　　　　";
                break;
            case 7:
                space = "　　　　　　";
                break;
            case 8:
                space = "　　　　　　　";
                break;
            case 9:
                space = "　　　　　　　　";
                break;
            case 10:
                space = "　　　　　　　　　";
                break;
        }


        return space;
    }
    /**
     * 表示グループ用のグループリストを取得する(全て)
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getAllGroupLabelList(Connection con, GsMessage gsMsg)
        throws SQLException {
        //全グループ
        String text = gsMsg.getMessage("cmn.group.all");
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(text, String.valueOf(-1)));

        //グループリスト取得
//        GroupDao dao = new GroupDao(con);
//        ArrayList<GroupModel> gpList = dao.getGroupTree(null);
        ArrayList<GroupModel> gpList = getGroupList(con);

        GroupModel gpMdl = null;
        for (int i = 0; i < gpList.size(); i++) {
            gpMdl = gpList.get(i);
            labelList.add(
                    new LabelValueBean(gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
        }
        log__.debug("labelList.size()=>" + labelList.size());
        return labelList;
    }

    /**
     * 表示グループ用のグループリストを取得する(所属グループのみ)
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「選択してください」のラベルを作成する, false:作成しない
     * @param gsMsg GSメッセージ
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getBelongGroupLabelList(int usrSid,
            Connection con, boolean sentakuFlg,  GsMessage gsMsg) throws SQLException {

        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();
        if (sentakuFlg) {
            labelList.add(new UsrLabelValueBean(textSelect, String.valueOf(-1)));
        }

        //グループリスト取得
        UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
//        ArrayList<GroupModel> gpList = gpDao.selectGroupNmList(usrSid);
        ArrayList<GroupModel> gpList = gpDao.selectGroupNmListOrderbyClass(usrSid);

//        CmnGroupmModel gpMdl = null;
        for (GroupModel gmodel : gpList) {
            labelList.add(new UsrLabelValueBean(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid())));
        }
        return labelList;
    }

    /**
     * 表示グループ用のグループリストを取得する(グループ管理者権限をもつグループのみ)
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「選択してください」のラベルを作成する, false:作成しない
     * @param gsMsg GSメッセージ
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getAdminGroupLabelList(int usrSid,
            Connection con, boolean sentakuFlg,  GsMessage gsMsg) throws SQLException {

        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (sentakuFlg) {
            labelList.add(new LabelValueBean(textSelect, String.valueOf(-1)));
        }

        //グループリスト取得
        CmnGroupmDao gpDao = new CmnGroupmDao(con);
//        List<CmnGroupmModel> gpList = gpDao.selectGroupAdmin(usrSid);
        List<GroupModel> gpList = gpDao.selectGroupAdminOrderbyClass(usrSid);

        for (GroupModel gmodel : gpList) {
            labelList.add(new LabelValueBean(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid())));
        }
        return labelList;
    }

    /**
     * ユーザSIDからデフォルトグループSIDを取得します
     * <P>グループ情報を取得できない場合は-1を返します
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return int デフォルトグループSID
     * @throws SQLException SQL実行時例外
     */
    public int getDefaultGroupSid(int usrSid, Connection con) throws SQLException {
        int gpSid = -1;
        GroupDao gpDao = new GroupDao(con);
        CmnGroupmModel gpMdl = gpDao.getDefaultGroup(usrSid);
        if (gpMdl != null) {
            gpSid = gpMdl.getGrpSid();
        }
        return gpSid;
    }

    /**
     * ユーザSIDからデフォルトグループIDを取得します
     * <P>グループ情報を取得できない場合はnullを返します
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return int デフォルトグループSID
     * @throws SQLException SQL実行時例外
     */
    public String getDefaultGroupId(int usrSid, Connection con) throws SQLException {
        String gpId = null;
        GroupDao gpDao = new GroupDao(con);
        CmnGroupmModel gpMdl = gpDao.getDefaultGroup(usrSid);
        if (gpMdl != null) {
            gpId = gpMdl.getGrpId();
        }
        return gpId;
    }

    /**
     * ユーザSID、グループSIDからグループに所属しているか判定します
     * @param usrSid ユーザSID
     * @param grpSid グループSID
     * @param con コネクション
     * @return boolean true:所属している false:所属していない
     * @throws SQLException SQL実行時例外
     */
    public boolean isBelongGroup(int usrSid, int grpSid, Connection con) throws SQLException {
        boolean ret = false;
        CmnBelongmDao dao = new CmnBelongmDao(con);
        ret = dao.isExist(grpSid, usrSid);
        return ret;
    }

    /**
     * ユーザ１とユーザ2が同じグループに所属しているか判定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param user1 ユーザ1
     * @param user2 ユーザ2
     * @param con コネクション
     * @return boolean true:所属している false:所属していない
     * @throws SQLException SQL実行時例外
     */
    public boolean isBothBelongGroup(int user1, int user2, Connection con) throws SQLException {
        boolean ret = false;
        UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> gpList = gpDao.selectGroupNmListOrderbyClass(user1);
        for (GroupModel gpMdl : gpList) {
            if (isBelongGroup(user2, gpMdl.getGroupSid(), con)) {
                return true;
            }
        }
        return ret;
    }

    /**
     * ユーザ１とユーザ2の両方が所属するグループを取得します
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param user1 ユーザ1
     * @param user2 ユーザ2
     * @param con コネクション
     * @return boolean true:所属している false:所属していない
     * @throws SQLException SQL実行時例外
     */
    public String getBothBelongGroup(int user1, int user2, Connection con) throws SQLException {
        String gpSid = "";
        UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> gpList = gpDao.selectGroupNmListOrderbyClass(user1);
        for (GroupModel gpMdl : gpList) {
            if (isBelongGroup(user2, gpMdl.getGroupSid(), con)) {
                gpSid = String.valueOf(gpMdl.getGroupSid());
            }
        }
        return gpSid;
    }

    /**
     * <br>[機  能] グループの階層構造Model一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return グループの階層構造Model一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getGroupTree(Connection con) throws SQLException {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> tree = dao.getGroupTree(sortMdl, true);

        return tree;
    }

    /**
     * <br>[機  能] 指定したグループのグループ情報を取得する
     * <br>[解  説] グループ階層順に並び替えを行う
     * <br>[備  考]
     * @param con コネクション
     * @param groupSids グループSID
     * @return グループ情報
     * @throws SQLException SQL実行時例外
     */
    public List<CmnGroupmModel> getGroupTreeList(Connection con, int[] groupSids)
    throws SQLException {
        ArrayList<GroupModel> tree = getGroupTree(con);
        List<CmnGroupmModel> glist = new ArrayList<CmnGroupmModel>();
        for (GroupModel treeMdl : tree) {

            for (int sltGrpSid : groupSids) {
                if (sltGrpSid == treeMdl.getGroupSid()) {
                    CmnGroupmModel sltGrpMdl = new CmnGroupmModel();
                    sltGrpMdl.setGrpSid(treeMdl.getGroupSid());
                    sltGrpMdl.setGrpName(treeMdl.getGroupName());
                    sltGrpMdl.setGrpId(treeMdl.getGroupId());
                    glist.add(sltGrpMdl);
                    break;
                }
            }
        }

        return glist;
    }

    /**
     * 表示グループ用(コンボボックス)のグループリストを取得する(全て)
     * @param con コネクション
     * @param defFlg true:「選択してください」を含める false:グループリストのみ
     * @param gsMsg GSメッセージ
     * @param groupSids グループSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList < LabelValueBean > getGroupTreeLabelList(
            Connection con, boolean defFlg, GsMessage gsMsg, String[] groupSids)
    throws SQLException {


        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (defFlg) {
            //選択してください
            String textSelect = gsMsg.getMessage("cmn.select.plz");

            labelList.add(new LabelValueBean(textSelect, String.valueOf(-1)));
        }

        ArrayList<GroupModel> tree = getGroupTree(con);
        List<CmnGroupmModel> glist = new ArrayList<CmnGroupmModel>();
        for (GroupModel treeMdl : tree) {

            if (groupSids != null && groupSids.length > 0) {
                for (String sltGrpSid : groupSids) {

                    if (ValidateUtil.isNumber(sltGrpSid)) {
                        if (Integer.valueOf(sltGrpSid) == treeMdl.getGroupSid()) {
                            CmnGroupmModel sltGrpMdl = new CmnGroupmModel();
                            sltGrpMdl.setGrpSid(treeMdl.getGroupSid());
                            sltGrpMdl.setGrpName(treeMdl.getGroupName());
                            sltGrpMdl.setGrpId(treeMdl.getGroupId());
                            glist.add(sltGrpMdl);
                            break;
                        }
                    }
                }
            }
        }

        if (!glist.isEmpty()) {
            for (CmnGroupmModel gpMdl : glist) {
                labelList.add(new LabelValueBean(gpMdl.getGrpName(),
                        String.valueOf(gpMdl.getGrpSid())));
            }
        }

        return labelList;
    }

    /**
     * <br>[機  能] 検索したグループの情報一覧を取得する
     * <br>[解  説] 検索対象：グループID、グループ名
     * <br>[備  考]
     * @param con コネクション
     * @param grpId グループID
     * @param grpName グループ名
     * @return グループの階層構造Model一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getSearchGroupNoTree(Connection con, String grpId, String grpName)
            throws SQLException {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> ret = dao.getPartGroupList(sortMdl, grpId, grpName);

        return ret;
    }
}
