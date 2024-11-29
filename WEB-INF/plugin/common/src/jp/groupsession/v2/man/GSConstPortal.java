package jp.groupsession.v2.man;

/**
 * <br>[機  能] ポータル 管理定数一覧
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class GSConstPortal {

    /** プラグインID */
    public static final String PLUGIN_ID = "portal";

    /** 採番IDサブ ポータルSID */
    public static final String SBNSID_SUB_PORTAL = "portal";
    /** 採番IDサブ ポータルSID */
    public static final String SBNSID_SUB_PORTLET = "portlet";
    /** 採番IDサブ ポートレット画像SID */
    public static final String SBNSID_SUB_PORTLET_IMAGE = "image";
    /** 採番IDサブ ポートレットカテゴリ */
    public static final String SBNSID_SUB_PLT_CATEGORY = "portlet_category";

    /** ポータルSID メイン */
    public static final int PORTAL_SID_MAIN = 0;

    /** 処理モード 登録 */
    public static final int CMD_MODE_ADD = 0;
    /** 処理モード 編集 */
    public static final int CMD_MODE_EDIT = 1;

    /** 管理者設定 編集区分 0=管理者のみ */
    public static final int EDIT_KBN_ADM = 0;
    /** 管理者設定 編集区分 1=制限なし(デフォルト) */
    public static final int EDIT_KBN_PUBLIC = 1;

    /** ポータル名 最大文字数 */
    public static final int MAX_LENGTH_PORTAL_NAME = 100;
    /** ポータル説明 最大文字数 */
    public static final int MAX_LENGTH_PTL_DESCRIPTION = 1000;

    /** ポータル公開区分 表示 */
    public static final int PTL_OPENKBN_OK = 0;
    /** ポータル公開区分 非表示 */
    public static final int PTL_OPENKBN_NG = 1;

    /** ポータルアクセス制限 制限しない */
    public static final int PTL_ACCESS_OFF = 0;
    /** ポータルアクセス制限 制限する */
    public static final int PTL_ACCESS_ON = 1;

    /** ポータル区分 共通 */
    public static final int PTS_KBN_COMMON = 0;
    /** ポータル区分 ユーザ */
    public static final int PTS_KBN_USER = 1;

    /** ポータル登録のグループコンボ グループ一覧のVALUE */
    public static final String GROUP_COMBO_VALUE = "-9";
    /** ポータル登録の閲覧コンボ グループの場合の先頭文字 */
    public static final String MEMBER_GROUP_HEADSTR = "G";

    /** ポータルレイアウト 表示位置 上 */
    public static final int LAYOUT_POSITION_TOP = 1;
    /** ポータルレイアウト 表示位置 下 */
    public static final int LAYOUT_POSITION_BOTTOM = 2;
    /** ポータルレイアウト 表示位置 左 */
    public static final int LAYOUT_POSITION_LEFT = 3;
    /** ポータルレイアウト 表示位置 中 */
    public static final int LAYOUT_POSITION_CENTER = 4;
    /** ポータルレイアウト 表示位置 右 */
    public static final int LAYOUT_POSITION_RIGHT = 5;

    /** レイアウト表示区分 表示 */
    public static final int LAYOUT_VIEW_ON = 0;
    /** レイアウト表示区分 非表示 */
    public static final int LAYOUT_VIEW_OFF = 1;

    /** ポータル位置情報種別 ポートレット */
    public static final int PTP_TYPE_PORTLET = 0;
    /** ポータル位置情報種別 プラグイン */
    public static final int PTP_TYPE_PLUGIN = 1;
    /** ポータル位置情報種別 プラグインポートレット */
    public static final int PTP_TYPE_PLUGINPORTLET = 2;
    /** ポータル位置情報種別 インフォメーション */
    public static final int PTP_TYPE_INFORMATION = 3;

    /** ポートレット 内容の最大表示文字数設定名 */
    public static final String PTLCONF_BODY_LIMIT = "PORTLET_MAXLENGTH";

    /** ポートレット 内容の最大表示文字数 デフォルト値 */
    public static final int PORTLET_CONTENT_LIMIT_DEFAULT = 100000;
    /** ポートレット 内容の最大表示文字数 無制限 */
    public static final int PORTLET_CONTENT_LIMIT_NOLIMIT = 0;

    /** ポータルメニュー戻り先画面 管理者設定画面 */
    public static final int BACKSCREEN_MAIN_ADMIN_MENU = 0;
    /** ポータルメニュー戻り先画面 ポータルメイン画面 */
    public static final int BACKSCREEN_PORTAL_MAIN = 1;

    /** ポートレットパラメータ区分 なし */
    public static final int PTP_PARAMKBN_OFF = 0;
    /** ポートレットパラメータ区分 あり */
    public static final int PTP_PARAMKBN_ON = 1;

}