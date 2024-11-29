package jp.co.sjts.util.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * <br>[機 能] MD5によるメッセージダイジェスト処理の実装クラス
 * <br>[解 説] メッセージダイジェストを生成し、戻り値はBASE64でASCIIに変換した文字列を返します。
 * <br>[備 考]
 * @author JTS
 */
public class Md5 {

    /**
     * <p>メッセージダイジェストを生成
     * @param passphrase パスフレーズ
     * @param text ダイジェスト生成対象文字列
     * @return メッセージダイジェスト(BASE64化されている)
     */
    public static String encrypt(String passphrase, String text) {

        String mdpassstring = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passphrase.getBytes());
            byte[] mdpass = md.digest(text.getBytes());
            mdpassstring = new String(Base64.encodeBase64(mdpass));
        } catch (NoSuchAlgorithmException e) {
        }
        return mdpassstring;
    }
}