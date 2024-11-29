package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.SQLException;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;

/**
 * <br>[機  能] WEBメール メールを削除する 取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsDeleteParamModel extends WmlAccountsMailsMultiParamModel {

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        //アカウント・メールチェック
        super.validate(ctx);

        //指定されたメールの保存先ディレクトリチェック
        WmlMaildataDao mailDataDao = new WmlMaildataDao(ctx.getCon());
        for (long mailSid : getSidArray()) {
            //「ゴミ箱」ディレクトリ以外の場合、エラーとする
            if (mailDataDao.getDirType(mailSid) != GSConstWebmail.DIR_TYPE_DUST) {
                throw new RestApiPermissionException(
                    WmlEnumReasonCode.PARAM_CANT_ACCESS_MAIL,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.mail")
                    )
                .setParamName("sidArray");
            }
        }
    }

}
