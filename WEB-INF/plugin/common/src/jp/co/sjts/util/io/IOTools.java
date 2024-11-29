package jp.co.sjts.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.sort.FileCompare;

/**
 * <br>[機  能] I/O関係のユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IOTools {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IOTools.class);
    /**
     * <br>[機  能] ディレクトリパスのチェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dirPath ディレクトリのパスを指定します。
     * @param blnCreate true:ファイルが存在しない場合に作成します。
     *         ,false:作成しません。
     * @return true:ファイルが存在します(作成した場合も)。
     *          false:ファイルが存在しません。
     * @exception IOToolsException IOエラー
     */
    public static boolean isDirCheck(
        final String dirPath,
        final boolean blnCreate
        )throws IOToolsException {

        String path = dirPath;

        path = setEndPathChar(dirPath); //パスの最後の文字をセパレータに設定
        path = replaceFileSep(dirPath); //ファイルセパレータを変換
        File file = null;
        try {

            //ディレクトリの有効性をチェック
            file = new File(path);
            boolean exists = true;

            if (!file.exists()) {
                //ファイルが存在しない場合
                if (blnCreate) { //作成する
                    if (!file.mkdirs()) {

                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            throw new IOToolsException("書き込み権限を取得できないため"
                                    + "ディレクトリ作成に失敗しました。" + path);
                        } else {
                            throw new IOToolsException("親ディレクトリのパスを取得する事が"
                                    + "出来ないためディレクトリ作成に失敗しました。" + path);
                        }

                    }

                } else { //作成しない
                    exists = false;
                }
            }

            return exists;
        } catch (SecurityException e) {
            throw new IOToolsException("アクセス権エラー:" + path);
        } finally {
            path = null;
            file = null;
        }
    }

    /**
     * <br>[機  能] ファイルの有効性チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dirPath ディレクトリのパスを指定します。
     * @param fileName ファイル名を指定します。
     * @param blnCreate true:ファイルが存在しない場合に作成します。,
     *                   false:作成しません。
     * @return true:ファイルが存在します(作成した場合も)。
     *          false:ファイルが存在しません。
     * @exception IOToolsException IOエラー
     */
    public static boolean isFileCheck(
        final String dirPath,
        final String fileName,
        final boolean blnCreate
        ) throws IOToolsException {

        String path = dirPath;
        path = setEndPathChar(path); //パスの最後の文字をセパレータに設定
        path = replaceFileSep(path); //ファイルセパレータを変換

        boolean result = true;
        //ディレクトリの有効性をチェック
        if (isDirCheck(path, blnCreate)) {
            //有効なディレクトリである
            File file = new File(path + fileName);
            try {
                if (!file.exists()) { //ファイルが存在しない
                    if (blnCreate) { //作成する場合
                        file.createNewFile();

                    } else { //作成しない場合
                        result = false;
                    }
                }
            } catch (IOException e) {
                throw new IOToolsException("IOエラー");
            } catch (SecurityException e) {
                throw new IOToolsException("アクセス権エラー:"
                    + path + File.separator + fileName);
            }
        } else {
            //有効なディレクトリではない
            result = false;
        }

        return result;
    }

    /**
     * <br>[機  能] ファイルセパレーターをプラットホームに合わせて変換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param path ファイル(ディレクトリ)のパス
     * @return 変換後のパス
     */
    public static String replaceFileSep(final String path) {
        return path.replace('/', File.separatorChar);
    }
    /**
     * <br>[機  能] ファイルセパレーターを全て「/」に変換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param path ファイル(ディレクトリ)のパス
     * @return 変換後のパス
     */
    public static String replaceSlashFileSep(final String path) {
        return path.replace('\\', '/');
    }

    /**
     * <br>[機  能] パスの最後の文字がセパレーターでない場合に付加します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dirPath ディレクトリのパス
     * @return パスの最後の文字がセパレータのパス
     */
    public static String setEndPathChar(final String dirPath) {
        String path = dirPath;
        if (!dirPath.endsWith(File.separator)) {
            path += File.separator;
        }
        return path;
    }

    /**
     * <br>[機  能] 引数で指定されたディレクトリにあるファイル名を返します。
     * <br>[解  説]
     * <br>[備  考] ファイルやディレクトリがない場合はnullを返します。
     *
     * @param dirPath ディレクトリ
     * @return Enumeration型でファイル名を返します。
     */
    public static List<String> getFileNames(final String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }

        //ファイル情報一覧をファイル名順に並び替える
        Arrays.sort(files, new FileCompare());

        ArrayList<String> list = new ArrayList<String>();
        for (int intCnt = 0; intCnt < files.length; intCnt++) {
            list.add(files[intCnt].getName());
        }
        return list;
    }

    /**
     * <br>[機  能] 引数で指定されたディレクトリにあるファイルオブジェクトを返します。
     * <br>[解  説]
     * <br>[備  考] ファイルやディレクトリがない場合はnullを返します。
     *
     * @param dirPath ディレクトリのパス
     * @return Enumeration型でファイルを返します。
     */
    public static Enumeration<File> getFiles(final String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }

        Vector<File> vec = new Vector<File>();
        for (int intCnt = 0; intCnt < files.length; intCnt++) {
            vec.add(files[intCnt]);
        }
        return vec.elements();
    }

    /**
     * <br>[機  能] 引数で指定されたディレクトリにあるディレクトリを返します。(1階層のみ)
     * <br>[解  説]
     * <br>[備  考] ディレクトリがない場合はnullを返します。
     *
     * @param dirPath ディレクトリのパス
     * @return Enumeration型でファイルを返します。
     */
    public static List<File> getDirs(final String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }

        ArrayList<File>list = new ArrayList<File>();
        for (int intCnt = 0; intCnt < files.length; intCnt++) {
            if (files[intCnt].isDirectory()) {
                list.add(files[intCnt]);
            }
        }
        return list;
    }

    /**
     * <br>[機  能] 指定したディレクトリ以下を全て削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dirPath ディレクトリのパス
     */
    public static void deleteDir(final String dirPath) {
        File dir = new File(dirPath);
        try {
            deleteDir(dir);
        } catch (IOToolsException e) {
            log__.error("ディレクトリの削除に失敗 path=" + dirPath, e);
        }
    }

    /**
     * <br>[機  能] 指定したディレクトリ以下を全て削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dir ディレクトリ
     * @exception IOToolsException 削除に失敗した場合にスロー
     */
    public static void deleteDir(final File dir)throws IOToolsException {
        if (!dir.exists()) { //すでにディレクトリが存在しない場合
            return;
        }
        if (!dir.isDirectory()) {
            throw new IOToolsException("ディレクトリではありません。");
        }

        //子削除
        File[] files = dir.listFiles();
        for (int intCnt = 0; intCnt < files.length; intCnt++) {
            if (files[intCnt].isDirectory()) {
                deleteDir(files[intCnt]);

            } else if (!files[intCnt].delete()) {
                throw new IOToolsException(
                    files[intCnt].getAbsolutePath()
                    + "の削除に失敗しました。");
            }
        }

        //ディレクトリ削除
        if (!dir.delete()) {
            throw new IOToolsException(
                dir.getAbsolutePath()
                + "の削除に失敗しました。");
        }
    }

    /**
     * <br>[機  能] 指定したディレクトリ内のファイル、ディレクトリを全て削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dirPath ディレクトリのパス
     * @exception IOToolsException IOエラー
     */
    public static void deleteInDir(final String dirPath) throws IOToolsException {
        File dir = new File(dirPath);
        deleteInDir(dir);
    }

    /**
     * <br>[機  能] 指定したディレクトリ内のファイル、ディレクトリを全て削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dir ディレクトリ
     * @exception IOToolsException 削除に失敗した場合にスロー
     */
    public static void deleteInDir(final File dir)throws IOToolsException {
        if (!dir.exists()) { //すでにディレクトリが存在しない場合
            return;
        }
        if (!dir.isDirectory()) {
            throw new IOToolsException("ディレクトリではありません。");
        }

        //子削除
        File[] files = dir.listFiles();
        if (files == null || files.length <= 0) {
            return;
        }

        for (int intCnt = 0; intCnt < files.length; intCnt++) {
            if (files[intCnt].isDirectory()) {
                deleteDir(files[intCnt]);

            } else if (!files[intCnt].delete()) {
                throw new IOToolsException(
                    files[intCnt].getAbsolutePath()
                    + "の削除に失敗しました。");
            }
        }
    }

    /**
     * <br>[機  能] コピー元とコピー先のエンコードを指定してコピーします。
     * <br>[解  説]
     * <br>[備  考] <p>指定したファイルがディレクトリである場合はそれ以下のファイルも再起的に<br>
     *              コピーします。</p>
     *
     * @param srcFile コピー元
     * @param srcEncoding コピー元ファイルのエンコード
     * @param destFile コピー先
     * @param destEncoding コピー先ファイルのエンコード
     * @param filter ファイルフィルター
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     */
    public static void copyNkf(
        final File srcFile,
        final String srcEncoding,
        final File destFile,
        final String destEncoding,
        final FilenameFilter filter
        )
        throws
        IOToolsException,
        IOException {

        if (filter != null) {
            if (!filter.accept(srcFile.getParentFile(), srcFile.getName())) {
                return;
            }
        }

        //
        if (srcFile.isFile()) {
            //ファイルの場合
            copyFile(srcFile, srcEncoding, destFile, destEncoding);
        } else {
            //ディレクトリの場合
            copyDir(srcFile, srcEncoding, destFile, destEncoding, filter);
        }

    }

    /**
     * <br>[機  能] 指定されたディレクトリ以下をコピーします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param srcDir コピー元
     * @param destDir コピー先
     * @exception IOException IOエラー
     * @exception IOToolsException IO
     */
    public static void copyDir(final File srcDir, final File destDir)
        throws
        IOException,
        IOToolsException {
        copyDir(srcDir, null, destDir, null, null);
    }

    /**
     * <br>[機  能] 指定されたディレクトリ以下をコピーします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param srcDir コピー元
     * @param srcEncoding コピー元エンコード
     * @param destDir コピー先
     * @param destEncoding コピー先エンコード
     * @param filter ファイルフィルター
     * @exception IOToolsException エラー
     * @exception IOException IOエラー
     */
    public static void copyDir(
                            final File srcDir,
                            final String srcEncoding,
                            final File destDir,
                            final String destEncoding,
                            final FilenameFilter filter
                            )throws
                            IOToolsException,
                            IOException {

        //コピーの対象であるか判定
        if (filter != null) {
            if (!filter.accept(srcDir.getParentFile(), srcDir.getName())) {
                return;
            }
        }

        //コピー元の存在を確認
        if (!srcDir.exists()) {
            return;
        }
        //ディレクトリであることを確認
        if (!srcDir.isDirectory()) {
            return;
        }

        //コピー先のディレクトリを作成する(存在すればなにもしない)
        if (!isDirCheck(destDir.getAbsolutePath(), true)) {
            throw new IOToolsException("コピーに失敗 ");
        }

        //コピー実行
        File[] files = srcDir.listFiles();
        for (int intCnt = 0; intCnt < files.length; intCnt++) {

            //コピーの対象であるか判定
            if (filter != null) {
                if (!filter.accept(srcDir.getParentFile(), srcDir.getName())) {
                    continue;
                }
            }

            if (files[intCnt].isFile()) {
                //ファイルの場合
                copyFile(
                    files[intCnt],
                    srcEncoding,
                    new File(setEndPathChar(destDir.getAbsolutePath())
                        + files[intCnt].getName()),
                    destEncoding
                );
            } else {
                //ディレクトリの場合
                copyDir(
                    files[intCnt],
                    srcEncoding,
                    new File(setEndPathChar(destDir.getAbsolutePath())
                        + files[intCnt].getName()),
                    destEncoding,
                    filter);
            }
        }
    }

    /**
     * <br>[機  能] ファイルをコピーします。
     * <br>[解  説]
     * <br>[備  考] <p>srcEncoding,destEncodingの両方を指定した場合にエンコードコピーを実行します。<br>
     *              どちらか片方のみや、両方nullの場合はバイナリとしてコピーします。</p>
     * @param srcFile コピー元
     * @param srcEncoding コピー元エンコード
     * @param destFile コピー先
     * @param destEncoding コピー先エンコード
     * @throws IOException IOエラーの場合にスロー
     */
    public static void copyFile(
        final File srcFile,
        final String srcEncoding,
        final File destFile,
        final String destEncoding)
        throws IOException {

        //ファイルの場合
        if (srcEncoding != null && destEncoding != null) {
            //エンコード指定あり、テキストコピー
            copyTextFile(
                srcFile,
                srcEncoding,
                destFile,
                destEncoding);
        } else {
            //エンコードなし、バイナリコピー
            copyBinFile(srcFile, destFile);
        }
    }

    /**
     * <br>[機  能] テキストファイルをコピーする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param srcFile  コピー元ファイル
     * @param srcEncoding コピー元エンコード
     * @param destFile コピー先ファイル
     * @param destEncoding コピー先エンコード
     * @exception IOException IOエラー
     */
    public static void copyTextFile(
        final File srcFile,
        final String srcEncoding,
        final File destFile,
        final String destEncoding)
        throws
        IOException {

        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            //コピー元のエンコード指定がない場合
            if (srcEncoding == null) {
                in = new BufferedReader(new FileReader(srcFile));
            } else {
                in = new BufferedReader(new InputStreamReader(
                                         new FileInputStream(srcFile),
                                         srcEncoding));
            }
            //コピー先のエンコード指定がない場合
            if (destFile == null) {
                out = new BufferedWriter(new FileWriter(destFile));
            } else {
                out = new BufferedWriter(new OutputStreamWriter(
                                         new FileOutputStream(destFile),
                                         destEncoding));
            }

            String line = in.readLine();
            while (line != null) {
                out.write(line);
                out.newLine();
                line = in.readLine();
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] バイナリファイルをコピーする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param srcFile  コピー元ファイル
     * @param destFile コピー先ファイル
     * @exception IOException IOエラー
     */
    public static void copyBinFile(
        final String srcFile,
        final String destFile
        ) throws IOException {
        copyBinFile(new File(srcFile), new File(destFile));
    }

    /**
     * <br>[機  能] バイナリファイルをコピーする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param srcFile  コピー元ファイル
     * @param destFile コピー先ファイル
     * @exception IOException IOエラー
     */
    public static void copyBinFile(
        final File srcFile,
        final File destFile
        )throws IOException {

        int buffersize = 8 * 1024;
        copyBinFile(srcFile, destFile, buffersize);
    }

    /**
     * <br>[機  能] バイナリファイルをコピーする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param srcFile  コピー元ファイル
     * @param destFile コピー先ファイル
     * @param buffersize バッファーサイズ
     * @exception IOException IOエラー
     */
    public static void copyBinFile(
        final File srcFile,
        final File destFile,
        int buffersize
        ) throws IOException {

        Files.copy(Paths.get(srcFile.getPath()),
                    Paths.get(destFile.getPath()),
                    StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * <br>[機  能] pathで指定したファイルを削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param path 削除ファイルのパス
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                deleteFile(file);
            } catch (IOToolsException e) {
                log__.error("ファイルの削除に失敗");
            }
        }
        file = null;
    }

    /**
     * <br>[機  能] pathで指定したファイルを削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param path 削除ファイルのパス
     * @exception IOToolsException IOToolsのエラー
     */
    public static void deleteFile(File path) throws IOToolsException {
        if (path.isDirectory()) {
            throw new IOToolsException("ディレクトリです path=" + path);
        }

        if (!path.delete()) {
            throw new IOToolsException("ファイルの削除に失敗しました path=" + path);
        }
    }

    /**
     * <br>[機  能] 指定したディレクトリ以下を検索し見つかったファイル、ディレクトリを全て返す
     * <br>[解  説]
     * <br>[備  考]
     * @param dir 検索対象のディレクトリ
     * @param filter 検索条件
     * @return 見つかったファイル全て
     * @exception IOToolsException IOToolsのエラー
     */
    public static File[] search(
        final File dir,
        final FilenameFilter filter
        ) throws IOToolsException {

        if (dir.isFile()) {
            throw new IOToolsException("ディレクトリではありません ");
        }

        Vector<File> ret = new Vector<File>();

        //ディレクトリのみ取得し再帰処理を行う
        DirectoryFilter dirFilter = new DirectoryFilter();
        File[] dirs = dir.listFiles(dirFilter);
        if (dirs != null) {
            for (int i = 0; i < dirs.length; i++) {
                File[] retFiles = search(dirs[i], filter);
                ret = __addArrayToVector(ret, retFiles);
            }
        }

        //現在のディレクトリ内から条件に一致すものを取得
        File[] files = dir.listFiles(filter);
        ret = __addArrayToVector(ret, files);

        return __transFileArray(ret);
    }

    /**
     * <br>[機  能] Vectorにファイル内のファイルを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param vec 対象のベクター
     * @param files ファイル
     * @return 追加後のVector
     */
    @SuppressWarnings("unchecked")
    private static Vector < File > __addArrayToVector(
        final Vector < File > vec,
        final File[] files) {
        Vector < File > ret = (Vector<File>) vec.clone();

        if (files == null) {
            return ret;
        }
        for (int i = 0; i < files.length; i++) {
            ret.add(files[i]);
        }
        return ret;
    }

    /**
     * <br>[機  能] ファイルオブジェクトの配列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param vec ファイルオブジェクトを格納したVector
     * @return ファイルオブジェクトの配列
     */
    private static File[] __transFileArray(Vector<File> vec) {
        File[] files = new File[vec.size()];
        for (int i = 0; i < vec.size(); i++) {
            files[i] = (File) vec.get(i);
        }

        return files;
    }

    /**
     * <p>指定したStringをファイルに出力する
     *
     * @param path 出力先ディレクトリ
     * @param fileName ファイル名
     * @param enc エンコード
     * @param str 出力するString
     * @throws IOException IOエラー
     */
    public static void writeText(
        String path,
        String fileName,
        String enc,
        String str
        ) throws IOException {

        String fullpath = path + File.separator + fileName;
        writeText(fullpath, enc, str);
    }

    /**
     * <p>指定したStringをファイルに出力する
     *
     * @param filePath ファイルパス
     * @param enc エンコード
     * @param str 出力するString
     * @throws IOException IOエラー
     */
    public static void writeText(
        String filePath,
        String enc,
        String str
        ) throws IOException {

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(filePath), enc));
            out.write(str);
        } catch (IOException e) {
            throw e;
        } finally {
            out.close();
        }
    }

    /**
     * <br>[機  能] テキストファイルを文字列で返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param textPath 読込むテキストファイルパス
     * @param enc 文字エンコーディング
     * @throws IOException 入出力例外
     * @return テキスト文字列
     */
    public static String readText(String textPath, String enc) throws IOException {
        StringBuilder buffer = new StringBuilder();
        InputStreamReader reader = null;

        try {
            reader = new InputStreamReader(new FileInputStream(textPath), enc);

            char[] charBuffer = new char[1024]; // 一時読み込み
            int byteRead = 0;

            // ファイルからデータを読み込み
            while ((byteRead = reader.read(charBuffer, 0, 1024)) != -1) {
                // 読込データ格納
                buffer.append(charBuffer, 0, byteRead);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return buffer.toString();
    }

    /**
     * <br>[機  能] 指定したディレクトリ以下を全て削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dirPath ディレクトリのパス
     * @exception IOToolsException IOエラー
     */
    public static void deleteDirRepeat(final String dirPath) throws IOToolsException {
        File dir = new File(dirPath);
        deleteDirRepeat(dir);
    }

    /**
     * <br>[機  能] 指定したディレクトリ以下を全て削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dir ディレクトリ
     * @exception IOToolsException 削除に失敗した場合にスロー
     */
    public static void deleteDirRepeat(final File dir)throws IOToolsException {
        if (!dir.exists()) { //すでにディレクトリが存在しない場合
            return;
        }
        if (!dir.isDirectory()) {
            throw new IOToolsException("ディレクトリではありません。");
        }

        //子削除
        File[] files = dir.listFiles();
        for (int intCnt = 0; intCnt < files.length; intCnt++) {
            if (files[intCnt].isDirectory()) {
                deleteDir(files[intCnt]);

            } else {

                if (!files[intCnt].delete()) {
                    while (true) {
                        if (!files[intCnt].exists()) {
                            break;
                        }
                        files[intCnt].delete();
                    }
                }
            }
        }

        //ディレクトリ削除
        if (!dir.delete()) {
            while (true) {
                if (!dir.exists()) {
                    break;
                }
                dir.delete();
            }
        }
    }

    /**
     * <br>[機  能] バイナリファイルを移動する。
     * <br>[解  説]
     * <br>[備  考]
     * @param srcFile  移動元ファイル
     * @param destFile 移動先ファイル
     * @throws IOException IOエラー
     * @throws IOToolsException 移動元ファイルの削除に失敗
     */
    public static void moveBinFile(
        final String srcFile,
        final String destFile) throws IOException, IOToolsException {
        moveBinFile(new File(srcFile), new File(destFile));
    }

    /**
     * <br>[機  能] バイナリファイルを移動する。
     * <br>[解  説]
     * <br>[備  考]
     * @param srcFile  移動元ファイル
     * @param destFile 移動先ファイル
     * @throws IOException IOエラー
     * @throws IOToolsException 移動元ファイルの削除に失敗
     */
    public static void moveBinFile(
        final File srcFile,
        final File destFile) throws IOException, IOToolsException {

        boolean result = srcFile.renameTo(destFile);
        if (!result) {
            copyBinFile(srcFile, destFile);
            IOTools.deleteFile(srcFile);
        }
    }
}
