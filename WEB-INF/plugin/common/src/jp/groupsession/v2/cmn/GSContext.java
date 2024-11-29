package jp.groupsession.v2.cmn;

import java.util.HashMap;

/**
 * <br>[機  能] GroupSessionの共通情報を格納する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSContext {

    /** アプリケーションのルートパス */
    public static final String APP_ROOT_PATH = "APP_ROOT_PATH";
    /** テンポラリディレクトリのパス */
    public static final String TEMP_PATH_PREFIX = "GSTEMPDIR";
    /** Blob用テンポラリディレクトリのパス */
    public static final String TEMP_PATH_BLOB = "LOBFILE";
    /** 添付ファイルユーティリティー */
    public static final String TEMP_FILE_UTIL = "GS_TEMP_FILE_UTIL";
    /** MessageResources */
    public static final String MSG_RESOURCE = "MSG_RESOURCE";
    /** ステータス */
    public static final String STATUS = "STATUS";

    /** パラメータ格納マップ */
    private HashMap < String, Object > map__ = null;

    /**
     * <p>デフォルトコンストラクタ
     */
    public GSContext() {
        super();
        map__ = new HashMap<String, Object>();
    }

    /**
     * <p>値を格納します。
     * @param key キー
     * @param val 値
     */
    public void put(String key, Object val) {
        map__.put(key, val);
    }

    /**
     * <p>値を取得します。
     * @param key キー
     * @return 値
     */
    public Object get(String key) {
        return map__.get(key);
    }
}