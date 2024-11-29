package jp.groupsession.v2.struts.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.FormTag;

/**
 * <br>[機  能] モバイルのhtml:formタグを拡張
 * <br>[解  説] Docomoの固体識別番号を取得するためにフォームタグにutnを追加する。
 * <br>[備  考]
 *
 * @author JTS
 */
public class MobileHtmlFormTag extends FormTag {

    /** ドコモで固定識別番号の送信を指定する */
    private String utn__;

    /**
     * <br>[機  能] utnを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return utn
     */
    public String getUtn() {
        return utn__;
    }

    /**
     * <br>[機  能] utnを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param utn utn
     */
    public void setUtn(String utn) {
        utn__ = utn;
    }

    @Override
    protected String renderFormStartElement()
    throws JspException {
    StringBuffer results = new StringBuffer("<form");
    renderName(results);
    renderAttribute(results, "method", getMethod() != null ? getMethod() : "post");
    renderAction(results);
    renderAttribute(results, "accept-charset", getAcceptCharset());
    renderAttribute(results, "class", getStyleClass());
    renderAttribute(results, "dir", getDir());
    renderAttribute(results, "enctype", getEnctype());
    renderAttribute(results, "lang", getLang());
    renderAttribute(results, "onreset", getOnreset());
    renderAttribute(results, "onsubmit", getOnsubmit());
    renderAttribute(results, "style", getStyle());
    renderAttribute(results, "target", getTarget());
    renderAttribute(results, "utn", getUtn());
    renderOtherAttributes(results);
    results.append(">");
    return results.toString();
}
}
