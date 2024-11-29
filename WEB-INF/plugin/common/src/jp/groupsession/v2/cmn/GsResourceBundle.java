package jp.groupsession.v2.cmn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.ValidateUtil;

/**
 * <br>[機  能] GsResources.propertiesからGroupSessionの各種設定を取得する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsResourceBundle {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GsResourceBundle.class);

    /** プロパティファイル名 */
    private static final String RESOURCE_FILE_NANE__ = "Gs2Resources.properties";

    /** インスタンス */
    private static Properties resourceProperties__ = new Properties();

    static {
        GsResourceBundle bundle = new GsResourceBundle();
        resourceProperties__ = bundle._loadProperties();
        bundle = null;
    }

    /**
     * <p>インスタンスを生成させないためのプライベートコンストラクタ
     */
    private GsResourceBundle() {
    }

    /**
     * <p>プロパティファイルから引数をキーに値を取得する
     * <>
     * @param key メッセージを取得するキー
     * @return 引数に対応した値
     */
    public static String getString(String key) {
        return resourceProperties__.getProperty(key);
    }

    /**
     * <br>[機  能] ユーザ数上限の再読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     */
    public static void reloadUserLimit() {
        GsResourceBundle bundle = new GsResourceBundle();
        Properties reloadProp = bundle._loadProperties();
        if (reloadProp != null) {
            String userLimitCount = reloadProp.getProperty(GSConst.USER_COUNT_LIMIT);

            if (userLimitCount == null || !ValidateUtil.isNumber(userLimitCount)) {
                userLimitCount = "0";
            }

            resourceProperties__.setProperty(
                        GSConst.USER_COUNT_LIMIT,
                        userLimitCount
                        );
        }
    }

    /**
     * <br>[機  能] Gs2Resources.propertiesの読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @return Properties
     */
    protected Properties _loadProperties() {
        Properties props = null;
        InputStream inStream = null;
        try {
            inStream = GsResourceBundle.class.getClassLoader()
                        .getResourceAsStream(RESOURCE_FILE_NANE__);

            props = new Properties();
            props.load(inStream);
        } catch (IOException e) {
            log__.error("Gs2Resources.propertiesの読み込みに失敗", e);

        } finally {
            try  {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (Exception e) {
                log__.error("終了処理でエラー発生", e);
            }
        }

        return props;
    }
}
