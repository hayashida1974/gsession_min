package jp.groupsession.v2.cmn;

/**
 * <br>[機  能] 共通画面で使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstCommon {

    /** 採番区分 SID 共通 */
    public static final String SBNSID_COMMON = "common";
    /** 採番区分 SID SUB */
    public static final String SBNSID_SUB_COMMON = "cmn";
    /** 採番区分 SID SUB マイグループ */
    public static final String SBNSID_SUB_MYGROUP = "mygroup";
    /** 採番区分 SID SUB Oauth認証情報SID */
    public static final String SBNSID_SUB_OAUTH = "oauth";
    /** 採番区分 SID SUB Oauth認証トークンSID */
    public static final String SBNSID_SUB_OAUTHTOKEN = "oauthtoken";
    /** 採番区分 SID SUB 連携APISID */
    public static final String SBNSID_SUB_APICON = "apiconnect";
    /** 採番区分 SID SUB 連携API履歴SID */
    public static final String SBNSID_SUB_APIHIS = "apihistory";
    /** プラグインID 共通 */
    public static final String PLUGIN_ID_COMMON = "common";
    /** プラグインID スケジュール */
    public static final String PLUGIN_ID_SCHEDULE = "schedule";
    /** プラグインID 施設予約 */
    public static final String PLUGIN_ID_RESERVE = "reserve";
    /** プラグインID プロジェクト */
    public static final String PLUGIN_ID_PROJECT = "project";
    /** プラグインID 日報 */
    public static final String PLUGIN_ID_NIPPOU = "nippou";

    /** マイグループ名MAX文字数 */
    public static final int MAX_LENGTH_MYGROUPNAME = 20;
    /** メモMAX文字数 */
    public static final int MAX_LENGTH_MEMO = 1000;

    /** メッセージに表示するテキスト URL */
    public static final String TEXT_ENT_URL = "URL";
    /** 雛形名称MAX文字数 */
    public static final int MAX_LENGTH_HINANAME = 50;
    /** 件名MAX文字数 */
    public static final int MAX_LENGTH_SMLTITLE = 100;
    /** 本文MAX文字数 */
    public static final int MAX_LENGTH_SMLBODY = 5000;
    /** 企業情報 会社名MAX文字数 */
    public static final int MAX_LENGTH_ENT_NAME = 50;
    /** 企業情報 会社カナMAX文字数 */
    public static final int MAX_LENGTH_ENT_NAMEKN = 100;
    /** 企業情報 URL文字数MAX文字数 */
    public static final int MAX_LENGTH_ENT_URL = 100;
    /** 企業情報 備考文字数MAX文字数 */
    public static final int MAX_LENGTH_ENT_BIKO = 1000;

    /** オペレーションログ 操作内容補足 MAX文字数 */
    public static final int MAX_LENGTH_LOG_OP_VALUE = 3000;

    //汎用
    /** 有効状態を表す */
    public static final String ENABLE = "enable";
    /** 無効状態を表す */
    public static final String DISABLE = "disable";

    /** 添付ファイル(本体)の接尾文字列 */
    public static final String ENDSTR_SAVEFILE = "file";
    /** オブジェクトファイルの接尾文字列 */
    public static final String ENDSTR_OBJFILE = "obj";

    /** ファイルサイズ 1MB */
    public static final int FILE_SIZE_1MB = 1048576;
    /** 添付ファイル容量の最大値 */
    public static final int FILE_MAX_SIZE = 10485760;
    /** 添付ファイル容量の最大値(MB) */
    public static final String TEXT_FILE_MAX_SIZE = "100MB";

    /** 添付ファイル取り扱い後に元ファイルを削除しない */
    public static final int TEMPFILE_FINALIZE_NOTDEL = 0;
    /** 添付ファイル取り扱い後に元ファイルを削除する */
    public static final int TEMPFILE_FINALIZE_DEL = 1;

    /** テキスト欄 MAX文字数(50文字) */
    public static final int MAX_LENGTH_TEXT = 50;

    /** 添付ファイル処理モード 0(通常：複数ファイル) */
    public static final int CMN110MODE_FILE = 0;
    /** 添付ファイル処理モード 1(画像：単一画像ファイル登録) */
    public static final int CMN110MODE_GAZOU = 1;
    /** 添付ファイル処理モード 2(通常：単一ファイル) */
    public static final int CMN110MODE_FILE_TANITU = 2;
    /** 添付ファイル処理モード 3(ファイル管理：複数ファイル) */
    public static final int CMN110MODE_FILEKANRI = 3;
    /** 添付ファイル処理モード 4(画像：単一画像ファイル登録ユーザ登録) */
    public static final int CMN110MODE_TANITU_USR031 = 4;
    /** 添付ファイル処理モード 5(画像：単一画像ファイル登録キャビネット登録) */
    public static final int CMN110MODE_TANITU_FIL030 = 5;
    /** 添付ファイル処理モード 6(画像：複数ファイル連続登録施設画像登録) */
    public static final int CMN110MODE_RETO_RSV090 = 6;
    /** 添付ファイル処理モード 7(エディターへのファイル登録) */
    public static final int CMN110MODE_EDITOR = 7;
    /** 添付ファイル処理モード 8(メモ登録) */
    public static final int CMN110MODE_MEMO = 8;
    /** 添付ファイル処理モード 9(ファイル管理：単一ファイル) */
    public static final int CMN110MODE_FILEKANRI_TANITU = 9;
    /** 添付ファイル処理モード 10(チャット：メッセージ登録) */
    public static final int CMN110MODE_CHAT_MESSAGE = 10;

    /** 数値フィールド初期値 */
    public static final int NUM_INIT = -1;

    /** メイン画面表示区分(表示) */
    public static final int MAIN_DSP = 1;
    /** メイン画面表示区分(非表示) */
    public static final int MAIN_NOT_DSP = 0;
    /** メイン画面表示区分(自動リロード時間設定時) */
    public static final int MAIN_RELOAD = 2;

    /** メイン画面自動リロード時間 */
    public static final int MAIN_DSPRELOAD = 600000;

    /** メイン画面項目 時計 */
    public static final String MAIN_DSPVALUE_CLOCK = "1";
    /** メイン画面項目 最終ログイン時間 */
    public static final String MAIN_DSPVALUE_LOGINHISTORY = "2";
    /** メイン画面項目 自動リロード時間 */
    public static final String MAIN_DSPVALUE_AUTORELOAD = "3";
    /** メイン画面項目 天気予報 */
    public static final String MAIN_DSPVALUE_WEATHER = "4";
    /** メイン画面項目 ニュース */
    public static final String MAIN_DSPVALUE_NEWS = "6";
    /** メイン画面項目(時計),(最終ログイン時間),(自動リロード時間),(天気予報) */
    public static final String[] MAIN_DSPVALUE = {MAIN_DSPVALUE_CLOCK,
                                                    MAIN_DSPVALUE_LOGINHISTORY,
                                                    MAIN_DSPVALUE_AUTORELOAD,
                                                    MAIN_DSPVALUE_WEATHER,
                                                    MAIN_DSPVALUE_NEWS};

    /** 天気予報 初期表示地域(東京) */
    public static final String MAIN_DSP_WEATHER_INIT_AREA = "18";

    /** ユーザ情報popup リンクタイプ 無効 */
    public static final int APPENDINFO_LINK_TYPE_NONE = 0;
    /** ユーザ情報popup リンクタイプ スケジュール */
    public static final int APPENDINFO_LINK_TYPE_SCH = 1;

    /** 端末区分 0=全て */
    public static final int TERMINAL_KBN_ALL = 0;
    /** 端末区分 1=PC */
    public static final int TERMINAL_KBN_PC = 1;
    /** 端末区分 2=モバイル */
    public static final int TERMINAL_KBN_MOBILE = 2;
    /** 端末区分 テキスト 1=PC */
    public static final String TERMINAL_KBN_PC_TEXT = "PC";

    /** キャリア 0=全て */
    public static final int CAR_KBN_PC_ALL = 0;
    /** キャリア 1=PC */
    public static final int CAR_KBN_PC = 1;
    /** キャリア 2=DoCoMo */
    public static final int CAR_KBN_DOCOMO = 2;
    /** キャリア 3=KDDI */
    public static final int CAR_KBN_KDDI = 3;
    /** キャリア 4=SoftBank */
    public static final int CAR_KBN_SOFTBANK = 4;

    /** キャリア テキスト 1=PC */
    public static final String CAR_KBN_PC_TEXT = "PC";
    /** キャリア テキスト 2=DoCoMo */
    public static final String CAR_KBN_DOCOMO_TEXT = "docomo";
    /** キャリア テキスト 3=KDDI */
    public static final String CAR_KBN_KDDI_TEXT = "au";
    /** キャリア テキスト 4=SoftBank */
    public static final String CAR_KBN_SOFTBANK_TEXT = "SoftBank";

    /** ID/パスワード保存区分  0=保存する */
    public static final int IDPASS_SAVE_OK = 0;
    /** ID/パスワード保存区分  1=保存しない */
    public static final int IDPASS_SAVE_NG = 1;

    /** 企業情報 期首月 デフォルト */
    public static final int DEFAULT_KISYU = 4;


    /** ロックアウト ログイン失敗回数 */
    public static final String LOCKOUT_FAILCOUNT = "LOGIN_FAILCOUNT";

    /** ログイン設定 ロックアウト区分 ロックアウトしない */
    public static final int LOGIN_LOCKKBN_NOSET = 0;
    /** ログイン設定 ロックアウト区分 ロックアウトする */
    public static final int LOGIN_LOCKKBN_LOCKOUT = 1;
    /** ログイン設定 ログイン失敗回数 最大値 */
    public static final int LOGIN_FAILCOUNT_MAX = 20;
    /** ログイン設定 失敗カウント期間 */
    public static final int[] LOGIN_FAILTIME_LIST = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                                        20, 30, 60, 120};
    /** ログイン設定 ロック期間 */
    public static final int[] LOGIN_LOCKTIME_LIST = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                                        20, 30, 60, 120};

    /** OutOｆMemory */
    public static final String OUT_OF_MEMORY = "OutOfMemory";

    /** モード:新規登録 */
    public static final int MODE_ADD = 0;
    /** モード:編集 */
    public static final int MODE_EDIT = 1;

    /** 日付 末日 */
    public static final int LAST_DAY_OF_MONTH = 99;

    /** グループ選択コンボボックスでのグループ一覧のグループSID */
    public static final int GRP_SID_GRPLIST = -9;

    /** GSファイアウォール設定 モバイルアプリからのアクセス許可 許可しない */
    public static final int CFC_ALLOW_MBL_NO = 0;
    /** GSファイアウォール設定 モバイルアプリからのアクセス許可 許可する */
    public static final int CFC_ALLOW_MBL_YES = 1;
    /** GSファイアウォール設定 モバイルアプリからのアクセス許可_プラグイン制限 制限しない */
    public static final int CFC_ALLOW_MBL_PLUGIN_NO = 0;
    /** GSファイアウォール設定 モバイルアプリからのアクセス許可_プラグイン制限 制限する */
    public static final int CFC_ALLOW_MBL_PLUGIN_RESTRICT = 1;
    /** GSファイアウォール設定 モバイルアプリからのアクセス許可 デフォルトで許可するプラグイン */
    public static final String[] FIREWALL_MBLAPP_ALLOW_PLUGINLIST = {
        GSConst.PLUGINID_COMMON,
        GSConst.PLUGINID_MAIN,
        GSConst.PLUGINID_API,
        GSConst.PLUGINID_MOBILE
    };

    /** 年 0年 */
    public static final int YEAR_ZERO = 0;
    /** 月 0月*/
    public static final int MONTH_ZERO = 0;
    /** 日 0日*/
    public static final int DAY_ZERO = 0;

    /** CrossRide リンク 表示する */
    public static final int CROSSRIDE_LINK_ON = 0;
    /** CrossRide リンク 表示しない */
    public static final int CROSSRIDE_LINK_OFF = 1;
    /** GSモバイル アプリ リンク 表示する */
    public static final int APP_LINK_ON = 0;
    /** GSモバイル アプリ リンク 表示しない */
    public static final int APP_LINK_OFF = 1;

    /** エディター添付ファイルの一時保存ディレクトリ */
    public static final String EDITOR_BODY_FILE = "bodyFile";

    /** ワンタイムパスワード 有効期限*/
    public static final int OTP_LIMIT_TIME = 5;
    /** ワンタイムパスワード 文字列長*/
    public static final int OTP_LENGTH = 4;

    /** テーマ選択 デフォルト */
    public static final int THEME_DEFAULT = 1;
    /** テーマ選択 グレー */
    public static final int THEME_GRAY = 2;
    /** テーマ選択 緑 */
    public static final int THEME_GREEN = 3;
    /** テーマ選択 赤 */
    public static final int THEME_RED = 4;
    /** テーマ選択 ピンク */
    public static final int THEME_PINK = 5;
    /** テーマ選択 黄色 */
    public static final int THEME_YELLOW = 6;
    /** テーマ選択 オリジナル */
    public static final int THEME_ORIGINAL = 7;
    /** テーマ選択 草原 */
    public static final int THEME_SOUGEN = 8;
    /** テーマ選択 夕日 */
    public static final int THEME_SUNSET = 9;
    /** テーマ選択 さくら */
    public static final int THEME_SAKURA = 10;
    /** テーマ選択 青空 */
    public static final int THEME_SKY = 11;
    /** テーマ選択 シティ */
    public static final int THEME_CITY = 12;
    /** テーマ選択 ダーク */
    public static final int THEME_DARK = 13;

    /** メニュー画面開閉状態 */
    public static final String COOKIE_MENUOPEN = "menuOpen";
    /** メニュー画面開閉ロック状態 */
    public static final String COOKIE_MENULOCK = "menuLock";

    /** 検索区分(AND) */
    public static final int KEY_WORD_KBN_AND = 0;
    /** 検索区分(OR) */
    public static final int KEY_WORD_KBN_OR = 1;

    /** メールサーバ 認証形式 基本認証 */
    public static final int MAILSERVER_AUTH_TYPE_NORMAL = 0;
    /** メールサーバ 認証形式 OAuth */
    public static final int MAILSERVER_AUTH_TYPE_OAUTH = 1;

    /** OAuth認証 クライアントID 最大文字数 */
    public static final int MAXLEN_AUTH_ID = 1000;
    /** OAuth認証 シークレットキー 最大文字数 */
    public static final int MAXLEN_AUTH_SECRET = 1000;
    /** OAuth認証 備考 最大文字数 */
    public static final int MAXLEN_BIKO = 1000;
    /** OAuth認証 メールアドレス 最大文字数 */
    public static final int MAXLEN_AUTH_ADDRESS = 256;

    /** Api連携 APIタイプ 外部API */
    public static final int CAC_API_TYPE_EXTERNAL = 0;
    /** Api連携 APIタイプ GSessionAPI */
    public static final int CAC_API_TYPE_GS = 1;

    /** Api連携 API名 最大文字数 */
    public static final int MAXLEN_CAC_NAME = 50;
    /** Api連携 Content-Type 最大文字数 */
    public static final int MAXLEN_CAC_CONTENT = 50;
    /** Api連携 ユーザID 最大文字数 */
    public static final int MAXLEN_CAC_USER = 256;
    /** Api連携 パスワード 最大文字数 */
    public static final int MAXLEN_CAC_PASS = 256;
    /** Api連携 API名 最大文字数 */
    public static final int MAXLEN_CAC_HEADER = 100;
    /** Api連携パラメータ 名称 最大文字数 */
    public static final int MAXLEN_CAP_NAME = 50;
    /** Api連携 トークンヘッダ 最大文字数 */
    public static final int MAXLEN_CAP_HEADER = 100;

    /** OAuth認証情報 一時保管ファイル名*/
    public static final String AUTH_FILENAME = "oauthData";

    /** OAuth認証情報 プロバイダ Google */
    public static final int COU_PROVIDER_GOOGLE = 1;
    /** OAuth認証情報 プロバイダ Microsoft*/
    public static final int COU_PROVIDER_MICROSOFT = 2;

    /** OAuth認証情報 プロバイダ名 Google */
    public static final String PROVIDER_NAME_GOOGLE = "cmn.cmn260.02";
    /** OAuth認証情報 プロバイダ名 Microsoft*/
    public static final String PROVIDER_NAME_MICROSOFT = "cmn.cmn260.03";

    /** メール設定(mailserver.conf) Google 受信サーバ ホスト */
    public static final String MAILCONF_OAUTH_RECEIVE_HOST_GOOGLE = "OAUTH_RECEIVE_HOST_GOOGLE";
    /** メール設定(mailserver.conf) Google 受信サーバ ポート */
    public static final String MAILCONF_OAUTH_RECEIVE_PORT_GOOGLE = "OAUTH_RECEIVE_PORT_GOOGLE";
    /** メール設定(mailserver.conf) Google 送信サーバ ホスト */
    public static final String MAILCONF_OAUTH_SEND_HOST_GOOGLE = "OAUTH_SEND_HOST_GOOGLE";
    /** メール設定(mailserver.conf) Google 送信サーバ ポート */
    public static final String MAILCONF_OAUTH_SEND_PORT_GOOGLE = "OAUTH_SEND_PORT_GOOGLE";
    /** メール設定(mailserver.conf) Microsoft 受信サーバ ホスト */
    public static final String MAILCONF_OAUTH_RECEIVE_HOST_MICROSOFT
                                                                = "OAUTH_RECEIVE_HOST_MICROSOFT";
    /** メール設定(mailserver.conf) Microsoft 受信サーバ ポート */
    public static final String MAILCONF_OAUTH_RECEIVE_PORT_MICROSOFT
                                                                = "OAUTH_RECEIVE_PORT_MICROSOFT";
    /** メール設定(mailserver.conf) Microsoft 送信サーバ ホスト */
    public static final String MAILCONF_OAUTH_SEND_HOST_MICROSOFT = "OAUTH_SEND_HOST_MICROSOFT";
    /** メール設定(mailserver.conf) Microsoft 送信サーバ ポート */
    public static final String MAILCONF_OAUTH_SEND_PORT_MICROSOFT = "OAUTH_SEND_PORT_MICROSOFT";

    /** REST API グループ情報API マイグループ 取得しない */
    public static final int RESTAPI_GROUPS_MYGROUP_OUT = 0;
    /** REST API グループ情報API マイグループ 取得する */
    public static final int RESTAPI_GROUPS_MYGROUP_IN = 1;
    /** REST API グループ情報API デフォルトグループ FALSE */
    public static final int RESTAPI_GROUPS_DEFAULTGROUP_NO = 0;
    /** REST API グループ情報API デフォルトグループ TRUE */
    public static final int RESTAPI_GROUPS_DEFAULTGROUP_YES = 1;

    /** ファイルダウンロード */
    public static final int FILE_DOWNLOAD = 0;
    /** ファイルダウンロード(inline)*/
    public static final int FILE_DOWNLOAD_INLINE = 1;

    /** プレビュー対象ファイルの拡張子一覧 */
    public static final String[] FILEPREVIEW_EXTENSION 
        = {"png", "jpg", "jpeg", "pdf", "gif", "txt", "js", "css", "html"};
    /** プレビュー対象テキストファイルの拡張子一覧 */
    public static final String[] TEXTFILEPREVIEW_EXTENSION = {"txt", "js", "css", "html"};
}
