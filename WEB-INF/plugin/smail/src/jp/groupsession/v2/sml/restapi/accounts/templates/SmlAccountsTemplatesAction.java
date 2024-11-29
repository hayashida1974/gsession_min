package jp.groupsession.v2.sml.restapi.accounts.templates;

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
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 個人ひな形情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsTemplatesAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] 個人ひな形情報API
     * <br>[解  説] GET
     * <br>[備  考] 個人ひな形情報を取得する
     * @param res レスポンス
     * @param param
     * @param ctx
     * @throws SQLException
     */
    @Get
    public void doGet(
            HttpServletResponse res,
            SmlAccountsTemplatesGetParamModel param,
            RestApiContext ctx) throws SQLException {

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

        // 選択されているアカウントが使用可能かを判定する
        SmailDao smlDao = new SmailDao(ctx.getCon());
        if (param.getAccountSid() <= 0 || !smlDao.canUseCheckAccount(
                ctx.getRequestUserModel().getUsrsid(), param.getAccountSid())) {
            // アカウントが存在しない場合
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.account")
                    );
        }

        SmlHinaDao  smlHnDao = new SmlHinaDao(ctx.getCon());
        List<SmlHinaModel> hinaList = smlHnDao.selectHinaList(
                param.getAccountSid(), GSConstSmail.HINA_KBN_PRI);

        RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
        for (SmlHinaModel hinaModel : hinaList) {
            ResultElement result = new ResultElement("result");
            result.addContent(new ResultElement(
                    "sid").addContent(String.valueOf(hinaModel.getShnSid())));
            result.addContent(new ResultElement("name").addContent(hinaModel.getShnHname()));
            result.addContent(new ResultElement("mailMarkType").addContent(
                    String.valueOf(hinaModel.getShnMark())));
            result.addContent(new ResultElement(
                    "mailSubjectText").addContent(hinaModel.getShnTitle()));
            result.addContent(new ResultElement("mailBodyText").addContent(hinaModel.getShnBody()));
            builder.addResult(result);
        }
        builder.build().execute();
    }
}
