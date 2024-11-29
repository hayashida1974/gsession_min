package jp.groupsession.v2.rap.mbh.push;

import java.io.IOException;
import java.sql.SQLException;

import jp.groupsession.v2.rap.mbh.push.exception.PushServiceException;
/**
 *
 * <br>[機  能] PushServiceOperator 用 関数型インタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@FunctionalInterface
public interface IPushOperationConsumer  {
    /**
     * PushServiceOperator.doOperationで
     * PUSHサーバトークン取得に成功後に行う処理を実装する
     * 失敗時は実行されない
     *
     *
     * @param t PushServiceOperator サーバートークン取得済み
     * @exception PushServiceException 関数実行時例外 共通ログ出力対象外
     * @exception SQLException SQL実行時例外 共通ログ出力対象
     */
    void accept(IPushServiceOperator t)
            throws PushServiceException;
}
