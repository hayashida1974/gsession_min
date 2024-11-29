package jp.groupsession.v2.wml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;

/**
 * <br>[機  能] 送信メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlMailModel implements Serializable {

    /** メッセージ番号 */
    private long mailNum__ = 0;

    /** Subject */
    private String subject__ = null;

    /** 送信元FROM */
    private List<String> from__ = null;

    /** 送信先TO */
    private List<String> to__ = null;
    /** 送信先CC */
    private List<String> cc__ = null;
    /** 送信先BCC */
    private List<String> bcc__ = null;

    /** 送信先WML_SENDADDRESS.WSA_NUM登録用*/
    private int renban__ = 1;

    /** 送信日時 */
    private UDate sendDate__ = null;
    /** 送信予定日時 */
    private UDate sendPlanDate__ = null;

    /** UID */
    private String uid__ = null;

    /** ヘッダーキー */
    private List<String> headerKey__ = null;
    /** ヘッダーキーと内容のMapping */
    private Map<String, List<String>> headerMap__ = null;

    /** メール本文 */
    private String content__ = null;

    /** ディスクサイズ */
    private long diskSize__ = 0;

    /** charset */
    private String charset__ = null;

    /** 添付ファイル情報 */
    private List<WmlMailFileModel> tempFileList__ = null;

    /** エラーフラグ */
    private boolean errFlg__ = false;
    /** エラー内容 */
    private List<String> errMessage__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlMailModel() {
        from__ = new ArrayList<String>();
        to__ = new ArrayList<String>();
        cc__ = new ArrayList<String>();
        bcc__ = new ArrayList<String>();
        headerKey__ = new ArrayList<String>();
        headerMap__ = new HashMap<String, List<String>>();
        tempFileList__ = new ArrayList<WmlMailFileModel>();
    }

    /**
     * <p>mailNum を取得します。
     * @return mailNum
     */
    public long getMailNum() {
        return mailNum__;
    }

    /**
     * <p>mailNum をセットします。
     * @param mailNum mailNum
     */
    public void setMailNum(long mailNum) {
        mailNum__ = mailNum;
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
     * <p>from を取得します。
     * @return from
     */
    public List<String> getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     */
    public void setFrom(List<String> from) {
        from__ = from;
    }
    /**
     * <p>bcc を取得します。
     * @return bcc
     */
    public List<String> getBcc() {
        return bcc__;
    }
    /**
     * <p>bcc をセットします。
     * @param bcc bcc
     */
    public void setBcc(List<String> bcc) {
        bcc__ = bcc;
    }
    /**
     * <p>cc を取得します。
     * @return cc
     */
    public List<String> getCc() {
        return cc__;
    }
    /**
     * <p>cc をセットします。
     * @param cc cc
     */
    public void setCc(List<String> cc) {
        cc__ = cc;
    }
    /**
     * <p>uid を取得します。
     * @return uid
     */
    public String getUid() {
        return uid__;
    }
    /**
     * <p>uid をセットします。
     * @param uid uid
     */
    public void setUid(String uid) {
        uid__ = uid;
    }
    /**
     * <p>content を取得します。
     * @return content
     */
    public String getContent() {
        return content__;
    }
    /**
     * <p>content をセットします。
     * @param content content
     */
    public void setContent(String content) {
        content__ = content;
    }
    /**
     * <p>headerKey を取得します。
     * @return headerKey
     */
    public List<String> getHeaderKey() {
        return headerKey__;
    }

    /**
     * <p>headerKey をセットします。
     * @param headerKey headerKey
     */
    public void setHeaderKey(List<String> headerKey) {
        headerKey__ = headerKey;
    }

    /**
     * <p>headerMap を取得します。
     * @return headerMap
     */
    public Map<String, List<String>> getHeaderMap() {
        return headerMap__;
    }

    /**
     * <p>headerMap をセットします。
     * @param headerMap headerMap
     */
    public void setHeaderMap(Map<String, List<String>> headerMap) {
        headerMap__ = headerMap;
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
     * <p>diskSize を取得します。
     * @return diskSize
     */
    public long getDiskSize() {
        return diskSize__;
    }

    /**
     * <p>diskSize をセットします。
     * @param diskSize diskSize
     */
    public void setDiskSize(long diskSize) {
        diskSize__ = diskSize;
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
    public List<String> getTo() {
        return to__;
    }

    /**
     * <p>to をセットします。
     * @param to to
     */
    public void setTo(List<String> to) {
        to__ = to;
    }

    /**
     * <br>[機  能] 送信元FROMを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param from 送信元FROM
     */
    public void addFrom(String from) {
        from__.add(from);
    }

    /**
     * <br>[機  能] 送信先TOを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param to 送信先TO
     */
    public void addTo(String to) {
        to__.add(to);
    }

    /**
     * <br>[機  能] 送信先CCを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param cc 送信先CC
     */
    public void addCc(String cc) {
        cc__.add(cc);
    }

    /**
     * <br>[機  能] 送信先BCCを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param bcc 送信先BCC
     */
    public void addBcc(String bcc) {
        bcc__.add(bcc);
    }

    /**
     * <p>errFlg を取得します。
     * @return errFlg
     */
    public boolean isErrFlg() {
        return errFlg__;
    }

    /**
     * <p>errFlg をセットします。
     * @param errFlg errFlg
     */
    public void setErrFlg(boolean errFlg) {
        errFlg__ = errFlg;
    }

    /**
     * <p>errMessage を取得します。
     * @return errMessage
     */
    public List<String> getErrMessage() {
        return errMessage__;
    }

    /**
     * <p>errMessage をセットします。
     * @param errMessage errMessage
     */
    public void setErrMessage(List<String> errMessage) {
        errMessage__ = errMessage;
    }

    /**
     * <p>charset を取得します。
     * @return charset
     */
    public String getCharset() {
        return charset__;
    }

    /**
     * <p>charset をセットします。
     * @param charset charset
     */
    public void setCharset(String charset) {
        charset__ = charset;
    }

    /**
     * <br>[機  能] ヘッダー情報を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param name 名称
     * @param content 内容
     */
    public void addHeader(String name, String content) {
        if (headerKey__.indexOf(name) < 0) {
            headerKey__.add(name);
            headerMap__.put(name, new ArrayList<String>());
        }

        headerMap__.get(name).add(content);
    }

    /**
     * <br>[機  能] ヘッダー情報を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param name 名称
     * @param content 内容
     */
    public void addHeader(String name, String[] content) {
        if (name != null && content != null) {
            for (String value : content) {
                addHeader(name, value);
            }
        }
    }

    /**
     * <br>[機  能] 添付ファイル情報を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param fileData ファイル情報
     */
    public void addTempFile(WmlMailFileModel fileData) {
        tempFileList__.add(fileData);
    }

    /**
     * <br>[機  能] 次のファイル番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 次のファイル番号
     */
    public int getNextFileNum() {

        int fileNum = 1;
        if (tempFileList__ != null) {
            fileNum += tempFileList__.size();
        }

        return fileNum;
    }

    /**
     * <br>[機  能] エラー内容を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param errMessage エラー内容
     */
    public void addErrMessage(String errMessage) {
        if (errMessage__ == null) {
            errMessage__ = new ArrayList<String>();
        }

        errMessage__.add(errMessage);
    }

    /**
     * <p>renban を取得します。
     * @return renban
     * @see jp.groupsession.v2.wml.model.WmlMailModel#renban__
     */
    public int getRenban() {
        return renban__;
    }

    /**
     * <p>renban をセットします。
     * @param renban renban
     * @see jp.groupsession.v2.wml.model.WmlMailModel#renban__
     */
    public void setRenban(int renban) {
        renban__ = renban;
    }
}
