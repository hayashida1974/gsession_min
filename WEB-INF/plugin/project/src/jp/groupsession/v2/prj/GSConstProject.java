package jp.groupsession.v2.prj;

/**
 * <p>プロジェクト管理 管理定数一覧
 * @author JTS
 */
public class GSConstProject {

    /** プラグインID */
    public static final String PLUGIN_ID_PROJECT = "project";
    /** プラグインID */
    public static final String PLUGIN_ID_SMAIL = "smail";
    /** プラグインID */
    public static final String PLUGIN_ID_CIRCULAR = "circular";
    /** プラグインID */
    public static final String PLUGIN_ID_ADDRESS = "address";
    /** プラグインID */
    public static final String PLUGIN_ID_ZAISEKI = "zaiseki";
    /** 採番区分 SID プロジェクト管理 */
    public static final String SBNSID_PROJECT = PLUGIN_ID_PROJECT;
    /** 採番区分 SID SUB プロジェクトSID */
    public static final String SBNSID_SUB_PROJECT = "prj";
    /** 採番区分 SID SUB プロジェクトテンプレートSID */
    public static final String SBNSID_SUB_PROJECT_TMP = "prt";
    /** 採番区分 SID SUB TODOSID */
    public static final String SBNSID_SUB_TODO = "todo";
    /** 採番区分 SID SUB 管理番号 */
    public static final String SBNSID_SUB_KANRI = "kanri";
    /** 採番区分 SID SUB 変更履歴SID */
    public static final String SBNSID_SUB_HISTORY = "history";
    /** 採番区分 SID SUB TODOコメントSID */
    public static final String SBNSID_SUB_COMMENT = "comment";

    /** モード：プロジェクトテンプレその他モード */
    public static final int MODE_TMP_OTHER = 0;
    /** モード：プロジェクトテンプレ個人モード */
    public static final int MODE_TMP_KOJIN = 1;
    /** モード：プロジェクトテンプレ共有モード */
    public static final int MODE_TMP_KYOYU = 2;
    /** モード：プロジェクトテンプレ選択モード */
    public static final int MODE_TMP_SELECT = 3;

    /** テンプレート区分 0:共有テンプレート */
    public static final int PRT_KBN_KYOYU = 0;
    /** テンプレート区分 1:個人テンプレート */
    public static final int PRT_KBN_KOJIN = 1;

    /** TODOモード */
    public static final String MODE_TODO = "0";
    /** プロジェクトモード */
    public static final String MODE_PROJECT = "1";

    /** 内部モード */
    public static final String MODE_NAIBU = "0";
    /** 外部モード */
    public static final String MODE_GAIBU = "1";

    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** ユーザ削除実行しない */
    public static final String USER_NO_DELETE = "0";
    /** ユーザ削除実行する */
    public static final String USER_DELETE = "1";

    /** プラグイン使用する */
    public static final int PLUGIN_USE = 0;
    /** プラグイン使用しない */
    public static final int PLUGIN_NOT_USE = 1;

//    /** 管理番号表示フォーマット */
//    public static final String KANRI_NO_FORMAT = "000";
    /** 管理番号表示フォーマット */
    public static final String KANRI_NO_FORMAT = "00000000";

    /** 処理モード 登録 */
    public static final String CMD_MODE_ADD = "0";
    /** 処理モード 更新 */
    public static final String CMD_MODE_EDIT = "1";

    /** プロジェクト編集権限 管理者のみ */
    public static final int PRJ_EDIT_KENGEN_ADM = 0;
    /** プロジェクト編集権限 設定無し */
    public static final int PRJ_EDIT_KENGEN_ALL = 1;

    /** TODO編集権限 所属メンバーのみ */
    public static final int TODO_EDIT_KENGEN_MEM = 0;
    /** TODO編集権限 プロジェクト管理者のみ */
    public static final int TODO_EDIT_KENGEN_ADM = 1;
    /** TODO編集権限 権限設定無し */
    public static final int TODO_EDIT_KENGEN_ALL = 2;

    /** 警告開始区分 無し */
    public static final int KEIKOKU_NO = -1;
    /** 警告開始区分 一ヶ月前 */
    public static final int KEIKOKU_BEF30 = 30;
    /** 警告開始区分 十日前 */
    public static final int KEIKOKU_BEF10 = 10;
    /** 警告開始区分 五日前 */
    public static final int KEIKOKU_BEF5 = 5;
    /** 警告開始区分 三日前 */
    public static final int KEIKOKU_BEF3 = 3;
    /** 警告開始区分 一日前 */
    public static final int KEIKOKU_BEF1 = 1;

    /** 警告区分 警告無し */
    public static final int KEIKOKU_NASI = 0;
    /** 警告区分 警告有り */
    public static final int KEIKOKU_ARI = 1;

    /** 進捗率 100%(固定) */
    public static final int RATE_MAX = 100;
    /** 進捗率 0%(固定) */
    public static final int RATE_MIN = 0;

    /** 状態SID 100%(固定) */
    public static final int STATUS_100 = 2;
    /** 状態SID 0%(固定) */
    public static final int STATUS_0 = 1;
    /** 状態SID マイプロジェクト */
    public static final int STATUS_MYPRJ = 0;

    /** 重要度 低 */
    public static final int WEIGHT_KBN_LOW = 1;
    /** 重要度 中 */
    public static final int WEIGHT_KBN_MIDDLE = 2;
    /** 重要度 高 */
    public static final int WEIGHT_KBN_HIGH = 3;

    /** 公開区分 公開 */
    public static final int KBN_KOUKAI_ENABLED = 0;
    /** 公開区分 非公開 */
    public static final int KBN_KOUKAI_DISABLED = 1;

    /** マイプロジェクト区分 通常プロジェクト */
    public static final int KBN_MY_PRJ_DEF = 0;
    /** マイプロジェクト区分 マイプロジェクト */
    public static final int KBN_MY_PRJ_MY = 1;

    /** TODOカテゴリ 全て */
    public static final String TODO_CATEGORY_ALL = "0";
    /** TODOカテゴリ なし */
    public static final String TODO_CATEGORY_NO = "-1";

    /** ソートキーTODOモード(プロジェクト名) */
    public static final int SORT_PROJECT_TITLE = 0;
    /** ソートキーTODOモード(管理番号) */
    public static final int SORT_TODO_NO = 1;
    /** ソートキーTODOモード(TODOタイトル) */
    public static final int SORT_TODO_TITLE = 2;
    /** ソートキーTODOモード(重要度) */
    public static final int SORT_TODO_WEIGHT = 3;
    /** ソートキーTODOモード(状態) */
    public static final int SORT_TODO_STATUS = 4;
    /** ソートキーTODOモード(開始予定) */
    public static final int SORT_TODO_START_PLAN = 5;
    /** ソートキーTODOモード(期限) */
    public static final int SORT_TODO_LIMIT_PLAN = 6;

    /** ソートキープロジェクトモード(ID) */
    public static final int SORT_PRJECT_ID = 10;
    /** ソートキープロジェクトモード(プロジェクト名称) */
    public static final int SORT_PROJECT_NAME = 11;
    /** ソートキープロジェクトモード(状態) */
    public static final int SORT_PRJECT_STATUS = 12;
    /** ソートキープロジェクトモード(開始) */
    public static final int SORT_PRJECT_START = 13;
    /** ソートキープロジェクトモード(終了) */
    public static final int SORT_PRJECT_END = 14;
    /** ソートキープロジェクトモード(予算) */
    public static final int SORT_PRJECT_YOSAN = 15;

    /** ソートキープロジェクトモード(ID) */
    public static final String SORT_STR_PRJECT_ID = "ID";

    /** プロジェクトメンバー管理者区分 権限無し */
    public static final int KBN_POWER_NORMAL = 0;
    /** プロジェクトメンバー管理者区分 権限有り */
    public static final int KBN_POWER_ADMIN = 1;

    /** プロジェクト参加ユーザ区分 自社 */
    public static final int KBN_PROJECT_MEMBER_INNER = 0;
    /** プロジェクト参加ユーザ区分 外部 */
    public static final int KBN_PROJECT_MEMBER_OUTER = 9;

    /** 遷移元画面ID prj010(ダッシュボード) */
    public static final String SCR_ID_PRJ010 = "prj010";
    /** 遷移元画面ID prj020(プロジェクト登録編集) */
    public static final String SCR_ID_PRJ020 = "prj020";
    /** 遷移元画面ID prj040(プロジェクト詳細検索) */
    public static final String SCR_ID_PRJ040 = "prj040";
    /** 遷移元画面ID prj030(プロジェクトメイン) */
    public static final String SCR_ID_PRJ030 = "prj030";
    /** 遷移元画面ID prj070(TODO詳細検索) */
    public static final String SCR_ID_PRJ070 = "prj070";
    /** 遷移元画面ID prj060(TODO参照) */
    public static final String SCR_ID_PRJ060 = "prj060";
    /** 遷移元画面ID prj020(プロジェクトテンプレート登録編集) */
    public static final String SCR_ID_PRJ140 = "prj140";
    /** 遷移元画面ID main(メインメニュー) */
    public static final String SCR_ID_MAIN = "main";

    /** 遷移先:ダッシュボードへ */
    public static final String SCR_INDEX = "prjIndex";
    /** 遷移先:プロジェクト検索へ */
    public static final String SCR_PRJ_SEARCH = "prjSearch";
    /** 遷移先:プロジェクトメインへ */
    public static final String SCR_PRJ_MAIN = "prjMain";
    /** 遷移先:TODO詳細検索へ */
    public static final String SCR_TODO_SEARCH = "todoSearch";
    /** 遷移先:TODO参照へ */
    public static final String SCR_TODO_VIEW = "todoView";
    /** 遷移先:メインメニューへ */
    public static final String SCR_MENU = "gf_main";
    /** 遷移先:回覧板新規作成へ */
    public static final String SCR_CIR_NEW = "gf_cirNew";
    /** 遷移先:ショートメール作成へ */
    public static final String SCR_SML_NEW = "gf_smlNew";

    /** 検索フラグ 検索しない */
    public static final int SEARCH_FLG_NO = 0;
    /** 検索フラグ 検索実行 */
    public static final int SEARCH_FLG_OK = 1;

    /** メッセージ TODOデータ */
    public static final String MSG_TODO = "TODO";

    /** MAX桁数 テンプレート名 */
    public static final int MAX_LENGTH_TMP_NAME = 50;
    /** MAX桁数 プロジェクトID */
    public static final int MAX_LENGTH_PRJ_ID = 20;
    /** MAX桁数 プロジェクト名 */
    public static final int MAX_LENGTH_PRJ_NAME = 70;
    /** MAX桁数 プロジェクト略称 */
    public static final int MAX_LENGTH_PRJ_SHORT_NAME = 20;
    /** MAX桁数 予算 */
    public static final int MAX_LENGTH_YOSAN = 14;
    /** MAX桁数 目標・目的 */
    public static final int MAX_LENGTH_TARGET = 300;
    /** MAX桁数 内容 */
    public static final int MAX_LENGTH_CONTENT = 1000;
    /** MAX桁数 備考 */
    public static final int MAX_LENGTH_BIKO = 1000;
    /** MAX桁数 進捗率 */
    public static final int MAX_LENGTH_RATE = 2;
    /** MAX桁数 状態名称 */
    public static final int MAX_LENGTH_STATUS_NAME = 20;
    /** MAX桁数 カテゴリ名称 */
    public static final int MAX_LENGTH_CATE_NAME = 20;
    /** MAX桁数 TODOタイトル */
    public static final int MAX_LENGTH_TODO_TITLE = 100;
    /** MAX桁数 TODOコメント */
    public static final int MAX_LENGTH_TODO_CMT = 1000;
    /** MAX桁数 状態変更理由 */
    public static final int MAX_LENGTH_STATUS_REASON = 500;
    /** MAX桁数 工数(整数) */
    public static final int MAX_LENGTH_KOSU_SEISU = 3;
    /** MAX桁数 工数(小数) */
    public static final int MAX_LENGTH_KOSU_SYOSU = 1;
    /** MAX桁数 プロジェクトメンバーID */
    public static final int MAX_LENGTH_PRJ_MEM_ID = 20;
    /** MAX桁数 ディレクトリ名 */
    public static final int MAX_LENGTH_DIRECTORY_NAME = 50;

    /** TODO_CSVファイル名 */
    public static final String TODO_CSV_FILENAME = "prjTodoImport.xls";
    /** プロジェクト状態保存ファイル名 */
    public static final String SAVE_FILENAME = "prjStatusfile";
    /** プロジェクト状態保存ファイル名(保存用) */
    public static final String SAVE_FILENAME_OLD = "prjStatusfileOld";

    /** プロジェクト(全て) */
    public static final int PROJECT_ALL = 0;

    /** プロジェクトTODO 日付(今日) */
    public static final int DATE_TODAY = 1;
    /** プロジェクトTODO 日付(今日以前) */
    public static final int DATE_THE_PAST = 2;
    /** プロジェクトTODO 日付(今日以降) */
    public static final int DATE_THE_FUTURE = 3;
    /** プロジェクトTODO 日付(未入力) */
    public static final int DATE_NOT_INPUT = 4;
    /** プロジェクトTODO 日付(全て) */
    public static final int DATE_ALL = 0;

    /** プロジェクトTODO 状態(全て) */
    public static final int STATUS_ALL = 0;
    /** プロジェクトTODO 状態(0%) */
    public static final int STATUS_MIKAN = -1;
    /** プロジェクトTODO 状態(100%) */
    public static final int STATUS_SINKO = -2;
    /** プロジェクトTODO 状態(100%) */
    public static final int STATUS_KANRYO = -3;
    /** プロジェクトTODO 状態(未完了) */
    public static final int STATUS_YOTEI_AND_MIKAN = -4;

    /** プロジェクトTODO メンバー(全員) */
    public static final int MEMBER_ALL = 0;
    /** プロジェクトTODO メンバー(担当なし) */
    public static final int MEMBER_NOTHING = -100;

    /** 参加プロジェクト 未完 */
    public static final int PRJ_MEMBER_NOT_END = 1;
    /** 参加プロジェクト 完了 */
    public static final int PRJ_MEMBER_END = 2;
    /** 参加プロジェクト 全て */
    public static final int PRJ_MEMBER_ALL = 3;

    /** 公開プロジェクト 未完 */
    public static final int PRJ_OPEN_NOT_END = 4;
    /** 公開プロジェクト 完了 */
    public static final int PRJ_OPEN_END = 5;
    /** 公開プロジェクト 全て */
    public static final int PRJ_OPEN_ALL = 6;

    /** 全ての未完プロジェクト (システム管理者限定) */
    public static final int PRJ_NOT_END_ALL = 7;
    /** 全ての完了プロジェクト (システム管理者限定) */
    public static final int PRJ_END_ALL = 8;
    /** 全てのプロジェクト (システム管理者限定) */
    public static final int PRJ_ALL = 9;

    /** インポート方法 追加インポート */
    public static final int PRJ_IMP_MEANS_ADD = 1;
    /** インポート方法 リセットインポート */
    public static final int PRJ_IMP_MEANS_RESET = 2;

    /** ショートメール通知区分 0:自由に設定可 */
    public static final int TODO_MAIL_FREE = 0;
    /** ショートメール通知区分 1:必ず管理者へ通知する */
    public static final int TODO_MAIL_SEND_ADMIN = 1;

    /** ショートメール通知 無し */
    public static final int NOT_SEND = 0;
    /** ショートメール通知 全メンバー */
    public static final int SEND_ALL_MEMBER = 1;
    /** ショートメール通知 担当者 */
    public static final int SEND_TANTO = 2;
    /** ショートメール通知 プロジェクトリーダー */
    public static final int SEND_LEADER = 3;
    /** ショートメール通知 プロジェクトリーダー + 担当者 */
    public static final int SEND_LEADER_AND_TANTO = 4;

    /** ディレクトリ区分 0=フォルダ */
    public static final int DIRECTORY_FOLDER = 0;
    /** ディレクトリ区分 1=ファイル */
    public static final int DIRECTORY_FILE = 1;

    /** ディレクトリ階層 0=ルート */
    public static final int DIRECTORY_LEVEL_0 = 0;
    /** ディレクトリ階層 1=1階層目 */
    public static final int DIRECTORY_LEVEL_1 = 1;
    /** ディレクトリ階層 2=2階層目 */
    public static final int DIRECTORY_LEVEL_2 = 2;
    /** ディレクトリ階層 3=3階層目 */
    public static final int DIRECTORY_LEVEL_3 = 3;
    /** ディレクトリ階層 4=4階層目 */
    public static final int DIRECTORY_LEVEL_4 = 4;
    /** ディレクトリ階層 5=5階層目 */
    public static final int DIRECTORY_LEVEL_5 = 5;
    /** ディレクトリ階層 6=6階層目 */
    public static final int DIRECTORY_LEVEL_6 = 6;

    /** ディレクトリ未選択時 */
    public static final String DIRECTORY_NOT_SELECT = "-1";
    /** ルートディレクトリSID */
    public static final int DIRECTORY_ROOT = 0;
    /** ルートディレクトリ名称 */
    public static final String DIRECTORY_ROOT_NAME = "ROOT";

    /** ソートキー ディレクトリ一覧(ディレクトリ名) */
    public static final int SORT_DIRECTORY_NAME = 0;
    /** ソートキー ディレクトリ一覧(ファイルサイズ) */
    public static final int SORT_DIRECTORY_SIZE = 1;
    /** ソートキー ディレクトリ一覧(更新日時) */
    public static final int SORT_UPDATE_DATE = 2;
    /** ソートキー ディレクトリ一覧(更新者) */
    public static final int SORT_UPDATE_USER = 3;

    /** TODO作成時 処理モード TODO作成権限があるプロジェクト全て */
    public static final int PRJ_KBN_ADMIN = 0;
    /** TODO作成時 処理モード 参加していてTODO作成権限があるプロジェクト */
    public static final int PRJ_KBN_PARTICIPATION = 1;

    /** キーワード検索区分 (and) */
    public static final int KEY_WORD_KBN_AND = 0;
    /** キーワード検索区分 (or) */
    public static final int KEY_WORD_KBN_OR = 1;

    /** 検索対象 タイトル */
    public static final int SEARCH_TARGET_TITLE = 1;
    /** 検索対象 内容 */
    public static final int SEARCH_TARGET_NAIYOU = 2;

    /** プロジェクトアイコン添付ファイル フォルダ名 */
    public static final String TEMP_ICN_PRJ = "Ico";
    /** プロジェクトステータス フォルダ名 */
    public static final String TEMP_STATUS_PRJ = "Status";

    /** ダッシュボード初期表示画面  TODO */
    public static final int DSP_TODO = 0;
    /** ダッシュボード初期表示画面  プロジェクト */
    public static final int DSP_PROJECT = 1;

    /** 遷移先URL TODO */
    public static final String DSP_TODO_URL = "../project/prj010.do";
    /** 遷移先URL プロジェクト */
    public static final String DSP_PROJECT_URL = "../project/prj010.do?CMD=tabProjectClick";

    /** プロジェクトポートレット TODO状態内訳選択画面ID */
    public static final String SCREENID_PRJPTL020 = "prjptl020";

    /** スケジュール画面TODO 表示する   */
    public static final int DSP_TODO_SHOW = 0;
    /** スケジュール画面TODO 表示しない */
    public static final int DSP_TODO_NOT_SHOW = 1;

    /** TODO登録画面 入力区分 簡易入力   */
    public static final int DSP_TODO_EASY = 0;
    /** TODO登録画面 入力区分 詳細入力   */
    public static final int DSP_TODO_DETAIL = 1;

    /** TODO詳細検索画面 プロジェクト 全て */
    public static final int PROJECT_ALL_SELECT = -1;

    /** グループコンボに設定するテキスト ユーザ指定のVALUE */
    public static final String GROUP_COMBO_VALUE_USER = "-8";
    /** メンバー グループの頭文字 */
    public static final String MEMBER_GROUP_HEADSTR = "G";
}