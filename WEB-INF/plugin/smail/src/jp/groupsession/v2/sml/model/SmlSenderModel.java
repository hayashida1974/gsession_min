package jp.groupsession.v2.sml.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 * <br>[機  能] ショートメール送信処理クラスModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class SmlSenderModel extends AbstractModel {

    /** 差出人 */
    int sendUsid__ = -1;
    /** メール件名 */
    private String sendTitle__ = null;
    /** メールマーク */
    private int sendMark__ = -1;
    /** メール本文 */
    private String sendBody__ = null;
    /** メール形式 */
    private int sendType__ = GSConstSmail.SAC_SEND_MAILTYPE_NORMAL;
    /** 送信先ユーザSID配列 */
    private List<Integer> sendToUsrSidArray__ = null;
    /** ファイルのフルパス */
    private String fileFulPath__ = null;
    /** 保存先のフルパス */
    private String saveFulPath__ = null;
    /** 添付ファイルフラグ */
    private boolean tempFile__ = false;

    /**
     * @return sendBody__ を戻します。
     */
    public String getSendBody() {
        return sendBody__;
    }
    /**
     * @param sendBody 設定する sendBody__。
     */
    public void setSendBody(String sendBody) {
        sendBody__ = sendBody;
    }
    /**
     * @return sendMark__ を戻します。
     */
    public int getSendMark() {
        return sendMark__;
    }
    /**
     * @param sendMark 設定する sendMark__。
     */
    public void setSendMark(int sendMark) {
        sendMark__ = sendMark;
    }
    /**
     * @return sendTitle__ を戻します。
     */
    public String getSendTitle() {
        return sendTitle__;
    }
    /**
     * @param sendTitle 設定する sendTitle__。
     */
    public void setSendTitle(String sendTitle) {
        sendTitle__ = sendTitle;
    }
    /**
     * @return sendToUsrSidArray__ を戻します。
     */
    public List<Integer> getSendToUsrSidArray() {
        return sendToUsrSidArray__;
    }
    /**
     * @param sendToUsrSidArray 設定する sendToUsrSidArray__。
     */
    public void setSendToUsrSidArray(List<Integer> sendToUsrSidArray) {
        sendToUsrSidArray__ = sendToUsrSidArray;
    }
    /**
     * <p>sendUsid を取得します。
     * @return sendUsid
     */
    public int getSendUsid() {
        return sendUsid__;
    }
    /**
     * <p>sendUsid をセットします。
     * @param sendUsid sendUsid
     */
    public void setSendUsid(int sendUsid) {
        sendUsid__ = sendUsid;
    }
    /**
     * <p>fileFulPath を取得します。
     * @return fileFulPath
     */
    public String getFileFulPath() {
        return fileFulPath__;
    }
    /**
     * <p>fileFulPath をセットします。
     * @param fileFulPath fileFulPath
     */
    public void setFileFulPath(String fileFulPath) {
        fileFulPath__ = fileFulPath;
    }
    /**
     * <p>saveFulPath を取得します。
     * @return saveFulPath
     */
    public String getSaveFulPath() {
        return saveFulPath__;
    }
    /**
     * <p>saveFulPath をセットします。
     * @param saveFulPath saveFulPath
     */
    public void setSaveFulPath(String saveFulPath) {
        saveFulPath__ = saveFulPath;
    }
    /**
     * <p>tempFile を取得します。
     * @return tempFile
     */
    public boolean isTempFile() {
        return tempFile__;
    }
    /**
     * <p>tempFile をセットします。
     * @param tempFile tempFile
     */
    public void setTempFile(boolean tempFile) {
        tempFile__ = tempFile;
    }
    /**
     * <p>sendType を取得します。
     * @return sendType
     */
    public int getSendType() {
        return sendType__;
    }
    /**
     * <p>sendType をセットします。
     * @param sendType sendType
     */
    public void setSendType(int sendType) {
        sendType__ = sendType;
    }
}