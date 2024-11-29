package jp.groupsession.v2.cmn.exception;

import org.apache.struts.chain.commands.InvalidPathException;

/**
 * <br>[機  能] 存在しないパスにアクセスしたときの例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSInvalidPathException extends Exception {
    
    /** 遷移先URL */
    private String url__ = null;
    
    public GSInvalidPathException(InvalidPathException e, String url) {
        super(e.getMessage() + "\r\nPath:" + e.getPath() + "\r\nURL:" + url, e);
        setUrl(url);
    }
    
    /**
     * <p>url を取得します。
     * @return url
     * @see jp.groupsession.v2.cmn.exception.GSInvalidPathException#url__
     */
    public String getUrl() {
        return url__;
    }


    /**
     * <p>url をセットします。
     * @param url url
     * @see jp.groupsession.v2.cmn.exception.GSInvalidPathException#url__
     */
    public void setUrl(String url) {
        url__ = url;
    }
}
