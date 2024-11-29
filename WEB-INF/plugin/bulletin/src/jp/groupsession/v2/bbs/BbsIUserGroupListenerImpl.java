package jp.groupsession.v2.bbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsForMemDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouBodyBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BbsUserDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsForMemModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] 掲示板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsIUserGroupListenerImpl implements IUserGroupListener {

    /**
     * <br>[機  能] ユーザ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 追加されるユーザSID
     * @param con DBコネクション
     * @param cntCon MlCountMtController
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon,
            Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

        //追加ユーザがメンバーに含まれる かつ
        //新規ユーザのスレッド閲覧状態 = 「既読」のフォーラムを取得する
        List<Integer> bfiSidList = __getInitForumSidList(con, usid);

        //スレッド閲覧情報の登録を行う
        if (!bfiSidList.isEmpty()) {
            UDate now = new UDate();
            BbsThreViewDao threViewDao = new BbsThreViewDao(con);

            for (Integer bfiSid : bfiSidList) {
                threViewDao.insert(bfiSid, usid, eusid, now);
            }
        }
    }

    /**
     * <br>[機  能] ユーザ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

        //フォーラムメンバーの削除を行う。
        BbsForMemDao forMemDao = new BbsForMemDao(con);
        forMemDao.delete(usid);

        //掲示板個人設定の削除
        BbsUserDao userDao = new BbsUserDao(con);
        BbsUserModel userModel = new BbsUserModel();
        userModel.setUsrSid(usid);
        userDao.delete(userModel);

        //スレッド閲覧情報の削除
        BbsThreViewDao threViewDao = new BbsThreViewDao(con);
        threViewDao.delete(userModel);

        //草稿情報の削除
        BbsSoukouDao dao = new BbsSoukouDao(con);
        BbsSoukouBinDao binDao = new BbsSoukouBinDao(con);
        BbsSoukouBodyBinDao binBodyDao = new BbsSoukouBodyBinDao(con);
        //バイナリー情報の論理削除
        int binNum = binDao.updateBinfForSoukouUserSid(usid);
        int binBodyNum = binBodyDao.updateBinfForSoukouUserSid(usid);
        // 草稿添付の削除
        if (binNum > 0) {
            binDao.deleteSoukouBinUserSid(usid);
        }
        if (binBodyNum > 0) {
           binBodyDao.deleteSoukouBodyBinUserSid(usid);
        }
        // 草稿削除
        dao.deleteSoukouUserSid(usid);
    }

    /**
     * <br>[機  能] グループ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void addGroup(Connection con, int gsid, int eusid) throws SQLException {
    }

    /**
     * <br>[機  能] グループ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(
            Connection con, int gsid, int eusid, RequestModel reqMdl) throws SQLException {
    }

    /**
     * <br>[機  能] ユーザの所属グループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param pastGsids 変更前のグループSID配列
     * @param gsids 変更後のグループSID配列
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeBelong(Connection con, int usid, int[] pastGsids, int[] gsids, int eusid)
    throws SQLException {

        if (gsids == null || gsids.length == 0) {
            return;
        }

        //所属グループに追加されたグループを取得する
        List<Integer> addGroupList = new ArrayList<Integer>();
        List<Integer> excGroupList = new ArrayList<Integer>();

        if (pastGsids == null || pastGsids.length == 0) {
            for (int gsid : gsids) {
                addGroupList.add(gsid);
            }
        } else {
            Arrays.sort(pastGsids);
            for (int gsid : gsids) {
                if (Arrays.binarySearch(pastGsids, gsid) < 0) {
                    addGroupList.add(gsid);
                } else {
                    excGroupList.add(gsid);
                }
            }
        }

        //追加されたグループが存在しない場合、処理を終了する
        if (addGroupList.isEmpty()) {
            return;
        }

        //追加グループがメンバーに含まれる かつ
        //新規ユーザのスレッド閲覧状態 = 「既読」のフォーラムを取得する
        List<Integer> bfiSidList = __getInitForumSidList(con, addGroupList);

        //取得したフォーラムのうち、変更前のグループまたは対象ユーザがメンバーとして登録されているものを除外する
        bfiSidList = __excludeForumSidList(con, bfiSidList, excGroupList, usid);

        //スレッド閲覧情報の登録を行う
        if (!bfiSidList.isEmpty()) {
            UDate now = new UDate();
            BbsThreViewDao threViewDao = new BbsThreViewDao(con);
            for (Integer bfiSid : bfiSidList) {
                threViewDao.insertAllReadThreadInForum(bfiSid, usid, eusid, now);
            }
        }
    }

    /**
     * <br>[機  能] 指定したグループがメンバーに含まれる かつ
     * <br>         新規ユーザのスレッド閲覧状態=「既読」のフォーラムを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupList グループSIDリスト
     * @return フォーラムSID
     * @throws SQLException SQL実行例外
     */
    private List<Integer> __getInitForumSidList(
            Connection con, List<Integer> groupList) throws SQLException {
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfGroup(con, groupList);
        List <Integer> forumSidList = new ArrayList <Integer>();

        for (BbsForInfModel bfiMdl : bfiMdlList) {
            if (bfiMdl.getBfiRead() == 1) {
                forumSidList.add(bfiMdl.getBfiSid());
            }
        }

        return forumSidList;
    }

    /**
     * <br>[機  能] 指定したユーザがメンバーに含まれる かつ
     * <br>         新規ユーザのスレッド閲覧状態=「既読」のフォーラムを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return フォーラムSID
     * @throws SQLException SQL実行例外
     */
    private List<Integer> __getInitForumSidList(Connection con, int userSid)
            throws SQLException {
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfUser(con, userSid);
        List<Integer> bfiSidList = new ArrayList<Integer>();
        for (BbsForInfModel bfiMdl : bfiMdlList) {
            bfiSidList.add(bfiMdl.getBfiSid());
        }

        BbsForInfDao forumDao = new BbsForInfDao(con);
        return forumDao.getInitForumSidList(bfiSidList);
    }

    /**
     * <br>[機  能] 取得したフォーラムのうち、変更前のグループまたは対象ユーザがメンバーとして登録されているものを除外します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSidList フォーラムSID一覧
     * @param excGroupList 除外するグループのグループSID
     * @param userSid ユーザSID
     * @return フォーラムSID一覧
     * @throws SQLException SQL実行例外
     */
    private List<Integer> __excludeForumSidList(
            Connection con, List<Integer> bfiSidList,
            List<Integer> excGroupList, int userSid)
                    throws SQLException {

        BbsForMemDao bfmDao = new BbsForMemDao(con);
        List <BbsForMemModel> bfmMdlList =
                bfmDao.getForumOfUserOrGroup(userSid, excGroupList);
        List<Integer> excForumSidList = new ArrayList<Integer>();
        for (BbsForMemModel bfmMdl : bfmMdlList) {
            excForumSidList.add(bfmMdl.getBfiSid());
        }

        BbsForInfDao forumDao = new BbsForInfDao(con);
        return forumDao.excludeForumSidList(bfiSidList, excForumSidList);
    }

    /**
     * <br>[機  能] ユーザのデフォルトグループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param gsid 変更後のデフォルトグループ
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeDefaultBelong(Connection con, int usid, int gsid, int eusid)
    throws SQLException {
    }
}
