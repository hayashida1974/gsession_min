package jp.groupsession.v2.rng;

/**
 * <br>[機  能] 稟議 定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngConst {

    /** プラグインID 稟議 */
    public static final String PLUGIN_ID_RINGI = "ringi";
    /** プラグインID 稟議テンプレート */
    public static final String PLUGIN_ID_RINGI_TEMPLATE = "ringitemplate";

    /** 稟議リスナークラスの定義名 */
    public static final String RNG_LISTENER_ID = "rnglistener";

    /** 管理者設定 */
    public static final int CONF_KBN_ADM = 1;
    /** 個人設定 */
    public static final int CONF_KBN_PRI = 2;

    /** 採番ID 稟議 */
    public static final String SBNSID_RINGI = "ringi";
    /** 採番IDサブ 稟議SID */
    public static final String SBNSID_SUB_RINGI_ID = "rngid";
    /** 採番IDサブ 稟議テンプレートSID */
    public static final String SBNSID_SUB_RINGI_TEMPLATE = "rngtplid";
    /** 採番IDサブ 経路テンプレートSID */
    public static final String SBNSID_SUB_RINGI_CHANNEL_TEMPLATE = "rngcntplid";
    /** 採番IDサブ テンプレートカテゴリSID */
    public static final String SBNSID_SUB_RINGI_TEMPLATE_CATEGORY = "category";
    /** 採番IDサブ 経路ステップSID */
    public static final String SBNSID_SUB_RINGI_KEIRO_STEP = "rngkeirostep";
    /** 採番IDサブ 稟議API連携SID */
    public static final String SBNSID_SUB_RINGI_TEMPLATE_APICONNECT = "rngtplapiconnect";

    /** オーダーキー 昇順 */
    public static final int RNG_ORDER_ASC = 0;
    /** オーダーキー 降順 */
    public static final int RNG_ORDER_DESC = 1;
    /** 検索条件 AND */
    public static final int RNG_SEARCHTYPE_AND = 0;
    /** 検索条件 OR */
    public static final int RNG_SEARCHTYPE_OR = 1;

    /** ソートキー タイトル */
    public static final int RNG_SORT_TITLE = 0;
    /** ソートキー 姓名 */
    public static final int RNG_SORT_NAME = 1;
    /** ソートキー 申請日時 */
    public static final int RNG_SORT_DATE = 2;
    /** ソートキー 受信日 */
    public static final int RNG_SORT_JYUSIN = 3;
    /** ソートキー 確認日 */
    public static final int RNG_SORT_KAKUNIN = 4;
    /** ソートキー 登録日時 */
    public static final int RNG_SORT_TOUROKU = 5;
    /** ソートキー 結果 */
    public static final int RNG_SORT_KEKKA = 6;

    /** モード 受信 */
    public static final int RNG_MODE_JYUSIN = 0;
    /** モード 申請中 */
    public static final int RNG_MODE_SINSEI = 1;
    /** モード 完了 */
    public static final int RNG_MODE_KANRYO = 2;
    /** モード 草稿 */
    public static final int RNG_MODE_SOUKOU = 3;
    /** モード 後閲 */
    public static final int RNG_MODE_KOETU = 4;

    /** 画面モード メイン以外 */
    public static final int RNG_MODE_NOT_MAIN = 0;
    /** 画面モード メイン */
    public static final int RNG_MODE_MAIN = 1;

    /** テンプレートモード 全て */
    public static final int RNG_TEMPLATE_ALL = 0;
    /** テンプレートモード 共有 */
    public static final int RNG_TEMPLATE_SHARE = 1;
    /** テンプレートモード 個人 */
    public static final int RNG_TEMPLATE_PRIVATE = 2;

    /** 承認者種別 承認者 */
    public static final int RNG_RNCTYPE_APPR = 0;
    /** 承認者種別 最終確認者 */
    public static final int RNG_RNCTYPE_CONFIRM = 1;
    /** 承認者種別 申請者 */
    public static final int RNG_RNCTYPE_APPL = 2;

    /** 稟議 状態 草稿 */
    public static final int RNG_STATUS_DRAFT = 0;
    /** 稟議 状態 申請中 */
    public static final int RNG_STATUS_REQUEST = 1;
    /** 稟議 状態 決裁 */
    public static final int RNG_STATUS_SETTLED = 2;
    /** 稟議 状態 却下 */
    public static final int RNG_STATUS_REJECT = 3;
    /** 稟議 状態 強制完了*/
    public static final int RNG_STATUS_DONE = 4;
    /** 稟議 状態 取り下げ*/
    public static final int RNG_STATUS_TORISAGE = 5;

    /** 稟議経路情報 状態 未設定 */
    public static final int RNG_RNCSTATUS_NOSET = 0;
    /** 稟議経路情報 状態 確認中 */
    public static final int RNG_RNCSTATUS_CONFIRM = 1;
    /** 稟議経路情報 状態 承認 */
    public static final int RNG_RNCSTATUS_APPR = 2;
    /** 稟議経路情報 状態 否認 */
    public static final int RNG_RNCSTATUS_DENIAL = 3;
    /** 稟議経路情報 状態 確認 */
    public static final int RNG_RNCSTATUS_CONFIRMATION = 4;
    /** 稟議経路情報 状態 再申請 */
    public static final int RNG_RNCSTATUS_REAPPLY = 5;
    /** 稟議経路情報 状態 後閲 */
    public static final int RNG_RNCSTATUS_KOETU = 6;
    /** 稟議経路情報 状態 スキップ*/
    public static final int RNG_RNCSTATUS_SKIP = 7;
    /** 稟議経路情報 状態 差し戻し*/
    public static final int RNG_RNCSTATUS_SASI = 8;
    /** 稟議経路情報 状態 取り下げ*/
    public static final int RNG_RNCSTATUS_TORISAGE = 9;
    /** 稟議経路情報 状態 承認完了*/
    public static final int RNG_RNCSTATUS_COMP = 10;

    /** 稟議経路情報 後閲 許可 */
    public static final int RNG_KOETU_YES = 0;
    /** 稟議経路情報 後閲 許可しない */
    public static final int RNG_KOETU_NO = 1;

    /** 稟議経路情報 グループ管理者 許可*/
    public static final int RNG_GROUP_ADMIN_YES = 0;
    /** 稟議経路情報 グループ管理者 許可しない*/
    public static final int RNG_GROUP_ADMIN_NO = 1;

    /** 完了フラグ 未定 */
    public static final int RNG_COMPFLG_UNDECIDED = 0;
    /** 完了フラグ 完了 */
    public static final int RNG_COMPFLG_COMPLETE = 1;

    /** 処理モード 登録 */
    public static final int RNG_CMDMODE_ADD = 0;
    /** 処理モード 更新 */
    public static final int RNG_CMDMODE_EDIT = 1;

    /** 承認モード 稟議承認 */
    public static final int RNG_APPRMODE_APPR = 0;
    /** 承認モード 審議中承認 */
    public static final int RNG_APPRMODE_DISCUSSING  = 1;
    /** 承認モード 承認完了 */
    public static final int RNG_APPRMODE_COMPLETE  = 2;
    /** 承認モード 再申請 */
    public static final int RNG_APPRMODE_APPL = 3;

    /** 稟議一覧 1ページの最大表示件数 */
    public static final int RNG_PAGE_VIEWCNT = 30;
    /** タイトルMAX文字数 */
    public static final int MAX_LENGTH_TITLE = 100;
    /** 内容MAX文字数 */
    public static final int MAX_LENGTH_CONTENT = 1000;
    /** フォーマットMAX文字数 */
    public static final int MAX_LENGTH_FORMAT = 1000;
    /** 申請IDMAX文字数 */
    public static final int MAX_LENGTH_RINGI_ID = 100;
    /** コメントMAX文字数 */
    public static final int MAX_LENGTH_COMMENT = 300;
    /** 経路テンプレート名称MAX文字数 */
    public static final int MAX_LENGTH_KEIRONAME = 20;

    /** 管理者設定 ショートメール通知設定 各ユーザが設定 */
    public static final int RAR_SML_NTF_USER = 0;
    /** 管理者設定 ショートメール通知設定  管理者が設定 */
    public static final int RAR_SML_NTF_ADMIN = 1;
    /** 管理者設定 ショートメール通知設定区分 通知する */
    public static final int RAR_SML_NTF_KBN_YES = 0;
    /** 管理者設定 ショートメール通知設定区分 通知しない */
    public static final int RAR_SML_NTF_KBN_NO = 1;

    /** 管理者設定 ショートメール通知設定 各ユーザが設定 */
    public static final int RAR_SML_NTF_KUT_USER = 0;
    /** 管理者設定 ショートメール通知設定 管理者が設定 */
    public static final int RAR_SML_NTF_KUT_ADMIN = 1;

    /** 管理者設定 ショートメール後閲通知設定区分 通知する */
    public static final int RAR_SML_NTF_KUT_KBN_YES = 0;
    /** 管理者設定 ショートメール通知設定区分 通知しない */
    public static final int RAR_SML_NTF_KUT_KBN_NO = 1;

    /** 管理者設定 汎用稟議テンプレート 使用する */
    public static final int RAR_HANYO_FLG_YES = 1;
    /** 管理者設定 汎用稟議テンプレート 使用しない */
    public static final int RAR_HANYO_FLG_NO = 0;

    /** 管理者設定 個人テンプレート 使用する */
    public static final int RAR_TEMPLATE_PERSONAL_FLG_YES = 1;
    /** 管理者設定 個人テンプレート 使用しない */
    public static final int RAR_TEMPLATE_PERSONAL_FLG_NO = 0;

    /** 管理者設定 個人経路テンプレート 使用する */
    public static final int RAR_KEIRO_PERSONAL_FLG_YES = 1;
    /** 管理者設定 個人経路テンプレート 使用しない */
    public static final int RAR_KEIRO_PERSONAL_FLG_NO = 0;
    /** 個人設定 ショートメール通知(通知する) */
    public static final int RNG_SMAIL_TSUUCHI = 0;
    /** 個人設定 ショートメール通知(通知しない) */
    public static final int RNG_SMAIL_NOT_TSUUCHI = 1;

    /** 個人設定 代理人期間設定フラグ(設定する)*/
    public static final int RNG_DAIRI_KIKAN = 0;
    /** 個人設定 代理人期間設定フラグ(設定しない)*/
    public static final int RNG_DAIRI_NOT_KIKAN = 1;

    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** 自動削除区分 削除しない */
    public static final int RAD_KBN_NO = 0;
    /** 自動削除区分 削除する */
    public static final int RAD_KBN_DELETE = 1;
    /** 手動削除区分 削除しない */
    public static final int MANU_DEL_NO = 0;
    /** 手動削除区分 削除する */
    public static final int MANU_DEL_OK = 1;

    /** 年 0年 */
    public static final int YEAR_ZERO = 0;
    /** 年 1年 */
    public static final int YEAR_ONE = 1;
    /** 年 2年 */
    public static final int YEAR_TWO = 2;
    /** 年 3年 */
    public static final int YEAR_THREE = 3;
    /** 年 4年 */
    public static final int YEAR_FOUR = 4;
    /** 年 5年 */
    public static final int YEAR_FIVE = 5;
    /** 年 10年 */
    public static final int YEAR_TEN = 10;

    /** 削除 月 開始月 */
    public static final int DEL_MONTH_START = 0;
    /** 削除 月 終了月 */
    public static final int DEL_MONTH_END = 11;
    /** 削除 日 開始日 */
    public static final int DEL_DAY_START = 0;
    /** 削除 日 終了日 */
    public static final int DEL_DAY_END = 30;

    /** 年キーALL */
    public static final int[] LIST_YEAR_KEY_ALL = new int[] { YEAR_ZERO,
        YEAR_ONE, YEAR_TWO, YEAR_THREE, YEAR_FOUR, YEAR_FIVE, YEAR_TEN };

    /** 稟議 管理者設定 稟議削除権限 管理者のみ */
    public static final int RAR_DEL_AUTH_ADM = 0;
    /** 稟議 管理者設定 稟議削除権限 制限なし */
    public static final int RAR_DEL_AUTH_UNRESTRICTED = 1;

    /** 稟議 管理者設定 稟議申請ID 全稟議統一*/
    public static final int RAR_SINSEI_TOUITU = 0;
    /** 稟議 管理者設定 稟議申請ID テンプレート毎に設定*/
    public static final int RAR_SINSEI_TEMP = 1;
    /** 稟議 管理者設定 稟議申請ID 特になし設定*/
    public static final int RAR_SINSEI_NONE = 2;

    /** 稟議 管理者設定 稟議申請ID重複 許可する*/
    public static final int RAR_SINSEI_KYOKA = 0;
    /** 稟議 管理者設定 稟議申請ID重複 許可しない*/
    public static final int RAR_SINSEI_NOT_KYOKA = 1;

    /** 稟議 管理者設定 稟議申請ID手入力 テンプレート毎に設定*/
    public static final int RAR_SINSEI_MANUAL_TEMPLATE = 0;
    /** 稟議 管理者設定 稟議申請ID手入力 許可する*/
    public static final int RAR_SINSEI_MANUAL_KYOKA = 1;
    /** 稟議 管理者設定 稟議申請ID手入力 許可しない*/
    public static final int RAR_SINSEI_MANUAL_NOT_KYOKA = 2;

    /** 稟議 管理者設定 稟議連番 リセットなし*/
    public static final int RAR_RESET_NONE = 0;
    /** 稟議 管理者設定 稟議連番 年*/
    public static final int RAR_RESET_YEAR = 1;
    /** 稟議 管理者設定 稟議連番 月*/
    public static final int RAR_RESET_MONTH = 2;
    /** 稟議 管理者設定 稟議連番 日*/
    public static final int RAR_RESET_DAY = 3;

    /** 稟議 管理者設定 稟議フォーマット設定文字*/
    public static final String[] CONF_LIST_FORMAT = {"文字入力", "連番値", "年4桁", "年2桁", "月", "日"};
    /** 稟議 管理者設定 稟議フォーマット設定値*/
    public static final String[] CONF_LIST_FORMAT_NO = {"1", "2", "3", "4", "5", "6"};

    /** 稟議 経路進行条件 全員の審議が必要*/
    public static final int RNG_OUT_CONDITION_DELIBERATION = 0;
    /** 稟議 経路進行条件 全員の承認が必要*/
    public static final int RNG_OUT_CONDITION_APPROVAL = 1;
    /** 稟議 経路進行条件 承認数*/
    public static final int RNG_OUT_CONDITION_NUMBER = 2;
    /** 稟議 経路進行条件 承認割合*/
    public static final int RNG_OUT_CONDITION_RATE = 3;

    /** 論理削除フラグ 全て */
    public static final int JKBN_ALL = -1;
    /** 論理削除フラグ 削除されていない */
    public static final int JKBN_ALIVE = 0;
    /** 論理削除フラグ 削除済み*/
    public static final int JKBN_DELETE = 9;

    /** 汎用稟議 タイトル*/
    public static final String RNG_FORMID_HANYOU_TITLE = "汎用稟議テンプレート";
    /** 汎用稟議 内容要素 フォームID*/
    public static final String RNG_FORMID_HANYOU_NAIYO = "汎用稟議＿内容";
    /** 汎用稟議 内容要素 フォームID*/
    public static final String RNG_FORMID_HANYOU_TEMP = "汎用稟議＿添付";

    /** 稟議 削除不可能 */
    public static final int RNG_DEL_NG = 0;
    /** 稟議 削除可能 */
    public static final int RNG_DEL_OK = 1;

    /** 稟議カテゴリ管理者 */
    public static final String CATEGORY_ADMIN = "adminList";
    /** 稟議カテゴリ使用制限 */
    public static final String CATEGORY_USE_LIMIT = "limitList";
    /** 稟議カテゴリ 使用制限(制限しない) */
    public static final int LIMIT_NOT_USE = 0;
    /** 稟議カテゴリ 使用制限(制限する) */
    public static final int LIMIT_USE = 1;
    /** 稟議カテゴリ 制限方法(制限するユーザを指定) */
    public static final int LIMIT_TYPE_LIMIT = 0;
    /** 稟議カテゴリ 制限方法(許可するユーザを指定) */
    public static final int LIMIT_TYPE_ACCEPT = 1;

    /** 稟議経路情報 申請者を経路追加できない */
    public static final int RNG_OWNSINGI_NO = 0;
    /** 稟議経路情報 申請者を経路追加できる */
    public static final int RNG_OWNSINGI_YES = 1;


    /** 稟議経路情報 申請者と審議者が同じ場合 上位経路から開始しない */
    public static final int RNG_KEIROSKIP_NO = 0;
    /** 稟議経路情報 申請者と審議者が同じ場合 上位経路から開始する */
    public static final int RNG_KEIROSKIP_YES = 1;

    /** 稟議経路情報 スキップできない経路 */
    public static final int RNG_DISABLE_SKIP = 0;
    /** 稟議経路情報 スキップ可能な経路 */
    public static final int RNG_ABLE_SKIP = 1;

    /** 稟議経路情報 審議者による経路追加不可 */
    public static final int RNG_DISABLE_ADDKEIRO = 0;
    /** 稟議経路情報 審議者による経路追加可 */
    public static final int RNG_ABLE_ADDKEIRO = 1;

    /** 稟議経路情報 スキップ先経路判定 スキップ先ではない */
    public static final int RNG_NOT_TARGET_KEIRO = 0;
    /** 稟議経路情報 スキップ先経路判定 スキップ先 */
    public static final int RNG_TARGET_KEIRO = 1;

    /** 稟議カテゴリ SID情報 全て*/
    public static final int RNG_RTC_SID_ALL = -1;
    /** 稟議カテゴリ SID情報 カテゴリなし*/
    public static final int RNG_RTC_SID_NONE = 0;

    /** 稟議カテゴリ テンプレート仕様バージョン 480未満 */
    public static final int RNG_RTP_SPEC_VER_INIT = 0;
    /** 稟議カテゴリ テンプレート仕様バージョン 480以降*/
    public static final int RNG_RTP_SPEC_VER_A480 = 1;

    /** 稟議テンプレート一覧 目的区分 使用 */
    public static final int RTPLIST_MOKUTEKI_USE = 0;
    /** 稟議テンプレート一覧 管理 */
    public static final int RTPLIST_MOKUTEKI_KANRI = 1;
    /** 稟議テンプレート使用制限 あり　 */
    public static final int RTP_LIMIT_TEMPLATE_YES = 1;
    /** 稟議テンプレート使用制限　なし　 */
    public static final int RTP_LIMIT_TEMPLATE_NO = 0;

    /** 遷移元申請・承認*/
    public static final int STATUS_SOURCE_APPLY_SML = 0;
    /** 遷移元却下*/
    public static final int STATUS_SOURCE_REJECT_SML = 1;
    /** 遷移元差し戻し*/
    public static final int STATUS_SOURCE_REMAND_SML = 2;
    /** 遷移元後閲*/
    public static final int STATUS_SOURCE_KOETU_SML = 3;
    /** 遷移元完了*/
    public static final int STATUS_SOURCE_DONE_SML = 4;
    /** 遷移元取り下げ*/
    public static final int STATUS_SOURCE_TORISAGE_SML = 5;
    /** 承認完了 */
    public static final int STATUS_SOURCE_APPROVAL_COMP_SML = 6;
    /** スキップ*/
    public static final int STATUS_SOURCE_KOETU_SKIP_SML = 7;

    /** 複写時エラー：なし */
    public static final int RNG_COPY_ERROR_NOTHING = 0;
    /** 複写時エラー：不正なアクセス */
    public static final int RNG_COPY_ERROR_ACCESS = 1;
    /** 複写時エラー：テンプレート制限 */
    public static final int RNG_COPY_ERROR_TEMPLATE = 2;

    /** テンプレートバージョン比較コード 変更なし*/
    public static final int CODE_TPVERCHK_EQ = 0;
    /** テンプレートバージョン比較コード 内容バージョン変更*/
    public static final int CODE_TPVERCHK_RTP = 1;
    /** テンプレートバージョン比較コード 経路バージョン変更*/
    public static final int CODE_TPVERCHK_RCT = 2;
    /** テンプレートバージョン比較コード 内容バージョン＆経路バージョン変更*/
    public static final int CODE_TPVERCHK_BOTH = 3;

    /** 経路コメント 文字長*/
    public static final int KEIRO_COMMENT_LENGTH = 200;

    /** テンプレート取得条件 最新のみ */
    public static final int MAXVER_KBN_ON = 1;

    /** 決済後アクション使用フラグ 使用しない*/
    public static final int FLG_NOTUSE_APICONNECT = 0;
    /** 決済後アクション使用フラグ 使用する*/
    public static final int FLG_USE_APICONNECT = 1;

}
