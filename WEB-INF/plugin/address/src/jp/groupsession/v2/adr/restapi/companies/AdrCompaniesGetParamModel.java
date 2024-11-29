package jp.groupsession.v2.adr.restapi.companies;

import java.sql.SQLException;

import jp.groupsession.v2.adr.restapi.biz.AdrRestCompanyBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;

/**
 * <br>[機  能] アドレス帳 会社情報取得API用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class AdrCompaniesGetParamModel {

    /** 企業コード */
    @NotBlank
    @TextField
    private String companyId__ = null;

    public String getCompanyId() {
        return companyId__;
    }

    public void setCompanyId(String companyId) {
        companyId__ = companyId;
    }

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        AdrRestCompanyBiz biz = new AdrRestCompanyBiz();
        biz.validateCompany(ctx, companyId__);
    }
}
