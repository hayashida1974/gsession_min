package jp.co.sjts.util.date;

/**
 * <br>[機  能] 対象が存在しない場合にスローする例外
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class NotExistsDateException extends Exception {
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public NotExistsDateException() {
        super();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] エラーメッセージを指定してインスタンスを生成します
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public NotExistsDateException(String s) {
        super(s);
    }
}
