package jp.groupsession.v2.wml;

/**
 * <br>[機  能] メール受信サーバインターフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ReceiveServer {
    /**
     * <br>[機  能] メールサーバへの接続を切断する
     * <br>[解  説]
     * <br>[備  考]
     * @throws Exception メールサーバへの接続切断に失敗
     */
    public void close() throws Exception;
}
