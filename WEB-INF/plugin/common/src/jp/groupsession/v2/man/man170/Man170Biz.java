package jp.groupsession.v2.man.man170;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLoginHistoryModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] メイン ログイン履歴詳細画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man170Biz {

    /**
     * <br>[機  能] ログイン履歴一覧検索
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setSearchData(Connection con, RequestModel reqMdl, Man170ParamModel paramMdl)
    throws SQLException {

        //表示ユーザ情報を取得
        int usrSid = paramMdl.getMan050SelectedUsrSid();
        CmnUsrmInfDao uinfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel userMdl = uinfDao.selectUserNameAndJtkbn(usrSid);
        if (userMdl != null) {
            String name =
                NullDefault.getString(userMdl.getUsiSei(), "")
                + "  "
                + NullDefault.getString(userMdl.getUsiMei(), "");
            paramMdl.setMan170UserName(name);
            paramMdl.setMan170UserJtkbn(userMdl.getUsrJkbn());
        }

        CmnLoginHistoryDao loginHisDao = new CmnLoginHistoryDao(con);

        //検索用Modelを作成
        CmnLoginHistoryModel hisMdl = new CmnLoginHistoryModel();
        hisMdl.setUsrSid(usrSid);
        hisMdl.setClhTerminal(paramMdl.getMan170Terminal());
        hisMdl.setClhCar(paramMdl.getMan170Car());

        //検索結果件数
        int searchCnt = loginHisDao.selectHistoryCnt(hisMdl);

        //最大表示件数
        int maxDsp = Man170Form.PAGE_MAX_DATA_CMT;

        //ページ調整
        int maxPage = searchCnt / maxDsp;
        if ((searchCnt % maxDsp) > 0) {
            maxPage++;
        }

        int page = paramMdl.getMan170PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }

        paramMdl.setMan170PageTop(page);
        paramMdl.setMan170PageBottom(page);

        //ページコンボ設定
        paramMdl.setMan170PageList(
                PageUtil.createPageOptions(searchCnt, maxDsp));

        //検索結果セット
        paramMdl.setMan170LoginHistoryList(
            loginHisDao.getLoginHistoryList(
                    hisMdl,
                    paramMdl.getMan170SortKey(),
                    paramMdl.getMan170OrderKey(),
                    page, maxDsp, reqMdl));
    }
}