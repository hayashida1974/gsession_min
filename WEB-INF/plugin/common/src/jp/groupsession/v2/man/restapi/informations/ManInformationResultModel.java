package jp.groupsession.v2.man.restapi.informations;

/**
 *
 * <br>[機  能] インフォメーション情報レスポンス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ManInformationResultModel {
    /** プラグインID*/
    private String pluginId__;
    /** メッセージ*/
    private String messageText__;
    /** url*/
    private String urlText__;
    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see jp.groupsession.v2.man.restapi.informations.ManInformationResultModel#pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see jp.groupsession.v2.man.restapi.informations.ManInformationResultModel#pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * <p>messageText を取得します。
     * @return messageText
     * @see jp.groupsession.v2.man.restapi.informations.ManInformationResultModel#messageText__
     */
    public String getMessageText() {
        return messageText__;
    }
    /**
     * <p>messageText をセットします。
     * @param messageText messageText
     * @see jp.groupsession.v2.man.restapi.informations.ManInformationResultModel#messageText__
     */
    public void setMessageText(String messageText) {
        messageText__ = messageText;
    }
    /**
     * <p>urlText を取得します。
     * @return urlText
     * @see jp.groupsession.v2.man.restapi.informations.ManInformationResultModel#urlText__
     */
    public String getUrlText() {
        return urlText__;
    }
    /**
     * <p>urlText をセットします。
     * @param urlText urlText
     * @see jp.groupsession.v2.man.restapi.informations.ManInformationResultModel#urlText__
     */
    public void setUrlText(String urlText) {
        urlText__ = urlText;
    }

}
