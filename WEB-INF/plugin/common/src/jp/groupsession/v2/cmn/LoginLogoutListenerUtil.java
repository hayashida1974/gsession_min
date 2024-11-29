package jp.groupsession.v2.cmn;

import jp.groupsession.v2.cmn.config.PluginConfig;

/**
 * <br>[機  能] jp.groupsession.v2.cmn.ILoginLogoutListenerについてのユーティリティークラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LoginLogoutListenerUtil {

    /**
     * <p>プラグイン設定ファイルよりログイン、ログアウトリスナー実装クラスのリストを返す
     * @param pconfig ログイン、ログアウトリスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ログイン、ログアウトリスナー
     */
    public static ILoginLogoutListener[] getLoginLogoutListeners(PluginConfig pconfig)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        String[] gsListenerClass = pconfig.getLoginLogoutListeners();
        ILoginLogoutListener[] lis = getLoginLogoutListeners(gsListenerClass);
        return lis;
    }

    /**
     * <p>ログイン、ログアウトリスナー実装クラスのリストを返す
     * @param listenerClass ログイン、ログアウトリスナー実装クラス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @return ログイン、ログアウトリスナー
     */
    public static ILoginLogoutListener[] getLoginLogoutListeners(String[] listenerClass)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        ILoginLogoutListener[] lis = new ILoginLogoutListener[listenerClass.length];
        for (int i = 0; i < listenerClass.length; i++) {
            lis[i] = (ILoginLogoutListener) Class.forName(listenerClass[i]).newInstance();
        }

        return lis;
    }

}
