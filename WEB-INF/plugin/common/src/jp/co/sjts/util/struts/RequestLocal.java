package jp.co.sjts.util.struts;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <br>[機  能] リクエスト毎のローカル変数Map
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class RequestLocal {
    /** 初期化済みフラグキー */
    private static final String LIVE_FLG = "live";
    /** ThreadLocal */
    private static final ThreadLocal<Map<Object, Object>> LOCAL__ =
            new ThreadLocal<Map<Object, Object>>() {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }
    };

    /**
     *
     * <br>[機  能] リクエストローカルの生存期間設定クラス
     * <br>[解  説] close実行時にリクエストローカル内のクラスを解放する
     * <br>[備  考] AutoCloseableなクラスはcloseも実行される
     *
     * @author JTS
     */
    public static class RequestLocalLife implements AutoCloseable {
        private RequestLocalLife() {

        }
        @Override
        public void close() {
            for (Object value : LOCAL__.get().values()) {
                if (value instanceof AutoCloseable) {
                    try {
                        ((AutoCloseable) value).close();
                    } catch (Exception e) {
                    }
                }
            }
            LOCAL__.get().clear();
        }

    }
    /**
     *
     * <br>[機  能] リクエストローカルの生存期間設定クラスを生成する
     * <br>[解  説] RequestLocalLifeをcloseするとリクエストローカル内のクラスを解放する
     * <br>[備  考] thread内で実行済みの場合 nullを返す
     * @return リクエストローカルの生存期間設定クラス
     */
    public static RequestLocalLife init() {
        if (LOCAL__.get().containsKey(LIVE_FLG)) {
            return null;
        }
        RequestLocalLife ret = new RequestLocalLife();
        LOCAL__.get().put(LIVE_FLG, LIVE_FLG);
        return ret;
    }
    /**
     *
     * <br>[機  能] 保管済みのリクエストローカル変数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param <T> 内容部のジェネリクス
     * @param key キー
     * @param clz ジェネリクス宣言
     * @return リクエストローカル変数
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Object key, Class<T> clz) {
        if (!LOCAL__.get().containsKey(LIVE_FLG)) {
            return null;
        }

        Object ret = LOCAL__.get().get(key);
        if (ret == null) {
            return null;
        }
        if (!clz.isAssignableFrom(ret.getClass())) {
            return null;
        }
        return (T) ret;
    }
    /**
     *
     * <br>[機  能] リクエストローカル変数キーの存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param key
     * @return キーが存在すればtrue
     */
    public static boolean containsKey(Object key) {
        if (!LOCAL__.get().containsKey(LIVE_FLG)) {
            return false;
        }
        return LOCAL__.get().containsKey(key);

    }
    /**
     *
     * <br>[機  能] リクエストローカル変数を保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param <T> 内容部のジェネリクス
     * @param key
     * @param value
     */
    public static <T>  void put(Object key, T value) {
        if (!LOCAL__.get().containsKey(LIVE_FLG)) {
            return;
        }
        LOCAL__.get().put(key, value);

    }


}
