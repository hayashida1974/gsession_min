package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] リスナー情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Listener {

    /** リスナー名 */
    private String listenername__ = null;
    /** リスナー実装クラス */
    private String listenerclass__ = null;

    /**
     * @return listenerclass を戻します。
     */
    public String getListenerclass() {
        return listenerclass__;
    }
    /**
     * @param listenerclass 設定する listenerclass。
     */
    public void setListenerclass(String listenerclass) {
        listenerclass__ = listenerclass;
    }
    /**
     * @return listenername を戻します。
     */
    public String getListenername() {
        return listenername__;
    }
    /**
     * @param listenername 設定する listenername。
     */
    public void setListenername(String listenername) {
        listenername__ = listenername;
    }
}
