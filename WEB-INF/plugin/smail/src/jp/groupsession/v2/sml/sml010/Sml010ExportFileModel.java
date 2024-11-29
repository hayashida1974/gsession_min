package jp.groupsession.v2.sml.sml010;

import java.io.File;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;

/**
 * <br>[機  能] ショートメール メールエクスポートファイル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml010ExportFileModel extends AbstractModel {

    /** メッセージ番号 */
    private long messageNum__ = 0;
    /** 件名 */
    private String subject__ = null;
    /** 送信日時 */
    private UDate sdate__ = null;
    /** ファイルパス */
    private File filePath__ = null;
    /** 差出人 */
    private String sender__ = null;
    /** 宛先 */
    private String atesaki__ = null;
    /** CC */
    private String atesakiCC__ = null;
    /** BCC */
    private String atesakiBCC__ = null;

    /** 出力メール一覧 */
    private ArrayList<SmailDetailModel> smlList__ = null;
    /** 出力メール添付ファイルリスト */
    private ArrayList<CmnBinfModel> smlFileList__ = null;

    /**
     * <p>messageNum を取得します。
     * @return messageNum
     */
    public long getMessageNum() {
        return messageNum__;
    }
    /**
     * <p>messageNum をセットします。
     * @param messageNum messageNum
     */
    public void setMessageNum(long messageNum) {
        messageNum__ = messageNum;
    }
    /**
     * <p>filePath を取得します。
     * @return filePath
     */
    public File getFilePath() {
        return filePath__;
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
     * <p>filePath をセットします。
     * @param filePath filePath
     */
    public void setFilePath(File filePath) {
        filePath__ = filePath;
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
     * <p>smlFileList を取得します。
     * @return smlFileList
     */
    public ArrayList<CmnBinfModel> getSmlFileList() {
        return smlFileList__;
    }
    /**
     * <p>smlFileList をセットします。
     * @param smlFileList smlFileList
     */
    public void setSmlFileList(ArrayList<CmnBinfModel> smlFileList) {
        smlFileList__ = smlFileList;
    }
    /**
     * <p>smlList を取得します。
     * @return smlList
     */
    public ArrayList<SmailDetailModel> getSmlList() {
        return smlList__;
    }
    /**
     * <p>smlList をセットします。
     * @param smlList smlList
     */
    public void setSmlList(ArrayList<SmailDetailModel> smlList) {
        smlList__ = smlList;
    }
    /**
     * <p>sender を取得します。
     * @return sender
     */
    public String getSender() {
        return sender__;
    }
    /**
     * <p>sender をセットします。
     * @param sender sender
     */
    public void setSender(String sender) {
        sender__ = sender;
    }
    /**
     * <p>atesaki を取得します。
     * @return atesaki
     */
    public String getAtesaki() {
        return atesaki__;
    }
    /**
     * <p>atesaki をセットします。
     * @param atesaki atesaki
     */
    public void setAtesaki(String atesaki) {
        atesaki__ = atesaki;
    }
    /**
     * <p>atesakiCC を取得します。
     * @return atesakiCC
     */
    public String getAtesakiCC() {
        return atesakiCC__;
    }
    /**
     * <p>atesakiCC をセットします。
     * @param atesakiCC atesakiCC
     */
    public void setAtesakiCC(String atesakiCC) {
        atesakiCC__ = atesakiCC;
    }
    /**
     * <p>atesakiBCC を取得します。
     * @return atesakiBCC
     */
    public String getAtesakiBCC() {
        return atesakiBCC__;
    }
    /**
     * <p>atesakiBCC をセットします。
     * @param atesakiBCC atesakiBCC
     */
    public void setAtesakiBCC(String atesakiBCC) {
        atesakiBCC__ = atesakiBCC;
    }
}
