package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.util.HashMap;

/**
 * <br>[機  能] プラグインポートレットに関するインターフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface PortletBiz {

    /**
     * <br>プラグインポートレットタイトルを取得する。
     * @param con コネクション
     * @param paramMap パラメータマップ
     * @return title ポートレットプラグインタイトル
     * @throws Exception 実行時例外
     */
    public String getPortletTitle(Connection con, HashMap<String, String> paramMap)
    throws Exception;

}
