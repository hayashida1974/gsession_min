package jp.groupsession.v2.man.man240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnLogConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man240Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man240Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param pconfig プラグインコンフィグ
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Man240ParamModel paramMdl, PluginConfig pconfig,
                            RequestModel reqMdl)
    throws SQLException {
        log__.debug("初期表示");

        CmnLogConfDao logConfDao = new CmnLogConfDao(con);
        List<CmnLogConfModel> logConfList = new ArrayList<CmnLogConfModel>();
        List<Man240BaseForm> dspList = new ArrayList<Man240BaseForm>();
        Man240BaseForm model = null;

        PluginConfig logPluginConfig = __getPluginConfigForLog(pconfig, con);

        //プラグインID一覧を取得する。
        List <String> pluginIdList = logPluginConfig.getPluginIdList();

        if (pluginIdList != null && pluginIdList.size() > 0) {
            //オペレーションログ設定を取得する。
            logConfList = logConfDao.select(pluginIdList);
        }

        //プラグイン情報一覧を取得する。
        List < Plugin > pluginDataList = logPluginConfig.getPluginDataList();

        if (pluginDataList == null || pluginDataList.size() < 1) {
            return;
        }

        //その他プラグイン
        for (Plugin plugin : pluginDataList) {

            for (CmnLogConfModel logConfModel : logConfList) {

                if (logConfModel.getLgcPlugin().equals(plugin.getId())) {
                    model = new Man240BaseForm();
                    model.setPluginName(plugin.getName(reqMdl));
                    model.setLgcPlugin(logConfModel.getLgcPlugin());
                    model.setLgcLevelError(String.valueOf(logConfModel.getLgcLevelError()));
                    model.setLgcLevelWarn(String.valueOf(logConfModel.getLgcLevelWarn()));
                    model.setLgcLevelInfo(String.valueOf(logConfModel.getLgcLevelInfo()));
                    model.setLgcLevelTrace(String.valueOf(logConfModel.getLgcLevelTrace()));
                    dspList.add(model);

                    break;
                }
            }
        }

        paramMdl.setMan240LogConfList(dspList);
    }

    /**
     * ログ出力対象のPluginConfigを取得します。
     * @param pconfig プラグイン設定
     * @param con コネクション
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    private PluginConfig __getPluginConfigForLog(PluginConfig pconfig, Connection con)
    throws SQLException {

        PluginConfig ret = new PluginConfig();
        //管理者設定を取得
        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<String> menuPluginIdList = tdispDao.getMenuPluginIdList(GSConst.SYSTEM_USER_ADMIN);

        Plugin plugin = pconfig.getPlugin(GSConst.PLUGINID_COMMON);
        if (plugin != null) {
            ret.addPlugin(plugin);
        }
        for (String pId : menuPluginIdList) {
            if (pId.equals(GSConst.PLUGINID_COMMON) || pId.equals("license")) {
                continue;
            }
            plugin = pconfig.getPlugin(pId);
            if (plugin != null && plugin.getLogInfo().isOut()) {
                ret.addPlugin(plugin);
            }
        }
        return ret;
    }
}