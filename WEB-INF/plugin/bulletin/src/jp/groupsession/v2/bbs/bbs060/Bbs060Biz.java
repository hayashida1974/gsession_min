package jp.groupsession.v2.bbs.bbs060;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs010.Bbs010Biz;
import jp.groupsession.v2.bbs.dao.BbsBinDao;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreRsvDao;
import jp.groupsession.v2.bbs.dao.BbsThreSumDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.bbs.model.BbsThreViewModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.bbs.model.BbsWriteInfModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinForumDiskModel;
import jp.groupsession.v2.bbs.model.BulletinSearchModel;
import jp.groupsession.v2.bbs.pdf.BbsListPdfModel;
import jp.groupsession.v2.bbs.pdf.BbsListPdfUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 掲示板 スレッド一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs060Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public Bbs060Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param adminFlg システム管理者・プラグイン管理者フラグ
     * @throws Exception 実行例外
     */
    public void setInitData(
            String cmd, Bbs060ParamModel paramMdl, Connection con,
            int userSid, boolean adminFlg)
                    throws Exception {
        log__.debug("START");
        String searchDspID = NullDefault.getString(paramMdl.getSearchDspID(), "");
        if (cmd.equals("backPage")
                && searchDspID.equals(GSConstBulletin.SERCHDSPID_THREADLIST)) {
            paramMdl.setThreadSid(0);
        }
        log__.debug("End");
    }

    /**
     * <br>[機  能] スレッドの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid ユーザSID
     * @param now 現在日時
     * @return スレッド件数
     * @throws SQLException SQL実行例外
     */
    private int __getThreadCount(
            Connection con, int bfiSid, UDate now)
                    throws SQLException {

        BulletinDao btDao = new BulletinDao(con);
        return btDao.getThreadCount(bfiSid, now);

    }

    /**
     * <br>[機  能] 閲覧フォーラムのアイコンバイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return アイコンバイナリSID
     * @throws SQLException SQL実行例外
     */
    public Long getIcoBinSid(
            Bbs060ParamModel paramMdl, Connection con)
                    throws SQLException {

        //閲覧フォーラムのアイコンバイナリSIDを取得する
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        Long icoBinSid = bfiDao.selectIcoBinSid(paramMdl.getBbs010forumSid());
        paramMdl.setBbs060BinSid(icoBinSid);

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
    public boolean cheIcoHnt(
            Connection con, int bfiSid, Long imgBinSid)
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
     * <br>[機  能] フォーラムSIDとアイコンバイナリSIDを照合する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param imgBinSid 画像バイナリSID
     * @return true:照合OK false:照合NG
     * @throws SQLException SQL実行例外
     */
    public boolean checkBbsBodyBin(
            Connection con, int bfiSid, Long imgBinSid)
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
     * <br>[機  能] 表示フォーラム内のスレッドを全て既読にする(掲示予定のものは除く)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void allReadThread(
            Connection con, Bbs060ParamModel paramMdl, int userSid)
                    throws SQLException {

        int bfiSid = paramMdl.getBbs010forumSid();
        BbsThreViewDao dao = new BbsThreViewDao(con);
        dao.updateAllReadThreadInForum(bfiSid, userSid, userSid, new UDate());
        dao.insertAllReadThreadInForum(bfiSid, userSid, userSid, new UDate());
    }

    /**
     * <br>[機  能] 指定したスレッドを未読状態に戻す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void unReadThread(
            Connection con, Bbs060ParamModel paramMdl, int userSid)
                    throws SQLException {
        int bfiSid = paramMdl.getBbs010forumSid();
        String[] sid = paramMdl.getBbs060ChkInfSid();
        if (sid == null || sid.length < 1) {
            return;
        }
        BbsThreViewDao dao = new BbsThreViewDao(con);
        dao.updateUnReadThreadInForum(bfiSid, sid, userSid, userSid, new UDate());
    }

    /**
     * <br>[機  能] スレッドの情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param adminFlg システム管理者・プラグイン管理者フラグ
     * @param buMdl ユーザ情報モデル
     * @throws SQLException SQL実行時例外
     * @return JSONObject
     */
    public JSONObject getThreadData(
            Connection con, Bbs060ParamModel paramMdl, int userSid,
            boolean adminFlg, BaseUserModel buMdl)
                    throws SQLException {
        JSONObject ret = new JSONObject();

        //フォーラム名を設定
        BbsBiz bbsBiz = new BbsBiz(con);
        int bfiSid = paramMdl.getBbs010forumSid();
        BulletinDspModel btDspMdl = bbsBiz.getForumData(con, bfiSid);
        paramMdl.setBbs060forumName(StringUtilHtml.transToHTmlPlusAmparsant(btDspMdl.getBfiName()));

        //フォーラムのディスク容量警告を設定
        BulletinForumDiskModel diskData = bbsBiz.getForumDiskData(con, bfiSid);
        if (bbsBiz.checkForumWarnDisk(diskData)) {
            paramMdl.setBbs060forumWarnDisk(diskData.getBfiWarnDiskTh());
        } else {
            paramMdl.setBbs060forumWarnDisk(0);
        }

        //掲示板個人情報を取得
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        UDate now = new UDate();

        //最大件数
        int threCnt = __getThreadCount(con, bfiSid, now);
        int maxThreCnt = bUserMdl.getBurThreCnt();

        //ページ調整
        int maxPage = threCnt / maxThreCnt;
        if ((threCnt % maxThreCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbs060page1();
        if (page < 1 || maxPage < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs060page1(page);
        paramMdl.setBbs060page2(page);
        //ページコンボ設定
        paramMdl.setBbsPageLabel(PageUtil.createPageOptions(threCnt, maxThreCnt));

        //スレッド一覧を取得する
        BulletinDao btDao = new BulletinDao(con);
        int start = (page - 1) * maxThreCnt + 1;
        int end = start + maxThreCnt - 1;
        List<BulletinDspModel> threadList =
                btDao.getThreadList(bfiSid, userSid, now,
                        bUserMdl.getBurNewCnt(), start, end,
                        Integer.parseInt(paramMdl.getBbs060sortKey()),
                        Integer.parseInt(paramMdl.getBbs060orderKey()),
                        bbsBiz.getBfiSidForMemberInfo(con, bfiSid));

        //HTMLへの書き出し用のエスケープ処理
        List<BulletinDspModel> escapedThreadList = new ArrayList<BulletinDspModel>();
        for (BulletinDspModel bdMdl : threadList) {
            bdMdl.setBtiTitle(StringUtilHtml.transToHTmlPlusAmparsant(
                    bdMdl.getBtiTitle()));
            bdMdl.setGrpName(StringUtilHtml.transToHTmlPlusAmparsant(
                    bdMdl.getGrpName()));
            bdMdl.setUserName(StringUtilHtml.transToHTmlPlusAmparsant(
                    bdMdl.getUserName()));
            escapedThreadList.add(bdMdl);
        }

        paramMdl.setThreadList(escapedThreadList);

        //サブコンテンツの情報を取得
        __getSubContentData(con, paramMdl, btDspMdl, bUserMdl, userSid, adminFlg);

        //フォーラムのメンバー数を取得する
        paramMdl.setForumMemberCount(
                String.valueOf(bbsBiz.getForumMemberCount(con, bfiSid)));

        //フォーラムSIDからアイコンバイナリSIDを取得する
        Bbs060Biz bbs060Biz = new Bbs060Biz();
        Long binSid = bbs060Biz.getIcoBinSid(paramMdl, con);
        paramMdl.setBbs060BinSid(binSid);

        //予約投稿数を取得
        paramMdl.setBbs060scheduledPostNum(
                btDao.getScheduledPost(now, bfiSid, userSid, adminFlg));

        //新規スレッドボタン表示フラグ
        paramMdl.setBbs060createDspFlg(bbsBiz.isForumEditAuth(con, bfiSid, buMdl));

        ret = JSONObject.fromObject(paramMdl);

        return ret;
    }


    /**
     * <br>[機  能] サブコンテンツの情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param btDspMdl フォーラム情報
     * @param bUserMdl 個人設定情報
     * @param userSid ユーザSID
     * @param adminFlg 管理者か否か true:管理者, false:一般ユーザ
     * @throws SQLException SQL実行時例外
     */
    private void __getSubContentData(
            Connection con, Bbs060ParamModel paramMdl, BulletinDspModel btDspMdl,
            BbsUserModel bUserMdl, int userSid, boolean adminFlg)
                    throws SQLException {

        BulletinDao btDao = new BulletinDao(con);

        //[サブコンテンツ]フォーラム一覧表示フラグ判定
        int bbs060forumDspFlg = GSConstBulletin.BBS_FORUM_NOT_DSP;
        if (bUserMdl.getBurSubForum() == GSConstBulletin.BBS_MIDOKU_TRD_DSP) {

            //フォーラムリスト取得
            Bbs010Biz bbs010Biz = new Bbs010Biz();
            List<BulletinDspModel> forumList = bbs010Biz.getForumTreeDataList(
                    con, userSid, bUserMdl.getBurNewCnt(), 0, 0, adminFlg, false);
            List<BulletinDspModel> escapedForumList = new ArrayList<BulletinDspModel>();
            for (BulletinDspModel bdMdl : forumList) {
                bdMdl.setBfiName(StringUtilHtml.transToHTmlPlusAmparsant(
                        bdMdl.getBfiName()));
                bdMdl.setBfiCmt(StringUtilHtml.transToHTmlPlusAmparsant(
                        bdMdl.getBfiCmt()));
                escapedForumList.add(bdMdl);
            }
            paramMdl.setForumList(escapedForumList);
            bbs060forumDspFlg = GSConstBulletin.BBS_FORUM_DSP;
        }
        paramMdl.setBbs060forumDspFlg(bbs060forumDspFlg);

        //[サブコンテンツ]未読スレッド表示フラグ判定
        List<BulletinDspModel> midokuList = new ArrayList<BulletinDspModel>();
        if (bUserMdl.getBurSubUnchkThre() == GSConstBulletin.BBS_FORUM_DSP) {

            //ユーザがメンバーに含まれているフォーラムのSIDを取得
            BbsBiz bbsBiz = new BbsBiz();
            List<Integer> groupSidList = new ArrayList<Integer>();
            List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfUser(con, userSid);
            for (BbsForInfModel bfiMdl : bfiMdlList) {
                groupSidList.add(bfiMdl.getBfiSid());
            }

            //未読スレッド一覧listを取得する
            midokuList =
                    btDao.getThreadList2(userSid, bUserMdl.getBurThreMainCnt(), groupSidList);
        }
        paramMdl.setNotReadThreadList(midokuList);

        //[サブコンテンツ]「全て既読にする」機能が使用可能かを判定する
        paramMdl.setBbs060mreadFlg(btDspMdl.getBfiMread() == GSConstBulletin.BBS_FORUM_MREAD_YES);
        if (paramMdl.isBbs060mreadFlg()) {
            //表示フォーラム内の未読スレッド件数を取得する
            paramMdl.setBbs060unreadCnt(btDao.getUnreadThreadCount(btDspMdl.getBfiSid(), userSid));
        }
    }

    /**
     * <br>[機  能] 投稿の情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param adminFlg 管理者か否か true:管理者, false:一般ユーザ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param buMdl ユーザ情報モデル
     * @throws SQLException SQL実行時例外
     * @return JSONObject
     * @throws IOException ユーザ画像ファイルの取得に失敗
     * @throws IOToolsException ユーザ画像ファイルの取得に失敗
     * @throws IOException
     */
    public JSONObject getPostData(
            RequestModel reqMdl, Connection con, Bbs060ParamModel paramMdl,
            int userSid, boolean adminFlg, String appRoot,
            String tempDir, BaseUserModel buMdl)
                    throws SQLException, IOException, IOToolsException {
        JSONObject ret = new JSONObject();

        int bfiSid = paramMdl.getBbs010forumSid();
        int btiSid = paramMdl.getThreadSid();

        BbsBiz biz = new BbsBiz();

        //スレッドが存在しない場合
        BulletinDspModel btDspMdl = biz.getThreadData(con, paramMdl.getThreadSid());
        if (btDspMdl == null) {
            return ret;
        }

        //パラメータ.フォーラムと取得したスレッド情報のフォーラムが一致しない場合
        if (bfiSid != btDspMdl.getBfiSid()) {
            return ret;
        }

        int limit = btDspMdl.getBtiLimit();
        UDate limitFrDate = btDspMdl.getBtiLimitFrDate();
        UDate limitToDate = btDspMdl.getBtiLimitDate();

        if (!(adminFlg || btDspMdl.getAddUserSid() == userSid)) {
            if (!biz.isForumAdmin(bfiSid, userSid, con)) {
                //システム管理者でも作成者でもフォーラム管理者でもない場合
                UDate now = new UDate();

                if (btDspMdl.getBtiLimit() == GSConstBulletin.THREAD_LIMIT_YES
                        && (now.compareDateYMDHM(limitFrDate) == UDate.LARGE
                        || now.compareDateYMDHM(limitToDate) == UDate.SMALL)) {
                    //開始日が未来に設定されている。あるいは終了日が過去に設定されている。
                    return ret;
                }
            }
        }

        //フォーラム名、スレッド名、重要度を設定
        paramMdl.setBbs060forumName(
                StringUtilHtml.transToHTmlPlusAmparsant(btDspMdl.getBfiName()));
        paramMdl.setBbs060threadTitle(
                StringUtilHtml.transToHTmlPlusAmparsant(btDspMdl.getBtiTitle()));
        paramMdl.setBbs060ThreImportance(btDspMdl.getBfiThreImportance());

        //フォーラム編集権限
        boolean editAuth = biz.isForumEditAuth(con, bfiSid, buMdl);

        //返信ボタン・引用返信ボタン表示フラグ
        int reply = GSConstBulletin.BBS_THRE_REPLY_NO;
        if (btDspMdl.getBfiReply() == GSConstBulletin.BBS_THRE_REPLY_YES) {
            if (editAuth) {
                reply = GSConstBulletin.BBS_THRE_REPLY_YES;
            }
        }
        paramMdl.setBbs060reply(String.valueOf(reply));

        if (limit == GSConstBulletin.THREAD_LIMIT_YES) {
            String strLimitDate = UDateUtil.getYymdJ(limitFrDate, reqMdl)
                    + " ～ " + UDateUtil.getYymdJ(limitToDate, reqMdl);
            paramMdl.setBbs060limitDate(strLimitDate);
        }

        //フォーラム管理者フラグ
        boolean forumAdmin = biz.isForumAdmin(bfiSid, userSid, con);

        //スレッド削除ボタン表示フラグを設定
        int showBtnFlg = BulletinDspModel.SHOWBTNFLG_NO;
        if (editAuth && (btDspMdl.getAddUserSid() == userSid || adminFlg || forumAdmin)) {
            //編集権限を持つ投稿者本人
            showBtnFlg = BulletinDspModel.SHOWBTNFLG_YES;
        }
        paramMdl.setBbs060showThreBtn(showBtnFlg);

        if (paramMdl.getBbs060showThreBtn() == BulletinDspModel.SHOWBTNFLG_NO
                && !editAuth
                && paramMdl.getBbs060reply().equals(
                        String.valueOf(GSConstBulletin.BBS_THRE_REPLY_NO))) {
            paramMdl.setBbs060btnDspFlg(BulletinDspModel.SHOWALLBTNFLG_NO);
        }

        //掲示板個人情報を取得
        BbsBiz bbsBiz = new BbsBiz(con);
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        int order = paramMdl.getBbs060postOrderKey();
        if (order != GSConstBulletin.BBS_WRTLIST_ORDER_ASC
        && order != GSConstBulletin.BBS_WRTLIST_ORDER_DESC) {
            order = bUserMdl.getBurWrtlistOrder();
            paramMdl.setBbs060postOrderKey(order);
        }
        //画像表示有無
        paramMdl.setPhotoFileDsp(bUserMdl.getBurThreImage());
        //添付ファイル画像表示有無
        paramMdl.setTempImageFileDsp(bUserMdl.getBurTempImage());

        //最大件数
        BulletinDao bbsDao = new BulletinDao(con);
        int wrtCnt = bbsDao.getWriteCount(btiSid);
        int maxWrtCnt = bUserMdl.getBurWrtCnt();
        int maxPage = wrtCnt / maxWrtCnt;
        if ((wrtCnt % maxWrtCnt) > 0) {
            maxPage++;
        }
        //ページ調整
        int page = paramMdl.getBbs060postPage1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs060postPage1(page);
        paramMdl.setBbs060postPage2(page);

        //ページコンボ設定
        if (maxPage > 1) {
            ArrayList < LabelValueBean > pageList =
                            new ArrayList < LabelValueBean >();
            for (int i = 1; i <= maxPage; i++) {
                pageList.add(new LabelValueBean(i + " / " + maxPage, Integer.toString(i)));
            }
            paramMdl.setBbsPageLabel(pageList);
        }

        BulletinDao btDao = new BulletinDao(con);
        int start = 1;
        int end = maxWrtCnt;
        if (page >= 2) {
            start += maxWrtCnt;

            start += (page - 2) * (maxWrtCnt);
            end = start + maxWrtCnt - 1;
        }

        BulletinSearchModel searchMdl = new BulletinSearchModel();
        searchMdl.setBtiSid(btiSid);
        searchMdl.setUserSid(userSid);
        searchMdl.setAdmin(adminFlg);
        searchMdl.setNewCnt(bUserMdl.getBurNewCnt());
        searchMdl.setStart(start);
        searchMdl.setEnd(end);
        searchMdl.setAppRoot(appRoot);
        searchMdl.setTempDir(tempDir);
        searchMdl.setOrderWriteDate(order);
        searchMdl.setForumAdmin(biz.isForumAdmin(bfiSid, userSid, con));
        List<BulletinDspModel> postList = btDao.getWriteList(searchMdl);
        List<BulletinDspModel> escapedPostList = new ArrayList<BulletinDspModel>();
        for (BulletinDspModel bdMdl : postList) {
            bdMdl.setBwiValuePlain(StringUtilHtml.transToHTmlPlusAmparsant(
                    bdMdl.getBwiValuePlain()));
            bdMdl.setGrpName(StringUtilHtml.transToHTmlPlusAmparsant(
                    bdMdl.getGrpName()));
            bdMdl.setUserName(StringUtilHtml.transToHTmlPlusAmparsant(
                    bdMdl.getUserName()));
            bdMdl.setPhotoFileName(StringUtilHtml.transToHTmlPlusAmparsant(
                    bdMdl.getPhotoFileName()));

            bdMdl.setBwiValue(StringUtilHtml.removeIllegalTag(bdMdl.getBwiValue()));

            //添付ファイル名のHTMLエスケープ
            List<CmnBinfModel> tmpList = bdMdl.getTmpFileList();
            if (tmpList != null && !tmpList.isEmpty()) {
                for (int tmpIdx = 0; tmpIdx < tmpList.size(); tmpIdx++) {
                    tmpList.get(tmpIdx).setBinFileName(
                            StringUtilHtml.transToHTmlPlusAmparsant(
                                    tmpList.get(tmpIdx).getBinFileName()));
                }
                bdMdl.setTmpFileList(tmpList);
            }
            escapedPostList.add(bdMdl);
        }
        paramMdl.setPostList(escapedPostList);

        //DB[投稿情報.親投稿フラグ]によってボタンの切り替えを行う
        for (int i = 0; i < paramMdl.getPostList().size(); i++) {
            if (paramMdl.getPostList().get(i).getBwiParentFlg()
                    == GSConstBulletin.BBS_PARENT_FLG_YES) {
                paramMdl.getPostList().get(i).setThdWriteFlg(1);
                break;
            }
        }

        //スレッド閲覧情報を更新
        if (con.getAutoCommit()) {
            con.setAutoCommit(false);
        }
        updateView(con, btiSid, userSid, bfiSid);
        con.setAutoCommit(true);

        //サブコンテンツの情報を取得
        __getSubContentData(con, paramMdl, btDspMdl, bUserMdl, userSid, adminFlg);

        //スレッドのURLを作成
        paramMdl.setThreadUrl(
                bbsBiz.createThreadUrl(reqMdl, paramMdl.getBbs010forumSid(),
                                    paramMdl.getThreadSid()));

        //フォーラムのメンバー数を取得する
        paramMdl.setForumMemberCount(
                String.valueOf(bbsBiz.getForumMemberCount(con, bfiSid)));
        //既読件数を取得する
        paramMdl.setReadedCnt(
                btDao.getForumNum(biz.getBfiSidForMemberInfo(con, bfiSid), btiSid));

        //フォーラムSIDからアイコンバイナリSIDを取得する
        Long binSid = getIcoBinSid(paramMdl, con);
        paramMdl.setBbs060BinSid(binSid);

        ret = JSONObject.fromObject(paramMdl);

        return ret;
    }

    /**
     * <br>[機  能] スレッド閲覧情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param btiSid スレッドSID
     * @param userSid ユーザSID
     * @param bfiSid フォーラムSID
     * @throws SQLException SQL実行例外
     */
    public void updateView(
            Connection con, int btiSid, int userSid, int bfiSid)
                    throws SQLException {

        boolean commit = false;
        try {
            UDate now = new UDate();
            BbsThreViewDao threViewDao = new BbsThreViewDao(con);
            BbsThreViewModel threViewMdl = new BbsThreViewModel();
            threViewMdl.setBtiSid(btiSid);
            threViewMdl.setUsrSid(userSid);
            threViewMdl.setBivViewKbn(1);
            threViewMdl.setBivEuid(userSid);
            threViewMdl.setBivEdate(now);

            if (threViewDao.update(threViewMdl) <= 0) {
                threViewMdl.setBfiSid(bfiSid);
                threViewMdl.setBivAuid(userSid);
                threViewMdl.setBivAdate(now);
                threViewDao.insert(threViewMdl);
            }

            commit = true;
        } catch (SQLException e) {
            log__.warn("スレッド閲覧情報の更新失敗", e);
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] スレッド情報の削除処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void deleteThreadData(
            int bfiSid, int btiSid, Connection con, int userSid)
                    throws SQLException {

        UDate now = new UDate();

        //バイナリー情報の論理削除
        BulletinDao bbsDao = new BulletinDao(con);
        bbsDao.deleteBinfForThread(btiSid);

        //投稿添付情報の削除
        bbsDao.deleteBbsBinInThread(btiSid);

        //投稿情報の削除
        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con);
        bbsWriteInfDao.deleteWriteInThread(btiSid);

        //スレッド情報の削除
        BbsThreInfDao bbsThreInfDao = new BbsThreInfDao(con);
        bbsThreInfDao.delete(btiSid);

        //予約投稿リストに存在している場合、削除
        BbsThreRsvDao bbsThreRsvDao = new BbsThreRsvDao(con);
        bbsThreRsvDao.delete(btiSid);

        //スレッド閲覧情報の削除
        BbsBiz bbsBiz = new BbsBiz();
        bbsBiz.deleteThreadView(con, btiSid);

        //スレッド集計情報の削除
        BbsThreSumDao bbsThreSumDao = new BbsThreSumDao(con);
        bbsThreSumDao.delete(btiSid);

        //フォーラム集計情報の更新
        bbsBiz.updateBbsForSum(con, bfiSid, userSid, now);
    }

    /**
     * <br>[機  能] 投稿情報の削除処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void deletePostData(
            Bbs060ParamModel paramMdl,
            Connection con,
            int userSid)
                    throws Exception {
        UDate now = new UDate();

        int bwiSid = paramMdl.getBbs060postSid();

        //バイナリー情報の論理削除
        BulletinDao bbsDao = new BulletinDao(con);
        bbsDao.deleteBinfForWrite(bwiSid);

        //投稿情報の削除
        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con);
        bbsWriteInfDao.delete(bwiSid);

        //投稿添付情報の削除
        BbsBinDao bbsBinDao = new BbsBinDao(con);
        bbsBinDao.delete(bwiSid);

        //スレッド集計情報の更新
        BbsBiz bbsBiz = new BbsBiz();
        int btiSid = paramMdl.getThreadSid();
        bbsBiz.updateBbsThreSum(con, btiSid, userSid, now, now);

        //フォーラム集計情報の更新
        int bfiSid = paramMdl.getBbs010forumSid();
        bbsBiz.updateBbsForSum(con, bfiSid, userSid, now);
    }

    /**
     * <br>[機  能] ユーザがスレッドを削除できるかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param btiSid スレッドSID
     * @param userSid ユーザSID
     * @return true:スレッド削除可能 false:スレッド削除不可
     * @throws SQLException SQL例外発生例外
     */
    public boolean canDeleteThread(
            Connection con, int btiSid, int userSid)
                    throws SQLException {

        BbsBiz bbsBiz = new BbsBiz();

        //フォーラム管理者の場合、スレッドの削除を許可する
        BbsThreInfDao threadDao = new BbsThreInfDao(con);
        BbsThreInfModel threadMdl = threadDao.select(btiSid);
        if (bbsBiz.isForumAdmin(threadMdl.getBfiSid(), userSid, con)) {
            return true;
        }

        BulletinDspModel bbsMdl = bbsBiz.getThreadData(con, btiSid);
        return bbsMdl.getAddUserSid() == userSid;
    }

    /**
     * <br>[機  能] ユーザが投稿を削除できるかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bwiSid 投稿SID
     * @param userSid ユーザSID
     * @return true:投稿削除可能 false:投稿削除不可
     * @throws SQLException SQL例外発生例外
     */
    public boolean canDeletePost(
            Connection con, int bwiSid, int userSid)
                    throws SQLException {

        BbsBiz bbsBiz = new BbsBiz();

        //フォーラム管理者の場合、スレッドの削除を許可する
        BbsWriteInfDao writeDao = new BbsWriteInfDao(con);
        BbsWriteInfModel writeMdl = writeDao.select(bwiSid);
        if (bbsBiz.isForumAdmin(writeMdl.getBfiSid(), userSid, con)) {
            return true;
        }

        BulletinDspModel btMdl = bbsBiz.getWriteData(con, bwiSid);
        return btMdl.getAddUserSid() == userSid;
    }

    /**
     * <br>[機  能] 添付ファイルバイナリSIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bwiSid 投稿SID
     * @param tmpBinSid 添付ファイルバイナリSID
     * @param forumSid フォーラムSID
     * @param threadSid スレッドSID
     * @return フォーラム件数
     * @throws SQLException SQL実行例外
     */
    public boolean cheTmpHnt(
            Connection con, int bwiSid,
            Long tmpBinSid, int forumSid, int threadSid)
                    throws SQLException {

        boolean bwiCheckFlg = false;

        //添付ファイルバイナリSIDチェック
        BulletinDao bbsDao = new BulletinDao(con);
        boolean existForTmpFlg = bbsDao.existBbsWriTmp(bwiSid, tmpBinSid, forumSid, threadSid);

        if (existForTmpFlg) {
            bwiCheckFlg = true;
        }

        return bwiCheckFlg;
    }

    /**
     * <br>[機  能] 投稿情報の取得
     * <br>[解  説] 結果true:親 false:子
     * <br>[備  考]
     * @param con コネクション
     * @param bwiSid 投稿SID
     * @return 親か子の判定
     * @throws SQLException SQL実行例外
     */
    public boolean getDbTokoInf(
            Connection con, int bwiSid)
                    throws SQLException {

        boolean result = false;
        BbsWriteInfModel bbsWriteInfModel = new BbsWriteInfModel();

        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con);

        bbsWriteInfModel = bbsWriteInfDao.select(bwiSid);

        if (bbsWriteInfModel.getBwiParentFlg() == GSConstBulletin.BBS_PARENT_FLG_YES) {
            result = true;
        }

        return result;
    }

    /**
     * <br>[機  能]選択されたスレッドの投稿一覧をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトパス
     * @param userSid ユーザーSID
     * @param admin 管理者 true:管理者, false:一般ユーザー
     * @return  pdfMdl PDFモデル
     * @throws IOException IO実行時例外
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイル操作実行例外
     */
    public BbsListPdfModel createBbsListPdf(
            Connection con,
            RequestModel reqMdl,
            Bbs060ParamModel paramMdl,
            String appRootPath,
            String tempDir,
            int userSid,
            boolean admin)
                    throws IOException, IOToolsException, SQLException {
        OutputStream oStream = null;

        //PDF出力情報をPDF出力用モデルに格納
        BbsListPdfModel pdfMdl = new BbsListPdfModel();
        pdfMdl = __getBbsPdfDataList(con, paramMdl, userSid, admin, appRootPath, tempDir);

        String downloadFileName = pdfMdl.getBfiName() + "_" + pdfMdl.getBtiTitle();
        String encOutBookName = __fileNameCheck(downloadFileName) + ".pdf";

        String saveFileName = "pdfBbs_" + pdfMdl.getBfiSid() + "_" + pdfMdl.getBtiSid() + ".pdf";
        String escSaveFileName = __fileNameCheck(saveFileName);

        pdfMdl.setFileName(encOutBookName);
        pdfMdl.setSaveFileName(escSaveFileName);

        try {
            IOTools.isDirCheck(tempDir, true);
            oStream = new FileOutputStream(tempDir + escSaveFileName);

            //PDFファイル生成を行う
            BbsListPdfUtil pdfUtil = new BbsListPdfUtil(reqMdl);
            pdfUtil.createBbsReport(appRootPath, oStream, pdfMdl);

        } catch (Exception e) {
            log__.error("投稿一覧PDF出力に失敗しました。", e);

        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        return pdfMdl;
    }

    /**
     * <br>[機  能] PDF出力用のデータを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザーSID
     * @param admin 管理者 true:管理者, false:一般ユーザ
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトパス
     * @return PDF出力データ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイル操作実行例外
     * @throws IOException 入出力実行例外
     */
    private BbsListPdfModel __getBbsPdfDataList(
            Connection con, Bbs060ParamModel paramMdl, int userSid,
            boolean admin, String appRootPath, String tempDir)
                    throws SQLException, IOToolsException, IOException {

        BbsListPdfModel ret = new BbsListPdfModel();
        int bfiSid = paramMdl.getBbs010forumSid();
        int btiSid = paramMdl.getThreadSid();

        BbsBiz biz = new BbsBiz();

        //フォーラム名、スレッド名を設定
        BulletinDspModel btDspMdl = biz.getThreadData(con, btiSid);
        //フォーラムSID
        ret.setBfiSid(bfiSid);
        //フォーラム名
        ret.setBfiName(btDspMdl.getBfiName());
        //スレッドSID
        ret.setBtiSid(btiSid);
        //スレッド名
        ret.setBtiTitle(btDspMdl.getBtiTitle());
        //重要度
        ret.setBfiThreImportance(btDspMdl.getBfiThreImportance());

        //昇順に固定
        int order = paramMdl.getBbs060postOrderKey();

        //最大件数
        BulletinDao bbsDao = new BulletinDao(con);
        int wrtCnt = bbsDao.getWriteCount(btiSid);

        //終了は常に最大件数にする
        BulletinDao btDao = new BulletinDao(con);
        int start = 1;
        int end = wrtCnt;

        //投稿一覧情報を取得
        BulletinSearchModel searchMdl = new BulletinSearchModel();
        searchMdl.setBtiSid(btiSid);
        searchMdl.setUserSid(userSid);
        searchMdl.setAdmin(admin);
        searchMdl.setStart(start);
        searchMdl.setEnd(end);
        searchMdl.setAppRoot(appRootPath);
        searchMdl.setTempDir(tempDir);
        searchMdl.setOrderWriteDate(order);
        searchMdl.setForumAdmin(biz.isForumAdmin(bfiSid, userSid, con));
        List<BulletinDspModel> bbsDspList = btDao.getWriteList(searchMdl);

        //投稿一覧
        ret.setWriteList(bbsDspList);

        return ret;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String __fileNameCheck(String fileName) {
            String escName = fileName;
            escName = StringUtilHtml.replaceString(escName, "/", "");
            escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
            escName = StringUtilHtml.replaceString(escName, "?", "");
            escName = StringUtilHtml.replaceString(escName, "*", "");
            escName = StringUtilHtml.replaceString(escName, ":", "");
            escName = StringUtilHtml.replaceString(escName, "|", "");
            escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
            escName = StringUtilHtml.replaceString(escName, "<", "");
            escName = StringUtilHtml.replaceString(escName, ">", "");
            escName = StringUtilHtml.replaceString(escName, ".", "");
            escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内
        return escName;
    }
}
