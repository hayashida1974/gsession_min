package jp.co.sjts.util.ldap.model;

/**
 * <br>[機  能] LDAPの接続情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LdapConnectModel {

    /** アドレス */
    private String address__ = null;
    /** ポート番号 */
    private String port__ = null;
    /** バインド識別名 */
    private String bindDn__ = null;
    /** パスワード */
    private String password__ = null;
    /** ベース識別名 */
    private String baseDn__ = null;
    /** DN設定 */
    private String dnElement__ = null;
    /** SSL使用フラグ */
    private boolean useSsl__ = false;
    /** タイムアウト時間 */
    private long timeout__ = 0;

    /**
     * <p>address を取得します。
     * @return address
     */
    public String getAddress() {
        return address__;
    }


    /**
     * <p>address をセットします。
     * @param address address
     */
    public void setAddress(String address) {
        address__ = address;
    }


    /**
     * <p>baseDn を取得します。
     * @return baseDn
     */
    public String getBaseDn() {
        return baseDn__;
    }


    /**
     * <p>baseDn をセットします。
     * @param baseDn baseDn
     */
    public void setBaseDn(String baseDn) {
        baseDn__ = baseDn;
    }


    /**
     * <p>bindDn を取得します。
     * @return bindDn
     */
    public String getBindDn() {
        return bindDn__;
    }


    /**
     * <p>bindDn をセットします。
     * @param bindDn bindDn
     */
    public void setBindDn(String bindDn) {
        bindDn__ = bindDn;
    }


    /**
     * <p>dnElement を取得します。
     * @return dnElement
     */
    public String getDnElement() {
        return dnElement__;
    }


    /**
     * <p>dnElement をセットします。
     * @param dnElement dnElement
     */
    public void setDnElement(String dnElement) {
        dnElement__ = dnElement;
    }


    /**
     * <p>password を取得します。
     * @return password
     */
    public String getPassword() {
        return password__;
    }


    /**
     * <p>password をセットします。
     * @param password password
     */
    public void setPassword(String password) {
        password__ = password;
    }


    /**
     * <p>port を取得します。
     * @return port
     */
    public String getPort() {
        return port__;
    }


    /**
     * <p>port をセットします。
     * @param port port
     */
    public void setPort(String port) {
        port__ = port;
    }


    /**
     * <p>timeout を取得します。
     * @return timeout
     */
    public long getTimeout() {
        return timeout__;
    }


    /**
     * <p>timeout をセットします。
     * @param timeout timeout
     */
    public void setTimeout(long timeout) {
        timeout__ = timeout;
    }


    /**
     * <p>useSsl を取得します。
     * @return useSsl
     */
    public boolean isUseSsl() {
        return useSsl__;
    }


    /**
     * <p>useSsl をセットします。
     * @param useSsl useSsl
     */
    public void setUseSsl(boolean useSsl) {
        useSsl__ = useSsl;
    }


    /**
     * <br>[機  能] プロトコルを取得する
     * <br>[解  説] SSL使用 = 使用するの場合は "ldaps"、使用しないの場合は"ldap"を返す1
     * <br>[備  考]
     * @return プロトコル
     */
    public String getProtocol() {
        if (isUseSsl()) {
            return "ldaps";
        }

        return "ldap";
    }

    /**
     * <br>[機  能] LdapConnectModelのcloneを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @return clone
     */
    public LdapConnectModel cloneModel() {
        LdapConnectModel model = new LdapConnectModel();
        model.setAddress(address__);
        model.setPort(port__);
        model.setBindDn(bindDn__);
        model.setPassword(password__);
        model.setBaseDn(baseDn__);
        model.setDnElement(dnElement__);
        model.setUseSsl(useSsl__);
        model.setTimeout(timeout__);

        return model;
    }
}
