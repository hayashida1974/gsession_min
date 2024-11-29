package jp.groupsession.v2.cmn;

import java.sql.Connection;

import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] トップメニューのURLを取得する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ITopMenuInfo {

    /**
     * <br>[機  能] トップメニューのプラグインIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return id プラグインID
     */
    public String getTopMenuId();
    /**
     * <br>[機  能] トップメニューのURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return URL　トップメニューのURL
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws Exception URL取得失敗時に例外発生
     */
    public String getTopMenuUrl(Connection con, RequestModel reqMdl) throws Exception;
}