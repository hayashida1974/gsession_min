package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.ITopMenuInfo;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.TopMenuInfo;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] TOPメニューの情報を取得するためのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TopMenuBiz {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(TopMenuBiz.class);

    /**
     * <p>Jobの実行
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param pluginConfig プラグイン情報
     * @throws SQLException SQL実行時例外
     * @return topInfoMap トップメニュー情報
     */
    public Map<String, TopMenuInfo> setTopMenu(
                            RequestModel reqMdl,
                            Connection con, PluginConfig pluginConfig
                            )
                               throws SQLException {

        HashMap<String, TopMenuInfo> topInfoMap = new HashMap<String, TopMenuInfo>();
        String listenerName = null;
        try {
            CommonBiz cmnBiz = new CommonBiz();
            ITopMenuInfo[] menuInfos
                = cmnBiz.getIMenuInfo(pluginConfig, con);
            if (menuInfos.length > 0) {
                for (ITopMenuInfo listener : menuInfos) {
                    TopMenuInfo topInfo = new TopMenuInfo();
                    if (listener.getTopMenuUrl(con, reqMdl) != null) {
                        topInfo.setUrl(listener.getTopMenuUrl(con, reqMdl));
                        topInfoMap.put(listener.getTopMenuId(), topInfo);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            log__.error("指定されたTOPメニュー実装クラスが存在しない : "
                    + NullDefault.getString(listenerName, ""), e);
        } catch (Exception e) {
            log__.error("TOPメニュー情報の取得に失敗", e);
        } catch (Throwable e) {
            log__.error("TOPメニュー情報の取得に失敗", e);
        }
        return topInfoMap;
    }


}
