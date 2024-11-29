package jp.groupsession.v2.cmn;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 添付ファイル操作クラスのインスタンスを作成するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TempFileUtilFactory {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(TempFileUtilFactory.class);

    /**
     * <br>[機  能] FileUtilのインスタンスを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return IFileUtil IFileUtilのインスタンス
     */
    public static ITempFileUtil getInstance(String tempDir) {
        String strFileUtil = GsResourceBundle.getString("ITempFileUtil");
        log__.info("TempFileUtil is " + strFileUtil);
        ITempFileUtil fileutil = null;

        if (strFileUtil != null) {
            try {
                fileutil = (ITempFileUtil) Class.forName(strFileUtil).getConstructor(
                        new Class[]{String.class}).newInstance(new Object[]{tempDir});
            } catch (InstantiationException e) {
                log__.error(e);
            } catch (IllegalAccessException e) {
                log__.error(e);
            } catch (ClassNotFoundException e) {
                log__.error(e);
            } catch (NoSuchMethodException e) {
                log__.error(e);
            } catch (InvocationTargetException e) {
                log__.error(e);
            }
        } else {
            fileutil = new GSTempFileFileSystemUtil(tempDir);
        }

        return fileutil;
    }
}
