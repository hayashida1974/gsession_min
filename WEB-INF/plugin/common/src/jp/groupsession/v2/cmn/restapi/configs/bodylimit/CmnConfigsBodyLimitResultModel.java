package jp.groupsession.v2.cmn.restapi.configs.bodylimit;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.biz.config.bodylimit.IBodyLimitConfig;

public class CmnConfigsBodyLimitResultModel {
    /*アプリケーションルートパス */
    String appRootPath__;

    /** ショートメール本文の文字制限数 */
    private int smailBodyLimitNum__;
    /** WEBメール本文の文字制限数 */
    private int webmailBodyLimitNum__;
    /** 回覧板内容の文字制限数 */
    private int circularBodyLimitNum__;
    /** ポートレット本文の文字制限数 */
    private int portletBodyLimitNum__;

    


    public CmnConfigsBodyLimitResultModel(String appRootPath) {
        appRootPath__ = appRootPath;

        smailBodyLimitNum__ =
            __getConfValue("jp.groupsession.v2.sml.smailBodyLimitConfig");

        webmailBodyLimitNum__ =
            __getConfValue("jp.groupsession.v2.wml.WebmailBodyLimitConfig");

        circularBodyLimitNum__ =
            __getConfValue("jp.groupsession.v2.cir.CircularBodyLimitConfig");

        portletBodyLimitNum__ =
            __getConfValue("jp.groupsession.v2.ptl.PortletBodyLimitConfig");
    }




    /** @return ショートメール本文の文字制限数 */
    public int getSmailBodyLimitNum() {
        return smailBodyLimitNum__;
    }




    /** @return WEBメール本文の文字制限数 */
    public int getWebmailBodyLimitNum() {
        return webmailBodyLimitNum__;
    }




    /** @return 回覧板内容の文字制限数 */
    public int getCircularBodyLimitNum() {
        return circularBodyLimitNum__;
    }




    /** @return ポートレット本文の文字制限数 */
    public int getPortletBodyLimitNum() {
        return portletBodyLimitNum__;
    }




    private int __getConfValue(String confClassName) {
        IBodyLimitConfig instance = null;

        if (!StringUtil.isNullZeroString(confClassName)) {
            try {
                @SuppressWarnings("unchecked")
                Class<IBodyLimitConfig> cls = (Class<IBodyLimitConfig>) Class
                        .forName(confClassName);
                instance = Optional.ofNullable(cls
                    .getDeclaredConstructor()
                    .newInstance())
                    .map(o -> (IBodyLimitConfig) o)
                    .orElse(null);

            } catch (IllegalAccessException | ClassNotFoundException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException | InstantiationException e) {
            }
            if (instance != null) {
                return instance.getMaxLength(appRootPath__);
            }
        }
        return -1;
    }    

}
