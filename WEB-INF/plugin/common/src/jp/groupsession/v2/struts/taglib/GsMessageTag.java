package jp.groupsession.v2.struts.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.struts.msg.GsMessage;
import org.apache.struts.taglib.bean.MessageTag;

/**
 * <br>[機  能] bean:messageタグを拡張
 * <br>[解  説] 各種メッセージファイルと画面上のメッセージを別にする
 * <br>         /WEB-INF/plugin/common/struts_config.xmlに
 * <br>         通常のメッセージリソースファイル(Messages.properties)とは別の
 * <br>         画面(jsp)表示用メッセージリソースファイルを用意すること
 * <br>         例:
 * <br>          &lt;message-resources parameter="Messages" /&gt;
 * <br>          &lt;message-resources parameter="ApplicationResources" key="message" /&gt;
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsMessageTag extends MessageTag {

    /**
     * Process the start tag.
     * @return int
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        __setSetting((HttpServletRequest) pageContext.getRequest());
        return super.doStartTag();
    }

    /**
     * <p>メッセージ取得前の設定を行う
     * @param req リクエスト
     */
    private void __setSetting(HttpServletRequest req) {

        String resourceKey = GsMessage.RESOURECE_KEY;

        GsMessage gsMsg = new GsMessage();
        setLocale(gsMsg.getLocale(req));

        //ユーザモデルから言語を取得する処理
        String locale = gsMsg.getLocale(req);

        if (!StringUtil.isNullZeroString(locale)) {
            String languageCode = null;
            languageCode = gsMsg.getLanguageCode(locale);
            if (!StringUtil.isNullZeroString(languageCode)) {
                resourceKey += "_" + languageCode;
            } else {
                //ユーザエージェントから言語を取得
                languageCode = req.getLocale().getLanguage();
                if (!StringUtil.isNullZeroString(languageCode)) {

                    if (!languageCode.equals("ja")
                            && !languageCode.equals("en")) {
                        languageCode = "ja";
                    }
                    resourceKey += "_" + languageCode;
                }
            }
        }
        setBundle(resourceKey);
    }
}
