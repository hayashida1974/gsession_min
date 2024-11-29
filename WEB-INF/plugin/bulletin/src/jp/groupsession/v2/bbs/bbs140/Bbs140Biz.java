package jp.groupsession.v2.bbs.bbs140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BulletinWachModel;

/**
 * <br>[機  能] 掲示板 フォーラムメンバー閲覧状況ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs140Biz {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Bbs140Biz() {
    }

    /**
     * <br>[機  能] 初期画面設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public static void setDsp(Connection con, Bbs140ParamModel paramMdl) throws SQLException {

        BulletinDao dao = new BulletinDao(con);

        //メンバー情報を持つフォーラムSIDを取得
        BbsBiz bbsBiz = new BbsBiz();
        int memberForumSid = bbsBiz.getBfiSidForMemberInfo(con, paramMdl.getBbs010forumSid());

        //最大件数
        int memCnt = dao.getForumMemCount(memberForumSid);

        //ページ調整
        int maxPage = memCnt / Bbs140Form.VIEW_MAXCNT;

        if ((memCnt % Bbs140Form.VIEW_MAXCNT) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbs140pageNum1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs140pageNum1(page);
        paramMdl.setBbs140pageNum2(page);

        //ページコンボ設定
        paramMdl.setBbs140PageLabel(PageUtil.createPageOptions(memCnt, Bbs140Form.VIEW_MAXCNT));

        //フォーラムメンバー一覧設定
        List<BulletinWachModel> memList =
                dao.getForumMemWatchList(
                        paramMdl.getBbs010forumSid(),
                        paramMdl.getThreadSid(),
                        paramMdl.getBbs140sortKey(),
                        paramMdl.getBbs140orderKey(),
                        page,
                        Bbs140Form.VIEW_MAXCNT,
                        memberForumSid);

        paramMdl.setBbs140users(memList);

    }
}
