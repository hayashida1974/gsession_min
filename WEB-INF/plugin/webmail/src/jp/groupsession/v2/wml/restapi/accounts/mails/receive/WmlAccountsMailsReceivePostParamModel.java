package jp.groupsession.v2.wml.restapi.accounts.mails.receive;

import java.sql.SQLException;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
@ParamModel
public class WmlAccountsMailsReceivePostParamModel {
    /** アカウントID */
    private String accountId__;
    /**
     * <p>accountId を取得します。
     * @return accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsSendParamModel#accountId__
     */
    public String getAccountId() {
        return accountId__;
    }

    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsSendParamModel#accountId__
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }

    /**
     * <br>[機  能] アカウントに対しての入力チェックを実行する
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
