package jp.co.sjts.util.http.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <br>[機  能] ClassLoaderが操作されてしまう脆弱性への対応を行うクラス
 * <br>[解  説] 不正な文字列がパラメータに含まれる場合、リクエストを拒否する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ParamFilter implements Filter {
    /** 除外パターン */
    private static final Pattern EXCLUDE_PATTERN__ = Pattern.compile("(^|\\W)[cC]lass\\W");

    /**
     * <br>[機  能] 初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param conf FilterConfig
     */
    public void init(FilterConfig conf) {
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

        HttpServletRequest req = (HttpServletRequest) request;

        @SuppressWarnings("all")
        Enumeration paramNames = req.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (__isExcludeParam(paramName)) {
                throw new IllegalArgumentException("不正なパラメータです: " + paramName);
            }
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for  (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (__isExcludeParam(cookieName)) {
                    throw new IllegalArgumentException("不正なクッキー情報です: " + cookieName);
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * <br>[機  能] 不正な文字列が含まれているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param target 判定対象
     * @return 判定結果 true: 含まれる false: 含まれない
     */
    private static boolean __isExcludeParam(String target) {
        return EXCLUDE_PATTERN__.matcher(target).find();
    }
}
