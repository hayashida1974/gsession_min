package jp.groupsession.v2.cmn.server;

/**
 * <br>[機 能] GroupSession内でサーバとして動作するためのインターフェイス
 * <br>[解 説] <br>この実装クラスはサーブレット起動時にstartメソッドがコールされ
 * サーブレット終了時にはshutdownメソッドがコールされる。
 * <br>[備 考]
 */
public interface IServer {

    /**
     * <br>[機  能]サーバを起動する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void start();

    /**
     * <br>[機  能]サーバを終了する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void shutdown();

    /**
     * <br>[機  能]サーバ終了処理が完了したかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:完了 false:終了処理実行中
     */
    public boolean isShutdownEnd();
}
