package jp.groupsession.v2.mbh.interfaces;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] API用トークン発行実行クラスインタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IApiTokenCreater {

    /**
     * <br>[機  能] プッシュ通知 関数実行
     * <br>[解  説] 通知が利用可能な場合のみ関数が実行される
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param usrSid ユーザSID
     * @return トークン
     */
    public abstract String doCreateToken(Connection con, 
            HttpServletRequest req, RequestModel reqMdl, int usrSid);


}
