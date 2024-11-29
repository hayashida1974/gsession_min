package jp.co.sjts.util.csv;

/**
 * <br>[機  能] CSV出力に失敗した場合にエラーとする場合の例外
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class CSVException extends Exception {

    /**
     * <br>[機  能] 空のException
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public CSVException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public CSVException(String s) {
        super(s);
    }

    /**
     * <br>[機  能] エラーメッセージ、Throwableをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     * @param e throwable
     */
    public CSVException(String s, Throwable e) {
        super(s, e);
    }

}
