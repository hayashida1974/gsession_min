package jp.groupsession.v2.cmn;

/**
 * <br>[機  能] GroupSessionのステータスオブジェクト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Status {

    /** 初期化中 */
    private boolean init__ = false;
    /** シャットダウン中 */
    private boolean shutdown__ = false;
    /** バックアップ中 */
    private boolean bakcup__ = false;

    /**
     * <br>[機  能] 初期化中か判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:初期化中 false:通常
     */
    public boolean isInit() {
        return init__;
    }

    /**
     * <br>[機  能] 初期化状態をセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param init 初期化状態
     */
    public void setInit(boolean init) {
        init__ = init;
    }

    /**
     * <br>[機  能] シャットダウン中か判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:シャットダウン中 false: 通常
     */
    public boolean isShutdown() {
        return shutdown__;
    }

    /**
     * <br>[機  能] シャットダウン状態をセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param shutdown シャットダウン状態
     */
    public void setShutdown(boolean shutdown) {
        shutdown__ = shutdown;
    }

    /**
     * <br>[機  能] バックアップ中か判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:バックアップ中 false: 通常
     */
    public boolean isBakcup() {
        return bakcup__;
    }

    /**
     * <br>[機  能]バックアップ状態をセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param bakcup バックアップ状態
     */
    public void setBakcup(boolean bakcup) {
        bakcup__ = bakcup;
    }

}
