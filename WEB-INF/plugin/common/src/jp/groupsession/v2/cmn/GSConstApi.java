package jp.groupsession.v2.cmn;

/**
 * <p>GroupSession WEB API定数一覧
 * @author JTS
 */
public class GSConstApi {
    /** ユーザSID 項目名 */
    public static final String USER_SID_STRING = "cmn.user.sid";
    /** グループSID 項目名 */
    public static final String GROUP_SID_STRING = "cmn.group.sid";
    /** キャビネットSID 項目名 */
    public static final String TEXT_CABINET_SID = "cmn.cabinet.sid";
    /** バイナリSID 項目名 */
    public static final String TEXT_BIN_SID = "cmn.binary.sid";
    /** 添付ファイル 項目名 */
    public static final String TEXT_TEMP_FILE = "cmn.attach.file";
    /** ショートメールSID 項目名 */
    public static final String TEXT_SML_SID = "cmn.smail.sid";
    /** ディレクトリSID 項目名 */
    public static final String TEXT_DIRECTORY_SID = "fil.111";
    /** 親ディレクトリSID 項目名 */
    public static final String TEXT_PARENT_DIRECTORY_SID = "cmn.parantdirectory.sid";
    /** 選択したファイル 項目名 */
    public static final String TEXT_SELECT_FILE = "fil.92";
    /** 選択したキャビネット 項目名 */
    public static final String TEXT_SELECT_CABINET = "cmn.selected.cabinet";
    /** 選択したフォルダ 項目名 */
    public static final String TEXT_SELECT_FOLDER = "cmn.selected.folder";
    /** モード 項目名 */
    public static final String TEXT_MODE = "cmn.mode";
    /** キャビネット区分 項目名 */
    public static final String TEXT_CABINET_KBN = "fil.991";

    /** ショートメールリスト最大表示件数 */
    public static final int SMAIL_MAX_RECORD_COUNT = 20;

    /** ディレクトリ区分 0=フォルダ */
    public static final int DIRECTORY_FOLDER = 0;
    /** ディレクトリ区分 1=ファイル */
    public static final int DIRECTORY_FILE = 1;

    /** キャビネット区分：共有キャビネット */
    public static final int CABINET_KBN_PUBLIC = 0;
    /** キャビネット区分：個人キャビネット */
    public static final int CABINET_KBN_PRIVATE = 1;


    /** 認証使用区分 使用しない*/
    public static final int USEKBN_AUTH_NOUSE = 0;
    /** 認証使用区分 使用する*/
    public static final int USEKBN_AUTH_USE = 1;
    /** 認証使用区分 指定IPのみ使用*/
    public static final int USEKBN_AUTH_USEIP = 2;

    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_30M = 0;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_1H = 1;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_2H = 2;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_3H = 3;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_5H = 4;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_8H = 5;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_10H = 6;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_12H = 7;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_15H = 8;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_20H = 9;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_1D = 10;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_1W = 11;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_2W = 12;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_1MONTH = 13;
    /** トークン有効期限 */
    public static final int TOKEN_LIMIT_FREE = 14;

    /** トークン 有効 */
    public static final int TOKEN_ENABLED = 0;
    /** トークン 無効化済み */
    public static final int TOKEN_DISABLED = 9;

    /** トークンタイプ 一次トークン */
    public static final int TOKEN_TYPE_OTP = 0;
    /** トークンタイプ トークン */
    public static final int TOKEN_TYPE_AUTH = 1;


    /** リクエスト属性キー ログインユーザ */
    public static final String ATTRKEY_REQUSER = "requestUser__";
    /** リクエスト属性キー ユーザプラグインコンフィグ */
    public static final String ATTRKEY_PCONF = "userPconfig__";
    /** リクエスト属性キー ログインリザルト */
    public static final String ATTRKEY_LOGINRESULT = "loginResult__";

    /** requestLocalキー コネクション*/
    public static final Object LOCALKEY_CONNECTION = new Object();
    /** requestLocalキー リクエストモデル*/
    public static final Object LOCALKEY_REQUESTMODEL = new Object();
    /** requestLocalキー 認証結果*/
    public static final Object LOCALKEY_LOGINRESULT = new Object();
    /** requestLocalキー ログインセッション*/
    public static final Object LOCALKEY_LOGINSESSION_WRITER = new Object();

    /** RESTAPIがサポートするメソッド */
    public static final String[] SUPORTMETHOD_RESTAPI =
            new String[] {"GET", "POST", "PUT", "DELETE"};

}
