package jp.groupsession.v2.sml;

import jp.groupsession.v2.cmn.config.PluginConfig;

/**
 * <br>[機  能] jp.groupsession.v2.sml.ISMailListenerについてのユーティリティークラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SMailListenerUtil {

    /**
     * <p>プラグイン設定ファイルよりショートメールリスナー実装クラスのリストを返す
     * @param pconfig ショートメールリスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ショートメールリスナー
     */
    public static ISMailListener[] getSMailListeners(PluginConfig pconfig)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        String[] gsListenerClass = pconfig.getSMailListeners();
        ISMailListener[] lis = getLoginLogoutListeners(gsListenerClass);
        return lis;
    }

    /**
     * <p>ショートメールリスナー実装クラスのリストを返す
     * @param listenerClass ショートメールリスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ショートメールリスナー
     */
    public static ISMailListener[] getLoginLogoutListeners(String[] listenerClass)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        ISMailListener[] lis = new ISMailListener[listenerClass.length];
        for (int i = 0; i < listenerClass.length; i++) {
            lis[i] = (ISMailListener) Class.forName(listenerClass[i]).newInstance();
        }

        return lis;
    }

}
