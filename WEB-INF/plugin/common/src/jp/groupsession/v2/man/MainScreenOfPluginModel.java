package jp.groupsession.v2.man;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] メインに表示する画面情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MainScreenOfPluginModel extends AbstractModel
implements Comparable<MainScreenOfPluginModel> {

    /** 表示位置 左 */
    public static final int POSITION_LEFT = 1;
    /** 表示位置 右 */
    public static final int POSITION_RIGHT = 2;
    /** 表示位置 上 */
    public static final int POSITION_TOP = 3;
    /** 表示位置 下 */
    public static final int POSITION_BOTTOM = 4;
    /** 表示位置 中 */
    public static final int POSITION_CENTER = 5;

    /** compareTo(Object o)メソッド実施時、並び順がoより大きい場合に返されます */
    public static final int LARGE = 1;
    /** compare(Object o)メソッド実施時、並び順がoと等しい場合に返されます */
    public static final int EQUAL = 0;
    /** compare(Object o)メソッド実施時、並び順がoがoより小さい場合に返されます */
    public static final int SMALL = -1;

    /** プラグインID */
    private String pluginId__ = null;
    /** プラグイン名称 */
    private String pluginName__ = null;
    /** 画面URL */
    private String screenUrl__ = null;
    /** javascriptパス */
    private String scriptPath__ = null;
    /** stylesheetパス */
    private String stylePath__ = null;
    /** 表示位置 */
    private int position__ = POSITION_LEFT;
    /** 表示順 */
    private int order__ = -1;
    /** ID */
    private String id__ = null;
    /** 表示の際、scriptの実行を行うか */
    private boolean loadScript__ = false;

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * <p>pluginName を取得します。
     * @return pluginName
     */
    public String getPluginName() {
        return pluginName__;
    }
    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }
    /**
     * <p>screenUrl を取得します。
     * @return screenUrl
     */
    public String getScreenUrl() {
        return screenUrl__;
    }
    /**
     * <p>screenUrl をセットします。
     * @param screenUrl screenUrl
     */
    public void setScreenUrl(String screenUrl) {
        screenUrl__ = screenUrl;
    }
    /**
     * <p>scriptPath を取得します。
     * @return scriptPath
     */
    public String getScriptPath() {
        return scriptPath__;
    }
    /**
     * <p>scriptPath をセットします。
     * @param scriptPath scriptPath
     */
    public void setScriptPath(String scriptPath) {
        scriptPath__ = scriptPath;
    }
    /**
     * <p>stylePath を取得します。
     * @return stylePath
     */
    public String getStylePath() {
        return stylePath__;
    }
    /**
     * <p>stylePath をセットします。
     * @param stylePath stylePath
     */
    public void setStylePath(String stylePath) {
        stylePath__ = stylePath;
    }
    /**
     * <p>position を取得します。
     * @return position
     */
    public int getPosition() {
        return position__;
    }
    /**
     * <p>position をセットします。
     * @param position position
     */
    public void setPosition(int position) {
        position__ = position;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
    }
    /**
     * <p>id を取得します。
     * @return id
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>loadScript を取得します。
     * @return loadScript
     */
    public boolean isLoadScript() {
        return loadScript__;
    }
    /**
     * <p>loadScript をセットします。
     * @param loadScript loadScript
     */
    public void setLoadScript(boolean loadScript) {
        loadScript__ = loadScript;
    }
    /**
     * <br>[機  能] このオブジェクトと指定されたオブジェクトの順序を比較します。
     * <br>[解  説] 表示順を元に比較を行います。
     * <br>[備  考]
     *
     * @param o 比較対象のオブジェクト
     * @return 比較結果
     */
    public int compareTo(MainScreenOfPluginModel o) {
        int result = EQUAL;

        int order1 = getOrder();
        int order2 = o.getOrder();
        if (order1 > order2) {
            result = LARGE;
        } else if (order1 < order2) {
            result = SMALL;
        }

        return result;
    }
}
