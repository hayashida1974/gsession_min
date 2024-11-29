package jp.groupsession.v2.adr.restapi.industries;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] アドレス帳 業種一覧取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrIndustriesGetBiz {

    /** 実行結果*/
    private final List<AdrIndustriesResultModel> result__ = new ArrayList<AdrIndustriesResultModel>();
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    /**
     * 
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public AdrIndustriesGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 業種一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute() throws SQLException {

        //業種一覧取得
        AdrTypeindustryDao atiDao = new AdrTypeindustryDao(con__);
        List<AdrTypeindustryModel> atiMdlList = atiDao.select();
        for (AdrTypeindustryModel atiMdl : atiMdlList) {
            AdrIndustriesResultModel resultMdl = new AdrIndustriesResultModel();
            resultMdl.setSid(atiMdl.getAtiSid());
            resultMdl.setName(atiMdl.getAtiName());
            result__.add(resultMdl);
        }
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<AdrIndustriesResultModel> getResult() {
        return result__;
    }
}
