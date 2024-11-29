package jp.co.sjts.util.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;

/**
 * <br>[機  能] ブラウザ(クライアント)に関するユーティリティークラス
 * <br>[解  説]
 * <br>[備  考] リクエストヘッダのUSER-AGENTを元にするため、厳密な判定ではないことに注意すること。
 * @author JTS
 */
public class BrowserUtil {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(BrowserUtil.class);

    /** user-agentキーワード */
    public static final String USER_AGENT_KEYWORD = "user-agent";
    /** IE判定用文字列 */
    public static final String BROWSER_KEYWORD_MSIE = "MSIE";
    /** IE判定用文字列 */
    public static final String BROWSER_KEYWORD_TRIDENT = "Trident";
    /** Edge判定用文字列 */
    public static final String BROWSER_KEYWORD_EDGE = "Edge";
    /** Chrome判定用文字列 ただし Edgeにも含まれる*/
    public static final String BROWSER_KEYWORD_CHROME = "Chrome";

    /**
     * Netscape判定用文字列 ver6未満は取得不可(判定に値する文字列が付加されていないため。)
     */
    public static final String BROWSER_KEYWORD_NETSCAPE = "Netscape";
    /** Firefox判定用文字列 */
    public static final String BROWSER_KEYWORD_FIREFOX = "Firefox";
    /** Safari判定用文字列  ただし Chrome,Edgeにも含まれる*/
    public static final String BROWSER_KEYWORD_SAFARI = "Safari";
    /** DoCoMo判定用文字列 */
    public static final String BROWSER_KEYWORD_DOCOMO = "DoCoMo";
    /** AU WAP2.0対応ブラウザ 判定用文字列 */
    public static final String BROWSER_KEYWORD_AUWAP20 = "KDDI";
    /** AU WAP2.0未対応、HDMLブラウザ 判定用文字列 */
    public static final String BROWSER_KEYWORD_AUHDML = "UP.Browser";
    /** Vodafone判定用文字列1(Vodafone) */
    public static final String BROWSER_KEYWORD_VODAFONE1 = "Vodafone";
    /** Vodafone判定用文字列2(J-PHONE) */
    public static final String BROWSER_KEYWORD_VODAFONE2 = "J-PHONE";
    /** Vodafone判定用文字列3(MOT-V980) */
    public static final String BROWSER_KEYWORD_VODAFONE3 = "MOT-V980";
    /** Vodafone判定用文字列4(MOT-C980) */
    public static final String BROWSER_KEYWORD_VODAFONE4 = "MOT-C980";
    /** SoftBank判定用文字列 */
    public static final String BROWSER_KEYWORD_SOFTBANK = "SoftBank";
    /** Android */
    public static final String BROWSER_KEYWORD_ANDROID = "Android";
    /** iPhone */
    public static final String BROWSER_KEYWORD_IPHONE = "iPhone";
    /** iPad */
    public static final String BROWSER_KEYWORD_IPAD = "iPad";
    /** iPod */
    public static final String BROWSER_KEYWORD_IPOD = "iPod";
    /** GSモバイルアシスト */
    public static final String BROWSER_KEYWORD_GSMBA = "GsMobileApps";
    /** GSクロスライド */
    private static final String BROWSER_KEYWORD_GSCROSSRIDE = "GSCrossRide";

    /**
     * <br>[機  能] クライアントがIEかどうか判定を行う
     * <br>[解  説] IEの場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:IE,false:IE以外
     */
    public static boolean isIe(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_MSIE)
        || isTargetBrowser(req, BROWSER_KEYWORD_TRIDENT)) {
            //IEの場合
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] クライアントがEdgeかどうか判定を行う
     * <br>[解  説] Edgeの場合true,それ以外の場合false
     * <br>[備  考] EdgeのUSER_AGENT文字列は以下の通りでSafari、Chromeと全部入り
     * <br>  Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
     * <br> Chrome/39.0.2171.71 Safari/537.36 Edge/12.0
     * @param req HttpServletRequest
     * @return true:Edge,false:Edge以外
     */
    public static boolean isEdge(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_EDGE)) {
            //Edgeの場合
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] クライアントがIEかどうか判定を行う
     * <br>[解  説] Netscapeの場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:Netscape, false:Netscape以外
     */
    public static boolean isNetscape(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_NETSCAPE)) {
            //Netscapeの場合
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがFirefoxかどうか判定を行う
     * <br>[解  説] Firefoxの場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:Firefox, false:Firefox以外
     */
    public static boolean isFirefox(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_FIREFOX)) {
            //Firefoxの場合
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがSafariかどうか判定を行う
     * <br>[解  説] Safariの場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:Safari, false:Safari以外
     */
    public static boolean isSafari(HttpServletRequest req) {
        boolean ret = isTargetBrowser(req, BROWSER_KEYWORD_SAFARI);
        if (!ret) {
            return ret;
        }
        //Safariの文字列を含み、Edgeを含む場合
        if (isEdge(req)) {
            return false;
        }
        if (isTargetBrowser(req, BROWSER_KEYWORD_CHROME)) {
            return false;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがDocomo携帯端末かどうか判定を行う
     * <br>[解  説] Docomoの場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:Docomo, false:Docomo以外
     */
    public static boolean isDocomo(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_DOCOMO)) {
            //Docomoの場合
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがAU携帯端末(WAP2.0対応端末)かどうか判定を行う
     * <br>[解  説] AU携帯端末(WAP2.0対応端末)の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:AU携帯端末(WAP2.0対応端末), false:それ以外
     */
    public static boolean isAu(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_AUWAP20)) {
            ret = true;
        } else if (isTargetBrowser(req, BROWSER_KEYWORD_AUHDML)) {
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがAU携帯端末(WAP2.0対応端末)かどうか判定を行う
     * <br>[解  説] AU携帯端末(WAP2.0対応端末)の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:AU携帯端末(WAP2.0対応端末), false:それ以外
     */
    public static boolean isAuWap20(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_AUWAP20)) {
            //AU携帯端末(WAP2.0対応端末)の場合
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがAU携帯端末(WAP2.0未対応、HDML)かどうか判定を行う
     * <br>[解  説] AU携帯端末(WAP2.0未対応、HDML)の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:AU携帯端末(WAP2.0未対応、HDML), false:それ以外
     */
    public static boolean isAuHdml(HttpServletRequest req) {
        if (isAuWap20(req)) {
            //WAP2.0対応端末の場合false
            return false;
        }
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_AUHDML)) {
            //AU携帯端末(WAP2.0未対応、HDML)の場合
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがVodafone携帯端末かどうか判定を行う
     * <br>[解  説] Vodafone携帯端末)の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:Vodafone携帯端末, false:それ以外
     */
    public static boolean isVodafone(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_VODAFONE1)) {
            //Vodafone
            ret = true;
        } else if (isTargetBrowser(req, BROWSER_KEYWORD_VODAFONE2)) {
            //J-PHONE
            ret = true;
        } else if (isTargetBrowser(req, BROWSER_KEYWORD_VODAFONE3)) {
            //MOT-V980
            ret = true;
        } else if (isTargetBrowser(req, BROWSER_KEYWORD_VODAFONE4)) {
            //MOT-C980
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] クライアントがSoftBank携帯端末かどうか判定を行う
     * <br>[解  説] SoftBank携帯端末の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:SoftBank携帯, false:それ以外
     */
    public static boolean isSoftBank(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_SOFTBANK)) {
            //SoftBank携帯端末の場合
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] クライアントがAndroid携帯端末かどうか判定を行う
     * <br>[解  説] Android携帯端末の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:Android携帯, false:それ以外
     */
    public static boolean isAndroid(HttpServletRequest req) {
        boolean ret = false;
        if (req != null) {
            if (isTargetBrowser(req, BROWSER_KEYWORD_ANDROID)) {
                //Android携帯端末の場合
                ret = true;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] クライアントがiPhone携帯端末かどうか判定を行う
     * <br>[解  説] iPhone携帯端末の場合true,それ以外の場合false
     * <br>[備  考] 発売当初のiPadを除外するため文字列「iPhone」を含み「iPad」を含まないことを判定条件とする。
     * @param req HttpServletRequest
     * @return true:iPhone携帯, false:それ以外
     */
    public static boolean isIPhone(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_IPHONE) && !isIPad(req)) {
            //iPhone携帯端末の場合
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] クライアントがiPad端末かどうか判定を行う
     * <br>[解  説] iPad携帯端末の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:iPad, false:それ以外
     */
    public static boolean isIPad(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_IPAD)) {
            //iPad端末の場合
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] クライアントがiPod端末かどうか判定を行う
     * <br>[解  説] iPod携帯端末の場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:iPod, false:それ以外
     */
    public static boolean isIPod(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_IPOD)) {
            //iPod端末の場合
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] クライアントがGSモバイルアシストかどうか判定を行う
     * <br>[解  説] GSモバイルアシストの場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:iPod, false:それ以外
     */
    public static boolean isMobileApps(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_GSMBA)) {
            //iPod端末の場合
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したブラウザかどうか判定する。
     * <br>[解  説] リクエストヘッダ(user-agent)の値で判定を行う。
     * <br>[備  考]
     * @param req HttpServletRequest
     * @param browserKeyWord ブラウザ判定文字列
     * @return boolean true:指定したブラウザ, false:指定したブラウザ以外
     */
    public static boolean isTargetBrowser(HttpServletRequest req, String browserKeyWord) {
        //user-agentの値を取得
        String agent = NullDefault.getString(req.getHeader(USER_AGENT_KEYWORD), "");
        log__.debug("agent: " + agent);
        if (!StringUtil.isNullZeroStringSpace(agent)) {
            if (agent.indexOf(browserKeyWord) >= 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * <br>[機  能] クライアントがCrossRideかどうか判定を行う
     * <br>[解  説] GSモバイルアシストの場合true,それ以外の場合false
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return true:iPod, false:それ以外
     */
    public static boolean isCrossRide(HttpServletRequest req) {
        boolean ret = false;
        if (isTargetBrowser(req, BROWSER_KEYWORD_GSCROSSRIDE)) {
            ret = true;
        }
        return ret;
    }
}