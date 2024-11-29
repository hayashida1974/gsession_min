package jp.co.sjts.util.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.http.BrowserUtil;

/**
 * <br>[機  能] リクエストにフィルタ設定する文字コードをセットするクラス。
 * <br>[解  説] web.xmlより文字コードを取得する。記述例は下記を参照
 * <br>[備  考]
 *
 *  &lt;filter&gt;
 *    &lt;filter-name&gt;FilterEncoding&lt;/filter-name&gt;
 *    &lt;filter-class&gt;jp.co.sjts.util.http.filter.FilterEncoding&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *      &lt;param-name&gt;encoding&lt;/param-name&gt;
 *      &lt;param-value&gt;UTF-8&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *  &lt;/filter&gt;
 *  &lt;filter-mapping&gt;
 *    &lt;filter-name&gt;FilterEncoding&lt;/filter-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * @author JTS
 */
public class FilterEncoding implements Filter {

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

        //ユーザエージェント取得
        HttpServletRequest req = (HttpServletRequest) request;
        String userAgent = req.getHeader(BrowserUtil.USER_AGENT_KEYWORD);

        //モバイルアクセス判定
        boolean mobileAccess =
            BrowserUtil.isDocomo(req)
            || BrowserUtil.isAuWap20(req)
            || BrowserUtil.isVodafone(req)
            || BrowserUtil.isSoftBank(req);

        if (userAgent != null && userAgent.indexOf("BREW-Applet") >= 0) {
            mobileAccess = true;
        }

        String encoding = "";
        if (mobileAccess) {
            encoding = config__.getInitParameter("encoding_mobile");
        } else {
            encoding = config__.getInitParameter("encoding");
        }

        request.setCharacterEncoding(encoding);
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
}
