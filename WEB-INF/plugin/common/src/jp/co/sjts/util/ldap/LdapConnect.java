package jp.co.sjts.util.ldap;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ldap.model.LdapConnectModel;

/**
 * <br>[機  能] LDAPで接続、コンテキストを取得するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LdapConnect {

    /** プロパティファイル名 */
    private static final String BUNDLE_NAME = LdapConst.LDAP_BUNDLE_NAME;
    /** インスタンス */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * <br>[機  能] 接続情報を作成する
     * <br>[解  説] ldap.propertiesから接続情報を取得する
     * <br>[備  考]
     * @return 接続情報
     */
    public static LdapConnectModel createConnectData() {
        LdapConnectModel connectData = new LdapConnectModel();
        connectData.setAddress(RESOURCE_BUNDLE.getString("PROVIDER_URL"));
        connectData.setPort(RESOURCE_BUNDLE.getString("PROVIDER_PORT"));
        connectData.setBindDn(RESOURCE_BUNDLE.getString("SECURITY_PRINCIPAL"));
        connectData.setPassword(RESOURCE_BUNDLE.getString("SECURITY_CREDENTIALS"));
        connectData.setDnElement(RESOURCE_BUNDLE.getString("DNELEMENT"));

        return connectData;
    }

    /**
     * <br>[機  能] 設定ファイルの情報を元に、初期コンテキストを取得する
     * <br>[解  説] コンテキストのcloseは呼出側で行う
     * <br>[備  考]
     * @return DirContext 初期コンテキスト
     * @throws NamingException コンテキスト取得時例外
     */
    public static DirContext getInitContext() throws NamingException {


        // 初期コンテキストの作成
        return getDirContext(createConnectData());
    }

    /**
     * <br>[機  能] DirContextを取得する
     * <br>[解  説] コンテキストのcloseは呼出側で行う
     * <br>[備  考]
     * @param connectData LDAP接続情報
     * @return DirContext
     * @throws NamingException コンテキスト取得時例外
     */
    public static DirContext getDirContext(LdapConnectModel connectData)
    throws NamingException {

        String factory = RESOURCE_BUNDLE.getString("INITIAL_CONTEXT_FACTORY");
        String url = __getProviderUrl(connectData);
        String auth = RESOURCE_BUNDLE.getString("SECURITY_AUTHENTICATION");
        String keystore = RESOURCE_BUNDLE.getString("KEYSTORE");
        String keystorePassword = RESOURCE_BUNDLE.getString("KEYSTOREPASSWORD");

        // セッションの作成
        Hashtable <Object, Object> env = new Hashtable <Object, Object>();
        //ファクトリークラス
        env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
        //プロバイダURL
        env.put(Context.PROVIDER_URL, url);
        //認証モード
        env.put(Context.SECURITY_AUTHENTICATION, auth);
        //rootのバインド識別名
        env.put(Context.SECURITY_PRINCIPAL, NullDefault.getString(connectData.getBindDn(), ""));
        //rootのパスワード
        env.put(Context.SECURITY_CREDENTIALS, NullDefault.getString(connectData.getPassword(), ""));

        if (connectData.isUseSsl()) {
            // SSL 通信を行う設定
            env.put(Context.SECURITY_PROTOCOL, "ssl");
            // 既定のSSL の信頼するkeystore を設定する
            System.setProperty("javax.net.ssl.trustStore", keystore);
            System.setProperty("javax.net.ssl.trustStorePassword", keystorePassword);

        }

        // 初期コンテキストの作成
        return new InitialDirContext(env);
    }

    /**
     * <br>[機  能] コンテキストのcloseを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx DirContext
     */
    public static void closeCtx(DirContext ctx) {

        try {
            if (ctx != null) {
                ctx.close();
            }
        } catch (NamingException e) {
        }
    }

    /**
     * <br>[機  能] 設定ファイルの情報を元に、プロバイダURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param connectData LDAP接続情報
     * @return String プロバイダURL
     */
    private static String __getProviderUrl(LdapConnectModel connectData) {

        String url = connectData.getProtocol()
                    + "://" + connectData.getAddress();
        String port = connectData.getPort();
        if (!StringUtil.isNullZeroString(port)) {
            url = url + ":" + port;
        }
        url = url + "/";

        return url;
    }

}
