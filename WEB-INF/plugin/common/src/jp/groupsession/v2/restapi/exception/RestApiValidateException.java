package jp.groupsession.v2.restapi.exception;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.response.ResultElement;

/**
 *
 * <br>[機  能] RESTAPI 入力チェック例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiValidateException extends RestApiException {
    /** HTTPステータスコード*/
    private final int status__ = 422;
    /** メッセージ挿入オブジェクト*/
    private String[] paramName__;



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
     * コンストラクタ
     * @param logMessage ログ表示メッセージ
     * @param trace トレース例外
     */
    public RestApiValidateException(String logMessage, Throwable trace) {
        super(logMessage, trace);
    }


    /**
     *
     * コンストラクタ
     * @param reasonCode 理由コード
     * @param messageKey メッセージキー
     * @param values メッセージ挿入オブジェクト
     */
    public RestApiValidateException(
        IReasonCode reasonCode,
        String messageKey,
        Object... values) {
        super(RestApiValidateException.class.getName(), null);
        setReasonCode(reasonCode);
        setMessageResource(
                messageKey, values);
    }
    /**
     *
     * <br>[機  能] 例外にレスポンス用パラメータ名を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramName パラメータ名
     * @return this
     */
    public RestApiValidateException setParamName(
            String... paramName) {
        paramName__ = paramName;
        return this;
    }

    @Override
    public List<ResultElement> createElement(RestApiContext ctx) {
        return super.createElement(ctx).stream()
            .map(e -> {
                if (paramName__ != null && paramName__.length > 0) {
                    e.addContent(new ResultElement("parameterArray")
                        .addContent(
                            Stream.of(paramName__)
                                .map(n -> new ResultElement("parameterName")
                                            .addContent(n))
                                .collect(Collectors.toList())
                                )
                        );

                }
                return e;

            })
            .collect(Collectors.toList());
    }

}
