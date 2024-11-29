package jp.co.sjts.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <br>[機  能] Javaのオブジェクト自身をファイルの読み書きに使用するためのクラスです。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class ObjectFile {

    /** 保存ファイル */
    private File tmpFile__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考] 空のコンストラクタを禁止するための宣言です。
     */
    @SuppressWarnings("unused")
    private ObjectFile() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] 保存場所のパス、ファイル名を指定してインスタンスを生成します。
     * <br>[備  考]
     *
     * @param dirPath  ファイルを保存するディレクトリのパス
     * @param fileName ファイル名
     */
    public ObjectFile(String dirPath, String fileName) {
        tmpFile__ = new File(dirPath, fileName);
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] 保存ファイルのフルパスを指定してインスタンスを生成します。
     * <br>[備  考]
     *
     * @param fileName 保存ファイルのフルパス
     */
    public ObjectFile(String fileName) {
        tmpFile__ = new File(fileName);
    }

    /**
     * <br>[機  能] ファイルから、オブジェクトを読み取ります。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 取得したオブジェクト
     * @exception IOToolsException IOエラー
     */
    public synchronized Object load() throws IOToolsException {

        Object obj = null;

        if ((tmpFile__.isDirectory()) || (!tmpFile__.exists())) {
            throw new IOToolsException(
                "指定されたファイルは、ディレクトリであるか、存在しません。");
        }

        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tmpFile__);
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        } catch (Exception e) {
            throw new IOToolsException(e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new IOToolsException(e.getMessage());
            }
        }

        return obj;

    }

    /**
     * <br>[機  能] オブジェクトを、ファイルに書き込みます。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param obj 書き込むオブジェクト
     * @exception IOToolsException IOエラー
     */
    public synchronized void save(Object obj) throws IOToolsException {

        if (tmpFile__.isDirectory()) {
            throw new IOToolsException(
                    "指定されたファイルは、ディレクトリとして既に存在しています。");
        }

        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            if (!tmpFile__.exists()) {
                tmpFile__.createNewFile();
            }
            fos = new FileOutputStream(tmpFile__);
            oos = new ObjectOutputStream(fos);
            //synchronized(obj) {
                oos.writeObject(obj);
            //}
        } catch (Exception e) {
                String path = null;
                if (tmpFile__ != null) {
                    path = tmpFile__.getAbsolutePath();
                }
                throw new IOToolsException(e.getMessage() + " path=" + path);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                throw new IOToolsException(e.getMessage());
            }
        }
    }

    /**
     * <br>[機  能] オブジェクトが書き込まれたファイルを削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @exception IOToolsException IOエラー
     */
    public synchronized void remove() throws IOToolsException {
        if (!tmpFile__.exists()) {
            return;
        }
        if (!tmpFile__.isFile()) {
            throw new IOToolsException("指定された名前のファイルは存在しません。");
        }
        if (!tmpFile__.delete()) {
            throw new IOToolsException("ファイルの削除に失敗しました。");
        }
    }
}
