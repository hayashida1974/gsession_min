package jp.co.sjts.util.archive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
/**
 *
 * <br>[機 能] zip圧縮、解凍を行うクラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class ZipUtil {

    /** ファイル作成時のバッファーサイズ(4MB) */
    public static final int BUFFERSIZE_CREATE = 4 * 1024 * 1024;

    /**
     * [機 能] 単一のファイルをZIPする。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param encoding ファイルシステムのエンコーディング
     * @param zipFilePath ZIP出力ファイル名
     * @param srcFilePath 入力ファイル名
     * @throws IOException IOエラー
     * @throws IOToolsException IOエラー
     */
    public static void zipFile(String srcFilePath,
            String zipFilePath) throws IOException, IOToolsException {
        File file = new File(srcFilePath);

        if (file.isDirectory()) {
            throw new IOException("パスにディレクトリが指定されています。");
        }
        String[] srcDirPathList = {srcFilePath};
        __createZip(zipFilePath, srcDirPathList);
    }

    /**
     * [機 能] ファイルをZIP形式で圧縮する。<br>
     * [解 説] <br>
     * [備 考] Deflater<br>
     *
     * @param zipFilePath ZIP出力ファイル名
     * @param srcFilePath 入力ファイル名
     * @throws IOException IOエラー
     */
    public static void zipDeflaterFile(String srcFilePath, String zipFilePath)
    throws IOException {

        File file = new File(srcFilePath);

        if (file.isDirectory()) {
            throw new IOException("パスにディレクトリが指定されています。");
        }

        FileInputStream fis = null;
        DeflaterOutputStream dos = null;

        int length;
        byte[] buffer = new byte[BUFFERSIZE_CREATE];

        try {
            fis = new FileInputStream(file);
            dos = new DeflaterOutputStream(
                    new BufferedOutputStream(new FileOutputStream(zipFilePath)));

            while ((length = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
            }

            dos.finish();

        } finally {
            __close(fis);
            __close(dos);
        }
    }

    /**
     * [機 能] 単一のディレクトリをZIPする。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param srcDirPath 入力ディレクトリ名
     * @param zipFilePath ZIP出力ファイル名
     * @throws IOException IOエラー
     * @throws IOToolsException IOエラー
     */
    public static void zipDir(
        String srcDirPath,
        String zipFilePath) throws IOException, IOToolsException {
        
        File dir = new File(srcDirPath);

        if (dir.isFile()) {
            throw new IOException("パスにファイルが指定されています。");
        }
        String[] srcDirPathList = {srcDirPath};
        __createZip(zipFilePath, srcDirPathList);
    }

    /**
     * [機 能] 単一のディレクトリをZIPする。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param encording 文字コード
     * @param srcDirPath 入力ディレクトリ名
     * @param zipFilePath ZIP出力ファイル名
     * @throws IOException IOエラー
     * @throws IOToolsException IOエラー
     */
    public static void zipDir(
        String encording,
        String srcDirPath,
        String zipFilePath) throws IOException, IOToolsException {
        
        zipDir(srcDirPath, zipFilePath);
    }

    /**
     * [機 能] 複数のディレクトリ/ファイルをZIPする。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param srcDirPathList 入力ディレクトリ名の一覧
     * @param zipFilePath ZIP出力ファイル名
     * @throws IOException IOエラー
     * @throws IOToolsException IOエラー
     */
    public static void zip(
        String[] srcDirPathList,
        String zipFilePath) throws IOException, IOToolsException {

        __createZip(zipFilePath, srcDirPathList);
    }

    /**
     * [機 能] 複数のディレクトリ/ファイルをZIPする。<br>
     * [解 説] <br> 「ZIPファイルに含めない」ファイル名の一覧を指定する
     * [備 考] <br>
     * @param srcDirPathList 入力ディレクトリ名の一覧
     * @param zipFilePath ZIP出力ファイル名
     * @param excludeList 除外するファイル一覧(ファイル名)
     * @throws IOException IOエラー
     * @throws IOToolsException IOエラー
     */
    public static void zipExclude(
        String[] srcDirPathList,
        String zipFilePath,
        List<String> excludeList) throws IOException, IOToolsException {

        __createZip(zipFilePath, srcDirPathList, "", excludeList);
    }

    /**
     * [機 能] 複数のディレクトリ/ファイルをパスワード付きでZIPする。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param fileList 入力ファイル一覧
     * @param zipFilePath ZIP出力ファイルパス
     * @param pass パスワード
     * @throws IOException
     * @throws IOToolsException 
     */
    public static void zipWithPass(
        String[] srcDirPathList,
        String zipFilePath,
        String pass) throws IOException, IOToolsException {

        __createZip(zipFilePath, srcDirPathList, pass, null);
    }
    
    /**
     * [機 能] targetFilesをZIP圧縮してzipFileに出力する。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param zipFilePath ZIP出力ファイル名
     * @param targetFiles 入力ファイル名(ディレクトリ)配列
     * @throws IOException 入出力例外
     * @throws IOToolsException  入出力例外
     */
    private static void __createZip(
        String zipFilePath,
        String[] srcDirPathList) throws IOException, IOToolsException {

        __createZip(zipFilePath, srcDirPathList, "", null);
    }

    /**
     * [機 能] targetFilesをZIP圧縮してzipFileに出力する。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param zipFilePath ZIP出力ファイル名
     * @param targetFiles 入力ファイル名(ディレクトリ)配列
     * @param pass パスワード
     * @param excludeList 除外するファイル一覧(ファイル名)
     * @throws IOException 入出力例外
     * @throws IOToolsException  入出力例外
     */
    private static void __createZip(
        String zipFilePath,
        String[] srcDirPathList,
        String pass,
        List<String> excludeList) throws IOException, IOToolsException {

        String encoding = "UTF-8";
        ZipOutputStream out = null;
        try {
            if (!NullDefault.getStringZeroLength(pass, "").isEmpty()) {
                out = new ZipOutputStream(
                    new BufferedOutputStream(new FileOutputStream(zipFilePath)),
                    pass.toCharArray(),
                    Charset.forName(encoding));
            } else {
                out = new ZipOutputStream(
                    new BufferedOutputStream(new FileOutputStream(zipFilePath)),
                    Charset.forName(encoding));
            }
            for (int i = 0; i < srcDirPathList.length; i++) {
                File file = new File(srcDirPathList[i]);
                int deleteLength = file.getPath().length() - file.getName().length();
                __createZip(out, file, deleteLength, pass, excludeList);
            }
        } finally {
            __close(out);
        }
    }

    /**
     * [機 能] targetFileをoutのZIPエントリへ出力する。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param out ZIP出力先
     * @param targetFile 入力ファイル名(ディレクトリ)
     * @param deleteLength Zipエントリから削除するパスの長さ
     * @param excludeList 除外するファイル一覧(ファイル名)
     * @throws IOException 入出力例外
     * @throws IOToolsException 入出力例外
     */
    private static void __createZip(
        ZipOutputStream out,
        File targetFile,
        int deleteLength,
        String pass,
        List<String> excludeList) throws IOException, IOToolsException {

        if (targetFile.isDirectory()) {
            File[] files = targetFile.listFiles();
            if (files == null) {
                throw new IOToolsException("ディレクトリアクセスに失敗："+ targetFile.getAbsolutePath());
            }
            for (int i = 0; i < files.length; i++) {
                __createZip(out, files[i], deleteLength, pass, excludeList);
            }
        } else {
            //除外するファイルかを確認
            if (excludeList != null && excludeList.contains(targetFile.getName())) {
                return;
            }

            ZipParameters zipParam = new ZipParameters();
            zipParam.setCompressionLevel(CompressionLevel.NORMAL);
            if (!NullDefault.getStringZeroLength(pass, "").isEmpty()) {
                zipParam.setEncryptFiles(true);
                zipParam.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
            }
            zipParam.setFileNameInZip(__getEntryPath(targetFile, deleteLength));
            out.putNextEntry(zipParam);
            byte[] buf = new byte[BUFFERSIZE_CREATE];
            int count;
            BufferedInputStream in = null;
            try {
                in = new BufferedInputStream(
                        new FileInputStream(targetFile));
                while ((count = in.read(buf)) != -1) {
                    out.write(buf, 0, count);
                }
            } finally {
                __close(in);
                __zipCloseEntry(out);
            }
        }
    }

    /**
     * [機 能] ZIPエントリパスを返す。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param file ZIPエントリ対象ファイル
     * @param deleteLength Zipエントリから削除するパスの長さ
     * @return ZIPエントリのパス
     */
    private static String __getEntryPath(File file, int deleteLength) {
        return file.getPath().replaceAll("\\\\", "/").substring(deleteLength);
    }

    /**
     *
     * <br>[機  能] クローズ処理
     * <br>[解  説] finnallyで利用するため
     * <br>[備  考]
     * @param cls クローズ対象
     */
    private static void __close(Closeable cls) {
        try {
            cls.close();
        } catch (NullPointerException e) {
        } catch (IOException e) {
        }
    }

    /**
     *
     * <br>[機  能] クローズ処理
     * <br>[解  説] finnallyで利用するため
     * <br>[備  考]
     * @param cls クローズ対象
     */
    private static void __zipCloseEntry(ZipOutputStream cls) {
        try {
            cls.closeEntry();
        } catch (NullPointerException e) {
        } catch (IOException e) {
        }
    }
}