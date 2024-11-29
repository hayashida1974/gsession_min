package jp.groupsession.v2.cmn.config;


/**
 * <br>[機  能] メインに表示する画面情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MainScreenInfo {

    /** 表示/非表示 */
    private String view__ = null;
    /** 画面ID */
    private String id__ = null;
    /** 表示位置 */
    private String position__ = null;
    /** 表示順 */
    private String order__ = null;
    /** 表示の際、scriptの実行を行うか */
    private boolean loadScript__ = false;
    /** ポートレットに表示を行うか デフォルト:false */
    private boolean pluginPortlet__ = false;

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
     * <p>position を取得します。
     * @return position
     */
    public String getPosition() {
        return position__;
    }
    /**
     * <p>position をセットします。
     * @param position position
     */
    public void setPosition(String position) {
        position__ = position;
    }
    /**
     * <p>view を取得します。
     * @return view
     */
    public String getView() {
        return view__;
    }
    /**
     * <p>view をセットします。
     * @param view view
     */
    public void setView(String view) {
        view__ = view;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public String getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(String order) {
        order__ = order;
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
     * <p>pluginPortlet を取得します。
     * @return pluginPortlet
     */
    public boolean isPluginPortlet() {
        return pluginPortlet__;
    }
    /**
     * <p>pluginPortlet をセットします。
     * @param pluginPortlet pluginPortlet
     */
    public void setPluginPortlet(boolean pluginPortlet) {
        pluginPortlet__ = pluginPortlet;
    }
}
