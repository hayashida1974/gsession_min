package jp.groupsession.v2.wml;

import jp.groupsession.v2.cmn.biz.config.bodylimit.IBodyLimitConfig;
import jp.groupsession.v2.wml.biz.WmlBiz;

public class WebmailBodyLimitConfig implements IBodyLimitConfig {

    @Override
    public int getMaxLength(String appRootPath) {
        return WmlBiz.getBodyLimitLength(appRootPath);
    }

}
