package jp.groupsession.v2.cmn.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnProxyAddressDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSPassword;

/**
 * <br>[機  能] HTTPリクエスト、レスポンスの操作を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HttpOperation {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(HttpOperation.class);

    /** HTTPメソッド */
    public static final int METHOD_GET = 0;
    public static final int METHOD_POST= 1;
    public static final int METHOD_PUT = 2;
    public static final int METHOD_DELETE = 3;

    /** タイムアウト(秒) */
    private int timeout__ = 0;
    /** プロキシサーバを使用するか */
    private boolean useProxy__ = false;
    /** プロキシサーバの接続情報 */
    private CmnContmModel ret__ = null;
    /** ユーザ認証を行うか */
    private boolean auth__ = false;
    /** ユーザ認証パラメータ ユーザ */
    String authUser__ = null;
    /** ユーザ認証パラメータ パスワード */
    String authPass__ = null;

    /** プロキシサーバを使用しないURL */
    private List<String> urlList__ = null;

    /**
     *<br> [機 能] コンストラクタ
     *<br> [解 説]
     *<br> [備 考]
     */
    @SuppressWarnings("all")
    private HttpOperation() {
    }

    /**
     *<br> [機 能] コンストラクタ
     *<br> [解 説]
     *<br> [備 考]
     * @param con コネクション
     * @param timeout タイムアウト(秒)
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワードの復号に失敗
     */
    public HttpOperation(Connection con, int timeout)
    throws SQLException, EncryptionException {
        timeout__ = timeout;

        if (con == null || con.isClosed()) {
            return;
        }

        //プロキシ設定を取得する
        CmnContmDao contDao = new CmnContmDao(con);
        ret__ = contDao.select();
        contDao = null;

        useProxy__ = ret__ != null && ret__.getCntPxyUse() == GSConstMain.PROXY_SERVER_USE;

        if (useProxy__) {
            log__.debug("プロキシを使用する");
            auth__ = ret__.getCntPxyAuth() == GSConstMain.PROXY_SERVER_USERAUTH_AUTH;
            if (auth__) {
                authUser__ = ret__.getCntPxyAuthId();
                authPass__ = GSPassword.getDecryPassword(ret__.getCntPxyAuthPass());
            }

            urlList__ = new ArrayList<String>();
            if (ret__.getCntPxyAdrkbn() == GSConstMain.PROXY_SERVER_ADRKBN_EXISTADDRESS) {
                CmnProxyAddressDao adrDao = new CmnProxyAddressDao(con);
                urlList__ = Arrays.asList(adrDao.getAddressList());
            }
        }

    }

    /**
     * 指定したURLへHTTPリクエストを送信する
     * @param url URL
     * @param httpReqMdl リクエスト送信時の各種情報
     * @return レスポンス情報
     * @throws IOException HTTPリクエスト送信に失敗
     * @throws InterruptedException HTTPリクエスト送信に失敗
     */
    public HttpResponseModel sendRequest(String url, HttpRequestModel httpReqMdl)
    throws IOException, InterruptedException {
        return sendRequest(url, httpReqMdl, METHOD_POST);
    }

    /**
     * 指定したURLへHTTPリクエストを送信する
     * @param url URL
     * @param httpReqMdl リクエスト送信時の各種情報
     * @param httpMethod HTTPメソッド
     * @return レスポンス情報
     * @throws IOException HTTPリクエスト送信に失敗
     * @throws InterruptedException HTTPリクエスト送信に失敗
     */
    public HttpResponseModel sendRequest(String url, HttpRequestModel httpReqMdl, int httpMethod)
    throws IOException, InterruptedException {

        //パラメータ文字列を設定
        String paramString = __createParamString(httpReqMdl);

        //GETの場合、URLパラメータを設定
        if (httpMethod == METHOD_GET) {
            if (!StringUtil.isNullZeroString(paramString)) {
                if (paramString.indexOf("?") < 0) {
                    url += "?";
                } else {
                    url += "&";
                }
                url += paramString;
            }
        }

        HttpRequest.Builder requestBuilder
             = HttpRequest.newBuilder().uri(URI.create(url));

        //HTTPメソッド毎の処理
        if (httpMethod == METHOD_GET) {
            requestBuilder.GET();
        } else if (httpMethod == METHOD_DELETE) {
            requestBuilder.DELETE();
        } else if (httpMethod == METHOD_POST || httpMethod == METHOD_PUT) {
            __setBody(requestBuilder, httpReqMdl, httpMethod);
        }

        //HTTPヘッダを設定
        __setHeader(requestBuilder, httpReqMdl);

        HttpClient httpClient = __createHttpClient(url, httpReqMdl);
        HttpRequest request = requestBuilder.build();

        HttpResponse<InputStream> response
            = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        HttpResponseModel httpResMdl = __createResponseModel(response);

        return httpResMdl;
    }

    /**
     * <br>[機  能] HttpClientを取得する
     * <br>[解  説] CMN_CONTMからプロキシサーバ設定を取得する
     * <br>[備  考] ソケットがつながる迄のタイムアウト,繋がってからレスポンスを返す迄のタイムアウトは同じ値が設定されます。
     * @param url URL 接続先のURL
     * @param httpReqMdl リクエスト送信時の各種情報
     * @return HttpClient
     */
    private HttpClient __createHttpClient(String url, HttpRequestModel httpReqMdl) {
        boolean useProxy = false;
        boolean auth = false;

        if (useProxy__) {
            useProxy = true;
            for (String noUseUrl : urlList__) {
                if (url.indexOf(noUseUrl) >= 0) {
                    useProxy = false;
                    break;
                }
            }

            if (useProxy) {
                auth = auth__;
            }
        }

        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder();
        httpClientBuilder.connectTimeout(Duration.ofSeconds(timeout__));

        if (useProxy) {
            httpClientBuilder.proxy(
                ProxySelector.of(new InetSocketAddress(ret__.getCntPxyUrl(), ret__.getCntPxyPort())));

            if (auth) {
                httpClientBuilder.authenticator(
                            new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(authUser__, authPass__.toCharArray());
                                }
                            });
            }
        }

        if (httpReqMdl.isRedirectFlg()) {
            httpClientBuilder.followRedirects(HttpClient.Redirect.ALWAYS);
        }

        return httpClientBuilder.build();
    }

    /**
     * パラメータ文字列を生成する
     * @param httpReqMdl リクエスト送信時の情報
     * @return パラメータ文字列
     * @throws UnsupportedEncodingException 不正な文字コードを指定
     */
    private String __createParamString(HttpRequestModel httpReqMdl)
    throws UnsupportedEncodingException {
        Map<String, List<String>> paramMap = httpReqMdl.getParam();
        if (paramMap == null || paramMap.isEmpty()) {
            return null;
        }

        String paramString = "";
        Iterator<String> iter =  paramMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            for (String value : paramMap.get(key)) {
                if (paramString.length() > 0) {
                    paramString += "&";
                }

                paramString += key + "=" + URLEncoder.encode(value, "UTF-8");
            }
        }

        return paramString;
    }

    /**
     * ヘッダ情報を設定する
     * @param requestBuilder HTTPリクエストのビルダー
     * @param httpReqMdl HTTPリクエスト情報
     */
    private void  __setHeader(HttpRequest.Builder requestBuilder, HttpRequestModel httpReqMdl) {
        //HTTPヘッダを設定
        boolean contentTypeFlg = false;
        Map<String, String> headerMap = httpReqMdl.getHeader();
        if (headerMap != null && !headerMap.isEmpty()) {

            Iterator<String> iter =  headerMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                if (key != null && headerMap.get(key) != null) {
                    requestBuilder.header(key, headerMap.get(key));
                    if (!contentTypeFlg) {
                        contentTypeFlg = key.toLowerCase().equals("content-type");
                    }
                }
            }
        }

        if (!contentTypeFlg) {
            String contentType = "application/x-www-form-urlencoded";
            if (httpReqMdl.getContentType() != null) {
                contentType = httpReqMdl.getContentType();
            }
            requestBuilder.header("Content-Type", contentType);
        }
    }

    /**
     * リクエストボディを設定する
     * @param requestBuilder HTTPリクエストのビルダー
     * @param httpReqMdl HTTPリクエスト情報
     * @param httpMethod HTTPメソッド
     * @throws UnsupportedEncodingException 不正なエンコードを設定
     * @throws IOException ファイル読み込みに失敗
     */
    private void  __setBody(HttpRequest.Builder requestBuilder, HttpRequestModel httpReqMdl, int httpMethod)
    throws UnsupportedEncodingException, IOException {
        if (!httpReqMdl.isMultipartFlg()) {
            String paramString = httpReqMdl.getBodyString();
            if (paramString == null) {
                paramString = __createParamString(httpReqMdl);
            }

            paramString = NullDefault.getString(paramString, "");
            if (httpMethod == METHOD_POST) {
                requestBuilder.POST(
                    HttpRequest.BodyPublishers.ofString(paramString)
                );
            } else if (httpMethod == METHOD_PUT) {
                requestBuilder.PUT(
                    HttpRequest.BodyPublishers.ofString(paramString)
                );
            }
            return;
        }

        //MultiPart
        List<HttpPart> partList = httpReqMdl.getPartList();
        if (partList != null && !partList.isEmpty()) {
            String boundary = UUID.randomUUID().toString();

            httpReqMdl.addHeader("Content-Type", "multipart/form-data; boundary="
                                + boundary);
            int idx = 0;
            List<byte[]> byteArrayList = new ArrayList<byte[]>();
            StringBuilder bodyBuilder = null;
            for (HttpPart part : partList) {
                bodyBuilder = new StringBuilder("");
                bodyBuilder.append("--").append(boundary);
                bodyBuilder.append("\r\n");
                bodyBuilder.append("Content-Disposition: form-data; name=");
                bodyBuilder.append(part.getParamName());

                String charset = "UTF-8";
                if (part.getStrValue() != null) {
                    bodyBuilder.append("\r\n");
                    bodyBuilder.append("Content-Type: text/plain; charset=");
                    if (part.getCharset() != null) {
                        charset = part.getCharset();
                    }
                    bodyBuilder.append(charset);
                    bodyBuilder.append("\r\n\r\n");
                    bodyBuilder.append(part.getStrValue());
                    byteArrayList.add(bodyBuilder.toString().getBytes(charset));
                } else {
                    bodyBuilder.append("; filename=" + part.getFileName());
                    bodyBuilder.append("\r\n");
                    bodyBuilder.append("Content-Type: application/octet-stream;");
                    bodyBuilder.append("\r\n\r\n");
                    byteArrayList.add(bodyBuilder.toString().getBytes(charset));
                    byteArrayList.add(Files.readAllBytes(part.getFile().toPath()));
                }

                idx++;
                if (idx == partList.size()) {
                    byteArrayList.add(("\r\n" + "--" + boundary + "--").getBytes(charset));
                }
                byteArrayList.add("\r\n".getBytes(charset));
            }

            if (httpMethod == METHOD_POST) {
                requestBuilder.POST(
                    HttpRequest.BodyPublishers.ofByteArrays(byteArrayList)
                );
            } else if (httpMethod == METHOD_PUT) {
                requestBuilder.PUT(
                    HttpRequest.BodyPublishers.ofByteArrays(byteArrayList)
                );
            }

        } else {
            if (httpMethod == METHOD_POST) {
                requestBuilder.POST(
                    HttpRequest.BodyPublishers.ofString(httpReqMdl.getBodyString())
                );
            } else if (httpMethod == METHOD_PUT) {
                requestBuilder.PUT(
                    HttpRequest.BodyPublishers.ofString(httpReqMdl.getBodyString())
                );
            }
        }

    }

    private HttpResponseModel __createResponseModel(HttpResponse<InputStream> response)
    throws IOException {
        HttpResponseModel result = new HttpResponseModel();

        //URL
        result.setUrl(response.uri().getPath());

        //ステータスコード
        result.setStatusCode(response.statusCode());

        //ヘッダ
        result.setHeader(response.headers().map());

        //ヘッダからcharset取得
        String charset = Encoding.UTF_8;
        if (result.getHeader() != null && result.getHeader().get("Content-Type") != null) {
            Pattern pattern = Pattern.compile("charset=(.*)$");
            Matcher matcher = pattern.matcher(result.getHeader().get("Content-Type").get(0));
            if (matcher.find()) {
                charset = matcher.group(1);
            }
        }

        //ボディ
        result.setBodyByteArray(response.body().readAllBytes());
        result.setBody(new String(result.getBodyByteArray(), charset));

        return result;
    }
}
