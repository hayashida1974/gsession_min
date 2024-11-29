package jp.groupsession.v2.bbs.bbs020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs010.Bbs010Biz;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;

/**
 * <br>[機  能] 掲示板 フォーラム管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs020Biz {

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
    public void setInitData(
            Bbs020ParamModel paramMdl, Connection con, int userSid, boolean admin)
                    throws Exception {
        //掲示板個人情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        //最大件数
        int forumCnt = __getForumCount(con, userSid, true);
        //ページ調整
        int maxPage = forumCnt / bUserMdl.getBurForCnt();
        if ((forumCnt % bUserMdl.getBurForCnt()) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbs020page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs020page1(page);
        paramMdl.setBbs020page2(page);

        //ページコンボ設定
        paramMdl.setBbsPageLabel(PageUtil.createPageOptions(forumCnt, bUserMdl.getBurForCnt()));

        //フォーラムリスト取得
        int start = (page - 1) * bUserMdl.getBurForCnt();
        int end = start + bUserMdl.getBurForCnt() - 1;
        Bbs010Biz bbs010Biz = new Bbs010Biz();
        List<BulletinDspModel> forumList = bbs010Biz.getForumTreeDataList(
                con, userSid, bUserMdl.getBurNewCnt(), start, end, admin, true);
        paramMdl.setForumList(forumList);
    }

    /**
     * <br>[機  能] フォーラム情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void deleteForumData(Bbs020ParamModel paramMdl, Connection con)
    throws Exception {

        //ラジオボタンの値を設定する。
        int indexRadio = paramMdl.getBbs020indexRadio();
        BbsForInfDao forDao = new BbsForInfDao(con);
        BbsForInfModel bean = new BbsForInfModel();
        bean.setBfiSid(paramMdl.getBbs020forumSid());
        BbsForInfModel model = forDao.select(bean);
        int delIndex = model.getBfiSort();
        if (indexRadio > delIndex) {
            paramMdl.setBbs020indexRadio(indexRadio - 1);
        } else if (indexRadio == delIndex) {
            paramMdl.setBbs020indexRadio(1);
        }

        //削除処理
        BulletinDao btDao = new BulletinDao(con);
        btDao.deleteForumData(paramMdl.getBbs020forumSid());

        //並び順の変更を行う。
        List<BbsForInfModel> forList = forDao.select(delIndex);
        if (forList != null) {
            for (BbsForInfModel mdl : forList) {
                mdl.setBfiSort(mdl.getBfiSort() - 1);
                forDao.updateBBSSort(mdl);
            }
        }
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
     * <br>[機  能] フォーラムの並び順が変更可能か判定し、可能であれば変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param mode モード
     * @param userSid ユーザSID
     * @param admin 管理者権限 true：管理者ユーザ、false：一般ユーザ
     * @throws Exception 実行例外
     */
    public void updateSort(
            Bbs020ParamModel paramMdl, Connection con, String mode,
            int userSid, boolean admin)
                    throws Exception {
        
        final BbsForInfDao dao = new BbsForInfDao(con);

        //選択された項目のSID
        int motoSid = paramMdl.getBbs020indexRadio();
        
        SortChangeBiz<BbsForInfModel> sortBiz =
                SortChangeBiz.<BbsForInfModel> builder()
                .setFuncTargetList(() -> {
                    BbsForInfModel bfiMdl = new BbsForInfModel();
                    bfiMdl.setBfiSid(motoSid);
                    bfiMdl = dao.select(bfiMdl);
                    BbsBiz bbsBiz = new BbsBiz();
                    List<BbsForInfModel> ret = null;
                    if (bfiMdl != null) {
                        ret = bbsBiz.getSortedChildForum(
                                con, bfiMdl.getBfiParentSid(), true, userSid);
                    }
                    return ret;
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getBfiSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getBfiSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getBfiSid() == m2.getBfiSid()) {
                        return 0;
                    } else {
                        return (m1.getBfiSid() - m2.getBfiSid()) 
                                / Math.abs(m1.getBfiSid() - m2.getBfiSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setBfiSort(newSort);
                    dao.updateBBSSort(m);
                })
                .build();
        
        if (mode.equals(GSConstBulletin.FORUM_SORT_UP)) {
            sortBiz.up();
        } else if (mode.equals(GSConstBulletin.FORUM_SORT_DOWN)) {
            sortBiz.down();
        }
    }

    /**
     * <br>[機  能] フォーラムの表示順を修正します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param parentSid 親フォーラムSID
     * @throws SQLException SQL実行時例外
     */
    public void fixForumSort(
            Connection con,
            int parentSid)
                    throws SQLException {
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiList =  bbsBiz.getSortedChildForum(con, parentSid, true, -1);
        BbsForInfModel bbsInfMdl;

        //1からの連番になっているか確認する
        int count = GSConstBulletin.BAC_SML_NTF_ADMIN;
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        for (BbsForInfModel mdl : bfiList) {
            if (mdl.getBfiSort() != count) {
                //連番と一致しない場合、修正する
                bbsInfMdl = new BbsForInfModel();
                bbsInfMdl.setBfiSid(mdl.getBfiSid());
                bbsInfMdl.setBfiSort(count);
                bfiDao.updateBBSSort(bbsInfMdl);
            }
            ++count;
        }
    }

    /**
     * <br>[機  能] 対象のフォーラムを親に持つフォーラムの最大の表示順を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sid 対象の親フォーラムSID
     * @return 対象のフォーラムを親に持つフォーラムの最大の表示順
     * @throws SQLException SQL実行時例外
     */
    public int getMaxBfiSort(Connection con, int sid)
            throws SQLException {
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        int ret = bfiDao.getMaxSortOfChild(sid);
        return ret;
    }

    /**
     * <br>[機  能] フォーラムの並び順を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param selectForumModel 選択中のフォーラムモデル
     * @param con コネクション
     * @param mode モード up:上へ down:下へ
     * @throws Exception 実行例外
     */
    private void __updateSort(
            BbsForInfModel selectForumModel,
            Connection con,
            String mode)
                    throws Exception {
        int selectForumSort = selectForumModel.getBfiSort();

        //同じ親フォーラムを持つフォーラムを取得
        BbsBiz bbsBiz = new BbsBiz();
        List < BbsForInfModel > childForumList = new ArrayList < BbsForInfModel >();
        childForumList = bbsBiz.getSortedChildForum(
                con, selectForumModel.getBfiParentSid(), true, -1);

        BbsForInfDao bfiDao = new BbsForInfDao(con);
        if (mode.equals(GSConstBulletin.FORUM_SORT_UP)) {
            //「上へ」の場合
            for (BbsForInfModel model : childForumList) {
                if (selectForumSort == model.getBfiSort()) {
                    model.setBfiSort(model.getBfiSort() - 1);
                    bfiDao.updateBBSSort(model);

                } else if (model.getBfiSort() == selectForumSort - 1) {
                    model.setBfiSort(model.getBfiSort() + 1);
                    bfiDao.updateBBSSort(model);
                }
            }

        } else if (mode.equals(GSConstBulletin.FORUM_SORT_DOWN)) {
            //「下へ」の場合
            for (BbsForInfModel model : childForumList) {
                if (selectForumSort == model.getBfiSort()) {
                    model.setBfiSort(model.getBfiSort() + 1);
                    bfiDao.updateBBSSort(model);

                } else if (model.getBfiSort() == selectForumSort + 1) {
                    model.setBfiSort(model.getBfiSort() - 1);
                    bfiDao.updateBBSSort(model);
                }
            }
        }

    }

}
