package jp.groupsession.v2.cmn;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 設定ファイル(.conf)の読み込みを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConfigBundle {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConfigBundle.class);

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
    private ConfigBundle() {
    }

    /**
     *<br> [機 能] 設定ファイルの読み込みを行う
     *<br> [解 説] WEB-INF/conf以下の設定ファイル(*.conf)を読み込む
     *<br> [備 考]
     *
     * @param context Context
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    public static synchronized void readConfig(ServletContext context) throws IOException {

        String prefix =  context.getRealPath("/");
        prefix = IOTools.setEndPathChar(prefix);
        prefix = prefix.concat("/WEB-INF/conf/");

        //設定ファイル一覧を取得
        List<String> files = IOTools.getFileNames(prefix);

        //設定ファイルの読み込みを行う
        for (String fileName : files) {

            if (!fileName.endsWith(".conf")) {
                continue;
            }

            StringTokenizer lines = new StringTokenizer(
                                        IOTools.readText(prefix.concat(fileName), Encoding.UTF_8),
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

    }
    /**
     *<br> [機 能] 設定ファイルの読み込みを行う
     *<br> [解 説] WEB-INF/conf以下の設定ファイル(*.conf)を読み込む
     *<br> [備 考]
     *
     * @param appRoot ルートパス
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    public static synchronized void readConfig(String appRoot) throws IOException {

        String prefix =  appRoot;
        prefix = IOTools.setEndPathChar(prefix);
        prefix = prefix.concat("/WEB-INF/conf/");

        //設定ファイル一覧を取得
        List<String> files = IOTools.getFileNames(prefix);


        //設定ファイルの読み込みを行う
        for (String fileName : files) {

            if (!fileName.endsWith(".conf")) {
                continue;
            }

            StringTokenizer lines = new StringTokenizer(
                                        IOTools.readText(prefix.concat(fileName), Encoding.UTF_8),
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
}
