package jp.co.sjts.util.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * <br>[機 能] Sha1,256,384,512によるメッセージダイジェスト処理の実装クラス
 * <br>[解 説] メッセージダイジェストを生成し、戻り値はBASE64でASCIIに変換した文字列を返します。
 * <br>[備 考]
 * @author JTS
 */
public class Sha {

    /**
     * [機 能] Sha1によるメッセージダイジェストを生成。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param passphrase パスフレーズ
     * @param text ダイジェスト生成対象
     * @return メッセージダイジェスト(BASE64化されている)
     */
    public static String encryptSha1(String passphrase, String text) {

        String mdpassstring = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(passphrase.getBytes());
            byte[] mdpass = md.digest(text.getBytes());
            mdpassstring = new String(Base64.encodeBase64(mdpass));
        } catch (NoSuchAlgorithmException e) {
        }
        return mdpassstring;
    }

    /**
     * [機 能] Sha256によるメッセージダイジェストを生成<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param passphrase パスフレーズ
     * @param text ダイジェスト生成対象
     * @return メッセージダイジェスト(BASE64化されている)
     */
    public static String encryptSha256(String passphrase, String text) {

        String mdpassstring = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passphrase.getBytes());
            byte[] mdpass = md.digest(text.getBytes());
            mdpassstring = new String(Base64.encodeBase64(mdpass));
        } catch (NoSuchAlgorithmException e) {
        }
        return mdpassstring;
    }

    /**
     * [機 能] Sha384によるメッセージダイジェストを生成<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param passphrase パスフレーズ
     * @param text ダイジェスト生成対象
     * @return メッセージダイジェスト(BASE64化されている)
     */
    public static String encryptSha384(String passphrase, String text) {

        String mdpassstring = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(passphrase.getBytes());
            byte[] mdpass = md.digest(text.getBytes());
            mdpassstring = new String(Base64.encodeBase64(mdpass));
        } catch (NoSuchAlgorithmException e) {
        }
        return mdpassstring;
    }

    /**
     * [機 能] Sha512によるメッセージダイジェストを生成<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param passphrase パスフレーズ
     * @param text ダイジェスト生成対象
     * @return メッセージダイジェスト(BASE64化されている)
     */
    public static String encryptSha512(String passphrase, String text) {

        String mdpassstring = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(passphrase.getBytes());
            byte[] mdpass = md.digest(text.getBytes());
            mdpassstring = new String(Base64.encodeBase64(mdpass));
        } catch (NoSuchAlgorithmException e) {
        }
        return mdpassstring;
    }


    /**
     * [機 能] Sha512によるメッセージダイジェストを生成<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param passphrase パスフレーズ
     * @return メッセージダイジェスト(BASE64化されている)
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     */
    public static String encryptSha512AsHex(String passphrase)
            throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(passphrase.getBytes());
        byte[] hash = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        return hexString.toString();
    }
}
