package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール 新規送信, 新規草稿作成用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsPostParamModel extends WmlAccountsMailsParamModel {

    /**
     * <br>[機  能] アカウントに対しての入力チェック, 参照メールのディレクトリに対してのチェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        List<RestApiValidateException> valErr = new ArrayList<>();

        try {
            //アカウントに対するチェック
            super.validate(ctx);

        } catch (RestApiValidateException e) {
            valErr.add(0, e);
        }

        //送信タイプが複写の時に、予約送信と草稿のメールが指定されている場合エラー
        if (getProcType() == GSConstWebmail.SEND_TYPE_EDIT) {
            Connection con = ctx.getCon();
            long refarenceSid = getRefarenceSid();
            WmlRestMailDataBiz mailDataBiz = new WmlRestMailDataBiz();
            if (mailDataBiz.isSoukouEditMail(con, refarenceSid)) {
                throw new RestApiValidateException(
                    WmlEnumReasonCode.PARAM_NEW_TYPE_DIRECTORY_COLLABORATION,
                    "error.mail.new.send.directory"
                ).setParamName("refarenceSid");
            }
        }

        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }
    }
  
}
