package jp.groupsession.v2.bbs.bbs041;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs040.Bbs040Biz;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BulletinSearchModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 掲示板検索結果一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs041Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs041Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void setInitData(
            Bbs041ParamModel paramMdl,
            Connection con,
            int userSid,
            boolean admin,
            RequestModel reqMdl)
                    throws Exception {
        log__.debug("START");

        //最大件数
        UDate now = new UDate();
        Bbs040Biz biz040 = new Bbs040Biz();
        int searchCnt = biz040.getSearchCount(paramMdl, con, userSid, admin, now);
        int maxCnt = GSConstBulletin.BBSSEARCH_MAXCOUNT;
        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbs041page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBbs041page1(page);
        paramMdl.setBbs041page2(page);

        //ページコンボ設定
        paramMdl.setBbsPageLabel(PageUtil.createPageOptions(searchCnt, maxCnt));


        //検索条件を取得
        BulletinSearchModel searchMdl = biz040.createSearchModel(paramMdl, userSid, admin, now);
        searchMdl.setStart((page - 1) * maxCnt + 1);
        searchMdl.setEnd(searchMdl.getStart() + maxCnt - 1);

        //ユーザがメンバーに含まれているフォーラムリスト
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfUser(con, searchMdl.getUserSid());
        List<Integer> forumList = new ArrayList<Integer>();
        for (BbsForInfModel bfiMdl : bfiMdlList) {
            forumList.add(bfiMdl.getBfiSid());
        }

        BulletinDao bbsDao = new BulletinDao(con);
        paramMdl.setResultList(bbsDao.getSearchResultList(searchMdl, reqMdl, forumList));


        //WEB検索のキーワードを設定する。
        String key = paramMdl.getS_key();
        String webSearchWord = "";
        if (!StringUtil.isNullZeroStringSpace(key)) {
            webSearchWord = StringUtil.toSingleCortationEscape(key);
        }
        paramMdl.setBbs041WebSearchWord(webSearchWord);

        log__.debug("End");
    }

}
