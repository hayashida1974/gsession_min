package jp.groupsession.v2.cmn.exception;

import javax.servlet.ServletException;

/**
 * <br>[機  能] ダウンロード中に切断されたに時にthrowされる
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DownloadCancelException extends ServletException {

    /**
     * <br>[機  能] 空のExceptionを生成します
     * <br>[解  説]
     * <br>[備  考]
     */
    public DownloadCancelException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param s エラーメッセージ
     */
    public DownloadCancelException(String s) {
        super(s);
    }

    /**
     * <br>[機  能] エラーメッセージ、Throwableをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param s エラーメッセージ
     * @param e throwable
     */
    public DownloadCancelException(String s, Throwable e) {
        super(s, e);
    }
}
