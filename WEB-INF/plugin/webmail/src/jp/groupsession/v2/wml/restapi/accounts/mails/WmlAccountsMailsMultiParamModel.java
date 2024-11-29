package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.SQLException;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.MaxArraySize;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlMailAccessAuthChecker;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

@ParamModel
public class WmlAccountsMailsMultiParamModel {
    /** アカウントID */
    private String accountId__;
    /** メールSID */
    @NotBlank
    @MaxArraySize(50)
    private long[] sidArray__;

    /**
     * <p>accountId を取得します。
     * @return accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsGetParamModel#accountId__
     */
    public String getAccountId() {
        return accountId__;
    }

    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsGetParamModel#accountId__
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }
    /**
     * <p>sidArray を取得します。
     * @return
     */
    public long[] getSidArray() {
        return sidArray__;
    }
    /**
     * <p>sidArray をセットします。
     * @param sidArray
     */
    public void setSidArray(long[] sidArray) {
        sidArray__ = sidArray;
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

        //メール利用可能チェック
        int wacSid = wraBiz.getWmlAccountSid(ctx.getCon(), accountId__);
        WmlMailAccessAuthChecker cheker
            = new WmlMailAccessAuthChecker(ctx.getCon(), wacSid, sidArray__);

        if (!cheker.canAccessAllMail()) {
            throw new RestApiValidateException(
                WmlEnumReasonCode.PARAM_CANT_ACCESS_MAIL,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.mail")
            );
        }
    }

}
