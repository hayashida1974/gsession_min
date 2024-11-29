package jp.groupsession.v2.man.man120;

/**
 * <br>[機  能] プラグイン情報(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man120Model {

    /** プラグインID */
    private String pluginId__;
    /** プラグイン名 */
    private String pluginName__;
    /** プラグイン区分 */
    private int pluginKbn__;
    /** バイナリSID */
    private Long pluginBinSid__ = new Long(0);
    /** アイコンURL */
    private String icon__ = null;
    /** アイコンURL（クラシック） */
    private String iconClassic__ = null;
    /**
     * <p>pluginBinSid を取得します。
     * @return pluginBinSid
     */
    public Long getPluginBinSid() {
        return pluginBinSid__;
    }
    /**
     * <p>pluginBinSid をセットします。
     * @param pluginBinSid pluginBinSid
     */
    public void setPluginBinSid(Long pluginBinSid) {
        pluginBinSid__ = pluginBinSid;
    }
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
     * <p>icon を取得します。
     * @return icon
     */
    public String getIcon() {
        return icon__;
    }
    /**
     * <p>icon をセットします。
     * @param icon icon
     */
    public void setIcon(String icon) {
        icon__ = icon;
    }
    /**
     * <p>iconClassic を取得します。
     * @return iconClassic
     */
    public String getIconClassic() {
        return iconClassic__;
    }
    /**
     * <p>iconClassic をセットします。
     * @param iconClassic iconClassic
     */
    public void setIconClassic(String iconClassic) {
        iconClassic__ = iconClassic;
    }
}
