package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.GSConstWebmail;
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
    private int wftSid__ = 0;
    /** 条件_添付ファイル */
    private boolean tempFile__ = false;
    /** 動作_ラベル */
    private boolean label__ = false;
    /** ラベルSID */
    private int labelSid__ = 0;
    /** 動作_既読にする */
    private boolean readed__ = false;
    /** 動作_フォルダに移動する */
    private int move__ = GSConstWebmail.FILTER_MOVE_NOSET;
    /** 動作_指定アドレスに転送する */
    private boolean forward__ = false;
    /** 転送先メールアドレス */
    private String forwardAddress__ = null;
    /** 条件 */
    private int condition__ = 0;

    /**
     * <p>wftSid を取得します。
     * @return wftSid
     */
    public int getWftSid() {
        return wftSid__;
    }

    /**
     * <p>wftSid をセットします。
     * @param wftSid wftSid
     */
    public void setWftSid(int wftSid) {
        wftSid__ = wftSid;
    }

    /**
     * <p>move を取得します。
     * @return move
     */
    public int getMove() {
        return move__;
    }

    /**
     * <p>move をセットします。
     * @param move move
     */
    public void setMove(int move) {
        move__ = move;
    }

    /**
     * <p>forward を取得します。
     * @return forward
     */
    public boolean isForward() {
        return forward__;
    }

    /**
     * <p>forward をセットします。
     * @param forward forward
     */
    public void setForward(boolean forward) {
        forward__ = forward;
    }

    /**
     * <p>forwardAddress を取得します。
     * @return forwardAddress
     */
    public String getForwardAddress() {
        return forwardAddress__;
    }

    /**
     * <p>forwardAddress をセットします。
     * @param forwardAddress forwardAddress
     */
    public void setForwardAddress(String forwardAddress) {
        forwardAddress__ = forwardAddress;
    }

    /**
     * <p>label を取得します。
     * @return label
     */
    public boolean isLabel() {
        return label__;
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

}
