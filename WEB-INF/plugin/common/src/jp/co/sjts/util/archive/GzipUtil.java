package jp.co.sjts.util.archive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import jp.co.sjts.util.io.IOTools;

/**
 * <br>[機  能] gzipユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GzipUtil {

    /** ファイル終端 */
    private static final int EOF = -1;
    /** ファイル作成時のバッファーサイズ(4MB) */
    public static final int BUFFERSIZE_CREATE = 4 * 1024 * 1024;
    /**
     * <br>[機  能] 特定の1ファイルをgzipに圧縮する
     * <br>[解  説]
     * <br>[備  考] 圧縮ファイルパスにディレクトリは指定不可
     *
     * @param inputPath 圧縮ファイルパス
     * @param outputPath 出力ファイルパス
     * @throws IOException 入出力例外が発生した場合
     */
    public static void createGzipFile(String inputPath, String outputPath)
        throws IOException {

        String fisPath = IOTools.replaceFileSep(inputPath);
        File inputFile = new File(fisPath);

        if (inputFile.isDirectory()) {
            throw new IOException("パスにディレクトリが指定されています");
        }

        FileInputStream fis = null;

        GZIPOutputStream gos = null;
        int length;
        byte[] buffer = new byte[BUFFERSIZE_CREATE];

        try {
            fis = new FileInputStream(fisPath);
            gos = new GZIPOutputStream(
                    new FileOutputStream(outputPath));

            while ((length = fis.read(buffer)) != EOF) {
                gos.write(buffer, 0, length);
            }

            gos.finish();

        } finally {

            if (fis != null) {
                fis.close();
            }
            if (gos != null) {
                gos.close();
            }
        }
    }

    /**
     * <br>[機  能] 特定のgzipを解凍する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param inputPath 解凍ファイルパス
     * @param outputPath 出力ファイルパス
     * @throws IOException 入出力例外が発生した場合
     */
    public static void decompressionGzipFile(String inputPath, String outputPath)
        throws IOException {

        String fisPath = IOTools.replaceFileSep(inputPath);
        File inputFile = new File(fisPath);

        GZIPInputStream gis = null;
        BufferedOutputStream bos = null;
        try {
            gis = new GZIPInputStream(
                    new BufferedInputStream(
                        new FileInputStream(inputFile)));

            bos = new BufferedOutputStream(
                    new FileOutputStream(outputPath));

            int c;
            while ((c = gis.read()) != EOF) {
               bos.write((byte) c);
            }
            bos.flush();

        } finally {
            if (gis != null) {
                gis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }
}