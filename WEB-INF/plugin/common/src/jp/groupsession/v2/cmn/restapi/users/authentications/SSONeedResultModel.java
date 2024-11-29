package jp.groupsession.v2.cmn.restapi.users.authentications;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SSONeedResultModel {
    /** メッセージ                                        */
    private String messageText__;
    /** 理由区分コード                                      */
    private String reasonCodeText__;
    /** ログイン方法                                      */
    private String loginTypeText__;
    /**
     * <p>messageText を取得します。
     * @return messageText
     * @see SSONeedResultModel#messageText__
     */
    public String getMessageText() {
        return messageText__;
    }
    /**
     * <p>messageText をセットします。
     * @param messageText messageText
     * @see SSONeedResultModel#messageText__
     */
    public void setMessageText(String messageText) {
        messageText__ = messageText;
    }
    /**
     * <p>reasonCodeText を取得します。
     * @return reasonCodeText
     * @see SSONeedResultModel#reasonCodeText__
     */
    public String getReasonCodeText() {
        return reasonCodeText__;
    }
    /**
     * <p>reasonCodeText をセットします。
     * @param reasonCodeText reasonCodeText
     * @see SSONeedResultModel#reasonCodeText__
     */
    public void setReasonCodeText(String reasonCodeText) {
        reasonCodeText__ = reasonCodeText;
    }
    /**
     * <p>loginTypeText を取得します。
     * @return loginTypeText
     * @see SSONeedResultModel#loginTypeText__
     */
    public String getLoginTypeText() {
        return loginTypeText__;
    }
    /**
     * <p>loginTypeText をセットします。
     * @param loginTypeText loginTypeText
     * @see SSONeedResultModel#loginTypeText__
     */
    public void setLoginTypeText(String loginTypeText) {
        loginTypeText__ = loginTypeText;
    }

}
