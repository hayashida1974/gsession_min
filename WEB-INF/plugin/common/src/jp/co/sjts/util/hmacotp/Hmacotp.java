package jp.co.sjts.util.hmacotp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.openauthentication.otp.OneTimePasswordAlgorithm;

/**
 *
 * <br>[機  能] HOTPジェネレータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hmacotp {
    /**
     *
     * <br>[機  能] HOTPの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param otpkey otpキー
     * @param counter 生成対象回数
     * @return ワンタイムパスワード
     */
    public static String generate(String otpkey, int counter) {

        String format = otpkey + "";
        try {
            return OneTimePasswordAlgorithm.generateOTP(format.getBytes(), counter, 8, true, 8);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
