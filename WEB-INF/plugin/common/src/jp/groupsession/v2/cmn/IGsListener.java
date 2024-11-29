package jp.groupsession.v2.cmn;

import java.sql.Connection;

/**
 * <br>[機  能] Servlet init() 又はdestroy()実行時、またはに実行されるリスナー
 * <br>[解  説]
 * <br>[備  考] DBのコミット、ロールバック処理は各自実行すること。
 *
 * @author JTS
 */
public interface IGsListener {

    /**
     * <p>init()実行時に実行される
     * @param gscontext GS共通情報
     * @param con DBコネクション
     * @param domain ドメイン
     * @throws Exception 実行例外
     */
    public void gsInit(GSContext gscontext, Connection con, String domain) throws Exception;

    /**
     * <p>destroy()実行時に実行される
     * @param gscontext GS共通情報
     * @param con DBコネクション
     * @param domain ドメイン
     * @throws Exception 実行例外
     */
    public void gsDestroy(GSContext gscontext, Connection con, String domain) throws Exception;
}