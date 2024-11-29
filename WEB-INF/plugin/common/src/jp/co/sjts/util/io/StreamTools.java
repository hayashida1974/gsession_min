package jp.co.sjts.util.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <br>[機  能] Stream関係のユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class StreamTools {

    /**
     * <br>[機  能] InputStreamをbyte配列へ変換します。(バッファーサイズ1024)
     * <br>[解  説]
     * <br>[備  考] 注意事項：メモリ上に展開されるためOutOfMemoryErrorに注意してください。<br>
     * 本メソッドで引数のInputStreamはcloseしません。呼び出し元でcloseしてください。
     *
     * @param src  対象のInputStream
     * @exception IOException IOエラー
     * @return 変換後のbyte[]
     */
    public static byte[] readInputStreamToByteArray(
        InputStream src
        )throws IOException {
        return readInputStreamToByteArray(src, 1024);
    }

    /**
     * <br>[機  能] InputStreamをbyte配列へ変換します。
     * <br>[解  説]
     * <br>[備  考] 注意事項：メモリ上に展開されるためOutOfMemoryErrorに注意してください。<br>
     * 本メソッドで引数のInputStreamはcloseしません。呼び出し元でcloseしてください。
     *
     * @param src  対象のInputStream
     * @param buffersize バッファーサイズ
     * @exception IOException IOエラー
     * @return 変換後のbyte[]
     */
    public static byte[] readInputStreamToByteArray(
        InputStream src,
        int buffersize
        )throws IOException {

        ByteArrayOutputStream out = null;

        try {
            BufferedInputStream in = new BufferedInputStream(src);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[buffersize];
            int count = 0;
            do {
                out.write(buffer, 0, count);
                count = in.read(buffer, 0, buffer.length);
            } while (count != -1);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return out.toByteArray();
    }
}
