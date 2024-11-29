package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] プラグイン情報  拡張ポイントを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ExtentionInfo {

    /** 拡張ポイント名 */
    private String name__ = null;
    /** 拡張ポイントURL */
    private String url__ = null;
    /** 拡張ポイントの説明 */
    private String description__ = null;

    /**
     * @return description を戻します。
     */
    public String getDescription() {
        return description__;
    }
    /**
     * @param description 設定する description。
     */
    public void setDescription(String description) {
        description__ = description;
    }
    /**
     * @return name を戻します。
     */
    public String getName() {
        return name__;
    }
    /**
     * @param name 設定する name。
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * @return url を戻します。
     */
    public String getUrl() {
        return url__;
    }
    /**
     * @param url 設定する url。
     */
    public void setUrl(String url) {
        url__ = url;
    }

}
