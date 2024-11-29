package jp.groupsession.v2.cmn.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] HTTPリクエスト送信時に指定する各種情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HttpRequestModel extends AbstractModel {

    /** リクエストヘッダ */
    private Map<String, String> header__ = new HashMap<String, String>();
    /** リクエストパラメータ */
    private Map<String, List<String>> param__ = new HashMap<String, List<String>>();

    /** リクエストボディ(文字列) */
    private String bodyString__ = null;
    /** リクエストボディ Content-Type */
    private String contentType__ = null;

    /** リダイレクトフラグ */
    private boolean redirectFlg__ = false;

    /** マルチパートフラグ */
    private boolean multipartFlg__ = false;
    /** パート情報 */
    private List<HttpPart> partList__ = null;

    public Map<String, String> getHeader() {
        return header__;
    }
    public void setHeader(Map<String, String> header) {
        header__ = header;
    }
    public Map<String, List<String>> getParam() {
        return param__;
    }
    public void setParam(Map<String, List<String>> param) {
        param__ = param;
    }
    public String getBodyString() {
        return bodyString__;
    }
    public void setBodyString(String bodyString) {
        bodyString__ = bodyString;
    }
    public String getContentType() {
        return contentType__;
    }
    public void setContentType(String contentType) {
        contentType__ = contentType;
    }
    public boolean isRedirectFlg() {
        return redirectFlg__;
    }
    public void setRedirectFlg(boolean redirectFlg) {
        redirectFlg__ = redirectFlg;
    }
    public boolean isMultipartFlg() {
        return multipartFlg__;
    }
    public void setMultipartFlg(boolean multipartFlg) {
        multipartFlg__ = multipartFlg;
    }
    public List<HttpPart> getPartList() {
        return partList__;
    }
    public void setPartList(List<HttpPart> partList) {
        partList__ = partList;
    }

    /**
     * リクエストヘッダーを追加する
     * @param key キー
     * @param value 値
     */
    public void addHeader(String key, String value) {
        header__.put(key, value);
    }

    /**
     * リクエストヘッダーを追加する
     * @param key キー
     * @param value 値
     */
    public void addParam(String key, String value) {
        if (!param__.containsKey(key)) {
            param__.put(key, new ArrayList<String>());
        }
        param__.get(key).add(value);
    }
}
