package jp.groupsession.v2.batch;

import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.config.PluginConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * <br>[機  能] 夜間バッチJOBスケジュール実行時に呼ばれるリスナー
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DayTrigerListener implements TriggerListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(DayTrigerListener.class);

    /** GS共通情報 */
    private GSContext  gscontext__ = null;
    /** プラグイン情報 */
    private PluginConfig pluginConfig__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public DayTrigerListener() {
        super();
        log__.debug("コンストラクタ");
    }

    /**
     * <br>[機  能] クラス名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String クラス名
     */
    public String getName() {
        log__.debug("getName");
        return DayTrigerListener.class.getName();
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param arg0 Trigger
     * @param arg1 JobExecutionContext
     */
    public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
        log__.debug("triggerFired");
    }

    /**
     * <br>[機  能] スケジュール実行時、JobExecutionContextにGS共通情報、データソースをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param arg0 Trigger
     * @param context JobExecutionContext
     * @return boolean
     */
    public boolean vetoJobExecution(Trigger arg0, JobExecutionContext context) {
        log__.debug("vetoJobExecution");
        context.put("GSCONTEXT", gscontext__);
        context.put("BTLISTENER", pluginConfig__);

        log__.debug("GSCONTEXT" + gscontext__);
        log__.debug("BTLISTENER" +  pluginConfig__);

        return false;
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param arg0 Trigger
     */
    public void triggerMisfired(Trigger arg0) {
        log__.debug("triggerMisfired");

    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramTrigger Trigger
     * @param paramJobExecutionContext JobExecutionContext
     * @param paramCompletedExecutionInstruction Trigger.CompletedExecutionInstruction
     */
    public void triggerComplete(Trigger paramTrigger, JobExecutionContext paramJobExecutionContext,
                        Trigger.CompletedExecutionInstruction paramCompletedExecutionInstruction) {
        log__.debug("triggerComplete");
    }

    /**
     * @param gscontext 設定する gscontext。
     */
    public void setGscontext(GSContext gscontext) {
        gscontext__ = gscontext;
    }

    /**
     * @param pluginConfig 設定する pluginConfig。
     */
    public void setPluginConfig(PluginConfig pluginConfig) {
        pluginConfig__ = pluginConfig;
    }
}