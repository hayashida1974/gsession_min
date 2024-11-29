package jp.groupsession.v2.restapi.exception;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.response.ResultElement;

public class RestApiExceptionNest extends RestApiException {
    /** 例外リスト*/
    private final List<RestApiException> exceptionList__;
    /** HTTPステータスコード*/
    private final int status__;
    /**
     * <p>status を取得します。
     * @return status
     *
     */
    @Override
    public int getStatus() {
        return status__;
    }
    /**
     *
     * コンストラクタ
     * @param status HTTPステータス
     * @param exceptionList 例外リスト
     */
    public RestApiExceptionNest(int status, List<RestApiException> exceptionList) {
        super(RestApiValidateExceptionNest.class.getName(), null);
        status__ = status;
        exceptionList__ = exceptionList;
    }
    @Override
    public List<ResultElement> createElement(RestApiContext ctx) {
        return Optional.ofNullable(exceptionList__)
            .stream()
            .flatMap(e -> e.stream())
            .flatMap(e -> e.createElement(ctx).stream())
            .collect(Collectors.toList());
    }

}
