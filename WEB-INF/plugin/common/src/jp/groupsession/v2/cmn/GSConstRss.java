package jp.groupsession.v2.cmn;

/**
 * <br>[機  能] 稟議プラグインで使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstRss {

    /** プラグインID RSS */
    public static final String PLUGIN_ID_RSS = "rss";

    /** RSS一覧のデフォルト表示件数 */
    public static final int RSS_DEFAULT_VIEWCNT = 5;
    /** 登録ランキングの最大表示件数 */
    public static final int RSSRANKING_VIEWCNT = 10;

    /** 処理モード 登録 */
    public static final int RSSCMDMODE_ADD = 0;
    /** 処理モード 更新 */
    public static final int RSSCMDMODE_EDIT = 1;

    /** ヘルプモード 登録 */
    public static final String RSSHELPMODE_ADD = "0";
    /** ヘルプモード 更新 */
    public static final String RSSHELPMODE_EDIT = "1";

    /** 採番ID 稟議 */
    public static final String SBNSID_RSS = "rss";
    /** 採番IDサブ 稟議SID */
    public static final String SBNSID_SUB_RSS_ID = "rssid";

    /** 位置フラグ 左 */
    public static final int RSS_POSITIONFLG_LEFT = 1;
    /** 位置フラグ 右 */
    public static final int RSS_POSITIONFLG_RIGHT = 2;
    /** 表示フラグ 表示 */
    public static final int RSS_VIEWFLG_SHOW = 0;
    /** 表示フラグ 非表示 */
    public static final int RSS_VIEWFLG_NOTSHOW = 1;

    /** RSS購読数 */
    public static final int RSS_KOUDOKU_FLG_YES = 1;
    /** RSS購読数 */
    public static final int RSS_KOUDOKU_FLG_NO = 0;

    /** メイン表示フラグ 表示 */
    public static final int RSS_MAIN_VIEWFLG_NOT_MAINVIEW = -1;
    /** メイン表示フラグ 表示 */
    public static final int RSS_MAIN_VIEWFLG_SHOW = 0;
    /** メイン表示フラグ 非表示 */
    public static final int RSS_MAIN_VIEWFLG_NOTSHOW = 1;

    /** メッセージに表示するテキスト RSS */
    public static final String RSS_MSG = "RSS";
    /** メッセージに表示するテキスト URL */
    public static final String TEXT_RSS_URL = "URL";

    /** RSS名称MAX文字数 */
    public static final int MAX_LENGTH_RSSNAME = 50;
    /** フィードURLMAX文字数 */
    public static final int MAX_LENGTH_FEEDURL = 2000;
    /** URLMAX文字数 */
    public static final int MAX_LENGTH_URL = 2000;

    /** タイムアウト */
    public static final int TIMEOUT = 3;
    /** RSS更新間隔 単位（分）*/
    public static final int RSS_INF_UPDATE_TIME = 30;

    /** 登録ランキング一覧表示ページ数 */
    public static final int DSP_PAGE_NUM = 1;
    /** 登録ランキング一覧取得最大件数 */
    public static final int RANKING_MAX_COUNT = 5;
    /** 新着ランキング一覧取得最大件数 */
    public static final int NEWRANKING_MAX_COUNT = 5;
    /** RSS見出し 最大表示件数 */
    public static final int MIDASI_MAX_COUNT = 10;
    /** 取得するフィード情報件数 */
    public static final int RSS_FEED_COUNT = 10;

    /** BASIC認証を行う*/
    public static final int RSS_BASIC_AUTH_USE = 1;
    /** BASIC認証を行わない*/
    public static final int RSS_BASIC_AUTH_NOT_USE = 0;

    /** ソート　RSS名称 */
    public static final int RSS_SORT_NAME = 1;
    /** ソート　ユーザー数 */
    public static final int RSS_SORT_USER_COUNT = 2;
    /** ソート　最終更新日時 */
    public static final int RSS_SORT_LAST_UPDATE = 3;
    /** ソートキーALL */
    public static final int[] LIST_SORT_KEY_ALL = new int[] { RSS_SORT_NAME,
        RSS_SORT_USER_COUNT, RSS_SORT_LAST_UPDATE };

    /** オーダーキー (昇順) */
    public static final int ORDER_KEY_ASC = 0;
    /** オーダーキー (降順) */
    public static final int ORDER_KEY_DESC = 1;

    /** 新着RSSデフォルト表示日数 */
    public static final int NEW_DEFO_DSP_COUNT = 7;
}
