package jp.groupsession.v2.wml.restapi.accounts.mails.draft;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsPostParamModel;

/**
 * 
 */
@ParamModel
public class WmlAccountsMailsDraftPostParamModel extends WmlAccountsMailsPostParamModel {
    /** 草稿保管（新規） 入力チェック*/
    @Validator
    public void validate(RestApiContext ctx) throws SQLException, RestApiValidateException {
        List<RestApiValidateException> valErr = new ArrayList<>();

        try {
            super.validate(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(0, e);
        }
        

        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }
    }


}
