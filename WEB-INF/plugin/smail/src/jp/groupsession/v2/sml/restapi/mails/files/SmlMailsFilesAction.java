package jp.groupsession.v2.sml.restapi.mails.files;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiAttachementResponseWriter;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.model.AccountDataModel;
import jp.groupsession.v2.sml.sml270.Sml270Dao;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 添付ファイル情報API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlMailsFilesAction extends AbstractRestApiAction {

    /**
     *
     * <br>[機  能] 添付ファイル情報API
     * <br>[解  説] GET
     * <br>[備  考] 添付ファイル情報を取得する
     * @param req サーブレットリクエスト
     * @param res サーブレットレスポンス
     * @param param パラメータモデル
     * @param ctx RestApiコンテキスト
     * @throws SQLException
     * @throws TempFileException
     */
    @Get
    public void doGet(HttpServletRequest req,
            HttpServletResponse res,
            SmlMailsFilesGetParamModel param,
            RestApiContext ctx) throws SQLException, TempFileException {

        //ユーザが使用可能なアカウントの一覧を取得
        int sessionUserSid = ctx.getRequestUserModel().getUsrsid();
        Sml270Dao dao270 = new Sml270Dao(ctx.getCon());
        List<AccountDataModel> accountList = dao270.getAccountList(sessionUserSid);
        List<Integer> accountSidList =
                accountList.stream()
                .map(mdl -> mdl.getAccountSid())
                .collect(Collectors.toList());

        //利用可能なアカウント内の受信メール情報かを判定
        int smailSid = param.getMailSid();
        SmlJmeisDao smjDao = new SmlJmeisDao(ctx.getCon());
        boolean existSmail = smjDao.existMailInAccount(accountSidList, smailSid);

        if (!existSmail) {
            //受信メール情報が存在しない場合、利用可能なアカウント内の送信メールかを判定
            SmlSmeisDao smsDao = new SmlSmeisDao(ctx.getCon());
            existSmail = smsDao.existMailInAccount(accountSidList, smailSid);
        }

        if (!existSmail) {
            //メールが存在しない
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.shortmail")
                    );
        }

        //ファイル存在チェック
        long binSid = param.getBinSid();
        SmlBinDao smlBinDao = new SmlBinDao(ctx.getCon());
        int fileSmlSid = smlBinDao.getSmlSid(binSid);
        if (fileSmlSid == 0 || fileSmlSid != smailSid) {
            //添付ファイルが存在しない
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.attach.file")
                    );
        }

        //DBよりファイル情報を取得する。
        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel binModel = cmnBiz.getBinInfo(ctx.getCon(), param.getBinSid(),
                GroupSession.getResourceManager().getDomain(req));
        if (binModel == null
                || binModel.getBinJkbn() == GSConst.JTKBN_DELETE
                || binModel.getBinFilekbn() != GSConst.FILEKBN_COMMON) {
            //ファイルが存在しないか、削除されている場合
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.attach.file")
                    );
        }

        RestApiAttachementResponseWriter.execute(res, req, ctx, param.getBinSid());
    }
}