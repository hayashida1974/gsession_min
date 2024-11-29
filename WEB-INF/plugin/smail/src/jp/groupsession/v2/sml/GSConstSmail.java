package jp.groupsession.v2.sml;


/**
 * <br>[機  能] 施設予約プラグインで共通使用する定数クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstSmail {

    /** プラグインID */
    public static final String PLUGIN_ID_SMAIL = "smail";
    /** プラグイン名 */
    public static final String PLUGIN_NAME_SMAIL = "ショートメール";

    /** 削除処理時にどのメールかを判定するキーの、SIDフォーマット */
    public static final String MAIL_KEY_FORMAT = "0000000000";
    /** 在席管理のプラグインID */
    public static final String PLUGIN_ID_ZAISEKI = "zaiseki";

    //採番
    /** 採番SID(メール・ひな形SID) */
    public static final String SAIBAN_SML_SID = "smail";
    /** 採番SIDサブ(メールSID) */
    public static final String SAIBAN_SUB_MAIL_SID = "mail";
    /** 採番SIDサブ(雛形SID) */
    public static final String SAIBAN_SUB_HINA_SID = "hina";
    /** 採番SIDサブ(アカウントSID) */
    public static final String SBNSID_SUB_ACCOUNT = "account";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_LABEL = "label";
    /** 採番IDサブ フィルターSID */
    public static final String SBNSID_SUB_FILTER = "filter";
    /** 採番IDサブ 送信制限管理SID */
    public static final String SBNSID_SUB_SBC = "sbc";

    /** リスト表示画面での1ページ最大表示件数(規定値) */
    public static final int MAX_RECORD_COUNT = 10;
    /** リスト表示画面での1ページ最大表示件数(最大値) */
    public static final int MAX_RECORD_COUNT_MAX = 50;
    /** メッセージ一覧表示モード (受信) */
    public static final String TAB_DSP_MODE_JUSIN = "0";
    /** メッセージ一覧表示モード (送信) */
    public static final String TAB_DSP_MODE_SOSIN = "1";
    /** メッセージ一覧表示モード (草稿) */
    public static final String TAB_DSP_MODE_SOKO = "2";
    /** メッセージ一覧表示モード (受信(※TOP画面より)) */
    public static final String TAB_DSP_MODE_JUSIN_FROM_TOP = "3";
    /** メッセージ一覧表示モード (ゴミ箱) */
    public static final String TAB_DSP_MODE_GOMIBAKO = "4";
    /** メッセージ一覧表示モード (ラベル) */
    public static final String TAB_DSP_MODE_LABEL = "5";

    /** メッセージ作成モード (新規) */
    public static final String MSG_CREATE_MODE_NEW = "0";
    /** メッセージ作成モード (返信) */
    public static final String MSG_CREATE_MODE_HENSIN = "1";
    /** メッセージ作成モード (全返信) */
    public static final String MSG_CREATE_MODE_ZENHENSIN = "2";
    /** メッセージ作成モード (転送) */
    public static final String MSG_CREATE_MODE_TENSO = "3";
    /** メッセージ作成モード (草稿から作成) */
    public static final String MSG_CREATE_MODE_SOKO = "4";
    /** メッセージ作成モード (スケジュール日間から) */
    public static final String MSG_CREATE_MODE_SC_NIKKAN = "5";
    /** メッセージ作成モード (スケジュール週間から) */
    public static final String MSG_CREATE_MODE_SC_SYUKAN = "6";
    /** メッセージ作成モード (在席管理から) */
    public static final String MSG_CREATE_MODE_ZAISEKI = "8";
    /** メッセージ作成モード (メインから) */
    public static final String MSG_CREATE_MODE_MAIN = "10";
    /** メッセージ作成モード (複写して新規作成) */
    public static final String MSG_CREATE_MODE_COPY = "11";
    /** メッセージ作成モード (日報日間から) */
    public static final String MSG_CREATE_MODE_NTP_NIKKAN = "12";
    /** メッセージ作成モード (日報週間から) */
    public static final String MSG_CREATE_MODE_NTP_SYUKAN = "13";

    /** ショートメール内容確認 前へボタンモード */
    public static final int MSG_PREV = 0;
    /** ショートメール内容確認 次へボタンモード */
    public static final int MSG_NEXT = 1;

    /** オーダーキー (昇順) */
    public static final int ORDER_KEY_ASC = 0;
    /** オーダーキー (降順) */
    public static final int ORDER_KEY_DESC = 1;

    /** メッセージ開封区分 (未開封) */
    public static final int OPKBN_UNOPENED = 0;
    /** メッセージ開封区分 (開封済) */
    public static final int OPKBN_OPENED = 1;
    /** メッセージ転送区分 (転送しない) */
    public static final int FWKBN_NO = 0;
    /** メッセージ転送区分 (転送済) */
    public static final int FWKBN_OK = 1;

    /** ショートメール状態区分 登録 */
    public static final int SML_JTKBN_TOROKU = 0;
    /** ショートメール状態区分 ゴミ箱 */
    public static final int SML_JTKBN_GOMIBAKO = 1;
    /** ショートメール状態区分 削除 */
    public static final int SML_JTKBN_DELETE = 9;

    /** メッセージ一覧ソートカラム (マーク) */
    public static final int MSG_SORT_KEY_MARK = 0;
    /** メッセージ一覧ソートカラム (件名) */
    public static final int MSG_SORT_KEY_TITLE = 1;
    /** メッセージ一覧ソートカラム (差出人) */
    public static final int MSG_SORT_KEY_NAME = 2;
    /** メッセージ一覧ソートカラム (日時) */
    public static final int MSG_SORT_KEY_DATE = 3;
    /** メッセージ一覧ソートカラム (宛先) */
    public static final int MSG_SORT_KEY_ATESAKI = 4;
    /** メッセージ一覧ソートカラム (サイズ) */
    public static final int MSG_SORT_KEY_SIZE = 5;

    /** 検索対象 件名 */
    public static final int SEARCH_TARGET_TITLE = 1;
    /** 検索対象 本文 */
    public static final int SEARCH_TARGET_HONBUN = 2;

    /** キーワード検索区分 (and) */
    public static final int KEY_WORD_KBN_AND = 0;
    /** キーワード検索区分 (or) */
    public static final int KEY_WORD_KBN_OR = 1;

    /** 雛形一覧ソートカラム (雛形名称) */
    public static final int HINA_SORT_KEY_NAME = 0;
    /** 雛形一覧ソートカラム (件名) */
    public static final int HINA_SORT_KEY_TITLE = 1;
    /** 雛形一覧ソートカラム (マーク) */
    public static final int HINA_SORT_KEY_MARK = 2;

    /** マークコード (全て) */
    public static final int MARK_KBN_ALL = -1;
    /** マークコード (マーク無し) */
    public static final int MARK_KBN_NONE = 0;
    /** マークコード (電話) */
    public static final int MARK_KBN_TEL = 4;
    /** マークコード (重要) */
    public static final int MARK_KBN_INP = 8;

    /** マークコード (表情 :スマイル) */
    public static final int MARK_KBN_SMAILY = 101;
    /** マークコード (表情 :悩み) */
    public static final int MARK_KBN_WORRY = 102;
    /** マークコード (表情 :怒り) */
    public static final int MARK_KBN_ANGRY = 103;
    /** マークコード (表情 :悲しみ) */
    public static final int MARK_KBN_SADRY = 104;
    /** マークコード (ビール) */
    public static final int MARK_KBN_BEER = 201;
    /** マークコード (ハート) */
    public static final int MARK_KBN_HART = 202;
    /** マークコード (疲れ) */
    public static final int MARK_KBN_ZASETSU = 203;

    /** 検索未実行 */
    public static final int SEARCH_EXECUTE_FALSE = 0;
    /** 検索実行 */
    public static final int SEARCH_EXECUTE_TRUE = 1;

    /** ショートメール検索１ページ当りの表示件数初期値 */
    public static final int SEARCH_PAGE_COUNT_DEFOULT = 10;
    /** ショートメール検索画面フラグ */
    public static final String SEARCH_BACK_ON = "1";

    //転送設定
    /** 転送設定しない  */
    public static final int MAIL_FORWARD_NO_SET = 0;
    /** 転送設定する  */
    public static final int MAIL_FORWARD_SET = 1;


    /** 転送設定 (転送を許可しない) */
    public static final int MAIL_FORWARD_NG = 0;
    /** 転送設定 (転送を許可する) */
    public static final int MAIL_FORWARD_OK = 1;
    /** 転送設定 (不在時のみ転送する) */
    public static final int MAIL_FORWARD_FUZAI_OK = 2;
    /** SMTPサーバURL最大桁数 */
    public static final int MAX_LENGTH_SMTP = 200;
    /** SMTPサーバポート番号最大桁数 */
    public static final int MAX_LENGTH_SMTP_PORT = 5;
    /** メールアドレス最大桁数 */
    public static final int MAX_LENGTH_MAIL = 50;
    /** SMTPサーバユーザ最大桁数 */
    public static final int MAX_LENGTH_USER = 100;
    /** SMTPサーバパスワード最大桁数 */
    public static final int MAX_LENGTH_PASS = 100;
    /** 転送先制限区分 制限しない */
    public static final String MAIL_FORWARD_NO_LIMIT = "0";
    /** 転送先制限区分 制限する */
    public static final String MAIL_FORWARD_LIMIT = "1";

    /** 自動リロード時間 10分 */
    public static final int MAIL_RELOAD_10MIN = 600000;

    /** プラグイン使用する */
    public static final int PLUGIN_USE = 0;
    /** プラグイン使用しない */
    public static final int PLUGIN_NOT_USE = 1;

    /** SMTPポート番号 最大値 */
    public static final int MAX_NUMBER_SMTP_PORTNUM = 65535;

    /** 不正転送先メールアドレスチェックボタン ON */
    public static final boolean FW_CHECK_ON = true;
    /** 不正転送先メールアドレスチェックボタン OFF */
    public static final boolean FW_CHECK_OFF = false;

    /** SSL使用 */
    public static final int SSL_USE = 1;
    /** SSL未使用 */
    public static final int SSL_NOTUSE = 0;

    /** 不正転送先チェックモード デフォルト */
    public static final int FW_CHECK_MODE_DF = 0;
    /** 不正転送先チェックモード デフォルト */
    public static final int FW_CHECK_MODE_ZAISEKI = 1;
    /** 不正転送先チェックモード デフォルト */
    public static final int FW_CHECK_MODE_HUZAI = 2;
    /** 不正転送先チェックモード デフォルト */
    public static final int FW_CHECK_MODE_OTHER = 3;

    /** ショートメール削除設定 0:削除しない */
    public static final int SML_AUTO_DEL_NO = 0;
    /** ショートメール削除設定 1:一定期間が経過したメールを削除する */
    public static final int SML_AUTO_DEL_LIMIT = 1;
    /** ショートメール自動削除設定 2:ログアウト時に全て削除する */
    public static final int SML_AUTO_DEL_LOGOUT = 2;

    /** ショートメール自動削除設定 設定ユーザ区分 0:管理者 */
    public static final int SML_ADEL_USR_KBN_ADM = 0;
    /** ショートメール自動削除設定 設定ユーザ区分 1:通常ユーザ */
    public static final int SML_ADEL_USR_KBN_USER = 1;

    /** ショートメール自動削除区分 0:管理者が設定する */
    public static final int SML_DEL_KBN_ADM_SETTING = 0;
    /** ショートメール自動削除区分 0:各ユーザが設定する */
    public static final int SML_DEL_KBN_USER_SETTING = 1;

    /** ひな形モード 共通 */
    public static final int HINA_KBN_CMN = 0;
    /** ひな形モード 個人 */
    public static final int HINA_KBN_PRI = 1;

    /** エラー区分 */
    public static final int ERROR_KBN = -1;

    /** 送信区分 全て */
    public static final int SML_SEND_KBN_ALL = -1;
    /** 送信区分 宛先 */
    public static final int SML_SEND_KBN_ATESAKI = 0;
    /** 送信区分 CC */
    public static final int SML_SEND_KBN_CC = 1;
    /** 送信区分 BCC */
    public static final int SML_SEND_KBN_BCC = 2;

    /** メイン表示 区分(デフォルト 未読のみ) */
    public static final int SML_MAIN_KBN_MIDOKU = 0;
    /** メイン表示 表示件数(デフォルト 10件) */
    public static final int SML_MAIN_CNT_10 = 10;
    /** メイン表示 表示順(デフォルト 降順) */
    public static final int SML_MAIN_SORT_KOUJYUN = 0;

    /** ショートメール一覧 写真表示設定(表示) */
    public static final int SML_PHOTO_DSP_DSP = 0;
    /** ショートメール一覧 写真表示設定(非表示) */
    public static final int SML_PHOTO_DSP_NOTDSP = 1;

    /** ショートメール 日次バッチで一度に削除するメール件数 */
    public static final int SML_BATCH_DELETE_COUNT = 100;

    /** ショートメール 返信区分 未返信 */
    public static final int SML_NO_REPLY = 0;
    /** ショートメール 返信区分 返信 */
    public static final int SML_REPLY = 1;

    /** ショートメール 転送区分 未転送 */
    public static final int SML_NO_FW = 0;
    /** ショートメール 転送区分 転送 */
    public static final int SML_FW = 1;

    /** ショートメール 添付画像表示区分 表示 */
    public static final int SML_IMAGE_TEMP_DSP = 0;
    /** ショートメール 添付画像表示区分 非表示 */
    public static final int SML_IMAGE_TEMP_NOT_DSP = 1;

    /** アカウント情報 アカウント種別 通常 */
    public static final int SAC_TYPE_NORMAL = 0;
    /** アカウント情報 アカウント種別 共通(グループ) */
    public static final int SAC_TYPE_GROUP = 1;
    /** アカウント情報 アカウント種別 共通(ユーザ) */
    public static final int SAC_TYPE_USER = 2;

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

    /** ソートキー 件名 */
    public static final int SKEY_TITLE = 0;
    /** ソートキー 差出人 */
    public static final int SKEY_FROM = 1;
    /** ソートキー 宛先 */
    public static final int SKEY_TO = 2;
    /** ソートキー 日時 */
    public static final int SKEY_DATE = 3;

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
    public static final int SAC_JKBN_NORMAL = 0;
    /** アカウント 状態区分 削除 */
    public static final int SAC_JKBN_DELETE = 1;

    /** ラベル区分 アカウント固有 */
    public static final int LABELTYPE_ONES = 0;
    /** ラベル区分 全てのアカウント */
    public static final int LABELTYPE_ALL = 1;

    /** 1ページ表示件数 10件 */
    public static final int LIMIT_DSP = 10;
    /** アカウント 1ページ表示件数 30件 */
    public static final int LIMIT_DSP_ACCOUNT = 30;
    /** 送受信ログ管理 1ページ表示件数 50件 */
    public static final int LIMIT_DSP_MAILLOG = 50;

    /** 検索キーワード MAX文字数 */
    public static final int MAXLEN_SEARCH_KEYWORD = 100;
    /** 条件 MAX文字数 */
    public static final int MAXLEN_CONDITION_KEYWORD = 256;
    /** アカウント MAX文字数 */
    public static final int MAXLEN_ACCOUNT = 100;
    /** アカウント アドレス　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_ADDRESS = 256;
    /** アカウント ディスク容量　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_DISK = 6;
    /** アカウント 備考　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_BIKO = 1000;
    /** 件名 MAX文字数 */
    public static final int MAXLEN_NAME = 100;
    /** ユーザIDMAX文字数 */
    public static final int MAXLEN_USERID = 20;
    /** ユーザIDMIN文字数 */
    public static final int MINLEN_USERID = 2;

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
    /** メッセージに表示するテキスト ディスク容量 */
    public static final String TEXT_DISK = "wml.87";
    /** メッセージに表示するテキスト 備考 */
    public static final String TEXT_BIKO = "cmn.memo";
    /** メッセージに表示するテキスト 使用者 グループ */
    public static final String TEXT_GROUP = "wml.188";
    /** メッセージに表示するテキスト 使用者ユーザ */
    public static final String TEXT_USER = "wml.189";
    /** メッセージに表示するテキスト 日付 */
    public static final String TEXT_DATE = "cmn.date2";
    /** メッセージに表示するテキスト 件名 */
    public static final String TEXT_NAME = "cmn.subject";
    /** メッセージに表示するテキスト ラベル */
    public static final String TEXT_SEL_LABEL = "cmn.label";
    /** メッセージに表示するテキスト ラベル */
    public static final String TEXT_MAIL_AUTO_RSV = "wml.152";
    /** メッセージに表示するテキスト 送信文字コード */
    public static final String TEXT_SEND_WORDCODE = "wml.wml040kn.01";
    /** メッセージに表示するテキスト 送信メール形式 */
    public static final String TEXT_SEND_TYPE = "cmn.format.";
    /** メッセージに表示するテキスト 使用者ユーザ */
    public static final String TEXT_USE_USER = "cmn.employer.user";
    /** メッセージに表示するテキスト 使用者グループ */
    public static final String TEXT_USE_GROUP = "cmn.employer.group";
    /** メッセージに表示するテキスト APOP */
    public static final String TEXT_APOP = "APOP";

    /** 処理区分 新規追加 */
    public static final int CMDMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int CMDMODE_EDIT = 1;

    /** 返信時署名表示 表示しない */
    public static final int SIGN_RECEIVE_NOT_DSP = 0;
    /** 返信時署名表示 表示する */
    public static final int SIGN_RECEIVE_DSP = 1;

    /** 署名位置 先頭 */
    public static final int SIGN_POINT_TOP = 0;
    /** 署名位置 文末 */
    public static final int SIGN_POINT_BOTTOM = 1;

    /** 権限設定 管理者 */
    public static final int PERMIT_ADMIN = 0;
    /** 権限設定 アカウント単位 */
    public static final int PERMIT_ACCOUNT = 1;
    /** メッセージに表示するテキスト アカウント名 */
    public static final String TEXT_ACCOUNT = "wml.96";

    /** ユーザ区分 グループ */
    public static final int USRKBN_GROUP = 0;
    /** ユーザ区分 ユーザ */
    public static final int USRKBN_USER = 1;

    /** メッセージに表示するテキスト 組織名 */
    public static final String TEXT_ORGANIZE = "wml.25";
    /** メッセージに表示するテキスト 署名 */
    public static final String TEXT_SIGN = "wml.34";

    /** アカウント 署名　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_SIGN = 1000;

    /** アカウント  デフォルトアカウント */
    public static final int ACNT_DEF = 0;
    /** アカウント  作成アカウント */
    public static final int ACNT_MAKE_ACNT = 1;

    /** 自動削除  管理者が設定 */
    public static final int AUTO_DEL_ADM = 0;
    /** 自動削除  各アカウントが設定 */
    public static final int AUTO_DEL_ACCOUNT = 1;

    /** アカウント テーマ 未設定 */
    public static final int SAC_THEME_NOSET = 0;

    /** アカウント情報 引用符 デフォルト(">") */
    public static final int SAC_QUOTES_DEF = 0;
    /** アカウント情報 引用符 なし */
    public static final int SAC_QUOTES_NONE = 1;
    /** アカウント情報 引用符 ">>" */
    public static final int SAC_QUOTES_2 = 2;
    /** アカウント情報 引用符 "<" */
    public static final int SAC_QUOTES_3 = 3;
    /** アカウント情報 引用符 "<<" */
    public static final int SAC_QUOTES_4 = 4;
    /** アカウント情報 引用符 "|" */
    public static final int SAC_QUOTES_5 = 5;

    /** アカウント情報 送信メール形式 標準 */
    public static final int SAC_SEND_MAILTYPE_NORMAL = 0;
    /** アカウント情報 送信メール形式 HTMLメール */
    public static final int SAC_SEND_MAILTYPE_HTML = 1;

    /** アカウント作成区分 管理者のみ作成可能 */
    public static final int KANRI_USER_ONLY = 0;
    /** アカウント作成区分 制限なし */
    public static final int KANRI_USER_NO = 1;

    /** アカウント使用者設定制限 制限なし */
    public static final int KANRI_ACCOUNT_USER_NO = 0;
    /** アカウント使用者設定制限 管理者のみ設定可能 */
    public static final int KANRI_ACCOUNT_USER_ONLY = 1;

    /** アカウント作成区分 作成可 */
    public static final int ACCOUNT_ADD_OK = 0;
    /** アカウント作成区分 作成不可 */
    public static final int ACCOUNT_ADD_NG = 1;

    /** アカウント削除区分 削除可*/
    public static final int ACCOUNT_DELETE_OK = 0;
    /** アカウント削除区分 削除不可 */
    public static final int ACCOUNT_DELETE_NG = 1;

    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** 画面区分 個人 */
    public static final int DSP_KOJIN = 0;
    /** 画面区分 管理者 */
    public static final int DSP_KANRI = 1;

    /** アカウント登録・編集 選択タブ  基本*/
    public static final int SEL_TAB_NORMAL = 0;
    /** アカウント登録・編集 選択タブ 自動削除 */
    public static final int SEL_TAB_AUTODEL = 1;
    /** アカウント登録・編集 選択タブ  転送設定*/
    public static final int SEL_TAB_TENSO = 2;
    /** アカウント登録・編集 選択タブ  その他*/
    public static final int SEL_TAB_OTHER = 3;

    /** アカウント設定区分 指定 */
    public static final int ACCOUNT_SEL = 0;
    /** アカウント設定区分 すべて */
    public static final int ACCOUNT_ALL = 1;

    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** アカウント識別文字列 */
    public static final String SML_ACCOUNT_STR = "sac";

    /** スクリプト実行フラグ 実行しない */
    public static final int SCRIPT_FIG_FALSE = 0;
    /** スクリプト実行フラグ 実行する */
    public static final int SCRIPT_FIG_TRUE = 1;

    /** スクリプト なし */
    public static final int SCRIPT_NO_SET = 0;
    /** スクリプト メール表示 */
    public static final int SCRIPT_DSP_MAIL = 1;
    /** スクリプト メール作成 */
    public static final int SCRIPT_CREATE_MAIL = 2;
    /** スクリプト WEBメール共有 */
    public static final int SCRIPT_WEB_MAIL = 3;

    /** attach.html エンコード */
    public static final String ATTACH_ENCODE = "UTF-8";
    /** HTMLメールファイル名 */
    public static final String HTMLMAIL_FILENAME = "attach.html";

    /** ログ ラベル設定 */
    public static final String LOG_VALUE_LABEL = "cmn.label.settings";

    /** ラベル追加種別 既存のラベルを追加 */
    public static final int ADDLABEL_NORMAL = 0;
    /** ラベル追加種別 新規登録したラベルを追加 */
    public static final int ADDLABEL_NEW = 1;

    /** メッセージに表示するテキスト  */
    public static final String TEXT_CONDITION = "wml.wml140.02";

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

    /** フィルター設定 ラベル 未設定 */
    public static final int FILTER_LABEL_NOSET = 0;
    /** フィルター設定 ラベル ラベルを付与する */
    public static final int FILTER_LABEL_SETLABEL = 1;

    /** フィルター設定 動作_既読にする 未設定 */
    public static final int FILTER_READED_NOSET = 0;
    /** フィルター設定 動作_既読にする 既読にする */
    public static final int FILTER_READED_SETREADED = 1;

    /** フィルター設定 動作_ゴミ箱に移動する 未設定 */
    public static final int FILTER_DUST_NOSET = 0;
    /** フィルター設定 動作_ゴミ箱に移動する ゴミ箱に移動する */
    public static final int FILTER_DUST_MOVEDUST = 1;

    /** フィルター設定 動作_指定アドレスへ転送する 未設定 */
    public static final int FILTER_FORWARD_NOSET = 0;
    /** フィルター設定 動作_指定アドレスへ転送する 転送する */
    public static final int FILTER_FORWARD_SEND = 1;

    /** フィルター設定 条件 全ての条件と一致する */
    public static final int FILTER_CONDITION_AND = 0;
    /** フィルター設定 条件 いずれかの条件と一致する */
    public static final int FILTER_CONDITION_OR = 1;

    /** メール情報表示順 ソートキー 添付ファイルの有無 */
    public static final int SMS_SKEY_FILE = 1;
    /** メール情報表示順 ソートキー 件名 */
    public static final int SMS_SKEY_SUBJECT = 2;
    /** メール情報表示順 ソートキー 差出人 */
    public static final int SMS_SKEY_FROM = 3;
    /** メール情報表示順 ソートキー 日時 */
    public static final int SMS_SKEY_DATE = 4;
    /** メール情報表示順 ソートキー 未読/既読 */
    public static final int SMS_SKEY_READED = 5;
    /** メール情報表示順 ソートキー サイズ */
    public static final int SMS_SKEY_SIZE = 6;

    /** フィルター種別 アカウント固有 */
    public static final int SFT_TYPE_ONE = 0;
    /** フィルター種別 全てのアカウント */
    public static final int SFT_TYPE_ALL = 1;

    /** 未読/既読フラグ 未読 */
    public static final int READEDFLG_NOREAD = 0;
    /** 未読/既読フラグ 既読 */
    public static final int READEDFLG_READED = 1;

    /** 集計データ ログ区分 送信 */
    public static final int LOG_COUNT_KBN_SMAIL = 0;
    /** 集計データ ログ区分 受信 */
    public static final int LOG_COUNT_KBN_JMAIL = 1;

    /** 集計データ システムメール区分 通常メール */
    public static final int LOG_COUNT_SYSKBN_NORMAL = 0;
    /** 集計データ システムメール区分 システムメール */
    public static final int LOG_COUNT_SYSKBN_SYSTEM = 1;

    /** 統計グラフ　受信メール数 */
    public static final String SML_LOG_GRAPH_JMAIL = "sml_graph_jmail";
    /** 統計グラフ　送信メール数 */
    public static final String SML_LOG_GRAPH_SMAIL = "sml_graph_smail";

    /** 送信制限対象区分 ユーザ */
    public static final int SBD_TARGET_KBN_USER = 0;
    /** 送信制限対象区分 グループ */
    public static final int SBD_TARGET_KBN_GROUP = 1;
    /** 送信制限対象区分 代表アカウント */
    public static final int SBD_TARGET_KBN_ACCOUNT = 2;
    /** 送信制限対象許可区分 ユーザ */
    public static final int SBP_TARGET_KBN_USER = 0;
    /** 送信制限対象許可区分 グループ */
    public static final int SBP_TARGET_KBN_GROUP = 1;
    /** 送信制限対象許可区分 役職 */
    public static final int SBP_TARGET_KBN_POST = 2;

    /** 表示設定 設定種別 0:各ユーザが設定する */
    public static final int DISP_CONF_USER = 0;
    /** 表示設定 設定種別 1:管理者が設定する */
    public static final int DISP_CONF_ADMIN = 1;

    /** ログ出力種別判別フラグ なし */
    public static final int SML_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ 添付ファイル */
    public static final int SML_LOG_FLG_DOWNLOAD = 0;
    /** ログ出力種別判別フラグ PDFファイル */
    public static final int SML_LOG_FLG_PDF = 1;
    /** ログ出力種別判別フラグ EMLファイル */
    public static final int SML_LOG_FLG_EML = 2;

    /** オペレーションログ 最大文字数 宛先 */
    public static final int MAXLEN_OPLOG_SENDERTO = 1000;
    /** オペレーションログ 最大文字数 CC BCC */
    public static final int MAXLEN_OPLOG_SENDERCC = 500;
    /** オペレーションログ 最大文字数 送信内容 */
    public static final int MAXLEN_OPLOG_BODY = 200;

    /** 転送設定 使用しない */
    public static final String MAIL_FORWARD_OFF = "0";
    /** 転送設定 使用する */
    public static final String MAIL_FORWARD_ON = "1";

    /** SMTPサーバ SSLを使用する */
    public static final int SMTP_SSL_ON = 1;

    /** 転送先制限 制限しない */
    public static final String MAIL_FORWARD_LIMIT_OFF = "0";
    /** 転送先制限 制限する */
    public static final String MAIL_FORWARD_LIMIT_ON = "1";

    /** アカウントの作成 管理者のみ作成可能 */
    public static final int ACCOUNT_CREATE_ADMIN = 0;
    /** アカウントの作成 制限なし */
    public static final int ACCOUNT_CREATE_ALL = 1;

    /** 自動削除区分 管理者が設定する */
    public static final int AUTO_DELETE_ADMIN = 0;
    /** 自動削除区分 各アカウントが設定する */
    public static final int AUTO_DELETE_ALL = 1;

    /** 使用者 管理者のみ設定可能 */
    public static final int ACCOUNT_USER_ADMIN = 1;
    /** 使用者 制限なし */
    public static final int ACCOUNT_USER_ALL = 0;

    /** 認証 未完了 */
    public static final int AUTH_YET = 0;
    /** 認証 完了 */
    public static final int AUTH_DONE = 1;

    /** アカウント Push通知 有効フラグ 無効 */
    public static final int ACCOUNT_PUSH_OFF = 0;
    /** アカウント Push通知 有効フラグ 有効 */
    public static final int ACCOUNT_PUSH_ON = 1;

    /** 削除区分 登録 */
    public static final int DEL_KBN_NORMAL = 1;
    /** 削除区分 論理削除済み */
    public static final int DEL_KBN_DELETED = 2;

    /** RESTAPI メールボックス名 受信*/
    public static final String RESTAPI_MAILBOX_INBOX = "inbox";
    /** RESTAPI メールボックス名 送信*/
    public static final String RESTAPI_MAILBOX_SENT = "sent";
    /** RESTAPI メールボックス名 草稿*/
    public static final String RESTAPI_MAILBOX_DRAFT = "draft";
    /** RESTAPI メールボックス名 ゴミ箱*/
    public static final String RESTAPI_MAILBOX_TRASH = "trash";
    /** RESTAPI メールボックス名 ラベル*/
    public static final String RESTAPI_MAILBOX_LABEL = "label";

    /** RESTAPI 宛先 パラメータ名*/
    public static final String RESTAPI_PARAM_TO = "toArray";
    /** RESTAPI CC パラメータ名*/
    public static final String RESTAPI_PARAM_CC = "ccArray";
    /** RESTAPI BCC パラメータ名*/
    public static final String RESTAPI_PARAM_BCC = "bccArray";
    /** RESTAPI 引継ぎバイナリSID パラメータ名*/
    public static final String RESTAPI_PARAM_BIN_SID_ARRAY = "binSidArray";

}