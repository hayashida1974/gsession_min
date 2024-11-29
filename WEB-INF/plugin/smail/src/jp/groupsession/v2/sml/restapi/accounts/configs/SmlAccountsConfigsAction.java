package jp.groupsession.v2.sml.restapi.accounts.configs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アカウント設定情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsConfigsAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] アカウント設定情報API
    * <br>[解  説] GET
    * <br>[備  考] 設定情報を取得する
    * @param res サーブレットレスポンス
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Get
   public void doGet(
           HttpServletResponse res,
           RestApiContext ctx) throws SQLException {
       SmlAccountsConfigsDao configDao = new SmlAccountsConfigsDao(ctx.getCon());

       //ショートメールプラグインアクセス権限確認
       List<String> pluginList = new ArrayList<String>();
       pluginList.add(GSConst.PLUGINID_SML);
       if (!canAccessPlugin(pluginList)) {
           throw new RestApiPermissionException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "error.cant.use.plugin",
                   new GsMessage(ctx.getRequestModel()).getMessage("cmn.shortmail")
                   );
       }
       // アカウント情報取得
       List<SmlAccountsConfigsGetResultModel> accountConfigList
                        = configDao.getAccountConfigList(
                                ctx.getRequestModel(), ctx.getRequestUserModel().getUsrsid());

       RestApiResponseWriter.builder(res, ctx)
       .addResultList(accountConfigList)
       .build().execute();
   }
}
