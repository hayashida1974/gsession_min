package jp.groupsession.v2.sml.biz;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.html.HtmlReplaceRequest;
import jp.co.sjts.util.html.HtmlReplaceResult;

/**
 *
 * <br>[機  能] ショートメール本文として登録したHTMLから表示用にコンバートしたHTMLを取得する
 * <br>[解  説] HTML無害化 URL文字列のリンク化を実行する
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlViewHtmlConverter {
    /**
     *
     * <br>[機  能] コンバートの実行
     * <br>[解  説] HTML無害化 URL文字列のリンク化を実行する
     * <br>[備  考]
     * @param src 元HTML
     * @return コンバート後HTML
     */
    public static String convert(String src) {
        HtmlReplaceRequest baseReplaceRequest = StringUtilHtml.removeIllegalTagRequest();
        HtmlReplaceRequest replaceRequest =
                HtmlReplaceRequest.builder()
                .setFuncTagReplace((tagName) -> {
                    return baseReplaceRequest.getFuncTagReplace().tagReplace(tagName);
                })
                .setFuncTypeReplace((tagName, typeName, typeParam) -> {
                    return baseReplaceRequest.getFuncTypeReplace().typeReplace(
                                     tagName, typeName, typeParam);
                })
                .setFuncBodyReplace((tagName, bodyParam) -> {
                    bodyParam = StringUtilHtml.transToHTmlForTextArea(bodyParam);
                    bodyParam = StringUtil.transToLink(bodyParam, StringUtil.OTHER_WIN, true);
                    bodyParam = StringUtilHtml.replaceSpecialChar(bodyParam);
                    return HtmlReplaceResult.builder(
                            HtmlReplaceRequest.MotionKbn.REPLACE, bodyParam);
                })
                .build();
        return StringUtilHtml.replaceFuncForHtml(src, replaceRequest);
    }

}
