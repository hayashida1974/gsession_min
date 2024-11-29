package jp.groupsession.v2.cmn;

import jp.groupsession.v2.cmn.config.PluginConfig;

/**
 * <br>[機  能] jp.groupsession.v2.cmn.IGsListenerについてのユーティリティークラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsListenerUtil {

    /**
     * <p>プラグイン設定ファイルよりユーザリスナー実装クラスのリストを返す
     * @param pconfig ユーザリスナー実装クラス
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @return ユーザリスナー
     */
    public static IGsListener[] getGsListeners(PluginConfig pconfig)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        String[] gsListenerClass = pconfig.getGsListeners();
        IGsListener[] lis = getGsListeners(gsListenerClass);
        return lis;
    }

    /**
     * <p>ユーザリスナー実装クラスのリストを返す
     * @param gsListenerClass ユーザリスナー実装クラス
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @return GSリスナー
     */
    public static IGsListener[] getGsListeners(String[] gsListenerClass)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        IGsListener[] lis = new IGsListener[gsListenerClass.length];
        for (int i = 0; i < gsListenerClass.length; i++) {
            lis[i] = (IGsListener) Class.forName(gsListenerClass[i]).newInstance();
        }

        return lis;
    }
}

