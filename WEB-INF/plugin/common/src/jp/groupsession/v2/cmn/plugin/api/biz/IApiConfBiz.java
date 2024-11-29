package jp.groupsession.v2.cmn.plugin.api.biz;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.model.base.IApiConfModel;
/**
 *
 * <br>[機  能] API基本設定機能インタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IApiConfBiz {

    /**
     *
     * <br>[機  能] 基本設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 基本設定
     * @throws SQLException SQL実行時例外
     */
    IApiConfModel getConf(Connection con) throws SQLException;
    /**
    *
    * <br>[機  能] 基本設定を保管
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param conf 保管設定モデル
    * @throws SQLException SQL実行時例外
    */
    void saveConf(Connection con, IApiConfModel conf) throws SQLException;
    /**
    *
    * <br>[機  能] トークン認証使用判定を行う
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
   * <br>[機  能] トークン認証使用判定を行う
   * <br>[解  説]
   * <br>[備  考]
   * @param req リクエスト
   * @param con コネクション
   * @return true:使用する
   * @throws SQLException SQL実行時例外
   */
  boolean isAbleBasicAuth(
          HttpServletRequest req, Connection con) throws SQLException;

}