package jp.groupsession.v2.wml.restapi.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;

/**
 * <br>[機  能] WEBメールのRESTAPI テンプレート添付ファイルに関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlRestTemplateBinBiz {

    /**
     * <br>[機  能] パスパラメータに指定したバイナリSIDが使用可能かをチェックし、使用不可能であればエラーをthowする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid WEBメールアカウントSID
     * @param templateSid テンプレートSID
     * @param binSid バイナリSID
     * @throws SQLException 
     */
    public void validateBin(
        RestApiContext ctx,
        String accountId,
        Integer templateSid,
        long binSid) throws SQLException {

            validateBinPathParam(ctx, accountId, templateSid, new long[] {binSid});
        }
    
    /**
     * <br>[機  能] パスパラメータに指定したバイナリSIDが使用可能かをチェックし、使用不可能であればエラーをthowする
     * <br>[解  説]
     * <br>[備  考] リソースに対するエラーコードを返す
     * @param con コネクション
     * @param wacSid WEBメールアカウントSID
     * @param templateSid テンプレートSID
     * @param binSids バイナリSIDリスト
     * @param 
     * @throws SQLException 
     */
    public void validateBinPathParam(
        RestApiContext ctx,
        String accountId,
        Integer templateSid,
        long[] binSids) throws SQLException {

        Connection con = ctx.getCon();
        WmlRestAccountBiz biz = new WmlRestAccountBiz();

        //アカウント使用可能チェック
        if (!biz.canUseAccount(ctx, accountId)) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_TEMPLATE_TEMPFILE,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.attach.file")
            );
        }

        //テンプレート情報を取得
        int wacSid = biz.getWmlAccountSid(con, accountId);
        if (!validateTemplate(ctx, wacSid, templateSid)) {
            
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_TEMPLATE_TEMPFILE,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.attach.file")
            );
        }

        //添付ファイルがテンプレートに紐づいているかを確認
        if (!__validateTemplateBin(ctx, templateSid, binSids)) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_TEMPLATE_TEMPFILE,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.attach.file")
            );
        }
    }

    /**
     * <br>[機  能] リクエストボディに指定したテンプレートが使用可能かチェックする
     * <br>[解  説]
     * <br>[備  考] パラメータに対するエラーコードを返す
     * @param ctx RESTAPIコンテキスト
     * @param wacSid WEBメールアカウントSID
     * @param templateSid テンプレートSID
     * @param paramName エラー用パラメータ名 テンプレート(英語)
     * @throws SQLException 
     */
    public void validateBinReqTemplate(
        RestApiContext ctx,
        String accountId,
        Integer templateSid,
        String paramName) throws SQLException {

        Connection con = ctx.getCon();

        //WEBメールアカウントSIDの取得
        WmlRestAccountBiz biz = new WmlRestAccountBiz();
        int wacSid = biz.getWmlAccountSid(con, accountId);

        //テンプレート情報を取得        
        if (!validateTemplate(ctx, wacSid, templateSid)) {
            
            throw new RestApiValidateException(
                WmlEnumReasonCode.PARAM_CANT_ACCESS_TEMPLATE,
                "error.input.notvalidate.data",
                new GsMessage(ctx.getRequestModel()).getMessage("cmn.template")
            ).setParamName(paramName);
        }
    }

    /**
     * <br>[機  能] リクエストボディに指定したテンプレートが使用可能かチェックする
     * <br>[解  説]
     * <br>[備  考] パラメータに対するエラーコードを返す
     * @param ctx RESTAPIコンテキスト
     * @param templateSid テンプレートSID
     * @param binSids バイナリSIDリスト
     * @param paramName エラー用パラメータ名 テンプレートの添付ファイル(英語)
     * @throws SQLException 
     */
    public void validateBinReqTempBin(
        RestApiContext ctx,
        Integer templateSid,
        long[] binSids,
        String paramName) throws SQLException {


        //添付ファイルがテンプレートに紐づいているかを確認        
        if (!__validateTemplateBin(ctx, templateSid, binSids)) {
            throw new RestApiValidateException(
                WmlEnumReasonCode.PARAM_CANT_ACCESS_TEMPLATE_TEMPFILE,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.attach.file")
            ).setParamName(paramName);
        }
    }

    /**
     * <br>[機  能] アカウントが指定したテンプレートを使用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @param wacSid WEBメールアカウントSID
     * @param templateSid テンプレートSID
     * @param 使用可能フラグ true:使用可能, false:使用不可能
     * @throws SQLException 
     */
    public boolean validateTemplate(
        RestApiContext ctx, int wacSid, int templateSid) throws SQLException {

        //テンプレート情報を取得
        Connection con = ctx.getCon();
        WmlMailTemplateDao wtpDao = new WmlMailTemplateDao(con);
        WmlMailTemplateModel wtpMdl = wtpDao.select(templateSid);

        boolean useableFlg = !(wtpMdl == null
            || wtpMdl.getWtpType() == GSConstWebmail.WTP_TYPE_ACCOUNT
            && wtpMdl.getWacSid() != wacSid);

        return useableFlg;
    }

    /**
     * <br>[機  能] 添付ファイルがテンプレートに紐づいているかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid WEBメールアカウントSID
     * @throws SQLException 
     */
    private boolean __validateTemplateBin(
        RestApiContext ctx, int templateSid, long[] binSids) throws SQLException {

        //バイナリSID情報を取得
        Connection con = ctx.getCon();
        WmlMailTemplateFileDao wtpfDao = new WmlMailTemplateFileDao(con);
        String[] binSidStr = wtpfDao.getBinSid(templateSid);
        List<Long> binSidList = Arrays.stream(binSidStr)
            .map(sid -> NullDefault.getLong(sid, -1))
            .collect(Collectors.toList());
        
        //指定された全ての添付ファイルが、テンプレートに紐づいているか確認
        List<Long> inputBinSids = Arrays.stream(binSids).boxed().collect(Collectors.toList());
        boolean accessFlg = binSidList.containsAll(inputBinSids);

        return accessFlg;
    }

    /**
     * <br>[機  能] アカウントSIDから当該アカウントが保持するテンプレート情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid WEBメールアカウントSID
     * @return テンプレート情報
     */
    public List<WmlMailTemplateModel> getTemplates(Connection con, int wacSid) throws SQLException {

        WmlMailTemplateDao dao = new WmlMailTemplateDao(con);
        List<WmlMailTemplateModel> ret = dao.getMailTemplateList(wacSid);
        return ret;
    }
}
