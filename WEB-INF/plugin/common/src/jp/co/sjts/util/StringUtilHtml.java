package jp.co.sjts.util;

import java.io.StringReader;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import jp.co.sjts.util.html.HtmlReplaceRequest;
import jp.co.sjts.util.html.HtmlReplaceResult;

/**
 * <br>[機  能] HTMLの予約語に対する変換処理をおこないます。
 * <br>         また「改行からBRタグ」「BRタグから改行」等の変更を行います。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class StringUtilHtml {

    /** wbrタグを追加する基準となる文字列 */
    public static final String[] AFTER_WBR_STR = {
                                    " ", ".", ",", "!", "?", ":", ";", "/",
                                    "+", "-", "=", "~", "(", ")", "[", "]",
                                    "　", "。", "、",  "！", "？", "：", "；", "／",
                                    "＋", "－", "＝", "～", "（", "）", "「", "」", "［", "］"
                                                    };
    /** wbrタグを追加する際に使用する正規表現 */
    public static Pattern pattern_AFTER_WBR_STR = null;

    static {
        StringBuilder sb = new StringBuilder("[");
        for (String str : AFTER_WBR_STR) {
            sb.append("\\");
            sb.append(str);
        }
        sb.append("]+");

        pattern_AFTER_WBR_STR = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
        sb.toString();
    }

    /**
     * <br>[機  能] form から受け取ったメッセージをhtmlで正常に
     * <br>         表示できる文字列に変換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  str     変換元の文字列
     * @return         変換済みの文字列
     */
    public static String transToHTml(String str) {
        if (str == null) {
                return null;
        }

        StringBuilder retSB = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(str);

        for (
            char c = stit.first();
            c != CharacterIterator.DONE;
            c = stit.next()
            ) {

            switch(c) {
            case ' ':
                retSB.append("&nbsp;");
                break;
            case '<':
                retSB.append("&lt;");
                break;
            case '>':
                retSB.append("&gt;");
                break;
            case '"':
                retSB.append("&quot;");
                break;
            case '\n':
                retSB.append("<BR>");
                break;
            case '\r':
                if (stit.next() != '\n') {
                    stit.previous();
                }
                retSB.append("<BR>");
                break;
            default :
                retSB.append(c);
                break;
            }
        }
        return retSB.toString();
    }

    /**
     * <br>[機  能] form から受け取ったメッセージをhtmlで正常に
     * <br>         表示できる文字列に変換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  str     変換元の文字列
     * @return         変換済みの文字列
     */
    public static String transToHTmlPlusAmparsant(String str) {
        if (str == null) {
                return null;
        }

        StringBuilder retSB = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(str);

        for (
            char c = stit.first();
            c != CharacterIterator.DONE;
            c = stit.next()
            ) {

            switch (c) {
            case '&':
                retSB.append("&amp;");
                break;
            case ' ':
                retSB.append("&nbsp;");
                break;
            case '<':
                retSB.append("&lt;");
                break;
            case '>':
                retSB.append("&gt;");
                break;
            case '"':
                retSB.append("&quot;");
                break;
            case '\n':
                retSB.append("<BR>");
                break;
            case '\r':
                if (stit.next() != '\n') {
                    stit.previous();
                }
                retSB.append("<BR>");
                break;
            default :
                retSB.append(c);
                break;
            }
        }
        return retSB.toString();
    }

    /**
     * <br>[機  能] form から受け取ったメッセージをhtmlで正常に
     * <br>         表示できる文字列に変換します。
     * <br>         また、URLをリンクに置き換えます
     * <br>[解  説]
     * <br>[備  考]
     * @param str 変換元の文字列
     * @return    変換済みの文字列
     */
    public static String transToHTmlPlusAmparsantAndLink(String str) {
        if (!StringUtil.isNullZeroString(str)) {
            Pattern pattern = Pattern.compile(StringUtil.URL_PATTERN,
                                            Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            String replaceStr = "";
            String bufReq = str.toString();

            while (matcher.find()) {
                String url = matcher.group();

                int urlIndex = bufReq.indexOf(url) + url.length();
                String urlStr = transToHTmlPlusAmparsant(bufReq.substring(0, bufReq.indexOf(url)));
                String linkStr = "<A HREF=\"" + url + "\"";
                linkStr += " target=\"_blank\"";

                int cornIndex = url.indexOf("://") + 3;
                String urlBf = url.substring(0, cornIndex);
                String urlAf = url.substring(cornIndex);
                String viewUrl = urlAf.replace("/", "/<wbr>");
                viewUrl = viewUrl.replace("%", "%<wbr>");
                linkStr += ">" + urlBf + viewUrl + "</A>";

                replaceStr += urlStr + linkStr;
                bufReq = bufReq.substring(urlIndex);
            }

            if (!StringUtil.isNullZeroString(bufReq)) {
                replaceStr += transToHTmlPlusAmparsant(bufReq);
            }

            if (replaceStr.length() > 0) {
                str = replaceStr;
            }
        }

        return str;
    }

    /**
     * <br>[機  能] form から受け取ったメッセージをhtmlで正常に
     * <br>         表示できる文字列に変換します。(TEXT AREA専用)
     * <br>[解  説]
     * <br>[備  考] transToHTmlとの違いは改行コードを処理しない点です。
     *
     * @param  str     変換元の文字列
     * @return         変換済みの文字列
     */
    public static String transToHTmlForTextArea(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder retSB = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(str);

        for (
            char c = stit.first();
            c != CharacterIterator.DONE;
            c = stit.next()
            ) {

            switch(c) {
            case '<':
                retSB.append("&lt;");
                break;
            case '>':
                retSB.append("&gt;");
                break;
            case '"':
                retSB.append("&quot;");
                break;
            case '&':
                retSB.append("&amp;");
                break;
            default :
                retSB.append(c);
                break;
            }
        }
        return retSB.toString();
    }

    /**
     * <br>[機  能] HTMLへエスケープされた文字を元のテキストへ変換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param msg 元に戻す文字列
     * @return 変換後の文字列
     */
    public static String transToText(String msg) {
        msg = replaceString(msg, "&lt;", "<");
        msg = replaceString(msg, "&gt;", ">");
        msg = replaceString(msg, "&quot;", "\"");
        msg = replaceString(msg, "<BR>", "\r\n");
        msg = replaceString(msg, "<br>", "\r\n");
        msg = replaceString(msg, "<br />", "\r\n");
        msg = replaceString(msg, "&amp;", "&");
        msg = replaceString(msg, "&nbsp;", " ");
        return msg;
    }

    /**
     * <br>[機  能] HTMLへエスケープされた文字を元のテキストへ変換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param msg 元に戻す文字列
     * @return 変換後の文字列
     */
    public static String transToTextBrFirst(String msg) {
        msg = replaceString(msg, "<BR>", "\r\n");
        msg = replaceString(msg, "<br>", "\r\n");
        msg = replaceString(msg, "<br />", "\r\n");
        msg = replaceString(msg, "&lt;", "<");
        msg = replaceString(msg, "&gt;", ">");
        msg = replaceString(msg, "&quot;", "\"");
        msg = replaceString(msg, "&amp;", "&");
        msg = replaceString(msg, "&nbsp;", " ");
        return msg;
    }

    /**
     * <br>[機  能] HTMLへエスケープされた文字を元のテキストへ変換します。
     * <br>[解  説] &lt;BR&gt;タグを改行へ変換した上でタグを取り除きます。
     * <br>またHTML予約語&amp\;、&nbsp\;も元の文字列（&,スペース）へ変換します。
     * <br>[備  考]
     *
     * @param msg 元に戻す文字列
     * @return 変換後の文字列
     */
    public static String transToTextAndDeleteTag(String msg) {
        //BR 改行変換
        msg = transBRtoCRLF(msg);
        //HTML予約語変換
        msg = replaceString(msg, "&gt;", ">");
        msg = replaceString(msg, "&quot;", "\"");
        msg = replaceString(msg, "&amp;", "&");
        msg = replaceString(msg, "&nbsp;", " ");
        //
        msg = deleteHtmlTagAndScriptStyleBlock(msg);
        return msg;
    }

    /**
     * <br>[機  能] 引数でわたされた文字列中にリターンコードがある時
     *              リターンコードを"<BR>"へ変換した文字列を返す。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param str 変換元の文字列
     * @return 変換済みの文字列
     */
    public static String returntoBR(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder retSB = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(str);

        for (
            char c = stit.first();
            c != CharacterIterator.DONE;
            c = stit.next()) {
            switch(c) {

            case '\n':
                retSB.append("<BR>");
                break;
            case '\r':
                if (stit.next() != '\n') {
                        stit.previous();
                }
                retSB.append("<BR>");
                break;
            default :
                retSB.append(c);
                break;
            }
        }
        return retSB.toString();
    }

    /**
     * <br>[機  能] 引数でわたされた文字列中に"<BR>"がある時
     *              <BR>をcrlfへ変換した文字列を返す。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param msg 変換元の文字列
     * @return            変換済みの文字列
     */
    public static String transBRtoCRLF(String msg) {
        msg = replaceString(msg, "<BR>", "\r\n");
        msg = replaceString(msg, "<br>", "\r\n");
        msg = replaceString(msg, "<br />", "\r\n");
        return msg;
    }

    /**
     * <br>[機  能] 指定した文字列を指定された文字列に置き換えます。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param msg 元の文字列
     * @param oldStr 置換対象の文字列
     * @param newStr おきかえる文字列
     * @return 置換後の文字列
     */
    public static String replaceString(
        String msg,
        String oldStr,
        String newStr) {

        int start = 0;
        int end = 0;
        StringBuilder retSB = new StringBuilder();

        if ((msg == null) || (oldStr == null) || (newStr == null)) {
            return null;
        }

        int oldLength = oldStr.length();

        while (true) {
            end = msg.indexOf(oldStr, start);
            if (end == -1) { //置換対象の文字列がない場合はそのまま返す
                retSB.append(msg.substring(start));
                break;
            } else {
                retSB.append(msg.substring(start, end));
                retSB.append(newStr);
                start = end + oldLength;
            }
        }
        return retSB.toString();
    }

    /**
     * <br>[機  能] 指定した文字列にwbrタグを追加する
     * <br>[解  説] 指定した文字長 or 規定の文字列の後ろ
     * <br>[備  考] 文字列のescapeも同時に行う
     * @param str 変換元の文字列
     * @param wbrLen 1行の最大文字長 この文字長を超えた場合、wbrタグを追加する
     * @return 変換後の文字列
     */
    public static String transToHTmlWithWbr(String str, int wbrLen) {
        if (!StringUtil.isNullZeroString(str)) {
            Matcher matcher = pattern_AFTER_WBR_STR.matcher(str);
            StringBuilder replaceStr = new StringBuilder("");

            while (matcher.find()) {
                String matchStr = matcher.group();
                int matchIndex = str.indexOf(matchStr) + 1;

                matchStr = str.substring(0, matchIndex);
                str = str.substring(matchIndex, str.length());
                while (matchStr.length() > wbrLen) {
                    replaceStr.append(transToHTml(matchStr.substring(0, wbrLen)));
                    replaceStr.append("<wbr/>");
                    matchStr = matchStr.substring(wbrLen, matchStr.length());
                }

                replaceStr.append(transToHTml(matchStr));
                replaceStr.append("<wbr/>");
            }

            if (str.length() > 0) {
                while (str.length() > wbrLen) {
                    replaceStr.append(transToHTml(str.substring(0, wbrLen)));
                    replaceStr.append("<wbr/>");
                    str = str.substring(wbrLen, str.length());
                }
                replaceStr.append(transToHTml(str));
            }
            str = replaceStr.toString();
            replaceStr = null;
            matcher = null;
        }

        return str;
    }

    /**
     * <br>[機  能] 文字列内にHTMLのタグが存在した場合、タグを削除した文字列を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param tagStr htmlタグを含む文字列
     * @return タグを含まない文字列(タグを削除した)
     */
    public static String deleteHtmlTag(String tagStr) {
        String ret = null;
        if (tagStr == null) {
            return ret;
        }
        try {
            DOMParser parser = new DOMParser();
            StringReader sr = new StringReader(tagStr);
            parser.parse(new InputSource(sr));
            Node node = parser.getDocument();
            //
            ret = __deleteHtmlTagNode(node, null);
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * <br>[機  能] Nodeについて再起処理を行い、CDATA_SECTION_NODE, TEXT_NODEのテキスト情報のみをバッファに格納する。
     * <br>[解  説]
     * <br>[備  考]
     * @param node XMLのNode
     * @param buf テキスト情報を格納するバッファ。nullの場合は作成する。
     * @return Nodeより取得したテキスト
     */
    private static String __deleteHtmlTagNode(Node node, StringBuilder buf) {
        if (buf == null) {
            buf = new StringBuilder();
        }

        if (node == null) {
            return buf.toString();
        }
        short type = node.getNodeType();
        if (Node.CDATA_SECTION_NODE == type || Node.TEXT_NODE == type) {
            String text = node.getNodeValue();
            if (text != null || text.trim().length() != 0) {
                buf.append(text.trim());
            }
        }

        NodeList nlist = node.getChildNodes();
        for (int i = 0; i < nlist.getLength(); i++) {
            Node cnode = nlist.item(i);
            __deleteHtmlTagNode(cnode, buf);
        }
        return buf.toString();
    }

    /**
     * <br>[機  能] HTMLの文字列をテキスト情報のみの文字列にする。
     * <br>[解  説]
     * <br>[備  考]
     * @param htmlStr 置き換え文字列
     * @return テキスト
     */
    public static String deleteHtmlTagAndScriptStyleBlock(String htmlStr) {
        HtmlReplaceRequest replaceRequest =
                HtmlReplaceRequest.builder()
                .setFuncTagReplace((tagName) -> {
                    if (tagName.equalsIgnoreCase("script")) {
                        //タグ名 : script
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.DELETE, null);
                    } else if (tagName.equalsIgnoreCase("style")) {
                        //タグ名 : style
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.DELETE, null);
                    } else {
                        //その他
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.STARTENDDELETE, null);
                    }
                })
                .setFuncTypeReplace((tagName, typeName, typeParam) -> {
                    return HtmlReplaceResult.builder(
                            HtmlReplaceRequest.MotionKbn.NONE, null);
                })
                .setFuncBodyReplace((tagName, bodyParam) -> {
                    return HtmlReplaceResult.builder(
                            HtmlReplaceRequest.MotionKbn.NONE, null);
                })
                .build();
        String ret = replaceFuncForHtml(htmlStr, replaceRequest);
        return ret;
    }

    /**
     * <br>[機  能]HTML特殊文字へ変換
     * <br>[解  説]
     * <br>[備  考]
     * @param bodyStr ショートメール本文
     * @return 文字列変換後本文
     */
    public static String replaceSpecialChar(String bodyStr) {
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&copy;", "©");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&yen;", "¥");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rdquo;", "”");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&iexcl;", "¡");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&cent;", "¢");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&pound;", "£");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&curren;", "¤");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&brvbar;", "¦");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sect;", "§");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&uml;", "¨");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ordf;", "ª");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&laquo;", "«");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&not;", "¬");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&shy;", " ­ ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&reg;", "®");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&macr;", "¯");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&deg;", "°");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&plusmn;", "±");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sup2;", "²");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sup3;", "³");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&acute;", "´");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&micro;", "µ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&para;", "¶");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&middot;", "·");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&cedil;", "¸");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sup1;", "¹");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ordm;", "º");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&raquo;", "»");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&frac14;", "¼");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&frac12;", "½");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&frac34;", "¾");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&iquest;", "¿");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Agrave;", "À");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Aacute;", "Á");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Acirc;", "Â");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Atilde;", "Ã");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Auml;", "Ä");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Aring;", "Å");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&AElig;", "Æ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ccedil;", "Ç");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Egrave;", "È");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Eacute;", "É");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ecirc;", "Ê");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Euml;", "Ë");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Igrave;", "Ì");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Iacute;", "Í");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Icirc;", "Î");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Iuml;", "Ï");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ETH;", "Ð");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ntilde;", "Ñ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ograve;", "Ò");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Oacute;", "Ó");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ocirc;", "Ô");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Otilde;", "Õ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ouml;", "Ö");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&times;", "×");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Oslash;", "Ø");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ugrave;", "Ù");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Uacute;", "Ú");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Ucirc;", "Û");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Uuml;", "Ü");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Yacute;", "Ý");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&THORN;", "Þ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&szlig;", "ß");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&agrave;", "à");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&aacute;", "á");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&acirc;", "â");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&atilde;", "ã");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&auml;", "ä");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&aring;", "å");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&aelig;", "æ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ccedil;", "ç");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&egrave;", "è");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&eacute;", "é");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ecirc;", "ê");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&euml;", "ë");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&igrave;", "ì");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&iacute;", "í");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&icirc;", "î");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&iuml;", "ï");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&eth;", "ð");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ntilde;", "ñ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ograve;", "ò");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&oacute;", "ó");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ocirc;", "ô");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&otilde;", "õ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ouml;", "ö");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&divide;", "÷");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&oslash;", "ø");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ugrave;", "ù");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&uacute;", "ú");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ucirc;", "û");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&uuml;", "ü");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&yacute;", "ý");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&thorn;", "þ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&yuml;", "ÿ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&fnof;", "ƒ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Alpha;", "Α");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Beta;", "Β");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Gamma;", "Γ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Delta;", "Δ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Epsilon;", "Ε");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Zeta;", "Ζ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Eta;", "Η");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Theta;", "Θ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Iota;", "Ι");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Kappa;", "Κ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Lambda;", "Λ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Mu;", "Μ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Nu;", "Ν");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Xi;", "Ξ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Omicron;", "Ο");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Pi;", "Π");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Rho;", "Ρ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Sigma;", "Σ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Tau;", "Τ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Upsilon;", "Υ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Phi;", "Φ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Chi;", "Χ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Psi;", "Ψ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Omega;", "Ω");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&alpha;", "α");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&beta;", "β");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&gamma;", "γ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&delta;", "δ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&epsilon;", "ε");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&zeta;", "ζ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&eta;", "η");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&theta;", "θ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&iota;", "ι");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&kappa;", "κ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lambda;", "λ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&mu;", "μ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&nu;", "ν");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&xi;", "ξ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&omicron;", "ο");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&pi;", "π");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rho;", "ρ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sigmaf;", "ς");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sigma;", "σ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&tau;", "τ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&upsilon;", "υ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&phi;", "φ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&chi;", "χ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&psi;", "ψ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&omega;", "ω");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&thetasym;", "ϑ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&upsih;", "ϒ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&piv;", "ϖ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&bull;", "•");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&hellip;", "…");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&prime;", "′");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Prime;", "″");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&oline;", "‾");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&frasl;", "⁄");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&weierp;", "℘");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&image;", "ℑ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&real;", "ℜ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&trade;", "™");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&alefsym;", "ℵ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&larr;", "←");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&uarr;", "↑");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rarr;", "→");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&darr;", "↓");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&harr;", "↔");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&crarr;", "↵");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lArr;", "⇐");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&uArr;", "⇑");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rArr;", "⇒");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&dArr;", "⇓");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&hArr;", "⇔");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&forall;", "∀");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&part;", "∂");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&exist;", "∃");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&empty;", "∅");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&nabla;", "∇");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&isin;", "∈");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&notin;", "∉");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ni;", "∋");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&prod;", "∏");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sum;", "∑");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&minus;", "−");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lowast;", "∗");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&radic;", "√");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&prop;", "∝");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&infin;", "∞");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ang;", "∠");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&and;", "∧");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&or;", "∨");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&cap;", "∩");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&cup;", "∪");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&int;", "∫");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&there4;", "∴");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sim;", "∼");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&cong;", "≅");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&asymp;", "≈");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ne;", "≠");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&equiv;", "≡");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&le;", "≤");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ge;", "≥");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sub;", "⊂");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sup;", "⊃");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&nsub;", "⊄");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sube;", "⊆");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&supe;", "⊇");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&oplus;", "⊕");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&otimes;", "⊗");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&perp;", "⊥");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sdot;", "⋅");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lceil;", "⌈");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rceil;", "⌉");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lfloor;", "⌊");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rfloor;", "⌋");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lang;", "〈");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rang;", "〉");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&loz;", "◊");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&spades;", "♠");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&clubs;", "♣");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&hearts;", "♥");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&diams;", "♦");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&OElig;", "Œ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&oelig;", "œ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Scaron;", "Š");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&scaron;", "š");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Yuml;", "Ÿ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&circ;", "ˆ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&tilde;", "˜");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ensp;", " ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&emsp;", " ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&thinsp;", " ");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&zwnj;", "‌");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&zwj;", "‍");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lrm;", "‎");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rlm;", "‏");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ndash;", "–");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&mdash;", "—");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lsquo;", "‘");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rsquo;", "’");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&sbquo;", "‚");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&ldquo;", "“");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&bdquo;", "„");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&dagger;", "†");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&Dagger;", "‡");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&permil;", "‰");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&lsaquo;", "‹");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&rsaquo;", "›");
        bodyStr = StringUtilHtml.replaceString(bodyStr, "&euro;", "€");
        return bodyStr;
    }

    /**
     * <br>[機  能] 置換元文字列をタグごとに置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param beforeString 置換元文字列
     * @return 置換後文字列
     */
    public static String replaceHtmlForView(String beforeString) {
        String ret = null;
        if (beforeString == null) {
            return ret;
        }
        HtmlReplaceRequest replaceRequest =
                HtmlReplaceRequest.builder()
                .setFuncTagReplace((tagName) -> {
                    if (tagName.equalsIgnoreCase("script")) {
                        //タグ名 : script
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.DELETE, null);
                    } else if (tagName.equalsIgnoreCase("style")) {
                        //タグ名 : style
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.DELETE, null);
                    } else if (tagName.equalsIgnoreCase("br")) {
                        //タグ名 : br
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.REPLACE, "\r\n");
                    } else {
                        //その他
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.STARTENDDELETE, null);
                    }
                })
                .setFuncTypeReplace((tagName, typeName, typeParam) -> {
                    return HtmlReplaceResult.builder(
                            HtmlReplaceRequest.MotionKbn.NONE, null);
                })
                .setFuncBodyReplace((tagName, bodyParam) -> {
                    bodyParam = StringUtilHtml.replaceString(bodyParam, "\r\n", "\n");
                    bodyParam = StringUtilHtml.replaceString(bodyParam, "\n", "\r\n");
                    return HtmlReplaceResult.builder(
                            HtmlReplaceRequest.MotionKbn.REPLACE, bodyParam);
                })
                .build();
        return replaceFuncForHtml(beforeString, replaceRequest);
    }

    /**
     * <br>[機  能] 置換元文字列をタグごとに置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param beforeString 置換元文字列
     * @param replaceRequest HTML置換リクエスト
     * @return 置換後文字列
     */
    public static String replaceFuncForHtml(
            String beforeString, HtmlReplaceRequest replaceRequest) {
        String ret = null;
        if (beforeString == null || replaceRequest == null) {
            return beforeString;
        }
        try {
            DOMParser parser = new DOMParser();
            StringReader sr = new StringReader(beforeString);
            parser.parse(new InputSource(sr));
            Node node = parser.getDocument();
            ret = __recursiveReplaceFuncForHtml(node, replaceRequest);
        } catch (Exception e) {
        }
        return ret;
    }

    private static String __recursiveReplaceFuncForHtml(Node node,
            HtmlReplaceRequest replaceRequest) {
        StringBuilder sb = new StringBuilder();
        if (node == null) {
            return sb.toString();
        }

        short type = node.getNodeType();
        if (Node.CDATA_SECTION_NODE == type || Node.TEXT_NODE == type) {
            //対象 : 本文
            HtmlReplaceResult bodyRes = replaceRequest.getFuncBodyReplace().bodyReplace(
                    node.getNodeName(),
                    node.getNodeValue());
            if (bodyRes.getMotionKbn() == HtmlReplaceRequest.MotionKbn.REPLACE) {
                //動作区分 : 置換
                sb.append(bodyRes.getAfterString());
            } else if (bodyRes.getMotionKbn() == HtmlReplaceRequest.MotionKbn.DELETE) {
                //動作区分 : 削除
            } else if (bodyRes.getMotionKbn() == HtmlReplaceRequest.MotionKbn.NONE) {
                //動作区分 : 何もしない
                sb.append(transToHTmlForTextArea(node.getNodeValue()));
            }
            return sb.toString();
        }

        HtmlReplaceResult res = replaceRequest.getFuncTagReplace().tagReplace(
                node.getNodeName());

        if (res.getMotionKbn() == HtmlReplaceRequest.MotionKbn.REPLACE) {
            //動作区分 : 置換
            sb.append(res.getAfterString());
        } else if (res.getMotionKbn() == HtmlReplaceRequest.MotionKbn.DELETE) {
            //動作区分 : 削除
        } else if (res.getMotionKbn() == HtmlReplaceRequest.MotionKbn.NONE
                || res.getMotionKbn() == HtmlReplaceRequest.MotionKbn.STARTENDDELETE) {
            //動作区分 : 何もしない/開始終了部削除
            boolean skipTag = false;
            if (Objects.equals("#document", node.getNodeName())
                || Objects.equals("BODY", node.getNodeName())
                || Objects.equals("HTML", node.getNodeName())
                || Objects.equals("HEAD", node.getNodeName())
                || Objects.equals("#comment", node.getNodeName())
                || res.getMotionKbn() == HtmlReplaceRequest.MotionKbn.STARTENDDELETE) {
                skipTag = true;
            }
            if (!skipTag) {
                //開始タグ
                sb.append("<");
                sb.append(node.getNodeName().toLowerCase());
                sb.append(" ");

                if (node.hasAttributes()) {
                    //属性
                    NamedNodeMap atrMap = node.getAttributes();
                    for (int i = 0; i < atrMap.getLength(); i++) {
                        Node attr = atrMap.item(i);

                        HtmlReplaceResult typeRes = replaceRequest.getFuncTypeReplace().typeReplace(
                                node.getNodeName().toLowerCase(),
                                attr.getNodeName().toLowerCase(),
                                attr.getNodeValue());
                        if (typeRes.getMotionKbn() == HtmlReplaceRequest.MotionKbn.REPLACE) {
                            //動作区分 : 置換
                            sb.append(typeRes.getAfterString());
                            sb.append(" ");
                        } else if (typeRes.getMotionKbn() == HtmlReplaceRequest.MotionKbn.DELETE) {
                            //動作区分 : 削除
                        } else if (typeRes.getMotionKbn() == HtmlReplaceRequest.MotionKbn.NONE) {
                            //動作区分 : 何もしない
                            sb.append(attr.getNodeName().toLowerCase());
                            sb.append("=\"");
                            sb.append(attr.getNodeValue());
                            sb.append("\" ");
                        }
                    }
                }
                if (Objects.equals("AREA", node.getNodeName())
                        || Objects.equals("BASE", node.getNodeName())
                        || Objects.equals("BR", node.getNodeName())
                        || Objects.equals("COL", node.getNodeName())
                        || Objects.equals("EMBED", node.getNodeName())
                        || Objects.equals("HR", node.getNodeName())
                        || Objects.equals("IMG", node.getNodeName())
                        || Objects.equals("INPUT", node.getNodeName())
                        || Objects.equals("KEYGEN", node.getNodeName())
                        || Objects.equals("LINK", node.getNodeName())
                        || Objects.equals("META", node.getNodeName())
                        || Objects.equals("PARAM", node.getNodeName())
                        || Objects.equals("SOURCE", node.getNodeName())
                        || Objects.equals("TRACK", node.getNodeName())
                        || Objects.equals("WBR", node.getNodeName())) {
                    skipTag = true;
                    sb.append("/>");
                } else {
                    sb.append(">");
                }
            }
            //子階層チェック
            NodeList nlist = node.getChildNodes();
            for (int i = 0; i < nlist.getLength(); i++) {
                Node cnode = nlist.item(i);
                sb.append(__recursiveReplaceFuncForHtml(cnode, replaceRequest));
            }
            if (!skipTag) {
                //終了タグ
                sb.append("</");
                sb.append(node.getNodeName().toLowerCase());
                sb.append(">");
            }
        }
        return sb.toString();
    }

    /**
     * <br>[機  能] HTML文字列から不正なタグを除去する
     * <br>[解  説]
     * <br>[備  考]
     * @return HTML置換リクエスト
     */
    public static HtmlReplaceRequest removeIllegalTagRequest() {

        HtmlReplaceRequest replaceRequest =
                HtmlReplaceRequest.builder()
                .setFuncTagReplace((tagName) -> {
                    if (tagName.equalsIgnoreCase("script")) {
                        //タグ名 : script
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.DELETE, null);
                    } else {
                        //その他
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.NONE, null);
                    }
                })
                .setFuncTypeReplace((tagName, typeName, typeParam) -> {
                    if (typeName.toLowerCase().indexOf("on") == 0) {
                        //属性名 : "ON"から始まる文字列
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.DELETE, null);
                    } else {
                        if (tagName.equalsIgnoreCase("iframe") && typeName.equalsIgnoreCase("src")
                                && typeParam.replaceAll("\\p{C}", "").toLowerCase().indexOf(
                                        "javascript") == 0) {
                            //タグ名 : iframe , 属性名 : src , 制御文字を除外した属性値 : "javascript"から始まる文字列
                            return HtmlReplaceResult.builder(
                                    HtmlReplaceRequest.MotionKbn.DELETE, null);
                        } else if (tagName.equalsIgnoreCase("iframe")
                                         && typeName.equalsIgnoreCase("srcdoc")) {
                            //タグ名 : iframe , 属性名 : srcdoc
                            StringBuffer sb = new StringBuffer();
                            sb.append(typeName);
                            sb.append("=\"");
                            sb.append(removeIllegalTag(typeParam));
                            sb.append("\"");
                            return HtmlReplaceResult.builder(
                                        HtmlReplaceRequest.MotionKbn.REPLACE, sb.toString());
                        } else if (((tagName.equalsIgnoreCase("a")
                                                 && typeName.equalsIgnoreCase("href"))
                                         || (tagName.equalsIgnoreCase("form")
                                                 && typeName.equalsIgnoreCase("action")))
                                         && typeParam.replaceAll("\\p{C}", "")
                                                 .toLowerCase().indexOf("javascript") == 0) {
                            //タグ名 : a , 属性名 : href , 制御文字を除外した属性値 : "javascript"から始まる文字列
                            //タグ名 : form , 属性名 : action , 制御文字を除外した属性値 : "javascript"から始まる文字列
                            StringBuffer sb = new StringBuffer();
                            sb.append(typeName);
                            sb.append("=\"#!\"");
                            return HtmlReplaceResult.builder(
                                    HtmlReplaceRequest.MotionKbn.REPLACE, sb.toString());
                        }
                        //上記以外
                        return HtmlReplaceResult.builder(
                                HtmlReplaceRequest.MotionKbn.NONE, null);
                    }
                })
                .setFuncBodyReplace((tagName, bodyParam) -> {
                    return HtmlReplaceResult.builder(
                            HtmlReplaceRequest.MotionKbn.NONE, null);
                })
                .build();
        return replaceRequest;
    }


    /**
     * <br>[機  能] HTML文字列から不正なタグを除去する
     * <br>[解  説]
     * <br>[備  考]
     * @param htmlStr HTML文字列
     * @return HTML文字列
     */
    public static String removeIllegalTag(String htmlStr) {
        HtmlReplaceRequest replaceRequest = removeIllegalTagRequest();
        String ret = replaceFuncForHtml(htmlStr, replaceRequest);
        return ret;
    }

    /**
     * <br>[機  能] 指定されたタグの除去を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param htmlStr HTML文字列
     * @param tag 除去するタグ
     * @return HTML文字列
     */
    public static String removeTag(String htmlStr, String tag) {
        if (StringUtil.isNullZeroString(htmlStr)) {
            return htmlStr;
        }
        //指定タグ存在チェックのため、対象文字列を小文字に変換
        StringBuilder sbHtmlStr = new StringBuilder(htmlStr);
        StringBuilder sbHtmlStrLower = new StringBuilder(htmlStr.toLowerCase());
        String[] targetList = new String[] {
                "<[\\s]*?" + tag +".*/[\\s]*?" + tag + "[\\s]*?>",
                "<[\\s]*?" + tag + ".*/[\\s]*?>"
        };
        for (String target : targetList) {
            //指定タグがHTML文字列に存在するかを確認
            Pattern p = Pattern.compile(target);
            Matcher m1 = p.matcher(sbHtmlStrLower);
            if (!m1.find()) {
//                return sbHtmlStr.toString();
                continue;
            }
            String matchStr = m1.group();
            int idx = 0;
            while (!StringUtil.isNullZeroString(matchStr)) {
                idx = sbHtmlStrLower.indexOf(matchStr, idx);
                sbHtmlStr.replace(idx, idx + matchStr.length(), "");
                sbHtmlStrLower.replace(idx, idx + matchStr.length(), "");
                matchStr = "";
                m1 = p.matcher(sbHtmlStrLower);
                if (m1.find()) {
                    matchStr = m1.group();
                }
            }
        }
        return sbHtmlStr.toString();
    }

    /**
     * <br>[機  能] 指定されたタグの特定の属性が存在する場合削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param htmlStr HTML文字列
     * @param tag 指定するタグ
     * @param attribute 指定する属性
     * @return HTML文字列
     */
    public static String removeAttribute(String htmlStr, String tag, String attribute) {

        if (StringUtil.isNullZeroString(htmlStr) || StringUtil.isNullZeroString(tag)
            || StringUtil.isNullZeroString(attribute)) {
            return htmlStr;
        }

        //存在チェックのため、対象文字列を小文字に変換
        StringBuilder sbHtmlStr = new StringBuilder(htmlStr);
        StringBuilder sbHtmlStrLower = new StringBuilder(htmlStr.toLowerCase());

        String[] targetList = new String[] {
                "<" + tag + "[\\s]*?.*" + attribute + "[\\s]*=.*/[\\s]*?" + tag + "[\\s]*?>",
                "<" + tag + "[\\s]*?.*" + attribute + "[\\s]*=.*/[\\s]*?>"
        };

        for (String target : targetList) {
            //指定タグの指定属性内に除去文字列がHTML文字列に存在するかを確認
            Pattern p = Pattern.compile(target, Pattern.DOTALL);
            Matcher m1 = p.matcher(sbHtmlStrLower);
            if (!m1.find()) {
                continue;
            }

            String matchStr = m1.group();
            int idx = 0;
            while (!StringUtil.isNullZeroString(matchStr)) {
                idx = sbHtmlStrLower.indexOf(matchStr, idx);
                sbHtmlStr.replace(idx, idx + matchStr.length(), "");
                sbHtmlStrLower.replace(idx, idx + matchStr.length(), "");
                matchStr = "";
                m1 = p.matcher(sbHtmlStrLower);
                if (m1.find()) {
                    matchStr = m1.group();
                }
            }
        }
        return sbHtmlStr.toString();
    }

    /**
     * <br>[機  能] 指定されたタグの特定の属性から指定文字が存在する場合に削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param htmlStr HTML文字列
     * @param tag 指定するタグ
     * @param attribute 指定する属性
     * @param word 指定する文字列
     * @return HTML文字列
     */
    public static String removeWord(String htmlStr, String tag, String attribute, String word) {

        if (StringUtil.isNullZeroString(htmlStr) || StringUtil.isNullZeroString(tag)
            || StringUtil.isNullZeroString(attribute) || StringUtil.isNullZeroString(word)) {
            return htmlStr;
        }

        //存在チェックのため、対象文字列を小文字に変換
        StringBuilder sbHtmlStr = new StringBuilder(htmlStr);
        StringBuilder sbHtmlStrLower = new StringBuilder(htmlStr.toLowerCase());

        String[] targetList = new String[] {
                "<[\\s]*?.*" + tag + "[\\s]*?.*" + attribute + "[\\s]*=\".*" + word + ".*\".*/[\\s]*?" + tag + "[\\s]*?>"
        };

        for (String target : targetList) {
            //指定タグの指定属性内に除去文字列がHTML文字列に存在するかを確認
            Pattern p = Pattern.compile(target);
            Matcher m1 = p.matcher(sbHtmlStrLower);
            if (!m1.find()) {
                continue;
            }

            String matchStr = m1.group();
            int idx = 0;
            while (!StringUtil.isNullZeroString(matchStr)) {
                idx = sbHtmlStrLower.indexOf(matchStr, idx);
                sbHtmlStr.replace(idx, idx + matchStr.length(), "");
                sbHtmlStrLower.replace(idx, idx + matchStr.length(), "");
                matchStr = "";
                m1 = p.matcher(sbHtmlStrLower);
                if (m1.find()) {
                    matchStr = m1.group();
                }
            }
        }
        return sbHtmlStr.toString();
    }
}