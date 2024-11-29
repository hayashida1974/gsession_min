package jp.groupsession.v2.adr.restapi.posts;

/**
 * <br>[機  能] アドレス帳 役職一覧取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrPostsResultModel {

    /** アドレス帳 役職SID */
    private Integer sid__ = 0;
    /** アドレス帳 役職名 */
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
