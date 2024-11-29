package jp.groupsession.v2.wml.restapi.accounts.mails.draft;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.biz.WmlTempFileBiz;
import jp.groupsession.v2.wml.exception.WmlDiskSizeOverException;
import jp.groupsession.v2.wml.exception.WmlMailSizeOverException;
import jp.groupsession.v2.wml.model.WmlSendResultModel;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestTempFileBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestTemplateBinBiz;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

public class WmlAccountsMailsDraftPutBiz {

    /** RestApiコンテキスト */
    private final RestApiContext ctx__;
    /** GSコンテキスト */
    private GSContext context__;
    /** パラメータ */
    private WmlAccountsMailsDraftPutParamModel param__;

    /** テンポラリディレクトリパス */
    private String tempDir__;

    /** テンポラリディレクトリに配置されている添付ファイル名 */
    private List<String> tempFileNameList__ = new ArrayList<String>();

    /** 実行結果 */
    private List<WmlAccountsMailsResultModel> result__ = new ArrayList<WmlAccountsMailsResultModel>();

    private WmlMailSendBiz sender__;

    /** 
     * コンストラクタ
     * @param ctx
     * @param param
     * @param context
     * @throws IOException 
     * @throws IOToolsException 
     * @throws SQLException 
     * @throws TempFileException 
    */
    public WmlAccountsMailsDraftPutBiz(WmlAccountsMailsDraftPutParamModel param,
        RestApiContext ctx, GSContext context, String tempDir) throws SQLException, IOToolsException, IOException, TempFileException {
        ctx__ = ctx;
        param__ = param;
        context__ = context;
        tempDir__ = tempDir;
        sender__ = __createMailSendBiz();
    }

    /** 
     * 草稿保存の実行
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイル操作例外
     * @throws IOToolsException 添付ファイル操作例外
     */
    protected void _execute() throws SQLException, IOException, IOToolsException {
        boolean defAutoCommit = ctx__.getCon().getAutoCommit();
        ctx__.getCon().setAutoCommit(false);
        RequestModel reqMdl = ctx__.getRequestModel();
        WmlSendResultModel result = null;
        try {
            result = sender__.executeDraftMail(
                ctx__.getCon(),
                context__,
                ctx__.getRequestUserSid(),
                ctx__.getAppRootPath(),
                tempDir__,
                reqMdl.getDomain(),
                reqMdl.getLocale()
                );
            ctx__.getCon().commit();
        } catch (WmlMailSizeOverException e) {
            //メールのサイズが上限を超えていた場合
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(WmlEnumReasonCode.FAILED_SAVE_MAIL_SIZE);
            apiException.setMessageResource(
                "error.sizeover.sendmail", e.getMaxSizeText());
            throw apiException;
        } catch(WmlDiskSizeOverException e) {
            //ディスクの容量制限を超えていた場合
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(WmlEnumReasonCode.FAILED_SAVE_DISK_CAPACITY);
            apiException.setMessageResource(
                "errors.free.msg", e.getErrorMsg());
            throw apiException;
        } catch (TempFileException e) {
            //添付ファイルの登録に失敗した場合
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(EnumError.SYS_RUNTIME_ERROR);
            apiException.setMessageResource(
                "error.free.msg", e.getMessage());
            throw apiException;
        } catch (IOException e) {
            //HTMLメールの作成に失敗
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(EnumError.SYS_RUNTIME_ERROR);
            apiException.setMessageResource(
                "error.free.msg", e.getMessage());
            throw apiException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (result == null) {
                //参照メールとテンプレートに紐づいているファイルをディレクトリから削除
                WmlRestTempFileBiz.deleteTempFile(tempDir__, tempFileNameList__);
            }
        }
        
        //結果の取得
        SmtpSendModel sendData = null;
        if (result != null) {
            sendData = result.getSendMailData();
        }

        //API実行結果の作成
        __setApiResult(result);

        //ログ出力
        __outputResultLog(sendData);

        //オートコミットの設定
        ctx__.getCon().commit();
        ctx__.getCon().setAutoCommit(defAutoCommit);

    }

    public List<WmlAccountsMailsResultModel> getResult() {
        return result__;
    }
    
    /**
     * 
     * @return
     * @throws SQLException
     * @throws IOToolsException
     * @throws IOException
     * @throws TempFileException 
     */
    private WmlMailSendBiz __createMailSendBiz() throws SQLException, IOToolsException, IOException, TempFileException {
        String appRootPath = ctx__.getAppRootPath();
        int userSid = ctx__.getRequestUserSid();
        Connection con = ctx__.getCon();

        WmlRestAccountBiz accountBiz = new WmlRestAccountBiz();
        int wacSid = accountBiz.getWmlAccountSid(con, param__.getAccountId());

        int sendPlanType = param__.getSendPlanType();
        UDate sendPlanDate = param__.getSendPlanDateTime();
        if (sendPlanDate == null 
            && param__.getSendPlanType() == GSConstWebmail.TIMESENT_FUTURE) {
            //予約送信が選択されているが日時未入力の場合は、即時送信とする
            sendPlanType = GSConstWebmail.TIMESENT_NORMAL;
        }

        //参照メールの添付ファイルをテンポラリディレクトリに配置
        __setRefarenceFileData();

        WmlRestTemplateBinBiz templateBiz = new WmlRestTemplateBinBiz();
        boolean canUseTemplate = templateBiz.validateTemplate(ctx__, wacSid, param__.getTemplateSid());
        if (canUseTemplate) {
            //テンプレートを使用できる場合、テンプレートの添付ファイルをテンポラリディレクトリに配置
            //利用できない添付ファイルは配置処理の中ではじかれる
            __setTemplateFileData();
        }

        int compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_NOTCOMPRESS;
        if (param__.getCompressFileFlg() == GSConstWebmail.RESTAPI_COMPRESS_YES) {
            compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_COMPRESS;
        }
        
        WmlMailSendBiz mailBiz = WmlMailSendBiz.builder()
            .setTo(WmlMailSendBiz.joinAddress(param__.getToArray()))
            .setCc(WmlMailSendBiz.joinAddress(param__.getCcArray()))
            .setBcc(WmlMailSendBiz.joinAddress(param__.getBccArray()))
            .setSubject(param__.getSubjectText())
            .setContent(param__.getBodyText())
            .setWacSid(wacSid)
            .setSendMessageNum(param__.getMailSid())
            .setSendMailType(GSConstWebmail.SEND_TYPE_EDIT)
            .setHtmlMail(false)
            .setTimeSentType(sendPlanType)
            .setSendPlanDate(sendPlanDate)
            .setCompressFileType(compressFileFlg)
            .build(con, context__, appRootPath, tempDir__, userSid);


        return mailBiz;
    }
    /**
     * テンプレートの添付ファイルをテンポラリディレクトリに展開する
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     * @throws SQLException
     */
    private void __setTemplateFileData() throws
        TempFileException, IOException, IOToolsException, SQLException {
        WmlTempFileBiz tempBinBiz = new WmlTempFileBiz();
        tempBinBiz.deproyAttachimentTemplateFileData(
            ctx__.getCon(),
            ctx__.getRequestModel(),
            param__.getTemplateSid(),
            param__.getTemplateBinSidArray(),
            ctx__.getAppRootPath(),
            tempDir__);        
    }
    /** 
     * 参照メールの添付ファイルをテンポラリディレクトリに展開する
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     * @throws SQLException 
    */
    private void __setRefarenceFileData() throws
    TempFileException, IOException, IOToolsException, SQLException {
        WmlTempFileBiz tempBinBiz = new WmlTempFileBiz();
        tempBinBiz.deproyAttachimentRefarenceFileData(
            ctx__.getCon(),
            ctx__.getRequestModel(),
            param__.getMailSid(),
            param__.getRefarenceBinSidArray(),
            ctx__.getAppRootPath(),
            tempDir__);        

    }

    /**
     * <br>[機  能] APIの実行結果の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws Exception 
     */
    private void __outputResultLog(SmtpSendModel sendData)  {

        if (sendData == null) {
            return;
        }
        //ログ出力
        GsMessage gsMsg = new GsMessage(ctx__.getRequestModel());
        String value = null;
        RequestModel reqMdl = ctx__.getRequestModel();
        value = gsMsg.getMessage("cmn.save.draft") + "：";
        
        value += "[" + gsMsg.getMessage("cmn.subject") + "]" + sendData.getSubject();
        value += " [To]" + NullDefault.getString(sendData.getTo(), "");
        value += " [Cc]" + NullDefault.getString(sendData.getCc(), "");
        value += " [Bcc]" + NullDefault.getString(sendData.getBcc(), "");
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.outPutApiLog(reqMdl, ctx__.getCon(), ctx__.getRequestUserSid(),
                "RESTAPI_MAIL_EDIT_DRAFT", gsMsg.getMessage("cmn.entry"),
                GSConstLog.LEVEL_TRACE, StringUtil.trimRengeString(value, 3000));
    }

    /**
     * <br>[機  能] APIの実行結果の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     * @throws Exception 
     */
    private void __setApiResult(WmlSendResultModel resultModel) throws SQLException {

        if (resultModel == null || resultModel.getSendMailData() == null) {
            return;
        }

        int wacSid = (int) resultModel.getSendMailData().getWacSid();
        long mailNum = resultModel.getMailNum();
        
        WmlRestMailDataBiz biz = new WmlRestMailDataBiz();
        Connection con = ctx__.getCon();
        long[] mailSidArray = new long[] {mailNum};
        String appRootPath = ctx__.getAppRootPath();

        List<WmlAccountsMailsResultModel> resultList = biz.getMailsResult(con, wacSid, mailSidArray, appRootPath);
        for (WmlAccountsMailsResultModel mdl : resultList) {
            //送信したメール1件の情報を取得
            result__ = new ArrayList<>();
            result__.add(mdl);
            break;
        }

    }

    
}
