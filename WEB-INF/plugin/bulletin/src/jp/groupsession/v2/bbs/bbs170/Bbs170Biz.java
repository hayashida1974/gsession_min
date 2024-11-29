package jp.groupsession.v2.bbs.bbs170;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Biz;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreRsvDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinForumDiskModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 掲示予定一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs170Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs170Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param adminFlg システム管理者・プラグイン管理者フラグ
     * @param buMdl ユーザ情報モデル
     * @throws Exception 実行例外
     */
    public void setInitData(
            RequestModel reqMdl, Bbs170ParamModel paramMdl, Connection con,
            int userSid, boolean adminFlg, BaseUserModel buMdl)
                    throws Exception {
        log__.debug("START");

        //フォーラムコンボを設定
        paramMdl.setBbs170forumList(
                __getForumLabelList(con, reqMdl, paramMdl, userSid, adminFlg, buMdl));

        //選択中フォーラムを設定
        int bfiSid = paramMdl.getBbs010forumSid();
        if (paramMdl.getBbs170allForumFlg() == 1) {
            //全フォーラム表示からスレッド編集画面に遷移し、戻ってきた場合
            bfiSid = -1;
            paramMdl.setBbs010forumSid(bfiSid);
        }

        if (bfiSid == -1) {
            //複数フォーラム表示時
            paramMdl.setBbs170allForumFlg(1);
            __getMultiForumData(paramMdl, con, userSid, adminFlg, buMdl);
        } else {
            //単一フォーラム選択時
            paramMdl.setBbs170allForumFlg(0);
            __getSingleForumData(paramMdl, con, userSid, adminFlg, bfiSid, buMdl);
        }

        log__.debug("End");
    }


    /**
     * <br>[機  能] 単一のフォーラム情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param adminFlg システム管理者・プラグイン管理者フラグ
     * @param bfiSid 選択フォーラム
     * @param buMdl ユーザ情報モデル
     * @throws SQLException SQL実行時例外
     */
    private void __getSingleForumData(
            Bbs170ParamModel paramMdl, Connection con,
            int userSid, boolean adminFlg, int bfiSid, BaseUserModel buMdl)
                    throws SQLException {
        BbsBiz bbsBiz = new BbsBiz();

        //フォーラム名を設定
        BulletinDspModel btDspMdl = bbsBiz.getForumData(con, bfiSid);
        String forumName = "";
        if (btDspMdl != null) {
            forumName = btDspMdl.getBfiName();
        }
        paramMdl.setBbs170forumName(forumName);

        //フォーラムのディスク容量警告を設定
        BulletinForumDiskModel diskData = bbsBiz.getForumDiskData(con, bfiSid);
        if (bbsBiz.checkForumWarnDisk(diskData)) {
            paramMdl.setBbs170forumWarnDisk(diskData.getBfiWarnDiskTh());
        } else {
            paramMdl.setBbs170forumWarnDisk(0);
        }

        LinkedHashMap<Integer, List<BulletinDspModel>> singleThreadListMap =
                new LinkedHashMap<Integer, List<BulletinDspModel>>();

        //掲示板個人情報を取得
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        UDate now = new UDate();

        //最大件数
        int threCnt = __getThreadCount(con, bfiSid, userSid, now, adminFlg);

        log__.debug("getThreadCount==>" + threCnt);
        int maxThreCnt = bUserMdl.getBurThreCnt();

        //ページ調整
        int maxPage = threCnt / maxThreCnt;
        if ((threCnt % maxThreCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbs170page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs170page1(page);
        paramMdl.setBbs170page2(page);
        //ページコンボ設定
        paramMdl.setBbsPageLabel(PageUtil.createPageOptions(threCnt, maxThreCnt));

        //スレッド一覧を取得する
        BulletinDao btDao = new BulletinDao(con);
        int start = (page - 1) * maxThreCnt + 1;
        int end = start + maxThreCnt - 1;

        List<BulletinDspModel> threadList = new ArrayList<BulletinDspModel>();
        if (bbsBiz.isForumEditAuth(con, bfiSid, buMdl)) {
            //編集権限がある場合、掲示予定スレッドを取得
            threadList = btDao.getRsvThreadList(
                    bfiSid, userSid, bUserMdl.getBurNewCnt(), start, end,
                    Integer.parseInt(paramMdl.getBbs170sortKey()),
                    Integer.parseInt(paramMdl.getBbs170orderKey()), adminFlg,
                    bbsBiz.getBfiSidForMemberInfo(con, bfiSid));
        }

        //フォーラムのメンバー数を取得する
//        paramMdl.setForumMemberCount(String.valueOf(btDao.getForumMemberCount(bfiSid)));

        //フォーラムSIDからアイコンバイナリSIDを取得する
        Bbs170Biz bbs170Biz = new Bbs170Biz();
        Long binSid = bbs170Biz.getIcoBinSid(paramMdl.getBbs010forumSid(), con);

        if (threadList != null && threadList.size() > 0) {
            BulletinDspModel topThread = threadList.get(0);

            //フォーラム名を設定
            BulletinDspModel forumData = bbsBiz.getForumData(con, bfiSid);
            topThread.setBfiName(forumData.getBfiName());

            topThread.setImgBinSid(binSid);

            singleThreadListMap.put(bfiSid, threadList);

            paramMdl.setBbs170forumThreadMap(singleThreadListMap);

        } else {
            paramMdl.setBbs170BinSid(binSid);
        }

    }

    /**
     * <br>[機  能] 複数のフォーラム情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param adminFlg システム管理者・プラグイン管理者フラグ
     * @param buMdl ユーザ情報モデル
     * @throws SQLException SQL実行時例外
     */
    private void __getMultiForumData(
            Bbs170ParamModel paramMdl, Connection con,
            int userSid, boolean adminFlg, BaseUserModel buMdl)
                    throws SQLException {
        BbsBiz bbsBiz = new BbsBiz();
        BulletinDao bbsDao = new BulletinDao(con);

        List<BbsForInfModel> usersForumWrite =
                bbsBiz.getForumListOfUser(con, userSid, GSConstBulletin.ACCESS_KBN_WRITE);
        List<BbsForInfModel> usersForumAll = bbsBiz.getForumListOfUser(con, userSid);

        //階層の表示順にフォーラムリスト情報を取得
        List<BulletinDspModel> hierarchicalList = bbsDao.getForumListWithHierarchy(
                userSid, new UDate(), 0, 0, 0,  false, usersForumWrite, usersForumAll);

        LinkedHashMap<Integer, List<BulletinDspModel>> multiThreadListMap =
                new LinkedHashMap<Integer, List<BulletinDspModel>>();
        for (BulletinDspModel mdl : hierarchicalList) {

            //掲示板個人情報を取得
            BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

            List<BulletinDspModel> threadList = new ArrayList<BulletinDspModel>();
            if (bbsBiz.isForumEditAuth(con, mdl.getBfiSid(), buMdl)) {
                //編集権限がある場合、掲示予定スレッドを取得
                threadList = bbsDao.getRsvThreadList(
                        mdl.getBfiSid(), userSid, bUserMdl.getBurNewCnt(), 0, 0,
                        Integer.parseInt(paramMdl.getBbs170sortKey()),
                        Integer.parseInt(paramMdl.getBbs170orderKey()), adminFlg,
                        bbsBiz.getBfiSidForMemberInfo(con, mdl.getBfiSid()));
            }
            if (threadList == null || threadList.size() < 1) {
                continue;
            }

            BulletinDspModel topThread = threadList.get(0);

            //フォーラム名を設定
            BulletinDspModel forumData = bbsBiz.getForumData(con, mdl.getBfiSid());
            topThread.setBfiName(forumData.getBfiName());

            //フォーラムSIDからアイコンバイナリSIDを取得する
            Bbs170Biz bbs170Biz = new Bbs170Biz();
            Long binSid = bbs170Biz.getIcoBinSid(mdl.getBfiSid(), con);
            topThread.setImgBinSid(binSid);

            multiThreadListMap.put(mdl.getBfiSid(), threadList);
        }

        paramMdl.setBbs170forumThreadMap(multiThreadListMap);
    }

    /**
     * <br>[機  能] フォーラムコンボを作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @param adminFlg システム管理者・プラグイン管理者フラグ
     * @param buMdl ユーザ情報モデル
     * @throws SQLException SQL実行例外
     * @return フォーラムコンボ
     */
    private List<LabelValueBean> __getForumLabelList(
            Connection con, RequestModel reqMdl, Bbs170ParamModel paramMdl,
            int userSid, boolean adminFlg, BaseUserModel buMdl)
                    throws SQLException {
        List<LabelValueBean> forumLabelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        forumLabelList.add(new LabelValueBean(
                gsMsg.getMessage("bbs.bbs170.2"), String.valueOf("-1")));

        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> usersForumWrite =
                bbsBiz.getForumListOfUser(con, userSid, GSConstBulletin.ACCESS_KBN_WRITE);
        List<BbsForInfModel> usersForumAll =
                bbsBiz.getForumListOfUser(con, userSid);

        BulletinDao bbsDao = new BulletinDao(con);
        List<BulletinDspModel> bdMdlList = bbsDao.getForumListWithHierarchy(
                userSid, new UDate(), 0, 0, 0,  false, usersForumWrite, usersForumAll);

        for (BulletinDspModel mdl : bdMdlList) {
            int forumLevel = mdl.getForumLevel();
            String bfiName = "";
            int i = 1;
            while (i < forumLevel) {
                bfiName += "　";
                ++i;
            }
            bfiName += mdl.getBfiName();

            //編集権限を持つならば件数を表示する
            if (bbsBiz.isForumEditAuth(con, mdl.getBfiSid(), buMdl)) {
                int rsvThreCnt =
                        bbsDao.countRsvThreData(mdl.getBfiSid(), userSid, adminFlg, new UDate());
                if (rsvThreCnt > 0) {
                    bfiName += " (" + String.valueOf(rsvThreCnt) + ")";
                }
            }
            String bfiSid = String.valueOf(mdl.getBfiSid());
            LabelValueBean bbsForLabel =
                    new LabelValueBean(bfiName, bfiSid);
            forumLabelList.add(bbsForLabel);
        }

        return forumLabelList;
    }

    /**
     * <br>[機  能] スレッドデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @param admin 管理者権限の有無
     * @param con コネクション
     * @return 削除後の掲示予定スレッド件数
     * @throws Exception 実行時例外
     */
    public int deleteThreadData(
            Bbs170ParamModel paramMdl, int userSid, boolean admin, Connection con)
            throws Exception {

        int rsvThrNum = 0;

        //スレッド情報の削除
        boolean commit = false;
        try {
            Bbs060Biz biz = new Bbs060Biz();
            biz.deleteThreadData(
                    paramMdl.getBbs170backForumSid(), paramMdl.getThreadSid(), con, userSid);
            //最終投稿日時の計算に影響しないよう、予約投稿リストから削除
            BbsThreRsvDao btrDao = new BbsThreRsvDao(con);
            btrDao.delete(paramMdl.getThreadSid());

            commit = true;
        } catch (Exception e) {
            log__.error("スレッドの削除に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        //削除後、掲示予定スレッド件数を取得する
        BulletinDao btDao = new BulletinDao(con);
        boolean allFlg = false;
        if (admin) {
            allFlg = true;
        }

        rsvThrNum = btDao.countRsvThreData(
                paramMdl.getBbs170backForumSid(), userSid, allFlg, new UDate());


        return rsvThrNum;
    }


    /**
     * <br>[機  能] 掲示予定のスレッドの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param usrSid ユーザSID
     * @param now 現在日時
     * @param adminFlg 管理者権限フラグ
     * @return スレッド件数
     * @throws SQLException SQL実行例外
     */
    private int __getThreadCount(
            Connection con, int bfiSid, int usrSid, UDate now, boolean adminFlg)
    throws SQLException {

        BulletinDao btDao = new BulletinDao(con);
        return btDao.countRsvThreData(bfiSid, usrSid, adminFlg, now);
    }

    /**
     * <br>[機  能] 閲覧フォーラムのアイコンバイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param forumSid フォーラムSID
     * @param con コネクション
     * @return アイコンバイナリSID
     * @throws SQLException SQL実行例外
     */
    public Long getIcoBinSid(int forumSid, Connection con)
            throws SQLException {

        //閲覧フォーラムのアイコンバイナリSIDを取得する
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        Long icoBinSid = bfiDao.selectIcoBinSid(forumSid);

        return icoBinSid;
    }

    /**
     * <br>[機  能] フォーラムSIDとアイコンバイナリSIDを照合する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param imgBinSid 画像バイナリSID
     * @return true:照合OK false:照合NG
     * @throws SQLException SQL実行例外
     */
    public boolean cheIcoHnt(Connection con, int bfiSid, Long imgBinSid)
    throws SQLException {

        boolean icoCheckFlg = false;

        //フォーラムSIDとアイコンバイナリSIDの組み合わせチェック
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        boolean existForIcoFlg = bfiDao.existBbsForIco(bfiSid, imgBinSid);

        if (existForIcoFlg) {
            icoCheckFlg = true;
        }

        return icoCheckFlg;
    }

    /**
     * <br>[機  能] 最初の投稿SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @return 投稿SID
     * @throws SQLException SQL実行例外
     */
    public int getFirstBwiSid(Connection con, int bfiSid, int btiSid)
    throws SQLException {

        BbsWriteInfDao dao = new BbsWriteInfDao(con);
        return dao.getFirstBwiSid(bfiSid, btiSid);
    }
}
