package jp.groupsession.v2.cmn.model;

import java.util.List;
import java.util.Map;

/**
 * <br>[機  能] リクエストから取得する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class PushRequestModel {
    /** 端末トークンリスト */
    private List<String> pushToken__ = null;
    /** タイトル */
    private String pushTitle__ = null;
    /** メッセージ */
    private String pushMessage__ = null;
    /** パラメータ */
    private Map<String, String> pushParam__ = null;
    /** アプリID */
    private String appId__ = null;
    /** ユーザSID */
    private int pushUser__;
    
    /**
     * <p>pushToken を取得します。
     * @return pushToken
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushToken__
     */
    public List<String> getPushToken() {
        return pushToken__;
    }
    /**
     * <p>pushToken をセットします。
     * @param pushToken pushToken
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushToken__
     */
    public void setPushToken(List<String> pushToken) {
        pushToken__ = pushToken;
    }
    /**
     * <p>pushTitle を取得します。
     * @return pushTitle
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushTitle__
     */
    public String getPushTitle() {
        return pushTitle__;
    }
    /**
     * <p>pushTitle をセットします。
     * @param pushTitle pushTitle
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushTitle__
     */
    public void setPushTitle(String pushTitle) {
        pushTitle__ = pushTitle;
    }
    /**
     * <p>pushMessage を取得します。
     * @return pushMessage
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushMessage__
     */
    public String getPushMessage() {
        return pushMessage__;
    }
    /**
     * <p>pushMessage をセットします。
     * @param pushMessage pushMessage
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushMessage__
     */
    public void setPushMessage(String pushMessage) {
        pushMessage__ = pushMessage;
    }
    /**
     * <p>pushParam を取得します。
     * @return pushParam
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushParam__
     */
    public Map<String, String> getPushParam() {
        return pushParam__;
    }
    /**
     * <p>pushParam をセットします。
     * @param pushParam pushParam
     * @see jp.groupsession.v2.cmn.model.PushRequestModel#pushParam__
     */
    public void setPushParam(Map<String, String> pushParam) {
        pushParam__ = pushParam;
    }
    /**
     * appIdを取得します
     * @return appId
     */
    public String getAppId() {
        return appId__;
    }
    /**
     * appIdをセットします
     * @param appId
     */
    public void setAppId(String appId) {
        appId__ = appId;
    }

    /**
     * pushUserを取得します
     * @return pushUser
     */
    public int getPushUser() {
        return pushUser__;
    }
    /**
     * pushUserをセットします
     * @param pushUser
     */
    public void setPushUser(int pushUser) {
        pushUser__ = pushUser;
    }
}
