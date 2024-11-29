package jp.groupsession.v2.wml.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メールフォーマット入力チェックの結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlValidateMailAddressResultModel extends AbstractModel {
    /** 正常なアドレス */
    private List<String> addressList__ = null;
    /** エラーメッセージ(文字列) */
    private String[] errMessage__ = null;
    /** エラーメッセージ */
    private List<ActionMessage> errMessageList__ = null;
    /**
     * <p>addressList を取得します。
     * @return addressList
     */
    public List<String> getAddressList() {
        return addressList__;
    }
    /**
     * <p>addressList をセットします。
     * @param addressList addressList
     */
    public void setAddressList(List<String> addressList) {
        addressList__ = addressList;
    }
    /**
     * <p>errMessage を取得します。
     * @return errMessage
     */
    public String[] getErrMessage() {
        return errMessage__;
    }
    /**
     * <p>errMessage をセットします。
     * @param errMessage errMessage
     */
    public void setErrMessage(String[] errMessage) {
        errMessage__ = errMessage;
    }
    /**
     * <p>errMessageList を取得します。
     * @return errMessageList
     */
    public List<ActionMessage> getErrMessageList() {
        return errMessageList__;
    }
    /**
     * <p>errMessageList をセットします。
     * @param errMessageList errMessageList
     */
    public void setErrMessageList(List<ActionMessage> errMessageList) {
        errMessageList__ = errMessageList;
    }
}
