package jp.groupsession.v2.cmn;

import jp.groupsession.v2.cmn.config.PluginConfig;

/**
 * <br>[機  能] jp.groupsession.v2.cmn.IAutoManualDeleteListenerについてのユーティリティークラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AutoManualDeleteListenerUtil {

    /**
     * <p>プラグイン設定ファイルより自動削除設定実装クラスのリストを返す
     * @param pconfig 自動削除設定リスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ショートメール通知リスナー
     */
    public static IAutoManualDeleteListener[] getAutoManualDeleteListeners(PluginConfig pconfig)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        String[] gsListenerClass = pconfig.getAutoManualDelete();
        IAutoManualDeleteListener[] lis = getAutoManualDeleteListeners(gsListenerClass);
        return lis;
    }

    /**
     * <p>プラグイン設定ファイルより自動削除設定実装クラスのリストを返す
     * @param listenerClass 自動削除設定リスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ショートメール通知リスナー
     */
    public static IAutoManualDeleteListener[] getAutoManualDeleteListeners(String[] listenerClass)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        IAutoManualDeleteListener[] lis = new IAutoManualDeleteListener[listenerClass.length];
        for (int i = 0; i < listenerClass.length; i++) {
            lis[i] = (IAutoManualDeleteListener) Class.forName(listenerClass[i]).newInstance();
        }

        return lis;
    }

}
