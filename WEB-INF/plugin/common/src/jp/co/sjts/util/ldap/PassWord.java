package jp.co.sjts.util.ldap;

import java.io.UnsupportedEncodingException;

/**
 * <br>[機  能] パスワードに関するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PassWord {

    /** パスワード */
    private String password__;

    /**
     * コンストラクタ
     * @param password パスワード
     */
    public PassWord(String password) {
        password__ = password;
    }

    /**
     * <br>[機  能] パスワードをバイト配列(UTF-16LE)で返す
     * <br>[解  説]
     * <br>[備  考]
     * @return byte[] パスワード(バイト配列(UTF-16LE))
     */
    public byte[] getBytePassword() {
        byte[] bytepass = null;
        try {
            bytepass = ("\"" + password__ + "\"").getBytes("UTF-16LE");
        } catch (UnsupportedEncodingException e) {
        }
        return bytepass;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password__;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        password__ = password;
    }

}
