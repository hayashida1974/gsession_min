package jp.co.sjts.util.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

/**
 * <br>[機  能] Basic認証に関するユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BasicAuthorizationUtil {

    /**
     * <br>[機  能] HTTPヘッダにAuthorizationヘッダーフィールドが含まれているか判定します。
     * <br>[解  説] 含まれている場合はtrue、含まれていない場合はfalseを返します。
     * <br>[備  考]
     * @param req リクエスト
     * @return boolean  true:Authorizationヘッダーを含む,false:含まない
     */
    public static boolean canGetAuthorization(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        boolean exist = false;
        if (auth != null) {
            exist = true;
        }

        return exist;
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * <p>リクエストヘッダよりユーザーIDを取得します。
     * @param req リクエスト
     * @return ユーザーID
     */
    public static String getUserID(HttpServletRequest req) {
        String dec = getDecordedAuth(req);
        return __getUserID(dec);
    }

    /**
     * <br>[機  能] リクエストヘッダよりパスワードを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return パスワード
     */
    public static String getPassword(HttpServletRequest req) {
        String dec = getDecordedAuth(req);
        return __getPassword(dec);
    }

    /**
     * <br>[機  能] Authorizationヘッダーフィールドから認証用の証明書を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return デコード済の認証証明書
     */
    public static String getDecordedAuth(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        return __getDecordedAuth(auth);
    }

    /**
     * <br>[機  能] HTTPヘッダにAuthorization または Proxy-Authorizationヘッダーフィールドが含まれているか判定します。
     * <br>[解  説] 含まれている場合はtrue、含まれていない場合はfalseを返します。
     * <br>[備  考]
     * @param req リクエスト
     * @return boolean  true:Authorization または Proxy-Authorizationヘッダーを含む,false:含まない
     */
    public static boolean canGetAuthorizationFromProxy(HttpServletRequest req) {
        boolean exist = req.getHeader("Proxy-Authorization") != null;
        if (!exist) {
            exist = canGetAuthorization(req);
        }
        return exist;
    }

    /**
     * <br>[機  能] リクエストヘッダよりユーザーIDを取得します。
     * <br>[解  説]
     * <br>[備  考] Authorization または Proxy-Authorizationヘッダーより取得
     * @param req リクエスト
     * @return ユーザーID
     */
    public static String getUserIDFromProxy(HttpServletRequest req) {
        String dec = getDecordedAuthFromProxy(req);
        return __getUserID(dec);
    }

    /**
     * <br>[機  能] リクエストヘッダよりパスワードを取得します。
     * <br>[解  説]
     * <br>[備  考] Authorization または Proxy-Authorizationヘッダーより取得
     * @param req リクエスト
     * @return パスワード
     */
    public static String getPasswordFromProxy(HttpServletRequest req) {
        String dec = getDecordedAuthFromProxy(req);
        return __getPassword(dec);
    }

    /**
     * <br>[機  能] Authorization または Proxy-Authorizationヘッダーフィールドから認証用の証明書を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return デコード済の認証証明書
     */
    public static String getDecordedAuthFromProxy(HttpServletRequest req) {
        String proxyAuth = req.getHeader("Proxy-Authorization");
        if (proxyAuth == null) {
            return getDecordedAuth(req);
        }

        return __getDecordedAuth(proxyAuth);
    }

    /**
     * <br>[機  能] Authorization またはProxy-Authrizationヘッダーフィールドから認証用の証明書を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param auth Authorization またはProxy-Authrizationヘッダーフィールド
     * @return デコード済の認証証明書
     */
    private static String __getDecordedAuth(String auth) {
        if (auth == null) {
            return null;
        }
        String realm = auth.substring(auth.lastIndexOf(' ') + 1);

        byte[] b = Base64.decodeBase64(realm.getBytes());
        String dec = new String(b);

        return dec;
    }

    /**
     * <br>[機  能] 認証情報よりユーザーIDを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param dec 認証情報
     * @return ユーザーID
     */
    private static String __getUserID(String dec) {
        if (dec == null) {
            return null;
        }
        String userid = dec.substring(0, dec.indexOf(":"));

        return userid;
    }

    /**
     * <br>[機  能] 認証情報よりパスワードを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param dec 認証情報
     * @return パスワード
     */
    private static String __getPassword(String dec) {
        if (dec == null) {
            return null;
        }
        String password = dec.substring(dec.indexOf(":") + 1);
        return password;
    }
}