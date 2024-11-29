package jp.groupsession.v2.cmn.restapi.users.image;

import java.io.File;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.restapi.users.CmnUsersParamModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiAttachementResponseWriter;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
*
* <br>[機  能] ユーザ画像を取得
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class CmnUsersImageAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] GET Action
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     */
    @Get
    public void doGet(
            HttpServletRequest req,
            HttpServletResponse res,
            CmnUsersParamModel param,
            RestApiContext ctx) {
        CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(ctx.getCon());
        AuthDao adao = new AuthDao(ctx.getCon());
        BaseUserModel smodel = null;
        try {
            smodel = adao.selectLoginNoPwd(param.getUserId(), null);
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行エラー", e);
        }

        if (smodel == null) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.user"));
        }

        CmnUsrmInfModel usrInfMdl = null;
        try {
            //ユーザ情報を取得する。
            usrInfMdl = usrInfDao.select(smodel.getUsrsid());
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行エラー", e);
        }

        if (usrInfMdl.getBinSid() == 0) {
            //写真なし


            //ファイルのダウンロード
            File file =
                    new File(getAppRootPath() + "/common/images/original/photo.png");
            RestApiAttachementResponseWriter.execute(res, req, ctx, file);

        } else {
            if (usrInfMdl.getUsiPictKf() == 0 || usrInfMdl.getUsrSid() == ctx.getRequestUserSid()) {
                //写真あり 公開

                Long binSid = usrInfMdl.getBinSid();
                RestApiAttachementResponseWriter.execute(res, req, ctx, binSid);

            } else {
                //写真あり 非公開
                //ファイルのダウンロード
                File file =
                        new File(getAppRootPath() + "/common/images/original/photo_hikokai.gif");
                RestApiAttachementResponseWriter.execute(res, req, ctx, file);
            }
        }


    }

}
