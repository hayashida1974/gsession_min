package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール 単体取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsGetParamModel {

    /** アカウントID */
    private String accountId__;
    /** メールSID */
    private long mailSid__;

    /**
     * <p>accountId を取得します。
     * @return accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsGetParamModel#accountId__
     */
    public String getAccountId() {
        return accountId__;
    }

    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsGetParamModel#accountId__
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }

    /**
     * <p>mailSid を取得します。
     * @return mailSid mailSid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsGetParamModel#mailSid__
     */
    public long getMailSid() {
        return mailSid__;
    }

    /**
     * <p>mailSid をセットします。
     * @param mailSid mailSid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsGetParamModel#mailSid__
     */
    public void setMailSid(long mailSid) {
        mailSid__ = mailSid;
    }

    /**
     * <br>[機  能] アカウント, メールSIDに対しての入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        Connection con = ctx.getCon();
        WmlRestAccountBiz biz = new WmlRestAccountBiz();

        //アカウント使用可能チェック
        if (!biz.canUseAccount(ctx, accountId__)) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_MAIL,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.mail")
            );
        }

        //メール使用可能チェック
        WmlRestMailDataBiz mailBiz = new WmlRestMailDataBiz();
        int wacSid = biz.getWmlAccountSid(con, accountId__);
        if (!mailBiz.canAccess(con, wacSid, mailSid__)) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_MAIL,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.mail")
            );
        }
    }
}
