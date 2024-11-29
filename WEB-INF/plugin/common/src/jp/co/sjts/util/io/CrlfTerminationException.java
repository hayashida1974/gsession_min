package jp.co.sjts.util.io;

import java.io.IOException;

/**
 * <br>[機 能] 読取に失敗した場合にthrowするException
 * <br>[解 説]
 * <br>[備 考]
 * @author JTS
 */
public class CrlfTerminationException extends IOException {
    /** Exceptionの発生した位置 */
    private int position__;

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param position 読取例外の発生した位置
     */
    public CrlfTerminationException(int position) {
        super();
        position__ = position;
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param s メッセージ
     * @param position 読取例外の発生した位置
     */
    public CrlfTerminationException(String s, int position) {
        super(s);
        position__ = position;
    }

    /**
     * <p>例外発生位置を取得する。
     * @return ポジション
     */
    public int position() {
        return position__;
    }
}