package jp.co.sjts.util.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <br>[機  能] ディレクトリのみを抽出するためのフィルタ。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class DirectoryFilter implements FilenameFilter {

    /**
     * <br>[機  能] フィルタ実行
     * <br>[解  説]
     * <br>[備  考]
     * @param dir ディレクトリ
     * @param name ファイル名
     * @return true:OK,false:NG
     */
    public boolean accept(File dir, String name) {
        String dirPath = dir.getAbsolutePath();
        dirPath = IOTools.setEndPathChar(dirPath);
        File file = new File(dirPath + name);
        return file.isDirectory();
    }

}
