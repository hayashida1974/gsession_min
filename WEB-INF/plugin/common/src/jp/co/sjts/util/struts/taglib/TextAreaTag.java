package jp.co.sjts.util.struts.taglib;

import javax.servlet.jsp.JspException;

import jp.co.sjts.util.StringUtilHtml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.WriteTag;

/**
 * <br>[機  能] StrutsのBeanタグではTextAreaの改行に対応できないため、
 * その拡張を行ったクラス
 * <br>[解  説]
 * <br>1) web.xml、ctag-bean.tld、jp.co.sjts.util.struts.taglib.TextAreaTagを
 * <br>CVSから取得する。
 * <br>
 * <br>2) <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>を
 * <br><%@ taglib uri="/WEB-INF/ctag-bean.tld" prefix="bean" %>に変更する。
 * <br>
 * <br>3) <bean:writeText name="xxxModel" property="xxxTxarea" crlf="false" />
 * <br>ctrfをfalseに設定する。省略したらtrueになります。
 * <br>
 * <br>[備  考]
 *
 * @author JTS
 */
public class TextAreaTag extends WriteTag {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(TextAreaTag.class);

    /**
     * 改行表示フラグ
     */
    private boolean crlf_ = true;

    /**
     * get 改行表示フラグ
     * @return boolean
     */
    public boolean getCrlf() {
        return crlf_;
    }

    /**
     * set 改行表示フラグ
     * @param b crlf
     */
    public void setCrlf(boolean b) {
        crlf_ = b;
    }

    /**
     * <br>[機 能] 処理(HTTP出力)開始
     * <br>[解 説]
     * <br>[備 考]
     *
     * @exception JspException JSPエラー
     * @return SKIP_BODY
     */
    public int doStartTag() throws JspException {

        log__.debug("-- START doStartTag --");
        if (ignore) {
            if (TagUtils.getInstance().lookup(pageContext, name, scope) == null) {
                return (SKIP_BODY);
            }
        }

        Object value = TagUtils.getInstance().lookup(pageContext, name, property, scope);

        if (value == null) {
            return (SKIP_BODY);
        }

        String output = formatValue(value);

        if (!crlf_) {
            TagUtils.getInstance().write(pageContext, StringUtilHtml.transToHTml(output));
            return (SKIP_BODY);
        }

        if (filter && crlf_) {
            TagUtils.getInstance().write(pageContext, TagUtils.getInstance().filter(output));
        } else {
            TagUtils.getInstance().write(pageContext, output);
        }

        log__.debug("-- END doStartTag --");
        return (SKIP_BODY);

    }

}
