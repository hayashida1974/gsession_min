package jp.groupsession.v2.cmn.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プラグインを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Plugin {

    /** プラグインID */
    private String id__ = null;
    /** プラグインID(略称) */
    private String shortId__ = null;
    /** 名称 */
    private String name__ = null;
    /** 名称(英語) */
    private String nameEng__ = null;
    /** 説明 */
    private String description__ = null;
    /** プラグイン区分 0:GS 1:ユーザ作成 */
    private int pluginKbn__ = 0;
    /** リクエストに付加するパラメータ */
    private Map < String, InitParam > initParams__ = null;
    /** リスナー */
    private Map < String, Listener > listeners__ = null;
    /** バッチ処理情報 */
    private BatchInfo batchInfo__ = null;
    /** サーバ */
    private Map < String, Server > servers__ = null;
    /** 拡張ポイント */
    private Map < String, ExtentionInfo > extentionInfos__ = null;
    /** メイン画面表示 */
    private ArrayList<MainScreenInfo> mainScreenInfo__ = null;
    /** トップメニュー情報 */
    private TopMenuInfo topMenuInfo__ = null;
    /** ヘルプ情報 */
    private HelpInfo helpInfo__ = null;
    /** メイン管理者設定メニュー */
    private AdminSettingInfo adminSettingInfo__ = null;
    /** メイン個人設定メニュー */
    private PrivateSettingInfo privateSettingInfo__ = null;
    /** ログ出力情報 */
    private LogInfo logInfo__ = null;
    /** プラグインポートレット情報 */
    private ArrayList<PortletInfo> portletInfo__ = null;
    /** プラグインマネージャー設定メニュー */
    private PluginManagerSettingInfo manageSettingInfo__ = null;


    /**
     * <br>[機  能] プラグイン情報のコピーを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return プラグイン情報
     */
    public Plugin clonePlugin(RequestModel reqMdl) {
        Plugin plugin = new Plugin();
        plugin.setId(getId());
        plugin.setShortId(getShortId());
        plugin.setName(getName(reqMdl));
        plugin.setNameEng(getNameEng());
        plugin.setDescription(getDescription());
        plugin.setPluginKbn(getPluginKbn());

        try {

            //リクエストに付加するパラメータ
            Iterator<String> keys = initParams__.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                InitParam initParam = new InitParam();
                __copyProperties(key, initParams__, initParam);
                plugin.addInitParam(initParam);
            }

            //リスナー
            keys = listeners__.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                Listener listener = new Listener();
                __copyProperties(key, listeners__, listener);
                plugin.addListener(listener);
            }

            //バッチ処理情報
            if (batchInfo__ != null) {
                BatchInfo batchInfo = new BatchInfo();
                BeanUtils.copyProperties(batchInfo, batchInfo__);
                plugin.setBatchInfo(batchInfo);
            }

            //サーバ
            keys = servers__.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                Server server = new Server();
                __copyProperties(key, servers__, server);

                plugin.addServer(server);
            }

            //拡張ポイント
            keys = extentionInfos__.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                ExtentionInfo extention = new ExtentionInfo();
                __copyProperties(key, extentionInfos__, extention);

                plugin.addExtentionInfo(extention);
            }

            //メイン画面表示
            for (MainScreenInfo copyFrom : mainScreenInfo__) {
                MainScreenInfo info = new MainScreenInfo();
                BeanUtils.copyProperties(info, copyFrom);
                plugin.setMainScreenInfo(info);
            }

            //トップメニュー情報
            if (topMenuInfo__ != null) {
                TopMenuInfo topMenuInfo = new TopMenuInfo();
                BeanUtils.copyProperties(topMenuInfo, topMenuInfo__);
                plugin.setTopMenuInfo(topMenuInfo);
            }

            //ヘルプ情報
            if (helpInfo__ != null) {
                HelpInfo helpInfo = new HelpInfo();
                BeanUtils.copyProperties(helpInfo, helpInfo__);
                plugin.setHelpInfo(helpInfo);
            }

            //メイン管理者設定メニュー
            if (adminSettingInfo__ != null) {
                AdminSettingInfo adminSettingInfo = new AdminSettingInfo();
                BeanUtils.copyProperties(adminSettingInfo, adminSettingInfo__);
                plugin.setAdminSettingInfo(adminSettingInfo);
            }

            //メイン個人設定メニュー
            if (privateSettingInfo__ != null) {
                PrivateSettingInfo privateSettingInfo = new PrivateSettingInfo();
                BeanUtils.copyProperties(privateSettingInfo, privateSettingInfo__);
                plugin.setPrivateSettingInfo(privateSettingInfo);
            }

            //プラグインマネージャー表示設定
            if (manageSettingInfo__ != null) {
                PluginManagerSettingInfo manageSettingInfo = new PluginManagerSettingInfo();
                BeanUtils.copyProperties(manageSettingInfo, manageSettingInfo__);
                plugin.setManageSettingInfo(manageSettingInfo);
            }

            //ログ出力情報
            if (logInfo__ != null) {
                LogInfo logInfo = new LogInfo();
                BeanUtils.copyProperties(logInfo, logInfo__);
                plugin.setLogInfo(logInfo);
            }

            //プラグインポートレット情報
            for (PortletInfo copyFrom : portletInfo__) {
                PortletInfo portletInfo = new PortletInfo();
                BeanUtils.copyProperties(portletInfo, copyFrom);
                plugin.setPortletInfo(portletInfo);
            }

            } catch (IllegalAccessException e) {
                plugin = null;
            } catch (InvocationTargetException e) {
                plugin = null;
            }

            return plugin;
    }

    /**
     * <p> プロパティのコピーを行う
     * @param key KEY
     * @param copyFromMap コピー元Map
     * @param copyTo コピー先
     * @throws IllegalAccessException プロパティのコピーに失敗
     * @throws InvocationTargetException プロパティのコピーに失敗
     */
    @SuppressWarnings("all")
    private void __copyProperties(String key, Map copyFromMap, Object copyTo)
    throws IllegalAccessException, InvocationTargetException {
        BeanUtils.copyProperties(copyTo, copyFromMap.get(key));
    }

    /**
     * @return logInfo
     */
    public LogInfo getLogInfo() {
        return logInfo__;
    }

    /**
     * @param logInfo セットする logInfo
     */
    public void setLogInfo(LogInfo logInfo) {
        logInfo__ = logInfo;
    }

    /**
     * <p>adminSettingInfo を取得します。
     * @return adminSettingInfo
     */
    public AdminSettingInfo getAdminSettingInfo() {
        return adminSettingInfo__;
    }

    /**
     * <p>adminSettingInfo をセットします。
     * @param adminSettingInfo adminSettingInfo
     */
    public void setAdminSettingInfo(AdminSettingInfo adminSettingInfo) {
        adminSettingInfo__ = adminSettingInfo;
    }

    /**
     * <p>helpInfo を取得します。
     * @return helpInfo
     */
    public HelpInfo getHelpInfo() {
        return helpInfo__;
    }

    /**
     * <p>helpInfo をセットします。
     * @param helpInfo helpInfo
     */
    public void setHelpInfo(HelpInfo helpInfo) {
        helpInfo__ = helpInfo;
    }

    /**
     * コンストラクタ
     */
    public Plugin() {
        initParams__ = new HashMap <String, InitParam>();
        listeners__ = new HashMap<String, Listener>();
        batchInfo__ = new BatchInfo();
        servers__ = new HashMap<String, Server>();
        extentionInfos__ = new HashMap<String, ExtentionInfo>();
        mainScreenInfo__ = new ArrayList<MainScreenInfo>();
        adminSettingInfo__ = new AdminSettingInfo();
        privateSettingInfo__ = new PrivateSettingInfo();
        logInfo__ = new LogInfo();
        portletInfo__ = new ArrayList<PortletInfo>();
    }

    /**
     * <p>batchInfo を取得します。
     * @return batchInfo
     */
    public BatchInfo getBatchInfo() {
        return batchInfo__;
    }
    /**
     * <p>batchInfo をセットします。
     * @param batchInfo batchInfo
     */
    public void setBatchInfo(BatchInfo batchInfo) {
        batchInfo__ = batchInfo;
    }
    /**
     * @return description を戻します。
     */
    public String getDescription() {
        return description__;
    }
    /**
     * @param description 設定する description。
     */
    public void setDescription(String description) {
        description__ = description;
    }
    /**
     * @return id を戻します。
     */
    public String getId() {
        return id__;
    }
    /**
     * @param id 設定する id。
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>shortId を取得します。
     * @return shortId
     * @see jp.groupsession.v2.cmn.config.Plugin#shortId__
     */
    public String getShortId() {
        return shortId__;
    }

    /**
     * <p>shortId をセットします。
     * @param shortId shortId
     * @see jp.groupsession.v2.cmn.config.Plugin#shortId__
     */
    public void setShortId(String shortId) {
        shortId__ = shortId;
    }
    /**
     * <p>言語の文字列を取得する
     * @param reqMdl リクエスト情報
     * @return name を戻します。
     */
    public String getName(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage();
        return __getLanguageName(gsMsg.getLocale(reqMdl));
    }

    /**
     * @param name 設定する name。
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>nameEng を取得します。
     * @return nameEng
     */
    public String getNameEng() {
        return nameEng__;
    }

    /**
     * <p>nameEng をセットします。
     * @param nameEng nameEng
     */
    public void setNameEng(String nameEng) {
        nameEng__ = nameEng;
    }

    /**
     * @param initparam リクエストパラメータ
     */
    public void addInitParam(InitParam initparam) {
        initParams__.put(initparam.getParamname(), initparam);
    }
    /**
     * @param listener リスナー
     */
    public void addListener(Listener listener) {
        listeners__.put(listener.getListenername(), listener);
    }
    /**
     * @param server サーバ
     */
    public void addServer(Server server) {
        servers__.put(server.getServiceName(), server);
    }
    /**
     * @param extentionInfo 拡張ポイント
     */
    public void addExtentionInfo(ExtentionInfo extentionInfo) {
        extentionInfos__.put(extentionInfo.getName(), extentionInfo);
    }

    /**
     * @return extentionInfos を戻します。
     */
    public Map < String, ExtentionInfo > getExtentionInfos() {
        return extentionInfos__;
    }

    /**
     * <p>mainScreenInfo を取得します。
     * @return mainScreenInfo
     */
    public ArrayList<MainScreenInfo> getMainScreenInfo() {
        return mainScreenInfo__;
    }

    /**
     * <p>mainScreenInfo をセットします。
     * @param mainScreenInfo mainScreenInfo
     */
    public void setMainScreenInfo(MainScreenInfo mainScreenInfo) {
        mainScreenInfo__.add(mainScreenInfo);
    }

    /**
     * <p>topMenuInfo を取得します。
     * @return topMenuInfo
     */
    public TopMenuInfo getTopMenuInfo() {
        return topMenuInfo__;
    }

    /**
     * <p>topMenuInfo をセットします。
     * @param topMenuInfo topMenuInfo
     */
    public void setTopMenuInfo(TopMenuInfo topMenuInfo) {
        topMenuInfo__ = topMenuInfo;
    }

    /**
     * <p>pluginKbn を取得します。
     * @return pluginKbn
     */
    public int getPluginKbn() {
        return pluginKbn__;
    }

    /**
     * <p>pluginKbn をセットします。
     * @param pluginKbn pluginKbn
     */
    public void setPluginKbn(int pluginKbn) {
        pluginKbn__ = pluginKbn;
    }

    /**
     * <p>portletInfo を取得します。
     * @return portletInfo
     */
    public ArrayList<PortletInfo> getPortletInfo() {
        return portletInfo__;
    }

    /**
     * <p>portletInfo をセットします。
     * @param portletInfo portletInfo
     */
    public void setPortletInfo(PortletInfo portletInfo) {
        portletInfo__.add(portletInfo);
    }

    /**
     * 指定されたリスナー名のリスナークラスを返します
     * @param name リスナー名
     * @return リスナークラス
     */
    public String getListenerClass(String name) {
        String listenerclass = null;

        Listener listener =  listeners__.get(name);
        if (listener != null) {
            listenerclass = listener.getListenerclass();
        }

        return listenerclass;
    }

    /**
     * 指定されたサーバサービス名のサーバクラスを返します
     * @param name リスナー名
     * @return リスナークラス
     */
    public String getServerClass(String name) {
        String serverclass = null;

        Server server =  servers__.get(name);
        if (server != null) {
            serverclass = server.getClassName();
        }

        return serverclass;
    }

    /**
     * サーバサービス名のサーバクラスを全て返します
     * @return リスナークラス
     */
    public String[] getServerClassNames() {
        List < String > serverClassNames = new ArrayList<String>();

        if (servers__.isEmpty()) {
            return null;
        }

        Iterator<Entry<String, Server>> iter = servers__.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Server> entry = iter.next();
            Server value = (Server) entry.getValue();
            serverClassNames.add(value.getClassName());
        }
        return (String[]) serverClassNames.toArray(new String[serverClassNames.size()]);
    }

    /**
     * <br>[機  能] メニューに表示するプラグインとして設定されているかを判定します。
     * <br>[解  説]
     * <br>[備  考] plugin.xml内の設定のみで判定を行います。
     * <br>         使用する側でCMN_TDISPに登録されている情報も参照してください。
     * @return 判定結果 true:メニューに表示する false:メニューに表示しない
     */
    public boolean isMenuPlugin() {
        boolean result = false;

        if (topMenuInfo__ != null
        && NullDefault.getString(topMenuInfo__.getView(), "false").equals("true")
        && !StringUtil.isNullZeroString(topMenuInfo__.getUrl())) {
            result = true;
        }

        return result;
    }

    /**
     * <br>[機  能] メイン画面に表示するプラグインとして設定されているかを判定します。
     * <br>[解  説]
     * <br>[備  考] plugin.xml内の設定のみで判定を行います。
     * <br>         使用する側でCMN_TDISPに登録されている情報も参照してください。
     * @return 判定結果 true:メイン画面に表示する false:メイン画面に表示しない
     */
    public boolean isMainScreenPlugin() {
        boolean result = false;

        if (mainScreenInfo__ != null && !mainScreenInfo__.isEmpty()) {
            for (int i = 0; i < mainScreenInfo__.size(); i++) {
                MainScreenInfo screenInfo = mainScreenInfo__.get(i);
                if (NullDefault.getString(
                        screenInfo.getView(), "false").equals("true")) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
    /**
     * <br>[機  能] プラグインマネージャ画面に表示するプラグインとして設定されているかを判定します。
     * <br>[解  説] 未設定は表示するとして判定します。不正な設定値の場合は非表示として判定します。
     * <br>[備  考] plugin.xml内の設定のみで判定を行います。
     * <br>
     * @return 判定結果 true:プラグインマネージャ画面に表示する false:プラグインマネージャ画面に表示しない
     */
    public boolean isViewPluginManager() {
        if (manageSettingInfo__ == null) {
            return true;
        }
        String strView = manageSettingInfo__.getView();
        if (StringUtil.isNullZeroString(strView)) {
            return true;
        }
        if ("true".equals(manageSettingInfo__.getView())) {
            return true;
        }
        return false;
    }
    /**
     * @return listeners を戻します。
     */
    public Map < String, Listener > getListeners() {
        return listeners__;
    }

    /**
     * @return initParams を戻します。
     */
    public Map < String, InitParam > getInitParams() {
        return initParams__;
    }

    /**
     * <p>privateSettingInfo を取得します。
     * @return privateSettingInfo
     */
    public PrivateSettingInfo getPrivateSettingInfo() {
        return privateSettingInfo__;
    }

    /**
     * <p>privateSettingInfo をセットします。
     * @param privateSettingInfo privateSettingInfo
     */
    public void setPrivateSettingInfo(PrivateSettingInfo privateSettingInfo) {
        privateSettingInfo__ = privateSettingInfo;
    }

    /**
     * <p>manageSettingInfo を取得します。
     * @return manageSettingInfo
     * @see jp.groupsession.v2.cmn.config.Plugin#manageSettingInfo__
     */
    public PluginManagerSettingInfo getManageSettingInfo() {
        return manageSettingInfo__;
    }

    /**
     * <p>manageSettingInfo をセットします。
     * @param manageSettingInfo manageSettingInfo
     * @see jp.groupsession.v2.cmn.config.Plugin#manageSettingInfo__
     */
    public void setManageSettingInfo(PluginManagerSettingInfo manageSettingInfo) {
        manageSettingInfo__ = manageSettingInfo;
    }

    /**
     * <p>言語の文字列を取得する
     * @param country 国コード
     * @return resourceKey
     */
    private String __getLanguageName(String country) {

        String name = name__;

        if (!StringUtil.isNullZeroString(country)) {
            if (country.equals("JP")) {
                name = name__;
            } else if (country.equals("US")
                    || country.equals("BK")
                    || country.equals("AU")) {
                if (!StringUtil.isNullZeroString(nameEng__)) {
                    name = nameEng__;
                }
            }
        }
        return name;
    }
}
