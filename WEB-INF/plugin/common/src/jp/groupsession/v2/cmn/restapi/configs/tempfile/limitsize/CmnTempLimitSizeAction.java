package jp.groupsession.v2.cmn.restapi.configs.tempfile.limitsize;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;

/**
 * <br>[機  能] 添付ファイル制限サイズ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnTempLimitSizeAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] 添付ファイル制限サイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param ctx
     * @throws SQLException
     */
    @Get
    public void doGet(HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx) throws SQLException {
        RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);

        CmnFileConfDao cfcDao = new CmnFileConfDao(ctx.getCon());
        //添付ファイル最大容量取得

        CmnFileConfModel cfcMdl = cfcDao.select();


        //FileSize 1ファイルの制限サイズ（単位：MB）
        builder.addResult(
                new ResultElement("Result")
                    .addContent(
                            new ResultElement("sizeMbNum")
                                .addContent(Integer.toString(cfcMdl.getFicMaxSize()))
                            )
                    );

        builder.build().execute();

    }
}
