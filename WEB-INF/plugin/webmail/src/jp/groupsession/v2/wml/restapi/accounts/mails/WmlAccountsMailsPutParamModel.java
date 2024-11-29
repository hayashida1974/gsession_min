package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.NoParameter;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール 編集して送信, 編集して草稿作成用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsPutParamModel extends WmlAccountsMailsParamModel {

    /**メールSID */
    @Required
    long mailSid__;

    /**
     * <p>mailSid を取得します。
     * @return
     */
    public long getMailSid() {
        return mailSid__;
    }
    /**
     * <p>mailSid をセットします。
     * @param mailSid
     */
    public void setMailSid(long mailSid) {
        mailSid__ = mailSid;
    }
    @NoParameter
    @Override
    public long getRefarenceSid() {
        return super.getRefarenceSid();
    }
    @Override
    public void setRefarenceSid(long refarenceSid) {
        //編集時にrefarenceSidを設定させない
        super.setRefarenceSid(-1);
    }
    @NoParameter
    @Override
    public int getProcType() {
        return 0;
    }
    @Override
    public void setProcType(int procType) {
        //編集時にprocTypeを設定させない
        super.setProcType(0);
    }


    /**
     * <br>[機  能] アカウントとメールSIDに対しての入力チェック, 参照メールのディレクトリに対してのチェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        List<RestApiValidateException> valErr = new ArrayList<>();

        WmlRestMailDataBiz mailBiz = new WmlRestMailDataBiz();
        try {
            mailBiz.validate(ctx, getAccountId(), getMailSid());

        } catch (RestApiValidateException e) {
            valErr.add(0, e);
        }

        //指定したメールが予約送信または草稿のメールなのかチェック
        boolean isSoukouEdit = mailBiz.isSoukouEditMail(ctx.getCon(), getMailSid());
        if (isSoukouEdit == false) {
            RestApiException apiException = new RestApiException(null);
            apiException.setReasonCode(WmlEnumReasonCode.PARAM_EDIT_TYPE_DIRECTORY_COLLABORATION);
            apiException.setMessageResource(
                "error.mail.edit.send.directory");
            throw apiException;
        }

        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }
    }
  
}
