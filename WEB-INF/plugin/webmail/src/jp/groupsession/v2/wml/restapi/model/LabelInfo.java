package jp.groupsession.v2.wml.restapi.model;

/**
 * <br>[機  能] WEBメールRESTAPI ラベル情報保持クラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class LabelInfo {

    /** ラベルSID */
    private long sid__ = 0;
    /** ラベル名 */
    private String name__ = null;

    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.wml.restapi.model.LabelInfo#sid__
     */
    public long getSid() {
        return sid__;
    }
    /**
     * <p>sid を設定します。
     * @param sid
     * @see jp.groupsession.v2.wml.restapi.model.LabelInfo#sid__
     */
    public void setSid(long sid) {
        sid__ = sid;
    }

    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.wml.restapi.model.LabelInfo#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name を設定します。
     * @param name
     * @see jp.groupsession.v2.wml.restapi.model.LabelInfo#name__
     */
    public void setName(String name) {
        name__ = name;
    }
}
