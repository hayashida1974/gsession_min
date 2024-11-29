package jp.groupsession.v2.adr.restapi.model;

/**
 * <br>[機  能] アドレス帳 業種情報 保持モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class IndustryInfo {

    /** 業種SID */
    private Integer sid__ = 0;
    /** 業種名 */
    private String name__ = null;
    
    public Integer getSid() {
        return sid__;
    }
    public void setSid(Integer sid) {
        sid__ = sid;
    }
    public String getName() {
        return name__;
    }
    public void setName(String name) {
        name__ = name;
    }
}
