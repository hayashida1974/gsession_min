package jp.groupsession.v2.adr.restapi.companies;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.adr.restapi.biz.AdrRestCompanyBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] アドレス帳 会社情報取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrCompaniesGetBiz {

    /** 実行結果*/
    private AdrCompaniesResultModel result__ = new AdrCompaniesResultModel();
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
    public AdrCompaniesGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 会社情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute(AdrCompaniesGetParamModel param) throws SQLException {

        AdrRestCompanyBiz biz = new AdrRestCompanyBiz();
        result__ = biz.getCompanyInfo(con__, param.getCompanyId());
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public AdrCompaniesResultModel getResult() {
        return result__;
    }
}
