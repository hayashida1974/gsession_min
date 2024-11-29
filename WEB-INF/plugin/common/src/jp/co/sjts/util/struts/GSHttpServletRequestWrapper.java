package jp.co.sjts.util.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import jp.co.sjts.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] リクエストの値を取得するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSHttpServletRequestWrapper.class);

    /**
     * <br>[機  能] HttpServletRequest
     * <br>[解  説]
     * <br>[備  考]
     *@param req リクエスト
     */
    public GSHttpServletRequestWrapper(HttpServletRequest req) {
        super(req);
    }

    /**
     * <br>[機  能] URIを取得するクラス
     * <br>[解  説]
     * <br>[備  考]
     * @return uri
     */
    public String getRequestURI() {
        String uri = ((HttpServletRequest) getRequest()).getRequestURI();

        return uri;
    }

    /**
     * <br>[機  能] ヘッダーからhttpdで保存したパラメータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return String httpdでヘッダーに保存したdomainパラメータの値
     */
    public String getDomain(HttpServletRequest req) {
        //return ((HttpServletRequest) getRequest()).getHeader("domain");
        String domain = null;
        if (req != null) {
            domain = req.getHeader("domain");
        }
        return domain;
    }

    /**
     * <br>[機  能] sessionにhttpdで保存したdomainパラメータを保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public void setDomain(HttpServletRequest req) {
    String domain = getDomain(req);
        if (!StringUtil.isNullZeroString(domain)) {
            this.getSession(true).setAttribute("domain", domain);
        }
    }

    /**
     * <br>[機  能] ドメインが取得できるかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return String httpdでヘッダーに保存したdomainパラメータの値
     * @throws Exception ドメインの取得に失敗
     */
    public String getDomainCheck(HttpServletRequest req) throws Exception {

        String domain = null;
        if (req != null) {
            try {
                //domain = ((HttpServletRequest) getRequest()).getHeader("domain");
                domain = req.getHeader("domain");
            } catch (Exception e) {
                log__.fatal("ドメインの取得に失敗(ドメイン存在チェック時)");
                throw e;
            }
        }
        return domain;
    }
}
