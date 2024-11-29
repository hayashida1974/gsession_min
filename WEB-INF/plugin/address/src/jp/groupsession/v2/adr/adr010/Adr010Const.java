package jp.groupsession.v2.adr.adr010;

/**
 * <p>アドレス帳一覧画面で使用する定数一覧
 * @author JTS
 */
public class Adr010Const {

    /** 処理モード 会社 */
    public static final int CMDMODE_COMPANY = 0;
    /** 処理モード 氏名 */
    public static final int CMDMODE_NAME = 1;
    /** 処理モード 担当者 */
    public static final int CMDMODE_TANTO = 2;
    /** 処理モード プロジェクト */
    public static final int CMDMODE_PROJECT = 3;
    /** 処理モード 詳細検索 */
    public static final int CMDMODE_DETAILED = 4;
    /** 処理モード コンタクト履歴 */
    public static final int CMDMODE_CONTACT = 5;

    /** ソートキー 氏名 */
    public static final int SORTKEY_UNAME = 1;
    /** ソートキー 会社名 */
    public static final int SORTKEY_CONAME = 2;
    /** ソートキー 支店・営業所名 */
    public static final int SORTKEY_COBASENAME = 3;
    /** ソートキー 役職 */
    public static final int SORTKEY_POSITION = 4;

    /** ソートキー コンタクト履歴 日時 */
    public static final int SORTKEY_CONTACT_DATE = 5;
    /** ソートキー コンタクト履歴 種別 */
    public static final int SORTKEY_CONTACT_TYPE = 6;
    /** ソートキー コンタクト履歴 タイトル */
    public static final int SORTKEY_CONTACT_TITLE = 7;
    /** ソートキー コンタクト履歴 登録者 */
    public static final int SORTKEY_CONTACT_ENTRYUSER = 8;

    /** ソートキー一覧 通常 */
    public static final int[] SORTKEY_NORMAL = new int[] {Adr010Const.SORTKEY_UNAME,
                                                            Adr010Const.SORTKEY_CONAME,
                                                            Adr010Const.SORTKEY_COBASENAME,
                                                            Adr010Const.SORTKEY_POSITION };
    /** ソートキー一覧 コンタクト履歴 */
    public static final int[] SORTKEY_CONTACT = new int[] {Adr010Const.SORTKEY_CONTACT_DATE,
                                                            Adr010Const.SORTKEY_UNAME,
                                                            Adr010Const.SORTKEY_CONAME,
                                                            Adr010Const.SORTKEY_COBASENAME,
                                                            Adr010Const.SORTKEY_CONTACT_TYPE,
                                                            Adr010Const.SORTKEY_CONTACT_TITLE,
                                                            Adr010Const.SORTKEY_CONTACT_ENTRYUSER};
    /** ソートキー一覧 プロジェクト */
    public static final int[] SORTKEY_PROJECT = new int[] {Adr010Const.SORTKEY_UNAME,
                                                            Adr010Const.SORTKEY_CONAME};

    /** 並び順 昇順 */
    public static final int ORDERKEY_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDERKEY_DESC = 1;

    /** 氏名五十音索引用カタカナテーブル */
    public static final char[] UNAME_KATAKANA = {
        'ア', 'カ', 'サ', 'タ', 'ナ', 'ハ', 'マ', 'ヤ', 'ラ', 'ワ',
        'イ', 'キ', 'シ', 'チ', 'ニ', 'ヒ', 'ミ', ' ' , 'リ', 'ヲ',
        'ウ', 'ク', 'ス', 'ツ', 'ヌ', 'フ', 'ム', 'ユ', 'ル', 'ン',
        'エ', 'ケ', 'セ', 'テ', 'ネ', 'ヘ', 'メ', ' ',  'レ', ' ' ,
        'オ', 'コ', 'ソ', 'ト', 'ノ', 'ホ', 'モ', 'ヨ', 'ロ', ' '
                                          };

    /** プロジェクト 進捗率 100%(固定) */
    public static final int RATE_MAX = 100;
    /** プロジェクト 進捗率 0%(固定) */
    public static final int RATE_MIN = 0;

}
