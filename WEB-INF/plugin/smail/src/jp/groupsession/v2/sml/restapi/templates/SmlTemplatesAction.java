package jp.groupsession.v2.sml.restapi.templates;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.model.SmlHinaModel;

/**
 * <br>[機  能] 共有ひな形情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlTemplatesAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] 共通ひな形情報API
     * <br>[解  説] GET
     * <br>[備  考] 共通ひな形情報を取得する
     * @param res レスポンス
     * @param ctx
     * @throws SQLException
     */
    @Get
    public void doGet(
            HttpServletResponse res,
            RestApiContext ctx) throws SQLException {

        SmlHinaDao  smlHnDao = new SmlHinaDao(ctx.getCon());
        List<SmlHinaModel> hinaList = smlHnDao.selectHinaList(0, GSConstSmail.HINA_KBN_CMN);

        RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
        for (SmlHinaModel hinaModel : hinaList) {
            ResultElement result = new ResultElement("result");
            result.addContent(new ResultElement("sid").addContent(String.valueOf(hinaModel.getShnSid())));
            result.addContent(new ResultElement("name").addContent(hinaModel.getShnHname()));
            result.addContent(new ResultElement("mailMarkType").addContent(
                    String.valueOf(hinaModel.getShnMark())));
            result.addContent(new ResultElement("mailSubjectText").addContent(hinaModel.getShnTitle()));
            result.addContent(new ResultElement("mailBodyText").addContent(hinaModel.getShnBody()));
            builder.addResult(result);
        }
        builder.build().execute();
    }
}
