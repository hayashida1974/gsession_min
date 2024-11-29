package jp.groupsession.v2.bbs.bbs010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs030.Bbs030Biz;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinNewBbsModel;
import jp.groupsession.v2.bbs.model.BulletinSearchModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 掲示板 フォーラム一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs010Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @throws Exception 実行例外
     */
    public void setInitData(Bbs010ParamModel paramMdl, Connection con, int userSid, boolean admin)
                    throws Exception {

        //掲示板個人情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);
        if (admin) {
            paramMdl.setBbs010AdminFlg(1);
        } else {
            paramMdl.setBbs010AdminFlg(0);
        }

        //最大件数
        int forumCnt = __getForumCount(con, userSid, false);
        //ページ調整
        int maxPage = forumCnt / bUserMdl.getBurForCnt();
        if ((forumCnt % bUserMdl.getBurForCnt()) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbs010page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs010page1(page);
        paramMdl.setBbs010page2(page);

        //ページコンボ設定
        paramMdl.setBbsPageLabel(PageUtil.createPageOptions(forumCnt, bUserMdl.getBurForCnt()));

        //フォーラムリスト取得
        int start = (page - 1) * bUserMdl.getBurForCnt();
        int end = start + bUserMdl.getBurForCnt() - 1;
        paramMdl.setForumList(getForumTreeDataList(
                con, userSid, bUserMdl.getBurNewCnt(), start, end, admin, false));

        //未読・既読情報を持つフォーラムリスト取得
        List<BulletinDspModel> forumList =
                getNoPageForumDataList(con, userSid, admin, -1);

        ArrayList<BulletinNewBbsModel> shinThredList =
                                       new ArrayList<BulletinNewBbsModel>();
        if (bUserMdl.getBurSubNewThre() == GSConstBulletin.BBS_THRED_DSP) {
            //メンバー情報を保持するフォーラムのリスト
            List<Integer> memberForumList = new ArrayList<Integer>();
            List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfUser(con, userSid);
            for (BbsForInfModel bfiMdl : bfiMdlList) {
                memberForumList.add(bfiMdl.getBfiSid());
            }

            //新着スレッド一覧を取得する
            BulletinDao btDao = new BulletinDao(con);
            List < BulletinDspModel > thredList =
                    btDao.getThreadList(
                            userSid, false, 10, Integer.MAX_VALUE, null, 0, memberForumList);

            if (!thredList.isEmpty()) {
                //表示用Modelに格納
                for (BulletinDspModel mdl : thredList) {
                    BulletinNewBbsModel newThredMdl = new BulletinNewBbsModel();
                    newThredMdl.setBfiSid(mdl.getBfiSid());
                    //半角文字対応
                    String name = mdl.getBfiName();
                    name = StringUtilHtml.transToHTmlPlusAmparsant(name);
                    newThredMdl.setBfiName(name);
                    newThredMdl.setBtiSid(mdl.getBtiSid());
                    //半角文字対応
                    String title = mdl.getBtiTitle();
                    title = StringUtilHtml.transToHTmlPlusAmparsant(title);
                    newThredMdl.setBtiTitle(title);
                    newThredMdl.setBfiSid(mdl.getBfiSid());
                    newThredMdl.setUserSid(mdl.getUserSid());
                    newThredMdl.setUserName(mdl.getUserName());
                    newThredMdl.setUserJkbn(mdl.getUserJkbn());
                    newThredMdl.setUserYukoKbn((mdl.getUserYukoKbn()));
                    newThredMdl.setGrpSid(mdl.getGrpSid());
                    newThredMdl.setGrpName(mdl.getGrpName());
                    newThredMdl.setGrpJkbn(mdl.getGrpJkbn());
                    newThredMdl.setThreImportance(mdl.getBfiThreImportance());
                    newThredMdl.setBtsTempflg(mdl.getBtsTempflg());

                    //フォーラムのアイコンバイナリSID取得
                    BulletinDspModel forMdl = btDao.getForumData(mdl.getBfiSid());
                    newThredMdl.setImgBinSid(forMdl.getBinSid());

                    String writeDate = mdl.getStrWriteDate();
                    //表示用に日付を整形
                    String strWriteDate = writeDate.substring(5, 21);
                    newThredMdl.setStrWriteDate(strWriteDate);
                    //スレッド未読フラグ
                    newThredMdl.setT_ReadFlg(mdl.getReadFlg());
                    //フォーラム未読フラグ
                    //フォーラム一覧からSIDの一致するフォーラムの状態を取得
                    for (BulletinDspModel forMdl2 : forumList) {
                        if (forMdl2.getBfiSid() == mdl.getBfiSid()) {
                            newThredMdl.setF_ReadFlg(forMdl2.getReadFlg());
                            break;
                        }
                    }
                    shinThredList.add(newThredMdl);
                }
            }
        }
        paramMdl.setShinThredList(shinThredList);
    }

    /**
     * <br>[機  能] フォーラム情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者フラグ true:管理者 false:管理者以外
     * @param parentSid 親フォーラムSID -1:指定なし
     * @return フォーラム情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<BulletinDspModel> getNoPageForumDataList(
            Connection con, int userSid, boolean admin, int parentSid)
                    throws SQLException {

        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> usersForumWrite =
                bbsBiz.getForumListOfUser(con, userSid, GSConstBulletin.ACCESS_KBN_WRITE);
        List<BbsForInfModel> usersForumAll = bbsBiz.getForumListOfUser(con, userSid);

        BulletinDao btDao = new BulletinDao(con);
        int start = 0;
        int end = 0;
        int newCnt = 0;
        return btDao.getForumList(userSid, false, new UDate(),
                newCnt, start, end, admin, parentSid, usersForumWrite, usersForumAll);
    }

    /**
     * <br>[機  能] フォーラム情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者フラグ true:管理者 false:管理者以外
     * @param page ページ
     * @param bUserMdl 掲示板個人情報
     * @param parentSid 親フォーラムSID -1:指定なし
     * @return フォーラム情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<BulletinDspModel> getForumDataList(
            Connection con, int userSid, boolean admin,
            int page, BbsUserModel bUserMdl, int parentSid)
                    throws SQLException {

        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> usersForumWrite =
                bbsBiz.getForumListOfUser(con, userSid, GSConstBulletin.ACCESS_KBN_WRITE);
        List<BbsForInfModel> usersForumAll = bbsBiz.getForumListOfUser(con, userSid);

        BulletinDao btDao = new BulletinDao(con);
        int start = (page - 1) * bUserMdl.getBurForCnt();
        int end = 0;
        if (bUserMdl.getBurForCnt() > 0) {
            end = start + bUserMdl.getBurForCnt() - 1;
        }
        return btDao.getForumList(userSid, false, new UDate(),
                bUserMdl.getBurNewCnt(), start, end, admin, parentSid,
                usersForumWrite, usersForumAll);
    }

    /**
     * <br>[機  能] 検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @return 検索結果件数
     * @throws Exception 実行例外
     */
    public int getSearchCount(Bbs010ParamModel paramMdl, Connection con, int userSid, boolean admin)
            throws Exception {

        //掲示板個人情報を取得
        BulletinSearchModel searchMdl = new BulletinSearchModel();
        searchMdl.setUserSid(userSid);
        searchMdl.setAdmin(admin);
        searchMdl.setNow(new UDate());

        searchMdl.setKeyword(paramMdl.getS_key());

        BbsBiz bbsBiz = new BbsBiz();
        return bbsBiz.getForumSearchCount(con, searchMdl);
    }

    /**
     * <br>[機  能] フォーラムの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @return フォーラム件数
     * @throws SQLException SQL実行例外
     */
    private int __getForumCount(Connection con, int userSid, boolean admin)
            throws SQLException {

        BbsBiz bbsBiz = new BbsBiz();
        return bbsBiz.getForumCount(con, userSid, admin, true);
    }

    /**
     * <br>[機  能] フォーラムSIDとアイコンバイナリSIDを照合する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param buModel セッションユーザ情報
     * @param bfiSid フォーラムSID
     * @param imgBinSid 画像バイナリSID
     * @return true:照合OK false:照合NG
     * @throws SQLException SQL実行例外
     */
    public boolean cheIcoHnt(
            Connection con, BaseUserModel buModel, int bfiSid, Long imgBinSid)
                    throws SQLException {

        boolean icoCheckFlg = false;

        //フォーラム閲覧権限のチェック
        BbsBiz bbsBiz = new BbsBiz();
        boolean existForUsrFlg = bbsBiz.isForumViewAuth(con, bfiSid, buModel.getUsrsid());

        //管理者権限のチェック
        CommonBiz cmnBiz = new CommonBiz();
        boolean isAdmin = cmnBiz.isPluginAdmin(con, buModel, GSConstBulletin.PLUGIN_ID_BULLETIN);

        if (!existForUsrFlg && !isAdmin) {
            return icoCheckFlg;
        }

        //フォーラムSIDとアイコンバイナリSIDの組み合わせチェック
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        boolean existForIcoFlg = bfiDao.existBbsForIco(bfiSid, imgBinSid);

        if (existForIcoFlg) {
            icoCheckFlg = true;
        }

        return icoCheckFlg;
    }

    /**
     * <br>[機  能] 階層化用のフォーラムのデータリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param newCnt new表示日数
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @param adminFlg 管理者か否か true:管理者, false:一般ユーザ
     * @param adminDisp 管理者設定画面用の表示か否か true:管理者設定用画面 false:通常画面
     * @return 階層化用のフォーラムのデータリスト
     */
    public List<BulletinDspModel> getForumTreeDataList(
            Connection con, int userSid, int newCnt,
            int start, int end, boolean adminFlg, boolean adminDisp) {
        List<BulletinDspModel> ret = new ArrayList<BulletinDspModel>();
        try {
            //第1階層のフォーラム情報を取得
            BbsBiz bbsBiz = new BbsBiz();
            List<BbsForInfModel> bfiMdlList =
                    bbsBiz.getSortedChildForum(
                            con, GSConstBulletin.BBS_DEFAULT_PFORUM_SID, adminDisp, userSid);

            int count = 0;
            List<BbsForInfModel> topForumList = new ArrayList<BbsForInfModel>();
            for (BbsForInfModel mdl : bfiMdlList) {
                //1ページに表示する分の第1階層フォーラムのSIDを取得
                if (count >= start) {
                    topForumList.add(mdl);
                }
               if (end > 0 && count >= end) {
                   break;
               }
               ++count;
            }

            //ページに表示する分のフォーラムSIDを取得
            List<BbsForInfModel> pageForumList = new ArrayList<BbsForInfModel>();
            pageForumList.addAll(topForumList);
            Bbs030Biz bbs030Biz = new Bbs030Biz();

            //ページに表示するフォーラムの内、下位のフォーラムが存在するフォーラムを抽出する
            List<Integer> topForumSidList = new ArrayList<Integer>();
            for (BbsForInfModel topForum : topForumList) {
                topForumSidList.add(topForum.getBfiSid());
            }

            //下位のフォーラム情報を取得する
            BbsForInfDao forInfDao = new BbsForInfDao(con);
            List<Integer> existsChildList = forInfDao.extractParentForum(topForumSidList);
            for (int topForumSid : topForumSidList) {
                //下位のフォーラム情報を持つフォーラムのみを対象とする
                if (existsChildList.contains(topForumSid)) {
                    bbs030Biz.getChildForum(
                            con, pageForumList, topForumSid, adminDisp, userSid);
                }
            }

            List<BbsForInfModel> usersForumWrite =
                    bbsBiz.getForumListOfUser(con, userSid, GSConstBulletin.ACCESS_KBN_WRITE);

            //リスト取得
            BulletinDao bbsDao = new BulletinDao(con);
            List<BulletinDspModel> forumTreeDataList =
                    bbsDao.getForumListWithHierarchy(
                            userSid, new UDate(), newCnt,
                            0, 0, adminFlg, usersForumWrite, pageForumList);

            ret = forumTreeDataList;

        } catch (SQLException e) {
            log__.error("SQLエラー", e);
        }
        return ret;
    }
}
