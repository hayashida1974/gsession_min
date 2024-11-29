package jp.groupsession.v2.cmn.config;
/**
 *
 * <br>[機  能] プラグインマネージャー表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PluginManagerSettingInfo {
    /** 表示/非表示 */
    private String view__ = null;

    /**
     * <p>view を取得します。
     * @return view
     * @see jp.groupsession.v2.cmn.config.PluginManagerSettingInfo#view__
     */
    public String getView() {
        return view__;
    }

    /**
     * <p>view をセットします。
     * @param view view
     * @see jp.groupsession.v2.cmn.config.PluginManagerSettingInfo#view__
     */
    public void setView(String view) {
        view__ = view;
    }

}
