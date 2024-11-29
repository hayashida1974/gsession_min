package jp.groupsession.v2.cmn;

/**
 * <br>[機  能] 施設予約プラグインで共通使用する定数クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstReserve {

    /** プラグインID */
    public static final String PLUGIN_ID_RESERVE = "reserve";

    /** プラグインID スケジュール */
    public static final String PLUGIN_ID_SCHEDULE = "schedule";

    /** プラグインID 在籍管理 */
    public static final String PLUGIN_ID_ZAISEKI = "zaiseki";

    /** プラグイン使用する */
    public static final int PLUGIN_USE = 0;
    /** プラグイン使用しない */
    public static final int PLUGIN_NOT_USE = 1;

    /** 施設予約設定 印刷区分使用設定 */
    public static final String RSV_PRINT_USE = "RSV_PRINT_USE";
    /** 施設予約設定 印刷区分使用設定 使用しない(デフォルト値) */
    public static final int RSV_PRINT_USE_NO = 0;
    /** 施設予約設定 印刷区分使用設定 使用する */
    public static final int RSV_PRINT_USE_YES = 1;

    /** エクスポート csvファイル名 */
    public static final String RSV_CSV_NAME = "ShisetuYoyaku.csv";
    /** エクスポート csvファイル名(施設グループ・施設情報一括) */
    public static final String RSV_CSV_NAME_GROUP_ALL = "Group_ShisetuAll.csv";

    /** 施設予約インポートCSVファイル名 */
    public static final String SAMPLE_RSV_CSV_NAME_ADM = "sample_admin.xls";

    /** 処理モード(新規) */
    public static final String PROC_MODE_SINKI = "0";
    /** 処理モード(編集) */
    public static final String PROC_MODE_EDIT = "1";
    /** 処理モード(ポップアップ表示) */
    public static final String PROC_MODE_POPUP = "2";
    /** 処理モード(複写して登録) */
    public static final String PROC_MODE_COPY_ADD = "3";

    /** POPUP処理モード(本日の予約を非表示) */
    public static final int POPUP_MODE_LIST_OFF = 0;
    /** POPUP処理モード(本日の予約を表示) */
    public static final int POPUP_MODE_LIST_ON = 1;

    /** 採番区分 SID 施設予約 */
    public static final String SBNSID_RESERVE = "reserve";
    /** 採番区分 SID SUB 施設グループ */
    public static final String SBNSID_SUB_GROUP = "group";
    /** 採番区分 SID SUB 施設 */
    public static final String SBNSID_SUB_SISETU = "sisetu";
    /** 採番区分 SID SUB 予約 */
    public static final String SBNSID_SUB_YOYAKU = "yoyaku";
    /** 採番区分 SID SUB 拡張予約 */
    public static final String SBNSID_SUB_KAKUTYO = "kakutyo";

    /** 画面ID 予約一覧[週間] */
    public static final String DSP_ID_RSV010 = "rsv010";
    /** 画面ID 予約一覧[日間] */
    public static final String DSP_ID_RSV020 = "rsv020";
    /** 画面ID 予約一覧[月間] */
    public static final String DSP_ID_RSV030 = "rsv030";
    /** 画面ID 予約一覧[一覧] */
    public static final String DSP_ID_RSV100 = "rsv100";
    /** 画面ID メイン */
    public static final String DSP_ID_RSVMAIN = "rsvmain";

    /** 施設 部屋インポートcsv サンプルファイル名 */
    public static final String SAMPLE_HEYA_CSV_NAME = "sample_heya.xls";
    /** 施設 物品インポートcsv サンプルファイル名 */
    public static final String SAMPLE_BUPPIN_CSV_NAME = "sample_buppin.xls";
    /** 施設 車インポートcsv サンプルファイル名 */
    public static final String SAMPLE_CAR_CSV_NAME = "sample_car.xls";
    /** 施設 書籍インポートcsv サンプルファイル名 */
    public static final String SAMPLE_BOOK_CSV_NAME = "sample_book.xls";
    /** 施設 その他インポートcsv サンプルファイル名 */
    public static final String SAMPLE_OTHER_CSV_NAME = "sample_other.xls";

    /** 施設(一括登録) 部屋インポートcsv サンプルファイル名 */
    public static final String SAMPLE_HEYA_CSV_NAME_ALL = "sample_heya_all.xls";
    /** 施設(一括登録) 物品インポートcsv サンプルファイル名 */
    public static final String SAMPLE_BUPPIN_CSV_NAME_ALL = "sample_buppin_all.xls";
    /** 施設(一括登録) 車インポートcsv サンプルファイル名 */
    public static final String SAMPLE_CAR_CSV_NAME_ALL = "sample_car_all.xls";
    /** 施設(一括登録) 書籍インポートcsv サンプルファイル名 */
    public static final String SAMPLE_BOOK_CSV_NAME_ALL = "sample_book_all.xls";
    /** 施設(一括登録) その他インポートcsv サンプルファイル名 */
    public static final String SAMPLE_OTHER_CSV_NAME_ALL = "sample_other_all.xls";

    /** 施設 部屋インポートcsv フォーマット文字列 */
    public static final String SAMPLE_HEYA_FORMAT = "SISETU_KBN_HEYA";
    /** 施設 物品インポートcsv フォーマット文字列 */
    public static final String SAMPLE_BUPPIN_FORMAT = "SISETU_KBN_BUPPIN";
    /** 施設 車インポートcsv フォーマット文字列 */
    public static final String SAMPLE_CAR_FORMAT = "SISETU_KBN_CAR";
    /** 施設 書籍インポートcsv フォーマット文字列 */
    public static final String SAMPLE_BOOK_FORMAT = "SISETU_KBN_BOOK";
    /** 施設 その他インポートcsv フォーマット文字列 */
    public static final String SAMPLE_OTHER_FORMAT = "SISETU_KBN_OTHER";

    /** グループ・施設一括登録 部屋インポートcsv 項目数 */
    public static final int GROUP_SISETU_HEYA_ITEM_NUM = 11;
    /** グループ・施設一括登録 物品インポートcsv 項目数 */
    public static final int GROUP_SISETU_BUPPIN_ITEM_NUM = 11;
    /** グループ・施設一括登録 車インポートcsv 項目数 */
    public static final int GROUP_SISETU_CAR_ITEM_NUM = 12;
    /** グループ・施設一括登録 書籍インポートcsv 項目数 */
    public static final int GROUP_SISETU_BOOK_ITEM_NUM = 12;
    /** グループ・施設一括登録 その他インポートcsv 項目数 */
    public static final int GROUP_SISETU_ETC_ITEM_NUM = 9;

    /** 施設 部屋インポートcsv 項目数 */
    public static final int SAMPLE_HEYA_ITEM_NUM = 9;
    /** 施設 物品インポートcsv 項目数 */
    public static final int SAMPLE_BUPPIN_ITEM_NUM = 9;
    /** 施設 車インポートcsv 項目数 */
    public static final int SAMPLE_CAR_ITEM_NUM = 10;
    /** 施設 書籍インポートcsv 項目数 */
    public static final int SAMPLE_BOOK_ITEM_NUM = 10;
    /** 施設 その他インポートcsv 項目数 */
    public static final int SAMPLE_ETC_ITEM_NUM = 7;

    /** 施設区分 0=全て*/
    public static final int RSK_KBN_ALL = 0;
    /** 施設区分 1=部屋 */
    public static final int RSK_KBN_HEYA = 1;
    /** 施設区分 2=物品 */
    public static final int RSK_KBN_BUPPIN = 2;
    /** 施設区分 3=車 */
    public static final int RSK_KBN_CAR = 3;
    /** 施設区分 4=書籍 */
    public static final int RSK_KBN_BOOK = 4;
    /** 施設区分 5=その他 */
    public static final int RSK_KBN_OTHER = 5;

    /** 施設区分 表示テキスト(ISBN) */
    public static final String RSK_TEXT_ISBN = "ISBN";

    /** 表示テキスト GS管理者 */
    public static final String TEXT_GS_KANRI = "admin";
    /** 表示テキスト システムメール */
    public static final String TEXT_SYSTEM_MAIL = "system_mail";
    /** 表示テキスト 施設グループ表示順変更 */
    public static final String TEXT_ = "";

    /** 可変入力項目フラグ値 0=可 */
    public static final int PROP_KBN_KA = 0;
    /** 可変入力項目フラグ値 1=不可 */
    public static final int PROP_KBN_HUKA = 1;

    /** 個人設定 表示しない */
    public static final int KOJN_SETTEI_DSP_NO = 0;
    /** 個人設定 表示する */
    public static final int KOJN_SETTEI_DSP_OK = 1;
    /** 個人設定 表示項目設定数 */
    public static final int KOJN_SETTEI_DSP_COUNT = 5;
    /** 自動リロード時間 10分 */
    public static final int AUTO_RELOAD_10MIN = 600000;

    /** 施設グループ権限設定 権限設定を行う */
    public static final int RSG_ADM_KBN_OK = 0;
    /** 施設グループ権限設定 権限設定を行わない */
    public static final int RSG_ADM_KBN_NO = 1;
    /** コンボリストデフォルト選択してください */
    public static final int COMBO_PLEASE_CHOICE = -1;
    /** コンボリストデフォルトOFF値 */
    public static final int COMBO_DEFAULT_VALUE = 0;
    /** コンボリストデフォルトON値 */
    public static final int COMBO_DEFAULT_ON_VALUE = 1;
    /** 1ページの最大表示件数 */
    public static final int MAX_RESULT_COUNT = 30;

    /** 週間画面での表示日数 */
    public static final int WEEK_DAY_COUNT = 7;
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

    /** 予約 デフォルト開始時間 */
    public static final int YRK_DEFAULT_START_HOUR = 9;
    /** 予約 デフォルト開始分 */
    public static final int YRK_DEFAULT_START_MINUTE = 0;
    /** 予約 デフォルト終了時間 */
    public static final int YRK_DEFAULT_END_HOUR = 18;
    /** 予約 デフォルト終了分 */
    public static final int YRK_DEFAULT_END_MINUTE = 0;

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

    /** 休日区分 休日 */
    public static final int HOLIDAY_TRUE = 1;
    /** 休日区分 平日 */
    public static final int HOLIDAY_FALSE = 0;

    /** 施設予約 バッチスケジュール設定 ラジオボタン区分 設定しない */
    public static final int RSV_RADIO_OFF = 0;
    /** 施設予約 バッチスケジュール設定 ラジオボタン区分 設定する */
    public static final int RSV_RADIO_ON = 1;

    /** 日間 デフォルト表示開始時間 */
    public static final int DEFAULT_START_HOUR = 9;
    /** 日間 デフォルト表示終了時間 */
    public static final int DEFAULT_END_HOUR = 18;

    /** 日間画面での時間分割単位(10分単位) */
    public static final int HOUR_DIVISION_COUNT = 6;
    /** 日間画面での1目盛の分 */
    public static final int HOUR_MEMORI_COUNT = 60 / HOUR_DIVISION_COUNT;

    /** 月間画面での同月判定値 0=異なる年月 */
    public static final int DIFFERENT_MONTH = 0;
    /** 月間画面での同月判定値 1=同じ年月 */
    public static final int SAME_MONTH = 1;

    /** 施設予約 予約時間経過表示区分 表示する */
    public static final int RSV_OVERTIME_DSP_ON = 0;
    /** 施設予約 予約時間経過表示区分 表示しない */
    public static final int RSV_OVERTIME_DSP_OFF = 1;

    /** 施設グループ名 最大文字数 */
    public static final int MAX_LENGTH_GROUP_NAME = 20;
    /** 施設グループID 最大文字数 */
    public static final int MAX_LENGTH_GROUP_ID = 15;
    /** 施設ID 最大文字数 */
    public static final int MAX_LENGTH_SISETU_ID = 15;
    /** ユーザID 最大文字数 */
    public static final int MAX_LENGTH_USER_ID = 20;
    /** ユーザ名 最大文字数 */
    public static final int MAX_LENGTH_USER_NAME = 20;
    /** 施設名 最大文字数 */
    public static final int MAX_LENGTH_SISETU_NAME = 100;
    /** 資産管理番号 最大文字数 */
    public static final int MAX_LENGTH_SISAN_KANRI = 20;
    /** 備考 最大文字数 */
    public static final int MAX_LENGTH_BIKO = 1000;
    /** 内容 最大文字数 */
    public static final int MAX_LENGTH_NAIYO = 1000;
    /** 可変項目1 最大文字数 */
    public static final int MAX_LENGTH_PROP1 = 10;
    /** 可変項目4 最大文字数 */
    public static final int MAX_LENGTH_PROP4 = 20;
    /** 可変項目5 最大文字数 */
    public static final int MAX_LENGTH_PROP5 = 17;
    /** 可変項目6 最大文字数 */
    public static final int MAX_LENGTH_PROP6 = 4;
    /** 利用目的 最大文字数 */
    public static final int MAX_LENGTH_MOKUTEKI = 50;
    /** キーワード 最大文字数 */
    public static final int MAX_LENGTH_KEYWORD = 50;
    /** 施設画像登録最大ファイル数 */
    public static final int MAX_NUMBER_SISETU_FILE = 10;
    /** 地図/場所テキストコメント */
    public static final int MAX_LENGTH_MAP_COMMENT = 50;

    /** 区分別情報 担当部署 最大文字数 */
    public static final int MAX_LENGTH_TBUSYO = 50;
    /** 区分別情報 担当・使用者名 最大文字数 */
    public static final int MAX_LENGTH_TNAME = 62;
    /** 区分別情報 人数 最大文字数 */
    public static final int MAX_LENGTH_TNUM = 5;
    /** 区分別情報 連絡先 最大文字数 */
    public static final int MAX_LENGTH_CONTACT = 20;
    /** 区分別情報 会議名案内 最大文字数 */
    public static final int MAX_LENGTH_GUIDE = 50;
    /** 区分別情報 駐車場見込み台数 最大文字数 */
    public static final int MAX_LENGTH_PARKNUM = 5;
    /** 区分別情報 行き先 最大文字数 */
    public static final int MAX_LENGTH_DEST = 50;

    /** ソート 利用者 */
    public static final int RSV_SORT_NAME = 0;
    /** ソート 開始日 */
    public static final int RSV_SORT_FROM = 1;
    /** ソート 終了日 */
    public static final int RSV_SORT_TO = 2;
    /** ソート 内容（目的） */
    public static final int RSV_SORT_CONTENT = 3;
    /** ソート 施設　*/
    public static final int RSV_SORT_SISETU = 4;

    /** 施設利用状況照会一覧表示設定 表示件数 デフォルト */
    public static final int RSV_DEFAULT_DSP = 10;
    /** 施設利用状況照会一覧表示設定 表示件数 最大値 */
    public static final int RSV_MAX_DSP = 50;

    /** 一括設定 0=適用しない */
    public static final int PROP_CHECK_NO = 0;
    /** 一括設定 1=適用する */
    public static final int PROP_CHECK_YES = 1;

    /** 編集権限 0:制限無し */
    public static final int EDIT_AUTH_NONE = 0;
    /** 編集権限 1:使用者・予約者のみ */
    public static final int EDIT_AUTH_PER_AND_ADU = 1;
    /** 編集権限 2:所属グループ・予約者のみ */
    public static final int EDIT_AUTH_GRP_AND_ADU = 2;

    /** 拡張区分 1 = 毎日 */
    public static final int KAKUTYO_KBN_EVERY_DAY = 1;
    /** 拡張区分 2 = 毎週 */
    public static final int KAKUTYO_KBN_EVERY_WEEK = 2;
    /** 拡張区分 3 = 毎月 */
    public static final int KAKUTYO_KBN_EVERY_MONTH = 3;
    /** 拡張区分 3 = 毎年 */
    public static final int KAKUTYO_KBN_EVERY_YEAR = 4;

    /** 曜日 未選択 */
    public static final int WEEK_CHECK_OFF = 0;
    /** 曜日 選択 */
    public static final int WEEK_CHECK_ON = 1;

    /** 振替 0 = 振替なし */
    public static final int FURIKAE_NO = 0;
    /** 振替 1 = 前営業日 */
    public static final int FURIKAE_MAE = 1;
    /** 振替 2 = 翌営業日 */
    public static final int FURIKAE_ATO = 2;

    /** スケジュールへの反映 0 = 反映する */
    public static final int SCD_REFLECTION_OK = 0;
    /** スケジュールへの反映 1 = 反映しない */
    public static final int SCD_REFLECTION_NO = 1;

    /** 開始日付区分 */
    public static final int FROM_DATE_KBN = 0;
    /** 終了日付区分 */
    public static final int TO_DATE_KBN = 1;

    /** 時間分割単位 10分 デフォルト */
    public static final int DF_HOUR_DIVISION = 10;
    /** 時間分割単位 5分 */
    public static final int HOUR_DIVISION5 = 5;
    /** 時間分割単位 15分 */
    public static final int HOUR_DIVISION15 = 15;

    /** 施設予約インポート項目数 */
    public static final int IMP_VALUE_SIZE_ADM = 19;

    /** 施設画像表示 0 = ON */
    public static final int SISETU_IMG_ON = 0;
    /** 施設画像表示 1 = OFF */
    public static final int SISETU_IMG_OFF = 1;

    /** 施設情報表示 0 = 表示する */
    public static final int SISETU_DATA_DSP_ON = 0;
    /** 施設情報表示 1 = 表示しない */
    public static final int SISETU_DATA_DSP_OFF = 1;

    /** 施設画像デフォルト値 */
    public static final int IMG_DEFO_VALUE = -1;

    /** 画像区分 施設 */
    public static final int TEMP_IMG_KBN_SISETU = 0;
    /** 画像区分 場所 */
    public static final int TEMP_IMG_KBN_PLACE = 1;

    /** 施設/設備画像添付ファイル フォルダ名 */
    public static final String TEMP_IMG_SISETU_UPLOAD = "upSisetuImg";

    /** 場所/地図画像添付ファイル フォルダ名 */
    public static final String TEMP_IMG_PLACE_UPLOAD = "upPlaceImg";

    /** 場所/地図画像データモデルファイル名 */
    public static final String SAVE_FILENAME = "placeDatafile";
    /** 場所/地図画像データ フォルダ名 */
    public static final String TEMP_IMG_PLACE_DATA = "upPlaceImgData";

    /** 施設/設備画像添付ファイル 週間・日間編集画面用フォルダ名 */
    public static final String TEMP_IMG_SISETU_EDIT = "editSisetuImg";
    /** 施設/設備画像添付ファイル 週間・日間表示用フォルダ名 */
    public static final String TEMP_IMG_SISETU_DSP = "dspSisetuImg";

    /** 表示区分 週間・日間に表示しない画像 */
    public static final int IMG_NOT_DSP_KBN = 0;
    /** 画像区分 週間・日間に表示する画像 */
    public static final int IMG_DSP_KBN = 1;
    /** 画面　週間 */
    public static final int DSP_WEEK = 0;
    /** 画面　日間 */
    public static final int DSP_DAY = 1;
    /** 画面　週間 URL */
    public static final String DSP_WEEK_URL = "../reserve/rsv010.do";
    /** 画面　日間 URL */
    public static final String DSP_DAY_URL = "../reserve/rsv020.do";

    /** CSV出力対象 施設ID */
    public static final String CSV_OUT_ROOMID = "1";
    /** CSV出力対象 施設 */
    public static final String CSV_OUT_ROOM = "2";
    /** CSV出力対象 ユーザID */
    public static final String CSV_OUT_USERID = "3";
    /** CSV出力対象 利用者 */
    public static final String CSV_OUT_USER = "4";
    /** CSV出力対象 利用目的 */
    public static final String CSV_OUT_PURPOSE = "5";
    /** CSV出力対象 利用開始日 */
    public static final String CSV_OUT_USEFR_DATE = "6";
    /** CSV出力対象 開始時刻 */
    public static final String CSV_OUT_USEFR_TIME = "7";
    /** CSV出力対象 利用終了日 */
    public static final String CSV_OUT_USETO_DATE = "8";
    /** CSV出力対象 終了時刻 */
    public static final String CSV_OUT_USETO_TIME = "9";
    /** CSV出力対象 内容 */
    public static final String CSV_OUT_BIKO = "10";
    /** CSV出力対象 編集権限 */
    public static final String CSV_OUT_EDPERM = "11";
    /** CSV出力対象 公開区分 */
    public static final String CSV_OUT_PUBLIC = "12";
    /** CSV出力対象 利用区分 */
    public static final String CSV_OUT_USE_KBN = "13";
    /** CSV出力対象 連絡先 */
    public static final String CSV_OUT_CONTACT = "14";
    /** CSV出力対象 会議名案内 */
    public static final String CSV_OUT_GUIDE = "15";
    /** CSV出力対象 駐車場見込み台数 */
    public static final String CSV_OUT_PARKNUM = "16";
    /** CSV出力対象 行先 */
    public static final String CSV_OUT_DEST = "17";
    /** CSV出力対象 担当部署 */
    public static final String CSV_OUT_BUSYO = "18";
    /** CSV出力対象 担当者名 or 使用者名 */
    public static final String CSV_OUT_USENAME = "19";
    /** CSV出力対象 人数 */
    public static final String CSV_OUT_USENUM = "20";


    /** 施設予約アクセス制限方法 アクセス制限しない */
    public static final int RSV_ACCESS_MODE_FREE = -1;
    /** 施設予約アクセス制限方法 使用を許可 */
    public static final int RSV_ACCESS_MODE_PERMIT = 0;
    /** 施設予約アクセス制限方法 使用を制限 */
    public static final int RSV_ACCESS_MODE_LIMIT = 1;

    /** 施設予約アクセス区分 閲覧 */
    public static final int RSV_ACCESS_KBN_READ = 0;
    /** 施設予約アクセス区分 予約可能 */
    public static final int RSV_ACCESS_KBN_WRITE = 1;

    /** 施設予約アクセス権限選択 グループの頭文字 */
    public static final String GROUP_HEADSTR = "G";
    /** グループコンボに設定するテキスト グループ一覧のVALUE */
    public static final String GROUP_COMBO_VALUE = "-9";

    /** 同時登録選択区分 選択解除 */
    public static final int SELECT_OFF = 0;
    /** 同時登録選択区分 選択 */
    public static final int SELECT_ON = 1;

    /** スケジュール登録区分 ユーザ */
    public static final int RSV_SCHKBN_USER = 0;
    /** スケジュール登録区分 グループ */
    public static final int RSV_SCHKBN_GROUP = 1;

    /** 管理者設定 日間表示時間帯区分 0:ユーザ単位で設定 */
    public static final int RAC_DTMKBN_USER = 0;
    /** 管理者設定 日間表示時間帯区分 1:管理者強制 */
    public static final int RAC_DTMKBN_ADM = 1;

    /** 管理者設定 初期値 期間権限設定 区分 0:ユーザ単位で設定 */
    public static final int RAC_INIPERIODKBN_USER = 0;
    /** 管理者設定 初期値 期間権限設定 区分 1:管理者強制 */
    public static final int RAC_INIPERIODKBN_ADM = 1;

    /** 管理者設定 初期値 編集権限設定 区分 0:ユーザ単位で設定 */
    public static final int RAC_INIEDITKBN_USER = 0;
    /** 管理者設定 初期値 編集権限設定 区分 1:管理者強制 */
    public static final int RAC_INIEDITKBN_ADM = 1;

    /** 管理者設定 初期値 公開区分設定 区分 0:ユーザ単位で設定 */
    public static final int RAC_INIPUBLICKBN_USER = 0;
    /** 管理者設定 初期値 公開区分設定 区分 1:管理者強制 */
    public static final int RAC_INIPUBLICKBN_ADM = 1;

    /** 個人設定 ショートメール通知区分 0=通知しない */
    public static final int RSU_SMAIL_SEND_NO = 0;
    /** 個人設定 ショートメール通知区分 1=通知する */
    public static final int RSU_SMAIL_SEND_OK = 1;

    /** 管理者設定 ショートメール通知区分 0=通知する */
    public static final int RSU_ADM_SMAIL_SEND_OK = 0;
    /** 管理者設定 ショートメール通知区分 1=通知しない */
    public static final int RSU_ADM_SMAIL_SEND_NO = 1;

    /** 施設グループ情報 施設予約の承認 施設情報で設定する */
    public static final int RSG_APPR_KBN_SISETSU = 0;
    /** 施設グループ情報 施設予約の承認 承認を行う */
    public static final int RSG_APPR_KBN_APPR = 1;
    /** 施設情報 施設予約の承認 未設定 */
    public static final int RSD_APPR_KBN_NOSET = 0;
    /** 施設情報 施設予約の承認 承認を行う */
    public static final int RSD_APPR_KBN_APPR = 1;

    /** 施設予約情報 承認状況 通常 */
    public static final int RSY_APPR_STATUS_NORMAL = 0;
    /** 施設予約情報 承認状況 承認待ち */
    public static final int RSY_APPR_STATUS_NOAPPR = 1;

    /** 施設予約情報 承認区分 未設定 */
    public static final int RSY_APPR_KBN_NOSET = 0;
    /** 施設予約情報 承認区分 承認 */
    public static final int RSY_APPR_KBN_APPROVAL = 1;
    /** 施設予約情報 承認区分 否認 */
    public static final int RSY_APPR_KBN_REJECTION = 2;

    /** 検索条件 承認状況 全て */
    public static final int SRH_APPRSTATUS_ALL = 0;
    /** 検索条件 承認状況 通常(承認済) */
    public static final int SRH_APPRSTATUS_NORMAL = 1;
    /** 検索条件 承認状況 承認待ち */
    public static final int SRH_APPRSTATUS_NOAPPR = 2;
    /** 検索条件 承認状況 承認対象のみ */
    public static final int SRH_APPRSTATUS_APPRONLY = 3;

    /** 施設予約情報 利用区分 未設定 */
    public static final int RSY_USE_KBN_NOSET = 0;
    /** 施設予約情報 利用区分 会議 */
    public static final int RSY_USE_KBN_KAIGI = 1;
    /** 施設予約情報 利用区分 研修 */
    public static final int RSY_USE_KBN_KENSYU = 2;
    /** 施設予約情報 利用区分 その他 */
    public static final int RSY_USE_KBN_OTHER = 3;

    /** 施設予約情報 印刷区分 印刷しない */
    public static final int RSY_PRINT_KBN_NO = 0;
    /** 施設予約情報 印刷区分 印刷する */
    public static final int RSY_PRINT_KBN_YES = 1;

    /** 管理者設定 ショートメール通知設定区分 各ユーザが設定 */
    public static final int RAC_SMAIL_SEND_KBN_USER = 0;
    /** 管理者設定 ショートメール通知設定区分  管理者が設定 */
    public static final int RAC_SMAIL_SEND_KBN_ADMIN = 1;
    /** 管理者設定 ショートメール通知設定 通知する */
    public static final int RAC_SMAIL_SEND_YES = 0;
    /** 管理者設定 ショートメール通知設定 通知しない */
    public static final int RAC_SMAIL_SEND_NO = 1;

    /** 並び順 上へ */
    public static final int SORT_UP = 0;
    /** 並び順 下へ */
    public static final int SORT_DOWN = 1;

    /** 公開区分 公開 */
    public static final int PUBLIC_KBN_ALL = 0;
    /** 公開区分 予定あり */
    public static final int PUBLIC_KBN_PLANS = 1;
    /** 公開区分 所属グループのみ公開 */
    public static final int PUBLIC_KBN_GROUP = 2;
    /** 公開区分 指定ユーザグループのみ */
    public static final int PUBLIC_KBN_USRGRP = 3;
    /** 公開区分 タイトルのみ */
    public static final int PUBLIC_KBN_TITLE = 4;
    /** 権限範囲判定結果 権限内 */
    public static final int POWRANGE_IN = 0;
    /** 権限範囲判定結果 権限外 */
    public static final int POWRANGE_OUT = 1;
    /** 権限範囲判定結果 権限外(公開区分:タイトルのみ) */
    public static final int RANGE_OUT_TITLE = 2;
    /** 権限範囲判定結果 削除 */
    public static final int POWRANGE_DEL = -1;

    /** 表示ユーザ 登録者 */
    public static final int DSP_USR_ADD = 0;
    /** 表示ユーザ 更新者 */
    public static final int DSP_USR_EDIT = 1;

    /** 空き状況区分 空き以外も含む*/
    public static final int SISETU_MIX = 0;
    /** 空き状況区分 空きのみ*/
    public static final int SISETU_FREE = 1;

    /** 初期表示グループ 全て */
    public static final int INIT_GROUP_ALL = 0;
    /** 初期表示グループ 選択して下さい */
    public static final int INIT_GROUP_NONE = -1;

    /** 初期表示画面 週間 */
    public static final int INIT_DISP_WEEK = 0;
    /** 初期表示画面 日間 */
    public static final int INIT_DISP_DAY = 1;

    /** 表示項目 利用目的 */
    public static final String SHOW_TARGET = "1";
    /** 表示項目 登録者名 */
    public static final String SHOW_ADDUSER = "1";

    /** 自動リロード時間 1分 */
    public static final String AUTORELOAD_1 = "60000";
    /** 自動リロード時間 3分 */
    public static final String AUTORELOAD_3 = "180000";
    /** 自動リロード時間 5分 */
    public static final String AUTORELOAD_5 = "300000";
    /** 自動リロード時間 10分 */
    public static final String AUTORELOAD_10 = "600000";
    /** 自動リロード時間 20分 */
    public static final String AUTORELOAD_20 = "1200000";
    /** 自動リロード時間 30分 */
    public static final String AUTORELOAD_30 = "1800000";
    /** 自動リロード時間 40分 */
    public static final String AUTORELOAD_40 = "2400000";
    /** 自動リロード時間 50分 */
    public static final String AUTORELOAD_50 = "3000000";
    /** 自動リロード時間 60分 */
    public static final String AUTORELOAD_60 = "3600000";
    /** 自動リロード時間 リロードしない */
    public static final String AUTORELOAD_0 = "0";

    /** 自動リロード時間 1分 */
    public static final String AUTORELOAD_TEXT_1 = "1分";
    /** 自動リロード時間 3分 */
    public static final String AUTORELOAD_TEXT_3 = "3分";
    /** 自動リロード時間 5分 */
    public static final String AUTORELOAD_TEXT_5 = "5分";
    /** 自動リロード時間 10分 */
    public static final String AUTORELOAD_TEXT_10 = "10分";
    /** 自動リロード時間 20分 */
    public static final String AUTORELOAD_TEXT_20 = "20分";
    /** 自動リロード時間 30分 */
    public static final String AUTORELOAD_TEXT_30 = "30分";
    /** 自動リロード時間 40分 */
    public static final String AUTORELOAD_TEXT_40 = "40分";
    /** 自動リロード時間 50分 */
    public static final String AUTORELOAD_TEXT_50 = "50分";
    /** 自動リロード時間 60分 */
    public static final String AUTORELOAD_TEXT_60 = "60分";
    /** 自動リロード時間 リロードしない */
    public static final String AUTORELOAD_TEXT_0 = "リロードしない";

    /** 開始時刻 0時 */
    public static final String START_TIME_0 = "0";
    /** 開始時刻 1時 */
    public static final String START_TIME_1 = "1";
    /** 開始時刻 2時 */
    public static final String START_TIME_2 = "2";
    /** 開始時刻 3時 */
    public static final String START_TIME_3 = "3";
    /** 開始時刻 4時 */
    public static final String START_TIME_4 = "4";
    /** 開始時刻 5時 */
    public static final String START_TIME_5 = "5";
    /** 開始時刻 6時 */
    public static final String START_TIME_6 = "6";
    /** 開始時刻 7時 */
    public static final String START_TIME_7 = "7";
    /** 開始時刻 8時 */
    public static final String START_TIME_8 = "8";
    /** 開始時刻 9時 */
    public static final String START_TIME_9 = "9";
    /** 開始時刻 10時 */
    public static final String START_TIME_10 = "10";
    /** 開始時刻 11時 */
    public static final String START_TIME_11 = "11";
    /** 開始時刻 12時 */
    public static final String START_TIME_12 = "12";
    /** 開始時刻 13時 */
    public static final String START_TIME_13 = "13";
    /** 開始時刻 14時 */
    public static final String START_TIME_14 = "14";
    /** 開始時刻 15時 */
    public static final String START_TIME_15 = "15";
    /** 開始時刻 16時 */
    public static final String START_TIME_16 = "16";
    /** 開始時刻 17時 */
    public static final String START_TIME_17 = "17";
    /** 開始時刻 18時 */
    public static final String START_TIME_18 = "18";
    /** 開始時刻 19時 */
    public static final String START_TIME_19 = "19";
    /** 開始時刻 20時 */
    public static final String START_TIME_20 = "20";
    /** 開始時刻 21時 */
    public static final String START_TIME_21 = "21";
    /** 開始時刻 22時 */
    public static final String START_TIME_22 = "22";
    /** 開始時刻 23時 */
    public static final String START_TIME_23 = "23";

    /** 表示件数 10 */
    public static final int SHOW_COUNT_10 = 10;
    /** 表示件数 20 */
    public static final int SHOW_COUNT_20 = 20;
    /** 表示件数 30 */
    public static final int SHOW_COUNT_30 = 30;
    /** 表示件数 40 */
    public static final int SHOW_COUNT_40 = 40;
    /** 表示件数 50 */
    public static final int SHOW_COUNT_50 = 50;

    /** 時間単位 5 */
    public static final String TIME_5 = "5";
    /** 時間単位 10 */
    public static final String TIME_10 = "10";
    /** 時間単位 15 */
    public static final String TIME_15 = "15";

    /** 各ユーザが設定する */
    public static final int AUTH_ALL_USER = 0;
    /** 管理者が設定する */
    public static final int AUTH_ADMIN_USER = 1;

    /** 終了時間が過ぎた予約を表示しない */
    public static final int END_RESERVE_HIDDEN = 1;
    /** 終了時間が過ぎた予約を表示する */
    public static final int END_RESERVE_SHOW = 0;

    /** 施設予約公開対象 種別 ユーザ */
    public static final int RDP_TYPE_USER = 0;
    /** 施設予約公開対象 種別 グループ */
    public static final int RDP_TYPE_GROUP = 1;
}