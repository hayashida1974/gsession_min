package jp.groupsession.v2.wml.syspro.wml1010kn;

import java.io.File;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml1010knMailModel extends AbstractModel {

    /** インポート */
    private int import__ = 0;
    /** ファイル名 */
    private String fileName__ = null;
    /** 件名 */
    private String subject__ = null;
    /** 本文 */
    private String body__ = null;
    /** 日時 */
    private UDate date__ = null;
    /** 既読 */
    private boolean readed__ = false;
    /** 返信 */
    private boolean reply__ = false;
    /** 転送 */
    private boolean forward__ = false;
    /** from */
    private String from__ = null;
    /** 添付ファイル */
    private boolean attach__ = false;
    /** サイズ */
    private int size__ = 0;

	public int getImport() {
		return import__;
	}
	public void setImport(int import__) {
		this.import__ = import__;
	}

	/**
     * @return attach
     */
    public boolean isAttach() {
        return attach__;
    }
    /**
     * @param attach 設定する attach
     */
    public void setAttach(boolean attach) {
        attach__ = attach;
    }
    /**
     * @return body
     */
    public String getBody() {
        return body__;
    }
    /**
     * @param body 設定する body
     */
    public void setBody(String body) {
        body__ = body;
    }
    /**
     * @return date
     */
    public UDate getDate() {
        return date__;
    }
    /**
     * @param date 設定する date
     */
    public void setDate(UDate date) {
        date__ = date;
    }
    /**
     * @return forward
     */
    public boolean isForward() {
        return forward__;
    }
    /**
     * @param forward 設定する forward
     */
    public void setForward(boolean forward) {
        forward__ = forward;
    }
    /**
     * @return from
     */
    public String getFrom() {
        return from__;
    }
    /**
     * @param from 設定する from
     */
    public void setFrom(String from) {
        from__ = from;
    }
    /**
     * @return fileName
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * @param fileName 設定する fileName
     */
    public void setFileName(String fileName) {
    	fileName__ = fileName;
    }
    /**
     * @return readed
     */
    public boolean isReaded() {
        return readed__;
    }
    /**
     * @param readed 設定する readed
     */
    public void setReaded(boolean readed) {
        readed__ = readed;
    }
    /**
     * @return reply
     */
    public boolean isReply() {
        return reply__;
    }
    /**
     * @param reply 設定する reply
     */
    public void setReply(boolean reply) {
        reply__ = reply;
    }
    /**
     * @return subject
     */
    public String getSubject() {
        return subject__;
    }
    /**
     * @param subject 設定する subject
     */
    public void setSubject(String subject) {
        subject__ = subject;
    }

	public int getSize() {
		return size__;
	}
	public void setSize(int size) {
		this.size__ = size;
	}

    /**
     * 日時の文字列表現を取得する
     * @return 日時の文字列表現
     */
    public String getStrDate() {
        if (date__ == null) {
            return "";
        }

        return UDateUtil.getSlashYYMD(date__)
            + " " + UDateUtil.getSeparateHMS(date__);
    }
}
