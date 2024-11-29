package jp.groupsession.v2.man;


/**
 * <br>[機  能] メインに表示するインフォメッセージの情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MainInfoMessageModel {

    /** プラグインID */
    public String pluginId__ = null;
    /** プラグイン名 */
    public String pluginName__ = null;
    /** 旧メッセージ */
    public String message__ = null;
    /** 新メッセージ */
    public String originalMessage__ = null;
    /** リンクURL */
    public String linkUrl__ = null;
    /** POPUP区分 */
    public boolean popupDsp__ = false;
    /** アイコンURL */
    private String icon__ = null;

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * @return the icon
     */
    public String getIcon() {
        return icon__;
    }
    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        icon__ = icon;
    }
    /**
     * @return the popupDsp
     */
    public boolean isPopupDsp() {
        return popupDsp__;
    }
    /**
     * @param popupDsp the popupDsp to set
     */
    public void setPopupDsp(boolean popupDsp) {
        popupDsp__ = popupDsp;
    }
    /**
     * <p>linkUrl を取得します。
     * @return linkUrl
     */
    public String getLinkUrl() {
        return linkUrl__;
    }
    /**
     * <p>linkUrl をセットします。
     * @param linkUrl linkUrl
     */
    public void setLinkUrl(String linkUrl) {
        linkUrl__ = linkUrl;
    }
    /**
     * <p>message を取得します。
     * @return message
     */
    public String getMessage() {
        return message__;
    }
    /**
     * <p>message をセットします。
     * @param message message
     */
    public void setMessage(String message) {
        message__ = message;
    }
    /**
     * <p>originalMessage を取得します。
     * @return originalMessage
     */
    public String getOriginalMessage() {
        return originalMessage__;
    }
    /**
     * <p>originalMessage をセットします。
     * 5.0.0 新デザイン系で使用されるメッセージのこと
     * @param originalMessage originalMessage
     */
    public void setOriginalMessage(String originalMessage) {
        originalMessage__ = originalMessage;
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
}
