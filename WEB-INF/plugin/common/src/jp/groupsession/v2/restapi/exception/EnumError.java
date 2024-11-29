package jp.groupsession.v2.restapi.exception;


/**
 * <br>[機  能] 理由コード列挙型
 * <br>[解  説] 
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumError implements IReasonCode {

    /** 存在しないAPIへのリクエストを行った場合 */
    @ReasonCodeString("ERROR-001")
    REQ_URL,

    /** APIがリクエストメソッドに対応していない場合 */
    @ReasonCodeString("ERROR-002")
    REQ_METHOD,

    /** APIがリクエストメソッドに対応していない場合 */
    @ReasonCodeString("ERROR-003")
    REQ_BODY_FORMAT,

    /** GSがバックアップ中の場合 */
    @ReasonCodeString("ERROR-004")
    SYS_DB_BACKUP,
    /** GSが高負荷によりDBにアクセスできない場合 */
    @ReasonCodeString("ERROR-005")
    SYS_DB_BUSY,
    /** 予期しない不具合 */
    @ReasonCodeString("ERROR-006")
    SYS_RUNTIME_ERROR,

    /** APIが使用不可能なユーザでアクセスした場合 */
    @ReasonCodeString("ERROR-007")
    RESOURCE_USER_CANTUSE_API,

    /** 必須の値がリクエストされなかった場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_REQUIRED,

    /** スペースのみが許可されていない場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_ONLY_SPACE,

    /** スペースで開始する文字列が許可されていない場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_START_SPACE,

    /** タブ文字が許可されていない場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_TAB,

    /** 許可されていない文字が使用されている場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_LETER,

    /** 最大サイズを超えた値を使用された場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_MAXLENGTH,

    /** 許可されていないフォーマットの値が使用された場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_FORMAT,

    /** 許可されていないパラメータの組み合わせ */
    @ReasonCodeString("ERROR-008")
    PARAM_COLLABORATION,

    /** パラメータに使用権限のない選択値が使用された場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_IMPERMISSIBLE,

    /** 上記以外の不正な値が使用された場合 */
    @ReasonCodeString("ERROR-008")
    PARAM_OTHER_INVALID,

    /** 不正な認証トークン */
    @ReasonCodeString("AUTH-001")
    AUTH_TOKEN,
    /** トークン使用不可 */
    @ReasonCodeString("AUTH-002")
    AUTH_CANT_USE_TOKEN,
    /** 不正な認証トークン */
    @ReasonCodeString("AUTH-003")
    AUTH_IDPASSWORD,
    /** ベーシック認証使用不可 */
    @ReasonCodeString("AUTH-004")
    AUTH_CANT_USE_BASIC;
    
}
