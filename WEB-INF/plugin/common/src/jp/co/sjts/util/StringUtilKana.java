package jp.co.sjts.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;


/**
 * <br>[機  能] 文字(ひらがな、カタカナ)に関するユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class StringUtilKana {
    /** 半角カタカナのUnicodeの最小値 */
    public static final char KATAKANA_HALF_MIN = (char) 0xff61;

    /** 半角カタカナのUnicodeの最大値 */
    public static final char KATAKANA_HALF_MAX = (char) 0xff9f;

    /** 全角カタカナのUnicodeの最小値 */
    public static final char KATAKANA_FULL_MIN = (char) 0x30a1;

    /** 全角カタカナのUnicodeの最大値 */
    public static final char KATAKANA_FULL_MAX = (char) 0x30fc;

    /** 平仮名テーブル */
    private static final char[] HIRAGANA__ = {
                                           'あ' , 'い' , 'う' , 'え' , 'お' ,
                                           'か' , 'き' , 'く' , 'け' , 'こ' ,
                                           'さ' , 'し' , 'す' , 'せ' , 'そ' ,
                                           'た' , 'ち' , 'つ' , 'て' , 'と' ,
                                           'な' , 'に' , 'ぬ' , 'ね' , 'の' ,
                                           'は' , 'ひ' , 'ふ' , 'へ' , 'ほ' ,
                                           'ま' , 'み' , 'む' , 'め' , 'も' ,
                                           'や' , 'ゆ' , 'よ' ,
                                           'ら' , 'り' , 'る' , 'れ' , 'ろ' ,
                                           'わ' , 'を' , 'ん'
                                          };

    /** 平仮名テーブル(濁音・半濁音・小文字含む) */
    private static final char[] HIRAGANA_ALL__ = {
                                           'あ' , 'い' , 'う' , 'え' , 'お' ,
                                           'か' , 'き' , 'く' , 'け' , 'こ' ,
                                           'さ' , 'し' , 'す' , 'せ' , 'そ' ,
                                           'た' , 'ち' , 'つ' , 'て' , 'と' ,
                                           'な' , 'に' , 'ぬ' , 'ね' , 'の' ,
                                           'は' , 'ひ' , 'ふ' , 'へ' , 'ほ' ,
                                           'ま' , 'み' , 'む' , 'め' , 'も' ,
                                           'や' , 'ゆ' , 'よ' ,
                                           'ら' , 'り' , 'る' , 'れ' , 'ろ' ,
                                           'わ' , 'ゐ' , 'ゑ' , 'を' ,
                                           'ん' ,
                                           'ぁ' , 'ぃ' , 'ぅ' , 'ぇ' , 'ぉ' ,
                                           'っ' ,
                                           'ゃ' , 'ゅ' , 'ょ',
                                           'ゎ' ,
                                           'が' , 'ぎ' , 'ぐ' , 'げ' , 'ご' ,
                                           'ざ' , 'じ' , 'ず' , 'ぜ' , 'ぞ' ,
                                           'だ' , 'ぢ' , 'づ' , 'で' , 'ど' ,
                                           'ば' , 'び' , 'ぶ' , 'べ' , 'ぼ' ,
                                           'ぱ' , 'ぴ' , 'ぷ' , 'ぺ' , 'ぽ'
                                        };

    /** カタカナテーブル */
    private static final char[] KATAKANA__ = {
                                           'ア' , 'イ' , 'ウ' , 'エ' , 'オ' ,
                                           'カ' , 'キ' , 'ク' , 'ケ' , 'コ' ,
                                           'サ' , 'シ' , 'ス' , 'セ' , 'ソ' ,
                                           'タ' , 'チ' , 'ツ' , 'テ' , 'ト' ,
                                           'ナ' , 'ニ' , 'ヌ' , 'ネ' , 'ノ' ,
                                           'ハ' , 'ヒ' , 'フ' , 'ヘ' , 'ホ' ,
                                           'マ' , 'ミ' , 'ム' , 'メ' , 'モ' ,
                                           'ヤ' , 'ユ' , 'ヨ' ,
                                           'ラ' , 'リ' , 'ル' , 'レ' , 'ロ' ,
                                           'ワ' , 'ヲ' , 'ン'
                                          };

    /** カタカナテーブル */
    private static final char[][] INIT_KANA__ = {
                                          {
                                           'カ' , 'キ' , 'ク' , 'ケ' , 'コ' ,
                                           'サ' , 'シ' , 'ス' , 'セ' , 'ソ' ,
                                           'タ' , 'チ' , 'ツ' , 'テ' , 'ト' ,
                                           'ハ' , 'ヒ' , 'フ' , 'ヘ' , 'ホ' ,
                                           'ハ' , 'ヒ' , 'フ' , 'ヘ' , 'ホ' ,
                                           'ヤ' , 'ユ' , 'ヨ' , 'ウ' ,
                                           'ア' , 'イ' , 'ウ' , 'エ' , 'オ'
                                          },
                                          {
                                           'ガ' , 'ギ' , 'グ' , 'ゲ' , 'ゴ' ,
                                           'ザ' , 'ジ' , 'ズ' , 'ゼ' , 'ゾ' ,
                                           'ダ' , 'ヂ' , 'ヅ' , 'デ' , 'ド' ,
                                           'バ' , 'ビ' , 'ブ' , 'ベ' , 'ボ' ,
                                           'パ' , 'ピ' , 'プ' , 'ペ' , 'ポ',
                                           'ャ' , 'ュ' , 'ョ' , 'ヴ' ,
                                           'ァ' , 'ィ' , 'ゥ' , 'ェ' , 'ォ'
                                          }
                                        };


    /** カタカナテーブル(濁音・半濁音・小文字含む) */
    private static final char[] KATAKANA_ALL__ = {
                                           'ア' , 'イ' , 'ウ' , 'エ' , 'オ' ,
                                           'カ' , 'キ' , 'ク' , 'ケ' , 'コ' ,
                                           'サ' , 'シ' , 'ス' , 'セ' , 'ソ' ,
                                           'タ' , 'チ' , 'ツ' , 'テ' , 'ト' ,
                                           'ナ' , 'ニ' , 'ヌ' , 'ネ' , 'ノ' ,
                                           'ハ' , 'ヒ' , 'フ' , 'ヘ' , 'ホ' ,
                                           'マ' , 'ミ' , 'ム' , 'メ' , 'モ' ,
                                           'ヤ' , 'ユ' , 'ヨ' ,
                                           'ラ' , 'リ' , 'ル' , 'レ' , 'ロ' ,
                                           'ワ' , 'ヰ' , 'ヱ' , 'ヲ' ,
                                           'ン' ,
                                           'ァ' , 'ィ' , 'ゥ' , 'ェ' , 'ォ' ,
                                           'ッ' ,
                                           'ャ' , 'ュ' , 'ョ',
                                           'ヮ' ,
                                           'ガ' , 'ギ' , 'グ' , 'ゲ' , 'ゴ' ,
                                           'ザ' , 'ジ' , 'ズ' , 'ゼ' , 'ゾ' ,
                                           'ダ' , 'ヂ' , 'ヅ' , 'デ' , 'ド' ,
                                           'バ' , 'ビ' , 'ブ' , 'ベ' , 'ボ' ,
                                           'パ' , 'ピ' , 'プ' , 'ペ' , 'ポ'
                                        };

    /** 半角カタカナ⇔全角カタカナ変換テーブル */
    private static final String[][] KATAKANA_HALF_FULL_TABLE = {
    // 2文字校正の濁点付き半角カタカナ
            // 必ずテーブルの先頭においてサーチ順を優先させる。
            {"ｶﾞ", "ガ" }, {"ｷﾞ", "ギ" }, {"ｸﾞ", "グ" }, {"ｹﾞ", "ゲ" },
            {"ｺﾞ", "ゴ" }, {"ｻﾞ", "ザ" }, {"ｼﾞ", "ジ" }, {"ｽﾞ", "ズ" },
            {"ｾﾞ", "ゼ" }, {"ｿﾞ", "ゾ" }, {"ﾀﾞ", "ダ" }, {"ﾁﾞ", "ヂ" },
            {"ﾂﾞ", "ヅ" }, {"ﾃﾞ", "デ" }, {"ﾄﾞ", "ド" }, {"ﾊﾞ", "バ" },
            {"ﾋﾞ", "ビ" }, {"ﾌﾞ", "ブ" }, {"ﾍﾞ", "ベ" }, {"ﾎﾞ", "ボ" },
            {"ﾊﾟ", "パ" }, {"ﾋﾟ", "ピ" }, {"ﾌﾟ", "プ" }, {"ﾍﾟ", "ペ" },
            {"ﾎﾟ", "ポ" }, {"ｳﾞ", "ヴ" },

            //1文字構成の半角カタカナ
            {"ｱ", "ア" }, {"ｲ", "イ" }, {"ｳ", "ウ" }, {"ｴ", "エ" },
            {"ｵ", "オ" }, {"ｶ", "カ" }, {"ｷ", "キ" }, {"ｸ", "ク" },
            {"ｹ", "ケ" }, {"ｺ", "コ" }, {"ｻ", "サ" }, {"ｼ", "シ" },
            {"ｽ", "ス" }, {"ｾ", "セ" }, {"ｿ", "ソ" }, {"ﾀ", "タ" },
            {"ﾁ", "チ" }, {"ﾂ", "ツ" }, {"ﾃ", "テ" }, {"ﾄ", "ト" },
            {"ﾅ", "ナ" }, {"ﾆ", "ニ" }, {"ﾇ", "ヌ" }, {"ﾈ", "ネ" },
            {"ﾉ", "ノ" }, {"ﾊ", "ハ" }, {"ﾋ", "ヒ" }, {"ﾌ", "フ" },
            {"ﾍ", "ヘ" }, {"ﾎ", "ホ" }, {"ﾏ", "マ" }, {"ﾐ", "ミ" },
            {"ﾑ", "ム" }, {"ﾒ", "メ" }, {"ﾓ", "モ" }, {"ﾔ", "ヤ" },
            {"ﾕ", "ユ" }, {"ﾖ", "ヨ" }, {"ﾗ", "ラ" }, {"ﾘ", "リ" },
            {"ﾙ", "ル" }, {"ﾚ", "レ" }, {"ﾛ", "ロ" }, {"ﾜ", "ワ" },
            {"ｦ", "ヲ" }, {"ﾝ", "ン" }, {"ｧ", "ァ" }, {"ｨ", "ィ" },
            {"ｩ", "ゥ" }, {"ｪ", "ェ" }, {"ｫ", "ォ" }, {"ｬ", "ャ" },
            {"ｭ", "ュ" }, {"ｮ", "ョ" }, {"ｯ", "ッ" }, {"｡", "。" },
            {"｢", "「" }, {"｣", "」" }, {"､", "、" }, {"･", "・" },
            {"-", "－" }, {"ｰ", "ー" }, {"", "" }, {"ｰ", "ー" },
            {"ﾞ", "゛" }, {"ﾟ", "゜" }
            };

   /**
    * (半角カタカナを)全角カタカナに変換する
    * @param target
    *            変換する半角カタカナ文字列
    * @return 変換後の全角カタカナ文字列
    */
    public static String katakanaHalf2Full(String target) {
        //戻り値
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < target.length(); i++) {
            Character cc = Character.valueOf(target.substring(i, i + 1).charAt(0));
            String str = target.substring(i, i + 1);
            //Unicode半角カタカナのコード範囲か調べる
            if (cc.compareTo(Character.valueOf(KATAKANA_HALF_MIN)) >= 0
                    && cc.compareTo(Character.valueOf(KATAKANA_HALF_MAX)) <= 0) {
                //半角カタカナ範囲内の場合変換
                if ("ﾞ".equals(str) || "ﾟ".equals(str)) {
                    //濁音の場合 ひとつ前の文字とあわせて変換する(1文字目以外)
                    if (i != 0) {
                        String dk = __toFullKanaChar(target.substring(i - 1, i + 1));
                        if (dk.equals(target.substring(i - 1, i + 1))) {
                            //変換前と後がかわらない場合(濁点が2つ、つづく場合)は、1文字で変換
                            ret.append(__toFullKanaChar(str));
                        } else {
                            //1つ前の文字を削除
                            ret.deleteCharAt(ret.length() - 1);
                            ret.append(dk);
                        }
                    } else {
                        //初めの文字が濁音の場合はそのまま変換
                        ret.append(__toFullKanaChar(str));
                    }
                } else {
                    //濁音ではない場合
                    ret.append(__toFullKanaChar(str));
                }
            } else {
                //半角カタカナ範囲外
                ret.append(str);
            }
        }
        return ret.toString();
    }

    /**
     * <p>(半角カナ)文字列を全角カナ文字列に変換する
     * @param target 変換対象の文字列
     * @return 変換後の文字列
     */
    private static String __toFullKanaChar(String target) {
        //ぐるぐる大作戦
        for (int i = 0; i < KATAKANA_HALF_FULL_TABLE.length; i++) {
            //
            if (KATAKANA_HALF_FULL_TABLE[i][0].equals(target)) {
                //
                return KATAKANA_HALF_FULL_TABLE[i][1];
            }
        }
        //一致する文字がない場合はそのまま
        return target;
    }

    /**
     * <br>
     * [機 能] 先頭のカナ文字を取得します。濁音等がなしになります。 <br>
     * [解 説]<br>
     * [備 考]
     *
     * @param str
     *            対象のカナ文字
     * @return 先頭のカナ文字 該当しない場合は-1
     */
    public static String getInitKanaChar(String str) {
        char init = str.charAt(0);
        for (int i = 0; i < INIT_KANA__[0].length; i++) {
            if (INIT_KANA__[1][i] == init) {
                init = INIT_KANA__[0][i];
                break;
            }
        }
        return Character.toString(init);
    }

    /**
     * <br>[機  能] 指定されたカナ文字列の先頭から索引用カナ文字列
     * <br>         (ア,カ,サ,タ,ナ,ハ,マ,ヤ,ラ,ワ のいずれか)を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param kanaString 対象のカナ文字列
     * @return 索引用カナ文字列 strがnullもしくは空文字の場合はnull
     */
    public static String getIndexKanaString(String kanaString) {

        if (kanaString == null || kanaString.length() <= 0) {
            return null;
        }

        char indexKana = kanaString.charAt(0);

        if (indexKana >= 'ァ' && indexKana <= 'オ') {
            indexKana = 'ア';
        } else if (indexKana >= 'カ' && indexKana <= 'ゴ') {
            indexKana = 'カ';
        } else if (indexKana >= 'サ' && indexKana <= 'ゾ') {
            indexKana = 'サ';
        } else if (indexKana >= 'タ' && indexKana <= 'ド') {
            indexKana = 'タ';
        } else if (indexKana >= 'ナ' && indexKana <= 'ノ') {
            indexKana = 'ナ';
        } else if (indexKana >= 'ハ' && indexKana <= 'ポ') {
            indexKana = 'ハ';
        } else if (indexKana >= 'マ' && indexKana <= 'モ') {
            indexKana = 'マ';
        } else if (indexKana >= 'ャ' && indexKana <= 'ヨ') {
            indexKana = 'ヤ';
        } else if (indexKana >= 'ラ' && indexKana <= 'ロ') {
            indexKana = 'ラ';
        } else if (indexKana >= 'ヮ' && indexKana <= 'ン') {
            indexKana = 'ワ';
        }

        return String.valueOf(indexKana);
    }
    /**
     * <br>[機  能] ひらがなからインデックス値を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param ch 対象のカナ文字
     * @return インデックス値 該当しない場合は-1
     */
    public static int getIndexHira(char ch) {
        int idx = -1;

        for (int i = 0; i < HIRAGANA__.length; i++) {
            if (KATAKANA__[i] == ch) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * <br>[機  能] カタカナからインデックス値を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param ch 対象のカナ文字
     * @return インデックス値 該当しない場合は-1
     */
    public static int getIndexKana(char ch) {
        int idx = -1;
        for (int i = 0; i < KATAKANA__.length; i++) {
            if (KATAKANA__[i] == ch) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * <br>[機  能] ひらがな(濁点・半濁音・小文字含む)からインデックス値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ch 対象のひらがな文字
     * @return インデックス値 該当しない場合は-1
     */
    public static int getIndexHiraAll(char ch) {
        int idx = -1;
        for (int i = 0; i < HIRAGANA_ALL__.length; i++) {
            if (HIRAGANA_ALL__[i] == ch) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * <br>[機  能] カタカナ(濁点・半濁音・小文字含む)からインデックス値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ch 対象のひらがな文字
     * @return インデックス値 該当しない場合は-1
     */
    public static int getIndexKanaAll(char ch) {
        int idx = -1;
        for (int i = 0; i < KATAKANA_ALL__.length; i++) {
            if (KATAKANA_ALL__[i] == ch) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * <br>[機  能] 文字列のひらがなをカタカナ(全角)に変換して返す
     * <br>[解  説]
     * <br>[備  考] ひらがな以外はそのまま返す
     *
     * @param target 変換対象文字列
     * @return 変換後文字列(targetがNULLまたは空文字の場合は空文字を返す)
     */
    public static String convHiraganaToKatakana(String target) {

        StringBuilder retSb = new StringBuilder();
        if (StringUtil.isNullZeroString(target)) {
            return retSb.toString();
        }

        StringCharacterIterator stit = new StringCharacterIterator(target);
        for (char c = stit.first(); c != CharacterIterator.DONE; c = stit.next()) {
            int hiraIdx = getIndexHiraAll(c);
            if (hiraIdx == -1) {
                retSb.append(c);
            } else {
                char convChar = KATAKANA_ALL__[hiraIdx];
                retSb.append(convChar);
            }
        }

        return retSb.toString();
    }
}