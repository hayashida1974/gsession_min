package jp.co.sjts.util;

/**
 * <br>[機 能] 文字コード定数クラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class Encoding {
    /** Shift-JIS、EUC-JP、ISO 2022 JP の検出および変換 (Unicode への変換のみ) */
    public static final String JISAUTODETECT = "JISAutoDetect";

    /** UTF-8 */
    public static final String UTF_8 = "UTF-8";
    /** UTF-16 */
    public static final String UTF_16 = "UTF-16";
    /** Windows Japanese */
    public static final String WINDOWS_31J = "windows-31j";
    /** JISX 0201, 0208 and 0212, EUC encoding Japanese */
    public static final String EUC_JP = "EUC-JP";
    /** Shift-JIS, Japanese */
    public static final String SHIFT_JIS = "Shift_JIS";
    /** JIS X 0201, 0208, in ISO 2022 form, Japanese */
    public static final String ISO_2022_JP = "ISO-2022-JP";
    /** ISO8859_1 */
    public static final String ISO8859_1 = "ISO8859_1";
    /** MS932 */
    public static final String MS932 = "MS932";
    /** CP932 */
    public static final String CP932 = "CP932";

    /**
     * [機 能] HTMLにセットするCHARSETをブラウザが解釈できる文字列に変換する<br>
     * [解 説] <br>
     * [備 考] JavaのキャラクタセットはShift_JIS以外は同じ文字列でブラウザが解釈できるため、Shift_JISのみ変換する。<br>
     * <br>Shift_JIS以外はそのままreturnする。
     * @param encoding 出力指定の文字コード(Java内部で使用しているコード)
     * @return HTMLセット用のCHARSET
     */
    public static String getHtmlCharset(String encoding) {
        String ret = null;
        if (encoding == null) {
            return null;
        } else if (WINDOWS_31J.equals(encoding) || MS932.equals(encoding)
                || SHIFT_JIS.equals(encoding)) {
            ret = SHIFT_JIS;
        } else {
            ret = encoding;
        }
        //
        return ret;
    }
}