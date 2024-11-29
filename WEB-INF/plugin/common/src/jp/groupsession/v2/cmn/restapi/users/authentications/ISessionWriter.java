package jp.groupsession.v2.cmn.restapi.users.authentications;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.restapi.controller.RestApiContext;

public interface ISessionWriter {

    void doLogin(HttpServletRequest req, RestApiContext ctx);

}
