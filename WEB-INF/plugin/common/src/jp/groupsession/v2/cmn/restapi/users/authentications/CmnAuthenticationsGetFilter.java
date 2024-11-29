package jp.groupsession.v2.cmn.restapi.users.authentications;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.filter.IActionFilter;
import jp.groupsession.v2.restapi.filter.RestApiActionFilterChain;

public class CmnAuthenticationsGetFilter implements IActionFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res,
            RestApiContext ctx, RestApiActionFilterChain chain) {

        chain.doFilter(req, res, ctx);

        RequestLocal.put(
                GSConstApi.LOCALKEY_LOGINSESSION_WRITER,
                new CmnAuthenticationsSessionWriter());

    }

}
