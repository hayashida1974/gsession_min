package jp.groupsession.v2.struts.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.bean.WriteTag;

/**
 * <br>[機  能] scriptタグを作成する
 * <br>[解  説] 読み込むメッセージjsファイルを設定する
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsJsMessageTag extends WriteTag {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GsJsMessageTag.class);

    /** デフォルト言語 */
    public static final String DEFAULT_LANG = "ja";
    /** デフォルトjsファイルパス */
    public static final String DEFAULT_JS_PATH = "../common/js/message_ja.js";
    /** pageContext */
    private PageContext pageContext__;
    /** 言語 */
    private String lang__ = null;
    /** jsファイル */
    private String jsPath__ = null;
    /** jsフラグ */
    private Boolean jsFlg__ = false;
    /** ファイル名 */
    private String fileName__ = null;

    /**
     * @return pageContext を戻します。
     */
    public PageContext getPageContext() {
        return pageContext__;
    }
    /**
     * @param pageContext 設定する pageContext。
     */
    public void setPageContext(PageContext pageContext) {
        pageContext__ = pageContext;
    }
    /**
     * @param fileName 設定する fileName。
     */
    public void setFilename(String fileName) {
        fileName__ = fileName;
    }
    /**
     * @return fileName を戻します。
     */
    public String getFilename() {
        return fileName__;
    }

    /**
     * <p>タグが読み込まれた時
     * @throws JspException 実行例外
     * @return SKIP_BODY
     */
    public int doStartTag() throws JspException {

        BaseUserModel smodel = new BaseUserModel();

        JspWriter out = pageContext__.getOut();

        if (pageContext__.getSession() == null) {
            try {
                out.print("<script language=\"JavaScript\" src='" + DEFAULT_JS_PATH
                 + "?" + GSConst.VERSION_PARAM + "' ></script>");
            } catch (Exception e) {
//                    throw new JspException(e.getMessage());
                log__.debug("Exception1", e);
            }
        } else if (pageContext__.getSession().getAttribute(GSConst.SESSION_KEY) == null) {
            try {
                out.print("<script language=\"JavaScript\" src='" + DEFAULT_JS_PATH
                        + "?" + GSConst.VERSION_PARAM + "' ></script>");
            } catch (Exception e) {
//                    throw new JspException(e.getMessage());
                log__.debug("Exception2", e);
            }
        } else {
            GsMessage gsmsg = new GsMessage();
            try {
                smodel = (BaseUserModel) pageContext__.getSession()
                        .getAttribute(GSConst.SESSION_KEY);
                lang__ = gsmsg.getLanguageCode(smodel.getCountry());
                if (lang__ == null) {
                    jsPath__ = DEFAULT_JS_PATH;
                } else {
                    jsPath__ = "../common/js/message_" + lang__ + ".js";
                }

                out.print("<script language=\"JavaScript\" src='" + jsPath__
                        + "?" + GSConst.VERSION_PARAM + "' ></script>");

                jsFlg__ = true;

            } catch (Exception e) {
                  //throw new JspException(e.getMessage());
                log__.debug("Exception3", e);
            } finally {
                if (jsFlg__ == false) {
                    try {
                        out.print("<script language=\"JavaScript\" src='" + DEFAULT_JS_PATH
                                + "?" + GSConst.VERSION_PARAM + "' ></script>");
                    } catch (Exception e) {
    //                    throw new JspException(e.getMessage());
                        log__.debug("Exception4", e);
                    }
                }
            }
        }
        return SKIP_BODY;
    }

    /**
     * <p>タグが読み終わった時
     * @throws JspException 実行例外
     * @return EVAL_PAGE
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
