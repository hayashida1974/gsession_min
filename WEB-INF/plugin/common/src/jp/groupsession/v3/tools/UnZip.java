package jp.groupsession.v3.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.co.sjts.util.io.IOTools;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] zip解凍プログラムクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UnZip {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UnZip.class);
    /** エンコード Windows-31J */
    public static final String ENCORD_TYPE_WINDOWS_31J = "Windows-31J";

    /**
     * <br>[機  能] zip解凍開始プログラムです
     * <br>[解  説]
     * <br>[備  考]
     * @param args コマンドライン引数
     * @throws Exception 実行例外
     */
    public static void main(String[] args) throws Exception {

        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);

        log__.info("解凍するファイルのパスを入力してください.");

        String zipPath = br.readLine();

        log__.info("解凍したファイルの保存先パスを入力してください.");
        String unzipBackupRootDir = br.readLine();

        log__.info("解凍ファイルのパス:" + zipPath);

        //拡張子を除いたディレクトリ名を取得
        String baseName = FilenameUtils.getBaseName(zipPath);
        String unzipBackupDir = unzipBackupRootDir + File.separator + baseName;

        log__.info("保存先パス:" + unzipBackupDir);

        //ディレクトリ存在チェック(なければ作成)
        File file = new File(unzipBackupRootDir);
        boolean isDirHnt = file.exists();
        IOTools.isDirCheck(unzipBackupDir, true);

        try {

            log__.info("--------解凍処理開始----------");

            //unzipする
            unzip(zipPath, unzipBackupDir, ENCORD_TYPE_WINDOWS_31J);

        } catch (Exception e) {
            log__.info("");
            log__.info("");
            log__.info("ファイルの解凍に失敗しました", e);
            log__.info("");
            log__.info("ERROR:ファイルの解凍に失敗");
            log__.info("ファイル名が正しく設定されているか確認し、再度実行してください。");

            if (!isDirHnt) {
                //ディレクトリを削除
                IOTools.deleteDir(unzipBackupRootDir);
            }

            return;
        }

        log__.info("--------解凍処理終了----------");
    }

    /**
     * <br>[機  能] ZIPファイルを解凍します。
     * <br>[解  説]
     * <br>[備  考]
     * @param filename ZIPファイル名
     * @param outDir 解凍先ディレクトリ名
     * @param encoding ファイルシステムのエンコーディング
     * @throws IOException IO例外
     */
    public static void unzip(String filename, String outDir, String encoding) throws IOException {
        uncompress(filename, outDir, encoding);
    }

    /**
     * <br>[機  能] ZIPファイルを解凍します。
     * <br>[解  説]
     * <br>[備  考]
     * @param readPath 読み込むZipファイルのパス
     * @param outDir 解凍先ディレクトリ名
     * @param encord ファイルシステムのエンコーディング
     * @throws IOException IO例外
     */
    public static void uncompress(String readPath, String outDir, String encord)
            throws IOException {

        //InputStreamの生成
        ZipArchiveInputStream arcInputStream = new ZipArchiveInputStream(
            new FileInputStream(readPath), encord, true);

        BufferedInputStream bis = new BufferedInputStream(arcInputStream);

        //Entryを1つずつ取得し、ファイル出力する
        ArchiveEntry entry;
        while ((entry = arcInputStream.getNextEntry()) != null) {
            // ディレクトリの場合は、ディレクトリ生成
            if (entry.isDirectory()) {
                new File(entry.getName()).mkdirs();

            } else {
                // ファイルの場合は、ファイル出力
                File file = new File(outDir, entry.getName());
                file.getParentFile().mkdirs();

                // OutputStreamの生成
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(file));

                // 出力
                int size;
                int buffersize = 8 * 1024;
                byte[] buff = new byte[buffersize];
                while ((size = bis.read(buff)) != -1) {
                    bos.write(buff, 0, size);
                }
                bos.flush();
                buff = null;

                bos.close();
            }
        }

        bis.close();
    }
}

