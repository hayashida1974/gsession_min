package jp.groupsession.v2.sml.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ラベル情報のの取得条件を格納したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class LabelDataModel extends AbstractModel {

    /** ラベルSID */
    private int labelSid__ = 0;
    /** ラベル名 */
    private String labelName__ = null;
    /** ラベル並び順 */
    private int labelOrder__ = 0;
    /** 表示順(画面用) */
    private String lbValue__ = null;
    /**
     * <p>lbValue を取得します。
     * @return lbValue
     */
    public String getLbValue() {
        return lbValue__;
    }
    /**
     * <p>lbValue をセットします。
     * @param lbValue lbValue
     */
    public void setLbValue(String lbValue) {
        lbValue__ = lbValue;
    }
    /**
     * <p>labelName を取得します。
     * @return labelName
     */
    public String getLabelName() {
        return labelName__;
    }
    /**
     * <p>labelName をセットします。
     * @param labelName labelName
     */
    public void setLabelName(String labelName) {
        labelName__ = labelName;
    }
    /**
     * <p>labelOrder を取得します。
     * @return labelOrder
     */
    public int getLabelOrder() {
        return labelOrder__;
    }
    /**
     * <p>labelOrder をセットします。
     * @param labelOrder labelOrder
     */
    public void setLabelOrder(int labelOrder) {
        labelOrder__ = labelOrder;
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
}
