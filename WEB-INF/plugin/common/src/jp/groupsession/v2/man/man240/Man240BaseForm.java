package jp.groupsession.v2.man.man240;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ設定画面 プラグインごとの情報を保持するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man240BaseForm {

    /** プラグイン名 */
    private String pluginName__;
    /** プラグインID */
    private String lgcPlugin__;
    /** ログレベル エラー */
    private String lgcLevelError__;
    /** ログレベル 警告 */
    private String lgcLevelWarn__;
    /** ログレベル 重要情報 */
    private String lgcLevelInfo__;
    /** ログレベル トレース */
    private String lgcLevelTrace__;

    /**
     * <p>pluginName を取得します。
     * @return pluginName
     */
    public String getPluginName() {
        return pluginName__;
    }

    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }

    /**
     * <p>lgcPlugin を取得します。
     * @return lgcPlugin
     */
    public String getLgcPlugin() {
        return lgcPlugin__;
    }

    /**
     * <p>lgcPlugin をセットします。
     * @param lgcPlugin lgcPlugin
     */
    public void setLgcPlugin(String lgcPlugin) {
        lgcPlugin__ = lgcPlugin;
    }

    /**
     * <p>lgcLevelError を取得します。
     * @return lgcLevelError
     */
    public String getLgcLevelError() {
        return lgcLevelError__;
    }

    /**
     * <p>lgcLevelError をセットします。
     * @param lgcLevelError lgcLevelError
     */
    public void setLgcLevelError(String lgcLevelError) {
        lgcLevelError__ = lgcLevelError;
    }

    /**
     * <p>lgcLevelWarn を取得します。
     * @return lgcLevelWarn
     */
    public String getLgcLevelWarn() {
        return lgcLevelWarn__;
    }

    /**
     * <p>lgcLevelWarn をセットします。
     * @param lgcLevelWarn lgcLevelWarn
     */
    public void setLgcLevelWarn(String lgcLevelWarn) {
        lgcLevelWarn__ = lgcLevelWarn;
    }

    /**
     * <p>lgcLevelInfo を取得します。
     * @return lgcLevelInfo
     */
    public String getLgcLevelInfo() {
        return lgcLevelInfo__;
    }

    /**
     * <p>lgcLevelInfo をセットします。
     * @param lgcLevelInfo lgcLevelInfo
     */
    public void setLgcLevelInfo(String lgcLevelInfo) {
        lgcLevelInfo__ = lgcLevelInfo;
    }

    /**
     * <p>lgcLevelTrace を取得します。
     * @return lgcLevelTrace
     */
    public String getLgcLevelTrace() {
        return lgcLevelTrace__;
    }

    /**
     * <p>lgcLevelTrace をセットします。
     * @param lgcLevelTrace lgcLevelTrace
     */
    public void setLgcLevelTrace(String lgcLevelTrace) {
        lgcLevelTrace__ = lgcLevelTrace;
    }


}
