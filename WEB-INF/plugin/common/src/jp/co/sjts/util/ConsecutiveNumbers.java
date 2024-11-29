package jp.co.sjts.util;

import java.io.File;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機 能] ファイル名に連番を付加する機能を提供します
 * <br>[解 説]
 * <br>[備 考]
 * @author JTS
 */
public class ConsecutiveNumbers {

    /**
     * <br>[機 能] ファイル名に連番を付加する
     * <br>[解 説]
     * <br>[備 考]
     * @param dirname ディレクトリ名
     * @return String ファイル名(ミリ秒＋連番)
     */
    public static String numbering(String dirname) {

        int num = 0;
        String filenumber = "";

        if (dirname == null || "".equals(dirname)) {
            return filenumber;
        }

        UDate date = new UDate();
        String strDate = date.getTimeStamp() + date.getStrMilliSecond();

        while (true) {
            num++;
            String filenum = StringUtil.toDecFormat(String.valueOf(num), "000");
            File file = new File(dirname + "/" + strDate + filenum);
            if (!file.exists()) {
                break;
            }
        }

        filenumber = strDate + StringUtil.toDecFormat(String.valueOf(num), "000");
        return filenumber;
    }
}