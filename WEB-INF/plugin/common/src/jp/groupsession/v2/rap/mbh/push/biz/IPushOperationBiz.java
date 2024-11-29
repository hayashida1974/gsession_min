package jp.groupsession.v2.rap.mbh.push.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.rap.mbh.push.model.PushTokenModel;

/**
 * <br>[機  能] Push通知に関する各種操作クラスのインタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IPushOperationBiz {


    /**
     * <br>[機  能] 指定したユーザの端末トークン情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param APP_ID アプリケーションID
     * @return 端末トークン情報
     * @throws SQLException SQL実行時例外
     */
    public List<PushTokenModel> getUserTokenList(Connection con, int usrSid, String APP_ID) throws SQLException;


    /**
     * <br>[機  能] 指定したトークン情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param tokenList 削除対象となるトークンの一覧
     * @throws SQLException SQL実行時例外
     */
    public void deleteToken(Connection con, List<String> tokenList) throws SQLException;

}
