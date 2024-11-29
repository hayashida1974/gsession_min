package jp.groupsession.v2.cmn.exception;

import java.util.HashSet;

/**
 *
 * <br>[機  能] 添付ファイル登録時、登録データが存在しない場合の例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSAttachFileNotExistException extends Exception {
    /**
     *
     * コンストラクタ
     */
    public GSAttachFileNotExistException() {
        super();
    }
    /**
     *
     * コンストラクタ
     * @param s エラーメッセージ
     */
    public GSAttachFileNotExistException(String s) {
        super(s);
    }
    /**
     *
     * <br>[機  能] エクセプションの原因からこのエクセプションを探す
     * <br>[解  説] 原因になければnullを返す
     * <br>[備  考]
     * @param e 判定対象
     * @return 見つかったエクセプション
     */
    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param e
     * @return
     */
    public static GSAttachFileNotExistException searchCause(Throwable e) {
        if (e == null) {
            return null;
        }
        if (e instanceof GSAttachFileNotExistException) {
            return (GSAttachFileNotExistException) e;
        }
        Throwable cause = e.getCause();

        HashSet<Throwable> checked = new HashSet<>();
        while (cause != null
                && cause != e
                && !checked.contains(cause)) {
            if (cause instanceof GSAttachFileNotExistException) {
                return (GSAttachFileNotExistException) cause;
            }
            checked.add(cause);
            cause = cause.getCause();

        }
        return null;

    }
}
