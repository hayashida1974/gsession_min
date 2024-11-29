package jp.groupsession.v2.usr.usr090;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.cmn.model.base.CmnLoginHistoryModel;

/**
 * <br>[機  能] 固体識別番号 入力履歴画面ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr090Biz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Usr090Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Usr090ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Usr090ParamModel paramMdl) throws SQLException {

        //検索対象ユーザ
        int usrSid = paramMdl.getTargetUsrSid();

        CmnLoginHistoryDao loginHisDao = new CmnLoginHistoryDao(con__);

        //検索用Modelを作成
        CmnLoginHistoryModel hisMdl = new CmnLoginHistoryModel();
        hisMdl.setUsrSid(usrSid);
        hisMdl.setClhTerminal(GSConstCommon.TERMINAL_KBN_MOBILE);

        //検索結果件数
        int searchCnt = loginHisDao.getUidHistoryCount(hisMdl);

        //最大表示件数
        int maxDsp = Usr090Form.PAGE_MAX_DATA_CMT;

        //ページ調整
        int maxPage = searchCnt / maxDsp;
        if ((searchCnt % maxDsp) > 0) {
            maxPage++;
        }

        int page = paramMdl.getUsr090PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }

        paramMdl.setUsr090PageTop(page);
        paramMdl.setUsr090PageBottom(page);

        //ページコンボ設定
        paramMdl.setUsr090PageList(
                PageUtil.createPageOptions(searchCnt, maxDsp));

        //検索結果セット
        paramMdl.setUsr090UidList(
            loginHisDao.getUidHistoryList(
                    hisMdl,
                    paramMdl.getUsr090SortKey(),
                    paramMdl.getUsr090OrderKey(),
                    page,
                    maxDsp));
    }
}