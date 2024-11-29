package jp.groupsession.v2.adr.restapi.companies.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.dao.AdrCompanySearchDao;
import jp.groupsession.v2.adr.model.AdrCompanySearchModel;
import jp.groupsession.v2.adr.restapi.biz.AdrRestCompanyBiz;
import jp.groupsession.v2.adr.restapi.companies.AdrCompaniesResultModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] アドレス帳 会社情報一覧取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrCompaniesQueryPostBiz {

    /** 実行結果*/
    private final List<AdrCompaniesResultModel> result__ = new ArrayList<AdrCompaniesResultModel>();
    /** 検索結果件数 */
    private Integer maxCount__ = 0;
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
    public AdrCompaniesQueryPostBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 会社一覧情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute(AdrCompaniesQueryPostParamModel param) throws SQLException {

        //検索条件を検索用モデルに格納
        AdrCompanySearchModel searchMdl = new AdrCompanySearchModel();
        searchMdl.setLimit(param.getLimit());
        searchMdl.setOffset(param.getOffset());
        searchMdl.setSortOrderFlg(param.getSortOrderFlg());
        searchMdl.setKanaStartOffsetText(param.getKanaStartOffsetText());
        searchMdl.setKeywordText(param.getKeywordText());
        searchMdl.setCompanyNameKanaStartText(param.getCompanyNameKanaStartText());
        searchMdl.setIndustrySid(param.getIndustrySid());
        searchMdl.setTodofukenSid(param.getTodofukenSid());

        //検索結果件数をセット
        AdrCompanySearchDao dao = new AdrCompanySearchDao(con__);
        maxCount__ = dao.countSearchCompany(searchMdl);

        //会社情報を検索
        List<Integer> acoSids = new ArrayList<Integer>();
        if (param.getOffset() <= maxCount__) {
            acoSids = dao.searchCompany(searchMdl);
        }

        //検索結果をセット
        AdrRestCompanyBiz biz = new AdrRestCompanyBiz();
        for (Integer acoSid : acoSids) {
            AdrCompaniesResultModel resultModel = biz.getCompanyInfo(con__, acoSid);
            result__.add(resultModel);
        }
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<AdrCompaniesResultModel> getResult() {
        return result__;
    }

    /**
     *
     * <br>[機  能] 検索結果件数の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public Integer getMaxCount() {
        return maxCount__;
    }
}
