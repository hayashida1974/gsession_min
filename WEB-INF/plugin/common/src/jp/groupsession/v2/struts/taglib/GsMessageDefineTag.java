package jp.groupsession.v2.struts.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.taglib.bean.DefineTag;

/**
 * <br>[機  能] bean:defineタグを拡張
 * <br>[解  説] 指定されたmsgkeyからメッセージを取得し、defineのvalueとして設定する
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsMessageDefineTag extends DefineTag {

    /**
     * メッセージキー
     */
    protected String msgkey_ = null;
    /**
     * @return msgkey
     */
    public String getMsgkey() {
        return msgkey_;
    }
    /**
     * @param msgkey 設定する msgkey
     */
    public void setMsgkey(String msgkey) {
        msgkey_ = msgkey;
    }

    /**
     * Check if we need to evaluate the body of the tag
     * @return int
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        GsMessage gsMsg = new GsMessage();
        this.value = gsMsg.getMessage((HttpServletRequest) pageContext.getRequest(),
                                    msgkey_);

        return super.doStartTag();
    }
}
