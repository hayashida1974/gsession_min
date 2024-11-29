package jp.groupsession.v2.cmn;

/**
 * <br>[機  能] WEBメールプラグインで使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstWebmail {

    /** プラグインID */
    public static final String PLUGIN_ID_WEBMAIL = "webmail";
    /** プラグイン名 */
    public static final String PLUGIN_NAME_WEBMAIL = "wml.wml010.25";

    /** 採番ID WEBメール */
    public static final String SBNSID_WEBMAIL = "webmail";

    /** 採番IDサブ メッセージ番号 */
    public static final String SBNSID_SUB_MESSAGE = "message";
    /** 採番IDサブ アカウントSID */
    public static final String SBNSID_SUB_ACCOUNT = "account";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_LABEL = "label";
    /** 採番IDサブ Oauth認証情報SID */
    public static final String SBNSID_SUB_OAUTH = "oauth";
    /** 採番IDサブ フィルターSID */
    public static final String SBNSID_SUB_FILTER = "filter";
    /** 採番IDサブ バイナリSID */
    public static final String SBNSID_SUB_BINARY = "binary";
    /** 採番IDサブ ディレクトリSID */
    public static final String SBNSID_SUB_DIRECTORY = "directory";
    /** 採番IDサブ メールテンプレートSID */
    public static final String SBNSID_SUB_TEMPLATE_M = "templateM";
    /** 採番IDサブ 送信先リスト */
    public static final String SBNSID_SUB_DESTLIST = "destlist";

    /** メールヘッダ内容の最大文字数 */
    public static final int HEADER_MAXLEN = 1000;

    /** WEBメール設定 メール自動受信時の最大スレッド数 */
    public static final String MAILCONF_RECEIVE_THREAD_MAXCOUNT = "MAILRECEIVE_THREAD_MAXCOUNT";
    /** WEBメール設定 メール自動受信時の最大スレッド数 デフォルト値 */
    public static final int RECEIVE_THREAD_MAXCOUNT_DEFAULT = 10;

    /** WEBメール設定 一度に受信できるメールの最大件数 */
    public static final String MAILCONF_RECEIVE_MAXCOUNT = "MAILRECEIVE_MAXCOUNT";
    /** WEBメール設定 一度に受信できるメールの最大件数 デフォルト値 */
    public static final int RECEIVE_MAXCOUNT_DEFAULT = 100;

    /** WEBメール設定 受信サーバーへの接続タイムアウト時間(秒) */
    public static final String MAILCONF_RECEIVE_CONNECTTIMEOUT = "MAILRECEIVE_CONNECTTIMEOUT";
    /** WEBメール設定 受信サーバーへの接続タイムアウト時間(秒) デフォルト値 */
    public static final int RECEIVE_CONNECTTIMEOUT_DEFAULT = 30;
    /** WEBメール設定 メール受信のタイムアウト時間(秒) */
    public static final String MAILCONF_RECEIVE_TIMEOUT = "MAILRECEIVE_TIMEOUT";
    /** WEBメール設定 メール受信のタイムアウト時間(秒) デフォルト値 */
    public static final int RECEIVE_TIMEOUT_DEFAULT = 60;
    /** WEBメール設定 メール受信 受信サイズ比較基準時間(分) */
    public static final String MAILCONF_MAILRECEIVE_RCVSVR_CHECKTIME
        = "MAILRECEIVE_RCVSVR_CHECKTIME";
    /** WEBメール設定 メール受信 受信サイズ比較基準時間(分) デフォルト値*/
    public static final int RECEIVE_RCVSVR_CHECKTIME_DEFAULT = 0;
    /** WEBメール設定 メール本文の最大文字数 */
    public static final String MAILCONF_MAILBODY_LIMIT = "MAILBODY_LIMIT";
    /** WEBメール設定 メール本文の最大表示文字数 デフォルト値 */
    public static final int MAILBODY_LIMIT_DEFAULT = 30000;
    /** WEBメール設定 メール本文の最大表示文字数 無制限 */
    public static final int MAILBODY_LIMIT_NOLIMIT = 0;

    /** WEBメール設定 メール情報一括削除時の最大スレッド数 */
    public static final String MAILCONF_DELETE_THREAD_MAXCOUNT = "MAILDELETE_THREAD_MAXCOUNT";
    /** WEBメール設定 メール情報一括削除時の最大スレッド数 デフォルト値 */
    public static final int DELETE_THREAD_MAXCOUNT_DEFAULT = 5;
    /** WEBメール設定 メール情報一括削除 メール件数の削除件数上限 */
    public static final String MAILCONF_DELETE_MAIL_LIMIT = "MAILDELETE_MAIL_LIMIT";
    /** WEBメール設定 メール情報一括削除時の削除件数上限 デフォルト値 */
    public static final int DELETE_MAIL_LIMIT_DEFAULT = 10000;

    /** 受信サーバ ポート デフォルト */
    public static final int RECEIVE_PORT_DEFAULT = 995;
    /** 送信サーバ ポート デフォルト */
    public static final int SEND_PORT_DEFAULT = 587;

    /** 初期表示フラグ 初期 */
    public static final int INIT_FLG = 0;
    /** 初期表示フラグ 初期済 */
    public static final int NOT_INIT_FLG = 1;

    /** HTMLメールファイル名 */
    public static final String HTMLMAIL_FILENAME = "attach.html";

    /** 処理区分 新規追加 */
    public static final int CMDMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int CMDMODE_EDIT = 1;

    /** 並び順 昇順 */
    public static final int ORDER_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDER_DESC = 1;

    /** アカウント処理モード 個人(通常) */
    public static final int ACCOUNTMODE_NORMAL = 0;
    /** アカウント処理モード 個人(個人設定) */
    public static final int ACCOUNTMODE_PSNLSETTING = 1;
    /** アカウント処理モード 個人(共通) */
    public static final int ACCOUNTMODE_COMMON = 2;

    /** アカウント 状態区分 通常 */
    public static final int WAC_JKBN_NORMAL = 0;
    /** アカウント 状態区分 削除 */
    public static final int WAC_JKBN_DELETE = 1;

    /** アカウント情報 アカウント種別 通常 */
    public static final int WAC_TYPE_NORMAL = 0;
    /** アカウント情報 アカウント種別 共通(グループ) */
    public static final int WAC_TYPE_GROUP = 1;
    /** アカウント情報 アカウント種別 共通(ユーザ) */
    public static final int WAC_TYPE_USER = 2;

    /** アカウント情報 送信サーバ SSL SSLを使用しない */
    public static final int WAC_SEND_SSL_NOTUSE = 0;
    /** アカウント情報 送信サーバ SSL SSLを使用する */
    public static final int WAC_SEND_SSL_USE = 1;

    /** アカウント情報 受信サーバ 種別 POP */
    public static final int WAC_RECEIVE_TYPE_POP = 0;
    /** アカウント情報 受信サーバ 種別 IMAP */
    public static final int WAC_RECEIVE_TYPE_IMAP = 1;

    /** アカウント情報 受信サーバ SSL SSLを使用しない */
    public static final int WAC_RECEIVE_SSL_NOTUSE = 0;
    /** アカウント情報 受信サーバ SSL SSLを使用する */
    public static final int WAC_RECEIVE_SSL_USE = 1;

    /** アカウント情報 ディスク容量制限 制限なし */
    public static final int WAC_DISK_UNLIMITED = 0;
    /** アカウント情報 ディスク容量制限 制限あり */
    public static final int WAC_DISK_LIMIT = 1;
    /** アカウント情報 ディスク容量制限 管理者強制設定なし */
    public static final int WAC_DISK_NO_ADM_COMP = 0;
    /** アカウント情報 ディスク容量制限 管理者強制設定あり */
    public static final int WAC_DISK_ADM_COMP = 1;
    /** アカウント情報 ディスク容量制限 通常 */
    public static final int WAC_DISK_SPS_NORMAL = 0;
    /** アカウント情報 ディスク容量制限 特例設定 */
    public static final int WAC_DISK_SPS_SPSETTINGS = 1;


    /** アカウント情報 受信時削除 削除しない */
    public static final int WAC_DELRECEIVE_NO = 0;
    /** アカウント情報 受信時削除 削除する */
    public static final int WAC_DELRECEIVE_YES = 1;

    /** アカウント情報 受信済みメールを受信 受信しない */
    public static final int WAC_RERECEIVE_NO = 0;
    /** アカウント情報 受信済みメールを受信 受信する */
    public static final int WAC_RERECEIVE_YES = 1;

    /** アカウント情報 APOP 使用しない */
    public static final int WAC_APOP_NOTUSE = 0;
    /** アカウント情報 APOP 使用する */
    public static final int WAC_APOP_USE = 1;

    /** アカウント情報 TOPコマンド 有効 */
    public static final int WAC_TOP_COMMAND_ENABLE = 0;
    /** アカウント情報 TOPコマンド 無効 */
    public static final int WAC_TOP_COMMAND_DISABLE = 1;

    /** アカウント情報 認証形式 基本認証 */
    public static final int WAC_AUTH_TYPE_NORMAL = 0;
    /** アカウント情報 認証形式 OAuth */
    public static final int WAC_AUTH_TYPE_OAUTH = 1;

    /** アカウント情報 SMTP認証 認証しない */
    public static final int WAC_SMTPAUTH_NO = 0;
    /** アカウント情報 SMTP認証 認証する */
    public static final int WAC_SMTPAUTH_YES = 1;

    /** アカウント情報 POP Before SMTP認証 認証しない */
    public static final int WAC_POPBSMTP_NO = 0;
    /** アカウント情報 POP Before SMTP認証 認証する */
    public static final int WAC_POPBSMTP_YES = 1;

    /** アカウント情報 送信文字コード ISO-2022-JP */
    public static final int WAC_ENCODE_SEND_ISO2022JP = 0;
    /** アカウント情報 送信文字コード Unicode(UTF-8) */
    public static final int WAC_ENCODE_SEND_UTF8 = 1;

    /** アカウント情報 送信メール形式 標準 */
    public static final int WAC_SEND_MAILTYPE_NORMAL = 0;
    /** アカウント情報 送信メール形式 HTMLメール */
    public static final int WAC_SEND_MAILTYPE_HTML = 1;

    /** 自動削除区分 自動削除 */
    public static final int WAC_DELKBN_AUTO = 2;

    /** アカウント 署名位置区分 文頭 */
    public static final int WAC_SIGN_POINT_KBN_TOP = 0;
    /** アカウント 署名位置区分 文末 */
    public static final int WAC_SIGN_POINT_KBN_BOTTOM = 1;
    /** アカウント 署名表示区分 非表示 */
    public static final int WAC_SIGN_DSP_KBN_HIDE = 0;
    /** アカウント 署名表示区分 表示 */
    public static final int WAC_SIGN_DSP_KBN_DSP = 1;

    /** アカウント テーマ 未設定 */
    public static final int WAC_THEME_NOSET = 0;
    /** アカウント テーマ デフォルト (Classic) */
    public static final int WAC_THEME_THEME1 = 1;
    /** アカウント テーマ グレー (Classic) */
    public static final int WAC_THEME_THEME2 = 2;
    /** アカウント テーマ 緑 (Classic) */
    public static final int WAC_THEME_THEME3 = 3;
    /** アカウント テーマ 赤 (Classic) */
    public static final int WAC_THEME_THEME4 = 4;
    /** アカウント テーマ ピンク (Classic) */
    public static final int WAC_THEME_THEME5 = 5;
    /** アカウント テーマ 黄色 (Classic) */
    public static final int WAC_THEME_THEME6 = 6;
    /** アカウント テーマ オリジナル*/
    public static final int WAC_THEME_THEME7 = 7;
    /** アカウント テーマ 草原*/
    public static final int WAC_THEME_THEME8 = 8;
    /** アカウント テーマ 夕日*/
    public static final int WAC_THEME_THEME9 = 9;
    /** アカウント テーマ さくら*/
    public static final int WAC_THEME_THEME10 = 10;
    /** アカウント テーマ 青空*/
    public static final int WAC_THEME_THEME11 = 11;
    /** アカウント テーマ シティ*/
    public static final int WAC_THEME_THEME12 = 12;
    /** アカウント テーマ ダーク*/
    public static final int WAC_THEME_THEME13 = 13;
    /** アカウント テーマ 木目*/
    public static final int WAC_THEME_THEME14 = 14;
    /** アカウント テーマ ダークブルー*/
    public static final int WAC_THEME_THEME15 = 15;

    /** アカウント 宛先の確認 確認しない */
    public static final int WAC_CHECK_ADDRESS_NOCHECK = 0;
    /** アカウント 宛先の確認 確認する */
    public static final int WAC_CHECK_ADDRESS_CHECK = 1;
    /** アカウント 添付ファイルの確認 確認しない */
    public static final int WAC_CHECK_FILE_NOCHECK = 0;
    /** アカウント 添付ファイルの確認 確認する */
    public static final int WAC_CHECK_FILE_CHECK = 1;
    /** アカウント 添付ファイル自動圧縮 圧縮しない */
    public static final int WAC_COMPRESS_FILE_NOTCOMPRESS = 0;
    /** アカウント 添付ファイル自動圧縮 圧縮する */
    public static final int WAC_COMPRESS_FILE_COMPRESS = 1;
    /** アカウント 添付ファイル自動圧縮 作成時に選択 */
    public static final int WAC_COMPRESS_FILE_INPUT = 2;
    /** アカウント 時間差送信 未設定 */
    public static final int WAC_TIMESENT_NOSET = 0;
    /** アカウント 時間差送信 後で送信する */
    public static final int WAC_TIMESENT_LATER = 1;
    /** アカウント 時間差送信 作成時に選択 */
    public static final int WAC_TIMESENT_INPUT = 2;

    /** アカウント 添付ファイル自動圧縮 初期値 未設定 */
    public static final int WAC_COMPRESS_FILE_DEF_NOSET = 0;
    /** アカウント 添付ファイル自動圧縮 初期値 圧縮しない */
    public static final int WAC_COMPRESS_FILE_DEF_NOTCOMPRESS = 1;
    /** アカウント 添付ファイル自動圧縮 初期値 圧縮する */
    public static final int WAC_COMPRESS_FILE_DEF_COMPRESS = 2;
    /** アカウント 時間差送信 初期値 未設定 */
    public static final int WAC_TIMESENT_DEF_NOSET = 0;
    /** アカウント 時間差送信 初期値 即時送信 */
    public static final int WAC_TIMESENT_DEF_IMM = 1;
    /** アカウント 時間差送信 初期値 後で送信する */
    public static final int WAC_TIMESENT_DEF_LATER = 2;

    /** アカウント情報 引用符 デフォルト(">") */
    public static final int WAC_QUOTES_DEF = 0;
    /** アカウント情報 引用符 なし */
    public static final int WAC_QUOTES_NONE = 1;
    /** アカウント情報 引用符 ">>" */
    public static final int WAC_QUOTES_2 = 2;
    /** アカウント情報 引用符 "<" */
    public static final int WAC_QUOTES_3 = 3;
    /** アカウント情報 引用符 "<<" */
    public static final int WAC_QUOTES_4 = 4;
    /** アカウント情報 引用符 "|" */
    public static final int WAC_QUOTES_5 = 5;

    /** アカウント情報 自動保存 デフォルト("使用しない") */
    public static final int WAC_AUTO_SAVE_DEF = 0;

    /** 添付ファイルフラグ 無し */
    public static final int TEMPFLG_NOTHING = 0;
    /** 添付ファイルフラグ 有り */
    public static final int TEMPFLG_EXIST = 1;

    /** 返信フラグ 通常 */
    public static final int REPLYFLG_NORMAL = 0;
    /** 返信フラグ 返信済み */
    public static final int REPLYFLG_REPLY = 1;

    /** 転送フラグ 通常 */
    public static final int FORWARDFLG_NORMAL = 0;
    /** 転送フラグ 転送済み */
    public static final int FORWARDFLG_FORWARD = 1;

    /** 未読/既読フラグ 未読 */
    public static final int READEDFLG_NOREAD = 0;
    /** 未読/既読フラグ 既読 */
    public static final int READEDFLG_READED = 1;

    /** メール添付ファイル 状態区分 通常 */
    public static final int TEMPFILE_STATUS_NORMAL = 0;
    /** メール添付ファイル 状態区分 削除 */
    public static final int TEMPFILE_STATUS_DELETE = 9;

    /** メール添付ファイル HTMLメール 通常 */
    public static final int TEMPFILE_HTMLMAIL_NORMAL = 0;
    /** メール添付ファイル HTMLメール HTMLメール */
    public static final int TEMPFILE_HTMLMAIL_HTML = 1;

    /** ディレクトリ種別 受信箱 */
    public static final int DIR_TYPE_RECEIVE = 1;
    /** ディレクトリ種別 送信済み */
    public static final int DIR_TYPE_SENDED = 2;
    /** ディレクトリ種別 予約送信 */
    public static final int DIR_TYPE_NOSEND = 3;
    /** ディレクトリ種別 草稿 */
    public static final int DIR_TYPE_DRAFT = 4;
    /** ディレクトリ種別 ゴミ箱 */
    public static final int DIR_TYPE_DUST = 5;
    /** ディレクトリ種別 迷惑メール */
    public static final int DIR_TYPE_SPAM = 6;
    /** ディレクトリ種別 保管 */
    public static final int DIR_TYPE_STORAGE = 7;

    /** 送信先種別 TO */
    public static final int WSA_TYPE_TO = 1;
    /** 送信先種別 CC */
    public static final int WSA_TYPE_CC = 2;
    /** 送信先種別 BCC */
    public static final int WSA_TYPE_BCC = 3;

    /** 受信メール 状態 0:通常 */
    public static final int WMD_STATUS_NORMAL = 0;
    /** 受信メール 状態 9:ゴミ箱 */
    public static final int WMD_STATUS_DUST = 9;

    /** ソートキー アカウント名 */
    public static final int SKEY_ACCOUNTNAME = 0;
    /** ソートキー メールアドレス */
    public static final int SKEY_MAIL = 1;
    /** ソートキー 使用者 */
    public static final int SKEY_USER = 2;
    /** ソートキー ディスク使用量 */
    public static final int SKEY_DISKSIZE = 3;
    /** ソートキー 受信日時 */
    public static final int SKEY_RECEIVEDATE = 4;
    /** ソートキー 認証形式 */
    public static final int SKEY_AUTHTYPE = 5;

    /** ソートキー 件名 */
    public static final int SKEY_TITLE = 0;
    /** ソートキー 差出人 */
    public static final int SKEY_FROM = 1;
    /** ソートキー 宛先 */
    public static final int SKEY_TO = 2;
    /** ソートキー 日時 */
    public static final int SKEY_DATE = 3;

    /** ラベル区分 アカウント固有 */
    public static final int LABELTYPE_ONES = 0;
    /** ラベル区分 全てのアカウント */
    public static final int LABELTYPE_ALL = 1;

    /** 条件１ */
    public static final int CONDITION_ONE = 1;
    /** 条件２ */
    public static final int CONDITION_TWO = 2;
    /** 条件３ */
    public static final int CONDITION_THREE = 3;
    /** 条件４ */
    public static final int CONDITION_FOUR = 4;
    /** 条件５ */
    public static final int CONDITION_FIVE = 5;

    /** 受信メール 返信フラグ 通常 */
    public static final int WMD_REPLY_NORMAL = 0;
    /** 受信メール 返信フラグ 返信済み */
    public static final int WMD_REPLY_REPLY = 1;
    /** 受信メール 転送フラグ 通常 */
    public static final int WMD_FORWARD_NORMAL = 0;
    /** 受信メール 転送フラグ 転送済み */
    public static final int WMD_FORWARD_FORWARD = 1;
    /** 受信メール 未読/既読フラグ 未読 */
    public static final int WMD_READED_NO = 0;
    /** 受信メール 未読/既読フラグ 既読 */
    public static final int WMD_READED_YES = 1;
    /** メール情報 編集種別 未設定 */
    public static final int WMD_EDITFLG_NOSET = 0;
    /** メール情報 編集種別 返信 */
    public static final int WMD_EDITFLG_REPLY = 1;
    /** メール情報 編集種別 転送 */
    public static final int WMD_EDITFLG_FORWARD = 2;

    /** メール種別 受信 */
    public static final int MAILTYPE_RECEIVE = 0;
    /** メール種別 送信 */
    public static final int MAILTYPE_SEND = 1;

    /** デフォルトディレクトリ 通常 */
    public static final int DEF_NORMAL = 0;
    /** デフォルトディレクトリ デフォルトディレクトリ */
    public static final int DEF_DEFAULT = 1;

    /** ユーザ区分 グループ */
    public static final int USRKBN_GROUP = 0;
    /** ユーザ区分 ユーザ */
    public static final int USRKBN_USER = 1;

    /** アカウント作成区分 制限なし */
    public static final int KANRI_USER_NO = 0;
    /** アカウント作成区分 管理者のみ作成可能 */
    public static final int KANRI_USER_ONLY = 1;

    /** アカウント 送信メール形式 未設定 */
    public static final int ACNT_SENDFORMAT_NOSET = 0;
    /** アカウント 送信メール形式 TEXT形式 */
    public static final int ACNT_SENDFORMAT_TEXT = 1;
    /** アカウント 送信メール形式 HTML形式 */
    public static final int ACNT_SENDFORMAT_HTML = 2;

    /** アカウント 送受信ログの登録 登録する */
    public static final int ACNT_LOG_REGIST_REGIST = 0;
    /** アカウント 送受信ログの登録 登録しない */
    public static final int ACNT_LOG_REGIST_NOTREGIST = 1;

    /** アカウント 自動受信区分 未設定 */
    public static final int AUTO_RECEIVE_FIRST = 0;

    /** アカウント 自動受信時間 5分 */
    public static final int AUTO_RECEIVE_5 = 5;
    /** アカウント 自動受信時間 10分 */
    public static final int AUTO_RECEIVE_10 = 10;
    /** アカウント 自動受信時間 30分 */
    public static final int AUTO_RECEIVE_30 = 30;
    /** アカウント 自動受信時間 60分 */
    public static final int AUTO_RECEIVE_60 = 60;

    /** アカウント署名 デフォルト 通常 */
    public static final int WSI_DEF_NORMAL = 0;
    /** アカウント署名 デフォルト デフォルト */
    public static final int WSI_DEF_DEFAULT = 1;

    /** 権限設定 管理者 */
    public static final int PERMIT_ADMIN = 0;
    /** 権限設定 アカウント単位 */
    public static final int PERMIT_ACCOUNT = 1;

    /** 自動受信時間キーALL */
    public static final int[] LIST_AUTO_REV_KEY_ALL = new int[] { AUTO_RECEIVE_5,
        AUTO_RECEIVE_10, AUTO_RECEIVE_30, AUTO_RECEIVE_60 };
    /** 自動保存ラベルの選択値 */
    public static final String[] MINUTE_VALUE
    = new String[] {"0", "1", "2", "3", "4", "5", "10"};
    /** CSV 自動保存 */
    public static final String[] CSV_MINUTE_VALUE
    = new String[] {"0", "1", "2", "3", "4", "5", "6"};

    /** 迷惑メールディレクトリ区分 表示 */
    public static final int DSP_VIEW_OK = 0;
    /** 迷惑メールディレクトリ区分 非表示 */
    public static final int DSP_VIEW_NO = 1;

    /** メール自動受信 受信しない */
    public static final int MAIL_AUTO_RSV_OFF = 0;
    /** メール自動受信 受信する */
    public static final int MAIL_AUTO_RSV_ON = 1;

    /** メール送信 リターンコード 正常終了 */
    public static final int SEND_RTNCODE_SUCCESS = 0;
    /** メール送信 リターンコード メール送信に失敗 */
    public static final int SEND_RTNCODE_FAIL = 1;
    /** メール送信 リターンコード ディスク使用量制限のため送信不能 */
    public static final int SEND_RTNCODE_DISKOVER = 2;

    /** アカウントインポート用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME = "import_account.xls";

    /** メッセージに表示するテキスト 検索キーワード */
    public static final String TEXT_SEARCH_KEYWORD = "cmn.keyword";
    /** メッセージに表示するテキスト ラベル名 */
    public static final String TEXT_LABEL = "cmn.label.name";
    /** メッセージに表示するテキスト フィルター名 */
    public static final String TEXT_FILTER = "wml.84";
    /** メッセージに表示するテキスト 条件１ */
    public static final String TEXT_CONDITION1 = "wml.wml140kn.05";
    /** メッセージに表示するテキスト 条件２ */
    public static final String TEXT_CONDITION2 = "wml.wml140kn.04";
    /** メッセージに表示するテキスト 条件３ */
    public static final String TEXT_CONDITION3 = "wml.wml140kn.03";
    /** メッセージに表示するテキスト 条件４ */
    public static final String TEXT_CONDITION4 = "wml.wml140kn.02";
    /** メッセージに表示するテキスト 条件５ */
    public static final String TEXT_CONDITION5 = "wml.wml140kn.01";
    /** メッセージに表示するテキスト 転送先アドレス */
    public static final String TEXT_FORWARDADRESS = "wml.201";
    /** メッセージに表示するテキスト 転送先アドレス */
    public static final String TEXT_CONDITION = "wml.wml140.02";
    /** メッセージに表示するテキスト アカウントID */
    public static final String TEXT_ACCOUNT_ID = "wml.281";
    /** メッセージに表示するテキスト アカウント名 */
    public static final String TEXT_ACCOUNT = "wml.96";
    /** メッセージに表示するテキスト メールアドレス */
    public static final String TEXT_ADDRESS = "cmn.mailaddress";
    /** メッセージに表示するテキスト メール受信サーバ名 */
    public static final String TEXT_SERVER_RECEIVE = "wml.81";
    /** メッセージに表示するテキスト メール受信ポート番号 */
    public static final String TEXT_PORT_RECEIVE = "wml.155";
    /** メッセージに表示するテキスト メール受信 SSLの使用 */
    public static final String TEXT_SSL_RECEIVE = "wml.153";
    /** メッセージに表示するテキスト 受信サーバユーザ名 */
    public static final String TEXT_USER_RECEIVE = "wml.195";
    /** メッセージに表示するテキスト 受信サーバパスワード */
    public static final String TEXT_PASS_RECEIVE = "wml.194";
    /** メッセージに表示するテキスト メール送信サーバ */
    public static final String TEXT_SERVER_SEND = "wml.80";
    /** メッセージに表示するテキスト メール送信サーバユーザ名 */
    public static final String TEXT_USER_SEND = "wml.158";
    /** メッセージに表示するテキスト メール送信ポート番号 */
    public static final String TEXT_PORT_SEND = "wml.159";
    /** メッセージに表示するテキスト メール受信 SSLの使用 */
    public static final String TEXT_SSL_SEND = "wml.156";
    /** メッセージに表示するテキスト メール送信サーバパスワード */
    public static final String TEXT_PASS_SEND = "wml.157";
    /** メッセージに表示するテキスト ディスク容量 */
    public static final String TEXT_DISK = "wml.87";
    /** メッセージに表示するテキスト 備考 */
    public static final String TEXT_BIKO = "cmn.memo";
    /** メッセージに表示するテキスト 使用者 グループ */
    public static final String TEXT_GROUP = "wml.188";
    /** メッセージに表示するテキスト 使用者ユーザ */
    public static final String TEXT_USER = "wml.189";
    /** メッセージに表示するテキスト 組織名 */
    public static final String TEXT_ORGANIZE = "wml.25";
    /** メッセージに表示するテキスト 署名 */
    public static final String TEXT_SIGN = "wml.34";
    /** メッセージに表示するテキスト 自動TO */
    public static final String TEXT_AUTO_TO = "wml.52";
    /** メッセージに表示するテキスト 自動CC */
    public static final String TEXT_AUTO_CC = "wml.53";
    /** メッセージに表示するテキスト 自動BCC */
    public static final String TEXT_AUTO_BCC = "wml.54";
    /** メッセージに表示するテキスト 日付 */
    public static final String TEXT_DATE = "cmn.date2";
    /** メッセージに表示するテキスト メールアドレス検索項目 */
    public static final String TEXT_SEARCH_ADDRESS = "wml.147";
    /** メッセージに表示するテキスト 件名 */
    public static final String TEXT_NAME = "cmn.subject";
    /** メッセージに表示するテキスト ラベル */
    public static final String TEXT_SEL_LABEL = "cmn.label";
    /** メッセージに表示するテキスト ラベル */
    public static final String TEXT_MAIL_AUTO_RSV = "wml.152";
    /** メッセージに表示するテキスト SMTP認証 */
    public static final String TEXT_SMTP_NINSYO = "wml.121";
    /** メッセージに表示するテキスト 受信時削除 */
    public static final String TEXT_RSV_DELETE = "wml.36";
    /** メッセージに表示するテキスト 受信済みでも受信 */
    public static final String TEXT_RSV_OK = "wml.39";
    /** メッセージに表示するテキスト 送信前POP認証 */
    public static final String TEXT_SEND_POP = "wml.17";
    /** メッセージに表示するテキスト 送信文字コード */
    public static final String TEXT_SEND_WORDCODE = "wml.wml040kn.01";
    /** メッセージに表示するテキスト 送信メール形式 */
    public static final String TEXT_SEND_TYPE = "cmn.format.";
    /** メッセージに表示するテキスト 使用者ユーザ */
    public static final String TEXT_USE_USER = "cmn.employer.user";
    /** メッセージに表示するテキスト APOP */
    public static final String TEXT_APOP = "APOP";
    /** メッセージに表示するテキスト TOPコマンド */
    public static final String TEXT_TOP_CMD = "wml.278";

    /** メールヘッダー MAX文字列 */
    public static final int MAX_LEN_MAILHEADER = 200;
    /** 検索キーワード MAX文字数 */
    public static final int MAXLEN_SEARCH_KEYWORD = 100;
    /** 条件 MAX文字数 */
    public static final int MAXLEN_CONDITION_KEYWORD = 256;
    /** アカウントID MAX文字数 */
    public static final int MAXLEN_ACCOUNT_ID = 100;
    /** アカウント 名前 MAX文字数 */
    public static final int MAXLEN_ACCOUNT = 200;
    /** アカウント サーバ MAX文字数 */
    public static final int MAXLEN_ACCOUNT_SERVER = 100;
    /** アカウント サーバ ユーザ MAX文字数 */
    public static final int MAXLEN_ACCOUNT_SERVER_USER = 256;
    /** アカウント サーバ パスワード MAX文字数 */
    public static final int MAXLEN_ACCOUNT_SERVER_PASS = 100;
    /** アカウント アドレス　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_ADDRESS = 256;
    /** アカウント ポート番号　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_PORT = 5;
    /** アカウント ディスク容量　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_DISK = 6;
    /** アカウント 備考　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_BIKO = 1000;
    /** アカウント 署名　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_SIGN = 1000;
    /** アカウント 署名 タイトル　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_SIGN_TITLE = 100;
    /** アカウント 組織名　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_ORGANIZE = 100;
    /** 件名 MAX文字数 */
    public static final int MAXLEN_NAME = 100;

    /** メール 件名 MAX文字数 */
    public static final int MAXLEN_MAIL_SUBJECT = 100;
    /** メール添付ファイル ファイル拡張子 MAX文字数 */
    public static final int MAXLEN_WTF_FILE_EXTENSION = 20;

    /** 送信メールサイズの制限 メールサイズ上限 MAX文字数 */
    public static final int MAXLEN_SEND_MAXSIZE = 6;

    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** 受信サーバポート 初期値 */
    public static final int SERVER_DEFO_RESV = 110;
    /** 送信サーバポート 初期値 */
    public static final int SERVER_DEFO_SEND = 25;

    /** 検索未実行 */
    public static final int SEARCH_EXECUTE_FALSE = 0;
    /** 検索実行 */
    public static final int SEARCH_EXECUTE_TRUE = 1;

    /** 日時区分 一致する */
    public static final int DATE_KBN_EQUAL = 1;
    /** 日時区分 以前 */
    public static final int DATE_KBN_BEFORE = 2;
    /** 日時区分 以降 */
    public static final int DATE_KBN_AFTER = 3;
    /** 日時区分 範囲指定 */
    public static final int DATE_KBN_DATEAREA = 4;

    /** メールアドレス検索区分 未チェック */
    public static final int ADDRESS_KBN_NO = 0;
    /** メールアドレス検索区分 チェック */
    public static final int ADDRESS_KBN_OK = 1;

    /** 種別 受信 */
    public static final int TYPE_RESV = 0;
    /** 種別 送信 */
    public static final int TYPE_SEND = 1;
    /** 種別 指定なし */
    public static final int TYPE_FREE = 2;

    /** 検索日付コンボ未選択 */
    public static final int SELECT_DATECOMBO = -1;

    /** 1ページ表示件数 10件 */
    public static final int LIMIT_DSP = 10;
    /** アカウント 1ページ表示件数 30件 */
    public static final int LIMIT_DSP_ACCOUNT = 30;
    /** 送受信ログ管理 1ページ表示件数 50件 */
    public static final int LIMIT_DSP_MAILLOG = 50;
    /** 送信先リスト 1ページ表示件数 30件 */
    public static final int LIMIT_DSP_SENDLIST = 30;
    /** 一括削除リスト 1ページ表示件数 30件 */
    public static final int LIMIT_DSP_DELETELIST = 30;

    /** ゴミ箱自動削除区分 設定しない */
    public static final int WAD_DUST_NO = 0;
    /** ゴミ箱自動削除区分 ログアウト毎に削除 */
    public static final int WAD_DUST_LOGOUT = 1;
    /** ゴミ箱自動削除区分 自動削除 */
    public static final int WAD_DUST_AUTO = WAC_DELKBN_AUTO;
    /** 送信済み自動削除区分 設定しない */
    public static final int WAD_SEND_NO = 0;
    /** 送信済み自動削除区分 ログアウト毎に削除 */
    public static final int WAD_SEND_LOGOUT = 1;
    /** 送信済み自動削除区分 自動削除 */
    public static final int WAD_SEND_AUTO = WAC_DELKBN_AUTO;
    /** 草稿自動削除区分 設定しない */
    public static final int WAD_DRAFT_NO = 0;
    /** 草稿自動削除区分 ログアウト毎に削除 */
    public static final int WAD_DRAFT_LOGOUT = 1;
    /** 草稿自動削除区分 自動削除 */
    public static final int WAD_DRAFT_AUTO = WAC_DELKBN_AUTO;
    /** 受信箱 自動削除区分 設定しない */
    public static final int WAD_RESV_NO = 0;
    /** 受信箱 自動削除区分 ログアウト毎に削除 */
    public static final int WAD_RESV_LOGOUT = 1;
    /** 受信箱 自動削除区分 自動削除 */
    public static final int WAD_RESV_AUTO = WAC_DELKBN_AUTO;
    /** 保管 自動削除区分 設定しない */
    public static final int WAD_KEEP_NO = 0;
    /** 保管 自動削除区分 ログアウト毎に削除 */
    public static final int WAD_KEEP_LOGOUT = 1;
    /** 保管 自動削除区分 自動削除 */
    public static final int WAD_KEEP_AUTO = WAC_DELKBN_AUTO;
    /** 送信メールサイズの制限 制限なし */
    public static final int WAD_SEND_LIMIT_UNLIMITED = 0;
    /** 送信メールサイズの制限 制限あり */
    public static final int WAD_SEND_LIMIT_LIMITED = 1;
    /** メール転送制限 転送可 */
    public static final int WAD_FWLIMIT_UNLIMITED = 0;
    /** メール転送制限 転送制限あり */
    public static final int WAD_FWLIMIT_LIMITED = 1;
    /** メール転送制限 転送禁止 */
    public static final int WAD_FWLIMIT_PROHIBITED = 2;
    /** BCC強制変換 制限なし */
    public static final int WAD_BCC_UNLIMITED = 0;
    /** BCC強制変換 強制変換 */
    public static final int WAD_BCC_CONVERSION = 1;
    /** ディスク容量警告 未設定 */
    public static final int WAD_WARN_DISK_NO = 0;
    /** ディスク容量警告 警告する */
    public static final int WAD_WARN_DISK_YES = 1;
    /** サーバー情報の設定 許可しない */
    public static final int WAD_SETTING_SERVER_NO = 0;
    /** サーバー情報の設定 許可する */
    public static final int WAD_SETTING_SERVER_YES = 1;
    /** 代理人 許可しない */
    public static final int WAD_PROXY_USER_NO = 0;
    /** 代理人 許可する */
    public static final int WAD_PROXY_USER_YES = 1;
    /** リンク 制限なし*/
    public static final int WAD_LINK_UNLIMITED = 0;
    /** リンク 制限あり*/
    public static final int WAD_LINK_LIMITED = 1;
    /** TLD 制限なし*/
    public static final int WAD_TLD_UNLIMITED = 0;
    /** TLD 制限あり*/
    public static final int WAD_TLD_LIMITED = 1;

    /** 管理者設定 添付ファイル自動圧縮 圧縮しない */
    public static final int WAD_COMPRESS_FILE_NOTCOMPRESS = 0;
    /** 管理者設定 添付ファイル自動圧縮 圧縮する */
    public static final int WAD_COMPRESS_FILE_COMPRESS = 1;
    /** 管理者設定 添付ファイル自動圧縮 作成時に選択 */
    public static final int WAD_COMPRESS_FILE_INPUT = 2;
    /** 管理者設定 時間差送信 未設定 */
    public static final int WAD_TIMESENT_NOSET = 0;
    /** 管理者設定 時間差送信 後で送信する */
    public static final int WAD_TIMESENT_LATER = 1;
    /** 管理者設定 時間差送信 作成時に選択 */
    public static final int WAD_TIMESENT_INPUT = 2;

    /** 管理者設定 添付ファイル自動圧縮 初期値 未設定 */
    public static final int WAD_COMPRESS_FILE_DEF_NOSET = 0;
    /** 管理者設定 添付ファイル自動圧縮 初期値 圧縮しない */
    public static final int WAD_COMPRESS_FILE_DEF_NOTCOMPRESS = 1;
    /** 管理者設定 添付ファイル自動圧縮 初期値 圧縮する */
    public static final int WAD_COMPRESS_FILE_DEF_COMPRESS = 2;
    /** 管理者設定 時間差送信 初期値 未設定 */
    public static final int WAD_TIMESENT_DEF_NOSET = 0;
    /** 管理者設定 時間差送信 初期値 即時送信 */
    public static final int WAD_TIMESENT_DEF_IMM = 1;
    /** 管理者設定 時間差送信 初期値 後で送信する */
    public static final int WAD_TIMESENT_DEF_LATER = 2;

    /** 送受信ログ 自動削除区分 設定しない */
    public static final int WAL_KBN_NOSET = 0;
    /** 送受信ログ 自動削除区分 自動削除 */
    public static final int WAL_KBN_AUTODELETE = 1;

    /** 自動削除区分 設定しない */
    public static final String SETUP_NO = "cmn.noset";
    /** 自動削除区分 ログアウト毎に削除 */
    public static final String SETUP_LOGOUT = "wml.wml050.02";
    /** 自動削除区分 自動削除 */
    public static final String SETUP_AUTO = "cmn.autodelete";

    /** 手動削除区分 削除しない */
    public static final int MANU_DEL_NO = 0;
    /** 手動削除区分 削除する */
    public static final int MANU_DEL_OK = 1;

    /** 手動削除区分 削除しない */
    public static final String MANU_SETUP_NO = "cmn.dont.delete";
    /** 手動削除区分 削除する */
    public static final String MANU_SETUP_OK = "wml.60";

    /** フィルター種別 アカウント固有 */
    public static final int WFT_TYPE_ONE = 0;
    /** フィルター種別 全てのアカウント */
    public static final int WFT_TYPE_ALL = 1;

    /** フィルター設定 全ての条件に一致 */
    public static final String ALL_CONDITION = "wml.wml140.01";
    /** フィルター設定 いずれかの条件に一致 */
    public static final String ANY_CONDITION = "wml.containing.either";
    /** フィルター設定 件名 */
    public static final String FILTER_TITLE = "cmn.subject";
    /** フィルター設定 宛先 */
    public static final String FILTER_ADDRESS = "cmn.from";
    /** フィルター設定 差出人 */
    public static final String FILTER_SEND = "cmn.sendfrom";
    /** フィルター設定 本文 */
    public static final String FILTER_MAIN = "cmn.body";
    /** フィルター設定 に次を含む */
    public static final String FILTER_INCLUDE = "wml.141";
    /** フィルター設定 に次を含まない */
    public static final String FILTER_EXCLUDE = "wml.140";
    /** フィルター設定 全てのアカウント */
    public static final String FILTER_ALLACCOUNT = "wml.wml120.01";

    /** フィルター設定 件名 */
    public static final int FILTER_TYPE_TITLE = 0;
    /** フィルター設定 宛先 */
    public static final int FILTER_TYPE_ADDRESS = 1;
    /** フィルター設定 CC */
    public static final int FILTER_TYPE_CC = 4;
    /** フィルター設定 差出人 */
    public static final int FILTER_TYPE_SEND = 2;
    /** フィルター設定 本文 */
    public static final int FILTER_TYPE_MAIN = 3;
    /** フィルター設定 に次を含む */
    public static final int FILTER_TYPE_INCLUDE = 0;
    /** フィルター設定 に次を含まない */
    public static final int FILTER_TYPE_EXCLUDE = 1;

    /** フィルター設定 添付ファイル 未設定 */
    public static final int FILTER_TEMPFILE_NO = 0;
    /** フィルター設定 添付ファイル 添付ファイルを条件に含む */
    public static final int FILTER_TEMPFILE_YES = 1;

    /** フィルター設定 ラベル 未設定 */
    public static final int FILTER_LABEL_NOSET = 0;
    /** フィルター設定 ラベル ラベルを付与する */
    public static final int FILTER_LABEL_SETLABEL = 1;

    /** フィルター設定 動作_既読にする 未設定 */
    public static final int FILTER_READED_NOSET = 0;
    /** フィルター設定 動作_既読にする 既読にする */
    public static final int FILTER_READED_SETREADED = 1;

    /** フィルター設定 動作_フォルダに移動する 未設定 */
    public static final int FILTER_MOVE_NOSET = 0;
    /** フィルター設定 動作_フォルダに移動する ゴミ箱に移動する */
    public static final int FILTER_MOVE_DUST = 1;
    /** フィルター設定 動作_フォルダに移動する 保管に移動する */
    public static final int FILTER_MOVE_STRAGE = 2;

    /** フィルター設定 動作_指定アドレスへ転送する 未設定 */
    public static final int FILTER_FORWARD_NOSET = 0;
    /** フィルター設定 動作_指定アドレスへ転送する 転送する */
    public static final int FILTER_FORWARD_SEND = 1;

    /** フィルター設定 条件 全ての条件と一致する */
    public static final int FILTER_CONDITION_AND = 0;
    /** フィルター設定 条件 いずれかの条件と一致する */
    public static final int FILTER_CONDITION_OR = 1;

    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** ソートキー 添付ファイル */
    public static final int SORTKEY_TEMPFILE = 1;
    /** ソートキー タイトル */
    public static final int SORTKEY_TITLE = 2;
    /** ソートキー 送信先 */
    public static final int SORTKEY_MAILADDRESS = 3;
    /** ソートキー 送信日 */
    public static final int SORTKEY_SDATE = 4;
    /** ソートキー 未読/既読 */
    public static final int SORTKEY_READED = 5;
    /** ソートキー サイズ */
    public static final int SORTKEY_SIZE = 6;

    /** メール情報表示順 ソートキー 添付ファイルの有無 */
    public static final int WMS_SKEY_FILE = 1;
    /** メール情報表示順 ソートキー 件名 */
    public static final int WMS_SKEY_SUBJECT = 2;
    /** メール情報表示順 ソートキー 差出人 */
    public static final int WMS_SKEY_FROM = 3;
    /** メール情報表示順 ソートキー 日時 */
    public static final int WMS_SKEY_DATE = 4;
    /** メール情報表示順 ソートキー 未読/既読 */
    public static final int WMS_SKEY_READED = 5;
    /** メール情報表示順 ソートキー サイズ */
    public static final int WMS_SKEY_SIZE = 6;

    /** メール情報_送信予約 送信区分 未設定 */
    public static final int WSP_SENDKBN_NOSET = 0;
    /** メール情報_送信予約 送信区分 送信対象 */
    public static final int WSP_SENDKBN_SENDTARGET = 1;
    /** メール情報_送信予約 メール種別 通常 */
    public static final int WSP_MAILTYPE_NORMAL = 0;
    /** メール情報_送信予約 メール種別 HTMLメール */
    public static final int WSP_MAILTYPE_HTML = 1;
    /** メール情報_送信予約 添付ファイルの圧縮 未設定 */
    public static final int WSP_COMPRESS_FILE_NOSET = 0;
    /** メール情報_送信予約 添付ファイルの圧縮 圧縮しない */
    public static final int WSP_COMPRESS_FILE_NOTCOMPRESS = 1;
    /** メール情報_送信予約 添付ファイルの圧縮 圧縮する */
    public static final int WSP_COMPRESS_FILE_COMPRESS = 2;

    /** 送信先リスト_送信先 種別 ユーザ情報 */
    public static final int WDA_TYPE_USER = 0;
    /** 送信先リスト_送信先 種別 アドレス帳 */
    public static final int WDA_TYPE_ADDRESS = 1;
    /**送信先リスト_アクセス設定 ユーザ区分 ユーザ */
    public static final int WLA_KBN_USER = 0;
    /**送信先リスト_アクセス設定 ユーザ区分 グループ */
    public static final int WLA_KBN_GROUP = 1;
    /**送信先リスト_アクセス設定 権限区分 閲覧のみ */
    public static final int WLA_AUTH_READ = 0;
    /**送信先リスト_アクセス設定 権限区分 追加・編集・削除可能 */
    public static final int WLA_AUTH_ALL = 1;

    /** 年 0年 */
    public static final int YEAR_ZERO = 0;
    /** 年 1年 */
    public static final int YEAR_ONE = 1;
    /** 年 2年 */
    public static final int YEAR_TWO = 2;
    /** 年 3年 */
    public static final int YEAR_THREE = 3;
    /** 年 4年 */
    public static final int YEAR_FOUR = 4;
    /** 年 5年 */
    public static final int YEAR_FIVE = 5;
    /** 年 10年 */
    public static final int YEAR_TEN = 10;

    /** 削除 月 開始月 */
    public static final int DEL_MONTH_START = 0;
    /** 削除 月 終了月 */
    public static final int DEL_MONTH_END = 11;
    /** 削除 日 開始日 */
    public static final int DEL_DAY_START = 0;
    /** 削除 日 終了日 */
    public static final int DEL_DAY_END = 30;

    /** 年キーALL */
    public static final int[] LIST_YEAR_KEY_ALL = new int[] { YEAR_ZERO,
        YEAR_ONE, YEAR_TWO, YEAR_THREE, YEAR_FOUR, YEAR_FIVE, YEAR_TEN };

    /** メール情報、UIDLの最大削除数 */
    public static final int MAX_DEL_MAILDATA = 100;

    /** ログ 新着メールを確認 */
    public static final String LOG_VALUE_NEWMAIL = "wml.196";
    /** ログ ゴミ箱へ移動 */
    public static final String LOG_VALUE_DUSTMAIL = "wml.137";
    /** ログ ゴミ箱を空にする */
    public static final String LOG_VALUE_EMPTYTRASH = "cmn.empty.trash";
    /** ログ ラベル設定 */
    public static final String LOG_VALUE_LABEL = "cmn.label.settings";
    /** ログ メールの移動 */
    public static final String LOG_VALUE_MOVEMAIL = "wml.wml010.20";
    /** ログ 添付ファイルの削除 */
    public static final String LOG_VALUE_TEMPDEL = "wml.wml010.36";
    /** ログ メールの削除 */
    public static final String LOG_VALUE_DELETEMAIL = "wml.326";

    /** 返信時署名表示 表示しない */
    public static final int SIGN_RECEIVE_NOT_DSP = 0;
    /** 返信時署名表示 表示する */
    public static final int SIGN_RECEIVE_DSP = 1;

    /** 署名位置 先頭 */
    public static final int SIGN_POINT_TOP = 0;
    /** 署名位置 文末 */
    public static final int SIGN_POINT_BOTTOM = 1;

    /** 署名 自動挿入 挿入する */
    public static final int WAC_SIGN_AUTO_INSERT = 0;
    /** 署名 自動挿入 挿入しない */
    public static final int WAC_SIGN_AUTO_NO = 1;

    /** メールテンプレート 種別 共通 */
    public static final int WTP_TYPE_COMMON = 0;
    /** メールテンプレート 種別 アカウント固有 */
    public static final int WTP_TYPE_ACCOUNT = 1;

    /** メールテンプレート区分 通常 */
    public static final int MAILTEMPLATE_NORMAL = 0;
    /** メールテンプレート区分 共通 */
    public static final int MAILTEMPLATE_COMMON = 1;

    /** 集計データ ログ区分 送信 */
    public static final int LOG_COUNT_KBN_SMAIL = 0;
    /** 集計データ ログ区分 受信 */
    public static final int LOG_COUNT_KBN_JMAIL = 1;

    /** 統計グラフ　受信メール数 */
    public static final String WML_LOG_GRAPH_JMAIL = "wml_graph_jmail";
    /** 統計グラフ　送信メール数 */
    public static final String WML_LOG_GRAPH_SMAIL = "wml_graph_smail";

    /** ログ出力種別判別フラグ なし */
    public static final int WML_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ 添付ファイル */
    public static final int WML_LOG_FLG_DOWNLOAD = 0;
    /** ログ出力種別判別フラグ PDF */
    public static final int WML_LOG_FLG_PDF = 1;
    /** ログ出力種別判別フラグ EML */
    public static final int WML_LOG_FLG_EML = 2;

    /** 受信失敗通知受け取りフラグ 未受信 */
    public static final int WML_FAILEDNOTICE_NOTRECEIVED = 0;
    /** 受信失敗通知受け取りフラグ 受信済み */
    public static final int WML_FAILEDNOTICE_RECEIVED = 1;

    /** 一括削除ソートキー 差出人 */
    public static final int WML_DELETE_FROM = 1;
    /** 一括削除ソートキー 件名 */
    public static final int WML_DELETE_TITLE = 2;
    /** 一括削除ソートキー 日時 */
    public static final int WML_DELETE_SDATE = 3;


    /** アカウントID 共通 */
    public static final int WML_ACCOUNT_COMMON = 0;

    /** 添付ファイル一括ダウンロード時のTempフォルダ名 */
    public static final String WML_FILE_LIST = "FileList";

    /** 草稿保存時 送信先の連番値開始番号*/
    public static final int START_WLS_NUM = 1000000;

    /** 整数型のメール添付ファイル ファイル名MAX文字数 **/
    public static final int MAXLEN_WTF_FILE_NAME = 260;

    /** RESTAPI メールボックス名 受信*/
    public static final String RESTAPI_MAILBOX_INBOX = "inbox";
    /** RESTAPI メールボックス名 送信*/
    public static final String RESTAPI_MAILBOX_SENT = "sent";
    /** RESTAPI メールボックス名 予約送信*/
    public static final String RESTAPI_MAILBOX_FUTURE = "future";
    /** RESTAPI メールボックス名 草稿*/
    public static final String RESTAPI_MAILBOX_DRAFT = "draft";
    /** RESTAPI メールボックス名 ゴミ箱*/
    public static final String RESTAPI_MAILBOX_TRASH = "trash";
    /** RESTAPI メールボックス名 保管*/
    public static final String RESTAPI_MAILBOX_KEEP = "keep";

    /** ディスク容量警告 警告なし(警告の閾値に達していない) */
    public static final int WARN_DISK_NO = 0;
    /** ディスク容量警告 警告あり(警告の閾値に達している) */
    public static final int WARN_DISK_YES = 1;

    /** 予約送信タイプ 即時送信 */
    public static final int TIMESENT_NORMAL = 0;
    /** 予約送信タイプ 時間差送信 */
    public static final int TIMESENT_AFTER = 1;
    /** 予約送信タイプ 予約送信 */
    public static final int TIMESENT_FUTURE = 2;

    /** RESTAPI 自動圧縮フラグ 圧縮しない */
    public static final int RESTAPI_COMPRESS_NO = 0;
    /** RESTAPI 自動圧縮フラグ 圧縮する */
    public static final int RESTAPI_COMPRESS_YES = 1;

    /** 送信メール 種別 新規作成 */
    public static final int SEND_TYPE_NORMAL = 0;
    /** 送信メール 種別 返信 */
    public static final int SEND_TYPE_REPLY = 1;
    /** 送信メール 種別 全員に返信 */
    public static final int SEND_TYPE_REPLY_ALL = 2;
    /** 送信メール 種別 転送 */
    public static final int SEND_TYPE_FORWARD = 3;
    /** 送信メール 種別 編集 */
    public static final int SEND_TYPE_EDIT = 4;

    /** RESTAPI メールアドレス 最大文字数 */
    public static final int RESTAPI_MAXLEN_ACCOUNT_ADDRESS = 768;

    /** 送信メール HTMLメール テキストメール */
    public static final int SEND_HTMLMAIL_TEXT = 0;
    /** 送信メール HTMLメール HTMLメール */
    public static final int SEND_HTMLMAIL_HTML = 1;
}