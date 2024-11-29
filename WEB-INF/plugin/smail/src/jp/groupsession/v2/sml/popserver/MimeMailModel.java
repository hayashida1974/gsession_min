package jp.groupsession.v2.sml.popserver;

/**
 * <br>[機  能] Mime形式のメールを格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MimeMailModel {
    /** メッセージID */
    private String msgid__ = null;
    /** メール用文字列 */
    private String mstr__ = null;
    /** バイト数 */
    private int size__ = 0;
    /** 削除フラグ */
    private boolean delFlg__ = false;

    /** メールSID */
    private int smlSid__;

    /**
     * <p>デフォルトコンストラクタ
     */
    public MimeMailModel() {
    }

    /**
     * @return msgid を戻します。
     */
    public String getMsgid() {
        return msgid__;
    }

    /**
     * @param msgid 設定する msgid。
     */
    public void setMsgid(String msgid) {
        msgid__ = msgid;
    }

    /**
     * @return mstr を戻します。
     */
    public String getMstr() {
        return mstr__;
    }

    /**
     * @param mstr 設定する mstr。
     */
    public void setMstr(String mstr) {
        mstr__ = mstr;
    }

    /**
     * @return size を戻します。
     */
    public int getSize() {
        return size__;
    }

    /**
     * @param size 設定する size。
     */
    public void setSize(int size) {
        size__ = size;
    }

    /**
     * @return delFlg を戻します。
     */
    public boolean isDelFlg() {
        return delFlg__;
    }

    /**
     * @param delFlg 設定する delFlg。
     */
    public void setDelFlg(boolean delFlg) {
        delFlg__ = delFlg;
    }

    /**
     * @return smlSid を戻します。
     */
    public int getSmlSid() {
        return smlSid__;
    }

    /**
     * @param smlSid 設定する smlSid。
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }
}
