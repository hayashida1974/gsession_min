package jp.groupsession.v2.restapi.filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.restapi.filter.annotation.ActionFilter;
import jp.groupsession.v2.restapi.filter.annotation.AddFilter;
/**
 *
 * <br>[機  能] RestApi Action実行時フィルターチェーン
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiActionFilterChain {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RestApiActionFilterChain.class);

    /** キャッシュ*/
    private static final Map<Class<?>, Object> FILTERMAP =
            new ConcurrentHashMap<Class<?>, Object>();
    /** フィルターオブジェクト */
    private final Iterator<Object> filterItr__;
    /** フィルター注釈 */
    private final Iterator<Annotation> annotationItr__;
    /** 最終フィルタまで実行されたか*/
    private boolean finish__ = false;

    /**
     *
     * コンストラクタ
     * @param req
     * @param filters
     */
    protected RestApiActionFilterChain(HttpServletRequest req, List<Object> filters) {
        List<Object> listFil = new ArrayList<>();
        List<Annotation> listAnt = new ArrayList<>();
        for (Object filObj : filters) {
            if ((filObj instanceof Class<?>)) {
                //ActionFilterはリクエスト情報が一致するものを追加
                Arrays.stream(
                        ((Class<?>) filObj).getAnnotationsByType(ActionFilter.class)
                        )
                .filter(ant -> {
                        if (ant.path().length() > 0) {
                            String[] pathArr = ant.path().split("\\*");
                            String check = "/restapi" + req.getPathInfo();
                            for (String path : pathArr) {
                                if (!check.startsWith(path)) {
                                    return false;
                                }
                                check = check.substring(path.length());
                                if (check.indexOf("/") < 0) {
                                    check = "";
                                    continue;
                                }
                                check = check.substring(check.indexOf("/"));
                            }
                        }
                        if (ant.method().length() > 0) {
                            return (Objects.equals(
                                    ant.method().toUpperCase(),
                                    req.getMethod().toUpperCase()));
                        }
                        return true;

                    })
                .forEach(ant -> {
                    Object filter =
                            FILTERMAP.get(((Class<?>) filObj));
                    try {
                        if (filter == null) {
                            filter = ((Class<?>) filObj).getDeclaredConstructor().newInstance();
                            FILTERMAP.put(((Class<?>) filObj), filter);
                        }

                    } catch (NoSuchMethodException
                            | SecurityException
                            | IllegalAccessException
                            | IllegalArgumentException
                            | InvocationTargetException
                            | InstantiationException e) {
                        return;
                    }
                    listFil.add(filter);
                    listAnt.add(ant);

                });

            }
            if ((filObj instanceof AddFilter)) {
                AddFilter adf = (AddFilter) filObj;
                Object filter =
                        FILTERMAP.get(adf.value());
                try {
                    if (filter == null) {
                        filter = adf.value().getDeclaredConstructor().newInstance();
                        FILTERMAP.put(adf.value(), filter);
                    }

                } catch (NoSuchMethodException
                        | SecurityException
                        | IllegalAccessException
                        | IllegalArgumentException
                        | InvocationTargetException
                        | InstantiationException e) {
                    continue;
                }
                listFil.add(filter);
                listAnt.add(adf);
                continue;
            }

        }
        filterItr__ = listFil.iterator();
        annotationItr__ = listAnt.iterator();
    }
    public void doFilter(HttpServletRequest req, HttpServletResponse res, RestApiContext ctx) {
        if (!filterItr__.hasNext() || !annotationItr__.hasNext()) {
            log__.debug("フィルタチェーン実行完了");
            finish__ = true;
            return;
        }
        final Object obj = filterItr__.next();
        Optional.ofNullable(annotationItr__.next())
            .filter(ant -> ((ant instanceof AddFilter)
                                || (ant instanceof ActionFilter))
                    )
            .ifPresent(ant -> {
                try {
                    String runner =
                            (String) ant.getClass().getMethod("runner").invoke(ant);

                    Method m = obj.getClass().getMethod(
                            runner,
                            HttpServletRequest.class,
                            HttpServletResponse.class,
                            RestApiContext.class,
                            RestApiActionFilterChain.class);
                    log__.debug("実行 RestApiFilter:" + obj.getClass());
                    m.invoke(obj, req, res, ctx, this);
                } catch (InvocationTargetException e) {
                    if (e.getTargetException() instanceof RestApiException) {
                        throw (RestApiException) e.getTargetException();
                    }
                    throw new RuntimeException("リクエストメソッド実行失敗", e);
                } catch (NoSuchMethodException
                        | SecurityException
                        | IllegalAccessException
                        | IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }

            });
    }
    /**
     *
     * <br>[機  能] フィルタをすべて実行したか
     * <br>[解  説]
     * <br>[備  考]
     * @return true: filterをすべて実行した false: filterが中断された
     */
    public boolean isFinish() {
        return finish__;
    }

}
