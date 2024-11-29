package jp.groupsession.v2.man.restapi.application.version;

import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
/**
 *
 * <br>[機  能] GSバージョン情報API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("main")
public class ManApplicationVersionAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] GSバージョン情報API
    * <br>[解  説] GET
    * <br>[備  考] GSバージョン情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param ctx RestApiコンテキスト
    */
   @Get
   public void doGet(HttpServletRequest req,
           HttpServletResponse res,
           RestApiContext ctx) {

       HashMap<String, String> result = new HashMap<String, String>();

       // versionText
       result.put("versionText", GSConst.VERSION);

       RestApiResponseWriter.builder(res, ctx)
       .addResult(new ResultElement("result")
               .addContent(
                   result.entrySet()
                       .stream()
                       .map(e -> new ResultElement(e.getKey()).addContent(e.getValue()))
                       .collect(Collectors.toSet())))
       .build().execute();
   }
}