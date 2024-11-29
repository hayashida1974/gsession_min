package jp.co.sjts.util.cache;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ファイルキャッシュ設定ファイル(filecache.conf)の読み込みを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileCacheConfig {

    /** ファイルキャッシュ保存先ディレクトリ */
    public static final String SAVEDIR = "CACHE_SAVEDIR";
    /** ディスク使用率上限 */
    public static final String DISKLIMIT = "CACHE_DISKLIMIT";

    /** ディスク使用率上限 デフォルト*/
    public static final String DISKLIMIT_DEFAULT = "80";

    /** 設定ファイル名 */
    private static final String CONF_FILENAME__ = "filecache.conf";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCacheConfig.class);

    /** 設定値を格納するMap */
    private static Map<String, String> confMap__ = null;

    static {
        confMap__ = Collections.synchronizedMap(new HashMap<String, String>());
    }

    /**
     *<br> [機 能] コンストラクタ
     *<br> [解 説]
     *<br> [備 考]
     */
    private FileCacheConfig() {
    }

    /**
     *<br> [機 能] 設定ファイルの読み込みを行う
     *<br> [解 説]
     *<br> [備 考]
     *
     * @param appRoot ルートパス
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    public static synchronized void readConfig(String appRoot) throws IOException {

        String filePath = IOTools.setEndPathChar(appRoot);
        filePath += "/WEB-INF/conf/";
        filePath += CONF_FILENAME__;
        //設定ファイルの読み込みを行う
        StringTokenizer lines = new StringTokenizer(
                                    IOTools.readText(filePath, Encoding.UTF_8),
                                    "\n");

        while (lines.hasMoreTokens()) {
            String line = lines.nextToken();
            line = StringUtil.toDeleteReturnCode(line);
            if (line.startsWith("#") || line.indexOf("=") < 0) {
                //先頭に"#"がついた行はコメントとして扱う
                continue;
            }

            int index = line.indexOf("=");
            String key = line.substring(0, index);
            String value = line.substring(index + 1).trim();
            if (value.length() == 0) {
                value = null;
            }
            log__.debug("<==設定ファイル内容==>");
            log__.debug("key:value==>" + key + ":" + value);
            confMap__.put(key, value);
        }
    }

    /**
     * <br>[機  能] 設定値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param key キー
     * @return 設定値
     */
    public static synchronized String getValue(String key) {
        return confMap__.get(key);
    }

    /**
     * <br>[機  能] ファイルキャッシュ保存先ディレクトリを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ファイルキャッシュ保存先ディレクトリ
     */
    public static synchronized String getSaveDir() {
        return getValue(SAVEDIR);
    }

    /**
     * <br>[機  能] ディスク使用率上限を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ディスク使用率上限
     */
    public static synchronized String getDiskLimit() {
        return NullDefault.getString(
                                        getValue(DISKLIMIT),
                                        DISKLIMIT_DEFAULT);
    }
}
