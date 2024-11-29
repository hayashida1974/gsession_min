package jp.groupsession.v2.sml;

import jp.groupsession.v2.cmn.GSConstShortMail;
import jp.groupsession.v2.cmn.biz.config.bodylimit.IBodyLimitConfig;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;

public class smailBodyLimitConfig implements IBodyLimitConfig {

    @Override
    public int getMaxLength(String appRootPath) {
        int ret = SmlCommonBiz.getBodyLimitLength(appRootPath);
        if (ret < 0) {
            return GSConstShortMail.MAILBODY_LIMIT_DEFAULT;
        }
        return ret;
    }

}
