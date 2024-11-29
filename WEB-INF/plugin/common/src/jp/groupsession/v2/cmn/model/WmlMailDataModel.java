package jp.groupsession.v2.cmn.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;

/**
 * <br>[機  能] WEBメール メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlMailDataModel extends AbstractModel {
    /** 差出人メールアドレス */
    private String fromAddress__ = null;
    /** 送信日時 */
    private UDate sendDate__ = null;
    /** 件名 */
    private String subject__ = null;
    /** 本文 */
    private String body__ = null;
    /** 宛先 */
    private List<String> toList__ = null;
    /** CC */
    private List<String> ccList__ = null;
    /** 添付ファイル情報 */
    private List<WmlTempfileModel> tempFileList__ = null;
    /**
     * <p>fromAddress を取得します。
     * @return fromAddress
     */
    public String getFromAddress() {
        return fromAddress__;
    }
    /**
     * <p>fromAddress をセットします。
     * @param fromAddress fromAddress
     */
    public void setFromAddress(String fromAddress) {
        fromAddress__ = fromAddress;
    }
    /**
     * <p>subject を取得します。
     * @return subject
     */
    public String getSubject() {
        return subject__;
    }
    /**
     * <p>subject をセットします。
     * @param subject subject
     */
    public void setSubject(String subject) {
        subject__ = subject;
    }
    /**
     * <p>body を取得します。
     * @return body
     */
    public String getBody() {
        return body__;
    }
    /**
     * <p>body をセットします。
     * @param body body
     */
    public void setBody(String body) {
        body__ = body;
    }
    /**
     * <p>tempFileList を取得します。
     * @return tempFileList
     */
    public List<WmlTempfileModel> getTempFileList() {
        return tempFileList__;
    }
    /**
     * <p>tempFileList をセットします。
     * @param tempFileList tempFileList
     */
    public void setTempFileList(List<WmlTempfileModel> tempFileList) {
        tempFileList__ = tempFileList;
    }
    /**
     * <p>sendDate を取得します。
     * @return sendDate
     */
    public UDate getSendDate() {
        return sendDate__;
    }
    /**
     * <p>sendDate をセットします。
     * @param sendDate sendDate
     */
    public void setSendDate(UDate sendDate) {
        sendDate__ = sendDate;
    }
    /**
     * <p>toList を取得します。
     * @return toList
     */
    public List<String> getToList() {
        return toList__;
    }
    /**
     * <p>toList をセットします。
     * @param toList toList
     */
    public void setToList(List<String> toList) {
        toList__ = toList;
    }
    /**
     * <p>ccList を取得します。
     * @return ccList
     */
    public List<String> getCcList() {
        return ccList__;
    }
    /**
     * <p>ccList をセットします。
     * @param ccList ccList
     */
    public void setCcList(List<String> ccList) {
        ccList__ = ccList;
    }
}
