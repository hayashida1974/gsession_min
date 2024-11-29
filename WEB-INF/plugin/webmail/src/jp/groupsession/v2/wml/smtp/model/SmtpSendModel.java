package jp.groupsession.v2.wml.smtp.model;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;

/**
 * <br>[機  能] 送信メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmtpSendModel implements Serializable {

    /** コネクション */
    private Connection con__ = null;
    /** アカウントSID */
    private long wacSid__ = 0;
    /** ディレクトリSID */
    private long wdrSid__ = 0;
    /** GroupSession基本情報 */
    private GSContext gsContext__ = null;
    /** ユーザSID */
    private int userSid__ = 0;
    /** HTMLメールフラグ */
    private boolean htmlMail__ = false;
    /** サブジェクト */
    private String subject__ = null;
    /** fromアドレス*/
    private String from__ = null;
    /** toアドレス */
    private String to__ = null;
    /** ccアドレス */
    private String cc__ = null;
    /** bccアドレス */
    private String bcc__ = null;
    /** 本文 */
    private String body__ = null;
    /** 添付ファイルのリスト */
    private List<WmlMailFileModel> tempFileList__ = null;
    /** 草稿からの送信 */
    private boolean sendToDraft__ = false;
    /** ヘッダー情報 */
    private Map<String, List<String>> headerData__ = null;
    /** 送受信ログの登録 */
    private Integer logRegist__ = null;

    /** メール送信のリターンコード */
    private int rtnCode__ = 0;

    /** 後で送信 */
    private boolean timeSent__ = false;
    /** 後で送信 送信日 */
    private UDate sendPlanDate__ = null;
    /** 後で送信 添付ファイルの圧縮 */
    private int sendPlanCompressFileType__ = 0;

    /** 編集元メール メッセージ番号 */
    private long originMailNum__ = 0;
    /** 編集種別 */
    private int editType__ = 0;

    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public long getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid wacSid
     */
    public void setWacSid(long wacSid) {
        wacSid__ = wacSid;
    }
    /**
     * <p>bcc を取得します。
     * @return bcc
     */
    public String getBcc() {
        return bcc__;
    }
    /**
     * <p>bcc をセットします。
     * @param bcc bcc
     */
    public void setBcc(String bcc) {
        bcc__ = bcc;
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
     * <p>cc を取得します。
     * @return cc
     */
    public String getCc() {
        return cc__;
    }
    /**
     * <p>cc をセットします。
     * @param cc cc
     */
    public void setCc(String cc) {
        cc__ = cc;
    }
    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>from を取得します。
     * @return from
     */
    public String getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     */
    public void setFrom(String from) {
        from__ = from;
    }
    /**
     * <p>gsContext を取得します。
     * @return gsContext
     */
    public GSContext getGsContext() {
        return gsContext__;
    }
    /**
     * <p>gsContext をセットします。
     * @param gsContext gsContext
     */
    public void setGsContext(GSContext gsContext) {
        gsContext__ = gsContext;
    }
    /**
     * <p>htmlMail を取得します。
     * @return htmlMail
     */
    public boolean isHtmlMail() {
        return htmlMail__;
    }
    /**
     * <p>htmlMail をセットします。
     * @param htmlMail htmlMail
     */
    public void setHtmlMail(boolean htmlMail) {
        htmlMail__ = htmlMail;
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
     * <p>tempFileList を取得します。
     * @return tempFileList
     */
    public List<WmlMailFileModel> getTempFileList() {
        return tempFileList__;
    }
    /**
     * <p>tempFileList をセットします。
     * @param tempFileList tempFileList
     */
    public void setTempFileList(List<WmlMailFileModel> tempFileList) {
        tempFileList__ = tempFileList;
    }
    /**
     * <p>to を取得します。
     * @return to
     */
    public String getTo() {
        return to__;
    }
    /**
     * <p>to をセットします。
     * @param to to
     */
    public void setTo(String to) {
        to__ = to;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>wdrSid_ を取得します。
     * @return wdrSid_
     */
    public long getWdrSid() {
        return wdrSid__;
    }
    /**
     * <p>wdrSid_ をセットします。
     * @param wdrSid wdrSid
     */
    public void setWdrSid(long wdrSid) {
        wdrSid__ = wdrSid;
    }
    /**
     * <p>sendToDraft を取得します。
     * @return sendToDraft
     */
    public boolean isSendToDraft() {
        return sendToDraft__;
    }
    /**
     * <p>sendToDraft をセットします。
     * @param sendToDraft sendToDraft
     */
    public void setSendToDraft(boolean sendToDraft) {
        sendToDraft__ = sendToDraft;
    }
    /**
     * <p>headerData を取得します。
     * @return headerData
     */
    public Map<String, List<String>> getHeaderData() {
        return headerData__;
    }
    /**
     * <p>headerData をセットします。
     * @param headerData headerData
     */
    public void setHeaderData(Map<String, List<String>> headerData) {
        headerData__ = headerData;
    }

    /**
     * <p>rtnCode を取得します。
     * @return rtnCode
     */
    public int getRtnCode() {
        return rtnCode__;
    }
    /**
     * <p>rtnCode をセットします。
     * @param rtnCode rtnCode
     */
    public void setRtnCode(int rtnCode) {
        rtnCode__ = rtnCode;
    }
    /**
     * <p>logRegist を取得します。
     * @return logRegist
     */
    public Integer getLogRegist() {
        return logRegist__;
    }
    /**
     * <p>logRegist をセットします。
     * @param logRegist logRegist
     */
    public void setLogRegist(Integer logRegist) {
        logRegist__ = logRegist;
    }
    /**
     * <p>timeSent を取得します。
     * @return timeSent
     */
    public boolean isTimeSent() {
        return timeSent__;
    }
    /**
     * <p>timeSent をセットします。
     * @param timeSent timeSent
     */
    public void setTimeSent(boolean timeSent) {
        timeSent__ = timeSent;
    }
    /**
     * <p>sendPlanDate を取得します。
     * @return sendPlanDate
     */
    public UDate getSendPlanDate() {
        return sendPlanDate__;
    }
    /**
     * <p>sendPlanDate をセットします。
     * @param sendPlanDate sendPlanDate
     */
    public void setSendPlanDate(UDate sendPlanDate) {
        sendPlanDate__ = sendPlanDate;
    }
    /**
     * <p>sendPlanCompressFileType を取得します。
     * @return sendPlanCompressFileType
     */
    public int getSendPlanCompressFileType() {
        return sendPlanCompressFileType__;
    }
    /**
     * <p>sendPlanCompressFileType をセットします。
     * @param sendPlanCompressFileType sendPlanCompressFileType
     */
    public void setSendPlanCompressFileType(int sendPlanCompressFileType) {
        sendPlanCompressFileType__ = sendPlanCompressFileType;
    }
    /**
     * <p>originMailNum を取得します。
     * @return originMailNum
     */
    public long getOriginMailNum() {
        return originMailNum__;
    }
    /**
     * <p>originMailNum をセットします。
     * @param originMailNum originMailNum
     */
    public void setOriginMailNum(long originMailNum) {
        originMailNum__ = originMailNum;
    }
    /**
     * <p>editType を取得します。
     * @return editType
     */
    public int getEditType() {
        return editType__;
    }
    /**
     * <p>editType をセットします。
     * @param editType editType
     */
    public void setEditType(int editType) {
        editType__ = editType;
    }

    /**
     * <br>[機  能] ヘッダー情報を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param type ヘッダー種別
     * @param value ヘッダー情報
     */
    public void addHeaderData(String type, String value) {
        if (headerData__ == null) {
            headerData__ = new HashMap<String, List<String>>();
        }

        if (headerData__.get(type) == null) {
            headerData__.put(type, new ArrayList<String>());
        }

        headerData__.get(type).add(value);
    }
}
