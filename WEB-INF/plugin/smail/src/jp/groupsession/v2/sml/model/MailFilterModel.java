package jp.groupsession.v2.sml.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] フィルター情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MailFilterModel extends AbstractModel {

    /** フィルターSID */
    private int sftSid__ = 0;
    /** 条件_添付ファイル */
    private boolean tempFile__ = false;
    /** 動作_ラベル */
    private boolean label__ = false;
    /** ラベルSID */
    private int labelSid__ = 0;
    /** 動作_既読にする */
    private boolean readed__ = false;
    /** 動作_ゴミ箱に移動する */
    private boolean dust__ = false;
    /** 条件 */
    private int condition__ = 0;

    /**
     * <p>sftSid を取得します。
     * @return sftSid
     */
    public int getSftSid() {
        return sftSid__;
    }

    /**
     * <p>sftSid をセットします。
     * @param sftSid sftSid
     */
    public void setSftSid(int sftSid) {
        sftSid__ = sftSid;
    }

    /**
     * <p>dust を取得します。
     * @return dust
     */
    public boolean isDust() {
        return dust__;
    }

    /**
     * <p>dust をセットします。
     * @param dust dust
     */
    public void setDust(boolean dust) {
        dust__ = dust;
    }

    /**
     * <p>label をセットします。
     * @param label label
     */
    public void setLabel(boolean label) {
        label__ = label;
    }

    /**
     * <p>labelSid を取得します。
     * @return labelSid
     */
    public int getLabelSid() {
        return labelSid__;
    }

    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     */
    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }

    /**
     * <p>readed を取得します。
     * @return readed
     */
    public boolean isReaded() {
        return readed__;
    }

    /**
     * <p>readed をセットします。
     * @param readed readed
     */
    public void setReaded(boolean readed) {
        readed__ = readed;
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
     * <p>condition を取得します。
     * @return condition
     */
    public int getCondition() {
        return condition__;
    }

    /**
     * <p>condition をセットします。
     * @param condition condition
     */
    public void setCondition(int condition) {
        condition__ = condition;
    }

    /**
     * <p>label を取得します。
     * @return label
     */
    public boolean isLabel() {
        return label__;
    }

}
