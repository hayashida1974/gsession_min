package jp.groupsession.v2.wml.restapi.accounts.labels;

import java.sql.SQLException;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメールラベル 登録用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccoutsLabelsPostParamModel {

    /** アカウントID */
    private String accountId__;
    /** ラベル名 */
    @NotBlank
    @TextField
    @MaxLength(100)
    private String name__ = "";

    /**
     * <p>accountId を取得します。
     * @return accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsPostParamModel#accountId__
     */
    public String getAccountId() {
        return accountId__;
    }
    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsPostParamModel#accountId__
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }
    /**
     * <p>name を取得します。
     * @return name name
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsPostParamModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsPostParamModel#name__
     */
    public void setName(String name) {
        name__ = name;
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
