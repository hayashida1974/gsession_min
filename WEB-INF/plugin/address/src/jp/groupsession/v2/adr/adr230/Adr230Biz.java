package jp.groupsession.v2.adr.adr230;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] アドレス帳 会社選択ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr230Biz {
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Adr230Biz(Connection con, RequestModel reqMdl) {
        reqMdl_ = reqMdl;
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr230ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Adr230ParamModel paramMdl) throws SQLException {

        AdrCompanyDao comDao = new AdrCompanyDao(con__);

        //検索結果件数
        int searchCnt = comDao.getCompanyDataCount();

        //最大表示件数
        int maxDsp = Adr230Form.PAGE_MAX_DATA_CMT;

        //ページ調整
        int maxPage = searchCnt / maxDsp;
        if ((searchCnt % maxDsp) > 0) {
            maxPage++;
        }

        int page = paramMdl.getAdr230PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }

        paramMdl.setAdr230PageTop(page);
        paramMdl.setAdr230PageBottom(page);

        //ページコンボ設定
        paramMdl.setAdr230PageList(
                PageUtil.createPageOptions(searchCnt, maxDsp));

        //検索結果セット
        paramMdl.setAdr230CompanyList(
                comDao.getCompanyDataList(
                        paramMdl.getAdr230SortKey(),
                        paramMdl.getAdr230OrderKey(),
                        page,
                        maxDsp));
    }
}