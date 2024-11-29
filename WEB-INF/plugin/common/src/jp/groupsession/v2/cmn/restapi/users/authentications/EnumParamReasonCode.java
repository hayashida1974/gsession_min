package jp.groupsession.v2.cmn.restapi.users.authentications;

import jp.groupsession.v2.restapi.exception.IReasonCode;

/**
 * <br>[機  能] 認証情報API 理由区分コード
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumParamReasonCode implements IReasonCode {
    /** 不正なトークン*/
    @ReasonCodeString("PARAM_TOKEN")
    TOKEN,
    /** トークンの期限切れ*/
    @ReasonCodeString("AUTH-005")
    PARAM_TOKEN_DEAD;
}
