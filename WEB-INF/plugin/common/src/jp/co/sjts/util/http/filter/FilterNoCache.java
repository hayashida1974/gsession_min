package jp.co.sjts.util.http.filter;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] キャシュを制御するフィルタクラス
 * <br>[解  説] web.xmlにて適用範囲を指定する。
 * <br>[備  考]
 *
 *  &lt;filter&gt;
 *    &lt;filter-name&gt;FilterNoCache&lt;/filter-name&gt;
 *    &lt;filter-class&gt;jp.co.sjts.util.http.filter.FilterNoCache&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *      &lt;param-name&gt;execludes&lt;/param-name&gt;
 *      &lt;param-value&gt;UTF-8&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *  &lt;/filter&gt;
 *  &lt;filter-mapping&gt;
 *    &lt;filter-name&gt;FilterNoCache&lt;/filter-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * @author JTS
 */
public class FilterNoCache implements Filter {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(FilterNoCache.class);
    /** フィルタ設定情報 */
    private FilterConfig config__ = null;

    /**
     * <br>[機  能] 初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param conf FilterConfig
     */
    public void init(FilterConfig conf) {
        config__ = conf;
    }

    /**
     * <br>[機  能] 終了処理
     * <br>[解  説]
     * <br>[備  考]
     */
    public void destroy() {
    }

    /**
     * <br>[機  能] フィルター実行
     * <br>[解  説]
     * <br>[備  考]
     * @param request リクエスト
     * @param response レスポンス
     * @param chain フィルター
     * @throws ServletException サーバー例外
     * @throws IOException 入出力例外
     */
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain)
        throws ServletException, IOException {

        if (response instanceof HttpServletResponse) {
            setNocache((HttpServletResponse) response);
            log__.debug("Nocacheをセット");
        } else {
            log__.debug("Nocacheをセットしない");
        }
        chain.doFilter(request, response);
    }

    /**
     * <p>FilterConfigを取得
     * @return FilterConfigを取得
     */
    public FilterConfig getConfig() {
        return config__;
    }

    /**
     * <p>FilterConfigをセット
     * @param config FilterConfig
     */
    public void setConfig(FilterConfig config) {
        config__ = config;
    }

    /**
     * キャッシュを無効にする
     * @param res HttpServletResponse
     */
    public static void setNocache(HttpServletResponse res) {
        //キャッシュを無効にする
        Calendar objCal1 = Calendar.getInstance();
        Calendar objCal2 = Calendar.getInstance();
        objCal2.set(1970, 0, 1, 0, 0, 0);
        res.setDateHeader("Last-Modified", objCal1.getTime().getTime());
        res.setDateHeader("Expires", objCal2.getTime().getTime());
//        res.setHeader("pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
    }
}
