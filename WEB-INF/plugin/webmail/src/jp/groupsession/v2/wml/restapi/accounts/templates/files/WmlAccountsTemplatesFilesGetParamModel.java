package jp.groupsession.v2.wml.restapi.accounts.templates.files;

import java.sql.SQLException;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.biz.WmlRestTemplateBinBiz;

/**
 * <br>[機  能] WEBメール テンプレート添付ファイルダウンロード用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsTemplatesFilesGetParamModel {

    /** WEBメールアカウントID */
    @NotBlank
    @TextField
    private String accountId__ = null;
    /** テンプレートSID */
    @NotBlank
    private Integer templateSid__ = 0;
    /** バイナリSID */
    @NotBlank
    private long binSid__ = 0;

    public String getAccountId() {
        return accountId__;
    }
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }
    public Integer getTemplateSid() {
        return templateSid__;
    }
    public void setTemplateSid(Integer templateSid) {
        templateSid__ = templateSid;
    }
    public long getBinSid() {
        return binSid__;
    }
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        WmlRestTemplateBinBiz biz = new WmlRestTemplateBinBiz();
        biz.validateBin(ctx, accountId__, templateSid__, binSid__);
    }
}
