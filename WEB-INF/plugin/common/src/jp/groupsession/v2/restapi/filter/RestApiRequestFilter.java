package jp.groupsession.v2.restapi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.log.LogBlock;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.filter.annotation.ActionFilter;
import jp.groupsession.v2.restapi.filter.annotation.AddFilter;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
/**
 *
 * <br>[機  能] RestApi共通Filter
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiRequestFilter implements Filter {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(RestApiRequestFilter.class);

    /** アクションフィルタリスト*/
    private static final List<Class<?>> ACTIONFILTER_LIST =
            Collections.synchronizedList(new ArrayList<>());

    @Override
    public void destroy() {
        log__.debug("destroy");

    }
    /**
     * restapi以外へのput、deleteメソッドアクセスを受け付けない
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse responce,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) responce;

        try (LogBlock lb__ = LogBlock.builder("RestApiFilter do ", log__)
                                .build()) {

            //RESTAPIがサポートするメソッドは GET,POST,PUT,DELETE
            req.setAttribute(GSConst.ATTRKEY_SUPORTMETHOD, GSConstApi.SUPORTMETHOD_RESTAPI);

            log__.debug(String.format("contextPath : %s", req.getContextPath()));
            log__.debug(String.format("requestURI : %s", req.getRequestURI()));
            log__.debug(String.format("requestMethod : %s", req.getMethod()));

            if (Arrays.stream(GSConstApi.SUPORTMETHOD_RESTAPI)
                    .anyMatch(method ->
                        Objects.equals(
                                method,
                                req.getMethod().toUpperCase())
                    ) == false
                    ) {

                RestApiResponseWriter.ErrorResponceBuilder builder =
                        RestApiResponseWriter.builderError(res);
                res.setStatus(405);
                builder.setRequest(req);
                builder.addResult(new ResultElement("error")
                        .addContent(
                                new ResultElement("messageText")
                                    .addContent("メソッドに対応していません。")
                                    )
                        .addContent(
                                new ResultElement("reasonCodeText")
                                    .addContent(
                                            EnumError.REQ_METHOD.reasonCodeText()
                                            )
                                    )
                        );
                builder.build().execute();

                return;

            }

            chain.doFilter(request, responce);
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log__.debug("init");

    }
    /**
     *
     * <br>[機  能] RESTAPI アクションフィルタの読み込み
     * <br>[解  説]
     * <br>[備  考]
     * @param loader
     */
    public static void loadRestApi(ClassLoader loader) {
            log__.debug("load restapi");
            ACTIONFILTER_LIST.addAll(
                    new RestApiActionFilterLoader()
                        .load(loader)
                    );

    }
    /**
     *
     * <br>[機  能] RESTAPI アクションフィルタの実行
     * <br>[解  説]
     * <br>[備  考]
     * @param req サーブレットリクエスト
     * @param res サーブレットレスポンス
     * @param addFilters 追加フィルタ
     * @return true: filterをすべて実行した false: filterが中断された
     */
    public static boolean executeActionFilter(HttpServletRequest req,
            HttpServletResponse res,
            AddFilter[] addFilters) {

        List<Object> filterClzList = new ArrayList<>();
        ACTIONFILTER_LIST.stream()
            .filter(clz -> Objects.equals(
                    clz.getName(),
                    "jp.groupsession.v2.restapi.controller.RestApiContextFilter")
                    )
            .findFirst()
            .ifPresent(clz -> filterClzList.add(clz));

        Stream.concat(
                ACTIONFILTER_LIST.stream()
                    .filter(clz -> Objects.equals(
                            clz.getName(),
                            "jp.groupsession.v2.restapi.controller.RestApiContextFilter")
                                 == false
                    )
                    .filter(clz -> __checkPath(req, clz)),
                Arrays.stream(addFilters)
                )
            .forEach(clz -> filterClzList.add(clz));

        RestApiActionFilterChain chain =
                new RestApiActionFilterChain(
                        req,
                        filterClzList
                        );
        chain.doFilter(req, res, null);

        return chain.isFinish();

    }
    private static boolean __checkPath(HttpServletRequest req, Class<?> clz) {
        ActionFilter ant = clz.getAnnotation(ActionFilter.class);
        if (ant == null) {
            return false;
        }
        boolean methodOK = (
                ValidateUtil.isEmpty(ant.method())
                || Objects.equals(ant.method(), req.getMethod())
                );
        if (methodOK == false) {
            return false;
        }
        if (ValidateUtil.isEmpty(ant.path())) {
            return true;
        }

        final List<String> chkList = new ArrayList<String>();
        final List<String> reqPathList = new ArrayList<String>();
        for (String path : ant.path().split("/")) {
            if (ValidateUtil.isEmpty(path)) {
                continue;
            }
            chkList.add(path);
        }
        for (String path : req.getServletPath().split("/")) {
            if (ValidateUtil.isEmpty(path)) {
                continue;
            }
            reqPathList.add(path);
        }
        for (String path : req.getPathInfo().split("/")) {
            if (ValidateUtil.isEmpty(path)) {
                continue;
            }
            reqPathList.add(path);
        }

        Iterator<String> it1 = chkList.iterator();
        Iterator<String> it2 = reqPathList.iterator();
        while (it1.hasNext()
                && it2.hasNext()) {
            String path1 = it1.next();
            String path2 = it2.next();
            if (Objects.equals(path1, "*")) {
                continue;
            }
            if (Objects.equals(path1, path2) == false) {
                return false;
            }
        }
        if (it1.hasNext() == false && it2.hasNext() == false) {
            return true;
        }

        return false;
    }

}
