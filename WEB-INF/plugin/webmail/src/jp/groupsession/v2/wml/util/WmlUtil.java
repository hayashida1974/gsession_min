package jp.groupsession.v2.wml.util;

import jp.co.sjts.util.encryption.Blowfish;

import org.apache.commons.codec.binary.Base64;

/**
 * <br>[機  能] WEBメール内で使用するユーティリティークラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlUtil {

    /**
     * <p>パスフレーズ定数
     * <p>echo "GS2 Webmail" | md5sum の結果
     * <p>[411c41497884e53e592ae8a89b307f5c]
     * <p>パスフレーズは最大16桁までなので先頭から16文字
     */
    public static final String PHRASE = "411c41497884e53e";

    /**
     * <br>[機  能] アカウント文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param host サーバ名
     * @param port ポート
     * @param user ユーザID
     * @return アカウント文字列
     * @throws Exception アカウント文字列の生成に失敗
     */
    public static String getAccountString(String host, int port, String user) throws Exception {

        StringBuilder sb = new StringBuilder(host);
        sb.append(":");
        sb.append(port);
        sb.append(":");
        sb.append(user);

        byte[] enpt = Blowfish.encrypt(PHRASE, sb.toString());
        sb = null;
        return new String(Base64.encodeBase64(enpt));

    }

    /**
     * <br>[機  能] パスワードの暗号化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param password パスワード
     * @return 暗号化したパスワード
     * @throws Exception パスワードの暗号化に失敗
     */
    public static String encryptMailPassword(String password) throws Exception {
        byte[] encryptValue = Blowfish.encrypt(PHRASE, password);
        return new String(Base64.encodeBase64(encryptValue), "UTF-8");
    }

    /**
     * <br>[機  能] パスワードの復号を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param encryptPassword 暗号化したパスワード
     * @return パスワード
     * @throws Exception パスワードの復号に失敗
     */
    public static String decryptMailPassword(String encryptPassword) throws Exception {
        byte[] decryptValue = Base64.decodeBase64(encryptPassword.getBytes("UTF-8"));
        return Blowfish.decrypt(PHRASE, decryptValue);
    }
}
