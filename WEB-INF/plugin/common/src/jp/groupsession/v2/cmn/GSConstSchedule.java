package jp.groupsession.v2.cmn;

import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] スケジュールプラグインで使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstSchedule {
    /** プラグインID */
    public static final String PLUGIN_ID_SCHEDULE = "schedule";

    /** 在席管理のプラグインID */
    public static final String PLUGIN_ID_ZAISEKI = "zaiseki";
    /** ショートメールのプラグインID */
    public static final String PLUGIN_ID_SMAIL = "smail";
    /** 施設予約のプラグインID */
    public static final String PLUGIN_ID_RESERVE = "reserve";

    /** メイン：日間画面でのタイトル非表示スケジュール時間(45分以下非表示) */
    public static final int MAIN_DSP_HOURS = 3;
    /** デフォルト時間分割単位 */
    public static final int DF_HOUR_DIVISION = 10;
    /** デフォルト時間分割単位 */
    public static final int HOUR_DIVISION5 = 5;
    /** デフォルト時間分割単位 */
    public static final int HOUR_DIVISION15 = 15;

    /** 日間画面での時間分割単位(15分単位) */
    public static final int HOUR_DIVISION_COUNT_15 = 4;
    /** 日間画面での時間分割単位(10分単位) */
    public static final int HOUR_DIVISION_COUNT_10 = 6;
    /** 日間画面での時間分割単位(5分単位) */
    public static final int HOUR_DIVISION_COUNT_5 = 12;

    /** 日間画面での表示時間数 */
    public static final int DAILY_HOUR_COUNT = 9;
    /** 日間画面でのCOLS */

    /** メイン日間画面でのROWS */
    public static final int DAILY_ROWS_COUNT = 96;
    /** 週間画面での表示日数 */
    public static final int WEEK_DAY_COUNT = 7;
    /** グループスケジュール */
    public static final int SCHEDULE_GROUP = -1;
    /** グループ+メンバースケジュール */
    public static final int SCHEDULE_GROUP_MEMBER = -2;

    /** 年コンボの表示件数 */
    public static final int YEAR_LIST_CNT = 5;
    /** 自動リロード時間 10分 */
    public static final int AUTO_RELOAD_10MIN = 600000;

    /** 開始　時 */
    public static final int DAY_START_HOUR = 0;
    /** 開始　分 */
    public static final int DAY_START_MINUTES = 0;
    /** 開始　秒 */
    public static final int DAY_START_SECOND = 0;
    /** 開始　ミリ秒 */
    public static final int DAY_START_MILLISECOND
    = java.util.Calendar.getInstance().getMinimum(java.util.Calendar.MILLISECOND);
    /** 終了　時 */
    public static final int DAY_END_HOUR = 23;
    /** 終了　分 */
    public static final int DAY_END_MINUTES = 59;
    /** 終了　秒 */
    public static final int DAY_END_SECOND = 59;
    /** 終了　ミリ秒 */
    public static final int DAY_END_MILLISECOND
    = java.util.Calendar.getInstance().getMaximum(java.util.Calendar.MILLISECOND);

    /** 設定可能な最大　分 */
    public static final int DAY_SYSMAX_MINUTES = 55;

    /** デフォルト時間指定：from時 */
    public static final int DF_FROM_HOUR = 9;
    /** デフォルト時間指定：from分 */
    public static final int DF_FROM_MINUTES = 0;

    /** デフォルト時間指定：to時 */
    public static final int DF_TO_HOUR = 18;
    /** デフォルト時間指定：to分 */
    public static final int DF_TO_MINUTES = 0;

    /** デフォルト時間指定：fromAM時 */
    public static final int DF_FROM_AM_HOUR = 9;
    /** デフォルト時間指定：fromAM分 */
    public static final int DF_FROM_AM_MINUTES = 0;
    /** デフォルト時間指定：toAM時 */
    public static final int DF_TO_AM_HOUR = 12;
    /** デフォルト時間指定：toAM分 */
    public static final int DF_TO_AM_MINUTES = 0;

    /** デフォルト時間指定：fromPM時 */
    public static final int DF_FROM_PM_HOUR = 13;
    /** デフォルト時間指定：fromPM分 */
    public static final int DF_FROM_PM_MINUTES = 0;
    /** デフォルト時間指定：toPM時 */
    public static final int DF_TO_PM_HOUR = 18;
    /** デフォルト時間指定：toPM分 */
    public static final int DF_TO_PM_MINUTES = 0;

    /** デフォルト時間指定：fromAllDay時 */
    public static final int DF_FROM_ALL_DAY_HOUR = 9;
    /** デフォルト時間指定：fromAllDay分 */
    public static final int DF_FROM_ALL_DAY_MINUTES = 0;
    /** デフォルト時間指定：toAllDay時 */
    public static final int DF_TO_ALL_DAY_HOUR = 18;
    /** デフォルト時間指定：toAllDay分 */
    public static final int DF_TO_ALL_DAY_MINUTES = 0;

    /** 背景色 未選択 */
    public static final int NONE_BG_COLOR = 0;
    /** 背景色 デフォルト */
    public static final int DF_BG_COLOR = 1;

    /** デフォルトスケジュールグループID */
    public static final int DF_SCHGP_ID = -1;

    /** ユーザ区分 ユーザスケジュール */
    public static final int USER_KBN_USER = 0;
    /** ユーザ区分 グループスケジュール */
    public static final int USER_KBN_GROUP = 1;

    /** グループに所属 */
    public static final int GROUP_BELONG = 1;
    /** グループに未所属 */
    public static final int GROUP_NOT_BELONG = 0;

    /** 時間指定区分 有り */
    public static final int TIME_EXIST = 0;
    /** 時間指定区分 無し */
    public static final int TIME_NOT_EXIST = 1;
    /** 公開区分 する */
    public static final int DSP_PUBLIC = 0;
    /** 公開区分 しない */
    public static final int DSP_NOT_PUBLIC = 1;
    /** 公開区分 予定あり */
    public static final int DSP_YOTEIARI = 2;
    /** 公開区分 所属グループのみ */
    public static final int DSP_BELONG_GROUP = 3;
    /** 公開区分 指定ユーザ・グループのみ */
    public static final int DSP_USRGRP = 4;
    /** 公開区分 タイトルのみ */
    public static final int DSP_TITLE = 5;
    

    /** 公開区分 指定無し */
    public static final int DSP_ALL = -1;

    /** 休日区分 休日 */
    public static final int HOLIDAY_TRUE = 1;
    /** 休日区分 平日 */
    public static final int HOLIDAY_FALSE = 0;

    /** 今日区分 今日 */
    public static final int TODAY_TRUE = 1;
    /** 今日区分 今日以外 */
    public static final int TODAY_FALSE = 0;

    /** 編集 可能 */
    public static final int CAN_EDIT_TRUE = 1;
    /** 編集 不可 */
    public static final int CAN_EDIT_FALSE = 0;

    /** メンバ表示順 各ユーザが指定する */
    public static final int MEM_DSP_USR = 1;
    /** メンバ表示順 管理者が設定する */
    public static final int MEM_DSP_ADM = 0;

    /** メンバ表示順 ユーザが変更 */
    public static final int MEM_EDIT_EXECUTE = 1;
    /** メンバ表示順 ユーザ未変更 */
    public static final int MEM_EDIT_NOT_EXECUTE = 0;

    /** 時間指定　未設定 */
    public static final String TIME_NOT_SELECT = "-1";

    /** タイトルMAX文字数 */
    public static final int MAX_LENGTH_TITLE = 50;
    /** 内容MAX文字数 */
    public static final int MAX_LENGTH_VALUE = 1000;
    /** 備考MAX文字数 */
    public static final int MAX_LENGTH_BIKO = 1000;
    /** コメントMAX文字数 */
    public static final int MAX_LENGTH_COMMNET = 50;

    /** 処理モード　登録 */
    public static final String CMD_ADD = "add";
    /** 処理モード　修正 */
    public static final String CMD_EDIT = "edit";
    /** 処理モード　削除 */
    public static final String CMD_DELETE = "del";
    /** 処理モード　閲覧 */
    public static final String CMD_DSP = "dsp";

    /** 画面遷移元　週間 */
    public static final String DSP_MOD_WEEK = "1";
    /** 画面遷移元　月間 */
    public static final String DSP_MOD_MONTH = "2";
    /** 画面遷移元　日間 */
    public static final String DSP_MOD_DAY = "3";
    /** 画面遷移元　メイン */
    public static final String DSP_MOD_MAIN = "4";

    /** 画面遷移元　一覧 */
    public static final String DSP_MOD_LIST = "5";
    /** 画面遷移元　個人月間 */
    public static final String DSP_MOD_KOJIN_WEEK = "6";
    /** 一覧 初期表示件数 */
    public static final int DEFAULT_LIST_CNT = 10;

    /** ソート 名前 */
    public static final int SORT_KEY_NAME = GSConstUser.USER_SORT_NAME;
    /** ソート 社員/職員番号 */
    public static final int SORT_KEY_SNO = GSConstUser.USER_SORT_SNO;
    /** ソート 役職 */
    public static final int SORT_KEY_YKSK = GSConstUser.USER_SORT_YKSK;
    /** ソート 生年月日 */
    public static final int SORT_KEY_BDATE = GSConstUser.USER_SORT_BDATE;
    /** ソート ソートキー1 */
    public static final int SORT_KEY_SORTKEY1 = GSConstUser.USER_SORT_SORTKEY1;
    /** ソート ソートキー2 */
    public static final int SORT_KEY_SORTKEY2 = GSConstUser.USER_SORT_SORTKEY2;

    /** ソート 開始日 */
    public static final int SORT_KEY_FRDATE = 2;
    /** ソート 終了日 */
    public static final int SORT_KEY_TODATE = 3;
    /** ソート タイトル */
    public static final int SORT_KEY_TITLE = 4;
    /** ソートキーALL */
    public static final int[] LIST_SORT_KEY_ALL = new int[] { SORT_KEY_NAME,
        SORT_KEY_FRDATE, SORT_KEY_TODATE, SORT_KEY_TITLE};

    /** 検索対象 件名 */
    public static final int SEARCH_TARGET_TITLE = 1;
    /** 検索対象 本文 */
    public static final int SEARCH_TARGET_HONBUN = 2;
    /** キーワード検索区分 (and) */
    public static final int KEY_WORD_KBN_AND = 0;
    /** キーワード検索区分 (or) */
    public static final int KEY_WORD_KBN_OR = 1;

    /** ソート 昇順 */
    public static final int ORDER_KEY_ASC = 0;
    /** ソート 降順 */
    public static final int ORDER_KEY_DESC = 1;

    /** ソートキーALL */
    public static final int[] SORT_KEY_ALL = new int[] { SORT_KEY_NAME,
            SORT_KEY_SNO, SORT_KEY_YKSK, SORT_KEY_BDATE, SORT_KEY_SORTKEY1, SORT_KEY_SORTKEY2 };

    /** 一覧表示件数 */
    public static final int[] LIST_LINE_COUNTER = new int[] { 10,
            20, 30, 40, 50 };


    /** CSV出力対象 ログインID */
    public static final String CSV_OUT_LOGIN_ID = "1";
    /** CSV出力対象 グループID */
    public static final String CSV_OUT_GROUP_ID = "2";
    /** CSV出力対象 氏名 */
    public static final String CSV_OUT_NAME = "3";
    /** CSV出力対象 開始日付 */
    public static final String CSV_OUT_FRDATE = "4";
    /** CSV出力対象 開始時刻 */
    public static final String CSV_OUT_FRTIME = "5";
    /** CSV出力対象 終了日付 */
    public static final String CSV_OUT_TODATE = "6";
    /** CSV出力対象 終了時刻 */
    public static final String CSV_OUT_TOTIME = "7";
    /** CSV出力対象 タイトル */
    public static final String CSV_OUT_TITLE = "8";
    /** CSV出力対象 タイトル色 */
    public static final String CSV_OUT_TITLE_COLOR = "9";
    /** CSV出力対象 内容 */
    public static final String CSV_OUT_VALUE = "10";
    /** CSV出力対象 備考 */
    public static final String CSV_OUT_BIKO = "11";
    /** CSV出力対象 編集権限 */
    public static final String CSV_OUT_EDITPERMIT = "12";
    /** CSV出力対象 公開 */
    public static final String CSV_OUT_OPEN = "13";
    /** CSV出力対象 時間指定区分 */
    public static final String CSV_OUT_TIMEKBN = "14";
    /** CSV出力対象 登録者氏名 */
    public static final String CSV_OUT_ADNAME = "15";
    /** CSV出力対象 更新者氏名 */
    public static final String CSV_OUT_EDNAME = "16";

    /** 曜日設定 今日 */
    public static final int CHANGE_WEEK_TODAY = 0;
    /** 曜日設定 日曜日 */
    public static final int CHANGE_WEEK_SUN = 1;
    /** 曜日設定 月曜日 */
    public static final int CHANGE_WEEK_MON = 2;
    /** 曜日設定 火曜日 */
    public static final int CHANGE_WEEK_TUE = 3;
    /** 曜日設定 水曜日 */
    public static final int CHANGE_WEEK_WED = 4;
    /** 曜日設定 木曜日 */
    public static final int CHANGE_WEEK_THU = 5;
    /** 曜日設定 金曜日 */
    public static final int CHANGE_WEEK_FRI = 6;
    /** 曜日設定 土曜日 */
    public static final int CHANGE_WEEK_SAT = 7;

    //共有範囲設定
    /** 共有設定 (全員で共有する) */
    public static final int CRANGE_SHARE_ALL = 0;
    /** 共有設定 (グループメンバ内で共有する) */
    public static final int CRANGE_SHARE_GROUP = 1;

    //自動データ削除設定
    /** 自動データ削除 設定しない */
    public static final int AUTO_DELETE_OFF = 0;
    /** 自動データ削除 自動削除する */
    public static final int AUTO_DELETE_ON = 1;

    //編集権限設定
    /** 編集権限 設定しない */
    public static final int EDIT_CONF_NONE = 0;
    /** 編集権限 本人 */
    public static final int EDIT_CONF_OWN = 1;
    /** 編集権限 グループ */
    public static final int EDIT_CONF_GROUP = 2;

    //拡張登録
    /** 拡張区分 毎日 */
    public static final int EXTEND_KBN_DAY = 1;
    /** 拡張区分 毎週 */
    public static final int EXTEND_KBN_WEEK = 2;
    /** 拡張区分 毎月 */
    public static final int EXTEND_KBN_MONTH = 3;
    /** 拡張区分 毎年 */
    public static final int EXTEND_KBN_YEAR = 4;
    /** 拡張区分 隔週 */
    public static final int EXTEND_KBN_BIWEEK = 5;
    /** 振替区分 しない */
    public static final int FURIKAE_KBN_NONE = 0;
    /** 振替区分 前営業日 */
    public static final int FURIKAE_KBN_BEF = 1;
    /** 振替区分 翌営業日 */
    public static final int FURIKAE_KBN_AFT = 2;
    /** 振替区分 登録をしない */
    public static final int FURIKAE_KBN_NOADD = 9;
    /** 設定なし */
    public static final int SETTING_NONE = 0;

    /** 拡張区分 毎月 週指定 */
    public static final int EXTEND_MONTH_WEEK = 0;
    /** 拡張区分 毎月 日付指定 */
    public static final int EXTEND_MONTH_DAY = 1;

    /** 拡張区分 毎月 日付指定 設定区分(のみ) */
    public static final int CONF_KBN_ONLY = 0;
    /** 拡張区分 毎月 日付指定 設定区分(から) */
    public static final int CONF_KBN_FROM = 1;
    /** 拡張区分 毎月 日付指定 設定区分(まで) */
    public static final int CONF_KBN_TO = 2;

    /** 拡張SID 初期値 */
    public static final int DEF_SCE_SID = -1;

    /** 施設予約プラグイン使用する */
    public static final int RESERVE_PLUGIN_USE = 0;
    /** 施設予約プラグイン使用しない */
    public static final int RESERVE_PLUGIN_NOT_USE = 1;
    /** プラグイン使用する */
    public static final int PLUGIN_USE = 0;
    /** プラグイン使用しない */
    public static final int PLUGIN_NOT_USE = 1;
    /** マイグループSIDの先頭文字*/
    public static final String MY_GROUP_STRING = "M";
    /** 表示リストSIDの先頭文字*/
    public static final String DSP_LIST_STRING = "H";
    /** 管理者設定 ショートメール通知設定区分 各ユーザが設定 */
    public static final int SMAIL_SEND_KBN_USER = 0;
    /** 管理者設定 ショートメール通知設定区分  管理者が設定 */
    public static final int SMAIL_SEND_KBN_ADMIN = 1;
    /** ショートメール通知を使用しない */
    public static final int SMAIL_NOT_USE = 0;
    /** ショートメール通知を使用する */
    public static final int SMAIL_USE = 1;

    /** スケジュール重複登録 可 */
    public static final int SCH_REPEAT_KBN_OK = 0;
    /** スケジュール重複登録 不可 */
    public static final int SCH_REPEAT_KBN_NG = 1;
    /** スケジュール重複登録 警告を表示 */
    public static final int SCH_REPEAT_KBN_WARNING = 2;

    /** 自分のスケジュール重複登録 不可 */
    public static final int SCH_REPEAT_MY_KBN_NG = 0;
    /** 自分のスケジュール重複登録 可 */
    public static final int SCH_REPEAT_MY_KBN_OK = 1;

    /** タイトルカラー 青 */
    public static final int BGCOLOR_BLUE = 1;
    /** タイトルカラー 赤 */
    public static final int BGCOLOR_RED = 2;
    /** タイトルカラー 緑 */
    public static final int BGCOLOR_GREEN = 3;
    /** タイトルカラー 橙 */
    public static final int BGCOLOR_ORANGE = 4;
    /** タイトルカラー 黒 */
    public static final int BGCOLOR_BLACK = 5;

    /** タイトルカラー 紺 */
    public static final int BGCOLOR_NAVY = 6;
    /** タイトルカラー 茶 */
    public static final int BGCOLOR_MAROON = 7;
    /** タイトルカラー 青緑 */
    public static final int BGCOLOR_TEAL = 8;
    /** タイトルカラー 灰 */
    public static final int BGCOLOR_GRAY = 9;
    /** タイトルカラー 水色 */
    public static final int BGCOLOR_LBLUE = 10;

    /** タイトルカラー 全配列 */
    public static final int[] BGCOLOR_ALL = new int[] {
            BGCOLOR_BLUE,
            BGCOLOR_RED,
            BGCOLOR_GREEN,
            BGCOLOR_ORANGE,
            BGCOLOR_BLACK,
            BGCOLOR_NAVY,
            BGCOLOR_MAROON,
            BGCOLOR_TEAL,
            BGCOLOR_GRAY,
            BGCOLOR_LBLUE
    };

    /** インポートサンプルCSVファイル名(管理者向け) */
    public static final String SAMPLE_SCH_CSV_NAME_ADM = "sample_admin.xlsx";
    /** インポートサンプルCSVファイル名(一般向け) */
    public static final String SAMPLE_SCH_CSV_NAME = "sample.xlsx";
    /** インポート項目数(管理者向け) */
    public static final int IMP_VALUE_SIZE_ADM = 12;
    /** インポート項目数(一般向け) */
    public static final int IMP_VALUE_SIZE = 10;

    /** 開始日付区分 */
    public static final int FROM_DATE_KBN = 0;
    /** 終了日付区分 */
    public static final int TO_DATE_KBN = 1;
    /** ユーザ指定　未設定 */
    public static final String USER_NOT_SELECT = "-1";
    /** 初期表示フラグ 初期 */
    public static final int INIT_FLG = 0;
    /** 初期表示フラグ 初期済 */
    public static final int NOT_INIT_FLG = 1;
    /** 初期表示フラグ URLリンク先から遷移 */
    public static final int LINK_INIT_FLG = 2;

    /** 複写フラグ 通常 */
    public static final String NOT_COPY_FLG = "0";
    /** 複写フラグ 複写 */
    public static final String COPY_FLG = "1";

    /** 同時登録選択区分 選択解除 */
    public static final int SELECT_OFF = 0;
    /** 同時登録選択区分 選択 */
    public static final int SELECT_ON = 1;

    /** 同期編集フラグ OFF */
    public static final int SAME_EDIT_OFF = 0;
    /** 同期編集フラグ ON */
    public static final int SAME_EDIT_ON = 1;


    /** 空き状況遷移元画面　通常 */
    public static final int MOVE_NO = 0;
    /** 空き状況遷移元画面　拡張 */
    public static final int MOVE_EX = 1;

    /** 個人週間スケジュール　表示形式 */
    public static final String DEFAULT_VIEW = "agendaWeek";
    /** 個人週間スケジュール　開始時刻 */
    public static final String DEFAULT_MIN_TIME = "9:00";
    /** 個人週間スケジュール　終了時刻 */
    public static final String DEFAULT_MAX_TIME = "18:00";
    /** 個人週間スケジュール　終了時刻 */
    public static final String DEFAULT_TIME_FORMAT = "H:mm{ - H:mm}";
    /** 個人週間スケジュール　アスペクト比 */
    public static final double DEFAULT_ASPECT_RATIO = 1.0;
    /** 個人週間スケジュール　テーマ有効、無効 */
    public static final boolean DEFAULT_THEME = true;
    /** 個人週間スケジュール　選択有効、無効 */
    public static final boolean DEFAULT_SELECTTABLE = true;
    /** 個人週間スケジュール　選択後時間表示 */
    public static final boolean DEFAULT_SELECTHELPER = true;
    /** 個人週間スケジュール　編集可否 */
    public static final boolean DEFAULT_EDITABLE = true;
    /** 個人週間スケジュール　header　left */
    public static final String DEFAULT_HEADER_LEFT = "";
    /** 個人週間スケジュール　header　center */
    public static final String DEFAULT_HEADER_CENTER = "title";
    /** 個人週間スケジュール　header　right */
    public static final String DEFAULT_HEADER_RIGHT = "";
    /** 個人週間スケジュール　ﾀｲﾄﾙフォーマット month */
    public static final String DEFAULT_COLUMN_FORMAT_MONTH = "ddd";
    /** 個人週間スケジュール　コラムフォーマット day */
    public static final int DEFAULT_SLOT_MINUTES = 30;
    /** 個人週間スケジュール　クリック時遷移URL */
    public static final String SELECT_URL = "\"../schedule/sch200.do\"";

    /** 表示位置区分 全日 */
    public static final int DSP_ALL_DAY = 0;
    /** 表示位置区分 定時 */
    public static final int DSP_REGULAR_TIME = 1;

    /** 画面　日間 */
    public static final int DSP_DAY = 0;
    /** 画面　週間 */
    public static final int DSP_WEEK = 1;
    /** 画面　月間 */
    public static final int DSP_MONTH = 2;
    /** 画面 個人週間 */
    public static final int DSP_PRI_WEEK = 3;

    /** 遷移先URL 日間 */
    public static final String DSP_DAY_URL = "../schedule/sch030.do";
    /** 遷移先URL 週間 */
    public static final String DSP_WEEK_URL = "../schedule/sch010.do";
    /** 遷移先URL 月間 */
    public static final String DSP_MONTH_URL = "../schedule/sch020.do";
    /** 遷移先URL 個人週間 */
    public static final String DSP_PRI_WEEK_URL = "../schedule/sch200.do";

    /** dropイベント */
    public static final int EVENT_DROP = 0;
    /** resizeイベント */
    public static final int EVENT_RESIZE = 1;

    /** 日次バッチで一度に削除するスケジュール件数 */
    public static final int SCH_BATCH_DELETE_COUNT = 100;

    /** スケジュールポートレット グループスケジュール選択画面ID */
    public static final String SCREENID_SCHPTL020 = "schptl020";

    /** グループスケジュール表示設定  表示する */
    public static final int GROUP_SCH_SHOW = 0;
    /** グループスケジュール表示設定  表示しない*/
    public static final int GROUP_SCH_NOT_SHOW = 1;

    /** 管理者設定 初期値 時間設定 設定種別 0:ユーザ単位で設定 */
    public static final int SAD_INITIME_STYPE_USER = 0;
    /** 管理者設定 初期値 時間設定 設定種別 1:管理者強制 */
    public static final int SAD_INITIME_STYPE_ADM = 1;

    /** 管理者設定 初期値 編集権限設定 設定種別 0:ユーザ単位で設定 */
    public static final int SAD_INIEDIT_STYPE_USER = 0;
    /** 管理者設定 初期値 編集権限設定 設定種別 1:管理者強制 */
    public static final int SAD_INIEDIT_STYPE_ADM = 1;

    /** 管理者設定 初期値 公開区分設定 設定種別 0:ユーザ単位で設定 */
    public static final int SAD_INIPUBLIC_STYPE_USER = 0;
    /** 管理者設定 初期値 公開区分設定 設定種別 1:管理者強制 */
    public static final int SAD_INIPUBLIC_STYPE_ADM = 1;

    /** 管理者設定 初期値 同時修正設定 設定種別 0:ユーザ単位で設定 */
    public static final int SAD_INISAME_STYPE_USER = 0;
    /** 管理者設定 初期値 同時修正設定 設定種別 1:管理者強制 */
    public static final int SAD_INISAME_STYPE_ADM = 1;

    /** 管理者設定 初期値 重複登録区分 設定種別 0:ユーザ単位で設定 */
    public static final int SAD_REPEAT_STYPE_USER = 0;
    /** 管理者設定 初期値 重複登録区分 設定種別 1:管理者強制 */
    public static final int SAD_REPEAT_STYPE_ADM = 1;

    /** 期間スケジュールなし*/
    public static final int PERIOD_NOT_EXSIST = 1;

    /** 他プラグインデータ取得用パラメータ  ユーザSid*/
    public static final String APPEND_PARAM_USRSID = "usrSid";
    /** 他プラグインデータ取得用パラメータ  開始日付*/
    public static final String APPEND_PARAM_FRDATE = "frDate";
    /** 他プラグインデータ取得用パラメータ  終了日付*/
    public static final String APPEND_PARAM_TODATE = "toDate";

    /** 画面ID sch010*/
    public static final String DSP_ID_SCH010 = "sch010";

    /** スケジュール管理者設定 デフォルト表示グループ ユーザ単位で設定 */
    public static final int SAD_DSP_GROUP_USER = 0;
    /** スケジュール管理者設定 デフォルト表示グループ デフォルトグループに強制 */
    public static final int SAD_DSP_GROUP_DEFGROUP = 1;

    /** スケジュール管理者設定 タイトル色区分 追加しない */
    public static final int SAD_MSG_NO_ADD = 0;
    /** スケジュール管理者設定 タイトル色区分 追加する */
    public static final int SAD_MSG_ADD = 1;

    /** スケジュール管理者設定 メンバー表示順設定 メンバー表示順 氏名*/
    public static final int SCH_MEM_ORDER_SORT_NAME = 1;
    /** スケジュール管理者設定 メンバー表示順設定 メンバー表示順 社員/職員番号*/
    public static final int SCH_MEM_ORDER_SORT_SYAIN = 2;
    /** スケジュール管理者設定 メンバー表示順設定 メンバー表示順 役職*/
    public static final int SCH_MEM_ORDER_SORT_POST = 3;
    /** スケジュール管理者設定 メンバー表示順設定 メンバー表示順 生年月日*/
    public static final int SCH_MEM_ORDER_SORT_BIRTHDAY = 5;
    /** スケジュール管理者設定 メンバー表示順設定 メンバー表示順 ソートキー1*/
    public static final int SCH_MEM_ORDER_SORT_SORT1 = 11;
    /** スケジュール管理者設定 メンバー表示順設定 メンバー表示順 ソートキー2*/
    public static final int SCH_MEM_ORDER_SORT_SORT2 = 12;

    /** スケジュール個人設定 デフォルト表示グループSID デフォルトグループ */
    public static final int SCC_DSP_GROUP_DEF = -1;

    /** スケジュール個人設定 グループメンバー表示設定  表示する */
    public static final int SCC_USER_SHOW = 0;
    /** スケジュール個人設定 グループメンバー表示設定  表示しない */
    public static final int SCC_USER_NOT_SHOW = 1;

    /** 出欠確認区分 確認しない */
    public static final int ATTEND_KBN_NO = 0;
    /** 出欠確認区分 確認する */
    public static final int ATTEND_KBN_YES = 1;

    /** 出欠回答区分 未回答 */
    public static final int ATTEND_ANS_NONE = 0;
    /** 出欠回答区分 出席 */
    public static final int ATTEND_ANS_YES = 1;
    /** 出欠回答区分 欠席 */
    public static final int ATTEND_ANS_NO = 2;

    /** 出欠登録者区分 登録者 */
    public static final int ATTEND_REGIST_USER_YES = 0;
    /** 出欠登録者区分 登録者以外 */
    public static final int ATTEND_REGIST_USER_NO = 1;

    /** スケジュール編集画面 表示モード 通常スケジュール */
    public static final int EDIT_DSP_MODE_NORMAL = 0;
    /** スケジュール編集画面 表示モード 出欠依頼者 */
    public static final int EDIT_DSP_MODE_REGIST = 1;
    /** スケジュール編集画面 表示モード 出欠回答者 */
    public static final int EDIT_DSP_MODE_ANSWER = 2;

    /** 出欠確認内容更新通知メール 通知しない */
    public static final int ATTEND_UPDATE_MAIL_NO = 0;
    /** 出欠確認内容更新通知メール 通知する */
    public static final int ATTEND_UPDATE_MAIL_YES = 1;

    /** 出欠確認一覧表示 最大件数 (スケジュール編集画面) */
    public static final int ATTEND_LIST_MAX_NUM = 10;

    /** 出欠確認 出欠確認依頼者スケジュール 存在フラグ 存在 */
    public static final int ATTEND_SCH_DEL_NO = 0;
    /** 出欠確認 出欠確認依頼者スケジュール 存在フラグ 削除 */
    public static final int ATTEND_SCH_DEL_YES = 1;

    /** スケジュール特例アクセス_制限対象 種別 ユーザ */
    public static final int SST_TYPE_USER = 0;
    /** スケジュール特例アクセス_制限対象 種別 グループ */
    public static final int SST_TYPE_GROUP = 1;

    /** スケジュール特例アクセス_許可対象 種別 ユーザ */
    public static final int SSP_TYPE_USER = 0;
    /** スケジュール特例アクセス_許可対象 種別 グループ */
    public static final int SSP_TYPE_GROUP = 1;
    /** スケジュール特例アクセス_許可対象 種別 役職 */
    public static final int SSP_TYPE_POSITION = 2;

    /** スケジュール特例アクセス_許可対象 権限区分 閲覧のみ */
    public static final int SSP_AUTH_VIEWONLY = 0;
    /** スケジュール特例アクセス_許可対象 権限区分 登録・編集・削除 */
    public static final int SSP_AUTH_EDIT = 1;

    /** スケジュールアクセス可能フラグ 不可能 */
    public static final int SCH_ACCESS_NO = 0;
    /** スケジュールアクセス可能フラグ 可能 */
    public static final int SCH_ACCESS_YES = 1;

    /** スケジュール アクセス権限フィルタ すべて */
    public static final int SSP_AUTHFILTER_NONE = 0;
    /** スケジュール アクセス権限フィルタ 閲覧可能 */
    public static final int SSP_AUTHFILTER_VIEW = 1;
    /** スケジュール アクセス権限フィルタ 編集可能 */
    public static final int SSP_AUTHFILTER_EDIT = 2;

    /** メイン画面から遷移 */
    public static final int FROM_MAIN = 1;

    /** ポートレットパラメータ グループSID */
    public static final String SCH_PORTLET_PARAM1 = "schDspGrpSid";
    /** ポートレットパラメータ アイテムID */
    public static final String SCH_PORTLET_PARAM2 = "schPtl010ItemId";
    
    /** リマインダー通知 通知する */
    public static final int REMINDER_USE_YES = 1;
    /** リマインダー通知 通知しない */
    public static final int REMINDER_USE_NO = 0;

    /** リマインダー通知 通知時間 通知しない */
    public static final int REMINDER_TIME_NO = 0;
    /** リマインダー通知 通知時間 0分前 */
    public static final int REMINDER_TIME_ZERO = 1;
    /** リマインダー通知 通知時間 5分前 */
    public static final int REMINDER_TIME_FIVE_MINUTES = 2;
    /** リマインダー通知 通知時間 10分前 */
    public static final int REMINDER_TIME_TEN_MINUTES = 3;
    /** リマインダー通知 通知時間 15分前 */
    public static final int REMINDER_TIME_FIFTEEN_MINUTES = 4;
    /** リマインダー通知 通知時間 30分前 */
    public static final int REMINDER_TIME_THIRTY_MINUTES = 5;
    /** リマインダー通知 通知時間 1時間前 */
    public static final int REMINDER_TIME_ONE_HOUR = 6;
    /** リマインダー通知 通知時間 2時間前 */
    public static final int REMINDER_TIME_TWO_HOUR = 7;
    /** リマインダー通知 通知時間 3時間前 */
    public static final int REMINDER_TIME_THREE_HOUR = 8;
    /** リマインダー通知 通知時間 6時間前 */
    public static final int REMINDER_TIME_SIX_HOUR = 9;
    /** リマインダー通知 通知時間 12時間前 */
    public static final int REMINDER_TIME_TWELVE_HOUR = 10;
    /** リマインダー通知 通知時間 1日前 */
    public static final int REMINDER_TIME_ONE_DAY = 11;
    /** リマインダー通知 通知時間 2日前 */
    public static final int REMINDER_TIME_TWO_DAY = 12;
    /** リマインダー通知 通知時間 1週間前 */
    public static final int REMINDER_TIME_ONE_WEEK = 13;
    
    /** 画面ID sch041(スケジュール繰り返し登録) */
    public static final String SCR_ID_SCH040 = "sch040";
    /** 画面ID sch041(スケジュール繰り返し登録) */
    public static final String SCR_ID_SCH041 = "sch041";

    /** 公開対象 種別：グループ */
    public static final int SDP_TYPE_USER = 0;
    /** 公開対象 種別：グループ */
    public static final int SDP_TYPE_GROUP = 1;

    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** スケジュール一括登録フラグ 一括登録 */
    public static final int SCH_IKKATSUFLG_ENTRY = 1;
    /** スケジュール一括登録表示モード 一括登録 */
    public static final int SCH_IKKATSUVIEWMODE_YES = 1;

    /** 簡易登録用 重複警告チェック チェックを行わない */
    public static final int WARN_CHECK_NO = 0;
    /** 簡易登録用 重複警告チェック チェックを行う */
    public static final int WARN_CHECK_YES = 1;
    
    /** 管理者設定 ショートメール通知設定区分 通知する */
    public static final int SML_NTF_KBN_YES = 0;
    /** 管理者設定 ショートメール通知設定区分 通知しない */
    public static final int SML_NTF_KBN_NO = 1;
}
