package jp.groupsession.v2.wml.restapi.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.base.WmlTempfileDao;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;

/**
 * <br>[機  能] WEBメールのRESTAPI メール添付ファイルに関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlRestMailBinBiz {

    /**
     * <br>[機  能] アカウントSIDから当該アカウントが保持するメール情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid WEBメールアカウントSID
     * @return メール情報
     */
    public List<WmlMaildataModel> getMails(Connection con, int wacSid) throws SQLException {

        WmlMaildataDao dao = new WmlMaildataDao(con);
        List<WmlMaildataModel> ret = dao.getMailDataList(wacSid);
        return ret;
    }

    /**
     * <br>[機  能] メールSIDから当該メールに添付されたファイル情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mailSid メールSID
     * @return 添付ファイル情報
     */
    public List<WmlTempfileModel> getBins(Connection con, long mailSid) throws SQLException {

        WmlTempfileDao dao = new WmlTempfileDao(con);
        List<WmlTempfileModel> ret = dao.getTempFileList(mailSid);
        return ret;
    }

    /**
     * <br>[機  能] パスパラメータに指定したアカウントID/メールSID/バイナリSIDをログインユーザが使用可能かをチェックし、使用不可能であればエラーをthrowします。
     * <br>[解  説]
     * <br>[備  考] エラーコードはWEBMAIL-103です
     * @param ctx RestApiコンテキスト
     * @param accountId WEBメールアカウントID
     * @param mailSid メールSID
     * @param binSid バイナリSID
     */
    public void validateBin(
        RestApiContext ctx,
        String accountId,
        long mailSid,
        long binSid) throws SQLException {

        Connection con = ctx.getCon();
        WmlRestAccountBiz biz = new WmlRestAccountBiz();

        //アカウント使用可能チェック
        if (!biz.canUseAccount(ctx, accountId)) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_MAIL_TEMPFILE,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.attach.file")
            );
        }

        //メール使用可能チェック
        WmlRestMailDataBiz mailBiz = new WmlRestMailDataBiz();
        int wacSid = biz.getWmlAccountSid(con, accountId);
        if (!mailBiz.canAccess(con, wacSid, mailSid)) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_MAIL_TEMPFILE,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.attach.file")
            );
        }

        //添付ファイル使用可能チェック
        boolean binUseableFlg = false;
        List<WmlTempfileModel> binList = getBins(con, mailSid);
        for (WmlTempfileModel tmpFileMdl : binList) {
            if (tmpFileMdl.getWtfSid() == binSid 
                    && tmpFileMdl.getWtfJkbn() != GSConstWebmail.TEMPFILE_STATUS_DELETE) {
                binUseableFlg = true;
            }
        }
        if (!binUseableFlg) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_MAIL_TEMPFILE,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.attach.file")
            );
        }
        
    }
}