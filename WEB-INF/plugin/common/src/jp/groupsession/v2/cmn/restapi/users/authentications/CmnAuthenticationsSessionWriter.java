package jp.groupsession.v2.cmn.restapi.users.authentications;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.login.biz.GSLoginBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] ログインセッション作成ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnAuthenticationsSessionWriter implements ISessionWriter {

    @Override
    public void doLogin(HttpServletRequest req, RestApiContext ctx) {
        GSLoginBiz cmnLoginBiz = new GSLoginBiz();
        try {
            cmnLoginBiz.doLogin(
                    req, ctx.getRequestUserModel(), ctx.getCon(), ctx.getPluginConfig());
            ctx.getCon().commit();

        } catch (Exception e) {
            throw new RuntimeException("ログインセッション生成失敗", e);
        }

    }

}
