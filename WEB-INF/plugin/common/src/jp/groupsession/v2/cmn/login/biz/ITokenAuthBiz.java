package jp.groupsession.v2.cmn.login.biz;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.model.base.IApiConfModel;
/**
 *
 * <br>[機  能] トークン認証処理インタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ITokenAuthBiz {

    /**
     *
     * <br>[機  能] トークン認証
     * <br>[解  説]
     * <br>[備  考]
     * @param token トークン文字列
     * @param con コネクション
     * @return 認証結果
     * @throws SQLException SQL実行時例外
     */
    LoginResultModel authToken(String token, Connection con)
            throws SQLException;

    /**
     *
     * <br>[機  能] トークン認証使用判定
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return true:使用する
     * @throws SQLException SQL実行時例外
     */
    boolean isAbleTokenAuth(
            HttpServletRequest req, Connection con) throws SQLException;

    /**
     *
     * <br>[機  能] トークンの発行処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel ログインユーザ
     * @param aconf Api基本設定モデル
     * @return トークン
     * @throws SQLException SQL実行時例外
     */
    String createToken(HttpServletRequest req, BaseUserModel smodel,
            IApiConfModel aconf) throws SQLException;






}