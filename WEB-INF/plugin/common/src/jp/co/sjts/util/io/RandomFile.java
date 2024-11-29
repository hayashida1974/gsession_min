package jp.co.sjts.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <br>[機 能] java.io.RandomAccessFileを使用したファイル操作クラス
 * <br>[解 説] 読取専用モードでのアクセスや、ファイルの終端に文字列をライトする等の機能を使用できます。
 * <br>[備 考]
 * @author JTS
 */
public class RandomFile {

    /** ランダムアクセスファイル */
    private RandomAccessFile raFile__ = null;

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     */
    @SuppressWarnings("unused")
    private RandomFile() {
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param file ファイル名
     * @throws FileNotFoundException 指定したファイルが存在しない場合
     */
    public RandomFile(String file) throws FileNotFoundException {
        this(new File(file), "rw");
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     *
     * @param file ファイル名
     * @param mode アクセスモード
     * @throws FileNotFoundException 指定したファイルが存在しない場合
     */
    public RandomFile(String file, String mode) throws FileNotFoundException {
        this(new File(file), mode);
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     *
     * @param file ファイル名
     * @throws FileNotFoundException 指定したファイルが存在しない場合
     */
    public RandomFile(File file) throws FileNotFoundException {
        this(file, "rw");
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param file ファイル名
     * @param mode アクセスモード
     * @throws FileNotFoundException 指定したファイルが存在しない場合
     */
    public RandomFile(File file, String mode) throws FileNotFoundException {
        raFile__ = new RandomAccessFile(file, mode);
    }

    /**
     * [機 能] ファイルを１行読み込む<br>
     * [解 説] <br>
     * [備 考] <br>
     * @return 文字列
     * @throws IOException
     *             ファイルの読み込みに失敗
     */
    public String readLine() throws IOException {
        return raFile__.readLine();
    }

    /**
     * [機 能] ファイルの指定した位置へ指定した文字列を書き込む<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param writeStr ファイルの最終行へ書き込む文字列
     * @param position 書き込み開始位置
     * @throws IOException
     *             ファイルの書き込みに失敗 <br>
     *             またはモードに「読み込み用」を指定した場合
     */
    public void write(String writeStr, long position) throws IOException {
        raFile__.seek(position);
        raFile__.write(writeStr.getBytes());
    }

    /**
     * [機 能] ファイルの最終行へ指定した文字列を書き込む<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param writeStr ファイルの最終行へ書き込む文字列
     * @throws IOException
     *             ファイルの書き込みに失敗 <br>
     *             またはモードに「読み込み用」を指定した場合
     */
    public void writeTail(String writeStr) throws IOException {
        raFile__.seek(raFile__.length());
        raFile__.write("\r\n".getBytes());
        raFile__.write(writeStr.getBytes());
    }

    /**
     * [機 能] ファイルを閉じる<br>
     * [解 説] <br>
     * [備 考] <br>
     * @throws IOException IOエラー
     */
    public void close() throws IOException {
        raFile__.close();
    }

    /**
     * [機 能] ファイルインスタンスを削除<br>
     * [解 説] <br>
     * [備 考] <br>
     */
    public void destroy() {
        try {
            if (raFile__ != null) {
                raFile__.close();
            }
        } catch (Exception e) {
        }
    }
}