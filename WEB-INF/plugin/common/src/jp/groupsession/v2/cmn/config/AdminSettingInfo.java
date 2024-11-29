package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] メイン管理者設定表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdminSettingInfo {

    /** 表示/非表示 */
    private String view__ = null;
    /** 管理者メニュー画面URL */
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
     * <p>view を取得します。
     * @return view
     */
    public String getView() {
        return view__;
    }
    /**
     * <p>view をセットします。
     * @param view view
     */
    public void setView(String view) {
        view__ = view;
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
