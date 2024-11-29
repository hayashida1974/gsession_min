package jp.groupsession.v2.adr;

import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>アドレス帳 管理定数一覧
 * @author JTS
 */
public class GSConstAddress {

    /** プラグインID */
    public static final String PLUGIN_ID_ADDRESS = "address";

    /** 採番IDサブ アドレス帳SID */
    public static final String SBNSID_SUB_ADDRESS = "adressbook";
    /** 採番IDサブ 業種SID */
    public static final String SBNSID_SUB_INDUSTRY = "industry";
    /** 採番IDサブ 会社SID */
    public static final String SBNSID_SUB_COMPANY = "company";
    /** 採番IDサブ 会社拠点SID */
    public static final String SBNSID_SUB_CO_BASE = "cobase";
    /** 採番IDサブ カテゴリSID */
    public static final String SBNSID_SUB_CATEGORY = "category";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_LABEL = "label";
    /** 採番IDサブ 役職SID */
    public static final String SBNSID_SUB_POSITION = "position";

    /** 処理区分 新規追加 */
    public static final int PROCMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int PROCMODE_EDIT = 1;

    /** 拠点種別 無し */
    public static final int ABATYPE_NONE = 0;
    /** 拠点種別 本社 */
    public static final int ABATYPE_HEADOFFICE = 1;
    /** 拠点種別 支店 */
    public static final int ABATYPE_BRANCH = 2;
    /** 拠点種別 営業所 */
    public static final int ABATYPE_BUSINESSOFFICE = 3;
    /** 拠点種別 文字列 本社 */
    public static final String ABATYPE_HEADOFFICE_STR = "本社";
    /** 拠点種別 文字列 支店 */
    public static final String ABATYPE_BRANCH_STR = "支店";
    /** 拠点種別 文字列 営業所 */
    public static final String ABATYPE_BUSINESSOFFICE_STR = "営業所";

    /** 会社一覧 1ページの最大表示件数 */
    public static final int COMPANYSEARCH_MAXCOUNT = 10;

    /** メッセージに表示するテキスト ＦＡＸ */
    public static final String TEXT_FAX = "ＦＡＸ";
    /** メッセージに表示するテキスト URL */
    public static final String TEXT_URL = "URL";

    /** エラーメッセージに表示するテキスト E-MAIL */
    public static final String TEXT_EMAIL = "E-MAIL";

    /** エラーメッセージに表示するテキスト 会社SID */
    public static final String TEXT_ACO_SID = "会社SID";
    /** エラーメッセージに表示するテキスト 拠点SID */
    public static final String TEXT_ABA_SID = "拠点SID";

    /** 氏名 姓 MAX文字数 */
    public static final int MAX_LENGTH_NAME_SEI = 30;
    /** 氏名 名 MAX文字数 */
    public static final int MAX_LENGTH_NAME_MEI = 30;
    /** 氏名 姓カナ MAX文字数 */
    public static final int MAX_LENGTH_NAME_SEI_KN = 60;
    /** 氏名 名カナ MAX文字数 */
    public static final int MAX_LENGTH_NAME_MEI_KN = 60;
    /** 所属 MAX文字数 */
    public static final int MAX_LENGTH_SYOZOKU = 60;
    /** 役職 MAX文字数 */
    public static final int MAX_LENGTH_YAKUSYOKU = 30;
    /** メールアドレス MAX文字数 */
    public static final int MAX_LENGTH_MAIL = 256;
    /** メールアドレスコメント MAX文字数 */
    public static final int MAX_LENGTH_MAIL_COMMENT = 10;
    /** 郵便番号上3桁 MAX文字数 */
    public static final int MAX_LENGTH_POSTNO1 = 3;
    /** 郵便番号下4桁 MAX文字数 */
    public static final int MAX_LENGTH_POSTNO2 = 4;
    /** 住所 MAX文字数 */
    public static final int MAX_LENGTH_ADDRESS = 100;
    /** 電話番号 MAX文字数 */
    public static final int MAX_LENGTH_TEL = 20;
    /** 内線 MAX文字数 */
    public static final int MAX_LENGTH_TEL_NAI = 10;
    /** 電話番号コメント MAX文字数 */
    public static final int MAX_LENGTH_TEL_COMMENT = 10;
    /** ＦＡＸ MAX文字数 */
    public static final int MAX_LENGTH_FAX = 20;
    /** ＦＡＸコメント MAX文字数 */
    public static final int MAX_LENGTH_FAX_COMMENT = 10;
    /** アドレス帳 備考 MAX文字数 */
    public static final int MAX_LENGTH_ADR_BIKO = 1000;
    /** アドレス帳 備考 MAX文字数 */
    public static final int MAX_LENGTH_ADR2_BIKO = 300;
    /** アドレス帳 備考 MAX文字数 */
    public static final int MAX_LENGTH_ADR3_BIKO = 10000;
    /** 企業コード MAX文字数 */
    public static final int MAX_LENGTH_COMPANY_CODE = 20;
    /** 会社名 MAX文字数 */
    public static final int MAX_LENGTH_COMPANY_NAME = 50;
    /** 会社名(カナ) MAX文字数 */
    public static final int MAX_LENGTH_COMPANY_NAME_KN = 100;
    /** URL MAX文字数 */
    public static final int MAX_LENGTH_URL = 100;
    /** 会社拠点名 MAX文字数 */
    public static final int MAX_LENGTH_COBASE_NAME = 50;
    /** 役職名 MAX文字数 */
    public static final int MAX_LENGTH_POSITION_NAME = 30;
    /** ラベル名称 MAX文字数 */
    public static final int MAX_LENGTH_LABEL_NAME = 20;

    /** アドレス帳登録者制限設定 制限なし */
    public static final int POW_ADR_ALL = 0;
    /** アドレス帳登録者制限設定 制限あり */
    public static final int POW_ADR_LIMIT = 1;

    /** 管理者設定 制限なし */
    public static final int POW_ALL = 0;
    /** 管理者設定 管理者のみ */
    public static final int POW_LIMIT = 1;

    /** コンタクト履歴一覧 ソート 種別 */
    public static final int CONTACT_SORT_SYUBETU = 0;
    /** コンタクト履歴一覧 ソート タイトル */
    public static final int CONTACT_SORT_TITLE = 1;
    /** コンタクト履歴一覧 ソート 登録者 */
    public static final int CONTACT_SORT_ADDUSER = 2;
    /** コンタクト履歴一覧 ソート コンタクト日時 */
    public static final int CONTACT_SORT_DATE = 3;

    /** 企業一覧 ソート 企業コード */
    public static final int COMPANY_SORT_CODE = 0;
    /** 企業一覧 ソート 会社名 */
    public static final int COMPANY_SORT_NAME = 1;
    /** 企業一覧 ソート 備考 */
    public static final int COMPANY_SORT_BIKO = 2;

    /** 企業一覧 ソート 拠点区分 */
    public static final int COMPANY_SORT_ABA_TYPE = 3;
    /** 企業一覧 ソート 拠点名 */
    public static final int COMPANY_SORT_ABA_NAME = 4;
    /** 企業一覧 ソート 都道府県 */
    public static final int COMPANY_SORT_TDFK = 5;
    /** 企業一覧 ソート 住所1 */
    public static final int COMPANY_SORT_ADDR1 = 6;
    /** 企業一覧 ソート 住所2 */
    public static final int COMPANY_SORT_ADDR2 = 7;

    /** 編集権限 本人のみ */
    public static final int EDITPERMIT_OWN = 0;
    /** 編集権限 グループ指定 */
    public static final int EDITPERMIT_GROUP = 1;
    /** 編集権限 ユーザ指定 */
    public static final int EDITPERMIT_USER = 2;
    /** 編集権限 設定なし */
    public static final int EDITPERMIT_NORESTRICTION = 3;

    /** 個人設定 アドレス一覧表示件数 デフォルト値 */
    public static final String DEFAULT_ADRCOUNT = "30";
    /** 個人設定 会社一覧表示件数 デフォルト値 */
    public static final String DEFAULT_COMCOUNT = "30";

    /** プロジェクト区分 参加プロジェクト*/
    public static final int PROTYPE_ADD = 0;
    /** プロジェクト区分 全て*/
    public static final int PROTYPE_ALL = 1;

    /** 状態 未完 */
    public static final int STATUS_NO = 0;
    /** 状態 完了 */
    public static final int STATUS_COMP = 1;
    /** 状態 全て */
    public static final int STATUS_ALL = 2;

    /** 会社検索区分モード 五十音検索 */
    public static final int SEARCH_COMPANY_MODE_50 = 0;
    /** 会社検索モード 詳細検索 */
    public static final int SEARCH_COMPANY_MODE_DETAIL = 1;

    /** カテゴリ内のラベルの有無 0 = 存在しない*/
    public static final int CATEGORY_EXIST_NO = 0;
    /** カテゴリ内のラベルの有無 1 = 存在する*/
    public static final int CATEGORY_EXIST_YES = 1;

    /** ラベルカテゴリ 「未設定」 */
    public static final int LABEL_CATEGORY_NOSET = 1;

    /** 検索対象 件名 */
    public static final int SEARCH_TARGET_TITLE = 1;
    /** 検索対象 備考 */
    public static final int SEARCH_TARGET_BIKO = 2;

    /** キーワード検索区分 (and) */
    public static final int KEY_WORD_KBN_AND = 0;
    /** キーワード検索区分 (or) */
    public static final int KEY_WORD_KBN_OR = 1;

    /** 添付ファイル区分 指定なし */
    public static final int TEMPFILE_KBN_FREE = 0;
    /** 添付ファイル区分 あり */
    public static final int TEMPFILE_KBN_EXIST = 1;
    /** 添付ファイル区分 なし */
    public static final int TEMPFILE_KBN_NOT_EXIST = 2;

    /** 種別 種別なし */
    public static final int NOT_SYUBETU = -1;

    /** アドレス帳コンタクト履歴検索画面 */
    public static final int DSP_CONTACT_ADR010 = 0;
    /** コンタクト履歴検索画面 */
    public static final int DSP_CONTACT_ADR160 = 1;

    /** 閲覧・編集権限 各ユーザが設定する */
    public static final int MEM_DSP_USR = 1;
    /** 閲覧・編集権限 管理者が設定する */
    public static final int MEM_DSP_ADM = 0;

    /** 閲覧・編集権限 閲覧 担当者のみ */
    public static final int MEM_OP_TANTO = 0;
    /** 閲覧・編集権限 閲覧 グループ指定 */
    public static final int MEM_OP_GROUP = 1;
    /** 閲覧・編集権限 閲覧 ユーザ指定 */
    public static final int MEM_OP_USER = 2;
    /** 閲覧・編集権限 閲覧 権限設定無し */
    public static final int MEM_OP_ALL = 3;

    /** ソートキーALL */
    public static final int[] VIEWPERMIT_ALL = new int[] { GSConst.ADR_VIEWPERMIT_OWN,
                             GSConst.ADR_VIEWPERMIT_GROUP, GSConst.ADR_VIEWPERMIT_USER,
                             GSConst.ADR_VIEWPERMIT_NORESTRICTION };

    /** 企業インポート 企業コード上書き する  */
    public static final int COMPANY_UPDATE_OK = 1;
    /** 企業インポート 企業コード上書き しない  */
    public static final int COMPANY_UPDATE_NO = 0;

    /** CSVファイルヘッダーテキスト 企業コード */
    public static final String TEXT_COMP_CODE = "企業コード";
    /** CSVファイルヘッダーテキスト 会社名 */
    public static final String TEXT_COMP_NAME = "会社名";
    /** CSVファイルヘッダーテキスト 会社名(カナ) */
    public static final String TEXT_COMP_NAME_KN = "会社名(カナ)";
    /** CSVファイルヘッダーテキスト URL */
    public static final String TEXT_COMP_URL = "URL";
    /** CSVファイルヘッダーテキスト 備考 */
    public static final String TEXT_COMP_BIKO = "備考";

    /** 登録者制限 なし*/
    public static final int ARESTKBN_NONE = 0;
    /** 登録者制限 許可するユーザグループを選択*/
    public static final int ARESTKBN_SELECT_ABLE = 1;

    /** 表示 */
    public static final int DSP_ELEMENT = 1;
    /** 非表示 */
    public static final int NOT_DSP_ELEMENT = 0;

    /** コンタクト履歴 編集*/
    public static final int CONTACT_EDIT = 0;
    /** コンタクト履歴 編集不可*/
    public static final int CONTACT_NOT_EDIT = 1;

    /** 役職編集権限 管理者のみ編集可*/
    public static final int ADR_POSITION_EDIT_ADMIN = 1;
    /** 役職編集権限 役職編集権限を設定しない*/
    public static final int ADR_POSITION_EDIT = 0;
    /** 業種編集権限 管理者のみ編集可*/
    public static final int ADR_INDUSTRY_EDIT_ADMIN = 1;
    /** 業種編集権限 業種編集権限を設定しない*/
    public static final int ADR_INDUSTRY_EDIT = 0;
    /** 会社編集権限 管理者のみ編集可*/
    public static final int ADR_COMPANY_EDIT_ADMIN = 1;
    /** 会社編集権限 会社編集権限を設定しない*/
    public static final int ADR_COMPANY_EDIT = 0;
    /** ラベル編集権限 管理者のみ編集可*/
    public static final int ADR_LABEL_EDIT_ADMIN = 1;
    /** ラベル編集権限 ラベル編編集権限を設定しない*/
    public static final int ADR_LABEL_EDIT = 0;
    /** エクスポート権限 管理者のみ編集可*/
    public static final int ADR_EXPORT_ADMIN = 1;
    /** エクスポート権限 エクスポート権限を設定しない*/
    public static final int ADR_EXPORT = 0;

}