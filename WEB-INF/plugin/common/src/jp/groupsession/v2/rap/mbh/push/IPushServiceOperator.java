package jp.groupsession.v2.rap.mbh.push;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.model.PushRequestModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rap.mbh.push.api.IPushApiRequest;

/**
 * <br>[機  能] Push通知実行クラスインタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IPushServiceOperator {
    /**
     * <br>[機  能] プッシュ通知の使用可否判定
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用可否判定
     */
    public abstract boolean isUseable();
    /**
     * <br>[機  能] プッシュ通知APIリクエスト実行
     * <br>[解  説] 通知が利用可能な場合のみAPIが実行される
     * <br>[備  考]
     * @param pushRequest リクエスト
     * @return レスポンス
     */
    public abstract String api(IPushApiRequest pushRequest);


    /**
     * <br>[機  能] プッシュ通知 関数実行
     * <br>[解  説] 通知が利用可能な場合のみ関数が実行される
     * <br>[備  考] 関数はバックグラウンドで実行されるため関数外で取得したコネクションを利用したDBアクセスは使用できない
     * @param function 関数型インタフェース
     * @return 使用可否判定
     */
    public abstract IPushServiceOperator doOperation(IPushOperationConsumer function);

    /**
     * ライセンス更新時に実行。旧ライセンスで発行済みjwtトークンを破棄する
     * @param con
     * @throws SQLException
     */
    public void removeJwt(Connection con) throws SQLException;
    /**
     * <br>[機  能] プッシュ通知実行
     * <br>[解  説] 通知が利用可能な場合のみ通知メッセージがバックグラウンドで一括送信される
     * <br>[備  考] モバイル利用ができないユーザへは送信されない
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param pushMdlList プッシュリクエストモデルリスト
     * @param pluginId プラグインID
     * @return 使用可否判定
     * @throws SQLException
     */
    public abstract void sendMessage(Connection con, RequestModel reqMdl,
            List<PushRequestModel> pushMdlList, String pluginId) throws SQLException;


}
