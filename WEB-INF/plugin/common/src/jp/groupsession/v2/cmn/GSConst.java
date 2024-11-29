package jp.groupsession.v2.cmn;

import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.Encoding;

/**
 * <br>[機 能] GroupSession共通定数一覧
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class GSConst {

    /** プロジェクト名 */
    public static final String PROJECT_NAME = "Group Session";
    /** バージョン */
    public static final String VERSION = "5.6.1";
    /** バージョン(js, css設定用) */
    public static final String VERSION_PARAM = "561";
    /** キー値 (MD5…"gsession2_jts") */
    public static final String GSESSION2_ID
        = "d41d8cd98f00b204e9800998ecf8427e";
    /** UIDのイニシャル */
    public static final String GSUID = "SN";
    /** GSホームページURL */
    public static final String GS_HOMEPAGE_URL = "https://groupsession.jp/";
    /** セッション有効期限 (秒) 初期値 720分 */
    public static final int SESSION_TIME = 60 * 12;
    /** セッションが確立しているか判定するためのキー名 */
    public static final String SESSION_KEY = "GSSESSIONID";
    /** セッションキー名(パスワード) */
    public static final String SESSION_KEY_PASSWORD = "GSSESSIONPASSWORD";
    /** セッションキー名(ドメイン） */
    public static final String SESSION_KEY_DOMAIN = "GSSESSIONDOMAIN";
    /** リクエスト属性キー RESTAPI 受け入れメソッド */
    public static final String ATTRKEY_SUPORTMETHOD = "GSSUPORTMETHOD";
    /** 標準サポートメソッド */
    public static final String[] SUPORTMETHOD_DEF = new String[] {"GET", "POST"};

    /** ヘルプURL */
    public static final String HELPURL = "HELPURL";
    /** コマンドパラメータ名 */
    public static final String P_CMD = "CMD";
    /** 改行文字列 */
    public static final String NEW_LINE_STR = "<BR>";
    /** GSデータディレクトリ */
    public static final String GSDATA_DIR = "WEB-INF/";
    /** DB保存用ディレクトリ */
    public static final String DB_SAVE_DIR = "WEB-INF/db/";
    /** DB保存用 ディレクトリ(gsdata.conf設定時に使用) */
    public static final String DB_DIR = "db/";
    /** 添付ファイル保存用ディレクトリ */
    public static final String FILE_SAVE_DIR = "WEB-INF/file/";
    /** 添付ファイル保存用ディレクトリ(gsdata.conf設定時に使用) */
    public static final String FILE_DIR = "file/";
    /** ファイル管理用添付ファイル保存用ディレクトリ */
    public static final String FILE_KANRI_SAVE_DIR = "WEB-INF/filekanri/";
    /** ファイル管理用添付ファイル保存用ディレクトリ(gsdata.conf設定時に使用) */
    public static final String FILE_KANRI_DIR = "filekanri/";
    /** WEBメール用添付ファイル保存用ディレクトリ */
    public static final String FILE_WEBMAIL_SAVE_DIR = "WEB-INF/webmail/";
    /** WEBメール用添付ファイル保存用ディレクトリ(gsdata.conf設定時に使用) */
    public static final String FILE_WEBMAIL_DIR = "webmail/";
    /** ディレクトリ名 DB */
    public static final String DIR_NAME_DB = "db";
    /** ディレクトリ名 ファイル */
    public static final String DIR_NAME_FILE = "file";
    /** ディレクトリ名 ファイル管理 */
    public static final String DIR_NAME_FILEKANRI = "filekanri";
    /** ディレクトリ名 webmail */
    public static final String DIR_NAME_WEBMAIL = "webmail";

    /** 出力エンコーディング */
    public static final String ENCODING = Encoding.UTF_8;
    /** ログファイル名称(ALL) */
    public static final String LOGFILE_NAME = "GroupSession";
    /** ログファイル名称(ERROR) */
    public static final String LOGFILE_NAME_ERROR = "error";
    /** ドメイン */
    public static final String GS_DOMAIN = "gsDomain";

    /** req アトリビュートキー アクションパス*/
    public static final String REQ_ATTRKEY_ACTIONPATH = "actionPath__";


    //データソース
    /** 設定ファイルよりデータソースを取得するためのキー */
    public static final String DATASOURCE_KEY = "GsDataSource";
    /** 設定ファイルよりデータソースを取得するためのキー(裁判用) */
    public static final String DATASOURCE_NUM_KEY = "GsDataSourceNum";

    /** 採番区分 SID バイナリーSID */
    public static final String SBNSID_BIN = "binary";
    /** 採番区分 SID SUB バイナリーSID */
    public static final String SBNSID_SUB_BIN = "bin";

    /** バイナリー情報 共通 */
    public static final int FILEKBN_COMMON = 0;
    /** バイナリー情報 ファイル管理 */
    public static final int FILEKBN_FILE = 1;

    /** 状態区分 (登録) */
    public static final int JTKBN_TOROKU = 0;
    /** 状態区分 (削除) */
    public static final int JTKBN_DELETE = 9;

    /** 管理者権限有り */
    public static final int USER_ADMIN = 1;
    /** 管理者権限無し */
    public static final int USER_NOT_ADMIN = 0;

    /** 設定 管理者が設定する */
    public static final int SETTING_ADM = 0;
    /** 設定 各ユーザが指定する */
    public static final int SETTING_USR = 1;

    /** 設定 表示する */
    public static final String USER_SETTING_SHOW = "true";
    /** 設定 表示しない */
    public static final String USER_SETTING_HIDE = "false";

    /** システム予約ユーザ admin */
    public static final int SYSTEM_USER_ADMIN = 0;
    /** システム予約ユーザ system_mail */
    public static final int SYSTEM_USER_MAIL = 1;

    /** 役職 未設定値 */
    public static final int POS_DEFAULT = 0;

    /** 一覧表示件数(初期値) */
    public static final int LIST_COUNT_LIMIT = 10;

    /** オーダーキー (昇順) */
    public static final int ORDER_KEY_ASC = 0;
    /** オーダーキー (降順) */
    public static final int ORDER_KEY_DESC = 1;

    /** 在席ステータス (その他) */
    public static final int UIOSTS_ETC = 0;
    /** 在席ステータス (在席) */
    public static final int UIOSTS_IN = 1;
    /** 在席ステータス (不在) */
    public static final int UIOSTS_LEAVE = 2;

    /** フィルター有無(有り) */
    public static final int BEAN_WRITE_FILTER_YES = 0;
    /** フィルター有無(無し) */
    public static final int BEAN_WRITE_FILTER_NO = 1;

    /** 在席備考 最大文字数 */
    public static final int MAX_LENGTH_ZSKBIKO = 30;

    /** アドレス帳 採番ID アドレス帳 */
    public static final String SBNSID_ADDRESS = "address";
    /** アドレス帳 採番IDサブ コンタクト履歴SID */
    public static final String SBNSID_SUB_CONTACT = "contact";
    /** アドレス帳 採番IDサブ コンタクト履歴グループSID */
    public static final String SBNSID_SUB_CONTACT_GRP = "contactGrp";
    /** アドレス帳 閲覧権限 本人のみ */
    public static final int ADR_VIEWPERMIT_OWN = 0;
    /** アドレス帳 閲覧権限 グループ指定 */
    public static final int ADR_VIEWPERMIT_GROUP = 1;
    /** アドレス帳 閲覧権限 ユーザ指定 */
    public static final int ADR_VIEWPERMIT_USER = 2;
    /** アドレス帳 閲覧権限 設定なし */
    public static final int ADR_VIEWPERMIT_NORESTRICTION = 3;

    /** アドレス帳 コンタクト履歴種別 その他 */
    public static final int CONTYP_OTHER = 0;
    /** アドレス帳 コンタクト履歴種別 電話 */
    public static final int CONTYP_TEL = 1;
    /** アドレス帳 コンタクト履歴種別 メール */
    public static final int CONTYP_MAIL = 2;
    /** アドレス帳 コンタクト履歴種別 WEB */
    public static final int CONTYP_WEB = 3;
    /** アドレス帳 コンタクト履歴種別 打ち合わせ */
    public static final int CONTYP_MEETING = 4;
    /** アドレス帳 コンタクト履歴種別テキスト WEB */
    public static final String TEXT_CONTYP_WEB = "WEB";
    /** プラグインID 共通 */
    public static final String PLUGINID_COMMON = "common";
    /** プラグインID メイン */
    public static final String PLUGINID_MAIN = "main";
    /** プラグインID ユーザ情報 */
    public static final String PLUGINID_USER = "user";
    /** プラグインID アドレス */
    public static final String PLUGINID_ADDRESS = "address";
    /** プラグインID 稟議 */
    public static final String PLUGIN_ID_RINGI = "ringi";
    /** プラグインID */
    public static final String PLUGINID_API = "api";
    /** プラグインID */
    public static final String PLUGINID_BMK = "bookmark";
    /** プラグインID */
    public static final String PLUGINID_CIR = "circular";
    /** プラグインID */
    public static final String PLUGINID_HELP = "help";
    /** プラグインID */
    public static final String PLUGINID_IP = "ipkanri";
    /** プラグインID */
    public static final String PLUGINID_LIC = "license";
    /** プラグインID */
    public static final String PLUGINID_MOBILE = "mobile";
    /** プラグインID */
    public static final String PLUGINID_PORTAL = "portal";
    /** プラグインID */
    public static final String PLUGINID_PRJ = "project";
    /** プラグインID */
    public static final String PLUGINID_RSS = "rss";
    /** プラグインID */
    public static final String PLUGINID_SCH = "schedule";
    /** プラグインID */
    public static final String PLUGINID_SEARCH = "search";
    /** プラグインID */
    public static final String PLUGINID_SML = "smail";
    /** プラグインID */
    public static final String PLUGINID_TIMECARD = "timecard";
    /** プラグインID */
    public static final String PLUGINID_WML = "webmail";
    /** プラグインID */
    public static final String PLUGINID_ZSK = "zaiseki";
    /** プラグインID */
    public static final String PLUGIN_ID_RESERVE = "reserve";
    /** プラグインID */
    public static final String PLUGIN_ID_FILE = "file";
    /** プラグインID */
    public static final String PLUGIN_ID_BULLETIN = "bulletin";
    /** プラグインID */
    public static final String PLUGIN_ID_NIPPOU = "nippou";
    /** プラグインID */
    public static final String PLUGIN_ID_ANPI = "anpi";
    /** プラグインID */
    public static final String PLUGIN_ID_ENQUETE = "enquete";
    /** プラグインID */
    public static final String PLUGIN_ID_CHAT = "chat";
    /** プラグインID */
    public static final String PLUGIN_ID_MEMO = "memo";
    /** プラグインID */
    public static final String PLUGIN_ID_LDAP = "ldap";
    /** プラグインID モバイル */
    public static final String PLUGIN_ID_MOBILE = "mobile";
    /** プラグイン名 共通 */
    public static final String PLUGIN_NAME_COMMON = "共通";

    /** プラグイン使用区分 使用する*/
    public static final int PLUGIN_USE = 0;
    /** プラグイン使用区分 使用しない*/
    public static final int PLUGIN_NOT_USE = 1;

    /** リクエストに格納するエラーのキー名称 */
    public static final String ERROR_KEY = "ERROR_KEY";
    /** リクエストに格納するエラーのキー(PARAM)名称 */
    public static final String ERROR_PARA_KEY = "ERROR_PARA_KEY";

    /** パスワードの変更 許可する */
    public static final int CHANGEPASSWORD_PARMIT = 0;
    /** パスワードの変更 許可しない */
    public static final int CHANGEPASSWORD_NOTPARMIT = 1;

    /** ユーザコンボ ソートキー 未設定 */
    public static final int USERCMB_SKEY_NOSET = 0;
    /** ユーザコンボ ソートキー 名前 */
    public static final int USERCMB_SKEY_NAME = 1;
    /** ユーザコンボ ソートキー 社員/職員番号 */
    public static final int USERCMB_SKEY_SNO = 2;
    /** ユーザコンボ ソートキー 役職 */
    public static final int USERCMB_SKEY_POSITION = 3;
    /** ユーザコンボ ソートキー 生年月日 */
    public static final int USERCMB_SKEY_BDATE = 4;
    /** ユーザコンボ ソートキー ソートキー1 */
    public static final int USERCMB_SKEY_SORTKEY1 = 5;
    /** ユーザコンボ ソートキー ソートキー2 */
    public static final int USERCMB_SKEY_SORTKEY2 = 6;

    /** グループコンボ 階層区分 階層を考慮しない */
    public static final int GROUPCMB_SKBN_NOSET = 0;
    /** グループコンボ 階層区分 階層別にソートする */
    public static final int GROUPCMB_SKBN_SET = 1;
    /** グループコンボ ソートキー 未設定 */
    public static final int GROUPCMB_SKEY_NOSET = 0;
    /** グループコンボ ソートキー グループID */
    public static final int GROUPCMB_SKEY_GRPID = 1;
    /** グループコンボ ソートキー 名前 */
    public static final int GROUPCMB_SKEY_NAME = 2;

    /** ファイル保存先区分 ファイルシステム */
    public static final int FILE_HOZON_KBN_FILESYSTEM = 0;
    /** ファイル保存先区分 DB H2 */
    public static final int FILE_HOZON_KBN_DB_H2 = 1;
    /** ファイル保存先区分 DB PostgreSQL */
    public static final int FILE_HOZON_KBN_DB_POSTGRES = 2;

    /** 採番区分 SID OIDSID */
    public static final String SBNSID_OID = "oid";
    /** 採番区分 SID SUB OIDSID */
    public static final String SBNSID_SUB_OID = "oidnum";

    /** ユーザ情報 採番ID ユーザ情報 */
    public static final String SBNSID_USER = "user";

    /** DB種別 H2 Database */
    public static final int DBTYPE_H2DB = 0;
    /** DB種別 PostgreSQL */
    public static final int DBTYPE_POSTGRES = 1;
    /** DB種別 Oracle */
    public static final int DBTYPE_ORACLE = 2;

    /** ユーザ数上限 */
    public static final String USER_COUNT_LIMIT = "UserCountLimit";

    /** AP server Muiti */
    public static final String MULTIAP = "MultiAP";
    /** AP server Muiti MULTI */
    public static final String MULTIAP_MULTI = "1";

    /** AP server Number */
    public static final String AP_NUMBER = "APNumber";

    /** 連携 AP IPアドレス設定名 */
    public static final String MULTIAP_ACCEPTIP = "MultiApAcceptIp";

    /** 連携 AP 中継リクエストURL 設定名 */
    public static final String MULTIAP_RELAYURL = "MultiApRelayUrl";

    /** Batch Status */
    public static final String BATCH_STATUS = "BatchStatus";
    /** Batch Status 実行する */
    public static final String BATCH_STATUS_TRUE = "0";
    /** Batch Status 実行しない */
    public static final String BATCH_STATUS_FALSE = "1";
    /** Batch Status 制限して実行 */
    public static final String BATCH_STATUS_LIMITATION = "2";

    /** Main Status */
    public static final String MAIN_STATUS = "MainStatus";
    /** Main Status */
    public static final String MAIN_STATUS_TRUE = "0";

    /** IP ADDRESS status */
    public static final String IPADRSTATUS = "IpAdrStatus";
    /** IP ADDRESS status */
    public static final String IPADRSTATUS_TRUE = "1";

    /** PGroonga */
    public static final String PGROONGA = "PGroonga";
    /** PGroonga 使用する */
    public static final String PGROONGA_USE_TRUE = "1";

    /** プラグイン MAX文字数 */
    public static final int MAX_LENGTH_URL = 1000;

    /** 送信区分 宛先 */
    public static final int SEND_KBN_ATESAKI = 0;
    /** 送信区分 宛先(ユーザ選択時) */
    public static final int SEND_KBN_ATESAKI_1 = 3;
    /** 送信区分 CC */
    public static final int SEND_KBN_CC = 1;
    /** 送信区分 BCC */
    public static final int SEND_KBN_BCC = 2;

    /** アドレス区分 メールアドレス１ */
    public static final int ADDRESS_KBN_1 = 1;
    /** アドレス区分 メールアドレス２ */
    public static final int ADDRESS_KBN_2 = 2;
    /** アドレス区分 メールアドレス３ */
    public static final int ADDRESS_KBN_3 = 3;

    /** プラグイン区分 GS */
    public static final int PLUGIN_KBN_GS = 0;
    /** プラグイン区分 ユーザ作成 */
    public static final int PLUGIN_KBN_USER = 1;

    /** 日次バッチ強制実行パラメータ  実行しない*/
    public static final int NOT_DO_BATCH = 0;
    /** 日次バッチ強制実行パラメータ  実行する*/
    public static final int DO_BATCH = 1;

    /** 添付ファイル名MAX文字数 */
    public static final int MAX_LENGTH_FILE = 255;

    /** 添付ファイル読み込み 遅延ログ 閾値(ミリ秒) */
    public static final long DELAY_LIMIT_FILEREAD = 1000;

    /** デフォルトテーマパス */
    public static final String DEF_THEME_PATH = "common/css/theme1";

    /** ユーザ有効フラグ 値 有効*/
    public static final int YUKOMUKO_YUKO = 0;
    /** ユーザ有効フラグ 値 無効 */
    public static final int YUKOMUKO_MUKO = 1;

    /** 自動ユーザインポートサンプル */
    public static final String AUTO_IMPORT_SAMPLE = "sample_user_auto_import.xlsx";
    /** ユーザ情報 or アクセス制御コンボ(選択用)のグループコンボに設定する「 グループ一覧」の値 */
    public static final int GROUP_COMBO_VALUE = -9;

    /** 特例アクセス_制限対象 種別 ユーザ */
    public static final int ST_TYPE_USER = 0;
    /** 特例アクセス_制限対象 種別 グループ */
    public static final int ST_TYPE_GROUP = 1;

    /** 特例アクセス_許可対象 種別 ユーザ */
    public static final int SP_TYPE_USER = 0;
    /** 特例アクセス_許可対象 種別 グループ */
    public static final int SP_TYPE_GROUP = 1;
    /** 特例アクセス_許可対象 種別 役職 */
    public static final int SP_TYPE_POSITION = 2;

    /** 特例アクセス_許可対象 権限区分 閲覧のみ */
    public static final int SP_AUTH_VIEWONLY = 0;
    /** 特例アクセス_許可対象 権限区分 登録・編集・削除 */
    public static final int SP_AUTH_EDIT = 1;
    /** 処理区分 新規追加 */
    public static final int CMDMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int CMDMODE_EDIT = 1;

    //共有範囲設定
    /** 共有設定 (全員で共有する) */
    public static final int CRANGE_SHARE_ALL = 0;
    /** 共有設定 (グループメンバ内で共有する) */
    public static final int CRANGE_SHARE_GROUP = 1;

    /** GroupSessionBizのUrl */
    public static final String BIZ_URL = "biz.gs.sjts.co.jp";

    /** SQL in句内パラメータの最大数 */
    public static final int MAX_NUM_SQL_IN = 1000;

    /** 登録対象フォーラム 新規フォーラム作成 */
    public static final int TARGET_NEW_FORUM = 0;
    /** 登録対象フォーラム 登録済みのフォーラム */
    public static final int TARGET_OLD_FORUM = 1;

    /** 登録対象プロジェクト 新規プロジェクト作成 */
    public static final int TARGET_NEW_PROJECT = 0;
    /** 登録対象プロジェクト 登録済みのプロジェクト */
    public static final int TARGET_OLD_PROJECT = 1;

    /** ログ出力 スレッド変数キー GSドメイン */
    public static final String KEY_LOGTHREADSTRAGE_DOMAIN = "gsdomain";

    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** データサイズ集計 合計値区分 合計 */
    public static final int USEDDATA_SUMTYPE_TOTAL = 0;
    /** データサイズ集計 合計値区分 差分データ */
    public static final int USEDDATA_SUMTYPE_DIFF = 1;

    /** 表示しない */
    public static final int DSP_NOT = 0;
    /** 表示する */
    public static final int DSP_OK = 1;

    /** 六曜(先勝) */
    public static final String ROKUYOU_SENSHOU = "先勝";
    /** 六曜(友引) */
    public static final String ROKUYOU_TOMOBIKI = "友引";
    /** 六曜(先負) */
    public static final String ROKUYOU_SENBU = "先負";
    /** 六曜(仏滅) */
    public static final String ROKUYOU_BUTSUMETSU = "仏滅";
    /** 六曜(大安) */
    public static final String ROKUYOU_TAIAN = "大安";
    /** 六曜(赤口) */
    public static final String ROKUYOU_SHAKKOU = "赤口";
    /** 六曜区分(先勝) */
    public static final int RKY_KBN_SENSHOU = 1;
    /** 六曜区分(友引) */
    public static final int RKY_KBN_TOMOBIKI = 2;
    /** 六曜区分(先負) */
    public static final int RKY_KBN_SENBU = 3;
    /** 六曜区分(仏滅) */
    public static final int RKY_KBN_BUTSUMETSU = 4;
    /** 六曜区分(大安) */
    public static final int RKY_KBN_TAIAN = 5;
    /** 六曜区分(赤口) */
    public static final int RKY_KBN_SHAKKOU = 6;

    /** 種別 ユーザ */
    public static final int TYPE_USER = 0;
    /**  種別 グループ */
    public static final int TYPE_GROUP = 1;

    //自動削除項目
    /** 0:削除しない */
    public static final int AUTO_DEL_NO = 0;
    /** 1:一定期間が経過した回覧板を削除する */
    public static final int AUTO_DEL_LIMIT = 1;
    /** 2:ログアウト時に全て削除する */
    public static final int AUTO_DEL_LOGOUT = 2;
    //手動削除項目
    /** 0:削除しない */
    public static final int MANUAL_DEL_NO = 0;
    /** 1:削除する */
    public static final int MANUAL_DEL_LIMIT = 1;

    /** 削除 年 開始年 */
    public static final int[] DEL_YEAR_DATE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    /** 削除 月 開始月 */
    public static final int[] DEL_MONTH_DATE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    /** 削除 日 開始月 */
    public static final int[] DEL_DAY_DATE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                                 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                                                 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    /** GSアプリ GSモバイル */
    public static final String APP_GS_MOBILE = "jp.co.sjts.mobile.gsapp";

    /** 絵文字の後続文字列 */
    public static final List<Integer> EMOJI_COMBINE_CODE =
        Arrays.asList(new Integer[] {
            0x200D, 0xFE0F, 0x1F3FB, 0x1F3FC, 0x1F3FD, 0x1F3FE, 0x1F3FF, 0x20E3
        });

    /** 肌の色を示すコード */
    public static final List<Integer> EMOJI_SKIN_CODE =
        Arrays.asList(new Integer[] {0x1F3FB, 0x1F3FC, 0x1F3FD, 0x1F3FE, 0x1F3FF});

    /** keycapを示すコード */
    public static final int EMOJI_KEYCAP_CODE = 0x20E3;

    /** 絵文字を結合するコード */
    public static final int EMOJI_ZWJ_CODE = 0x200D;
}
