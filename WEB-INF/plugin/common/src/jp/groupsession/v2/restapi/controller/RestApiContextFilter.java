package jp.groupsession.v2.restapi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.restapi.filter.IActionFilter;
import jp.groupsession.v2.restapi.filter.RestApiActionFilterChain;
import jp.groupsession.v2.restapi.filter.annotation.ActionFilter;
@ActionFilter()
public class RestApiContextFilter implements IActionFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res,
            RestApiContext ctx, RestApiActionFilterChain chain) {
        chain.doFilter(req, res, RestApiContext._getInstance());

    }

}
