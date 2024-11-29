package jp.groupsession.v2.cmn.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnLogConfModel;

/**
 * <br>[機  能] オペレーションログ設定情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LoggingConfig {

    /** プラグインID一覧 */
    private List < String > pluginIdList__ = null;
    /** プラグイン別のログ出力設定保持用Map */
    private Map < String, CmnLogConfModel > logConfMap__ = null;

    /**
     * コンストラクタ
     */
    public LoggingConfig() {
        pluginIdList__ = new ArrayList<String>();
        logConfMap__ = new HashMap<String, CmnLogConfModel>();
    }

    /**
     * オペレーションログ設定情報一覧を取得する
     * @return オペレーションログ設定情報一覧
     */
    public List < CmnLogConfModel > getPluginDataList() {
        List<CmnLogConfModel> pluginDataList = new ArrayList<CmnLogConfModel>();
        for (String pluginId : getPluginIdList()) {
            pluginDataList.add(logConfMap__.get(pluginId));
        }
        return pluginDataList;
    }

    /**
     * プラグインID一覧を取得する
     * @return プラグインID一覧
     */
    public synchronized List < String > getPluginIdList() {

        List<String> userPlist = new ArrayList<String>();

        if (pluginIdList__ != null && !pluginIdList__.isEmpty()) {
            //ユーザ毎の別なインスタンスを作成
            userPlist.addAll(pluginIdList__);
            //メインの順序を1番目に設定
            if (userPlist.indexOf(GSConst.PLUGINID_MAIN) >= 0) {
                userPlist.remove(GSConst.PLUGINID_MAIN);
                userPlist.add(0, GSConst.PLUGINID_MAIN);
            }

            //ユーザ情報を2番目に表示する
            if (userPlist.indexOf(GSConst.PLUGINID_USER) > 0) {
                userPlist.remove(GSConst.PLUGINID_USER);
                userPlist.add(1, GSConst.PLUGINID_USER);
            }
        }

        return userPlist;
    }

    /**
     * プラグイン情報の追加を行う
     * @param conf プラグイン情報
     */
    public void addLogConf(CmnLogConfModel conf) {
        pluginIdList__.add(conf.getLgcPlugin());
        logConfMap__.put(conf.getLgcPlugin(), conf);
    }

    /**
     * 指定したIDのプラグイン情報を取得する
     * @param pluginId プラグインID
     * @return プラグイン情報
     */
    public CmnLogConfModel getPlugin(String pluginId) {
        return logConfMap__.get(pluginId);
    }

}