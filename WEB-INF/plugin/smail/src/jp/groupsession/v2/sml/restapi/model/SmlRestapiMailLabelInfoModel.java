package jp.groupsession.v2.sml.restapi.model;

/**
 *
 * <br>[機  能] メール情報 ラベル情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlRestapiMailLabelInfoModel {
    /** ラベルSID */
    private int sid__;
    /** ラベル名 */
    private String name__;
    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailLabelInfoModel#sid__
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailLabelInfoModel#sid__
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailLabelInfoModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.sml.restapi.model.SmlRestapiMailLabelInfoModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
}
