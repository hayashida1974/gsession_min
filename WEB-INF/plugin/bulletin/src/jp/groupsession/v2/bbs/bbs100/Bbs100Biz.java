package jp.groupsession.v2.bbs.bbs100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] 掲示板 フォーラムメンバー一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs100Biz {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Bbs100Biz() {
    }

    /**
     * <br>[機  能] 初期画面設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public static void setDsp(Connection con, Bbs100ParamModel paramMdl)
            throws SQLException {

        con.setAutoCommit(true);
        BulletinDao dao = new BulletinDao(con);

        //メンバー情報を持つフォーラムSIDを取得
        BbsBiz bbsBiz = new BbsBiz();
        int forumSid = bbsBiz.getBfiSidForMemberInfo(con, paramMdl.getBbs010forumSid());

        //最大件数
        int memCnt = dao.getForumMemCount(forumSid);

        //ページ調整
        int maxPage = memCnt / Bbs100Form.VIEW_MAXCNT;
        if ((maxPage % Bbs100Form.VIEW_MAXCNT) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbs100pageNum1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs100pageNum1(page);
        paramMdl.setBbs100pageNum2(page);

        //ページコンボ設定
        paramMdl.setBbs100PageLabel(PageUtil.createPageOptions(memCnt, Bbs100Form.VIEW_MAXCNT));

        //フォーラムメンバー一覧設定
        List<CmnUsrmInfModel> memList =
                dao.getForumMemList(forumSid,
                        paramMdl.getBbs100sortKey(),
                        paramMdl.getBbs100orderKey(),
                        page,
                        Bbs100Form.VIEW_MAXCNT);

        paramMdl.setBbs100users(memList);
        con.setAutoCommit(false);
    }
}
