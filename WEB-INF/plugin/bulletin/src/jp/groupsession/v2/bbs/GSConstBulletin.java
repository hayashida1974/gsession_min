package jp.groupsession.v2.bbs;

import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 掲示板定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstBulletin {

    /** プラグインID */
    public static final String PLUGIN_ID_BULLETIN = "bulletin";

    /** 採番ID 掲示板 */
    public static final String SBNSID_BULLETIN = "bulletin";
    /** 採番IDサブ フォーラムSID */
    public static final String SBNSID_SUB_BULLETIN_FORUM = "forum";
    /** 採番IDサブ スレッドSID */
    public static final String SBNSID_SUB_BULLETIN_THREAD = "thread";
    /** 採番IDサブ 投稿SID */
    public static final String SBNSID_SUB_BULLETIN_WRITE = "write";
    /** 採番IDサブ 草稿SID */
    public static final String SBNSID_SUB_BULLETIN_SOUKOU = "soukou";

    /** 処理モード 登録 */
    public static final int BBSCMDMODE_ADD = 0;
    /** 処理モード 更新 */
    public static final int BBSCMDMODE_EDIT = 1;
    /** 処理モード 引用編集 */
    public static final int BBSCMDMODE_INYOU = 2;
    /** 処理モード 複写して新規作成 */
    public static final int BBSCMDMODE_COPY = 3;
    /** 処理モード 草稿から新規作成 */
    public static final int BBSCMDMODE_SOUKOU = 4;

    /** 検索条件 キーワード区分 AND */
    public static final int KEYWORDKBN_AND = 0;
    /** 検索条件 キーワード区分 OR */
    public static final int KEYWORDKBN_OR = 1;
    /** 検索条件 既読/未読 未読 */
    public static final int BBSREADKBN_NOREAD = 1;
    /** 検索条件 既読/未読 既読 */
    public static final int BBSREADKBN_READ = 2;

    /** 検索画面ID 掲示板一覧/検索 */
    public static final String SERCHDSPID_FORUMLIST = "0101";
    /** 検索画面ID 掲示板一覧/詳細検索 */
    public static final String SERCHDSPID_FORUMDTL = "0102";
    /** 検索画面ID スレッド一覧/検索 */
    public static final String SERCHDSPID_THREADLIST = "0601";
    /** 検索画面ID スレッド一覧/詳細検索 */
    public static final String SERCHDSPID_THREADDTL = "0602";

    /** フォーラム名MAX文字数 */
    public static final int MAX_LENGTH_FORUMNAME = 70;
    /** コメントMAX文字数 */
    public static final int MAX_LENGTH_FORUMCOMMENT = 1000;
    /** タイトルMAX文字数 */
    public static final int MAX_LENGTH_THRETITLE = 70;
    /** 内容MAX文字数 */
    public static final int MAX_LENGTH_THREVALUE = 10000;
    /** 内容MAX文字数 */
    public static final int MAX_LENGTH_WRITEVALUE = 10000;
    /** フォーラム ディスク容量 MAX文字数 */
    public static final int MAX_LENGTH_FORUM_DISK = 6;

    /** ソート 名前 */
    public static final int SORT_KEY_NAME = GSConstUser.USER_SORT_NAME;
    /** ソート 社員/職員番号 */
    public static final int SORT_KEY_SNO = GSConstUser.USER_SORT_SNO;
    /** ソート 役職 */
    public static final int SORT_KEY_YKSK = GSConstUser.USER_SORT_YKSK;
    /** ソート 閲覧状況 */
    public static final int SORT_KEY_WATCH = 11;
    /** ソート 昇順 */
    public static final int ORDER_KEY_ASC = 0;
    /** ソート 降順 */
    public static final int ORDER_KEY_DESC = 1;

    /** ソート スレッド */
    public static final int SORT_KEY_THRED = 1;
    /** ソート 投稿数 */
    public static final int SORT_KEY_TOUKOU = 2;
    /** ソート 閲覧数 */
    public static final int SORT_KEY_ETSURAN = 3;
    /** ソート 投稿者 */
    public static final int SORT_KEY_USER = 4;
    /** ソート 最新書き込み */
    public static final int SORT_KEY_SAISHIN = 5;
    /** ソート サイズ */
    public static final int SORT_KEY_SIZE = 6;
    /** ソート 掲示開始日 */
    public static final int SORT_KEY_LIMIT_FR = 7;
    /** ソート 掲示終了日 */
    public static final int SORT_KEY_LIMIT_TO = 8;

    /** 検索結果一覧画面の表示明細件数 */
    public static final int BBSSEARCH_MAXCOUNT = 10;

    /** スレッド閲覧区分 未読 */
    public static final int BBS_THRE_VIEW_NO = 0;
    /** スレッド閲覧区分 既読 */
    public static final int BBS_THRE_VIEW_YES = 1;
    /** スレッド閲覧区分 既読から未読 */
    public static final int BBS_THRE_VIEW_UNREAD = 2;
    /** 管理者設定 ショートメール通知設定 各ユーザが設定 */
    public static final int BAC_SML_NTF_USER = 0;
    /** 管理者設定 ショートメール通知設定  管理者が設定 */
    public static final int BAC_SML_NTF_ADMIN = 1;
    /** 管理者設定 ショートメール通知設定区分 通知する */
    public static final int BAC_SML_NTF_KBN_YES = 0;
    /** 管理者設定 ショートメール通知設定区分 通知しない */
    public static final int BAC_SML_NTF_KBN_NO = 1;

    /** ショートメール通知(通知する) */
    public static final int BBS_SMAIL_TSUUCHI = 0;
    /** ショートメール通知(通知しない) */
    public static final int BBS_SMAIL_NOT_TSUUCHI = 1;

    /** 投稿者画像(表示する) */
    public static final int BBS_IMAGE_DSP = 0;
    /** 投稿者画像(表示しない) */
    public static final int BBS_IMAGE_NOT_DSP = 1;

    /** 添付画像(表示する) */
    public static final int BBS_IMAGE_TEMP_DSP = 0;
    /** 添付画像(表示しない) */
    public static final int BBS_IMAGE_TEMP_NOT_DSP = 1;

    /** [サブコンテンツ] 新着スレッド(表示する) */
    public static final int BBS_THRED_DSP = 0;
    /** [サブコンテンツ] 新着スレッド(表示しない) */
    public static final int BBS_THRED_NOT_DSP = 1;

    /** [サブコンテンツ] フォーラム一覧(表示する) */
    public static final int BBS_FORUM_DSP = 0;
    /** [サブコンテンツ] フォーラム一覧(表示しない) */
    public static final int BBS_FORUM_NOT_DSP = 1;

    /** [サブコンテンツ] 未読スレッド(表示する) */
    public static final int BBS_MIDOKU_TRD_DSP = 0;
    /** [サブコンテンツ] 未読スレッド(表示しない) */
    public static final int BBS_MIDOKU_TRD_NOT_DSP = 1;

    /** 投稿一覧の並び順 昇順 */
    public static final int BBS_WRTLIST_ORDER_ASC = 0;
    /** 投稿一覧の並び順 降順 */
    public static final int BBS_WRTLIST_ORDER_DESC = 1;

    /** フォーラム表示順 上へ */
    public static final String FORUM_SORT_UP = "up";
    /** フォーラム表示順 下へ */
    public static final String FORUM_SORT_DOWN = "down";

    /** フォーラム ディスク容量制限 制限なし */
    public static final int BFI_DISK_NOLIMIT = 0;
    /** フォーラム ディスク容量制限 制限あり */
    public static final int BFI_DISK_LIMITED = 1;
    /** フォーラム ディスク容量警告 未設定 */
    public static final int BFI_WARN_DISK_NO = 0;
    /** フォーラム ディスク容量警告 警告する */
    public static final int BFI_WARN_DISK_YES = 1;

    /** 確認済み投稿(表示する) */
    public static final int BBS_CHECKED_DSP = 0;
    /** 確認済み投稿(表示しない) */
    public static final int BBS_CHECKED_NOT_DSP = 1;

    //自動データ削除設定
    /** 自動データ削除 設定しない */
    public static final int AUTO_DELETE_OFF = 0;
    /** 自動データ削除 自動削除する */
    public static final int AUTO_DELETE_ON = 1;

    /** 返信可能区分 可能 */
    public static final int BBS_THRE_REPLY_YES = 0;
    /** 返信可能区分 不可能 */
    public static final int BBS_THRE_REPLY_NO = 1;

    /** 新規ユーザスレッド閲覧状態 未読 */
    public static final int NEWUSER_THRE_VIEW_NO = 0;
    /** 新規ユーザスレッド閲覧状態 既読 */
    public static final int NEWUSER_THRE_VIEW_YES = 1;

    /** 掲示期限 無効 */
    public static final int THREAD_DISABLE = 0;
    /** 掲示期限 有効 */
    public static final int THREAD_ENABLE = 1;

    /** 掲示期限 例外  */
    public static final int THREAD_NOEXCEPTION = 0;
    /** 掲示期限 例外 (無効+スレッド期限設定あり) */
    public static final int THREAD_EXCEPTION = 1;

    /** 掲示期限 期限なし */
    public static final int THREAD_LIMIT_NO = 0;
    /** 掲示期限 期限あり */
    public static final int THREAD_LIMIT_YES = 1;

    /** スレッド保存期限 設定しない */
    public static final int THREAD_KEEP_NO = 0;
    /** スレッド保存期限 設定する */
    public static final int THREAD_KEEP_YES = 1;

    /** スレッド一覧 未読スレッド一覧 最大表示件数 */
    public static final int NOREAD_THREADLIST_MAXCNT = 10;

    /** フォーラムメンバー グループの頭文字 */
    public static final String FORUM_MEMBER_GROUP_HEADSTR = "G";
    /** フォーラムメンバー登録のグループコンボに設定するテキスト グループ一覧のVALUE */
    public static final String GROUP_COMBO_VALUE = "-9";

    /** フォーラム管理者のグループコンボに設定するテキスト ユーザ指定のVALUE */
    public static final String GROUP_COMBO_VALUE_USER = "-8";

    /** フォーラムアイコン添付ファイル フォルダ名 */
    public static final String TEMP_ICN_BBS = "bbs";

    /** アクセス権限区分 閲覧 */
    public static final int ACCESS_KBN_READ = 0;
    /** アクセス権限区分 追加・編集・削除 */
    public static final int ACCESS_KBN_WRITE = 1;
    /** アクセス権限区分 フォーラム管理者 */
    public static final int ACCESS_KBN_FORUM_ADM = 2;

    /** 日次バッチで一度に削除するスレッド閲覧情報件数 */
    public static final int BBS_BATCH_DELETE_COUNT = 100;

    /** 掲示板ポートレット スレッド一覧選択画面ID */
    public static final String SCREENID_BBSPTL020 = "bbsptl020";

    /** 全て既読にする 許可する */
    public static final int BBS_FORUM_MREAD_YES = 0;
    /** 返信可能区分 許可しない */
    public static final int BBS_FORUM_MREAD_NO = 1;

    /** スレッドテンプレート区分 使用しない */
    public static final int BBS_THRE_TEMPLATE_NO = 0;
    /** スレッドテンプレート区分 使用する */
    public static final int BBS_THRE_TEMPLATE_YES = 1;

    /** スレッドテンプレート投稿使用区分 使用しない */
    public static final int BBS_THRE_TEMPLATE_WRITE_NO = 0;
    /** スレッドテンプレート投稿使用区分 使用する */
    public static final int BBS_THRE_TEMPLATE_WRITE_YES = 1;

    /** 掲示板集計データ_集計結果 ログ区分 投稿 */
    public static final int BLS_KBN_WRITE = 0;
    /** 掲示板集計データ_集計結果 ログ区分 閲覧 */
    public static final int BLS_KBN_VIEW = 1;

    /** プラグインポートレット スレッド一覧の表示件数 */
    public static final int BBS_PORTLET_THRE_LIST_CNT = 10;

    /** スレッド削除メッセージ */
    public static final String TEXT_THRE_DEL = "bbs.2";

    /** 終了　時 */
    public static final int DAY_END_HOUR = 23;
    /** 終了　分 */
    public static final int DAY_END_MINUTES = 59;
    /** 終了　秒 */
    public static final int DAY_END_SECOND = 59;
    /** 終了　ミリ秒 */
    public static final int DAY_END_MILLISECOND
    = java.util.Calendar.getInstance().getMaximum(java.util.Calendar.MILLISECOND);

    /** 統計グラフ　閲覧数 */
    public static final String BBS_LOG_GRAPH_VIEW = "bbs_graph_view";
    /** 統計グラフ　投稿数 */
    public static final String BBS_LOG_GRAPH_WRITE = "bbs_graph_write";

    /** ログ出力種別判別フラグ なし */
    public static final int BBS_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ 添付ファイル */
    public static final int BBS_LOG_FLG_DOWNLOAD = 0;
    /** ログ出力種別判別フラグ PDFファイル */
    public static final int BBS_LOG_FLG_PDF = 1;
    /** 投稿・親投稿フラグ 子の投稿 */
    public static final int BBS_PARENT_FLG_NO = 0;
    /** 投稿・親投稿フラグ 親の投稿 */
    public static final int BBS_PARENT_FLG_YES = 1;

    /** 掲示板フォーラムの最小階層レベル */
    public static final int BBS_FORUM_MIN_LEVEL = 1;
    /** 掲示板フォーラムの最大階層レベル */
    public static final int BBS_FORUM_MAX_LEVEL = 3;
    /** 掲示板 親フォーラム未設定SID */
    public static final int BBS_DEFAULT_PFORUM_SID = 0;

    /** モード フォーラムメンバー グループ */
    public static final int MODE_MEMBER_GROUP = 0;
    /** モード フォーラムメンバー ユーザ */
    public static final int MODE_MEMBER_USER = 1;
    /** モード フォーラムメンバー ユーザの所属グループ */
    public static final int MODE_MEMBER_USERS_GROUP = 2;
    /** モード フォーラムメンバー ユーザ+グループに所属するユーザ */
    public static final int MODE_MEMBER_RELATIVE_USER = 3;

    /** 一覧表示モード スレッド */
    public static final int TYPE_THREAD = 0;
    /** 一覧表示モード 投稿 */
    public static final int TYPE_POST = 1;
    /** 一覧表示モード フォーラム */
    public static final int TYPE_FORUM = 2;

    /** 子フォーラム 無し */
    public static final int FORUM_CHILD_NOTEXIST = 0;
    /** 子フォーラム 有り */
    public static final int FORUM_CHILD_EXIST = 1;

    /** スレッド未設定SID */
    public static final int THREAD_SID_NONE = 0;

    /** 画面ID bbs041 */
    public static final String DSP_ID_BBS041 = "bbs041";
    /** 画面ID bbs170 */
    public static final String DSP_ID_BBS170 = "bbs170";
    /** 画面ID bbs220 */
    public static final String DSP_ID_BBS220 = "bbs220";

    /** コンテンツタイプ プレーンテキスト */
    public static final int CONTENT_TYPE_TEXT_PLAIN = 0;
    /** コンテンツタイプ HTMLテキスト */
    public static final int CONTENT_TYPE_TEXT_HTML = 1;

    /** 管理者設定 投稿タイプ初期値 設定種別 0:ユーザ単位で設定 */
    public static final int BAC_INI_POST_TYPE_KBN_USER = 0;
    /** 管理者設定 投稿タイプ初期値 設定種別 1:管理者強制 */
    public static final int BAC_INI_POST_TYPE_KBN_ADM = 1;

    /** 親フォーラムのメンバーに準拠しない */
    public static final int FOLLOW_PARENT_MEMBER_NO = 0;
    /** 親フォーラムのメンバーに準拠する */
    public static final int FOLLOW_PARENT_MEMBER_YES = 1;

    /** スレッド集計情報 添付ファイル 無し */
    public static final int BTS_TEMPFLG_NOTHING = 0;
    /** スレッド集計情報 添付ファイル 有り */
    public static final int BTS_TEMPFLG_EXIST = 1;

    /** スレッドの重要度 通常*/
    public static final int BTI_IMPORTANCE_NO = 0;
    /** スレッドの重要度 重要*/
    public static final int BTI_IMPORTANCE_YES = 1;

    /** 掲示期間無制限許可*/
    public static final int BFI_UNLIMITED_YES = 0;
    /** 掲示期間無制限不許可*/
    public static final int BFI_UNLIMITED_NO = 1;

    /** 草稿一覧 */

    /** 草稿ログ本文最大文字数 */
    public static final int LOG_CONTENT_MAXCNT = 20;
    /** 草稿タイプ スレッド */
    public static final int SOUKOU_TYPE_THREAD = 0;
    /** 草稿タイプ 投稿 */
    public static final int SOUKOU_TYPE_TOUKOU = 1;

    /** フォーラム　削除済み */
    public static final int FORUM_DELETE_YES = 1;
    /** フォーラム　削除してない */
    public static final int FORUM_DELETE_NO = 0;

    /** スレッド　削除済み */
    public static final int THREAD_DELETE_YES = 1;
    /** スレッド　削除してない */
    public static final int THREAD_DELETE_NO = 0;

    /** 添付ファイル　あり */
    public static final int SOUKOU_TEMP_YES = 1;
    /** 添付ファイル　なし */
    public static final int SOUKOU_TEMP_NO = 0;

    /** 草稿編集ボタン表示する */
    public static final int SOUKOU_EDITBUTTON_YES = 1;
    /** 草稿編集ボタン表示しない */
    public static final int SOUKOU_EDITBUTTON_NO = 0;

    /** 草稿内容添付表示する */
    public static final int SOUKOU_DSPTEMPCONTENT_YES = 1;
    /** 草稿内容添付表示しない */
    public static final int SOUKOU_DSPTEMPCONTENT_NO = 0;

    /** 草稿に保存ボタン表示する */
    public static final int DSP_SOUKOUBTN_YES = 1;
    /** 草稿に保存ボタン表示しない */
    public static final int DSP_SOUKOUBTN_NO = 0;

    /** ソート 種類 */
    public static final int SOUKOU_SORT_KEY_TYPE = 1;
    /** ソート 作成日時 */
    public static final int SOUKOU_SORT_KEY_ADATE = 4;

    /** スレッド移動 未完了 */
    public static int MOVE_THREAD_NOT_COMP = 0;
    /** スレッド移動 完了 */
    public static int MOVE_THREAD_COMP = 1;

}

