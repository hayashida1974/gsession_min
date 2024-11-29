package jp.groupsession.v2.sml.restapi.model;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
/**
 *
 * <br>[機  能] メール情報 リザルトモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlRestapiMailModel {
    /** メールSID */
    private int sid__;
    /** メール区分 */
    private int mailType__;
    /** 本文形式 */
    private int bodyFormatFlg__;
    /** 開封フラグ */
    private int openFlg__;
    /** 返信フラグ */
    private int replyFlg__;
    /** 転送フラグ */
    private int forwardFlg__;
    /** 件名 */
    private String subjectText__;
    /** マーク */
    private int markType__;
    /** 本文 */
    private String bodyText__;
    /** メール状態区分 */
    private int statusType__;
    /** 差出人アカウントSID */
    private int senderAccountSid__;
    /** 差出人アカウント名 */
    private String senderName__;
    /** 差出人ユーザ削除フラグ */
    private int senderUserDeleteFlg__;
    /** 差出人ユーザログイン停止フラグ */
    private int senderLoginStopFlg__;
    /** 送信日時 */
    private String sendDateTime__;
    /** 送信先情報配列 */
    @ChildElementName("atesakiInfo")
    private List<SmlRestapiMailAtesakiInfoModel> atesakiArray__
            = new ArrayList<SmlRestapiMailAtesakiInfoModel>();
    /** ラベル情報配列 */
    @ChildElementName("labelInfo")
    private List<SmlRestapiMailLabelInfoModel> labelArray__
            = new ArrayList<SmlRestapiMailLabelInfoModel>();
    /** 添付ファイル情報配列 */
    @ChildElementName("tmpFileInfo")
    private List<SmlRestapiMailTmpFileInfoModel> tmpFileArray__
            = new ArrayList<SmlRestapiMailTmpFileInfoModel>();
    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#sid__
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#sid__
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     * <p>mailType を取得します。
     * @return mailType
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#mailType__
     */
    public int getMailType() {
        return mailType__;
    }
    /**
     * <p>mailType をセットします。
     * @param mailType mailType
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#mailType__
     */
    public void setMailType(int mailType) {
        mailType__ = mailType;
    }
    /**
     * <p>bodyFormatFlg を取得します。
     * @return bodyFormatFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#bodyFormatFlg__
     */
    public int getBodyFormatFlg() {
        return bodyFormatFlg__;
    }
    /**
     * <p>bodyFormatFlg をセットします。
     * @param bodyFormatFlg bodyFormatFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#bodyFormatFlg__
     */
    public void setBodyFormatFlg(int bodyFormatFlg) {
        bodyFormatFlg__ = bodyFormatFlg;
    }
    /**
     * <p>openFlg を取得します。
     * @return openFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#openFlg__
     */
    public int getOpenFlg() {
        return openFlg__;
    }
    /**
     * <p>openFlg をセットします。
     * @param openFlg openFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#openFlg__
     */
    public void setOpenFlg(int openFlg) {
        openFlg__ = openFlg;
    }
    /**
     * <p>replyFlg を取得します。
     * @return replyFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#replyFlg__
     */
    public int getReplyFlg() {
        return replyFlg__;
    }
    /**
     * <p>replyFlg をセットします。
     * @param replyFlg replyFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#replyFlg__
     */
    public void setReplyFlg(int replyFlg) {
        replyFlg__ = replyFlg;
    }
    /**
     * <p>forwardFlg を取得します。
     * @return forwardFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#forwardFlg__
     */
    public int getForwardFlg() {
        return forwardFlg__;
    }
    /**
     * <p>forwardFlg をセットします。
     * @param forwardFlg forwardFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#forwardFlg__
     */
    public void setForwardFlg(int forwardFlg) {
        forwardFlg__ = forwardFlg;
    }
    /**
     * <p>subjectText を取得します。
     * @return subjectText
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#subjectText__
     */
    public String getSubjectText() {
        return subjectText__;
    }
    /**
     * <p>subjectText をセットします。
     * @param subjectText subjectText
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#subjectText__
     */
    public void setSubjectText(String subjectText) {
        subjectText__ = subjectText;
    }
    /**
     * <p>markType を取得します。
     * @return markType
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#markType__
     */
    public int getMarkType() {
        return markType__;
    }
    /**
     * <p>markType をセットします。
     * @param markType markType
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#markType__
     */
    public void setMarkType(int markType) {
        markType__ = markType;
    }
    /**
     * <p>bodyText を取得します。
     * @return bodyText
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#bodyText__
     */
    public String getBodyText() {
        return bodyText__;
    }
    /**
     * <p>bodyText をセットします。
     * @param bodyText bodyText
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#bodyText__
     */
    public void setBodyText(String bodyText) {
        bodyText__ = bodyText;
    }
    /**
     * <p>statusType を取得します。
     * @return statusType
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#statusType__
     */
    public int getStatusType() {
        return statusType__;
    }
    /**
     * <p>statusType をセットします。
     * @param statusType statusType
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#statusType__
     */
    public void setStatusType(int statusType) {
        statusType__ = statusType;
    }
    /**
     * <p>senderAccountSid を取得します。
     * @return senderAccountSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderAccountSid__
     */
    public int getSenderAccountSid() {
        return senderAccountSid__;
    }
    /**
     * <p>senderAccountSid をセットします。
     * @param senderAccountSid senderAccountSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderAccountSid__
     */
    public void setSenderAccountSid(int senderAccountSid) {
        senderAccountSid__ = senderAccountSid;
    }
    /**
     * <p>senderName を取得します。
     * @return senderName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderName__
     */
    public String getSenderName() {
        return senderName__;
    }
    /**
     * <p>senderName をセットします。
     * @param senderName senderName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderName__
     */
    public void setSenderName(String senderName) {
        senderName__ = senderName;
    }
    /**
     * <p>senderUserDeleteFlg を取得します。
     * @return senderUserDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderUserDeleteFlg__
     */
    public int getSenderUserDeleteFlg() {
        return senderUserDeleteFlg__;
    }
    /**
     * <p>senderUserDeleteFlg をセットします。
     * @param senderUserDeleteFlg senderUserDeleteFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderUserDeleteFlg__
     */
    public void setSenderUserDeleteFlg(int senderUserDeleteFlg) {
        senderUserDeleteFlg__ = senderUserDeleteFlg;
    }
    /**
     * <p>senderLoginStopFlg を取得します。
     * @return senderLoginStopFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderLoginStopFlg__
     */
    public int getSenderLoginStopFlg() {
        return senderLoginStopFlg__;
    }
    /**
     * <p>senderLoginStopFlg をセットします。
     * @param senderLoginStopFlg senderLoginStopFlg
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#senderLoginStopFlg__
     */
    public void setSenderLoginStopFlg(int senderLoginStopFlg) {
        senderLoginStopFlg__ = senderLoginStopFlg;
    }
    /**
     * <p>sendDateTime を取得します。
     * @return sendDateTime
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#sendDateTime__
     */
    public String getSendDateTime() {
        return sendDateTime__;
    }
    /**
     * <p>sendDateTime をセットします。
     * @param sendDateTime sendDateTime
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#sendDateTime__
     */
    public void setSendDateTime(String sendDateTime) {
        sendDateTime__ = sendDateTime;
    }
    /**
     * <p>atesakiArray を取得します。
     * @return atesakiArray
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#atesakiArray__
     */
    public List<SmlRestapiMailAtesakiInfoModel> getAtesakiArray() {
        return atesakiArray__;
    }
    /**
     * <p>atesakiArray をセットします。
     * @param atesakiArray atesakiArray
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#atesakiArray__
     */
    public void setAtesakiArray(List<SmlRestapiMailAtesakiInfoModel> atesakiArray) {
        atesakiArray__ = atesakiArray;
    }
    /**
     * <p>labelArray を取得します。
     * @return labelArray
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#labelArray__
     */
    public List<SmlRestapiMailLabelInfoModel> getLabelArray() {
        return labelArray__;
    }
    /**
     * <p>labelArray をセットします。
     * @param labelArray labelArray
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#labelArray__
     */
    public void setLabelArray(List<SmlRestapiMailLabelInfoModel> labelArray) {
        labelArray__ = labelArray;
    }
    /**
     * <p>tmpFileArray を取得します。
     * @return tmpFileArray
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#tmpFileArray__
     */
    public List<SmlRestapiMailTmpFileInfoModel> getTmpFileArray() {
        return tmpFileArray__;
    }
    /**
     * <p>tmpFileArray をセットします。
     * @param tmpFileArray tmpFileArray
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel#tmpFileArray__
     */
    public void setTmpFileArray(List<SmlRestapiMailTmpFileInfoModel> tmpFileArray) {
        tmpFileArray__ = tmpFileArray;
    }
}
