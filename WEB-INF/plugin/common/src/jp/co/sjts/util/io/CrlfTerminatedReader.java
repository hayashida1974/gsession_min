package jp.co.sjts.util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * <br>[機 能] プラットフォームに依存せずにInputStreamをCRLFを終端として読み込むクラス
 * <br>[解 説]
 * <br>[備 考] BufferedReaderの拡張クラス
 * @author JTS
 */
public class CrlfTerminatedReader extends BufferedReader {

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param in InputStream
     * @param encoding 文字コード
     * @throws UnsupportedEncodingException サポートされないエンコーディング
     */
    public CrlfTerminatedReader(InputStream in, String encoding)
            throws UnsupportedEncodingException {
        super(new InputStreamReader(in, encoding));
    }

    /** 1行分のデータを格納 */
    private StringBuilder lineBuffer = new StringBuilder();

    /** 読取が最終文字列に達した場合に返すコード */
    private static final int EOF = -1;
    /** CRコード */
    private static final int CR = 13;
    /** LFコード */
    private static final int LF = 10;
    /** 改行コードの不正使用判定に使用する位置情報 */
    private static int tainted = -1;

    /**
     * [機 能] ストリームからCRFLを終端として1行読み込む<br>
     * [解 説] <br>
     * [備 考] <br>
     * @return 読み込んだ1行の文字列
     * @throws IOException
     *             if an I/O error occurs.
     */
    public String readLine() throws IOException {

        //初期化
        lineBuffer.delete(0, lineBuffer.length());

        //CRフラグ
        boolean crflg = false;
        while (true) {
            int inChar = read();

            if (!crflg) {
                //通常の読込み
                switch (inChar) {
                case CR:
                    crflg = true;
                    break;
                case EOF:
                    //CRLFなしで読込みデータが終端にいたった場合はnullを返す
                    return null;
                case LF:
                    //CRなしにLFの出現 UNIX系のファイルの可能性あり
                    if (tainted == -1) {
                        tainted = lineBuffer.length();
                    }
                default:
                    lineBuffer.append((char) inChar);
                }
            } else {
                //CR受信後の読込
                switch (inChar) {
                case LF: //CR + LF
                    if (tainted != -1) {
                        int pos = tainted;
                        tainted = -1;
                        throw new CrlfTerminationException(
                                "改行コードの使用の仕方に問題があります。", pos);
                    }
                    return lineBuffer.toString();
                case EOF:
                    //CRで読込みデータが終端にいたった場合
                    return null;
                case CR:
                    //CR + CR 2回以上つづけてCR
                    if (tainted == -1) {
                        tainted = lineBuffer.length();
                    }
                    lineBuffer.append((char) CR);
                    break;
                default:
                    //CR受信後に他(改行に関係ない)の文字列を受信
                    if (tainted == -1) {
                        tainted = lineBuffer.length();
                    }
                    lineBuffer.append((char) CR);
                    lineBuffer.append((char) inChar);
                    crflg = false;
                }
            }
        }
    }
}