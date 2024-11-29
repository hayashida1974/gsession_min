package jp.groupsession.v2.restapi.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;

/**
 * RestApi 共通Exception
 */
public class RestApiException extends RuntimeException {
    /** 理由コード*/
    private IReasonCode reasonCode__;
    /** メッセージキー*/
    private String messageKey__;
    /** メッセージ挿入オブジェクト*/
    private Object[] values__;
    /** メッセージ挿入オブジェクト*/
    private String[] paramName__;

    /** HTTPステータスコード*/
    private final int status__ = 400;
    /**
     * コンストラクタ
     * @param trace トレース例外
     */
    public RestApiException(Throwable trace) {
        super("RestApiException", trace);
    }
    /**
     * コンストラクタ
     * @param logMessage ログ表示メッセージ
     * @param trace トレース例外
     */
    public RestApiException(String logMessage, Throwable trace) {
        super(logMessage, trace);
    }
    /**
     *
     * <br>[機  能] 例外にレスポンス用メッセージを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param messageKey メッセージキー
     * @param values メッセージ挿入オブジェクト
     * @return this
     */
    public RestApiException setMessageResource(
            String messageKey,
            Object... values) {
        messageKey__ = messageKey;
        values__ = values;
        return this;
    }
    /**
     *
     * <br>[機  能] 例外にレスポンス用メッセージを設定
     * <br>[解  説] すでにメッセージ挿入されている場合は実行しない
     * <br>[備  考]
     * @param messageKey メッセージキー
     * @param values メッセージ挿入オブジェクト
     * @return this
     */
    public RestApiException setMessageResourceIfBlank(
            String messageKey,
            Object... values) {
        if (messageKey__ != null) {
            return this;
        }
        messageKey__ = messageKey;
        values__ = values;
        return this;
    }
    /**
     *
     * <br>[機  能] 理由コードを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param reasonCode 理由コード
     * @return this
     */
    public RestApiException setReasonCode(
            IReasonCode reasonCode) {
        reasonCode__ = reasonCode;
        return this;
    }
    /**
     * <p>reasonCode を取得します。
     * @return reasonCode
     * @see jp.groupsession.v2.restapi.exception.RestApiException#reasonCode__
     */
    protected IReasonCode getReasonCode() {
        return reasonCode__;
    }
    /**
     * <p>messageKey を取得します。
     * @return messageKey
     * @see jp.groupsession.v2.restapi.exception.RestApiException#messageKey__
     */
    protected String getMessageKey() {
        return messageKey__;
    }
    /**
     * <p>values を取得します。
     * @return values
     * @see jp.groupsession.v2.restapi.exception.RestApiException#values__
     */
    protected Object[] getValues() {
        return values__;
    }

    /**
     *
     * <br>[機  能] レスポンスへのエラーメッセージ書き込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param ctx RESTAPIコンテキスト
     */
    public final void write(HttpServletRequest req, HttpServletResponse res, RestApiContext ctx) {
        res.setStatus(getStatus());

        RestApiResponseWriter.ErrorResponceBuilder builder =
                RestApiResponseWriter.builderError(res);

        builder.setRestApiContext(ctx);
        createElement(ctx).stream()
            .forEach(e -> builder.addResult(e));
        builder.build().execute();
    }


    /**
     *
     * <br>[機  能] 例外にレスポンス用パラメータ名を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramName パラメータ名
     * @return this
     */
    public RestApiException setParamName(
            String... paramName) {
        paramName__ = paramName;
        return this;
    }

    /**
     *
     * <br>[機  能] エレメント生成
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @return エレメント
     */
    public List<ResultElement> createElement(RestApiContext ctx) {
        ResultElement result = new ResultElement("error");
        if (!StringUtil.isNullZeroString(messageKey__)) {
            result.addContent(new ResultElement("messageText")
                .addContent(ctx.getMessageResources().getMessage(messageKey__, values__)));
        }
        if (reasonCode__ == null) {
            reasonCode__ = EnumError.SYS_RUNTIME_ERROR;
        }
        result.addContent(new ResultElement("reasonCodeText")
                                .addContent(reasonCode__.reasonCodeText()));

        return List.of(result);

    }
    /**
     * <p>HTTPステータスコード を取得します。
     * @return status
     */
    public int getStatus() {
        return status__;
    }

}
