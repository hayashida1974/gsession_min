package jp.groupsession.v2.cmn.restapi.users.authentications;

import jp.groupsession.v2.restapi.exception.IReasonCode;

/**
 * <br>[機  能] 認証情報API 理由区分コード
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumResourceReasonCode implements IReasonCode {
    /** ログイン停止*/
    @ReasonCodeString("AUTH-007")
    RESOURCE_USER_LOGIN_STOP,
    /** トークン認証使用不可*/
    @ReasonCodeString("AUTH-008")
    RESOURCE_TOKEN_NOTUSE,
    /** シングルサインオンが必要*/
    @ReasonCodeString("AUTH-012")
    RESOURCE_AUTH_NEED_SAML,
    /** シングルサインオンが必要*/
    @ReasonCodeString("AUTH-013")
    RESOURCE_AUTH_NEED_SHIBBOLETH,
    /** シングルサインオンが必要*/
    @ReasonCodeString("AUTH-014")
    RESOURCE_AUTH_NEED_NTLM,
    /** 所属グループ未設定*/
    @ReasonCodeString("AUTH-015")
    RESOURCE_AUTH_NOTSET_BELONGGRP,
    /** デフォルトグループ未設定*/
    @ReasonCodeString("AUTH-016")
    RESOURCE_AUTH_NOTSET_DEFGRP,
    /** ワンタイムパスワードログインが必要*/
    @ReasonCodeString("AUTH-009")
    RESOURCE_AUTH_NEED_OTP,
    /** ワンタイムパスワードログインが必要*/
    @ReasonCodeString("AUTH-010")
    RESOURCE_SENDERROR_OTP,
    /** ワンタイムパスワード送信先設定が必要*/
    @ReasonCodeString("AUTH-011")
    RESOURCE_NOTHING_OTPSENDADDRESS;
}
