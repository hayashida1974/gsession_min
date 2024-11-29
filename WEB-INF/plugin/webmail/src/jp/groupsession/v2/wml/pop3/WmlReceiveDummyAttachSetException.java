package jp.groupsession.v2.wml.pop3;

import jp.groupsession.v2.cmn.exception.TempFileException;

/**
 *
 * <br>[機  能] 受信処理中の添付ファイル実体削除によるダミーファイルセットが行われた場合の通知エクセプション
 * <br>[解  説] ダミーによりメールデータ登録は正常に完了されている。
 * <br>[備  考] 受信処理内でのサーバのメール削除を防ぐために通知の意味合いがる
 *
 * @author JTS
 */
public class WmlReceiveDummyAttachSetException extends Exception {
    /**
     *
     * コンストラクタ
     * @param e 受信処理中の添付ファイル実体削除による例外
     */
    public WmlReceiveDummyAttachSetException(TempFileException e) {
        // TODO 自動生成されたコンストラクター・スタブ
    }

}
