package jp.groupsession.v2.bbs.bbs061;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Biz;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsForMemDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouDao;
import jp.groupsession.v2.bbs.dao.BbsThreSumDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsThreSumModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinForumDiskModel;
import jp.groupsession.v2.bbs.model.ForumModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 掲示板 スレッド一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs061Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs060Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public Bbs061Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param userSid ユーザSID
     * @param adminFlg システム管理者・プラグイン管理者フラグ
     * @param buMdl ユーザ情報モデル
     * @throws Exception 実行例外
     */
    public void setInitData(
            String cmd, Bbs061ParamModel paramMdl, Connection con, RequestModel reqMdl,
            int userSid, boolean adminFlg, BaseUserModel buMdl)
                    throws Exception {

        ArrayList<ForumModel> allTree = __getForumTree(con);
        ArrayList<ForumModel> tree = new ArrayList<ForumModel>();

        //編集権限チェック
        for (ForumModel mdl : allTree) {
            if (mdl.getForumSid() != paramMdl.getBbs010forumSid()) {
                if (isCanMoveToForum(
                        con, mdl.getForumSid(), paramMdl.getBbs060ChkInfSid(), buMdl)) {
                    tree.add(mdl);
                }
            }
        }

        if (tree.isEmpty() || tree.size() < 1) {
            return;
        }

        paramMdl.setCheckForum(tree.get(0).getForumSid());
        paramMdl.setMoveForumList(tree);
    }

    /**
     * <br>[機  能] フォーラムの階層構造Model一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return フォーラムの階層構造Model一覧
     */
    private ArrayList<ForumModel> __getForumTree(Connection con) {
        ArrayList<ForumModel> ret = null;

        try {
            BbsForInfDao bfiDao = new BbsForInfDao(con);
            List<BbsForInfModel> forInfList = bfiDao.selectWithHierarchy();

            if (forInfList == null || forInfList.isEmpty()) {
                return null;
            }

            ret = new ArrayList<ForumModel>();

            Iterator<BbsForInfModel> it = forInfList.iterator();

            while (it.hasNext()) {
                BbsForInfModel forInfModel = it.next();

                ForumModel mdl = new ForumModel();
                //フォーラムSIDをセット
                mdl.setForumSid(forInfModel.getBfiSid());
                //フォーラム名をセット
                mdl.setForumName(forInfModel.getBfiName());
                //フォーラム階層レベルをセット
                mdl.setClassLevel(forInfModel.getBfiLevel());
                //アイコン画像のバイナリSIDをセット
                mdl.setBinSid(forInfModel.getBinSid());
                ret.add(mdl);
            }
        } catch (SQLException e) {
            log__.error("SQLエラー", e);
        }
        return ret;
    }

    /**
     * <br>[機  能] フォーラム名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムSID
     * @return フォーラム名
     * @throws Exception 実行例外
     */
    public String getForumName(
            Connection con, int forumSid)
                    throws Exception {

        BulletinDao dao = new BulletinDao(con);
        BulletinDspModel mdl = dao.getForumData(forumSid);
        return mdl.getBfiName();
    }

    /**
     * <br>[機  能] スレッドの移動を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param buMdl ユーザモデル
     * @throws Exception 実行例外
     */
    public void moveThread(
            Connection con, Bbs061ParamModel paramMdl, BaseUserModel buMdl)
                    throws Exception {

        //フォーラム変更
        BulletinDao dao = new BulletinDao(con);
        dao.updateBinfForMove(paramMdl.getCheckForum(), paramMdl.getBbs060ChkInfSid());
        BbsWriteInfDao wriDao = new BbsWriteInfDao(con);
        wriDao.updateWriForMove(paramMdl.getCheckForum(), paramMdl.getBbs060ChkInfSid());
        BbsSoukouDao souDao = new BbsSoukouDao(con);
        souDao.updateSouForMove(paramMdl.getCheckForum(), paramMdl.getBbs060ChkInfSid());

        BbsForMemDao bfmDao = new BbsForMemDao(con);
        BbsThreViewDao btvDao = new BbsThreViewDao(con);
        
        String[] forumMemSid = bfmDao.getForumMemberId(paramMdl.getCheckForum());
        List<Integer> usrGrpList = new ArrayList<Integer>();
        Integer[] forumSid = Stream.of(paramMdl.getBbs060ChkInfSid()).mapToInt(
                Integer::parseInt).boxed().toArray(Integer[]::new);

        for (String usrGrpSid : forumMemSid) {

            if (usrGrpSid.substring(0, 1).equals("G")) {
                //グループの時はそのグループに所属するユーザSIDを取得
                CmnBelongmDao cbmDao = new CmnBelongmDao(con);
                int grpSid = Integer.parseInt(usrGrpSid.substring(1));
                List<Integer> grpMemList = cbmDao.selectBelongUserSid(grpSid);

                for (int usrSid : grpMemList) {
                    usrGrpList.add(usrSid);
                }
            } else {
                usrGrpList.add(Integer.parseInt(usrGrpSid));
            }
        }
        btvDao.delete(forumSid, usrGrpList);

        //フォーラム閲覧数リセット
        BbsThreViewDao viewDao = new BbsThreViewDao(con);
        viewDao.updateBelongForum(paramMdl.getCheckForum(), paramMdl.getBbs060ChkInfSid());

        //フォーラム集計情報の更新
        BbsBiz biz = new BbsBiz();
        UDate now = new UDate();
        //移動元フォーラムの更新
        biz.updateBbsForSum(con, paramMdl.getBbs010forumSid(), buMdl.getUsrsid(), now);
        //移動先フォーラムの更新
        biz.updateBbsForSum(con, paramMdl.getCheckForum(), buMdl.getUsrsid(), now);

    }


    /**
     * <p> 移動先のフォーラムに対し、選択したスレッドを移動できるか判定
     * @param con コネクション
     * @param bfiSid 移動先フォーラムSID
     * @param moveThreads 移動対象スレッドSID
     * @param buMdl セッションユーザモデル
     * @return trueであれば移動可能
     * @throws Exception 例外処理
     * */
    public boolean isCanMoveToForum(
            Connection con, int bfiSid, String[] moveThreads, BaseUserModel buMdl)
                    throws Exception {

        BbsBiz bbsBiz  = new BbsBiz(con);
        if (!bbsBiz.isForumEditAuth(con, bfiSid, buMdl)) {
            return false;
        }


        List<Integer> threadSidList = new ArrayList<Integer>();
        for (String threadSid : moveThreads) {
            threadSidList.add(Integer.parseInt(threadSid));
        }

        if (!isDiskSizeForMove(con, bfiSid, threadSidList)) {
            return false;
        }

        return true;
    }


    /**
     * <p> スレッドを移動した場合、移動先フォーラムの容量制限を超過しないか判定
     * @param con コネクション
     * @param bfiSid 移動先フォーラムSID
     * @param threadSidList 移動対象スレッドSID
     * @return trueであれば、移動先フォーラムの容量は超過しない
     * @throws Exception 例外処理
     * */
    public boolean isDiskSizeForMove(Connection con, int bfiSid, List<Integer> threadSidList)
            throws Exception {
        BbsThreSumDao threadSumDao = new BbsThreSumDao(con);
        List<BbsThreSumModel> threadSumMdlList = threadSumDao.select(threadSidList);

        //ディスク容量チェック
        BbsBiz bbsBiz = new BbsBiz();
        BulletinForumDiskModel diskData = bbsBiz.getForumDiskData(con, bfiSid);
        if (diskData.getBfiDisk() == GSConstBulletin.BFI_DISK_LIMITED) {
            long limitSize = diskData.getBfiDiskSize() * 1024 * 1024;
            long diskSize = diskData.getBfsSize();
            for (BbsThreSumModel sum : threadSumMdlList) {
                diskSize += sum.getBtsSize();
            }
            if (diskSize > limitSize) {
                return false;
            }
        }
        return true;
    }
}
