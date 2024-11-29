package jp.groupsession.v2.man.model;

/**
 * <br>[機  能] メインプラグインメニュー表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ManAdmSettingInfoModel {

    /** プラグイン名 */
    private String name__ = null;
    /** プラグインID */
    private String id__ = null;
    /** 遷移先画面URL */
    private String url__ = null;
    /** アイコンURL */
    private String icon__ = null;
    /** アイコンURL（クラシック） */
    private String iconClassic__ = null;

    /**
     * <p>url を取得します。
     * @return url
     */
    public String getUrl() {
        return url__;
    }
    /**
     * <p>url をセットします。
     * @param url url
     */
    public void setUrl(String url) {
        url__ = url;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>id を取得します。
     * @return id
     * @see jp.groupsession.v2.man.model.ManAdmSettingInfoModel#id__
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     * @see jp.groupsession.v2.man.model.ManAdmSettingInfoModel#id__
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>icon を取得します。
     * @return icon
     */
    public String getIcon() {
        return icon__;
    }
    /**
     * <p>icon をセットします。
     * @param icon icon
     */
    public void setIcon(String icon) {
        icon__ = icon;
    }
    /**
     * <p>iconClassic を取得します。
     * @return iconClassic
     */
    public String getIconClassic() {
        return iconClassic__;
    }
    /**
     * <p>iconClassic をセットします。
     * @param iconClassic iconClassic
     */
    public void setIconClassic(String iconClassic) {
        iconClassic__ = iconClassic;
    }
}