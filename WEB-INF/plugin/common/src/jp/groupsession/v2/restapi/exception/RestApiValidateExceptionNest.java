package jp.groupsession.v2.restapi.exception;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.response.ResultElement;

/**
 * <br>[機  能] バリデート例外をまとめて出力するための例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiValidateExceptionNest extends RestApiValidateException {
    /** 例外リスト*/
    private final List<RestApiValidateException> exceptionList__;
    /** HTTPステータスコード*/
    private final int status__ = 422;
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
     * @param exceptionList 例外リスト
     */
    public RestApiValidateExceptionNest(List<RestApiValidateException> exceptionList) {
        super(RestApiValidateExceptionNest.class.getName(), null);
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
