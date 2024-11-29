package jp.groupsession.v2.sml.restapi.model;

/**
 *
 * <br>[機  能] メール情報 件数情報(ラベル)モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlRestapiMailCountLabelModel extends SmlRestapiMailCountModel {
    /** ラベルSID */
    private int labelSid__;
    /** ラベル名 */
    private String labelName__;
    /**
     * <p>labelSid を取得します。
     * @return labelSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountLabelModel#labelSid__
     */
    public int getLabelSid() {
        return labelSid__;
    }
    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountLabelModel#labelSid__
     */
    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }
    /**
     * <p>labelName を取得します。
     * @return labelName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountLabelModel#labelName__
     */
    public String getLabelName() {
        return labelName__;
    }
    /**
     * <p>labelName をセットします。
     * @param labelName labelName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountLabelModel#labelName__
     */
    public void setLabelName(String labelName) {
        labelName__ = labelName;
    }
}
