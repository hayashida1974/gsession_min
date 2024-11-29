package jp.groupsession.v2.struts.msg;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] メッセージファイルよりメッセージを取得する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsMessage {

    /** メッセージリソースファイルの識別子 */
    public static final String RESOURECE_KEY = "message";
    /** Locale */
    private Locale locale__ = null;

    /** MessageResources */
    private static MessageResources messageResources__ = null;

    /**
     * @return messageResources
     */
    public static MessageResources getMessageResources() {
        return messageResources__;
    }

    /**
     * @param messageResources 設定する messageResources
     */
    public static void setMessageResources(MessageResources messageResources) {
        messageResources__ = messageResources;
    }

    /**
     * <p>コンストラクタ
     */
    public GsMessage() {
        locale__ = null;
    }

    /**
     * <p>コンストラクタ
     * @param req リクエスト
     */
    public GsMessage(HttpServletRequest req) {
        locale__ = new Locale(getLocale(req));
    }

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエスト情報
     */
    public GsMessage(RequestModel reqMdl) {
        locale__ = new Locale(getLocale(reqMdl));
    }

    /**
     * <p>コンストラクタ
     * @param locale ロケール
     */
    public GsMessage(Locale locale) {
        locale__ = locale;
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param req リクエスト
     * @param key メッセージキー
     * @return メッセージ
     */
    public String getMessage(HttpServletRequest req, String key) {
        return getMessage(req, key, "");
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param req リクエスト
     * @param key メッセージキー
     * @param value0 メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(HttpServletRequest req, String key, String value0)
    {
        return getMessage(key, new String[] {
            value0
        });
    }


    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param req リクエスト
     * @param key メッセージキー
     * @param value0 メッセージパラメータ
     * @param value1 メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(HttpServletRequest req, String key, String value0, String value1)
    {
        return getMessage(key, new String[] {
            value0, value1
        });
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param req リクエスト
     * @param key メッセージキー
     * @param value0 メッセージパラメータ
     * @param value1 メッセージパラメータ
     * @param value2 メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(HttpServletRequest req, String key, String value0, String value1,
            String value2) {

        return getMessage(key, new String[] {
            value0, value1, value2
        });
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param req リクエスト
     * @param key メッセージキー
     * @param value0 メッセージパラメータ
     * @param value1 メッセージパラメータ
     * @param value2 メッセージパラメータ
     * @param value3 メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(HttpServletRequest req, String key, String value0, String value1,
            String value2, String value3) {

        return getMessage(key, new String[] {
            value0, value1, value2, value3
        });
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param req リクエスト
     * @param key メッセージキー
     * @param args メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(HttpServletRequest req, String key, String[] args) {
        try {
            return messageResources__.getMessage(new Locale(getLocale(req)), key, args);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param key メッセージキー
     * @return メッセージ
     */
    public String getMessage(String key) {
        return getMessage(key, new String[0]);
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param key メッセージキー
     * @param value0 メッセージパラメータ
     * @return メッセージ
     */
    public String getMessageVal0(String key, String value0) {
        return getMessage(key, new String[] {value0});
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param key メッセージキー
     * @param args メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(String key, String[] args) {

        try {
            if (locale__ != null) {
                return messageResources__.getMessage(locale__, key, args);
            }
            return messageResources__.getMessage(Locale.JAPANESE, key, args);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param msgResource MessageResources
     * @param key メッセージキー
     * @return メッセージ
     */
    public String getMessage(MessageResources msgResource, String key) {
        return getMessage(msgResource, key, null);
    }

    /**
     * <p>指定したメッセージキーに対応するメッセージを取得する
     * @param msgResource MessageResources
     * @param key メッセージキー
     * @param arg メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(MessageResources msgResource, String key, String[] arg) {
        try {
            return msgResource.getMessage(key, arg);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p>locale を取得する
     * @param req リクエスト
     * @return lang
     */
    public String getLocale(HttpServletRequest req) {
        String lang = "JP";

        if (req != null) {
            lang = req.getLocale().getLanguage();

            if (req.getSession(false) != null
            && req.getSession(false).getAttribute(GSConst.SESSION_KEY) != null) {
                BaseUserModel umodel = (BaseUserModel) req.getSession(false).getAttribute(
                        GSConst.SESSION_KEY);
                lang = getLocale(umodel);
            }
        }
        return lang;
    }

    /**
     * <p>locale を取得する
     * @param reqMdl リクエスト情報
     * @return lang
     */
    public String getLocale(RequestModel reqMdl) {
        String lang = "JP";

        if (reqMdl != null) {
            lang = getLocale(reqMdl.getSmodel());
        }

        return lang;
    }

    /**
     * <p>locale を取得する
     * @param umodel セッションユーザ情報
     * @return lang
     */
    public String getLocale(BaseUserModel umodel) {
        String lang = "JP";

        if (umodel != null) {
            lang = umodel.getCountry();
        }
        if (!StringUtil.isNullZeroString(lang)) {
            if (!lang.equals("JP")
             && !lang.equals("US")
             && !lang.equals("BK")
             && !lang.equals("AU")) {
                lang = "JP";
            }
        }

        return lang;
    }

    /**
     * <p>リソースを取得する
     * @param req リクエスト
     * @return resourceKey
     */
    public String getResourceKey(HttpServletRequest req) {

        String resourceKey = GsMessage.RESOURECE_KEY;
        String locale = getLocale(req);
        if (!StringUtil.isNullZeroString(locale)) {
            String languageCode = null;
            languageCode = getLanguageCode(locale);
            if (!StringUtil.isNullZeroString(languageCode)) {
                resourceKey += "_" + languageCode;
            }
        }
        return resourceKey;
    }

    /**
     * <p>リソースを取得する
     * @param resourceKey resourceKey
     * @param req リクエスト
     * @return resourceKey
     */
    public String getResourceKey(String resourceKey, HttpServletRequest req) {

        String locale = getLocale(req);
        if (!StringUtil.isNullZeroString(locale)) {
            String languageCode = null;
            languageCode = getLanguageCode(locale);
            if (!StringUtil.isNullZeroString(languageCode)) {
                resourceKey += "_" + languageCode;
            }
        }
        return resourceKey;
    }

    /**
     * <p>locale を取得する
     * @param country 国コード
     * @return resourceKey
     */
    public String getLanguageCode(String country) {

        if (country.equals("JP")) {
            return "ja";
        } else if (country.equals("US")
                || country.equals("BK")
                || country.equals("AU")) {
            return "en";
        } else {
            return "ja";
        }
    }

    /**
     * <p>言語の文字列を取得する
     * @param country 国コード
     * @return resourceKey
     */
    public String getLanguageStr(String country) {

        if (country.equals("JP")) {
            return "言語";
        } else if (country.equals("US")
                || country.equals("BK")
                || country.equals("AU")) {
            return "Language";
        } else {
            return "言語";
        }
    }

    /**
     * <p>locale を取得します。
     * @return locale
     */
    public Locale getLocale() {
        return locale__;
    }

    /**
     * <p>locale をセットします。
     * @param locale locale
     */
    public void setLocale(Locale locale) {
        locale__ = locale;
    }
}