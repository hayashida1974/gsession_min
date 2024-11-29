package jp.co.sjts.util;

import java.util.Random;

/**
 * <br>[機  能] ランダムなパスワードを作成します。
 * <br>[解  説]
 * <p><br><br>
 *
 * <p>パスワードルール
 * <ul>
 * <li>半角英数字のみ使用可能(A-Z,a-z,0-9)
 * <li>6文字以上
 * <li>英字のみ、数字のみのパスワードは使用できない
 * <li>大文字と小文字は区別する。
 * </ul>
 *
 * <p>ユーザの利便性を考慮し、6-10文字の間で作成する。
 * <br>[備  考]
 * @author JTS
 */
public class RandomPassword {

    /** 使用可能な全ての文字テーブル */
    private static final String[] TABLE__ = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z",
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
        "u", "v", "w", "x", "y", "z",
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    /**
     * <br>[機  能] ランダムなパスワードを生成します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 生成されたパスワード
     */
    public static String createPassword() {
        String password = "";

        Random rdm = new Random();
        int mojisuu = getRandomInt(6, 10);

        //必ず必要な英字のインデックス(1文字のみ)
        int eiji = rdm.nextInt(mojisuu);
        //必ず必要な数値のインデックス(1文字のみ)
        int suuchi = 0;
        boolean flg = true;
        while (flg) {
            suuchi = rdm.nextInt(mojisuu);
            if (eiji != suuchi) {
                flg = false;
            }
        }

        for (int intCnt = 0; intCnt < mojisuu; intCnt++) {
            if (intCnt == eiji) {
                password += TABLE__[rdm.nextInt(42)];
            } else if (intCnt == suuchi) {
                password += Integer.toString(rdm.nextInt(10));
            } else {
                password += TABLE__[rdm.nextInt(52)];
            }
        }
        return password;
    }

    /**
     * <br>[機  能] 指定した範囲でのランダムな正数値を返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param min 下限値 min値も含む
     * @param max 上限値 max値も含む
     * @return ランダムに生成された整数値
     */
    public static int getRandomInt(int min, int max) {
        Random rdm = new Random();
        boolean flg = true;
        int ret = 0;
        while (flg) {
            ret = rdm.nextInt(max + 1);
            if (ret >= min) {
                flg = false;
            }
        }
        return ret;
    }
}
