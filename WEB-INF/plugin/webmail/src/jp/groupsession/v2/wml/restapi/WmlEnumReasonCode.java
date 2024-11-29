package jp.groupsession.v2.wml.restapi;

import jp.groupsession.v2.restapi.exception.IReasonCode;

/**
 *
 * <br>[機  能] 理由コード列挙型
 * <br>[解  説] 原因がWEBメールのRESTAPIに関するエラーの場合
 * <br>[備  考]
 *
 * @author JTS
 */
public enum WmlEnumReasonCode implements IReasonCode {

    /** WEBメールが利用できない */
    @ReasonCodeString("WEBMAIL-000")
    PLUGIN_CANT_USE,

    /** 対象のWEBメールアカウントへアクセスできない */
    @ReasonCodeString("WEBMAIL-101")
    RESOURCE_CANT_ACCESS_ACCOUNT,

    /** 対象のメールへアクセスできない */
    @ReasonCodeString("WEBMAIL-102")
    RESOURCE_CANT_ACCESS_MAIL,

    /** 対象のメールの添付ファイルへアクセスできない */
    @ReasonCodeString("WEBMAIL-103")
    RESOURCE_CANT_ACCESS_MAIL_TEMPFILE,

    /** 対象のテンプレートの添付ファイルへアクセスできない */
    @ReasonCodeString("WEBMAIL-104")
    RESOURCE_CANT_ACCESS_TEMPLATE_TEMPFILE,

    /** 入力パラメータで指定したメールへアクセスできない */
    @ReasonCodeString("WEBMAIL-201")
    PARAM_CANT_ACCESS_MAIL,

    /** 入力パラメータで指定したテンプレートへアクセスできない */
    @ReasonCodeString("WEBMAIL-202")
    PARAM_CANT_ACCESS_TEMPLATE,

    /** 入力パラメータで指定したメールの添付ファイルへアクセスできない */
    @ReasonCodeString("WEBMAIL-203")
    PARAM_CANT_ACCESS_MAIL_TEMPFILE,

    /** 入力パラメータで指定したテンプレートの添付ファイルへアクセスできない */
    @ReasonCodeString("WEBMAIL-204")
    PARAM_CANT_ACCESS_TEMPLATE_TEMPFILE,

    /** 入力パラメータのメールアドレスフォーマットが不正 */
    @ReasonCodeString("WEBMAIL-205")
    PARAM_INVALID_ADDRESS_FORMAT,

    /** 入力パラメータのメールアドレスに送信制限されたドメインが使用されている */
    @ReasonCodeString("WEBMAIL-206")
    PARAM_CANT_SEND_ADDRESS,

    /** ディスク容量制限により、メール送信に失敗 */
    @ReasonCodeString("WEBMAIL-207")
    FAILED_SEND_DISK_CAPACITY,

    /** メールサーバアクセスエラーによりメール送信に失敗 */
    @ReasonCodeString("WEBMAIL-208")
    FAILED_SEND_SERVER_ACCESS,

    /** 入力パラメータで指定のラベルへアクセスできない */
    @ReasonCodeString("WEBMAIL-209")
    PARAM_CANT_ACCESS_LABEL,
    /** ディスク容量制限により、草稿保管に失敗 */
    @ReasonCodeString("WEBMAIL-210")
    FAILED_SAVE_DISK_CAPACITY,
    /** メールサイズ制限により、メール送信に失敗 */
    @ReasonCodeString("WEBMAIL-211")
    FAILED_SEND_MAIL_SIZE,
    /** メールサイズ制限により、草稿保管に失敗 */
    @ReasonCodeString("WEBMAIL-212")
    FAILED_SAVE_MAIL_SIZE,
    /** 新規送信時に草稿, 予約送信のメールを複写した場合 */
    @ReasonCodeString("WEBMAIL-213")
    PARAM_NEW_TYPE_DIRECTORY_COLLABORATION,
    /** 編集して送信時に草稿, 予約送信以外のメールを指定した場合 */
    @ReasonCodeString("WEBMAIL-214")
    PARAM_EDIT_TYPE_DIRECTORY_COLLABORATION,
    ;
    
}
