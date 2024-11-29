package jp.groupsession.v2.cmn.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] HTTPレスポンスから取得した各種情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HttpResponseModel extends AbstractModel {

    /** HTTPリクエスト送信先URL */
    private String url__ = null;
    /** ステータスコード */
    private int statusCode__ = 0;
    /** レスポンスヘッダ */
    private Map<String, List<String>> header__ = new HashMap<String, List<String>>();
    /** レスポンスボディ */
    private String body__ = null;
    /** レスポンスボディ(byteArray) */
    private byte[] bodyByteArray__ = null;

    public String getUrl() {
        return url__;
    }
    public void setUrl(String url) {
        url__ = url;
    }
    public int getStatusCode() {
        return statusCode__;
    }
    public void setStatusCode(int statusCode) {
        statusCode__ = statusCode;
    }
    public Map<String, List<String>> getHeader() {
        return header__;
    }
    public void setHeader(Map<String, List<String>> header) {
        header__ = header;
    }
    public String getBody() {
        return body__;
    }
    public void setBody(String body) {
        body__ = body;
    }
    public byte[] getBodyByteArray() {
        return bodyByteArray__;
    }
    public void setBodyByteArray(byte[] bodyByteArray) {
        bodyByteArray__ = bodyByteArray;
    }

    /**
     * 指定したヘッダー情報を取得する
     * @param headerName フィールド名
     * @return ヘッダー情報
     */
    public List<String> getHeader(String headerName) {
        return header__.get(headerName);
    }

    /**
     * レスポンスボディをInputStream形式で返す
     * @return レスポンスボディ
     */
    public InputStream getBodyStream() {
        if (bodyByteArray__ == null) {
            return null;
        }

        return new ByteArrayInputStream(bodyByteArray__);
    }
}
