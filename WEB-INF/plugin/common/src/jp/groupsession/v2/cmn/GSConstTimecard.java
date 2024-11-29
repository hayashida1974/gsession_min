package jp.groupsession.v2.cmn;

import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] タイムカードプラグインで使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstTimecard {

    /** プラグインID */
    public static final String PLUGIN_ID_TIMECARD = "timecard";

    /** タイムカード内戻り先画面 トップ */
    public static final int TCD_BACKSCREEN_TOP = 0;
    /** タイムカード内戻り先画面 管理者設定 */
    public static final int TCD_BACKSCREEN_ADMIN = 1;

    /** メイン：日間画面でのタイトル非表示スケジュール時間(45分以下非表示) */
    public static final int MAIN_DSP_HOURS = 3;
    /** 不正データフラグ(正常) */
    public static final int DATA_NOT_FAIL = 0;
    /** 不正データフラグ(不正) */
    public static final int DATA_FAIL = 1;

    /** 前日終業せず */
    public static final int STATUS_NOT_END = 0;
    /** 未登録 */
    public static final int STATUS_FREE = 1;
    /** 開始のみ入力 */
    public static final int STATUS_HAFE = 2;
    /** 登録済み */
    public static final int STATUS_FIN = 3;
    /** 不正 */
    public static final int STATUS_FAIL = 9;

    /** 登録ステータス(メインにて更新) */
    public static final int TCD_STATUS_MAIN = 0;
    /** 登録ステータス(編集画面にて更新) */
    public static final int TCD_STATUS_EDIT = 1;

    /** 時間帯設定4目盛でのMAXROWS */
    public static final int ZONE_ROWS_COUNT_FORU = 96;
    /** 時間帯設定6目盛でのMAXROWS */
    public static final int ZONE_ROWS_COUNT_SIX = 144;
    /** 時間帯設定4目盛 */
    public static final int HOUR_ROWS_COUNT_FORU = 4;
    /** 時間帯設定6目盛 */
    public static final int HOUR_ROWS_COUNT_SIX = 6;

    /** 時間設定限度 */
    public static final int MAX_TIMECARD_HOUR = 47;

    /** デフォルトの間隔 */
    public static final int DF_INTERVAL = 15;
    /** デフォルトの換算値 */
    public static final int DF_KANSAN = 10;
    /** デフォルトの締日 */
    public static final int DF_SIMEBI = 99;

    /** 休日換算フラグ(休日) */
    public static final int HOLIDAY_FLG = 1;
    /** 休日換算フラグ(休日以外) */
    public static final int NOT_HOLIDAY_FLG = 0;

    /** タイムカードロックフラグ(ロック) */
    public static final int LOCK_FLG = 1;
    /** タイムカードロックフラグ(アンロック) */
    public static final int UNLOCK_FLG = 0;

    /** 修正時備考入力必須フラグ 必須 */
    public static final int BIKO_NECESSARY_FLG = 1;
    /** 修正時備考入力必須フラグ */
    public static final int BIKO_UNNECESSARY_FLG = 0;

    /** デフォルトの始業時間 */
    public static final int DF_IN_TIME = 9;
    /** デフォルトの終業時間 */
    public static final int DF_OUT_TIME = 18;
    /** デフォルトの始業分 */
    public static final int DF_IN_MIN = 0;
    /** デフォルトの終業分 */
    public static final int DF_OUT_MIN = 0;

    /** 備考の最大文字数 */
    public static final int BIKO_MAX_LENGTH = 20;

    /** 表示最大年数 */
    public static final int MAX_YEAR = 2099;

    /** 表示最小年数 */
    public static final int MIN_YEAR = 2000;

    /** 一般ユーザ */
    public static final int USER_KBN_NORMAL = 0;
    /** グループ管理者ユーザ */
    public static final int USER_KBN_GRPADM = 1;
    /** 管理者ユーザ */
    public static final int USER_KBN_ADMIN = 2;

    //遅刻区分
    /** 指定無し */
    public static final int CHK_KBN_UNSELECT = 0;
    /** 遅刻 */
    public static final int CHK_KBN_SELECT = 1;
    //早退区分
    /** 指定無し */
    public static final int SOU_KBN_UNSELECT = 0;
    /** 早退 */
    public static final int SOU_KBN_SELECT = 1;

    //休日有無
    /** 休日以外 */
    public static final int HOL_NOTHOLIDAY = 0;
    /** 休日 */
    public static final int HOL_HOLIDAY = 1;

    /** 休日集計区分  指定無し */
    public static final int HOL_KBN_UNSELECT = 0;
    /** 休日集計区分 有休 */
    public static final int HOL_KBN_YUUKYUU = 1;
    /** 休日集計区分  欠勤 */
    public static final int HOL_KBN_KEKKIN = 2;

    /** 欠勤 休日区分Sid */
    public static final int THI_SID_KEKKIN = 1;
    /** 慶弔 休日区分Sid */
    public static final int THI_SID_KEITYO = 2;
    /** 有休 休日区分Sid */
    public static final int THI_SID_YUUKYU = 3;
    /** 代休 休日区分Sid */
    public static final int THI_SID_DAIKYU = 4;
    /** その他 休日区分Sid */
    public static final int THI_SID_SONOTA = 5;
    /** 振替休 休日区分Sid */
    public static final int THI_SID_HURIKYU = 6;

    /** デフォルト休日MAXSID*/
    public static final int DEF_HOLIDAY_MAX_SID = 6;

    /** 複数編集時の日付セパレータ */
    public static final String DAYS_SEP = ",";

    /** 遷移元ID格納パラメータ名 */
    public static final String DSP_MOD = "dspMod";

    /** 項目最大桁数(備考) */
    public static final int MAX_LENGTH_BIKO = 100;
    /** 項目最大桁数(休暇理由) */
    public static final int MAX_LENGTH_ABSENCE_REASON = 100;
    /** 項目最大桁数(休日その他) */
    public static final int MAX_LENGTH_SONOTA = 10;
    /** 項目最大桁数(休日日数) */
    public static final int MAX_LENGTH_HOLDAYS = 3;

    /** 時間帯設定　未設定時間のSID */
    public static final int BLANK_ZONE_SID = -1;

    /** 入力単位(間隔) */
    public static final int[] TIMECARD_BETWEEN = new int[] { 1,
            10, 15, 30, 60 };
    /** 入力単位(進数) */
    public static final int[] TIMECARD_SINSU = new int[] { 10, 60 };
    /** 締日 */
    public static final int[] TIMECARD_LIMITDAY = new int[] { 5,
            10, 15, 20, 25, 99 };
    /** 時間帯区分(通常) */
    public static final int TIMEZONE_KBN_NORMAL = 1;
    /** 時間帯区分(残業) */
    public static final int TIMEZONE_KBN_ZANGYO = 2;
    /** 時間帯区分(深夜残業) */
    public static final int TIMEZONE_KBN_SINYA = 3;
    /** 時間帯区分(休憩) */
    public static final int TIMEZONE_KBN_KYUKEI = 4;

    /** ソートKEY(氏名) */
    public static final int SORT_SIMEI = GSConstUser.USER_SORT_NAME;
    /** ソートKEY(社員/職員番号) */
    public static final int SORT_SYAINNO = GSConstUser.USER_SORT_SNO;
    /** ソートKEY(稼動日) */
    public static final int SORT_KADODAYS = 12;
    /** ソートKEY(稼働時間) */
    public static final int SORT_KADOHOURS = 13;
    /** ソートKEY(残業日数) */
    public static final int SORT_ZANDAYS = 14;
    /** ソートKEY(残業時間) */
    public static final int SORT_ZANHOURS = 15;
    /** ソートKEY(深夜日数) */
    public static final int SORT_SINYADAYS = 16;
    /** ソートKEY(深夜時間) */
    public static final int SORT_SINYAHOURS = 17;
    /** ソートKEY(遅刻) */
    public static final int SORT_CHIKOKU = 18;
    /** ソートKEY(早退) */
    public static final int SORT_SOUTAI = 19;
    /** ソートKEY(休出日数) */
    public static final int SORT_KYUDEDAYS = 25;
    /** ソートKEY(休出時間) */
    public static final int SORT_KYUDEHOURS = 26;
    /** ソートKEY(休日) */
    public static final int SORT_HOLIDAY = 28;
    /** ソートKEY(有休日数) */
    public static final int SORT_YUKYUDAYS = 29;
    /** ソートKEY(有休使用日数) */
    public static final int SORT_YUKYU_USEDAYS = 30;
    /** ソートKEY(有休消化率) */
    public static final int SORT_YUKYU_USEPERCENT = 31;

    /** 画面表示区分:初期表示 */
    public static final int DSP_INIT = 0;
    /** 画面表示区分:再表示 */
    public static final int DSP_RE = 1;

    /** 追加 */
    public static final int CMDMODE_ADD = 0;
    /** 編集 */
    public static final int CMDMODE_EDIT = 1;
    /** 削除 */
    public static final int CMDMODE_DELETE = 2;

    /** デフォルト時間帯SID */
    public static final int DEFAULT_TIMEZONE_SID = 0;

    /** 時間帯情報使用者：デフォルトではない */
    public static final int USED_TIMEZONE_NOT_DEFAULT = 0;
    /** 時間帯情報使用者：デフォルトで使用 */
    public static final int USED_TIMEZONE_DEFAULT = 1;
    /** 時間帯情報使用者：デフォルトかつオンリー */
    public static final int USED_TIMEZONE_DEFAULT_ONLY = 2;

    /** 時間帯設定:休日区分 休日として扱わない */
    public static final int HOLKBN_WEEKDAY = 0;
    /** 時間帯設定:休日区分 休日として扱う */
    public static final int HOLKBN_HOLDAY = 1;

    /** ソート 昇順 */
    public static final int ORDER_KEY_ASC = 0;
    /** ソート 降順 */
    public static final int ORDER_KEY_DESC = 1;

    /** 表示する*/
    public static final String DSP_CHART = "0";
    /** 表示しない*/
    public static final String DSP_CHART_NON = "1";

    /** 複数編集フラグ 複数*/
    public static final boolean PLURAL_FLG_PLURAL = true;
    /** 複数編集フラグ 1日のみ*/
    public static final boolean PLURAL_FLG_SINGLE = false;

    /** メイン画面での打刻に在席管理を連動させない */
    public static final int ZAISEKI_OFF = 0;
    /** メイン画面での打刻に在席管理を連動させる */
    public static final int ZAISEKI_ON = 1;

    /** 勤務表出力形式 Excel */
    public static final int KINMU_EXCEL = 0;
    /** 勤務表出力形式 PDF */
    public static final int KINMU_PDF = 1;

    /** タイムカード打刻ボタン表示フラグ(非表示) */
    public static final int DAKOKUBTN_DSP_NOT = 0;
    /** タイムカード打刻ボタン表示フラグ(表示) */
    public static final int DAKOKUBTN_DSP_OK = 1;

    /** タイムカード打刻ボタン押下フラグ(未押下) */
    public static final int DAKOKUBTN_PUSH_NOT = 0;
    /** タイムカード打刻ボタン押下フラグ(押下) */
    public static final int DAKOKUBTN_PUSH = 1;

    /** タイムカード打刻区分（業務開始） */
    public static final int DAKOKUKBN_START = 0;
    /** タイムカード打刻区分（業務終了） */
    public static final int DAKOKUKBN_END = 1;
    /** タイムカード打刻区分（未設定） */
    public static final int DAKOKUKBN_NONE = -1;


    /** ログ出力種別判別フラグ なし */
    public static final int TCD_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ PDF */
    public static final int TCD_LOG_FLG_PDF = 0;

    /** 不正タイムカードの判定時間 */
    public static final int TOMORROW_TEN_AM = 10;

    /** 時間帯変更方法区分 デフォルト時間帯変更 */
    public static final int CHANGE_USER_DEF_TIMEZONE = 0;
    /** 時間帯変更方法区分 使用時間帯変更 */
    public static final int CHANGE_USER_USE_TIMEZONE = 1;

    /** 使用区分　使用可 */
    public static final int USE_KBN_OK = 0;
    /** 使用区分　使用不可 */
    public static final int USE_KBN_NG = 1;

    /** 時間帯使用区分　使用可 */
    public static final int TIMEZONE_USE_KBN_OK = 1;
    /** 時間帯使用区分　使用不可 */
    public static final int TIMEZONE_USE_KBN_NG = 0;

    /** 休日内容使用区分　使用可 */
    public static final int HOLIDAY_CONTENT_KBN_OK = 1;
    /** 休日内容使用区分　使用不可 */
    public static final int HOLIDAY_CONTENT_KBN_NG = 0;

    /** CSVインポートフラグ 可能 */
    public static final int IMPORT_FLG_OK = 0;
    /** CSVインポートフラグ 不可 */
    public static final int IMPORT_FLG_NG = 1;

    /** CSVインポート 項目数 */
    public static final int IMP_VALUE_SIZE = 14;

    /** 有休日数 CSVインポート 項目数 */
    public static final int IMP_YUKYU_SIZE = 3;

    /** 開始日付区分 */
    public static final int FROM_DATE_KBN = 0;
    /** 終了日付区分 */
    public static final int TO_DATE_KBN = 1;

    /** 有休日数情報 新規登録モード */
    public static final int YUKYU_MODE_INSERT = 0;
    /** 有休日数情報 新規登録モード */
    public static final int YUKYU_MODE_UPDATE = 1;

    /** 有休日数情報 最大取得件数 */
    public static final int MAX_YUKYU_GET_COUNT = 30;

    /** 有休日数インポートサンプルCSV ファイル名 */
    public static final String SAMPLE_YUKYU_CSV_NAME = "yukyuCsvSample.xlsx";
    /** インポートサンプル ファイル名 */
    public static final String SAMPLE_TCD_CSV_NAME = "importSample.xlsx";
    /** 勤務表出力フォーマット変更方法 ファイル名 */
    public static final String SAMPLE_INSTRUCTIONS = "howtoFormat.pdf";

    /** 添付ファイル  一時保管 ファイル名 */
    public static final String TCD_TEMP_FORMAT_EDIT = "formatEdit";
    /** フォーマットファイル名 ファイル名 */
    public static final String TCD_TEMP_FORMAT_NAME = "format.xlsx";
}
