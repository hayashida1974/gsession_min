package jp.groupsession.v2.usr;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.encryption.Blowfish;
import jp.co.sjts.util.encryption.EncryptionException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] パスワードに関する機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSPassword {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSPassword.class);
    /**
     * <p>パスフレーズ定数
     * <p>echo "GSession2" | md5sum の結果
     * <p>[d41d8cd98f00b204e9800998ecf8427e]
     * <p>パスフレーズは最大16桁までなので先頭から16文字
     */
    public static final String GSESSION2_PHRASE = "d41d8cd98f00b204";

    /**
     * <p>入力されたパスワードをダミー文字列(***)に置き換える
     * @param text パスワード
     * @return ダミー(***)に変換された文字列
     */
    public static String createDamyPassword(String text) {

        String pwdamy = "";
        if (!StringUtil.isNullZeroString(text)) {
            String pw = text;
            for (int cnt = 0; cnt < pw.length(); cnt++) {
                pwdamy += "*";
            }
        }
        return pwdamy;
    }

    /**
     * <br>[機  能] 暗号化されたパスワードを復号する
     * <br>[解  説]
     * <br>[備  考]
     * @param password 暗号化されたパスワード
     * @return 復号化したパスワード
     * @throws EncryptionException パスワードの復号に失敗
     */
    public static String getDecryPassword(String password) throws EncryptionException {

        String decStr = null;

        if (password != null) {
            try {
                byte[] decBytes = Base64.decodeBase64(password.getBytes());
                decStr = Blowfish.decrypt(GSESSION2_PHRASE, decBytes);
            } catch (Exception e) {
                log__.error("パスワード復号化に失敗", e);
                throw new EncryptionException("パスワード復号化に失敗", e);
            }
        }

        return decStr;
    }

    /**
     * <br>[機  能] 暗号化されたパスワードを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param password パスワード
     * @return 暗号化されたパスワード
     * @throws EncryptionException 暗号化に失敗
     */
    public static String getEncryPassword(String password) throws EncryptionException {

        String enStr = null;

        try {
            byte[] enpt = Blowfish.encrypt(GSESSION2_PHRASE, password);
            enStr = new String(Base64.encodeBase64(enpt));
        } catch (Exception e) {
            log__.error("暗号化に失敗", e);
            throw new EncryptionException("暗号化に失敗", e);
        }
        return enStr;
    }
}