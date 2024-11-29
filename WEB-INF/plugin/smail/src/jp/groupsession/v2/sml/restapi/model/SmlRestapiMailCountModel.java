package jp.groupsession.v2.sml.restapi.model;

/**
 *
 * <br>[機  能] メール情報 件数情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlRestapiMailCountModel {
    /** アカウント名 */
    private String boxName__;
    /** 件数 */
    private int countNum__ = 0;
    /** 未読件数 */
    private int nonReadCountNum__ = 0;
    /**
     * <p>boxName を取得します。
     * @return boxName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountModel#boxName__
     */
    public String getBoxName() {
        return boxName__;
    }
    /**
     * <p>boxName をセットします。
     * @param boxName boxName
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountModel#boxName__
     */
    public void setBoxName(String boxName) {
        boxName__ = boxName;
    }
    /**
     * <p>countNum を取得します。
     * @return countNum
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountModel#countNum__
     */
    public int getCountNum() {
        return countNum__;
    }
    /**
     * <p>countNum をセットします。
     * @param countNum countNum
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountModel#countNum__
     */
    public void setCountNum(int countNum) {
        countNum__ = countNum;
    }
    /**
     * <p>nonReadCountNum を取得します。
     * @return nonReadCountNum
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountModel#nonReadCountNum__
     */
    public int getNonReadCountNum() {
        return nonReadCountNum__;
    }
    /**
     * <p>nonReadCountNum をセットします。
     * @param nonReadCountNum nonReadCountNum
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountModel#nonReadCountNum__
     */
    public void setNonReadCountNum(int nonReadCountNum) {
        nonReadCountNum__ = nonReadCountNum;
    }
}
