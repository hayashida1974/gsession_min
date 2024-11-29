package jp.groupsession.v2.adr.adr241;


/**
 * <p>アドレス帳 会社選択画面 担当者一覧で使用するの定数クラス
 * @author JTS
 */
public class Adr241Const {

    /** 会社情報 存在する。 */
    public static final String EXIST = "0";
    /** 会社情報 存在しない */
    public static final String NOT_EXIST = "1";
    /** 会社情報 選択中 */
    public static final String INDEX_SELECT = "2";

    /** インデックス 全て */
    public static final String KANA_ALL = "-1";

    /** 索引用カタカナテーブル あ行 */
    public static final String[] KANA_A = new String[]{"ア", "イ", "ウ", "エ", "オ"};
    /** 索引用カタカナテーブル か行 */
    public static final String[] KANA_K = new String[]{"カ", "キ", "ク", "ケ", "コ"};
    /** 索引用カタカナテーブル さ行 */
    public static final String[] KANA_S = new String[]{"サ", "シ", "ス", "セ", "ソ"};
    /** 索引用カタカナテーブル た行 */
    public static final String[] KANA_T = new String[]{"タ", "チ", "ツ", "テ", "ト"};
    /** 索引用カタカナテーブル な行 */
    public static final String[] KANA_N = new String[]{"ナ", "ニ", "ヌ", "ネ", "ノ"};
    /** 索引用カタカナテーブル は行 */
    public static final String[] KANA_H = new String[]{"ハ", "ヒ", "フ", "ヘ", "ホ"};
    /** 索引用カタカナテーブル ま行 */
    public static final String[] KANA_M = new String[]{"マ", "ミ", "ム", "メ", "モ"};
    /** 索引用カタカナテーブル や行 */
    public static final String[] KANA_Y = new String[]{"ヤ", "ユ", "ヨ"};
    /** 索引用カタカナテーブル ら行 */
    public static final String[] KANA_R = new String[]{"ラ", "リ", "ル", "レ", "ロ"};
    /** 索引用カタカナテーブル わ行 */
    public static final String[] KANA_W = new String[]{"ワ", "ヲ", "ン"};

    /** 索引用カタカナテーブル インデックス */
    public static final String[] KANA_INDEX
            = new String[]{"ア", "カ", "サ", "タ", "ナ", "ハ", "マ", "ヤ", "ラ", "ワ"};

}
