package jp.groupsession.v2.cmn.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * <br>[機  能] プラグイン設定ファイル(plugin.xml)の解析を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PluginDigester {

    /**
     * プラグイン設定の読み込みを行う
     * @param filePath 設定ファイルパス
     * @return プラグイン設定
     * @throws IOException 設定ファイルパスが不正
     * @throws SAXException 設定ファイルの解析に失敗
     */
    public static Plugin createPluginConfig(String filePath)
    throws IOException, SAXException {
        Digester d = new Digester();

        d.addObjectCreate("plugin", Plugin.class);

        d.addBeanPropertySetter("plugin/id", "id");
        d.addBeanPropertySetter("plugin/short-id", "shortId");
        d.addBeanPropertySetter("plugin/name", "name");
        d.addBeanPropertySetter("plugin/name-eng", "nameEng");
        d.addBeanPropertySetter("plugin/description", "description");

        d.addObjectCreate("plugin/init-param", InitParam.class);
        d.addSetNext("plugin/init-param", "addInitParam");
        d.addBeanPropertySetter("plugin/init-param/param-name", "paramname");
        d.addBeanPropertySetter("plugin/init-param/param-value", "paramvalue");

        //リスナー
        d.addObjectCreate("plugin/listener", Listener.class);
        d.addSetNext("plugin/listener", "addListener");
        d.addBeanPropertySetter("plugin/listener/listener-name", "listenername");
        d.addBeanPropertySetter("plugin/listener/listener-class", "listenerclass");

        //バッチ処理情報
        d.addObjectCreate("plugin/batch-info", BatchInfo.class);
        d.addSetNext("plugin/batch-info", "setBatchInfo");
        d.addBeanPropertySetter("plugin/batch-info/limitation", "limitation");

        //サーバ
        d.addObjectCreate("plugin/server", Server.class);
        d.addSetNext("plugin/server", "addServer");
        d.addBeanPropertySetter("plugin/server/server-name", "serviceName");
        d.addBeanPropertySetter("plugin/server/server-class", "className");

        d.addObjectCreate("plugin/extention-info", ExtentionInfo.class);
        d.addSetNext("plugin/extention-info", "addExtentionInfo");
        d.addBeanPropertySetter("plugin/extention-info/name", "name");
        d.addBeanPropertySetter("plugin/extention-info/url", "url");
        d.addBeanPropertySetter("plugin/extention-info/description", "description");

        //メイン画面表示情報
        d.addObjectCreate("plugin/mainscreen-info", MainScreenInfo.class);
        d.addSetNext("plugin/mainscreen-info", "setMainScreenInfo");
        d.addBeanPropertySetter("plugin/mainscreen-info/view", "view");
        d.addBeanPropertySetter("plugin/mainscreen-info/id", "id");
        d.addBeanPropertySetter("plugin/mainscreen-info/position", "position");
        d.addBeanPropertySetter("plugin/mainscreen-info/order", "order");
        d.addBeanPropertySetter("plugin/mainscreen-info/loadScript", "loadScript");
        d.addBeanPropertySetter("plugin/mainscreen-info/pluginPortlet", "pluginPortlet");

        //トップメニュー情報
        d.addObjectCreate("plugin/topmenu-info", TopMenuInfo.class);
        d.addSetNext("plugin/topmenu-info", "setTopMenuInfo");
        d.addBeanPropertySetter("plugin/topmenu-info/view", "view");
        d.addBeanPropertySetter("plugin/topmenu-info/url", "url");
        d.addBeanPropertySetter("plugin/topmenu-info/order", "order");
        d.addBeanPropertySetter("plugin/topmenu-info/target", "target");
        d.addBeanPropertySetter("plugin/topmenu-info/icon", "icon");
        d.addBeanPropertySetter("plugin/topmenu-info/iconClassic", "iconClassic");

        //ヘルプ表示可否
        d.addObjectCreate("plugin/help-info", HelpInfo.class);
        d.addSetNext("plugin/help-info", "setHelpInfo");
        d.addBeanPropertySetter("plugin/help-info/view", "view");

        //メイン管理者設定表示
        d.addObjectCreate("plugin/adminsetting-info", AdminSettingInfo.class);
        d.addSetNext("plugin/adminsetting-info", "setAdminSettingInfo");
        d.addBeanPropertySetter("plugin/adminsetting-info/view", "view");
        d.addBeanPropertySetter("plugin/adminsetting-info/url", "url");
        d.addBeanPropertySetter("plugin/adminsetting-info/icon", "icon");
        d.addBeanPropertySetter("plugin/adminsetting-info/iconClassic", "iconClassic");

        //メイン個人設定表示
        d.addObjectCreate("plugin/privatesetting-info", PrivateSettingInfo.class);
        d.addSetNext("plugin/privatesetting-info", "setPrivateSettingInfo");
        d.addBeanPropertySetter("plugin/privatesetting-info/view", "view");
        d.addBeanPropertySetter("plugin/privatesetting-info/url", "url");
        d.addBeanPropertySetter("plugin/privatesetting-info/icon", "icon");
        d.addBeanPropertySetter("plugin/privatesetting-info/iconClassic", "iconClassic");

        //プラグインマネージャー設定表示
        d.addObjectCreate("plugin/managesetting-info", PluginManagerSettingInfo.class);
        d.addSetNext("plugin/managesetting-info", "setManageSettingInfo");
        d.addBeanPropertySetter("plugin/managesetting-info/view", "view");


        //ログ出力対象
        d.addObjectCreate("plugin/log-info", LogInfo.class);
        d.addSetNext("plugin/log-info", "setLogInfo");
        d.addBeanPropertySetter("plugin/log-info/output", "out");

        //プラグインポートレット情報
        d.addObjectCreate("plugin/portlet-info", PortletInfo.class);
        d.addSetNext("plugin/portlet-info", "setPortletInfo");
        d.addBeanPropertySetter("plugin/portlet-info/id", "id");
        d.addBeanPropertySetter("plugin/portlet-info/list-id", "listId");
        d.addBeanPropertySetter("plugin/portlet-info/list-nameid", "listNameId");
        d.addBeanPropertySetter("plugin/portlet-info/order", "order");
        d.addBeanPropertySetter("plugin/portlet-info/script", "script");
        d.addBeanPropertySetter("plugin/portlet-info/biz-class", "bizClass");

        Plugin bean = (Plugin) d.parse(new File(filePath));

        return bean;
    }
}
