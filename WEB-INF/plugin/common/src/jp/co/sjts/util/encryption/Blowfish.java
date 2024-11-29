package jp.co.sjts.util.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import jp.co.sjts.util.Encoding;

/**
 * <br>[機  能] 暗号化Blowfishに関するユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Blowfish {
    /** 暗号化アルゴリズム */
    private static final String ALGORITHM = "Blowfish";

    /**
     * <br>[機  能] Blowfishによる暗号化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param key パスフレーズ
     * @param text 暗号化対象文字列
     * @return 暗号化した文字列
     * @throws IllegalBlockSizeException ブロック暗号に提供されたデータの長さが正しくない
     * @throws InvalidKeyException 無効な符号化、長さの誤り、未初期化などの無効な鍵である
     * @throws NoSuchAlgorithmException 暗号アルゴリズムが現在の環境では使用できない
     * @throws UnsupportedEncodingException サポートされないエンコード
     * @throws BadPaddingException 適切にパディングされない
     * @throws NoSuchPaddingException 現在の環境では使用可能でない場合にスロー
     */
    public static byte[] encrypt(String key, String text)
            throws IllegalBlockSizeException,
            InvalidKeyException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException,
            BadPaddingException,
            NoSuchPaddingException {

        SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
        byte[] encrypted = cipher.doFinal(text.getBytes(Encoding.UTF_8));
        return encrypted;
    }

    /**
     * <br>[機  能] Blowfishによる暗号化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param key パスフレーズ
     * @param encrypted 複合化対象文字列
     * @return 複合化した文字列
     * @throws IllegalBlockSizeException ブロック暗号に提供されたデータの長さが正しくない
     * @throws InvalidKeyException 無効な符号化、長さの誤り、未初期化などの無効な鍵である
     * @throws NoSuchAlgorithmException  暗号アルゴリズムが現在の環境では使用できない
     * @throws UnsupportedEncodingException サポートされないエンコード
     * @throws BadPaddingException 適切にパディングされない
     * @throws NoSuchPaddingException 現在の環境では使用可能でない場合にスロー
     */
    public static String decrypt(String key, byte[] encrypted)
            throws IllegalBlockSizeException,
            InvalidKeyException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException,
            BadPaddingException,
            NoSuchPaddingException {

        SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, sksSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, Encoding.UTF_8);
    }
}
