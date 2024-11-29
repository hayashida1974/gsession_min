package jp.groupsession.v2.usr;

import java.io.File;

/**
 * <br>[機  能] ユーザ情報プラグイン 定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstUser {

    /** プラグインID */
    public static final String PLUGIN_ID_USER = "user";
    /** 予約済みユーザSID上限 */
    public static final int USER_RESERV_SID = 100;

    /** 管理グループID */
    public static final String USER_KANRI_ID = "00000000";

    /** ユーザ所属マスタ デフォルトグループ 通常 */
    public static final int BEG_DEFGRP_NORMAL = 0;
    /** ユーザ所属マスタ デフォルトグループ デフォルト */
    public static final int BEG_DEFGRP_DEFAULT = 1;

    /** ソート　ID */
    public static final int USER_SORT_ID = 0;
    /** ソート　名前 */
    public static final int USER_SORT_NAME = 1;
    /** ソート　社員/職員番号 */
    public static final int USER_SORT_SNO = 2;
    /** ソート　役職 */
    public static final int USER_SORT_YKSK = 3;
    /** ソート　最終ログイン時間 */
    public static final int USER_SORT_LALG = 4;
    /** ソート 生年月日 */
    public static final int USER_SORT_BDATE = 5;
    /** ソート　在席状況 */
    public static final int USER_SORT_UIO = 6;
    /** ソート　在席コメント */
    public static final int USER_SORT_COMM = 7;
    /** ソート　端末 */
    public static final int USER_SORT_TERMINAL = 8;
    /** ソート　キャリア */
    public static final int USER_SORT_CAR = 9;
    /** ソート　固体識別番号 */
    public static final int USER_SORT_UID = 10;
    /** ソート　ソートキー1 */
    public static final int USER_SORT_SORTKEY1 = 11;
    /** ソート　ソートキー2 */
    public static final int USER_SORT_SORTKEY2 = 12;

    /** ソートキー ログイン時間 */
    public static final int SORT_DATE = 1;
    /** ソートキー 端末 */
    public static final int SORT_TERMINAL = 2;
    /** ソートキー IPアドレス */
    public static final int SORT_IP = 3;
    /** ソートキー キャリア */
    public static final int SORT_CAR = 4;
    /** ソートキー 固体識別番号 */
    public static final int SORT_UID = 5;

    /** ユーザ情報ソートキー */
    public static final int[] LIST_SORT_KEY_USR = new int[] {
        USER_SORT_SNO, USER_SORT_NAME, USER_SORT_YKSK, USER_SORT_BDATE,
                                 USER_SORT_SORTKEY1, USER_SORT_SORTKEY2 };

    /** ユーザの状態 全てのユーザ */
    public static final int USER_JTKBN_ALL = -1;
    /** ユーザの状態 有効なユーザ */
    public static final int USER_JTKBN_ACTIVE = 0;
    /** ユーザの状態 無効なユーザ */
    public static final int USER_JTKBN_DELETE = 9;
    /** 管理者グループのグループSID */
    public static final int SID_ADMIN = 0;
    /** システムメールのユーザSID */
    public static final int SID_SYSTEM_MAIL = 1;
    /** エラーメッセージに表示するテキスト E-MAIL */
    public static final String TEXT_EMAIL = "E-MAIL";
        /** エラーメッセージに表示するテキスト FAX */
    public static final String TEXT_FAX1 = "ＦＡＸ１";
        /** エラーメッセージに表示するテキスト FAX */
    public static final String TEXT_FAX2 = "ＦＡＸ２";
        /** エラーメッセージに表示するテキスト FAX */
    public static final String TEXT_FAX3 = "ＦＡＸ３";
    /** グループIDMAX文字数 */
    public static final int MAX_LENGTH_GROUPID = 50;
    /** グループ名MAX文字数 */
    public static final int MAX_LENGTH_GROUPNAME = 50;
    /** グループ名カナMAX文字数 */
    public static final int MAX_LENGTH_GROUPNAMEKANA = 75;
    /** グループコメントMAX文字数 */
    public static final int MAX_LENGTH_GROUPCOMMENT = 1000;

    /** ユーザIDMAX文字数 */
    public static final int MAX_LENGTH_USERID = 256;
    /** ユーザIDMIN文字数 */
    public static final int MIN_LENGTH_USERID = 2;
    /** パスワードMAX文字数 */
    public static final int MAX_LENGTH_PASSWORD = 256;
    /** パスワードMIN文字数 */
    public static final int MIN_LENGTH_PASSWORD = 2;

    /** ユーザ名(姓)MAX文字数 */
    public static final int MAX_LENGTH_USER_NAME_SEI = 30;
    /** ユーザ名(名)MAX文字数 */
    public static final int MAX_LENGTH_USER_NAME_MEI = 30;
    /** ユーザ名(姓)カナMAX文字数 */
    public static final int MAX_LENGTH_USER_NAME_SEI_KN = 60;
    /** ユーザ名(名)カナMAX文字数 */
    public static final int MAX_LENGTH_USER_NAME_MEI_KN = 60;
    /** 社員/職員番号MAX文字数 */
    public static final int MAX_LENGTH_SHAINNO = 20;
    /** 役職MAX文字数 */
    public static final int MAX_LENGTH_YAKUSHOKU = 30;
    /** 所属MAX文字数 */
    public static final int MAX_LENGTH_SYOZOKU = 60;
    /** ソートキーMAX文字数 */
    public static final int MAX_LENGTH_SORTKEY = 10;
    /** ユーザコメント(備考)MAX文字数 */
    public static final int MAX_LENGTH_USERCOMMENT = 1000;
    /** 年齢MAX桁数 */
    public static final int MAX_LENGTH_AGE = 2;
    /** コメントMAX文字数 */
    public static final int MAX_LENGTH_CMT = 10;
    /** 内線MAX文字数 */
    public static final int MAX_LENGTH_NAISEN = 15;

    /** ユーザメールアドレスMAX文字数 */
    public static final int MAX_LENGTH_MAIL = 256;
    /** ユーザ電話番号MAX文字数 */
    public static final int MAX_LENGTH_TEL = 20;
    /** ユーザ郵便番号上３桁MAX文字数 */
    public static final int MAX_LENGTH_POST1 = 3;
    /** ユーザ郵便番号下４桁MAX文字数 */
    public static final int MAX_LENGTH_POST2 = 4;
    /** ユーザ住所MAX文字数 */
    public static final int MAX_LENGTH_ADD = 100;
    /** ユーザ生年月日　年MAX文字数 */
    public static final int MAX_LENGTH_YEAR = 4;
    /** ユーザ生年月日　月MAX文字数 */
    public static final int MAX_LENGTH_MONTH = 2;
    /** ユーザ生年月日　日MAX文字数 */
    public static final int MAX_LENGTH_DAY = 2;
    /** ユーザコメント(都道府県コード)MAX文字数 */
    public static final int MAX_LENGTH_TDFK = 2;
    /** 固体識別番号MAX文字数 */
    public static final int MAX_LENGTH_UID = 50;

    /** ユーザインポート(通常)用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME01 = "sample.xls";
    /** ユーザインポート用(グループ一括)サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME02 = "sample02.xls";
    /** ユーザインポート用(複数グループ)サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME03 = "sample03.xls";
    /** ユーザインポート(ldap)用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME01_LDAP = "sample_ldap.xls";
    /** ユーザインポート(グループ一括ldap)用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME02_LDAP = "sample02_ldap.xls";
    /** ユーザインポート(複数グループldap)用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME03_LDAP = "sample03_ldap.xls";

    /** グループインポート用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME_GROUP = "sampleGroupImport.xls";
    /** 役職インポート用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME_POSITION = "samplePositionImport.xls";
    /** ユーザ一括削除用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME_DELETE = "sampleDelete.xls";
    /** 個人情報公開フラグ 0 = 公開する */
    public static final int INDIVIDUAL_INFO_OPEN = 0;
    /** 個人情報公開フラグ 1 = 公開しない */
    public static final int INDIVIDUAL_INFO_CLOSE = 1;

    /** 個人情報公開フラグMAX文字数 */
    public static final int MAX_LENGTH_INFO_FLG = 1;

    /** モード(氏名) */
    public static final int MODE_NAME = 1;
    /** モード(グループ) */
    public static final int MODE_GROUP = 2;
    /** モード(詳細) */
    public static final int MODE_SHOUSAI = 3;
    /** モード(通常) */
    public static final int MODE_NORMAL = 4;
    /** モード(グループ一括) */
    public static final int MODE_GROUP_ALL = 5;
    /** モード(複数グループ一括) */
    public static final int MODE_MULTIPLE_GROUP = 6;
    /** モード(複数グループ一括) */
    public static final int MODE_AUTO_IMPORT = 7;
    /** グループ名称変更初期値 */
    public static final int GROUP_NAME_NOTCHANGE = 0;
    /** グループ名称変更 */
    public static final String GROUP_NAME_CHANGE = "1";

    /** ユーザ情報のデフォルト表示件数 */
    public static final int DEFAULT_DSP_CNT = 50;
    /** 検索済かのフラグ 未検索 */
    public static final int SEARCH_MI = 0;
    /** 検索済かのフラグ 検索済 */
    public static final int SEARCH_ZUMI = 1;

    /** 検索結果CSVエクスポート区分 全ユーザ許可 */
    public static final int CSV_EXPORT_ALL = 0;
    /** 検索結果CSVエクスポート区分 管理者のみ許可 */
    public static final int CSV_EXPORT_ADMIN = 1;

    /** エクスポート機能使用可否 使用可能 */
    public static final String EXPORT_USE_OK = "OK";
    /** エクスポート機能使用可否 使用可能 */
    public static final String EXPORT_USE_NG = "NG";

    /** モバイルプラグイン使用 許可する */
    public static final int MBL_USE_OK = 0;
    /** モバイルプラグイン使用 許可しない */
    public static final int MBL_USE_NG = 1;

    /** ログイン 固体識別番号制御 0 = 制御しない */
    public static final int UID_DOESNT_CONTROL = 0;
    /** ログイン 固体識別番号制御 1 = 制御する */
    public static final int UID_CONTROL = 1;

    /** ログイン 固体識別番号自動登録 0 = 自動登録しない */
    public static final int UID_AUTO_REG_NO = 0;
    /** ログイン 固体識別番号自動登録 1 = 自動登録する */
    public static final int UID_AUTO_REG_OK = 1;

    /** インポートモード　追加 */
    public static final int IMPORT_MODE_INSERT = 0;
    /** インポートモード　上書き */
    public static final int IMPORT_MODE_UPDATE = 1;
    /** インポートモード　グループ作成 */
    public static final int IMPORT_MODE_CREATE = 1;

    /** 自動インポート　登録 */
    public static final int AUTO_IMP_INSERT = 1;
    /** 自動インポート　編集 */
    public static final int AUTO_IMP_EDIT = 2;
    /** 自動インポート　削除 */
    public static final int AUTO_IMP_DELETE = 3;
    /** 初回ログイン時のパスワード変更 0 = 変更しない */
    public static final int PSWD_UPDATE_OFF = 0;
    /** 初回ログイン時のパスワード変更 1 = 変更する */
    public static final int PSWD_UPDATE_ON = 1;

    /** デフォルト表示順 各ユーザが指定する */
    public static final int DEFO_DSP_USR = 1;
    /** デフォルト表示順 管理者が設定する */
    public static final int DEFO_DSP_ADM = 0;

    /** デフォルト表示順 ユーザが変更 */
    public static final int DEFO_EDIT_EXECUTE = 1;
    /** デフォルト表示順 ユーザ未変更 */
    public static final int DEFO_EDIT_NOT_EXECUTE = 0;

    /** パスワード変更モード 0 = 通常遷移 */
    public static final int PSWD_MODE_NOMAL = 0;
    /** パスワード変更モード 1 = ログイン画面より遷移(有効期限切れ) */
    public static final int PSWD_MODE_LIMIT = 1;
    /** パスワード変更モード 2 = ログイン画面より遷移(初回ログイン時パスワード変更) */
    public static final int PSWD_MODE_UPDATE = 2;

    /** ヘルプモード 0 = 追加確認画面遷移 */
    public static final int HELP_MODE_ADD = 0;
    /** ヘルプモード 1 = 編集確認画面遷移 */
    public static final int HELP_MODE_EDIT = 1;
    /** ヘルプモード 2 = 削除確認画面遷移 */
    public static final int HELP_MODE_DELETE = 2;

    /** ユーザ ラベル・カテゴリ処理区分(新規作成) */
    public static final int PROCMODE_ADD = 0;
    /** ユーザ ラベル・カテゴリ処理区分(編集) */
    public static final int PROCMODE_EDIT = 1;

    /** 採番IDサブ カテゴリSID */
    public static final String SBNSID_SUB_CATEGORY = "category";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_LABEL = "label";

    /** カテゴリ内のラベルの有無 0 = 存在しない*/
    public static final int CATEGORY_EXIST_NO = 0;
    /** カテゴリ内のラベルの有無 1 = 存在する*/
    public static final int CATEGORY_EXIST_YES = 1;

    /** 管理者設定 制限なし */
    public static final int POW_ALL = 0;
    /** 管理者設定 管理者のみ */
    public static final int POW_LIMIT = 1;

    /** ラベル付与機能使用可否 使用可能 */
    public static final String LABEL_SET_OK = "OK";
    /** ラベル付与機能使用可否 使用不可能 */
    public static final String LABEL_SET_NG = "NG";

    /** ラベル編集機能使用可否 使用可能 */
    public static final String LABEL_EDIT_OK = "OK";
    /** ラベル編集機能使用可否 使用不可能 */
    public static final String LABEL_EDIT_NG = "NG";

    /** システムメールユーザ画像パス */
    public static final String IMAGE_PATH_SYSTEMAIL
            = "user" + File.separator + "images" + File.separator;

    /** システムメールユーザ画像名 */
    public static final String IMAGE_NAME_SYSTEMAIL = "photo_system_mail.gif";


    /** ユーザインポート パスワード変更を行う */
    public static final int PASS_CHANGE_OK = 0;
    /** ユーザインポート パスワード変更を行わない */
    public static final int PASS_CHANGE_NG = 1;

    /** 性別　未設定 */
    public static final int SEIBETU_UNSET = 0;
    /** 性別　男 */
    public static final int SEIBETU_MAN = 1;
    /** 性別　女 */
    public static final int SEIBETU_WOMAN = 2;

    /** CSVインポート　処理実行 */
    public static final int CSV_IMPORT_RUN = 0;
    /** CSVインポート　情報表示 */
    public static final int CSV_IMPORT_DISPLAY = 1;

    /**QRコード表示区分*/
    public static final int QR_CODE_DISP = 1;
    /**QRコード表示区分*/
    public static final int QR_CODE_NOT_DISP = 0;

    /**存在しないユーザ*/
    public static final int NOT_EXIST_USER = -1;

    /** 自動ユーザインポート読み込みファイル */
    public static final String USER_IMPORT_FILE_DIR = "USER_IMPORT_FILE_DIR";
    /** 自動ユーザインポート成功ファイル */
    public static final String USER_IMPORT_SUCCESS_DIR = "USER_IMPORT_SUCCESS_DIR";
    /** 自動ユーザインポート失敗ファイル */
    public static final String USER_IMPORT_FAIL_DIR = "USER_IMPORT_FAIL_DIR";

    /** 自動インポートをしない */
    public static final int AUTO_IMPORT_NO = 0;
    /**自動インポートをする */
    public static final int AUTO_IMPORT_OK = 1;

    /**インポートタイミング 5分毎 */
    public static final int IMPORT_TIMING_MIN = 0;
    /** インポートタイミング 1時間毎 */
    public static final int IMPORT_TIMING_HOUR = 1;
    /** インポートタイミング 指定時間 */
    public static final int IMPORT_TIMING_SELECT = 2;

    /** 自動ユーザインポート 強制実行 */
    public static final String IMPORT_MANUAL_DIR = "manual";
    /** 自動ユーザインポート 自動 */
    public static final String IMPORT_AUTO_DIR = "auto";

    /** 自動インポートデフォルトパス */
    public static final String AUTO_IMPORT_DEF_PATH = "WEB-INF/autoSync/";
    /** ユーザ連携でインポートを行うフォルダの初期値 */
    public static final String USER_IMPORT = "userImport/importFile";
    /** ユーザ連携でインポートが成功したファイルの保存初期位置 */
    public static final String USER_IMPORT_SUCCESS = "userImport/successFile";
    /** ユーザ連携でインポートを失敗したファイルの保存初期位置 */
    public static final String USER_IMPORT_FAILED = "userImport/failFile";
    /** ユーザ連携でインポートを失敗したときに添付するエラーログファイル */
    public static final String USER_IMPORT_FAILED_LOG = "TempErrorLog.txt";
    /** ユーザ検索条件 ログイン停止フラグ */
    public static final int USER_SEARCH_USRUKOFLG_ALL = -1;
    /** ユーザ検索条件 ログイン停止フラグ */
    public static final int USER_SEARCH_USRUKOFLG_YUKO = 0;
    /** ユーザ検索条件 ログイン停止フラグ */
    public static final int USER_SEARCH_USRUKOFLG_MUKO = 1;

    /** グループコンボに設定するテキスト ユーザ指定のVALUE */
    public static final String GROUP_COMBO_VALUE_USER = "-8";
    /** メンバー グループの頭文字 */
    public static final String MEMBER_GROUP_HEADSTR = "G";
}