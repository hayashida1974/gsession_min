package jp.groupsession.v2.cmn.cmn003;

/**
 * <br>[機  能] メインメニューに表示する情報を保持します。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MenuInfo {

    /** プラグインID */
    private String pluginId__ = null;
    /** プラグイン名称 */
    private String name__ = null;
    /** URL */
    private String url__ = null;
    /** 別ウィンドウフラグ  0:規定 1:別ウィンドウ*/
    private int target__ = 0;
    /** プラグイン区分 0:GS 1:ユーザ作成 */
    private int pluginKbn__ = 0;
    /** パラメータ設定区分  0:設定しない 1:設定する */
    private int paramKbn__ = 0;
    /** 送信区分  0:POST形式 1:GET形式*/
    private int sendKbn__ = 0;
    /** アイコンURL */
    private String icon__ = null;
    /** アイコンURL（クラシック） */
    private String iconClassic__ = null;

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
    /** バイナリSID */
    private Long binSid__ = new Long(0);
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>target を取得します。
     * @return target
     */
    public int getTarget() {
        return target__;
    }
    /**
     * <p>target をセットします。
     * @param target target
     */
    public void setTarget(int target) {
        target__ = target;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
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
     * <p>url を取得します。
     * @return url
     */
    public String getUrl() {
        return url__;
    }
    /**
     * <p>url をセットします。
     * @param url url
     */
    public void setUrl(String url) {
        url__ = url;
    }
    /**
     * <p>paramKbn を取得します。
     * @return paramKbn
     */
    public int getParamKbn() {
        return paramKbn__;
    }
    /**
     * <p>paramKbn をセットします。
     * @param paramKbn paramKbn
     */
    public void setParamKbn(int paramKbn) {
        paramKbn__ = paramKbn;
    }
    /**
     * <p>sendKbn を取得します。
     * @return sendKbn
     */
    public int getSendKbn() {
        return sendKbn__;
    }
    /**
     * <p>sendKbn をセットします。
     * @param sendKbn sendKbn
     */
    public void setSendKbn(int sendKbn) {
        sendKbn__ = sendKbn;
    }
    /**
     * <p>icon を取得します。
     * @return icon
     * @see jp.groupsession.v2.cmn.cmn003.MenuInfo#icon__
     */
    public String getIcon() {
        return icon__;
    }
    /**
     * <p>icon をセットします。
     * @param icon icon
     * @see jp.groupsession.v2.cmn.cmn003.MenuInfo#icon__
     */
    public void setIcon(String icon) {
        icon__ = icon;
    }
    /**
     * <p>iconClassic を取得します。
     * @return iconClassic
     * @see jp.groupsession.v2.cmn.cmn003.MenuInfo#iconClassic__
     */
    public String getIconClassic() {
        return iconClassic__;
    }
    /**
     * <p>iconClassic をセットします。
     * @param iconClassic iconClassic
     * @see jp.groupsession.v2.cmn.cmn003.MenuInfo#iconClassic__
     */
    public void setIconClassic(String iconClassic) {
        iconClassic__ = iconClassic;
    }

}
