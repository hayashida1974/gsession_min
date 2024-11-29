package jp.groupsession.v2.wml.restapi.accounts.templates;

import java.sql.SQLException;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメールテンプレート一覧 取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsTemplatesGetParamModel {

    /** WEBメールアカウントID */
    @NotBlank
    @TextField
    private String accountId__ = null;

    public String getAccountId() {
        return accountId__;
    }

    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        //アカウント利用可能チェック
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        wraBiz.validateAccount(ctx, accountId__);
    }
}
