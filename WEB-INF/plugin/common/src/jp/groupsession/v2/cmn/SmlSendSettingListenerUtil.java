package jp.groupsession.v2.cmn;

import jp.groupsession.v2.cmn.config.PluginConfig;

/**
 * <br>[機  能] jp.groupsession.v2.cmn.ILoginLogoutListenerについてのユーティリティークラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlSendSettingListenerUtil {

    /**
     * <p>プラグイン設定ファイルよりショートメール通知実装クラスのリストを返す
     * @param pconfig ショートメール通知リスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ショートメール通知リスナー
     */
    public static ISmlSendSettingListener[] getSmlSendSettingListeners(PluginConfig pconfig)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        String[] gsListenerClass = pconfig.getSmlSendSetting();
        ISmlSendSettingListener[] lis = getSmlSendSettingListeners(gsListenerClass);
        return lis;
    }

    /**
     * <p>プラグイン設定ファイルよりショートメール通知実装クラスのリストを返す
     * @param listenerClass ショートメール通知リスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ショートメール通知リスナー
     */
    public static ISmlSendSettingListener[] getSmlSendSettingListeners(String[] listenerClass)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        ISmlSendSettingListener[] lis = new ISmlSendSettingListener[listenerClass.length];
        for (int i = 0; i < listenerClass.length; i++) {
            lis[i] = (ISmlSendSettingListener) Class.forName(listenerClass[i]).newInstance();
        }

        return lis;
    }

}
