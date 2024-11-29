package jp.co.sjts.util.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * <br>[機  能] SQLでファイルをlob等に保存する場合のモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SqlFile {
    /** file */
    private File file__ = null;
    /** fileをInputStreamに変換したオブジェクト */
    private InputStream inStream__ = null;

    /**
     * デフォルトコンストラクタ
     */
    public SqlFile() {
    }

    /**
     * <p>file を取得します。
     * @return file
     */
    public File getFile() {
        return file__;
    }
    /**
     * <p>file をセットします。
     * @param file file
     */
    public void setFile(File file) {
        file__ = file;
    }
    /**
     * <p>inStream を取得します。
     * @return inStream
     */
    public InputStream getInStream() {
        return inStream__;
    }
    /**
     * <p>inStream をセットします。
     * @param inStream inStream
     */
    public void setInStream(InputStream inStream) {
        inStream__ = inStream;
    }

    /**
     * <br>[機  能] メンバのFileをInputStreamに変換する
     * <br>[解  説]
     * <br>[備  考]
     * @return fileをInputStreamに変換したオブジェクト
     * @throws FileNotFoundException FileNotFoundException
     */
    public InputStream toInputStream() throws FileNotFoundException {
        //
        FileInputStream fis = new FileInputStream(file__);
        inStream__ = fis;
        return fis;
    }
}
