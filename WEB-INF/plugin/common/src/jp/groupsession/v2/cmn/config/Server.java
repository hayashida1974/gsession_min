package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] サーバ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Server {

    /** サーバーサービス名 */
    private String serviceName__ = null;
    /** クラス名(FQDN) */
    private String className__ = null;

    /**
     * <br>[機  能] サーバーサービス名を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param name サーバ名
     */
    public void setServiceName(String name) {
        serviceName__ = name;
    }

    /**
     * <br>[機  能] サーバーサービス名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return サービス名
     */
    public String getServiceName() {
        return serviceName__;
    }

    /**
     * <br>[機  能] クラス名(FQDN)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return クラス名(FQDN)
     */
    public String getClassName() {
        return className__;
    }

    /**
     * <br>[機  能] クラス名(FQDN)を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param className クラス名(FQDN)
     */
    public void setClassName(String className) {
        className__ = className;
    }
}
