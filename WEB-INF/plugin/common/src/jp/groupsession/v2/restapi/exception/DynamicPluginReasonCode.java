package jp.groupsession.v2.restapi.exception;

/**
 * <br>[機  能] 各プラグインの利用不可エラーコードを取得するためのクラスです。
 * <br>[解  説] 
 * <br>[備  考] 各プラグイン特有のエラーコードを取得するためには使用しないでください。
 *
 * @author JTS
 */
public class DynamicPluginReasonCode implements IReasonCode {

    /** プラグインID */
    private String pluginId__;
    /** エラーコード末尾の数字3桁 */
    private String number__;

    /** エラーコード末尾 プラグイン利用不可 */
    public static final String PLUGIN_CANT_USE = "000";

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginId プラグインID
     * @param number エラーコード末尾の数字3桁
     */
    public DynamicPluginReasonCode(String pluginId, String number) {
        pluginId__ = pluginId;
        number__ = number;
    }

    /**
     * <br>[機  能] プラグインID, 末尾の数字3桁からエラーコードを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginId プラグインID
     * @param number エラーコード末尾の数字3桁
     */
    @Override
    public String reasonCodeText() {
        return String.format("%S-%s", pluginId__, number__);
    }
}
