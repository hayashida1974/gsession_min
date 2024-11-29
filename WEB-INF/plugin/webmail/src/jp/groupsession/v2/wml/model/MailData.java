package jp.groupsession.v2.wml.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MailData extends AbstractModel {

    /** メッセージ番号 */
    private long mailNum__ = 0;
    /** 送信日時 */
    private UDate sdate__ = null;
    /** 差出人 */
    private String from__ = null;
    /** 件名 */
    private String title__ = null;
    /** 本文 */
    private String body__ = null;
    /** 添付ファイル有無 */
    private boolean tempFlg__ = false;
    /** 添付ファイル情報 */
    private List<MailTempFileModel> tempFileList__ = null;

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
     * <p>sdate を取得します。
     * @return sdate
     */
    public UDate getSdate() {
        return sdate__;
    }
    /**
     * <p>sdate をセットします。
     * @param sdate sdate
     */
    public void setSdate(UDate sdate) {
        sdate__ = sdate;
    }
    /**
     * <p>tempFileList を取得します。
     * @return tempFileList
     */
    public List<MailTempFileModel> getTempFileList() {
        return tempFileList__;
    }
    /**
     * <p>tempFileList をセットします。
     * @param tempFileList tempFileList
     */
    public void setTempFileList(List<MailTempFileModel> tempFileList) {
        tempFileList__ = tempFileList;
    }
    /**
     * <p>tempFlg を取得します。
     * @return tempFlg
     */
    public boolean isTempFlg() {
        return tempFlg__;
    }
    /**
     * <p>tempFlg をセットします。
     * @param tempFlg tempFlg
     */
    public void setTempFlg(boolean tempFlg) {
        tempFlg__ = tempFlg;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }

}
