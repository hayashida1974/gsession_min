package jp.groupsession.v2.cmn.biz;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] servername.conf SERVER_URL設定に関して取り扱う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AccessUrlBiz {
    /**SERVER_URLの設定値*/
    private String confValue__;
    /** SERVER_URLの設定値不正フラグ */
    private boolean confError__;

    /**
     *
     * デフォルトコンストラクタ
     */
    private AccessUrlBiz() {
    }

    /**
     *
     * <br>[機  能] インスタンス生成
     * <br>[解  説]
     * <br>[備  考]
     * @return インスタンス
     */
    public static AccessUrlBiz getInstance() {
        String serverUrl = ConfigBundle.getValue("SERVER_URL");
        AccessUrlBiz ret = new AccessUrlBiz();
        ret.confValue__ = NullDefault.getString(serverUrl, "");
        if (ret.confValue__.length() > 0) {
            //URLにスキーマがない場合は不正
            if (!ret.confValue__.startsWith("http://")
                    && !ret.confValue__.startsWith("https://")) {
                ret.confError__ = true;
                ret.confValue__ = null;
            }
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] SERVER_URLのフォーマットが不正の場合trueを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return SERVER_URLのフォーマットが不正の場合true
     */
    public boolean haveConfError() {
        return confError__;

    }
    /**
     *
     * <br>[機  能] SERVER_URLとリクエストモデルからgsessionのベースURLを生成する
     * <br>[解  説] ベースURL：{$scheme}://{$サーバネーム}/{$コンテキストパス(gsession)}/
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramUrl アクセスURLのパラメータとしての「url」URLエンコードは不要
     * <br>
     * @return gsessionのベースURL
     * @throws UnsupportedEncodingException URLエンコード実行時例外
     * @throws URISyntaxException リクエストURL取得例外
     */
    public String getAccessUrl(
            RequestModel reqMdl,

            String paramUrl) throws UnsupportedEncodingException, URISyntaxException {

        if (paramUrl.startsWith("//")) {
            paramUrl = paramUrl.substring(1);
        }

        String accessUrl = getBaseUrl(reqMdl);
        accessUrl += "common/cmn001.do?url=";
        accessUrl += URLEncoder.encode(paramUrl, Encoding.UTF_8);
        return accessUrl;

    }
    /**
     *
     * <br>[機  能] SERVER_URLとリクエストモデルからgsessionのベースURLを生成する
     * <br>[解  説] ベースURL：{$scheme}://{$サーバネーム}/{$コンテキストパス(gsession)}/
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return gsessionのベースURL
     * @throws URISyntaxException リクエストURL取得例外
     */
    public String getBaseUrlNotUseReferer(RequestModel reqMdl) throws URISyntaxException {
        //SERVER_URL使用時
        if (!confError__ && confValue__.length() > 0) {
            String ret = confValue__;
            if (!confValue__.endsWith("/")) {
                ret = ret + "/";
            }
            return ret;
        }
        String pluginPathName = NullDefault.getString(reqMdl.getActionPath(), "");
        if (!pluginPathName.startsWith("/")) {
            pluginPathName = "/" + pluginPathName;
        }
        String contextPath = getContextPath(reqMdl);

        //リクエストURL使用時
        if (reqMdl.getRequestURI() == null) {
            throw new URISyntaxException("null", "リクエストが存在しない");
        }
        String reqUrl = reqMdl.getRequestURL().toString();
        return __createBaseUrl(reqUrl, contextPath);
    }
    /**
    *
    * <br>[機  能] SERVER_URLとリクエストモデルからgsessionのベースURLを生成する
    * <br>[解  説] ベースURL：{$scheme}://{$サーバネーム}/{$コンテキストパス(gsession)}/
    * <br>[備  考]
    * @param reqMdl リクエストモデル
    * @return gsessionのベースURL
    * @throws URISyntaxException リクエストURL取得例外
    */
   public String getBaseUrl(RequestModel reqMdl) throws URISyntaxException {
       //SERVER_URL使用時
       if (!confError__ && confValue__.length() > 0) {
           String ret = confValue__;
           if (!confValue__.endsWith("/")) {
               ret = ret + "/";
           }
           return ret;
       }
       String pluginPathName = NullDefault.getString(reqMdl.getActionPath(), "");
       if (!pluginPathName.startsWith("/")) {
           pluginPathName = "/" + pluginPathName;
       }
       String contextPath = getContextPath(reqMdl);

       //REFERER使用時
       String referer = reqMdl.getReferer();
       if (!StringUtil.isNullZeroString(referer)) {
           try {
               return __createBaseUrl(referer, contextPath);

           } catch (URISyntaxException e) {
           }

       }
       //リクエストURL使用時
       if (reqMdl.getRequestURI() == null) {
           throw new URISyntaxException("null", "リクエストが存在しない");
       }
       String reqUrl = reqMdl.getRequestURL().toString();
       return __createBaseUrl(reqUrl, contextPath);
   }
    /**
     *
     * <br>[機  能] URLからベースURLを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqUrl リクエストURL文字列
     * @param contextPath コンテキストパス
     * @return ベースURL
     * @throws URISyntaxException リクエストURLフォーマット不具合
     */
    String __createBaseUrl(String reqUrl, String contextPath) throws URISyntaxException {
        URI uri;
        uri = new URI(reqUrl);
        String path = uri.getPath();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String baseUrl;
        if (path.length() == 0) {
            baseUrl = reqUrl;

        } else {
            int pathIndex = reqUrl.indexOf(uri.getPath());
            baseUrl = reqUrl.substring(0, pathIndex);
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        if (contextPath.length() != 0) {
            baseUrl += contextPath + "/";
        }

        return baseUrl;

    }
    /**
     *
     * <br>[機  能] SERVER_URLとリクエストモデルからgsessionのスキーマを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return SERVER_URLに設定されたURLのスキーマ
     */
    public String getScheme(RequestModel reqMdl) {
        //SERVER_URL使用時
        if (!confError__ && confValue__.length() > 0) {
            if (confValue__.startsWith("https://")) {
                return "https";
            }
            return "http";
        }
        //REFERER使用時
        String referer = reqMdl.getReferer();
        if (!StringUtil.isNullZeroString(referer)) {

            try {
                URI uri;
                uri = new URI(referer);
                return uri.getScheme();
            } catch (URISyntaxException e) {
            }

        }
        //リクエストURL使用時
        try {
            if (reqMdl.getRequestURI() == null) {
                throw new URISyntaxException("null", "リクエストが存在しない");
            }
            String reqUrl = reqMdl.getRequestURL().toString();
            URI uri;
            uri = new URI(reqUrl);
            return uri.getScheme();
        } catch (URISyntaxException e) {
        }
        return "http";

    }

    /**
     *
     * <br>[機  能] SERVER_URLとリクエストモデルからgsessionのコンテキストパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return / + コンテキストパス(gsession)
     * @throws URISyntaxException リクエストURL取得例外
     */
    public String getContextPath(RequestModel reqMdl) throws URISyntaxException {
        //SERVER_URL使用時
        if (!confError__ && confValue__.length() > 0) {
            try {
                URI uri;
                uri = new URI(confValue__);
                String path = uri.getPath();
                if (path.startsWith("/")) {
                    path = path.substring(1);
                }
                if (path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }
                return path;
            } catch (URISyntaxException e) {
            }
        }
        String pluginPathName = NullDefault.getString(reqMdl.getActionPath(), "");
        if (!pluginPathName.startsWith("/")) {
            pluginPathName = "/" + pluginPathName;
        }

        String domain = "";
        String reqDomain = NullDefault.getString(reqMdl.getDomain(), "");
        if (!reqDomain.equals(GSConst.GS_DOMAIN)) {
            domain = "/" + reqDomain;
        }

        //リクエストURL使用時
        if (reqMdl.getRequestURI() == null) {
            throw new URISyntaxException("null", "リクエストが存在しない");
        }
        String reqUrl = reqMdl.getRequestURL().toString();
        URI uri;
        uri = new URI(reqUrl);
        String path = uri.getPath();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        path = path.substring(0, path.indexOf(pluginPathName));
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        return path + domain;

    }


}
