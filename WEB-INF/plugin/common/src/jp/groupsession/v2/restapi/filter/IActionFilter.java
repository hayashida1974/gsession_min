package jp.groupsession.v2.restapi.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.restapi.controller.RestApiContext;

public interface IActionFilter {
    /**
     *
     * <br>[機  能] フィルター実行
     * <br>[解  説]
     * <br>[備  考]
     * @param req サーブレットリクエスト
     * @param res サーブレットレスポンス
     * @param ctx RestApiコンテキスト
     * @param chain フィルターチェイン
     */
    public void doFilter(
            HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx,
            RestApiActionFilterChain chain);
}
