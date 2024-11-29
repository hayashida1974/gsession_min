package jp.groupsession.v2.man.man080;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] ファイル一覧に表示する情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man080FileModel implements Serializable, Comparable<Man080FileModel> {

    /** ファイル名 */
    private String fileName__ = null;
    /** 作成日 */
    private UDate makeDate__ = null;
    /** 作成日(文字列) */
    private String strMakeDate__ = null;
    /** ファイル容量 */
    private String fileSize__ = null;
    /** ZIP形式出力区分 */
    private String zipOutput__ = null;
    /** ファイル名 ダウンロード用ハッシュ値 */
    private String hashFileName__ = null;

    /**
     * <br>[機  能] このオブジェクトと指定されたオブジェクトの順序を比較します。
     * <br>[解  説] 表示順を元に比較を行います。
     * <br>[備  考]
     *
     * @param o 比較対象のオブジェクト
     * @return 比較結果
     */
    public int compareTo(Man080FileModel o) {

        int res = 0;
        long difference = o.getMakeDate().getTimeMillis()
                        - getMakeDate().getTimeMillis();

        if (difference > 0) {
            res = 1;
        } else if (difference < 0) {
            res = -1;
        }
        return res;
    }

    /**
     * <p>fileName を取得します。
     * @return fileName
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    /**
     * <p>fileSize を取得します。
     * @return fileSize
     */
    public String getFileSize() {
        return fileSize__;
    }
    /**
     * <p>fileSize をセットします。
     * @param fileSize fileSize
     */
    public void setFileSize(String fileSize) {
        fileSize__ = fileSize;
    }
    /**
     * <p>makeDate を取得します。
     * @return makeDate
     */
    public UDate getMakeDate() {
        return makeDate__;
    }

    /**
     * <p>makeDate をセットします。
     * @param makeDate makeDate
     */
    public void setMakeDate(UDate makeDate) {
        makeDate__ = makeDate;
    }

    /**
     * <p>strMakeDate を取得します。
     * @return strMakeDate
     */
    public String getStrMakeDate() {
        return strMakeDate__;
    }

    /**
     * <p>strMakeDate をセットします。
     * @param strMakeDate strMakeDate
     */
    public void setStrMakeDate(String strMakeDate) {
        strMakeDate__ = strMakeDate;
    }

    /**
     * <p>zipOutput を取得します。
     * @return zipOutput
     */
    public String getZipOutput() {
        return zipOutput__;
    }

     /**
     * <p>zipOutput をセットします。
     * @param zipOutput zipOutput
     */
     public void setZipOutput(String zipOutput) {
        zipOutput__ = zipOutput;
    }

    /**
     * <p>hashFileName を取得します。
     * @return hashFileName
     */
    public String getHashFileName() {
        return hashFileName__;
    }

    /**
     * <p>hashFileName をセットします。
     * @param hashFileName hashFileName
     */
    public void setHashFileName(String hashFileName) {
        hashFileName__ = hashFileName;
    }
}
