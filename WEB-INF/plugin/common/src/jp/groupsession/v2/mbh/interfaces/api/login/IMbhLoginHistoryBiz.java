package jp.groupsession.v2.mbh.interfaces.api.login;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] モバイルアプリログイン処理 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IMbhLoginHistoryBiz {
    
    /**
    *
    * <br>[機  能] ログイン履歴の保管
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエスト
    * @param smodel セッションユーザ
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @throws Exception SQL実行時例外
    */
   public void writeLoginHistry(HttpServletRequest req,
         BaseUserModel smodel, Connection con, RequestModel reqMdl) throws Exception;
}